package com.order.model;

import java.util.*;

import com.detail.model.DetailJDBCDAO;
import com.detail.model.DetailVO;
import com.prod.model.ProdVO;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;


public class OrderJDBCDAO implements OrderDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO ORDERS(OR_NO, MEM_ID, OR_NAME, OR_PHONE, OR_ZIP, OR_ADDR, OR_NOTE, OR_TOTAL )   VALUES('S'||to_char(sysdate,'yyyymmdd')||'-'||LPAD(to_char(order_seq.NEXTVAL), 5, '0'),?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM ORDERS ORDER BY OR_NO";
	private static final String GET_ONE_STMT = "SELECT * FROM ORDERS WHERE OR_NO = ?";
	private static final String UPDATE = "UPDATE ORDERS SET OR_STATUS=? WHERE OR_NO=?";
	private static final String ORDER_SEARCH = "SELECT * FROM ORDERS WHERE MEM_ID = ? ORDER BY OR_NO";
	private static final String GET_ORDER_BY_STAUTS = "SELECT * FROM ORDERS WHERE OR_STATUS=? ORDER BY OR_NO";
	
	@Override
	public String insert(OrderVO orderVO, List<DetailVO> detailList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String ord_next_no = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			String[] col = { "OR_NO" };
			
			pstmt = con.prepareStatement(INSERT_STMT, col);

			pstmt.setString(1,    orderVO.getMem_id());
			pstmt.setString(2,    orderVO.getOr_name());
			pstmt.setString(3,    orderVO.getOr_phone());
			pstmt.setInt(4,       orderVO.getOr_zip());
			pstmt.setString(5,    orderVO.getOr_addr());
			pstmt.setString(6,    orderVO.getOr_note());
			pstmt.setInt(7,       orderVO.getOr_total());
			
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				ord_next_no = rs.getString(1);
				System.out.println("OR_NO = " + ord_next_no);
			}
			
			DetailJDBCDAO detailDAO = new DetailJDBCDAO();
			for (DetailVO detailVO : detailList) {
				detailVO.setOr_no(ord_next_no);
				detailDAO.insert(detailVO, con);
			}
			
			
			
			
			con.commit();
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return  ord_next_no;
	}

	
	

	

	@Override
	public OrderVO findByPrimaryKey(String or_no) {
		OrderVO orderVO = null;
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
				orderVO = new OrderVO();
				orderVO.setOr_no(rs.getString("or_no"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setOr_name(rs.getString("or_name"));
				orderVO.setOr_phone(rs.getString("or_phone"));
				orderVO.setOr_zip(rs.getInt("or_zip"));
				orderVO.setOr_addr(rs.getString("or_addr"));
				orderVO.setOr_note(rs.getString("or_note"));
				orderVO.setOr_total(rs.getInt("or_total"));
				orderVO.setOr_status(rs.getInt("or_status"));
				orderVO.setOr_time(rs.getTimestamp("or_time"));
				
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
		return orderVO;
	}

	@Override
	public List<OrderVO> getAll() {
		List<OrderVO> list = new ArrayList<OrderVO>();
		OrderVO orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderVO = new OrderVO();
				orderVO.setOr_no(rs.getString("or_no"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setOr_name(rs.getString("or_name"));
				orderVO.setOr_phone(rs.getString("or_phone"));
				orderVO.setOr_zip(rs.getInt("or_zip"));
				orderVO.setOr_addr(rs.getString("or_addr"));
				orderVO.setOr_note(rs.getString("or_note"));
				orderVO.setOr_total(rs.getInt("or_total"));
				orderVO.setOr_status(rs.getInt("or_status"));
				orderVO.setOr_time(rs.getTimestamp("or_time"));
				list.add(orderVO);
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
	public void update(OrderVO orderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1,    orderVO.getOr_status());
			pstmt.setString(2,    orderVO.getOr_no());

			pstmt.executeUpdate();

			// Handle any driver errors
					} catch (ClassNotFoundException e) {
						throw new RuntimeException("Couldn't load database driver. "
								+ e.getMessage());
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
	
//訂單查詢	
	@Override
	public List<OrderVO> orderSearch(String mem_id) {
			List<OrderVO> list = new ArrayList<OrderVO>();
			OrderVO orderVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(ORDER_SEARCH);
				
				pstmt.setString(1, mem_id);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					orderVO = new OrderVO();
					orderVO.setOr_no(rs.getString("or_no"));
					orderVO.setMem_id(rs.getString("mem_id"));
					orderVO.setOr_name(rs.getString("or_name"));
					orderVO.setOr_phone(rs.getString("or_phone"));
					orderVO.setOr_zip(rs.getInt("or_zip"));
					orderVO.setOr_addr(rs.getString("or_addr"));
					orderVO.setOr_note(rs.getString("or_note"));
					orderVO.setOr_total(rs.getInt("or_total"));
					orderVO.setOr_status(rs.getInt("or_status"));
					orderVO.setOr_time(rs.getTimestamp("or_time"));
					list.add(orderVO);
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
	public List<OrderVO> getOrderByStauts(Integer or_stauts) {
		List<OrderVO> list = new ArrayList<OrderVO>();
		OrderVO orderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ORDER_BY_STAUTS);
			
			pstmt.setInt(1, or_stauts);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderVO = new OrderVO();
				orderVO.setOr_no(rs.getString("or_no"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setOr_name(rs.getString("or_name"));
				orderVO.setOr_phone(rs.getString("or_phone"));
				orderVO.setOr_zip(rs.getInt("or_zip"));
				orderVO.setOr_addr(rs.getString("or_addr"));
				orderVO.setOr_note(rs.getString("or_note"));
				orderVO.setOr_total(rs.getInt("or_total"));
				orderVO.setOr_status(rs.getInt("or_status"));
				orderVO.setOr_time(rs.getTimestamp("or_time"));
				list.add(orderVO);
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

			OrderJDBCDAO dao = new OrderJDBCDAO();

//////			// 新增
//			OrderVO orderVO1 = new OrderVO();
//			orderVO1.setMem_id("M000001");
//			orderVO1.setOr_name("小辣");
//			orderVO1.setOr_phone("0982777666");
//			orderVO1.setOr_zip(666);
//			orderVO1.setOr_addr("高雄市");
//			orderVO1.setOr_note("請在晚上寄過來");
//			orderVO1.setOr_total(600);
//			
//			
//			
//			DetailVO detailVO1 = new DetailVO();
//			detailVO1.setProd_no(1);
//			detailVO1.setOr_qty(10);
//			detailVO1.setOr_p_price(1000);
//			
//			
//			DetailVO detailVO2 = new DetailVO();
//			detailVO2.setProd_no(5);
//			detailVO2.setOr_qty(1);
//			detailVO2.setOr_p_price(1);
//			
//			
//			List<DetailVO> detailList = new ArrayList<>();
//			detailList.add(detailVO1);
//			detailList.add(detailVO2);
//			
//			
//			dao.insert(orderVO1, detailList);
//			System.out.println("新增成功!!");
//			
			
			
			//查詢單列
//			OrderVO orderVO3 = dao.findByPrimaryKey("S20201006-00001");
//			System.out.println(orderVO3.getOr_no());
//			System.out.println(orderVO3.getMem_id());
//			System.out.println(orderVO3.getOr_name());
//			System.out.println(orderVO3.getOr_phone());
//			System.out.println(orderVO3.getOr_zip());
//			System.out.println(orderVO3.getOr_addr());
//			System.out.println(orderVO3.getOr_note());
//			System.out.println(orderVO3.getOr_total());
//			System.out.println(orderVO3.getOr_status());
//			System.out.println(orderVO3.getOr_time());
//			Format f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			System.out.println(f1.format(orderVO3.getOr_time().getTime()));
//			System.out.println("查單筆成功!!");
//			
//			
//			
//			//查詢全部
//			List<OrderVO> list = dao.getAll();
//			for(OrderVO aOrder : list) {
//				System.out.print(aOrder.getOr_no()+ ", ");
//				System.out.print(aOrder.getMem_id()+ ", ");
//				System.out.print(aOrder.getOr_name()+ ", ");
//				System.out.print(aOrder.getOr_phone()+ ", ");
//				System.out.print(aOrder.getOr_zip()+ ", ");
//				System.out.print(aOrder.getOr_addr()+ ", ");
//				System.out.print(aOrder.getOr_note()+ ", ");
//				System.out.print(aOrder.getOr_total()+ ", ");
//				System.out.print(aOrder.getOr_status()+ ", ");
//				Format f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				System.out.println(f2.format(aOrder.getOr_time().getTime()));
//				System.out.println();
//			}
//			System.out.println("查全部成功!!");
//			
//			//修改
//			OrderVO orderVO2 = new OrderVO();
//			orderVO2.setOr_no("S20201006-00001");
//			orderVO2.setOr_status(4);
//			dao.update(orderVO2);
//			System.out.println("修改成功");
//			
			
//訂單查詢
//			List<OrderVO> list2 = dao.orderSearch("M000001");
//			for(OrderVO aOrder : list2) {
//				System.out.print(aOrder.getOr_no()+ ", ");
//				System.out.print(aOrder.getMem_id()+ ", ");
//				System.out.print(aOrder.getOr_name()+ ", ");
//				System.out.print(aOrder.getOr_phone()+ ", ");
//				System.out.print(aOrder.getOr_zip()+ ", ");
//				System.out.print(aOrder.getOr_addr()+ ", ");
//				System.out.print(aOrder.getOr_note()+ ", ");
//				System.out.print(aOrder.getOr_total()+ ", ");
//				System.out.print(aOrder.getOr_status()+ ", ");
//				Format f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				System.out.println(f2.format(aOrder.getOr_time().getTime()));
//				System.out.println();
//			}
//			System.out.println("訂單查詢成功!!");
			
			//用訂單狀態查詢
			List<OrderVO> list = dao.getOrderByStauts(4);
			for(OrderVO aOrder : list) {
				System.out.print(aOrder.getOr_no()+ ", ");
				System.out.print(aOrder.getMem_id()+ ", ");
				System.out.print(aOrder.getOr_name()+ ", ");
				System.out.print(aOrder.getOr_phone()+ ", ");
				System.out.print(aOrder.getOr_zip()+ ", ");
				System.out.print(aOrder.getOr_addr()+ ", ");
				System.out.print(aOrder.getOr_note()+ ", ");
				System.out.print(aOrder.getOr_total()+ ", ");
				System.out.print(aOrder.getOr_status()+ ", ");
				Format f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println(f2.format(aOrder.getOr_time().getTime()));
				System.out.println();
			}
			
			
			
			
			
			
			
			
		}






		





		@Override
		public void changeStatus(OrderVO orderVO) {
			// TODO Auto-generated method stub
			
		}






		@Override
		public List<OrderVO> getOrderByStauts_Buyer(Integer or_stauts, String mem_id) {
			// TODO Auto-generated method stub
			return null;
		}

}
