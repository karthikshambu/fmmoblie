package com.strata.firstmilebooks.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.adapters.ProductCatalogAdapter;
import com.strata.firstmilebooks.model.ProductList;

public class ProductListActivity extends Activity {
	ListView pl_listview;
	ProductCatalogAdapter adapter;
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
	    
	    getActionBar().setTitle(getIntent().getStringExtra("name"));
	    getActionBar().setSubtitle(getIntent().getStringExtra("publisher"));
	    
	    
	    String[] product_name = getResources().getStringArray(R.array.product_name);
	    String[] product_image = getResources().getStringArray(R.array.product_image);
	    String[] product_desc = getResources().getStringArray(R.array.product_desc);
	    final String[] product_creator = getResources().getStringArray(R.array.product_creator);
	    final String[] product_manufacturer = getResources().getStringArray(R.array.product_manufacturer);
	    final String[] product_created_date = getResources().getStringArray(R.array.product_created_date);
	    
	    
	    for(int i=0; i < product_name.length; i++){
	    	ProductList single_prod = new ProductList();
	    	single_prod.setPl_name(product_name[i]);
	    	single_prod.setPl_image(product_image[i]);
	    	single_prod.setPl_desc(product_desc[i]);
	    	product_list.add(single_prod);
	    }
	    
	    
	    adapter = new ProductCatalogAdapter(this, product_list);
	    // selecting single ListView item
	    pl_listview.setAdapter(adapter);
	    
	    pl_listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					//Bundle b=new Bundle();
					position -= 1;
					Intent in = new Intent(context,ProductDetailActivity.class);
					in.putExtra("prod_name", product_list.get(position).getPl_name());
					in.putExtra("prod_desc", product_list.get(position).getPl_desc());
					in.putExtra("prod_image", product_list.get(position).getPl_image());
					in.putExtra("product_creator", product_creator[position]);
					in.putExtra("product_manufacturer", product_manufacturer[position]);
					in.putExtra("product_created_date", product_created_date[position]);
					startActivity(in);
				}
			});
	    

	  }


}
