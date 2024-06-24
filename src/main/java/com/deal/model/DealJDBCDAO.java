package com.deal.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.detail.model.DetailVO;


public class DealJDBCDAO implements DealDAO_interface{
	

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "G1";
		String passwd = "123456";
		
		
		
		private static final String GET_ONE_STMT = "SELECT * FROM members WHERE mem_id = ?";
		private static final String INSERT_STMT = "INSERT INTO WEL_RECORD(TNS_ID, ORDER_ID, MEM_ID, TNS_SRC, TNS_AMOUNT, TNS_TIME) VALUES(tns_seq.nextval,?,?,?,?,CURRENT_TIMESTAMP)";
		
		
		@Override
		public DealVO findByPrimaryKey(String mem_id) {
			DealVO dealVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);
				
				pstmt.setString(1, mem_id);
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					dealVO = new DealVO();
					dealVO.setBalance(rs.getInt("balance"));
					
					
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
			return dealVO;
		}
		
		
		
		public static void main(String[] args) {

			DealJDBCDAO dao = new DealJDBCDAO();
			
			//�d�߳�C
			DealVO dealVO = dao.findByPrimaryKey("M000001");
			System.out.println(dealVO.getBalance());
			
			System.out.println("查單筆成功");
		}



		@Override
		public void insert(String or_no, Connection con, String mem_id, Integer tns_src, Integer tns_amount) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void update_balance(Connection con, String mem_id, Integer balance) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public DealVO find_Memid_byPtr(Integer ptr_no) {
			// TODO Auto-generated method stub
			return null;
		}



		



		
		
		
	
	
}
