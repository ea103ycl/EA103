package com.eval.model;



	import java.io.InputStream;
	import java.sql.*;
	import java.util.*;

	import javax.naming.Context;
	import javax.naming.InitialContext;
	import javax.naming.NamingException;
	import javax.sql.DataSource;



import com.eval.model.*;
import com.prod.model.ProdVO;


	public class EvalService {
		
		private EvalDAO_interface dao;

		public EvalService() {
			dao = new EvalDAO();
		}
		
		public EvalVO addEval(Integer prod_no,  String or_no, String mem_id, Integer eval_rang,String eval_review) {

			EvalVO evalVO = new EvalVO();
			evalVO.setProd_no(prod_no);
			evalVO.setOr_no(or_no);
			evalVO.setMem_id(mem_id);
			evalVO.setEval_rang(eval_rang);
			evalVO.setEval_review(eval_review);
			dao.insert(evalVO);

				return evalVO;
			}
		public List<EvalVO> getAllByProd_no(Integer prod_no) {
			return dao.getAllByProd_no(prod_no);
		}
		
		public List<EvalVO> getAllByOr_no(String or_no) {
			return dao.getAllByOr_no(or_no);
		}
		
		public EvalVO getOneByOr_no_and_Prod_no(String or_no, Integer prod_no) {
			return dao.getOneByOr_no_and_Prod_no(or_no, prod_no);
		}
		
		public EvalVO getMem_pic_ByMem_id(String mem_id) {
			return dao.getMem_pic_ByMem_id(mem_id);
			}
		
	}
