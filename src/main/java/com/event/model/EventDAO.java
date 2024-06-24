package com.event.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EventDAO  implements EventDAO_interface{
	private static DataSource ds=null;
	private static final String INSERT=
			"INSERT INTO EVENT(event_no,event_name,event_start,event_end,event_ul_start,event_ul_end,event_vote_start,event_vote_end,event_stat)"
			+ "VALUES('E'||LPAD(to_char(event_seq.nextval),6,'0'),?,?,?,?,?,?,?,?)";
	private static final String DELETE=
			"DELETE FROM EVENT WHERE EVENT_NO=?";
	private static final String UPDATE=
			"UPDATE EVENT SET event_name=?,event_start=?,event_end=?,event_ul_start=?,event_ul_end=?,event_vote_start=?,event_vote_end=?,event_stat=?"
			+ "WHERE event_no=?";
	private static final String FINDALLEVENT=
			"SELECT * FROM EVENT ORDER BY EVENT_NO";
	private static final String FINDBYEVENTNO=
			"SELECT * FROM EVENT WHERE EVENT_NO=?";
	private static final String FINDWITHOUTEND="SELECT * FROM EVENT WHERE EVENT_STAT!=3";
	private static final String FINDLASTENDEVENT="select event_no from(select event_no from event where event_stat=3  order by event_no desc)where rownum<=1";
	
	static{
		try {
			Context ctx=new javax.naming.InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/EA103G1");//java.sql.DataSource，使用方法再取連線
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	@Override
	public String insert(EventVO eventVO) {
		Connection con=null;
		PreparedStatement pstmt=null;
		String event_no="";
		String[] colname= {"event_no"};//auto generate key
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(INSERT, colname);
			pstmt.setString(1,eventVO.getEvent_name());			
			pstmt.setTimestamp(2, eventVO.getEvent_start());
			pstmt.setTimestamp(3, eventVO.getEvent_end());
			pstmt.setTimestamp(4, eventVO.getEvent_ul_start());
			pstmt.setTimestamp(5, eventVO.getEvent_ul_end());
			pstmt.setTimestamp(6, eventVO.getEvent_vote_start());
			pstmt.setTimestamp(7, eventVO.getEvent_vote_end());
			pstmt.setInt(8,eventVO.getEvent_stat());
			pstmt.executeUpdate();
			
			rs= pstmt.getGeneratedKeys();
			
			while(rs.next()) {
				event_no=rs.getString(1);
				System.out.println("取得剛產生的event_no準備回傳"+rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
		return event_no;
	}
	@Override
	public void delete(String event_no) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(DELETE);
			pstmt.setString(1, event_no);
			pstmt.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
	public void update(EventVO eventVO) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con =ds.getConnection();
			pstmt=con.prepareStatement(UPDATE);
			pstmt.setString(1, eventVO.getEvent_name());
			pstmt.setTimestamp(2, eventVO.getEvent_start());
			pstmt.setTimestamp(3, eventVO.getEvent_end());
			pstmt.setTimestamp(4, eventVO.getEvent_ul_start());
			pstmt.setTimestamp(5, eventVO.getEvent_ul_end());
			pstmt.setTimestamp(6, eventVO.getEvent_vote_start());
			pstmt.setTimestamp(7, eventVO.getEvent_vote_end());
			pstmt.setInt(8, eventVO.getEvent_stat());
			pstmt.setString(9, eventVO.getEvent_no());//當作修改條件
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
	public List<EventVO> findAllEvent() {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		List<EventVO> eventVOs=new ArrayList<EventVO>();
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDALLEVENT);
			rs =pstmt.executeQuery();
			
			while(rs.next()) {
				EventVO eventVO=new EventVO();
				eventVO.setEvent_no(rs.getString(1));
				eventVO.setEvent_name(rs.getString(2));
				eventVO.setEvent_start(rs.getTimestamp(3));
				eventVO.setEvent_end(rs.getTimestamp(4));
				eventVO.setEvent_ul_start(rs.getTimestamp(5));
				eventVO.setEvent_ul_end(rs.getTimestamp(6));
				eventVO.setEvent_vote_start(rs.getTimestamp(7));
				eventVO.setEvent_vote_end(rs.getTimestamp(8));
				eventVO.setEvent_stat(rs.getInt(9));
				eventVOs.add(eventVO);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
		
		return eventVOs;
	}
	@Override
	public EventVO findByPrimaryKey(String event_no) {
		Connection con=null;
		PreparedStatement pstmt=null;
		EventVO eventVO=null;
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDBYEVENTNO);
			pstmt.setString(1, event_no);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				eventVO= new EventVO();
				eventVO.setEvent_no(rs.getString(1));
				eventVO.setEvent_name(rs.getString(2));
				eventVO.setEvent_start(rs.getTimestamp(3));
				eventVO.setEvent_end(rs.getTimestamp(4));
				eventVO.setEvent_ul_start(rs.getTimestamp(5));
				eventVO.setEvent_ul_end(rs.getTimestamp(6));
				eventVO.setEvent_vote_start(rs.getTimestamp(7));
				eventVO.setEvent_vote_end(rs.getTimestamp(8));
				eventVO.setEvent_stat(rs.getInt(9));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
		return eventVO;
	}
	
	public List<EventVO> findWithoutEnd(){
		Connection con=null;
		EventVO eventVO=null;
		List<EventVO> eventVOs=new ArrayList<EventVO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(FINDWITHOUTEND);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				eventVO=new EventVO();
				eventVO.setEvent_no(rs.getString(1));
				eventVO.setEvent_name(rs.getString(2));
				eventVO.setEvent_start(rs.getTimestamp(3));
				eventVO.setEvent_end(rs.getTimestamp(4));
				eventVO.setEvent_ul_start(rs.getTimestamp(5));
				eventVO.setEvent_ul_end(rs.getTimestamp(6));
				eventVO.setEvent_vote_start(rs.getTimestamp(7));
				eventVO.setEvent_vote_end(rs.getTimestamp(8));
				eventVO.setEvent_stat(rs.getInt(9));
				eventVOs.add(eventVO);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			
			
		}finally {
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
		return eventVOs;
	}
	
	@Override
	public String findLastEndEvent() {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		String lastEndEventNo="";
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDLASTENDEVENT);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				lastEndEventNo=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
		return lastEndEventNo;
	}

	

}
