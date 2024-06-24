package com.vote_d.model;

import java.io.Serializable;

public class Vote_DVO implements Serializable{
	private  String mem_id;
	private Integer event_p_no;
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public Integer getEvent_p_no() {
		return event_p_no;
	}
	public void setEvent_p_no(Integer event_p_no) {
		this.event_p_no = event_p_no;
	}
	
}
