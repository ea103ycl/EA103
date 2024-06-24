package com.event_tag.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Event_TagVO implements Serializable {
	private Integer event_tag_no;
	private String mem_id;
	private String event_no;
	private Timestamp event_tag_time;
	private String event_tag_name;
	public Integer getEvent_tag_no() {
		return event_tag_no;
	}
	public void setEvent_tag_no(Integer event_tag_no) {
		this.event_tag_no = event_tag_no;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getEvent_no() {
		return event_no;
	}
	public void setEvent_no(String event_no) {
		this.event_no = event_no;
	}
	public Timestamp getEvent_tag_time() {
		return event_tag_time;
	}
	public void setEvent_tag_time(Timestamp event_tag_time) {
		this.event_tag_time = event_tag_time;
	}
	public String getEvent_tag_name() {
		return event_tag_name;
	}
	public void setEvent_tag_name(String event_tag_name) {
		this.event_tag_name = event_tag_name;
	}
	
	
	
	
}
