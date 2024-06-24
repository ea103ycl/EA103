package com.painter_msg.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.painter.model.PainterVO;

import tools.YclTools;

public class PainterMsgDAO implements PainterMsgDAO_interface {

//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	String userid = "G1";
//	String passwd = "123456";

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
	
	private static final String INSERT_PAINTER_MSG_STMT = "INSERT INTO PAINTER_MSG (MSG_NO, PTR_NO, MEM_ID, MSG, MSG_DT, MSG_STAT )"
			+ "VALUES ( PAINTER_MSG_SEQ.NEXTVAL, ?, ?, ?, SYSDATE, ?) ";
	private static final String DELETE_PAINTER_MSG_STMT = "UPDATE PAINTER_MSG SET MSG_STAT = ? WHERE MSG_NO = ? ";
	private static final String GET_ALL_STMT = "SELECT * FROM PAINTER_MSG WHERE MSG_STAT = 1 AND PTR_NO = ? ORDER BY MSG_NO ";
	private static final String GET_PAINTER_MSG_CNT_STMT = "SELECT COUNT(1) AS CNT FROM PAINTER_MSG WHERE PTR_NO = ? AND MSG_STAT = 1";

	@Override
	public void insert(PainterMsgVO painterMsgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(INSERT_PAINTER_MSG_STMT);

			pstmt.setInt(1, painterMsgVO.getPtr_no());
			pstmt.setString(2, painterMsgVO.getMem_id());
			pstmt.setString(3, painterMsgVO.getMsg());
			pstmt.setInt(4, 1); //預設1:正常
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	public void delete(Integer msg_no, Integer msg_stat) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(DELETE_PAINTER_MSG_STMT);
			pstmt.setInt(1, msg_stat);
			pstmt.setInt(2, msg_no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}

			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	public List<PainterMsgVO> getAll(Integer ptr_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PainterMsgVO> list = new ArrayList<PainterMsgVO>();
		PainterMsgVO painterMsgVO = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_STMT);
			pstmt.setInt(1, ptr_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				painterMsgVO = new PainterMsgVO();
				painterMsgVO.setMsg_no(rs.getInt("msg_no"));
				painterMsgVO.setPtr_no(rs.getInt("ptr_no"));
				painterMsgVO.setMem_id(rs.getString("mem_id"));
				painterMsgVO.setMsg(YclTools.readStringAppendBr(rs.getString("msg")));
				painterMsgVO.setMsg_dt(rs.getTimestamp("msg_dt"));
				painterMsgVO.setMsg_stat(rs.getInt("msg_stat"));
				list.add(painterMsgVO);
			}

		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}

			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	public Integer getPainterMsgCnt(Integer ptr_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer msgCnt = 0;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_PAINTER_MSG_CNT_STMT);
			pstmt.setInt(1, ptr_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				msgCnt = rs.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}

			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
		return msgCnt;
	}

}
