package com.ad.model;

import java.io.Serializable;
import java.sql.Date;

public class Advertisement_VO implements Serializable{
	
	private String ad_no;
	private String ad_name;
	private String ad_link;
	private Date ad_upload_time;
	private Date ad_remove_time;
	private byte[] ad_photo;
	
	public Advertisement_VO() {

	}
	
	public Advertisement_VO(String ad_no, String ad_name, String ad_link, Date ad_upload_time, Date ad_remove_time,
			byte[] ad_photo) {
		super();
		this.ad_no = ad_no;
		this.ad_name = ad_name;
		this.ad_link = ad_link;
		this.ad_upload_time = ad_upload_time;
		this.ad_remove_time = ad_remove_time;
		this.ad_photo = ad_photo;
	}
	public String getAdNo() {
		return ad_no;
	}
	public void setAdNo(String ad_no) {
		this.ad_no = ad_no;
	}
	public String getAdName() {
		return ad_name;
	}
	public void setAdName(String ad_name) {
		this.ad_name = ad_name;
	}
	public String getAdLink() {
		return ad_link;
	}
	public void setAdLink(String ad_link) {
		this.ad_link = ad_link;
	}
	public Date getAdUploadTime() {
		return ad_upload_time;
	}
	public void setAdUploadTime(Date ad_upload_time) {
		this.ad_upload_time = ad_upload_time;
	}
	public Date getAdRemoveTime() {
		return ad_remove_time;
	}
	public void setAdRemoveTime(Date ad_remove_time) {
		this.ad_remove_time = ad_remove_time;
	}
	public byte[] getAdPhoto() {
		return ad_photo;
	}
	public void setAdPhoto(byte[] ad_photo) {
		this.ad_photo = ad_photo;
	}
	
}
