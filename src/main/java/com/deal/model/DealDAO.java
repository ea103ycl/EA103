package com.deal.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.detail.model.DetailVO;
import com.prod.model.ProdDAO_interface;
import java.sql.Connection;


public class DealDAO implements DealDAO_interface {
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	        private static final String GET_ONE_PTR = "SELECT * FROM PAINTER WHERE PTR_NO = ?";
	         private static final String UPDATE_BALANCE = "UPDATE MEMBERS SET balance = ? WHERE MEM_ID = ?";
			private static final String GET_ONE_STMT = "SELECT * FROM members WHERE mem_id = ?";
			private static final String INSERT_STMT = "INSERT INTO WEL_RECORD(TNS_ID, ORDER_ID, MEM_ID, TNS_SRC, TNS_AMOUNT, TNS_TIME) VALUES(tns_seq.nextval,?,?,?,?,CURRENT_TIMESTAMP)";
			
			@Override
			public DealVO findByPrimaryKey(String mem_id) {
				DealVO dealVO = null;
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_ONE_STMT);

					pstmt.setString(1, mem_id);

					rs = pstmt.executeQuery();

					while (rs.next()) {
						dealVO = new DealVO();
						dealVO.setBalance(rs.getInt("balance"));
						
						
					}

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
				return dealVO;
			}

			@Override
			public void insert(String or_no, Connection con, String mem_id, Integer tns_src, Integer tns_amount) {
			
			
					PreparedStatement pstmt = null;

					try {
						pstmt = con.prepareStatement(INSERT_STMT);

						pstmt.setString(1, or_no);
						pstmt.setString(2, mem_id);
						pstmt.setInt(3,   tns_src);
						pstmt.setInt(4,   tns_amount);
						
						pstmt.executeUpdate();


						// Handle any driver errors
					} catch (SQLException se) {
						try {
							con.rollback();
						} catch (SQLException e) {
							e.printStackTrace();
						}
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
						
					}
				}

			
			
			
			@Override
			public void update_balance(Connection con, String mem_id, Integer balance) {

				PreparedStatement pstmt = null;

				try {
					pstmt = con.prepareStatement(UPDATE_BALANCE);

					pstmt.setInt(1, balance);
					pstmt.setString(2, mem_id);
					
					
					pstmt.executeUpdate();


					// Handle any driver errors
				} catch (SQLException se) {
					try {
						con.rollback();
					} catch (SQLException e) {
						e.printStackTrace();
					}
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
					
				}
			}

			@Override
			public DealVO find_Memid_byPtr(Integer ptr_no) {
				DealVO dealVO = null;
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_ONE_PTR);

					pstmt.setInt(1, ptr_no);

					rs = pstmt.executeQuery();

					while (rs.next()) {
						dealVO = new DealVO();
						dealVO.setMem_id(rs.getString("mem_id"));
						
						
					}

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
				return dealVO;
			}
			

			

			


			


			

			
}
