package com.vote_d.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Vote_DDAO implements Vote_DDAO_interface{
	private static DataSource ds=null;
	private static final String VOTENUM_BY_MEM_IN_EVENTNO="select count(*)"
			+ "from(select mem_id,event_p_no from event_p where event_no=? )ta "
			+ "right join (select *from vote_d where mem_id=?) tb "
			+ "on ta.event_p_no=tb.event_p_no ";
	private static final String INSERT="insert into vote_d values(?,?)";
	private static final String FINDALLBYMEM="select *from vote_d where mem_id=?";
	private static final String DELETE="DELETE FROM VOTE_D WHERE EVENT_P_NO=? AND MEM_ID=?";
	private static final String FINDALL="SELECT* FROM VOTE_D";
	static{
		try {
			Context ctx=new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/EA103G1");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void insert(Vote_DVO vote_d,Connection con) {
		System.out.println("in Vote_DVO insert method");
		// TODO Auto-generated method stub
		
		PreparedStatement pstmt=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(INSERT);
			System.out.println("vote_d.getMem_id(): "+vote_d.getMem_id());
			System.out.println("vote_d.getEvent_p_no(): "+vote_d.getEvent_p_no());
			pstmt.setString(1, vote_d.getMem_id());
			pstmt.setInt(2, vote_d.getEvent_p_no());
			pstmt.execute();
			
		}catch(SQLException e) {
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
	public Integer voteNumByMemInEventno(String event_no, String mem_id) {
		// TODO Auto-generated method stub
		Connection con=null;
		Integer voteNum=0;
		PreparedStatement pstmt=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(VOTENUM_BY_MEM_IN_EVENTNO);
			pstmt.setString(1, event_no);
			pstmt.setString(2, mem_id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				voteNum=rs.getInt(1);
				
			}
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
		
		return voteNum;
	}



	@Override
	public List<Vote_DVO> findAllByMem(String mem_id) {
		// TODO Auto-generated method stub
		Connection con=null;
		List<Vote_DVO> vote_dVOs=new ArrayList<Vote_DVO>();
		PreparedStatement pstmt=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDALLBYMEM);
			pstmt.setString(1, mem_id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				Vote_DVO vote_dVO=new Vote_DVO();
				vote_dVO.setMem_id(rs.getString(1));
				vote_dVO.setEvent_p_no(rs.getInt(2));
				vote_dVOs.add(vote_dVO);
			}
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
		return vote_dVOs;
	}
	
	@Override
	public void delete(Integer event_p_no, String mem_id) {
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(DELETE);
			pstmt.setInt(1, event_p_no);
			pstmt.setString(2, mem_id);
			pstmt.execute();
			
		} catch (SQLException e) {
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
	public List<Vote_DVO> findAllVote_D() {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		List<Vote_DVO> vote_dVOs=new ArrayList<Vote_DVO>();
		try {
			con=ds.getConnection();
			pstmt=con.prepareStatement(FINDALL);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				Vote_DVO vote_dVO=new Vote_DVO();
				vote_dVO.setMem_id(rs.getString(1));
				vote_dVO.setEvent_p_no(rs.getInt(2));
				vote_dVOs.add(vote_dVO);
			}
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
		return vote_dVOs;
	}
}
