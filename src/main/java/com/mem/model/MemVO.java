package com.mem.model;

import java.sql.Date;

public class MemVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String mem_id;
	private Integer lv;
	private String m_accno;
	private String m_psw;
	private String m_name;
	private String m_gender;
	private Date m_bday;
	private String m_phone;
	private String m_mobile;
	private Integer m_zip;
	private String m_city;
	private String m_addr;
	private String m_email;
	private String m_word;
	private byte[] m_photo;
	private Integer m_source;
	private String m_joindate;
	private Integer m_active;
	private Integer m_public;
	private Integer m_bancount;
	private Integer balance;

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public Integer getLv() {
		return lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	public String getM_accno() {
		return m_accno;
	}

	public void setM_accno(String m_accno) {
		this.m_accno = m_accno;
	}

	public String getM_psw() {
		return m_psw;
	}

	public void setM_psw(String m_psw) {
		this.m_psw = m_psw;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_gender() {
		return m_gender;
	}

	public void setM_gender(String m_gender) {
		this.m_gender = m_gender;
	}

	public Date getM_bday() {
		return m_bday;
	}

	public void setM_bday(Date m_bday) {
		this.m_bday = m_bday;
	}

	public String getM_phone() {
		return m_phone;
	}

	public void setM_phone(String m_phone) {
		this.m_phone = m_phone;
	}

	public String getM_mobile() {
		return m_mobile;
	}

	public void setM_mobile(String m_mobile) {
		this.m_mobile = m_mobile;
	}

	public Integer getM_zip() {
		return m_zip;
	}

	public void setM_zip(Integer m_zip) {
		this.m_zip = m_zip;
	}

	public String getM_city() {
		return m_city;
	}

	public void setM_city(String m_city) {
		this.m_city = m_city;
	}

	public String getM_addr() {
		return m_addr;
	}

	public void setM_addr(String m_addr) {
		this.m_addr = m_addr;
	}

	public String getM_email() {
		return m_email;
	}

	public void setM_email(String m_email) {
		this.m_email = m_email;
	}

	public String getM_word() {
		return m_word;
	}

	public void setM_word(String m_word) {
		this.m_word = m_word;
	}

	public byte[] getM_photo() {
		return m_photo;
	}

	public void setM_photo(byte[] m_photo) {
		this.m_photo = m_photo;
	}

	public Integer getM_source() {
		return m_source;
	}

	public void setM_source(Integer m_source) {
		this.m_source = m_source;
	}

	public String getM_joindate() {
		return m_joindate;
	}

	public void setM_joindate(String m_joindate) {
		this.m_joindate = m_joindate;
	}

	public Integer getM_active() {
		return m_active;
	}

	public void setM_active(Integer m_active) {
		this.m_active = m_active;
	}

	public Integer getM_public() {
		return m_public;
	}

	public void setM_public(Integer m_public) {
		this.m_public = m_public;
	}

	public Integer getM_bancount() {
		return m_bancount;
	}

	public void setM_bancount(Integer m_bancount) {
		this.m_bancount = m_bancount;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

}
