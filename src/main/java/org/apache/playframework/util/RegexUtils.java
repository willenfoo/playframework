package org.apache.playframework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @ClassName: RegexUtils.java
 * @Description: Regex Utils
 * @author SonicWu
 * @date 2009-8-21 下午05:56:34
 */
public class RegexUtils {
	
	/**
	 * Match Content
	 * @param content
	 * @param pattern
	 * @return result
	 */
	public static String match(String content, String pattern) {
		return match(content,pattern,false);
	}
	
	/**
	 * Match Content
	 * @param content
	 * @param pattern
	 * @param caseInsensitive
	 * @return
	 */
	public static String match(String content, String pattern, boolean caseInsensitive) {
		Pattern p = null;
		if(caseInsensitive) {
			p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		}
		else{
			p = Pattern.compile(pattern);
		}
		Matcher matcher = p.matcher(content);
		while(matcher.find()) {
			int groupCount = matcher.groupCount();
			for (int i = 1; i <= groupCount; i++) {
				String result = matcher.group(i);
				if(result != null && !result.isEmpty())
					return result;
			}
		}
		return null;
	}
	
	
	
	/**
	 * Match Content
	 * @param content
	 * @param pattern
	 * @param group
	 * @return result
	 */
	public static String match(String content, String pattern, int group) {
		return match(content,pattern,group,false);
	}
	
	/**
	 * Match Content
	 * @param content
	 * @param pattern
	 * @param group
	 * @param caseInsensitive
	 * @return result
	 */
	public static String match(String content, String pattern, int group, boolean caseInsensitive) {
		Pattern p = Pattern.compile(pattern);
		if(caseInsensitive) {
			p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		}
		Matcher matcher = p.matcher(content);
		while(matcher.find()) {
			return matcher.group(group);
		}
		return null;
	}
	
	/**
	 * Match Content by count
	 * @param content
	 * @param pattern
	 * @param count
	 * @param group
	 * @return result
	 */
	public static String matchCount(String content, String pattern, int count, int group) {
		return matchCount(content, pattern, count, group, false);
	}
	
	/**
	 * Match Content by count
	 * @param content
	 * @param pattern
	 * @param count
	 * @param group
	 * @param caseInsensitive
	 * @return result
	 */
	public static String matchCount(String content, String pattern, int count, int group, boolean caseInsensitive) {
		Pattern p = Pattern.compile(pattern);
		if(caseInsensitive) {
			p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		}
		Matcher matcher = p.matcher(content);
		int n = 0;
		while(matcher.find()) {
			n++;
			if (n == count) {
				return matcher.group(group);
			}
		}
		return null;
	}
	
	/**
	 * isMatch
	 * @param content
	 * @param pattern
	 * @return true or false
	 */
	public static boolean isMatch(String content, String pattern) {
		return isMatch(content,pattern,false);
	}
	
	/**
	 * isMatch
	 * @param content
	 * @param pattern
	 * @param caseInsensitive
	 * @return true or false
	 */
	public static boolean isMatch(String content, String pattern, boolean caseInsensitive) {
		Pattern p = Pattern.compile(pattern);
		if(caseInsensitive) {
			p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		}
		Matcher matcher = p.matcher(content);
		return matcher.find();
	}

}
