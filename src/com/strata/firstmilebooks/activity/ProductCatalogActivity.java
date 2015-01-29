package com.strata.firstmilebooks.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.adapters.ProductAdapter;
import com.strata.firstmilebooks.model.ProductList;

public class ProductCatalogActivity extends Activity {
	ListView product_listview;
	ProductAdapter adapter;
	ArrayList<ProductList> product_list = new ArrayList<ProductList>();
	Context context;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_group_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_next) {
			Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			
			Toast.makeText(getApplicationContext(), "your list has been created", Toast.LENGTH_LONG).show();
		}
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
	    
	    
	    for(int i=0; i < product_name.length; i++){
	    	ProductList single_prod = new ProductList();
	    	single_prod.setPl_name(product_name[i]);
	    	single_prod.setPl_image(product_image[i]);
	    	single_prod.setPl_desc(product_desc[i]);
	    	product_list.add(single_prod);
	    }
	    
	    adapter = new ProductAdapter(this, product_list);
	    // selecting single ListView item
	    product_listview.setAdapter(adapter);
	  }


}

