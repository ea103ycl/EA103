package com.emp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Func_No_List_JDBCDAO implements Func_No_List_DAO_interface{
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "G1";
		String passwd = "123456";

		private static final String INSERT_STMT = 
			"INSERT INTO FUNC_NO_LIST (func_id,func_name) VALUES ( ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT func_id,func_name FROM FUNC_NO_LIST order by func_id";
		private static final String GET_ONE_STMT = 
			"SELECT func_id,func_name FROM FUNC_NO_LIST where func_id = ?";
		private static final String DELETE = 
			"DELETE FROM FUNC_NO_LIST where func_id = ?";
		private static final String UPDATE = 
			"UPDATE FUNC_NO_LIST set func_name=? where func_id = ?";

		@Override
		public void insert(Func_No_List_VO func_No_List_VO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, func_No_List_VO.getFuncId());
				pstmt.setString(2, func_No_List_VO.getFuncName());

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
		public void update(Func_No_List_VO func_No_List_VO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);

				
				pstmt.setString(1, func_No_List_VO.getFuncName());
				pstmt.setString(2, func_No_List_VO.getFuncId());

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
		public void delete(String func_id) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(DELETE);

				pstmt.setString(1, func_id);

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
		public Func_No_List_VO findByPrimaryKey(String func_id) {

			Func_No_List_VO func_No_List_VO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1, func_id);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo 也稱為 Domain objects
					func_No_List_VO = new Func_No_List_VO();
					func_No_List_VO.setFuncId(rs.getString("func_id"));
					func_No_List_VO.setFuncName(rs.getString("func_name"));

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
			return func_No_List_VO;
		}

		@Override
		public List<Func_No_List_VO> getAll() {
			List<Func_No_List_VO> list = new ArrayList<Func_No_List_VO>();
			Func_No_List_VO func_No_List_VO = null;

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
					func_No_List_VO = new Func_No_List_VO();
					func_No_List_VO.setFuncId(rs.getString("func_id"));
					func_No_List_VO.setFuncName(rs.getString("func_name"));
					list.add(func_No_List_VO); // Store the row in the list
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

			Func_No_List_JDBCDAO dao = new Func_No_List_JDBCDAO();

//			// 新增
//			Func_No_List_VO func_No_List_VO1 = new Func_No_List_VO();
//			func_No_List_VO1.setFuncId("X");
//			func_No_List_VO1.setFuncName("打CODE管理");
//			dao.insert(func_No_List_VO1);

//			// 修改
//			Func_No_List_VO func_No_List_VO2 = new Func_No_List_VO();
//			func_No_List_VO2.setFuncId("Z");
//			func_No_List_VO2.setFuncName("飲食管理");
//			dao.update(func_No_List_VO2);
	//
			// 刪除
//			dao.delete("X");
	//
//			// 查詢
//			Func_No_List_VO func_No_List_VO3 = dao.findByPrimaryKey("C");
//			System.out.print(func_No_List_VO3.getFuncId() + ",");
//			System.out.print(func_No_List_VO3.getFuncName() + ",");
//			System.out.println("---------------------");

			// 查詢
			List<Func_No_List_VO> list = dao.getAll();
			for (Func_No_List_VO aFunc : list) {
				System.out.print(aFunc.getFuncId() + ",");
				System.out.print(aFunc.getFuncName() + ",");
				System.out.println();
			}
		}
	}
		
		
		
		
		
		

	
	
	
	
	
	
	
	

		