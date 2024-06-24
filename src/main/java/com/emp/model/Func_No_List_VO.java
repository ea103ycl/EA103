package com.emp.model;

import java.io.Serializable;

public class Func_No_List_VO implements Serializable{
	
	private String func_id;
	private String func_name;
	
	public Func_No_List_VO() {
		
	}
	
	public Func_No_List_VO(String func_id, String func_name) {
		this.func_id = func_id;
		this.func_name = func_name;
	}
	
	
	public String getFuncId() {
		return func_id;
	}
	
	public void setFuncId(String func_id) {
		this.func_id = func_id;
	}
	
	public String getFuncName() {
		return func_name;
	}

	public void setFuncName(String func_name) {
		this.func_name = func_name;
	}
	
}



