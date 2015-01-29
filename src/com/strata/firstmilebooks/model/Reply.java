package com.strata.firstmilebooks.model;

public class Reply {

	String content;
	String type;
	String time;
	int res_id;
	int consumer_id;
	int offer_id;
	int reply_id;
	int feed_id;

	Consumer consumer;
	Offer offer;
	Restaurant restaurant;



	public Reply(){
		consumer = new Consumer();
		restaurant = new Restaurant();
		offer = new Offer();
	}

	public int getFeed_id() {
		return feed_id;
	}



	public int setFeed_id(int feed_id) {
		this.feed_id = feed_id;
		return feed_id;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getType() {
		return type;
	}



	public String setType(String type) {
		this.type = type;
		return type;
	}


	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public int getRes_id() {
		return res_id;
	}



	public void setRes_id(int res_id) {
		this.res_id = res_id;
	}



	public int getConsumer_id() {
		return consumer_id;
	}



	public void setConsumer_id(int consumer_id) {
		this.consumer_id = consumer_id;
	}



	public int getOffer_id() {
		return offer_id;
	}



	public void setOffer_id(int offer_id) {
		this.offer_id = offer_id;
	}



	public Consumer getConsumer() {
		return consumer;
	}



	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}



	public Offer getOffer() {
		return offer;
	}



	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public int getReply_id() {
		return reply_id;
	}

	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}


}
