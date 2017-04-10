package org.apache.playframework.druid.filter;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat.Mode;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.StringUtils;

/**
 * 非法sql拦截，拦截sql的全表更新（不带where条件，必须带上二个以上where条件，可配置带一个where条件），
 * 屏蔽删除（可配置允许删除的表）操作及更新结果影响条数，一般来说90%情况下，不超过一条
 * @author willenfoo
 * @Date 2017-04-09
 *
 */
public class IllegalSQLFilter extends FilterEventAdapter {

	private  Logger logger = LogManager.getLogger(getClass());

	
	/**
	 * 允许删除的表设置，否则不允许删除数据
	 */
	private List<String> allowDeleteTables;
	
	/**
	 * 可配置带一个where条件的表
	 */
	private List<String> updateWhereOneTables;
	
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

	@Override
	protected void statementExecuteBefore(StatementProxy statement, String sql) {
		String dbType = JdbcConstants.MYSQL;
		List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
		// 解析出的独立语句的个数
		for (int i = 0; i < stmtList.size(); i++) {
			SQLStatement stmt = stmtList.get(i);
			MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
			stmt.accept(visitor);
			
			String currentTable = visitor.getCurrentTable();
			String sqlCommandType = visitor.getTableStat(currentTable).toString();
			if (StringUtils.equals(Mode.Update.name(), sqlCommandType)) {
				boolean isWhereGtOne = true;
				if (updateWhereOneTables != null && !updateWhereOneTables.isEmpty()) {
					for (String table : updateWhereOneTables) {
						if (StringUtils.equals(currentTable, table)) {
							isWhereGtOne = false;
							break;
						}
					}
				}
				if (isWhereGtOne) {
					if (visitor.getConditions() == null || visitor.getConditions().size() < 2) {
						String errorMsg = "非法sql,update 语句必须要有二个以上where条件 ,sql: " + SQLUtils.format(sql, dbType);
						logger.error(errorMsg);
						throw new IllegalArgumentException(errorMsg);
					}
				}
			} else if (StringUtils.equals(Mode.Delete.name(), sqlCommandType)) {
				boolean isAllowDelete = false;
				if (allowDeleteTables != null && !allowDeleteTables.isEmpty()) {
					for (String table : allowDeleteTables) {
						if (StringUtils.equals(currentTable, table)) {
							isAllowDelete = true;
							break;
						}
					}
				}
				if (isAllowDelete == false) {
					String errorMsg = "非法sql,不允许删除数据, table:"+currentTable+",sql: " + SQLUtils.format(sql, dbType);
					logger.error(errorMsg);
					throw new IllegalArgumentException(errorMsg);
				}
			}
		}
		super.statementExecuteBefore(statement, sql);
	}

	@Override
	protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {
		if (!result) {
			int updateCount = 0;
			try {
				updateCount = statement.getUpdateCount();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			if (updateCount > 1) {
				String dbType = JdbcConstants.MYSQL;
				List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
				// 解析出的独立语句的个数
				for (int i = 0; i < stmtList.size(); i++) {
					SQLStatement stmt = stmtList.get(i);
					MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
					stmt.accept(visitor);
					String sqlCommandType = visitor.getTableStat(visitor.getCurrentTable()).toString();
					if (StringUtils.equals(Mode.Update.name(), sqlCommandType)) {
						String errorMsg = "非法sql,update 更新了二条以上数据,如有需要，请配置SQL白名单 ,sql: " + SQLUtils.format(sql, dbType);
						logger.error(errorMsg);
						throw new IllegalArgumentException(errorMsg);
					}
				}
			}
		}
		super.statementExecuteAfter(statement, sql, result);
	}

}
