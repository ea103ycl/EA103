package com.prod.controller;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.prod.model.ProdService;
import com.prod.model.ProdVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)


public class CartServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		HttpSession session = req.getSession();
		Vector<ProdVO> buylist = (Vector<ProdVO>) session.getAttribute("shoppingcart");
		
		
//購物車
		if (!action.equals("CHECKOUT")) {

			
			if (action.equals("DELETE")) {
				String del = req.getParameter("del");
				int d = Integer.parseInt(del);//用index去判斷要刪第幾個
				buylist.removeElementAt(d);
				String url = "/frontend/shop/Cart.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
				return;
			}
			
			 if (action.equals("ADD")){
			
				boolean match = false;

			
				ProdVO aprod = getProd(req);

				
				if (buylist == null) {
					buylist = new Vector<ProdVO>();
					buylist.add(aprod);
				} else {
					for (int i = 0; i < buylist.size(); i++) {
						ProdVO prodVO = buylist.get(i);

						
						if (prodVO.getProd_no().equals(aprod.getProd_no())) {
		
							prodVO.setProd_qty(prodVO.getProd_qty()	+ aprod.getProd_qty());
							System.out.println(aprod.getProd_name());
							System.out.println(aprod.getProd_qty());
							buylist.setElementAt(prodVO, i);
							match = true;
						} // end of if name matches
					} // end of for

					
					if (!match)
						buylist.add(aprod);
					
					  
				}
			}

			session.setAttribute("shoppingcart", buylist);
			if(action.equals("ADD")) {
			res.getWriter().println(buylist.size());
			}
			
			
			
//			String url = "/frontend/shop/EShop.jsp";
//			RequestDispatcher rd = req.getRequestDispatcher(url);
//			rd.forward(req, res);
//			return;
		}

	
		else if (action.equals("CHECKOUT")) {
			int total = 0;
			for (int i = 0; i < buylist.size(); i++) {
				ProdVO order = buylist.get(i);
				int price = order.getProd_price();
				int qty = order.getProd_qty();
				total += (price * qty);
			}

			String or_total = String.valueOf(total);
			req.setAttribute("or_total", or_total);
			String url = "/frontend/shop/Checkout.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}
	}
	
	
	
	
	

	
	private ProdVO getProd(HttpServletRequest req) {
		
		Integer prod_no = new Integer(req.getParameter("prod_no").trim());
		String prod_name = req.getParameter("prod_name");
		Integer prod_price = new Integer(req.getParameter("prod_price").trim());
		Integer prod_qty = new Integer(req.getParameter("prod_qty").trim());

		ProdVO bk = new ProdVO();

		bk.setProd_name(prod_name);
		bk.setProd_no(prod_no);
		bk.setProd_price(prod_price);
		bk.setProd_qty(prod_qty);
		
		return bk;
		
	}
}
	
	
	
	




