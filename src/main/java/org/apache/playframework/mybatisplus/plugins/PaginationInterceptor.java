/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.playframework.mybatisplus.plugins;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.MybatisDefaultParameterHandler;
import com.baomidou.mybatisplus.entity.CountOptimize;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.toolkit.JdbcUtils;
import com.baomidou.mybatisplus.toolkit.PluginUtils;
import com.baomidou.mybatisplus.toolkit.SqlUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ComparisonOperator;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;

/**
 * <p>
 * 分页拦截器
 * </p>
 *
 * @author hubin
 * @Date 2016-01-23
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PaginationInterceptor implements Interceptor {

    private static final Log logger = LogFactory.getLog(PaginationInterceptor.class);

    private static final Map<String, TableInfo> tableInfoMap = new ConcurrentHashMap<>();
    
    /* 溢出总页数，设置第一页 */
    private boolean overflowCurrent = false;
    /* 是否设置动态数据源 设置之后动态获取当前数据源 */
    private boolean dynamicDataSource = false;
    /* Count优化方式 */
    private String optimizeType = "default";
    /* 方言类型 */
    private String dialectType;
    /* 方言实现类 */
    private String dialectClazz;

    /**
     * 
	 * id分页需要满足的条件，  
	 * 第一：表中最新的数据排在最前面（desc） 或者排在最后面（asc），无特殊排序需求
	 * 第二：不能跳页，比如成第一页跳转到第三页
	 * 
	 * 实现思路
	 * 第一：得到 from 的表名 及 as 的别名,也得到表对应的主健id，拼接成字符串准备放在where条件后面
	 * 第二：得到排序是 desc 还是 asc， 人工是desc就用 id < 传入id值， 人工是asc 就用 id > 传入id值
     * 第三：得到sql中是否有where条件，把 id 加到where条件第一位，利用索引性能极高 
     * @param pageId
     * @param buildSql
     * @return
     */
    private String getPageIdSql(PageId<?> pageId, String buildSql) {
    	if (pageId.getIndexId() != null) {
        	Select select = null;
			try {
				select = (Select) CCJSqlParserUtil.parse(buildSql);
			} catch (JSQLParserException e) {
				return buildSql;
			}
        	SelectBody selectBody = select.getSelectBody();
        	PlainSelect plainSelect = (PlainSelect)selectBody;
        	Table table = (Table)plainSelect.getFromItem();
        	
        	if (tableInfoMap.isEmpty()) {
        		List<TableInfo> tableInfos = TableInfoHelper.getTableInfos();
        		for (TableInfo tableInfo : tableInfos) {
        			tableInfoMap.put(tableInfo.getTableName(), tableInfo);
        		}
        	}
        	
        	//是否存在别名
        	Alias alias = plainSelect.getFromItem().getAlias();
        	String pageIdFilter = null;
        	if (alias != null) {
        		pageIdFilter = alias.getName() + "." + tableInfoMap.get(table.getName()).getKeyColumn();
        	} else {
        		pageIdFilter = tableInfoMap.get(table.getName()).getKeyColumn();
        	}
        	ComparisonOperator co = null;
        	List<OrderByElement> orderByElements = plainSelect.getOrderByElements();
        	 
    		// 不允许多字段 复杂排序
    		if (orderByElements != null && orderByElements.size() > 1) {
    			throw new MybatisPlusException("根据id分页,不允许多字段 复杂排序");
    		} else {
    			OrderByElement orderByElement = null;
    			if (orderByElements != null) {
    				orderByElement = orderByElements.get(0);
    			} else {
    				orderByElement = new OrderByElement();
    				orderByElement.setExpression(new Column(pageIdFilter));
    				orderByElements = new ArrayList<OrderByElement>();
    			}
    			if (orderByElement.isAscDescPresent() && pageId.getCurrent() > 0) {
    				co = new MinorThan();
    				pageId.setAsc(false);
    			} else if (orderByElement.isAscDescPresent() && pageId.getCurrent() < 0) {
    				co = new GreaterThan();
    				orderByElement.setAscDescPresent(false);
    				orderByElement.setAsc(true);
    				plainSelect.setOrderByElements(orderByElements);
    				pageId.setAsc(false);
    			} else if (orderByElement.isAsc() && pageId.getCurrent() > 0) {
    				co = new GreaterThan();
    				pageId.setAsc(true);
    			} else if (orderByElement.isAsc() && pageId.getCurrent() < 0) {
    				co = new MinorThan();
    				orderByElement.setAscDescPresent(true);
    				orderByElement.setAsc(false);
    				orderByElements.add(orderByElement);
    				plainSelect.setOrderByElements(orderByElements);
    				pageId.setAsc(true);
    			}
    		}
        	 
        	Expression RIGHT_EXPRESSION = new Column(pageId.getIndexId().toString());
        	
        	co.setLeftExpression(new Column(pageIdFilter));
        	co.setRightExpression(RIGHT_EXPRESSION);
        	if (plainSelect.getWhere() != null) {
        		plainSelect.setWhere(new AndExpression(co, plainSelect.getWhere()));
        	} else {
        		plainSelect.setWhere(co);
        	}
        	return buildSql = select.toString();
        } 
    	return buildSql;
    }
    
    /**
     * Physical Pagination Interceptor for all the queries with parameter {@link org.apache.ibatis.session.RowBounds}
     */
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        // 先判断是不是SELECT操作
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
        /* 不需要分页的场合 */
        if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();
        Connection connection = (Connection) invocation.getArgs()[0];
        if (isDynamicDataSource()) {
            dialectType = JdbcUtils.getDbType(connection.getMetaData().getURL()).getDb();
        }
        
        if (rowBounds instanceof PageId) {
        	/**
        	 * id分页需要满足的条件，  
        	 * 第一：表中最新的数据排在最前面（desc） 或者排在最后面（asc），无特殊排序需求
        	 * 第二：不能跳页，比如成第一页跳转到第三页
        	 * 
        	 * 实现思路
        	 * 第一：得到 from 的表名 及 as 的别名,也得到表对应的主健id，拼接成字符串准备放在where条件后面
        	 * 第二：得到排序是 desc 还是 asc， 人工是desc就用 id < 传入id值， 人工是asc 就用 id > 传入id值
             * 第三：得到sql中是否有where条件，把 id 加到where条件第一位，利用索引性能极高 
        	 */
        	PageId<?> pageId = (PageId<?>) rowBounds;
            boolean orderBy = true;
            if (pageId.isSearchCount()) {
                CountOptimize countOptimize = SqlUtils.getCountOptimize(originalSql, optimizeType, dialectType, pageId.isOptimizeCount());
                orderBy = countOptimize.isOrderBy();
                this.queryTotal(countOptimize.getCountSQL(), mappedStatement, boundSql, pageId, connection);
                if (pageId.getTotal() <= 0) {
                    return invocation.proceed();
                }
            }
            String buildSql = SqlUtils.concatOrderBy(originalSql, pageId, orderBy);
            buildSql = getPageIdSql(pageId, buildSql);
            originalSql = DialectFactory.buildPaginationSql(pageId, buildSql, dialectType, dialectClazz);
        } else if (rowBounds instanceof Pagination) {
            Pagination page = (Pagination) rowBounds;
            boolean orderBy = true;
            if (page.isSearchCount()) {
                CountOptimize countOptimize = SqlUtils.getCountOptimize(originalSql, optimizeType, dialectType, page.isOptimizeCount());
                orderBy = countOptimize.isOrderBy();
                this.queryTotal(countOptimize.getCountSQL(), mappedStatement, boundSql, page, connection);
                if (page.getTotal() <= 0) {
                    return invocation.proceed();
                }
            }
            String buildSql = SqlUtils.concatOrderBy(originalSql, page, orderBy);
            originalSql = DialectFactory.buildPaginationSql(page, buildSql, dialectType, dialectClazz);
        } else {
            // support physical Pagination for RowBounds
            originalSql = DialectFactory.buildPaginationSql(rowBounds, originalSql, dialectType, dialectClazz);
        }

		/*
         * <p> 禁用内存分页 </p> <p> 内存分页会查询所有结果出来处理（这个很吓人的），如果结果变化频繁这个数据还会不准。</p>
		 */
        metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
        metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        
        return invocation.proceed();
    }

    /**
     * 查询总记录条数
     *
     * @param sql
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    protected void queryTotal(String sql, MappedStatement mappedStatement, BoundSql boundSql, Pagination page, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            DefaultParameterHandler parameterHandler = new MybatisDefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), boundSql);
            parameterHandler.setParameters(statement);
            int total = 0;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getInt(1);
                }
            }
            page.setTotal(total);
            /*
             * 溢出总页数，设置第一页
			 */
            int pages = page.getPages();
            if (overflowCurrent && (page.getCurrent() > pages)) {
                page = new Pagination(1, page.getSize());
                page.setTotal(total);
            }
        } catch (Exception e) {
            logger.error("Error: Method queryTotal execution error !", e);
        }
    }

    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    public void setProperties(Properties prop) {
        String dialectType = prop.getProperty("dialectType");
        String dialectClazz = prop.getProperty("dialectClazz");

        if (StringUtils.isNotEmpty(dialectType)) {
            this.dialectType = dialectType;
        }
        if (StringUtils.isNotEmpty(dialectClazz)) {
            this.dialectClazz = dialectClazz;
        }
    }

    public void setDialectType(String dialectType) {
        this.dialectType = dialectType;
    }

    public void setDialectClazz(String dialectClazz) {
        this.dialectClazz = dialectClazz;
    }

    public void setOverflowCurrent(boolean overflowCurrent) {
        this.overflowCurrent = overflowCurrent;
    }

    public void setOptimizeType(String optimizeType) {
        this.optimizeType = optimizeType;
    }

    public boolean isDynamicDataSource() {
        return dynamicDataSource;
    }

    public void setDynamicDataSource(boolean dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }
}
