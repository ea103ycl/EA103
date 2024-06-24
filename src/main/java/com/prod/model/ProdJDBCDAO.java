package com.prod.model;

import java.util.*;
import com.prod.model.ProdVO;

import java.io.InputStream;
import java.sql.*;


public class ProdJDBCDAO implements ProdDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO PRODUCT(PROD_NO, PTR_NO, MA_NO, PROD_NAME, PROD_PRICE, PROD_DETAIL ,PROD_STATUS, PROD_PIC )   VALUES(PROD_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE PRODUCT SET  PTR_NO=?, MA_NO=?, PROD_NAME=?, PROD_PRICE=?, PROD_DETAIL=?, PROD_STATUS=?, PROD_PIC=? WHERE PROD_NO=?";
	private static final String GET_ALL_STMT = "SELECT * FROM PRODUCT ORDER BY PROD_NO";
	private static final String GET_ONE_STMT = "SELECT * FROM PRODUCT WHERE PROD_NO = ?";
	private static final String DELETE = "DELETE FROM PRODUCT WHERE PROD_NO = ?";
	private static final String GET_FUZZY_QUERY = "SELECT * FROM PRODUCT WHERE PROD_NAME LIKE ?";
	private static final String GET_ALL_STMT_STATUS = "SELECT * FROM PRODUCT WHERE PROD_STATUS = 1  ORDER BY PROD_NO";
	private static final String GET_PTR_MA_PROD = "SELECT * FROM PRODUCT WHERE ptr_no = ? and  ma_no = ? AND PROD_STATUS = 1";
	private static final String GET_ONE_MA = "SELECT * FROM MATERIAL_DATA WHERE MA_NO= ?";
	@Override
	public void insert(ProdVO prodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1,    prodVO.getPtr_no());
			pstmt.setString(2,    prodVO.getMa_no());
			pstmt.setString(3, prodVO.getProd_name());
			pstmt.setInt(4,    prodVO.getProd_price());
			pstmt.setString(5, prodVO.getProd_detail());
			pstmt.setInt(6,    prodVO.getProd_status());
			pstmt.setBytes(7,  prodVO.getProd_pic());
			
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
	public void update(ProdVO prodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1,    prodVO.getPtr_no());
			pstmt.setString(2,    prodVO.getMa_no());
			pstmt.setString(3, prodVO.getProd_name());
			pstmt.setInt(4,    prodVO.getProd_price());
			pstmt.setString(5, prodVO.getProd_detail());
			pstmt.setInt(6,    prodVO.getProd_status());
			pstmt.setBytes(7,  prodVO.getProd_pic());
			pstmt.setInt(8,    prodVO.getProd_no());

			pstmt.executeUpdate();

			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(Integer prod_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, prod_no);
			
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public ProdVO findByPrimaryKey(Integer prod_no) {
		ProdVO prodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, prod_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				prodVO = new ProdVO();
				prodVO.setProd_no(rs.getInt("prod_no"));
				prodVO.setPtr_no(rs.getInt("ptr_no"));
				prodVO.setMa_no(rs.getString("ma_no"));
				prodVO.setProd_name(rs.getString("prod_name"));
				prodVO.setProd_price(rs.getInt("prod_price"));
				prodVO.setProd_detail(rs.getString("prod_detail"));
				prodVO.setProd_status(rs.getInt("prod_status"));
				prodVO.setProd_pic(rs.getBytes("prod_pic"));
				
			}
			
			// Handle any driver errors
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
		return prodVO;
	}

	@Override
	public List<ProdVO> getAll() {
		
		

		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		ResultSet rs = null;
		List<ProdVO> list = new ArrayList<ProdVO>();
		ProdVO prodVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				prodVO = new ProdVO();
				prodVO.setProd_no(rs.getInt("prod_no"));
				prodVO.setPtr_no(rs.getInt("ptr_no"));
				prodVO.setMa_no(rs.getString("ma_no"));
				prodVO.setProd_name(rs.getString("prod_name"));
				prodVO.setProd_price(rs.getInt("prod_price"));
				prodVO.setProd_detail(rs.getString("prod_detail"));
				prodVO.setProd_status(rs.getInt("prod_status"));
				prodVO.setProd_pic(rs.getBytes("prod_pic"));
				list.add(prodVO);
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
	
//模糊查詢
	@Override
	public List<ProdVO> FuzzySearch(String prod_name) {
		List<ProdVO> list = new ArrayList<ProdVO>();
		ProdVO prodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_FUZZY_QUERY);
			
			pstmt.setString(1, "%"+prod_name+"%");
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				prodVO = new ProdVO();
				prodVO.setProd_no(rs.getInt("prod_no"));
				prodVO.setPtr_no(rs.getInt("ptr_no"));
				prodVO.setMa_no(rs.getString("ma_no"));
				prodVO.setProd_name(rs.getString("prod_name"));
				prodVO.setProd_price(rs.getInt("prod_price"));
				prodVO.setProd_detail(rs.getString("prod_detail"));
				prodVO.setProd_status(rs.getInt("prod_status"));
				prodVO.setProd_pic(rs.getBytes("prod_pic"));
				list.add(prodVO);
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
	
	@Override
	public void changeStatus(ProdVO prodVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProdVO> getAll_status() {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		ResultSet rs = null;
		List<ProdVO> list = new ArrayList<ProdVO>();
		ProdVO prodVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT_STATUS);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				prodVO = new ProdVO();
				prodVO.setProd_no(rs.getInt("prod_no"));
				prodVO.setPtr_no(rs.getInt("ptr_no"));
				prodVO.setMa_no(rs.getString("ma_no"));
				prodVO.setProd_name(rs.getString("prod_name"));
				prodVO.setProd_price(rs.getInt("prod_price"));
				prodVO.setProd_detail(rs.getString("prod_detail"));
				prodVO.setProd_status(rs.getInt("prod_status"));
				prodVO.setProd_pic(rs.getBytes("prod_pic"));
				list.add(prodVO);
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
	
	
	//同個作品不同素材查詢商品		
	@Override
	public ProdVO OthrMaSearch(Integer ptr_no, String ma_no) {
		ProdVO prodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_PTR_MA_PROD);
			
			pstmt.setInt(1, ptr_no);
			pstmt.setString(2, ma_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				prodVO = new ProdVO();
				prodVO.setProd_no(rs.getInt("prod_no"));
				prodVO.setPtr_no(rs.getInt("ptr_no"));
				prodVO.setMa_no(rs.getString("ma_no"));
				prodVO.setProd_name(rs.getString("prod_name"));
				prodVO.setProd_price(rs.getInt("prod_price"));
				prodVO.setProd_detail(rs.getString("prod_detail"));
				prodVO.setProd_status(rs.getInt("prod_status"));
				prodVO.setProd_pic(rs.getBytes("prod_pic"));
				
			}
			
			// Handle any driver errors
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
		return prodVO;
	}

	@Override
	public ProdVO getOneByMa_no(String ma_no) {
		ProdVO prodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_MA);
			
			pstmt.setString(1, ma_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				prodVO = new ProdVO();
				
				prodVO.setMa_no(rs.getString("ma_no"));
				prodVO.setMa_name(rs.getString("ma_name"));
				
				
			}
			
			// Handle any driver errors
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
		return prodVO;
	}
	
	
	
	
	
	
	
		public static void main(String[] args) {

			ProdJDBCDAO dao = new ProdJDBCDAO();

////////			// 新增
//			ProdVO prodVO1 = new ProdVO();
//			prodVO1.setPtr_no(1);
//			prodVO1.setMa_no("L0001");
//			prodVO1.setProd_name("壁虎馬克杯");
//			prodVO1.setProd_price(100);
//			prodVO1.setProd_detail("真的有壁虎666");
//			prodVO1.setProd_status(1);
//			prodVO1.setProd_pic(null);
//			
//			dao.insert(prodVO1);
//			System.out.println("新增成功!!");
//			
//			//修改
//			ProdVO prodVO2 = new ProdVO();
//			prodVO2.setProd_no(1);
//			prodVO2.setPtr_no(1);
//			prodVO2.setMa_no("L0001");
//			prodVO2.setProd_name("閃電鳥馬克杯");
//			prodVO2.setProd_price(100);
//			prodVO2.setProd_detail("吃飽睡睡飽吃");
//			prodVO2.setProd_status(1);
//			prodVO2.setProd_pic(null);
//		
//			dao.update(prodVO2);
//			System.out.println("修改成功");
//			
//			
//			//刪除
//			dao.delete(4);
//			System.out.println("刪除成功");
//			
//			
//			//查詢單列
//			ProdVO prodVO3 = dao.findByPrimaryKey(1);
//			System.out.println("查詢單筆資料:");
//			System.out.println(prodVO3.getProd_no());
//			System.out.println(prodVO3.getPtr_no());
//			System.out.println(prodVO3.getMa_no());
//			System.out.println(prodVO3.getProd_name());
//			System.out.println(prodVO3.getProd_price());
//			System.out.println(prodVO3.getProd_detail());
//			System.out.println(prodVO3.getProd_status());
//			System.out.println(prodVO3.getProd_pic());
//			
//			
//			
//			
//			//查詢全部
//			List<ProdVO> list = dao.getAll();
//			System.out.println("查詢全部資料:");
//			for(ProdVO aProd : list) {
//				System.out.print(aProd.getProd_no()+ ", ");
//				System.out.print(aProd.getPtr_no()+ ", ");
//				System.out.print(aProd.getMa_no()+ ", ");
//				System.out.print(aProd.getProd_name()+ ", ");
//				System.out.print(aProd.getProd_price()+ ", ");
//				System.out.print(aProd.getProd_detail()+ ", ");
//				System.out.print(aProd.getProd_status()+ ", ");
//				System.out.print(aProd.getProd_pic()+ ", ");
//				System.out.println();
//			}
			
			//查詢上架的商品
//			List<ProdVO> list2 = dao.getAll_status();
//			System.out.println("查詢全部資料:");
//			for(ProdVO aProd : list2) {
//				System.out.print(aProd.getProd_no()+ ", ");
//				System.out.print(aProd.getPtr_no()+ ", ");
//				System.out.print(aProd.getMa_no()+ ", ");
//				System.out.print(aProd.getProd_name()+ ", ");
//				System.out.print(aProd.getProd_price()+ ", ");
//				System.out.print(aProd.getProd_detail()+ ", ");
//				System.out.print(aProd.getProd_status()+ ", ");
//				System.out.print(aProd.getProd_pic()+ ", ");
//				System.out.println();
//			}
			
			
			
//			//模糊查詢
//			List<ProdVO> list2 = dao.FuzzySearch("蝴蝶");
//			System.out.println("模糊查詢資料:");
//			for(ProdVO aProd : list2) {
//				System.out.print(aProd.getProd_no()+ ", ");
//				System.out.print(aProd.getPtr_no()+ ", ");
//				System.out.print(aProd.getMa_no()+ ", ");
//				System.out.print(aProd.getProd_name()+ ", ");
//				System.out.print(aProd.getProd_price()+ ", ");
//				System.out.print(aProd.getProd_detail()+ ", ");
//				System.out.print(aProd.getProd_status()+ ", ");
//				System.out.print(aProd.getProd_pic()+ ", ");
//				System.out.println();
//			}
			
			//同作品不同素材
//			ProdVO prodVO3 = dao.OthrMaSearch(1,"L0005");
//			System.out.println("查詢單筆資料:");
//			System.out.println(prodVO3.getProd_no());
//			System.out.println(prodVO3.getPtr_no());
//			System.out.println(prodVO3.getMa_no());
//			System.out.println(prodVO3.getProd_name());
//			System.out.println(prodVO3.getProd_price());
//			System.out.println(prodVO3.getProd_detail());
//			System.out.println(prodVO3.getProd_status());
//			System.out.println(prodVO3.getProd_pic());
			
			
			
			//查詢單列
			ProdVO prodVO3 = dao.getOneByMa_no("L0005");
			System.out.println("查詢單筆資料:");
			System.out.println(prodVO3.getMa_no());
			System.out.println(prodVO3.getMa_name());
			
			
			
			
			
		}

		
		
		
		
		@Override
		public List<ProdVO> MaSearch(String ma_no) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ProdVO> PtrSearch(Integer ptr_no) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ProdVO> getAllptr() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ProdVO> getAllma() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ProdVO> getRelatedProd(Integer ptr_no) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ProdVO getOneByPtr_no(Integer ptr_no) {
			// TODO Auto-generated method stub
			return null;
		}

		

		

		

		

	
	
}
