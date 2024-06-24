package com.emp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Available_Func_JDBCDAO implements Available_Func_DAO_interface{
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "G1";
		String passwd = "123456";

		private static final String INSERT_STMT = 
			"INSERT INTO AVAILABLE_FUNC (emp_no,func_id) VALUES ( ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT emp_no,func_id FROM AVAILABLE_FUNC order by emp_no";
		private static final String GET_ONE_STMT = 
			"SELECT emp_no,func_id FROM AVAILABLE_FUNC where emp_no = ?";
		private static final String DELETE = 
			"DELETE FROM AVAILABLE_FUNC where emp_no = ?";
		private static final String UPDATE = 
			"UPDATE AVAILABLE_FUNC set func_id=? where emp_no = ?";

		@Override
		public void insert(Available_Func_VO available_Func_VO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, available_Func_VO.getEmpNo());
				pstmt.setString(2, available_Func_VO.getFuncId());

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
		public void update(Available_Func_VO available_Func_VO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);

				
				pstmt.setString(1, available_Func_VO.getFuncId());
				pstmt.setString(2, available_Func_VO.getEmpNo());

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
		public List<Available_Func_VO> findByPrimaryKey(String emp_no) {
			List<Available_Func_VO> list = new ArrayList<Available_Func_VO>();
			Available_Func_VO available_Func_VO = null;
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
					available_Func_VO = new Available_Func_VO();
					available_Func_VO.setEmpNo(rs.getString("emp_no"));
					available_Func_VO.setFuncId(rs.getString("func_id"));
					list.add(available_Func_VO);
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

		@Override
		public List<Available_Func_VO> getAll() {
			List<Available_Func_VO> list = new ArrayList<Available_Func_VO>();
			Available_Func_VO available_Func_VO = null;

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
					available_Func_VO = new Available_Func_VO();
					available_Func_VO.setEmpNo(rs.getString("emp_no"));
					available_Func_VO.setFuncId(rs.getString("func_id"));
					list.add(available_Func_VO); // Store the row in the list
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

			Available_Func_JDBCDAO dao = new Available_Func_JDBCDAO();

//			// 新增
//			Available_Func_VO available_Func_VO1 = new Available_Func_VO();
//			available_Func_VO1.setEmpNo("E0009");
//			available_Func_VO1.setFuncId("C");
//			dao.insert(available_Func_VO1);

//			// 修改
//			Available_Func_VO available_Func_VO2 = new Available_Func_VO();
//			available_Func_VO2.setEmpNo("E0010");
//			available_Func_VO2.setFuncId("A");
//			dao.update(available_Func_VO2);
	//
//			// 刪除
//			dao.delete("E9999");
	//
////			// 查詢
			List<Available_Func_VO> available_Func_VO3 = dao.findByPrimaryKey("E9999");
			for(Available_Func_VO aFunc : available_Func_VO3) {
			System.out.print(aFunc.getEmpNo() + ",");
			System.out.print(aFunc.getFuncId() + ",");
			System.out.println();
			}
//			System.out.println("---------------------");

			// 查詢
//			List<Available_Func_VO> list = dao.getAll();
//			for (Available_Func_VO aFunc : list) {
//				System.out.print(aFunc.getEmpNo() + ",");
//				System.out.print(aFunc.getFuncId() + ",");
//				System.out.println();
//			}
		}
	}
		
		
		
		
		
		

	
	
	
	
	
	
	
	

