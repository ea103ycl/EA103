package com.discount.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DiscountSettingServlet extends HttpServlet{

	public void doGet(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException {
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("------------------進入DiscountServlet-----------------------");
		
		if("insertdiscount".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("Servlet -- 觸發insertdiscount");
			
			try {
				String event_no = req.getParameter("event_no");
				System.out.println("取值 - event_no = " + event_no);
				Integer reach_number = null;
				try {
					reach_number =  new Integer(req.getParameter("reach_number"));
					System.out.println("取值 - reach_number = " + reach_number);
				}catch (NumberFormatException e) {
					reach_number = 0;
					errorMsgs.add("人數請填數字");
					System.out.println("已add人數請填數字.!");
				}
				Integer reach_discount = null;
				try {
					reach_discount =  new Integer(req.getParameter("reach_discount"));
					System.out.println("取值 - reach_discount = " + reach_discount);
				}catch (NumberFormatException e) {
					reach_discount = 0;
					errorMsgs.add("百分比請填數字");
					System.out.println("已add百分比請填數字.!");
				}

				DiscountSettingVO discountsettingVO = new DiscountSettingVO();
				System.out.println("建立discountsettingVO");
				discountsettingVO.setReach_discount(reach_discount);
				discountsettingVO.setReach_number(reach_number);
				discountsettingVO.setEvent_no(event_no);
				System.out.println("將取到的值放入discountsettingVO裡");
				
				if (!errorMsgs.isEmpty()) {
					System.out.println("已判斷!errorMsgs.isEmpty()");
					req.setAttribute("discountsettingVO", discountsettingVO); // 含有輸入格式錯誤的preproductVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/preproduct/addDiscount.jsp");
					System.out.println("已判斷failureView");
					failureView.forward(req, res);
					System.out.println("已forward");
					return;
				}
				/***************************2.開始新增資料***************************************/
				System.out.println("即將開始新增資料");
				DiscountSettingService discountsettingSvc = new DiscountSettingService();
				discountsettingVO = discountsettingSvc.addDiscountWithEvent(reach_discount, reach_number, event_no);
				System.out.println("執行Svc =>addDiscountWithEvent");
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				System.out.println("新增完成,準備轉交url");
				String url = "/backend/preproduct/backendindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);	
				
				
			}catch (Exception e) {
				errorMsgs.add("無法取得資料，愛你唷"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/addDiscount.jsp");
				failureView.forward(req,res);
			}
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
	}

}
