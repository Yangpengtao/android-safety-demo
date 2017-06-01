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
	 * ����һ���Գ�AES������Կ
	 * 
	 * @param keysize
	 *            ��Կ�Ĵ�С
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
	 * ����һ�������32���ֽڵĳ�ʼ��������IV�����䳤��Ӧ����AES��Կ�ĳ��ȣ�256λ����ƥ��
	 * 
	 * @return
	 */
	public static IvParameterSpec getIV() {
		if (iv == null) {
			byte[] ivByteArray = new byte[32];
			// ʹ��������������
			new SecureRandom().nextBytes(ivByteArray);
			iv = new IvParameterSpec(ivByteArray);
		}
		return iv;
	}

	/**
	 * ��д�����������������һ�������ַ���
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
		// ���������Spongy Castle,����ʹ�ø��ӏ����AES_GCM����������֤���ܣ������Լ�������Ƿ��⵽�˴۸ġ�
		// final Cipher cipher=Cipher.getInstance("AES/GCM/NoPadding","SC");
		final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, getKey(), getIV());
		return cipher.doFinal(plainText.getBytes("UTF-8"));
	}

	public static SecretKey key;

	/**
	 * �õ���Կ
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
	 * ���� ��Cipher.DECRPT_MODE
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
		// ���������Spongy Castle,����ʹ�ø��ӏ����AES_GCM����������֤���ܣ������Լ�������Ƿ��⵽�˴۸ġ�
		// final Cipher cipher=Cipher.getInstance("AES/GCM/NoPadding","SC");
		final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, getKey(), getIV());
		return cipher.doFinal(cipherText).toString();
	}

}
