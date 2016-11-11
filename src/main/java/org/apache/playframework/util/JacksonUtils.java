package org.apache.playframework.util;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class JacksonUtils {

	private static final Log log = LogFactory.getLog(JacksonUtils.class);
	
	public static String writeValueAsString(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
		String result = null;
		try {
			result = mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("返回的json数据是===========" + result);
		return result;
	}
	
 
}
