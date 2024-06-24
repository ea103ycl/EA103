package com.wel_record.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.wel_record.model.WelRecordService;
import com.wel_record.model.WelRecordVO;

import ecpay.example.EcpayAllInOne;
import tools.MoneyTool;

//@WebServlet("/welRecord/welRecord.do") //mark by YCL

public class WelRecordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final int WITHDRAW_LIMIT = 300000; // 單日提領上限三十萬

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		WelRecordService welRecordSvc = new WelRecordService();

		// 是否為綠界刷卡成功後轉回
		req.setAttribute("EcpaySuccess", false);
		// 是否為提款成功
		req.setAttribute("withdrawSuccess", false);

		if ("deposit".equals(action)) {

			// 取得當下登入的會員VO
			MemVO memVO = new MemVO();
			HttpSession session = req.getSession();
			memVO = (MemVO) session.getAttribute("memVO");

			System.out.println("memVO.accno:" + memVO.getM_accno());

			List<String> errorMsgsForMoney = new LinkedList<String>();

			req.setAttribute("errorMsgsForMoney", errorMsgsForMoney);

			try {

				// ********************1.接手請求參數並依輸入格式做錯誤處理******************

				Integer amount = new Integer(req.getParameter("depositAmount").trim());

				if (amount <= 0) {
					errorMsgsForMoney.add("金額不得小於等於零");

				}
				if (!errorMsgsForMoney.isEmpty()) {
					System.out.println("金額小於零, 導回原頁面");
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
					failureView.forward(req, res);
					return;
				}

//				********************2.開始結帳******************

				Boolean ifCheckOutSucess = MoneyTool.checkOut(session, 10, null, amount);// 儲值傳入正數

				if (ifCheckOutSucess) {

					// 綠界設定檔路徑
					String paymentConDirectory = "/WEB-INF/payment_conf.xml";
					String paymentConfPath = getServletContext().getRealPath(paymentConDirectory);

					// 綠界返回會員專區之url
					StringBuffer url = req.getRequestURL();
					url.append("?action=transfer2memArea&memid=");
					url.append(memVO.getMem_id());
					System.out.println(url.toString());

					// 產生綠界form並存入request中
					String form = EcpayAllInOne.getForm(amount, paymentConfPath, url.toString());
					req.setAttribute("form", form);

					// 結帳成功後轉交至轉綠界頁面
					System.out.println("準備轉至綠界");
					String returnUrl = "/frontend/members/memforward2Ecpay.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(returnUrl);
					successView.forward(req, res);

				} else {
					System.out.println("結帳失敗");
					errorMsgsForMoney.add("儲值失敗,請檢查格式是否正確"); // 結帳失敗後的處理
				}

			} catch (Exception e) {
				errorMsgsForMoney.add("請確認輸入格式是否正確！");
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
				failureView.forward(req, res);
			}

		}

		// 從綠界刷卡結束後轉回會員專區
		if ("transfer2memArea".equals(action)) {

			// 更新session之memVO

			String mem_id = req.getParameter("memid");

			MemService memSrc = new MemService();

			MemVO updatedMemVO = memSrc.findByPrimaryKey(mem_id);

			HttpSession session = req.getSession();

			session.setAttribute("memVO", updatedMemVO);

			String url = "/frontend/members/memArea.jsp";
			RequestDispatcher forwardView = req.getRequestDispatcher(url);

			req.setAttribute("EcpaySuccess", true);

			forwardView.forward(req, res);

		}

		if ("withdraw".equals(action)) {

			// 取得當下登入的會員VO
			MemVO memVO = new MemVO();
			HttpSession session = req.getSession();
			memVO = (MemVO) session.getAttribute("memVO");

			System.out.println("memVO.accno:" + memVO.getM_accno());

			List<String> errorMsgsForMoney = new LinkedList<String>();

			req.setAttribute("errorMsgsForMoney", errorMsgsForMoney);

			try {

				// ********************1.接手請求參數並依輸入格式做錯誤處理******************

				Integer amount = new Integer(req.getParameter("withdrawAmount").trim());

				if (amount > WITHDRAW_LIMIT) {
					errorMsgsForMoney.add("單日提領上限為新台幣30萬元");
				}

				if (amount <= 0) {
					errorMsgsForMoney.add("金額不得小於等於零");

				}

				if (!errorMsgsForMoney.isEmpty()) {

					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");

					failureView.forward(req, res);
					return;
				}

//				********************2.開始結帳******************

				Boolean ifCheckOutSucess = MoneyTool.checkOut(session, 20, null, -amount);// 扣款請傳入負數

				if (ifCheckOutSucess) {

					String returnUrl = "/frontend/members/memArea.jsp";// 結帳成功後轉交回原會員頁面
					RequestDispatcher successView = req.getRequestDispatcher(returnUrl);
					req.setAttribute("withdrawSuccess", true);
					successView.forward(req, res);

				} else {
					errorMsgsForMoney.add("提領失敗,請檢查餘額是否充足或格式正確"); // 結帳失敗後的處理

				}

				if (!errorMsgsForMoney.isEmpty()) {

					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");

					failureView.forward(req, res);
					return;
				}

			} catch (Exception e) {
				errorMsgsForMoney.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
				failureView.forward(req, res);
			}

		}

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>(); // 創建一個list並將錯誤訊息存入此list中, 再將此list存放於req cope中
			req.setAttribute("errorMsgs", errorMsgs);

			try {
//				********************1.接手請求參數並依輸入格式做錯誤處理******************
				String str = req.getParameter("tns_id");
				if (str == null || (str.trim().length() == 0)) // 未輸入或輸入空白
				{
					errorMsgs.add("請輸入交易流水號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/welRecord/select_page_g1.jsp");

					failureView.forward(req, res);
					return;
				}

				Integer tns_id = null;
				try {
					tns_id = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("流水編號格式不正確");
				}
//				********************2.開始查詢資料******************

				WelRecordVO welRecordVO = welRecordSvc.getOneWelRecord(tns_id);

				if (welRecordVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/welRecord/select_page_g1.jsp");

					failureView.forward(req, res);
					return;
				}

//				********************3.查詢完畢,準備轉交******************
				req.setAttribute("welRecordVO", welRecordVO);
				String url = "/backend/welRecord/listOneWelRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

//				********************其他可能錯誤處理******************
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/emp/select_page.jsp");
				failureView.forward(req, res);
			}
		}

//			---------------------------------查詢錢包紀錄by會員編號----------------------------------

		if ("getAllbyMem_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
//				********************1.接收參數  下拉式選單沒有錯誤處理******************

				String mem_id = req.getParameter("mem_id");

//				********************2.開始查詢資料******************

				List<WelRecordVO> list = welRecordSvc.getWelRecordByMemID(mem_id);

				if (list == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/welRecord/select_page_g1.jsp");

					failureView.forward(req, res);
					return;
				}

//				********************3.查詢完畢,準備轉交******************
				req.setAttribute("set", list);
				String url = "/backend/welRecord/listAllWelRecordByMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

//				********************其他可能錯誤處理******************
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/emp/select_page.jsp");
				failureView.forward(req, res);
			}

		}

		// 查詢全部錢包紀錄

		if ("getAllRecords".equals(action)) {

			List<WelRecordVO> list = welRecordSvc.getAll();

			JSONArray array = new JSONArray(list);
			out.print(array);

		}

		// 查詢單一會員錢包紀錄by會員編號、會員帳號或單一交易流水號

		if ("getOneById_Accno".equals(action)) {

			try {

				// 處理請求參數, 取得交易來源碼
				String input = req.getParameter("input").trim();

				if (input.substring(0, 1).equals("M")) {

					System.out.println("input:" + input);
					List<WelRecordVO> list = welRecordSvc.getWelRecordByMemID(input);

					JSONArray array = new JSONArray(list);
					out.print(array);

				} else if (input.substring(0, 1).equals("4")) {

					int tnsid = Integer.parseInt(input);

					System.out.println("tnsid:" + tnsid);

					WelRecordVO welRecordVO = welRecordSvc.getOneWelRecord(tnsid);

					JSONObject obj = new JSONObject(welRecordVO);

					out.print(obj);

				} else {
					System.out.println(false);
					out.print(false);
				}

			} catch (Exception e) {
				e.getMessage();
				out.print(false);
			}
		}

		// 篩選交易來源
		if ("filterRecords".equals(action)) {

			try {

				// 處理請求參數, 取得交易來源碼
				String input = req.getParameter("filter").trim();

				int src = Integer.parseInt(input);

				// 開始查詢資料

				List<WelRecordVO> list = null;

				if (src == 10 || src == 20) { // 會員儲值 or 會員提款

					list = welRecordSvc.getWelRecordBySrc(src);

				} else if (src == 77) { // 平台扣款-訂單

					list = welRecordSvc.getWelRecordAmongSrc(40, 43);

				} else if (src == 88) { // 平台撥款-分潤/折扣金

					list = welRecordSvc.getWelRecordAmongSrc(30, 34);

				} else { // 平台退款-訂單

					list = welRecordSvc.getWelRecordAmongSrc(35, 38);

				}

				JSONArray array = new JSONArray(list);
				out.print(array);

			} catch (Exception e) {
				e.getMessage();

			}

		}

	}

}
