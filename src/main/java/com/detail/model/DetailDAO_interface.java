package com.detail.model;

import java.sql.Connection;
import java.util.List;

import com.detail.model.DetailVO;
import com.prod.model.ProdVO;

public interface  DetailDAO_interface {


		public void insert(DetailVO detailVO, Connection con); 
		
		public DetailVO findByPrimaryKey(String or_no);

		public List<DetailVO> getAll();
		
		public List<DetailVO> detailSearch(String or_no);
		
		
	}


