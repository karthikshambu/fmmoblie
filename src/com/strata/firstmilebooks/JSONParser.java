package com.strata.firstmilebooks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() {

	}

	public JSONObject getJSONForSignIn(ArrayList<NameValuePair> data,String url) {

		// Making HTTP request
		try {
			jObj = null;
			is = null;
			json = "";

			// defaultHttpClient
			//DefaultHttpClient httpClient = new DefaultHttpClient();
			
			//HttpGet httpPost = new HttpGet(url);
			
			HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url); 
            UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(data);
            httpPost.setEntity(postEntity);
            HttpResponse httpResponse = httpclient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			
			//HttpResponse httpResponse = httpClient.execute(httpPost);
			//HttpEntity httpEntity = httpResponse.getEntity();
			//is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error",
					"Error converting result " + e.getStackTrace());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}

	public JSONObject getJSONFromUrl(String url, Context mContext) {

		// Making HTTP request
		try {
			jObj = null;
			is = null;
			json = "";
			SharedPreferences value = mContext.getSharedPreferences("PREF",
					Context.MODE_PRIVATE);
			String auth_token = value.getString("AUTH_TOKEN", "");
			String numb = value.getString("NUMBER", "");
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpPost = new HttpGet(url);
			httpPost.setHeader("auth-token",auth_token);
            httpPost.setHeader("numb",numb);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error",
					"Error converting result " + e.getStackTrace());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}
		
	public JSONObject postJSONArrayToServer(ArrayList<NameValuePair> data,String url, Context mContext) {
        try {
        	SharedPreferences value = mContext.getSharedPreferences("PREF",
					Context.MODE_PRIVATE);
			String auth_token = value.getString("AUTH_TOKEN", "");
			String numb = value.getString("NUMBER", "");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url); 
            httpPost.setHeader("auth-token",auth_token);
            httpPost.setHeader("numb",numb);
            UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(data);
            httpPost.setEntity(postEntity);
            HttpResponse httpResponse = httpclient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
        try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error",
					"Error converting result " + e.getStackTrace());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;
	}
	public JSONObject sendMessageToServer(String data,int last_id,String url, Context mContext, String message) {
        try {
        	SharedPreferences value = mContext.getSharedPreferences("PREF",
					Context.MODE_PRIVATE);
			String auth_token = value.getString("AUTH_TOKEN", "");
			String numb = value.getString("NUMBER", "");
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
 
            // 7. Set some headers to inform server about the type of the content   
            httpPost.setHeader("auth-token",auth_token);
            httpPost.setHeader("numb",numb);
            
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            //postParameters.add(new BasicNameValuePair("group_name", "INSANE GROUP"));
            postParameters.add(new BasicNameValuePair("chat_id", data));
            String last = String.valueOf(last_id);
            postParameters.add(new BasicNameValuePair("last_reply_id", last));
            if (!message.isEmpty())
            	postParameters.add(new BasicNameValuePair("message", message));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            httpPost.setEntity(formEntity);
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
 
            // 9. receive response as inputStream
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
        try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error",
					"Error converting result " + e.getStackTrace());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;
	}
}
