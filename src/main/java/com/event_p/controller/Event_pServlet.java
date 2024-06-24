package com.event_p.controller;

import java.io.BufferedInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.*;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.MultipartConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;



import com.event_p.model.*;
import com.vote_d.model.*;

@MultipartConfig
//@WebServlet("/Event_pServlet")
public class Event_pServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html charset=UTF-8");
		String action = req.getParameter("action");



		if("insert".equals(action)) {			
			Map<String,String> errMsgs=new HashMap<String,String>();
			req.setAttribute("errMsgs", errMsgs);
			HttpSession sess=req.getSession();
System.out.println("");
System.out.println("imgBytes is null?"+(((byte[])sess.getAttribute("imgBytes"))==null));
			try {

				//�|���s��
				String mem_id= req.getParameter("mem_id");
//				String mem_idReg="^[M][0-9]{6}$";
//				if(mem_id==null||mem_id.trim().length()==0) {
//					errMsgs.add("�|���s���ФŪť�");				
//				}else if(!mem_id.trim().matches(mem_idReg)) {
//					errMsgs.add("�HM���}�Y6�ӼƦr");
//				}
				
				//���ʽs��
				String event_no=req.getParameter("event_no");
				String regex="^[E]{1}[0-9]{6}$";
				System.out.println(event_no);
				if(event_no==null||event_no.trim().length()==0) {
					errMsgs.put("event_no","�ФŪť�");
					event_no="���ʽs��";
				}else if(!event_no.trim().matches(regex)){
					errMsgs.put(event_no,"�п�J�}�Y��E�`�@6�ӼƦr");
					event_no="���ʽs��";
				}
				//�@�~�W��
				String event_p_name=req.getParameter("event_p_name");
				if(event_p_name==null||event_p_name.trim().length()==0) {
					errMsgs.put("event_p_name","�ФŪť�");
					event_p_name="�@�~�W��";
				}
				
				
				//�W�Ǯɨt�ήɶ�
				java.sql.Timestamp ts=new java.sql.Timestamp(System.currentTimeMillis());//�s�W�Ϥ��ɶ�
				
				//�@�~�벼��
				String event_vote_num=req.getParameter("event_vote_num");
				if(event_vote_num==null||event_vote_num.trim().length()==0) {				
					event_vote_num="0";
				}
				//�벼�Ʀ�
				String vote_rank=req.getParameter("vote_rank");
				if(vote_rank==null||vote_rank.trim().length()==0) {				
					vote_rank="0";//���s�ɥN��S�Q�벼
				}
				//��Z�@�~���A
				String event_p_stat=req.getParameter("event_p_stat");
				if(event_p_stat==null||event_p_stat.trim().length()==0) {				
					event_p_stat="1";//���s�ɥN��i�H�Q�벼
				}
				
				
				Part part=req.getPart("pic01");				
				
	//			String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	//			System.out.println(fileName);//test���ɮצW��
				InputStream fileContent = part.getInputStream();
				ByteArrayOutputStream baos=new ByteArrayOutputStream();
				byte[] buf=new byte[8192];
				int i=0;
				while((i=fileContent.read(buf))!=-1) {
					baos.write(buf,0,i);
				}
				byte[] img=null;
				img=baos.toByteArray();//��Ƥwbyte�}�C�^��
				if(img.length==0) {
					if((byte[])sess.getAttribute("imgBytes")!=null) {
						img=(byte[])sess.getAttribute("imgBytes");
						sess.setAttribute("imgBytes",null);//�걼�Ϥ�byte
					}else {
						errMsgs.put("img","�ФW�ǹϤ�");
					}
					
				}
				Event_PVO event_pVO=new Event_PVO();
				event_pVO.setMem_id(mem_id);
				event_pVO.setEvent_no(event_no);
				event_pVO.setEvent_p_name(event_p_name);
				event_pVO.setEvent_p_date(ts);
				event_pVO.setEvent_vote_num(new Integer(event_vote_num));
				event_pVO.setVote_rank(new Integer(vote_rank));
				event_pVO.setEvent_p_stat(new Integer(event_p_stat));
				event_pVO.setEvent_p_img(img);
				
				if(!errMsgs.isEmpty()) {
					req.setAttribute("event_pVO", event_pVO);
					//����Ϥ�byte[]
					
					String path="/frontend/event_p/TestInsert.jsp";
					RequestDispatcher fail=req.getRequestDispatcher(path);
					fail.forward(req, res);
					return;
				}
				Event_PService svc=new Event_PService();
				svc.insert(mem_id,event_no,event_p_name, ts,new Integer(event_vote_num),new Integer(vote_rank),new Integer(event_p_stat), img);
				List<Event_PVO> event_pVOs=svc.findAllNoReport(event_no);
				
				sess.setAttribute("event_pVOs", event_pVOs);
				System.out.println("event_pServlet action insert start forward");
				String path="/frontend/event_p/event_spec.jsp";
				RequestDispatcher success=req.getRequestDispatcher(path);
				success.forward(req, res);
			
			}catch(Exception e) {
				e.printStackTrace();				
				errMsgs.put("another","�������~");
				String path="/frontend/event_p/TestInsert.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req, res);
				return;
			}
			
		}

		if ("findAllPic".equals(action)) {
			List<String> errMsgs = new ArrayList<String>();
			req.setAttribute("errMsgs", errMsgs);
			try {
				Event_PDAO dao = new Event_PDAO();
				List<Event_PVO> event_pVOs = dao.findAllPic();
				// ��X��r�A�Ϥ��Υt�@��servlet��X
				req.setAttribute("event_pVOs", event_pVOs);
				// �ǰe��listALLPic.jsp
				String path = "/backend/event_p/listAllPic.jsp";
				RequestDispatcher success = req.getRequestDispatcher(path);
				success.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace();
				errMsgs.add("�������~");
				String path = "/backend/event_p/select_page.jsp";
				RequestDispatcher fail = req.getRequestDispatcher(path);
				fail.forward(req, res);
			}

		}
		//from listAll
		if("updateFromListAll".equals(action)) {
			String event_p_noStr=(String)req.getParameter("event_p_no");
			
			Integer event_p_no=new Integer(event_p_noStr);
			
			Event_PService svc=new Event_PService();			
			Event_PVO event_pVO=svc.findByPrimaryKey(event_p_no);
			req.setAttribute("event_pVO", event_pVO);
			String path="/backend/event_p/event_pUpdate.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
		}
		
		//update
		if("update".equals(action)) {
			List<String> errMsgs=null;			
			Event_PVO event_pVO=null;
			String event_p_no=req.getParameter("event_p_no");
			Event_PService svc=new Event_PService();
			//get pass event_pVO by event_P_no
			Event_PVO event_pVOPass= svc.findByPrimaryKey(new Integer( event_p_no));
			
			try {
			errMsgs=new ArrayList<String>();
			req.setAttribute("errMsgs", errMsgs);
			
			String mem_id=req.getParameter("mem_id");
			String event_no= req.getParameter("event_no");
			String event_p_name=req.getParameter("event_p_name");
			if(event_p_name==null||event_p_name.trim().length()==0) {
				errMsgs.add("�@�~�W�ٽФŪť�");
				event_p_name=event_pVOPass.getEvent_p_name();
			}
			
			String event_vote_num=req.getParameter("event_vote_num");
			if(event_vote_num==null||event_vote_num.trim().length()==0) {
				errMsgs.add("�п�J�Ʀr");
				event_vote_num=event_pVOPass.getEvent_vote_num().toString();
			}
			String vote_rank=req.getParameter("vote_rank");
			if(event_vote_num==null||event_vote_num.trim().length()==0) {
				errMsgs.add("�п�J�Ʀr");
				vote_rank=event_pVOPass.getVote_rank().toString();
			}
			
			String event_p_stat=req.getParameter("event_p_stat");
//			System.out.println("event_p_stat:" +event_p_stat);
			Timestamp event_p_date=new Timestamp(System.currentTimeMillis());
			byte[] event_p_img=event_pVOPass.getEvent_p_img();//�]�w���쥻�Ϥ�
//			Part part=req.getPart("pic01");
//
//			InputStream is=part.getInputStream();
//			ByteArrayOutputStream baos=new ByteArrayOutputStream();
//			byte[] buf=new byte[1024];
//			byte[] event_p_img=null;
//			int i=0;
//			while((i=is.read(buf))!=-1) {
//				baos.write(buf);
//			}
			
//			
//			event_p_img=baos.toByteArray();
//			System.out.println("event_p_img.length: "+event_p_img.length);
//			if(event_p_img.length==0) {
//				System.out.println("img is null");
//				System.out.println(event_pVOPass.getEvent_p_img().length==0);
//				event_p_img=event_pVOPass.getEvent_p_img();//�]�w���쥻�Ϥ�
//			}
			
			event_pVO=new Event_PVO();
			event_pVO.setEvent_p_no(new Integer( event_p_no));
			event_pVO.setMem_id(mem_id);
			event_pVO.setEvent_no(event_no);
			event_pVO.setEvent_p_name(event_p_name);
			System.out.println(event_p_name);//test
			event_pVO.setEvent_p_date(event_p_date);
			event_pVO.setEvent_vote_num(new Integer( event_vote_num));
			event_pVO.setVote_rank(new Integer( vote_rank));
			event_pVO.setEvent_p_stat(new Integer( event_p_stat));
			event_pVO.setEvent_p_img(event_p_img);
			if(!errMsgs.isEmpty()) {
				System.out.println("errMsgs");
				req.setAttribute("event_pVO", event_pVO);
				String path="/backend/event_p/event_pUpdate.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req,res);
				return;
			}
			System.out.println("success");
//			Event_PService svc=new Event_PService();
			svc.update(mem_id, event_no, event_p_name, event_p_date, new Integer( event_vote_num), new Integer( vote_rank), new Integer( event_p_stat), event_p_img, new Integer( event_p_no));
			//��e���
//			HttpSession sess=req.getSession();
//			List<Event_PVO> event_pVOs=svc.findAllByEventNo(event_no);
//			sess.setAttribute("SearchByEventNo", event_no);
//			sess.setAttribute("event_pVOs", event_pVOs);
//			String path="/backend/event_p/listAllPic.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(req.getParameter("requestURL"));
			ok.forward(req, res);
			}catch(Exception e) {
				e.printStackTrace();
				req.setAttribute("event_pVO", event_pVO);
				errMsgs.add("�X�{��L���~");				
				String path="/backend/event_p/event_pUpdate.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req,res);
				return;
			}
			
		}
		if("findByPrimaryKey".equals(action)) {
			
			List<String> errMsgs=new ArrayList<String>();
			req.setAttribute("errMsgs", errMsgs);
			
			String event_p_no= req.getParameter("event_p_no");
			Event_PService svc=new Event_PService();
			Event_PVO event_pVO=svc.findByPrimaryKey(new Integer(event_p_no));
			req.setAttribute("event_pVO", event_pVO);
			String path="/backend/event_p/displayOne.jsp";
			RequestDispatcher success=req.getRequestDispatcher(path);
			success.forward(req, res);
		}
		if("vote".equals(action)) {
			System.out.println("start vote action-------------");
			System.out.println("in vote");
			List<String> errMsgs=new ArrayList<String>();
			req.setAttribute("errMsgs", errMsgs);
			
			String event_p_no= req.getParameter("event_p_no");
			//�P�_�b�o�����ʦ��S���W�L�T���A�n�ǤJmem_id�Mevent_no
			String event_no=req.getParameter("event_no");
			String mem_id=req.getParameter("mem_id");
			System.out.println("event_no :"+event_no+",mem_id :"+mem_id);
			
			Vote_DDAO dao=new Vote_DDAO();
			Integer voteNum=dao.voteNumByMemInEventno(event_no, mem_id);
			//�P�_ �벼���Ƥ���W�W�w���ơA�����ۦP�@�~
			List<Vote_DVO>vote_dVOs= dao.findAllByMem(mem_id);
			boolean checkDup=false;
			Iterator iter=vote_dVOs.iterator();
			while(iter.hasNext()) {
				Vote_DVO vote_dVO=(Vote_DVO)iter.next();
				Integer event_p_noInt=new Integer(event_p_no);
				
				if((int)event_p_noInt==(int)vote_dVO.getEvent_p_no()) {
					 checkDup=true;
					 System.out.println("checkDup change to true �H���Ƨ�P�@�ӧ@�~");
					 
				 }
			
			}
			//�P�@�Ӭ��ʥu���3�Ӥ��P�@�~
			if(voteNum<4&&!checkDup) {
				Event_PService svc=new Event_PService();
				svc.votePic(new Integer(event_p_no),mem_id);
				System.out.println("success vote!");
				
				//���ܥثesess����event_pVOs
				HttpSession sess=req.getSession();
				
				List<Event_PVO> event_pVOs=svc.findAllNoReport(event_no);
				sess.setAttribute("event_pVOs", event_pVOs);
				
				String path="/frontend/event_p/event_spec.jsp";
				RequestDispatcher ok=req.getRequestDispatcher(path);
				ok.forward(req,res);
				return;
			}else {
				//�Q�ο��~�T���ӳq���w�F��벼���ƤW��

				if(voteNum.equals(4)) {
					errMsgs.add("�F��벼�ƤW��(�̦h��3��)");
					
					
					
				}
				if(checkDup) {
					errMsgs.add("�w��L���@�~");
					
				}
				if(!errMsgs.isEmpty()) {
					//�^��event_p_no�N��H��L���@�~�s��
					req.setAttribute("event_p_no", event_p_no);
					
					RequestDispatcher fail=req.getRequestDispatcher(req.getParameter("requestURL"));
					fail.forward(req, res);
					return;
				}else {
					errMsgs.add("�������~");
					System.out.println("voteNum:"+voteNum);
					req.setAttribute("event_p_no", event_p_no);
					RequestDispatcher fail=req.getRequestDispatcher("/frontend/event_p/event_spec.jsp");
					fail.forward(req, res);
				}
			}

		}
		if("vote_from_ajax".equals(action)) {

			System.out.println("start vote ajax action-------------");
			System.out.println("in vote");
			
			String event_p_no= req.getParameter("event_p_no");
			System.out.println("event_p_no :"+event_p_no);
			//�P�_�b�o�����ʦ��S���W�L�T���A�n�ǤJmem_id�Mevent_no
			String event_no=req.getParameter("event_no");
			String mem_id=req.getParameter("mem_id");
			System.out.println("event_no :"+event_no+",mem_id :"+mem_id);
			
			Vote_DDAO dao=new Vote_DDAO();
			Integer voteNum=dao.voteNumByMemInEventno(event_no, mem_id);
			//�P�_ �벼���Ƥ���W�W�w���ơA�����ۦP�@�~
			List<Vote_DVO>vote_dVOs= dao.findAllByMem(mem_id);
			boolean checkDup=false;
			Iterator iter=vote_dVOs.iterator();
			while(iter.hasNext()) {
				Vote_DVO vote_dVO=(Vote_DVO)iter.next();
				Integer event_p_noInt=new Integer(event_p_no);
				
				if((int)event_p_noInt==(int)vote_dVO.getEvent_p_no()) {
					 checkDup=true;
					 System.out.println("checkDup change to true �H���Ƨ�P�@�ӧ@�~");
					 
				 }
			
			}
			System.out.println("voteNum :"+voteNum);
			//�P�@�Ӭ��ʥu���3�Ӥ��P�@�~
			if(voteNum<3&&!checkDup) {
				Event_PService svc=new Event_PService();
				svc.votePic(new Integer(event_p_no),mem_id);
				System.out.println("success vote by ajax!");
				//���ܥثesess����event_pVOs
				HttpSession sess=req.getSession();
				Event_PService pSvc=new Event_PService();
				List<Event_PVO> event_pVOs=pSvc.findAllNoReport(event_no);
				sess.setAttribute("event_pVOs", event_pVOs);
				
				PrintWriter out=res.getWriter();
				String strSuccess="0";
				out.write(strSuccess);
				return;
			}else {
				//�Q�ο��~�T���ӳq���w�F��벼���ƤW��
				PrintWriter out=res.getWriter();
				res.setCharacterEncoding("UTF-8");
				String strErr="";
				if(voteNum==3) {
					System.out.println(voteNum);
					strErr+="1";//�F��벼�ƤW��(�̦h��3��)
					
				}
				if(checkDup) {
					strErr+=","+"2";//�w��L���@�~
					
				}
				if(!(strErr.length()==0)) {
					out.write(strErr);
				}else {
					strErr+="�������~";
					out.write(strErr);
				}
			}

					
		}
		
		if("to_event_sepc".equals(action)) {
			System.out.println("�ǳ���e��event_spec:-----------------------");
			String event_no=req.getParameter("event_no");
			req.setAttribute("event_no", event_no);
			String path="/frontend/event_p/event_spec.jsp";
			RequestDispatcher success=req.getRequestDispatcher(path);
			success.forward(req, res);
		}
		if("updateUploadPic".equals(action)) {
			List<String> errMsgs=null;			
			Event_PVO event_pVO=null;
			HttpSession sess=req.getSession();
			Event_PService svc=null;
			try {
			errMsgs=new ArrayList<String>();
			req.setAttribute("errMsgs", errMsgs);
			svc=new Event_PService();//��mem_id�d��VO
			String mem_id= req.getParameter("mem_id");
			String event_no=(String)sess.getAttribute("event_no");
			event_pVO=svc.findUploadByMemid(mem_id,event_no);
			//update img
			Part part=req.getPart("pic01");
			InputStream in=part.getInputStream();
			BufferedInputStream bis=new BufferedInputStream(in);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			byte[] buf=new byte[4096];
			int i=0;
			while((i=bis.read(buf))!=-1) {
				baos.write(buf);
			}
			byte[] event_p_img=baos.toByteArray();
			//update event_p_name
			String event_p_name=req.getParameter("event_p_name");
			if(event_p_name==null||event_p_name.trim().length()==0) {
				errMsgs.add("�@�~�W�٤��i�ť�");
				event_p_name=event_pVO.getEvent_p_name();//�^�_��W����Z�W��
			}else if(event_p_name.trim().length()>20) {
				errMsgs.add("�@�~�W�ٹL���A�W�٤���W�L20�r");
				event_p_name=event_pVO.getEvent_p_name();
			}
			
			
			if(event_p_img.length==0) {
				System.out.println("img is null");
				System.out.println(event_pVO.getEvent_p_img().length==0);
				event_p_img=event_pVO.getEvent_p_img();//�]�w���쥻�Ϥ�event_pVO���W�����Ϥ�
			}
					

			if(!errMsgs.isEmpty()) {
				System.out.println("errMsgs");
				req.setAttribute("event_pVO", event_pVO);
				String path="/frontend/event_p/event_p_update.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req,res);
				return;
			}
			event_pVO.setEvent_p_name(event_p_name);
			event_pVO.setEvent_p_img(event_p_img);
			svc.update(mem_id, event_pVO.getEvent_no(), event_pVO.getEvent_p_name(), event_pVO.getEvent_p_date(),event_pVO. getEvent_vote_num(),  event_pVO.getVote_rank(), event_pVO.getEvent_p_stat(), event_pVO.getEvent_p_img(),event_pVO.getEvent_p_no());
			//�]�w�@��event_pVOs��event_spec
			
			sess.setAttribute("event_pVOs", svc.findAllNoReport(event_no));
			String path="/frontend/event_p/event_spec.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
			}catch(Exception e) {
				e.printStackTrace();
				req.setAttribute("event_pVO", event_pVO);
				errMsgs.add("�X�{��L���~");				
				String path="/frontend/event_p/event_p_update.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req,res);
				return;
			}
		}
		if("change_to_update".equals(action)) {
			HttpSession sess=req.getSession();
			String mem_id= req.getParameter("mem_id");
			String event_no=(String)sess.getAttribute("event_no");
			Event_PService svc=new Event_PService();
			Event_PVO event_pVO=svc.findUploadByMemid(mem_id,event_no);
			req.setAttribute("event_pVO", event_pVO);
			String path="/frontend/event_p/event_p_update.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req,res);
			
		}
		if("delete".equals(action)) {
			System.out.println("in delete");
			try {
			String event_p_no= req.getParameter("event_p_no");
			String event_no=req.getParameter("event_no");
			System.out.println(event_p_no);
			Event_PService pSvc=new Event_PService();
			pSvc.delete(new Integer(event_p_no));
			HttpSession sess=req.getSession();
			
//			List<Event_PVO> event_pVOs=pSvc.findAllNoReport(req.getParameter("event_no").trim());
//			sess.setAttribute("event_pVOs", event_pVOs);
			System.out.println("Event_pServlet action delete start forward");
			//���s�]�w�@��event_pVOs�bsession
			
			sess.setAttribute("event_pVOs", pSvc.findAllByEventNo((String)sess.getAttribute("event_no")));
			//sess ���s�]�w
//			HttpSession sess=req.getSession();
			List<Event_PVO> event_pVOs=pSvc.findAllNoReport(event_no);
			sess.setAttribute("event_pVOs",event_pVOs);
			String path="/frontend/event_p/event_spec.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
			}catch(Exception e) {
				e.printStackTrace();
				String path="/frontend/event_p/event_homePage.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(path);
				fail.forward(req, res);
			}
		}
		if("deleteVote".equals(action)) {
			List<String> errMsgs=new ArrayList<String>();
			req.setAttribute("errMsgs", errMsgs);
			//���o mem_id event_p_no
			String mem_id= req.getParameter("mem_id");
			String event_p_no=req.getParameter("event_p_no");
			//�P�_vote���Ӹ̭��O�_������ �A���~�i���R��
			Vote_DService voteSvc=new Vote_DService();
			Event_PService eventPSvc=new Event_PService();
			List<Vote_DVO> vote_dVOs=voteSvc.findAllByMem(mem_id);
			Iterator iter=vote_dVOs.iterator();
			System.out.println("vote_dVOs.isEmpty() :"+vote_dVOs.isEmpty());
			boolean hadVoted=false;
			while(iter.hasNext()) {
				Vote_DVO vote_dVO=(Vote_DVO)iter.next();
				System.out.println("vote_dVO.getEvnet_p_no() :"+vote_dVO.getEvent_p_no());
				if((int)(new Integer(event_p_no))==(int)vote_dVO.getEvent_p_no()) {
					System.out.println("same start delete");
					hadVoted=true;
				}
			}
			if(hadVoted) {
				eventPSvc.deleteVote(new Integer(event_p_no));
				voteSvc.delete(new Integer(event_p_no), mem_id);
			}else {
				System.out.println("�|���벼�L���@�~");
				errMsgs.add("���벼�L�����~");
			}
			
			if(!errMsgs.isEmpty()) {
				//�^��event_p_no ��ܦb���T���@�~�W
				req.setAttribute("event_p_no", event_p_no);
//				String path="/frontend/event_p/event_spec.jsp";
				RequestDispatcher fail=req.getRequestDispatcher(req.getParameter("requestURL"));
				fail.forward(req, res);
				return;
			}
			//���s�]�wsession
			String event_no=req.getParameter("event_no");
			HttpSession sess=req.getSession();
			Event_PService pSvc=new Event_PService();
			List<Event_PVO> event_pVOs=pSvc.findAllNoReport(event_no);
			sess.setAttribute("event_pVOs", event_pVOs);
			//��������
			String path="/frontend/event_p/event_spec.jsp";//���� ����令event_spec
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
		}
		
		
		if("delete_vote_ByAjax".equals(action)) {
			PrintWriter out=res.getWriter();
			System.out.println("in action: delete_vote_ByAjax-----------------");
			//���o mem_id event_p_no
			String mem_id= req.getParameter("mem_id");
			String event_p_no=req.getParameter("event_p_no");
			//�P�_vote���Ӹ̭��O�_������ �A���~�i���R��
			Vote_DService voteSvc=new Vote_DService();
			Event_PService eventPSvc=new Event_PService();
System.out.println("mem_id:"+mem_id);
			List<Vote_DVO> vote_dVOs=voteSvc.findAllByMem(mem_id);
			Iterator iter=vote_dVOs.iterator();
			System.out.println("vote_dVOs.isEmpty() :"+vote_dVOs.isEmpty());
			boolean hadVoted=false;
			
			while(iter.hasNext()) {
				Vote_DVO vote_dVO=(Vote_DVO)iter.next();
				System.out.println("vote_dVO.getEvnet_p_no() :"+vote_dVO.getEvent_p_no());
				if((int)(new Integer(event_p_no))==(int)vote_dVO.getEvent_p_no()) {
					System.out.println("same start delete");
					hadVoted=true;
				}
			}
			if(hadVoted) {
				System.out.println("start to delete vote detail");
				eventPSvc.deleteVote(new Integer(event_p_no));
				voteSvc.delete(new Integer(event_p_no), mem_id);
				out.write("0");
			}else {
				String errStr="1";
				out.write(errStr);
			}

		
		}
		
		
		if("selectOrderBy".equals(action)) {
			HttpSession sess=req.getSession();
			String event_no=(String)sess.getAttribute("event_no");
			String colName=req.getParameter("colName");

			Event_PService pSvc=new Event_PService();
			List<Event_PVO> event_pVOs=pSvc.findAllOrderBySelect(event_no,colName);
			
			
			sess.setAttribute("event_pVOs", event_pVOs);
			String path="/frontend/event_p/event_spec.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
		}
		if("findAllByEventNo".equals(action)) {
			String event_no=req.getParameter("event_no");
			Event_PService pSvc=new Event_PService();
			List<Event_PVO> event_pVOs= pSvc.findAllByEventNo(event_no);
			HttpSession sess=req.getSession();
			sess.setAttribute("SearchByEventNo", event_no);
			sess.setAttribute("event_pVOs", event_pVOs);
			String path="/backend/event_p/listAllPic.jsp";
			RequestDispatcher ok=req.getRequestDispatcher(path);
			ok.forward(req, res);
		}
		if("listAll".equals(action)) {
			Event_PService pSvc=new Event_PService();
			List<Event_PVO>event_pVOs=pSvc.finAllPic();
			HttpSession sess=req.getSession();
			sess.setAttribute("event_pVOs", event_pVOs);
			RequestDispatcher ok=req.getRequestDispatcher("/backend/event_p/listAllPic.jsp");
			ok.forward(req, res);
			
		}
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
