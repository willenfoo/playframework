/**
 * Copyright (c) 2011-2016, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.playframework.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.playframework.freemarker.FreemarkerHelper;
import org.apache.playframework.generator.mybatisplus.PackageConfig;
import org.apache.playframework.util.StringUtils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * 代码生成器演示
 * </p>
 * 
 * @author hubin
 * @date 2016-12-01
 */
public class CodeGenerator {

	/**
	 * <p>
	 * MySQL 生成演示
	 * </p>
	 */
	public static void main(String[] args) {
		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		final GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir("D:/src/");
		gc.setFileOverride(true);
		gc.setActiveRecord(false);// 开启 activeRecord 模式
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(false);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		gc.setAuthor("fuwei");

		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		//gc.setMapperName("%sDao");
		// gc.setXmlName("%sDao");
		gc.setServiceName("%sService");
		// gc.setServiceImplName("%sServiceDiy");
		// gc.setControllerName("%sAction");
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setTypeConvert(new MySqlTypeConvert(){
			// 自定义数据库表字段类型转换【可选】
			@Override
			public DbColumnType processTypeConvert(String fieldType) {
				System.out.println("转换类型：" + fieldType);
				return super.processTypeConvert(fieldType);
			}
		});
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("devhlapp");
		dsc.setPassword("ab7a2YZfsihSmRjK");
		dsc.setUrl("jdbc:mysql://106.14.29.74:3306/devhlapp_0426?characterEncoding=utf8");
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// strategy.setCapitalMode(true);// 全局大写命名
		// strategy.setDbColumnUnderline(true);//全局下划线命名
		strategy.setTablePrefix(new String[] { "bmd_", "mp_" });// 此处可以修改为您的表前缀
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setInclude(new String[] { "user_action" }); // 需要生成的表
		// strategy.setExclude(new String[]{"test"}); // 排除生成的表
		// 自定义实体父类
		// strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
		// 自定义实体，公共字段
		// strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
		// 自定义 mapper 父类
		// strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
		// 自定义 service 父类
		strategy.setSuperServiceClass("org.apache.playframework.service.BaseService");
		// 自定义 service 实现类父类
		strategy.setSuperServiceImplClass("org.apache.playframework.service.impl.BaseServiceImpl");
		// 自定义 controller 父类
		// strategy.setSuperControllerClass("com.baomidou.demo.TestController");
		// 【实体】是否生成字段常量（默认 false）
		// public static final String ID = "test_id";
		// strategy.setEntityColumnConstant(true);
		// 【实体】是否为构建者模型（默认 false）
		// public User setName(String name) {this.name = name; return this;}
		// strategy.setEntityBuliderModel(true);
		mpg.setStrategy(strategy);

		// 包配置
		final PackageConfig pc = new PackageConfig();
		pc.setModuleName("tourism");
		pc.setParent("com.huanlvjinfu");// 自定义包路径
		pc.setFunctionModuleName("financecheck");
		pc.setController("controller/"+pc.getFunctionModuleName());// 这里是控制器包名，默认 web
		pc.setMapper("dao");
		pc.setXml("dao/mapper");
		pc.setEntity("model");
		
		mpg.setPackageInfo(pc);

		// 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
				this.setMap(map);
			}
		};
		
		
		final FreemarkerHelper viewEngine = new FreemarkerHelper("/org/apache/playframework/generator/template");
		List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
		focList.add(new FileOutConfig("/templates/entity.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				
				String entityName = StringUtils.lowerCaseFirst(tableInfo.getEntityName());
				
				Map<String, Object> params = new HashMap<>();
				params.put("table", tableInfo);
				params.put("packageConfig", pc);
				
				String listText = viewEngine.parse("list.ftl", params);
				String listAddress = gc.getOutputDir()+"/views/"+pc.getFunctionModuleName()+"/"+entityName+"/list.jsp";
				try {
					FileUtils.write(new File(listAddress), listText);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				String jsText = viewEngine.parse("js.ftl", params);
				String jsAddress = gc.getOutputDir()+"/views/"+pc.getFunctionModuleName()+"/"+entityName+"/"+entityName+".js";
				try {
					FileUtils.write(new File(jsAddress), jsText);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				String editText = viewEngine.parse("edit.ftl", params);
				String editAddress = gc.getOutputDir()+"/views/"+pc.getFunctionModuleName()+"/"+entityName+"/edit.jsp";
				try {
					FileUtils.write(new File(editAddress), editText);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				String controllerText = viewEngine.parse("controller.ftl", params);
				String controllerAddress = gc.getOutputDir() + "/" +
						pc.getParent().replaceAll("\\.", "/") + "/" +
						pc.getController() + "/" + tableInfo.getEntityName() + "Controller.java";
				try {
					FileUtils.write(new File(controllerAddress), controllerText);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				String voText = viewEngine.parse("vo.ftl", params);
				String voAddress = gc.getOutputDir() + "/" +
						pc.getParent().replaceAll("\\.", "/") + "/vo/" +
						"/" + tableInfo.getEntityName() + "Vo.java";
				try {
					FileUtils.write(new File(voAddress), voText);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// 自定义输入文件名称
				return "D:/src/develop/code/my_" + tableInfo.getEntityName() + ".java";
			}
		});
		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);

		// 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
		// 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
		// TemplateConfig tc = new TemplateConfig();
		// tc.setController("...");
		// tc.setEntity("...");
		// tc.setMapper("...");
		// tc.setXml("...");
		// tc.setService("...");
		// tc.setServiceImpl("...");
		// mpg.setTemplate(tc);

		// 执行生成
		mpg.execute();

		// 打印注入设置
		System.err.println(mpg.getCfg().getMap().get("abc"));
	}

}
