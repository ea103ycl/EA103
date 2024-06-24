package com.painter_tag_map.model;

import java.sql.Connection;
import java.util.List;

public interface PainterTagMapDAO_interface {
	
	
	public void insert(Connection con, Integer ptr_no, String tag_desc);
	public void delete(Connection con, Integer ptr_no);
	public List<PainterTagMapVO> getAllByPtrNo(Integer ptr_no);

}
