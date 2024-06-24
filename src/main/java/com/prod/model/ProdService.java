package com.prod.model;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import com.prod.model.ProdVO;

public class ProdService {
	
	private ProdDAO_interface dao;

	public ProdService() {
		dao = new ProdDAO();
	}
	
	public ProdVO addProd(Integer ptr_no, String ma_no, String prod_name,
			Integer prod_price, String prod_detail, Integer prod_status, byte[] prod_pic) {

		ProdVO prodVO = new ProdVO();
		prodVO.setPtr_no(ptr_no);
		prodVO.setMa_no(ma_no);
		prodVO.setProd_name(prod_name);
		prodVO.setProd_price(prod_price);
		prodVO.setProd_detail(prod_detail);
		prodVO.setProd_status(prod_status);
		prodVO.setProd_pic(prod_pic);
		
		dao.insert(prodVO);

			return prodVO;
		}
	
	public ProdVO updateProd(Integer prod_no, Integer ptr_no, String ma_no, String prod_name,
			Integer prod_price, String prod_detail, Integer prod_status, byte[] prod_pic) {

		ProdVO prodVO = new ProdVO();
		
		prodVO.setProd_no(prod_no);
		prodVO.setPtr_no(ptr_no);
		prodVO.setMa_no(ma_no);
		prodVO.setProd_name(prod_name);
		prodVO.setProd_price(prod_price);
		prodVO.setProd_detail(prod_detail);
		prodVO.setProd_status(prod_status);
		prodVO.setProd_pic(prod_pic);
		
		dao.update(prodVO);
		
		return prodVO;
	}
	
	
	
	public void deleteProd(Integer prod_no) {
		dao.delete(prod_no);
	}
	
	public ProdVO getOneProd(Integer prod_no) {
		return dao.findByPrimaryKey(prod_no);
	}

	public List<ProdVO> getAll() {
		return dao.getAll();
	}
	
	public List<ProdVO> getAllptr() {
		return dao.getAllptr();
	}
	
	public List<ProdVO> getAllma() {
		return dao.getAllma();
	}
	
	public List<ProdVO> getRelatedProd(Integer ptr_no) {
		return dao.getRelatedProd(ptr_no);
	}
	
	
	public List<ProdVO> getAll_stauts() {
		return dao.getAll_status();
	}
	
	public List<ProdVO> FuzzySearch(String prod_name) {
		return dao.FuzzySearch(prod_name);
	}
	
	public List<ProdVO> MaSearch(String ma_no) {
		return dao.MaSearch(ma_no);
	}
	
	public List<ProdVO> PtrSearch(Integer ptr_no) {
		return dao.PtrSearch(ptr_no);
	}
	
	
	public ProdVO getOneOtherMa(Integer ptr_no, String ma_no) {
		return dao.OthrMaSearch(ptr_no, ma_no);
	}
	
	
	
	
	public ProdVO ChangeStatus(Integer prod_no,  Integer prod_status) {

		ProdVO prodVO = new ProdVO();
		
		prodVO.setProd_no(prod_no);
		prodVO.setProd_status(prod_status);
		dao.changeStatus(prodVO);
		return prodVO;
	}
	
	
	public ProdVO getOneByMa_no(String ma_no) {
		return dao.getOneByMa_no(ma_no);
	}
	
	public ProdVO getOneByPtr_no(Integer ptr_no) {
		return dao.getOneByPtr_no(ptr_no);
	}
	
	
	
	
	
	
}
