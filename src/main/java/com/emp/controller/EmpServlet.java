package com.emp.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.emp.model.*;

@MultipartConfig
public class EmpServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

//		if ("login".equals(action)) {
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try { // 設定空值為了預防打錯字EX:empoo
//				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//				String str1 = req.getParameter("emp_no"); // return String
//				String emp_no = str1.trim();
//				String emp_no_Reg = "^E[0-9]{3}[0-9]{1}$";
//				
//				if (emp_no == null || emp_no.length() == 0) {
//					errorMsgs.add("請輸入員工編號。");
//				}else if (!emp_no.matches(emp_no_Reg)) {
//					errorMsgs.add("請輸入正確格式，員工編號為E開頭接4位數字。");
//				}
//
//				String emp_pwd = req.getParameter("emp_pwd");
//				String emp_pwd_Reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$";
//				
//				String str2 = req.getParameter("emp_pwd"); // return String
//				if (str2 == null || (str2.trim()).length() == 0) {
//					errorMsgs.add("請輸入員工密碼。");
//				} else if (!emp_pwd.trim().matches(emp_pwd_Reg)) {
//					errorMsgs.add("密碼只能是由數字和字母組成，並且要同時含有數字和字母，且長度要在4-8位之間。");
//				}				
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
//
//				Emp_Account_Service empSvc = new Emp_Account_Service();
//				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);
//				if (emp_Account_VO == null) {
//					errorMsgs.add("查無資料。");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
//
//				if (!emp_Account_VO.getEmpPwd().equals(emp_pwd)) {
//					errorMsgs.add("密碼錯誤。");
//				}else {
//						RequestDispatcher successView = req.getRequestDispatcher("/backend/emp/index.jsp");
//						successView.forward(req, res);					
//					}
//	
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
//
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
//				failureView.forward(req, res);
//			}
//		}
		if ("logout".equals(action)) {
			HttpSession session = req.getSession();
			session.removeAttribute("emp_no");
			session.removeAttribute("funcList");
			RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
			failureView.forward(req, res);
		}
			
		if("updatePwd".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
				String str = req.getParameter("emp_no"); // return String
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入帳號。");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/resetPwd.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String emp_no = str.trim();
				String emp_no_Reg = "^E[0-9]{3}[0-9]{1}$";

				if (!emp_no.matches(emp_no_Reg)) {
					errorMsgs.add("請輸入正確格式，帳號為E開頭接4位數字。");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/resetPwd.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				Emp_Account_Service empSvc = new Emp_Account_Service();
				
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);
				String dbpwd = emp_Account_VO.getEmpPwd();
				
				String old_pwd = req.getParameter("old_pwd");
				if(old_pwd == null || old_pwd.length() == 0) {
					errorMsgs.add("請輸入密碼。");
				}else if(!dbpwd.equals(old_pwd)) {
					errorMsgs.add("密碼錯誤。");
				}	
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/resetPwd.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String emp_pwd = req.getParameter("emp_pwd");
				String emp_pwdc = req.getParameter("emp_pwdc");
				String emp_pwd_Reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$";
				
			if (emp_pwd == null || emp_pwd.trim().length() == 0) {
				errorMsgs.add("新密碼請勿空白。");
			} else if (!emp_pwd.trim().matches(emp_pwd_Reg)) {
				errorMsgs.add("新密碼只能是由數字和字母組成，並且要同時含有數字和字母，且長度要在4-8位之間。");
			} else if(!emp_pwd.equals(emp_pwdc)) {	
				errorMsgs.add("請檢查新密碼與重複輸入欄位是否相符。");
			}
			
			if(!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/resetPwd.jsp");
				failureView.forward(req, res);
				return;
			}
		
			empSvc.resetPwd(emp_no, emp_pwd);
			
			RequestDispatcher successView = req.getRequestDispatcher("/backend/emp/login.jsp");
			successView.forward(req, res);
										
			} catch(Exception e) {
				errorMsgs.add("無法更新資料"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/resetPwd.jsp");
				failureView.forward(req,res);
			}
		}
		
		
		if("newEmp".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
				String str = req.getParameter("emp_no"); // return String
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入帳號。");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/newEmp.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String emp_no = str.trim();
				String emp_no_Reg = "^E[0-9]{3}[0-9]{1}$";

				if (!emp_no.matches(emp_no_Reg)) {
					errorMsgs.add("請輸入正確格式，帳號為E開頭接4位數字。");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/newEmp.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				Emp_Account_Service empSvc = new Emp_Account_Service();
				
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);
				String dbpwd = emp_Account_VO.getEmpPwd();
				
				String old_pwd = req.getParameter("old_pwd");
				if(old_pwd == null || old_pwd.length() == 0) {
					errorMsgs.add("請輸入密碼。");
				}else if(!dbpwd.equals(old_pwd)) {
					errorMsgs.add("密碼錯誤。");
				}	
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/newEmp.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String emp_pwd = req.getParameter("emp_pwd");
				String emp_pwdc = req.getParameter("emp_pwdc");
				String emp_pwd_Reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$";
				
			if (emp_pwd == null || emp_pwd.trim().length() == 0) {
				errorMsgs.add("新密碼請勿空白。");
			} else if (!emp_pwd.trim().matches(emp_pwd_Reg)) {
				errorMsgs.add("新密碼只能是由數字和字母組成，並且要同時含有數字和字母，且長度要在4-8位之間。");
			} else if(!emp_pwd.equals(emp_pwdc)) {	
				errorMsgs.add("請檢查新密碼與重複輸入欄位是否相符。");
			}
			
			if(!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/newEmp.jsp");
				failureView.forward(req, res);
				return;
			}
		
			empSvc.resetPwd(emp_no, emp_pwd);
			
			RequestDispatcher successView = req.getRequestDispatcher("/backend/emp/login.jsp");
			successView.forward(req, res);
										
			} catch(Exception e) {
				errorMsgs.add("無法更新資料"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/newEmp.jsp");
				failureView.forward(req,res);
			}
		}	
	
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			// Object Value
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try { // 設定空值為了預防打錯字EX:empoo
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				
				String str1 = (req.getParameter("emp_no1")).trim(); // return String
				String str2 = req.getParameter("emp_no2");
				String str3 = req.getParameter("emp_no3");
				String emp_no = null;				
					
				if((str1 == null||str1.length()==0) && (str2 == null||str2.length()==0)) {
					emp_no = str3;
				} else if((str1 == null||str1.length()==0) && (str3 == null||str3.length()==0)) {
					emp_no = str2;
				} else if((str2 == null||str2.length()==0) && (str3 == null||str3.length()==0)) {
					emp_no = str1;
				}
				
				if(((str1 != null && str1.length()!=0) && (str2 != null && str2.length()!=0)) ||
				   ((str1 != null && str1.length()!=0) && (str3 != null && str3.length()!=0)) ||
				   ((str2 != null && str2.length()!=0) && (str3 != null && str3.length()!=0)) ||
				   ((str1 != null && str1.length()!=0) && (str2 != null && str2.length()!=0) && (str3 != null && str3.length()!=0))) {
					errorMsgs.add("請輸入單一查詢資料");
				}else {					
					if(emp_no == null || emp_no.length()==0) {
						errorMsgs.add("請輸入員工資料。");					
					}
				}
																
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/listAllEmp.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String emp_no_Reg = "^E[0-9]{3}[0-9]{1}$";
				if (!emp_no.matches(emp_no_Reg)) {
					errorMsgs.add("請輸入正確格式，員工編號為E開頭接4位數字。");
				}
			
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/listAllEmp.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				/*************************** 2.開始查詢資料 *****************************************/
				Emp_Account_Service empSvc = new Emp_Account_Service();
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);
				if (emp_Account_VO == null) {
					errorMsgs.add("查無資料。");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/listAllEmp.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("emp_Account_VO", emp_Account_VO); // 資料庫取出的empVO物件,存入req
				String url = "/backend/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}

		// first, get data of one emp
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String emp_no = req.getParameter("emp_no");

				/*************************** 2.開始查詢資料 ****************************************/
				Emp_Account_Service empSvc = new Emp_Account_Service();
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("emp_Account_VO", emp_Account_VO); // 資料庫取出的empVO物件,存入req
				String url = "/backend/emp/update_emp_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String emp_no = req.getParameter("emp_no");

				String emp_name = req.getParameter("emp_name");
				String emp_name_Reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9-)]{2,10}$";
				if (emp_name == null || emp_name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白。");
				} else if (!emp_name.trim().matches(emp_name_Reg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和- , 且長度必需在2到10之間。");
				}

				
				Emp_Account_Service empSvc = new Emp_Account_Service();
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);
				String emp_pwd = emp_Account_VO.getEmpPwd();
	
				String emp_pos = req.getParameter("emp_pos");
				if (emp_pos == null || emp_pos.trim().length() == 0) {
					errorMsgs.add("職位請勿空白。");
				}

				String emp_mail = req.getParameter("emp_mail");
				String emp_mail_Reg = "\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}";
				if (!emp_mail.trim().matches(emp_mail_Reg)) {
					errorMsgs.add("請輸入正確的email格式。");
				}

				byte[] emp_photo = null;
				Part part = req.getPart("emp_photo");

				InputStream in = part.getInputStream();
				if (in.available() == 0) {
					empSvc = new Emp_Account_Service();
					emp_Account_VO = empSvc.getOneEmp(emp_no);
					emp_photo = emp_Account_VO.getEmpPhoto();
				} else {
					byte[] buf = new byte[in.available()];
					in.read(buf);
					emp_photo = buf;
				}

				Integer emp_status = new Integer(req.getParameter("emp_status"));

				String[] func_Arr = req.getParameterValues("func_id");

				if (func_Arr == null || func_Arr.length == 0) {
					errorMsgs.add("請輸入權限");
				}

				emp_Account_VO = new Emp_Account_VO();
				Available_Func_VO available_Func_VO = new Available_Func_VO();

				emp_Account_VO.setEmpNo(emp_no);
				emp_Account_VO.setEmpName(emp_name);
				emp_Account_VO.setEmpPwd(emp_pwd);
				emp_Account_VO.setEmpPos(emp_pos);
				emp_Account_VO.setEmpMail(emp_mail);
				emp_Account_VO.setEmpPhoto(emp_photo);
				emp_Account_VO.setEmpStatus(emp_status);

				if (func_Arr != null && func_Arr.length != 0) {
					for (int i = 0; i < func_Arr.length; i++) {
						available_Func_VO.setFuncId(func_Arr[i]);
					}
				}

				available_Func_VO.setEmpNo(emp_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) { // 把輸入錯誤的內容也再送回頁面上
					req.setAttribute("emp_Account_VO", emp_Account_VO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/update_emp_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				empSvc = new Emp_Account_Service(); // 將上面的各個參數經由updateEmp設定進去
				emp_Account_VO = empSvc.updateEmp(emp_no, emp_name, emp_pwd, emp_pos, emp_mail, emp_photo, emp_status);

				Available_Func_Service funcSvc = new Available_Func_Service();
				funcSvc.deleteEmpFunc(emp_no);

				for (int i = 0; i < func_Arr.length; i++) {
					available_Func_VO = funcSvc.addEmpFunc(emp_no, func_Arr[i]);
				}

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("emp_Account_VO", emp_Account_VO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/backend/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/update_emp_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Emp_Account_Service empSvc = new Emp_Account_Service();

				String emp_name = req.getParameter("emp_name");
				String emp_name_Reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9-)]{2,10}$";
				if (emp_name == null || emp_name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白。");
				} else if (!emp_name.trim().matches(emp_name_Reg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和- , 且長度必需在2到10之間。");
				}

//				String emp_pwd = req.getParameter("emp_pwd");
//				String emp_pwd_Reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$";
//				if (emp_pwd == null || emp_pwd.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白。");
//				} else if (!emp_pwd.trim().matches(emp_pwd_Reg)) {
//					errorMsgs.add("密碼只能是由數字和字母組成，並且要同時含有數字和字母，且長度要在4-8位之間。");
//				}

				String emp_pwd = randomPwd();

				String emp_pos = req.getParameter("emp_pos");
				if (emp_pos == null || emp_pos.trim().length() == 0) {
					errorMsgs.add("職位請勿空白。");
				}

				String emp_mail = req.getParameter("emp_mail");
				String emp_mail_Reg = "\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}";
				if (!emp_mail.trim().matches(emp_mail_Reg)) {
					errorMsgs.add("請輸入正確的email格式。");
				}

				byte[] emp_photo = null;
				Part part = req.getPart("emp_photo");

				InputStream in = part.getInputStream();
				if (in.available() == 0) {
					errorMsgs.add("尚未選擇圖片");
				} else {
					byte[] buf = new byte[in.available()];
					in.read(buf);
					emp_photo = buf;
				}
				Integer emp_status = new Integer(req.getParameter("emp_status"));

				String[] func_Arr = req.getParameterValues("func_id");
				if (func_Arr == null || func_Arr.length == 0) {
					errorMsgs.add("請輸入權限");
				}

				Emp_Account_VO emp_Account_VO = new Emp_Account_VO();
				Available_Func_VO available_Func_VO = new Available_Func_VO();

				emp_Account_VO.setEmpName(emp_name);
				emp_Account_VO.setEmpPwd(emp_pwd);
				emp_Account_VO.setEmpPos(emp_pos);
				emp_Account_VO.setEmpMail(emp_mail);
				emp_Account_VO.setEmpPhoto(emp_photo);
				emp_Account_VO.setEmpStatus(emp_status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) { // 把輸入錯誤的內容也再送回頁面上
					req.setAttribute("emp_Account_VO", emp_Account_VO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/addEmp.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/

				Emp_Account_VO emp_Account_VO2 = empSvc.addEmp(emp_name, emp_pwd, emp_pos, emp_mail, emp_photo,
						emp_status);

				String emp_no = emp_Account_VO2.getEmpNo();

				available_Func_VO.setEmpNo(emp_no);

				if (func_Arr != null && func_Arr.length != 0) {
					for (int i = 0; i < func_Arr.length; i++) {
						available_Func_VO.setFuncId(func_Arr[i]);
					}
				}

				Available_Func_Service funcSvc = new Available_Func_Service();

				for (int i = 0; i < func_Arr.length; i++) {
					available_Func_VO = funcSvc.addEmpFunc(emp_no, func_Arr[i]);
				}
				
				
				String resetUrl = "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/backend/emp/newEmp.jsp"
						 + "?emp_no1="+emp_no+"&emp_pwd1="+emp_pwd;
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			    //寄送E-Mail
			    String to = emp_mail;			      
			    String subject = "密碼通知";			      
			    String ch_name = emp_name;
			    String messageText = "Hello! " + ch_name + " 以下是你的密碼重置訊息" + "\n" + 
			    					 "帳號:" + emp_no+ "\n" +
			    		             "密碼:" + emp_pwd + "\n" + 
			    					 " (已經啟用)" + "\n" + "請點選以下連結重設密碼"+"\n"+
			    					 resetUrl;
			    
	
			       
			    MailService mailService = new MailService();
			    mailService.sendMail(to, subject, messageText);
			    
			    HttpSession session = req.getSession();
			    session.setAttribute("emp_no1",emp_no);
			    session.setAttribute("emp_pwd1",emp_pwd);
			    

			    
				String url = "/backend/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/addEmp.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String emp_no = req.getParameter("emp_no");

				/*************************** 2.開始刪除資料 ***************************************/
				Available_Func_Service funcSvc = new Available_Func_Service();
				funcSvc.deleteEmpFunc(emp_no);

				Emp_Account_Service empSvc = new Emp_Account_Service();
				empSvc.deleteEmp(emp_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/backend/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
	}

	private String randomPwd() {
		int r;
		StringBuilder ranPwd = new StringBuilder();

		ranPwd.append((int) ((Math.random() * 10)));

		for (int i = 0; i < 7; i++) {
			r = (int) (Math.random() * 3 + 1);
			if (r == 1) {
				ranPwd.append((int) ((Math.random() * 10)));
			} else if (r == 2) {
				ranPwd.append((char) (((Math.random() * 26) + 65)));
			} else {
				ranPwd.append(((char) ((Math.random() * 26) + 97)));
			}
		}
		return ranPwd.toString();
	}
}
