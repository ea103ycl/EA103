package com.cs.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Order_Res_Rec_JDBCDAO implements Order_Res_Rec_DAO_interface{
	
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "G1";
		String passwd = "123456";

		private static final String GET_ALL_STMT = 
			"SELECT svc_no,emp_no,mem_id,order_id,tns_src,svc_text FROM ORDER_RES_REC order by svc_no";
		private static final String GET_ONE_STMT = 
			"SELECT svc_no,emp_no,mem_id,order_id,tns_src,svc_text FROM ORDER_RES_REC where svc_no = ?";
		private static final String DELETE = 
			"DELETE FROM ORDER_RES_REC where svc_no = ?";
		private static final String UPDATE = 
			"UPDATE ORDER_RES_REC set emp_no=?, mem_id=?, order_id=?, tns_src=?, svc_text=? where svc_no = ?";

		@Override
		public void update(Order_Res_Rec_VO order_Res_Rec_VO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setString(1, order_Res_Rec_VO.getEmpNo());
				pstmt.setString(2, order_Res_Rec_VO.getMemId());
				pstmt.setInt(3, order_Res_Rec_VO.getOrderId());
				pstmt.setInt(4, order_Res_Rec_VO.getTnsSrc());
				pstmt.setString(5, order_Res_Rec_VO.getSvcText());
				pstmt.setString(6, order_Res_Rec_VO.getSvcNo());

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
		public void delete(String svc_no) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(DELETE);

				pstmt.setString(1, svc_no);

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
		public Order_Res_Rec_VO findByPrimaryKey(String svc_no) {

			Order_Res_Rec_VO order_Res_Rec_VO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1, svc_no);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo 也稱為 Domain objects
					order_Res_Rec_VO = new Order_Res_Rec_VO();
					order_Res_Rec_VO.setSvcNo(rs.getString("svc_no"));
					order_Res_Rec_VO.setEmpNo(rs.getString("emp_no"));
					order_Res_Rec_VO.setMemId(rs.getString("mem_id"));
					order_Res_Rec_VO.setOrderId(rs.getInt("order_id"));
					order_Res_Rec_VO.setTnsSrc(rs.getInt("tns_src"));
					order_Res_Rec_VO.setSvcText(rs.getString("svc_text"));
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
			return order_Res_Rec_VO;
		}

		@Override
		public List<Order_Res_Rec_VO> getAll() {
			List<Order_Res_Rec_VO> list = new ArrayList<Order_Res_Rec_VO>();
			Order_Res_Rec_VO order_Res_Rec_VO = null;

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
					order_Res_Rec_VO = new Order_Res_Rec_VO();
					order_Res_Rec_VO.setSvcNo(rs.getString("svc_no"));
					order_Res_Rec_VO.setEmpNo(rs.getString("emp_no"));
					order_Res_Rec_VO.setMemId(rs.getString("mem_id"));
					order_Res_Rec_VO.setOrderId(rs.getInt("order_id"));
					order_Res_Rec_VO.setTnsSrc(rs.getInt("tns_src"));
					order_Res_Rec_VO.setSvcText(rs.getString("svc_text"));
					list.add(order_Res_Rec_VO); // Store the row in the list
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

		public static void main(String [] args) {

			Order_Res_Rec_JDBCDAO dao = new Order_Res_Rec_JDBCDAO();

//			// 修改
//			Order_Res_Rec_VO order_Res_Rec_VO2 = new Order_Res_Rec_VO();
//			order_Res_Rec_VO2.setSvcNo("R0011");
//			order_Res_Rec_VO2.setEmpNo("E0001");
//			order_Res_Rec_VO2.setMemId("M000001");
//			order_Res_Rec_VO2.setOrderId(1);
//			order_Res_Rec_VO2.setTnsSrc(99);
//			order_Res_Rec_VO2.setSvcText("111");
//			dao.update(order_Res_Rec_VO2);

//			// 刪除
//			dao.delete("R0011");

//			// 查詢
//			Order_Res_Rec_VO order_Res_Rec_VO3 = dao.findByPrimaryKey("R0001");
//			System.out.print(order_Res_Rec_VO3.getSvcNo() + ",");
//			System.out.print(order_Res_Rec_VO3.getEmpNo() + ",");
//			System.out.print(order_Res_Rec_VO3.getMemId() + ",");
//			System.out.print(order_Res_Rec_VO3.getOrderId() + ",");
//			System.out.print(order_Res_Rec_VO3.getTnsSrc() + ",");
//			System.out.print(order_Res_Rec_VO3.getSvcText() + ",");
//			System.out.println("---------------------");

			// 查詢
			List<Order_Res_Rec_VO> list = dao.getAll();
			for (Order_Res_Rec_VO aRec : list) {
				System.out.print(aRec.getSvcNo() + ",");
				System.out.print(aRec.getEmpNo() + ",");
				System.out.print(aRec.getMemId() + ",");
				System.out.print(aRec.getOrderId() + ",");
				System.out.print(aRec.getTnsSrc() + ",");
				System.out.print(aRec.getSvcText() + ",");
				System.out.println();
			}
		}
	}
		
		
		
		
		
		

	
	
	
	
	
	
	
	
	
	


