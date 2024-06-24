package com.vote_d.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Vote_DJDBCDAO implements Vote_DDAO_interface{
	private static Connection con=null;
	private static final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USERNAME="G1";
	private static final String PASSWORD="123456";
	private static final String INSERT="INSERT INTO VOTE_D(MEM_ID,EVENT_P_NO) VALUES(?,?)";
	private static final String VOTENUM_BY_MEM_IN_EVENTNO="select count(*)"
			+ "from(select mem_id,event_p_no from event_p where event_no=? )ta "
			+ "right join (select *from vote_d where mem_id=?) tb "
			+ "on ta.event_p_no=tb.event_p_no ";
	private static final String DELETE="DELETE FROM VOTE_D WHERE EVENT_P_NO=? AND MEM_ID=?";
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insert(Vote_DVO vote_dVO) {
		try {
			PreparedStatement pstmt=con.prepareStatement(INSERT);
			pstmt.setString(1,vote_dVO.getMem_id());
			pstmt.setInt(2, vote_dVO.getEvent_p_no());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Integer voteNumByMemInEventno(String event_no, String mem_id) {
		// TODO Auto-generated method stub
		Integer voteNum=0;
		try {
			PreparedStatement pstmt=con.prepareStatement(VOTENUM_BY_MEM_IN_EVENTNO);
			pstmt.setString(1, event_no);
			pstmt.setString(2, mem_id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				voteNum=rs.getInt(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return voteNum;
	}
	@Override
	public void delete(Integer event_p_no, String mem_id) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, 3003);
			pstmt.setString(2, "M000001");
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Vote_DJDBCDAO dao=new Vote_DJDBCDAO();
		//新增
//		Vote_DVO vote_dVO=new Vote_DVO();
//		vote_dVO.setMem_id("M000001");
//		vote_dVO.setEvnet_p_no(3002);
		
//		dao.insert(vote_dVO);
		
		//輸入mem_id和event_no查詢投票總數紀錄
		 Integer voteNum=dao.voteNumByMemInEventno("E001001", "M000001");
		
		
		//刪除
//		dao.delete(3003, "M000001");
//		System.out.println("del");
	}
	@Override
	public List<Vote_DVO> findAllByMem(String mem_id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Vote_DVO> findAllVote_D() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void insert(Vote_DVO vote_d, Connection con) {
		// TODO Auto-generated method stub
		
	}



}
