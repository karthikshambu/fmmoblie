package com.strata.firstmilebooks.database_helper;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.strata.firstmilebooks.model.Consumer;
import com.strata.firstmilebooks.model.Reply;

public class ReplyDBAdapter extends MainDBHelper{
	Context context;
	// REPLY Table - column names
	private static final String TABLE_REPLY = "feed_replies";
	private static final String KEY_CREATED_AT = "created_at";

	private static final String KEY_REPLY_CONTENT = "content";
    private static final String KEY_REPLY_TYPE = "type";
    private static final String KEY_REPLY_FEED_ID = "feed_id";
    private static final String KEY_REPLY_CONSUMER_ID = "consumer_id";
    private static final String KEY_REPLY_OFFER_ID = "offer_id";
    private static final String KEY_REPLY_RES_ID = "res_id";
    private static final String KEY_REPLY_ID = "reply_id";



    RestaurantDBAdapter restaurant;
    OfferDBAdapter offer;
    ConsumerDBAdapter consumer;

	public ReplyDBAdapter(Context context) {
		super(context);
		this.context = context;
		restaurant = new RestaurantDBAdapter(context);
		offer = new OfferDBAdapter(context);
		consumer = new ConsumerDBAdapter(context);
	}

	// Get replies for a feed
    public ArrayList<Reply> getAllReplys(int feed_id) {
        ArrayList<Reply> replies = new ArrayList<Reply>();
        String selectQuery = "SELECT  * FROM " + TABLE_REPLY + " WHERE " + KEY_REPLY_FEED_ID
        					 + " = "+feed_id;



        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.getCount() > 0 && c.moveToFirst()) {


	            do {
	                Reply reply = new Reply();
	                reply.setType(c.getString(c.getColumnIndex(KEY_REPLY_TYPE)));
	                String type = reply.getType();
	                reply.setContent(c.getString(c.getColumnIndex(KEY_REPLY_CONTENT)));
	                reply.setTime(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
	                int consumer_id = c.getInt(c.getColumnIndex(KEY_REPLY_CONSUMER_ID));
	                if(consumer_id > 0){
		                reply.setConsumer_id(consumer_id);
		                Consumer con = consumer.getConsumerById(consumer_id);
		                reply.setConsumer(con);
	                }

	                if(type.equals("RS")){
	                	reply.setRes_id(c.getInt(c.getColumnIndex(KEY_REPLY_RES_ID)));
						reply.setRestaurant(restaurant.getResById(c.getInt(c.getColumnIndex(KEY_REPLY_RES_ID))));
					}else if(type.equals("OF")){
						reply.setOffer(offer.getOfferById(c.getInt(c.getColumnIndex(KEY_REPLY_OFFER_ID))));
						reply.setRestaurant(restaurant.getResById(c.getInt(c.getColumnIndex(KEY_REPLY_RES_ID))));
					}

	                replies.add(reply);
	            } while (c.moveToNext());
        }
        restaurant.close();
        offer.close();
        return replies;
    }



    public boolean insertNewReply(ArrayList<Reply> replies){
    	if(replies!=null){
	    	SQLiteDatabase db = this.getWritableDatabase();
			for (int i = 0; i < replies.size(); i++) {
				Reply reply = replies.get(i);

				Reply existing = findReplyById(reply.getReply_id());

				if(existing == null){
					String type = reply.getType();
					ContentValues contentValues = new ContentValues();
					contentValues.put(KEY_REPLY_CONTENT, reply.getContent());
					contentValues.put(KEY_REPLY_TYPE, reply.getType());
					contentValues.put(KEY_CREATED_AT, reply.getTime());
					contentValues.put(KEY_REPLY_FEED_ID, reply.getFeed_id());
					contentValues.put(KEY_REPLY_ID, reply.getReply_id());
					if(reply.getConsumer() != null){
						long consumer_id = consumer.insertNewConsumer(reply.getConsumer());
						contentValues.put(KEY_REPLY_CONSUMER_ID, consumer_id);
					}

					if(type.equals("RS")){
						contentValues.put(KEY_REPLY_RES_ID, reply.getRes_id());
		            	restaurant.insertNewRestaurant(reply.getRestaurant());
		            	restaurant.close();
					}else if(type.equals("OF")){
						contentValues.put(KEY_REPLY_OFFER_ID, reply.getOffer_id());
						contentValues.put(KEY_REPLY_RES_ID, reply.getOffer().getRes_id());
						offer.insertNewOffer(reply.getOffer());
						offer.close();
					}
					db.insert(TABLE_REPLY, null, contentValues);
				}
			}
			return true;
    	}
    	return false;
    }

    public Reply findReplyById(int id){
    	Reply reply =null;
    	String selectQuery = "SELECT  * FROM " + TABLE_REPLY + " WHERE " + KEY_REPLY_ID
				 + " = "+id;



		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);


		// looping through all rows and adding to list
		if (c.getCount() > 0 && c.moveToFirst()) {
			reply= new Reply();
            reply.setReply_id(c.getInt(c.getColumnIndex(KEY_REPLY_ID)));
		}
    	return reply;
    }

    public int getLastReplyId(int feed_id){
		String selectQuery = "SELECT  max("+KEY_REPLY_ID+") FROM " + TABLE_REPLY + " WHERE " + KEY_REPLY_FEED_ID
                + " = "+feed_id;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		int last_id = (c.moveToFirst() ? c.getInt(0) : 0);
    	return last_id;
    }

	public int getNewMessages(int last_reply_id, int feed_id) {
		String selectQuery = "SELECT  count("+KEY_REPLY_ID+") FROM " + TABLE_REPLY + " WHERE " + KEY_REPLY_FEED_ID
                + " = "+feed_id + " AND "+ KEY_REPLY_ID + " >"+last_reply_id;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(selectQuery, null);
		int last_id = (c.moveToFirst() ? c.getInt(0) : 0);
    	return last_id;
	}
	public boolean deleteRepliesByFeedId(int feed_id){
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(TABLE_REPLY, KEY_REPLY_FEED_ID + "=" + feed_id, null) > 0;
	}
}
