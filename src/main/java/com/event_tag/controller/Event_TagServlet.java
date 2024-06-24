package com.event_tag.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;
import com.event_tag.model.*;

@WebServlet("/Event_TagServlet")
public class Event_TagServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		PrintWriter out=res.getWriter();
		String action = req.getParameter("action");
		if ("insert".equals(action)) {
			List<String> errMsgs = new ArrayList<String>();
			req.setAttribute("errMsgs", errMsgs);
			HttpSession sess=req.getSession();
			try {
				String mem_id = (String)sess.getAttribute("mem_id");
//				String event_no = req.getParameter("event_no");//再新增tag下，活動預設為null

				String event_tag_name = req.getParameter("event_tag_name");
				if (event_tag_name == null || event_tag_name.trim().length() == 0) {
					errMsgs.add("請輸入文字");
					event_tag_name = "";
				}

				// timestamp自己抓
				Timestamp ts = new Timestamp(System.currentTimeMillis());

				Event_TagVO event_tagVO = new Event_TagVO();
				event_tagVO.setMem_id(mem_id);
				event_tagVO.setEvent_no(null);
				event_tagVO.setEvent_tag_name(event_tag_name);
				event_tagVO.setEvent_tag_time(ts);
				if (!errMsgs.isEmpty()) {
					req.setAttribute("event_tagVO", event_tagVO);
					String path = "/frontend/event_p/event_homePage.jsp";
					RequestDispatcher fail = req.getRequestDispatcher(path);
					fail.forward(req, res);
					return;
				}
				Event_TagService svc = new Event_TagService();
				System.out.println("ok");
				svc.insert(mem_id, null, ts, event_tag_name);
				String path = "/frontend/event_p/event_homePage.jsp";
				RequestDispatcher ok = req.getRequestDispatcher(path);
				ok.forward(req, res);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errMsgs.add("其他錯誤");
				String path = "/frontend/event_p/event_homePage.jsp";
				RequestDispatcher fail = req.getRequestDispatcher(path);
				fail.forward(req, res);
			}

		}
		if("findByPrimaryKey".equals(action)) {
			try {
			String event_tag_no=req.getParameter("event_tag_no");
			Event_TagService svc=new Event_TagService();
			Event_TagVO event_tagVO= svc.findByPrimarykey(new Integer(event_tag_no));
			req.setAttribute("event_tagVO", event_tagVO);
			String path="/backend/event_tag/displayOneTag.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		if("randTag".equals(action)) {
			Event_TagService tagSvc=new Event_TagService();
			List<Event_TagVO> event_tagVOs=tagSvc.randTag();
			
			req.setAttribute("event_tagVOs", event_tagVOs);
			String path="/backend/event/TestInsert.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
		}
		if("roll".equals(action)) {
			Event_TagService tagSvc=new Event_TagService();
			List<Event_TagVO> event_tagVOs=tagSvc.randTag();
			System.out.println(event_tagVOs.size());
			Iterator iter=event_tagVOs.iterator();
			String str="";//以字串傳給前端
			if(event_tagVOs.size()==0) {
				str+="沒有HashTag可以抽選";
				System.out.println(str);
			}else {
				while(iter.hasNext()) {
					Event_TagVO event_tagVO=(Event_TagVO)iter.next();
					str+=(str=="")?event_tagVO.getEvent_tag_name():","+event_tagVO.getEvent_tag_name();
					
				}	
			}
			req.setAttribute("str", str);
			out.write(str);
		}
	}

}
