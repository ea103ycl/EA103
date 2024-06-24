package com.painter.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.painter.model.PainterService;

import tools.YclTools;

public class GetOrigPtr extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		try {
			Integer ptr_no = Integer.valueOf(req.getParameter("ptr_no").trim());
			PainterService painterSvc = new PainterService();
			YclTools.readByteArrayFromDB(req, res, painterSvc.getPicByPtrNo(ptr_no));

		} catch (Exception e) {

			System.out.println("===[GetOrigPtr]can't get painter");
			InputStream in = getServletContext().getResourceAsStream("/backend/img/null.jpg");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();

		}
	}

}
