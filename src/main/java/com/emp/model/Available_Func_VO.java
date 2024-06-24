package com.emp.model;

import java.io.Serializable;

public class Available_Func_VO implements Serializable{
	
	private String emp_no;
	private String func_id;
	
	public Available_Func_VO() {

	}

	public Available_Func_VO(String emp_no, String func_id) {
		this.emp_no = emp_no;
		this.func_id = func_id;
	}

	public String getEmpNo() {
		return emp_no;
	}

	public void setEmpNo(String emp_no) {
		this.emp_no = emp_no;
	}

	public String getFuncId() {
		return func_id;
	}

	public void setFuncId(String func_id) {
		this.func_id = func_id;
	}
	
}





