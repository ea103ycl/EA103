package com.prod.model;

import java.io.InputStream;
import java.util.*;

public interface ProdDAO_interface {
	
	public void insert(ProdVO prodVO);

	public void delete(Integer prod_no);

	public void update(ProdVO prodVO);

	public ProdVO findByPrimaryKey(Integer prod_no);

	public List<ProdVO> getAll();
	
	public List<ProdVO> getAllptr();
	
	public List<ProdVO> getAllma();
	
	public List<ProdVO> getAll_status();
	
	public List<ProdVO> FuzzySearch(String prod_name);
	
	public void changeStatus(ProdVO prodVO);
	
	public List<ProdVO> MaSearch(String ma_no);
	
	public List<ProdVO> PtrSearch(Integer ptr_no);
	
	public List<ProdVO> getRelatedProd(Integer ptr_no);

	public ProdVO OthrMaSearch(Integer ptr_no, String ma_no);
	
	public ProdVO getOneByPtr_no(Integer ptr_no);
	public ProdVO getOneByMa_no(String ma_no);
	
	
	// 萬用複合查詢(傳入參數型態Map)(回傳 List)
//	public List<ProdVO> getAll(Map<String, String[]> map);

}
