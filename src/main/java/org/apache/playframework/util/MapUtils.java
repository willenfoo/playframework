package org.apache.playframework.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Map操作工具类
 * @author willenfoo
 *
 */
public class MapUtils {

	/**
	 * 清楚map值为空的key
	 * @param map
	 */
	public static <T> void cleanNullValue(Map<String, T> map) {
		Iterator<Entry<String, T>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,T> entry = it.next();
			if (entry.getValue() == null) {
				 it.remove(); 
			} else if (entry.getValue() instanceof String) {
				if (StringUtils.isEmpty(entry.getValue().toString())) {
					 it.remove(); 
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("key", 111);
		 
		map.put("value", null);
		System.out.println(map);
		cleanNullValue(map);
		System.out.println(map);
	}
}
