package com.strata.firstmilebooks.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.strata.firstmilebooks.JSONParser;
import com.strata.firstmilebooks.R;


public class ProductDetailActivity extends Activity {
	Context context;
	TextView lbl_author;
	TextView lbl_publisher;
	TextView lbl_created_date;
	JSONParse json_parse;
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.product_details_layout);
	   
	    context = getApplicationContext();	    
	    TextView lbl_name = (TextView) findViewById(R.id.prod_name);
	    TextView lbl_desc = (TextView) findViewById(R.id.prod_desc);
	    ImageView prod_image = (ImageView)findViewById(R.id.prod_image); 
	    
	    lbl_author = (TextView) findViewById(R.id.prod_creator);
	    lbl_publisher = (TextView) findViewById(R.id.prod_manufacturer);
	    lbl_created_date = (TextView) findViewById(R.id.prod_created_date);
	    
	    lbl_name.setText("Name : " + getIntent().getStringExtra("prod_name"));
	    lbl_desc.setText("Description: " + getIntent().getStringExtra("prod_desc"));
	    
	    lbl_author.setText("Author: " + getIntent().getStringExtra("product_creator"));
	    lbl_publisher.setText("Publisher: " + getIntent().getStringExtra("product_manufacturer"));
		lbl_created_date.setText("Created Date: " + getIntent().getStringExtra("product_created_date"));
		
		
		Picasso.with(context)
			.load(getIntent().getStringExtra("prod_image"))
			.noFade()
			.fit().centerCrop()
			.into(prod_image);
	    
	    
	    
	    //json_parse = new JSONParse();
	    //json_parse.execute();
	   
	}
	
	private class JSONParse extends AsyncTask<String,String,JSONObject>{
		 protected void onPreExecute(){
		 }
		 protected JSONObject doInBackground(String... args){
			 String url = "http://192.168.2.10:3000/products/return_data_to_mobile";
			 JSONParser jp = new JSONParser();
			 JSONObject json = jp.getJSONFromUrl(url);
			 return json;
		 }
		 protected void onPostExecute(JSONObject json){
			if (json != null && !isCancelled()){
				try {
//					JSONArray list = json.getJSONArray(TAG_WISHLIST);
//					if(list != null){
//						String[] bookArrayStringed = new String[list.length()+1];
//						bookList.clear();
//						// looping through All data
//						for (int i = 0; i < list.length(); i++) {
//							String[] temp_array = new String[11];
//							JSONObject c = list.getJSONObject(i);
//					
//							temp_array[0] = c.getString(TAG_TITLE);
//						}
//					}
					lbl_author.setText(json.getString("author"));
					lbl_publisher.setText(json.getString("publisher"));
					lbl_created_date.setText(json.getString("created_date"));
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
	
		 }
	}
//	@Override
//	public void onDestroy(){
//		super.onDestroy();
//		json_parse.cancel(true);
//	}

}