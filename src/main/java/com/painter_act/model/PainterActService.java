package com.painter_act.model;

import java.util.List;

public class PainterActService {

	private PainterActDAO_interface dao;

	public PainterActService() {
		dao = new PainterActDAO();
	}

	public void update(Integer executeType, PainterActVO painterActVO){
		dao.update(executeType, painterActVO);
	}

	public List<PainterActVO> getAllByActType(Integer ptr_no, Integer act_type) {
		return dao.getAllByActType(ptr_no, act_type);
	}

	public PainterActVO getOneByActType(Integer ptr_no, Integer act_type, String mem_id) {
		return dao.getOneByActType(ptr_no, act_type, mem_id);
	}

}
