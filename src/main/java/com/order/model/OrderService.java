package com.order.model;

import java.util.List;

import com.detail.model.DetailVO;
import com.prod.model.ProdVO;

public class OrderService {
	
	private OrderDAO_interface dao;

	public OrderService() {
		dao = new OrderDAO();
	}
	
	
	public OrderVO addOrder(String or_no, String mem_id, String or_name, String or_phone,
			Integer or_zip, String or_addr, String or_note, Integer or_total, List<DetailVO> detailList) {
		
		OrderVO orderVO = new OrderVO();
		
		
		orderVO.setMem_id(mem_id);
		orderVO.setOr_name(or_name);
		orderVO.setOr_phone(or_phone);
		orderVO.setOr_zip(or_zip);
		orderVO.setOr_addr(or_addr);
		orderVO.setOr_note(or_note);
		orderVO.setOr_total(or_total);
		
		
		String ord_next_no = dao.insert(orderVO,detailList);
		orderVO.setOr_no(ord_next_no);
		
			return orderVO;
			
		}
	
	
	public OrderVO getOneOrder(String or_no) {
		return dao.findByPrimaryKey(or_no);
	}
	

	public List<OrderVO> getAll() {
		return dao.getAll();
	}
	
	
	
	public OrderVO updateProd(Integer or_status) {

		OrderVO orderVO = new OrderVO();
		
		orderVO.setOr_status(or_status);
		dao.update(orderVO);
		
		return orderVO;
	}
	
	public List<OrderVO> orderSearch(String mem_id) {
		return dao.orderSearch(mem_id);
	}
	
	
	public OrderVO ChangeStatus(String or_no,  Integer or_status) {

		OrderVO orderVO = new OrderVO();
		
		orderVO.setOr_no(or_no);
		orderVO.setOr_status(or_status);
		dao.changeStatus(orderVO);
		return orderVO;
	}
	
	public List<OrderVO> getOrderByStauts(Integer or_status) {
		return dao.getOrderByStauts(or_status);
	}
	
	public List<OrderVO> getOrderByStauts_Buyer(Integer or_status,String mem_id) {
		return dao.getOrderByStauts_Buyer(or_status,mem_id);
	}
	
}


