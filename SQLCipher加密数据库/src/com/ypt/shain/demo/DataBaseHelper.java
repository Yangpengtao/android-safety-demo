package com.ypt.shain.demo;

import java.io.File;

import net.sqlcipher.database.SQLiteDatabase;
import android.content.Context;

public class DataBaseHelper {

	private static final int DB_VERSION = 1;
	private static final String DB_NAME="my_encrypted_data.db";
	
	
	public void initDB(Context context  ,String password){
		SQLiteDatabase.loadLibs(context);
		File file=context.getDatabasePath(DB_NAME);
		file.mkdir();
		SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase( file, password, null );
		database.execSQL("create table MyTable(a,b)");
	}
}
