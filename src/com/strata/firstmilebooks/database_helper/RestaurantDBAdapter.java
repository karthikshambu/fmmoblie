package com.strata.firstmilebooks.database_helper;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.strata.firstmilebooks.model.Restaurant;

public class RestaurantDBAdapter extends MainDBHelper{

	private static final String TABLE_RESTAURANT = "restaurants";
	private static final String KEY_RESTAURANT_NO = "phone_no" ;
    private static final String KEY_RESTAURANT_IMAGE = "image";
    private static final String KEY_RESTAURANT_NAME = "name";
    private static final String KEY_RESTAURANT_ID = "res_id";
    private static final String KEY_RESTAURANT_FEED_ID = "feed_id";


	public RestaurantDBAdapter(Context context) {
		super(context);
	}

	public ArrayList<Restaurant> getAllRes(int feed_id) {
        ArrayList<Restaurant> restaurant_list = new ArrayList<Restaurant>();
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT + " WHERE " + KEY_RESTAURANT_FEED_ID
        					 + " = "+feed_id;

        Log.e("Restaurant_db_helper", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.getCount() > 0 && c.moveToFirst()) {
            do {
            	Restaurant restaurant = new Restaurant();
                restaurant.setNumber(c.getString(c.getColumnIndex(KEY_RESTAURANT_NO)));
                restaurant.setName(c.getString(c.getColumnIndex(KEY_RESTAURANT_NAME)));
                restaurant.setImage(c.getString(c.getColumnIndex(KEY_RESTAURANT_IMAGE)));

                restaurant_list.add(restaurant);
            } while (c.moveToNext());
        }

        return restaurant_list;
    }

	public Restaurant getResById(int res_id) {
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT + " WHERE " + KEY_RESTAURANT_ID
        					 + " = "+res_id;



        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Restaurant restaurant = new Restaurant();

        // looping through all rows and adding to list
        if (c.getCount() > 0 && c.moveToFirst()) {
            restaurant.setNumber(c.getString(c.getColumnIndex(KEY_RESTAURANT_NO)));
            restaurant.setName(c.getString(c.getColumnIndex(KEY_RESTAURANT_NAME)));
            restaurant.setImage(c.getString(c.getColumnIndex(KEY_RESTAURANT_IMAGE)));
        }

        return restaurant;
    }

	public void insertNewRestaurant(Restaurant restaurant){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_RESTAURANT_NO, restaurant.getNumber());
		contentValues.put(KEY_RESTAURANT_IMAGE, restaurant.getImage());
		contentValues.put(KEY_RESTAURANT_NAME, restaurant.getName());

		contentValues.put(KEY_RESTAURANT_ID, restaurant.getRes_id());

		db.insert(TABLE_RESTAURANT, null, contentValues);
	}
}
