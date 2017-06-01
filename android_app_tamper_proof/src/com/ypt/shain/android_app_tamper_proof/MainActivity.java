package com.ypt.shain.android_app_tamper_proof;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private static String CERTIFICATE_SHA1 = "C82833EEC72F3928D3FF3DFFB538A3BFFFE04FC3";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView text = (TextView) findViewById(R.id.text);
		if (validateAppSignature(this)) {
			text.setText("true");
		} else {
			text.setText("false");
		}
	}

	public static boolean validateAppSignature(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(),
							PackageManager.GET_SIGNATURES);
			Signature[] appSignatures = packageInfo.signatures;

			for (Signature signature : appSignatures) {
				byte[] signatureBytes = signature.toByteArray();

				String currentSignature = calcSHA1(signatureBytes);
				return CERTIFICATE_SHA1.equalsIgnoreCase(currentSignature);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static String calcSHA1(byte[] signatureBytes)
			throws NoSuchAlgorithmException {

		MessageDigest digest = MessageDigest.getInstance("SHA1");
		digest.update(signatureBytes);
		byte[] signatureHash = digest.digest();
		return bytesToHex(signatureHash);
	}

	private static String bytesToHex(byte[] signatureHash) {
		final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] hexChars = new char[signatureHash.length * 2];
		int v;
		for (int j = 0; j < signatureHash.length; j++) {
			v = signatureHash[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
