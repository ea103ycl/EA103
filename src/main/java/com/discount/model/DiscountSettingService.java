package com.discount.model;

import java.util.List;

public class DiscountSettingService {
	private DiscountSettingDAO_interface dao;
	public DiscountSettingService() {
		dao = new DiscountSettingDAO();
	}
	
	public DiscountSettingVO addDiscountSetting(String dis_no,String po_prod_no,Integer reach_number,Integer reach_discount) {
		DiscountSettingVO discountsettingVO = new DiscountSettingVO();
		
		discountsettingVO.setDis_no(dis_no);
		discountsettingVO.setPo_prod_no(po_prod_no);
		discountsettingVO.setReach_number(reach_number);
		discountsettingVO.setReach_discount(reach_discount);
		dao.insert(discountsettingVO);
		
		return discountsettingVO;
	}
	
	public DiscountSettingVO updateDiscountSetting(String po_prod_no,Integer reach_number,Integer reach_discount) {
		DiscountSettingVO discountsettingVO = new DiscountSettingVO();
		discountsettingVO.setPo_prod_no(po_prod_no);
		discountsettingVO.setReach_number(reach_number);
		discountsettingVO.setReach_discount(reach_discount);
		dao.update(discountsettingVO);
		
		return discountsettingVO;
	}
	public DiscountSettingVO addDiscountWithEvent(Integer reach_discount,Integer reach_number,String event_no){
		System.out.println("-----進入Service階段-----");
		DiscountSettingVO discountsettingVO = new DiscountSettingVO();

		discountsettingVO.setEvent_no(event_no);
		discountsettingVO.setReach_number(reach_number);
		discountsettingVO.setReach_discount(reach_discount);
		System.out.println("-----Service階段set完值-----");
		dao.addDiscountWithEvent(discountsettingVO);
		System.out.println("已用dao.addDiscountWithEvent(discountsettingVO)");
		System.out.println("準備return discountsettingVO");
		return discountsettingVO;
	}
	
	public void deleteDiscountSetting(String dis_no) {
		dao.delete(dis_no);
	}
	
	public DiscountSettingVO getOneDiscountSetting(String dis_no) {
		return dao.findByPrimaryKey(dis_no);
	}
	public List<DiscountSettingVO> getAll(){
		return dao.getALL();
	}
	
	
	
	
}
