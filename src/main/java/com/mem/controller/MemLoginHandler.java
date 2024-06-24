package com.mem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mem.model.MemService;
import com.mem.model.MemVO;

import tools.MailService;

//@WebServlet("/frontend/members/memLoginHandler.do") //mark by YCL
public class MemLoginHandler extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		String action = req.getParameter("action");
		MemService memSvc = new MemService();

//-------------------------------------------登入--------------------------------------

		if ("login".equals(action))
			{

				MemVO memVO = new MemVO();

				System.out.println("checkingAccount");
				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				try
					{

						String usrid = req.getParameter("usrid");
						System.out.println(usrid);
						if (usrid == null || usrid.trim().length() == 0)
							{
								errorMsgs.add("使用者名稱請勿空白");

							}
						String password = req.getParameter("password");
						System.out.println(password);
						if (password == null || password.trim().length() == 0)
							{
								errorMsgs.add("密碼請勿空白");

							}

						if (!errorMsgs.isEmpty())
							{

								memVO.setM_accno(usrid);
								memVO.setM_psw(password);
								req.setAttribute("memVO", memVO);// 含有輸入格式錯誤的memVO物件,也存入req轉交回登入畫面
								RequestDispatcher failureView = req
										.getRequestDispatcher("/frontend/members/memLogin.jsp");
								failureView.forward(req, res);
								return;
							}

						/*************************** 2.開始搜尋資料,比對帳號是否有效 ********************************/

						Map<String, String> map = new TreeMap<String, String>();
						map = memSvc.getAllAccounts();

						boolean hasUsrid = map.containsKey(usrid);

						System.out.println("hasUsrid" + hasUsrid);

						System.out.println(map.get(usrid));

						if (hasUsrid && (password.equals(map.get(usrid))))
							{

								try
									{ /************ 3.若帳號密碼正確, 將會員資料存入session並轉回原網頁 ***************/
										System.out.println("Usrid Correct? " + true);

										memVO = memSvc.findByAccnoEmail(usrid);

										HttpSession session = req.getSession();
										session.setAttribute("memVO", memVO);

										// 創建session監聽器，將使用者名稱存放至上線列表onLineList
										session.setAttribute("onlineUser", new LoginSessionvBindingListener(usrid));

										String location = (String) session.getAttribute("location");// 取出session是否有存來源網頁
										if (location != null)
											{
												session.removeAttribute("location"); // 如果有來源網頁,先清空session的location屬性,再重導回來源網頁
												res.sendRedirect(location); // location存放為req.getRequestURI,如/frontdend/members/memLogin.jsp
												return;
											}

										else
											{
												// 若沒有來源網頁, 導回首頁
												res.sendRedirect(req.getContextPath() + "/frontend/front_index.jsp");
											}

									} catch (Exception ignored)
									{
									}

							} else
							{
								errorMsgs.add("帳號密碼錯誤或查無此使用者");
								RequestDispatcher failureView = req
										.getRequestDispatcher("/frontend/members/memLogin.jsp");
								failureView.forward(req, res);
								return;
							}

						/*************************** 其他可能的錯誤處理 **********************************/
					} catch (Exception e)
					{
						errorMsgs.add(e.getMessage());
						System.out.println(e.getMessage());
					}

			}

		// -------------------------------------------登出--------------------------------------

		if ("logout".equals(action))
			{
				HttpSession session = req.getSession();
				session.invalidate();

				res.sendRedirect(req.getContextPath() + "/frontend/front_index.jsp");
				return;

			}

		// -------------------------------------------變更密碼--------------------------------------

		if ("changePwd".equals(action))
			{

				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try
					{

						String oldPassword = req.getParameter("oldPassword").trim();

						String newPassword1 = req.getParameter("newPassword1").trim();
						String newPassword2 = req.getParameter("newPassword2").trim();
						System.out.print(oldPassword);

						System.out.print(newPassword1);

						System.out.print(newPassword2);

						// 先取出目前登入的使用者帳號
						HttpSession session = req.getSession();
						MemVO memVO = (MemVO) session.getAttribute("memVO");
						MemVO newMemVO = memSvc.findByPrimaryKey(memVO.getMem_id());
						String mem_id = newMemVO.getMem_id();

						// 取出資料庫該帳號的密碼
						String realPwd = newMemVO.getM_psw();

						String pswReg = "^[(a-zA-Z0-9)]{6,20}$";

						// 錯誤驗證
						if (oldPassword == null || newPassword1 == null || newPassword2 == null
								|| oldPassword.trim().length() == 0 || newPassword1.trim().length() == 0
								|| newPassword2.trim().length() == 0)
							{
									{
										errorMsgs.add("密碼請勿空白");
									}
							} else if (!oldPassword.equals(realPwd))
							{

								errorMsgs.add("原密碼輸入不正確");

							} else if (!newPassword1.trim().matches(pswReg) || !newPassword2.trim().matches(pswReg))
							{
								errorMsgs.add("新密碼輸入格式錯誤");
							}

						else if (!newPassword1.equals(newPassword2))
							{
								errorMsgs.add("兩次密碼輸入密碼不一致,請重新輸入");
							} else
							{

								System.out.println("通過確認密碼驗證");

								// 通過驗證,開始更新密碼

								memSvc.updatePsw(mem_id, newPassword1);
								System.out.println("密碼已更新:" + mem_id + ",新密碼:" + newPassword1);
								// 更新session資料
								MemVO newSessionMemVO = memSvc.findByPrimaryKey(memVO.getMem_id());
								session.setAttribute("memVO", newSessionMemVO);

								// 回傳ajax 成功訊息
								out.print("true");
								System.out.println("已傳送ajax");

							}

						// 如有錯誤轉交回原畫面
						if (!errorMsgs.isEmpty())
							{
								out.print("有錯誤訊息轉交回原畫面");
								RequestDispatcher failureView = req
										.getRequestDispatcher("/frontend/members/memArea.jsp");
								System.out.println(errorMsgs);
								failureView.forward(req, res);
								System.out.println("有錯誤訊息轉交回原畫面");
								return;
							}

						/*************************** 其他可能的錯誤處理 **********************************/
					} catch (Exception e)
					{
						out.print("Exception");
						errorMsgs.add(e.getMessage());

					}

			}

		// -------------------------------------------忘記密碼寄送email--------------------------------------

		if ("forgetPsw".equals(action))
			{

				String directory = "/frontend/members/images/logobg2.png";
				String photoPath = getServletContext().getRealPath(directory);

				System.out.println(photoPath);

				String subject = "ArtsBlock-重設您的密碼";
				String email = null;

				String input = req.getParameter("input").trim();

				String emailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				String usridReg = "^[(a-zA-Z0-9)]{6,20}$";

				if (input == null || input.trim().length() == 0)
					{
						out.print(false);

					} else if (input.trim().matches(emailReg) || input.trim().matches(usridReg))
					{
						// 輸入電子信箱 或 使用者名稱符合格式

						try
							{ // 開始查詢是否有該使用者及寄送郵件 並回傳ture
								MemVO memVO = memSvc.findByAccnoEmail(input);

								email = memVO.getM_email();

								MailService.sendMail(email, subject, photoPath, req, memVO);

								out.print(true);

							} catch (Exception e)
							{ // 查詢不到則回傳false
								out.print(false);
								e.printStackTrace();
							}

					}

				System.out.println("通過電子信箱驗證");

			}

		// ------------------------------------從emailURL到修改密碼頁面-------------------------------------------

		if ("URLfromEmail".equals(action))
			{

				System.out.println("URLfromEmail");
//			// 取得請求參數中的accno
				String mem_id = req.getParameter("memid");
				MemVO memVO = (MemVO) memSvc.findByPrimaryKey(mem_id);
				System.out.println("URLfromEmail mem_id:" + mem_id);

				// 將會員vo存入session並轉交
				HttpSession session = req.getSession();
				session.setAttribute("memVO", memVO);

				RequestDispatcher forgotPswView = req.getRequestDispatcher("/frontend/members/memVerify.jsp");
				forgotPswView.forward(req, res);

			}

		// ------------------------------------修改密碼頁面-------------------------------------------

		if ("changePwdfromEmail".equals(action))
			{

				try
					{

						// 錯誤驗證
						String newPassword1 = req.getParameter("newPassword1").trim();
						String newPassword2 = req.getParameter("newPassword2").trim();
						System.out.println("newPassword1:" + newPassword1 + ",newPassword2:" + newPassword2);
						System.out.println(newPassword1.equals(newPassword2));
						if (newPassword1.equals(newPassword2))
							{

								System.out.println("newPassword1.equals(newPassword2):yessss");

								// 取出session會員VO及目前登入的使用者帳號
								HttpSession session = req.getSession();
								MemVO memVO = (MemVO) session.getAttribute("memVO");
								String mem_id = memVO.getMem_id();

								System.out.println("changePwdfromEmail mem_id:" + mem_id);

								// 通過驗證,開始更新密碼

								memSvc.updatePsw(mem_id, newPassword1);
								System.out.println("密碼已更新:" + mem_id + ",新密碼:" + newPassword1);
								// 更新session資料
								MemVO newSessionMemVO = memSvc.findByPrimaryKey(mem_id);
								session.setAttribute("memVO", newSessionMemVO);

								// 回傳ajax 成功訊息
								out.print("true");
								System.out.println("已傳送ajax");

							} else
							{

								out.print(false);
								return;
							}

						/*************************** 其他可能的錯誤處理 **********************************/
					} catch (Exception e)
					{
						out.print(false);
						e.getMessage();
						return;

					}

			}

	}

}
