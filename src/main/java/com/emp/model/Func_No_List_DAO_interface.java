package com.emp.model;

import java.util.List;

public interface Func_No_List_DAO_interface {

	public void insert(Func_No_List_VO func_No_List_VO);
	public void update(Func_No_List_VO func_No_List_VO);
	public void delete(String func_id);
    public Func_No_List_VO findByPrimaryKey(String func_id);
    public List<Func_No_List_VO> getAll();
    
}
