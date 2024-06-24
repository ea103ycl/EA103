package com.material.model;

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
import java.util.Map;
import java.util.TreeMap;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_MatData;

public class Material_Data_JDBCDAO implements Material_Data_DAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO MATERIAL_DATA (ma_no,ma_ty_no,ma_name,ma_price,ma_photo,ma_status) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ma_no,ma_ty_no,ma_name,ma_price,ma_photo,ma_status FROM MATERIAL_DATA order by ma_no";
	private static final String GET_ONE_STMT = "SELECT ma_no,ma_ty_no,ma_name,ma_price,ma_photo,ma_status FROM MATERIAL_DATA where ma_no = ?";
	private static final String DELETE = "DELETE FROM MATERIAL_DATA where ma_no = ?";
	private static final String DELETETYPE = "DELETE FROM MATERIAL_DATA where ma_ty_no = ?";
	private static final String GET_ONE_TYPE = "SELECT ma_no,ma_ty_no,ma_name,ma_price,ma_photo,ma_status FROM MATERIAL_DATA where ma_ty_no = ?";
	private static final String UPDATE = "UPDATE MATERIAL_DATA set ma_ty_no=?, ma_name=?, ma_price=?, ma_photo=?, ma_status=? where ma_no = ?";
	private static final String SEND_PHOTO = "UPDATE MATERIAL_DATA set ma_photo=? where ma_no = ?";

	@Override
	public void insert(Material_Data_VO material_Data_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, material_Data_VO.getMaNo());
			pstmt.setString(2, material_Data_VO.getMaTyNo());
			pstmt.setString(3, material_Data_VO.getMaName());
			pstmt.setInt(4, material_Data_VO.getMaPrice());
			pstmt.setBytes(5, material_Data_VO.getMaPhoto());
			pstmt.setInt(6, material_Data_VO.getMaStatus());

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
//	public void update(Material_Data_VO material_Data_VO) {
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
//			pstmt.setString(1, material_Data_VO.getMaTyNo());
//			pstmt.setString(2, material_Data_VO.getMaName());
//			pstmt.setInt(3, material_Data_VO.getMaPrice());
//			pstmt.setBytes(4, material_Data_VO.getMaPhoto());
//			pstmt.setInt(5, material_Data_VO.getMaStatus());
//			pstmt.setString(6, material_Data_VO.getMaNo());
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

	
	@Override
	public void update(Material_Data_VO material_Data_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SEND_PHOTO);

			pstmt.setBytes(1, material_Data_VO.getMaPhoto());
			pstmt.setString(2, material_Data_VO.getMaNo());

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
	public void delete(String ma_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ma_no);

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
	
	public void deletetype(String ma_ty_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(DELETETYPE);
			pstmt.setString(1, ma_ty_no);
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
	public Material_Data_VO findByPrimaryKey(String ma_no) {

		Material_Data_VO material_Data_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ma_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				material_Data_VO = new Material_Data_VO();
				material_Data_VO.setMaNo(rs.getString("ma_no"));
				material_Data_VO.setMaTyNo(rs.getString("ma_ty_no"));
				material_Data_VO.setMaName(rs.getString("ma_name"));
				material_Data_VO.setMaPrice(rs.getInt("ma_price"));
				material_Data_VO.setMaPhoto(rs.getBytes("ma_photo"));
				material_Data_VO.setMaStatus(rs.getInt("ma_status"));
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
		return material_Data_VO;
	}
	
	
	public List<Material_Data_VO> findByMaterialType(String ma_ty_no) {

	    List<Material_Data_VO> list = new ArrayList<Material_Data_VO>();

		Material_Data_VO material_Data_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_TYPE);

			pstmt.setString(1, ma_ty_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				material_Data_VO = new Material_Data_VO();
				material_Data_VO.setMaNo(rs.getString("ma_no"));
				material_Data_VO.setMaTyNo(rs.getString("ma_ty_no"));
				material_Data_VO.setMaName(rs.getString("ma_name"));
				material_Data_VO.setMaPrice(rs.getInt("ma_price"));
				material_Data_VO.setMaPhoto(rs.getBytes("ma_photo"));
				material_Data_VO.setMaStatus(rs.getInt("ma_status"));
				list.add(material_Data_VO);
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
	
	@Override
	public List<Material_Data_VO> getAll() {
		List<Material_Data_VO> list = new ArrayList<Material_Data_VO>();
		Material_Data_VO material_Data_VO = null;

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
				material_Data_VO = new Material_Data_VO();
				material_Data_VO.setMaNo(rs.getString("ma_no"));
				material_Data_VO.setMaTyNo(rs.getString("ma_ty_no"));
				material_Data_VO.setMaName(rs.getString("ma_name"));
				material_Data_VO.setMaPrice(rs.getInt("ma_price"));
				material_Data_VO.setMaPhoto(rs.getBytes("ma_photo"));
				material_Data_VO.setMaStatus(rs.getInt("ma_status"));
				list.add(material_Data_VO); // Store the row in the list
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
	
	public List<Material_Data_VO> getAll(Map<String,String[]> map) {
		List<Material_Data_VO> list = new ArrayList<Material_Data_VO>();
		Material_Data_VO material_Data_VO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			String finalSQL = "select * from MATERIAL_DATA "
			          + jdbcUtil_CompositeQuery_MatData.get_WhereCondition(map)
			          + "order by ma_no";
			
			pstmt = con.prepareStatement(finalSQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				material_Data_VO = new Material_Data_VO();
				material_Data_VO.setMaNo(rs.getString("ma_no"));
				material_Data_VO.setMaTyNo(rs.getString("ma_ty_no"));
				material_Data_VO.setMaName(rs.getString("ma_name"));
				material_Data_VO.setMaPrice(rs.getInt("ma_price"));
				material_Data_VO.setMaPhoto(rs.getBytes("ma_photo"));
				material_Data_VO.setMaStatus(rs.getInt("ma_status"));
				list.add(material_Data_VO);				
			}
			
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

		Material_Data_JDBCDAO dao = new Material_Data_JDBCDAO();
		byte[] pic = null;

//		// 新增
//		Material_Data_VO material_Data_VO1 = new Material_Data_VO();
//		material_Data_VO1.setMaNo("L0099");
//		material_Data_VO1.setMaTyNo("T0003");
//		material_Data_VO1.setMaName("減肥");
//		material_Data_VO1.setMaPrice(7777);
//		material_Data_VO1.setMaStatus(1);
//
//		try {
//			pic = getPictureByteArray("C:/img/5.png");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		material_Data_VO1.setMaPhoto(pic);
//		dao.insert(material_Data_VO1);		

//		// 修改
//		Material_Data_VO material_Data_VO2 = new Material_Data_VO();
//		material_Data_VO2.setMaNo("L0099");
//		material_Data_VO2.setMaTyNo("T0001");
//		material_Data_VO2.setMaName("極限運動");
//		material_Data_VO2.setMaPrice(9999);
//		material_Data_VO2.setMaStatus(0);
//
//		try {
//			pic = getPictureByteArray("C:/img/1.png");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		material_Data_VO2.setMaPhoto(pic);
//		dao.update(material_Data_VO2);

		
		
		// 送照片進DB
//		for (int i = 1; i <= 9; i++) {
//			try {
//				pic = getPictureByteArray("C:/img/" + i + ".png");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			Material_Data_VO material_Data_VO2 = new Material_Data_VO();
//			material_Data_VO2.setMaNo("L000"+i);
//			material_Data_VO2.setMaPhoto(pic);
//			dao.update(material_Data_VO2);
//		}
			
		
//
//		// 刪除
//		dao.delete("L0099");
//
//		// 查詢
//		Material_Data_VO material_Data_VO3 = dao.findByPrimaryKey("L0009");
//		System.out.print(material_Data_VO3.getMaNo() + ",");
//		System.out.print(material_Data_VO3.getMaTyNo() + ",");
//		System.out.print(material_Data_VO3.getMaName() + ",");
//		System.out.print(material_Data_VO3.getMaPrice() + ",");
//		System.out.print(material_Data_VO3.getMaPhoto() + ",");
//		System.out.print(material_Data_VO3.getMaStatus() + ",");
//		System.out.println("---------------------");

//		List<Material_Data_VO> list = dao.findByMaterialType("T0002");
//		for (Material_Data_VO aMat : list) {
//			System.out.print(aMat.getMaNo() + ",");
//			System.out.print(aMat.getMaTyNo() + ",");
//			System.out.print(aMat.getMaName() + ",");
//			System.out.print(aMat.getMaPrice() + ",");
//			System.out.print(aMat.getMaPhoto() + ",");
//			System.out.print(aMat.getMaStatus() + ",");
//			System.out.println();
//		}
//		
		// 查詢
//		List<Material_Data_VO> list = dao.getAll();
//		for (Material_Data_VO aMat : list) {
//			System.out.print(aMat.getMaNo() + ",");
//			System.out.print(aMat.getMaTyNo() + ",");
//			System.out.print(aMat.getMaName() + ",");
//			System.out.print(aMat.getMaPrice() + ",");
//			System.out.print(aMat.getMaPhoto() + ",");
//			System.out.print(aMat.getMaStatus() + ",");
//			System.out.println();
//		}
		
			// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
			Map<String, String[]> map = new TreeMap<String, String[]>();
			map.put("ma_no", new String[] { "L0001" });
			map.put("ma_ty_no", new String[] { "T0001" });
			map.put("ma_name", new String[] { "馬克杯" });
			map.put("ma_price_down", new String[] { "0" });
			map.put("ma_price_up", new String[] { "500" });
			map.put("ma_status", new String[] { "0" });
			map.put("action", new String[] { "getXXX" }); // 注意Map裡面會含有action的key

			String finalSQL = "select * from MATERIAL_DATA "
					          + jdbcUtil_CompositeQuery_MatData.get_WhereCondition(map)
					          + "order by ma_no";
			System.out.println("●●finalSQL = " + finalSQL);
			
			List<Material_Data_VO> list = dao.getAll(map);
			for(Material_Data_VO x : list) {
				System.out.println(x.getMaTyNo());
				System.out.println(x.getMaNo());
				System.out.println(x.getMaName());
				System.out.println(x.getMaPrice());
				System.out.println(x.getMaStatus());
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

