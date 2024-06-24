package com.preorder.Controller;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.mem.model.MemService;
import com.preorder.model.PreOrderService;
import com.preorder.model.PreOrderVO;
import com.preorderdetail.model.PreOrderDetailService;
import com.preorderdetail.model.PreOrderDetailVO;
import com.preproduct.model.PreProductVO;

import tools.MoneyTool;


public class ShoppingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		
		@SuppressWarnings("unchecked")
		List<PreProductVO> buylist = (Vector<PreProductVO>) session.getAttribute("preshoppingcart");
		String action = req.getParameter("action");
		System.out.println("------------------進入CHECKOUT - Servlet-----------------------");
		System.out.println("Shopping - Servlet的action被觸發!");
		
		if(!action.equals("CHECKOUT")) {
			System.out.println("觸發結帳>>sevlet != CHECKOUT");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			if(action.equals("DELETE")) {
				System.out.println("觸發>>action = DELETE");
				String del = req.getParameter("del");
				System.out.println("取出參數>>del = "+del);
				int d = Integer.parseInt(del);
				System.out.println("移除>>buylist.remove(d);");
				buylist.remove(d);
				System.out.println("目前購物車數量: "+buylist.size());
				session.setAttribute("shoppingcart", buylist);
				System.out.println("Servlet - 準備轉交去url!");
				String url ="/frontend/preproduct/shoppingCart.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				System.out.println("Servlet - 完成轉交url!");
				rd.forward(req, res);
				return;
			}
			else if(action.equals("ADD")) {
				System.out.println("觸發>> action = ADD");
				PreProductVO preproductVO = getPreProductVO(req);
				
				if(buylist == null) {
					System.out.println("判斷到>> buylist == null");
					buylist = new Vector<PreProductVO>();
					System.out.println("將new Vector<PreProductVO>放入buylist裡");
					buylist.add(preproductVO);
					System.out.println("執行buylist.add(preproductVO);");
				}else {
					System.out.println("判斷到>> buylist != null");
					if(buylist.contains(preproductVO)) {
						errorMsgs.add("本商品已經加入購物車囉!");
					}else {
						buylist.add(preproductVO);
						System.out.println("執行buylist.add(preproductVO);");
					}
				}
			}
			session.setAttribute("preshoppingcart", buylist);
			String url ="/frontend/preproduct/frontindex.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
			System.out.println("執行rd.forward(req, res);");
		}
		else if (action.equals("CHECKOUT")) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String po_no = null;
				String mem_id = req.getParameter("mem_id");
				System.out.println("接收-會員編號 = " + mem_id);
				Integer po_zip = (new Integer(req.getParameter("po_zip").trim()));
				System.out.println("接收-郵遞區 = " + po_zip);
				String po_name = req.getParameter("po_name");
				System.out.println("接收-收件人 = " + po_name);
				String po_phone = req.getParameter("po_phone");
				System.out.println("接收-連絡電話 = " + po_phone);
				String po_addr =  req.getParameter("po_addr");
				System.out.println("接收-收件地址 = " + po_addr);
				Integer po_total = (new Integer(req.getParameter("total_cart_amt").trim()));
				System.out.println("接收-總金額 = " + po_total);
				String po_note = req.getParameter("po_note");
				System.out.println("接收-買方備註 = " + po_note);
				String[] po_prod_no = req.getParameterValues("po_prod_no");
				System.out.println("接收-商品編號 = " + po_prod_no);
				String[] po_qty = req.getParameterValues("po_qty");
				System.out.println("接收-商品數量 = " + po_qty);
				String[] po_price = req.getParameterValues("po_price");
				System.out.println("接收-商品價格 = " + po_price);
				
				PreOrderVO preorderVO = new PreOrderVO();
				preorderVO.setMem_id(mem_id);
				preorderVO.setPo_zip(po_zip);
				preorderVO.setPo_name(po_name);
				preorderVO.setPo_phone(po_phone);
				preorderVO.setPo_addr(po_addr);
				preorderVO.setPo_total(po_total);
				preorderVO.setPo_note(po_note);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("preorderVO", preorderVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/shoppingCart.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				
				Boolean ifCheckOutSucess = MoneyTool.checkOut(session, 41, po_no, -(po_total));// 扣款請傳入負數
				
				if(ifCheckOutSucess) {
					List<PreOrderDetailVO> list = new ArrayList<>();
					
					for(int i = 0;i<buylist.size();i++) {
						System.out.println("start insert 第"+(i+1)+"件商品");
						Integer po_qty1 = new Integer(po_qty[i]);;
						System.out.println(po_qty1);
						Integer po_price1 = new Integer(po_price[i]);
						String po_prod_no1 = po_prod_no[i];
						System.out.println("商品數量 = "+po_qty1);
						System.out.println("商品價格 = "+po_price1);
						System.out.println("商品編號 = "+po_prod_no1);
						
						PreOrderDetailVO preorderdetailVO  = new PreOrderDetailVO();
						preorderdetailVO.setPo_prod_no(po_prod_no1);
						preorderdetailVO.setPo_qty(po_qty1);
						preorderdetailVO.setPo_price(po_price1);
						System.out.println("已經在preorderdetailVO裡set完畢");
						list.add(preorderdetailVO);
					}
				
				
				PreOrderService preorderSvc = new PreOrderService();
				System.out.println("準備執行進preorderSvc.addPreOrderWithDetail");
				preorderVO = preorderSvc.addPreOrderWithDetail(mem_id, po_zip, po_name, po_phone, po_addr, 1, po_total, po_note,list);
				
				}else {
					errorMsgs.add("你可能餘額不太夠喔!!");
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/shoppingCart.jsp");
					failureView.forward(req,res);
					return;
				}

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/frontend/preproduct/order_Success_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				buylist.removeAll(buylist);
				System.out.println("準備forward");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/	
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/preproduct/shoppingCart.jsp");
				failureView.forward(req, res);
			}
		}
	}

	private PreProductVO getPreProductVO(HttpServletRequest req) {
		System.out.println("--------------------------------");
		System.out.println("進入到>> PreProductVO getPreProductVO");
		 String po_prod_no = req.getParameter("po_prod_no");
		 String event_p_no = req.getParameter("event_p_no");
		 String ma_no = req.getParameter("ma_no"); 
		 String po_price = req.getParameter("po_price");
		 String po_detail = req.getParameter("po_detail");
		 
		 System.out.println("get完PreProductVO所有屬性");
		 PreProductVO preproductVO = new PreProductVO();
		 
		 preproductVO.setPo_prod_no(po_prod_no);
		 preproductVO.setEvent_p_no(new Integer(event_p_no));
		 preproductVO.setMa_no(ma_no);
		 preproductVO.setPo_price(new Integer(po_price));
		 preproductVO.setPo_detail(po_detail);
		 System.out.println("set完preproductVO所有屬性>>return");
		 return preproductVO;
		 
		 
	}
}
