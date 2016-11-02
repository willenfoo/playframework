package org.apache.playframework.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * 
 * @version 1.0
 * @author brent xu
 * 
 */
public final class Md5Utils {
    
    public static String getMD5(String source) {
		String result = null;
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd','e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(source.getBytes("UTF-8"));
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2]; 
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { 
				byte byte0 = tmp[i]; 
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
				str[k++] = hexDigits[byte0 & 0xf];
			}
			result = new String(str);

		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
    
    public static byte[] getMD5Byte(byte[] data){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md.digest(data);
	}
    
    public static byte[] getMD5Byte(String source){
		MessageDigest md = null;
		byte[] bytes = null;
		try {
			md = MessageDigest.getInstance("MD5");
			bytes = md.digest(source.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}
    
	
    public static void main(String[] args) {
		System.out.println(getMD5("123456"));
	}
}