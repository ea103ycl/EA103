package com.preorder.model;

import java.io.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.*;
import javax.servlet.http.*;

import com.preorderdetail.model.PreOrderDetailService;
import com.preorderdetail.model.PreOrderDetailVO;
import com.preproduct.model.PreProductService;
import com.preproduct.model.PreProductVO;
import com.wel_record.model.WelRecordService;

import tools.MoneyTool;
public class PreOrderServlet extends HttpServlet{
	public void doGet(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException {
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String action = req.getParameter("action");

		
		if("getOne_For_Display".equals(action)) {
		
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String po_no = req.getParameter("po_no");
				System.out.println("po_no = "+po_no);

				/*****************�}�l�d��*********************/
				PreOrderDetailService preorderdetailSvc = new PreOrderDetailService();
				List<PreOrderDetailVO> preorderdetaillist = preorderdetailSvc.getAllByPo_no(po_no);
				System.out.println("preorderdetailVO = "+preorderdetaillist);
				
				
				if(preorderdetaillist == null) {
					errorMsgs.add("查沒有這筆資料阿...");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/order_Success_List.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*****************�d�ߦ��\�ǳ����*********************/
				req.setAttribute("preorderVO",preorderdetaillist);
				req.setAttribute("po_no",po_no);
				String url = "/frontend/preproduct/order_Detail_Page.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/order_Success_List.jsp");
				failureView.forward(req,res);
			}
		}
		
		if("getOne_For_Display2".equals(action)) {
			System.out.println("PreOrder - getOne_For_Display�QĲ�o!");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String po_no = req.getParameter("po_no");
				System.out.println("po_no = "+po_no);

				/*****************�}�l�d��*********************/
				PreOrderDetailService preorderdetailSvc = new PreOrderDetailService();
				List<PreOrderDetailVO> preorderdetaillist = preorderdetailSvc.getAllByPo_no(po_no);
				System.out.println("preorderdetailVO = "+preorderdetaillist);
				
				
				if(preorderdetaillist == null) {
					errorMsgs.add("查沒有這筆資料阿....");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/preOrder.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*****************�d�ߦ��\�ǳ����*********************/
				req.setAttribute("preorderdetailVO",preorderdetaillist);
				req.setAttribute("po_no",po_no);
				String url = "/backend/preproduct/preOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/preOrder.jsp");
				failureView.forward(req,res);
			}
		}
		
		if("cancel_order".equals(action)) {
			System.out.println("PreOrder - Servlet(�����q��)�QĲ�o!");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String formhash = req.getParameter("formhash");
				System.out.println("�����檺formhash = "+formhash);
				Set<String> formhashSession = (Set<String>) session.getAttribute("formhashSession");
				System.out.println("����session���������X = "+formhashSession);
				
				if(formhashSession == null || !formhashSession.contains(formhash)) {
//					formhashSession.remove(formhash);
					session.setAttribute("formhashSession", formhashSession);
					RequestDispatcher failureView = req.getRequestDispatcher
							("/frontend/preproduct/order_Success_List.jsp");
					failureView.forward(req, res);
					return;
				}else {
					System.out.println("�@�����`");
				}
				
				String po_no = req.getParameter("po_no");
				System.out.println("�n�������q��s����po_no = "+po_no);
				Integer po_total = (new Integer(req.getParameter("po_total")));
				System.out.println("�n�h�ڪ����B��po_total = "+po_total);
				
				PreOrderVO preorderVO = new PreOrderVO();
				preorderVO.setPo_no(po_no);
				preorderVO.setPo_total(po_total);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("preorderVO", preorderVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/order_Success_List.jsp");
					failureView.forward(req, res);
					errorMsgs.clear();
					System.out.println("����errorMsgs.clear()");
					return;
				}
				/*************************** 2.�}�l�h�ڤΧR����� ***************************************/
				System.out.println("�ǳƨϥ�MoneyTool�u��");
				Boolean ifCheckOutSucess = MoneyTool.checkOut(session, 36, po_no, po_total);
				System.out.println("�ϥΧ�MoneyTool�u��");
				
				
				System.out.println("�i�J�R���q�涥�q");
				PreOrderService preorderSvc = new PreOrderService();
				preorderSvc.deletePreOrder(po_no);
				
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/
				System.out.println("操作成功，從Session裡把formhash刪除");
				formhashSession.remove(formhash);
				session.setAttribute("formhashSession", formhashSession);
				session.removeAttribute("formhashSession");

				System.out.println("�R������,�ǳ����url");
				String url = "/frontend/preproduct/order_Success_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				

			} catch (Exception e) {
				errorMsgs.add("�L�k���o��ơA�R�A��"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/order_Success_List.jsp");
				failureView.forward(req,res);
			}
			
			
		}
		if("look_discount_pono".equals(action)) {
			System.out.println("PreOrder - Servlet(look_discount_pono)�QĲ�o!");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String po_prod_no = req.getParameter("po_prod_no");
				System.out.println("���opo_prod_no = "+po_prod_no);
				Integer reach_number = new Integer(req.getParameter("reach_number"));
				System.out.println("���oreach_number = "+reach_number);
				
				/*****************�}�l�d��*********************/
				
				PreOrderService preorderSvc = new PreOrderService();
				List<PreOrderVO> preorderlist = preorderSvc.look_discount_pono(reach_number,po_prod_no);
				System.out.println("preorderdetailVO = "+preorderlist);
				System.out.println("從DAO離開 回到Servlet，準備成功轉交");
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/preproduct/PonoByReachDiscount.jsp");
					failureView.forward(req, res);
					return;
				}
				/*****************�d�ߦ��\�ǳ����*********************/
				HttpSession ordersession = req.getSession();
				ordersession.setAttribute("preorderlist",preorderlist);
				System.out.println("ordersession = "+ordersession);

				String url = "/backend/preproduct/PonoByReachDiscount.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				System.out.println("�j����");
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("�L�k���o��ơA�R�A��"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/quantityOfSale.jsp");
				failureView.forward(req,res);
			}
		}
		if ("update_status".equals(action)) {
			System.out.println("-----ServletĲ�oupdate_status-----");
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String po_no = new String(req.getParameter("po_no"));
				System.out.println("���opo_prod_no = "+po_no);
				/*********************************************************************************/
				Integer po_status = new Integer(req.getParameter("po_status"));
				System.out.println("���oevent_p_no = "+po_status);
				/*********************************************************************************/
				
				/*********************************************************************************/
				PreOrderVO preorderVO = new PreOrderVO();
				preorderVO.setPo_no(po_no);
				preorderVO.setPo_status(po_status);
				
				/*********************************************************************************/
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("preorderVO", preorderVO);
					System.out.println("�P�_��errorMsgs.isEmpty()���O�Ū�");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/preproduct/preOrder.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				/***************************2.�}�l�ק���*****************************************/
				PreOrderService preorderSvc = new PreOrderService();
				
				preorderVO = preorderSvc.updateStatus(po_status,po_no);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("preorderVO", preorderVO);
				String url = "/backend/preproduct/preOrder.jsp";
			
				RequestDispatcher successView = req.getRequestDispatcher(url);
				System.out.println("***************************************************");
				successView.forward(req, res);
	
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/preproduct/preOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("returnDiscount".equals(action)) {
			System.out.println("-----ServletĲ�oreturnDiscount-----");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
			/***************************0.���׭��ƶǭȰ��D**********************/
				String formhash = req.getParameter("formhash");
				System.out.println("�����檺formhash = "+formhash);
				Set<String> formhashSession = (Set<String>) session.getAttribute("formhashSession");
				System.out.println("����session���������X = "+formhashSession);
				
				if(formhashSession == null || !formhashSession.contains(formhash)) {
//					formhashSession.remove(formhash);
					session.setAttribute("formhashSession", formhashSession);
					RequestDispatcher failureView = req.getRequestDispatcher
							("/backend/preproduct/PonoByReachDiscount.jsp");
					failureView.forward(req, res);
					return;
				}else {
					System.out.println("�@�����`");
				}
			/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
			String po_no = req.getParameter("po_no");
			System.out.println("����po_no = "+po_no);
			//--------------------------------------------------------------------------------
			Integer po_status = new Integer(req.getParameter("po_status"));
			if(po_status == 5) {
				errorMsgs.add("���q��w���פ��o�o�e������!");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/preproduct/PonoByReachDiscount.jsp");
				failureView.forward(req, res);
				return;
			}else
			{
				po_status = 5;
			}
			po_status = 5;
			System.out.println("����po_status = "+po_status);
			//--------------------------------------------------------------------------------
			String mem_id = req.getParameter("mem_id");
			System.out.println("����mem_id = "+mem_id);
			//--------------------------------------------------------------------------------
			Integer return_discount = new Integer(req.getParameter("return_discount"));
			System.out.println("����return_discount = "+return_discount);
			
			PreOrderVO preorderVO = new PreOrderVO();
			preorderVO.setPo_no(po_no);
			preorderVO.setPo_status(po_status);
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("preorderVO", preorderVO);
				System.out.println("�P�_��errorMsgs.isEmpty()���O�Ū�");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/preproduct/PonoByReachDiscount.jsp");
				failureView.forward(req, res);
				return; //�{�����_
			}
			/***************************2.�}�l�ק���*****************************************/
			PreOrderService preorderSvc = new PreOrderService();
			System.out.println("�ǳ���JServic���� = preorderSvc.updateStatus");
			preorderVO = preorderSvc.updateStatus(po_status,po_no);
			System.out.println("�ǳ���JServic���� = wrSrc.addWelRecord");
			WelRecordService wrSrc = new WelRecordService();
			wrSrc.addWelRecord(mem_id, 34, po_no, return_discount);
				
			/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/	
			req.setAttribute("preorderVO", preorderVO);
			
			formhashSession.remove(formhash);
			session.setAttribute("formhashSession", formhashSession);
			session.removeAttribute("formhashSession");
			
			String url = "/backend/preproduct/PonoByReachDiscount.jsp";
			
			RequestDispatcher successView = req.getRequestDispatcher(url);
			System.out.println("***************************************************");
			successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/preproduct/PonoByReachDiscount.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
