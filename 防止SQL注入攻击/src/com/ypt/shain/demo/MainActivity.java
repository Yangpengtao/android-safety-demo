package com.ypt.shain.demo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

	
	/*����ʹ��SQLiteDatabase.rawQuery(),������һ������������䡣ʹ��һ��Ԥ�ȱ���õ���䣬
	����SQLiteStatement���ṩ�Բ�����binding��escaping���Է�ֹSQLע��Ĺ�����
	������ʹ��SQLiteDatabase���е�query��insert��update��delete��������Щ����ͨ������ʹ�õ��ַ������飬�ṩ�˲���������䡣*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
