package com.order.model;

import java.sql.Timestamp;

public class OrderVO {
	private String    or_no;
	private String   mem_id;
	private String    or_name;
	private String    or_phone;
	private Integer   or_zip;
	private String    or_addr;
	private String    or_note;
	private Integer   or_total;
	private Integer   or_status;
	private Timestamp or_time;
	
	
	
	public OrderVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOr_no() {
		return or_no;
	}
	public void setOr_no(String or_no) {
		this.or_no = or_no;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getOr_name() {
		return or_name;
	}
	public void setOr_name(String or_name) {
		this.or_name = or_name;
	}
	public String getOr_phone() {
		return or_phone;
	}
	public void setOr_phone(String or_phone) {
		this.or_phone = or_phone;
	}
	public Integer getOr_zip() {
		return or_zip;
	}
	public void setOr_zip(Integer or_zip) {
		this.or_zip = or_zip;
	}
	public String getOr_addr() {
		return or_addr;
	}
	public void setOr_addr(String or_addr) {
		this.or_addr = or_addr;
	}
	public String getOr_note() {
		return or_note;
	}
	public void setOr_note(String or_note) {
		this.or_note = or_note;
	}
	public Integer getOr_total() {
		return or_total;
	}
	public void setOr_total(Integer or_total) {
		this.or_total = or_total;
	}
	public Integer getOr_status() {
		return or_status;
	}
	public void setOr_status(Integer or_status) {
		this.or_status = or_status;
	}
	public Timestamp getOr_time() {
		return or_time;
	}
	public void setOr_time(Timestamp or_time) {
		this.or_time = or_time;
	}
	
	
}
