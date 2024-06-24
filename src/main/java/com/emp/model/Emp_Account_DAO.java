package com.emp.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Emp_Account_DAO implements Emp_Account_DAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO EMP_ACCOUNT (emp_no,emp_name,emp_pwd,emp_pos,emp_mail,emp_photo,emp_status) VALUES ('E'||LPAD(to_char(EMP_NO_SEQ.NEXTVAL), 4, '0'), ?, ?, ?, ?, ?,?)";
	private static final String GET_ALL_STMT = "SELECT emp_no,emp_name,emp_pwd,emp_pos,emp_mail,emp_photo,emp_status FROM EMP_ACCOUNT order by emp_status desc, emp_no ";
	private static final String GET_ONE_STMT = "SELECT emp_no,emp_name,emp_pwd,emp_pos,emp_mail,emp_photo,emp_status FROM EMP_ACCOUNT where emp_no = ?";
	private static final String DELETE = "DELETE FROM EMP_ACCOUNT where emp_no = ?";
	private static final String UPDATE = "UPDATE EMP_ACCOUNT set emp_name=?, emp_pwd=?, emp_pos=?, emp_mail=?, emp_photo=?, emp_status=? where emp_no = ?";
	private static final String SEND_PHOTO = "UPDATE EMP_ACCOUNT set emp_photo=? where emp_no = ?";
	private static final String FORGET_PWD = "UPDATE EMP_ACCOUNT set emp_pwd=? where emp_no=?";

	@Override
	public Emp_Account_VO insert(Emp_Account_VO emp_Account_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			String[] cols = { "emp_no" };

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, emp_Account_VO.getEmpName());
			pstmt.setString(2, emp_Account_VO.getEmpPwd());
			pstmt.setString(3, emp_Account_VO.getEmpPos());
			pstmt.setString(4, emp_Account_VO.getEmpMail());
			pstmt.setBytes(5, emp_Account_VO.getEmpPhoto());
			pstmt.setInt(6, emp_Account_VO.getEmpStatus());

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				String emp_no = rs.getString(1);
				emp_Account_VO.setEmpNo(emp_no);
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
		return emp_Account_VO;
	}

	
	@Override
	public void update(Emp_Account_VO emp_Account_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, emp_Account_VO.getEmpName());
			pstmt.setString(2, emp_Account_VO.getEmpPwd());
			pstmt.setString(3, emp_Account_VO.getEmpPos());
			pstmt.setString(4, emp_Account_VO.getEmpMail());
			pstmt.setBytes(5, emp_Account_VO.getEmpPhoto());
			pstmt.setInt(6, emp_Account_VO.getEmpStatus());
			pstmt.setString(7, emp_Account_VO.getEmpNo());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	
	public void reset(Emp_Account_VO emp_Account_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FORGET_PWD);
			
			pstmt.setString(1, emp_Account_VO.getEmpPwd());
			pstmt.setString(2, emp_Account_VO.getEmpNo());
			
			pstmt.executeUpdate();
		} catch(SQLException se) {
			throw new RuntimeException("A database error occured."+ se.getMessage());
			
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
					
				} catch(SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}					
		}		
	}

	@Override
	public void delete(String emp_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, emp_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public Emp_Account_VO findByPrimaryKey(String emp_no) {

		Emp_Account_VO emp_Account_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, emp_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				emp_Account_VO = new Emp_Account_VO();
				emp_Account_VO.setEmpNo(rs.getString("emp_no"));
				emp_Account_VO.setEmpName(rs.getString("emp_name"));
				emp_Account_VO.setEmpPwd(rs.getString("emp_pwd"));
				emp_Account_VO.setEmpPos(rs.getString("emp_pos"));
				emp_Account_VO.setEmpMail(rs.getString("emp_mail"));
				emp_Account_VO.setEmpPhoto(rs.getBytes("emp_photo"));
				emp_Account_VO.setEmpStatus(rs.getInt("emp_status"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
		return emp_Account_VO;
	}

	@Override
	public List<Emp_Account_VO> getAll() {
		List<Emp_Account_VO> list = new ArrayList<Emp_Account_VO>();
		Emp_Account_VO emp_Account_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				emp_Account_VO = new Emp_Account_VO();
				emp_Account_VO.setEmpNo(rs.getString("emp_no"));
				emp_Account_VO.setEmpName(rs.getString("emp_name"));
				emp_Account_VO.setEmpPwd(rs.getString("emp_pwd"));
				emp_Account_VO.setEmpPos(rs.getString("emp_pos"));
				emp_Account_VO.setEmpMail(rs.getString("emp_mail"));
				emp_Account_VO.setEmpPhoto(rs.getBytes("emp_photo"));
				emp_Account_VO.setEmpStatus(rs.getInt("emp_status"));
				list.add(emp_Account_VO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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