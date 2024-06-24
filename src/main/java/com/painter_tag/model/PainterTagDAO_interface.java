package com.painter_tag.model;

import java.util.List;


public interface PainterTagDAO_interface {

	//==YCL===============================
	public Integer findTagNo(String tagDesc); //精確查詢版本
	public Integer insert(String tagDesc);
	public PainterTagVO getOneByTagNo(Integer tag_no);
	
	//==Tim===============================
	public List<Integer>getPicbyTag(String tag_desc);
	public Integer getTagNo(String tagDesc); //模糊查詢版本
	public String getTagDesc(Integer tag_no);
	public List<PainterTagVO> fuzzySearch(String tag);
	
}
