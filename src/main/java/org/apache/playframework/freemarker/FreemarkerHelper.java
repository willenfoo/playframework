package org.apache.playframework.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerHelper {
	
	private Configuration config = new Configuration();
 
	private final static Map<String, Object> staticAttributes = new HashMap<String, Object>();
	
	public FreemarkerHelper(String pathPrefix) {
		config.setClassForTemplateLoading(this.getClass(), pathPrefix);
		staticAttributes.put("StringUtils", FreemarkerStaticModels.useStaticPackage("org.apache.playframework.util.StringUtils"));
	}

	public String parse(String name, String encoding, Map<String, Object> params) {
		try {
			StringWriter swriter = new StringWriter();
			Template mytpl = config.getTemplate(name, encoding);
			params.putAll(staticAttributes);
			mytpl.process(params, swriter);
			return swriter.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String parse(String name, Map<String, Object> params) {
		return this.parse(name, "utf-8", params);
	}
}
