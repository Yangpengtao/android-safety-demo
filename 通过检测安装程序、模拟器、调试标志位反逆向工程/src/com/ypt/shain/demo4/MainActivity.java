package com.ypt.shain.demo4;

import com.ypt.shain.demo4.util.CheckUtil;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	
//	利用ProGuad删除所有的日志消息
	/*在proguad-project.txt里面插入配置信息
	-assumenosideeffects class android.util.Log{
		public static boolean isLoggable(java.lang.String,int);
		public static int v(...);
		public static int i(...);
		public static int w(...);
		public static int d(...);
		public static int e(...);
	}*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView text = (TextView) findViewById(R.id.text);
		if (CheckUtil.isDebuggable(this)) {
			text.setText("true");
		} else {
			text.setText("false");
		}
	}

}
