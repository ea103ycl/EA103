package com.wel_record.model;

public class WelRecordVO implements java.io.Serializable {

	private Integer tns_id;
	private String mem_id;
	private Integer tns_src;
	private String order_id;
	private Integer tns_amount;
	private String tns_time;

	public Integer getTns_id() {
		return tns_id;
	}

	public void setTns_id(Integer tns_id) {
		this.tns_id = tns_id;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
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

	public String getTns_time() {
		return tns_time;
	}

	public void setTns_time(String tns_time) {
		this.tns_time = tns_time;
	}

}
