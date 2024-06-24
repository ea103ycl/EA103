package com.ad.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Advertisement_JDBCDAO implements Advertisement_DAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO ADVERTISEMENT (ad_no,ad_name,ad_link,ad_upload_time,ad_remove_time,ad_photo) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ad_no,ad_name,ad_link,ad_upload_time,ad_remove_time,ad_photo FROM ADVERTISEMENT order by ad_no";
	private static final String GET_ONE_STMT = "SELECT ad_no,ad_name,ad_link,ad_upload_time,ad_remove_time,ad_photo FROM ADVERTISEMENT where ad_no = ?";
	private static final String DELETE = "DELETE FROM ADVERTISEMENT where ad_no = ?";
	private static final String UPDATE = "UPDATE ADVERTISEMENT set ad_name=?, ad_link=?, ad_upload_time=?, ad_remove_time=?, ad_photo=? where ad_no = ?";
	private static final String SEND_PHOTO = "UPDATE ADVERTISEMENT set ad_photo=? where ad_no = ?";

	@Override
	public void insert(Advertisement_VO advertisement_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, advertisement_VO.getAdNo());
			pstmt.setString(2, advertisement_VO.getAdName());
			pstmt.setString(3, advertisement_VO.getAdLink());
			pstmt.setDate(4, advertisement_VO.getAdUploadTime());
			pstmt.setDate(5, advertisement_VO.getAdRemoveTime());
			pstmt.setBytes(6, advertisement_VO.getAdPhoto());

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

//	@Override
//	public void update(Advertisement_VO advertisement_VO) {
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
//			
//			pstmt.setString(1, advertisement_VO.getAdName());
//			pstmt.setString(2, advertisement_VO.getAdLink());
//			pstmt.setDate(3, advertisement_VO.getAdUploadTime());
//			pstmt.setDate(4, advertisement_VO.getAdRemoveTime());
//			pstmt.setBytes(5, advertisement_VO.getAdPhoto());
//			pstmt.setString(6, advertisement_VO.getAdNo());
//
//			pstmt.executeUpdate();
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
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

	public void update(Advertisement_VO advertisement_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SEND_PHOTO);

			pstmt.setBytes(1, advertisement_VO.getAdPhoto());
			pstmt.setString(2, advertisement_VO.getAdNo());

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
	public void delete(String ad_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ad_no);

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
	public Advertisement_VO findByPrimaryKey(String ad_no) {

		Advertisement_VO advertisement_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ad_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				advertisement_VO = new Advertisement_VO();
				advertisement_VO.setAdNo(rs.getString("ad_no"));
				advertisement_VO.setAdName(rs.getString("ad_name"));
				advertisement_VO.setAdLink(rs.getString("ad_link"));
				advertisement_VO.setAdUploadTime(rs.getDate("ad_upload_time"));
				advertisement_VO.setAdRemoveTime(rs.getDate("ad_remove_time"));
				advertisement_VO.setAdPhoto(rs.getBytes("ad_photo"));
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
		return advertisement_VO;
	}

	@Override
	public List<Advertisement_VO> getAll() {
		List<Advertisement_VO> list = new ArrayList<Advertisement_VO>();
		Advertisement_VO advertisement_VO = null;

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
				advertisement_VO = new Advertisement_VO();
				advertisement_VO.setAdNo(rs.getString("ad_no"));
				advertisement_VO.setAdName(rs.getString("ad_name"));
				advertisement_VO.setAdLink(rs.getString("ad_link"));
				advertisement_VO.setAdUploadTime(rs.getDate("ad_upload_time"));
				advertisement_VO.setAdRemoveTime(rs.getDate("ad_remove_time"));
				advertisement_VO.setAdPhoto(rs.getBytes("ad_photo"));
				list.add(advertisement_VO); // Store the row in the list
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

		Advertisement_JDBCDAO dao = new Advertisement_JDBCDAO();
		byte[] pic = null;

//		// 新增
//		Advertisement_VO advertisement_VO1 = new Advertisement_VO();
//		advertisement_VO1.setAdNo("A0050");
//		advertisement_VO1.setAdName("咖啡");
//		advertisement_VO1.setAdLink(" ");
//		advertisement_VO1.setAdUploadTime(java.sql.Date.valueOf("2005-01-01"));
//		advertisement_VO1.setAdRemoveTime(java.sql.Date.valueOf("2006-01-01"));
//		try {
//			pic = getPictureByteArray("C:/img/5.png");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		advertisement_VO1.setAdPhoto(pic);
//		dao.insert(advertisement_VO1);
//	}

		// 修改
//		Advertisement_VO advertisement_VO2 = new Advertisement_VO();
//		advertisement_VO2.setAdNo("A0050");
//		advertisement_VO2.setAdName("運動");
//		advertisement_VO2.setAdLink("A123456");
//		advertisement_VO2.setAdUploadTime(java.sql.Date.valueOf("2020-01-01"));
//		advertisement_VO2.setAdRemoveTime(java.sql.Date.valueOf("2022-01-01"));
//
//		try {
//			pic = getPictureByteArray("C:/img/1.png");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		advertisement_VO2.setAdPhoto(pic);
//		dao.update(advertisement_VO2);
//	}

		// 送照片進DB
//		for (int i = 1; i <= 5; i++) {
//		try {
//			pic = getPictureByteArray("C:/img/" + i + ".png");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		Advertisement_VO advertisement_VO2 = new Advertisement_VO();
//		advertisement_VO2.setAdNo("A000"+i);
//		advertisement_VO2.setAdPhoto(pic);
//		dao.update(advertisement_VO2);
//	}

//		// 刪除
//		dao.delete("A0050");
//
//		// 查詢
//		Advertisement_VO advertisement_VO3 = dao.findByPrimaryKey("A0001");
//		System.out.print(advertisement_VO3.getAdNo() + ",");
//		System.out.print(advertisement_VO3.getAdName() + ",");
//		System.out.print(advertisement_VO3.getAdLink() + ",");
//		System.out.print(advertisement_VO3.getAdUploadTime() + ",");
//		System.out.print(advertisement_VO3.getAdRemoveTime() + ",");
//		System.out.print(advertisement_VO3.getAdPhoto() + ",");
//		System.out.println("---------------------");

		// 查詢
		List<Advertisement_VO> list = dao.getAll();
		for (Advertisement_VO aAdv : list) {
			System.out.print(aAdv.getAdNo() + ",");
			System.out.print(aAdv.getAdName() + ",");
			System.out.print(aAdv.getAdLink() + ",");
			System.out.print(aAdv.getAdUploadTime() + ",");
			System.out.print(aAdv.getAdRemoveTime() + ",");
			System.out.print(aAdv.getAdPhoto() + ",");
			System.out.println();
		}
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
