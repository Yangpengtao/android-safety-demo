package com.example.pbf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;

import android.os.Build;

public class Util {

	// start
	/**
	 * 定义一些辅助方法来获取或创建IV和salt值。 我们将把它们作为密钥推导和加密过程的一部分。
	 */
	private static IvParameterSpec iv;
	private static byte[] salt;

	public static IvParameterSpec getIV() {
		if (iv == null) {
			iv = new IvParameterSpec(generateRandomByteArray(32));
		}
		return iv;
	}

	public static byte[] getSalt() {
		if (salt == null) {
			salt = generateRandomByteArray(32);
		}
		return salt;
	}

	public static byte[] generateRandomByteArray(int sizeInBytes) {
		byte[] randomNumberByteArray = new byte[sizeInBytes];

		new SecureRandom().nextBytes(randomNumberByteArray);
		return randomNumberByteArray;
	}

	// end

	/**
	 * 生成PBE密钥
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static SecretKey generatePBEKey(char[] password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		final int iterations = 10000;
		final int outputKeyLength = 256;
		SecretKeyFactory secretKeyFactory = null;
		// android 4.4后，SecretKeyFactory发生了微妙变化
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
			secretKeyFactory = SecretKeyFactory
					.getInstance("PBKDF2WithHmacSHA1And8bit");
		else
			secretKeyFactory = SecretKeyFactory
					.getInstance("PBKDF2WithHmacSHA1");

		KeySpec keySpec = new PBEKeySpec(password, salt, iterations,
				outputKeyLength);
		SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
		return secretKey;
	}

	/**
	 * 进行加密
	 * 
	 * @param painText
	 * @param userPassword
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static byte[] encrpytWithPBE(String painText, String userPassword)
			throws GeneralSecurityException, IOException {
		SecretKey secretKey = generatePBEKey(userPassword.toCharArray(),
				getSalt());
		final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, getIV());
		return cipher.doFinal(painText.getBytes("UTF-8"));
	}

	public static String decrptWithPBE(byte[] cipherText, String userPassword)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		SecretKey secretKey = generatePBEKey(userPassword.toCharArray(),
				getSalt());
		final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, getIV());
		return cipher.doFinal(cipherText).toString();
	}

}
