package com.painter_tag.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PainterTagDAO implements PainterTagDAO_interface {

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

	//==YCL=========================================================
	private static final String GET_PAINTER_TAG_NO_STMT = "SELECT TAG_NO FROM PAINTER_TAG WHERE TAG_DESC = ? ";
	private static final String INSERT_PAINTER_TAG_STMT = "INSERT INTO PAINTER_TAG(TAG_NO, TAG_DESC) VALUES (PAINTER_TAG_SEQ.NEXTVAL, ?　)　";
	private static final String GET_ONE_BY_TAG_NO_STMT = "SELECT * FROM PAINTER_TAG WHERE TAG_NO = ? ";

	//==Tim=========================================================
	private static final String INSERT_STMT = "INSERT INTO painter_tag (tag_no,tag_desc) VALUES (PAINTER_TAG_SEQ.nextval,?)";
	private static final String SEARCHBYTAG_STMT = "SELECT tag_no,tag_desc FROM painter_tag WHERE lower(tag_desc) LIKE ?";
	private static final String GET_TAG_DESC_STMT = "SELECT tag_no,tag_desc FROM painter_tag WHERE tag_no = ?";
	private static final String GET_PIC_BY_TAG_DESC_STMT="SELECT PTR_NO FROM PAINTER_TAG_MAP WHERE TAG_NO=(SELECT tag_no FROM painter_tag WHERE tag_desc like ?)";

//	private static final String GET_PIC_BY_TAG_DESC_STMT="SELECT PTR_NO FROM PAINTER_TAG_MAP WHERE TAG_NO=(SELECT tag_no FROM painter_tag WHERE lower(tag_desc) LIKE ?)";

	
	//==YCL=========================================================
	public Integer findTagNo(String tagDesc) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer tag_no = 0;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_PAINTER_TAG_NO_STMT);
			pstmt.setString(1, tagDesc);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tag_no = rs.getInt("tag_no");
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

		return tag_no;
	}

	@Override
	public Integer insert(String tagDesc) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer tag_no = 0;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			tag_no = findTagNo(tagDesc);

			if (0 == tag_no) { // 已經存在的tag不重複儲存

				String[] cols = { "tag_no" }; // insert後要取回的欄位
				pstmt = con.prepareStatement(INSERT_PAINTER_TAG_STMT, cols);
				pstmt.setString(1, tagDesc);
				pstmt.executeUpdate();

				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					tag_no = rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace(System.err);
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
		return tag_no;
	}

	@Override
	public PainterTagVO getOneByTagNo(Integer tag_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PainterTagVO painterTagVO = null;
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_BY_TAG_NO_STMT);
			pstmt.setInt(1, tag_no);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				painterTagVO = new PainterTagVO();
				painterTagVO.setTag_no(rs.getInt("tag_no"));
				painterTagVO.setTag_desc(rs.getString("tag_desc"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace(System.err);
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

		return painterTagVO;

	}
	
	//==Tim=========================================================
	public List<Integer> getPicbyTag(String tag) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> list=null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PIC_BY_TAG_DESC_STMT);
			pstmt.setString(1,tag);
			rs = pstmt.executeQuery();
			list=new ArrayList<Integer>();
			
			while (rs.next()) {
				list.add(rs.getInt("ptr_no"));
				
				
			}

	
		} catch (SQLException e) {
			throw new RuntimeException("A database error occur");
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
//		System.out.println("(PainterTagDAO)"+list);
		return list;
	}
	

	@Override
	public List<PainterTagVO> fuzzySearch(String tag) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<PainterTagVO> list = new ArrayList<PainterTagVO>();
		PainterTagVO painterTagVO = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCHBYTAG_STMT);
			pstmt.setString(1, "%" + tag + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				painterTagVO = new PainterTagVO();

				painterTagVO.setTag_no(rs.getInt("tag_no"));
				painterTagVO.setTag_desc(rs.getString("tag_desc"));

				list.add(painterTagVO);

			}

		} catch (SQLException e) {
			throw new RuntimeException("A database error occur");
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
//		System.out.println(list);
		return list;
	}

	@Override
	public Integer getTagNo(String tag_desc) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		PainterTagVO painterTagVO = null;
		Integer tag_no=null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCHBYTAG_STMT);
			pstmt.setString(1, tag_desc);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				painterTagVO = new PainterTagVO();
				tag_no=rs.getInt("tag_no");
				painterTagVO.setTag_no(rs.getInt("tag_no"));
				painterTagVO.setTag_desc(rs.getString("tag_desc"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("A database error occur");
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
//		System.out.println("ptVO > tag_no:"+tag_no);
		return tag_no;
	}

	@Override
	public String getTagDesc(Integer tag_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		PainterTagVO painterTagVO = null;
		String tag_desc=null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_TAG_DESC_STMT);
			pstmt.setInt(1, tag_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				painterTagVO = new PainterTagVO();
				tag_desc=rs.getString("tag_desc");
				painterTagVO.setTag_no(rs.getInt("tag_no"));
				painterTagVO.setTag_desc(rs.getString("tag_desc"));

			

			}

		} catch (SQLException e) {
			throw new RuntimeException("A database error occur");
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
//		System.out.println(tag_desc);
		return tag_desc;
	}
	
}
