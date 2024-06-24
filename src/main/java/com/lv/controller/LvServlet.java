package com.lv.controller;

import java.io.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.lv.model.LvService;
import com.lv.model.LvVO;

import tools.YclTools;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class LvServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String action = req.getParameter("action");
		
		if ("showPic".equals(action)) {
			res.setContentType("image/gif");
			ServletOutputStream out = res.getOutputStream();
			try {
				Integer lv = Integer.valueOf(req.getParameter("lv").trim());
				LvService lvSvc = new LvService();		
				YclTools.readByteArrayFromDB(req, res, lvSvc.getIconByLv(lv));
			}catch (Exception e) {
				InputStream in = getServletContext().getResourceAsStream("/backend/img/null.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}
		}else if ("showMemLvIcon".equals(action)) {
			res.setContentType("image/gif");
			ServletOutputStream out2 = res.getOutputStream();
			try {
				String mem_id = ((String)req.getParameter("mem_id")).trim();
				LvService lvSvc = new LvService();		
				YclTools.readByteArrayFromDB(req, res, lvSvc.getOneMemberLvIcon(mem_id));
			}catch (Exception e) {
				InputStream in = getServletContext().getResourceAsStream("/backend/img/null.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out2.write(b);
				in.close();
			}
		} else {
			doPost(req, res);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// ======================================================================
		// 新增
		// ======================================================================
		if ("insert".contentEquals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String lv_nm = req.getParameter("lv_nm");
				if (lv_nm == null || (lv_nm.trim()).length() == 0) {
					errorMsgs.add("請輸入里程碑名稱");
				}

				String f_cnt_Str = req.getParameter("f_cnt");
				boolean isFCntNull = (f_cnt_Str == null || (f_cnt_Str.trim()).length() == 0);
				Integer f_cnt = 0;
				if (isFCntNull) {
					errorMsgs.add("請輸入所需粉絲數");
				} else {
					f_cnt = Integer.valueOf(f_cnt_Str);
					if (f_cnt < 0) {
						errorMsgs.add("所需粉絲數需>0");
					}

					LvService lvSrc = new LvService();
					LvVO lvVO = lvSrc.findByThresholdSetting(f_cnt);
					if (lvVO != null) {
						errorMsgs.add("系統內已存在此門檻的里程碑等級");
					}
				}

				Part filePart = req.getPart("imgPath");
				boolean isUploadFile = YclTools.isUploadFile(filePart);
				if (!isUploadFile) {
					errorMsgs.add("請上傳里程碑圖示");
				}

				/*************************** 2.開始新增資料 **********************/
				byte[] fileByteArray = YclTools.transPartToByteArray(filePart);

				LvVO lvVO = new LvVO();
				lvVO.setLv_nm(lv_nm);
				lvVO.setF_cnt(isFCntNull ? null : f_cnt);
				lvVO.setIcon(fileByteArray);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lvVO", lvVO); // 將輸入資料導回新增頁面，避免重新輸入
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/addLv.jsp");
					failureView.forward(req, res);
					return;
				}

				LvService lvSvc = new LvService();
				lvSvc.insert(lvVO);

				/*************************** 3.新增完畢導回查詢頁 ***********/
				RequestDispatcher successView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp"); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println("錯誤訊息:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/addLv.jsp");
				failureView.forward(req, res);
			}
		}
		
		// ======================================================================
		// 取得要修改的資料
		// ======================================================================
		if ("getOneForUpdate".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer lv = Integer.valueOf(req.getParameter("lv"));
				
				LvService lvSvc = new LvService();
				LvVO lvVO = lvSvc.findByPrimaryKey(lv);

				if (lvVO == null) {
					errorMsgs.add("查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("lvVO", lvVO); // 資料庫取出的lvVO物件,存入req
				String url = "/backend/lv/updateLv.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println("錯誤訊息:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp");
				failureView.forward(req, res);
			}
		}

		// ======================================================================
		// 儲存修改資料
		// ======================================================================
		if ("update".contentEquals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer lv = Integer.valueOf(req.getParameter("lv"));
				
				String lv_nm = req.getParameter("lv_nm");
				if (lv_nm == null || (lv_nm.trim()).length() == 0) {
					errorMsgs.add("請輸入里程碑名稱");
				}

				String f_cnt_Str = req.getParameter("f_cnt");
				boolean isFCntNull = (f_cnt_Str == null || (f_cnt_Str.trim()).length() == 0);
				Integer f_cnt = 0;
				if (isFCntNull) {
					errorMsgs.add("請輸入所需粉絲數");
				} else {
					f_cnt = Integer.valueOf(f_cnt_Str);
					if (f_cnt < 0) {
						errorMsgs.add("所需粉絲數需>0");
					}

					LvService lvSrc = new LvService();
					LvVO lvVO = lvSrc.findByThresholdSetting(f_cnt);
					if (lvVO != null && lvVO.getLv() != lv) {
						errorMsgs.add("系統內已存在此門檻的里程碑等級");
					}
				}

				Part filePart = req.getPart("imgPath");
				boolean isUploadFile = YclTools.isUploadFile(filePart);

				/*************************** 2.開始修改資料 **********************/
				byte[] fileByteArray = null;
				if (isUploadFile) {
					fileByteArray = YclTools.transPartToByteArray(filePart);
				}else {
					LvService lvSvc = new LvService();
					LvVO lvVO = lvSvc.findByPrimaryKey(lv);
					fileByteArray = lvVO.getIcon();
				}
				
				LvVO lvVO = new LvVO();
				lvVO.setLv(lv);
				lvVO.setLv_nm(lv_nm);
				lvVO.setF_cnt(isFCntNull ? null : f_cnt);
				lvVO.setIcon(fileByteArray);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lvVO", lvVO); // 將輸入資料導回新增頁面，避免重新輸入
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/updateLv.jsp");
					failureView.forward(req, res);
					return;
				}

				LvService lvSvc = new LvService();
				lvSvc.update(lvVO);
				
				/*************************** 3.新增完畢導回查詢頁 ***********/
				RequestDispatcher successView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp"); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println("錯誤訊息:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/updateLv.jsp");
				failureView.forward(req, res);
			}
		}

		// ======================================================================
		// 刪除
		// ======================================================================
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer lv = Integer.valueOf(req.getParameter("lv"));
				LvService lvSvc = new LvService();
				LvVO lvVO = lvSvc.findByPrimaryKey(lv);

				// 不得刪除最低等級(粉絲數為0)的資料
				if (lvVO.getF_cnt() == 0) {
					errorMsgs.add("不得刪除最低等級(粉絲數為0)的資料，僅可修改里程碑名稱、里程碑圖示");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始刪除資料 *****************************************/
				lvSvc.delete(lv);

				/*************************** 3.刪除完畢導回查詢頁 ***********/
				RequestDispatcher successView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp"); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println("錯誤訊息:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		// ======================================================================
		// 查詢所有資料
		// ======================================================================
		if ("getAll".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				LvService lvSvc = new LvService();
				List<LvVO> list = lvSvc.getAll();

				if (list == null) {
					errorMsgs.add("查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("list", list); // 資料庫取出的lvVO物件,存入req
				String url = "/backend/lv/listAllLv.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println("錯誤訊息:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/lv/listAllLv.jsp");
				failureView.forward(req, res);
			}
		}

		// ======================================================================
		// 取消新增OR修改
		// ======================================================================
		if ("cancel".equals(action)) {
			String url = "/backend/lv/listAllLv.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

	}
}
