package com.meeting.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.meeting.model.MeetingVO;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;

import tools.MoneyTool;

import com.meeting.model.*;
@MultipartConfig
public class MeetingServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("mt_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入見面會編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String mt_no = null;
				try {
					mt_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("見面會編號不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				MeetingService meetingSvc = new MeetingService();
				MeetingVO meetingVO = meetingSvc.getOneMeeting(mt_no);
				if (meetingVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("meetingVO", meetingVO); // 資料庫取出的meetingVO物件,存入req
				String url = "/frontend/meeting/listOneMeeting.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMeeting.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String mt_no = new String(req.getParameter("mt_no"));
				
				/***************************2.開始查詢資料****************************************/
				MeetingService meetingSvc = new MeetingService();
				MeetingVO meetingVO = meetingSvc.getOneMeeting(mt_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("meetingVO", meetingVO);         // 資料庫取出的empVO物件,存入req
				String url = "/frontend/meeting/update_meeting_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/meeting/listAllMeeting.jsp");
				failureView.forward(req, res);
			}
		}
		
//		if ("getOne_For_Reg".equals(action)) { // 來自listAllEmp.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				/***************************1.接收請求參數****************************************/
//				String mem_id = new String(req.getParameter("mem_id"));
//				
//				/***************************2.開始查詢資料****************************************/
//				MeetingService meetingSvc = new MeetingService();
//				MeetingVO meetingVO = meetingSvc.getOneMeeting(mem_id);
//								
//				/***************************3.查詢完成,準備轉交(Send the Success view)************/
//				req.setAttribute("meetingVO", meetingVO);         // 資料庫取出的empVO物件,存入req
//				String url = "/frontend/reg_inf/listAllReg_inf.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/frontend/meeting/listAllMeeting.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mt_no = new String(req.getParameter("mt_no").trim());
				
				String mem_id = req.getParameter("mem_id");
				String mem_idReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("會員ID: 請勿空白");
				} else if(!mem_id.trim().matches(mem_idReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員ID: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				Integer max_num = null;
				try {
					max_num = new Integer(req.getParameter("max_num").trim());
				} catch (NumberFormatException e) {
					max_num = 0;
					errorMsgs.add("人數請填數字.");
				}
				
				Integer min_num = null;
				try {
					min_num = new Integer(req.getParameter("min_num").trim());
				} catch (NumberFormatException e) {
					min_num = 0;
					errorMsgs.add("人數請填數字.");
				}
				
				String mt_place = req.getParameter("mt_place");
				String mt_placeReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (mt_place == null || mt_place.trim().length() == 0) {
					errorMsgs.add("地點: 請勿空白");
				} else if(!mt_place.trim().matches(mt_placeReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("地點: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				Integer ri_fee = null;
				try {
					ri_fee = new Integer(req.getParameter("ri_fee").trim());
				} catch (NumberFormatException e) {
					ri_fee = 0;
					errorMsgs.add("報名費請填數字.");
				}
				
				String mt_detail = req.getParameter("mt_detail");
				String mt_detailReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (mt_detail == null || mt_detail.trim().length() == 0) {
					errorMsgs.add("活動簡介: 請勿空白");
				} else if(!mt_detail.trim().matches(mt_detailReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("活動簡介: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
//				
				java.sql.Timestamp mt_start_time = null;
				try {
					mt_start_time = java.sql.Timestamp.valueOf(req.getParameter("mt_start_time").trim());
				} catch (IllegalArgumentException e) {
					mt_start_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				java.sql.Timestamp mt_end_time = null;
				try {
					mt_end_time = java.sql.Timestamp.valueOf(req.getParameter("mt_end_time").trim());
				} catch (IllegalArgumentException e) {
					mt_end_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				java.sql.Timestamp mt_time = null;
				try {
					mt_time = java.sql.Timestamp.valueOf(req.getParameter("mt_time").trim());
				} catch (IllegalArgumentException e) {
					mt_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				String mt_id = req.getParameter("mt_id");
				String mt_idReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (mt_id == null || mt_id.trim().length() == 0) {
					errorMsgs.add("活動名稱: 請勿空白");
				} else if(!mt_id.trim().matches(mt_idReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("活動名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
		
				Part part = req.getPart("mt_pic");
				InputStream in = part.getInputStream();
				byte[] mt_pic = new byte[in.available()];
				in.read(mt_pic);
				in.close();

				MeetingVO meetingVO = new MeetingVO();
				meetingVO.setMt_no(mt_no);
				meetingVO.setMem_id(mem_id);
				meetingVO.setMax_num(max_num);
				meetingVO.setMin_num(min_num);
				meetingVO.setMt_place(mt_place);
				meetingVO.setRi_fee(ri_fee);
				meetingVO.setMt_detail(mt_detail);
				meetingVO.setMt_start_time(mt_start_time);
				meetingVO.setMt_end_time(mt_end_time);
				meetingVO.setMt_time(mt_time);
				meetingVO.setMt_id(mt_id);
				meetingVO.setMt_pic(mt_pic);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("meetingVO", meetingVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/update_meeting_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MeetingService meetingSvc = new MeetingService();
				meetingVO = meetingSvc.updateMeeting(mt_no,mem_id,max_num, min_num, mt_place,ri_fee,mt_detail,mt_start_time,mt_end_time,mt_time,mt_id,mt_pic);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("meetingVO", meetingVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/frontend/meeting/listOneMeeting.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/meeting/update_meeting_input.jsp");
				failureView.forward(req, res);
		}
		}
	
		
		if ("getOne_For_Reg".equals(action)) { // 來自listoneMeeting.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String mt_no = new String(req.getParameter("mt_no"));
				
				/***************************2.開始查詢資料****************************************/
				MeetingService meetingSvc = new MeetingService();
				MeetingVO meetingVO = meetingSvc.getOneMeeting(mt_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("meetingVO", meetingVO);         // 資料庫取出的empVO物件,存入req
				String url = "/frontend/reg_inf/addReg_inf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/meeting/listOneMeeting.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				String mem_id = req.getParameter("mem_id");
				String mem_idReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("會員ID: 請勿空白");
				} else if(!mem_id.trim().matches(mem_idReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員ID: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				Integer max_num = null;
				try {
					max_num = new Integer(req.getParameter("max_num").trim());
				} catch (NumberFormatException e) {
					max_num = 0;
					errorMsgs.add("人數不為0");
				} 
				
				
				Integer min_num = null;
				try {
					min_num = new Integer(req.getParameter("min_num").trim());
				} catch (NumberFormatException e) {
					min_num = 0;
					errorMsgs.add("人數不為0");
				}
				
				if (min_num == null || min_num == 0) {
					errorMsgs.add("最大人數: 請勿空白");
				} else if (max_num == null || max_num == 0) {
					errorMsgs.add("最大人數: 請勿空白");
				} else if (max_num < min_num) {
					errorMsgs.add("最大人數需大於最少人數");
				} 
				
				
				
				
				
				
				
				
				String mt_place = req.getParameter("mt_place");
				String mt_placeReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (mt_place == null || mt_place.trim().length() == 0) {
					errorMsgs.add("地點: 請勿空白");
				} else if(!mt_place.trim().matches(mt_placeReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("地點: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				Integer ri_fee = null;
				try {
					ri_fee = new Integer(req.getParameter("ri_fee").trim());
				} catch (NumberFormatException e) {
					ri_fee = 0;
					errorMsgs.add("薪水請填數字.");
				}
				
				String mt_detail = req.getParameter("mt_detail");
				String mt_detailReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (mt_detail == null || mt_detail.trim().length() == 0) {
					errorMsgs.add("活動簡介: 請勿空白");
				} else if(!mt_place.trim().matches(mt_detailReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("活動簡介: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				java.sql.Timestamp mt_start_time = null;
				try {
					mt_start_time = java.sql.Timestamp.valueOf(req.getParameter("mt_start_time").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入開始報名日期!");
				}
				
				java.sql.Timestamp mt_end_time = null;
				try {
					mt_end_time = java.sql.Timestamp.valueOf(req.getParameter("mt_end_time").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入報名截止日期!");
				}
				
				java.sql.Timestamp mt_time = null;
				try {
					mt_time = java.sql.Timestamp.valueOf(req.getParameter("mt_time").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入活動日期!");
				}
			
				String mt_id = req.getParameter("mt_id");
				String mt_idReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (mt_detail == null || mt_id.trim().length() == 0) {
					errorMsgs.add("活動簡介: 請勿空白");
				} else if(!mt_id.trim().matches(mt_idReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("活動簡介: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				Integer mt_num = 0;

				Integer mt_status = 0;
				Part part = req.getPart("mt_pic");
				InputStream in = part.getInputStream();
				byte[] mt_pic = new byte[in.available()];
				in.read(mt_pic);
				in.close();

				MeetingVO meetingVO = new MeetingVO();
				
				meetingVO.setMem_id(mem_id);
				meetingVO.setMt_status(mt_status);
				meetingVO.setMax_num(max_num);
				meetingVO.setMin_num(min_num);
				meetingVO.setMt_place(mt_place);
				meetingVO.setRi_fee(ri_fee);
				meetingVO.setMt_detail(mt_detail);
				meetingVO.setMt_start_time(mt_start_time);
				meetingVO.setMt_end_time(mt_end_time);
				meetingVO.setMt_time(mt_time);
				meetingVO.setMt_id(mt_id);
				meetingVO.setMt_num(mt_num);
				meetingVO.setMt_pic(mt_pic);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("meetingVO", meetingVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/addMeeting.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				
				/***************************2.開始新增資料***************************************/
//	1.random->123
//	2.email->123
//	3.123->456->DB
				
				
				MeetingService meetingSvc = new MeetingService();
				meetingVO = meetingSvc.addMeeting(mem_id, mt_status,max_num, min_num, mt_place,ri_fee, mt_detail,mt_start_time,mt_end_time,mt_time,mt_id,mt_num,mt_pic);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/frontend/meeting/listAllMeeting_front.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/meeting/addMeeting.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String mt_no = new String(req.getParameter("mt_no"));
				
				/***************************2.開始刪除資料***************************************/
				MeetingService meetingSvc = new MeetingService();
				meetingSvc.deleteMeeting(mt_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/frontend/meeting/listAllMeeting.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/meeting/listAllMeeting.jsp");
				failureView.forward(req, res);
			}
		}
			if ("cancel_meeting".equals(action)) { // 來自search_mem_meeting.jsp的請求
				
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					String mt_no = new String(req.getParameter("mt_no").trim());
					Integer mt_status = 2;

					MeetingVO meetingVO = new MeetingVO();
					
					meetingVO.setMt_status(mt_status);
					meetingVO.setMt_no(mt_no);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("meetingVO", meetingVO); // 含有輸入格式錯誤的empVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/frontend/meeting/search_mem_meeting.jsp");
						failureView.forward(req, res);
						return; //程式中斷
					}
					
					/***************************2.開始修改資料*****************************************/
					MeetingService meetingSvc = new MeetingService();
					meetingVO = meetingSvc.cancelMeeting(mt_status,mt_no);
					System.out.println(mt_no);
					System.out.println(mt_status);
					/***************************3.修改完成,準備轉交(Send the Success view)*************/
					req.setAttribute("meetingVO", meetingVO); // 資料庫update成功後,正確的的empVO物件,存入req
					
					String url = "/frontend/meeting/search_mem_meeting.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
					successView.forward(req, res);

					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					errorMsgs.add("修改資料失敗:"+e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/search_mem_meeting.jsp");
					failureView.forward(req, res);
			}
			}
               if ("sharing_meeting".equals(action)) { // 來自search_mem_meeting.jsp的請求
				
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				HttpSession session1 = req.getSession();

				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					String mt_no = new String(req.getParameter("mt_no").trim());
					Integer mt_status = 5;
			        Integer amount = (new Integer(req.getParameter("sharingAmount").trim()));

					MeetingVO meetingVO = new MeetingVO();
					
					meetingVO.setMt_status(mt_status);
					meetingVO.setMt_no(mt_no);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("meetingVO", meetingVO); // 含有輸入格式錯誤的empVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/frontend/meeting/search_mem_meeting.jsp");
						failureView.forward(req, res);
						return; //程式中斷
					}
					
					/***************************2.開始修改資料*****************************************/
					MeetingService meetingSvc = new MeetingService();
					meetingVO = meetingSvc.cancelMeeting(mt_status,mt_no);
					System.out.println(mt_no);
					System.out.println(mt_status);

					Boolean ifCheckOutSucess = MoneyTool.checkOut(session1, 33, mt_no, amount);// 扣款請傳入負數
					if (ifCheckOutSucess) {
					
					
					

					/***************************3.修改完成,準備轉交(Send the Success view)*************/
					req.setAttribute("meetingVO", meetingVO); // 資料庫update成功後,正確的的empVO物件,存入req
					
					String url = "/frontend/meeting/search_mem_meeting.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 結帳成功後轉交回原頁面會員頁面
					successView.forward(req, res);} 
					else {
						errorMsgs.add("分潤失敗,請檢查格式正確");
						RequestDispatcher failureView = req.getRequestDispatcher("/frontend/meeting/search_mem_meeting.jsp");

						failureView.forward(req, res);
						return;
					}

					if (!errorMsgs.isEmpty()) {

						RequestDispatcher failureView = req.getRequestDispatcher("/frontend/meeting/search_mem_meeting.jsp");

						failureView.forward(req, res);
						return;
					}

				} catch (Exception e) {
					errorMsgs.add("無法取得資料:" + e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/meeting/search_mem_meeting.jsp");
					failureView.forward(req, res);
				}
				
               }
               /***************************以下為搜尋功能*****************************************/
             //模糊查詢
   			if ("Fuzzy_Search".equals(action)) { // 來自select_page.jsp的請求
   				
   		    	List<String> errorMsgs = new LinkedList<String>();
   				req.setAttribute("errorMsgs", errorMsgs);
   				
   				HttpSession session = req.getSession();

   				try {
   					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
   					String mt_id = req.getParameter("mt_id");
   					/***************************2.開始查詢資料*****************************************/
   					MeetingService meetingSvc = new MeetingService();
   					List<MeetingVO> fuzzy_list = new ArrayList<MeetingVO>();
   					fuzzy_list = meetingSvc.FuzzySearch(mt_id);
   					
   					if (fuzzy_list.size()==0) {
   						errorMsgs.add("查無相關見面會");
   						session.setAttribute("fuzzy_list",fuzzy_list);
   					}
   					
   					if (!errorMsgs.isEmpty()) {
   						RequestDispatcher failureView = req
   								.getRequestDispatcher("/frontend/meeting/meeting_fuzzy_search.jsp");
   						failureView.forward(req, res);
   						return;//程式中斷
   					}
   					
   					/***************************3.查詢完成,準備轉交(Send the Success view)*************/
   					session.setAttribute("fuzzy_list", fuzzy_list); 
   					RequestDispatcher successView = req.getRequestDispatcher("/frontend/meeting/meeting_fuzzy_search.jsp"); 
   					successView.forward(req, res);

   					/***************************其他可能的錯誤處理*************************************/
   				} catch (Exception e) {
   					errorMsgs.add("查詢失敗!!:" + e.getMessage());
   					RequestDispatcher failureView = req
   							.getRequestDispatcher("/frontend/meeting/meeting_fuzzy_search.jsp");
   					failureView.forward(req, res);
   			
   				}
   			}
               
               
               
               
               
               
          }
	}
	

