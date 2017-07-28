package org.apache.playframework.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.playframework.util.ReflectUtils;
import org.springframework.util.CollectionUtils;


public class TextUtils {

	public static <T> List<T> importTxt(String path, Class<T> returnClass, Map<String, String> titleMapper, String splitChar) {
		return importTxt(path, returnClass, titleMapper, splitChar, null);
	}
	
	public static <T> List<T> importTxt(String path, Class<T> returnClass, Map<String, String> titleMapper) {
		return importTxt(path, returnClass, titleMapper, ",", null);
	}
	
	public static <T> List<T> importTxt(String path, Class<T> returnClass, Map<String, String> titleMapper, Map<String, String> filterTitleMapper) {
		return importTxt(path, returnClass, titleMapper, ",", filterTitleMapper);
	}
	
	public static <T> List<T> importTxt(String path, Class<T> returnClass, Map<String, String> titleMapper, String splitChar, Map<String, String> filterTitleMapper) {
		List<T> list = new ArrayList<T>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null; // 用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
		try {
			fis = new FileInputStream(path);// FileInputStream
			// 从文件系统中的某个文件中获取字节
			isr = new InputStreamReader(fis, "UTF-8");// InputStreamReader 是字节流通向字符流的桥梁,
			br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new
											// InputStreamReader的对象
			List<String> titles = new ArrayList<>();
			int index = 0;
			String str;
			while ((str = br.readLine()) != null) {
				if (StringUtils.isBlank(str)) {
					continue;
				}
				if (index == 0) {
					titles.addAll(CollectionUtils.arrayToList(str.split(splitChar)));
				} else {
					String[] values = str.split(splitChar);
					//过滤数据
					boolean flag = false;
					if (!CollectionUtils.isEmpty(filterTitleMapper)) {
						for (Map.Entry<String, String> entry : filterTitleMapper.entrySet()) {
							String value = values[titles.indexOf(entry.getKey())];
							if (!StringUtils.equals(entry.getValue(), value)) {
								flag = true;
							}
						}
					}
					if (!flag) {
						T t = returnClass.newInstance();
						for (Map.Entry<String, String> entry : titleMapper.entrySet()) {
							ReflectUtils.setProperty(t, entry.getValue(), values[titles.indexOf(entry.getKey())]);
						} 
						list.add(t);
					}
				}
				index++;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}  finally {
			try {
				br.close();
				isr.close();
				fis.close();
				// 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
}

