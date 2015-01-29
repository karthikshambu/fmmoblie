package com.strata.firstmilebooks.activity;


import java.util.ArrayList;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;
import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.adapters.FeedAdapter;
import com.strata.firstmilebooks.database_helper.FeedDBAdapter;
import com.strata.firstmilebooks.model.DemoFeed;


public class HomeActivity extends Activity {
	ListView feed_listview;
	//private JSONParse json_parse;
	FeedAdapter adapter;
	TextView no_feeds;
	JSONArray list = null;
	ArrayList<DemoFeed> feed_list = new ArrayList<DemoFeed>();
	FeedDBAdapter db;
	Context context;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.tab_layout_menu, menu);
		menu.findItem(R.id.action_products).setIcon(
				   new IconDrawable(this, IconValue.fa_cutlery)
				   .colorRes(R.color.white)
				   .actionBarSize());
		menu.findItem(R.id.action_lists).setIcon(
				   new IconDrawable(this, IconValue.fa_database)
				   .colorRes(R.color.icon_color)
				   .actionBarSize());
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		int id = item.getItemId();
//		if (id == R.id.action_profile) {
//			Intent in = new Intent(getApplicationContext(), ProfileViewActivity.class);
//			startActivity(in);
//			return true;
//		}else 
			if (id == R.id.sign_out) {
			SharedPreferences preferences = getSharedPreferences("PREF",Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString("NAME", "");
			editor.putString("IMAGE", "");
			editor.putString("EMAIL", "");
			editor.putString("NUMBER", "");
			editor.putString("AUTH_TOKEN", "");
			editor.commit();
			Intent in = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(in);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_layout);
		context = getApplicationContext();
		feed_listview = (ListView) findViewById(R.id.feed_list);
//		no_feeds = (TextView) findViewById(R.id.no_feeds);
		String[] pub_name = getResources().getStringArray(R.array.publisher_name);
		String[] pub_image = getResources().getStringArray(R.array.publisher_image);
		String[] pl_name = getResources().getStringArray(R.array.product_list_name);
		String[] pl_type = getResources().getStringArray(R.array.product_list_type);
		String[] feed_date = getResources().getStringArray(R.array.date);
		String[] feed_time = getResources().getStringArray(R.array.time);
		String[] pl_desc = getResources().getStringArray(R.array.description);
		String[] pl_cover = getResources().getStringArray(R.array.cover_image);
		//db = new FeedDBAdapter(getApplicationContext());
		//feed_list = db.getAllFeeds();
		for(int i=0; i < pub_name.length; i++){
			DemoFeed dem_feed = new DemoFeed();
			dem_feed.setPublisher_name(pub_name[i]);
			dem_feed.setPublisher_image(pub_image[i]);
			dem_feed.setProduct_list_name(pl_name[i]);
			dem_feed.setProduct_list_type(pl_type[i]);
			dem_feed.setDate(feed_date[i]);
			dem_feed.setTime(feed_time[i]);
			dem_feed.setCover_image(pl_cover[i]);
			dem_feed.setDescription(pl_desc[i]);
			feed_list.add(dem_feed);
		}
		adapter = new FeedAdapter(this, feed_list);
		// selecting single ListView item
		
		//View headerView = ((LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.feed_list_header, null, false);
    	
    	//feed_listview.addHeaderView(headerView);
		feed_listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		feed_listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//Bundle b=new Bundle();
				Intent in = new Intent(context,ProductListActivity.class);
				in.putExtra("name", feed_list.get(position).getProduct_list_name());
				in.putExtra("desc", feed_list.get(position).getDescription());
				startActivity(in);
			}
		});
		
		/*LinearLayout new_run = (LinearLayout) headerView.findViewById(R.id.new_run);
		new_run.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent in = new Intent(getApplicationContext(), CreateGroup.class);
				Bundle bund = new Bundle();
				bund.putBoolean("isEdit", false);
				in.putExtras(bund);
				startActivity(in);
			}

		});

		json_parse = new JSONParse();
		json_parse.execute();*/
	}
	
	



	/*private class JSONParse extends AsyncTask<String, String, JSONObject> {
		protected void onPreExecute() {

		}

		protected JSONObject doInBackground(String... args) {
			String url = "http://"+Config.SERVER_BASE_URL+"/api/v1/get_feeds.json?last_updated="+db.getLastUpdated();
			JSONParser jp = new JSONParser();
			JSONObject json = jp.getJSONFromUrl(url, getApplicationContext());
			if (isCancelled()) {
				return null;
			} else
				return json;
		}

		protected void onPostExecute(JSONObject json) {
			if (json != null && !isCancelled()) {
				// = new ArrayList<NearbyArray>();
				try {
					// Getting Array of data
					list = json.getJSONArray("feed");
					ArrayList<Feed> feed_list1 = new ArrayList<Feed>();
					if (list.length() != 0) {
						// looping through All data
						for (int i = 0; i < list.length(); i++) {
							JSONObject d = list.getJSONObject(i);
							JSONObject c = d.getJSONObject("details");
							Feed feed_from_server = new Feed();
							String feed_type = d.getString("type");
							feed_from_server.setFeed_id(d.getInt("id"));
							feed_from_server.setType(feed_type);
							feed_from_server.setCreated(d.getString("created_at"));
							if(feed_type.equals("TeamLunch")){
								feed_from_server.setSubject(c.getString("subject"));
								feed_from_server.setState(c.getString("state"));
								//feed_from_server.setLast_updated_at(c.getInt("last_updated_at"));
								feed_from_server.setRun_type(c.getString("run_type"));
								feed_from_server.setLast_updated_at(d.getInt("last_updated"));
								//TODO 
								feed_from_server.setPayableAmount("100");
								feed_from_server.setRecivableAmount("0");
								feed_from_server.setVisited_res_id(c.getInt("visited_res_id"));
								feed_from_server.setNeighborhood_id(c.getInt("neighborhood_id"));
								feed_from_server.setNeighborhood_name(c.getString("neighborhood_name"));
								//basic info
								if(c.has("info")){
									JSONObject info = c.getJSONObject("info");
									feed_from_server.setDining_date(info.getString("date"));
									feed_from_server.setDining_time(info.getString("time"));
									feed_from_server.setBudget(info.getString("budget"));
								}
								// publisher
								if (c.has("publisher")){
									Consumer con = new Consumer();
									JSONObject publisher = c.getJSONObject("publisher");
									 con.setConsumer_id(publisher.getInt("id"));
									 con.setName(publisher.getString("name"));
									 con.setImage(publisher.getString("image"));
									 con.setPhone_no(publisher.getString("number"));
									 feed_from_server.setPublisher(con);
								}
								 //participants
								if (c.has("participants")){
									JSONArray participants_array = c.getJSONArray("participants");
									ArrayList<Consumer> participants = new ArrayList<Consumer>();
									if (participants_array.length() != 0){
										for (int j = 0; j < participants_array.length(); j++) {
											Consumer par = new Consumer();
											JSONObject p = participants_array.getJSONObject(j);
											par.setConsumer_id(p.getInt("id"));
											par.setName(p.getString("name"));
											par.setImage(p.getString("image"));
											par.setPhone_no(p.getString("number"));
											participants.add(par);
										}
									}
									feed_from_server.setParticipants(participants);
								}
								if (c.has("replies")){
									JSONArray messg = c.getJSONArray("replies");
									int this_feed_id;
									ArrayList<Reply> new_reply_list =  new ArrayList<Reply>();
									for (int j = 0; j < messg.length(); j++) {
										JSONObject m = messg.getJSONObject(j);
										Reply reply = new Reply();
										this_feed_id = reply.setFeed_id(m.getInt("feed_id"));
										String type = reply.setType(m.getString("type"));
										reply.setContent(m.getString("text"));
										reply.setTime(m.getString("time"));
										reply.setReply_id(m.getInt("id"));
										reply.setFeed_id(this_feed_id);
										reply.setType(type);
										if (m.has("consumer")){
											Consumer consumer = new Consumer();
											JSONObject c_json = m.getJSONObject("consumer");
											consumer.setName(c_json.getString("name"));
											consumer.setPhone_no (c_json.getString("number"));
											consumer.setImage(c_json.getString("image"));
											consumer.setConsumer_id(c_json.getInt("id"));

											reply.setConsumer_id(m.getInt("id"));
											reply.setConsumer(consumer);
										}
										if(type.equals("RS") && m.has("restaurant")){
											Restaurant restaurant = new Restaurant();
											JSONObject r_json = m.getJSONObject("restaurant");
											restaurant.setName(r_json.getString("name"));

											restaurant.setImage(r_json.getString("image"));
											restaurant.setRes_id(r_json.getInt("id"));
											reply.setRes_id(r_json.getInt("id"));
											reply.setRestaurant(restaurant);
										}
										else if(type.equals("OF") && m.has("offer")){
											Offer offer = new Offer();
											JSONObject o_json = m.getJSONObject("offer");

											offer.setName(o_json.getString("name"));
											offer.setImage(o_json.getString("image"));
											offer.setDetail(o_json.getString("detail"));
											offer.setValidity(o_json.getString("validity"));
											offer.setFeed_id(this_feed_id);
											offer.setOffer_id(o_json.getInt("id"));
											if(o_json.has("restaurant")){
												Restaurant restaurant = new Restaurant();
												JSONObject r_json = o_json.getJSONObject("restaurant");
												restaurant.setName(r_json.getString("name"));
												restaurant.setImage(r_json.getString("image"));
												restaurant.setRes_id(r_json.getInt("id"));
												offer.setRes_id(r_json.getInt("id"));
												offer.setRestaurant(restaurant);
											}
											reply.setOffer_id(o_json.getInt("id"));
											reply.setOffer(offer);
										}
										new_reply_list.add(reply);
									}
									feed_from_server.setReplies(new_reply_list);
								}

							}else if(feed_type.equals("BusinessScribble") || feed_type.equals("BloggerScribble")){
								feed_from_server.setHeader(c.getString("header"));
								feed_from_server.setBody(c.getString("body"));
								feed_from_server.setImage(c.getString("image"));
								feed_from_server.setContent_type(c.getString("scribble_type"));
							}
							feed_list1.add(feed_from_server);
						}
						db.insertNewFeeds(feed_list1);
					} else {
						no_feeds.setVisibility(View.VISIBLE);
					}
					feed_list.clear();
					feed_list = db.getAllFeeds();
					adapter.addAll(feed_list);
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
					no_feeds.setVisibility(View.VISIBLE);
				}
			}
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		context.registerReceiver(mMessageReceiver, new IntentFilter("com.strata.team_lunch.chat"));
		db = new FeedDBAdapter(getApplicationContext());
		feed_list = db.getAllFeeds();
		adapter = new FeedAdapter(this, feed_list);
		// selecting single ListView item
		feed_listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	public void onPause() {
		super.onPause();
		if(mMessageReceiver != null)
			context.unregisterReceiver(mMessageReceiver);
		db.close();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		db.close();
		json_parse.cancel(true);
		finish();
	}

	//This is the handler that will manager to process the broadcast intent
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	
	    	json_parse = new JSONParse();
			json_parse.execute();
	    }
	};*/
}
