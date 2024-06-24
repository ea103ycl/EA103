package com.painter.model;

import java.util.List;

public class PainterService {

	private PainterDAO_interface dao;
	
	public PainterService() {
		dao = new PainterDAO();
	}
	
	
	//==YCL===========================================================
	public void insert(PainterVO painterVO, String tag_desc) {
		dao.insert(painterVO, tag_desc);
	}
	
	public void update(PainterVO painterVO, String tag_desc) {
		dao.update(painterVO, tag_desc);
	}

	public void delete(Integer ptr_no, Integer ptr_status) {
		dao.delete(ptr_no, ptr_status);
	}

	public List<PainterVO> getSomeoneAll(String mem_id, String login_mem_id){
		return dao.getSomeoneAll(mem_id, login_mem_id);
	}

	public List<PainterVO> getSomeoneCollection(String mem_id, String login_mem_id) {
		return dao.getSomeoneCollection(mem_id, login_mem_id);
	}
	
	public List<PainterVO> getSomeoneFollowAll(String login_mem_id){
		 return dao.getSomeoneFollowAll(login_mem_id);
	 }
	
	public byte[] getPicByPtrNo(Integer ptr_no) {
		return dao.getPicByPtrNo(ptr_no);
	}
	public PainterVO getOnePainter(Integer ptr_no) {
		return dao.getOnePainter(ptr_no);
	}
	
	public List<String> getMembersByMAccno(String acct){
		return dao.getMembersByMAccno(acct);
	}
	
	//==Tim============================================================
	public List<PainterVO> getAll() {
		return dao.getAll();
	}
	
	public PainterVO getPainterVO(Integer ptr_no) {
//		return dao.findByPrimaryKey(ptr_no); //mark by YCL
		return dao.getOnePainter(ptr_no);
	}
	
	public List<PainterVO> getLatestPics() {
		return dao.latestPics();
	}
	
	public List<PainterVO> getMostLiked(Integer rankBegin,Integer rankEnd){
		return dao.getMostLiked(rankBegin, rankEnd);
	}
	
	public Integer getPicCount() {
		return dao.getPicCount();
	}
}
