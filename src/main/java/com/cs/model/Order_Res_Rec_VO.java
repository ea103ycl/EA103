package com.cs.model;

import java.io.Serializable;

public class Order_Res_Rec_VO implements Serializable{
	
	private String svc_no;
	private String emp_no;
	private String mem_id;
	private Integer order_id;
	private Integer tns_src;
	private String svc_text;

	public Order_Res_Rec_VO() {
		
	}
	
	public Order_Res_Rec_VO(String svc_no, String emp_no, String mem_id, Integer order_id, Integer tns_src, String svc_text) {
		this.svc_no = svc_no;
		this.emp_no = emp_no;
		this.mem_id = mem_id;
		this.order_id = order_id;
		this.tns_src = tns_src;
		this.svc_text = svc_text;
	}
	
	public String getSvcNo() {
		return svc_no;
	}
	
	public void setSvcNo(String svc_no) {
		this.svc_no = svc_no;
	}

	public String getEmpNo() {
		return emp_no;
	}

	public void setEmpNo(String emp_no) {
		this.emp_no = emp_no;
	}

	public String getMemId() {
		return mem_id;
	}

	public void setMemId(String mem_id) {
		this.mem_id = mem_id;
	}

	public Integer getOrderId() {
		return order_id;
	}

	public void setOrderId(Integer order_id) {
		this.order_id = order_id;
	}

	public Integer getTnsSrc() {
		return tns_src;
	}

	public void setTnsSrc(Integer tns_src) {
		this.tns_src = tns_src;
	}

	public String getSvcText() {
		return svc_text;
	}

	public void setSvcText(String svc_text) {
		this.svc_text = svc_text;
	}			
}
