package com.strata.firstmilebooks.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.activity.ConverseActivity;
import com.strata.firstmilebooks.model.DemoFeed;

public class FeedAdapter extends BaseAdapter {
	private final Activity context;
	//private final ArrayList<Feed> feed_list;
	private final ArrayList<DemoFeed> feed_list;

	public FeedAdapter(Activity context, ArrayList<DemoFeed> feed_list) {
		this.context = context;
		this.feed_list = feed_list;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		/*
		 * LayoutInflater inflater = context.getLayoutInflater(); View rowView =
		 * inflater.inflate(R.layout.list_item, null, true);
		 */
		DemoFeed feed = feed_list.get(position);
		//String type = feed.getType();
		View rowView;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		
		 rowView = inflater.inflate(R.layout.feed_list_item, parent,
				false);
//		WebView content  = (WebView) rowView.findViewById(R.id.id_content);
		TextView desc  = (TextView) rowView.findViewById(R.id.feed_desc);
		TextView feed_header  = (TextView) rowView.findViewById(R.id.feed_header);
		TextView created_at = (TextView) rowView.findViewById(R.id.id_dining_date);
		TextView time = (TextView) rowView.findViewById(R.id.time);
		TextView publisher_name = (TextView) rowView.findViewById(R.id.id_publisher_name);
		TextView btn_feed_converse = (TextView) rowView.findViewById(R.id.btn_feed_converse);
		TextView btn_feed_share = (TextView) rowView.findViewById(R.id.btn_feed_share);
		ImageView pub_image = (ImageView) rowView.findViewById(R.id.id_user_image);
		ImageView pl_cover_image = (ImageView) rowView.findViewById(R.id.id_feed_cover);
		
		desc.setText(feed.getDescription());
		feed_header.setText("Shared " + feed.getProduct_list_name() + " and added to " + feed.getProduct_list_type());
		created_at.setText(feed.getDate());
		time.setText(feed.getTime());
		publisher_name.setText(feed.getPublisher_name());
		btn_feed_converse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ConverseActivity.class);
				context.startActivity(intent);
				
			}
		});
		
		btn_feed_share.setOnClickListener(new OnClickListener(){
	        public void onClick(View v){
	        Intent i=new Intent(android.content.Intent.ACTION_SEND);
	        i.setType("text/plain");
	        i.putExtra(android.content.Intent.EXTRA_SUBJECT,"I am sharing a list with you. ");
	        i.putExtra(android.content.Intent.EXTRA_TEXT, "please have a look  http://www.bookbundles.in/bundles/10000");
	        context.startActivity(Intent.createChooser(i,"Share via"));
	        }
	    });

		
		Picasso.with(context)
		.load(feed.getPublisher_image())
		.noFade()
		.fit().centerCrop()
		.into(pub_image);
		
		Picasso.with(context)
		.load(feed.getCover_image())
		.noFade()
		.fit().centerCrop()
		.into(pl_cover_image);
		
		//content.setText(feed.getSubject());
		//created_at.setText(feed.getDining_date());
//		publisher_name.setText(feed);
		//time.setText(feed.getDining_time());
		return rowView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return feed_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return feed_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
