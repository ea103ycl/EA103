package com.event_p.model;
import java.sql.Timestamp;
import java.util.List;

import com.event_p.model.*;

public class Event_PService {
	private Event_PDAO dao;
	public Event_PService() {
		this.dao=new Event_PDAO();
	}
	public void insert(String mem_id,String event_no,String event_p_name,Timestamp event_p_date,Integer event_vote_num,Integer vote_rank,Integer event_p_stat,byte[] event_p_img) {
		Event_PVO event_pVO=new Event_PVO();

		event_pVO.setMem_id(mem_id);
		event_pVO.setEvent_no(event_no);
		event_pVO.setEvent_p_name(event_p_name);
		event_pVO.setEvent_p_date(event_p_date);
		event_pVO.setEvent_vote_num(event_vote_num);
		event_pVO.setVote_rank(vote_rank);
		event_pVO.setEvent_p_stat(event_p_stat);
		event_pVO.setEvent_p_img(event_p_img);
		dao.insert(event_pVO);
	}
	public void update(String mem_id,String event_no,String event_p_name,Timestamp event_p_date,Integer event_vote_num,Integer vote_rank,Integer event_p_stat,byte[] event_p_img,Integer event_p_no) {
		Event_PVO event_pVO=new Event_PVO();
		event_pVO.setEvent_p_no(new Integer( event_p_no));
		event_pVO.setMem_id(mem_id);
		event_pVO.setEvent_no(event_no);
		event_pVO.setEvent_p_name(event_p_name);
		event_pVO.setEvent_p_date(event_p_date);
		event_pVO.setEvent_vote_num(new Integer( event_vote_num));
		event_pVO.setVote_rank(new Integer( vote_rank));
		event_pVO.setEvent_p_stat(new Integer( event_p_stat));
		if(event_p_img.length==0) {
			System.out.println("更新的event_pVO 的img是null");
			
		}
		event_pVO.setEvent_p_img(event_p_img);
		dao.update(event_pVO);
	}
	public List<String> findAllMem(){
		List<String> memIds=dao.findAllMem();
		return memIds;
	}
	public List<String> findAllEventNo(){
		List<String> eventNos=dao.findAllEventNo();
		return eventNos;
	}
	public Event_PVO findByPrimaryKey(Integer event_p_no) {
		 Event_PVO event_pVO=dao.findByPrimaryKey(event_p_no);
		 return event_pVO;
	}
	public List<Event_PVO> finAllPic(){
		List<Event_PVO> event_pVOs= dao.findAllPic();
		return event_pVOs;
	}
	public void votePic(Integer event_p_no,String mem_id) {
		
		dao.votePic(event_p_no,mem_id);
		
	}
	public Event_PVO findTopByEventNoWithoutReport(String event_no) {
		Event_PVO event_pVO=dao.findTopEventPNoByEventNoWithoutReport(event_no);
		return event_pVO;
	}
	public List<Event_PVO> findAllByEventNo(String event_no){
		List<Event_PVO> event_pVOs= dao.findAllByEventNo(event_no);
		return event_pVOs;
	}
	public boolean checkUploadByMemid(String mem_id,String event_no) {
		return dao.checkUploadByMemid(mem_id,event_no);
	}
	public Event_PVO findUploadByMemid(String mem_id,String event_no) {
		return dao.findUploadByMemid(mem_id,event_no);
	}
	public void delete(Integer event_p_no) {
		dao.delete(event_p_no);
	}
	public void deleteVote(Integer event_p_no) {
		dao.deleteVote(event_p_no);
	}
	public List<Event_PVO> findAllByEventNoRankDescWithoutReport(String event_no){
		return dao.findAllByEventNoRankDescWithoutReport(event_no);
	}
	public List<Event_PVO> findAllNoReport(String event_no){
		return dao.findAllNoReport(event_no);
	}
	public List<Event_PVO> findAllOrderBySelect(String event_no,String colName){
		return dao.findAllOrderBySelect(event_no, colName);
	}
	public List<Event_PVO> find5PicByEventNo(String event_no){
		return dao.find5PicByEventNo(event_no);
	}
	
	//==By瑞龍=============================================================
	public List<Event_PVO> getAll(String event_no){
		return dao.getAll(event_no);
	}
}
