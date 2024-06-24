package com.emp.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Func_No_List_DAO implements Func_No_List_DAO_interface{
	
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G1");
		} catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
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

					con = ds.getConnection();
					pstmt = con.prepareStatement(INSERT_STMT);

					pstmt.setString(1, func_No_List_VO.getFuncId());
					pstmt.setString(2, func_No_List_VO.getFuncName());

					pstmt.executeUpdate();

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

					con = ds.getConnection();
					pstmt = con.prepareStatement(UPDATE);

					
					pstmt.setString(1, func_No_List_VO.getFuncName());
					pstmt.setString(2, func_No_List_VO.getFuncId());

					pstmt.executeUpdate();

					// Handle any driver errors
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

					con = ds.getConnection();
					pstmt = con.prepareStatement(DELETE);

					pstmt.setString(1, func_id);

					pstmt.executeUpdate();

					// Handle any driver errors
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
					con = ds.getConnection();
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

					con = ds.getConnection();
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
}

