package com.material.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import com.emp.model.Emp_Account_VO;
import com.material.model.*;

@MultipartConfig
public class MaterialServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("ma_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入素材名稱。");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/listAllMat.jsp");
					failureView.forward(req, res);
					return;
				}

				String ma_no = str.trim();
				String ma_no_Reg = "^L[0-9]{4}$";

				if (!ma_no.matches(ma_no_Reg)) {
					errorMsgs.add("請輸入正確格式，素材編號為L開頭接四位數字。");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/listAllMat.jsp");
					failureView.forward(req, res);
					return;
				}

				Material_Data_Service matSvc = new Material_Data_Service();
				Material_Data_VO material_Data_VO = matSvc.getOneMaterialData(ma_no);
				if (material_Data_VO == null) {
					errorMsgs.add("查無資料。");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/listAllMat.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("material_Data_VO", material_Data_VO);
				RequestDispatcher successView = req.getRequestDispatcher("/backend/material/listOneMat.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/listAllMat.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			
			try {
				String ma_no = req.getParameter("ma_no");

				Material_Data_Service matSvc = new Material_Data_Service();
				Material_Data_VO material_Data_VO = matSvc.getOneMaterialData(ma_no);
				
				
				req.setAttribute("material_Data_VO", material_Data_VO);
				RequestDispatcher successView = req.getRequestDispatcher("/backend/material/update_mat_input.jsp");
				successView.forward(req, res);
				
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			
			try {
				Material_Data_Service matSvc = new Material_Data_Service();
				List<Material_Data_VO> list = matSvc.getAll();

				Material_Type_Service matySvc = new Material_Type_Service();
				List<Material_Type_VO> list2 = matySvc.getAll();
				
				String ma_no = req.getParameter("ma_no");

				String ma_ty_no = ((req.getParameter("ma_ty_no")).trim());
				String ma_ty_no_Reg = "^T[0-9]{4}$";
				if (ma_ty_no == null || ma_ty_no.length() == 0) {
					errorMsgs.add("請輸入素材種類編號。");
				} else if (!ma_ty_no.matches(ma_ty_no_Reg)) {
					errorMsgs.add("請輸入正確格式，素材種類編號為T開頭接4位數字。");
				} else {
					int count = 0;
					for (Material_Type_VO maty_db : list2) {
						if (ma_ty_no.equals(maty_db.getMaTyNo())) {
							break;
						} else {
							count += 1;
							if (count == list2.size()) {
								errorMsgs.add("素材種類編號不存在");
							}
						}
					}
				}

				String ma_name = ((req.getParameter("ma_name")).trim());
				String ma_name_Reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9-)]{1,30}$";
				if (ma_name == null || ma_name.length() == 0) {
					errorMsgs.add("請輸入素材名稱。");
				} else if (!ma_name.matches(ma_name_Reg)) {
					errorMsgs.add("素材名稱: 只能是中、英文字母、數字和- , 且長度必需在2到30之間。");
				} 
				

				Integer ma_price = null;
				String str = ((req.getParameter("ma_price")).trim());
				if (str == null || str.length() == 0 || str.equals("0")) {
					errorMsgs.add("請輸入素材金額");
				} else {
					try {
						ma_price = new Integer(str);
					} catch (Exception e) {
						errorMsgs.add("素材金額不正確。");
					}
				}

				byte[] ma_photo = null;

				Part part = req.getPart("ma_photo");
				InputStream in = part.getInputStream();
				if (in.available() == 0) {
				Material_Data_VO material_Data_VO = matSvc.getOneMaterialData(ma_no);
				ma_photo = material_Data_VO.getMaPhoto();
				} else {
					byte[] buf = new byte[in.available()];
					in.read(buf);
					ma_photo = buf;
				}

				Integer ma_status = new Integer(req.getParameter("ma_status"));

				Material_Data_VO material_Data_VO = new Material_Data_VO();

				material_Data_VO.setMaNo(ma_no);
				material_Data_VO.setMaTyNo(ma_ty_no);
				material_Data_VO.setMaName(ma_name);
				material_Data_VO.setMaPrice(ma_price);
				material_Data_VO.setMaPhoto(ma_photo);
				material_Data_VO.setMaStatus(ma_status);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("material_Data_VO", material_Data_VO);
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/update_mat_input.jsp");
					failureView.forward(req, res);
					return;
				}

				material_Data_VO = matSvc.updateMaterialData(ma_no, ma_ty_no, ma_name, ma_price, ma_photo, ma_status);

				
				if(requestURL.equals("/backend/material/listMat_ByCompositeQuery.jsp")){
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					List<Material_Data_VO> list3  = matSvc.getAll(map);
					req.setAttribute("listMat_ByCompositeQuery",list3); //  複合查詢, 資料庫取出的list物件,存入request
				}
				
				req.setAttribute("mater_Data_VO", material_Data_VO);
				
				String url = requestURL;			
				RequestDispatcher successView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/update_mat_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_UpdateType".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String ma_ty_no = req.getParameter("ma_ty_no");

				Material_Type_Service matypeSvc = new Material_Type_Service();
				Material_Type_VO material_Type_VO = matypeSvc.getOneMaterialType(ma_ty_no);

				req.setAttribute("material_Type_VO", material_Type_VO);
				RequestDispatcher successView = req.getRequestDispatcher("/backend/material/update_mat_type.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/listAllMatype.jsp");
				failureView.forward(req, res);
			}
		}

		if("updateType".equals(action)) {
			
			List<String>errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			
			try {
				String ma_ty_no = req.getParameter("ma_ty_no");
//				String ma_ty_nam = req.getParameter("ma_ty_nam");			
				Material_Type_Service matypeSvc = new Material_Type_Service();
				Material_Type_VO material_Type_VO = matypeSvc.getOneMaterialType(ma_ty_no);
				String ma_ty_nam = material_Type_VO.getMaTyNam();
				
				String ma_ty_nam_edit = (req.getParameter("ma_ty_nam_edit")).trim();
				String ma_ty_no_editReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9-)]{1,30}$";
				if(ma_ty_nam_edit == null || ma_ty_nam_edit.length() ==0) {
					errorMsgs.add("請輸入新的類別名稱。");
				} else if(!ma_ty_nam_edit.matches(ma_ty_no_editReg)) {
					errorMsgs.add("類別名稱: 只能是中、英文字母、數字和- , 且長度必需在2到30之間。");
				} 
				
//				else if(ma_ty_nam.equals(ma_ty_nam_edit)) {
//					errorMsgs.add("類別名稱不可重複。");
//				}
				
				
				material_Type_VO.setMaTyNo(ma_ty_no);
				material_Type_VO.setMaTyNam(ma_ty_nam);			
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("material_Type_VO", material_Type_VO);
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/update_mat_type.jsp");
					failureView.forward(req, res);
					return;
				}
				matypeSvc.updateMaterialType(ma_ty_no, ma_ty_nam_edit);
				RequestDispatcher successView = req.getRequestDispatcher("/backend/material/listAllMatype.jsp");
				successView.forward(req, res);
				
			} catch(Exception e) {
				errorMsgs.add("更新資料失敗"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/update_mat_type.jsp");
				failureView.forward(req, res);
			}				
		}
		
		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Material_Data_Service matSvc = new Material_Data_Service();
				List<Material_Data_VO> list = matSvc.getAll();

				Material_Type_Service matySvc = new Material_Type_Service();
				List<Material_Type_VO> list2 = matySvc.getAll();
//
				String ma_ty_no = ((req.getParameter("ma_ty_no")).trim());
//				String ma_ty_no_Reg = "^T[0-9]{4}$";
//				if (ma_ty_no == null || ma_ty_no.length() == 0) {
//					errorMsgs.add("請輸入素材種類。");
//				} else if (!ma_ty_no.matches(ma_ty_no_Reg)) {
//					errorMsgs.add("請輸入正確格式，素材種類編號為T開頭接4位數字。");
//				} else {
//					int count = 0;
//					for (Material_Type_VO maty_db : list2) {
//						if (ma_ty_no.equals(maty_db.getMaTyNo())) {
//							break;
//						} else {
//							count += 1;
//							if (count == list2.size()) {
//								errorMsgs.add("素材種類不存在");
//							}
//						}
//					}
//				}
				
				String ma_ty_nam = (req.getParameter("ma_ty_nam")).trim();
				String ma_ty_nam_Reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9-)]{1,30}$";
				
				if((ma_ty_no == null || ma_ty_no.length() == 0)&&(ma_ty_nam == null || ma_ty_nam.length() == 0)){
					errorMsgs.add("請輸入素材種類。");	
				} else if((ma_ty_no != null && ma_ty_no.length() != 0)&&(ma_ty_nam != null && ma_ty_nam.length() != 0)) {
					errorMsgs.add("素材種類不可重複輸入。");
				} else if((!ma_ty_nam.matches(ma_ty_nam_Reg))&&(ma_ty_nam.length()!=0)) {
					errorMsgs.add("素材種類名稱: 只能是中、英文字母、數字和- , 且長度必需在2到30之間。");
				} 
	
				for(Material_Type_VO material_Type_VO :list2) {
					if(ma_ty_nam.equals(material_Type_VO.getMaTyNam())) {
						errorMsgs.add("素材種類不可重複新增。");
						break;
					}	
				  }	
			
//				if(!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/addMat.jsp");
//					failureView.forward(req, res);
//					return;
//				}
		
				String ma_name = ((req.getParameter("ma_name")).trim());
				String ma_name_Reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9-)]{1,30}$";
				if (ma_name == null || ma_name.length() == 0) {
					errorMsgs.add("請輸入素材名稱。");
				} else if (!ma_name.matches(ma_name_Reg)) {
					errorMsgs.add("素材名稱: 只能是中、英文字母、數字和- , 且長度必需在2到30之間。");
				} else {
					for (Material_Data_VO mana_db : list) {
						if (ma_name.equals(mana_db.getMaName())) {
							errorMsgs.add("素材名稱重複。");
							break;
						}
					}	
				}

				Integer ma_price = null;
				String str = ((req.getParameter("ma_price")).trim());
				if (str == null || str.length() == 0 || str.equals("0")) {
					ma_price = 0;
					errorMsgs.add("請輸入素材金額");		
				} else {
					try {
						ma_price = new Integer(str);
					} catch (Exception e) {
						ma_price = 0;
						errorMsgs.add("素材金額不正確。");
					}
				}

				byte[] ma_photo = null;

				Part part = req.getPart("ma_photo");
				InputStream in = part.getInputStream();
				if (in.available() == 0) {
					errorMsgs.add("尚未選擇圖片");
				} else {
					byte[] buf = new byte[in.available()];
					in.read(buf);
					ma_photo = buf;
				}

				Integer ma_status = new Integer(0);

				Material_Data_VO material_Data_VO = new Material_Data_VO();
				Material_Type_VO material_Type_VO = new Material_Type_VO();
							
				if((ma_ty_no == null || ma_ty_no.length() == 0)&&(errorMsgs.isEmpty())) {
			    material_Type_VO = matySvc.addMaterialType(ma_ty_nam);
				ma_ty_no = material_Type_VO.getMaTyNo();
				
				} else if((ma_ty_no == null || ma_ty_no.length() == 0)&&(!errorMsgs.isEmpty())) {
					material_Type_VO.setMaTyNam(ma_ty_nam);	
					req.setAttribute("material_Type_VO", material_Type_VO);
				}
				
				if((req.getParameter("ma_ty_no")).trim().length()!=0) {
				material_Data_VO.setMaTyNo(ma_ty_no);
				}
				material_Data_VO.setMaName(ma_name);
				material_Data_VO.setMaPrice(ma_price);
				material_Data_VO.setMaPhoto(ma_photo);
				material_Data_VO.setMaStatus(ma_status);
			
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("material_Data_VO", material_Data_VO);
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/addMat.jsp");
					failureView.forward(req, res);
					return;
				}

				material_Data_VO = matSvc.addMaterialData(ma_ty_no, ma_name, ma_price, ma_photo, ma_status);

				RequestDispatcher successView = req.getRequestDispatcher("/backend/material/listAllMat.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("新增資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/addMat.jsp");
				failureView.forward(req, res);
			}

		}

		if("insertMatype".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			Material_Type_Service matySvc = new Material_Type_Service();			
			List<Material_Type_VO>list = matySvc.getAll();
			
			try {
				
				String ma_ty_nam = (req.getParameter("ma_ty_nam")).trim();
				String ma_ty_nam_Reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9-)]{1,30}$";
				if(ma_ty_nam == null||ma_ty_nam.length() == 0) {
					errorMsgs.add("請輸入要新增的素材種類名稱。");
				}else if(!ma_ty_nam.matches(ma_ty_nam_Reg)) {
					errorMsgs.add("素材種類名稱: 只能是中、英文字母、數字和- , 且長度必需在2到30之間。");
				}
				
				for(Material_Type_VO material_Type_VO :list) {
					if(ma_ty_nam.equals(material_Type_VO.getMaTyNam())) {
						errorMsgs.add("素材種類不可重複新增。");
						break;
					}	 	
				  }	
				
				Material_Type_VO material_Type_VO = new Material_Type_VO();
				material_Type_VO.setMaTyNam(ma_ty_nam);
				
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("material_Type_VO", material_Type_VO);
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/addMat.jsp");
					failureView.forward(req, res);
					return;
				}
				
				matySvc.addMaterialType(ma_ty_nam);		
				RequestDispatcher successView = req.getRequestDispatcher("/backend/material/listAllMat.jsp");
				successView.forward(req, res);
				
				
			} catch(Exception e) {
				errorMsgs.add("無法新增資料"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/addMat.jsp");
				failureView.forward(req, res);
			}			
		}
				
		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String ma_no = req.getParameter("ma_no");
				Material_Data_Service matSvc = new Material_Data_Service();
				matSvc.deleteMaterialData(ma_no);

				RequestDispatcher successView = req.getRequestDispatcher("/backend/material/listAllMat.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/listAllMat.jsp");
				failureView.forward(req, res);
			}

		}
		
		if("deleteType".equals(action)) {
			List<String>errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String ma_ty_no = req.getParameter("ma_ty_no");
				Material_Data_Service matSvc = new Material_Data_Service();
				matSvc.deletetype(ma_ty_no);
				Material_Type_Service matypeSvc = new Material_Type_Service();
				matypeSvc.deleteMaterialType(ma_ty_no);
				
				RequestDispatcher successView = req.getRequestDispatcher("/backend/material/listAllMatype.jsp");
				successView.forward(req,res);
			
			} catch(Exception e) {
				errorMsgs.add("無法刪除資料"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/listAllMatype.jsp");
				failureView.forward(req, res);
			}			
		}
		
		if("listMat_ByCompositeQuery".equals(action)) {
			List<String>errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
//				Map<String, String[]> map = req.getParameterMap();
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
				if (req.getParameter("whichPage") == null){
					HashMap<String, String[]> map1 = new HashMap<String, String[]>(req.getParameterMap());
					session.setAttribute("map",map1);
					map = map1;
				} 
				
//				if(req.getParameter("ma_price_up") == null || req.getParameter("ma_price_up").length() == 0) {
//					map.put("ma_price_up",new String[] { "9999" });
//				}
//				
//				if(req.getParameter("ma_price_down") == null || req.getParameter("ma_price_down").length() == 0) {
//					map.put("ma_price_down",new String[] { "0" });
//				}
				
//				Set<String> keys = map.keySet();
//				for(String key : keys) {
//					System.out.println(key + " = "+map.get(key)[0]);
//				}
			
				Material_Data_Service matSvc = new Material_Data_Service();
				List<Material_Data_VO> list  = matSvc.getAll(map);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("listMat_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
				RequestDispatcher successView = req.getRequestDispatcher("/backend/material/listMat_ByCompositeQuery.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);							
				
			} catch(Exception e) {
				errorMsgs.add("查詢失敗"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/selectMat.jsp");
				failureView.forward(req, res);
			}			
		}
		
		if("listAllMatype".equals(action)) {
			List<String>errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
			RequestDispatcher successView = req.getRequestDispatcher("/backend/material/listAllMatype.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
			successView.forward(req, res);	
			
			} catch(Exception e) {
				errorMsgs.add("查詢失敗"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/material/selectMat.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
