package com.reg_inf_controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.reg_inf.model.Reg_infVO;

import tools.MoneyTool;

import com.mem.model.MemVO;
import com.reg_inf.model.*;

public class Reg_infServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		MemVO memVO = new MemVO();

		HttpSession session = req.getSession();
		memVO = (MemVO) session.getAttribute("memVO");
		

		if ("getOne_For_Display".equals(action)) { // 來自listAllReg_inf的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("ri_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入報名表編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String ri_id = null;
				try {
					ri_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("報名表編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				Reg_infVO reg_infVO = reg_infSvc.getOneReg_inf(ri_id);
				if (reg_infVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("reg_infVO", reg_infVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/reg_inf/listOneReg_inf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getReg_inf_mt_no".equals(action)) { // 來自listAllMeeting_back.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("mt_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入報名表編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String mt_no = null;
				try {
					mt_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("報名表編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				

				
				/***************************2.開始查詢資料*****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				List<Reg_infVO> reg_infVO = reg_infSvc.getReg_inf_mt_no(mt_no);
				System.out.println("getReg_inf_mt_no" + mt_no);

				if (reg_infVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("reg_infVO", reg_infVO); // 資料庫取出的empVO物件,存入req
//				HttpSession session = request.getSession("reg_infVO", reg_infVO);
				String url = "/backend/reg_inf/listAllReg_inf_mt_no.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Display_back".equals(action)) { // 來自listAllReg_inf_back.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("ri_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入報名表編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String ri_id = null;
				try {
					ri_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("報名表編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				Reg_infVO reg_infVO = reg_infSvc.getOneReg_inf(ri_id);
				if (reg_infVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("reg_infVO", reg_infVO); // 資料庫取出的empVO物件,存入req
				String url = "/backend/reg_inf/listOneReg_inf_back.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/reg_inf/listAllReg_inf_back.jsp");
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
				String ri_id = new String(req.getParameter("ri_id"));
				
				/***************************2.開始查詢資料****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				Reg_infVO reg_infVO = reg_infSvc.getOneReg_inf(ri_id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("reg_infVO", reg_infVO);         // 資料庫取出的empVO物件,存入req
				String url = "/frontend/reg_inf/update_reg_inf_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_reg_inf_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String ri_id = new String(req.getParameter("ri_id").trim());
				
				String mem_id = req.getParameter("mem_id");
				String mem_idReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("會員ID: 請勿空白");
				} else if(!mem_id.trim().matches(mem_idReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員ID: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
			
				String mt_no = new String(req.getParameter("mt_no").trim());
				
				Integer ri_qty = null;
				try {
					ri_qty = new Integer(req.getParameter("ri_qty").trim());
				} catch (NumberFormatException e) {
					ri_qty = 0;
					errorMsgs.add("人數請填數字.");
				}
				
				String ri_note = null;
				try {
					ri_note = new String(req.getParameter("ri_note").trim());
				} catch (NumberFormatException e) {
					ri_note = "";
					errorMsgs.add("請勿空白.");
				}
				
			
				Reg_infVO reg_infVO = new Reg_infVO();
				reg_infVO.setMt_no(mt_no);
				reg_infVO.setMem_id(mem_id);
				reg_infVO.setRi_qty(ri_qty);
				reg_infVO.setRi_note(ri_note);
				reg_infVO.setRi_id(ri_id);

				System.out.println(ri_note);
	
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reg_infVO", reg_infVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/reg_inf/update_reg_inf_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				reg_infVO = reg_infSvc.updateReg_inf(mt_no,mem_id,ri_qty,ri_note,ri_id);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("reg_infVO", reg_infVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/frontend/reg_inf/listAllReg_inf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/reg_inf/update_reg_inf_input.jsp");
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
			
				String mt_no = new String(req.getParameter("mt_no").trim());
				
				Integer ri_qty = null;
				try {
					ri_qty = new Integer(req.getParameter("ri_qty").trim());
				} catch (NumberFormatException e) {
					ri_qty = 0;
					errorMsgs.add("人數請填數字.");
				}
				
				String ri_note = null;
				try {
					ri_note = new String(req.getParameter("ri_note").trim());
				} catch (NumberFormatException e) {
					ri_note = "";
					errorMsgs.add("請勿空白.");
				}
//				String test = req.getParameter("ri_status");
//				if(test !=null) xxx;
				
				Integer ri_status = 1;
				Reg_infVO reg_infVO = new Reg_infVO();
				
				reg_infVO.setMt_no(mt_no);
				reg_infVO.setMem_id(mem_id);
				reg_infVO.setRi_status(ri_status);
				reg_infVO.setRi_qty(ri_qty);
				reg_infVO.setRi_note(ri_note);
				
	
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reg_infVO", reg_infVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				

				
				/***************************2.開始新增資料***************************************/
//	1.random->123
//	2.email->123
//	3.123->456->DB
				Reg_infService reg_infSvc = new Reg_infService();
				reg_infVO = reg_infSvc.addReg_inf(mt_no,mem_id,ri_qty,ri_status,ri_note);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/frontend/reg_inf/listAllReg_inf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/reg_inf/addReg_inf.jsp");
				failureView.forward(req, res);
			}
		}
        
        if ("checkout_reg".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>(); // 創建一個list並將錯誤訊息存入此list中, 再將此list存放於req cope中
			req.setAttribute("errorMsgs", errorMsgs);

			HttpSession session1 = req.getSession();
			try {
				
				
				// ********************1.接手請求參數並依輸入格式做錯誤處理******************
				String ri_id = null;
				Integer amount = (new Integer(req.getParameter("totalAmount").trim()));
				String mem_id = req.getParameter("mem_id");
				System.out.println(mem_id);
				String mem_idReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("會員ID: 請勿空白");
				} else if(!mem_id.trim().matches(mem_idReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員ID: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
			
				String mt_no = new String(req.getParameter("mt_no").trim());
				Integer check_remaining_people = (new Integer(req.getParameter("check_num").trim()));
				
				Integer ri_qty = (new Integer(req.getParameter("ri_qty").trim()));
				if(ri_qty <=0) {
					errorMsgs.add("人數必須大於0");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				else if(ri_qty > check_remaining_people ) {
					errorMsgs.add("人數超過囉! ");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
	
				String ri_note = null;
				try {
					ri_note = new String(req.getParameter("ri_note").trim());
				} catch (NumberFormatException e) {
					ri_note = "";
					errorMsgs.add("請勿空白.");
				}
				
				Integer ri_status = 1;
				Reg_infVO reg_infVO = new Reg_infVO();
		

				reg_infVO.setMt_no(mt_no);
				reg_infVO.setMem_id(mem_id);
				reg_infVO.setRi_status(ri_status);
				reg_infVO.setRi_qty(ri_qty);
				reg_infVO.setRi_note(ri_note);
				
				
//				********************2.開始結帳******************

				Reg_infService reg_infSvc = new Reg_infService();
				reg_infVO = reg_infSvc.addReg_inf(mt_no,mem_id,ri_qty,ri_status,ri_note);
		       String ri_id2 = reg_infVO.getRi_id();
		
		     
				Boolean ifCheckOutSucess = MoneyTool.checkOut(session1, 43, ri_id2, -amount);// 扣款請傳入負數

				if (ifCheckOutSucess) {
					System.out.println("test");
					reg_infSvc.check_mt_numReg_inf();
				String url = "/frontend/meeting/listAllMeeting_front.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 結帳成功後轉交回原頁面會員頁面
					successView.forward(req, res);
					  

				} else {
					reg_infSvc.deleteReg_inf(ri_id2);
					reg_infSvc.check_mt_numReg_inf();
					errorMsgs.add("報名失敗,請檢查餘額是否充足或格式正確"); // 結帳失敗後的處理
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/meeting/listAllMeeting_front.jsp");

					failureView.forward(req, res);
					return;
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");

					failureView.forward(req, res);
					return;
				}

			} catch (Exception e) {		
				System.out.println("test2");
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
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
				String ri_id = new String(req.getParameter("ri_id"));
				
				/***************************2.開始刪除資料***************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				reg_infSvc.deleteReg_inf(ri_id);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/frontend/reg_inf/listAllReg_inf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
            if ("cancel".equals(action)) { // 來自update_reg_inf_input.jsp的請求
			
            	List<String> errorMsgs = new LinkedList<String>(); 
    			req.setAttribute("errorMsgs", errorMsgs);

    			HttpSession session2 = req.getSession();

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String ri_id = new String(req.getParameter("ri_id").trim());
				System.out.println("ri_id.length: "+ri_id.length());
				if(ri_id.length()==0) {
					System.out.println("ri_id is null");
				}
				
		        Integer ri_status = 2;	
		        Integer ri_qty = 0;
				

		        Integer amount = (new Integer(req.getParameter("totalAmount").trim()));
		        
				Reg_infVO reg_infVO = new Reg_infVO();
				reg_infVO.setRi_status(ri_status);
				reg_infVO.setRi_qty(ri_qty);

				reg_infVO.setRi_id(ri_id);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reg_infVO", reg_infVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				/***************************2.開始修改資料*****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				reg_infVO = reg_infSvc.cancelReg_inf(ri_status,ri_qty,ri_id);
			    reg_infSvc.check_mt_numReg_inf();


				Boolean ifCheckOutSucess = MoneyTool.checkOut(session2, 38, ri_id, amount);// 扣款請傳入負數
				
                
				if (ifCheckOutSucess) {
					
				String url = "/frontend/reg_inf/listAllReg_inf.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 結帳成功後轉交回原頁面會員頁面
					successView.forward(req, res);
					  

				} else {
					errorMsgs.add("報名失敗,請檢查餘額是否充足或格式正確"); // 結帳失敗後的處理
				}

				if (!errorMsgs.isEmpty()) {

					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");

					failureView.forward(req, res);
					return;
				}

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
				failureView.forward(req, res);
			}

		}
            if ("cancel_back".equals(action)) { // 來自update_reg_inf_input.jsp的請求
    			
            	List<String> errorMsgs = new LinkedList<String>(); 
    			req.setAttribute("errorMsgs", errorMsgs);

    			HttpSession session2 = req.getSession();

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String ri_id = new String(req.getParameter("ri_id").trim());
				System.out.println("ri_id.length: "+ri_id.length());
				if(ri_id.length()==0) {
					System.out.println("ri_id is null");
				}
				String mem_id = new String(req.getParameter("mem_id").trim());

		        Integer ri_status = 2;	
		        Integer ri_qty = 0;
				

		        Integer amount = (new Integer(req.getParameter("totalAmount").trim()));
		        
				Reg_infVO reg_infVO = new Reg_infVO();
				reg_infVO.setRi_status(ri_status);
				reg_infVO.setRi_qty(ri_qty);
				reg_infVO.setRi_id(mem_id);

				reg_infVO.setRi_id(ri_id);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reg_infVO", reg_infVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/reg_inf/listAllReg_inf.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				/***************************2.開始修改資料*****************************************/
				Reg_infService reg_infSvc = new Reg_infService();
				reg_infVO = reg_infSvc.cancelReg_inf(ri_status,ri_qty,ri_id);
			    


				Boolean ifCheckOutSucess = MoneyTool.checkOut_back(mem_id, 38, ri_id, amount);// 扣款請傳入負數
				
                
				if (ifCheckOutSucess) {
					reg_infSvc.check_mt_numReg_inf();
				String url = "/backend/reg_inf/listAllReg_inf_mt_no.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 結帳成功後轉交回原頁面會員頁面
					successView.forward(req, res);
					  

				} else {
					errorMsgs.add("報名失敗,請檢查餘額是否充足或格式正確"); // 結帳失敗後的處理
				}

				if (!errorMsgs.isEmpty()) {

					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");

					failureView.forward(req, res);
					return;
				}

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
				failureView.forward(req, res);
			}

		}
          
            
    }

	
	}
