package com.event_tag.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Event_TagDAO implements Event_TagDAO_interface {
	private static DataSource ds;

	private static final String INSERT = "INSERT INTO EVENT_TAG(EVENT_TAG_NO,MEM_ID,EVENT_NO,EVENT_TAG_TIME,EVENT_TAG_NAME) "
			+ "VALUES(EVENT_TAG_SEQ.NEXTVAL,?,?,?,?)";
	private static final String UPDATE = "UPDATE EVENT_TAG SET MEM_ID=?,EVENT_NO=?,EVENT_TAG_TIME=?,EVENT_TAG_NAME=? WHERE EVENT_TAG_NO=?";
	private static final String FINDALLTAG = "SELECT* FROM EVENT_TAG";
	private static final String FINDONEBYPRIMARYKEY = "SELECT* FROM EVENT_TAG WHERE EVENT_TAG_NO=?";
	private static final String FINDALLMEM = "SELECT DISTINCT MEM_ID FROM EVENT_TAG";
	private static final String FINDALLEVENTNO = "SELECT DISTINCT EVENT_NO FROM EVENT_TAG";//找有被選過主題的hashTag
	private static final String RANDTAG="SELECT A.*FROM(SELECT*FROM EVENT_TAG ORDER BY DBMS_RANDOM.VALUE)A WHERE ROWNUM<=3";
	private static final String RANDTAGBYNOEVENTNO="SELECT DISTINCT event_tag_name FROM(SELECT*FROM EVENT_TAG ORDER BY DBMS_RANDOM.VALUE)A WHERE  ROWNUM<=3 and event_no is null";//取出尚未被選的Tag
	private static final String FINDALLBYTAGNAME="select *from event_tag where event_tag_name=?";
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G1");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Event_TagVO event_tagVO) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con = ds.getConnection();
			pstmt= con.prepareStatement(INSERT);
			pstmt.setString(1, event_tagVO.getMem_id());
			pstmt.setString(2, event_tagVO.getEvent_no());
			pstmt.setTimestamp(3, event_tagVO.getEvent_tag_time());
			pstmt.setString(4, event_tagVO.getEvent_tag_name());
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
	public void update(Event_TagVO event_tagVO) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, event_tagVO.getMem_id());
			pstmt.setString(2, event_tagVO.getEvent_no());
			pstmt.setTimestamp(3, event_tagVO.getEvent_tag_time());
			pstmt.setString(4, event_tagVO.getEvent_tag_name());
			pstmt.setInt(5, event_tagVO.getEvent_tag_no());
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
	public Event_TagVO findByPrimaryKey(Integer event_tag_no) {
		// TODO Auto-generated method stub
		Connection con=null;
		Event_TagVO event_tagVO = new Event_TagVO();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDONEBYPRIMARYKEY);
			pstmt.setInt(1, event_tag_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				event_tagVO.setEvent_tag_no(rs.getInt(1));
				event_tagVO.setMem_id(rs.getString(2));
				event_tagVO.setEvent_no(rs.getString(3));
				event_tagVO.setEvent_tag_time(rs.getTimestamp(4));
				event_tagVO.setEvent_tag_name(rs.getString(5));
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

		return event_tagVO;
	}

	@Override
	public List<Event_TagVO> listAllTag() {
		// TODO Auto-generated method stub
		Connection con=null;
		List<Event_TagVO> event_tagVOs = new ArrayList<Event_TagVO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDALLTAG);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Event_TagVO event_tagVO = new Event_TagVO();
				event_tagVO.setEvent_tag_no(rs.getInt(1));
				event_tagVO.setMem_id(rs.getString(2));
				event_tagVO.setEvent_no(rs.getString(3));
				event_tagVO.setEvent_tag_time(rs.getTimestamp(4));
				event_tagVO.setEvent_tag_name(rs.getString(5));
				event_tagVOs.add(event_tagVO);
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

		return event_tagVOs;

	}

	@Override
	public List<String> findAllMem() {
		// TODO Auto-generated method stub
		Connection con=null;
		List<String> memIds = new ArrayList<String>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDALLMEM);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				memIds.add(rs.getString(1));
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

		return memIds;
	}

	@Override
	public List<String> findAllEventNo() {
		// TODO Auto-generated method stub
		Connection con=null;
		List<String> eventNos = new ArrayList<String>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDALLEVENTNO);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getString(1) != null) {
					eventNos.add(rs.getString(1));
				}
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

		return eventNos;
	}

	@Override
	public List<Event_TagVO> randomTag() {
		// TODO Auto-generated method stub
		Connection con=null;
		List<Event_TagVO> event_tagVOs=new ArrayList<Event_TagVO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(RANDTAGBYNOEVENTNO);//取出尚未被選的Tag
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Event_TagVO event_tagVO=new Event_TagVO();
				event_tagVO.setEvent_tag_name(rs.getString(1));
				
				event_tagVOs.add(event_tagVO);
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
		
		return event_tagVOs;
	}

	@Override
	public List<Event_TagVO> findAllByTagName(String event_tag_name) {
		// TODO Auto-generated method stub
		Connection con=null;
		Event_TagVO tagVO=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Event_TagVO> tagVOs=new LinkedList<Event_TagVO>();
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDALLBYTAGNAME);
			pstmt.setString(1,event_tag_name);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				tagVO=new Event_TagVO();
				tagVO.setEvent_tag_no(rs.getInt(1));
				tagVO.setMem_id(rs.getString(2));
				tagVO.setEvent_no(rs.getString(3));
				tagVO.setEvent_tag_time(rs.getTimestamp(4));
				tagVO.setEvent_tag_name(rs.getString(5));
				tagVOs.add(tagVO);
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
		
		return tagVOs;
	}

}
