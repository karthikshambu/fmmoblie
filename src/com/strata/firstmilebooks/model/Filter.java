package com.strata.firstmilebooks.model;

import java.io.Serializable;

public class Filter implements Serializable {
	String name;
	String id;
	String type;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHash() {
		// TODO Auto-generated method stub
		String value_hash = "";
		value_hash = "{:type =>'"+type+"', :id => '"+ id +"'}";
		return value_hash;
	}
}
