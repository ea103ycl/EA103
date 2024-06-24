package com.bank.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankJDBCDAO implements BankDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO bank VALUES(bank_seq.NEXTVAL,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT bank_id, mem_id, bn_code, bn_accno, bn_accname FROM BANK ORDER BY bank_id";
	private static final String GET_ONE_STMT = "SELECT bank_id, mem_id, bn_code, bn_accno, bn_accname FROM BANK WHERE bank_id = ?";
	private static final String DELETE_ALL = "DELETE FROM BANK WHERE mem_id = ?";
	private static final String DELETE_ONE = "DELETE FROM BANK WHERE bank_id = ?";
	private static final String UPDATE = "UPDATE bank SET bn_code=?, bn_accno=? ,bn_accname=? WHERE bank_id = ?";

	@Override
	public void insert(BankVO bankVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);

			con = DriverManager.getConnection(url, userid, passwd);

			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, bankVO.getMem_id());
			pstmt.setString(2, bankVO.getBn_code());
			pstmt.setString(3, bankVO.getBn_accno());
			pstmt.setString(4, bankVO.getBn_accname());

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
	public void update(BankVO bankVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, bankVO.getBn_code());
			pstmt.setString(2, bankVO.getBn_accno());
			pstmt.setString(3, bankVO.getBn_accname());
			pstmt.setInt(4, bankVO.getBank_id());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	public void deleteOne(Integer bank_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_ONE);

			pstmt.setInt(1, bank_id);
			pstmt.executeUpdate();
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		catch (SQLException e) {
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
	public void deleteAll(String mem_id) {

		Connection con = null;
		PreparedStatement pstmt = null;
		BankVO bankVO = null;
		List<BankVO> list = new ArrayList<BankVO>();

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_ALL);

			pstmt.setString(1, mem_id);
			pstmt.executeUpdate();
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		catch (SQLException e) {
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
	public BankVO findByPrimaryKey(Integer bank_id) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BankVO bankVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, bank_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bankVO = new BankVO();
				bankVO.setMem_id(rs.getString("mem_id"));
				bankVO.setBn_code(rs.getString("bn_code"));
				bankVO.setBn_accno(rs.getString("bn_accno"));
				bankVO.setBn_accname(rs.getString("bn_accname"));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

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

		return bankVO;
	}

	@Override
	public List<BankVO> getAll() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BankVO> list = new ArrayList<BankVO>();

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BankVO bankVO = new BankVO();
				bankVO.setBank_id(rs.getInt("bank_id"));
				bankVO.setMem_id(rs.getString("mem_id"));
				bankVO.setBn_code(rs.getString("bn_code"));
				bankVO.setBn_accno(rs.getString("bn_accno"));
				bankVO.setBn_accname(rs.getString("bn_accname"));
				list.add(bankVO);
			}

		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

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
		return list;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BankJDBCDAO bankDAO = new BankJDBCDAO();

		// 新增
//		BankVO bankVO1 = new BankVO();
//
//		bankVO1.setMem_id("M000001");
//		bankVO1.setBn_code("820");
//		bankVO1.setBn_accno("123456789123");
//		bankVO1.setBn_accname("郭詠茹");
//
//		bankDAO.insert(bankVO1);

		// 修改
//		BankVO bankVO2 = new BankVO();
//
//		bankVO2.setBn_code("899");
//		bankVO2.setBn_accno("987654321123");
//		bankVO2.setBn_accname("郭詠茹");
//		bankVO2.setBank_id(1005);
//
//		bankDAO.update(bankVO2);

		// 刪除單一會員一個帳戶
//		bankDAO.deleteOne(1005);

		// 刪除單一會員全部帳戶
//		bankDAO.deleteAll("M000001");

		// 查詢單一帳戶byPK
//		int pk = 1003;
//		BankVO bankVO3 = new BankVO();
//		bankVO3 = bankDAO.findByPrimaryKey(pk);
//		System.out.println("流水號:" + pk);
//		System.out.println("會員編號:" + bankVO3.getMem_id());
//		System.out.println(bankVO3.getBn_code());
//		System.out.println(bankVO3.getBn_accno());
//		System.out.println(bankVO3.getBn_accname());
//		System.out.println("--------------------");

		// 查詢全部帳戶
		List<BankVO> list = new ArrayList<BankVO>();
		list = bankDAO.getAll();
		for (BankVO bankVO : list) {
			System.out.println("流水號:" + bankVO.getBank_id());
			System.out.println("會員編號:" + bankVO.getMem_id());
			System.out.println(bankVO.getBn_code());
			System.out.println(bankVO.getBn_accno());
			System.out.println(bankVO.getBn_accname());
			System.out.println("--------------------");
		}

	}

}
