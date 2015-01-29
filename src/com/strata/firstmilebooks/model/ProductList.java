package com.strata.firstmilebooks.model;


public class ProductList {
	private int pl_id;
	private String pl_name;
	private String pl_desc;
	private String pl_image;
	
	public String getPl_image() {
		return pl_image;
	}
	public void setPl_image(String pl_image) {
		this.pl_image = pl_image;
	}
	public int getPl_id() {
		return pl_id;
	}
	public void setPl_id(int i) {
		this.pl_id = i;
	}
	public String getPl_name() {
		return pl_name;
	}
	public void setPl_name(String pl_name) {
		this.pl_name = pl_name;
	}
	public String getPl_desc() {
		return pl_desc;
	}
	public void setPl_desc(String pl_desc) {
		this.pl_desc = pl_desc;
	}
}
