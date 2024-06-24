package com.painter_tag.model;

import java.util.List;

public class PainterTagService {

	PainterTagDAO_interface dao;

	public PainterTagService() {
		dao = new PainterTagDAO();
	}

	// ==YCL=======================================================
	public Integer insert(String tagDesc) {
		return dao.insert(tagDesc);
	}

	public PainterTagVO getOneByTagNo(Integer tag_no) {
		return dao.getOneByTagNo(tag_no);
	}

	// ==Tim=======================================================
	public List<Integer> getPicbyTag(String tag_desc) {
		return dao.getPicbyTag(tag_desc);
	}

	public List<PainterTagVO> fuzzySearch(String tag_desc) {
		return dao.fuzzySearch(tag_desc);
	}

	public Integer getPainterTagNo(String tag_desc) {
		return dao.getTagNo(tag_desc);
	}

	public String getPainterTagDesc(Integer tag_no) {
		return dao.getTagDesc(tag_no);
	}

}
