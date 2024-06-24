package com.material.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Material_Type_DAO implements Material_Type_DAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO MATERIAL_TYPE (ma_ty_no,ma_ty_nam) VALUES ('T'||LPAD(to_char(MATY_NO_SEQ.NEXTVAL), 4, '0'), ?)";
	private static final String GET_ALL_STMT = "SELECT ma_ty_no,ma_ty_nam FROM MATERIAL_TYPE order by ma_ty_no";
	private static final String GET_ONE_STMT = "SELECT ma_ty_no,ma_ty_nam FROM MATERIAL_TYPE where ma_ty_no = ?";
	private static final String DELETE = "DELETE FROM MATERIAL_TYPE where ma_ty_no = ?";
	private static final String UPDATE = "UPDATE MATERIAL_TYPE set ma_ty_nam=? where ma_ty_no = ?";

	@Override
	public Material_Type_VO insert(Material_Type_VO material_Type_VO) {
		
		String[]cols = {"ma_ty_no"};

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, material_Type_VO.getMaTyNam());

			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rs.next()) {
				String ma_ty_no = rs.getString(1);
				material_Type_VO.setMaTyNo(ma_ty_no);
			}
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." 
					+ se.getMessage());
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
		return material_Type_VO;
	}

	@Override
	public void update(Material_Type_VO material_Type_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, material_Type_VO.getMaTyNam());
			pstmt.setString(2, material_Type_VO.getMaTyNo());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." 
					+ se.getMessage());
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
	public void delete(String ma_ty_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ma_ty_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." 
					+ se.getMessage());
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
	public Material_Type_VO findByPrimaryKey(String ma_ty_no) {

		Material_Type_VO material_Type_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ma_ty_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				material_Type_VO = new Material_Type_VO();
				material_Type_VO.setMaTyNo(rs.getString("ma_ty_no"));
				material_Type_VO.setMaTyNam(rs.getString("ma_ty_nam"));

			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." 
					+ se.getMessage());
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
		return material_Type_VO;
	}

	@Override
	public List<Material_Type_VO> getAll() {
		List<Material_Type_VO> list = new ArrayList<Material_Type_VO>();
		Material_Type_VO material_Type_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				material_Type_VO = new Material_Type_VO();
				material_Type_VO.setMaTyNo(rs.getString("ma_ty_no"));
				material_Type_VO.setMaTyNam(rs.getString("ma_ty_nam"));
				list.add(material_Type_VO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." 
					+ se.getMessage());
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
