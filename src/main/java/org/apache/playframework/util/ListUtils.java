package org.apache.playframework.util;

import java.util.ArrayList;
import java.util.List;

/**
 * List操作工具类
 * @author willenfoo
 *
 */
public class ListUtils {

	/**
	 * 判断是否为空
	 * @param list
	 * @return
	 */
	public static <T> boolean isEmpty(List<T> list) {
		return list == null || list.isEmpty();
	}

	/**
	 * 判断是否不为空
	 * @param list
	 * @return
	 */
	public static <T> boolean isNotEmpty(List<T> list) {
		return !isEmpty(list);
	}
	
	/**
	 * 往list中添加数据
	 * @param values
	 * @return
	 */
	public static <T> List<T> add(T... values) {
		if (values != null) {
			List<T> list = new ArrayList<T>();
			for (T t : values) {
				list.add(t);
			}
			return list;
		}
		return null;
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		System.out.println(isNotEmpty(list));
	}
}