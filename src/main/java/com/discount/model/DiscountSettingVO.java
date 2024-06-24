package com.discount.model;

public class DiscountSettingVO implements java.io.Serializable{
	private String dis_no;
	private String po_prod_no;
	private Integer reach_number;
	private Integer reach_discount;
	
	private String event_no;
	
	public String getDis_no() {
		return dis_no;
	}
	public void setDis_no(String dis_no) {
		this.dis_no = dis_no;
	}
	public String getPo_prod_no() {
		return po_prod_no;
	}
	public void setPo_prod_no(String po_prod_no) {
		this.po_prod_no = po_prod_no;
	}
	public Integer getReach_number() {
		return reach_number;
	}
	public void setReach_number(Integer reach_number) {
		this.reach_number = reach_number;
	}
	public Integer getReach_discount() {
		return reach_discount;
	}
	public void setReach_discount(Integer reach_discount) {
		this.reach_discount = reach_discount;
	}
	
	public String getEvent_no() {
		return event_no;
	}
	public void setEvent_no(String event_no) {
		this.event_no = event_no;
	}
	
}
