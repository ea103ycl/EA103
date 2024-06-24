package com.event.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class EventVO implements Serializable {
private	String		event_no;
private String		event_name;
private	Timestamp 	event_start;
private	Timestamp	event_end;
private	Timestamp	event_ul_start;
private	Timestamp	event_ul_end;
private	Timestamp	event_vote_start;
private	Timestamp	event_vote_end;
private	Integer		event_stat;

public String getEvent_no() {
	return event_no;
}
public void setEvent_no(String event_no) {
	this.event_no = event_no;
}
public String getEvent_name() {
	return event_name;
}
public void setEvent_name(String event_name) {
	this.event_name = event_name;
}
public Timestamp getEvent_start() {
	return event_start;
}
public void setEvent_start(Timestamp event_start) {
	this.event_start = event_start;
}
public Timestamp getEvent_end() {
	return event_end;
}
public void setEvent_end(Timestamp event_end) {
	this.event_end = event_end;
}
public Timestamp getEvent_ul_start() {
	return event_ul_start;
}
public void setEvent_ul_start(Timestamp event_ul_start) {
	this.event_ul_start = event_ul_start;
}
public Timestamp getEvent_ul_end() {
	return event_ul_end;
}
public void setEvent_ul_end(Timestamp event_ul_end) {
	this.event_ul_end = event_ul_end;
}
public Timestamp getEvent_vote_start() {
	return event_vote_start;
}
public void setEvent_vote_start(Timestamp event_vote_start) {
	this.event_vote_start = event_vote_start;
}
public Timestamp getEvent_vote_end() {
	return event_vote_end;
}
public void setEvent_vote_end(Timestamp event_vote_end) {
	this.event_vote_end = event_vote_end;
}
public Integer getEvent_stat() {
	return event_stat;
}
public void setEvent_stat(Integer event_stat) {
	this.event_stat = event_stat;
}

}
