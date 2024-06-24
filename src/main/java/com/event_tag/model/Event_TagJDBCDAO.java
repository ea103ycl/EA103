package com.event_tag.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class Event_TagJDBCDAO {
	private static final String DRIVER="oracle.jdbc.driver.OracleDriver";
	private static final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USERNAME="G1";
	private static final String PASSWORD="123456";
	
	private static final String INSERT="INSERT INTO EVENT_TAG(EVENT_TAG_NO,MEM_ID,EVENT_NO,EVENT_TAG_TIME,EVENT_TAG_NAME) "
			+ "VALUES(EVENT_TAG_SEQ.NEXTVAL,?,?,?,?)";
	private static final String UPDATE="UPDATE EVENT_TAG SET MEM_ID=?,EVENT_NO=?,EVENT_TAG_TIME=?,EVENT_TAG_NAME=? WHERE EVENT_TAG_NO=?";
	private static final String FINDALLTAG="SELECT* FROM EVENT_TAG";
	private static final String FINDONEBYPRIMARYKEY="SELECT* FROM EVENT_TAG WHERE EVENT_TAG_NO=?";
	static Connection con;
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
	
	
	public void insert(Event_TagVO event_tagVO) {
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(INSERT);
			pstmt.setString(1, event_tagVO.getMem_id());
			pstmt.setString(2, event_tagVO.getEvent_no());
			pstmt.setTimestamp(3, event_tagVO.getEvent_tag_time());
			pstmt.setString(4, event_tagVO.getEvent_tag_name());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("insert done");
	}
	
	public void update(Event_TagVO event_tagVO) {
		PreparedStatement pstmt;
		try {
			pstmt=con.prepareStatement(UPDATE);
			pstmt.setString(1, event_tagVO.getMem_id());
			pstmt.setString(2, event_tagVO.getEvent_no());
			pstmt.setTimestamp(3, event_tagVO.getEvent_tag_time());
			pstmt.setString(4, event_tagVO.getEvent_tag_name());
			pstmt.setInt(5, event_tagVO.getEvent_tag_no());
			pstmt.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("update done");
	}
	public List<Event_TagVO> findAllTag(){
		List<Event_TagVO> event_tagVOs=new ArrayList<Event_TagVO>();
		PreparedStatement pstmt;
		try {
			pstmt=con.prepareStatement(FINDALLTAG);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				Event_TagVO event_tagVO=new Event_TagVO();
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
		}
		return event_tagVOs;
	}
	public Event_TagVO findByPrimaryKey(Integer event_tag_no) {
		Event_TagVO event_tagVO=new Event_TagVO();
		
		try {
			PreparedStatement pstmt=con.prepareStatement(FINDONEBYPRIMARYKEY);
			pstmt.setInt(1, event_tag_no);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				event_tagVO.setEvent_tag_no(rs.getInt(1));
				event_tagVO.setMem_id(rs.getString(2));
				event_tagVO.setEvent_no(rs.getString(3));
				event_tagVO.setEvent_tag_time(rs.getTimestamp(4));
				event_tagVO.setEvent_tag_name(rs.getString(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return event_tagVO;
	}
	
	
	public static void main(String[] args) {
		Event_TagVO event_tagVO =new Event_TagVO();
		Event_TagJDBCDAO dao=new Event_TagJDBCDAO();
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		//insert
//		event_tagVO.setMem_id("M000001");
//		event_tagVO.setEvent_no("E001001");
//		event_tagVO.setEvent_tag_time(ts);
//		event_tagVO.setEvent_tag_name("¥P¨§");
//		dao.insert(event_tagVO);
		
		//update
//		Event_TagVO event_tagVO01=new Event_TagVO();
//		event_tagVO01.setMem_id("M000001");
//		event_tagVO01.setEvent_no("E001001");
//		event_tagVO01.setEvent_tag_time(ts);
//		event_tagVO01.setEvent_tag_name("Â§ª«²°");
//		event_tagVO01.setEvent_tag_no(2001);
//		dao.update(event_tagVO01);
		
		//finAllTag
//		List<Event_TagVO> event_tagVOs= dao.findAllTag();
//		Iterator<Event_TagVO> iter=event_tagVOs.iterator();
//		while(iter.hasNext()) {
//			Event_TagVO event_tagVO2=iter.next();
//			System.out.println(event_tagVO2.getEvent_tag_name());
//		}
		
		//findOneByPrimaryKey
//		Integer event_tag_no=new Integer(2001);
//		
//		Event_TagVO event_tagVO3=dao.findByPrimaryKey(event_tag_no);
//		System.out.println(event_tagVO3.getEvent_tag_name());
		
	}
}
