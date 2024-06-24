package com.deal.model;

import java.sql.Connection;

import com.detail.model.DetailVO;
import com.prod.model.ProdVO;

public interface DealDAO_interface {

	public DealVO findByPrimaryKey(String mem_id);
	public void insert(String or_no, Connection con, String mem_id, Integer tns_src, Integer tns_amount);
	 public void update_balance( Connection con, String mem_id, Integer balance);
	 public DealVO find_Memid_byPtr(Integer ptr_no);
}
