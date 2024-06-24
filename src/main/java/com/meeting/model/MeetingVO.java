package com.meeting.model;
import java.sql.Date;
import java.sql.Timestamp;

public class MeetingVO implements java.io.Serializable{
	private String mt_no;
	private String mem_id;
	private Integer mt_status;
	private Integer max_num;
	private Integer min_num;
	private String mt_place;
	private Integer ri_fee;
	private String mt_detail;
	private Timestamp mt_start_time;
	private Timestamp mt_end_time;
	private Timestamp mt_time;
	private String mt_id;
	private Integer mt_num;
	private byte[] mt_pic;
	
	public String getMt_no() {
		return mt_no;
	}
	public void setMt_no(String mt_no) {
		this.mt_no = mt_no;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public Integer getMt_status() {
		return mt_status;
	}
	public void setMt_status(Integer mt_status) {
		this.mt_status = mt_status;
	}
	public Integer getMax_num() {
		return max_num;
	}
	public void setMax_num(Integer max_num) {
		this.max_num = max_num;
	}
	public Integer getMin_num() {
		return min_num;
	}
	public void setMin_num(Integer min_num) {
		this.min_num = min_num;
	}
	public String getMt_place() {
		return mt_place;
	}
	public void setMt_place(String mt_place) {
		this.mt_place = mt_place;
	}
	public Integer getRi_fee() {
		return ri_fee;
	}
	public void setRi_fee(Integer ri_fee) {
		this.ri_fee = ri_fee;
	}
	public String getMt_detail() {
		return mt_detail;
	}
	public void setMt_detail(String mt_detail) {
		this.mt_detail = mt_detail;
	}
	public Timestamp getMt_start_time() {
		return mt_start_time;
	}
	public void setMt_start_time(Timestamp mt_start_time) {
		this.mt_start_time = mt_start_time;
	}
	public Timestamp getMt_end_time() {
		return mt_end_time;
	}
	
	public void setMt_end_time(Timestamp mt_end_time) {
		this.mt_end_time = mt_end_time;
	}
	public Timestamp getMt_time() {
		return mt_time;
	}
	public void setMt_time(Timestamp mt_time) {
		this.mt_time = mt_time;
	}
	public String getMt_id() {
		return mt_id;
	}
	public void setMt_id(String mt_id) {
		this.mt_id = mt_id;
	}
	public Integer getMt_num() {
		return mt_num;
	}
	public void setMt_num(Integer mt_num) {
		this.mt_num = mt_num;
	}	
	public byte[] getMt_pic() {
		return mt_pic;
	}
	public void setMt_pic(byte[] mt_pic) {
		this.mt_pic = mt_pic;
	}
	
}
