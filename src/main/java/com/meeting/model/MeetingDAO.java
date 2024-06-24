package com.meeting.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.prod.model.ProdVO;


public class MeetingDAO implements MeetingDAO_interface {

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

			con = ds.getConnection();
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

			con = ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mt_no);

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
	public MeetingVO findByPrimaryKey(String mt_no) {

		MeetingVO meetingVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
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

		con = ds.getConnection();
		pstmt = con.prepareStatement(CANCEL_STMT);
		pstmt.setInt(1, meetingVO.getMt_status());
		pstmt.setString(2, meetingVO.getMt_no());		

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
	public List<MeetingVO> getMem_Meeting(String mem_id) {
		List<MeetingVO> list = new ArrayList<MeetingVO>();
		MeetingVO meetingVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_FUZZY_QUERY);
					pstmt.setString(1, "%"+mt_id+"%");
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
}



		
		
		
		
		
		
		