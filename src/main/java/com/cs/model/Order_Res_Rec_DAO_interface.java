package com.cs.model;

import java.util.List;

public interface Order_Res_Rec_DAO_interface {
	
	public void update(Order_Res_Rec_VO order_Res_Rec_VO);
	public void delete(String svc_no);
    public Order_Res_Rec_VO findByPrimaryKey(String svc_no);
    public List<Order_Res_Rec_VO> getAll();
	
}
