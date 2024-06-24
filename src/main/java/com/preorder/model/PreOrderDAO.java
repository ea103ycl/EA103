package com.preorder.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.preorder.model.PreOrderVO;
import com.preorderdetail.model.PreOrderDetailDAO;
import com.preorderdetail.model.PreOrderDetailVO;
import com.wel_record.model.WelRecordJNDIDAO;

public class PreOrderDAO implements PreOrderDAO_interface{
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
			"INSERT INTO PRE_ORDER (po_no,mem_id,po_time,po_zip,po_name,po_phone,po_addr,po_status,po_total,po_note) "
			+ "VALUES ('P'||to_char(sysdate,'yyyymmdd')||'-'||LPAD(to_char(PRE_ORDER_SEQ.NEXTVAL), 5, '0'), ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT po_no,mem_id,po_time,po_zip,po_name,po_phone,po_addr,po_status,po_total,po_note "
			+ "FROM PRE_ORDER order by po_no";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM PRE_ORDER where po_no = ?";
		private static final String DELETE = 
			"DELETE FROM PRE_ORDER_DETAIL where po_no = ?";
		private static final String DELETE2 = 
			"DELETE FROM PRE_ORDER where po_no = ?";
		private static final String UPDATE = 
			"UPDATE PRE_ORDER set mem_id=?, po_time=?, po_zip=?, po_name=?, po_phone=?, po_addr=?,po_status=?,po_total=?,po_note=? where po_no = ?";
		private static final String UPDATE_STATUS = "UPDATE PRE_ORDER set po_status=? where po_no = ?";
		private static final String GET_Detail_ByOrder_STMT = "SELECT PO_NO,PO_PROD_NO,PO_QTY,PO_PRICE FROM PRE_ORDER_DETAIL WHERE PO_NO = ? ORDER BY PO_PROD_NO";
		
		private static final String GET_ALL_ByMemid_STMT = "SELECT * FROM PRE_ORDER WHERE mem_id = ? ORDER BY PO_NO";
		
		private static final String GET_ALL_PonoByReachDiscount = "SELECT * FROM PRE_ORDER t1 left join pre_order_detail t2 on t1.po_no = t2.po_no where t1.po_no IN (select po_no from pre_order_detail where (po_prod_no IN (select po_prod_no from(SELECT p.po_prod_no,sum(PO_QTY) as po_qty FROM PRE_ORDER_DETAIL P WHERE P.PO_PROD_NO IN(SELECT PO_PROD_NO FROM PRE_ORDER WHERE po_status = 3)GROUP BY p.po_prod_no) where po_qty>= ? and po_prod_no = ?))) and po_prod_no =?";
		
	
	
	@Override
	public void insert(PreOrderVO preorderVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, preorderVO.getMem_id());
			pstmt.setInt(2, preorderVO.getPo_zip());
			pstmt.setString(3, preorderVO.getPo_name());
			pstmt.setString(4, preorderVO.getPo_phone());
			pstmt.setString(5, preorderVO.getPo_addr());
			pstmt.setInt(6, preorderVO.getPo_status());
			pstmt.setInt(7, preorderVO.getPo_total());
			pstmt.setString(8, preorderVO.getPo_note());

			pstmt.executeUpdate();
			System.out.println("INSERT_STMT��SQL�禡executeUpdate���槹��");
			// Handle any driver errors
		}catch (SQLException se) {
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
	public void update(PreOrderVO preorderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, preorderVO.getMem_id());
			pstmt.setTimestamp(2, preorderVO.getPo_time());
			pstmt.setInt(3, preorderVO.getPo_zip());
			pstmt.setString(4, preorderVO.getPo_name());
			pstmt.setString(5, preorderVO.getPo_phone());
			pstmt.setString(6, preorderVO.getPo_addr());
			pstmt.setInt(7, preorderVO.getPo_status());
			pstmt.setInt(8, preorderVO.getPo_total());
			pstmt.setString(9, preorderVO.getPo_note());
			pstmt.setString(10, preorderVO.getPo_no());
			

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
			con.setAutoCommit(false);
			
			System.out.println("�Y�N����DELETE��SQL���O");
			pstmt = con.prepareStatement(DELETE);
			System.out.println("po_no = "+po_no);
			pstmt.setString(1, po_no);
			pstmt.executeUpdate();
			System.out.println("�Y�N����DELETE2��SQL���O");
			pstmt = con.prepareStatement(DELETE2);
			System.out.println("po_no = "+po_no);
			pstmt.setString(1, po_no);
			pstmt.executeUpdate();
			System.out.println("������DELETE��SQL���O���槹��");
			
			con.commit();
			con.setAutoCommit(true);
			
			
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
	public PreOrderVO findByPrimaryKey(String po_no) {
		PreOrderVO preorderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, po_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				preorderVO = new PreOrderVO();
				preorderVO.setPo_no(rs.getString("po_no"));
				preorderVO.setMem_id(rs.getString("mem_id"));
				preorderVO.setPo_time(rs.getTimestamp("po_time"));
				preorderVO.setPo_zip(rs.getInt("po_zip"));
				preorderVO.setPo_name(rs.getString("po_name"));
				preorderVO.setPo_phone(rs.getString("po_phone"));
				preorderVO.setPo_addr(rs.getString("po_addr"));
				preorderVO.setPo_status(rs.getInt("po_status"));
				preorderVO.setPo_total(rs.getInt("po_total"));
				preorderVO.setPo_note(rs.getString("po_note"));
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
		return preorderVO;
	}
	@Override
	public List<PreOrderVO> getAll() {
		List<PreOrderVO> list = new ArrayList<PreOrderVO>();
		PreOrderVO preorderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			System.out.println("�q��DAO - �i�JgetAll()��k");
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				preorderVO = new PreOrderVO();
				preorderVO.setPo_no(rs.getString("po_no"));
				preorderVO.setMem_id(rs.getString("mem_id"));
				preorderVO.setPo_time(rs.getTimestamp("po_time"));
				preorderVO.setPo_zip(rs.getInt("po_zip"));
				preorderVO.setPo_name(rs.getString("po_name"));
				preorderVO.setPo_phone(rs.getString("po_phone"));
				preorderVO.setPo_addr(rs.getString("po_addr"));
				preorderVO.setPo_status(rs.getInt("po_status"));
				preorderVO.setPo_total(rs.getInt("po_total"));
				preorderVO.setPo_note(rs.getString("po_note"));
				list.add(preorderVO); // Store the row in the list
			}

			// Handle any driver errors
		}catch (SQLException se) {
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
	public Set<PreOrderDetailVO> getDetailByOrderno(String po_no) {
		Set<PreOrderDetailVO> set = new HashSet<PreOrderDetailVO>();
		PreOrderDetailVO preorderdetailVO = null;
		
		Connection con = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Detail_ByOrder_STMT);
			pstmt.setString(1, po_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				preorderdetailVO = new PreOrderDetailVO();
				preorderdetailVO.setPo_no(rs.getString("po_no"));
				preorderdetailVO.setPo_prod_no(rs.getString("po_prod_no"));
				preorderdetailVO.setPo_qty(rs.getInt("po_qty"));
				preorderdetailVO.setPo_price(rs.getInt("po_price"));
				set.add(preorderdetailVO);
			}
			
			
		}catch (SQLException se) {
			System.out.println("Ĳ�oSQLException");
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return set;
	}
	@Override
	public void inserWithDetail(PreOrderVO preorderVO, List<PreOrderDetailVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println("準備執行inserWithDetail");
		ResultSet rs = null;
		try {
			con = ds.getConnection();

			con.setAutoCommit(false);
			
			//���s�W�D�q��
			
			String cols[] = {"PO_NO"};
			System.out.println("準備執行INSERT_STMT的SQL函式");
			pstmt = con.prepareStatement(INSERT_STMT , cols);
			pstmt.setString(1,preorderVO.getMem_id());
			pstmt.setInt(2,preorderVO.getPo_zip());
			pstmt.setString(3,preorderVO.getPo_name());
			pstmt.setString(4,preorderVO.getPo_phone());
			pstmt.setString(5,preorderVO.getPo_addr());
			pstmt.setInt(6,preorderVO.getPo_status());
			pstmt.setInt(7,preorderVO.getPo_total());
			pstmt.setString(8,preorderVO.getPo_note());
			pstmt.executeUpdate();
			System.out.println("INSERT_STMT的SQL函式執行完畢");
			//�̨��������ۼW�D���
			String next_po_no=null;
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				next_po_no = rs.getString(1);
				System.out.println("自增主鍵值= " + next_po_no +"(剛新增成功的主訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			
			//�A�P�ɷs�W����
			PreOrderDetailDAO dao = new PreOrderDetailDAO();
			System.out.println("list.size()-A="+list.size());
			for (PreOrderDetailVO aPreOrderDetail : list) {
				System.out.println("現在的next_po_no是" + next_po_no);
				aPreOrderDetail.setPo_no(new String(next_po_no)) ;
				
				
				dao.insert2(aPreOrderDetail,con);
			}
			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
			
			WelRecordJNDIDAO dao_for_update = new WelRecordJNDIDAO();
			dao_for_update.updateOrderId(next_po_no, preorderVO.getMem_id());
			
			
			System.out.println("list.size()-B="+list.size());
			System.out.println("新增主訂單編號" + next_po_no + "時,共有預購明細" + list.size()
								+ "個同時被新增");
			
			
		} catch (SQLException se) {
			System.out.println("觸發SQLException");
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-dept");
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
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
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

	}
	@Override
	public List<PreOrderVO> getAllByMemid(String mem_id) {
		List<PreOrderVO> list = new ArrayList<PreOrderVO>();
		PreOrderVO preorderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			System.out.println("訂單DAO - 進入getAllByMemid()方法");
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ByMemid_STMT);
			pstmt.setString(1, mem_id);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO �]�٬� Domain objects
				preorderVO = new PreOrderVO();
				preorderVO.setPo_no(rs.getString("po_no"));
				preorderVO.setMem_id(rs.getString("mem_id"));
				preorderVO.setPo_time(rs.getTimestamp("po_time"));
				preorderVO.setPo_zip(rs.getInt("po_zip"));
				preorderVO.setPo_name(rs.getString("po_name"));
				preorderVO.setPo_phone(rs.getString("po_phone"));
				preorderVO.setPo_addr(rs.getString("po_addr"));
				preorderVO.setPo_status(rs.getInt("po_status"));
				preorderVO.setPo_total(rs.getInt("po_total"));
				preorderVO.setPo_note(rs.getString("po_note"));
				list.add(preorderVO); // Store the row in the list
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
	@Override
	public List<PreOrderVO> look_discount_pono(Integer reach_number,String po_prod_no) {
		List<PreOrderVO> list = new ArrayList<PreOrderVO>();
		PreOrderVO preorderVO = null;


		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		
		try {
			System.out.println("訂單DAO - 進入look_discount_pono()方法");
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_PonoByReachDiscount);
			pstmt.setInt(1, reach_number);
			System.out.println("set第一個問號= "+reach_number);
			pstmt.setString(2, po_prod_no);
			System.out.println("set第二個問號 = "+po_prod_no);
			pstmt.setString(3, po_prod_no);
			System.out.println("set第三個問號 = "+po_prod_no);
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				preorderVO = new PreOrderVO();
				
				preorderVO.setPo_no(rs.getString("po_no"));
				preorderVO.setMem_id(rs.getString("mem_id"));
				preorderVO.setPo_time(rs.getTimestamp("po_time"));
				preorderVO.setPo_zip(rs.getInt("po_zip"));
				preorderVO.setPo_name(rs.getString("po_name"));
				preorderVO.setPo_phone(rs.getString("po_phone"));
				preorderVO.setPo_addr(rs.getString("po_addr"));
				preorderVO.setPo_status(rs.getInt("po_status"));
				preorderVO.setPo_total(rs.getInt("po_total"));
				preorderVO.setPo_note(rs.getString("po_note"));
				//�Ȧs��
				preorderVO.setPo_qty(rs.getInt("po_qty"));
				preorderVO.setPo_price(rs.getInt("po_price"));
				preorderVO.setPo_prod_no(rs.getString("po_prod_no"));
				
				list.add(preorderVO);
			}
			
			

		
		}catch (SQLException se) {
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
		
		System.out.println("回傳list 結束PreOrderDAO");
		return list;
	}
	@Override
	public void updateStatus(PreOrderVO preorderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);
			pstmt.setInt(1, preorderVO.getPo_status());
			pstmt.setString(2, preorderVO.getPo_no());
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
