package com.eval.model;
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


public class Eval_mem_pic extends HttpServlet{



	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getpic".equals(action)) {
		res.setContentType("image/*");
		String mem_id = req.getParameter("mem_id").trim();
		
		ServletOutputStream out = res.getOutputStream();
		try {
			EvalService evalSvc = new EvalService();
			EvalVO evalVO = evalSvc.getMem_pic_ByMem_id(mem_id);
			
			out.write(evalVO.getM_photo());
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
}
	
	

