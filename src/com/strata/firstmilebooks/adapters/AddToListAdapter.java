package com.strata.firstmilebooks.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.model.ProductList;

public class AddToListAdapter extends BaseAdapter {
	private final Activity context;
	//private final ArrayList<Feed> feed_list;
	private final ArrayList<ProductList> product_list;

	public AddToListAdapter(Activity context, ArrayList<ProductList> product_list) {
		this.context = context;
		this.product_list = product_list;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		/*
		 * LayoutInflater inflater = context.getLayoutInflater(); View rowView =
		 * inflater.inflate(R.layout.list_item, null, true);
		 */
		ProductList pl = product_list.get(position);
		//String type = feed.getType();
		View rowView;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		
		rowView = inflater.inflate(R.layout.add_to_list_item, parent,false);
		TextView prod_desc  = (TextView) rowView.findViewById(R.id.id_product_desc);
		TextView prod_name = (TextView) rowView.findViewById(R.id.id_product_name);
		ImageView prod_image = (ImageView) rowView.findViewById(R.id.id_product_image);
		
		prod_name.setText(pl.getPl_name());
		prod_desc.setText(pl.getPl_desc());

		Picasso.with(context)
			.load(pl.getPl_image())
			.noFade()
			.fit().centerCrop()
			.into(prod_image);

		return rowView;
	}

	@Override
	public int getCount() {
		return product_list.size();
	}

	@Override
	public Object getItem(int position) {
		return product_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}




