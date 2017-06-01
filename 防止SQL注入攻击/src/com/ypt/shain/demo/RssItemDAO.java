package com.ypt.shain.demo;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class RssItemDAO {

	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;

	private static String COL_TITLE = "title";
	private static String TABLE_NAME = "RSS_ITEMS";
	public static String INSERT_SQL = "insert into " + TABLE_NAME
			+ " (content,link,title) values (?,?,?)";

	public RssItemDAO(SQLiteDatabase db) {
		this.db = db;
		this.insertStatement = db.compileStatement(INSERT_SQL);
	}

	/**
	 * 添加对象
	 * 
	 * @param item
	 *            RssItem对象
	 * @return
	 */
	public long save(RssItem item) {
		insertStatement.bindString(1, item.getContent());
		insertStatement.bindString(2, item.getLink());
		insertStatement.bindString(3, item.getTitle());
		return insertStatement.executeInsert();
	}

	public List<RssItem> fetchRssItemsByTitle(String searchTerm) {
		Cursor cursor = db.query(TABLE_NAME, null, COL_TITLE,
				new String[] { "%" + searchTerm + "%" }, "LIKE ?", null, null,
				null);
		List<RssItem> rssItems = new ArrayList<RssItem>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			// ..........
		}
		return rssItems;
	}

}
