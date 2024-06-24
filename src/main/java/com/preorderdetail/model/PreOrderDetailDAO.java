package com.preorderdetail.model;

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

import com.preorder.model.PreOrderVO;
import com.preproduct.model.PreProductVO;

public class PreOrderDetailDAO implements PreOrderDetailDAO_interface{
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
	
		private static final String INSERT_STMT = 
			"INSERT INTO PRE_ORDER_DETAIL (po_no,po_prod_no,po_qty,po_price) "
			+ "VALUES (?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT po_no,po_prod_no,po_qty,po_price FROM PRE_ORDER_DETAIL order by po_no";
		private static final String GET_ONE_STMT = 
			"SELECT po_no,po_prod_no,po_qty,po_price FROM PRE_ORDER_DETAIL where po_no = ?";
		private static final String DELETE = 
			"DELETE FROM PRE_ORDER_DETAIL where po_no = ?";
		private static final String DELETE_By_PoProdNo = 
			"DELETE FROM PRE_ORDER_DETAIL where po_prod_no = ?";
		private static final String UPDATE = 
			"UPDATE PRE_ORDER_DETAIL set po_prod_no=?, po_qty=?, po_price=? where po_no = ?";
		private static final String GET_DetailByPo_no_STMT = 
				"SELECT * FROM PRE_ORDER_DETAIL where po_no = ?";
		private static final String SUM_QTY_ByOrderAndStat_3 = "SELECT p.po_prod_no,sum(PO_QTY) as po_qty FROM PRE_ORDER_DETAIL P WHERE P.PO_PROD_NO IN((SELECT PO_PROD_NO FROM PRE_ORDER  WHERE po_status = 3)) GROUP BY PO_PROD_NO";
		
		@Override
		public void insert(PreOrderDetailVO preorderdetailVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, preorderdetailVO.getPo_no());
				pstmt.setString(2, preorderdetailVO.getPo_prod_no());
				pstmt.setInt(3, preorderdetailVO.getPo_qty());
				pstmt.setInt(4, preorderdetailVO.getPo_price());
				

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
		public void update(PreOrderDetailVO preorderdetailVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

				
				pstmt.setString(1, preorderdetailVO.getPo_prod_no());
				pstmt.setInt(2, preorderdetailVO.getPo_qty());
				pstmt.setInt(3, preorderdetailVO.getPo_price());
				pstmt.setString(4, preorderdetailVO.getPo_no());
			
				

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
		public void delete(String po_no) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(DELETE);

				pstmt.setString(1, po_no);

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
		public List<PreOrderDetailVO> findByPrimaryKey(String po_no) {
			List<PreOrderDetailVO> preorderdetaillist = new ArrayList<PreOrderDetailVO>();
			PreOrderDetailVO preorderdetailVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);
				pstmt.setString(1, po_no);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo 也稱為 Domain objects
					preorderdetailVO = new PreOrderDetailVO();
					System.out.println("po_no = "+ po_no);
					preorderdetailVO.setPo_no(rs.getString("po_no"));
					preorderdetailVO.setPo_prod_no(rs.getString("po_prod_no"));
					preorderdetailVO.setPo_qty(rs.getInt("po_qty"));
					preorderdetailVO.setPo_price(rs.getInt("po_price"));
					preorderdetaillist.add(preorderdetailVO);
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
			return preorderdetaillist;
		}
		@Override
		public List<PreOrderDetailVO> getAll() {
			List<PreOrderDetailVO> list = new ArrayList<PreOrderDetailVO>();
			PreOrderDetailVO preorderdetailVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVO 也稱為 Domain objects
					preorderdetailVO = new PreOrderDetailVO();
					preorderdetailVO.setPo_no(rs.getString("po_no"));
					preorderdetailVO.setPo_prod_no(rs.getString("po_prod_no"));
					preorderdetailVO.setPo_qty(rs.getInt("po_qty"));
					preorderdetailVO.setPo_price(rs.getInt("po_price"));
					
					list.add(preorderdetailVO); // Store the row in the list
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
		public void insert2(PreOrderDetailVO preorderdetailVO, Connection con) {
			PreparedStatement pstmt = null;
			
			try {
				
				pstmt = con.prepareStatement(INSERT_STMT);
				
				pstmt.setString(1, preorderdetailVO.getPo_no());
				pstmt.setString(2, preorderdetailVO.getPo_prod_no());
				pstmt.setInt(3, preorderdetailVO.getPo_qty());
				pstmt.setInt(4, preorderdetailVO.getPo_price());
				System.out.println("in insert2"+preorderdetailVO.getPo_no());
				System.out.println(preorderdetailVO.getPo_prod_no());
				System.out.println(preorderdetailVO.getPo_qty());
				System.out.println(preorderdetailVO.getPo_price());
				pstmt.executeUpdate();
				
				//處裡所有SQL錯誤問題
			} catch (SQLException se) {
				if (con != null) {
					try {
						// 3●設定於當有exception發生時之catch區塊內
						System.err.print("Transaction is being ");
						System.err.println("rolled back-由-emp");
						con.rollback();
					} catch (SQLException excep) {
						throw new RuntimeException("rollback error occured. "
								+ excep.getMessage());
					}
				}
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
			}
			
			
			
		}
		@Override
		public PreOrderDetailVO getAllByPo_no(String po_no) {
			
			PreOrderDetailVO preorderdetailVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				System.out.println("進入POD的DAO - getAllByPo_no(String po_no)方法");
				System.out.println("訂單DAO - 進入getAllByMemid()方法");
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_DetailByPo_no_STMT);
				pstmt.setString(1, po_no);
				
				rs = pstmt.executeQuery();
				while (rs.next()) {
					// empVO 也稱為 Domain objects
					preorderdetailVO = new PreOrderDetailVO();
					preorderdetailVO.setPo_no(rs.getString("po_no"));
					preorderdetailVO.setPo_prod_no(rs.getString("po_prod_no"));
					preorderdetailVO.setPo_qty(rs.getInt("po_qty"));
					preorderdetailVO.setPo_price(rs.getInt("po_price"));
					
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
			return preorderdetailVO;
		}
		@Override
		public List<PreOrderDetailVO> getAll_OrderQty() {
			System.out.println("預購訂單Detail的DAO - 進入getAll_OrderQty()方法");
			List<PreOrderDetailVO> list = new ArrayList<PreOrderDetailVO>();
			PreOrderDetailVO preorderdetailVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(SUM_QTY_ByOrderAndStat_3);
				rs = pstmt.executeQuery();
				System.out.println("POD-DAO執行executeQuery()方法");

				while (rs.next()) {
					// empVO 也稱為 Domain objects
					preorderdetailVO = new PreOrderDetailVO();
					System.out.println("取得po_prod_no = " + rs.getString("po_prod_no"));
					preorderdetailVO.setPo_prod_no(rs.getString("po_prod_no"));
					System.out.println("取得po_qty = "+rs.getInt("po_qty"));
					preorderdetailVO.setPo_qty(rs.getInt("po_qty"));

					System.out.println("count++");
					list.add(preorderdetailVO); 
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
		public void deleteBypo_prod_no(String po_prod_no) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(DELETE_By_PoProdNo);

				pstmt.setString(1, po_prod_no);

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

		
		
		
		
		
}
