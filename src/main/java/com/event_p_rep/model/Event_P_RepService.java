package com.event_p_rep.model;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.event_p.model.*;

public class Event_P_RepService {
	Event_P_RepDAO dao;
	Event_PService pSvc;
	public Event_P_RepService() {
		this.dao=new Event_P_RepDAO();
		this.pSvc=new Event_PService();
	}
	public void insert(Integer event_p_no,String mem_id,Timestamp rep_time,String rep_reason,Integer rep_stat) {
		Event_P_RepVO event_p_repVO=new Event_P_RepVO();
		event_p_repVO.setEvent_p_no(event_p_no);
		event_p_repVO.setMem_id(mem_id);
		event_p_repVO.setRep_time(rep_time);
		event_p_repVO.setRep_reason(rep_reason);
		event_p_repVO.setRep_stat(rep_stat);
		dao.insert(event_p_repVO);
	}
	public void update(Integer event_p_no,Integer rep_stat) {
		dao.update(event_p_no, rep_stat);
		
		//修正event_p的event_p_stat 如果rep_stat=2 event_p_stat=2 其餘event_p_stat=1
		System.out.println("rep_stat.equals(new Integer(2)): "+rep_stat.equals(new Integer(2)));
		if(rep_stat.equals(new Integer(2))) {
			Event_PVO event_pVO=pSvc.findByPrimaryKey(event_p_no);
//			event_pVO.setEvent_p_stat(2);
			pSvc.update(event_pVO.getMem_id(), event_pVO.getEvent_no(), event_pVO.getEvent_p_name(), event_pVO.getEvent_p_date(), event_pVO.getEvent_vote_num(), event_pVO.getVote_rank(),new Integer(2), event_pVO.getEvent_p_img(), event_pVO.getEvent_p_no());

		}else {
			Event_PVO event_pVO=pSvc.findByPrimaryKey(event_p_no);
//			event_pVO.setEvent_p_stat(1);
			pSvc.update(event_pVO.getMem_id(), event_pVO.getEvent_no(), event_pVO.getEvent_p_name(), event_pVO.getEvent_p_date(), event_pVO.getEvent_vote_num(), event_pVO.getVote_rank(), new Integer(1), event_pVO.getEvent_p_img(), event_pVO.getEvent_p_no());
		}
	};//修改狀態
	public void delete(Integer event_p_no,String mem_id){
		dao.delete(event_p_no, mem_id);
	}//前端會員取消檢舉。
	public List<Event_P_RepVO> findAllRep(){
		return dao.findAllRep();
	}
	public List<Event_P_RepVO> findRepByEvent_p_no(Integer event_p_no){
		return dao.findRepByEvent_p_no(event_p_no);
	}
	public boolean checkMemReport(String mem_id, String event_no) {
		return dao.checkMemReport(mem_id, event_no);
	}
	public List<Event_P_RepVO> findRepByEventNo(String event_no){
		return dao.findRepByEventNo(event_no);
	}
	public List<Event_P_RepVO> findAllByRepStat(Integer rep_stat){
		return dao.findAllByRepStat(rep_stat);
	}
	public List<Event_P_RepVO> findAllByCompositeQuery(Map<String, String[]> map){
		return dao.findAllByCompositeQuery(map);
	}
	public Event_P_RepVO findByMemId(String mem_id) {
		return dao.findByMemId(mem_id);
	}
	public Event_P_RepVO findReportByMemAndEventNo(String mem_id, String event_no) {
		return dao.findReportByMemAndEventNo(mem_id, event_no);
	}
//	public int getRepStatTwoChangeNum(String event_no) {
//		return dao.getRepStatTwoChangeNumByEventNo(event_no);
//	}
//	public boolean isRepStatTwoNumChange(String event_no) {
//		if(pSvc.findAllNoReport(event_no).size()!=dao.getRepStatTwoChangeNumByEventNo(event_no)) {
//			System.out.println("");
//			System.out.println("檢舉數量更動");
//			return true;
//		}else {
//			return false;
//		}
//		
//	}
}
