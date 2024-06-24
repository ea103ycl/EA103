package com.event_p.model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Event_PJNDIDAO implements Event_PDAO_interface{
	private static DataSource ds=null;
	private static final String INSERT="INSERT INTO EVENT_P VALUES(EVENT_P_SEQ.nextval,?,?,?,?,?,?,?,?)";
	//by 风纒
	private static final String GET_ALL_STMT = "SELECT * FROM EVENT_P WHERE EVENT_NO = (SELECT LAST_VALUE (EVENT_NO) OVER (ORDER BY EVENT_NO)  as LastValue FROM EVENT WHERE EVENT_STAT = 3 and rownum<2) ";

	
	
	static{
		try {
			Context ctx=new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/G1DB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void insert(Event_PVO pVO) {
		// TODO Auto-generated method stub
		Connection con=null;
		try {
			con=ds.getConnection();
			PreparedStatement pstmt=con.prepareStatement(INSERT);
			pstmt.setString(1,pVO.getMem_id());//穦ID眔
			pstmt.setString(2,pVO.getEvent_no() );//笆瑈腹
			pstmt.setString(3,pVO.getEvent_p_name());//珇嘿>ㄏノ块(眖呼ъ)
			
			pstmt.setTimestamp(4,pVO.getEvent_p_date());
			pstmt.setInt(5,pVO.getEvent_vote_num());//眔щ布计
			pstmt.setInt(6,pVO.getVote_rank());//パщ布计眔逼
			pstmt.setInt(7,pVO.getEvent_p_stat());//щ布
			pstmt.setBytes(8,pVO.getEvent_p_img());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public List<Event_PVO> findAllPic() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Event_PVO findByPrimaryKey(Integer event_p_no) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void update(Event_PVO pVO) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<String> findAllMem() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> findAllEventNo() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Event_PVO> findAllByEventNo(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void delete(Integer event_p_no) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void votePic(Integer event_p_no, String mem_id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteVote(Integer event_p_no) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Event_PVO> bigRankSort(String event_no, Integer rownum) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void bigRankUpdate(Integer vote_rank, Integer event_p_no) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkUploadByMemid(String mem_id, String event_no) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<Event_PVO> findAllNoReport(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Event_PVO> findAllOrderBySelect(String event_no, String colName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Event_PVO findUploadByMemid(String mem_id, String event_no) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Event_PVO findTopEventPNoByEventNoWithoutReport(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Event_PVO> findAllByEventNoRankDescWithoutReport(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Event_PVO> find5PicByEventNo(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//==风纒=========================================================
	@Override
	public List<Event_PVO> getAll(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}

}
