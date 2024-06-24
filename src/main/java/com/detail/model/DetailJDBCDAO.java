package com.detail.model;

import java.util.*;

import com.order.model.OrderJDBCDAO;
import com.order.model.OrderVO;
import com.prod.model.ProdVO;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;


public class DetailJDBCDAO implements DetailDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";

	
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public List<DetailVO> detailSearch(String or_no) {
		List<DetailVO> list = new ArrayList<DetailVO>();
		DetailVO detailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

//			OrderJDBCDAO dao = new OrderJDBCDAO();

//			// �s�W
//			OrderVO orderVO1 = new OrderVO();
//			orderVO1.setMem_id(2);
//			orderVO1.setOr_name("�p��");
//			orderVO1.setOr_phone("0982777666");
//			orderVO1.setOr_zip(666);
//			orderVO1.setOr_addr("������");
//			orderVO1.setOr_note("�Цb�ߤW�H�L��");
//			orderVO1.setOr_total(600);
//			
//			dao.insert(orderVO1);
//			System.out.println("�s�W���\!!");
			
			
//			DetailJDBCDAO dao2 = new DetailJDBCDAO();
////			// �s�W
//			DetailVO detailVO1 = new DetailVO();
//			detailVO1.setProd_no(1);
//			detailVO1.setOr_qty(10);
//			detailVO1.setOr_p_price(1000);
//			
////			dao2.insert(detailVO1);
//			System.out.println("�s�W���\!!");
			
			
			
			
			
			
			//�d�߳�C
//			DetailVO detailVO3 = dao.findByPrimaryKey("S20200929-00003");
//			System.out.println(detailVO3.getOr_no());
//			System.out.println(detailVO3.getProd_no());
//			System.out.println(detailVO3.getOr_qty());
//			System.out.println(detailVO3.getOr_p_price());
//			System.out.println("�d�߳浧���\!!");
//			
			
			
			
			
			//�d�ߥ���
//			List<DetailVO> list = dao.getAll();
//			for(DetailVO aOrder : list) {
//				System.out.print(aOrder.getOr_no()+ ", ");
//				System.out.print(aOrder.getProd_no()+ ", ");
//				System.out.print(aOrder.getOr_qty()+ ", ");
//				System.out.print(aOrder.getOr_p_price()+ ", ");
//				System.out.println();
//			}
//			System.out.println("�d�ߥ������\!!");
//			
		

			//�q�����
			DetailJDBCDAO dao3 = new DetailJDBCDAO();
			List<DetailVO> list2 = dao3.detailSearch("S20201013-00044");
			for(DetailVO aOrder : list2) {
				System.out.print(aOrder.getOr_no()+ ", ");
				System.out.print(aOrder.getProd_no()+ ", ");
				System.out.print(aOrder.getOr_qty()+ ", ");
				System.out.print(aOrder.getOr_p_price()+ ", ");
				System.out.println();
			}
			System.out.println("明細查詢成功!!");

		
		}





			
}

