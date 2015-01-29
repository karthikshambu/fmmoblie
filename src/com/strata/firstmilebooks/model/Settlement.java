package com.strata.firstmilebooks.model;

public class Settlement {
	int consumer_id;
	float receivable_amount;
	float payable_amount;
	Consumer consumer;
	public int getConsumer_id() {
		return consumer_id;
	}
	public void setConsumer_id(int consumer_id) {
		this.consumer_id = consumer_id;
	}
	public float getReceivable_amount() {
		return receivable_amount;
	}
	public void setReceivable_amount(float receivable_amount) {
		this.receivable_amount = receivable_amount;
	}
	public float getPayable_amount() {
		return payable_amount;
	}
	public void setPayable_amount(float payable_amount) {
		this.payable_amount = payable_amount;
	}
	public Consumer getConsumer() {
		return consumer;
	}
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
}
