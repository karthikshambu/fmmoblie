package com.strata.firstmilebooks.signup;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback.EmptyCallback;
import com.squareup.picasso.Picasso;
import com.strata.firstmilebooks.Config;
import com.strata.firstmilebooks.JSONParser;
import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.activity.ProfileViewActivity;
import com.strata.firstmilebooks.database_helper.ConsumerDBAdapter;
import com.strata.firstmilebooks.fragments.NeighbourSelectFragment;
import com.strata.firstmilebooks.model.Consumer;

public class ProfileEntryPage extends FragmentActivity implements NeighbourSelectFragment.OnlistSelectedListener {
	JSONArray nbs_array = new JSONArray();
	ArrayList<NameValuePair> postParameters;
	String location_id = "0";
	ProgressDialog prgDialog;
	String location_name;
	EditText user_name;
	EditText user_email;
	EditText user_phone;
	EditText user_select_location;
	FrameLayout frame_container;
	private NeighbourSelectFragment fragment;
	JSONParseSubmitInfo json_submit_info;
	JSONGetLocations json_get_locations;
	private static int RESULT_LOAD_IMG = 1;
	String imgPath = null;
	String fileName;
    Bitmap bitmap;
    ImageView user_image;
    String encodedString;
    ProgressBar progress_bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_entry_layout);
		prgDialog = new ProgressDialog(this);
        // Set Cancelable as False
        prgDialog.setCancelable(false);
        progress_bar = (ProgressBar) findViewById(R.id.progressBar1);
		String url_locations = "http://"+Config.SERVER_BASE_URL+"/api/v1/consumers/locations.json";
		json_get_locations = new JSONGetLocations();
		json_get_locations.execute(url_locations);

		user_image = (ImageView)findViewById(R.id.user_image);
		user_name = (EditText)findViewById(R.id.user_name);
		user_email = (EditText)findViewById(R.id.user_email);
		user_phone = (EditText)findViewById(R.id.user_phone);
		Button update_info = (Button)findViewById(R.id.update_info);
		user_select_location = (EditText)findViewById(R.id.user_select_location);

		frame_container = (FrameLayout)findViewById(R.id.frame_container);
		SharedPreferences preferences = getSharedPreferences("PREF",Context.MODE_PRIVATE);

		user_select_location.setText(preferences.getString("LOCATION_NAME", ""));
		location_id = String.valueOf(preferences.getInt("LOCATION", 0));

		user_select_location.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				fragment = new NeighbourSelectFragment();
				FragmentManager fragmentManager = getSupportFragmentManager();
	            fragmentManager.beginTransaction()
	                    .replace(R.id.frame_container, fragment).commit();
	            frame_container.setVisibility(View.VISIBLE);
			}
		});

		user_name.setText(preferences.getString("NAME", ""));
		user_phone.setText(preferences.getString("NUMBER", ""));
		//show email only if it is empty
		if(preferences.getString("EMAIL", "").isEmpty())
			user_email.setVisibility(View.VISIBLE);



		//--------------------------


		try{
			progress_bar.setVisibility(View.VISIBLE);
			Picasso.with(this)
			.load(preferences.getString("IMAGE", ""))
			.noFade()
			.into(user_image, new EmptyCallback() {
	            @Override public void onSuccess() {
	            	progress_bar.setVisibility(View.GONE);
	            }
	            @Override
	            public void onError() {
	            	progress_bar.setVisibility(View.GONE);
	            }
	        });

		} catch(Exception e){
		}

		user_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent galleryIntent = new Intent(Intent.ACTION_PICK,
		                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		        // Start the Intent
		        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
			}
		});

		update_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				prgDialog.setMessage("Updating");
	            prgDialog.show();
				postParameters = new ArrayList<NameValuePair>();
	            postParameters.add(new BasicNameValuePair("name", user_name.getText().toString()));
	            postParameters.add(new BasicNameValuePair("email", user_email.getText().toString()));
	            postParameters.add(new BasicNameValuePair("phone_no", user_phone.getText().toString()));
	            postParameters.add(new BasicNameValuePair("location", location_id));
	            if (imgPath != null){
	            	postParameters.add(new BasicNameValuePair("image_base_64", encodeImagetoString()));
	            }

                String url = "http://" + Config.SERVER_BASE_URL
        				+ "/api/v1/consumers/update_info.json";
                json_submit_info = new JSONParseSubmitInfo();
                json_submit_info.execute(url);
			}
		});
	}

	//add this where you want the nb select
	 public void onNeighbourSelected(String name,String id,String tagged_by) {
		 if(!id.isEmpty()){
			 location_id = id;
			 user_select_location.setText(name);
			 frame_container.setVisibility(View.GONE);
		 }
	 }

	    // When Image is selected from Gallery
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        try {
	            // When an Image is picked
	            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
	                    && null != data) {
	                // Get the Image from data

	                Uri selectedImage = data.getData();
	                String[] filePathColumn = { MediaStore.Images.Media.DATA };

	                // Get the cursor
	                Cursor cursor = getContentResolver().query(selectedImage,
	                        filePathColumn, null, null, null);
	                // Move to first row
	                cursor.moveToFirst();

	                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	                imgPath = cursor.getString(columnIndex);
	                cursor.close();
	                // Set the Image in ImageView
	        		try{
	        				String image_loc;
	        				progress_bar.setVisibility(View.VISIBLE);
	        				if (!imgPath.startsWith("/http")){
	        					image_loc = "file://"+imgPath;
	        				}
	        				else{
	        					image_loc = imgPath;
	        				}

	        				Picasso.with(this)
	        				.load(image_loc)
	        				.fit().centerCrop()
	        				.noFade()
	        				.into(user_image, new EmptyCallback() {
	        		            @Override public void onSuccess() {
	        		            	progress_bar.setVisibility(View.GONE);
	        		            }
	        		            @Override
	        		            public void onError() {
	        		            	progress_bar.setVisibility(View.GONE);
	        		            }
	        		        });

	        		} catch(Exception e){
	        		}


	            } else {
	                Toast.makeText(this, "You haven't picked Image",
	                        Toast.LENGTH_LONG).show();
	            }
	        } catch (Exception e) {
	            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
	                    .show();
	        }

	    }

	    public String encodeImagetoString() {
            BitmapFactory.Options options = null;
            options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            bitmap = BitmapFactory.decodeFile(imgPath,
                    options);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            bitmap.compress(Bitmap.CompressFormat.PNG, 5, stream);
            byte[] byte_arr = stream.toByteArray();
            // Encode Image to String
            encodedString = Base64.encodeToString(byte_arr, 0);
            return encodedString;
	    }


	private class JSONParseSubmitInfo extends AsyncTask<String, String, JSONObject> {

		protected JSONObject doInBackground(String... args) {
			JSONParser jp = new JSONParser();
			JSONObject json = jp.postJSONArrayToServer(postParameters,args[0],getApplicationContext());
			if (isCancelled()) {
				return null;
			}else
				return json;

		}

		protected void onPostExecute(JSONObject json) {
			try{
				if (json!=null && json.getBoolean("success")) {

					SharedPreferences preferences = getSharedPreferences("PREF",Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = preferences.edit();

					editor.putString("NUMBER", json.getJSONObject("user_details").getString("phone_no"));
					editor.putString("EMAIL", json.getJSONObject("user_details").getString("email"));
					editor.putString("NAME", json.getJSONObject("user_details").getString("name"));
					editor.putInt("LOCATION", json.getJSONObject("user_details").getInt("location"));
					editor.putString("LOCATION_NAME", json.getJSONObject("user_details").getString("location_name"));
					editor.putString("IMAGE", json.getJSONObject("user_details").getString("image"));
					editor.commit();
					Consumer con = new Consumer();
					con.setImage(json.getJSONObject("user_details").getString("image"));
					con.setName(json.getJSONObject("user_details").getString("name"));
					con.setConsumer_id(json.getJSONObject("user_details").getInt("consumer_id"));
					con.setPhone_no(json.getJSONObject("user_details").getString("phone_no"));
					ConsumerDBAdapter cn_db = new ConsumerDBAdapter(getApplicationContext());
					cn_db.updateConsumer(con);
					cn_db.close();
					Toast toast = Toast.makeText(getApplicationContext(),"Information successfully updated",Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.TOP, 0, 170);
					toast.show();

					Intent in = new Intent(getBaseContext(),ProfileViewActivity.class);
					in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(in);
					finish();

				}
			}catch (JSONException e) {
				e.printStackTrace();
				Toast toast = Toast.makeText(getApplicationContext(),
						"Failed to update information",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.TOP, 0, 170);
				toast.show();
			}
			prgDialog.hide();
		}
	}

	private class JSONGetLocations extends AsyncTask<String, String, JSONObject> {

		protected JSONObject doInBackground(String... args) {
			JSONParser jp = new JSONParser();
			JSONObject json = jp.getJSONFromUrl(args[0],getBaseContext());
			return json;
		}

		protected void onPostExecute(JSONObject json) {
			if(json!=null){
				try {
					nbs_array = json.getJSONArray("nbs");
		            String[] location_array = new String[nbs_array.length()];
		            for (int i = 0; i < nbs_array.length(); i++) {
		            	location_array[i] = nbs_array.getJSONObject(i).getString("name");
		    		}
		            // Create an ArrayAdapter using the string array and a default spinner layout
		    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,location_array);
		    		// Specify the layout to use when the list of choices appears
		    		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    		// Apply the adapter to the spinner
		//	    		user_select_location.setAdapter(adapter);
				} catch (JSONException e) {
					Toast toast = Toast.makeText(getApplicationContext(),"Failed to fetch data",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 170);
					toast.show();
				}
			}
		}
	}




	public void onDestroy(){
		super.onDestroy();
		if(json_submit_info != null){
			json_submit_info.cancel(true);
		}
		if(json_get_locations != null){
			json_get_locations.cancel(true);
		}
        if (prgDialog != null) {
            prgDialog.dismiss();
        }
	}
}