package com.emp.model;

import java.util.List;

public class Func_No_List_Service {
	
	private Func_No_List_DAO_interface dao;
	
	public Func_No_List_Service() {
		dao = new Func_No_List_DAO();
	}
	
	public Func_No_List_VO addFunc(String func_id, String func_name) {
		Func_No_List_VO func_No_List_VO = new Func_No_List_VO();
		
		func_No_List_VO.setFuncId(func_id);
		func_No_List_VO.setFuncName(func_name);
		dao.insert(func_No_List_VO);
		
		return func_No_List_VO;			
	}
	
	public Func_No_List_VO updateFunc(String func_id, String func_name) {
		Func_No_List_VO func_No_List_VO = new Func_No_List_VO();
		
		func_No_List_VO.setFuncId(func_id);
		func_No_List_VO.setFuncName(func_name);
		dao.insert(func_No_List_VO);
		
		return func_No_List_VO;			
	}
	
	public void deleteFunc(String func_id) {
		dao.delete(func_id);
	}
	
	public Func_No_List_VO getOneFunc(String func_id) {
		return dao.findByPrimaryKey(func_id);
	}
	
	public List<Func_No_List_VO> getAll(){
		return dao.getAll();
	}
}	

