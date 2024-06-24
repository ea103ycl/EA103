package com.material.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_MatData;

public class Material_Data_DAO implements Material_Data_DAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO MATERIAL_DATA (ma_no,ma_ty_no,ma_name,ma_price,ma_photo,ma_status) VALUES ('L'||LPAD(to_char(MA_NO_SEQ.NEXTVAL), 4, '0'), ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ma_no,ma_ty_no,ma_name,ma_price,ma_photo,ma_status FROM MATERIAL_DATA order by ma_no";
	private static final String GET_ONE_STMT = "SELECT ma_no,ma_ty_no,ma_name,ma_price,ma_photo,ma_status FROM MATERIAL_DATA where ma_no = ?";
	private static final String GET_ONE_TYPE = "SELECT ma_no,ma_ty_no,ma_name,ma_price,ma_photo,ma_status FROM MATERIAL_DATA where ma_ty_no = ?";
	private static final String DELETE = "DELETE FROM MATERIAL_DATA where ma_no = ?";
	private static final String DELETETYPE = "DELETE FROM MATERIAL_DATA where ma_ty_no = ?";
	private static final String UPDATE = "UPDATE MATERIAL_DATA set ma_ty_no=?, ma_name=?, ma_price=?, ma_photo=?, ma_status=? where ma_no = ?";

	@Override
	public void insert(Material_Data_VO material_Data_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, material_Data_VO.getMaTyNo());
			pstmt.setString(2, material_Data_VO.getMaName());
			pstmt.setInt(3, material_Data_VO.getMaPrice());
			pstmt.setBytes(4, material_Data_VO.getMaPhoto());
			pstmt.setInt(5, material_Data_VO.getMaStatus());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void update(Material_Data_VO material_Data_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, material_Data_VO.getMaTyNo());
			pstmt.setString(2, material_Data_VO.getMaName());
			pstmt.setInt(3, material_Data_VO.getMaPrice());
			pstmt.setBytes(4, material_Data_VO.getMaPhoto());
			pstmt.setInt(5, material_Data_VO.getMaStatus());
			pstmt.setString(6, material_Data_VO.getMaNo());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void delete(String ma_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ma_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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

	public void deletetype(String ma_ty_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETETYPE);

			pstmt.setString(1, ma_ty_no);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public Material_Data_VO findByPrimaryKey(String ma_no) {

		Material_Data_VO material_Data_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ma_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				material_Data_VO = new Material_Data_VO();
				material_Data_VO.setMaNo(rs.getString("ma_no"));
				material_Data_VO.setMaTyNo(rs.getString("ma_ty_no"));
				material_Data_VO.setMaName(rs.getString("ma_name"));
				material_Data_VO.setMaPrice(rs.getInt("ma_price"));
				material_Data_VO.setMaPhoto(rs.getBytes("ma_photo"));
				material_Data_VO.setMaStatus(rs.getInt("ma_status"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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
		return material_Data_VO;
	}

	public List<Material_Data_VO> findByMaterialType(String ma_ty_no) {

		List<Material_Data_VO> list = new ArrayList<Material_Data_VO>();

		Material_Data_VO material_Data_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_TYPE);

			pstmt.setString(1, ma_ty_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				material_Data_VO = new Material_Data_VO();
				material_Data_VO.setMaNo(rs.getString("ma_no"));
				material_Data_VO.setMaTyNo(rs.getString("ma_ty_no"));
				material_Data_VO.setMaName(rs.getString("ma_name"));
				material_Data_VO.setMaPrice(rs.getInt("ma_price"));
				material_Data_VO.setMaPhoto(rs.getBytes("ma_photo"));
				material_Data_VO.setMaStatus(rs.getInt("ma_status"));
				list.add(material_Data_VO);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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
	public List<Material_Data_VO> getAll() {
		List<Material_Data_VO> list = new ArrayList<Material_Data_VO>();
		Material_Data_VO material_Data_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				material_Data_VO = new Material_Data_VO();
				material_Data_VO.setMaNo(rs.getString("ma_no"));
				material_Data_VO.setMaTyNo(rs.getString("ma_ty_no"));
				material_Data_VO.setMaName(rs.getString("ma_name"));
				material_Data_VO.setMaPrice(rs.getInt("ma_price"));
				material_Data_VO.setMaPhoto(rs.getBytes("ma_photo"));
				material_Data_VO.setMaStatus(rs.getInt("ma_status"));
				list.add(material_Data_VO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
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

	public List<Material_Data_VO> getAll(Map<String, String[]> map) {
		List<Material_Data_VO> list = new ArrayList<Material_Data_VO>();
		Material_Data_VO material_Data_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			String finalSQL = "select * from MATERIAL_DATA " + jdbcUtil_CompositeQuery_MatData.get_WhereCondition(map)
					+ "order by ma_no";

			pstmt = con.prepareStatement(finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				material_Data_VO = new Material_Data_VO();
				material_Data_VO.setMaNo(rs.getString("ma_no"));
				material_Data_VO.setMaTyNo(rs.getString("ma_ty_no"));
				material_Data_VO.setMaName(rs.getString("ma_name"));
				material_Data_VO.setMaPrice(rs.getInt("ma_price"));
				material_Data_VO.setMaPhoto(rs.getBytes("ma_photo"));
				material_Data_VO.setMaStatus(rs.getInt("ma_status"));
				list.add(material_Data_VO);
			}

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured." + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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
}
