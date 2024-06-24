package com.deal.model;

import java.sql.Date;
import java.sql.Timestamp;



public class DealVO {
	String mem_id;   
	Integer tns_id;
	Integer tns_src;
	String order_id;
	Integer tns_amount;
	Timestamp tns_time;
	
	
	Integer balance;
	
	
	
	
	
	
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public Integer getTns_id() {
		return tns_id;
	}
	public void setTns_id(Integer tns_id) {
		this.tns_id = tns_id;
	}
	public Integer getTns_src() {
		return tns_src;
	}
	public void setTns_src(Integer tns_src) {
		this.tns_src = tns_src;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public Integer getTns_amount() {
		return tns_amount;
	}
	public void setTns_amount(Integer tns_amount) {
		this.tns_amount = tns_amount;
	}
	public Timestamp getTns_time() {
		return tns_time;
	}
	public void setTns_time(Timestamp tns_time) {
		this.tns_time = tns_time;
	}
	
}
