package com.preproduct.model;

import java.util.*;

import com.event_p.model.Event_PVO;
import com.material.model.Material_Data_VO;

public interface PreProductDAO_interface {
	public void insert(PreProductVO pre_productVO);
	//選一項素材，批量產生預購商品
	public void insertByRanking(PreProductVO pre_productVO);
	public void update(PreProductVO pre_productVO);
	public void delete(String po_prod_no);
	//切換"預購開始"and"預購結束"，藉以上/下架商品。
	public void switchPreProduct(PreProductVO pre_productVO);
	public PreProductVO findByPrimaryKey(String po_prod_no);
	public List<PreProductVO> getALLPreproductInTime();
	//根據"活動編號"取得"1到10名"且"狀態為3"的作品所有資訊。
	public List<PreProductVO> GET_ALLOF_PREPRODUCT();
	//依活動編號查詢商品
	public Set<Event_PVO> getEventpnoByPreproduct(Integer event_p_no);
	//依素材編號查詢商品
	public Set<Material_Data_VO> getManoByPreproduct(String ma_no);
	List<PreProductVO> getALL();

	 
	
	
	
}
