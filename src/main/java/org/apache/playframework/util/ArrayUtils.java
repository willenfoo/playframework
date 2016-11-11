package org.apache.playframework.util;

/**
 * 数组操作工具类
 * 
 * @author willenfoo
 *
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {


	/**
	 * 把数组转换成字符串，用盗号分割
	 * @param array
	 * @return
	 */
	public static <T> String arrayToString(T[] array) {
		if (array != null) {
			StringBuilder sb = new StringBuilder();
			for (T t : array) {
				sb.append(t + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String[] array = {"1","2"};
		System.out.println(arrayToString(array));
	}
}
