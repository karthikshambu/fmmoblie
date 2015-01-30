package com.strata.firstmilebooks.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.model.ProductList;

public class ProductCatalogAdapter extends BaseAdapter {
	private final Activity context;
	//private final ArrayList<Feed> feed_list;
	private final ArrayList<ProductList> product_list;

	public ProductCatalogAdapter(Activity context, ArrayList<ProductList> product_list) {
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
		
		rowView = inflater.inflate(R.layout.product_catalog_item, parent,false);
		TextView prod_desc  = (TextView) rowView.findViewById(R.id.id_product_desc);
		TextView prod_name = (TextView) rowView.findViewById(R.id.id_product_name);
		ImageView prod_image = (ImageView) rowView.findViewById(R.id.id_product_image);
		TextView btn_add = (TextView) rowView.findViewById(R.id.btn_add);
		
		prod_name.setText(pl.getPl_name());
		prod_desc.setText(pl.getPl_desc());
		
		//showing alert dialog box
		final String[] show_list = {"wishlist","Buyerlist >","  Dan Brown Collection","  Richard Feynmann Special","Sellerlist >","  Shiva Trililogy"};
		btn_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			   ContextThemeWrapper cw = new ContextThemeWrapper( context, R.style.AlertDialogTheme );
		       AlertDialog.Builder builder = new AlertDialog.Builder(cw);
		       builder.setTitle("Select the account that you want to login")
		                .setItems(show_list, new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int which) {
		                	   Toast.makeText(context, "Succefully Added to your List", Toast.LENGTH_SHORT).show();
		                	   context.finish();
		                }
		         });
		      builder.setIcon(R.drawable.ic_launcher);
		      builder.show();
			}
		});
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





