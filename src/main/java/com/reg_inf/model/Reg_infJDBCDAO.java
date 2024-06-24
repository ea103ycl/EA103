package com.reg_inf.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
public class Reg_infJDBCDAO implements Reg_infDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";

	private static final String INSERT_STMT = //'M'+'to_char(sysdate,'yyyymmdd')'+'-'+'LPAD(reg_inf_seq.NEXTVAL, 5, '0')'
			"INSERT INTO reg_inf (ri_id,mt_no,mem_id,ri_qty,ri_status,ri_note) VALUES ('M' || to_char(sysdate,'yyyymmdd') || '-' || LPAD(reg_inf_seq.NEXTVAL, 5, '0'), ?, ?, ?, ?, ?)";
		
		private static final String GET_ALL_STMT = 
				"SELECT ri_id,mt_no,mem_id,ri_qty,ri_status,ri_note FROM reg_inf order by ri_id";
		
		private static final String GET_ONE_STMT = 
				"SELECT ri_id,mt_no,mem_id,ri_qty,ri_status,ri_note FROM reg_inf where ri_id = ?";
		private static final String DELETE = 
				"DELETE FROM reg_inf where ri_id = ?";
		private static final String UPDATE = 
				"UPDATE reg_inf set mt_no = ?,mem_id = ?,ri_qty = ?,ri_note = ? where ri_id = ?";

		private static final String GET_MEM_STMT = 
				"SELECT ri_id,mt_no,mem_id,ri_qty,ri_status,ri_note FROM reg_inf where mem_id = ? order by ri_id desc";
	
		private static final String CANCEL_STMT = 
				"UPDATE reg_inf set ri_status = ?,ri_qty = ?  where ri_id = ?";
		private static final String GET_ALL_STMT_BACK = 
				"SELECT ri_id,mt_no,mem_id,ri_qty,ri_status,ri_note FROM reg_inf where mt_no = ?";
		private static final String CHECK_MT_NUM =
				"UPDATE MEETING SET MT_NUM =(SELECT SUM(RI_QTY) FROM REG_INF WHERE MEETING.MT_NO = REG_INF.MT_NO) WHERE (SELECT SUM(RI_QTY) FROM REG_INF WHERE MEETING.MT_NO = REG_INF.MT_NO GROUP BY MT_NO)>=0";
		
		@SuppressWarnings("finally")
		@Override
		public String insert(Reg_infVO reg_infVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String next_ri_id = null;

		try {
			String[] col = { "RI_ID" };
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, reg_infVO.getMt_no());
			pstmt.setString(2, reg_infVO.getMem_id());
			pstmt.setInt(3, reg_infVO.getRi_qty());
			pstmt.setInt(4, reg_infVO.getRi_status());
			pstmt.setString(5, reg_infVO.getRi_note());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_ri_id = rs.getString(1);
				System.out.println("RI_ID = " + next_ri_id);		
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
			return next_ri_id;
		}

	}
	
	@Override
	public void cancel(Reg_infVO reg_infVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(CANCEL_STMT);
			
			pstmt.setInt(1, reg_infVO.getRi_status());
			pstmt.setString(2, reg_infVO.getRi_id());
			pstmt.setInt(3, reg_infVO.getRi_qty());

			pstmt.executeUpdate();

			// Handle any driver errors
		} 
		catch (ClassNotFoundException e) {
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
	public void update(Reg_infVO reg_infVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1, reg_infVO.getMt_no());
			pstmt.setString(2, reg_infVO.getMem_id());
			pstmt.setInt(3, reg_infVO.getRi_qty());
			pstmt.setString(4, reg_infVO.getRi_note());
			pstmt.setString(5, reg_infVO.getRi_id());
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
	public void delete(String ri_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ri_id);

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
	public Reg_infVO findByPrimaryKey(String ri_id) {

		Reg_infVO reg_infVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ri_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				reg_infVO = new Reg_infVO();
				reg_infVO.setRi_id(rs.getString("ri_id"));
				reg_infVO.setMt_no(rs.getString("mt_no"));
				reg_infVO.setMem_id(rs.getString("mem_id"));
				reg_infVO.setRi_qty(rs.getInt("ri_qty"));
				reg_infVO.setRi_status(rs.getInt("ri_status"));
				reg_infVO.setRi_note(rs.getString("ri_note"));			
			}

			// Handle any driver errors
		}
		catch (ClassNotFoundException e) {
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
		return reg_infVO;
	}
	
	@Override
	public List<Reg_infVO> getMem_Reg_inf(String mem_id) {
		List<Reg_infVO> list = new ArrayList<Reg_infVO>();
		Reg_infVO reg_infVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		    Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_MEM_STMT);

			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				reg_infVO = new Reg_infVO();
				reg_infVO.setRi_id(rs.getString("ri_id"));
				reg_infVO.setMt_no(rs.getString("mt_no"));
				reg_infVO.setMem_id(rs.getString("mem_id"));
				reg_infVO.setRi_qty(rs.getInt("ri_qty"));
				reg_infVO.setRi_status(rs.getInt("ri_status"));
				reg_infVO.setRi_note(rs.getString("ri_note"));
				list.add(reg_infVO); // Store the row in the list
			}
			
			System.out.println("[Reg_infDAO]getMem_Reg_inf執行完畢");
			// Handle any driver errors
		} 
		catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		}
		
		catch (SQLException se) {
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
	public List<Reg_infVO> getAll() {
		List<Reg_infVO> list = new ArrayList<Reg_infVO>();
		Reg_infVO reg_infVO = null;

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
				reg_infVO = new Reg_infVO();
				reg_infVO.setRi_id(rs.getString("ri_id"));
				reg_infVO.setMt_no(rs.getString("mt_no"));
				reg_infVO.setMem_id(rs.getString("mem_id"));
				reg_infVO.setRi_qty(rs.getInt("ri_qty"));
				reg_infVO.setRi_status(rs.getInt("ri_status"));
				reg_infVO.setRi_note(rs.getString("ri_note"));
				list.add(reg_infVO); // Store the row in the list
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
	
	@Override
	public void check_mt_num() {

		Connection con = null;
		PreparedStatement pstmt = null;
	

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(CHECK_MT_NUM);
			pstmt.execute();

			// Handle any driver errors
					}
					catch (ClassNotFoundException e) {
						throw new RuntimeException("Couldn't load database driver. "
								+ e.getMessage());
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
public List<Reg_infVO> getReg_inf_mt_no(String mt_no) {
	List<Reg_infVO> list = new ArrayList<Reg_infVO>();
	Reg_infVO reg_infVO = null;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
		
		 Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT_BACK);					
			pstmt.setString(1, mt_no);
			rs = pstmt.executeQuery();

		while (rs.next()) {
			// empVO 也稱為 Domain objects
			reg_infVO = new Reg_infVO();
			reg_infVO.setRi_id(rs.getString("ri_id"));
			reg_infVO.setMt_no(rs.getString("mt_no"));
			reg_infVO.setMem_id(rs.getString("mem_id"));
			reg_infVO.setRi_qty(rs.getInt("ri_qty"));
			reg_infVO.setRi_status(rs.getInt("ri_status"));
			reg_infVO.setRi_note(rs.getString("ri_note"));
			list.add(reg_infVO); // Store the row in the list
		}
		// Handle any driver errors
				} 
				catch (ClassNotFoundException e) {
					throw new RuntimeException("Couldn't load database driver. "
							+ e.getMessage());
					// Handle any SQL errors
				}
				
				catch (SQLException se) {
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

		Reg_infJDBCDAO dao = new Reg_infJDBCDAO();

		// 新增
//		Reg_infVO reg_infVO1 = new Reg_infVO();
//		reg_infVO1.setMt_no("MT00001");
//		reg_infVO1.setMem_id("M000003");
//		reg_infVO1.setRi_qty(new Integer(10));
//		reg_infVO1.setRi_status(new Integer(1));
//		reg_infVO1.setRi_note("桃園縣");
//		
//		dao.insert(reg_infVO1);

		// 修改
//		Reg_infVO reg_infVO2 = new Reg_infVO();
//		reg_infVO2.setRi_id("10004");
//		reg_infVO2.setMt_no("1");
//		reg_infVO2.setMem_id("M000003");
//		reg_infVO2.setRi_qty(new Integer(30));
//		reg_infVO2.setRi_note("新竹縣");
//	
//		dao.update(reg_infVO2);

		// 刪除
//		dao.delete("10005");

		// 查詢
//		Reg_infVO reg_infVO3 = dao.findByPrimaryKey("M20201017-10001");
//		System.out.print(reg_infVO3.getRi_id() + ",");
//		System.out.print(reg_infVO3.getMt_no() + ",");
//		System.out.print(reg_infVO3.getMem_id() + ",");
//		System.out.print(reg_infVO3.getRi_qty() + ",");
//		System.out.print(reg_infVO3.getRi_status() + ",");
//		System.out.print(reg_infVO3.getRi_note() + ",");
//	
//		System.out.println("---------------------");

		// 查詢
//		List<Reg_infVO> list = dao.getAll();
//		for (Reg_infVO aReg_inf: list) {
//			System.out.print(aReg_inf.getRi_id() + ",");
//			System.out.print(aReg_inf.getMt_no() + ",");
//			System.out.print(aReg_inf.getMem_id() + ",");
//			System.out.print(aReg_inf.getRi_qty() + ",");
//			System.out.print(aReg_inf.getRi_status() + ",");
//			System.out.print(aReg_inf.getRi_note() + ",");
//			System.out.println();
//		}
		// 取消
//		Reg_infVO reg_infVO4 = new Reg_infVO();
//		reg_infVO4.setRi_id("M20201021-10013");
//		reg_infVO4.setRi_status(new Integer(2));
//		System.out.print(reg_infVO4.getRi_id()+ ",");
//		System.out.print(reg_infVO4.getRi_status()+ ",");
//
//		dao.cancel(reg_infVO4);
		dao.check_mt_num();
	}
		
	
}
