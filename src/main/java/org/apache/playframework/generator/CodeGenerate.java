package org.apache.playframework.generator;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.playframework.freemarker.FreemarkerHelper;
import org.apache.playframework.generator.mybatisplus.AutoGenerator;
import org.apache.playframework.generator.mybatisplus.ConfigDataSource;
import org.apache.playframework.util.StringUtils;

import com.baomidou.mybatisplus.enums.IdType;


/**
 * Hello world!
 *
 */
public class CodeGenerate extends AutoGenerator {

	private ConfigGenerator config;

	protected static final String JSP_SUFFIX = ".jsp";

	public CodeGenerate(ConfigGenerator config) {
		super.config = config;
		this.config = config;
	}

	public void execute() {
		if (config.getIdType() == null) {
			config.setIdType(IdType.AUTO);
		}
		
		// 其他参数请根据上面的参数说明自行配置，当所有配置完善后，运行AutoGenerator.run()方法生成Code
		// 生成代码
		run(config);

		
		generate();
	}

	/**
	 * 生成映射文件
	 */
	public void generate() {
		Connection conn = null;
		try {
			/**
			 * 创建连接
			 */
			Class.forName(config.getDbDriverName());
			conn = DriverManager.getConnection(config.getDbUrl(), config.getDbUser(), config.getDbPassword());

			/**
			 * 获取数据库表相关信息
			 */
			boolean isOracle = false;
			if (config.getConfigDataSource() == ConfigDataSource.ORACLE) {
				isOracle = true;
			}

			/**
			 * 根据配置获取应该生成文件的表信息
			 */
			List<String> tables = getTables(conn);
			if (null == tables) {
				return;
			}

			/**
			 * 检查文件夹是否存在
			 */
			File gf = new File(config.getSaveDir());
			if (!gf.exists()) {
				gf.mkdirs();
			}

			/**
			 * 修改设置最终目录的逻辑
			 */
			String saveDir = gf.getPath();
			String dtoPath = getFilePath(saveDir, getPathFromPackageName(config.getDtoPackage()));
			
			FreemarkerHelper viewEngine = new FreemarkerHelper("/org/apache/playframework/generator/template");
			Map<String, String> tableComments = getTableComment(conn);
			for (String table : tables) {
				List<String> columns = new ArrayList<String>();
				
				List<String> types = new ArrayList<String>();
				List<String> comments = new ArrayList<String>();
				/* ID 是否存在,不考虑联合主键设置 */
				boolean idExist = false;
				Map<String, IdInfo> idMap = new HashMap<String, IdInfo>();
				
				String tableFieldsSql = String.format(config.getConfigDataSource().getTableFieldsSql(), table);
				ResultSet results = conn.prepareStatement(tableFieldsSql).executeQuery();
				while (results.next()) {
					String field = results.getString(config.getConfigDataSource().getFieldName());

					/* 开启 baseEntity 跳过公共字段 */
					if (null != config.getConfigBaseEntity() && config.getConfigBaseEntity().includeColumns(field)) {
						continue;
					}

					columns.add(field);
					types.add(results.getString(config.getConfigDataSource().getFieldType()));
					comments.add(results.getString(config.getConfigDataSource().getFieldComment()));
					if (!isOracle && !idExist) {
						/* MYSQL 主键ID 处理方式 */
						String key = results.getString(config.getConfigDataSource().getFieldKey());
						if ("PRI".equals(key)) {
							boolean autoIncrement = false;
							if ("auto_increment".equals(results.getString("EXTRA"))) {
								autoIncrement = true;
							}
							idExist = true;
							idMap.put(field, new IdInfo(field, autoIncrement));
						}
					}
				}
				if (isOracle) {
					/* ORACLE 主键ID 处理方式 */
					String idSql = String.format(
							"SELECT A.COLUMN_NAME FROM USER_CONS_COLUMNS A, USER_CONSTRAINTS B WHERE A.CONSTRAINT_NAME = B.CONSTRAINT_NAME AND B.CONSTRAINT_TYPE = 'P' AND A.TABLE_NAME = '%s'",
							table);
					ResultSet rs = conn.prepareStatement(idSql).executeQuery();
					while (rs.next() && !idExist) {
						String field = rs.getString(config.getConfigDataSource().getFieldKey());
						idExist = true;
						idMap.put(field, new IdInfo(field, false));
					}
				}
				
				
				String beanName = getBeanName(table, config.isDbPrefix());

				List<Column> columnList = new ArrayList<Column>(); 
				for (int i = 0; i < columns.size(); i++) {
					Column column = new Column();
					column.setPropertyName(processField(columns.get(i)));
					column.setRemarks(comments.get(i));
					column.setClassName(mysqlProcessType(types.get(i)));
					columnList.add(column);
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("beanName", beanName);
				params.put("columns", columnList);
				params.put("dubboRegistryId", config.getDubboRegistryId());
				
				if (StringUtils.isNotBlank(config.getDtoPackage())) {
					String dtoName = String.format(config.getDtoName(), beanName);
					params.put("targetPackage", config.getDtoPackage());
					params.put("dtoName", dtoName);
					String dtoText = viewEngine.parse("dto.ftl", params);
					String dtoAddress = dtoPath +"/" + dtoName + JAVA_SUFFIX;
					FileUtils.write(new File(dtoAddress), dtoText);
				}
				
				if (StringUtils.isNotBlank(config.getControllerPackage())) {
					String controllerName = String.format(config.getControllerName(), beanName);
					String serviceName = String.format(config.getServiceName(), beanName);
					params.put("package", config.getControllerPackage());
					params.put("controllerName", controllerName);
					params.put("modelPackage", config.getEntityPackage());
					params.put("servicePackage", config.getServicePackage());
					params.put("serviceName", serviceName);
					String dtoText = viewEngine.parse("controller.ftl", params);
					String dtoAddress = PATH_CONTROLLER_IMPL +"/" + controllerName + JAVA_SUFFIX;
					FileUtils.write(new File(dtoAddress), dtoText);
				}
				
				if (StringUtils.isNotBlank(config.getDubboRegistryId())) {
					String serviceImplName = String.format(config.getServiceImplName(), beanName);
					String serviceName = String.format(config.getServiceName(), beanName);
					String mapperName = String.format(config.getMapperName(), beanName);
					params.put("package", config.getServiceImplPackage());
					params.put("serviceImplName", serviceImplName);
					params.put("modelPackage", config.getEntityPackage());
					params.put("servicePackage", config.getServicePackage());
					params.put("serviceName", serviceName);
					params.put("mapperName", mapperName);
					params.put("mapperPackage", config.getMapperPackage());
					String serviceImplText = viewEngine.parse("service_impl.ftl", params);
					String serviceImplAddress = PATH_SERVICE_IMPL +"/" + serviceImplName + JAVA_SUFFIX;
					FileUtils.write(new File(serviceImplAddress), serviceImplText);
					
					
					params.put("package", config.getServicePackage());
					String serviceText = viewEngine.parse("service.ftl", params);
					String serviceAddress = PATH_SERVICE +"/" + serviceName + JAVA_SUFFIX;
					FileUtils.write(new File(serviceAddress), serviceText);
				}
				if (config.isJspGenerator()) {
					for (Map.Entry<String, IdInfo> entry : idMap.entrySet()) {  
					    params.put("primaryKeyName", processField(entry.getKey()));
					}
					
					String viewsPath = "/views/";
					String findText = viewEngine.parse("find.ftl", params);
					String findAddress = config.getSaveDir() + viewsPath + StringUtils.lowerCaseFirst(beanName) + "/find" + JSP_SUFFIX;
					FileUtils.write(new File(findAddress), findText);

					String editText = viewEngine.parse("edit.ftl", params);
					String editAddress = config.getSaveDir() + viewsPath + StringUtils.lowerCaseFirst(beanName) + "/edit" + JSP_SUFFIX;
					FileUtils.write(new File(editAddress), editText);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 字段处理
	 *
	 * @param field
	 *            表字段
	 * @return
	 */
	protected String processField(String field) {
		/*
		 * 驼峰命名直接返回
		 */
		if (!field.contains("_")) {
			if (com.baomidou.mybatisplus.toolkit.StringUtils.isUpperCase(field)) {
				/*
				 * 纯大写命名，转为小写属性
				 */
				return field.toLowerCase();
			}
			return field;
		}

		/*
		 * 处理下划线分割命名字段
		 */
		StringBuilder sb = new StringBuilder();
		String[] fields = field.split("_");
		sb.append(fields[0].toLowerCase());
		for (int i = 1; i < fields.length; i++) {
			String temp = fields[i];
			sb.append(temp.substring(0, 1).toUpperCase());
			sb.append(temp.substring(1).toLowerCase());
		}
		return sb.toString();
	}
	
	class IdInfo {
		private String value;
		private boolean autoIncrement;

		public IdInfo(String value, boolean autoIncrement) {
			this.value = value;
			this.autoIncrement = autoIncrement;

		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public boolean isAutoIncrement() {
			return autoIncrement;
		}

		public void setAutoIncrement(boolean autoIncrement) {
			this.autoIncrement = autoIncrement;
		}
	}
}
