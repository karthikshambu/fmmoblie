package com.strata.firstmilebooks.database_helper;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.strata.firstmilebooks.model.Offer;

public class OfferDBAdapter extends MainDBHelper{

	private static final String TABLE_OFFER= "offers";
	private static final String KEY_CREATED_AT = "created_at";

	private static final String KEY_OFFER_IMAGE = "image";
    private static final String KEY_OFFER_NAME = "name";
    private static final String KEY_OFFER_ID = "offer_id";
    private static final String KEY_OFFER_RES_ID = "res_id";
    private static final String KEY_OFFER_FEED_ID = "feed_id";
    private static final String KEY_OFFER_DETAIL = "detail";
    private static final String KEY_OFFER_VALIDITY = "validity";
    RestaurantDBAdapter restaurantDb;
    public OfferDBAdapter(Context context) {
		super(context);
		restaurantDb = new RestaurantDBAdapter(context);
	}

	public ArrayList<Offer> getAllOffer(int feed_id) {
        ArrayList<Offer> offer_list = new ArrayList<Offer>();
        String selectQuery = "SELECT  * FROM " + TABLE_OFFER + " WHERE " + KEY_OFFER_FEED_ID
        					 + " = "+feed_id;

        Log.e("offer_db_helper", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.getCount() > 0 && c.moveToFirst()) {
            do {
            	Offer offer = new Offer();
            	offer.setOffer_id(c.getInt(c.getColumnIndex(KEY_OFFER_ID)));
            	offer.setName(c.getString(c.getColumnIndex(KEY_OFFER_NAME)));
            	offer.setImage(c.getString(c.getColumnIndex(KEY_OFFER_IMAGE)));
            	offer.setValidity(c.getString(c.getColumnIndex(KEY_OFFER_VALIDITY)));
            	offer.setDetail(c.getString(c.getColumnIndex(KEY_OFFER_DETAIL)));
            	offer.setTime(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                offer_list.add(offer);
            } while (c.moveToNext());
        }

        return offer_list;
    }

	public Offer getOfferById(int offer_id) {
        String selectQuery = "SELECT  * FROM " + TABLE_OFFER + " WHERE " + KEY_OFFER_ID
        					 + " = "+offer_id;

        Log.e("offer_db_helper", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Offer offer = new Offer();
        // looping through all rows and adding to list
        if (c.getCount() > 0 && c.moveToFirst()) {

        	offer.setRestaurant(restaurantDb.getResById(c.getInt(c.getColumnIndex(KEY_OFFER_RES_ID))));
        	offer.setRes_id(c.getInt(c.getColumnIndex(KEY_OFFER_RES_ID)));
        	offer.setName(c.getString(c.getColumnIndex(KEY_OFFER_NAME)));
        	offer.setImage(c.getString(c.getColumnIndex(KEY_OFFER_IMAGE)));
        	offer.setValidity(c.getString(c.getColumnIndex(KEY_OFFER_VALIDITY)));
        	offer.setDetail(c.getString(c.getColumnIndex(KEY_OFFER_DETAIL)));
        	offer.setTime(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
        }
        restaurantDb.close();

        return offer;
    }

	public void insertNewOffer(Offer offer){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_OFFER_IMAGE, offer.getImage());
		contentValues.put(KEY_OFFER_NAME, offer.getName());
		contentValues.put(KEY_OFFER_ID, offer.getOffer_id());
		contentValues.put(KEY_OFFER_RES_ID, offer.getRestaurant().getRes_id());
		restaurantDb.insertNewRestaurant(offer.getRestaurant());

		contentValues.put(KEY_OFFER_FEED_ID, offer.getFeed_id());
		contentValues.put(KEY_OFFER_DETAIL, offer.getDetail());
		contentValues.put(KEY_OFFER_VALIDITY, offer.getValidity());
		contentValues.put(KEY_CREATED_AT, offer.getTime());
		db.insert(TABLE_OFFER, null, contentValues);
		restaurantDb.close();
	}

	public boolean deleteOffersByFeedId(int feed_id){
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(TABLE_OFFER, KEY_OFFER_FEED_ID + "=" + feed_id, null) > 0;
	}
}
