package com.material.model;

import java.io.Serializable;

public class Material_Data_VO implements Serializable{
	
	private String ma_no;
	private String ma_ty_no;
	private String ma_name;
	private Integer ma_price;
	private byte[] ma_photo;
	private Integer ma_status;
	
	public Material_Data_VO() {
		
	}

	public Material_Data_VO(String ma_no, String ma_ty_no, String ma_name, Integer ma_price, byte[] ma_photo,
			Integer ma_status) {
		this.ma_no = ma_no;
		this.ma_ty_no = ma_ty_no;
		this.ma_name = ma_name;
		this.ma_price = ma_price;
		this.ma_photo = ma_photo;
		this.ma_status = ma_status;
	}

	public String getMaNo() {
		return ma_no;
	}

	public void setMaNo(String ma_no) {
		this.ma_no = ma_no;
	}

	public String getMaTyNo() {
		return ma_ty_no;
	}

	public void setMaTyNo(String ma_ty_no) {
		this.ma_ty_no = ma_ty_no;
	}

	public String getMaName() {
		return ma_name;
	}

	public void setMaName(String ma_name) {
		this.ma_name = ma_name;
	}

	public Integer getMaPrice() {
		return ma_price;
	}

	public void setMaPrice(Integer ma_price) {
		this.ma_price = ma_price;
	}

	public byte[] getMaPhoto() {
		return ma_photo;
	}

	public void setMaPhoto(byte[] ma_photo) {
		this.ma_photo = ma_photo;
	}

	public Integer getMaStatus() {
		return ma_status;
	}

	public void setMaStatus(Integer ma_status) {
		this.ma_status = ma_status;
	}			
}



