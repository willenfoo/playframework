package org.apache.playframework.mybatisplus.plugins;

import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.Token;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.plugins.parser.AbstractJsqlParser;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @date 2018-03-22
 * @author willenfoo
 * 由于开发人员水平参差不齐，即使订了开发规范很多人也不遵守
 * SQL是影响系统性能最重要的因素，所以拦截掉垃圾SQL语句
 *
 * 拦截SQL类型的场景
 *  1.必须使用到索引，包含left jion连接字段，符合索引最左原则
 *     必须使用索引好处，
 *     1.1 如果因为动态SQL，bug导致update的where条件没有带上，全表更新上万条数据
 *     1.2 如果检查到使用了索引，SQL性能基本不会太差
 *
 * 2.SQL尽量单表执行，有查询left jion的语句，必须在注释里面允许该SQL运行，否则会被拦截，有left jion的语句，如果不能拆成单表执行的SQL，请leader商量在做
 *    http://gaoxianglong.github.io/shark/
 *    SQL尽量单表执行的好处
 *    2.1 查询条件简单、易于开理解和维护；
 *    2.2 扩展性极强；（可为分库分表做准备）
 *    2.3 缓存利用率高；
 *
 * 2.在字段上使用函数
 * 3.where条件为空
 * 4.where条件使用了 !=
 * 5.where条件使用了 not 关键字
 * 6.不要使用子查询
 */
public class IllegalSQLIndexInterceptor extends AbstractJsqlParser {

	private static Map<String, List<IndexInfo>> indexInfoMap = new ConcurrentHashMap<String, List<IndexInfo>>();

	private String dbType;



	public List<IndexInfo> getIndexInfos(String key, String dbName, String tableName, StatementProxy statement) {
		List<IndexInfo> indexInfos = indexInfoMap.get(key);
		if (indexInfos == null || indexInfos.isEmpty()) {
			Connection conn = null;
			ResultSet rs = null;
			try {
				conn = statement.getConnectionProxy();
				DatabaseMetaData metadata = conn.getMetaData();
				rs = metadata.getIndexInfo(dbName, dbName, tableName, false, true);
				indexInfos = new ArrayList<IndexInfo>();
				while (rs.next()) {
					//索引中的列序列号等于1，才有效
					if (StringUtils.equals(rs.getString(8), "1")) {
						IndexInfo indexInfo = new IndexInfo();
						indexInfo.setDbName(rs.getString(1));
						indexInfo.setTableName(rs.getString(3));
						indexInfo.setColumnName(rs.getString(9));
						indexInfos.add(indexInfo);
					}
				}
				indexInfoMap.put(key, indexInfos);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return indexInfos;
	}

	public static Map<String, List<IndexInfo>> getIndexInfoMap() {
		return indexInfoMap;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	@Override
	public void processInsert(Insert insert) {

	}

	@Override
	public void processDelete(Delete delete) {

	}

	@Override
	public void processUpdate(Update update) {

	}

	@Override
	public void processSelectBody(SelectBody selectBody) {

	}

	private static class IndexInfo {

		private String dbName;

		private String tableName;

		private String columnName;

		public String getDbName() {
			return dbName;
		}

		public void setDbName(String dbName) {
			this.dbName = dbName;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
	}

}
