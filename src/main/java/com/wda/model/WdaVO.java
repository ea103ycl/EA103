package com.wda.model;

import java.sql.Timestamp;

public class WdaVO {

	private Integer wda_id;
	private String mem_id;
	private String bn_code;
	private String bn_accno;
	private String bn_accname;
	private Timestamp wda_date;
	private Integer wda_amount;
	private Integer wda_status;

	public Integer getWda_id() {
		return wda_id;
	}

	public void setWda_id(Integer wda_id) {
		this.wda_id = wda_id;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getBn_code() {
		return bn_code;
	}

	public void setBn_code(String bn_code) {
		this.bn_code = bn_code;
	}

	public String getBn_accno() {
		return bn_accno;
	}

	public void setBn_accno(String bn_accno) {
		this.bn_accno = bn_accno;
	}

	public String getBn_accname() {
		return bn_accname;
	}

	public void setBn_accname(String bn_accname) {
		this.bn_accname = bn_accname;
	}

	public Timestamp getWda_date() {
		return wda_date;
	}

	public void setWda_date(Timestamp wda_date) {
		this.wda_date = wda_date;
	}

	public Integer getWda_amount() {
		return wda_amount;
	}

	public void setWda_amount(Integer wda_amount) {
		this.wda_amount = wda_amount;
	}

	public Integer getWda_status() {
		return wda_status;
	}

	public void setWda_status(Integer wda_status) {
		this.wda_status = wda_status;
	}

}
