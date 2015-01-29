package com.strata.firstmilebooks.model;

import java.util.ArrayList;

public class Feed {
	String dining_date;
	String dining_time;
	String created;
	String subject;
	String Type;
	int publisher_id;
	int feed_id;
	int neighborhood_id;
	String neighborhood_name;
	int last_updated_at;
	int visited_res_id;
	String header;
	String body;
	String image;
	String content_type;
	String payableAmount;
	String recivableAmount;


	public String getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(String payableAmount) {
		this.payableAmount = payableAmount;
	}

	public String getRecivableAmount() {
		return recivableAmount;
	}

	public void setRecivableAmount(String recivableAmount) {
		this.recivableAmount = recivableAmount;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public int getVisited_res_id() {
		return visited_res_id;
	}

	public void setVisited_res_id(int visited_res_id) {
		this.visited_res_id = visited_res_id;
	}

	String run_type;

	public String getRun_type() {
		return run_type;
	}

	public void setRun_type(String run_type) {
		this.run_type = run_type;
	}

	Consumer publisher;
	ArrayList<Consumer> participants;
	ArrayList<Reply> replies;


	public int getLast_updated_at() {
		return last_updated_at;
	}

	public void setLast_updated_at(int last_updated_at) {
		this.last_updated_at = last_updated_at;
	}

	public ArrayList<Reply> getReplies() {
		return replies;
	}

	public void setReplies(ArrayList<Reply> replies) {
		this.replies = replies;
	}
	int last_reply_id;
	int participants_count;
	String budget;
	String state;
	Boolean opened;
	int newMessages;

	public int getNewMessages() {
		return newMessages;
	}

	public void setNewMessages(int newMessages) {
		this.newMessages = newMessages;
	}

	public Boolean getOpened() {
		return opened;
	}

	public void setOpened(Boolean opened) {
		this.opened = opened;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getParticipants_count() {
		return participants_count;
	}

	public void setParticipants_count(int participants_count) {
		this.participants_count = participants_count;
	}

	public int getLast_reply_id() {
		return last_reply_id;
	}

	public void setLast_reply_id(int last_reply_id) {
		this.last_reply_id = last_reply_id;
	}

	public Feed(){
		publisher = new Consumer();
	}

	public ArrayList<Consumer> getParticipants() {
		return participants;
	}
	public void setParticipants(ArrayList<Consumer> participants) {
		this.participants = participants;
	}
	public Consumer getPublisher() {
		return publisher;
	}
	public void setPublisher(Consumer publisher) {
		this.publisher = publisher;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getDining_date() {
		return dining_date;
	}
	public void setDining_date(String dining_date) {
		this.dining_date = dining_date;
	}
	public String getDining_time() {
		return dining_time;
	}
	public void setDining_time(String dining_time) {
		this.dining_time = dining_time;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getPublisher_id() {
		return publisher_id;
	}
	public void setPublisher_id(int publisher_id) {
		this.publisher_id = publisher_id;
	}
	public int getFeed_id() {
		return feed_id;
	}
	public void setFeed_id(int feed_id) {
		this.feed_id = feed_id;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}
	public String getBudget(){
		return budget;
	}

	public int getNeighborhood_id() {
		return neighborhood_id;
	}

	public void setNeighborhood_id(int neighborhood_id) {
		this.neighborhood_id = neighborhood_id;
	}

	public String getNeighborhood_name() {
		return neighborhood_name;
	}

	public void setNeighborhood_name(String neighborhood_name) {
		this.neighborhood_name = neighborhood_name;
	}

}
