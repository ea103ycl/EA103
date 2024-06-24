package com.event_p.controller;

import java.io.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.event_p.model.*;

@MultipartConfig
@WebServlet("/Event_PViewServlet")
public class Event_PViewServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/jepg");
		
		ServletOutputStream out=res.getOutputStream();
		String event_p_no= req.getParameter("event_p_no");//從網頁取得event_p_no
		byte[] img=null;
		if(event_p_no==null||event_p_no.trim().length()==0) {
			System.out.println("Event_PViewServlet read event_p_no=\"\"");
		}else {
			Event_PDAO dao=new Event_PDAO();//dao呼叫findByPrimaryKey
			Event_PVO event_pVO=dao.findByPrimaryKey(new Integer(event_p_no));
			img=event_pVO.getEvent_p_img(); //取得byte[]	
			out.write(img);
			out.close();
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
