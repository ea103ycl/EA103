package com.detail.model;

import java.util.List;
import java.sql.Connection;
import com.detail.model.DetailVO;
import com.order.model.OrderVO;
import com.prod.model.ProdVO;

public class DetailService {
	
	private DetailDAO_interface dao;

	public DetailService() {
		dao = new DetailDAO();
	}
	
	
	public DetailVO addDetail(String or_no, Integer prod_no, Integer or_qty, Integer or_p_price, Connection con) {
		
		DetailVO detailVO = new DetailVO();
			
		detailVO.setOr_no(or_no);
		detailVO.setProd_no(prod_no);
		detailVO.setOr_qty(or_qty);
		detailVO.setOr_p_price(or_p_price);
		
		
		dao.insert(detailVO,con);

			return detailVO;
		}
	
	
	public List<DetailVO> detailSearch(String or_no) {
		return dao.detailSearch(or_no);
	}
	
	
	
	
}
	