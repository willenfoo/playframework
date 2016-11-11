package org.apache.playframework.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public abstract class RSACoder {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String PUBLIC_KEY = "RSAPublicKey";
	public static final String PRIVATE_KEY = "RSAPrivateKey";

	private static final int KEY_SIZE = 512;

	// 私钥解密
	public static byte[] decryptByPrivateKey(byte[] data, byte[] privateKeyDate)
			throws Exception {
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyDate);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	
	// 私钥解密2
	public static String decryptByPrivateKey(String data, String privateKeyDate)
			throws Exception {
		BASE64Decoder dec=new BASE64Decoder();
		byte[] dataByte=decryptByPrivateKey(dec.decodeBuffer(data),dec.decodeBuffer(privateKeyDate));
		return new String(dataByte);
	}
	
	// 公钥解密
	public static byte[] decryptByPublicKey(byte[] data, byte[] publicKeyDate)
			throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyDate);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}
	
	// 公钥解密2
	public static String decryptByPublicKey(String data, String publicKeyDate)
			throws Exception {
		BASE64Decoder dec=new BASE64Decoder();
		byte[] dataByte=decryptByPublicKey(dec.decodeBuffer(data),dec.decodeBuffer(publicKeyDate));
		return new String(dataByte);
	}
	
	// 公钥加密
	public static byte[] encryptByPublicKey(byte[] data, byte[] publicKeyDate)
			throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyDate);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	// 公钥加密2
	public static String encryptByPublicKey(String data, String publicKeyDate)throws Exception {
		BASE64Decoder dec=new BASE64Decoder();
		BASE64Encoder enc=new BASE64Encoder(); 
		byte[] signByte=encryptByPublicKey(data.getBytes(),dec.decodeBuffer(publicKeyDate));
		return enc.encode(signByte);
	}
	
	// 私钥加密
	public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKeyDate)
			throws Exception {
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyDate);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	// 私钥加密2
	public static String encryptByPrivateKey(String data, String privateKeyDate)
	throws Exception {
		BASE64Decoder dec=new BASE64Decoder();
		BASE64Encoder enc=new BASE64Encoder(); 
		byte[] signByte=encryptByPrivateKey(data.getBytes(),dec.decodeBuffer(privateKeyDate));
		return enc.encode(signByte);
	}
	
	//私钥验证公钥密文
	public static boolean checkPublicEncrypt(String data,String sign,String pvKey)
	throws Exception{
		return data.equals(decryptByPrivateKey(sign,pvKey));
	}
	public static boolean checkPrivateEncrypt(String data,String sign,String pbKey)
	throws Exception{
		return data.equals(decryptByPublicKey(sign,pbKey));
	}
	//取得私钥
	public static byte[]getPrivateKey(Map<String,Object> keyMap) throws Exception{
		Key key=(Key)keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}
	//取得公钥
	public static byte[]getPublicKey(Map<String,Object> keyMap) throws Exception{
		Key key=(Key)keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}
	//初始化密钥
	public static Map<String,Object> initKey() throws Exception{
		KeyPairGenerator keyPairGen =KeyPairGenerator.getInstance(KEY_ALGORITHM);
		
		keyPairGen.initialize(KEY_SIZE);
		
		KeyPair keyPair=keyPairGen.generateKeyPair();
		
		RSAPublicKey publicKey=(RSAPublicKey)keyPair.getPublic();
		RSAPrivateKey privateKey=(RSAPrivateKey)keyPair.getPrivate();
		
		Map<String,Object> keyMap =new HashMap<String,Object>(2);
		keyMap.put(PUBLIC_KEY,publicKey);
		keyMap.put(PRIVATE_KEY,privateKey);
		return keyMap;
	}
	public static void main(String[] args){
		try {
			BASE64Encoder enc=new BASE64Encoder();    

//			Map<String,Object> mp=initKey();
//			byte[] publicKey=getPublicKey(mp);
//			byte[] privateKey=getPrivateKey(mp);
//			String pbkey=enc.encode(publicKey);
//			String prkey=enc.encode(privateKey);
//
//			System.out.println("公钥1:"+pbkey);
//			System.out.println("私钥:"+prkey);
			
			String pbkey="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANYDl5DEm6fwl2t93268Y7OkQHRJzjI6rmb9cezVEVhOIW4jYrsCHEYPI/ybZ8s5ZeQ7WvvZMWmZbixgU+7Q0rUCAwEAAQ==";
			String prkey="MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA1gOXkMSbp/CXa33fbrxjs6RAdEnOMjquZv1x7NURWE4hbiNiuwIcRg8j/Jtnyzll5Dta+9kxaZluLGBT7tDStQIDAQABAkEAyGm8ubkj+vT3F5ZcchrBUyzxnvSuv6LsR034Lcyp3YI1bN25zHctvX/pXcjDuWpLaP41EmxcRoWApYMFRaZ7AQIhAPyFFp5xK0iI9U82unxHLjYPrA98Tp6vYntECfyanSqlAiEA2Pam3An24wCzwZg8YbAAJ461gw6cP4X3ULLla9UHWtECIGc7YUvUqAU3OFHx5br4voOLVKPgBaQJvxD1d7+01ZuhAiEAjnxt2NQyb6Jmax2vifgsIc53JjrSImW4pibWxJqC2gECIDkrWU96DXHgpkBHyObH4Hc/8FEaQmu5aRoHF3TaR0JJ";
			BASE64Decoder dec=new BASE64Decoder();     
			byte[] publicKey=dec.decodeBuffer(pbkey);
			byte[] privateKey=dec.decodeBuffer(prkey);
			
			
			
			
			String input1="公钥加密私钥解密";
			System.out.println("原文:"+input1);
			byte[] encodedData1=encryptByPublicKey(input1.getBytes(),publicKey);
			String encodedData1Str=enc.encode(encodedData1);
			System.out.println("密文:"+encodedData1Str);
			byte[] decodedData1=decryptByPrivateKey(dec.decodeBuffer(encodedData1Str),privateKey);
			
			System.out.println("解密文:"+new String(decodedData1));
			
			
			String input2="私钥加密公钥解密";
			System.out.println("原文:"+input2);
			byte[] encodedData2=encryptByPrivateKey(input2.getBytes(),privateKey);
			String encodedData2Str=enc.encode(encodedData2);
			System.out.println("密文:"+encodedData2Str);
			byte[] decodedData2=decryptByPublicKey(dec.decodeBuffer(encodedData2Str),publicKey);
			System.out.println("解密文:"+new String(decodedData2));
					}catch(InvalidKeySpecException e){
			System.out.println("密钥不合法");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
