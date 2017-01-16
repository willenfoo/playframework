package org.apache.playframework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SPUtil {
	private static String staticSpId;
	private static final String SPID_FILE = "/META-INF/SPID";
	static {
		initStaticSpId();
	}

	/**
	 * 获取当前系统SPID
	 */
	private static void initStaticSpId() {
		InputStream is = null;

		BufferedReader br = null;

		try {
			is = SPUtil.class.getResourceAsStream(SPID_FILE);
			if (is != null) {
				br = new BufferedReader(new InputStreamReader(is));
				String s;
				while ((s = br.readLine()) != null) {
					if (!s.startsWith("#")) {
						staticSpId = s.trim();
					}
				}
			}
		} catch (IOException e) {

		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// ignore exception
			}
		}

	}

	public static String getSpid() {
		return staticSpId;
	}
	
	public static void main(String[] args){
    	System.out.println(getSpid());
    }
}
