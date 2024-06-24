package com.preorder.model;

import java.util.List;

import com.preorder.model.PreOrderVO;

import com.preorderdetail.model.PreOrderDetailDAO_interface;
import com.preorderdetail.model.PreOrderDetailVO;

public class PreOrderService {
	private PreOrderDAO_interface dao;
	public PreOrderService() {
		dao = new PreOrderDAO();
	}
	public PreOrderVO addPreOrderWithDetail(String mem_id,Integer po_zip,String po_name,
			String po_phone,String po_addr,Integer po_status,Integer po_total,String po_note,List<PreOrderDetailVO>list) {
		
		PreOrderVO preorderVO = new PreOrderVO();
		preorderVO.setMem_id(mem_id);
		preorderVO.setPo_zip(po_zip);
		preorderVO.setPo_name(po_name);
		preorderVO.setPo_phone(po_phone);
		preorderVO.setPo_addr(po_addr);
		preorderVO.setPo_status(po_status);
		preorderVO.setPo_total(po_total);
		preorderVO.setPo_note(po_note);
		
		
		System.out.println("PreOrderService已將preorderVO資料set完囉!");

		System.out.println("進入dao.inserWithDetail");
		dao.inserWithDetail(preorderVO , list);
		
		return preorderVO;
	}
	
	public PreOrderVO addPreOrder(String mem_id,java.sql.Timestamp po_time,Integer po_zip,String po_name,
			String po_phone,String po_addr,Integer po_status,Integer po_total,String po_note) {
		PreOrderVO preorderVO = new PreOrderVO();
		
		preorderVO.setMem_id(mem_id);
		preorderVO.setPo_time(po_time);
		preorderVO.setPo_zip(po_zip);
		preorderVO.setPo_name(po_name);
		preorderVO.setPo_phone(po_phone);
		preorderVO.setPo_addr(po_addr);
		preorderVO.setPo_status(po_status);
		preorderVO.setPo_total(po_total);
		preorderVO.setPo_note(po_note);
		dao.insert(preorderVO);
		return preorderVO;
	}
	
	public PreOrderVO updatePreOrder(String po_no,String mem_id,java.sql.Timestamp po_time,Integer po_zip,String po_name,
			String po_phone,String po_addr,Integer po_status,Integer po_total,String po_note) {
		PreOrderVO preorderVO = new PreOrderVO();
		
		preorderVO.setPo_no(po_no);
		preorderVO.setMem_id(mem_id);
		preorderVO.setPo_time(po_time);
		preorderVO.setPo_zip(po_zip);
		preorderVO.setPo_name(po_name);
		preorderVO.setPo_phone(po_phone);
		preorderVO.setPo_addr(po_addr);
		preorderVO.setPo_status(po_status);
		preorderVO.setPo_total(po_total);
		preorderVO.setPo_note(po_note);
		dao.update(preorderVO);
		
		return dao.findByPrimaryKey(po_no);
	}
	public PreOrderVO updateStatus(Integer po_status,String po_no) {
		PreOrderVO preorderVO = new PreOrderVO();
		System.out.println("進入Servic - updateStatus");
		preorderVO.setPo_status(po_status);
		preorderVO.setPo_no(po_no);
		dao.updateStatus(preorderVO);
		System.out.println("進入Servic - findByPrimaryKey準備執行");
		return dao.findByPrimaryKey(po_no);
	}
	
	
	public void updatePreOrder(PreOrderVO preorderVO) {
		dao.update(preorderVO);
	}
	
	public void deletePreOrder(String po_no) {
		dao.delete(po_no);
	}
	
	public PreOrderVO getOneEmp(String po_no) {
		return dao.findByPrimaryKey(po_no);
	}
	public List<PreOrderVO> getAllByMemid(String mem_id) {
		return dao.getAllByMemid(mem_id);
	}
	
	public List<PreOrderVO> getAll(){
		System.out.println("預購訂單Service - 進入getAll()方法");
		return dao.getAll();
	}
	public List<PreOrderVO> look_discount_pono(Integer reach_number,String po_prod_no){
		System.out.println("預購訂單Service - 進入look_discount_pono方法");
		return dao.look_discount_pono(reach_number,po_prod_no);
	}
	
	
	
}
