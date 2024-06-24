package com.event_p.model;

import java.sql.Blob;
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

import com.event_p.model.*;
import com.vote_d.model.*;

public class Event_PDAO implements Event_PDAO_interface {
	private static DataSource ds = null;
	private static final String INSERT = "INSERT INTO EVENT_P VALUES(EVENT_P_SEQ.nextval,?,?,?,?,?,?,?,?)";
	private static final String GETALLEVENTP = "SELECT *FROM EVENT_P ";
	private static final String FINDBYPRIMARYKEY = "SELECT *FROM EVENT_P WHERE EVENT_P_NO=?";
	private static final String UPDATE = "UPDATE EVENT_P SET MEM_ID=?,EVENT_NO=?,EVENT_P_NAME=?,EVENT_P_DATE=?,EVENT_VOTE_NUM=?,VOTE_RANK=?,EVENT_P_STAT=?,EVENT_p_IMG=? WHERE EVENT_P_NO=?";
	private static final String FINDALLMEM = "SELECT DISTINCT MEM_ID FROM EVENT_P";
	private static final String FINDALLEVENTNO = "SELECT DISTINCT EVENT_NO FROM EVENT ORDER BY EVENT_NO DESC";//ノHOME PAGE程穝程侣陪ボ
	private static final String VOTEPIC = "UPDATE EVENT_P SET EVENT_VOTE_NUM=EVENT_VOTE_NUM+1 WHERE EVENT_P_NO=?";
	private static final String FINDTOPBYEVENTNOWITHOUTREPORT = "select *from event_p \r\n" + 
			"where event_vote_num=\r\n" + 
			"(\r\n" + 
			"select max(event_vote_num) from\r\n" + 
			"(SELECT * FROM EVENT_P  \r\n" + 
			"WHERE EVENT_NO=? \r\n" + 
			"order by event_vote_num desc) A \r\n" + 
			"left join\r\n" + 
			"(select *from event_p_rep where event_p_rep.rep_stat=2) B\r\n" + 
			"on A.event_p_no=B.event_p_no\r\n" + 
			"where  B.event_p_no is null \r\n" + 
			") ";
	private static final String FINDALLBYEVENTNO = "SELECT* FROM EVENT_P WHERE EVENT_NO=?";
	private static final String FINDALLBYEVENTNORANKDESCWITHOUTREPORT="SELECT*FROM EVENT_P A\r\n" + 
			"left join\r\n" + 
			"(select *from event_p_rep where event_p_rep.rep_stat=2) B\r\n" + 
			"on A.event_p_no=B.event_p_no\r\n" + 
			"WHERE EVENT_NO=? and B.event_p_no is null \r\n" + 
			"ORDER BY A.VOTE_RANK ASC";
	private static final String CHECKUPLOADBYMEMID = "select*from event_p where mem_id=? AND EVENT_NO=?";
	private static final String FINDUPLOADBYMEMID = "select*from event_p where mem_id=? and event_no=?";
	private static final String DELETE = "DELETE FROM EVENT_P WHERE EVENT_P_NO=?";
	private static final String DELETEVOTE="UPDATE EVENT_P SET EVENT_VOTE_NUM=EVENT_VOTE_NUM-1 WHERE EVENT_P_NO=?";
	private static final String BIGRANKSORT="select  A.event_p_no,rank() over(order by event_vote_num desc) as rank  \r\n" + 
			"from event_p   A\r\n" + 
			"left join\r\n" + 
			"(select *from event_p_rep where event_p_rep.rep_stat=2) B\r\n" + 
			"on A.event_p_no=B.event_p_no\r\n" + 
			"where event_no=? and ROWNUM<=? and B.event_p_no is null";
	private static final String BIGRANKUPDATE="UPDATE EVENT_P SET VOTE_RANK=? WHERE EVENT_P_NO=?";
	private static final String FINDALLNOREPORT="select p.*" + 
			"from event_p p " + 
			"where p.event_p_stat=1  and p.event_no=?";
	private static final String FINDFIVEPICBYEVENTNO="select*from event_p where event_no=? and event_p_stat=1 and rownum<=5";

	//by 风纒
	private static final String GET_ALL_STMT = "SELECT * FROM EVENT_P WHERE EVENT_NO = ? and vote_rank between 1 and 10 and EVENT_P_STAT != 2 order by vote_rank";

	
	
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
	public void insert(Event_PVO pVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			pstmt.setString(1, pVO.getMem_id());// 穦ID眔
			pstmt.setString(2, pVO.getEvent_no());// 笆瑈腹
			pstmt.setString(3, pVO.getEvent_p_name());// 珇嘿>ㄏノ块(眖呼ъ)

			pstmt.setTimestamp(4, pVO.getEvent_p_date());
			pstmt.setInt(5, pVO.getEvent_vote_num());// 眔щ布计
			pstmt.setInt(6, pVO.getVote_rank());// パщ布计眔逼
			pstmt.setInt(7, pVO.getEvent_p_stat());// щ布
			pstmt.setBytes(8, pVO.getEvent_p_img());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public List<Event_PVO> findAllPic() {
		Connection con = null;
		List<Event_PVO> pVOs = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALLEVENTP);
			rs = pstmt.executeQuery();
			pVOs = new ArrayList<Event_PVO>(); // store in list
			while (rs.next()) {
				Event_PVO pVO = new Event_PVO();
				pVO.setEvent_p_no(rs.getInt(1));
				pVO.setMem_id(rs.getString(2));
				pVO.setEvent_no(rs.getString(3));
				pVO.setEvent_p_name(rs.getString(4));
				pVO.setEvent_p_date(rs.getTimestamp(5));
				pVO.setEvent_vote_num(rs.getInt(6));
				pVO.setVote_rank(rs.getInt(7));
				pVO.setEvent_p_stat(rs.getInt(8));
				pVO.setEvent_p_img(rs.getBytes(9));
				pVOs.add(pVO);
			}

		} catch (SQLException e) {

			e.printStackTrace();
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

		return pVOs;
	}

	public Event_PVO findByPrimaryKey(Integer event_p_no) {
		Connection con = null;
		Event_PVO event_pVO = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDBYPRIMARYKEY);
			pstmt.setInt(1, event_p_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				event_pVO = new Event_PVO();
				event_pVO.setEvent_p_no(rs.getInt(1));
				event_pVO.setMem_id(rs.getString(2));
				event_pVO.setEvent_no(rs.getString(3));
				event_pVO.setEvent_p_name(rs.getString(4));
				event_pVO.setEvent_p_date(rs.getTimestamp(5));
				event_pVO.setEvent_vote_num(rs.getInt(6));
				event_pVO.setVote_rank(rs.getInt(7));
				event_pVO.setEvent_p_stat(rs.getInt(8));
				event_pVO.setEvent_p_img(rs.getBytes(9));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		return event_pVO;
	}

	public void update(Event_PVO pVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, pVO.getMem_id());
			pstmt.setString(2, pVO.getEvent_no());
			pstmt.setString(3, pVO.getEvent_p_name());
			pstmt.setTimestamp(4, pVO.getEvent_p_date());
			pstmt.setInt(5, pVO.getEvent_vote_num());
			pstmt.setInt(6, pVO.getVote_rank());
			pstmt.setInt(7, pVO.getEvent_p_stat());
			pstmt.setBytes(8, pVO.getEvent_p_img());
			pstmt.setInt(9, pVO.getEvent_p_no());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public List<String> findAllMem() {
		// TODO Auto-generated method stub
		Connection con = null;
		List<String> memIds = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDALLMEM);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				memIds.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return memIds;
	}

	@Override
	public List<String> findAllEventNo() {
		// TODO Auto-generated method stub
		Connection con = null;
		List<String> eventNos = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDALLEVENTNO);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eventNos.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return eventNos;
	}

	@Override
	public void votePic(Integer event_p_no,String mem_id) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			
			con=ds.getConnection();
			
			con.setAutoCommit(false);//も笆commit
			pstmt=con.prepareStatement(VOTEPIC);
			pstmt.setInt(1, event_p_no);
			pstmt.execute();
			
			Vote_DDAO votedao=new Vote_DDAO();
			Vote_DVO vote_d=new Vote_DVO();
			vote_d.setMem_id(mem_id);
			vote_d.setEvent_p_no(event_p_no);
			votedao.insert(vote_d,con);
			
			con.setAutoCommit(true);
			con.commit();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getMessage();
			try {
				con.rollback();//篗щ布笆
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
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
	public Event_PVO findTopEventPNoByEventNoWithoutReport(String event_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		Event_PVO event_pVO = new Event_PVO();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDTOPBYEVENTNOWITHOUTREPORT);
			pstmt.setString(1, event_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				event_pVO.setEvent_p_no(rs.getInt(1));
				event_pVO.setMem_id(rs.getString(2));
				event_pVO.setEvent_no(rs.getString(3));
				event_pVO.setEvent_p_name(rs.getString(4));
				event_pVO.setEvent_p_date(rs.getTimestamp(5));
				event_pVO.setEvent_vote_num(rs.getInt(6));
				event_pVO.setVote_rank(rs.getInt(7));
				event_pVO.setEvent_p_stat(rs.getInt(8));
				event_pVO.setEvent_p_img(rs.getBytes(9));
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

		return event_pVO;
	}

	@Override
	public List<Event_PVO> findAllByEventNo(String event_no) {
		
		// TODO Auto-generated method stub
		Connection con = null;
		Event_PVO event_pVO = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		List<Event_PVO> event_pVOs = new ArrayList<Event_PVO>();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDALLBYEVENTNO);
			pstmt.setString(1, event_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				event_pVO = new Event_PVO();
				event_pVO.setEvent_p_no(rs.getInt(1));
				event_pVO.setMem_id(rs.getString(2));
				event_pVO.setEvent_no(rs.getString(3));
				event_pVO.setEvent_p_name(rs.getString(4));
				event_pVO.setEvent_p_date(rs.getTimestamp(5));
				event_pVO.setEvent_vote_num(rs.getInt(6));
				event_pVO.setVote_rank(rs.getInt(7));
				event_pVO.setEvent_p_stat(rs.getInt(8));
				event_pVO.setEvent_p_img(rs.getBytes(9));
				event_pVOs.add(event_pVO);
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
		return event_pVOs;
	}

	@Override
	public boolean checkUploadByMemid(String mem_id,String event_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECKUPLOADBYMEMID);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, event_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				return true;
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
		return false;
	}

	@Override
	public Event_PVO findUploadByMemid(String mem_id,String event_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		Event_PVO event_pVO = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDUPLOADBYMEMID);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, event_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				event_pVO = new Event_PVO();
				event_pVO.setEvent_p_no(rs.getInt(1));
				event_pVO.setMem_id(rs.getString(2));
				event_pVO.setEvent_no(rs.getString(3));
				event_pVO.setEvent_p_name(rs.getString(4));
				event_pVO.setEvent_p_date(rs.getTimestamp(5));
				event_pVO.setEvent_vote_num(rs.getInt(6));
				event_pVO.setVote_rank(rs.getInt(7));
				event_pVO.setEvent_p_stat(rs.getInt(8));
				event_pVO.setEvent_p_img(rs.getBytes(9));

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

		return event_pVO;
	}

	@Override
	public void delete(Integer event_p_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt=null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, event_p_no);
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
	public void deleteVote(Integer event_p_no) {
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(DELETEVOTE);
			pstmt.setInt(1, event_p_no);
			System.out.println("ready to delete vote");
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
	public List<Event_PVO> bigRankSort(String event_no, Integer rownum) {
		// TODO Auto-generated method stub
		System.out.println("--------------------------------");
		System.out.println("event no :"+event_no);
		Connection con=null;
		PreparedStatement pstmt=null;
		List<Event_PVO> event_pVOs=new ArrayList<Event_PVO>();
		ResultSet rs = null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(BIGRANKSORT);
			pstmt.setString(1, event_no);
			pstmt.setInt(2, rownum);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				System.out.println("event_p_no:"+rs.getInt(1));
				Event_PVO event_pVO=new Event_PVO();
				event_pVO.setEvent_p_no(rs.getInt(1));
				event_pVO.setVote_rank(rs.getInt(2));
				event_pVOs.add(event_pVO);
				
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
		
		return event_pVOs;
	}

	@Override
	public void bigRankUpdate(Integer vote_rank, Integer event_p_no) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(BIGRANKUPDATE);
			pstmt.setInt(1, vote_rank);
			pstmt.setInt(2, event_p_no);
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
	public List<Event_PVO> findAllByEventNoRankDescWithoutReport(String event_no) {
		// TODO Auto-generated method stub
		Connection con=null;
		
		List<Event_PVO> event_pVOs=new ArrayList<Event_PVO>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDALLBYEVENTNORANKDESCWITHOUTREPORT);
			pstmt.setString(1, event_no);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Event_PVO event_pVO= new Event_PVO();
				event_pVO.setEvent_p_no(rs.getInt(1));
				event_pVO.setMem_id(rs.getString(2));
				event_pVO.setEvent_no(rs.getString(3));
				event_pVO.setEvent_p_name(rs.getString(4));
				event_pVO.setEvent_p_date(rs.getTimestamp(5));
				event_pVO.setEvent_vote_num(rs.getInt(6));
				event_pVO.setVote_rank(rs.getInt(7));
				event_pVO.setEvent_p_stat(rs.getInt(8));
				event_pVO.setEvent_p_img(rs.getBytes(9));
				event_pVOs.add(event_pVO);
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
		return event_pVOs;
	}

	@Override
	public List<Event_PVO> findAllNoReport(String event_no) {
		// TODO Auto-generated method stub
		Connection con=null;
		List<Event_PVO> event_pVOs=new ArrayList<Event_PVO>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDALLNOREPORT);
			pstmt.setString(1, event_no);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Event_PVO event_pVO=new Event_PVO();
				event_pVO.setEvent_p_no(rs.getInt(1));
				event_pVO.setMem_id(rs.getString(2));
				event_pVO.setEvent_no(rs.getString(3));
				event_pVO.setEvent_p_name(rs.getString(4));
				event_pVO.setEvent_p_date(rs.getTimestamp(5));
				event_pVO.setEvent_vote_num(rs.getInt(6));
				event_pVO.setVote_rank(rs.getInt(7));
				event_pVO.setEvent_p_stat(rs.getInt(8));
				event_pVO.setEvent_p_img(rs.getBytes(9));
				 event_pVOs.add(event_pVO);
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
		
		return event_pVOs;
	}

	@Override
	public List<Event_PVO> findAllOrderBySelect(String event_no,String colName) {
		// TODO Auto-generated method stub
		Connection con=null;
		String[] order=colName.split("With");
		String sql=FINDALLNOREPORT+" order by p."+order[0]+" "+order[1];//use findAllNoReport to compose input parameter
		List<Event_PVO>event_pVOs=new ArrayList<Event_PVO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			//use input parameter to compose sql
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, event_no);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Event_PVO event_pVO=new Event_PVO();
				event_pVO.setEvent_p_no(rs.getInt(1));
				event_pVO.setMem_id(rs.getString(2));
				event_pVO.setEvent_no(rs.getString(3));
				event_pVO.setEvent_p_name(rs.getString(4));
				event_pVO.setEvent_p_date(rs.getTimestamp(5));
				event_pVO.setEvent_vote_num(rs.getInt(6));
				event_pVO.setVote_rank(rs.getInt(7));
				event_pVO.setEvent_p_stat(rs.getInt(8));
				event_pVO.setEvent_p_img(rs.getBytes(9));
				event_pVOs.add(event_pVO);
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
		return event_pVOs;
	}

	@Override
	public List<Event_PVO> find5PicByEventNo(String event_no) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		List<Event_PVO> event_pVO5s=new ArrayList<Event_PVO>();
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDFIVEPICBYEVENTNO);
			pstmt.setString(1, event_no);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Event_PVO pVO=new Event_PVO();
				pVO.setEvent_p_no(rs.getInt(1));
				pVO.setMem_id(rs.getString(2));
				pVO.setEvent_no(rs.getString(3));
				pVO.setEvent_p_name(rs.getString(4));
				pVO.setEvent_p_date(rs.getTimestamp(5));
				pVO.setEvent_vote_num(rs.getInt(6));
				pVO.setVote_rank(rs.getInt(7));
				pVO.setEvent_p_stat(rs.getInt(8));
				pVO.setEvent_p_img(rs.getBytes(9));
				event_pVO5s.add(pVO);
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
		
		return event_pVO5s;
	}

	//==风纒=========================================================
	@Override
	public List<Event_PVO> getAll(String event_no) {
			
		List<Event_PVO> list = new ArrayList<Event_PVO>();
		Event_PVO eventpVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			pstmt.setString(1, event_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				eventpVO = new Event_PVO();
				eventpVO.setEvent_p_no(rs.getInt("event_p_no"));
				eventpVO.setMem_id(rs.getString("mem_id"));
				eventpVO.setEvent_no(rs.getString("event_no"));
				eventpVO.setEvent_p_name(rs.getString("event_p_name"));
				eventpVO.setEvent_p_date(rs.getTimestamp("event_p_date"));
				eventpVO.setEvent_vote_num(rs.getInt("event_vote_num"));
				eventpVO.setVote_rank(rs.getInt("vote_rank"));
				eventpVO.setEvent_p_stat(rs.getInt("event_p_stat"));
				eventpVO.setEvent_p_img(rs.getBytes("event_p_img"));
				
				list.add(eventpVO);
				
			}

		} catch (SQLException e) {
			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		System.out.println(list.size());
		return list;
	}

}
