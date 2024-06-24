package com.order.model;

import java.util.List;

import com.detail.model.DetailVO;
import com.prod.model.ProdVO;


public interface OrderDAO_interface {

	public String insert(OrderVO orderVO, List<DetailVO> detailList);
	
	public void update(OrderVO orderVO);

	public OrderVO findByPrimaryKey(String order_no);

	public List<OrderVO> getAll();
	
	public List<OrderVO> orderSearch(String mem_id);
	
	public List<OrderVO> getOrderByStauts(Integer or_stauts);
	
	public void changeStatus(OrderVO orderVO);
	
	public List<OrderVO> getOrderByStauts_Buyer(Integer or_stauts,String mem_id);
	
}
