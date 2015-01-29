package com.strata.firstmilebooks.activity;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.gcm_registration.GCMRegisterActivity;
import com.strata.firstmilebooks.signup.GoogleSigninActivity;

public class MainActivity extends Activity {

	int current_date;
	Context context;
	// GCM register
	public static final String REG_ID = "regId";
	GCMRegisterActivity register_activity = new GCMRegisterActivity();

	JSONArray list = null;
	String auth;
	String numb;

	// String slot = "10000";

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		SharedPreferences pref = getSharedPreferences("PREF",Context.MODE_PRIVATE);
		numb = pref.getString("NUMBER", "");
		auth = pref.getString("AUTH_TOKEN", "");
		
		
		if (auth != null && !auth.isEmpty()) {
		//if (true) {
			// if (numb != null && numb != ""){
			// Get current date by calendar
			String registrationId = pref.getString(REG_ID, "");
			if (registrationId.isEmpty()) {
				register_activity.start_registration(getApplicationContext());
			}
			new Handler().postDelayed(new Runnable() {

		        @Override
		        public void run() {
		        	Intent in = new Intent(context,HomeActivity.class);
					in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(in);
		        }
		    }, 2000);
			
		} else {
			Intent in = new Intent(getApplicationContext(),GoogleSigninActivity.class);
			startActivity(in);
		}
	}

}