package org.apache.playframework.mybatisplus.plugins;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.executor.Executor;
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

import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;

/**
 * 非法sql拦截, 配合Druid的WallConfig使用， http://blog.csdn.net/yinxiangbing/article/details/47905403
 * deleteWhereNoneCheck false 检查DELETE语句是否无where条件，这是有风险的，但不是SQL注入类型的风险
 * updateWhereNoneCheck false 检查UPDATE语句是否无where条件，这是有风险的，但不是SQL注入类型的风险
 * ，拦截sql的全表更新（不带where条件，必须带上二个以上where条件，可配置带一个where条件），
 * 屏蔽删除（可配置允许删除的表）操作及更新结果影响条数，一般来说90%情况下，不超过一条
 * @author willenfoo
 * @Date 2017-04-09
 *
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class IllegalSQLInterceptor implements Interceptor {

	private static final Map<String, TableInfo> tableInfoMap = new ConcurrentHashMap<>();
	 
    private static final Log logger = LogFactory.getLog(IllegalSQLInterceptor.class);
	
	/**
	 * 允许删除的表设置，否则不允许删除数据
	 */
	private List<String> allowDeleteTables;
	
	/**
	 * 可配置带一个where条件的表
	 */
	private List<String> updateWhereOneTables;

	/**
	 * 根据逻辑删除判断，可配置带一个where条件的表
	 */
	public boolean logicDeleteUpdate = true;
	
	/**
	 * 根据逻辑删除判断，是否可以删除
	 */
	public boolean logicDeleteRemove = true;
	
	
    public Object intercept(Invocation invocation) throws Throwable {
    	if (tableInfoMap.isEmpty()) {
    		List<TableInfo> tableInfos = TableInfoHelper.getTableInfos();
    		for (TableInfo tableInfo : tableInfos) {
    			tableInfoMap.put(tableInfo.getTableName(), tableInfo);
    		}
    	}
        //处理UPDATE 语句
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        if (ms.getSqlCommandType() == SqlCommandType.UPDATE) {
            Object parameter = invocation.getArgs()[1];
            BoundSql boundSql = ms.getBoundSql(parameter);
            
            // 得到当前SQL操作的表名
            MySqlStatementParser sqlStatementParser = new MySqlStatementParser(boundSql.getSql()) ;
    		SQLUpdateStatement updateStatement = sqlStatementParser.parseUpdateStatement();
    		String tableName = updateStatement.getTableName().getSimpleName(); 
    		
            boolean isWhereGtOne = true;
            
            //根据逻辑删除判断，是否必须要有二个以上where条件，实体存在逻辑删除，需要二个以上where，否则只需要一个
            if (isLogicDeleteUpdate()) {
            	isWhereGtOne = tableInfoMap.get(tableName).isLogicDelete();
            } else {
            	// 按照配置来判断，是否必须要有二个以上where条件
            	if (updateWhereOneTables != null && !updateWhereOneTables.isEmpty()) {
					for (String table : updateWhereOneTables) {
						if (StringUtils.equals(tableName, table)) {
							isWhereGtOne = false;
							break;
						}
					}
				}
            }
            
            // 这里只判断了where条件后面必须有二个参数， 一个参数判断的 交给了  Druid deleteWhereNoneCheck来检查
			if (isWhereGtOne) {
				if (boundSql.getParameterMappings() == null || boundSql.getParameterMappings().size() < 2) {
					String errorMsg = "非法sql,update 语句必须要有二个以上where条件,如需一个where条件,请在updateWhereOneEntitys 属性中  配置白名单 , 方法是：" + ms.getId() + ", sql: "
							+ boundSql.getSql();
					logger.error(errorMsg);
					throw new IllegalArgumentException(errorMsg);
				}
			}
			Integer updateCount = Integer.valueOf(invocation.proceed().toString());
			if (updateCount > 1) {
				String errorMsg = "非法sql,update 更新了二条以上数据,如需更新多条数据，请配置SQL白名单  , 方法是：" + ms.getId() + ", sql: " + boundSql.getSql();
				logger.error(errorMsg);
				throw new IllegalArgumentException(errorMsg);
			}
			return updateCount;
        } 
        
        //处理 DELETE UPDATE 语句
        else if (ms.getSqlCommandType() == SqlCommandType.DELETE) {
            Object parameter = invocation.getArgs()[1];
            BoundSql boundSql = ms.getBoundSql(parameter);
            
           // 得到当前SQL操作的表名
            MySqlStatementParser sqlStatementParser = new MySqlStatementParser(boundSql.getSql()) ;
            MySqlDeleteStatement deleteStatement = sqlStatementParser.parseDeleteStatement();
    		String tableName = deleteStatement.getTableName().getSimpleName();
    		
        	boolean isAllowDelete = false;
        	
        	// 根据逻辑删除来判断是否可以删除， 如果是逻辑删除，不允许删除
        	if (isLogicDeleteRemove()) {
        		isAllowDelete = !tableInfoMap.get(tableName).isLogicDelete();
        	} else {
        		if (allowDeleteTables != null && !allowDeleteTables.isEmpty()) {
					for (String table : allowDeleteTables) {
						if (StringUtils.equals(tableName, table)) {
							isAllowDelete = true;
							break;
						}
					}
				}
        	}
			if (!isAllowDelete) {
				String errorMsg = "非法sql,不允许删除数据,如需删除，请在 allowDeleteEntitys 属性中配置白名单  , entiy:" + parameter.toString()
						+ ",sql: " + boundSql.getSql();
				logger.error(errorMsg);
				throw new IllegalArgumentException(errorMsg);
			}
			
			 // 删除也必须在where条件后面带上一个参数， 一个参数判断的 交给了  Druid deleteWhereNoneCheck true 来检查
        }
        return invocation.proceed();
    }


    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    public void setProperties(Properties prop) {
        
    }

	public List<String> getAllowDeleteTables() {
		return allowDeleteTables;
	}


	public void setAllowDeleteTables(List<String> allowDeleteTables) {
		this.allowDeleteTables = allowDeleteTables;
	}


	public List<String> getUpdateWhereOneTables() {
		return updateWhereOneTables;
	}


	public void setUpdateWhereOneTables(List<String> updateWhereOneTables) {
		this.updateWhereOneTables = updateWhereOneTables;
	}


	public boolean isLogicDeleteUpdate() {
		return logicDeleteUpdate;
	}


	public void setLogicDeleteUpdate(boolean logicDeleteUpdate) {
		this.logicDeleteUpdate = logicDeleteUpdate;
	}


	public boolean isLogicDeleteRemove() {
		return logicDeleteRemove;
	}


	public void setLogicDeleteRemove(boolean logicDeleteRemove) {
		this.logicDeleteRemove = logicDeleteRemove;
	}

}
