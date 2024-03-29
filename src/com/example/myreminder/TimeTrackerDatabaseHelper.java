package com.example.myreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class TimeTrackerDatabaseHelper{
	
	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "timetracker.db";
	private static final String TABLE_NAME = "timerecords";
	
	public static final String TIMETRACKER_COLUMN_ID = "_id";
	public static final String TIMETRACKER_COLUMN_TIME = "time";
	public static final String TIMETRACKER_COLUMN_NOTES = "notes";

	private TimeTrackerOpenHelper openHelper;
	private SQLiteDatabase database;

	public TimeTrackerDatabaseHelper(Context context) {
		// TODO Auto-generated constructor stub
		openHelper = new TimeTrackerOpenHelper(context);
		database = openHelper.getWritableDatabase();
	}

	
	public void saveTimeRecord(String time, String notes) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(TIMETRACKER_COLUMN_TIME, time);
			contentValues.put(TIMETRACKER_COLUMN_NOTES, notes);
			database.insert(TABLE_NAME, null, contentValues);
		    
		}
	
	
	
	public Cursor getAllTimeRecords() {
		
		return database.rawQuery("select * from " + TABLE_NAME,null);
	}
	
	


	
	private class TimeTrackerOpenHelper  extends SQLiteOpenHelper{

		

		public TimeTrackerOpenHelper(Context context) {
			
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

			
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			// TODO Auto-generated method stub
			database.execSQL("CREATE TABLE " + TABLE_NAME + "( "
					+ TIMETRACKER_COLUMN_ID + " INTEGER PRIMARY KEY, "
					+ TIMETRACKER_COLUMN_TIME + " TEXT, "
					+ TIMETRACKER_COLUMN_NOTES +" TEXT )");




		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(database);
			
		}
	
	}
}