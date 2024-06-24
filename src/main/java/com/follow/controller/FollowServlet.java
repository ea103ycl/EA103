package com.follow.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.follow.model.FollowService;
import com.follow.model.FollowVO;
import com.lv.model.LvService;
import com.lv.model.LvVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.painter.model.PainterService;
import com.painter.model.PainterVO;
import com.painter_act.model.PainterActService;
import com.painter_act.model.PainterActVO;

public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		//走ajax
		if(action.contentEquals("update")) {
			
			res.setCharacterEncoding("UTF-8"); //不寫會亂碼
			res.setContentType("application/json");
			List<String> errorMsgs = new LinkedList<String>();
		    JSONArray jsonArray = new JSONArray(); //object集合
		    JSONObject jsonObject = null; //每筆object
		    
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				MemVO memVO = (MemVO)req.getSession().getAttribute("memVO");
				String sid = (String)req.getParameter("sid"); //使用者目前在哪位會員的主頁面
				String f_mem_id = (String)req.getParameter("f_mem_id"); //使用者要關注OR取消關注的對象
				
				System.out.println("====== 使用 ajax 異動 追蹤 ========");
				System.out.println("session內的使用者【" + ( memVO != null ? memVO.getMem_id() : "NULL" ) + "】，目前正在瀏覽【" + sid + "】的主頁，並要求關注/取消關注【" + f_mem_id + "】");
				
				if(memVO == null) {
					errorMsgs.add("please reLogin");
				}
				
				if (!errorMsgs.isEmpty()) {
					
					for(String msg :errorMsgs) {
						jsonObject = new JSONObject();
						jsonObject.put("errorMsgs", msg);
						jsonArray.put(jsonObject);
					}
										
					System.out.println("錯誤訊息 Object to JSON: " + jsonArray.toString());
					res.getWriter().write(jsonArray.toString());
					return; //記得中斷程式
				}
				
				/*************************** 2.開始新增資料  **********************/
				FollowService followSvc = new FollowService();
				String mem_id = memVO.getMem_id(); // 會員編號
				
				//先判斷是否已有關注資料存在
				Integer followdInd = followSvc.getCnt(mem_id, f_mem_id);
				System.out.println("更新前，系統內 【" + ( followdInd == 0 ? "沒有" : "有" ) + "】" + mem_id + "關注" + f_mem_id + "的資料");
				
				System.out.println("=========================轉交DAO處理");
				//更新資料(dao內會判斷要刪除or新增)
				FollowVO followVO = new FollowVO();
				followVO.setMem_id(mem_id);
				followVO.setF_mem_id(f_mem_id);
				followSvc.update(followVO);
				System.out.println("=========================DAO處理完畢");
				
				//重判斷是否已有關注資料存在
				followdInd = followSvc.getCnt(mem_id, f_mem_id);
				System.out.println("更新後，系統內 【" + ( followdInd == 0 ? "沒有" : "有" ) + "】" + mem_id + "關注" + f_mem_id + "的資料");
				
				//判斷使用者當前是否在要關注/取消關注對象的主頁上
				Boolean updateSidDataInd = (f_mem_id.contentEquals(sid));
				System.out.println( "使用者要求異動的關注對象與當前主頁【" + updateSidDataInd + ( updateSidDataInd ? "同一人" : "不同人" ) + "】" );
				
				//判斷使用者當前是否在自己的主頁上
				Boolean updateOwnerDataInd = (mem_id.contentEquals(sid));
				System.out.println( "使用者與當前主頁【" + updateOwnerDataInd + ( updateOwnerDataInd ? "同一人" : "不同人" ) + "】" );
				
				//若是則須異動當前主頁的關注人數、粉絲數、里程碑
				Integer newFollowCnt = 0;
				Integer newFansCnt = 0;
				Integer newLv = 0;
				LvVO newLvVO = new LvVO();  
				if(updateSidDataInd || updateOwnerDataInd) {
					//重取當前主頁會員的關注人數
					newFollowCnt = followSvc.getFollowCnt(sid);
					System.out.println( "當前主頁的已關注人數變更為" + newFollowCnt );
					
					//重取當前主頁會員的粉絲人數
					newFansCnt = followSvc.getFansCnt(sid);
					System.out.println( "當前主頁的粉絲人數變更為" + newFansCnt );
					
					//重取當前主頁會員的里程碑等級
					MemService memSvc = new MemService();
					newLv = memSvc.findByPrimaryKey(sid).getLv();
					
					//重取lv
					LvService lvSvc = new LvService();
					newLvVO = lvSvc.findByPrimaryKey(newLv);
					System.out.println("當前主頁的里程碑等級變更為" + newLv + "，" + newLvVO.getLv_nm() + "，該等級粉絲數最少須" + newLvVO.getF_cnt() + "人");
					
				}
				
				jsonObject = new JSONObject();
				jsonObject.put("followdInd", followdInd); //異動後的追蹤狀況
				jsonObject.put("mem_id", mem_id);
				jsonObject.put("sid", sid);
				jsonObject.put("f_mem_id", f_mem_id); //本次使用者要求追蹤/取消追蹤的對象
//				jsonObject.put("updateSidDataInd", updateSidDataInd); //是否要異動當前主頁會員的頁面資料
				jsonObject.put("updateOwnerDataInd", updateOwnerDataInd); //是否要異動當前主頁會員的頁面資料(使用者在自己頁面上異動的情況)
				jsonObject.put("newFollowCnt", newFollowCnt); //當前主頁會員的最新關注人數
				jsonObject.put("newFansCnt", newFansCnt); //當前主頁會員的最新粉絲人數
				jsonObject.put("newLv", newLv); //當前主頁會員的最新里程碑等級
				jsonObject.put("newLvNm",newLvVO.getLv_nm()); //當前主頁會員的最新里程碑名稱
				jsonObject.put("newLvFCnt", newLvVO.getF_cnt()); //當前主頁會員的最新里程碑門檻
				jsonArray.put(jsonObject);
//				res.getWriter().write(jsonArray.toString());
				
				//學長範例的寫法
				res.setCharacterEncoding("UTF-8"); //需跟前端網頁相同編碼
				res.setContentType("application/json");
				PrintWriter out = res.getWriter();
				out.write(jsonArray.toString());
				out.flush();
				out.close();
								
			}catch(Exception e) {
				errorMsgs.add("無法異動資料:" + e.getMessage());
				System.out.println("錯誤訊息:" + e.fillInStackTrace().getMessage());
				
				try {
					for(String msg :errorMsgs) {
						jsonObject = new JSONObject();
						jsonObject.put("errorMsgs", msg);					
						jsonArray.put(jsonObject);
					}									
					System.out.println("錯誤訊息 Object to JSON: " + jsonArray.toString());
					res.getWriter().write(jsonArray.toString()); //於此時會return
				} catch (JSONException e1) {
					e1.printStackTrace();
					System.out.println("錯誤訊息:" + e1.fillInStackTrace().getMessage());
				}
			}
		}
		
		
		if(action.contentEquals("removeFans")) {
			
			res.setContentType("application/json");
			List<String> errorMsgs = new LinkedList<String>();
		    JSONArray jsonArray = new JSONArray(); //object集合
		    JSONObject jsonObject = null; //每筆object
		    
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				MemVO memVO = (MemVO)req.getSession().getAttribute("memVO");
				String fans_mem_id = (String)req.getParameter("fans_mem_id"); //使用者要移除的粉絲ID
				
				System.out.println("====== 使用 ajax 異動 追蹤 ========");
				System.out.println("session內的使用者【" + ( memVO != null ? memVO.getMem_id() : "NULL" ) + "】，要求移除粉絲【" + fans_mem_id + "】");
				
				if(memVO == null) {
					errorMsgs.add("please reLogin");
				}
				
				if (!errorMsgs.isEmpty()) {
					
					for(String msg :errorMsgs) {
						jsonObject = new JSONObject();
						jsonObject.put("errorMsgs", msg);
						jsonArray.put(jsonObject);
					}
										
					System.out.println("錯誤訊息 Object to JSON: " + jsonArray.toString());
					res.getWriter().write(jsonArray.toString());
					return; //記得中斷程式
				}
				
				/*************************** 2.開始新增資料  **********************/
				FollowService followSvc = new FollowService();
				String mem_id = memVO.getMem_id(); // 會員編號
				
				System.out.println("=========================轉交DAO處理");
				FollowVO followVO = new FollowVO();
				followVO.setMem_id(fans_mem_id);
				followVO.setF_mem_id(mem_id);
				followSvc.update(followVO);
				System.out.println("=========================DAO處理完畢");
				Integer followdInd = followSvc.getCnt(fans_mem_id, mem_id);
				System.out.println("更新後，系統內 【" + ( followdInd == 0 ? "沒有" : "有" ) + "】" + fans_mem_id + "關注" + mem_id + "的資料");
				
				//重取當前主頁會員的粉絲人數
				Integer newFansCnt = followSvc.getFansCnt(mem_id);
				System.out.println( "當前主頁的粉絲人數變更為" + newFansCnt );
					
				//重取當前主頁會員的里程碑等級
				MemService memSvc = new MemService();
				Integer newLv = memSvc.findByPrimaryKey(mem_id).getLv();
				
				//重取lv
				LvService lvSvc = new LvService();
				LvVO newLvVO = lvSvc.findByPrimaryKey(newLv);
				System.out.println("當前主頁的里程碑等級變更為" + newLv + "，" + newLvVO.getLv_nm() + "，該等級粉絲數最少須" + newLvVO.getF_cnt() + "人");
				
				jsonObject = new JSONObject();
				jsonObject.put("fans_mem_id", fans_mem_id); //本次移除的粉絲ID
				jsonObject.put("newFansCnt", newFansCnt); //最新粉絲人數
				jsonObject.put("newLv", newLv); //最新里程碑等級
				jsonObject.put("newLvNm", newLvVO.getLv_nm()); //當前主頁會員的最新里程碑名稱
				jsonObject.put("newLvFCnt", newLvVO.getF_cnt()); //當前主頁會員的最新里程碑門檻

				jsonArray.put(jsonObject);
				res.getWriter().write(jsonArray.toString());
								
			}catch(Exception e) {
				errorMsgs.add("無法異動資料:" + e.getMessage());
				System.out.println("錯誤訊息:" + e.fillInStackTrace().getMessage());
				
				try {
					for(String msg :errorMsgs) {
						jsonObject = new JSONObject();
						jsonObject.put("errorMsgs", msg);					
						jsonArray.put(jsonObject);
					}									
					System.out.println("錯誤訊息 Object to JSON: " + jsonArray.toString());
					res.getWriter().write(jsonArray.toString()); //於此時會return
				} catch (JSONException e1) {
					e1.printStackTrace();
					System.out.println("錯誤訊息:" + e1.fillInStackTrace().getMessage());
				}
			}
		}
		
	}

}
