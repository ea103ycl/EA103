package com.wda.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WdaJDBADAO implements WdaDAO_interface
{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO wda VALUES (wda_seq.NEXTVAL,?,?,?,?,CURRENT_TIMESTAMP,?,?)";
	private static final String UPDATE = "UPDATE wda SET mem_id=?, bn_code=?,bn_accno=?, bn_accname=?, wda_amount=?, wda_status=? WHERE wda_id = ?";
	private static final String DELETE = "DELETE FROM wda WHERE wda_id = ?";
	private static final String GET_ONE_STMT = "SELECT wda_id, mem_id, bn_code, bn_accno, bn_accname, wda_date, wda_amount, wda_status FROM wda WHERE wda_id = ?";
	private static final String GET_ALL_STMT = "SELECT wda_id, mem_id, bn_code, bn_accno, bn_accname, wda_date, wda_amount, wda_status FROM wda ORDER BY wda_id";

	@Override
	public void insert(WdaVO wdaVO)
	{

		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, wdaVO.getMem_id());
				pstmt.setString(2, wdaVO.getBn_code());
				pstmt.setString(3, wdaVO.getBn_accno());
				pstmt.setString(4, wdaVO.getBn_accname());
				pstmt.setInt(5, wdaVO.getWda_amount());
				pstmt.setInt(6, wdaVO.getWda_status());

				pstmt.executeUpdate();

			} catch (SQLException e)
			{

				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{

				e.printStackTrace();
			} finally
			{

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (Exception e)
							{
								e.printStackTrace();
							}
					}

			}

	}

	@Override
	public void update(WdaVO wdaVO)
	{
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setString(1, wdaVO.getMem_id());
				pstmt.setString(2, wdaVO.getBn_code());
				pstmt.setString(3, wdaVO.getBn_accno());
				pstmt.setString(4, wdaVO.getBn_accname());
				pstmt.setInt(5, wdaVO.getWda_amount());
				pstmt.setInt(6, wdaVO.getWda_status());
				pstmt.setInt(7, wdaVO.getWda_id());

				pstmt.executeUpdate();

			} catch (SQLException e)
			{

				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{

				e.printStackTrace();
			} finally
			{

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (Exception e)
							{
								e.printStackTrace();
							}
					}

			}

	}

	@Override
	public void delete(Integer wda_id)
	{
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, wda_id);
				pstmt.executeUpdate();

			} catch (ClassNotFoundException e)
			{

				e.printStackTrace();
			}

		catch (SQLException e)
			{
				e.printStackTrace();

			} finally
			{
				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
			}

	}

	@Override
	public WdaVO findByPrimaryKey(Integer wda_id)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WdaVO wdaVO = null;

		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setInt(1, wda_id);
				rs = pstmt.executeQuery();

				while (rs.next())
					{

						wdaVO = new WdaVO();
						wdaVO.setWda_id(rs.getInt("wda_id"));
						wdaVO.setMem_id(rs.getString("mem_id"));
						wdaVO.setBn_code(rs.getString("bn_code"));
						wdaVO.setBn_accno(rs.getString("bn_accno"));
						wdaVO.setBn_accname(rs.getString("bn_accname"));
						wdaVO.setWda_date(rs.getTimestamp("wda_date"));
						wdaVO.setWda_amount(rs.getInt("wda_amount"));
						wdaVO.setWda_status(rs.getInt("wda_status"));
					}

			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{

				e.printStackTrace();
			} finally
			{

				if (rs != null)
					{
						try
							{
								rs.close();
							} catch (SQLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}

			}

		return wdaVO;

	}

	@Override
	public List<WdaVO> getAll()
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WdaVO wdaVO = null;
		List<WdaVO> list = new ArrayList<WdaVO>();

		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);

				rs = pstmt.executeQuery();

				while (rs.next())
					{

						wdaVO = new WdaVO();
						wdaVO.setWda_id(rs.getInt("wda_id"));
						wdaVO.setMem_id(rs.getString("mem_id"));
						wdaVO.setBn_code(rs.getString("bn_code"));
						wdaVO.setBn_accno(rs.getString("bn_accno"));
						wdaVO.setBn_accname(rs.getString("bn_accname"));
						wdaVO.setWda_date(rs.getTimestamp("wda_date"));
						wdaVO.setWda_amount(rs.getInt("wda_amount"));
						wdaVO.setWda_status(rs.getInt("wda_status"));
						list.add(wdaVO);
					}

			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{

				e.printStackTrace();
			} finally
			{

				if (rs != null)
					{
						try
							{
								rs.close();
							} catch (SQLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}

			}

		return list;

	}

	public static void main(String[] args)
	{

		WdaJDBADAO wdaDAO = new WdaJDBADAO();

		// 新增
//		WdaVO wdaVO = new WdaVO();
//		wdaVO.setMem_id("M000003");
//		wdaVO.setBn_code("812");
//		wdaVO.setBn_accno("99888123456789");
//		wdaVO.setBn_accname("王曉明");
//		wdaVO.setWda_amount(2000);
//		wdaVO.setStatus(1);
//
//		wdaDAO.insert(wdaVO);

		// 修改
//		WdaVO wdaVO1 = new WdaVO();
//		wdaVO1.setMem_id("M000001");
//		wdaVO1.setBn_code("812");
//		wdaVO1.setBn_accno("99888123456789");
//		wdaVO1.setBn_accname("郭詠茹");
//		wdaVO1.setWda_amount(2000);
//		wdaVO1.setWda_status(1);
//		wdaVO1.setWda_id(5004);
//
//		wdaDAO.update(wdaVO1);

		// 刪除
//		wdaDAO.delete(5004);
		// 查詢byPK
//		int pk = 5005;
//		WdaVO wdaVO2 = new WdaVO();
//		wdaVO2 = wdaDAO.findByPrimaryKey(pk);
//		System.out.println("流水號:" + pk);
//		System.out.println("會員編號:" + wdaVO2.getMem_id());
//		System.out.println(wdaVO2.getBn_code());
//		System.out.println(wdaVO2.getBn_accno());
//		System.out.println(wdaVO2.getBn_accname());
//		System.out.println(wdaVO2.getWda_date());
//		System.out.println(wdaVO2.getWda_amount());
//		System.out.println(wdaVO2.getWda_status());
//
//		System.out.println("--------------------");

		// 查詢全部

		List<WdaVO> list = new ArrayList<WdaVO>();
		list = wdaDAO.getAll();

		for (WdaVO wdaVO : list)
			{
				System.out.println("流水號:" + wdaVO.getWda_id());
				System.out.println("會員編號:" + wdaVO.getMem_id());
				System.out.println(wdaVO.getBn_code());
				System.out.println(wdaVO.getBn_accno());
				System.out.println(wdaVO.getBn_accname());
				System.out.println(wdaVO.getWda_date());
				System.out.println(wdaVO.getWda_amount());
				System.out.println(wdaVO.getWda_status());

				System.out.println("--------------------");
			}

	}

}
