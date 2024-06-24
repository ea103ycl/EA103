package com.msg.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NameServlet extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String mem_id = req.getParameter("mem_id");
		
		req.setAttribute("mem_id", mem_id);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/frontend/chat/chat.jsp");
		dispatcher.forward(req, res);
	}
}
