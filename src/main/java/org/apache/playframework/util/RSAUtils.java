package org.apache.playframework.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.alibaba.druid.util.Base64;

public class RSAUtils {

	private static String DEFAULT_PRIVATE_KEY_STRING = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAgOnTZ9RjryLg4cIKyXnPqJXEIIglGX0n95Hk+wmZBo1frtx8e/mWujdg63O/LEDb2kipzZXbv4WiBnW1RyPOiwIDAQABAkBkkoe89GUEyKg7WMBDqQXKF3WYj76p4QuiSK8k7rr08YJRH9+ktNL7G1k9YXbdRRVbzXVfEysQzMarhH0IJ37xAiEA+q/J0HYpCSBOY0Kkp0WqBfNqwlxGdjH/AsFM/ePKwLkCIQCDpU1witY3sULHVJaxpTBTRCg3XFu/hItQ+aDmeFv/YwIhAJzoAm2UR8MKkYXXZK++DtmCrkPQ9dltGaxjyQjw1yshAiBiUixjOJ5DnBWG8EtYREqydb4fktBHWsP+Z2Jfn1b+hwIhAOvgTGIiviGatFRKNt/Ccans+BoVoDmDszXS2ZlAMGzy";
	public static final String DEFAULT_PUBLIC_KEY_STRING = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIDp02fUY68i4OHCCsl5z6iVxCCIJRl9J/eR5PsJmQaNX67cfHv5lro3YOtzvyxA29pIqc2V27+FogZ1tUcjzosCAwEAAQ==";

	public static void main(String[] args) throws Exception {
        /*String password = "W0JiKsiqja6NC8X3";
        String[] arr = genKeyPair(512);
        System.out.println("privateKey:" + arr[0]);
        System.out.println("publicKey:" + arr[1]);
        System.out.println("password:" + encrypt(arr[0], password));*/
        
		DEFAULT_PRIVATE_KEY_STRING = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAgi8c4AkeRbM47pozhu3b5/qT39LfyDqVc/PZ7qFlh0pYKC+cs0kfui6mQW4u+0kdO7AOiSY51g0v7Lw1IT7y7wIDAQABAkB4ELGF4oL775ZzYN5i9B7b7ZvSUy83AOjzvxPg5RO5WMOYfuke3N3uWLF0ji3Ltg2YiYefiyJrvUiMZjiyV0QJAiEAv/9yzP/OCTApLOTWAicl0IymPmYUfaPBGgm6RNq3sVsCIQCtlKYnUdaLNwSHyVGOj5Wcj5CIJLfGhvJzWtPwArHE/QIgPLLWqWapzY/TRKUn31BfDRqaKBn/mmTd1IyEveXjswcCIHZpxb9lENr/uVN4fu6l/QZE2fBGPChuvs7600IUVHlxAiEAlqRql2hae5xUeCQrRgfs0FMV40XURuGU4PXCXpGS2+0=";
        System.out.println(encrypt(DEFAULT_PRIVATE_KEY_STRING, "oBhc3uBfLQdgRha9"));
	}

	public static String decrypt(String cipherText) throws Exception {
		return decrypt((String) null, cipherText);
	}

	public static String decrypt(String publicKeyText, String cipherText)
			throws Exception {
		PublicKey publicKey = getPublicKey(publicKeyText);

		return decrypt(publicKey, cipherText);
	}

	public static PublicKey getPublicKeyByX509(String x509File) {
		if (x509File == null || x509File.length() == 0) {
			return RSAUtils.getPublicKey(null);
		}

		FileInputStream in = null;
		try {
			in = new FileInputStream(x509File);

			CertificateFactory factory = CertificateFactory
					.getInstance("X.509");
			Certificate cer = factory.generateCertificate(in);
			return cer.getPublicKey();
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to get public key", e);
		} 
	}

	public static PublicKey getPublicKey(String publicKeyText) {
		if (publicKeyText == null || publicKeyText.length() == 0) {
			publicKeyText = RSAUtils.DEFAULT_PUBLIC_KEY_STRING;
		}

		try {
			byte[] publicKeyBytes = Base64.base64ToByteArray(publicKeyText);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(
					publicKeyBytes);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SunRsaSign");
			return keyFactory.generatePublic(x509KeySpec);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to get public key", e);
		}
	}

	public static PublicKey getPublicKeyByPublicKeyFile(String publicKeyFile) {
		if (publicKeyFile == null || publicKeyFile.length() == 0) {
			return RSAUtils.getPublicKey(null);
		}

		FileInputStream in = null;
		try {
			in = new FileInputStream(publicKeyFile);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int len = 0;
			byte[] b = new byte[512 / 8];
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}

			byte[] publicKeyBytes = out.toByteArray();
			X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
			KeyFactory factory = KeyFactory.getInstance("RSA", "SunRsaSign");
			return factory.generatePublic(spec);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to get public key", e);
		} 
	}

	public static String decrypt(PublicKey publicKey, String cipherText)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		try {
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
		} catch (InvalidKeyException e) {
            // 因为 IBM JDK 不支持私钥加密, 公钥解密, 所以要反转公私钥
            // 也就是说对于解密, 可以通过公钥的参数伪造一个私钥对象欺骗 IBM JDK
            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
            RSAPrivateKeySpec spec = new RSAPrivateKeySpec(rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());
            Key fakePrivateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);
            cipher = Cipher.getInstance("RSA"); //It is a stateful object. so we need to get new one.
            cipher.init(Cipher.DECRYPT_MODE, fakePrivateKey);
		}
		
		if (cipherText == null || cipherText.length() == 0) {
			return cipherText;
		}

		byte[] cipherBytes = Base64.base64ToByteArray(cipherText);
		byte[] plainBytes = cipher.doFinal(cipherBytes);

		return new String(plainBytes);
	}

	public static String encrypt(String plainText) throws Exception {
		return encrypt((String) null, plainText);
	}

	public static String encrypt(String key, String plainText) throws Exception {
		if (key == null) {
			key = DEFAULT_PRIVATE_KEY_STRING;
		}

		byte[] keyBytes = Base64.base64ToByteArray(key);
		return encrypt(keyBytes, plainText);
	}

	public static String encrypt(byte[] keyBytes, String plainText)
			throws Exception {
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance("RSA", "SunRsaSign");
		PrivateKey privateKey = factory.generatePrivate(spec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        try {
		    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        } catch (InvalidKeyException e) {
            //For IBM JDK, 原因请看解密方法中的说明
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
            Key fakePublicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, fakePublicKey);
        }

		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		String encryptedString = Base64.byteArrayToBase64(encryptedBytes);

		return encryptedString;
	}

	public static byte[][] genKeyPairBytes(int keySize)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		byte[][] keyPairBytes = new byte[2][];

		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA", "SunRsaSign");
		gen.initialize(keySize, new SecureRandom());
		KeyPair pair = gen.generateKeyPair();

		keyPairBytes[0] = pair.getPrivate().getEncoded();
		keyPairBytes[1] = pair.getPublic().getEncoded();

		return keyPairBytes;
	}

	public static String[] genKeyPair(int keySize)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		byte[][] keyPairBytes = genKeyPairBytes(keySize);
		String[] keyPairs = new String[2];

		keyPairs[0] = Base64.byteArrayToBase64(keyPairBytes[0]);
		keyPairs[1] = Base64.byteArrayToBase64(keyPairBytes[1]);

		return keyPairs;
	}

}
