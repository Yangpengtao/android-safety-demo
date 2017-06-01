package com.ypt.shain.keystoredemo;

import java.math.BigInteger;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.util.Log;

public class Util {
	public static final String ANDROID_KEYSTORE="AndroidKeyStore";
	
	private KeyStore keyStore;
	
	
	public void loadKeyStore(){
		try {
			keyStore=KeyStore.getInstance(ANDROID_KEYSTORE);
			keyStore.load(null);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	public void generateNextKeyPair(String alias,Context context) throws Exception   {
		Calendar start=Calendar.getInstance();
		Calendar end=Calendar.getInstance();
		end.add(1, Calendar.YEAR);
		
		KeyPairGeneratorSpec spec=new KeyPairGeneratorSpec.Builder(context)
		.setAlias(alias)
		.setSubject(new X500Principal("CN="+alias))
		.setSerialNumber(BigInteger.TEN)
		.setStartDate(start.getTime())
		.setEndDate(end.getTime())
		.build();
		
		
		KeyPairGenerator gen=KeyPairGenerator.getInstance("RSA",ANDROID_KEYSTORE);
		gen.initialize(spec);
		gen.generateKeyPair();
	}
	
	/**
	 *给制定的别名  获取密钥
	 * @param alias
	 * @return
	 */
	public PrivateKey loadPrivateKey(String alias) throws Exception{
		if (keyStore.isKeyEntry(alias)) {
			Log.e("tag", "Could not find key alias: "+alias);
			return null;
		}
		KeyStore.Entry entry=keyStore.getEntry(alias , null);
		
		if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
			Log.e("tag", "alias: "+alias+" is not a PrivateKey");
			return  null;
		}
		return ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
	}

}
