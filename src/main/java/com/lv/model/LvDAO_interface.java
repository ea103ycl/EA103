package com.lv.model;

import java.util.List;

public interface LvDAO_interface {

	public void insert(LvVO lvVO);

	public void update(LvVO lvVO);

	public void delete(Integer lv);
	
	public LvVO findByPrimaryKey(Integer lv);
	
	public LvVO findByThresholdSetting(Integer f_cnt); //ㄌ块發萝计耬琩高琌Τ戈

	public LvVO findLvByFansCnt(Integer f_cnt); // ㄌ砆發萝计琩高┮妮单

	public byte[] findIconByLv(Integer lv);
	
	public byte[] getOneMemberLvIcon(String mem_id);
	
	public List<LvVO> getAll();

}
