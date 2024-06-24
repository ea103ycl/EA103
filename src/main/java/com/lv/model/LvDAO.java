package com.lv.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import tools.YclTools;

public class LvDAO implements LvDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO lv VALUES ( LV_SEQ.NEXTVAL, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE lv SET lv_nm=?, icon=?, f_cnt=? WHERE lv = ?";
	private static final String DELETE_STMT = "DELETE FROM lv WHERE lv = ?";
	private static final String GET_BY_PRIMARY_KEY_STMT = "SELECT * FROM lv WHERE lv = ?";
	private static final String GET_BY_THRESHOLD_SETTING_STMT = "SELECT * FROM lv WHERE f_cnt = ?";
	private static final String GET_LV_BY_FANS_STMT = "SELECT * FROM (SELECT RANK() OVER(ORDER BY F_CNT DESC) AS rank_no, lv.* FROM lv WHERE f_cnt <= ? ORDER BY f_cnt DESC) WHERE rank_no = 1";
	private static final String GET_ALL_STMT = "SELECT * FROM lv ORDER BY f_cnt";
	private static final String GET_ONE_BY_MEMID_STMT = "SELECT L.ICON FROM LV L WHERE L.LV = (SELECT M.LV FROM MEMBERS M WHERE M.MEM_ID = ? )";
	private static final String GET_BY_F_CNT_STMT = "SELECT * FROM (SELECT RANK() OVER(ORDER BY F_CNT DESC) AS rank_no, lv.* FROM lv WHERE f_cnt <= ? ORDER BY f_cnt DESC) WHERE rank_no = 1";
	private static final String GET_ICON_BY_LV = "SELECT icon FROM lv WHERE lv = ?";
	private static final String MODIFY_MEMBERS_LV_BEFORE_DELETE_OLD_LV = "UPDATE MEMBERS Z " +
			                                                             "   SET LV = (SELECT RES.NEW_LV " +
			                                                             "               FROM (SELECT X.MEM_ID, X.LV, B.F_CNT, A.LV AS NEW_LV, A.F_CNT AS NEW_LV_THRESHOULD,  " +
			                                                             "                            RANK() OVER(PARTITION BY X.MEM_ID ORDER BY A.F_CNT DESC) AS ORD_NO " +
			                                                             "                       FROM MEMBERS X, LV A, " +
			                                                             "                            (SELECT BT.F_MEM_ID,  " +
			                                                             "                                    COUNT(1) AS F_CNT  " +
			                                                             "                               FROM FOLLOW BT " +
			                                                             "                              GROUP BY BT.F_MEM_ID) B " +
			                                                             "                      WHERE B.F_MEM_ID = X.MEM_ID " +
			                                                             "                        AND A.LV <> X.LV " +
			                                                             "                        AND A.F_CNT <= B.F_CNT) RES " +
			                                                             "               WHERE RES.ORD_NO = 1 " +
			                                                             "                 AND RES.MEM_ID = Z.MEM_ID) " +
			                                                             " WHERE LV = ?";
	
	private static final String MODIFY_MEMBERS_LV_AFTER_UPDATE_LV = "UPDATE MEMBERS Z " +
											 			         	"   SET LV = (SELECT RES.NEW_LV " +
											 			         	"               FROM (SELECT X.MEM_ID, X.LV, B.F_CNT, A.LV AS NEW_LV, A.F_CNT AS NEW_LV_THRESHOULD,  " +
											 			         	"                            RANK() OVER(PARTITION BY X.MEM_ID ORDER BY A.F_CNT DESC) AS ORD_NO " +
											 			         	"                       FROM MEMBERS X, LV A, " +
											 			         	"                            (SELECT BT.F_MEM_ID,  " +
											 			         	"                                    COUNT(1) AS F_CNT  " +
											 			         	"                               FROM FOLLOW BT " +
											 			         	"                              GROUP BY BT.F_MEM_ID) B " +
											 			         	"                      WHERE B.F_MEM_ID = X.MEM_ID " +
											 			         	"                        AND A.F_CNT <= B.F_CNT ) RES " +
											 			         	"               WHERE RES.ORD_NO = 1 " +
											 			         	"                 AND RES.MEM_ID = Z.MEM_ID) " +
											 			         	"WHERE MEM_ID IN (SELECT F_MEM_ID" + 
											 			         	"                   FROM FOLLOW" + 
											 			         	"                  GROUP BY F_MEM_ID" + 
											 			         	"                 HAVING COUNT(1) >= ?)";

	private void modifyMembersLvBeforeDeleteOldLv(Integer lv) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(MODIFY_MEMBERS_LV_BEFORE_DELETE_OLD_LV);
			pstmt.setInt(1, lv);
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
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
	
	private void modifyMembersLvAfterUpdateLv(Integer f_cnt) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(MODIFY_MEMBERS_LV_AFTER_UPDATE_LV);
			pstmt.setInt(1, f_cnt);
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
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
	public void insert(LvVO lvVO){

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, lvVO.getLv_nm());
			pstmt.setBytes(2, lvVO.getIcon());
			pstmt.setInt(3, lvVO.getF_cnt());
			pstmt.executeUpdate();
			
			//將符合新門檻會員的里程碑等級更新為新增的里程碑等級
			this.modifyMembersLvAfterUpdateLv(lvVO.getF_cnt());

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
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
	public void update(LvVO lvVO){

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, lvVO.getLv_nm());
			pstmt.setBytes(2, lvVO.getIcon());
			pstmt.setInt(3, lvVO.getF_cnt());
			pstmt.setInt(4, lvVO.getLv());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
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
	public void delete(Integer lv){
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			
			//先調整會員主檔使用此lv的資料
			this.modifyMembersLvBeforeDeleteOldLv(lv);
			
			//再刪除此lv
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, lv);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
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
	public LvVO findByPrimaryKey(Integer lv){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LvVO lvVO = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_BY_PRIMARY_KEY_STMT);
			pstmt.setInt(1, lv);
			rs = pstmt.executeQuery();
			

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				lvVO = new LvVO();
				lvVO.setLv(rs.getInt("lv"));
				lvVO.setLv_nm(rs.getString("lv_nm"));
				lvVO.setIcon(rs.getBytes("icon"));
				lvVO.setF_cnt(rs.getInt("f_cnt"));
			}

		} catch (SQLException se) {
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

		return lvVO;
	}
	
	@Override
	public LvVO findLvByFansCnt(Integer f_cnt) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LvVO lvVO = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_LV_BY_FANS_STMT);
			pstmt.setInt(1, f_cnt);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				lvVO = new LvVO();
				lvVO.setLv(rs.getInt("lv"));
				lvVO.setLv_nm(rs.getString("lv_nm"));
				lvVO.setIcon(rs.getBytes("icon"));
				lvVO.setF_cnt(rs.getInt("f_cnt"));
			}
			
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
		} catch (SQLException se) {
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

		return lvVO;
	}

	@Override
	public List<LvVO> getAll() {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<LvVO> list = new ArrayList<LvVO>();
		LvVO lvVO = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lvVO = new LvVO();
				lvVO.setLv(rs.getInt("lv"));
				lvVO.setLv_nm(rs.getString("lv_nm"));				
				lvVO.setIcon(rs.getBytes("icon"));				
				lvVO.setF_cnt(rs.getInt("f_cnt"));
				list.add(lvVO);
			}

		} catch (SQLException se) {
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

		return list;
	}
	
	
	
	

	@Override
	public LvVO findByThresholdSetting(Integer f_cnt) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LvVO lvVO = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_BY_THRESHOLD_SETTING_STMT);
			pstmt.setInt(1, f_cnt);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lvVO = new LvVO();
				lvVO.setLv(rs.getInt("lv"));
				lvVO.setLv_nm(rs.getString("lv_nm"));
				lvVO.setIcon(rs.getBytes("icon"));
				lvVO.setF_cnt(rs.getInt("f_cnt"));
			}

		} catch (SQLException se) {
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

		return lvVO;
	}

	@Override
	public byte[] findIconByLv(Integer lv) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] icon = null;
		
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ICON_BY_LV);
			pstmt.setInt(1, lv);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				icon = rs.getBytes("icon");
			}

		} catch (SQLException se) {
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

		return icon;
	}

	
	//傳入會員編號，取得該會員的里程碑等級圖片
	@Override
	public byte[] getOneMemberLvIcon(String mem_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] icon = null;
		
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ONE_BY_MEMID_STMT);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				icon = rs.getBytes("icon");
			}

		} catch (SQLException se) {
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

		return icon;
	}
	
}
