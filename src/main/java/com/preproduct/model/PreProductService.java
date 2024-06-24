package com.preproduct.model;

import java.util.List;

public class PreProductService {
	private PreProductDAO dao;
	public PreProductService() {
		System.out.println("--------------------------------------");
		System.out.println("進入Service的建構子");
		dao = new PreProductDAO();
		System.out.println("Service使用PreProductDAO");
		System.out.println("--------------------------------------");
	}
	
	public PreProductVO addPreProduct(Integer event_p_no,String ma_no,java.sql.Timestamp po_start,
			java.sql.Timestamp po_end,Integer po_price,String po_detail) {
		System.out.println("-------Service - form觸發addPreProduct--------");
		
		PreProductVO preproductVO = new PreProductVO();
		preproductVO.setEvent_p_no(event_p_no);
		preproductVO.setMa_no(ma_no);
		preproductVO.setPo_start(po_start);
		preproductVO.setPo_end(po_end);
		preproductVO.setPo_price(po_price);
		preproductVO.setPo_detail(po_detail);
		 
		System.out.println("Service - 準備使用dao.insert");
		dao.insert(preproductVO);
		System.out.println("Service - dao.insert執行完成，return preproductVO");
		System.out.println("--------------------------------------");
		return preproductVO;
	}
	
	public PreProductVO addByRanking(String ma_no,java.sql.Timestamp po_start,
			java.sql.Timestamp po_end,Integer po_price,String po_detail) {
		System.out.println("-------Service - form觸發addByRanking--------");
		
		PreProductVO preproductVO = new PreProductVO();
		preproductVO.setMa_no(ma_no);
		preproductVO.setPo_start(po_start);
		preproductVO.setPo_end(po_end);
		preproductVO.setPo_price(po_price);
		preproductVO.setPo_detail(po_detail);
		
		System.out.println("Service - 準備使用dao.insertByRanking");
		dao.insertByRanking(preproductVO);
		System.out.println("Service - dao.insertByRanking執行完成，return preproductVO");
		System.out.println("--------------------------------------");
		return preproductVO;
	}
	
	public PreProductVO getOnePreProduct(String po_prod_no) {
		return dao.findByPrimaryKey(po_prod_no);
	}
	
	public PreProductVO updatePreProduct(String po_prod_no,Integer event_p_no,String ma_no,java.sql.Timestamp po_start,
			java.sql.Timestamp po_end,Integer po_price,String po_detail) {
		System.out.println("-------Service - form觸發updatePreProduct--------");
		
		PreProductVO preproductVO = new PreProductVO();
		preproductVO.setPo_prod_no(po_prod_no);
		preproductVO.setEvent_p_no(event_p_no);
		preproductVO.setMa_no(ma_no);
		preproductVO.setPo_start(po_start);
		preproductVO.setPo_end(po_end);
		preproductVO.setPo_price(po_price);
		preproductVO.setPo_detail(po_detail);
		
		System.out.println("Service - 準備使用dao.update");
		dao.update(preproductVO);
		System.out.println("Service - dao.update執行完成，return preproductVO");
		System.out.println("--------------------------------------");
		
		return preproductVO;
	}
	
	public PreProductVO switchPreProduct(String po_prod_no,java.sql.Timestamp po_start,
			java.sql.Timestamp po_end) {
		System.out.println("-------Service - form觸發switchPreProduct--------");
		
		PreProductVO preproductVO = new PreProductVO();
		preproductVO.setPo_prod_no(po_prod_no);
		preproductVO.setPo_start(po_start);
		preproductVO.setPo_end(po_end);
		
		
		System.out.println("Service - 準備使用dao.switchPreProduct");
		dao.switchPreProduct(preproductVO);
		System.out.println("Service - dao.switchPreProduct執行完成，return preproductVO");
		System.out.println("--------------------------------------");
		return preproductVO;
	}

	public void deletePreProduct(String po_prod_no) {
		dao.delete(po_prod_no);
	}

	public List<PreProductVO> getALLPreproductInTime(){
		System.out.println("-------Service - form 觸發 getAll()--------");
		return dao.getALLPreproductInTime();
		
	}
	
	public List<PreProductVO> GET_ALLOF_PREPRODUCT(){
		System.out.println("-------Service - form 觸發 GET_ALLOF_PREPRODUCT()--------");
		return dao.GET_ALLOF_PREPRODUCT();
	}
	
	public List<PreProductVO> getAll(){
		System.out.println("-------Service - form 觸發 getAll()--------");
		return dao.getALL();
		
	}
	
}
