package com.eval.model;

import java.sql.Timestamp;

public class EvalVO {
	
	private Integer   eval_no;
	private Integer   prod_no;
	private String    or_no;
	private String    mem_id;
	private Integer   eval_rang;
	private String   eval_review;
	private Timestamp eval_time;
	private byte[]   m_photo;
	
	
	public EvalVO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public byte[] getM_photo() {
		return m_photo;
	}


	public void setM_photo(byte[] m_photo) {
		this.m_photo = m_photo;
	}


	public Integer getEval_no() {
		return eval_no;
	}


	public void setEval_no(Integer eval_no) {
		this.eval_no = eval_no;
	}


	public Integer getProd_no() {
		return prod_no;
	}


	public void setProd_no(Integer prod_no) {
		this.prod_no = prod_no;
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


	public Integer getEval_rang() {
		return eval_rang;
	}


	public void setEval_rang(Integer eval_rang) {
		this.eval_rang = eval_rang;
	}


	public String getEval_review() {
		return eval_review;
	}


	public void setEval_review(String eval_review) {
		this.eval_review = eval_review;
	}


	


	public Timestamp getEval_time() {
		return eval_time;
	}


	public void setEval_time(Timestamp eval_time) {
		this.eval_time = eval_time;
	}

	
	
	

	


	

	
	
	
}
