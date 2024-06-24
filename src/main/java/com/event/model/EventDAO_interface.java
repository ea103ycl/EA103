package com.event.model;


import java.util.List;
//主題競賽會使用的方法
//當HashTag抽完時發布這次的主題競賽

public interface EventDAO_interface {
	public String insert(EventVO eventVO);
	public void delete(String event_no);
	public void update(EventVO eventVO);
	public List<EventVO> findAllEvent();
	public EventVO findByPrimaryKey(String event_no);
	public List<EventVO> findWithoutEnd();//扣除已結束的活動
	public String findLastEndEvent();
	
}
