package com.deal.model;



public class DealService {
	
	private DealDAO_interface dao;

	public DealService() {
		dao = new DealDAO();
	}
	

	
	
	public DealVO getOneDeal(String mem_id) {
		return dao.findByPrimaryKey(mem_id);
	}
	
	
	public DealVO find_Memid_byPtr(Integer ptr_no) {
		return dao.find_Memid_byPtr(ptr_no);
	}
	

	
	
	
	
	
}


