package com.painter_tag_map.model;

import java.sql.Connection;
import java.util.List;

public class PainterTagMapService {

	PainterTagMapDAO_interface dao;

	public PainterTagMapService() {
		dao = new PainterTagMapDAO();
	}

	public void insert(Connection con, Integer ptr_no, String tag_desc) {
		dao.insert(con, ptr_no, tag_desc);
	}
	
	public void delete(Connection con, Integer ptr_no) {
		dao.delete(con, ptr_no);
	}

	public List<PainterTagMapVO> getAllByPtrNo(Integer ptr_no) {
		return dao.getAllByPtrNo(ptr_no);
	}

}
