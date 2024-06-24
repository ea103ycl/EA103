package com.event_p.model;

import java.util.*;

import com.event_p.model.Event_PVO;

public interface Event_PDAO_interface {
	public  void insert(Event_PVO pVO);
	public 	List<Event_PVO> findAllPic();
	public Event_PVO findByPrimaryKey(Integer event_p_no);
	public void update(Event_PVO pVO);
	public List<String> findAllMem();
	public List<String> findAllEventNo();
	public void votePic(Integer event_p_no,String mem_id);
	public Event_PVO findTopEventPNoByEventNoWithoutReport(String event_no);
//	public Event_PVO findTopByEventNoWithoutReport(String event_no);
	
	public List<Event_PVO> findAllByEventNo(String event_no);
	public List<Event_PVO> findAllByEventNoRankDescWithoutReport(String event_no);
	public boolean checkUploadByMemid(String mem_id,String event_no);
	public Event_PVO findUploadByMemid(String mem_id,String event_no);
	public void delete(Integer event_p_no);
	public void deleteVote(Integer event_p_no);
	public List<Event_PVO> bigRankSort(String event_no,Integer rownum);
	public void bigRankUpdate(Integer vote_rank,Integer event_p_no);
	
	public List<Event_PVO> find5PicByEventNo(String event_no);
	public List<Event_PVO> findAllNoReport(String event_no);
	public List<Event_PVO> findAllOrderBySelect(String event_no,String colName);
	
	//By∑Á¿s
	public List<Event_PVO> getAll(String event_no);
}
