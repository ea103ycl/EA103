package com.event_tag.model;

import java.sql.Timestamp;
import java.util.*;

public class Event_TagService {
	Event_TagDAO dao;
	public Event_TagService() {
		this.dao=new Event_TagDAO();
	}
	public void insert(String mem_id,String event_no,Timestamp event_tag_time,String event_tag_name) {
		Event_TagVO event_tagVO=new Event_TagVO();
		event_tagVO.setMem_id(mem_id);
		event_tagVO.setEvent_no(event_no);
		event_tagVO.setEvent_tag_time(event_tag_time);
		event_tagVO.setEvent_tag_name(event_tag_name);		
		dao.insert(event_tagVO);
	}
	
	public void update(String mem_id,String event_no,Timestamp event_tag_time,String event_tag_name,Integer event_tag_no) {
		Event_TagVO event_tagVO=new Event_TagVO();
		event_tagVO.setMem_id(mem_id);
		event_tagVO.setEvent_no(event_no);
		event_tagVO.setEvent_tag_time(event_tag_time);
		event_tagVO.setEvent_tag_name(event_tag_name);
		event_tagVO.setEvent_tag_no(event_tag_no);
		dao.update(event_tagVO);
	}
	public List<Event_TagVO> findAllTag(){
		List<Event_TagVO> event_tagVOs=dao.listAllTag();
		return event_tagVOs;
	}
	
	public Event_TagVO findByPrimarykey(Integer event_tag_no) {
		Event_TagVO event_tagVO=dao.findByPrimaryKey(event_tag_no);
		
		return  event_tagVO;
	}
	
	public List<String> findAllMem(){
		List<String> memIds=dao.findAllMem();
		
		return memIds; 
	}
	public List<String> findAllEventNo(){
		List<String> eventNos=dao.findAllEventNo();
		
		return eventNos; 
	}
	
	public List<Event_TagVO> randTag(){
		return dao.randomTag();
	}
	public List<Event_TagVO> findAllByTagName(String event_tag_name) {
		return dao.findAllByTagName(event_tag_name);
	}
}
