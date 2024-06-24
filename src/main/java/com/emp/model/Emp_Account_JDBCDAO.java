package com.emp.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Emp_Account_JDBCDAO implements Emp_Account_DAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO EMP_ACCOUNT (emp_no,emp_name,emp_pwd,emp_pos,emp_mail,emp_photo,emp_status) VALUES (?, ?, ?, ?, ?, ?,?)";
	private static final String GET_ALL_STMT = "SELECT emp_no,emp_name,emp_pwd,emp_pos,emp_mail,emp_photo,emp_status FROM EMP_ACCOUNT order by emp_no";
	private static final String GET_ONE_STMT = "SELECT emp_no,emp_name,emp_pwd,emp_pos,emp_mail,emp_photo,emp_status FROM EMP_ACCOUNT where emp_no = ?";
	private static final String DELETE = "DELETE FROM EMP_ACCOUNT where emp_no = ?";
	private static final String UPDATE = "UPDATE EMP_ACCOUNT set emp_name=?, emp_pwd=?, emp_pos=?, emp_mail=?, emp_photo=?, emp_status=? where emp_no = ?";
	private static final String SEND_PHOTO = "UPDATE EMP_ACCOUNT set emp_photo=? where emp_no = ?";
	private static final String FORGET_PWD = "UPDATE EMP_ACCOUNT set emp_pwd=? where emp_no=?";
	
	@Override
	public Emp_Account_VO insert(Emp_Account_VO emp_Account_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, emp_Account_VO.getEmpNo());
			pstmt.setString(2, emp_Account_VO.getEmpName());
			pstmt.setString(3, emp_Account_VO.getEmpPwd());
			pstmt.setString(4, emp_Account_VO.getEmpPos());
			pstmt.setString(5, emp_Account_VO.getEmpMail());
			pstmt.setBytes(6, emp_Account_VO.getEmpPhoto());
			pstmt.setInt(7, emp_Account_VO.getEmpStatus());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return emp_Account_VO;

	}

	@Override
//	public void update(Emp_Account_VO emp_Account_VO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(UPDATE);
//
//			pstmt.setString(1, emp_Account_VO.getEmpName());
//			pstmt.setString(2, emp_Account_VO.getEmpPwd());
//			pstmt.setString(3, emp_Account_VO.getEmpPos());
//			pstmt.setString(4, emp_Account_VO.getEmpMail());
//			pstmt.setBytes(5, emp_Account_VO.getEmpPhoto());
//			pstmt.setInt(6, emp_Account_VO.getEmpStatus());
//			pstmt.setString(7, emp_Account_VO.getEmpNo());
//
//			pstmt.executeUpdate();
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//
//	}

	public void update(Emp_Account_VO emp_Account_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SEND_PHOTO);

			pstmt.setBytes(1, emp_Account_VO.getEmpPhoto());
			pstmt.setString(2, emp_Account_VO.getEmpNo());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	
	public void reset(Emp_Account_VO emp_Account_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FORGET_PWD);
			
			pstmt.setString(1, emp_Account_VO.getEmpPwd());
			pstmt.setString(2, emp_Account_VO.getEmpNo());
			
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
	
	

	@Override
	public void delete(String emp_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, emp_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public Emp_Account_VO findByPrimaryKey(String emp_no) {

		Emp_Account_VO emp_Account_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, emp_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				emp_Account_VO = new Emp_Account_VO();
				emp_Account_VO.setEmpNo(rs.getString("emp_no"));
				emp_Account_VO.setEmpName(rs.getString("emp_name"));
				emp_Account_VO.setEmpPwd(rs.getString("emp_pwd"));
				emp_Account_VO.setEmpPos(rs.getString("emp_pos"));
				emp_Account_VO.setEmpMail(rs.getString("emp_mail"));
				emp_Account_VO.setEmpPhoto(rs.getBytes("emp_photo"));
				emp_Account_VO.setEmpStatus(rs.getInt("emp_status"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return emp_Account_VO;
	}

	@Override
	public List<Emp_Account_VO> getAll() {
		List<Emp_Account_VO> list = new ArrayList<Emp_Account_VO>();
		Emp_Account_VO emp_Account_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				emp_Account_VO = new Emp_Account_VO();
				emp_Account_VO.setEmpNo(rs.getString("emp_no"));
				emp_Account_VO.setEmpName(rs.getString("emp_name"));
				emp_Account_VO.setEmpPwd(rs.getString("emp_pwd"));
				emp_Account_VO.setEmpPos(rs.getString("emp_pos"));
				emp_Account_VO.setEmpMail(rs.getString("emp_mail"));
				emp_Account_VO.setEmpPhoto(rs.getBytes("emp_photo"));
				emp_Account_VO.setEmpStatus(rs.getInt("emp_status"));
				list.add(emp_Account_VO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {

		Emp_Account_JDBCDAO dao = new Emp_Account_JDBCDAO();
		byte[] pic = null;

//		// 新增
//		Emp_Account_VO emp_Account_VO1 = new Emp_Account_VO();
//		emp_Account_VO1.setEmpNo("E0050");
//		emp_Account_VO1.setEmpName("王小明");
//		emp_Account_VO1.setEmpPwd("A22222");
//		emp_Account_VO1.setEmpPos("總監");
//		emp_Account_VO1.setEmpMail("55555@gmail.com.tw");
//		emp_Account_VO1.setEmpStatus(1);
//
//		try {
//			pic = getPictureByteArray("C:/img/5.png");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		emp_Account_VO1.setEmpPhoto(pic);
//		dao.insert(emp_Account_VO1);

//		// 修改
//		Emp_Account_VO emp_Account_VO2 = new Emp_Account_VO();
//		emp_Account_VO2.setEmpNo("E0050");
//		emp_Account_VO2.setEmpName("小強");
//		emp_Account_VO2.setEmpPwd("A12345");
//		emp_Account_VO2.setEmpPos("蟑螂");
//		emp_Account_VO2.setEmpMail("xxxx@gmail.com.tw");
//		emp_Account_VO2.setEmpStatus(1);
//
//		try {
//			pic = getPictureByteArray("C:/img/1.png");
//		} catch (IOException e) {
//			// 
//			e.printStackTrace();
//		}
//		emp_Account_VO2.setEmpPhoto(pic);
//		dao.update(emp_Account_VO2);
		
				
		
		// 送照片進DB
		for (int i = 1; i <= 9; i++) {
			try {
				pic = getPictureByteArray("C:/img/" + i + ".png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Emp_Account_VO emp_Account_VO2 = new Emp_Account_VO();
			emp_Account_VO2.setEmpNo("E000"+i);
			emp_Account_VO2.setEmpPhoto(pic);
			dao.update(emp_Account_VO2);
		}


		// 刪除
//		dao.delete("E0050");

		// 查詢
//		Emp_Account_VO emp_Account_VO3 = dao.findByPrimaryKey("E0003");
//		System.out.print(emp_Account_VO3.getEmpNo() + ",");
//		System.out.print(emp_Account_VO3.getEmpName() + ",");
//		System.out.print(emp_Account_VO3.getEmpPwd() + ",");
//		System.out.print(emp_Account_VO3.getEmpPos() + ",");
//		System.out.print(emp_Account_VO3.getEmpMail() + ",");
//		System.out.print(emp_Account_VO3.getEmpPhoto() + ",");
//		System.out.print(emp_Account_VO3.getEmpStatus() + ",");		
//		System.out.println("---------------------");

//		// 查詢
//		List<Emp_Account_VO> list = dao.getAll();
//		for (Emp_Account_VO aEmp : list) {
//			System.out.print(aEmp.getEmpNo() + ",");
//			System.out.print(aEmp.getEmpName() + ",");
//			System.out.print(aEmp.getEmpPwd() + ",");
//			System.out.print(aEmp.getEmpPos() + ",");
//			System.out.print(aEmp.getEmpMail() + ",");
//			System.out.print(aEmp.getEmpPhoto() + ",");
//			System.out.print(aEmp.getEmpStatus() + ",");
//			System.out.println();
//		}
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
}
