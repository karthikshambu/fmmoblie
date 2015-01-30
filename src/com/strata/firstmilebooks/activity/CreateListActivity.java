package com.strata.firstmilebooks.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.strata.firstmilebooks.R;

public class CreateListActivity extends Activity {
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.create_list_layout);
	   
	    EditText lbl_name = (EditText) findViewById(R.id.id_form_pl_name);
	    EditText lbl_desc = (EditText) findViewById(R.id.id_form_pl_desc);
	    
	    
	    
	}
	
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
			Intent in = new Intent(getApplicationContext(), AddToListActivity.class);
			startActivity(in);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
