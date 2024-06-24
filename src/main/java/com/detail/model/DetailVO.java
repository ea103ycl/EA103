package com.detail.model;

import java.sql.Connection;

public class DetailVO {
	
	private String    or_no;
	private Integer   prod_no;
	private Integer   or_qty;
	private Integer   or_p_price;
	
	
	public DetailVO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public  String getOr_no() {
		return or_no;
	}


	public void setOr_no(String  or_no) {
		this.or_no = or_no;
	}


	public Integer getProd_no() {
		return prod_no;
	}


	public void setProd_no(Integer prod_no) {
		this.prod_no = prod_no;
	}


	public Integer getOr_qty() {
		return or_qty;
	}


	public void setOr_qty(Integer or_qty) {
		this.or_qty = or_qty;
	}


	public Integer getOr_p_price() {
		return or_p_price;
	}


	public void setOr_p_price(Integer or_p_price) {
		this.or_p_price = or_p_price;
	}


	
	
	
}
