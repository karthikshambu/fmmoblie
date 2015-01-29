package com.strata.firstmilebooks.signup;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.strata.firstmilebooks.Config;
import com.strata.firstmilebooks.JSONParser;
import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.activity.MainActivity;

public class GoogleSigninActivity extends Activity implements OnClickListener,
				ConnectionCallbacks, OnConnectionFailedListener {

	private static final int RC_SIGN_IN = 0;
	// Profile pic image size in pixels
	private static final int PROFILE_PIC_SIZE = 100;
	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;
	//A flag indicating that a PendingIntent is in progress and prevents us
	//from starting further intents.
	private boolean mIntentInProgress;
	private boolean mSignInClicked = false;
	private ConnectionResult mConnectionResult;
	private SignInButton btnSignIn;
	private Button buttonEnterNumber;
	private Button revokeGoogle;
	private EditText my_numb;
	private ProgressBar sign_in_progress;
	private ArrayList<NameValuePair> postParameters;
	JSONParse json_parse;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_layout);
		//setup all the view components
		btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
		buttonEnterNumber = (Button)findViewById(R.id.btn_enter_number);
		revokeGoogle = (Button)findViewById(R.id.btn_revoke_google);
		my_numb = (EditText) findViewById(R.id.editText1);
		sign_in_progress = (ProgressBar) findViewById(R.id.progressBar1);
		//check if user number is available
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String number = tm.getLine1Number();
		if (number != null) {
			my_numb.setText(number.replace("+91", ""));
		}
		// Button click listeners
		btnSignIn.setOnClickListener(this);
		buttonEnterNumber.setOnClickListener(this);
		revokeGoogle.setOnClickListener(this);

		mGoogleApiClient = new GoogleApiClient.Builder(this)
							.addConnectionCallbacks(this).addOnConnectionFailedListener(this)
							.addApi(Plus.API, new Plus.PlusOptions.Builder().build())
							.addScope(Plus.SCOPE_PLUS_LOGIN).build();
		signOutFromGplus();
	}

	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
		    mGoogleApiClient.disconnect();
		}
	}
	//Method to resolve any signin errors
	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
		    try {
		        mIntentInProgress = true;
		        mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
		    } catch (SendIntentException e) {
		        mIntentInProgress = false;
		        mGoogleApiClient.connect();
		    }
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
		    GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,0).show();
		    return;
		}

		if (!mIntentInProgress) {
		    // Store the ConnectionResult for later usage
		    mConnectionResult = result;
		    if (mSignInClicked) {
		        // The user has already clicked 'sign-in' so we attempt to
		        // resolve all
		        // errors until the user is signed in, or they cancel.
		        resolveSignInError();
		    }
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,Intent intent) {
		if (requestCode == RC_SIGN_IN) {
		    if (responseCode != RESULT_OK) {
		        mSignInClicked = false;
		    }
		    mIntentInProgress = false;
		    if (!mGoogleApiClient.isConnecting()) {
		        mGoogleApiClient.connect();
		    }
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		mSignInClicked = false;
		Plus.PeopleApi.loadVisible(mGoogleApiClient, null);
		// Get user's information
		getProfileInformation();
	}

	//Fetching user's information name, email, profile pic
	private void getProfileInformation() {
		try {
		    if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
		    	sign_in_progress.setVisibility(View.VISIBLE);

		    	btnSignIn.setVisibility(View.GONE);
		    	revokeGoogle.setVisibility(View.VISIBLE);

		        Person currentPerson = Plus.PeopleApi
		                .getCurrentPerson(mGoogleApiClient);

		        //String personGooglePlusProfile = currentPerson.getUrl();
		        String personName = currentPerson.getDisplayName();
		        String personPhotoUrl = currentPerson.getImage().getUrl();
		        String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
		        // by default the profile url gives 50x50 px image only
		        // we can replace the value with whatever dimension we want by
		        // replacing sz=X
		        personPhotoUrl = personPhotoUrl.substring(0,personPhotoUrl.length() - 2)+ PROFILE_PIC_SIZE;

		        //put all data to sharedpref
		        SharedPreferences preferences = getSharedPreferences("PREF",Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("NAME", personName);
				editor.putString("IMAGE", personPhotoUrl);
				editor.putString("EMAIL", email);
				editor.commit();
				//put all data to params
		        postParameters = new ArrayList<NameValuePair>();
	            postParameters.add(new BasicNameValuePair("person_name", personName));
	            postParameters.add(new BasicNameValuePair("pic_url", personPhotoUrl));
	            postParameters.add(new BasicNameValuePair("email", email));

		        String url = "http://" + Config.SERVER_BASE_URL+"/api/v1/login_create.json";
				// Creating JSON Parser instance
				json_parse = new JSONParse();
				json_parse.execute(url);
		    } else {
		    	Toast toast = Toast.makeText(getApplicationContext(),
						"      Login Failed \nPlease Try Again Later",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 0, 170);
				toast.show();
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	@Override
		public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}

	private void signOutFromGplus() {
	    if (mGoogleApiClient.isConnected()) {
	        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
	        mGoogleApiClient.disconnect();
	        mGoogleApiClient.connect();
	    }
	}
	//Button on click listener

	@Override
	public void onClick(View v) {
		sign_in_progress.setVisibility(View.VISIBLE);
		switch (v.getId()) {
			case R.id.btn_sign_in:
			    // Signin button clicked
			    signInWithGplus();
			    break;
			case R.id.btn_enter_number:
			    // Signout button clicked
			    signInWithNumber();
			    break;
			case R.id.btn_revoke_google:
			    // Signout button clicked
				revokeGplusAccess();
			    break;
		}
	}

	//Sign-in into google
	private void signInWithGplus() {
		if (!mGoogleApiClient.isConnecting()) {
		    mSignInClicked = true;
		    resolveSignInError();
		}
	}

	//Signin using mobile number
	private void signInWithNumber() {
		String phone_no = my_numb.getText().toString();
		//put all data to params
        postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("phone_no", phone_no));

		String url = "http://" + Config.SERVER_BASE_URL+"/api/v1/login_create.json";
		// Creating JSON Parser instance
		json_parse = new JSONParse();
		json_parse.execute(url);

	}

	private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            mGoogleApiClient.connect();
                            btnSignIn.setVisibility(View.VISIBLE);
                            revokeGoogle.setVisibility(View.GONE);
                        }

                    });
        }
    }

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		protected void onPreExecute() {

		}

		protected JSONObject doInBackground(String... args) {
			JSONParser jp = new JSONParser();
			JSONObject json = jp.getJSONForSignIn(postParameters,args[0]);
			return json;
		}

		protected void onPostExecute(JSONObject json) {
			sign_in_progress.setVisibility(View.GONE);
			try { // getting JSON string from URL
				if (json!=null && json.getString("login").equals("success")) {
					//save user info to sharedpref
					SharedPreferences preferences = getSharedPreferences("PREF",Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = preferences.edit();
					editor.putString("AUTH_TOKEN", json.getString("auth_token"));
					editor.putString("NUMBER", json.getJSONObject("user_details").getString("phone_no"));
					editor.putString("EMAIL", json.getJSONObject("user_details").getString("email"));
					editor.putString("NAME", json.getJSONObject("user_details").getString("name"));
					editor.putInt("LOCATION", json.getJSONObject("user_details").getInt("location"));
					editor.putString("LOCATION_NAME", json.getJSONObject("user_details").getString("location_name"));
					editor.putInt("CONSUMER_ID", json.getInt("consumer_id"));
					editor.commit();
					//if the user is new then redirect to profile page
					if(json.getString("user_status").equals("new")){
						//JSONArray nbs_list = json.getJSONArray("nbs");
						//Bundle b = new Bundle();
						//b.putString("nbs_array",json.getJSONObject("user_details").getJSONArray("nbs").toString());
						Intent prof = new Intent(getApplicationContext(),ProfileEntryPage.class);
						//prof.putExtras(b);
						startActivity(prof);
					}else{
						Intent foo = new Intent(getApplicationContext(), MainActivity.class);
						foo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(foo);
					}
				} else {
					Toast toast = Toast.makeText(getApplicationContext(),
							"      Login Failed \nPlease Try Again Later",
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 170);
					toast.show();
				}

			} catch (JSONException e) {
				e.printStackTrace();
				Toast toast = Toast.makeText(getApplicationContext(),
						"      Login Failed \nPlease Try Again Later",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 0, 170);
				toast.show();
			}
		}
	}
}
