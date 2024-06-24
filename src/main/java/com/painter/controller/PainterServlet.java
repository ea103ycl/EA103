package com.painter.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lv.model.LvService;
import com.lv.model.LvVO;
import com.mem.model.MemVO;
import com.painter.model.PainterService;
import com.painter.model.PainterVO;
import com.painter_tag.model.PainterTagService;
import com.painter_tag_map.model.PainterTagMapService;
import com.painter_tag_map.model.PainterTagMapVO;

import tools.HeadphotoTool;
import tools.YclTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class PainterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String action = req.getParameter("action");
		
		//20201103 by YCL
		//action=showPic已廢棄，請改用GetOrigPtr.java(取得原始圖片大小版本)、GetShrinkPtr.java(取得指定pixel版本)
		//為避免影響已使用的人，此段暫時不mark
		if ("showPic".contentEquals(action)) {

			res.setContentType("image/gif");
			ServletOutputStream out = res.getOutputStream();
			try {
				Integer ptr_no = Integer.valueOf(req.getParameter("ptr_no").trim());
				PainterService painterSvc = new PainterService();
				YclTools.readByteArrayFromDB(req, res, painterSvc.getPicByPtrNo(ptr_no));
			} catch (Exception e) {
				System.out.println("===[PainterServlet]無法取得作品圖片");
				InputStream in = getServletContext().getResourceAsStream("/backend/img/null.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}

		} else if("showCreatorPhoto".contentEquals(action)){
			
			res.setContentType("image/gif");
			ServletOutputStream out = res.getOutputStream();
			String sid = ((String)req.getParameter("sid")).trim();
			
			try{
				HeadphotoTool.printHeadphotoByMemId(req, res, sid);
			}catch(Exception e) {
				
				
				System.out.println("===[PainterServlet]無法取得會員頭像");
				System.out.println(e);
				System.out.println("改取:" + getServletContext().getRealPath("/frontend/template/YCL/img/fakeAcctPhoto.png"));
				
				
				InputStream in = new FileInputStream(getServletContext().getRealPath("/frontend/template/YCL/img/fakeAcctPhoto.png"));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			}
			
		}else {
			doPost(req, res);
		}

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("PainterServlet, action = " + action);

		// ======================================================================
		// 新增
		// ======================================================================
		if ("insert".contentEquals(action)) {

			HashMap<Integer, String> errorMsgs = new HashMap<Integer, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				MemVO memVO = (MemVO) req.getSession().getAttribute("memVO");
				String mem_id = memVO.getMem_id(); // 會員編號

				String ptr_nm = req.getParameter("ptr_nm"); // 作品名稱
				String intro = req.getParameter("intro"); // 作品介紹
				Integer priv_stat = Integer.valueOf(req.getParameter("priv_stat")); // 隱私權
				String tag_desc = (String) req.getParameter("tag_desc"); // 作品tag

				Part filePart = req.getPart("imgPath");

				if (ptr_nm == null || (ptr_nm.trim()).length() == 0) {
					errorMsgs.put(1, "請輸入作品名稱");
				}

				boolean isTagDescTooLong = checkTagLength(tag_desc);
				if(!isTagDescTooLong) {
					errorMsgs.put(2, "tag過長，每個tag最多只能100個英文字或33個中文字");
				}
				
				boolean isUploadFile = YclTools.isUploadFile(filePart);
				if (!isUploadFile) {
					errorMsgs.put(3, "請上傳作品圖片");
				}

				byte[] fileByteArray = YclTools.transPartToByteArray(filePart);

				/*************************** 2.開始新增資料 **********************/
				PainterVO painterVO = new PainterVO();
				painterVO.setMem_id(mem_id);
				painterVO.setPtr_nm(ptr_nm);
				painterVO.setIntro(intro);
				painterVO.setPic(fileByteArray);
				painterVO.setPriv_stat(priv_stat);
				painterVO.setPtr_stat(1); // 預設作品狀態為1:正常
				painterVO.setLike_cnt(0);
				painterVO.setCol_cnt(0);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("painterInsertVO", painterVO);
					req.setAttribute("painterInsertTag_desc", tag_desc);
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/painter/listAllPainter.jsp");
					failureView.forward(req, res);
					return;
				}

				System.out.println("====[PainterServlet]開始新增作品 ====");
				PainterService painterSvc = new PainterService();
				painterSvc.insert(painterVO, tag_desc);

				/*************************** 3.新增完畢導回查詢頁 ***********/
				System.out.println("====[PainterServlet]新增作品完畢導回個人作品頁 ====");
//				RequestDispatcher successView = req.getRequestDispatcher("/frontend/painter/listAllPainter.jsp");
//				successView.forward(req, res);
				res.sendRedirect(req.getContextPath() + "/frontend/painter/listAllPainter.jsp");
				return;

			} catch (Exception e) {
				errorMsgs.put(3,"無法取得資料:" + e.getMessage());
				System.out.println("錯誤訊息:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/painter/listAllPainter.jsp");
				failureView.forward(req, res);
			}
		}

		// ======================================================================
		// 刪除
		// ======================================================================
		if ("delete".contentEquals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer ptr_no = Integer.parseInt(req.getParameter("ptr_no")); // 作品代號

			/*************************** 2.開始刪除資料 **********************/
			System.out.println("====[PainterServlet]開始刪除作品====");
			PainterService painterSvc = new PainterService();
			painterSvc.delete(ptr_no, 2); // 2:由創作者刪除

			/*************************** 3.新增完畢導回個人頁 ***********/
			System.out.println("====[PainterServlet]刪除作品完畢，導回個人作品頁====");
			res.sendRedirect(req.getContextPath() + "/frontend/painter/listAllPainter.jsp");
		}

		// ======================================================================
		// 修改
		// ======================================================================
		if ("update".contentEquals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgsForUpdate", errorMsgs);
			Integer ptr_no = Integer.valueOf(req.getParameter("ptr_no")); // 作品編號
			String src = (String) req.getParameter("src");
			String sid = (String) req.getParameter("sid");

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String ptr_nm = req.getParameter("ptr_nm"); // 作品名稱
				String intro = req.getParameter("intro"); // 作品介紹
				Integer priv_stat = Integer.valueOf(req.getParameter("priv_stat")); // 隱私權
				String tag_desc = (String) req.getParameter("tag_desc"); // 作品tag

				if (ptr_nm == null || (ptr_nm.trim()).length() == 0) {
					errorMsgs.add("請輸入作品名稱");
				}

				boolean isTagDescTooLong = checkTagLength(tag_desc);
				if(!isTagDescTooLong) {
					errorMsgs.add("tag過長，每個tag最多只能100個英文字或33個中文字");
				}

				Part filePart = req.getPart("imgPath");
				boolean isUploadFile = YclTools.isUploadFile(filePart);

				/*************************** 2.開始修改資料 **********************/
				System.out.println("====[PainterServlet]開始修改作品====");
				byte[] fileByteArray = null;
				if (isUploadFile) {
					fileByteArray = YclTools.transPartToByteArray(filePart);
				} else {
					PainterService painterSvc = new PainterService();
					PainterVO painterVO = painterSvc.getOnePainter(ptr_no);
					fileByteArray = painterVO.getPic();
				}

				PainterVO painterVO = new PainterVO();
				painterVO.setPtr_no(ptr_no);
				painterVO.setPtr_nm(ptr_nm);
				painterVO.setIntro(intro);
				painterVO.setPic(fileByteArray);
				painterVO.setPriv_stat(priv_stat);
				painterVO.setPtr_stat(1); // 預設作品狀態為1:正常
				painterVO.setLike_cnt(0);
				painterVO.setCol_cnt(0);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("painterVO", painterVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/painter/onePainter.jsp?ptr_no" + ptr_no + "&src=" + src+ "&sid=" + sid);
					failureView.forward(req, res);
					return;
				}

				PainterService painterSvc = new PainterService();
				painterSvc.update(painterVO, tag_desc);

				/*************************** 3.新增完畢導回查詢頁 ***********/
				System.out.println("====[PainterServlet]修改作品完畢====");
				res.sendRedirect(req.getContextPath() + "/frontend/painter/onePainter.jsp?ptr_no=" + ptr_no + "&src=" + src+ "&sid=" + sid);
				return;

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println("錯誤訊息:" + e.fillInStackTrace().getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/painter/onePainter.jsp?ptr_no=" + ptr_no + "&src=" + src+ "&sid=" + sid);
				failureView.forward(req, res);
			}
		}

		
		if ("searchMem".contentEquals(action)) {
			String acct = (String)req.getParameter("keyword");
			System.out.println("接收到的會員關鍵字：" + acct);
		    JSONArray jsonArray = new JSONArray(); //object集合
		    JSONObject jsonObject = null; //每筆object
		    
		    PainterService painterSvc = new PainterService();
		    List<String> list = painterSvc.getMembersByMAccno(acct);
		    
			try {
				for(String result :list) {
					jsonObject = new JSONObject();
					jsonObject.put("m_accno", result);					
					jsonArray.put(jsonObject);
				}									
				System.out.println("[PainterServlet]會員帳號 to JSON: " + jsonArray.toString());
				res.setCharacterEncoding("UTF-8"); //需跟前端網頁相同編碼
				res.setContentType("application/json");
				PrintWriter out = res.getWriter();
				out.write(jsonArray.toString());
				out.flush();
				out.close();
				
			} catch (JSONException e1) {
				e1.printStackTrace();
				System.out.println("[PainterServlet]錯誤訊息:" + e1.fillInStackTrace().getMessage());
			}
		    
		}
		
	}
	
	//判斷輸入的tag是否超過100byte(配合painter_tag.tag_desc的欄位大小)
	private boolean checkTagLength(String tag_desc){
		
		String[] tag_desc_array = tag_desc.split("#");
		
		for(String str : tag_desc_array) {
			
			//純英文的情況
			if(str.length() == str.getBytes().length && str.length() > 100 ) {
				System.out.println("[PainterServlet]tag長度過長：" + str.getBytes().length + "bytes，tag:"+ str);
				return false;
			}
			
			//中英文混雜的情況
			if(str.length() != str.getBytes().length && str.length() >33) {
				return false;
			}
			
		}
		
		return true;
		
	}
	

}
