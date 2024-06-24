package com.emp.model;

import java.util.List;

public interface Emp_Account_DAO_interface {
	
	public Emp_Account_VO insert(Emp_Account_VO emp_Account_VO);
	public void update(Emp_Account_VO emp_Account_VO);
	public void reset(Emp_Account_VO emp_Account_VO);
	public void delete(String emp_no);
	public Emp_Account_VO findByPrimaryKey(String emp_no);
	public List<Emp_Account_VO> getAll();
	
}
	

