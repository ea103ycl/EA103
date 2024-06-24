package com.event.model;

import java.sql.Timestamp;
import java.util.List;

public class EventService {
	//service需要用dao呼叫方法的能力
	//svc.insert();藉此斷絕VO與Controller的直接關係
	private EventDAO dao;
	public EventService() {
		dao= new EventDAO();
	}
	public String insert(String eventName,Timestamp event_start,Timestamp event_end,Timestamp event_ul_start,Timestamp event_ul_end,Timestamp event_vote_start,Timestamp event_vote_end,Integer event_stat) {
		EventVO eventVO=new EventVO();
		
		eventVO.setEvent_name(eventName);
		eventVO.setEvent_start(event_start);
		eventVO.setEvent_end(event_end);
		eventVO.setEvent_ul_start(event_ul_start);
		eventVO.setEvent_ul_end(event_ul_end);
		eventVO.setEvent_vote_start(event_vote_start);
		eventVO.setEvent_vote_end(event_vote_end);				
		eventVO.setEvent_stat(event_stat);
		return dao.insert(eventVO);
	}
	
	public void delete(String event_no) {
		dao.delete(event_no);
	}
	public void update(String event_no,String event_name,Timestamp event_start,Timestamp event_end,Timestamp event_ul_start,Timestamp event_ul_end,Timestamp event_vote_start,Timestamp event_vote_end,Integer event_stat) {
		EventVO eventVO=new EventVO();
		eventVO.setEvent_no(event_no);
		eventVO.setEvent_name(event_name);			
		eventVO.setEvent_start(event_start);				
		eventVO.setEvent_end(event_end);		
		eventVO.setEvent_ul_start(event_ul_start);//
		eventVO.setEvent_ul_end(event_ul_end);//
		eventVO.setEvent_vote_start(event_vote_start);//
		eventVO.setEvent_vote_end(event_vote_end);//
		eventVO.setEvent_stat(event_stat);
		dao.update(eventVO);
		//for bidding event_no
		
	}
	public EventVO findByPrimaryKey(String event_no) {
		EventVO eventVO=dao.findByPrimaryKey(event_no);
		return eventVO;
	}
	public List<EventVO> findWithoutEnd(){
		List<EventVO> eventVOs= dao.findWithoutEnd();
		return eventVOs;
	}
	public List<EventVO> findAllEvent(){
		List<EventVO> eventVOs=dao.findAllEvent();
		return eventVOs;
	}
	public String findLastEndEvent() {
		return dao.findLastEndEvent();
	}
}
