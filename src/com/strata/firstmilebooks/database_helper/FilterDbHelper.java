package com.strata.firstmilebooks.database_helper;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.strata.firstmilebooks.model.Filter;



public class FilterDbHelper extends MainDBHelper{
	private static final String TABLE_FILTER = "filters";
	private static final String KEY_NAME = "name";
	private static final String KEY_FILTER_ID = "filter_id";
	private static final String KEY_FILTER_TYPE = "type";
	public FilterDbHelper(Context context) {
		super(context);
	}

    public void insertParticipants(ArrayList<Filter> filters) {
    	SQLiteDatabase db = this.getWritableDatabase();
		for (int i = 0; i < filters.size(); i++) {
			Filter filter = filters.get(i);
			ContentValues contentValues = new ContentValues();
			contentValues.put(KEY_NAME, filter.getName());
			contentValues.put(KEY_FILTER_ID, filter.getId());
			contentValues.put(KEY_FILTER_TYPE, filter.getType());
			db.insert(TABLE_FILTER, null, contentValues);
		}
	}

     public void delete_filters() {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	 db.execSQL("delete from "+TABLE_FILTER);
	}
	 public ArrayList<Filter> getFilters(String type) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	ArrayList<Filter> filters = new ArrayList<Filter>();
    	String selectQuery = "SELECT  * FROM " + TABLE_FILTER + " WHERE "+KEY_FILTER_TYPE + "= '"+type+"'";
    	Cursor c = db.rawQuery(selectQuery, null);
    	Filter f;
    	if (c.getCount() > 0 && c.moveToFirst()) {
            do {
            	f = new Filter();
            	f.setId(c.getString(c.getColumnIndex(KEY_FILTER_ID)));
            	f.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            	f.setType(c.getString(c.getColumnIndex(KEY_FILTER_TYPE)));
            	filters.add(f);
            }while(c.moveToNext());
    	}
		return filters;
	}



}