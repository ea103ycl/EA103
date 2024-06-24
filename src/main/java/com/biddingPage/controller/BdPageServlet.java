package com.biddingPage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.bidding.model.BdRedis;
import com.bidding.model.BiddingService;
import com.bidding.model.BiddingVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;

import tools.MoneyTool;

//@WebServlet("/biddingPage/BdPageServlet") //mark By YCL
public class BdPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();

		// =====================front page action ===================
		
		if("setEventToContextscope".equals(action)) {
			ServletContext ctx=req.getServletContext();
			String eventNo=req.getParameter("eventNo");
			
			ctx.setAttribute("event_no", eventNo);
			System.out.println("(BdPageServlet) Bidding start.");
			RequestDispatcher view = req.getRequestDispatcher("/frontend/biddingFront/biddingIndex.jsp");
			view.forward(req, res);
		}
		
		
		
		if ("checkout".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			BdRedis bdr = new BdRedis();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String bdNo = req.getParameter("bdNo");// get biddingPage's requestScope bdNO(not Redis one)
				if (bdNo == null || (bdNo.trim().length() == 0)) {
					errorMsgs.add("競標編號不能為空白");
				}
				String memId = req.getParameter("memId");
				if (memId == null || (memId.trim()).length() == 0) {
					errorMsgs.add("會員id:不能為空白");
				}

				String bdName = req.getParameter("bdName");

				if (bdName == null || (bdName.trim()).length() == 0) {
					errorMsgs.add("請輸入收件人姓名");
				}

				String bdPhone = req.getParameter("bdPhone");
				String bdPhoneReg = "^[(0-9)]{10}$";
				if (bdPhone == null || (bdPhone.trim()).length() == 0) {
					errorMsgs.add("請輸入電話號碼");
				} else if (!bdPhone.trim().matches(bdPhoneReg)) {
					errorMsgs.add("請輸入正確電話號碼");
				}

				String bdAddr = req.getParameter("bdAddr");
				if (bdAddr == null || (bdAddr.trim()).length() == 0) {
					errorMsgs.add("請輸入收件地址");
				}

				Integer bdZip = null;
				try {
					bdZip = new Integer(req.getParameter("bdZip"));
				} catch (NumberFormatException e) {
					bdZip = 00000;
					errorMsgs.add("郵遞區號請填數字");
				}

				Integer bdProdNo = null;
				try {
					bdProdNo = new Integer(req.getParameter("bdProdNo").trim());
				} catch (NumberFormatException e) {
					bdProdNo = 0;
					errorMsgs.add("商品編號無法取得，請聯絡服務人員");
				}

				java.sql.Timestamp bdDateStr = null;
				try {
					bdDateStr = bdr.getRunningBd().getBdDateStr();
				} catch (IllegalArgumentException e) {
					bdDateStr = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("開始時間存取錯誤，請聯絡服務人員!");
				}

				java.sql.Timestamp bdDateEnd = null;
				try {
					bdDateEnd = bdr.getRunningBd().getBdDateEnd();
				} catch (IllegalArgumentException e) {
					bdDateEnd = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("截標時間存取錯誤，請聯絡服務人員!");
				}

				Integer bdStatus = null;
				try {
					bdStatus = 1;
				} catch (NumberFormatException e) {
					bdStatus = 0;
					errorMsgs.add("競標狀態只能為0,1");
				}

				Integer bdOrdStatus = null;
				try {
					bdOrdStatus = 0;
				} catch (NumberFormatException e) {
					bdOrdStatus = 0;
					errorMsgs.add("競標訂單狀態只能為0-4");
				}

				Integer pmtStatus = null;
				try {
					pmtStatus = 0;
				} catch (NumberFormatException e) {
					pmtStatus = 0;
					errorMsgs.add("付款狀態請填數字");
				}

				if (!errorMsgs.isEmpty()) {
					out.write(errorMsgs.get(0));
					System.out.println("(BdPageServlet)has errorMsgs");
					return;
				}

				Integer bdPrice = null;
				try {
					bdPrice = bdr.getTopBid(bdNo);
				} catch (NumberFormatException e) {
					bdPrice = 0;
					errorMsgs.add("得標金額無法取得，請聯絡服務人員");
				}

				HttpSession session = req.getSession();
				if (MoneyTool.checkOut(session, 42, bdr.getSqlBdNo(bdNo), bdPrice * -1)) {

					BiddingService bdSvc = new BiddingService();
					bdSvc.update(bdr.getSqlBdNo(bdNo), memId, bdProdNo, bdPrice, bdDateStr, bdDateEnd, bdStatus,
							bdOrdStatus, pmtStatus, bdZip, bdName, bdPhone, bdAddr);

					out.write("complete");

				} else {
					errorMsgs.add("您的錢包餘額不足");
					System.out.println("(BdPageServlet) checkout: MoneyTool error or Insufficient funds");
				}
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				out.write(errorMsgs.get(0));
			}
		}

		// =======================================================
		if ("setBidInfo".equals(action)) { // setBidInfo in to reqAttribute=======replaced by EventEndCheckListener
			try {
				String memId = req.getParameter("memId");
				String bdNo = req.getParameter("bdNo");

				req.setAttribute("memId", memId);
				req.setAttribute("bdNo", bdNo);
				BdRedis bdr = new BdRedis();
				bdr.registerBdNo(bdNo);

				RequestDispatcher view = req.getRequestDispatcher("/frontend/biddingFront/biddingPage.jsp");
				view.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("(BdPageServlet) getBidInfo error:" + e.getMessage());
				throw new RuntimeException("(BdPageServlet) getBidInfo error:" + e.getMessage());
			}
		}

		// ======================================
		if ("newBid".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				try {
					Integer bid = new Integer(req.getParameter("bid").trim());
					String memId = req.getParameter("memId");
					String bdNo = req.getParameter("bdNo");

					BdRedis bdr = new BdRedis();

					Integer currentBid = bdr.getTopBid(bdNo);

					if (currentBid < bid) {

						bdr.newBid(bdNo, memId, bid);

						System.out.println("(BdPageServlet)bid:" + bid);
						out.print(bid);

					} else {
						return;
					}
				} catch (NumberFormatException e) {
					System.out.println("(BdPageServlet) newBid error:" + e.getMessage());
					errorMsgs.add("please enter the correct bid!:" + e.getMessage());
				}

			} catch (Exception e) {
				System.out.println("(BdPageServlet) newBid error:" + e.getMessage());
				errorMsgs.add("(BdPageServlet)error!:" + e.getMessage());
			}

		}
		// ======================================
		if ("topBidders".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String bdNo = null;
				BdRedis bdr = new BdRedis();
				JSONObject jsonObj = new JSONObject();

				try {

					bdNo = req.getParameter("bdNo");
					MemService memSvc = new MemService();

					// ================getting Top bidders============
					if (bdr.getBidsCount(bdNo) > 0) {

						String top1 = bdr.getTopBidders(bdNo)[0];
						Integer price1 = bdr.getBid(bdNo, top1);

						MemVO mVO1 = memSvc.findByPrimaryKey(top1);
						jsonObj.put("top1name", mVO1.getM_name());
						jsonObj.put("top1memId", mVO1.getMem_id());
						jsonObj.put("price1", price1);
					}
					if (bdr.getBidsCount(bdNo) > 1) {

						String top2 = bdr.getTopBidders(bdNo)[1];
						Integer price2 = bdr.getBid(bdNo, top2);

						MemVO mVO2 = memSvc.findByPrimaryKey(top2);
						jsonObj.put("top2name", mVO2.getM_name());
						jsonObj.put("top2memId", mVO2.getMem_id());
						jsonObj.put("price2", price2);
					}
					if (bdr.getBidsCount(bdNo) > 2) {

						String top3 = bdr.getTopBidders(bdNo)[2];
						Integer price3 = bdr.getBid(bdNo, top3);

						MemVO mVO3 = memSvc.findByPrimaryKey(top3);
						jsonObj.put("top3name", mVO3.getM_name());
						jsonObj.put("top3memId", mVO3.getMem_id());
						jsonObj.put("price3", price3);
					}
					Integer numberOfBids = bdr.getBidsCount(bdNo);
					jsonObj.put("numberOfBids", numberOfBids);

					out.print(jsonObj);

				} catch (NumberFormatException e) {
					System.out.println("(BdPageServlet)topBidders error1:" + e.getMessage());
					errorMsgs.add("please enter the correct bid!:" + e.getMessage());
				}

			} catch (Exception e) {
				System.out.println("(BdPageServlet)topBidders error2:" + e.getMessage());
				errorMsgs.add("(BdPageServlet)error!:" + e.getMessage());
			}
		}
		// =============bidOver======================

	}
}
