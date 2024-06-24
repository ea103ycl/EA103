package com.meeting.model;

import java.util.*;
import java.sql.*;

public class MeetingJDBCDAO implements MeetingDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";

	private static final String INSERT_STMT = 
			"INSERT INTO meeting (mt_no,mem_id,mt_status,max_num,min_num,mt_place,ri_fee,mt_detail,mt_start_time,mt_end_time,mt_time,mt_id,mt_num,mt_pic) VALUES ('MT'||LPAD(meeting_seq.nextval, 5, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		private static final String GET_ALL_STMT = 
				"SELECT mt_no,mem_id,mt_status,max_num,min_num,mt_place,ri_fee,mt_detail,mt_start_time,mt_end_time,mt_time,mt_id,mt_num,mt_pic FROM meeting WHERE MT_STATUS = 0 order by mt_no desc";
		
		private static final String GET_ONE_STMT = 
			"SELECT mt_no,mem_id,mt_status,max_num,min_num,mt_place,ri_fee,mt_detail,mt_start_time,mt_end_time,mt_time,mt_id,mt_num,mt_pic FROM meeting where mt_no = ?";
		private static final String DELETE = 
			"DELETE FROM meeting where mt_no = ?";
		private static final String UPDATE = 
			"UPDATE meeting set mem_id=?,max_num=?,min_num=?,mt_place=?,ri_fee=?,mt_detail=?,mt_start_time=?,mt_end_time=?,mt_time=?,mt_id=?,mt_pic=? where mt_no = ?";

		private static final String CANCEL_STMT = 
				"UPDATE meeting set mt_status=? where mt_no = ?";
		private static final String GET_MEM_STMT = 
				"SELECT mt_no,mem_id,mt_status,max_num,min_num,mt_place,ri_fee,mt_detail,mt_start_time,mt_end_time,mt_time,mt_id,mt_num,mt_pic FROM meeting where mem_id = ? order by mt_time desc";
	
		private static final String CANCEL_MEETING_SEARCH = "SELECT * FROM meeting WHERE MT_STATUS = 2 ORDER BY MT_NUM desc";
		
		private static final String GET_FUZZY_QUERY = "SELECT * FROM MEETING WHERE mt_id LIKE ? AND MT_STATUS = 0";

		
		@Override
	public void insert(MeetingVO meetingVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, meetingVO.getMem_id());
			pstmt.setInt(2, meetingVO.getMt_status());
			pstmt.setInt(3, meetingVO.getMax_num());
			pstmt.setInt(4, meetingVO.getMin_num());
			pstmt.setString(5, meetingVO.getMt_place());
			pstmt.setInt(6, meetingVO.getRi_fee());
			pstmt.setString(7, meetingVO.getMt_detail());
			pstmt.setTimestamp(8, meetingVO.getMt_start_time());
			pstmt.setTimestamp(9, meetingVO.getMt_end_time());
			pstmt.setTimestamp(10, meetingVO.getMt_time());
			pstmt.setString(11, meetingVO.getMt_id());
			pstmt.setInt(12, meetingVO.getMt_num());
			pstmt.setBytes(13, meetingVO.getMt_pic());


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
	public void update(MeetingVO meetingVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1, meetingVO.getMem_id());
			pstmt.setInt(2, meetingVO.getMax_num());
			pstmt.setInt(3, meetingVO.getMin_num());
			pstmt.setString(4, meetingVO.getMt_place());
			pstmt.setInt(5, meetingVO.getRi_fee());
			pstmt.setString(6, meetingVO.getMt_detail());
			pstmt.setTimestamp(7, meetingVO.getMt_start_time());
			pstmt.setTimestamp(8, meetingVO.getMt_end_time());
			pstmt.setTimestamp(9, meetingVO.getMt_time());
			pstmt.setString(10, meetingVO.getMt_id());
			pstmt.setBytes(11, meetingVO.getMt_pic());
			pstmt.setString(12, meetingVO.getMt_no());
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
	public void delete(String mt_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mt_no);

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
	public MeetingVO findByPrimaryKey(String mt_no) {

		MeetingVO meetingVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mt_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				meetingVO = new MeetingVO();
				meetingVO.setMt_no(rs.getString("mt_no"));
				meetingVO.setMem_id(rs.getString("mem_id"));
				meetingVO.setMt_status(rs.getInt("mt_status"));
				meetingVO.setMax_num(rs.getInt("max_num"));
				meetingVO.setMin_num(rs.getInt("min_num"));
				meetingVO.setMt_place(rs.getString("mt_place"));
				meetingVO.setRi_fee(rs.getInt("ri_fee"));
				meetingVO.setMt_detail(rs.getString("mt_detail"));
				meetingVO.setMt_start_time(rs.getTimestamp("mt_start_time"));
				meetingVO.setMt_end_time(rs.getTimestamp("mt_end_time"));
				meetingVO.setMt_time(rs.getTimestamp("mt_time"));
				meetingVO.setMt_id(rs.getString("mt_id"));
				meetingVO.setMt_num(rs.getInt("mt_num"));	
				meetingVO.setMt_pic(rs.getBytes("mt_pic"));			

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
		return meetingVO;
	}

	@Override
	public List<MeetingVO> getAll() {
		List<MeetingVO> list = new ArrayList<MeetingVO>();
		MeetingVO meetingVO = null;

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
				meetingVO = new MeetingVO();
				meetingVO.setMt_no(rs.getString("mt_no"));
				meetingVO.setMem_id(rs.getString("mem_id"));
				meetingVO.setMt_status(rs.getInt("mt_status"));
				meetingVO.setMax_num(rs.getInt("max_num"));
				meetingVO.setMin_num(rs.getInt("min_num"));
				meetingVO.setMt_place(rs.getString("mt_place"));
				meetingVO.setRi_fee(rs.getInt("ri_fee"));
				meetingVO.setMt_detail(rs.getString("mt_detail"));
				meetingVO.setMt_start_time(rs.getTimestamp("mt_start_time"));
				meetingVO.setMt_end_time(rs.getTimestamp("mt_end_time"));
				meetingVO.setMt_time(rs.getTimestamp("mt_time"));
				meetingVO.setMt_id(rs.getString("mt_id"));
				meetingVO.setMt_num(rs.getInt("mt_num"));	
				meetingVO.setMt_pic(rs.getBytes("mt_pic"));	

				list.add(meetingVO); // Store the row in the list
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
	public void cancel(MeetingVO meetingVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(CANCEL_STMT);

			
			pstmt.setString(1, meetingVO.getMt_no());
			pstmt.setInt(2, meetingVO.getMt_status());

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
	public List<MeetingVO> getMem_Meeting(String mem_id) {
		List<MeetingVO> list = new ArrayList<MeetingVO>();
		MeetingVO meetingVO = null;

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
				meetingVO = new MeetingVO();
				meetingVO.setMt_no(rs.getString("mt_no"));
				meetingVO.setMem_id(rs.getString("mem_id"));
				meetingVO.setMt_status(rs.getInt("mt_status"));
				meetingVO.setMax_num(rs.getInt("max_num"));
				meetingVO.setMin_num(rs.getInt("min_num"));
				meetingVO.setMt_place(rs.getString("mt_place"));
				meetingVO.setRi_fee(rs.getInt("ri_fee"));
				meetingVO.setMt_detail(rs.getString("mt_detail"));
				meetingVO.setMt_start_time(rs.getTimestamp("mt_start_time"));
				meetingVO.setMt_end_time(rs.getTimestamp("mt_end_time"));
				meetingVO.setMt_time(rs.getTimestamp("mt_time"));
				meetingVO.setMt_id(rs.getString("mt_id"));
				meetingVO.setMt_num(rs.getInt("mt_num"));	
				meetingVO.setMt_pic(rs.getBytes("mt_pic"));			
				list.add(meetingVO); // Store the row in the list
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
	public List<MeetingVO> getMt_Meeting(String mt_no) {
		List<MeetingVO> list = new ArrayList<MeetingVO>();
		MeetingVO meetingVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mt_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				meetingVO = new MeetingVO();
				meetingVO.setMt_no(rs.getString("mt_no"));
				meetingVO.setMem_id(rs.getString("mem_id"));
				meetingVO.setMt_status(rs.getInt("mt_status"));
				meetingVO.setMax_num(rs.getInt("max_num"));
				meetingVO.setMin_num(rs.getInt("min_num"));
				meetingVO.setMt_place(rs.getString("mt_place"));
				meetingVO.setRi_fee(rs.getInt("ri_fee"));
				meetingVO.setMt_detail(rs.getString("mt_detail"));
				meetingVO.setMt_start_time(rs.getTimestamp("mt_start_time"));
				meetingVO.setMt_end_time(rs.getTimestamp("mt_end_time"));
				meetingVO.setMt_time(rs.getTimestamp("mt_time"));
				meetingVO.setMt_id(rs.getString("mt_id"));
				meetingVO.setMt_num(rs.getInt("mt_num"));	
				meetingVO.setMt_pic(rs.getBytes("mt_pic"));			
				list.add(meetingVO); // Store the row in the list
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
public List<MeetingVO> getCancel_Meeting() {
	List<MeetingVO> list = new ArrayList<MeetingVO>();
	MeetingVO meetingVO = null;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
		Class.forName(driver);
		con = DriverManager.getConnection(url, userid, passwd);
		pstmt = con.prepareStatement(CANCEL_MEETING_SEARCH);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			// empVO 也稱為 Domain objects
			meetingVO = new MeetingVO();
			meetingVO.setMt_no(rs.getString("mt_no"));
			meetingVO.setMem_id(rs.getString("mem_id"));
			meetingVO.setMt_status(rs.getInt("mt_status"));
			meetingVO.setMax_num(rs.getInt("max_num"));
			meetingVO.setMin_num(rs.getInt("min_num"));
			meetingVO.setMt_place(rs.getString("mt_place"));
			meetingVO.setRi_fee(rs.getInt("ri_fee"));
			meetingVO.setMt_detail(rs.getString("mt_detail"));
			meetingVO.setMt_start_time(rs.getTimestamp("mt_start_time"));
			meetingVO.setMt_end_time(rs.getTimestamp("mt_end_time"));
			meetingVO.setMt_time(rs.getTimestamp("mt_time"));
			meetingVO.setMt_id(rs.getString("mt_id"));
			meetingVO.setMt_num(rs.getInt("mt_num"));	
			meetingVO.setMt_pic(rs.getBytes("mt_pic"));	
			
			list.add(meetingVO); // Store the row in the list
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
//模糊查詢
@Override
public List<MeetingVO> FuzzySearch(String mt_id) {
	
	List<MeetingVO> list = new ArrayList<MeetingVO>();
	MeetingVO meetingVO = null;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
		Class.forName(driver);
		con = DriverManager.getConnection(url, userid, passwd);
		pstmt = con.prepareStatement(GET_FUZZY_QUERY);
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			meetingVO = new MeetingVO();
			meetingVO.setMt_no(rs.getString("mt_no"));
			meetingVO.setMem_id(rs.getString("mem_id"));
			meetingVO.setMt_status(rs.getInt("mt_status"));
			meetingVO.setMax_num(rs.getInt("max_num"));
			meetingVO.setMin_num(rs.getInt("min_num"));
			meetingVO.setMt_place(rs.getString("mt_place"));
			meetingVO.setRi_fee(rs.getInt("ri_fee"));
			meetingVO.setMt_detail(rs.getString("mt_detail"));
			meetingVO.setMt_start_time(rs.getTimestamp("mt_start_time"));
			meetingVO.setMt_end_time(rs.getTimestamp("mt_end_time"));
			meetingVO.setMt_time(rs.getTimestamp("mt_time"));
			meetingVO.setMt_id(rs.getString("mt_id"));
			meetingVO.setMt_num(rs.getInt("mt_num"));	
			meetingVO.setMt_pic(rs.getBytes("mt_pic"));	
			
			list.add(meetingVO); // Store the row in the list
		
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

	
	
	
	
	
	
	
	

	public static void main(String[] args) {

		MeetingJDBCDAO dao = new MeetingJDBCDAO();

		// 新增
		MeetingVO meetingVO1 = new MeetingVO();
		meetingVO1.setMem_id("M000003");
		meetingVO1.setMt_status(new Integer(1));
		meetingVO1.setMax_num(new Integer(60));
		meetingVO1.setMin_num(new Integer(20));
		meetingVO1.setMt_place("桃園縣");
		meetingVO1.setRi_fee(new Integer(600));
		meetingVO1.setMt_detail("12");
		meetingVO1.setMt_start_time(java.sql.Timestamp.valueOf("2020-12-24 12:26:23"));
		meetingVO1.setMt_end_time(java.sql.Timestamp.valueOf("2020-12-24 12:26:23"));
		meetingVO1.setMt_time(java.sql.Timestamp.valueOf("2020-12-24 12:26:23"));
		meetingVO1.setMt_id("1234567");
		meetingVO1.setMt_num(new Integer(600));
		meetingVO1.setMt_pic(null);

		dao.insert(meetingVO1);

		// 修改
		MeetingVO meetingVO2 = new MeetingVO();
		meetingVO2.setMt_no("MT00008");
		meetingVO2.setMem_id("M000003");
		meetingVO2.setMax_num(new Integer(30));
		meetingVO2.setMin_num(new Integer(10));
		meetingVO2.setMt_place("新竹縣");
		meetingVO2.setRi_fee(new Integer(6000));
		meetingVO2.setMt_detail("123");
		meetingVO2.setMt_start_time(java.sql.Timestamp.valueOf("2020-12-24 12:26:23"));
		meetingVO2.setMt_end_time(java.sql.Timestamp.valueOf("2020-12-24 12:26:23"));
		meetingVO2.setMt_time(java.sql.Timestamp.valueOf("2020-12-24 12:26:23"));
		meetingVO2.setMt_id("1234567");
		meetingVO2.setMt_pic(null);
		
		dao.update(meetingVO2);

		// 刪除
		dao.delete("4");

		// 查詢
		MeetingVO meetingVO3 = dao.findByPrimaryKey("MT00008");
		System.out.print(meetingVO3.getMt_no() + ",");
		System.out.print(meetingVO3.getMem_id() + ",");
		System.out.print(meetingVO3.getMt_status() + ",");
		System.out.print(meetingVO3.getMax_num() + ",");
		System.out.print(meetingVO3.getMin_num() + ",");
		System.out.print(meetingVO3.getMt_place() + ",");
		System.out.println(meetingVO3.getRi_fee());
		System.out.print(meetingVO3.getMt_detail() + ",");
		System.out.print(meetingVO3.getMt_start_time() + ",");
		System.out.print(meetingVO3.getMt_end_time() + ",");
		System.out.print(meetingVO3.getMt_time() + ",");
		System.out.print(meetingVO3.getMt_id() + ",");
		System.out.print(meetingVO3.getMt_num() + ",");
		System.out.print(meetingVO3.getMt_pic() + ",");

		System.out.println("---------------------");

		// 查詢
		List<MeetingVO> list = dao.getAll();
		for (MeetingVO aMeeting : list) {
			System.out.print(aMeeting.getMt_no() + ",");
			System.out.print(aMeeting.getMem_id() + ",");
			System.out.print(aMeeting.getMt_status() + ",");
			System.out.print(aMeeting.getMax_num() + ",");
			System.out.print(aMeeting.getMin_num() + ",");
			System.out.print(aMeeting.getMt_place() + ",");
			System.out.println(aMeeting.getRi_fee());
			System.out.print(aMeeting.getMt_detail() + ",");
			System.out.print(aMeeting.getMt_start_time() + ",");
			System.out.print(aMeeting.getMt_end_time() + ",");
			System.out.print(aMeeting.getMt_time() + ",");
			System.out.print(aMeeting.getMt_id() + ",");
			System.out.print(aMeeting.getMt_num() + ",");
			System.out.print(aMeeting.getMt_pic() + ",");

			System.out.println();
		}
	}
	
			
	
}