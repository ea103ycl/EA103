package com.biddingPage.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.mem.model.MemService;
import com.mem.model.MemVO;

@WebServlet("/ReadMemPic")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ReadMemPic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("image/gif"); // 輸出二位元資料

		String action = req.getParameter("action");

		if ("getPic".equals(action)) {

			MemService memSvc = new MemService();
			String memId=req.getParameter("memId");
			
			if(memId.length()==0) {
				System.out.println("(ReadMemPic) getPic: not getting memId");
				return;
			}
		
			byte[] b = memSvc.getPhoto(memId);
			ServletOutputStream out = res.getOutputStream();
			out.write(b);
			
			out.close();
		}
	}
}
