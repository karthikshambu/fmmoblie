package com.strata.firstmilebooks.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.adapters.ProductListAdapter;
import com.strata.firstmilebooks.model.ProductList;

public class ProductListActivity extends Activity {
	ListView pl_listview;
	ProductListAdapter adapter;
	ArrayList<ProductList> product_list = new ArrayList<ProductList>();
	Context context;
	
	
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.product_list_layout);
	    
	    context = getApplicationContext();
	    pl_listview = (ListView) findViewById(R.id.product_list);
	    View headerView = ((LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.product_list_header, null, false);
	    pl_listview.addHeaderView(headerView);
	    
	    TextView lbl_name = (TextView) headerView.findViewById(R.id.pl_name);
	    TextView lbl_desc = (TextView) headerView.findViewById(R.id.pl_desc);
	    
	    lbl_name.setText(getIntent().getStringExtra("name"));
	    lbl_desc.setText(getIntent().getStringExtra("desc"));
	    
	    
//	    db = new FeedDBAdapter(getApplicationContext());
//	    feed_list = db.getAllFeeds();
	    
//	    int setPl_id = getResources().getStringArray(R.array.publisher_name);
//	    String[] pub_image = getResources().getStringArray(R.array.publisher_image);
//	    String[] pl_name = getResources().getStringArray(R.array.product_list_name);
//	    String[] pl_type = getResources().getStringArray(R.array.product_list_type);
//	    String[] feed_date = getResources().getStringArray(R.array.date);
//	    String[] feed_time = getResources().getStringArray(R.array.time);
//	    String[] pl_desc = getResources().getStringArray(R.array.description);
//	    String[] pl_cover = getResources().getStringArray(R.array.cover_image);
	    
	    String[] product_name = getResources().getStringArray(R.array.product_name);
	    String[] product_image = getResources().getStringArray(R.array.product_image);
	    String[] product_desc = getResources().getStringArray(R.array.product_desc);
	    
	    
	    for(int i=0; i < product_name.length; i++){
	    	ProductList single_prod = new ProductList();
	    	single_prod.setPl_name(product_name[i]);
	    	single_prod.setPl_image(product_image[i]);
	    	single_prod.setPl_desc(product_desc[i]);
	    	product_list.add(single_prod);
	    }
	    
	    adapter = new ProductListAdapter(this, product_list);
	    // selecting single ListView item
	    pl_listview.setAdapter(adapter);
	    //adapter.notifyDataSetChanged();
//	    pl_listview.setOnItemClickListener(new OnItemClickListener(){

//	      @Override
//	      public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//	        position = position - 1; 
//	        Bundle b=new Bundle();
//	        Intent in;
//	        if(product_list.get(position).getType().equals("TeamLunch")){
//	          b.putString("feed_id", String.valueOf(feed_list.get(position).getFeed_id()));
//	          b.putString("publisher_id", String.valueOf(feed_list.get(position).getPublisher_id()));
//	          b.putInt("visited_res_id", feed_list.get(position).getVisited_res_id());
//	          in = new Intent(getApplicationContext(), ChatMainTabActivity.class);
//	          in.putExtras(b);
//	          startActivity(in);
//	        }else if(feed_list.get(position).getType().equals("BusinessScribble") || feed_list.get(position).getType().equals("BloggerScribble")){
//	          int content_id= feed_list.get(position).getFeed_id();
//	          b.putString("content_id", String.valueOf(content_id));
//	          in = new Intent(getApplicationContext(), SingleContentActivity.class);
//	          in.putExtras(b);
//	          startActivity(in);
//	        }
//	      }
//	    });
	    
//	    LinearLayout new_run = (LinearLayout) headerView.findViewById(R.id.new_run);
//	    new_run.setOnClickListener(new OnClickListener() {
//
//	      @Override
//	      public void onClick(View arg0) {
//	        Intent in = new Intent(getApplicationContext(), CreateGroup.class);
//	        Bundle bund = new Bundle();
//	        bund.putBoolean("isEdit", false);
//	        in.putExtras(bund);
//	        startActivity(in);
//	      }
//
//	    });
//
//	    json_parse = new JSONParse();
//	    json_parse.execute();
	  }


}
