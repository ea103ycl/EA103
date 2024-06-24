package com.material.model;

import java.io.Serializable;

public class Material_Type_VO implements Serializable{
	
	private String ma_ty_no;
	private String ma_ty_nam;
	
	public Material_Type_VO() {
		
	}
	
	public Material_Type_VO(String ma_ty_no, String ma_ty_nam) {
		this.ma_ty_no = ma_ty_no;
		this.ma_ty_nam = ma_ty_nam;
	}
	
	public String getMaTyNo() {
		return ma_ty_no;
	}
	
	public void setMaTyNo(String ma_ty_no) {
		this.ma_ty_no = ma_ty_no;
	}
	
	public String getMaTyNam() {
		return ma_ty_nam;
	}
	
	public void setMaTyNam(String ma_ty_nam) {
		this.ma_ty_nam = ma_ty_nam;
	}
	
}

