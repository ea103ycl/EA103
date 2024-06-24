package com.prod.model;

public class ProdVO implements java.io.Serializable{
	
	public ProdVO() {//ÁÊª«¨®¥Î
		prod_name = "";
		prod_no = 0;
		prod_qty = 0;
		prod_price = 0;
		
		}

	
	
	

	private Integer  prod_no;
	private Integer  ptr_no;
	private String   ma_no;
	





	private String   prod_name;
	private Integer  prod_price;
	private String   prod_detail;
	private Integer  prod_status;
	private byte[]   prod_pic;
	
	
	private Integer  prod_qty;
	private String  ma_name;
	private String  ptr_nm;
	
	
	
	
	public String getMa_name() {
		return ma_name;
	}


	public void setMa_name(String ma_name) {
		this.ma_name = ma_name;
	}
	
	public String getPtr_nm() {
		return ptr_nm;
	}


	public void setPtr_nm(String ptr_nm) {
		this.ptr_nm = ptr_nm;
	}


	


	public Integer getProd_qty() {
		return prod_qty;
	}


	public void setProd_qty(Integer prod_qty) {
		this.prod_qty = prod_qty;
	}
	
	
	
	

	public Integer getProd_no() {
		return prod_no;
	}


	public void setProd_no(Integer prod_no) {
		this.prod_no = prod_no;
	}


	public Integer getPtr_no() {
		return ptr_no;
	}


	public void setPtr_no(Integer ptr_no) {
		this.ptr_no = ptr_no;
	}


	public String getMa_no() {
		return ma_no;
	}


	public void setMa_no(String ma_no) {
		this.ma_no = ma_no;
	}


	public String getProd_name() {
		return prod_name;
	}


	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}


	public Integer getProd_price() {
		return prod_price;
	}


	public void setProd_price(Integer prod_price) {
		this.prod_price = prod_price;
	}


	public String getProd_detail() {
		return prod_detail;
	}


	public void setProd_detail(String prod_detail) {
		this.prod_detail = prod_detail;
	}


	public Integer getProd_status() {
		return prod_status;
	}


	public void setProd_status(Integer prod_status) {
		this.prod_status = prod_status;
	}


	public byte[] getProd_pic() {
		return prod_pic;
	}


	public void setProd_pic(byte[] prod_pic) {
		this.prod_pic = prod_pic;
	}

	

}
