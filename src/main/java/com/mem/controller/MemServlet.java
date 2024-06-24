package com.mem.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mem.model.MemService;
import com.mem.model.MemVO;

import tools.MailService;

//@WebServlet("/frontend/members/mem.do") //mark by YCL
public class MemServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{

		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		MemService memSvc = new MemService();
//�|�����U
		if ("InsertMem".equals(action))
			{

				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try
					{
						/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
						String name = req.getParameter("name");
						String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
						if (name == null || name.trim().length() == 0)
							{
								errorMsgs.add("�|���m�W�ФŪť�");
							} else if (!name.trim().matches(nameReg))
							{ // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
								errorMsgs.add("�|���m�W�п�J���B�^��r��, �B���ץ����b2��10����");
							}
						System.out.println("�q�L�|���m�W����");

						String gender = req.getParameter("gender");

						if (gender == null || gender.trim().length() == 0)
							{
								errorMsgs.add("�п�ܩʧO");
							}
						System.out.println(gender);
						System.out.println("�q�L�ʧO����");

						String mobile = req.getParameter("mobile").trim();
						String mobileReg = "\\d{10}";
						if (mobile == null || mobile.trim().length() == 0)
							{
								errorMsgs.add("����ФŪť�");
							} else if (!mobile.trim().matches(mobileReg))
							{ // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
								errorMsgs.add("�п�J�Q�X�Ʀr�p0912312312");
							}

						System.out.println("�q�L�������");

						String email = req.getParameter("email").trim();
						String emailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
						if (email == null || email.trim().length() == 0)
							{
								errorMsgs.add("�q�l�H�c�ФŪť�");
							} else if (!email.trim().matches(emailReg))
							{ // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
								errorMsgs.add("�п�J���T���q�l�H�c�榡");
							}

						System.out.println("�q�L�q�l�H�c����");

						String zip = req.getParameter("zip");

						System.out.println(zip);

						String city = "";

						Integer zipcode = null;

						if (zip == null || zip.trim().length() == 0)
							{
								errorMsgs.add("�п�ܶl���ϸ�");
							} else
							{

								String[] zips = zip.split(" ", 2);
								city = zips[1];
								zipcode = Integer.parseInt(zips[0]);

								System.out.println(zipcode);
								System.out.println("�q�L�l���ϸ�����");
							}
						String address = req.getParameter("address").trim();

						if (address == null || address.trim().length() == 0)
							{
								errorMsgs.add("�a�}�ФŪť�");

							}

						System.out.println("�q�L�a�}����");

						String usrid = req.getParameter("usrid").trim();
						String usridReg = "^[(a-zA-Z0-9)]{6,20}$";
						if (usrid == null || usrid.trim().length() == 0)
							{
								errorMsgs.add("�b���ФŪť�");
							} else if (!usrid.trim().matches(usridReg))
							{
								errorMsgs.add("�b���u��O�j�p�g�^��r���B�Ʀr, �B���ץ����b6��20����");
							}

						Set<String> usridSet = memSvc.getAllUsrId();

						System.out.println("usrid:" + usrid);
						if (usridSet.contains(usrid))
							errorMsgs.add("���b���w���H�ϥ�");

						System.out.println("�q�L�b������");

						String psw = req.getParameter("psw").trim();
						String pswReg = "^[(a-zA-Z0-9)]{6,20}$";
						String confirmpsw = null;

						if (psw == null || psw.trim().length() == 0)
							{
								errorMsgs.add("�K�X �ФŪť�");
							} else if (!psw.trim().matches(pswReg))
							{
								System.out.println(psw);
								errorMsgs.add("�K�X�u��O�j�p�g�^��r���B�Ʀr , �B���ץ��ݦb6��20����");
							} else
							{

								System.out.println("�q�L�K�X����");
								confirmpsw = req.getParameter("confirmpsw").trim();
								String confirmpswReg = "^[(a-zA-Z0-9)]{6,20}$";
								System.out.println(confirmpsw);
								if (confirmpsw == null || confirmpsw.trim().length() == 0)
									{
										errorMsgs.add("�T�{�K�X �ФŪť�");

									} else if (!confirmpsw.trim().matches(psw))
									{
										errorMsgs.add("�P�Ĥ@����J�K�X���۲�,�Э��s��J");
									} else if (!confirmpsw.trim().matches(confirmpswReg))
									{
										errorMsgs.add("�K�X�u��O�^��r���B�Ʀr , �B���ץ��ݦb6��20����");
									}
								System.out.println("�q�L�T�{�K�X����");
							}

						MemVO memVO = new MemVO();
						memVO.setM_accno(usrid);
						memVO.setM_psw(confirmpsw);
						memVO.setM_gender(gender);
						memVO.setM_name(name);
						memVO.setM_mobile(mobile);
						memVO.setM_city(city);
						memVO.setM_zip(zipcode);
						memVO.setM_addr(address);
						memVO.setM_email(email);

						// Send the use back to the form, if there were errors
						if (!errorMsgs.isEmpty())
							{

								for (String a : errorMsgs)
									{
										System.out.println(a);
									}

								req.setAttribute("memVO", memVO); // �t����J�榡���~��empVO����,�]�s�Jreq
								RequestDispatcher failureView = req
										.getRequestDispatcher("/frontend/members/memRegister.jsp");
								failureView.forward(req, res);
								return;
							}

						/*************************** 2.�}�l�s�W��� ***************************************/

						memSvc.addMem(1, usrid, confirmpsw, name, gender, null, null, mobile, zipcode, city, address,
								email, null, null, 1, 1, 1, 0, 0);

						MemVO registeredMemVO = memSvc.findByAccnoEmail(usrid);

						// �s�W���U�������Ҥ��|��VO��session��,��K���o�T�{�H
						HttpSession session = req.getSession();
						session.setAttribute("registeredMemVO", registeredMemVO);

						// -----------------�s�W�|���w�]�Ϥ�---------------------------------------

						// �q�����a�ؿ��y�J�w�]�Ϩè��o�Ϥ�byte[]
						String defaultPicDirectory = "";
						if (gender.equals("F"))
							{
								defaultPicDirectory = "/frontend/members/images/femaleDefault.png";
							} else
							{
								defaultPicDirectory = "/frontend/members/images/maleDefault.png";
							}

						String defaultPicPath = getServletContext().getRealPath(defaultPicDirectory);

						File file = new File(defaultPicPath);
						FileInputStream fis = new FileInputStream(file);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();// ����Ƭy�|��write���줸��Ʀs��@�Ӥ��ت�byte[]
						byte[] buffer = new byte[8192];
						int i;
						while ((i = fis.read(buffer)) != -1)
							{
								baos.write(buffer, 0, i);
								baos.flush();
							}

						baos.close();
						fis.close();

						byte[] photo = baos.toByteArray();

						// �}�l�e�i��Ʈw

						memSvc.addPhoto(registeredMemVO.getMem_id(), photo);

						System.out.println("預設照片資料庫成功");

						System.out.println("已呼叫MemService新增會員");

						/*************************** 3.�s�W����,�H�e���ҫH ********************/

						String directory = "/frontend/members/images/logobg2.png";
						String photoPath = getServletContext().getRealPath(directory);

						String subject = "ArtsBlock-註冊驗證信";

						MailService.sendEmail4Verify(email, subject, photoPath, req, registeredMemVO);

						/*************************** 4.�ǳ���� ***********/
						String url = "/frontend/members/memRegisterSuccess.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);

						System.out.println("�ǳ�forward");
						successView.forward(req, res);

						/*************************** ��L�i�઺���~�B�z **********************************/
					} catch (Exception e)
					{
						errorMsgs.add(e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memRegister.jsp");
						failureView.forward(req, res);
					}
			}

		// ���o���ҫH
		if ("sendVerifyAgain".equals(action))
			{
				HttpSession session = req.getSession();
				MemVO registeredMemVO = (MemVO) session.getAttribute("registeredMemVO");

				String directory = "/frontend/members/images/logobg2.png";
				String photoPath = getServletContext().getRealPath(directory);

				String subject = "ArtsBlock-���U���ҫH";

				String email = registeredMemVO.getM_email();
				MailService.sendEmail4Verify(email, subject, photoPath, req, registeredMemVO);

				String url = "/frontend/members/memRegisterSuccess.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}

		// �|�����ҫH�������Ҷs
		if ("verify".equals(action))
			{

				String mem_id = req.getParameter("memid");
				MemVO memVO = (MemVO) memSvc.findByPrimaryKey(mem_id);
				System.out.println("verify mem_id:" + mem_id);

				// �N�|��vo�s�Jsession
				HttpSession session = req.getSession();
				session.setAttribute("memVO", memVO);

				// �Ы�session��ť���A�N�ϥΪ̦W�٦s��ܤW�u�C��onLineList
				session.setAttribute("onlineUser", new LoginSessionvBindingListener(mem_id));

				// �H�c���Ҧ��\��^����
				RequestDispatcher verifiedView = req.getRequestDispatcher("/frontend/front_index.jsp");
				verifiedView.forward(req, res);
			}

		// �ק�|����� from memArea.jsp
		if ("UpdateMem".equals(action))
			{
				JSONObject jsonObject = new JSONObject();

				try
					{
						/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
						String name = req.getParameter("name");
						String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
						if (name == null || name.trim().length() == 0)
							{
								jsonObject.put("name", "�m�W�ФŪť�");
							} else if (!name.trim().matches(nameReg))
							{ // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
								jsonObject.put("name", "�|���m�W�п�J���B�^��r��, �B���ץ����b2��10����");
							}

						String gender = req.getParameter("gender");

						String birthday = req.getParameter("birthday");

						Date bday = java.sql.Date.valueOf(birthday);

						String mobile = req.getParameter("mobile").trim();

						String mobileReg = "\\d{10}";
						if (mobile == null || mobile.trim().length() == 0)
							{
								jsonObject.put("mobile", "����ФŪť�");
							} else if (!mobile.trim().matches(mobileReg))
							{ // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
								jsonObject.put("mobile", "�п�J�Q�X�Ʀr�p0912312312");
							}

						String phone = req.getParameter("phone").trim();
						String phoneReg = "[0-9]{10}";
						if (!phone.trim().matches(phoneReg))
							{
								jsonObject.put("phone", "�п�J�榡�p0212345678");
							}

						String email = req.getParameter("email").trim();
						String emailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
						if (email == null || email.trim().length() == 0)
							{
								jsonObject.put("email", "�q�l�H�c�ФŪť�");
							} else if (!email.trim().matches(emailReg))
							{ // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
								jsonObject.put("email", "�п�J���T���q�l�H�c�榡");
							}

						String contactZip = req.getParameter("contactZip");

						String contactCity = "";
						Integer contactZipcode = null;
						if (contactZip == null || contactZip.trim().length() == 0)
							{
								jsonObject.put("contactZip", "�п�J�l���ϸ�");
							} else
							{
								String[] zips = contactZip.split(" ", 2);
								contactCity = zips[1];
								contactZipcode = Integer.parseInt(zips[0]);
							}
						String contactAddress = req.getParameter("contactAddr").trim();

						if (contactAddress == null || contactAddress.trim().length() == 0)
							{
								jsonObject.put("contactAddr", "�a�}�ФŪť�");
							}

						if (jsonObject.length() != 0)
							{
								out.print(jsonObject);
								return;
							}

						HttpSession session = req.getSession();
						MemVO memVO = (MemVO) session.getAttribute("memVO");
						String mem_id = memVO.getMem_id();

						memVO.setM_gender(gender);
						memVO.setM_name(name);
						memVO.setM_mobile(mobile);
						memVO.setM_city(contactCity);
						memVO.setM_zip(contactZipcode);
						memVO.setM_addr(contactAddress);
						memVO.setM_email(email);

						/*************************** 2.�}�l�s�W��� ***************************************/
						memSvc = new MemService();
						memSvc.updateByUser(name, gender, bday, phone, mobile, contactZipcode, contactCity,
								contactAddress, email, mem_id);
						System.out.println("�w��s�|�����");

						MemVO UpdatedmemVO = memSvc.findByPrimaryKey(mem_id);
						session.setAttribute("memVO", UpdatedmemVO);

						/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
						System.out.println("out.print:true");
						// ajax�e�^���\�T��
						out.print("true");

						/*************************** ��L�i�઺���~�B�z **********************************/
					} catch (Exception e)
					{
						e.printStackTrace();
						out.print("exception");
						RequestDispatcher failureView = req.getRequestDispatcher("/frontend/members/memRegister.jsp");
						failureView.forward(req, res);
					}
			}

		if ("checkAccount".equals(action))
			{
				System.out.println("checkingAccount");
				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				try
					{
						System.out.println(req.getParameter("usrid"));
						String usrid = req.getParameter("usrid").trim();
						if (usrid == null || usrid.trim().length() == 0)
							{
								errorMsgs.add("�ϥΪ̦W�ٽФŪť�");

							}

						/*************************** 2.�}�l�j�M���,���b���O�_���� ********************************/

						memSvc = new MemService();

						Set<String> usridSet = memSvc.getAllUsrId();

						System.out.println("usrid:" + usrid);
						if (usridSet.contains(usrid))
							{

								out.print(true);

							} else
							{

								out.print(false);
							}
						/*************************** ��L�i�઺���~�B�z **********************************/
					} catch (Exception e)
					{
						errorMsgs.add(e.getMessage());
					}

			}

		if ("getAllMembers".equals(action))
			{

				memSvc = new MemService();
				List<MemVO> list = memSvc.getAll();

				JSONArray array = new JSONArray(list);
				out.print(array);

			}

		if ("getOneByIdNameAccout".equals(action))
			{

				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try
					{
						/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/

						String input = req.getParameter("input");

						System.out.println("input:" + input);
						String inputReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,20}$";
						if (input == null || input.trim().length() == 0)
							{
								errorMsgs.add("�п�J���j�M���|���b���B�W�٩νs��");
							} else if (!input.matches(inputReg))
							{
								errorMsgs.add("��J�榡���~, �Э��s��J");
							}
						System.out.println("�q�L�j�M����");

						/*********************** 2.�}�l�j�M��� *************************/

						memSvc = new MemService();
						List<MemVO> list = memSvc.findByPKNameAcc(input);

						for (MemVO memVO : list)
							{
								System.out.println("�b��:" + memVO.getM_accno());
								System.out.println("�s��:" + memVO.getMem_id());
								System.out.println("�W��:" + memVO.getM_name());
							}

						JSONArray array = new JSONArray(list);

						out.print(array);

					} catch (Exception e)
					{
						errorMsgs.add(e.getMessage());
						System.out.println(e.getMessage());
					}

			}

		if ("getContactZip".equals(action))
			{

				HttpSession session = req.getSession();
				MemVO memVO = (MemVO) session.getAttribute("memVO");

				System.out.println("getContactZip");

				out.print(new JSONObject(memVO));

			}

	}
}
