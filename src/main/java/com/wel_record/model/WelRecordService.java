package com.wel_record.model;

import java.util.List;

import com.mem.model.MemDAO_interface;
import com.mem.model.MemJNDIDAO;

public class WelRecordService {

	private WelRecordDAO_interface dao;
	private MemDAO_interface memDao;

	public WelRecordService() {

		dao = new WelRecordJNDIDAO();
		memDao = new MemJNDIDAO();

	}

	public WelRecordVO addWelRecord(String mem_id, Integer tns_src, String order_id, Integer tns_amount) {

		WelRecordVO welRecordVO = new WelRecordVO();

		welRecordVO.setMem_id(mem_id);
		welRecordVO.setTns_src(tns_src);
		welRecordVO.setOrder_id(order_id);
		welRecordVO.setTns_amount(tns_amount);

		dao.insert(welRecordVO);

		return welRecordVO;

	}

	public WelRecordVO getOneWelRecord(Integer tns_id) {

		return dao.findByPrimaryKey(tns_id);
	}

	public List<WelRecordVO> getWelRecordBySrc(int tns_src) {

		return dao.getWelRecordBySrc(tns_src);

	}

	public List<WelRecordVO> getWelRecordAmongSrc(int tns_srcStart, int tns_srcEnd) {

		return dao.getWelRecordAmongSrc(tns_srcStart, tns_srcEnd);
	}

	public List<WelRecordVO> getWelRecordByMemID(String mem_id) {

		return dao.getWelRecordByMemID(mem_id);
	}

	public List<WelRecordVO> getAll() {

		return dao.getAll();

	}
	

}
