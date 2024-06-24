package com.painter.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.painter.model.PainterService;
import com.painter.model.PainterVO;
import com.painter_msg.model.PainterMsgService;
import com.painter_msg.model.PainterMsgVO;
import com.painter_tag.model.*;
import com.painter_tag_map.model.PainterTagMapService;
import com.painter_tag_map.model.PainterTagMapVO;

//@WebServlet("/painter/TagGetPic") //mark by YCL
public class TagGetPic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		res.setCharacterEncoding("UTF-8");

		PrintWriter out = res.getWriter();
		String action = req.getParameter("action");

		if ("tagGetPic".equals(action)) { // 要找出最熱門tag 最新tag

			String tag_desc = req.getParameter("searchBar1");
			PainterTagService ptSvc = new PainterTagService();
			List<PainterTagVO> ptVOList = ptSvc.fuzzySearch(tag_desc);
			List<Integer> list = new ArrayList<Integer>();

			if (ptVOList.isEmpty()) {
				String errorMsgs = new String("No Data, Please confirm your search : " + tag_desc + " ?");
				req.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher view = req.getRequestDispatcher("/frontend/front_index.jsp");
				view.forward(req, res);
				return;
			}

			for (PainterTagVO ptVO : ptVOList) {
				String fuzzyTag = ptVO.getTag_desc();
				List<Integer> tagnoList = ptSvc.getPicbyTag(fuzzyTag);
				list.addAll(tagnoList);
			}

			req.setAttribute("ptrnoList", list);

			RequestDispatcher view = req.getRequestDispatcher("/frontend/front_index.jsp");
			view.forward(req, res);
			return;
		}

		if ("getMostLiked".equals(action)) {

			PainterService pSvc = new PainterService();
			Integer totalPic = pSvc.getPicCount();
			String rank = req.getParameter("rank");
			Integer rankBegin = Integer.valueOf(rank);
			if (rankBegin > totalPic) {
				return;
			}
			List<PainterVO> list = pSvc.getMostLiked(rankBegin+1, rankBegin + 15);

			for (PainterVO pVO : list) {
				out.print(
						"<div class='grid-item'>" + "<img draggable='true' ondragstart='drag(event);' id='"+pVO.getPtr_no()+"'"
								+ "src='"+req.getContextPath()+"/painter/getOrigPtr.do?ptr_no="+pVO.getPtr_no()+"'/>"
								+ "</div>");

			}
		}

		if ("searchByTag".equals(action)) {
			String srtag = req.getParameter("srtag");
			PainterTagService ptSvc = new PainterTagService();
			List<PainterTagVO> list = ptSvc.fuzzySearch(srtag);
			JSONObject jsonOb = new JSONObject();
			int i = 0;
			for (PainterTagVO ptVO : list) {
				if (i < 12) {
					i++;
					String result = ptVO.getTag_desc();
					try {
						jsonOb.put("bubble" + i, result);
					} catch (JSONException e) {
						throw new RuntimeException("(TagGetPic) searchByTag error:" + e.getMessage());
					}
				} else {
					break;
				}
			}
			out.println(jsonOb);
		}

		if ("picGetTag".equals(action)) {
			Integer ptrno = Integer.valueOf(req.getParameter("ptrno"));
			PainterTagMapService ptmSvc = new PainterTagMapService();
			PainterTagService ptSvc = new PainterTagService();
			List<PainterTagMapVO> list = ptmSvc.getAllByPtrNo(ptrno);
			JSONObject jsonOb = new JSONObject();

			int i = 0;
			for (PainterTagMapVO ptmVO : list) {
				if (i < 12) {
					i++;
					Integer tagno = ptmVO.getTag_no();
					String result = ptSvc.getPainterTagDesc(tagno);
					try {
						jsonOb.put("bubble" + i, result);
					} catch (JSONException e) {
						throw new RuntimeException("(TagGetPic) searchByTag error:" + e.getMessage());
					}
				} else {
					break;
				}
			}
			out.println(jsonOb);
		}

		if ("msgUpdate".equals(action)) {

			MemService memSvc = new MemService();
			PainterMsgService pmsgSvc = new PainterMsgService();
			PainterTagService ptSvc = new PainterTagService();
			PainterTagMapService ptmSvc = new PainterTagMapService();

			String ptrno = req.getParameter("ptrno");
			List<PainterMsgVO> list = pmsgSvc.getAll(Integer.valueOf(ptrno));
			List<PainterTagMapVO> tagnoList = ptmSvc.getAllByPtrNo(Integer.valueOf(ptrno));

			// ==========getTag=====================
			if (tagnoList.size() != 0) {

				out.print("<div>");
				for (PainterTagMapVO ptmVO : tagnoList) {
					Integer tagno = ptmVO.getTag_no();
					String tagDesc = ptSvc.getOneByTagNo(tagno).getTag_desc();

					out.print("<a> #" + tagDesc + "</a>");
				}
				out.print("</div>");
			}
			// ==========!getTag=====================

			// ==========getComment==============
			if (ptrno.length() == 0 || list.size() == 0) {
				out.print("Leave a comment...");
				return;
			}

			for (PainterMsgVO pmsgVO : list) {
				String memId = pmsgVO.getMem_id();
				MemVO memVO = memSvc.findByPrimaryKey(memId);
				String memName = memVO.getM_name();

				out.print("<div>" + "<div class='msgImg'><img src='" + req.getContextPath()
						+ "/ReadMemPic?action=getPic&memId=" + memId + "'> " + memName + "</div>"
						+ "<div class='msgComm'>" + pmsgVO.getMsg() + "</div>" + "</div>");
			}
			// ==========!getComment==============
		}
		if ("writeComment".equals(action)) {

			Integer ptrno = Integer.valueOf(req.getParameter("ptrno"));
			String comment = req.getParameter("comment");
			HttpSession session = req.getSession();
			PainterMsgService pmsgSvc = new PainterMsgService();
			PainterMsgVO pmsgVO = new PainterMsgVO();
			MemVO memVO = (MemVO) session.getAttribute("memVO");

			if (comment.trim().isEmpty()) {
				System.out.println("(TagGetPic)writeComment: comment is empty String");
				return;
			}
			try {
				pmsgVO.setMem_id(memVO.getMem_id());
				pmsgVO.setMsg(comment);
				pmsgVO.setPtr_no(ptrno);

				pmsgSvc.insert(pmsgVO);

				out.print("insertComplete");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
