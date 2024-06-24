package com.eval.model;



import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.eval.model.*;
import com.order.model.OrderVO;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;


public class EvalDAO implements EvalDAO_interface {
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

				private static final String INSERT_STMT = "INSERT INTO EVAL_PROD (eval_no, prod_no, or_no,  mem_id, eval_rang, eval_review) VALUES(eval_prod_seq.nextval,?,?,?,?,?)";
				private static final String GET_ALL_BY_PROD_NO = "SELECT * FROM EVAL_PROD WHERE PROD_NO=? ORDER BY EVAL_NO DESC";
				private static final String GET_ALL_BY_OR_NO = "SELECT * FROM EVAL_PROD WHERE OR_NO=? ORDER BY EVAL_NO";
				private static final String GET_ONE_STMT = "SELECT * FROM eval_prod WHERE OR_NO =? and prod_no=?";
				private static final String GET_PIC_BY_MEM_ID = "SELECT * from members where mem_id =?";
				
				
		public void insert(EvalVO evalVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1,    evalVO.getProd_no());
			pstmt.setString(2, evalVO.getOr_no());
			pstmt.setString(3, evalVO.getMem_id());
			pstmt.setInt(4,    evalVO.getEval_rang());
			pstmt.setString(5, evalVO.getEval_review());
			
			pstmt.executeUpdate();

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
	}

	@Override
	public List<EvalVO> getAllByProd_no(Integer prod_no) {
		List<EvalVO> list = new ArrayList<EvalVO>();
		EvalVO evalVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
		 pstmt = con.prepareStatement(GET_ALL_BY_PROD_NO);
			
			pstmt.setInt(1,prod_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				evalVO = new EvalVO();
				evalVO.setEval_no(rs.getInt("eval_no"));
				evalVO.setProd_no(rs.getInt("prod_no"));
				evalVO.setOr_no(rs.getString("or_no"));
				evalVO.setMem_id(rs.getString("mem_id"));
				evalVO.setEval_rang(rs.getInt("eval_rang"));
				evalVO.setEval_review(rs.getString("eval_review"));
				
				evalVO.setEval_time(rs.getTimestamp("eval_time"));
				list.add(evalVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

	@Override
	public List<EvalVO> getAllByOr_no(String or_no) {
		List<EvalVO> list = new ArrayList<EvalVO>();
		EvalVO evalVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
		 pstmt = con.prepareStatement(GET_ALL_BY_OR_NO);
			
			pstmt.setString(1,or_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				evalVO = new EvalVO();
				evalVO.setEval_no(rs.getInt("eval_no"));
				evalVO.setProd_no(rs.getInt("prod_no"));
				evalVO.setOr_no(rs.getString("or_no"));
				evalVO.setMem_id(rs.getString("mem_id"));
				evalVO.setEval_rang(rs.getInt("eval_rang"));
				evalVO.setEval_review(rs.getString("eval_review"));
				
				evalVO.setEval_time(rs.getTimestamp("eval_time"));
				list.add(evalVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

	@Override
	public EvalVO getOneByOr_no_and_Prod_no(String or_no, Integer prod_no) {
		EvalVO evalVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, or_no);
			pstmt.setInt(2, prod_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				evalVO = new EvalVO();
				evalVO.setOr_no(rs.getString("or_no"));
				evalVO.setMem_id(rs.getString("mem_id"));
				evalVO.setProd_no(rs.getInt("prod_no"));
				evalVO.setEval_rang(rs.getInt("eval_rang"));
				evalVO.setEval_review(rs.getString("eval_review"));
			}

			// Handle any SQL errors
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
		return evalVO;
	}

	@Override
	public EvalVO getMem_pic_ByMem_id(String mem_id) {
		 EvalVO evalVO = null;
		  Connection con = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  
		  try {
		   con = ds.getConnection();
		   pstmt = con.prepareStatement(GET_PIC_BY_MEM_ID);
		   
		   pstmt.setString(1, mem_id);
		   
		   rs = pstmt.executeQuery();
		   
		   while (rs.next()) {
		    evalVO = new EvalVO();
		    evalVO.setM_photo(rs.getBytes("m_photo"));
		    
		   }

		   // Handle any SQL errors
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
		  return evalVO;
		 }
	

}

