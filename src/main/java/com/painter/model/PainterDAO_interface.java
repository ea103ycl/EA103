package com.painter.model;

import java.util.List;

import com.mem.model.MemVO;

public interface PainterDAO_interface {

	//==YCL=======================================
	public void insert(PainterVO painterVO, String tag_desc);
	public void update(PainterVO painterVO, String tag_desc);
	public void delete(Integer ptr_no, Integer ptr_status);
	public List<PainterVO> getSomeoneAll(String mem_id, String login_mem_id);
	public List<PainterVO> getSomeoneCollection(String mem_id, String login_mem_id);
	public List<PainterVO> getSomeoneFollowAll(String login_mem_id);
	public byte[] getPicByPtrNo(Integer ptr_no);
	public PainterVO getOnePainter(Integer ptr_no);
	
	public List<String> getMembersByMAccno(String acct);
	
	
	//==Tim=======================================
	public List<PainterVO> getAll();
	public List<PainterVO> latestPics();
	public List<PainterVO> getMostLiked(Integer rankBegin,Integer rankEnd);
	public Integer getPicCount();
	
}
