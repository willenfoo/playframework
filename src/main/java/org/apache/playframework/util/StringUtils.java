package org.apache.playframework.util;

import org.apache.playframework.logging.MessageUtils;

import java.util.Random;

public final class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 判断字符串是否 在指定的长度内
	 */
	public static boolean isLength(String str, int min, int max) {
		if (str == null) {
			return false;
		} else {
			int length = str.length();
			if (length < min) {
				return false;
			} else if (length > max) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否是 指定长度
	 */
	public static boolean isLength(String str, int length) {
		if (str == null) {
			return false;
		} else {
			if (str.length() == length) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 生成一个随机数,生成的随机数【有重复】的数字
	 * @param several 得到多少位的随机数
	 * @return
	 */
	public static String random(int several) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < several; i++) {
			sb.append(random.nextInt(9));
		}
		return sb.toString();
	}

	/**
	 * 生成一个随机数,生成的随机数【没有重复】的数字,生成的长度最多是10位的随机数
	 * @param several 得到多少位的随机数,不能够大于10
	 * @return
	 */
	public static String randomNoRepeat(int several) {
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int length = array.length;
		if (several > length) {
			several = length;
		}
		Random rand = new Random();
		for (int i = length; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < several; i++) {
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * 把首字母转换成大写
	 * @param str
	 * @return
	 */
	public static String upperCaseFirst(String str) {
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}

	/**
	 * 把首字母转换成大小写
	 * @param str
	 * @return
	 */
	public static String lowerCaseFirst(String str) {
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toLowerCase());
	}

	public static String format(final String msgPattern, final Object... params) {
		return MessageUtils.formatMessage(msgPattern, params);
	}

	public static void main(String[] args) {

	}
}
