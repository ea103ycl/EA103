package com.discount.model;

import java.util.List;

import com.discount.model.DiscountSettingVO;

public interface DiscountSettingDAO_interface {
	public void insert(DiscountSettingVO discountsettingVO);
	public void update(DiscountSettingVO discountsettingVO);
	public void delete(String dis_no);
	public DiscountSettingVO findByPrimaryKey(String dis_no);
	public void addDiscountWithEvent(DiscountSettingVO discountsettingVO);
	public List<DiscountSettingVO> getALL();
}
