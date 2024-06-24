package com.card.model_TBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CardJDBCDAO implements CardDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String usrid = "G1";
	String psw = "123456";

	private static final String INSERT_STMT = "INSERT INTO card VALUES (card_seq.NEXTVAL, ?, ?, ?)";

	private static final String UPDATE_STMT = "UPDATE card SET mem_id=?, c_no=? ,c_vdate=? WHERE card_id=?";

	private static final String DELETE_ONE = "DELETE FROM card WHERE card_id=?";
	private static final String DELETE_ALL = "DELETE FROM card WHERE mem_id=?";
	private static final String GET_ONE_STMT = "SELECT card_id, mem_id, c_no, to_char(c_vdate,'yyyy-mm') c_vdate FROM card WHERE card_id=?";
	private static final String GET_ALL_STMT = "SELECT card_id, mem_id, c_no, to_char(c_vdate,'yyyy-mm') c_vdate FROM card ORDER BY card_id";

	@Override
	public void insert(CardVO cardVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, usrid, psw);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, cardVO.getMem_id());
			pstmt.setString(2, cardVO.getC_no());
			pstmt.setDate(3, cardVO.getC_vdate());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void update(CardVO cardVO) {

	}

	@Override
	public void deleteOne(Integer card_id) {

	}

	@Override
	public void deleteAll(String mem_id) {

	}

	@Override
	public CardVO findByPrimaryKey(Integer card_id) {

		return null;
	}

	@Override
	public List<CardVO> getAll() {

		return null;
	}

	public static void main(String[] args) {

		CardJDBCDAO cardDAO = new CardJDBCDAO();
		// ·s¼W
		CardVO cardVO = new CardVO();
		cardVO.setMem_id("M000002");
		cardVO.setC_no("1234567898745");
		cardVO.setC_vdate(java.sql.Date.valueOf("2022-01-01"));

		cardDAO.insert(cardVO);

	}

}
