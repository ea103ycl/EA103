package com.painter.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mem.model.MemVO;
import com.painter_tag_map.model.PainterTagMapService;
import com.painter_tag_map.model.PainterTagMapVO;

import tools.YclTools;

public class PainterDAO implements PainterDAO_interface {


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
	
	
	/*
	 * private static final String INSERT_STMT =
	 * "INSERT INTO painter( ptr_no, mem_id, ptr_nm, intro, pic, priv_stat, ptr_stat,create_dt) VALUES(?,?,?,?,?,?,?,?) "
	 * ; private static final String UPDATE_STMT =
	 * "UPDATE painter SET ptr_no=?, mem_id=?, ptr_nm=?, intro=?, pic=?, priv_stat=?, ptr_stat=?,create_dt=? WHERE  ptr_no=? "
	 * ;
	 */

	// ==YCL==============================================================================
	private static final String INSERT_STMT = "INSERT INTO painter( ptr_no, mem_id, ptr_nm, intro, pic, priv_stat, ptr_stat, like_cnt, col_cnt, create_dt) VALUES( PAINTER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, sysdate) ";
	private static final String UPDATE_STMT = "UPDATE painter SET ptr_nm = ?, intro = ?, pic= ?, priv_stat = ?, ptr_stat= ? WHERE ptr_no= ? ";
	private static final String DELETE_STMT = "UPDATE painter SET PTR_STAT = ? WHERE ptr_no =? ";
	private static final String GET_PIC_BY_PTR_NO_STMT = "SELECT pic FROM painter WHERE ptr_no = ? ";
	private static final String GET_ONE_PAINTER_STMT = "SELECT * FROM PAINTER WHERE PTR_NO = ?";
	private static final String GET_MEMBERS_BY_ACCT_STMT = "SELECT M_ACCNO FROM MEMBERS WHERE M_ACCNO LIKE ? ";
	
	// 取得某位會員發表過+未刪除的作品
	private static final String GET_SOMEONE_ALL_STMT = "SELECT P.PTR_NO, P.MEM_ID, P.PTR_NM, P.INTRO, P.PRIV_STAT, P.PTR_STAT, P.LIKE_CNT, P.COL_CNT, P.CREATE_DT FROM PAINTER P "
			+ " WHERE MEM_ID = ? " // 只抓指定會員的作品
			+ "   AND P.PTR_STAT = 1 " // 且作品狀態須為正常
			+ "   AND (P.MEM_ID = ? " // 是登入者本人的作品
			+ "        OR P.PRIV_STAT = 1 " // 或作品公開程度為1:公開 或 2:僅限會員本人(該會員需為創作者本人) 3:僅限粉絲(該會員需為該創作者的粉絲)
			+ "        OR ( P.PRIV_STAT = 2 AND P.MEM_ID = ? ) "
			+ "        OR ( P.PRIV_STAT = 3 AND 1 = (SELECT 1 FROM FOLLOW F WHERE F.MEM_ID = ? AND F.F_MEM_ID = P.MEM_ID) )"
			+ "       ) " + "ORDER BY PTR_NO DESC ";

	// 取得某位會員收藏的作品
	private static final String GET_SOMEONE_COLLECTION_STMT = "SELECT P.PTR_NO, P.MEM_ID, P.PTR_NM, P.INTRO, P.PRIV_STAT, P.PTR_STAT, P.LIKE_CNT, P.COL_CNT, P.CREATE_DT FROM PAINTER P "
			+ " WHERE P.PTR_NO IN ( SELECT PA.PTR_NO FROM PAINTER_ACT PA WHERE PA.ACT_TYPE = '2' AND PA.MEM_ID = ? ) " // 只抓指定會員有收藏的作品
			+ "   AND P.PTR_STAT = 1 " // 且作品狀態須為正常
			+ "   AND (P.MEM_ID = ? " // 是登入者本人的作品
			+ "        OR P.PRIV_STAT = 1 " // 或作品公開程度為1:公開 或 2:僅限會員本人(該會員需為創作者本人) 3:僅限粉絲(該會員需為該創作者的粉絲)
			+ "        OR ( P.PRIV_STAT = 2 AND P.MEM_ID = ? ) "
			+ "        OR ( P.PRIV_STAT = 3 AND 1 = (SELECT 1 FROM FOLLOW F WHERE F.MEM_ID = ? AND F.F_MEM_ID = P.MEM_ID) )"
			+ "       ) " + "ORDER BY PTR_NO DESC ";
	
	//取得某位會員所有追蹤對象們的作品與自己的作品
	private static final String GET_SOMEONE_FOLLOW_ALL_STMT = "SELECT P.PTR_NO, P.MEM_ID, P.PTR_NM, P.INTRO, P.PRIV_STAT, P.PTR_STAT, P.LIKE_CNT, P.COL_CNT, P.CREATE_DT FROM PAINTER P " + 
															  " WHERE ( P.MEM_ID IN (SELECT F_MEM_ID FROM FOLLOW F WHERE F.MEM_ID = ? ) " + 
															  "         AND (P.PTR_STAT = 1 AND P.PRIV_STAT IN(1,3)) ) " + 
															  "    OR ( P.MEM_ID = ? AND P.PTR_STAT = 1 ) " + 
															  " ORDER BY P.PTR_NO DESC ";


	// ==Tim==============================================================================
	private static final String SEARCH_ALL = "SELECT * FROM painter";
	private static final String SEARCH_NEW_STMT = "SELECT * FROM painter ORDER BY create_dt ";
	
	private static final String SEARCH_MOST_LIKED_STMT="SELECT * FROM"
			+ "(SELECT ROW_NUMBER() OVER (ORDER BY LIKE_CNT desc)"
			+ "AS RANK,PAINTER.* FROM PAINTER WHERE CREATE_DT BETWEEN ? AND ?)"
			+ "WHERE RANK BETWEEN ? AND ?";

	private static final String GET_PIC_COUNT="SELECT COUNT(*) FROM PAINTER";
	

	// ===================================================================================
	// Author:YCL
	// ===================================================================================
	public void insert(PainterVO painterVO, String tag_desc) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer ptr_no = 0;
		
		try {
			con = ds.getConnection();
			
			String[] cols = {"ptr_no"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			
			//會連動tag資料檔，先剩為非autoCommit
			con.setAutoCommit(false);
			
			System.out.println("[PainterDAO]1.準備新增作品");
			pstmt.setString(1, painterVO.getMem_id()); // 會員編號
			pstmt.setString(2, painterVO.getPtr_nm()); // 作品名稱
			pstmt.setString(3, painterVO.getIntro()); // 作品介紹
			pstmt.setBytes(4, painterVO.getPic()); // 作品圖片
			pstmt.setInt(5, painterVO.getPriv_stat()); // 隱私權狀態
			pstmt.setInt(6, painterVO.getPtr_stat()); // 作品狀態
			pstmt.setInt(7, painterVO.getLike_cnt()); // 被喜歡次數
			pstmt.setInt(8, painterVO.getCol_cnt()); // 被收藏次數
			pstmt.executeUpdate();

			// 取得新作品的的sequence
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				ptr_no = rs.getInt(1);
			}
			System.out.println("[PainterDAO]2.新增作品ptr_no=" + ptr_no + "完畢");
			
			//將連線轉交給painterTagMap並新增相關資料
			PainterTagMapService painterTagMapSvc = new PainterTagMapService();
			painterTagMapSvc.insert(con, ptr_no, tag_desc);
			System.out.println("[PainterDAO]3.新增作品ptr_no=" + ptr_no + "的PainterTagMap完畢");
			
			con.commit();
			con.setAutoCommit(true);
			System.out.println("[PainterDAO]4.ptr_no=" + ptr_no + "commit資料完畢");
			
		} catch (SQLException se) {
			try {
				System.err.println("rolled back-由-PainterDAO");
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public void update(PainterVO painterVO, String tag_desc) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();

			con.setAutoCommit(false);
			System.out.println("[PainterDAO]1.準備修改作品，ptr_no=" + painterVO.getPtr_no());
			
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, painterVO.getPtr_nm());
			pstmt.setString(2, painterVO.getIntro());
			pstmt.setBytes(3, painterVO.getPic());
			pstmt.setInt(4, painterVO.getPriv_stat());
			pstmt.setInt(5, painterVO.getPtr_stat());
			pstmt.setInt(6, painterVO.getPtr_no());
			pstmt.executeUpdate();
			System.out.println("[PainterDAO]2.修改作品ptr_no=" + painterVO.getPtr_no() + "資料完畢");
			
			//將連線轉交給painterTagMap並新增相關資料
			PainterTagMapService painterTagMapSvc = new PainterTagMapService();
			painterTagMapSvc.delete(con, painterVO.getPtr_no());
			System.out.println("[PainterDAO]3.刪除ptr_no=" + painterVO.getPtr_no() + "的painter_tag_map完畢");
			
			painterTagMapSvc.insert(con, painterVO.getPtr_no(), tag_desc);
			System.out.println("[PainterDAO]4.重新新增ptr_no=" + painterVO.getPtr_no() + "的painter_tag_map完畢");
			
			con.commit();
			con.setAutoCommit(true);
			System.out.println("[PainterDAO]5.ptr_no=" + painterVO.getPtr_no() + "資料commit完畢");
			
		} catch (SQLException e) {
			if (con != null) {
				try {
					System.err.println("rolled back-由-PainterDAO");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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


	public void delete(Integer ptr_no, Integer ptr_status) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			System.out.println("[PainterDAO]準備刪除作品，SQL=" + DELETE_STMT + "，ptr_status=" + ptr_status + "，ptr_no=" + ptr_no);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, ptr_status); // 1:正常 2:由創作者刪除 3:由管理員刪除
			pstmt.setInt(2, ptr_no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("database error" + e.getMessage());
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

	public List<PainterVO> getSomeoneAll(String mem_id, String login_mem_id) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PainterVO painterVO;
		List<PainterVO> list = new ArrayList<PainterVO>();

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_SOMEONE_ALL_STMT);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, login_mem_id);
			pstmt.setString(3, login_mem_id);
			pstmt.setString(4, login_mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				painterVO = new PainterVO();
				painterVO.setPtr_no(rs.getInt("ptr_no"));
				painterVO.setMem_id(rs.getString("mem_id"));
				painterVO.setPtr_nm(rs.getString("ptr_nm"));
				painterVO.setIntro(rs.getString("intro"));
//				painterVO.setPic(rs.getBytes("pic"));
				painterVO.setPriv_stat(rs.getInt("priv_stat"));
				painterVO.setPtr_stat(rs.getInt("ptr_stat"));
				painterVO.setLike_cnt(rs.getInt("like_cnt"));
				painterVO.setCol_cnt(rs.getInt("col_cnt"));
				painterVO.setCreate_dt(rs.getTimestamp("create_dt"));
				list.add(painterVO);
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

	// 取得某位會員已收藏的作品 ，且該作品須符合以下條件：
	// [1]PTR_STAT(作品狀態) = 1:為正常
	// [2]PRIV_STAT(作品公開程度)符合以下之一:
	// PRIV_STAT = 1:公開
	// PRIV_STAT = 2:僅限本人，但登入的會員與創作者同人
	// PRIV_STAT = 3:僅限粉絲，但登入的會員有追蹤創作者
	// PRIV_STAT不限，但作品須為本人創作
	public List<PainterVO> getSomeoneCollection(String mem_id, String login_mem_id) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PainterVO painterVO = null;
		List<PainterVO> list = new ArrayList<PainterVO>();

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_SOMEONE_COLLECTION_STMT);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, login_mem_id);
			pstmt.setString(3, login_mem_id);
			pstmt.setString(4, login_mem_id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				painterVO = new PainterVO();
				painterVO.setPtr_no(rs.getInt("ptr_no"));
				painterVO.setMem_id(rs.getString("mem_id"));
				painterVO.setPtr_nm(rs.getString("ptr_nm"));
				painterVO.setIntro(rs.getString("intro"));
//				painterVO.setPic(rs.getBytes("pic"));
				painterVO.setPriv_stat(rs.getInt("priv_stat"));
				painterVO.setPtr_stat(rs.getInt("ptr_stat"));
				painterVO.setLike_cnt(rs.getInt("like_cnt"));
				painterVO.setCol_cnt(rs.getInt("col_cnt"));
				painterVO.setCreate_dt(rs.getTimestamp("create_dt"));
				list.add(painterVO);
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
	
	
	public List<PainterVO> getSomeoneFollowAll(String login_mem_id) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PainterVO painterVO;
		List<PainterVO> list = new ArrayList<PainterVO>();

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_SOMEONE_FOLLOW_ALL_STMT);
			pstmt.setString(1, login_mem_id);
			pstmt.setString(2, login_mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				painterVO = new PainterVO();
				painterVO.setPtr_no(rs.getInt("ptr_no"));
				painterVO.setMem_id(rs.getString("mem_id"));
				painterVO.setPtr_nm(rs.getString("ptr_nm"));
				painterVO.setIntro(rs.getString("intro"));
//				painterVO.setPic(rs.getBytes("pic"));
				painterVO.setPriv_stat(rs.getInt("priv_stat"));
				painterVO.setPtr_stat(rs.getInt("ptr_stat"));
				painterVO.setLike_cnt(rs.getInt("like_cnt"));
				painterVO.setCol_cnt(rs.getInt("col_cnt"));
				painterVO.setCreate_dt(rs.getTimestamp("create_dt"));
				list.add(painterVO);
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

	public byte[] getPicByPtrNo(Integer ptr_no) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] pic = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_PIC_BY_PTR_NO_STMT);
			pstmt.setInt(1, ptr_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pic = rs.getBytes("pic");
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
		return pic;

	}

	@Override
	public PainterVO getOnePainter(Integer ptr_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PainterVO painterVO = null;

		try {

//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_PAINTER_STMT);

			pstmt.setInt(1, ptr_no);
			rs =  pstmt.executeQuery();

			if (rs.next()) {
				painterVO = new PainterVO();
				painterVO.setPtr_no(rs.getInt("ptr_no")); // 作品流水號
				painterVO.setMem_id(rs.getString("mem_id")); // 會員編號
				painterVO.setPtr_nm(rs.getString("ptr_nm")); // 作品名稱
				painterVO.setIntro(YclTools.readClobToString(rs.getClob("intro")));// 作品介紹
				painterVO.setPic(rs.getBytes("pic")); // 作品圖片
				painterVO.setPriv_stat(rs.getInt("priv_stat")); // 隱私權狀態
				painterVO.setPtr_stat(rs.getInt("ptr_stat")); // 作品狀態
				painterVO.setLike_cnt(rs.getInt("like_cnt")); // 被喜歡次數
				painterVO.setCol_cnt(rs.getInt("col_cnt")); // 被收藏次數
				painterVO.setCreate_dt(rs.getTimestamp("create_dt")); // 作品建立時間
			}

		} catch (IOException e) {
			e.printStackTrace();
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

		return painterVO;
	}
	
	public List<String> getMembersByMAccno(String acct) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_MEMBERS_BY_ACCT_STMT);
			pstmt.setString(1, "%" + acct + "%");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String m_acct = rs.getString(1);
				System.out.println(m_acct);
				list.add(m_acct);
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
	
	//==Tim===============================================================
	
	public List<PainterVO> latestPics() {

		PainterVO painterVO = null;
		List<PainterVO> list = new ArrayList<PainterVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCH_NEW_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {

				painterVO = new PainterVO();
				painterVO.setPtr_no(rs.getInt("ptr_no"));
				painterVO.setMem_id(rs.getString("mem_id"));
				painterVO.setPtr_nm(rs.getString("ptr_nm"));
				painterVO.setIntro(rs.getString("intro"));
				painterVO.setPic(rs.getBytes("pic"));
				painterVO.setPriv_stat(rs.getInt("priv_stat"));
				painterVO.setPtr_stat(rs.getInt("ptr_stat"));
				painterVO.setLike_cnt(rs.getInt("like_cnt"));
				painterVO.setCol_cnt(rs.getInt("col_cnt"));
				painterVO.setCreate_dt(rs.getTimestamp("create_dt"));
				list.add(painterVO);
			}

		} catch (SQLException e) {
			throw new RuntimeException("dataBase error" + e.getMessage());
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
	public List<PainterVO>getMostLiked(Integer rankBegin, Integer rankEnd){
		
		List<PainterVO> list =new ArrayList<PainterVO>();
		PainterVO painterVO=null;
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(SEARCH_MOST_LIKED_STMT);

			Date d = new Date();
			
			Timestamp past =new Timestamp(d.getTime()-(2*60*60*24*30*1000L));
			Timestamp now =new Timestamp(d.getTime());
			

			pstmt.setTimestamp(1,past);
			pstmt.setTimestamp(2,now);
			pstmt.setInt(3, rankBegin);
			pstmt.setInt(4, rankEnd);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				painterVO=new PainterVO();
				painterVO.setPtr_no(rs.getInt("ptr_no"));
				painterVO.setMem_id(rs.getString("mem_id"));
				painterVO.setPtr_nm(rs.getString("ptr_nm"));
				painterVO.setIntro(rs.getString("intro"));
				painterVO.setPic(rs.getBytes("pic"));
				painterVO.setPriv_stat(rs.getInt("priv_stat"));
				painterVO.setPtr_stat(rs.getInt("ptr_stat"));
				painterVO.setLike_cnt(rs.getInt("like_cnt"));
				painterVO.setCol_cnt(rs.getInt("col_cnt"));
				painterVO.setCreate_dt(rs.getTimestamp("create_dt"));
				list.add(painterVO);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error:"+e.getMessage());
		}finally {
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

	public List<PainterVO> getAll() {

		PainterVO painterVO = null;
		List<PainterVO> list = new ArrayList<PainterVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCH_ALL);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				
				painterVO = new PainterVO();
				painterVO.setPtr_no(rs.getInt("ptr_no"));
				painterVO.setMem_id(rs.getString("mem_id"));
				painterVO.setPtr_nm(rs.getString("ptr_nm"));
				painterVO.setIntro(rs.getString("intro"));
				painterVO.setPic(rs.getBytes("pic"));
				painterVO.setPriv_stat(rs.getInt("priv_stat"));
				painterVO.setPtr_stat(rs.getInt("ptr_stat"));
				painterVO.setLike_cnt(rs.getInt("like_cnt"));
				painterVO.setCol_cnt(rs.getInt("col_cnt"));
				painterVO.setCreate_dt(rs.getTimestamp("create_dt"));
				list.add(painterVO);
			}

		} catch (SQLException e) {
			throw new RuntimeException("dataBase error" + e.getMessage());
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
	
	public Integer getPicCount() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer count=0;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PIC_COUNT);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				count=rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new RuntimeException("dataBase error" + e.getMessage());
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
//		System.out.println(count);
		return count;
	}
}
