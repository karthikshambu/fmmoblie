package com.strata.firstmilebooks.database_helper;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.strata.firstmilebooks.model.Consumer;
import com.strata.firstmilebooks.model.Feed;

public class FeedDBAdapter extends MainDBHelper{
	//Feed
	private static final String TABLE_FEED = "feeds";
	private static final String KEY_FEED_TYPE = "feed_type";
    private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_FEED_SUBJECT = "subject";
    private static final String KEY_FEED_DINING_DATE = "dining_date";
    private static final String KEY_FEED_DINING_TIME = "dining_time";
    private static final String KEY_FEED_PUBLISHER_ID = "publisher_id";
    private static final String KEY_FEED_ID = "feed_id";
    private static final String TABLE_PARTICIPANT = "feed_consumers";
    private static final String KEY_PART_FEED_ID = "feed_id";
    private static final String KEY_CONSUMER_ID = "consumer_id";
    private static final String KEY_BUDGET = "budget";
    private static final String KEY_STATE = "state";
    private static final String KEY_FEED_LAST_READ_REPLY = "last_read_id";
    private static final String KEY_OPENED = "opened";
    private static final String KEY_NB_NAME = "neighborhood_name";
    private static final String KEY_NB_ID = "neighborhood_id";
    private static final String LAST_UPDATED = "last_updated";
    private static final String RUN_TYPE = "run_type";
    private static final String VISITED_RESTAURANT = "visited_res_id";
    private static final String PAYABLE_AMOUNT = "payable_amount";
    private static final String RECIVABLE_AMOUNT = "recivable_amount";
    private static final String KEY_HEADER = "header";
    private static final String KEY_BODY = "body";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_CONTENT_TYPE = "content_type";

    Context context;
	public FeedDBAdapter(Context context) {
		super(context);
		this.context = context;

	}

	// Get replies for a feed
    public ArrayList<Feed> getAllFeeds() {
        ArrayList<Feed> feed_list = new ArrayList<Feed>();
        String selectQuery = "SELECT  * FROM " + TABLE_FEED + " order by "+KEY_FEED_ID + " desc";

        Log.e("feed_db_helper", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        ReplyDBAdapter rdb = new ReplyDBAdapter(context);
        ConsumerDBAdapter cdb = new ConsumerDBAdapter(context);
        // looping through all rows and adding to list
        if (c.getCount() > 0 && c.moveToFirst()) {
            do {
                Feed feed = new Feed();
                int id = c.getInt(c.getColumnIndex(KEY_FEED_ID));
                String type = c.getString(c.getColumnIndex(KEY_FEED_TYPE));

                feed.setFeed_id(id);
                feed.setType(type);
                if(type.equals("TeamLunch")){
                	int consumer_id = c.getInt(c.getColumnIndex(KEY_FEED_PUBLISHER_ID));
                    int participants_count = participantsCount(id);

                    feed.setSubject(c.getString(c.getColumnIndex(KEY_FEED_SUBJECT)));
                    feed.setDining_date(c.getString(c.getColumnIndex(KEY_FEED_DINING_DATE)));
                    feed.setDining_time(c.getString(c.getColumnIndex(KEY_FEED_DINING_TIME)));
                    feed.setBudget(c.getString(c.getColumnIndex(KEY_BUDGET)));
                    feed.setOpened(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_BUDGET))));
                    feed.setState(c.getString(c.getColumnIndex(KEY_STATE)));
                    feed.setPublisher_id(consumer_id);
                    feed.setPublisher(cdb.getConsumerById(consumer_id));

                    feed.setRun_type(c.getString(c.getColumnIndex(RUN_TYPE)));
                    feed.setVisited_res_id(c.getInt(c.getColumnIndex(VISITED_RESTAURANT)));
                    feed.setNeighborhood_id(c.getInt(c.getColumnIndex(KEY_NB_ID)));
                    feed.setNeighborhood_name(c.getString(c.getColumnIndex(KEY_NB_NAME)));
                    feed.setCreated(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                    feed.setRecivableAmount(c.getString(c.getColumnIndex(RECIVABLE_AMOUNT)));
                    feed.setPayableAmount(c.getString(c.getColumnIndex(PAYABLE_AMOUNT)));
                    feed.setLast_reply_id(c.getInt(c.getColumnIndex(KEY_FEED_LAST_READ_REPLY)));
                    int newMessages = rdb.getNewMessages(feed.getLast_reply_id(),feed.getFeed_id());
                    feed.setNewMessages(newMessages);
                    feed.setParticipants_count(participants_count);
                }else if(type.equals("BusinessScribble") || type.equals("BloggerScribble")){
                	feed.setHeader(c.getString(c.getColumnIndex(KEY_HEADER)));
                	feed.setBody(c.getString(c.getColumnIndex(KEY_BODY)));
                	feed.setImage(c.getString(c.getColumnIndex(KEY_IMAGE)));
                	feed.setContent_type(c.getString(c.getColumnIndex(KEY_CONTENT_TYPE)));
                }

                feed_list.add(feed);
            } while (c.moveToNext());
        }
        rdb.close();
        cdb.close();
        return feed_list;
    }

    public Feed findFeedById(int feed_id){
        String selectQuery = "SELECT  * FROM " + TABLE_FEED + " WHERE " + KEY_FEED_ID
				 + " = "+feed_id;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
        ConsumerDBAdapter cdb = new ConsumerDBAdapter(context);
		Feed feed = null;
		// looping through all rows and adding to list
		if (c.getCount() > 0 && c.moveToFirst()) {
			feed = new Feed();
            int id = c.getInt(c.getColumnIndex(KEY_FEED_ID));
            String type = c.getString(c.getColumnIndex(KEY_FEED_TYPE));

            feed.setFeed_id(id);
            if(type.equals("TeamLunch")){
	            int consumer_id = c.getInt(c.getColumnIndex(KEY_FEED_PUBLISHER_ID));
	            int participants_count = participantsCount(id);
	            feed.setSubject(c.getString(c.getColumnIndex(KEY_FEED_SUBJECT)));
	            feed.setDining_date(c.getString(c.getColumnIndex(KEY_FEED_DINING_DATE)));
	            feed.setDining_time(c.getString(c.getColumnIndex(KEY_FEED_DINING_TIME)));
	            feed.setPublisher_id(consumer_id);
	            feed.setBudget(c.getString(c.getColumnIndex(KEY_BUDGET)));
	            feed.setPublisher(cdb.getConsumerById(consumer_id));
	            feed.setRun_type(c.getString(c.getColumnIndex(RUN_TYPE)));
	            feed.setVisited_res_id(c.getInt(c.getColumnIndex(VISITED_RESTAURANT)));
	            feed.setNeighborhood_id(c.getInt(c.getColumnIndex(KEY_NB_ID)));
	            feed.setNeighborhood_name(c.getString(c.getColumnIndex(KEY_NB_NAME)));
	            feed.setOpened(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_BUDGET))));
	            feed.setState(c.getString(c.getColumnIndex(KEY_STATE)));
	            feed.setCreated(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
	            feed.setRecivableAmount(c.getString(c.getColumnIndex(RECIVABLE_AMOUNT)));
	            feed.setPayableAmount(c.getString(c.getColumnIndex(PAYABLE_AMOUNT)));
	            feed.setLast_reply_id(c.getInt(c.getColumnIndex(KEY_FEED_LAST_READ_REPLY)));
	            feed.setParticipants_count(participants_count);
            }else if(type.equals("BusinessScribble") || type.equals("BloggerScribble")){
            	feed.setHeader(c.getString(c.getColumnIndex(KEY_HEADER)));
            	feed.setBody(c.getString(c.getColumnIndex(KEY_BODY)));
            	feed.setImage(c.getString(c.getColumnIndex(KEY_IMAGE)));
            	feed.setContent_type(c.getString(c.getColumnIndex(KEY_CONTENT_TYPE)));
            }
		}
		cdb.close();
		return feed;
    }


	public boolean insertNewFeeds(ArrayList<Feed> feed_list){
		if(feed_list!=null){
	    	SQLiteDatabase db = this.getWritableDatabase();
	    	ConsumerDBAdapter cdb = new ConsumerDBAdapter(context);
	    	ReplyDBAdapter rdb = new ReplyDBAdapter(context);
			for (int i = 0; i < feed_list.size(); i++) {
				ContentValues contentValues = new ContentValues();
				Feed feed = feed_list.get(i);
				Feed existing_feed = findFeedById(feed.getFeed_id());
				String type = feed.getType();
				if(existing_feed == null){
					contentValues.put(KEY_FEED_ID, feed.getFeed_id());
					contentValues.put(KEY_FEED_TYPE, feed.getType());
					if(type.equals("TeamLunch")){
						long consumer_id = cdb.insertNewConsumer(feed.getPublisher());
						contentValues.put(KEY_CREATED_AT, feed.getCreated());
						contentValues.put(KEY_FEED_SUBJECT, feed.getSubject());
						contentValues.put(KEY_FEED_DINING_DATE, feed.getDining_date());
						contentValues.put(KEY_FEED_DINING_TIME, feed.getDining_time());
						contentValues.put(KEY_BUDGET, feed.getBudget());
						contentValues.put(KEY_STATE, feed.getState());
						contentValues.put(KEY_FEED_PUBLISHER_ID, consumer_id);
						contentValues.put(KEY_FEED_LAST_READ_REPLY, 0);
						contentValues.put(KEY_OPENED, false);
						contentValues.put(RUN_TYPE, feed.getRun_type());
						contentValues.put(VISITED_RESTAURANT, feed.getVisited_res_id());
						contentValues.put(LAST_UPDATED,feed.getLast_updated_at());
						contentValues.put(KEY_NB_NAME, feed.getNeighborhood_name());
						contentValues.put(KEY_NB_ID, feed.getNeighborhood_id());
						contentValues.put(PAYABLE_AMOUNT, feed.getPayableAmount());
						contentValues.put(RECIVABLE_AMOUNT, feed.getRecivableAmount());
	                    //contentValues.put(LAST_UPDATED,feed.getLast_updated_at());
						insertParticipants(feed.getParticipants(),feed.getFeed_id());
						rdb.insertNewReply(feed.getReplies());
					}else if(type.equals("BusinessScribble") || type.equals("BloggerScribble")){
						contentValues.put(KEY_HEADER, feed.getHeader());
						contentValues.put(KEY_BODY, feed.getBody());
						contentValues.put(KEY_IMAGE, feed.getImage());
						contentValues.put(KEY_CONTENT_TYPE, feed.getContent_type());
					}
					db.insert(TABLE_FEED, null, contentValues);

				}else{
					updateFeed(feed);
					if(type.equals("TeamLunch")){
						deleteParticipantsByFeedId(feed.getFeed_id());
						insertParticipants(feed.getParticipants(),feed.getFeed_id());
						rdb.insertNewReply(feed.getReplies());
					}
				}
			}
			cdb.close();
			rdb.close();
	    	return true;
		}
		return false;
    }

	public boolean deleteFeedById(int feed_id){
		Feed existing_feed = findFeedById(feed_id);
		SQLiteDatabase db = this.getWritableDatabase();

		if(existing_feed != null){

			if(db.delete(TABLE_FEED, KEY_FEED_ID + "=" + feed_id, null) > 0){
				ReplyDBAdapter rdb = new ReplyDBAdapter(context);
				OfferDBAdapter odb = new OfferDBAdapter(context);
				deleteParticipantsByFeedId(feed_id);
				rdb.deleteRepliesByFeedId(feed_id);
				odb.deleteOffersByFeedId(feed_id);
				odb.close();
				rdb.close();
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
    }

	public boolean deleteParticipantsByFeedId(int feed_id){
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(TABLE_PARTICIPANT, KEY_PART_FEED_ID + "=" + feed_id, null) > 0;
	}
    public void insertParticipants(ArrayList<Consumer> participants,int feed_id) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ConsumerDBAdapter cdb = new ConsumerDBAdapter(context);
		for (int i = 0; i < participants.size(); i++) {
			ContentValues contentValues = new ContentValues();
			Consumer consumer = participants.get(i);
			long consumer_id = cdb.insertNewConsumer(consumer);
			contentValues.put(KEY_CONSUMER_ID, consumer_id);
			contentValues.put(KEY_FEED_ID, feed_id);
			db.insert(TABLE_PARTICIPANT, null, contentValues);
		}
		cdb.close();
	}

    public ArrayList<Consumer> getParticipants(int feed_id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	ArrayList<Consumer> participants = new ArrayList<Consumer>();
    	ConsumerDBAdapter cdb = new ConsumerDBAdapter(context);
    	String selectQuery = "SELECT  * FROM " + TABLE_PARTICIPANT + " WHERE "+KEY_FEED_ID + "= "+feed_id;
    	Cursor c = db.rawQuery(selectQuery, null);
    	Consumer con;
    	if (c.getCount() > 0 && c.moveToFirst()) {
            do {
            	int consumer_id =c.getInt(c.getColumnIndex(KEY_CONSUMER_ID));
            	con = cdb.getConsumerById(consumer_id);
            	participants.add(con);
            }while(c.moveToNext());
    	}
    	cdb.close();
		return participants;
	}

    public int participantsCount(int feed_id){
    	SQLiteDatabase db = this.getReadableDatabase();
    	int count = 0;
    	String selectQuery = "SELECT  count(1) FROM " + TABLE_PARTICIPANT + " WHERE "+KEY_FEED_ID + "= "+feed_id;
    	Cursor c = db.rawQuery(selectQuery, null);
    	count = (c.moveToFirst() ? c.getInt(0) : 0);
		return count;
    }


    public int updateFeed(Feed feed){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        String type = feed.getType();
        if(type!=null){
	        if(type.equals("TeamLunch")){
	        	contentValues.put(KEY_FEED_DINING_DATE, feed.getDining_date());
	    		contentValues.put(KEY_FEED_DINING_TIME, feed.getDining_time());
	    		contentValues.put(KEY_BUDGET, feed.getBudget());
	    		contentValues.put(KEY_STATE, feed.getState());
	    		contentValues.put(KEY_FEED_SUBJECT, feed.getSubject());
	    		contentValues.put(KEY_NB_NAME, feed.getNeighborhood_name());
	    		contentValues.put(KEY_NB_ID, feed.getNeighborhood_id());
	    		contentValues.put(RUN_TYPE, feed.getRun_type());
	    		contentValues.put(PAYABLE_AMOUNT, feed.getPayableAmount());
	    		contentValues.put(RECIVABLE_AMOUNT, feed.getRecivableAmount());
	    		contentValues.put(VISITED_RESTAURANT, feed.getVisited_res_id());
	        }else if(type.equals("BusinessScribble") || type.equals("BloggerScribble")){
	        	contentValues.put(KEY_HEADER, feed.getHeader());
				contentValues.put(KEY_BODY, feed.getBody());
				contentValues.put(KEY_IMAGE, feed.getImage());
				contentValues.put(KEY_CONTENT_TYPE, feed.getContent_type());
	        }

	        return db.update(TABLE_FEED, contentValues, KEY_FEED_ID + " = ?",
	                new String[] { String.valueOf(feed.getFeed_id()) });
        }
        return 0;

    }

    public int updateFeedState(int feed_id, String state){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

		contentValues.put(KEY_STATE, state);

        return db.update(TABLE_FEED, contentValues, KEY_FEED_ID + " = ?",
                new String[] { String.valueOf(feed_id) });

    }

    public void updateFeedStateAndSettlement(int feed_id, String state,String recivable, String payable){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

		contentValues.put(KEY_STATE, state);
		contentValues.put(PAYABLE_AMOUNT, payable);
		contentValues.put(RECIVABLE_AMOUNT, recivable);
        db.update(TABLE_FEED, contentValues, KEY_FEED_ID + " = ?",
                new String[] { String.valueOf(feed_id) });

    }

    public int updateFeedVisitedResId(int feed_id, Integer res_id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

		contentValues.put(VISITED_RESTAURANT, res_id);

        return db.update(TABLE_FEED, contentValues, KEY_FEED_ID + " = ?",
                new String[] { String.valueOf(feed_id) });

    }

    public int updateFeedLastRead(int feed_id,int last_read_id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_FEED_LAST_READ_REPLY, last_read_id);

        return db.update(TABLE_FEED, contentValues, KEY_FEED_ID + " = ?",
                new String[] { String.valueOf(feed_id) });
    }

    public int updateFeedOpened(int feed_id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_OPENED, true);

        return db.update(TABLE_FEED, contentValues, KEY_FEED_ID + " = ?",
                new String[] { String.valueOf(feed_id) });
    }

    public int getLastUpdated(){
        String selectQuery = "SELECT  max("+LAST_UPDATED+") FROM " + TABLE_FEED;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int last_id = (c.moveToFirst() ? c.getInt(0) : 0);
        return last_id;
    }

}
