package com.example.hotel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

	public dbHelper(Context context, String name, int version) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table reg_manage (name varchar(255)," + "pass varchar(255),"
				+ "type varchar(20))");
		db.execSQL("create table employee (name varchar(10)," +"sex varchar(10),"
				+ "id varchar(18)," + "time varchar(255)," + "days varchar,"
				+ "room_id varchar(10)primary key," + "money varchar)");
		db.execSQL("create table repair (room_id varchar(10),"
				+ " item varchar(255),"+"finish varchar(255))");
		db.execSQL("create table reg_repair (name varchar(255)," + "pass varchar(255),"
				+ "type varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
