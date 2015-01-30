package com.strata.firstmilebooks.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.adapters.ProductCatalogAdapter;
import com.strata.firstmilebooks.model.ProductList;

public class ProductCatalogActivity extends Activity {
	ListView product_listview;
	ProductCatalogAdapter adapter;
	ArrayList<ProductList> product_list = new ArrayList<ProductList>();
	Context context;
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		return super.onOptionsItemSelected(item);
	}
	
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.products_layout);
	    
	    context = getApplicationContext();
	    product_listview = (ListView) findViewById(R.id.products);
	    
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
	    product_listview.setAdapter(adapter);
	    
	    product_listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
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