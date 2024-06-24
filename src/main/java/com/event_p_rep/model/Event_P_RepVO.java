package com.event_p_rep.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Event_P_RepVO implements Serializable{
	private Integer event_p_no;
	private String mem_id;
	private Timestamp rep_time;
	private String rep_reason;
	private Integer rep_stat;
	public Integer getEvent_p_no() {
		return event_p_no;
	}
	public void setEvent_p_no(Integer event_p_no) {
		this.event_p_no = event_p_no;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public Timestamp getRep_time() {
		return rep_time;
	}
	public void setRep_time(Timestamp rep_time) {
		this.rep_time = rep_time;
	}
	public String getRep_reason() {
		return rep_reason;
	}
	public void setRep_reason(String rep_reason) {
		this.rep_reason = rep_reason;
	}
	public Integer getRep_stat() {
		return rep_stat;
	}
	public void setRep_stat(Integer rep_stat) {
		this.rep_stat = rep_stat;
	}
	
	
}
