package com.strata.firstmilebooks.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;
import com.squareup.picasso.Callback.EmptyCallback;
import com.squareup.picasso.Picasso;
import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.signup.ProfileEntryPage;

public class ProfileViewActivity extends Activity{
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_group_menu, menu);
		menu.findItem(R.id.action_next).setIcon(
				   new IconDrawable(this, IconValue.fa_pencil_square_o)
				   .colorRes(R.color.white)
				   .actionBarSize());
		return true;
	}

	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    int itemId = item.getItemId();
	    if (itemId == android.R.id.home){
	    	finish();
	    	return true;
	    }else if (itemId == R.id.action_next) {
	    	Intent edit_act = new Intent(this,ProfileEntryPage.class);
	    	startActivity(edit_act);
	    }
	    return super.onOptionsItemSelected(item);
	}
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_page);

		SharedPreferences preferences = getSharedPreferences("PREF",Context.MODE_PRIVATE);
		String name = preferences.getString("NAME", "");
		String email = preferences.getString("EMAIL", "");
		String image_url = preferences.getString("IMAGE", "");
		String nb_name = preferences.getString("LOCATION_NAME", "");
		String number = preferences.getString("NUMBER", "");

		//setup action bar
		getActionBar().setTitle(name);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		TextView lname = (TextView)findViewById(R.id.user_name);
		TextView lemail = (TextView)findViewById(R.id.user_email);
		TextView lnb = (TextView)findViewById(R.id.user_nb);
		TextView lphone = (TextView)findViewById(R.id.user_phone);
		ImageView limage = (ImageView)findViewById(R.id.user_image);
		final ProgressBar progress_bar = (ProgressBar)findViewById(R.id.progressBar1);

		lname.setText(name);
		lemail.setText(email);
		lnb.setText(nb_name);
		lphone.setText(number);

		//--------------------------
		try{
			if(image_url != null){
				Picasso.with(this)
				.load(image_url)
				.fit()
				.noFade()
				.into(limage, new EmptyCallback() {
		            @Override public void onSuccess() {
		            	progress_bar.setVisibility(View.GONE);
		            }
		            @Override
		            public void onError() {
		            	progress_bar.setVisibility(View.GONE);
		            }
		        });
			}
		} catch(Exception e){
		}

		//--------------------------
	}
}
