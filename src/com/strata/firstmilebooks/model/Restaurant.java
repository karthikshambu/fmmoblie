package com.strata.firstmilebooks.model;

public class Restaurant {
	String image;
	String name;
	String number;
	String nb_name;
	String res_categories;
	String rating;
	String budget;
	int res_id;

	public String getNb_name() {
		return nb_name;
	}
	public void setNb_name(String nb_name) {
		this.nb_name = nb_name;
	}
	public String getRes_categories() {
		return res_categories;
	}
	public void setRes_categories(String res_categories) {
		this.res_categories = res_categories;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}



	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	public int getRes_id() {
		return res_id;
	}
	public void setRes_id(int res_id) {
		this.res_id = res_id;
	}


}
