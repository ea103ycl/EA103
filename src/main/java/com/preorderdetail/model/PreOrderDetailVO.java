package com.preorderdetail.model;

public class PreOrderDetailVO implements java.io.Serializable{
	private String po_no;
	private String po_prod_no;
	private Integer po_qty;
	private Integer po_price;
	
	public String getPo_no() {
		return po_no;
	}
	public void setPo_no(String po_no) {
		this.po_no = po_no;
	}
	public String getPo_prod_no() {
		return po_prod_no;
	}
	public void setPo_prod_no(String po_prod_no) {
		this.po_prod_no = po_prod_no;
	}
	public Integer getPo_qty() {
		return po_qty;
	}
	public void setPo_qty(Integer po_qty) {
		this.po_qty = po_qty;
	}
	public Integer getPo_price() {
		return po_price;
	}
	public void setPo_price(Integer po_price) {
		this.po_price = po_price;
	}
	
	
}
