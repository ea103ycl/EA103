package com.prod.controller;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)



public class ShoppingServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		
		
   

	
	//模糊查詢
			if ("Fuzzy_Search".equals(action)) { // 來自select_page.jsp的請求
				
		    	List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);
				
				HttpSession session = req.getSession();

				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					String prod_name = req.getParameter("prod_name");
					/***************************2.開始查詢資料*****************************************/
					ProdService prodSvc = new ProdService();
					List<ProdVO> fuzzy_list = new ArrayList<ProdVO>();
					fuzzy_list = prodSvc.FuzzySearch(prod_name);
					
					if (fuzzy_list.size()==0) {
						errorMsgs.add("查無相關商品!!");
						session.setAttribute("fuzzy_list",fuzzy_list);
					}
					
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/frontend/shop/FuzzySearch.jsp");
						failureView.forward(req, res);
						return;//程式中斷
					}
					
					/***************************3.查詢完成,準備轉交(Send the Success view)*************/
					session.setAttribute("fuzzy_list", fuzzy_list); 
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/FuzzySearch.jsp"); 
					successView.forward(req, res);

					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					errorMsgs.add("查詢失敗!!:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shop/FuzzySearch.jsp");
					failureView.forward(req, res);
			
				}
			}
	
			
	//類別查詢
			if ("Ma_Search".equals(action)) { // 來自select_page.jsp的請求
				
		    	List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);
				
				HttpSession session = req.getSession();

				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					String ma_no = req.getParameter("ma_no");
					
					/***************************2.開始查詢資料*****************************************/
					ProdService prodSvc = new ProdService();
					List<ProdVO> ma_list = new ArrayList<ProdVO>();
					ma_list = prodSvc.MaSearch(ma_no);
					
					if (ma_list.size()==0) {
						errorMsgs.add("查無相關商品!!");
						session.setAttribute("ma_list",ma_list);
					}
					
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/frontend/shop/MaSearch.jsp");
						failureView.forward(req, res);
						return;//程式中斷
					}
					
					/***************************3.查詢完成,準備轉交(Send the Success view)*************/
					session.setAttribute("ma_list", ma_list); 
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/MaSearch.jsp"); 
					successView.forward(req, res);

					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					errorMsgs.add("查詢失敗!!:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shop/MaSearch.jsp");
					failureView.forward(req, res);
			
				}
			}
			
			
			
			
			//依照作品查詢
			if ("Ptr_Search".equals(action)) { // 來自select_page.jsp的請求
				
		    	List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);
				
				HttpSession session = req.getSession();

				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					Integer ptr_no = new Integer(req.getParameter("ptr_no").trim());
					
					/***************************2.開始查詢資料*****************************************/
					ProdService prodSvc = new ProdService();
					List<ProdVO> ptr_list = new ArrayList<ProdVO>();
					ptr_list = prodSvc.PtrSearch(ptr_no);
					
					if (ptr_list.size()==0) {
						errorMsgs.add("查無相關商品!!");
						session.setAttribute("ptr_list",ptr_list);
					}
					
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/frontend/shop/PtrSearch.jsp");
						failureView.forward(req, res);
						return;//程式中斷
					}
					
					/***************************3.查詢完成,準備轉交(Send the Success view)*************/
					session.setAttribute("ptr_list", ptr_list); 
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/PtrSearch.jsp"); 
					successView.forward(req, res);

					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					errorMsgs.add("查詢失敗!!:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shop/PtrSearch.jsp");
					failureView.forward(req, res);
			
				}
			}
			
			
			//同作品作品不同素材查詢
			if ("Other_Ma_Search".equals(action)) { // 來自select_page.jsp的請求
				
		    	List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);
				
				

				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					Integer ptr_no = new Integer(req.getParameter("ptr_no").trim());
					String ma_no = req.getParameter("ma_no").trim();
					Integer prod_no = new Integer(req.getParameter("prod_no"));//如果沒有其他素材的話用這個
					/***************************2.開始查詢資料*****************************************/
					
					
					ProdService prodSvc = new ProdService();
					ProdVO prodVO = prodSvc.getOneOtherMa(ptr_no, ma_no);
					
				
					
					if (prodVO==null) {
						errorMsgs.add("還沒有相關商品~拍謝^.^");
						ProdVO prodVO2 = prodSvc.getOneProd(prod_no);
						req.setAttribute("prodVO", prodVO2); //含有輸入格式錯誤的物件,也存入req
						RequestDispatcher failureView = req.getRequestDispatcher("/frontend/shop/ProdDetail.jsp");
						failureView.forward(req, res);
						return; //程式中斷
					}
					
					
					
					/***************************3.查詢完成,準備轉交(Send the Success view)*************/
					req.setAttribute("prodVO", prodVO); 
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/ProdDetail.jsp"); 
					successView.forward(req, res);
					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/shop/ProdDetail.jsp");
					failureView.forward(req, res);
				}
			}
					
					
					
					
			
			
			
			
			
			
	//查看商品詳情
			if ("getOne_For_Detail".equals(action)) { 
				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					Integer prod_no = new Integer(req.getParameter("prod_no"));
					/***************************2.開始查詢資料*****************************************/
					ProdService prodSvc = new ProdService();
					ProdVO prodVO = prodSvc.getOneProd(prod_no);
					/***************************3.查詢完成,準備轉交(Send the Success view)*************/
					req.setAttribute("prodVO", prodVO); 
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/ProdDetail.jsp"); 
					successView.forward(req, res);
					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/shop/EShop.jsp");
					failureView.forward(req, res);
				}
			}
			
			
			//清空購物車
			if ("Clear_Cart".equals(action)) { 
				try {
					
					HttpSession session = req.getSession();
					Vector<ProdVO> buylist = (Vector<ProdVO>) session.getAttribute("shoppingcart");
					session.removeAttribute("shoppingcart");//清空購物車
					
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/shop/Cart.jsp"); 
					successView.forward(req, res);
					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/shop/Cart.jsp");
					failureView.forward(req, res);
				}
			}
			
			
			
			
			
	}
}

