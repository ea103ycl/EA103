package com.painter_act.model;

import java.util.List;

public interface PainterActDAO_interface {
	
	//異動互動資料(executeType -1:刪除 1:增加)
	public void update(Integer executeType, PainterActVO painterActVO);
	
	//查詢有哪些會員點了某個作品的1:喜歡 2:收藏
	public List<PainterActVO> getAllByActType(Integer ptr_no, Integer act_type);
	
	//查詢登入的會員與作品的互動歷史
	public PainterActVO getOneByActType(Integer ptr_no, Integer act_type, String mem_id);
	
}
