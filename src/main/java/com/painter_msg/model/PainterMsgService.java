package com.painter_msg.model;

import java.util.List;

public class PainterMsgService {

	private PainterMsgDAO_interface dao;

	public PainterMsgService() {
		dao = new PainterMsgDAO();
	}

	public void insert(PainterMsgVO painterMsgVO) {
		dao.insert(painterMsgVO);
	}

	public void delete(Integer msg_no, Integer msg_stat) {
		dao.delete(msg_no, msg_stat);
	}

	public List<PainterMsgVO> getAll(Integer ptr_no) {
		return dao.getAll(ptr_no);
	}

	public Integer getPainterMsgCnt(Integer ptr_no) {
		return dao.getPainterMsgCnt(ptr_no);
	}

}
