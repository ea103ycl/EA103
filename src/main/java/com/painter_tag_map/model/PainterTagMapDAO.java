package com.painter_tag_map.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.painter_tag.model.PainterTagService;

public class PainterTagMapDAO implements PainterTagMapDAO_interface {

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

//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	String userid = "G1";
//	String passwd = "123456";

	private static final String DELETE_STMT = "DELETE FROM PAINTER_TAG_MAP WHERE PTR_NO = ? ";
	private static final String INSERT_STMT = "INSERT INTO PAINTER_TAG_MAP (PTR_NO, TAG_NO, TAG_SEQ) VALUES(?, ?, ?) ";
	private static final String GET_ALL_BY_PTR_NO_STMT = "SELECT * FROM PAINTER_TAG_MAP WHERE PTR_NO = ? ORDER BY TAG_SEQ";

	
	@Override
	public void insert(Connection con, Integer ptr_no, String tag_desc) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(INSERT_STMT);

			// 將tag轉為陣列ptr_no
			String[] tag_desc_array = tag_desc.split("#");
			
			System.out.println("[PainterTagMapDAO]開始新增painterTagMap");
			PainterTagService painterTagSvc = new PainterTagService();
			for (int i = 0; i < tag_desc_array.length; i++) {
				if (tag_desc_array[i] != null && tag_desc_array[i].trim().length() != 0) {
					Integer tag_no = painterTagSvc.insert(tag_desc_array[i].trim());
					pstmt.setInt(1, ptr_no);
					pstmt.setInt(2, tag_no);
					pstmt.setInt(3, i);
					pstmt.executeQuery();
					System.out.println("[PainterTagMapDAO]新增painterTagMap完成，ptr_no=" + ptr_no + "，tag_no=" + tag_no + "，流水號=" + i);
				}
			}
			System.out.println("[PainterTagMapDAO]新增painterTagMap完畢");
		} catch (Exception e) {
			if (con != null) {
				try {
					System.err.println("rolled back-由-PainterTagMapDAO");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

	}
	
	@Override
	public void delete(Connection con, Integer ptr_no) {
		
		PreparedStatement pstmt = null;
		
		try {
			System.out.println("[PainterTagMapDAO]開始刪除painterTagMap");
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, ptr_no);
			pstmt.executeQuery();
			
			System.out.println("[PainterTagMapDAO]刪除painterTagMap完畢");
			
		} catch (SQLException e) {
			if (con != null) {
				try {
					System.err.println("rolled back-由-PainterTagMapDAO");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}


	@Override
	public List<PainterTagMapVO> getAllByPtrNo(Integer ptr_no) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PainterTagMapVO> list = new ArrayList<PainterTagMapVO>();
		PainterTagMapVO painterTagMapVO = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_BY_PTR_NO_STMT);
			pstmt.setInt(1, ptr_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				painterTagMapVO = new PainterTagMapVO();
				painterTagMapVO.setPtr_no(rs.getInt("ptr_no"));
				painterTagMapVO.setTag_no(rs.getInt("tag_no"));
				painterTagMapVO.setTag_seq(rs.getInt("tag_seq"));
				list.add(painterTagMapVO);
			}

		} catch (SQLException e) {
			throw new RuntimeException("database error" + e.getMessage());
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

}
