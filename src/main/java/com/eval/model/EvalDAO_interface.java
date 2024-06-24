package com.eval.model;

import java.sql.Connection;
import java.util.List;

import com.detail.model.DetailVO;
import com.order.model.OrderVO;
import com.prod.model.ProdVO;

public interface  EvalDAO_interface {


		public void insert(EvalVO evalVO); 
		public List<EvalVO> getAllByProd_no(Integer prod_no);
		public List<EvalVO> getAllByOr_no(String or_no);
		public EvalVO getOneByOr_no_and_Prod_no(String or_no,Integer prod_no);
		public EvalVO getMem_pic_ByMem_id(String mem_id);
		
	}
