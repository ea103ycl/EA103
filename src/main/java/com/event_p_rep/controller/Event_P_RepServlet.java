package com.event_p_rep.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.event_p.model.*;
import com.event_p_rep.model.*;

import jdbc.util.CompositeQuery.CompositeQuery_Event_P_Rep;


//@WebServlet("/Event_P_RepServlet")
public class Event_P_RepServlet extends HttpServlet {


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action= req.getParameter("action");
		
		if("updateFromlistAllRep".equals(action)) {
			String rep_stat=req.getParameter("rep_stat");
			String event_p_no=req.getParameter("event_p_no");
System.out.println("in updateFromlistAllRep rep_stat: "+rep_stat);
System.out.println("in updateFromlistAllRep event_p_no: "+event_p_no);
			Event_P_RepService repSvc=new Event_P_RepService();
			repSvc.update(new Integer(event_p_no),new Integer(rep_stat));
			
			
			
			//再把repVOs set到sess裡
//			String rep_stat=req.getParameter("rep_stat");
			List<Event_P_RepVO> event_p_repVOs= repSvc.findAllByRepStat(new Integer(rep_stat));
System.out.println("rep_stat: "+rep_stat);
			HttpSession sess=req.getSession();
			sess.setAttribute("event_p_repVOs", event_p_repVOs);//設定剛剛作品選的狀態為依據搜尋結果
			sess.setAttribute("currentChoice", rep_stat);//讓select停在目前所顯示的狀態
			
			Event_PService pSvc=new Event_PService();
			//同時更新event_pVO的stat
			int pStat=1;
			if(rep_stat.equals("2")) pStat=2;
			Event_PVO event_pVO=pSvc.findByPrimaryKey(new Integer(event_p_no));
			pSvc.update(event_pVO.getMem_id(), event_pVO.getEvent_no(), event_pVO.getEvent_p_name(), event_pVO.getEvent_p_date(), event_pVO.getEvent_vote_num(), event_pVO.getVote_rank(),new Integer(pStat), event_pVO.getEvent_p_img(), new Integer(event_p_no));
			List<Event_PVO> event_pVOs=pSvc.findAllNoReport(pSvc.findByPrimaryKey(new Integer(event_p_no)).getEvent_no());
			sess.setAttribute("event_pVOs", event_pVOs);
//			String path="/backend/event_p_rep/listAllRep.jsp";
			RequestDispatcher success=req.getRequestDispatcher(req.getParameter("requestURL"));
			success.forward(req, res);
		}
		if("report".equals(action)){
			List<String> errMsgs=new ArrayList<String>();
			req.setAttribute("errMsgs",errMsgs);
			HttpSession sess=req.getSession();
			//判斷會員是否檢舉過
			String mem_id=req.getParameter("mem_id");
			String event_no=req.getParameter("event_no");
			String event_p_no= req.getParameter("event_p_no");
			Event_P_RepService repSvc=new Event_P_RepService();
			boolean checkReport=repSvc.checkMemReport(mem_id, event_no);
			if(checkReport==true) {
				//檢舉了哪件作品
				Event_P_RepVO event_p_repVO=repSvc.findByMemId(mem_id);
System.out.println((int)event_p_repVO.getEvent_p_no()==((int)(new Integer(event_p_no))));
				if((int)event_p_repVO.getEvent_p_no()==((int)(new Integer(event_p_no)))) {
					errMsgs.add("以檢舉過此作品");
				}
				errMsgs.add("已檢舉過作品");
				req.setAttribute("checkReport", checkReport);
				req.setAttribute("event_p_no", event_p_no);
				String path="/frontend/event_p/event_spec.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req, res);
				return;
			}
			
			//用於跳轉頁面
			Event_PService pSvc=new Event_PService();
			List<Event_PVO> event_pVOs=pSvc.findAllNoReport(event_no);//當有做到增刪改查時，作品都要刷新
			sess.setAttribute("event_pVOs", event_pVOs);
			req.setAttribute("event_p_no", event_p_no);
			String path="/frontend/event_p_rep/report.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
		}
		if("reportpic".equals(action)) {
System.out.println("get reportEvent_P");
			List<String> errMsgs=new ArrayList<String>();
			req.setAttribute("errMsgs",errMsgs);
			
			
			String event_p_no= req.getParameter("event_p_no");						
			String mem_id= req.getParameter("mem_id");
			Timestamp rep_time= new Timestamp(System.currentTimeMillis());//時間寫系統時間
			String rep_reason= req.getParameter("rep_reason");
			if(rep_reason==null||rep_reason.trim().length()==0) {
				errMsgs.add("檢舉內容不可空白");
				
			}
			Integer rep_stat=1;//檢舉狀態預設1(未處裡)
			if(!errMsgs.isEmpty()) {
				req.setAttribute("event_p_no", event_p_no);
System.out.println("reportEvent_p have errMsgs");
				String path="/frontend/event_p_rep/report.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req, res);
				return;
			}
			Event_P_RepVO event_p_repVO=new Event_P_RepVO();
			event_p_repVO.setEvent_p_no(new Integer( event_p_no));
			event_p_repVO.setMem_id(mem_id);
			event_p_repVO.setRep_time(rep_time);
			event_p_repVO.setRep_reason(rep_reason);
			event_p_repVO.setRep_stat(rep_stat);
			
			Event_P_RepService repSvc=new Event_P_RepService();
			repSvc.insert(new Integer( event_p_no), mem_id, rep_time, rep_reason, rep_stat);
System.out.println("success road");
			req.setAttribute("event_p_repVO", event_p_repVO);
			String path="/frontend/event_p/event_spec.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
		}
		if("findAllByEventNo".equals(action)) {
			String event_no=req.getParameter("event_no");
System.out.println("event_no:"+event_no);
			Event_P_RepService repSvc=new Event_P_RepService();
			List<Event_P_RepVO> event_p_repVOs=repSvc.findRepByEventNo(event_no);
			//start forward
			HttpSession sess=req.getSession();
			sess.setAttribute("event_p_repVOs", event_p_repVOs);
			req.setAttribute("event_no", event_no);
			String path="/backend/event_p_rep/listAllRep.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
		}
		if("findAllByRepStat".equals(action)) {
			String rep_stat=req.getParameter("rep_stat");
			
			Event_P_RepService repSvc=new Event_P_RepService();
			List<Event_P_RepVO> event_p_repVOs=repSvc.findAllByRepStat(new Integer(rep_stat));
			
			HttpSession sess=req.getSession();
			sess.setAttribute("event_p_repVOs", event_p_repVOs);
System.out.println("start to forward by findAllByRepStat");
			String path="/backend/event_p_rep/listAllRep.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
		}
		if("compositeQuery".equals(action)) {
			
			Map<String,String[]> map=req.getParameterMap();
//			System.out.println(map.get("rep_stat")[0]);
			String currentChoice=map.get("rep_stat")[0];
			Event_P_RepService repSvc=new Event_P_RepService();
			List<Event_P_RepVO> event_p_repVOs=null;
//			System.out.println(req.getParameter("rep_stat").equals("4"));
			if(req.getParameter("rep_stat").equals("4")) {
				
				event_p_repVOs=repSvc.findAllRep();
				
			}else {
				event_p_repVOs= repSvc.findAllByCompositeQuery(map);
				
			}
			//傳回狀態給頁面的select
			req.setAttribute("currentChoice", currentChoice);
			
			HttpSession sess=req.getSession();
			sess.setAttribute("event_p_repVOs", event_p_repVOs);
			String path="/backend/event_p_rep/listAllRep.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
			
		}
	}
	

}
