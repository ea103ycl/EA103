package com.emp.model;

import java.util.List;

public interface Available_Func_DAO_interface {
	
	public void insert(Available_Func_VO available_Func_VO);
	public void update(Available_Func_VO available_Func_VO);
	public void delete(String emp_no);
	public List<Available_Func_VO> findByPrimaryKey(String emp_no);
	public List<Available_Func_VO> getAll();
	
}


