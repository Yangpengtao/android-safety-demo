package com.ypt.shain.demo4.util;

import java.lang.reflect.InvocationTargetException;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class CheckUtil {

	/**
	 * ��ⰲ׿�����ǲ��ǹȸ�Ӧ���̵�
	 * 
	 * @param context
	 * @return  true ��ʾ�ǹȸ�Ӧ���г�
	 */
	public static boolean checkGooglePlayStore(Context context) {
		String installerPackageName = context.getPackageManager()
				.getInstallerPackageName(context.getPackageName());
		return installerPackageName != null
				&& installerPackageName.startsWith("com.google.android");
	}

	/**
	 * ����Լ��ǲ���������һ̨ģ������
	 * 
	 * @return  true ��ʾ��ģ����
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmulator() {
		try {
			Class systemPropertyClazz = Class
					.forName("android.os.SystemProperties");
			boolean kernelQemu = getProperty(systemPropertyClazz,
					"ro.kernel.qemu").length() > 0;
			boolean hardwareGoldfish = getProperty(systemPropertyClazz,
					"ro.hardware").equals("goldfish");
			boolean modelSdk = getProperty(systemPropertyClazz,
					"ro.product.model").equals("sdk");

			if (modelSdk || hardwareGoldfish || kernelQemu) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static String getProperty(Class clazz, String string)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException {
		return (String) clazz.getMethod("get", new Class[] { String.class })
				.invoke(clazz, new Object[] { string });
	}

	/**
	 * ���app�Ŀɵ�ʽ��־λ�Ƿ񱻴��ˣ�ֻ���ڿ����׶β�Ӧ�ñ��򿪵Ķ���
	 * @param context
	 * @return true   �ѱ���
	 */
	public static boolean isDebuggable(Context context) {
		return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
	}

}
