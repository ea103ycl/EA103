package com.eval.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//import com.order.model.OrderVO;
//import com.prod.model.ProdJDBCDAO;
//import com.prod.model.ProdVO;
//import com.sun.glass.ui.Pixels.Format;

public class EvalJDBCDAO implements EvalDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO EVAL_PROD (eval_no, prod_no, or_no,  mem_id, eval_rang, eval_review ) VALUES(eval_prod_seq.nextval,?,?,?,?,?)";
	private static final String GET_ALL_BY_PROD_NO = "SELECT * FROM EVAL_PROD WHERE PROD_NO=? ORDER BY EVAL_NO DESC";
	
	
	@Override
	public void insert(EvalVO evalVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setInt(1,    evalVO.getProd_no());
			pstmt.setString(2, evalVO.getOr_no());
			pstmt.setString(3, evalVO.getMem_id());
			pstmt.setInt(4,    evalVO.getEval_rang());
			pstmt.setString(5, evalVO.getEval_review());
			
			pstmt.executeUpdate();


			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
					Class.forName(driver);
					con = DriverManager.getConnection(url, userid, passwd);
					pstmt = con.prepareStatement(GET_ALL_BY_PROD_NO);
					
					pstmt.setInt(1, prod_no);
					
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
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("Couldn't load database driver. "
							+ e.getMessage());
					// Handle any SQL errors
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
	
	

	public static void main(String[] args) {

		EvalJDBCDAO dao = new EvalJDBCDAO();

//////		// �s�W
//		EvalVO evalVO = new EvalVO();
//		evalVO.setProd_no(1);
//		evalVO.setOr_no("S20201029-00001");
//		evalVO.setMem_id("M000001");
//		evalVO.setEval_rang(5);
//		evalVO.setEval_review("�W�L100��");
//		dao.insert(evalVO);
//		System.out.println("�s�W���\!!");
//	

//	������
		List<EvalVO> list = dao.getAllByProd_no(1);
		for(EvalVO aEval : list) {
			System.out.print(aEval.getEval_no()+ ", ");
			System.out.print(aEval.getMem_id()+ ", ");
			System.out.print(aEval.getOr_no()+ ", ");
			System.out.print(aEval.getProd_no()+ ", ");
			System.out.print(aEval.getEval_rang()+ ", ");
			
			System.out.print(aEval.getEval_review()+ ", ");
			
			SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(f2.format(aEval.getEval_time().getTime()));
			System.out.println();
		}
		System.out.println("訂單查詢成功!");




	}

	@Override
	public List<EvalVO> getAllByOr_no(String or_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EvalVO getOneByOr_no_and_Prod_no(String or_no, Integer prod_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EvalVO getMem_pic_ByMem_id(String mem_id) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
