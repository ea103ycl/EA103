package com.event_p.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;

public class Event_PVO implements Serializable {
	private Integer event_p_no;
	private String mem_id;
	private String event_no;
	private String event_p_name;
	private Timestamp event_p_date;
	private Integer event_vote_num;
	private Integer vote_rank;
	private Integer event_p_stat;
	private byte[] event_p_img;
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
	public String getEvent_no() {
		return event_no;
	}
	public void setEvent_no(String event_no) {
		this.event_no = event_no;
	}
	public String getEvent_p_name() {
		return event_p_name;
	}
	public void setEvent_p_name(String event_p_name) {
		this.event_p_name = event_p_name;
	}
	public Timestamp getEvent_p_date() {
		return event_p_date;
	}
	public void setEvent_p_date(Timestamp event_p_date) {
		this.event_p_date = event_p_date;
	}
	public Integer getEvent_vote_num() {
		return event_vote_num;
	}
	public void setEvent_vote_num(Integer event_vote_num) {
		this.event_vote_num = event_vote_num;
	}

	public Integer getVote_rank() {
		return vote_rank;
	}
	public void setVote_rank(Integer vote_rank) {
		this.vote_rank = vote_rank;
	}
	public Integer getEvent_p_stat() {
		return event_p_stat;
	}
	public void setEvent_p_stat(Integer event_p_stat) {
		this.event_p_stat = event_p_stat;
	}
	public byte[] getEvent_p_img() {
		return event_p_img;
	}
	public void setEvent_p_img(byte[] event_p_img) {
		this.event_p_img = event_p_img;
	}
	
}
