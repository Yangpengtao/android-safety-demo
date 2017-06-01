package com.ypt.shain.demo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Util {
	/**
	 * 生成一個对称AES加密密钥
	 * 
	 * @param keysize
	 *            密钥的大小
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static SecretKey generateAESKey(int keysize)
			throws NoSuchAlgorithmException {
		final SecureRandom random = new SecureRandom();
		final KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(keysize, random);
		return generator.generateKey();
	}

	private static IvParameterSpec iv;

	/**
	 * 创建一个随机的32个字节的初始化向量（IV），其长度应该与AES密钥的长度（256位）相匹配
	 * 
	 * @return
	 */
	public static IvParameterSpec getIV() {
		if (iv == null) {
			byte[] ivByteArray = new byte[32];
			// 使用随机数填充数组
			new SecureRandom().nextBytes(ivByteArray);
			iv = new IvParameterSpec(ivByteArray);
		}
		return iv;
	}

	/**
	 * 编写下面这个函数，加密一个任意字符串
	 * 
	 * @param plainText
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static byte[] encrpyt(String plainText)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		// 如果引入了Spongy Castle,可以使用更加強大的AES_GCM。不光有认证功能，还可以检测密文是否遭到了篡改。
		// final Cipher cipher=Cipher.getInstance("AES/GCM/NoPadding","SC");
		final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, getKey(), getIV());
		return cipher.doFinal(plainText.getBytes("UTF-8"));
	}

	public static SecretKey key;

	/**
	 * 得到密钥
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static Key getKey() throws NoSuchAlgorithmException {
		if (key == null) {
			key = generateAESKey(256);
		}
		return key;
	}

	/**
	 * 解密 用Cipher.DECRPT_MODE
	 * 
	 * @param cipherText
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static String decrpyt(byte[] cipherText)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException {
		// 如果引入了Spongy Castle,可以使用更加強大的AES_GCM。不光有认证功能，还可以检测密文是否遭到了篡改。
		// final Cipher cipher=Cipher.getInstance("AES/GCM/NoPadding","SC");
		final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, getKey(), getIV());
		return cipher.doFinal(cipherText).toString();
	}

}
