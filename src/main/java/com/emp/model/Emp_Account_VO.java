package com.emp.model;

import java.io.Serializable;
import java.sql.Date;

public class Emp_Account_VO implements Serializable{

	private String emp_no;
	private String emp_name;
	private String emp_pwd;
	private String emp_pos;
	private String emp_mail;
	private byte[] emp_photo;
	private Integer emp_status;
	
	public Emp_Account_VO() {
		
	}
	
	public Emp_Account_VO(String emp_no, String emp_name, String emp_pwd, String emp_pos, String emp_mail, byte[] emp_photo, Integer emp_status) {
		this.emp_no = emp_no;
		this.emp_name = emp_name;
		this.emp_pwd = emp_pwd;
		this.emp_pos = emp_pos;
		this.emp_mail = emp_mail;
		this.emp_photo = emp_photo;
		this.emp_status = emp_status;
	}
	
	public String getEmpNo() {
		return emp_no;
	}
	
	public void setEmpNo(String emp_no) {
		this.emp_no = emp_no;
	}
	
	public String getEmpName() {
		return emp_name;
	}
	
	public void setEmpName(String emp_name) {
		this.emp_name = emp_name;
	}
	
	public String getEmpPwd() {
		return emp_pwd;
	}
	
	public void setEmpPwd(String emp_pwd) {
		this.emp_pwd = emp_pwd;
	}
	
	public String getEmpPos() {
		return emp_pos;
	}
	
	public void setEmpPos(String emp_pos) {
		this.emp_pos = emp_pos;
	}
	
	public String getEmpMail() {
		return emp_mail;
	}
	
	public void setEmpMail(String emp_mail) {
		this.emp_mail = emp_mail;
	}
	
	public byte[] getEmpPhoto() {
		return emp_photo;
	}
	
	public void setEmpPhoto(byte[] emp_photo) {
		this.emp_photo = emp_photo;
	}
	
	public Integer getEmpStatus() {
		return emp_status;
	}
	
	public void setEmpStatus(Integer emp_status) {
		this.emp_status = emp_status;
	}	
}


