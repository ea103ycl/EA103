package com.emp.controller;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

import com.emp.model.Available_Func_Service;
import com.emp.model.Available_Func_VO;
import com.emp.model.Emp_Account_Service;
import com.emp.model.Emp_Account_VO;
import com.emp.model.MailService;

public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String dbno;
	private String dbpwd;

	// 【檢查使用者輸入的帳號(account) 密碼(password)是否有效】
	// 【實際上應至資料庫搜尋比對】
	protected boolean allowUser(String emp_no, String emp_pwd) {
		if (dbno.equals(emp_no) && dbpwd.equals(emp_pwd))
			return true;
		else
			return false;
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

	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		req.setCharacterEncoding("Big5");
		res.setContentType("text/html; charset=Big5");
		PrintWriter out = res.getWriter();
		String action = req.getParameter("action");

		if ("login".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// 【取得使用者 帳號(account) 密碼(password)】
				String str1 = req.getParameter("emp_no");
				String str2 = req.getParameter("emp_pwd");

				String emp_no = str1.trim();
				String emp_no_Reg = "^E[0-9]{3}[0-9]{1}$";

				if (emp_no == null || emp_no.length() == 0) {
					errorMsgs.add("請輸入員工編號。");
				} else if (!emp_no.matches(emp_no_Reg)) {
					errorMsgs.add("請輸入正確格式，員工編號為E開頭接4位數字。");
				}

				String emp_pwd = req.getParameter("emp_pwd");
				String emp_pwd_Reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$";

				if (str2 == null || (str2.trim()).length() == 0) {
					errorMsgs.add("請輸入員工密碼。");
				} else if (!emp_pwd.trim().matches(emp_pwd_Reg)) {
					errorMsgs.add("密碼只能是由數字和字母組成，並且要同時含有數字和字母，且長度要在4-8位之間。");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Emp_Account_Service empSvc = new Emp_Account_Service();
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);

				if (emp_Account_VO == null) {
					errorMsgs.add("查無資料。");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				dbno = emp_Account_VO.getEmpNo();
				dbpwd = emp_Account_VO.getEmpPwd();

				// 【檢查該帳號 , 密碼是否有效】
				if (!allowUser(emp_no, emp_pwd)) { // 【帳號 , 密碼無效時】
					errorMsgs.add("密碼錯誤。");

					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				} else { // 【帳號 , 密碼有效時, 才做以下工作】
					HttpSession session = req.getSession();
					session.setAttribute("emp_no", emp_no); // *工作1: 才在session內做已經登入過的標識

					Available_Func_Service AvaFuncSvc = new Available_Func_Service();
					List<Available_Func_VO> list = AvaFuncSvc.getOneEmpFunc(emp_no);

					StringBuffer sb = new StringBuffer();
					for (Available_Func_VO available_Func_VO : list) {
						String x = available_Func_VO.getFuncId();
						sb.append(x);
					}

					String funcList = sb.toString();

					session.setAttribute("funcList", funcList);

					try {
						String location = (String) session.getAttribute("location");
						if (location != null) {
							session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
							res.sendRedirect(location);
							return;
						}
					} catch (Exception ignored) {
				}

					res.sendRedirect(req.getContextPath() + "/backend/back-index.jsp"); // *工作3:
																						// (-->如無來源網頁:則重導至login_success.jsp)
				}

			} catch (Exception e) {
				errorMsgs.add("無法更新資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
				failureView.forward(req, res);

			}
		}
		
		if ("forget".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String str = req.getParameter("emp_no"); // return String
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入帳號。");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
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
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				Emp_Account_Service empSvc = new Emp_Account_Service();
				Emp_Account_VO emp_Account_VO = empSvc.getOneEmp(emp_no);

				if (emp_Account_VO == null) {
					errorMsgs.add("查無資料。");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
	
				empSvc = new Emp_Account_Service();
				emp_Account_VO = empSvc.getOneEmp(emp_no);
				String emp_mail = emp_Account_VO.getEmpMail();
				String emp_name = emp_Account_VO.getEmpName();
				String emp_pwd = randomPwd();
				empSvc.resetPwd(emp_no,emp_pwd);

				String resetUrl = "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/backend/emp/resetPwd.jsp"
						+ "?emp_no="+emp_no+"&emp_pwd="+emp_pwd;

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 寄送E-Mail
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
				session.setAttribute("emp_no", emp_no);
				session.setAttribute("emp_pwd", emp_pwd);

				String url = "/backend/emp/moveMail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法更新資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emp/login.jsp");
				failureView.forward(req, res);
			}
		}
	}
}