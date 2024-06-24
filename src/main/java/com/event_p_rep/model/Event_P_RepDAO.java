package com.event_p_rep.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.*;
import javax.sql.DataSource;

import jdbc.util.CompositeQuery.CompositeQuery_Event_P_Rep;

public class Event_P_RepDAO implements Event_P_RepDAO_interface{
	private static DataSource ds;
	
	private static final String INSERT="INSERT INTO EVENT_P_REP VALUES(?,?,?,?,?)";
	private static final String UPDATE="UPDATE EVENT_P_REP SET REP_STAT=? WHERE EVENT_P_NO=?";
	private static final String FINDALLREP="SELECT* FROM EVENT_P_REP";
	private static final String DELETE="DELETE FROM EVENT_P_REP WHERE EVENT_P_NO=? AND MEM_ID=?";
	private static final String FINDREPBYEVENTPNO="SELECT * FROM EVENT_P_REP WHERE EVENT_P_NO=?";
	private static final String FINDALLBYREPSTAT="select *from event_p_rep where rep_stat=?";
	private static final String FINDBYMEMID="select* from event_p_rep where mem_id=?";
	private static final String FINDREPBYEVENTNO="SELECT rep.* from event_p_rep rep\r\n" + 
			"inner join (SELECT *FROM event_p  ) event_p\r\n" + 
			"ON rep.event_p_no=event_p.event_p_no\r\n" + 
			"WHERE event_no=?";
	private static final String CHECKMEMREPORT="select r.*"
			+ "from event_p_rep r "
			+ "inner join  event_p p "
			+ "on r.event_p_no=p.event_p_no "
			+ "where  r.mem_id=? and p.event_no=?";
	private static final String FINDREPORTBYMEMANDEVENTNO="select r.*from event_p_rep r \r\n" + 
			"inner join(select*from event_p where event_no=?) p \r\n" + 
			"on r.event_p_no=p.event_p_no \r\n" + 
			"where r.mem_id=?";
	private static final String GETREPSTAT2NUMBER="select count( DISTINCT rep.event_p_no) from event_p_rep  rep " + 
			"inner join(select *from event_p) p" + 
			"on rep.event_p_no=p.event_p_no " + 
			"where rep.rep_stat=2  and p.event_no=?";
	static {
		try {
			Context ctx=new InitialContext();
			ds=(DataSource) ctx.lookup("java:comp/env/jdbc/EA103G1");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Event_P_RepVO event_p_repVO) {
		//開始建立連線
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(INSERT);
			pstmt.setInt(1, event_p_repVO.getEvent_p_no());
			pstmt.setString(2, event_p_repVO.getMem_id());
			pstmt.setTimestamp(3, event_p_repVO.getRep_time());
			pstmt.setString(4, event_p_repVO.getRep_reason());
			pstmt.setInt(5, event_p_repVO.getRep_stat());
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
	public void update(Integer event_p_no, Integer rep_stat) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(UPDATE);
			pstmt.setInt(1, rep_stat);
			pstmt.setInt(2, event_p_no);
			System.out.println("UPDATE start to execute: ");
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
	public void delete(Integer event_p_no, String mem_id) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(DELETE);
			pstmt.setInt(1, event_p_no);
			pstmt.setString(2, mem_id);
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
	public List<Event_P_RepVO> findAllRep() {
		// TODO Auto-generated method stub
		List<Event_P_RepVO> event_p_repVOs=new ArrayList<Event_P_RepVO>();
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDALLREP);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Event_P_RepVO event_p_repVO=new Event_P_RepVO();
				event_p_repVO.setEvent_p_no(rs.getInt(1));
				event_p_repVO.setMem_id(rs.getString(2));
				event_p_repVO.setRep_time(rs.getTimestamp(3));
				event_p_repVO.setRep_reason(rs.getString(4));
				event_p_repVO.setRep_stat(rs.getInt(5));
				event_p_repVOs.add(event_p_repVO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					// TODO: handle exception
					e2.printStackTrace();
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
		return event_p_repVOs;
	}

	@Override
	public List<Event_P_RepVO> findRepByEvent_p_no(Integer event_p_no) {
		// TODO Auto-generated method stub
		
		Connection con=null;
		PreparedStatement pstmt=null;
		List<Event_P_RepVO> event_p_repVOs=new ArrayList<Event_P_RepVO>();
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDREPBYEVENTPNO);
			pstmt.setInt(1, event_p_no);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Event_P_RepVO event_p_repVO=new Event_P_RepVO();
				event_p_repVO.setEvent_p_no(rs.getInt(1));
				event_p_repVO.setMem_id(rs.getString(2));
				event_p_repVO.setRep_time(rs.getTimestamp(3));
				event_p_repVO.setRep_reason(rs.getString(4));
				event_p_repVO.setRep_stat(rs.getInt(5));
				event_p_repVOs.add(event_p_repVO);
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
		return event_p_repVOs;
	}

	@Override
	public boolean checkMemReport(String mem_id, String event_no) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(CHECKMEMREPORT);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, event_no);
			rs =pstmt.executeQuery();
			while(rs.next()) {
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
	public List<Event_P_RepVO> findRepByEventNo(String event_no) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		List<Event_P_RepVO> event_p_repVOs=new ArrayList<Event_P_RepVO>();
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDREPBYEVENTNO);
			pstmt.setString(1, event_no);
			System.out.println(event_no);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Event_P_RepVO event_p_repVO=new Event_P_RepVO();
				event_p_repVO.setEvent_p_no(rs.getInt(1));
				event_p_repVO.setMem_id(rs.getString(2));
				event_p_repVO.setRep_time(rs.getTimestamp(3));
				event_p_repVO.setRep_reason(rs.getString(4));
				event_p_repVO.setRep_stat(rs.getInt(5));
				event_p_repVOs.add(event_p_repVO);
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
		
		
		return event_p_repVOs;
	}

	@Override
	public List<Event_P_RepVO> findAllByRepStat(Integer rep_stat) {
		// TODO Auto-generated method stub
		Connection con=null;
		Event_P_RepVO repVO=null;
		PreparedStatement pstmt=null;
		List<Event_P_RepVO> repVOs=new ArrayList<Event_P_RepVO>();
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDALLBYREPSTAT);
			pstmt.setInt(1, rep_stat);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				repVO=new Event_P_RepVO();
				repVO.setEvent_p_no(rs.getInt(1));
				repVO.setMem_id(rs.getString(2));
				repVO.setRep_time(rs.getTimestamp(3));
				repVO.setRep_reason(rs.getString(4));
				repVO.setRep_stat(rs.getInt(5));
				repVOs.add(repVO);
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
		return repVOs;
	}

	@Override
	public List<Event_P_RepVO> findAllByCompositeQuery(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		List<Event_P_RepVO> repVOs=new ArrayList<Event_P_RepVO>();
		CompositeQuery_Event_P_Rep compQRep=new CompositeQuery_Event_P_Rep();
		String whereCondiction=compQRep.get_WhereCondition(map);
		String sql="select*from event_p_rep"+whereCondiction+"order by event_p_no";
		System.out.println("compositeQuery SQL:"+sql);
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Event_P_RepVO repVO=new Event_P_RepVO();
				repVO.setEvent_p_no(rs.getInt(1));
				repVO.setMem_id(rs.getString(2));
				repVO.setRep_time(rs.getTimestamp(3));
				repVO.setRep_reason(rs.getString(4));
				repVO.setRep_stat(rs.getInt(5));
				repVOs.add(repVO);
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
		
		
		return repVOs;
	}

	@Override
	public Event_P_RepVO findByMemId(String mem_id) {
		// TODO Auto-generated method stub
		Connection con=null;
		Event_P_RepVO repVO=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDBYMEMID);
			pstmt.setString(1, mem_id);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				repVO=new Event_P_RepVO();
				repVO.setEvent_p_no(rs.getInt(1));
				repVO.setMem_id(rs.getString(2));
				repVO.setRep_time(rs.getTimestamp(3));
				repVO.setRep_reason(rs.getString(4));
				repVO.setRep_stat(rs.getInt(5));
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
		
		return repVO;
	}

	@Override
	public Event_P_RepVO findReportByMemAndEventNo(String mem_id, String event_no) {
		// TODO Auto-generated method stub
System.out.println("in findReportByMemAndEventNo:--------------------------");
System.out.println("mem_id :"+mem_id);
System.out.println("event_no :"+event_no);
System.out.println("----------------------------------");
		Connection con=null;
		PreparedStatement pstmt=null;
		Event_P_RepVO event_p_repVO=new Event_P_RepVO();
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDREPORTBYMEMANDEVENTNO);
			pstmt.setString(1, event_no);
			pstmt.setString(2, mem_id);
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				event_p_repVO.setEvent_p_no(rs.getInt(1));
				event_p_repVO.setMem_id(rs.getString(2));
				event_p_repVO.setRep_time(rs.getTimestamp(3));
				event_p_repVO.setRep_reason(rs.getString(4));
				event_p_repVO.setRep_stat(rs.getInt(5));
				
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
		if(event_p_repVO.getEvent_p_no()==null) {
System.out.println("nothing in event_p_repVO ");
			return null;
		}else {
			return event_p_repVO;
		}
		
	}

//	@Override
//	public int getRepStatTwoChangeNumByEventNo(String event_no) {
//		// TODO Auto-generated method stub
//		Connection con=null;
//		PreparedStatement pstmt=null;
//		int repStat2Num=0;
//		try {
//			con=ds.getConnection();
//			pstmt=con.prepareStatement(GETREPSTAT2NUMBER);
//			ResultSet rs=pstmt.executeQuery();
//			while(rs.next()) {
//				repStat2Num=rs.getInt(1);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		return repStat2Num;
//	}
	

}
