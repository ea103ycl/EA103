package com.preorder.model;
//package com.preorder.model;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//
//import com.preorder.model.PreOrderVO;
//import com.preorderdetail.model.PreOrderDetailDAOJDBC;
//import com.preorderdetail.model.PreOrderDetailVO;
//
//public class PreOrderDAOJDBC implements PreOrderDAO_interface{
//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	String userid = "G1";
//	String passwd = "123456";
//	
//	private static final String INSERT_STMT = 
//			"INSERT INTO PRE_ORDER (po_no,mem_id,po_time,po_zip,po_name,po_phone,po_addr,po_status,po_total,po_note) "
//			+ "VALUES (PRE_ORDER_SEQ.NEXTVAL, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?)";
//		private static final String GET_ALL_STMT = 
//			"SELECT po_no,mem_id,po_time,po_zip,po_name,po_phone,po_addr,po_status,po_total,po_note "
//			+ "FROM PRE_ORDER order by po_no";
//		private static final String GET_ONE_STMT = 
//			"SELECT po_no,mem_id,po_time,po_zip,po_name,po_phone,po_addr,po_status,po_total,po_note "
//			+ "FROM PRE_ORDER where po_no = ?";
//		private static final String DELETE = 
//			"DELETE FROM PRE_ORDER_DETAIL where po_no = ?";
//		private static final String DELETE2 = 
//			"DELETE FROM PRE_ORDER where po_no = ?";
//		private static final String UPDATE = 
//			"UPDATE PRE_ORDER set mem_id=?, po_time=?, po_zip=?, po_name=?, po_phone=?, po_addr=?,po_status=?,po_total=?,po_note=? where po_no = ?";
//		private static final String GET_Detail_ByOrder_STMT = "SELECT PO_NO,PO_PROD_NO,PO_QTY,PO_PRICE FROM PRE_ORDER_DETAIL WHERE PO_NO = ? ORDER BY PO_PROD_NO";
//		
//		private static final String GET_ALL_ByMemid_STMT = "SELECT * FROM PRE_ORDER WHERE mem_id = ? ORDER BY PO_NO";
//		
//	
//	
//	@Override
//	public void insert(PreOrderVO preorderVO) {
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			System.out.println("準備執行INSERT_STMT的SQL函式");
//			pstmt = con.prepareStatement(INSERT_STMT);
//
//			pstmt.setString(1, preorderVO.getMem_id());
//			pstmt.setInt(2, preorderVO.getPo_zip());
//			pstmt.setString(3, preorderVO.getPo_name());
//			pstmt.setString(4, preorderVO.getPo_phone());
//			pstmt.setString(5, preorderVO.getPo_addr());
//			pstmt.setInt(6, preorderVO.getPo_status());
//			pstmt.setInt(7, preorderVO.getPo_total());
//			pstmt.setString(8, preorderVO.getPo_note());
//
//			pstmt.executeUpdate();
//			System.out.println("INSERT_STMT的SQL函式executeUpdate執行完畢");
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		
//	}
//	@Override
//	public void update(PreOrderVO preorderVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(UPDATE);
//
//			pstmt.setString(1, preorderVO.getMem_id());
//			pstmt.setTimestamp(2, preorderVO.getPo_time());
//			pstmt.setInt(3, preorderVO.getPo_zip());
//			pstmt.setString(4, preorderVO.getPo_name());
//			pstmt.setString(5, preorderVO.getPo_phone());
//			pstmt.setString(6, preorderVO.getPo_addr());
//			pstmt.setInt(7, preorderVO.getPo_status());
//			pstmt.setInt(8, preorderVO.getPo_total());
//			pstmt.setString(9, preorderVO.getPo_note());
//			pstmt.setString(10, preorderVO.getPo_no());
//			
//
//			pstmt.executeUpdate();
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		
//	}
//	@Override
//	public void delete(String po_no) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			con.setAutoCommit(false);
//			
//			System.out.println("即將執行DELETE的SQL指令");
//			pstmt = con.prepareStatement(DELETE);
//			System.out.println("po_no = "+po_no);
//			pstmt.setString(1, po_no);
//			pstmt.executeUpdate();
//			System.out.println("即將執行DELETE2的SQL指令");
//			pstmt = con.prepareStatement(DELETE2);
//			System.out.println("po_no = "+po_no);
//			pstmt.setString(1, po_no);
//			pstmt.executeUpdate();
//			System.out.println("全部的DELETE的SQL指令執行完畢");
//			
//			con.commit();
//			con.setAutoCommit(true);
//			
//			
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		
//	}
//	@Override
//	public PreOrderVO findByPrimaryKey(String po_no) {
//		PreOrderVO preorderVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ONE_STMT);
//
//			pstmt.setString(1, po_no);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// empVo 也稱為 Domain objects
//				preorderVO = new PreOrderVO();
//				preorderVO.setPo_no(rs.getString("po_no"));
//				preorderVO.setMem_id(rs.getString("mem_id"));
//				preorderVO.setPo_time(rs.getTimestamp("po_time"));
//				preorderVO.setPo_zip(rs.getInt("po_zip"));
//				preorderVO.setPo_name(rs.getString("po_name"));
//				preorderVO.setPo_phone(rs.getString("po_phone"));
//				preorderVO.setPo_addr(rs.getString("po_addr"));
//				preorderVO.setPo_status(rs.getInt("po_status"));
//				preorderVO.setPo_total(rs.getInt("po_total"));
//				preorderVO.setPo_note(rs.getString("po_note"));
//			}
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return preorderVO;
//	}
//	@Override
//	public List<PreOrderVO> getAll() {
//		List<PreOrderVO> list = new ArrayList<PreOrderVO>();
//		PreOrderVO preorderVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			System.out.println("訂單DAO - 進入getAll()方法");
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ALL_STMT);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// empVO 也稱為 Domain objects
//				preorderVO = new PreOrderVO();
//				preorderVO.setPo_no(rs.getString("po_no"));
//				preorderVO.setMem_id(rs.getString("mem_id"));
//				preorderVO.setPo_time(rs.getTimestamp("po_time"));
//				preorderVO.setPo_zip(rs.getInt("po_zip"));
//				preorderVO.setPo_name(rs.getString("po_name"));
//				preorderVO.setPo_phone(rs.getString("po_phone"));
//				preorderVO.setPo_addr(rs.getString("po_addr"));
//				preorderVO.setPo_status(rs.getInt("po_status"));
//				preorderVO.setPo_total(rs.getInt("po_total"));
//				preorderVO.setPo_note(rs.getString("po_note"));
//				list.add(preorderVO); // Store the row in the list
//			}
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list;
//	}
//	@Override
//	public Set<PreOrderDetailVO> getDetailByOrderno(String po_no) {
//		Set<PreOrderDetailVO> set = new HashSet<PreOrderDetailVO>();
//		PreOrderDetailVO preorderdetailVO = null;
//		
//		Connection con = null;
//		PreparedStatement pstmt =null;
//		ResultSet rs = null;
//		
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url,userid,passwd);
//			pstmt = con.prepareStatement(GET_Detail_ByOrder_STMT);
//			pstmt.setString(1, po_no);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				preorderdetailVO = new PreOrderDetailVO();
//				preorderdetailVO.setPo_no(rs.getString("po_no"));
//				preorderdetailVO.setPo_prod_no(rs.getString("po_prod_no"));
//				preorderdetailVO.setPo_qty(rs.getInt("po_qty"));
//				preorderdetailVO.setPo_price(rs.getInt("po_price"));
//				set.add(preorderdetailVO);
//			}
//			
//			
//		}catch (ClassNotFoundException e) {
//			System.out.println("觸發ClassNotFoundException");
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//		} catch (SQLException se) {
//			System.out.println("觸發SQLException");
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return set;
//	}
//	@Override
//	public void inserWithDetail(PreOrderVO preorderVO, List<PreOrderDetailVO> list) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		System.out.println("準備執行inserWithDetail");
//		ResultSet rs = null;
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url,userid,passwd);
//			
//			con.setAutoCommit(false);
//			
//			//先新增主訂單
//			
//			String cols[] = {"PO_NO"};
//			System.out.println("準備執行INSERT_STMT的SQL函式");
//			pstmt = con.prepareStatement(INSERT_STMT , cols);
//			pstmt.setString(1,preorderVO.getMem_id());
//			pstmt.setInt(2,preorderVO.getPo_zip());
//			pstmt.setString(3,preorderVO.getPo_name());
//			pstmt.setString(4,preorderVO.getPo_phone());
//			pstmt.setString(5,preorderVO.getPo_addr());
//			pstmt.setInt(6,preorderVO.getPo_status());
//			pstmt.setInt(7,preorderVO.getPo_total());
//			pstmt.setString(8,preorderVO.getPo_note());
//			pstmt.executeUpdate();
//			System.out.println("INSERT_STMT的SQL函式執行完畢");
//			//攫取對應的自增主鍵值
//			String next_po_no=null;
//			rs = pstmt.getGeneratedKeys();
//			if(rs.next()) {
//				next_po_no = rs.getString(1);
//				System.out.println("自增主鍵值= " + next_po_no +"(剛新增成功的主訂單編號)");
//			} else {
//				System.out.println("未取得自增主鍵值");
//			}
//			
//			//再同時新增明細
//			PreOrderDetailDAOJDBC dao = new PreOrderDetailDAOJDBC();
//			System.out.println("list.size()-A="+list.size());
//			for (PreOrderDetailVO aPreOrderDetail : list) {
//				System.out.println("現在的next_po_no是" + next_po_no);
//				aPreOrderDetail.setPo_no(new String(next_po_no)) ;
//				
//				
//				dao.insert2(aPreOrderDetail,con);
//			}
//			// 2●設定於 pstm.executeUpdate()之後
//			con.commit();
//			con.setAutoCommit(true);
//			/*把凱洛琳的update方法塞到這邊試試看*/
//			System.out.println("list.size()-B="+list.size());
//			System.out.println("新增主訂單編號" + next_po_no + "時,共有預購明細" + list.size()
//								+ "個同時被新增");
//			
//			
//		} catch (ClassNotFoundException e) {
//			System.out.println("觸發ClassNotFoundException");
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			System.out.println("觸發SQLException");
//			if (con != null) {
//				try {
//					// 3●設定於當有exception發生時之catch區塊內
//					System.err.print("Transaction is being ");
//					System.err.println("rolled back-由-dept");
//					con.rollback();
//				} catch (SQLException excep) {
//					throw new RuntimeException("rollback error occured. "
//							+ excep.getMessage());
//				}
//			}
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			
//		}
//
//	}
//	@Override
//	public List<PreOrderVO> getAllByMemid(String mem_id) {
//		List<PreOrderVO> list = new ArrayList<PreOrderVO>();
//		PreOrderVO preorderVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			System.out.println("訂單DAO - 進入getAllByMemid()方法");
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ALL_ByMemid_STMT);
//			pstmt.setString(1, mem_id);
//			
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				// empVO 也稱為 Domain objects
//				preorderVO = new PreOrderVO();
//				preorderVO.setPo_no(rs.getString("po_no"));
//				preorderVO.setMem_id(rs.getString("mem_id"));
//				preorderVO.setPo_time(rs.getTimestamp("po_time"));
//				preorderVO.setPo_zip(rs.getInt("po_zip"));
//				preorderVO.setPo_name(rs.getString("po_name"));
//				preorderVO.setPo_phone(rs.getString("po_phone"));
//				preorderVO.setPo_addr(rs.getString("po_addr"));
//				preorderVO.setPo_status(rs.getInt("po_status"));
//				preorderVO.setPo_total(rs.getInt("po_total"));
//				preorderVO.setPo_note(rs.getString("po_note"));
//				list.add(preorderVO); // Store the row in the list
//			}
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list;
//	}
//	
//	
//	
//	
//	
//}
