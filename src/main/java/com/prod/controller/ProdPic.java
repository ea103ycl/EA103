
package com.prod.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prod.model.ProdService;
import com.prod.model.ProdVO;




public class ProdPic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getpic".equals(action)) {
		res.setContentType("image/*");
		Integer prod_no = new Integer(req.getParameter("prod_no"));
		ServletOutputStream out = res.getOutputStream();
		try {
			ProdService prodSvc = new ProdService();
			ProdVO prodVO = prodSvc.getOneProd(prod_no);
			out.write(prodVO.getProd_pic());
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
}