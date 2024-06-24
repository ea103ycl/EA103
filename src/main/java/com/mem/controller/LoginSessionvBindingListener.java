package com.mem.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

@WebListener
public class LoginSessionvBindingListener implements HttpSessionBindingListener
{

	private String userName;

	private static int count;

	public static int getCount()
	{
		return count;
	}

	public LoginSessionvBindingListener()
	{
	}

	public LoginSessionvBindingListener(String userName)
	{
		this.userName = userName;
	}

	/**
	 * @see HttpSessionBindingListener#valueBound(HttpSessionBindingEvent)
	 */

	// ----------------------------使用者登入--------------------------------
	public void valueBound(HttpSessionBindingEvent event)
	{
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();// 取得Servlet環境

		List onLineList = (List) application.getAttribute("onLineList");// 將onlinelist存入環境中

		if (onLineList == null) // 如果環境中沒有此list,則新增一個並存入環境
			{
				onLineList = new ArrayList<String>();
				application.setAttribute("onLineList", onLineList);
			}

		onLineList.add(this.userName); // 建立新物件時將userName存入list

		// 上線人數增加並存入環境
		LoginSessionvBindingListener.count++;
		application.setAttribute("onlineCount", count);
	}

	/**
	 * @see HttpSessionBindingListener#valueUnbound(HttpSessionBindingEvent)
	 */
	// ----------------------------使用者登出--------------------------------
	public void valueUnbound(HttpSessionBindingEvent event)
	{
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		List onLineList = (List) application.getAttribute("onLineList");// 取得環境內的list
		onLineList.remove(this.userName); // 將此userNames從list移除

		// 上線人數減少並存入環境
		LoginSessionvBindingListener.count--;
		application.setAttribute("onlineCount", count);
	}

}
