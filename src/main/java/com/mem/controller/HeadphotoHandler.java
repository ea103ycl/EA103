package com.mem.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.mem.model.MemService;
import com.mem.model.MemVO;

//@WebServlet("/headphotoHandler.do") //mark by YCL
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class HeadphotoHandler extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{

		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{

//		PrintWriter out = res.getWriter();
		req.setCharacterEncoding("UTF-8"); // �B�z�����ɦW
		res.setContentType("image/gif"); // ��X�G�줸���

//      ���osession�|��VO�η|��ID
		HttpSession session = req.getSession();
		MemVO memVO = (MemVO) session.getAttribute("memVO");
		String mem_id = memVO.getMem_id();

		String action = req.getParameter("action");
// ---------------------------�W�ǹϤ�-------------------------------

		if ("uploadPhoto".equals(action))
			{

//�q�ШD�Ѽƨ��opart����
				Part part = req.getPart("headPhoto");

// �ɮפW�ǥ������ت��a�ؿ�
				String saveDirectory = "/images_uploaded"; // �W���ɮת��ت��a�ؿ�;

				String realPath = getServletContext().getRealPath(saveDirectory);
				System.out.println("realPath=" + realPath); // ���ե�
				// �۰ʫإߥئa�ؿ�
				File fsaveDirectory = new File(realPath);
				if (!fsaveDirectory.exists())
					fsaveDirectory.mkdirs(); // �� ContextPath ���U,�۰ʫإߥئa�ؿ�

				; // ���o�ӷ��ɮצW��
				File f = new File(fsaveDirectory, "photo" + mem_id);

				System.out.println(f.toString());
				part.write(f.toString());

//�A�q�����a�ؿ��y�J�è��obyte[]

				File file = new File(f.toString());
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

//�}�l�e�i��Ʈw

				MemService memSvc = new MemService();

				memSvc.addPhoto(mem_id, photo);

				System.out.println("新增至資料庫成功");

				RequestDispatcher successView = req.getRequestDispatcher("/frontend/members/memArea.jsp");
				successView.forward(req, res);
			}
		// ---------------------------�q��Ʈw���X�Ϥ�-------------------------------

		if ("getPic".equals(action))
			{

				MemService memSvc = new MemService();
				byte[] photofromDB = memSvc.getPhoto(mem_id);
				ServletOutputStream servletOut = res.getOutputStream();
				servletOut.write(photofromDB);

			}
	}

}
