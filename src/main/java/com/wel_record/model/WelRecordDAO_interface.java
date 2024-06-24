package com.wel_record.model;

import java.util.List;

public interface WelRecordDAO_interface
{

	public void insert(WelRecordVO welRecordVO);

	public WelRecordVO findByPrimaryKey(Integer tns_id);

	public WelRecordVO updateOrderId(String order_id, String mem_id);

	public List<WelRecordVO> getWelRecordBySrc(int tns_src);

	public List<WelRecordVO> getWelRecordAmongSrc(int tns_srcStart, int tns_srcEnd);

	// 查詢一個會員有多筆錢包交易
	public List<WelRecordVO> getWelRecordByMemID(String mem_id);

	public List<WelRecordVO> getAll();

	public void insert4FakeData(WelRecordVO welRecordVO);

}
