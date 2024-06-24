package com.emp.model;

import java.util.List;

public class Available_Func_Service {
	
	private Available_Func_DAO_interface dao;
	
	public Available_Func_Service () {
		dao = new Available_Func_DAO();
	}
	
	public Available_Func_VO addEmpFunc(String emp_no, String func_id) {
		
		Available_Func_VO available_Func_VO = new Available_Func_VO();
		
		available_Func_VO.setEmpNo(emp_no);
		available_Func_VO.setFuncId(func_id);
		dao.insert(available_Func_VO);
		
		return available_Func_VO;		
	}
	
	public Available_Func_VO updateEmpFunc(String emp_no, String func_id) {
		Available_Func_VO available_Func_VO = new Available_Func_VO();
		
		available_Func_VO.setEmpNo(emp_no);
		available_Func_VO.setFuncId(func_id);
		dao.update(available_Func_VO);
		
		return available_Func_VO;
	}
	
	public void deleteEmpFunc(String emp_no) {
		dao.delete(emp_no);
	}
	
	public List<Available_Func_VO> getOneEmpFunc(String emp_no){
		return dao.findByPrimaryKey(emp_no);
	}
		
	public List<Available_Func_VO> getAll(){
		return dao.getAll();
	}
}
	
	
	