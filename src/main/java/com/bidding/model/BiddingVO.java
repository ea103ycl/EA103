package com.bidding.model;

import java.sql.*;

public class BiddingVO {
	private String bdNo;
	private String memId;
	private Integer bdProdNo;
	private Integer bdPrice;
	private Timestamp bdDateStr;
	private Timestamp bdDateEnd;
	private Integer bdStatus;
	private Integer bdOrdStatus;
	private Integer pmtStatus;
	private Integer bdZip;
	private String bdName;
	private String bdPhone;
	private String bdAddr;
	
	public String getBdNo() {
		return bdNo;
	}
	public void setBdNo(String bdNo) {
		this.bdNo = bdNo;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public Integer getBdProdNo() {
		return bdProdNo;
	}
	public void setBdProdNo(Integer bdProdNo) {
		this.bdProdNo = bdProdNo;
	}
	public Integer getBdPrice() {
		return bdPrice;
	}
	public void setBdPrice(Integer bdPrice) {
		this.bdPrice = bdPrice;
	}
	public Timestamp getBdDateStr() {
		return bdDateStr;
	}
	public void setBdDateStr(Timestamp bdDateStr) {
		this.bdDateStr = bdDateStr;
	}
	public Timestamp getBdDateEnd() {
		return bdDateEnd;
	}
	public void setBdDateEnd(Timestamp bdDateEnd) {
		this.bdDateEnd = bdDateEnd;
	}
	public Integer getBdStatus() {
		return bdStatus;
	}
	public void setBdStatus(Integer bdStatus) {
		this.bdStatus = bdStatus;
	}
	public Integer getBdOrdStatus() {
		return bdOrdStatus;
	}
	public void setBdOrdStatus(Integer bdOrdStatus) {
		this.bdOrdStatus = bdOrdStatus;
	}
	public Integer getPmtStatus() {
		return pmtStatus;
	}
	public void setPmtStatus(Integer pmtStatus) {
		this.pmtStatus = pmtStatus;
	}
	public Integer getBdZip() {
		return bdZip;
	}
	public void setBdZip(Integer bdZip) {
		this.bdZip = bdZip;
	}
	public String getBdName() {
		return bdName;
	}
	public void setBdName(String bdName) {
		this.bdName = bdName;
	}
	public String getBdPhone() {
		return bdPhone;
	}
	public void setBdPhone(String bdPhone) {
		this.bdPhone = bdPhone;
	}
	public String getBdAddr() {
		return bdAddr;
	}
	public void setBdAddr(String bdAddr) {
		this.bdAddr = bdAddr;
	}
	
}
