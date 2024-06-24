package com.emp.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;



public class AuthorityFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain)
			throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();
		String emp_no = (String) session.getAttribute("emp_no");

		if (emp_no == null || emp_no.length() == 0) {
			res.sendRedirect(req.getContextPath() + "/backend/emp/login.jsp");
			return;
		}

		String location = req.getRequestURI();
		
		String a = req.getContextPath() + "/backend/members/mem_selectPage.jsp";
		String b = req.getContextPath() + "/backend/welRecord/welRecord_selectPage.jsp";		
		String c1 = req.getContextPath() + "/backend/lv/listAllLv.jsp";	
		String c2 = req.getContextPath() + "/backend/lv/addLv.jsp";	
		String d1 = req.getContextPath() + "/backend/event/TestInsert.jsp";	
		String d2 = req.getContextPath() + "/backend/event/TestListAll.jsp";	
		String d3 = req.getContextPath() + "/backend/event_p/listAllPic.jsp";
		String d4 = req.getContextPath() + "/backend/event_p/listAllPicByEventNo.jsp";
		String d5 = req.getContextPath() + "/backend/event_p_rep/listAllRep.jsp";		
		String e1 = req.getContextPath() + "/backend/bidding/listAll.jsp";	
		String e2 = req.getContextPath() + "/backend/bidding/select_page.jsp";		
		String f1 = req.getContextPath() + "/backend/preproduct/addDiscount.jsp";	
		String f2 = req.getContextPath() + "/backend/preproduct/addPreProduct_ByMano.jsp";	
		String f3 = req.getContextPath() + "/backend/preproduct/addPreProduct.jsp";	
		String f4 = req.getContextPath() + "/backend/preproduct/backendindex.jsp";	
		String f5 = req.getContextPath() + "/backend/preproduct/PonoByReachDiscount.jsp";	
		String f6 = req.getContextPath() + "/backend/preproduct/quantityOfSale.jsp";		
		String g1 = req.getContextPath() + "/backend/prod/listAllProd.jsp";
		String g2 = req.getContextPath() + "/backend/prod/addProd.jsp";			
		String h1 = req.getContextPath() + "/backend/material/addMat.jsp";
		String h2 = req.getContextPath() + "/backend/material/listAllMat.jsp";
		String h3 = req.getContextPath() + "/backend/material/listAllMatype.jsp";
		String h4 = req.getContextPath() + "/backend/material/listMat_ByCompositeQuery.jsp";
		String h5 = req.getContextPath() + "/backend/material/selectMat.jsp";
		String i  = req.getContextPath() + "/backend/meeting/listAllMeeting_back.jsp";
		String j1 = req.getContextPath() + "/backend/preproduct/preOrder.jsp";	
		String j2 = req.getContextPath() + "/backend/preproduct/preOrderDetail.jsp";
		String j3 = req.getContextPath() + "/backend/prod/listAllOrder.jsp";
		String k1 = req.getContextPath() + "/backend/emp/addEmp.jsp";
		String k2 = req.getContextPath() + "/backend/emp/listAllEmp.jsp";		
	

		String funcList = (String)session.getAttribute("funcList");
		
//		Available_Func_Service AvaFuncSvc = new Available_Func_Service();
//		List<Available_Func_VO> list = AvaFuncSvc.getOneEmpFunc(emp_no);
//
//		StringBuffer sb = new StringBuffer();
//		for (Available_Func_VO available_Func_VO : list) {
//			String x = available_Func_VO.getFuncId();
//			sb.append(x);
//		}
//
//		String funcList = sb.toString();


		if (funcList.contains("A") && location.equals(a)) {
			chain.doFilter(request, response);
		} else if (funcList.contains("B") && location.equals(b)) {
			chain.doFilter(request, response);
		} else if (funcList.contains("C") && (location.equals(c1)||
				location.equals(c2))) {
			chain.doFilter(request, response);
		} else if(funcList.contains("D") && (location.equals(d1)||
				location.equals(d2)||location.equals(d3)||location.equals(d4)||location.equals(d5))){
			chain.doFilter(request, response);			
		} else if (funcList.contains("E") && (location.equals(e1)||
				location.equals(e2))) {
			chain.doFilter(request, response);
		} else if (funcList.contains("F") && (location.equals(f1)||location.equals(f2)||location.equals(f3)||
				location.equals(f4)||location.equals(f5)||location.equals(f6))) {
			chain.doFilter(request, response);
		} else if (funcList.contains("G") && (location.equals(g1)||
				location.equals(g2))) {
			chain.doFilter(request, response);
		} else if (funcList.contains("H") && (location.equals(h1)||location.equals(h2)||location.equals(h3)||
				location.equals(h4)||location.equals(h5))) {
			chain.doFilter(request, response);
		} else if (funcList.contains("I") && location.equals(i)) {
			chain.doFilter(request, response);
		} else if (funcList.contains("J") && (location.equals(j1) || location.equals(j2) || location.equals(j3))) {
			chain.doFilter(request, response);
		} else if(funcList.contains("K") && (location.equals(k1)||
			location.equals(k2))){
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/backend/emp/index.jsp");
		}
	}
}
