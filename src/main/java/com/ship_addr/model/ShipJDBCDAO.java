package com.ship_addr.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShipJDBCDAO implements ShipDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO ship_addr VALUES(ship_seq.NEXTVAL,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE ship_addr SET sh_zip=?, sh_name=? ,sh_phone=?,sh_addr=? WHERE ship_id = ?";
	private static final String DELETE_ONE = "DELETE FROM ship_addr WHERE ship_id = ?";
	private static final String DELETE_ALL = "DELETE FROM ship_addr WHERE mem_id = ?";
	private static final String GET_ONE_STMT = "SELECT ship_id, mem_id, sh_zip, sh_name, sh_phone, sh_addr FROM ship_addr WHERE ship_id = ?";
	private static final String GET_ALL_STMT = "SELECT ship_id, mem_id, sh_zip, sh_name, sh_phone, sh_addr FROM ship_addr ORDER BY ship_id";

	@Override
	public void insert(ShipVO shipVO) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);

			con = DriverManager.getConnection(url, userid, passwd);

			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, shipVO.getMem_id());
			pstmt.setInt(2, shipVO.getSh_zip());
			pstmt.setString(3, shipVO.getSh_name());
			pstmt.setString(4, shipVO.getSh_phone());
			pstmt.setString(5, shipVO.getSh_addr());

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
	public void update(ShipVO shipVO) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, shipVO.getSh_zip());
			pstmt.setString(2, shipVO.getSh_name());
			pstmt.setString(3, shipVO.getSh_phone());
			pstmt.setString(4, shipVO.getSh_addr());
			pstmt.setInt(5, shipVO.getShip_id());

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
	public void deleteOne(Integer ship_id) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_ONE);

			pstmt.setInt(1, ship_id);
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
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
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_ALL);

			pstmt.setString(1, mem_id);
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
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
	public ShipVO findByPrimaryKey(Integer ship_id) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ShipVO shipVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, ship_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				shipVO = new ShipVO();
				shipVO.setMem_id(rs.getString("mem_id"));
				shipVO.setSh_zip(rs.getInt("sh_zip"));
				shipVO.setSh_name(rs.getString("sh_name"));
				shipVO.setSh_phone(rs.getString("sh_phone"));
				shipVO.setSh_addr(rs.getString("sh_addr"));

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

		return shipVO;

	}

	@Override
	public List<ShipVO> getAll() {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ShipVO shipVO = null;
		List<ShipVO> list = new ArrayList<ShipVO>();

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				shipVO = new ShipVO();
				shipVO.setShip_id(rs.getInt("ship_id"));
				shipVO.setMem_id(rs.getString("mem_id"));
				shipVO.setSh_zip(rs.getInt("sh_zip"));
				shipVO.setSh_name(rs.getString("sh_name"));
				shipVO.setSh_phone(rs.getString("sh_phone"));
				shipVO.setSh_addr(rs.getString("sh_addr"));

				list.add(shipVO);
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

		return list;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ShipJDBCDAO shipDAO = new ShipJDBCDAO();

		// 新增
//		ShipVO shipVO1 = new ShipVO();
//
//		shipVO1.setMem_id("M000003");
//		shipVO1.setSh_zip(242);
//		shipVO1.setSh_name("吳詠智");
//		shipVO1.setSh_phone("0919009900");
//		shipVO1.setSh_addr("台北市中山北路二段76號");
//
//		shipDAO.insert(shipVO1);

		// 修改
//		ShipVO shipVO2 = new ShipVO();
//
//		shipVO2.setSh_zip(270);
//		shipVO2.setSh_name("吳曉智");
//		shipVO2.setSh_phone("0987887788");
//		shipVO2.setSh_addr("台北市中山北路二段78號");
//		shipVO2.setShip_id(2003);
//		shipDAO.update(shipVO2);

		// 刪除單一會員一個收件地址
//		shipDAO.deleteOne(2006);

		// 刪除單一會員全部收件地址
//		shipDAO.deleteAll("M000003");

		// 查詢單一收件地址byPK
//		int pk = 2003;
//		ShipVO shipVO3 = new ShipVO();
//		shipVO3 = shipDAO.findByPrimaryKey(pk);
//		System.out.println("流水號:" + pk);
//		System.out.println("會員編號:" + shipVO3.getMem_id());
//		System.out.println(shipVO3.getSh_zip());
//		System.out.println(shipVO3.getSh_name());
//		System.out.println(shipVO3.getSh_phone());
//		System.out.println(shipVO3.getSh_addr());
//		System.out.println("--------------------");

		// 查詢全部收件地址

		List<ShipVO> list = new ArrayList<ShipVO>();
		list = shipDAO.getAll();

		for (ShipVO shipVO : list) {
			System.out.println("流水號:" + shipVO.getShip_id());
			System.out.println("會員編號:" + shipVO.getMem_id());
			System.out.println(shipVO.getSh_zip());
			System.out.println(shipVO.getSh_name());
			System.out.println(shipVO.getSh_phone());
			System.out.println(shipVO.getSh_addr());
			System.out.println("--------------------");
		}

	}
}
