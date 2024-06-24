package com.mem.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mem.model.MemVO;

public class MemLoginFilter implements Filter {

	private FilterConfig fConfig;

	public void destroy() {
		fConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();
		MemVO memVO = (MemVO) session.getAttribute("memVO");

		if (memVO == null) {
			System.out.println("¨Ó·½location:" + req.getRequestURI());
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/frontend/members/memLogin.jsp");

			return;

		} else {
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig fConfig) {

		this.fConfig = fConfig;

	}

}
