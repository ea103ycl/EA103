package com.event_p_rep.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;


public class Event_P_RepJDBCDAO implements Event_P_RepDAO_interface{
	
	private static final String DRIVER="oracle.jdbc.driver.OracleDriver";
	private static final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USERNAME="G1";
	private static final String PASSWORD="123456";
	private static Connection con;
	private static final String INSERT="INSERT INTO EVENT_P_REP VALUES(?,?,?,?,?)";
	private static final String UPDATE="UPDATE EVENT_P_REP SET REP_STAT=? WHERE EVENT_P_NO=? ";
	private static final String FINDALLREP="SELECT* FROM EVENT_P_REP";
	private static final String DELETE="DELETE FROM EVENT_P_REP WHERE EVENT_P_NO=? AND MEM_ID=?";//前台取消檢舉
	private static final String FINDREPBYEVENTPNO="SELECT * FROM EVENT_P_REP WHERE EVENT_P_NO=?";
	
	static {
		try {
			Class.forName(DRIVER);
			con=DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void insert(Event_P_RepVO event_p_repVO) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt=con.prepareStatement(INSERT);
			pstmt.setInt(1, event_p_repVO.getEvent_p_no());
			pstmt.setString(2, event_p_repVO.getMem_id());
			pstmt.setTimestamp(3, event_p_repVO.getRep_time());
			pstmt.setString(4, event_p_repVO.getRep_reason());
			pstmt.setInt(5, event_p_repVO.getRep_stat());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	//update only need to change event_p stat
	public void update(Integer rep_stat,Integer event_p_no) {
		// TODO Auto-generated method stub
		try {
			
			PreparedStatement pstmt=con.prepareStatement(UPDATE);
			pstmt.setInt(1, rep_stat);
			pstmt.setInt(2, event_p_no);
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Integer event_p_no, String mem_id) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt=con.prepareStatement(DELETE);
			pstmt.setInt(1, event_p_no);
			pstmt.setString(2, mem_id);
			pstmt.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Event_P_RepVO> findAllRep() {
		// TODO Auto-generated method stub
		List<Event_P_RepVO> event_p_repVOs=new ArrayList<Event_P_RepVO>();
		try {
			PreparedStatement pstmt=con.prepareStatement(FINDALLREP);
			ResultSet rs=pstmt.executeQuery();
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
		}
		return event_p_repVOs;
	}
	@Override
	public List<Event_P_RepVO> findRepByEvent_p_no(Integer event_p_no) {
		// TODO Auto-generated method stub
		List<Event_P_RepVO> event_p_repVOs=new ArrayList<Event_P_RepVO>();
		try {
			PreparedStatement pstmt=con.prepareStatement(FINDREPBYEVENTPNO);
			pstmt.setInt(1, event_p_no);
			ResultSet rs=pstmt.executeQuery();
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
		}
		return event_p_repVOs;
	}
	
	public static void main(String[] args) {
		Event_P_RepJDBCDAO dao= new Event_P_RepJDBCDAO();
		Event_P_RepVO event_p_repVO=new Event_P_RepVO();
		
		//insert test
//		event_p_repVO.setEvent_p_no(3003);
//		event_p_repVO.setMem_id("M000001");
//		GregorianCalendar gc=new GregorianCalendar(2020,9,13);
//		java.util.Date date=gc.getGregorianChange();
//		event_p_repVO.setRep_time(new Timestamp(date.getTime()));
//		event_p_repVO.setRep_reason("冊冊冊");
//		event_p_repVO.setRep_stat(0);
//		dao.insert(event_p_repVO);
		
		//findAllRep
//		List<Event_P_RepVO> event_p_repVOs =dao.findAllRep();
//		Iterator<Event_P_RepVO> iter=event_p_repVOs.iterator();
//		while(iter.hasNext()) {
//			Event_P_RepVO event_p_repVOiter=iter.next();
//			System.out.println(event_p_repVOiter.getRep_reason());
//		}
		//delete
//		dao.delete(3003, "M000001");
		
		//findRepByevent_p_no
//		List<Event_P_RepVO> event_p_repVOs=dao.findRepByEvent_p_no(3003);
//		Iterator<Event_P_RepVO> iter=event_p_repVOs.iterator();
//		while(iter.hasNext()) {
//			Event_P_RepVO event_p_repVOiter=iter.next();
//			System.out.println(event_p_repVOiter.getRep_reason());
//		}
		
		//update event_p_no stat
//		dao.update(2, 3003);
		
	}

	@Override
	public boolean checkMemReport(String mem_id, String event_no) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Event_P_RepVO> findRepByEventNo(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event_P_RepVO> findAllByRepStat(Integer rep_stat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event_P_RepVO> findAllByCompositeQuery(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event_P_RepVO findByMemId(String mem_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event_P_RepVO findReportByMemAndEventNo(String mem_id, String event_no) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public int getRepStatTwoChange() {
//		// TODO Auto-generated method stub
//		return 0;
//	}


}
