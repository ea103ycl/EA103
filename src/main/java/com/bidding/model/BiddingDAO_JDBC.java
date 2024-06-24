package com.bidding.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class BiddingDAO_JDBC implements BiddingDAO_interface {

//	private static final String INSERT_STMT = "INSERT INTO dept2 (deptno,dname,loc) VALUES (dept2_seq.NEXTVAL, ?, ?)";
//	private static final String GET_ALL_STMT = "SELECT deptno , dname, loc FROM dept2";
//	private static final String GET_ONE_STMT = "SELECT deptno , dname, loc FROM dept2 where deptno = ?";
//	private static final String GET_Emps_ByDeptno_STMT = "SELECT empno,ename,job,to_char(hiredate,'yyyy-mm-dd') hiredate,sal,comm,deptno FROM emp2 where deptno = ? order by empno";
//	
//	private static final String DELETE_EMPs = "DELETE FROM emp2 where deptno = ?";
//	private static final String DELETE_DEPT = "DELETE FROM dept2 where deptno = ?";	
	private static final String INSERT_STMT = "INSERT INTO bidding VALUES ('B'|| LPAD(BD_NO_SEQ.NEXTVAL, 6, '0'),?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE bidding SET mem_id=?, bd_prod_no=?, bd_price=?, bd_date_str=?, bd_date_end=?, bd_status=?, bd_ord_status=?, pmt_status=?, bd_zip=?, bd_name=?, bd_phone=?, bd_addr=? WHERE bd_no=?";
	private static final String GETONE_STMT = "SELECT * FROM bidding WHERE bd_no=? ";
	private static final String GETALL_STMT = "SELECT * FROM bidding";

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String userid = "G1";
	private String password = "123456";

	public String insert(BiddingVO bVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String currBdNo=null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, bVO.getMemId());
			pstmt.setInt(2, bVO.getBdProdNo());
			pstmt.setInt(3, bVO.getBdPrice());
			pstmt.setTimestamp(4, bVO.getBdDateStr());
			pstmt.setTimestamp(5, bVO.getBdDateEnd());
			pstmt.setInt(6, bVO.getBdStatus());
			pstmt.setInt(7, bVO.getBdOrdStatus());
			pstmt.setInt(8, bVO.getPmtStatus());
			pstmt.setInt(9, bVO.getBdZip());
			pstmt.setString(10, bVO.getBdName());
			pstmt.setString(11, bVO.getBdPhone());
			pstmt.setString(12, bVO.getBdAddr());
			pstmt.executeUpdate();
	
		
			rs= pstmt.getGeneratedKeys();
			if (rs.next()) {
				currBdNo = rs.getString(1);
				System.out.println("自增主鍵值= " + currBdNo +"(新增成功的競標編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
	

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Driver loading failure"+e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("Database error occured"+e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		System.out.println("insert successfully");
		return currBdNo;
	}
	
	public void update(BiddingVO bVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, bVO.getMemId());
			pstmt.setInt(2, bVO.getBdProdNo());
			pstmt.setInt(3, bVO.getBdPrice());
			pstmt.setTimestamp(4, bVO.getBdDateStr());
			pstmt.setTimestamp(5, bVO.getBdDateEnd());
			pstmt.setInt(6, bVO.getBdStatus());
			pstmt.setInt(7, bVO.getBdOrdStatus());
			pstmt.setInt(8, bVO.getPmtStatus());
			pstmt.setInt(9, bVO.getBdZip());
			pstmt.setString(10, bVO.getBdName());
			pstmt.setString(11, bVO.getBdPhone());
			pstmt.setString(12, bVO.getBdAddr());
			// where BD_NO
			pstmt.setString(13, bVO.getBdNo());
			
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Driver loading failure" + e.getMessage());

		} catch (SQLException e) {
			throw new RuntimeException("database error occured" + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		System.out.println("update successfully");
	}

	public BiddingVO getOne(String bdNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BiddingVO bVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GETONE_STMT);

			pstmt.setString(1, bdNo);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bVO = new BiddingVO();

				bVO.setBdNo(rs.getString("bd_no"));
				bVO.setMemId(rs.getString("mem_id"));
				bVO.setBdProdNo(rs.getInt("bd_prod_no"));
				bVO.setBdPrice(rs.getInt("bd_price"));
				bVO.setBdDateStr(rs.getTimestamp("bd_date_str"));
				bVO.setBdDateEnd(rs.getTimestamp("bd_date_end"));
				bVO.setBdStatus(rs.getInt("bd_status"));
				bVO.setBdOrdStatus(rs.getInt("bd_ord_status"));
				bVO.setPmtStatus(rs.getInt("pmt_status"));
				bVO.setBdZip(rs.getInt("bd_zip"));
				bVO.setBdName(rs.getString("bd_name"));
				bVO.setBdPhone(rs.getString("bd_phone"));
				bVO.setBdAddr(rs.getString("bd_addr"));
				
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Driver loading failure:" + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("Database error occured:" + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		System.out.println(bVO);
		return bVO;
	}

	public List<BiddingVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		BiddingVO bVO = null;
		List<BiddingVO> list = new ArrayList<BiddingVO>();

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GETALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bVO = new BiddingVO();
				bVO.setBdNo(rs.getString("bd_no"));
				bVO.setMemId(rs.getString("mem_id"));
				bVO.setBdProdNo(rs.getInt("bd_prod_no"));
				bVO.setBdPrice(rs.getInt("bd_price"));
				bVO.setBdDateStr(rs.getTimestamp("bd_date_str"));
				bVO.setBdDateEnd(rs.getTimestamp("bd_date_end"));
				bVO.setBdStatus(rs.getInt("bd_status"));
				bVO.setBdOrdStatus(rs.getInt("bd_ord_status"));
				bVO.setPmtStatus(rs.getInt("pmt_status"));
				bVO.setBdZip(rs.getInt("bd_zip"));
				bVO.setBdName(rs.getString("bd_name"));
				bVO.setBdPhone(rs.getString("bd_phone"));
				bVO.setBdAddr(rs.getString("bd_addr"));

				list.add(bVO);

			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Driver loading failure:" + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("Database error occured:" + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		System.out.println(list);
		return list;
	}

	public static void main(String[] args) {
		BiddingDAO_JDBC dao = new BiddingDAO_JDBC();
		BiddingVO bVO = new BiddingVO();
		Timestamp t = new Timestamp(0);

		bVO.setBdNo("B000012");
		bVO.setMemId("M000001");
		bVO.setBdProdNo(3001);
		bVO.setBdPrice(2500);
		bVO.setBdDateStr(t);
		bVO.setBdDateEnd(t);
		bVO.setBdStatus(1);
		bVO.setBdOrdStatus(1);
		bVO.setPmtStatus(1);
		bVO.setBdZip(92347);
		bVO.setBdName("�s�P��");
		bVO.setBdPhone("0933567587");
		bVO.setBdAddr("bdAddr");


		// =======================insert================
//		dao.insert(bVO);

		// ======================update==================
//		dao.update(bVO);

		// =====================getOne==============
//		dao.getOne(bVO.getBdNo());

		// ==================getAll====================
//		dao.getAll();

	}
}
