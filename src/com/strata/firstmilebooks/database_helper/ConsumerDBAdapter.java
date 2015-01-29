package com.strata.firstmilebooks.database_helper;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.strata.firstmilebooks.model.Consumer;

public class ConsumerDBAdapter extends MainDBHelper{

	private static final String TABLE_CONSUMER = "consumers";
	private static final String KEY_CONSUMER_NAME = "name";
    private static final String KEY_CONSUMER_NO = "phone_no";
    private static final String KEY_CONSUMER_IMAGE = "image";
    private static final String KEY_CONSUMER_ID = "consumer_id";


	public ConsumerDBAdapter(Context context) {
		super(context);
	}

	// Get replies for a feed
    public ArrayList<Consumer> getAllConsumers() {
        ArrayList<Consumer> consumer_list = new ArrayList<Consumer>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONSUMER;

        Log.e("consumer_db_helper", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.getCount() > 0 && c.moveToFirst()) {

            do {
                Consumer consumer = new Consumer();

                consumer.setName(c.getString(c.getColumnIndex(KEY_CONSUMER_NAME)));
                consumer.setPhone_no(c.getString(c.getColumnIndex(KEY_CONSUMER_NO)));
                consumer.setImage(c.getString(c.getColumnIndex(KEY_CONSUMER_IMAGE)));
                consumer.setConsumer_id(c.getInt(c.getColumnIndex(KEY_CONSUMER_ID)));

                consumer_list.add(consumer);
            } while (c.moveToNext());

        }

        return consumer_list;
    }

    public Consumer getConsumerById(int consumer_id) {
        String selectQuery = "SELECT  * FROM " + TABLE_CONSUMER + " WHERE " + KEY_CONSUMER_ID
        					 + " = "+consumer_id;

        Log.e("consumer_db_helper", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Consumer consumer = null;
        // looping through all rows and adding to list
        if (c.getCount() > 0 && c.moveToFirst()) {
        	 consumer= new Consumer();
            consumer.setPhone_no(c.getString(c.getColumnIndex(KEY_CONSUMER_NO)));
            consumer.setName(c.getString(c.getColumnIndex(KEY_CONSUMER_NAME)));
            consumer.setImage(c.getString(c.getColumnIndex(KEY_CONSUMER_IMAGE)));
            consumer.setConsumer_id(c.getInt(c.getColumnIndex(KEY_CONSUMER_ID)));
        }

        return consumer;
    }

    public void updateConsumer(Consumer consumer) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_CONSUMER_NAME, consumer.getName());
		contentValues.put(KEY_CONSUMER_NO, consumer.getPhone_no());
		contentValues.put(KEY_CONSUMER_IMAGE, consumer.getImage());
		contentValues.put(KEY_CONSUMER_ID, consumer.getConsumer_id());
    	db.update(TABLE_CONSUMER, contentValues, KEY_CONSUMER_ID + " = ?",
                new String[] { String.valueOf(consumer.getConsumer_id()) });
	}

    public long insertNewConsumer(Consumer consumer){
    	SQLiteDatabase db = this.getWritableDatabase();
    	Consumer con = getConsumerById(consumer.getConsumer_id());
	    if(con == null){
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_CONSUMER_NAME, consumer.getName());
		contentValues.put(KEY_CONSUMER_NO, consumer.getPhone_no());
		contentValues.put(KEY_CONSUMER_IMAGE, consumer.getImage());
		contentValues.put(KEY_CONSUMER_ID, consumer.getConsumer_id());
		db.insert(TABLE_CONSUMER, null, contentValues);
	    }
	    else{
	    	updateConsumer(consumer);
	    }
    	return consumer.getConsumer_id();
    }



}
