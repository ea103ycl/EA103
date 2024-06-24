package com.painter_act.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemVO;
import com.painter.model.PainterService;
import com.painter.model.PainterVO;
import com.painter_act.model.PainterActService;
import com.painter_act.model.PainterActVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PainterActServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//ajax��
		if(("updatePainterActCnt").contentEquals(action)) {
			
			res.setContentType("application/json");
			List<String> errorMsgs = new LinkedList<String>();
		    JSONArray jsonArray = new JSONArray(); //object���X
		    JSONObject jsonObject = null; //�C��object

			
			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				Integer ptr_no = Integer.valueOf(req.getParameter("ptr_no"));
				Integer act_type = Integer.valueOf(req.getParameter("act_type")); //1:like 2:����
				MemVO memVO = (MemVO)req.getSession().getAttribute("memVO");
				
				System.out.println("====== 使用 ajax 異動 like ========");
				System.out.println( "ptr_no = " + ptr_no + "\t" + 
									"act_type = " + act_type + "\t" +
									"memVO是否為null = " + (memVO!=null));
				
				if(memVO == null) {
					errorMsgs.add("please reLogin");
				}
				
				if (act_type == null || (act_type != 1 && act_type != 2)) {
					errorMsgs.add("act_type錯誤，取到的值= " + act_type);
				}
				
				if (!errorMsgs.isEmpty()) {
					
					for(String msg :errorMsgs) {
						jsonObject = new JSONObject();
						jsonObject.put("errorMsgs", msg);
						jsonArray.put(jsonObject);
					}
										
					System.out.println("錯誤訊息 Object to JSON: " + jsonArray.toString());
					res.getWriter().write(jsonArray.toString());
					return; //�O�o���_�{��
				}
				
				/*************************** 2.�}�l�s�W���  **********************/
				PainterActService painterActSvc = new PainterActService();
				String mem_id = memVO.getMem_id(); // �|���s��
				PainterActVO painterActVO = painterActSvc.getOneByActType(ptr_no, act_type, mem_id);
				System.out.println("取VO完成，VO是否為空 ? " + (painterActVO==null));
			
				if(painterActVO != null) {
					System.out.println("走刪除" + "\t" + painterActVO.getMem_id() + "\t" + painterActVO.getAct_type() + "\t" + painterActVO.getPtr_no());
					painterActSvc.update(-1, painterActVO);
					System.out.println("刪除成功");
				}else {
					System.out.println("走新增");
					painterActVO = new PainterActVO();
					painterActVO.setPtr_no(ptr_no);
					painterActVO.setMem_id(mem_id);
					painterActVO.setAct_type(act_type);
					System.out.println(painterActVO.getMem_id() + "\t" + painterActVO.getAct_type() + "\t" + painterActVO.getPtr_no());
					painterActSvc.update(1, painterActVO);
					System.out.println("新增成功");
				}
			
				//�����H��
				PainterService painterService = new PainterService();
				PainterVO painterVO = painterService.getOnePainter(ptr_no);
				Integer newCnt = (act_type == 1 ? painterVO.getLike_cnt() : painterVO.getCol_cnt()); 
				
				System.out.println( "ptr_no:" + ptr_no +"��mem_id:" + mem_id + "��newCnt:" + newCnt);
				jsonObject = new JSONObject();
				jsonObject.put("newCnt", newCnt);
				jsonObject.put("ptr_no", ptr_no);
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
					res.getWriter().write(jsonArray.toString()); //�󦹮ɷ|return
				} catch (JSONException e1) {
					e1.printStackTrace();
					System.out.println("錯誤訊息:" + e1.fillInStackTrace().getMessage());
				}
			}
		}
		
		
	}

}
