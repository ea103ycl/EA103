package com.preproduct.model;
import java.sql.Timestamp;

public class PreProductVO implements java.io.Serializable{
	
	private String po_prod_no;
	private Integer event_p_no;
	private String ma_no;
	private Timestamp po_start;
	private Timestamp po_end;
	private Integer po_price;
	private String po_detail;
	
	private Integer po_qty;
	
	public String getPo_prod_no() {
		return po_prod_no;
	}
	public void setPo_prod_no(String po_prod_no) {
		this.po_prod_no = po_prod_no;
	}
	public Integer getEvent_p_no() {
		return event_p_no;
	}
	public void setEvent_p_no(Integer event_p_no) {
		this.event_p_no = event_p_no;
	}
	public String getMa_no() {
		return ma_no;
	}
	public void setMa_no(String ma_no) {
		this.ma_no = ma_no;
	}
	public Timestamp getPo_start() {
		return po_start;
	}
	public void setPo_start(Timestamp po_start) {
		this.po_start = po_start;
	}
	public Timestamp getPo_end() {
		return po_end;
	}
	public void setPo_end(Timestamp po_end) {
		this.po_end = po_end;
	}
	public Integer getPo_price() {
		return po_price;
	}
	public void setPo_price(Integer po_price) {
		this.po_price = po_price;
	}
	public String getPo_detail() {
		return po_detail;
	}
	public void setPo_detail(String po_detail) {
		this.po_detail = po_detail;
	}
	
	public Integer getPo_qty() {
		return po_qty;
	}
	public void setPo_qty(Integer po_qty) {
		this.po_qty = po_qty;
	}
	
	
}
