package com.strata.firstmilebooks.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.strata.firstmilebooks.Config;
import com.strata.firstmilebooks.JSONParser;
import com.strata.firstmilebooks.R;

public class NeighbourSelectFragment extends Fragment {
	private JSONGetLocations json_get_locations;
	private JSONArray nbs_array = new JSONArray();
	private Context context;
	String[] location_array;
	private ListView frag_listview;
	private SearchView search;
	ArrayList<String> items = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	OnlistSelectedListener mCallback;

	//Activity must implement this interface. check create_group/CreateGroup for the implementation
    public interface OnlistSelectedListener {
        public void onNeighbourSelected(String name,String id,String tagged_by);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnlistSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

    	this.context=getActivity();
    	View rootView = inflater.inflate(R.layout.neighbour_select_layout, container, false);
    	frag_listview = (ListView)rootView.findViewById(R.id.frag_listview);
    	search = (SearchView)rootView.findViewById(R.id.search);
    	return rootView;
    }
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		frag_listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				try {
					String name = parent.getAdapter().getItem(position).toString();
					int selec_id = items.indexOf(name);
					mCallback.onNeighbourSelected(name,nbs_array.getJSONObject(selec_id).getString("id"),"neighbour");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                // Nothing needs to happen when the user submits the search string
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
                if(adapter!=null)
                	adapter.getFilter().filter(newFilter);
                return true;
            }
        });

		String url_locations = "http://"+Config.SERVER_BASE_URL+"/api/v1/consumers/locations.json";
		json_get_locations = new JSONGetLocations();
		json_get_locations.execute(url_locations);
	}


	private class JSONGetLocations extends AsyncTask<String, String, JSONObject> {

		protected JSONObject doInBackground(String... args) {
			JSONParser jp = new JSONParser();
			JSONObject json = jp.getJSONFromUrl(args[0],context);
			return json;
		}

		protected void onPostExecute(JSONObject json) {
			if(json!=null){
				try {
					nbs_array = json.getJSONArray("nbs");
		            location_array = new String[nbs_array.length()];
		            for (int i = 0; i < nbs_array.length(); i++) {
		            	location_array[i] = nbs_array.getJSONObject(i).getString("name");
		            	items.add(nbs_array.getJSONObject(i).getString("name"));
		    		}
		            // Create an ArrayAdapter using the string array and a default spinner layout
	
		            adapter = new ArrayAdapter<String>(context,
				             android.R.layout.simple_list_item_1, android.R.id.text1, items);
		    		// Apply the adapter to the spinner
		    		frag_listview.setAdapter(adapter);
				} catch (JSONException e) {
					Toast toast = Toast.makeText(context,"Failed to fetch data",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 170);
					toast.show();
				}
			}
		}
	}

	public void onResume() {
		super.onResume();
		search.clearFocus();
	}

	public void onDestroy() {
		super.onDestroy();
		json_get_locations.cancel(true);
	}
}

