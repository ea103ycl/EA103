package com.discount.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.discount.model.DiscountSettingVO;

public class DiscountSettingDAO implements DiscountSettingDAO_interface {
	
	private static  DataSource ds=null;
	PreparedStatement pstmt=null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO DISCOUNT_SETTING (DIS_NO,PO_PROD_NO,REACH_NUMBER,REACH_DISCOUNT) "
			+ "VALUES (DISCOUNT_SETTING_SEQ.NEXTVAL,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM DISCOUNT_SETTING";
	private static final String GET_ONE_STMT = "SELECT * FROM DISCOUNT_SETTING WHERE DIS_NO = ?";
	private static final String UPDATE = "UPDATE DISCOUNT_SETTING SET po_prod_no=?, reach_number=?, reach_discount=? WHERE dis_no =?";
	private static final String DELETE = "DELETE FROM DISCOUNT_SETTING WHERE DIS_NO=?";
	private static final String GET_PO_BYEVENT = "INSERT INTO DISCOUNT_SETTING( DIS_NO, PO_PROD_NO, REACH_NUMBER, REACH_DISCOUNT) SELECT DISCOUNT_SETTING_SEQ.NEXTVAL, PO_PROD_NO, ?, ? FROM PRE_PRODUCT P WHERE P.EVENT_P_NO IN (\r\n" + 
			"(SELECT E.EVENT_P_NO FROM EVENT_P E WHERE E.EVENT_NO = ?))";
	
	
	

	@Override
	public void insert(DiscountSettingVO discountsettingVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println("********DiscountDAO - 執行insert********");
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, discountsettingVO.getPo_prod_no());
			pstmt.setInt(2, discountsettingVO.getReach_number());
			pstmt.setInt(3, discountsettingVO.getReach_discount());
			System.out.println("********DAO - 執行executeUpdate()********");
			pstmt.executeUpdate();
			System.out.println("********離開DAO********");

		}catch (SQLException e) {
			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(DiscountSettingVO discountsettingVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, discountsettingVO.getPo_prod_no());
			pstmt.setInt(2, discountsettingVO.getReach_number());
			pstmt.setInt(3, discountsettingVO.getReach_discount());
			pstmt.setString(4, discountsettingVO.getDis_no());

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
	public void delete(String dis_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, dis_no);

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
	public DiscountSettingVO findByPrimaryKey(String dis_no) {
		DiscountSettingVO discountsettingVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, dis_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				discountsettingVO = new DiscountSettingVO();
				discountsettingVO.setDis_no(rs.getString("dis_no"));
				discountsettingVO.setPo_prod_no(rs.getString("po_prod_no"));
				discountsettingVO.setReach_number(rs.getInt("reach_number"));
				discountsettingVO.setReach_discount(rs.getInt("reach_discount"));
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
		return discountsettingVO;
	}

	@Override
	public List<DiscountSettingVO> getALL() {
		List<DiscountSettingVO> list = new ArrayList<DiscountSettingVO>();
		DiscountSettingVO discountsettingVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				discountsettingVO = new DiscountSettingVO();
				discountsettingVO.setDis_no(rs.getString("dis_no"));
				discountsettingVO.setPo_prod_no(rs.getString("po_prod_no"));
				discountsettingVO.setReach_number(rs.getInt("reach_number"));
				discountsettingVO.setReach_discount(rs.getInt("reach_discount"));

				list.add(discountsettingVO); // Store the row in the list
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

	@Override
	public void addDiscountWithEvent(DiscountSettingVO discountsettingVO) {
		Connection con = null;
		PreparedStatement pstmt = null; //查詢
		System.out.println("------進入折扣DAO世界------");
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PO_BYEVENT);
			System.out.println("------pstmt都宣告好了------");
			pstmt.setInt(1, discountsettingVO.getReach_number());
			System.out.println("取值getReach_number = "+discountsettingVO.getReach_number());
			pstmt.setInt(2, discountsettingVO.getReach_discount());
			System.out.println("取值getReach_discount = "+discountsettingVO.getReach_discount());
			System.out.println("discountsettingVO.getEvent_no() = "+discountsettingVO.getEvent_no());
			pstmt.setString(3, discountsettingVO.getEvent_no());
			
			pstmt.executeUpdate(); //查詢出來的所有商品清單
			System.out.println("------執行完rs = pstmt.executeQuery()------");

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
	}


