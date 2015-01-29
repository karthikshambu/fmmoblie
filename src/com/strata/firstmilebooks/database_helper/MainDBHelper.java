package com.strata.firstmilebooks.database_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MainDBHelper extends SQLiteOpenHelper{
	//database version and name
	public static final String DATABASE_NAME = "TeamLunchAppDB.db";
	public static final int DATABASE_VERSION = 9;
	// Table Names
	private static final String TABLE_FEED = "feeds";
    private static final String TABLE_OFFER = "offers";
    private static final String TABLE_REPLY = "feed_replies";
    private static final String TABLE_CONSUMER = "consumers";
    private static final String TABLE_RESTAURANT = "restaurants";
    private static final String TABLE_PARTICIPANT = "feed_consumers";
    private static final String TABLE_FILTER = "filters";

    // Common column names
    private static final String KEY_ID = "_id";
    private static final String KEY_CREATED_AT = "created_at";

	// REPLY Table - column names
    private static final String KEY_REPLY_CONTENT = "content";
    private static final String KEY_REPLY_TYPE = "type";
    private static final String KEY_REPLY_ID = "reply_id";
    private static final String KEY_REPLY_FEED_ID = "feed_id";
    private static final String KEY_REPLY_CONSUMER_ID = "consumer_id";
    private static final String KEY_REPLY_OFFER_ID = "offer_id";
    private static final String KEY_REPLY_RES_ID = "res_id";

    // Feed Table - column names
    private static final String KEY_FEED_SUBJECT = "subject";
    private static final String KEY_FEED_DINING_DATE = "dining_date";
    private static final String KEY_FEED_DINING_TIME = "dining_time";
    private static final String KEY_FEED_PUBLISHER_ID = "publisher_id";
    private static final String KEY_FEED_ID = "feed_id";
    private static final String KEY_FEED_TYPE = "feed_type";
    private static final String KEY_FEED_LAST_READ_REPLY = "last_read_id";
    private static final String KEY_BUDGET = "budget";
    private static final String KEY_STATE = "state";
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

    // CONSUMER Table - column names
    private static final String KEY_CONSUMER_NAME = "name";
    private static final String KEY_CONSUMER_NO = "phone_no";
    private static final String KEY_CONSUMER_IMAGE = "image";
    private static final String KEY_CONSUMER_ID = "consumer_id";

    //RESTAURANT Table - column names
    private static final String KEY_RESTAURANT_NO = "phone_no" ;
    private static final String KEY_RESTAURANT_IMAGE = "image";
    private static final String KEY_RESTAURANT_NAME = "name";
    private static final String KEY_RESTAURANT_ID = "res_id";

    //OFFER Table - column names
    private static final String KEY_OFFER_IMAGE = "image";
    private static final String KEY_OFFER_NAME = "name";
    private static final String KEY_OFFER_ID = "offer_id";
    private static final String KEY_OFFER_RES_ID = "res_id";
    private static final String KEY_OFFER_FEED_ID = "feed_id";
    private static final String KEY_OFFER_DETAIL = "detail";
    private static final String KEY_OFFER_VALIDITY = "validity";

    // TABLE_PARTICIPANT - column names
    private static final String KEY_PART_CONSUMER_ID = "consumer_id";
    private static final String KEY_PART_FEED_ID = "feed_id";

    // Columna names for filters
    private static final String KEY_NAME = "name";
    private static final String KEY_FILTER_ID = "filter_id";
    private static final String KEY_FILTER_TYPE = "type";

    // FEED table create statement
    private static final String CREATE_TABLE_FEED = "CREATE TABLE " + TABLE_FEED
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FEED_SUBJECT+ " TEXT,"
    		+ KEY_FEED_DINING_TIME+ " TEXT,"
    	    + KEY_FEED_DINING_DATE+ " TEXT,"
    	    + KEY_BUDGET+ " TEXT,"
    	    + KEY_STATE+ " TEXT,"
    	    + KEY_OPENED+ " BOOLEAN,"
    	    + KEY_CREATED_AT+ " DATETIME,"
            + KEY_FEED_PUBLISHER_ID+ " INTEGER,"
            + KEY_FEED_LAST_READ_REPLY+ " INTEGER,"
    		+ KEY_FEED_ID+ " INTEGER,"
    		+ KEY_NB_NAME+ " TEXT,"
    		+ RECIVABLE_AMOUNT+ " TEXT,"
    		+ PAYABLE_AMOUNT+ " TEXT,"
            + LAST_UPDATED+ " INTEGER,"
            + RUN_TYPE+ " TEXT,"
            + KEY_HEADER+ " TEXT,"
            + KEY_BODY+ " TEXT,"
            + KEY_IMAGE+ " TEXT,"
            + KEY_CONTENT_TYPE+ " TEXT,"
            + KEY_FEED_TYPE+ " TEXT,"
            + VISITED_RESTAURANT+" INTEGER,"
    		+ KEY_NB_ID+ " INTEGER"+ ")";

    // REPLY table create statement
	private static final String CREATE_TABLE_REPLY = "CREATE TABLE " + TABLE_REPLY
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_REPLY_CONTENT + " TEXT,"
            + KEY_REPLY_TYPE + " TEXT,"
            + KEY_REPLY_FEED_ID + " INTEGER," + KEY_REPLY_RES_ID + " INTEGER,"
            + KEY_REPLY_CONSUMER_ID + " INTEGER,"+ KEY_REPLY_OFFER_ID + " INTEGER,"
            + KEY_REPLY_ID + " INTEGER,"+ KEY_CREATED_AT + " DATETIME" + ")";

	// CONSUMER table create statement
    private static final String CREATE_TABLE_CONSUMER = "CREATE TABLE "
            + TABLE_CONSUMER + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_CONSUMER_NO + " TEXT,"
            + KEY_CONSUMER_IMAGE + " TEXT,"
            + KEY_CONSUMER_NAME + " TEXT,"
            + KEY_CONSUMER_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // RESTAURANT table create statement
    private static final String CREATE_TABLE_RESTAURANT = "CREATE TABLE "
            + TABLE_RESTAURANT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_RESTAURANT_NO + " TEXT,"
            + KEY_RESTAURANT_IMAGE + " TEXT,"
            + KEY_RESTAURANT_NAME + " TEXT,"
            + KEY_RESTAURANT_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // OFFER table create statement
    private static final String CREATE_TABLE_OFFER = "CREATE TABLE "
            + TABLE_OFFER + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_OFFER_IMAGE + " TEXT,"
            + KEY_OFFER_NAME + " TEXT,"
            + KEY_OFFER_ID + " INTEGER,"
            + KEY_OFFER_RES_ID + " INTEGER,"
            + KEY_OFFER_FEED_ID + " INTEGER,"
            + KEY_OFFER_DETAIL + " TEXT,"
            + KEY_OFFER_VALIDITY + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // PARTICIPANT table create statement
    private static final String CREATE_TABLE_PARTICIPANT = "CREATE TABLE "
            + TABLE_PARTICIPANT + "("
            + KEY_PART_CONSUMER_ID + " INTEGER," + KEY_PART_FEED_ID + " INTEGER"+")";

    // PARTICIPANT table create statement
    private static final String CREATE_TABLE_FILTER = "CREATE TABLE "
            + TABLE_FILTER + "("
            + KEY_NAME + " INTEGER," + KEY_FILTER_TYPE + " TEXT," + KEY_FILTER_ID + " TEXT"+")";

	MainDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_REPLY);
        db.execSQL(CREATE_TABLE_FEED);
        db.execSQL(CREATE_TABLE_CONSUMER);
        db.execSQL(CREATE_TABLE_RESTAURANT);
        db.execSQL(CREATE_TABLE_OFFER);
        db.execSQL(CREATE_TABLE_PARTICIPANT);
        db.execSQL(CREATE_TABLE_FILTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
    int newVersion)
    {
        //if database changed then drop all and recreate
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPLY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONSUMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTICIPANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER);
        // create new tables
        onCreate(db);
    }

}
