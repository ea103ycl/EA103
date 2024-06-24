package com.painter_msg.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemVO;
import com.painter_msg.model.PainterMsgService;
import com.painter_msg.model.PainterMsgVO;

@MultipartConfig
public class PainterMsgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		System.out.println("msg servlet act = " + action);
		
		//新增留言
		if (("insert").contentEquals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String msg = (String) req.getParameter("msg");
			String src = (String) req.getParameter("src");
			String sid = (String) req.getParameter("sid");
			Integer ptr_no = Integer.valueOf(req.getParameter("ptr_no"));
			
			MemVO memVO = (MemVO)req.getSession().getAttribute("memVO");

			try {

				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				if(memVO == null){
					errorMsgs.add("請重新登入");
				}
				
				if (msg == null || msg.trim().length() == 0) {
					errorMsgs.add("請輸入留言");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/painter/onePainter.jsp?ptr_no="  + ptr_no + "&src=" + src+ "&sid=" + sid);
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 **********************/
				PainterMsgService painterMsgSvc = new PainterMsgService();
				PainterMsgVO painterMsgVO = new PainterMsgVO();
				painterMsgVO.setMem_id(memVO.getMem_id());
				painterMsgVO.setMsg(msg);
				painterMsgVO.setPtr_no(ptr_no);
				painterMsgSvc.insert(painterMsgVO);

				/*************************** 3.新增完畢導回查詢頁 ***********/
//				RequestDispatcher successView = req.getRequestDispatcher("/frontend/painter/onePainter.jsp?ptr_no=" + ptr_no);
//				successView.forward(req, res);
				res.sendRedirect(req.getContextPath() + "/frontend/painter/onePainter.jsp?ptr_no=" + ptr_no + "&src=" + src+ "&sid=" + sid + "#msg");

			} catch (Exception e) {
				errorMsgs.add("無法新增資料:" + e.getMessage());
				System.out.println("錯誤訊息:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/painter/onePainter.jsp?ptr_no=" + ptr_no + "&src=" + src+ "&sid=" + sid);
				failureView.forward(req, res);
			}

		}

		//由創作者刪除留言
		if (("deleteByPainterOwner").contentEquals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			Integer msg_no = Integer.valueOf(req.getParameter("msg_no"));
			Integer ptr_no = Integer.valueOf(req.getParameter("ptr_no"));
			String src = (String) req.getParameter("src");
			String sid = (String) req.getParameter("sid");
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				PainterMsgService painterMsgSvc = new PainterMsgService();
				painterMsgSvc.delete(msg_no, 2);

				RequestDispatcher successView = req.getRequestDispatcher("/frontend/painter/onePainter.jsp?ptr_no=" + ptr_no + "&src=" + src+ "&sid=" + sid);
				successView.forward(req, res);
				

			} catch (Exception e) {
				errorMsgs.add("無法刪除資料:" + e.getMessage());
				System.out.println("錯誤訊息:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/painter/onePainter.jsp?ptr_no=" + ptr_no + "&src=" + src+ "&sid=" + sid);
				failureView.forward(req, res);
			}
		}
		
		//由留言者刪除留言
		if (("deleteByMsgOwner").contentEquals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			Integer msg_no = Integer.valueOf(req.getParameter("msg_no"));
			Integer ptr_no = Integer.valueOf(req.getParameter("ptr_no"));
			String src = (String) req.getParameter("src");
			String sid = (String) req.getParameter("sid");
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				PainterMsgService painterMsgSvc = new PainterMsgService();
				painterMsgSvc.delete(msg_no, 3);

				RequestDispatcher successView = req.getRequestDispatcher("/frontend/painter/onePainter.jsp?ptr_no=" + ptr_no + "&src=" + src + "&sid=" + sid);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println("錯誤訊息:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/painter/onePainter.jsp?ptr_no=" + ptr_no + "&src=" + src+ "&sid=" + sid);
				failureView.forward(req, res);
			}
		}
		

	}

}
