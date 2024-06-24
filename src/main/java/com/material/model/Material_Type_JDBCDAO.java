package com.material.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Material_Type_JDBCDAO implements Material_Type_DAO_interface{

			String driver = "oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userid = "G1";
			String passwd = "123456";

			private static final String INSERT_STMT = 
				"INSERT INTO MATERIAL_TYPE (ma_ty_no,ma_ty_nam) VALUES ( ?, ?)";
			private static final String GET_ALL_STMT = 
				"SELECT ma_ty_no,ma_ty_nam FROM MATERIAL_TYPE order by ma_ty_no";
			private static final String GET_ONE_STMT = 
				"SELECT ma_ty_no,ma_ty_nam FROM MATERIAL_TYPE where ma_ty_no = ?";
			private static final String DELETE = 
				"DELETE FROM MATERIAL_TYPE where ma_ty_no = ?";
			private static final String UPDATE = 
				"UPDATE MATERIAL_TYPE set ma_ty_nam=? where ma_ty_no = ?";

			@Override
			public Material_Type_VO insert(Material_Type_VO material_Type_VO) {

				Connection con = null;
				PreparedStatement pstmt = null;

				try {

					Class.forName(driver);
					con = DriverManager.getConnection(url, userid, passwd);
					pstmt = con.prepareStatement(INSERT_STMT);

					pstmt.setString(1, material_Type_VO.getMaTyNo());
					pstmt.setString(2, material_Type_VO.getMaTyNam());

					pstmt.executeUpdate();

					// Handle any driver errors
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("Couldn't load database driver. "
							+ e.getMessage());
					// Handle any SQL errors
				} catch (SQLException se) {
					throw new RuntimeException("A database error occured. "
							+ se.getMessage());
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
				return material_Type_VO;

			}

			@Override
			public void update(Material_Type_VO material_Type_VO) {

				Connection con = null;
				PreparedStatement pstmt = null;

				try {

					Class.forName(driver);
					con = DriverManager.getConnection(url, userid, passwd);
					pstmt = con.prepareStatement(UPDATE);

					
					pstmt.setString(1, material_Type_VO.getMaTyNam());
					pstmt.setString(2, material_Type_VO.getMaTyNo());

					pstmt.executeUpdate();

					// Handle any driver errors
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("Couldn't load database driver. "
							+ e.getMessage());
					// Handle any SQL errors
				} catch (SQLException se) {
					throw new RuntimeException("A database error occured. "
							+ se.getMessage());
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
			public void delete(String ma_ty_no) {

				Connection con = null;
				PreparedStatement pstmt = null;

				try {

					Class.forName(driver);
					con = DriverManager.getConnection(url, userid, passwd);
					pstmt = con.prepareStatement(DELETE);

					pstmt.setString(1, ma_ty_no);

					pstmt.executeUpdate();

					// Handle any driver errors
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("Couldn't load database driver. "
							+ e.getMessage());
					// Handle any SQL errors
				} catch (SQLException se) {
					throw new RuntimeException("A database error occured. "
							+ se.getMessage());
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
			public Material_Type_VO findByPrimaryKey(String ma_ty_no) {

				Material_Type_VO material_Type_VO = null;
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {

					Class.forName(driver);
					con = DriverManager.getConnection(url, userid, passwd);
					pstmt = con.prepareStatement(GET_ONE_STMT);

					pstmt.setString(1, ma_ty_no);

					rs = pstmt.executeQuery();

					while (rs.next()) {
						// empVo 也稱為 Domain objects
						material_Type_VO = new Material_Type_VO();
						material_Type_VO.setMaTyNo(rs.getString("ma_ty_no"));
						material_Type_VO.setMaTyNam(rs.getString("ma_ty_nam"));

					}

					// Handle any driver errors
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("Couldn't load database driver. "
							+ e.getMessage());
					// Handle any SQL errors
				} catch (SQLException se) {
					throw new RuntimeException("A database error occured. "
							+ se.getMessage());
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
				return material_Type_VO;
			}

			@Override
			public List<Material_Type_VO> getAll() {
				List<Material_Type_VO> list = new ArrayList<Material_Type_VO>();
				Material_Type_VO material_Type_VO = null;

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
						material_Type_VO = new Material_Type_VO();
						material_Type_VO.setMaTyNo(rs.getString("ma_ty_no"));
						material_Type_VO.setMaTyNam(rs.getString("ma_ty_nam"));
						list.add(material_Type_VO); // Store the row in the list
					}

					// Handle any driver errors
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("Couldn't load database driver. "
							+ e.getMessage());
					// Handle any SQL errors
				} catch (SQLException se) {
					throw new RuntimeException("A database error occured. "
							+ se.getMessage());
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

				Material_Type_JDBCDAO dao = new Material_Type_JDBCDAO();

//				// 新增
//				Material_Type_VO material_Type_VO1 = new Material_Type_VO();
//				material_Type_VO1.setMaTyNo("T0099");
//				material_Type_VO1.setMaTyNam("測試");
//				dao.insert(material_Type_VO1);

////				// 修改
//				Material_Type_VO material_Type_VO2 = new Material_Type_VO();
//				material_Type_VO2.setMaTyNo("T0099");
//				material_Type_VO2.setMaTyNam("測試2");
//				dao.update(material_Type_VO2);
		//
//				// 刪除
//				dao.delete("T0099");
		//
				// 查詢
//				Material_Type_VO material_Type_VO3 = dao.findByPrimaryKey("T0002");
//				System.out.print(material_Type_VO3.getMaTyNo() + ",");
//				System.out.print(material_Type_VO3.getMaTyNam() + ",");
//				System.out.println("---------------------");

//				 查詢
//				List<Material_Type_VO> list = dao.getAll();
//				for (Material_Type_VO aMat : list) {
//					System.out.print(aMat.getMaTyNo() + ",");
//					System.out.print(aMat.getMaTyNam() + ",");
//					System.out.println();
//				}
			}
		}
			
			
			
			
			
			

		
		
		
		
		
		
		
		


