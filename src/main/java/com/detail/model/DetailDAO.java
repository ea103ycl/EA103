package com.detail.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.order.model.OrderVO;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;


public class DetailDAO implements DetailDAO_interface {
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

				private static final String INSERT_STMT = "INSERT INTO ORDER_DETAIL (OR_NO, PROD_NO, OR_QTY, OR_P_PRICE ) VALUES(?,?,?,?)";
				private static final String GET_ONE_STMT = "SELECT * FROM ORDER_DETAIL WHERE OR_NO = ?";
				private static final String GET_ALL_STMT = "SELECT * FROM ORDER_DETAIL ORDER BY OR_NO";
				private static final String DETAIL_SEARCH = "SELECT * FROM ORDER_DETAIL WHERE OR_NO = ? ORDER BY OR_NO";
				
				
				
	@Override
	public void insert(DetailVO detailVO, Connection con) {
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, detailVO.getOr_no());
			pstmt.setInt(2,    detailVO.getProd_no());
			pstmt.setInt(3,    detailVO.getOr_qty());
			pstmt.setInt(4,    detailVO.getOr_p_price());
			
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
	public DetailVO findByPrimaryKey(String or_no) {
		DetailVO detailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, or_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				detailVO = new DetailVO();
				
				detailVO.setOr_no(rs.getString("or_no"));
				detailVO.setProd_no(rs.getInt("prod_no"));
				detailVO.setOr_qty(rs.getInt("or_qty"));
				detailVO.setOr_p_price(rs.getInt("or_p_price"));
				
			}
			
			// Handle any driver errors
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
		return detailVO;
	}

	@Override
	public List<DetailVO> getAll() {
		List<DetailVO> list = new ArrayList<DetailVO>();
		DetailVO detailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				detailVO = new DetailVO();
				
				detailVO.setOr_no(rs.getString("or_no"));
				detailVO.setProd_no(rs.getInt("prod_no"));
				detailVO.setOr_qty(rs.getInt("or_qty"));
				detailVO.setOr_p_price(rs.getInt("or_p_price"));
				
				list.add(detailVO);
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
		return list;
	}






	@Override
	public List<DetailVO> detailSearch(String or_no) {
		List<DetailVO> list = new ArrayList<DetailVO>();
		DetailVO detailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DETAIL_SEARCH);
			
			pstmt.setString(1, or_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				detailVO = new DetailVO();
				
				detailVO.setOr_no(rs.getString("or_no"));
				detailVO.setProd_no(rs.getInt("prod_no"));
				detailVO.setOr_qty(rs.getInt("or_qty"));
				detailVO.setOr_p_price(rs.getInt("or_p_price"));
				
				list.add(detailVO);
			}
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

