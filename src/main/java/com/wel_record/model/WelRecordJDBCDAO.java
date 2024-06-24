package com.wel_record.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mem.model.MemJDBCDAO;
import com.mem.model.MemVO;

import tools.DateTool;

public class WelRecordJDBCDAO implements WelRecordDAO_interface
{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G1";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO wel_record VALUES(tns_seq.NEXTVAL,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String INSERT_ALL = "INSERT INTO wel_record VALUES(tns_seq.NEXTVAL,?,?,?,?,?)";
	private static final String GET_ONE_STMT = "SELECT tns_id, mem_id,tns_src,order_id,tns_amount,tns_time FROM wel_record WHERE tns_id = ?";
	private static final String GET_WELRECORDS_BY_MEMID = "SELECT tns_id, mem_id,tns_src,order_id,tns_amount,tns_time FROM wel_record WHERE mem_id = ? ORDER BY tns_id DESC";
	private static final String GET_ALL_STMT = "SELECT tns_id, mem_id,tns_src,order_id,tns_amount,tns_time FROM wel_record ORDER BY tns_id";
	private static final String GET_RECORDS_BY_SRC = "SELECT tns_id, mem_id,tns_src,order_id,tns_amount,tns_time FROM wel_record WHERE tns_src = ? ORDER BY tns_id DESC";
	private static final String GET_RECORDS_AMONG_SRC = "SELECT tns_id, mem_id,tns_src,order_id,tns_amount,tns_time FROM wel_record WHERE tns_src BETWEEN ? AND ? ORDER BY tns_src";
	private static final String UPDATE_ORDERID = "UPDATE wel_record SET order_id = ? WHERE MEM_ID = ? AND tns_id =(select max(tns_id) from wel_record WHERE MEM_ID = ?)";

//新增一筆交易紀錄
	@Override
	public void insert(WelRecordVO welRecordVO)
	{
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		MemJDBCDAO memDao = new MemJDBCDAO();

		try
			{

				Class.forName(driver);

				con = DriverManager.getConnection(url, userid, passwd);

				con.setAutoCommit(false);

				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, welRecordVO.getMem_id());
				pstmt.setInt(2, welRecordVO.getTns_src());
				pstmt.setString(3, welRecordVO.getOrder_id());
				pstmt.setInt(4, welRecordVO.getTns_amount());

				pstmt.executeUpdate();

				MemVO memVO = new MemVO();
				memVO = memDao.findByPrimaryKey(welRecordVO.getMem_id());
				memDao.updateBalance(welRecordVO.getTns_amount(), memVO, con);

				con.commit();
				con.setAutoCommit(true);

			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{
				if (con != null)
					{
						try
							{
								// 3●設定於當有exception發生時之catch區塊內
								con.rollback();

							} catch (SQLException excep)
							{
								throw new RuntimeException("rollback error occured. " + excep.getMessage());
							}
					}
				throw new RuntimeException("A database error occured. " + e.getMessage());

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
	public void insert4FakeData(WelRecordVO welRecordVO)
	{
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{

				Class.forName(driver);

				con = DriverManager.getConnection(url, userid, passwd);

				con.setAutoCommit(false);

				pstmt = con.prepareStatement(INSERT_ALL);

				pstmt.setString(1, welRecordVO.getMem_id());
				pstmt.setInt(2, welRecordVO.getTns_src());
				pstmt.setString(3, welRecordVO.getOrder_id());
				pstmt.setInt(4, welRecordVO.getTns_amount());
				pstmt.setTimestamp(5, Timestamp.valueOf(welRecordVO.getTns_time()));

				pstmt.executeUpdate();

			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
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

	// 修改單一會員最新一筆訂單編號
	@Override
	public WelRecordVO updateOrderId(String order_id, String mem_id)
	{
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		WelRecordVO welRecordVO = null;
		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);

				pstmt = con.prepareStatement(UPDATE_ORDERID);

				pstmt.setString(1, order_id);
				pstmt.setString(2, mem_id);
				pstmt.setString(3, mem_id);

				pstmt.executeUpdate();

			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
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
		return welRecordVO;

	}

//查詢by交易流水號
	@Override
	public WelRecordVO findByPrimaryKey(Integer tns_id)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WelRecordVO welRecordVO = null;

		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setInt(1, tns_id);
				rs = pstmt.executeQuery();

				while (rs.next())
					{
						welRecordVO = new WelRecordVO();
						welRecordVO.setTns_id(rs.getInt("tns_id"));
						welRecordVO.setMem_id(rs.getString("mem_id"));
						welRecordVO.setTns_src(rs.getInt("tns_src"));
						welRecordVO.setOrder_id(rs.getString("order_id"));
						welRecordVO.setTns_amount(rs.getInt("tns_amount"));
						welRecordVO.setTns_time(DateTool.timestamp2StringSec(rs.getTimestamp("tns_time")));
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

		return welRecordVO;
	}

	// 查詢單一會員交易紀錄
	@Override
	public List<WelRecordVO> getWelRecordByMemID(String mem_id)
	{

		List<WelRecordVO> list = new ArrayList<WelRecordVO>();
		WelRecordVO welRecordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_WELRECORDS_BY_MEMID);
				pstmt.setString(1, mem_id);
				rs = pstmt.executeQuery();

				while (rs.next())
					{
						welRecordVO = new WelRecordVO();
						welRecordVO.setTns_id(rs.getInt("tns_id"));
						welRecordVO.setMem_id(rs.getString("mem_id"));
						welRecordVO.setTns_src(rs.getInt("tns_src"));
						welRecordVO.setOrder_id(rs.getString("order_id"));
						welRecordVO.setTns_amount(rs.getInt("tns_amount"));
						welRecordVO.setTns_time(DateTool.timestamp2StringSec(rs.getTimestamp("tns_time")));
						list.add(welRecordVO);
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

	// 查詢指定來源交易紀錄
	@Override
	public List<WelRecordVO> getWelRecordBySrc(int tns_src)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WelRecordVO welRecordVO = null;
		List<WelRecordVO> list = new ArrayList<WelRecordVO>();

		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_RECORDS_BY_SRC);
				pstmt.setInt(1, tns_src);

				rs = pstmt.executeQuery();

				while (rs.next())
					{
						welRecordVO = new WelRecordVO();
						welRecordVO.setTns_id(rs.getInt("tns_id"));
						welRecordVO.setMem_id(rs.getString("mem_id"));
						welRecordVO.setTns_src(rs.getInt("tns_src"));
						welRecordVO.setOrder_id(rs.getString("order_id"));
						welRecordVO.setTns_amount(rs.getInt("tns_amount"));
						welRecordVO.setTns_time(DateTool.timestamp2StringSec(rs.getTimestamp("tns_time")));
						list.add(welRecordVO);
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

	// 查詢指定來源區間交易紀錄
	@Override
	public List<WelRecordVO> getWelRecordAmongSrc(int tns_srcStart, int tns_srcEnd)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WelRecordVO welRecordVO = null;
		List<WelRecordVO> list = new ArrayList<WelRecordVO>();

		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_RECORDS_AMONG_SRC);
				pstmt.setInt(1, tns_srcStart);
				pstmt.setInt(2, tns_srcEnd);

				rs = pstmt.executeQuery();

				while (rs.next())
					{
						welRecordVO = new WelRecordVO();
						welRecordVO.setTns_id(rs.getInt("tns_id"));
						welRecordVO.setMem_id(rs.getString("mem_id"));
						welRecordVO.setTns_src(rs.getInt("tns_src"));
						welRecordVO.setOrder_id(rs.getString("order_id"));
						welRecordVO.setTns_amount(rs.getInt("tns_amount"));
						welRecordVO.setTns_time(DateTool.timestamp2StringSec(rs.getTimestamp("tns_time")));
						list.add(welRecordVO);
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

	// 查詢全部交易紀錄
	@Override
	public List<WelRecordVO> getAll()
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WelRecordVO welRecordVO = null;
		List<WelRecordVO> list = new ArrayList<WelRecordVO>();

		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);

				rs = pstmt.executeQuery();

				while (rs.next())
					{
						welRecordVO = new WelRecordVO();
						welRecordVO.setTns_id(rs.getInt("tns_id"));
						welRecordVO.setMem_id(rs.getString("mem_id"));
						welRecordVO.setTns_src(rs.getInt("tns_src"));
						welRecordVO.setOrder_id(rs.getString("order_id"));
						welRecordVO.setTns_amount(rs.getInt("tns_amount"));
						welRecordVO.setTns_time(DateTool.timestamp2StringSec(rs.getTimestamp("tns_time")));
						list.add(welRecordVO);
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
		// TODO Auto-generated method stub
		WelRecordJDBCDAO welRecordDAO = new WelRecordJDBCDAO();
		// 新增
//		WelRecordVO welRecordVO = new WelRecordVO();
//		welRecordVO.setMem_id("M000003");
//		welRecordVO.setTns_src(10);
//		welRecordVO.setOrder_id(null);
//		welRecordVO.setTns_amount(-1000);
//
//		welRecordDAO.insert(welRecordVO);

		// 查詢單一錢包紀錄
//		WelRecordVO welRecordVO1 = new WelRecordVO();
//		welRecordVO1 = welRecordDAO.findByPrimaryKey(4002);
//		System.out.println(welRecordVO1.getTns_id());
//		System.out.println(welRecordVO1.getMem_id());
//
//		String str = "";
//		int src = welRecordVO1.getTns_src();
//		str = checkTnsSrc(src);
//
//		System.out.println(str);
//		System.out.println(welRecordVO1.getOrder_id());
//		System.out.println(welRecordVO1.getTns_amount());
//		System.out.println(welRecordVO1.getTns_time());

		// 查詢單一會員錢包紀錄

//		List<WelRecordVO> list = welRecordDAO.getWelRecordByMemID("M000002");
//
//		for (WelRecordVO welRecordVO1 : list) {
//
//			System.out.println(welRecordVO1.getTns_id());
//			System.out.println(welRecordVO1.getMem_id());
//
//			int src = welRecordVO1.getTns_src();
//			System.out.println(checkTnsSrc(src));
//
//			System.out.println("來源訂單號:" + welRecordVO1.getOrder_id());
//			System.out.println("交易金額:" + welRecordVO1.getTns_amount());
//			System.out.println("交易時間" + welRecordVO1.getTns_time());
//			System.out.println("---------------------");
//
//		}

		// 查詢特定交易來源錢包紀錄

//		List<WelRecordVO> list = new ArrayList<WelRecordVO>();
//		list = welRecordDAO.getWelRecordBySrc(40);
//
//		for (WelRecordVO welRecordVO1 : list) {
//			System.out.println(welRecordVO1.getTns_id());
//			System.out.println(welRecordVO1.getMem_id());
//
//			int src = welRecordVO1.getTns_src();
//			System.out.println(checkTnsSrc(src));
//
//			System.out.println(welRecordVO1.getOrder_id());
//			System.out.println(welRecordVO1.getTns_amount());
//			System.out.println(welRecordVO1.getTns_time());
//			System.out.println("---------------------");
//		}

		// 查詢特定交易來源區間錢包紀錄

//		List<WelRecordVO> list1 = new ArrayList<WelRecordVO>();
//		list1 = welRecordDAO.getWelRecordAmongSrc(40, 43);
//
//		for (WelRecordVO welRecordVO1 : list1) {
//			System.out.println(welRecordVO1.getTns_id());
//			System.out.println(welRecordVO1.getMem_id());
//
//			int src = welRecordVO1.getTns_src();
//			System.out.println(checkTnsSrc(src));
//
//			System.out.println(welRecordVO1.getOrder_id());
//			System.out.println(welRecordVO1.getTns_amount());
//			System.out.println(welRecordVO1.getTns_time());
//			System.out.println("---------------------");
//		}

		// 查詢全部錢包紀錄

		List<WelRecordVO> list = new ArrayList<WelRecordVO>();
		list = welRecordDAO.getAll();

		for (WelRecordVO welRecordVO1 : list)
			{
				System.out.println(welRecordVO1.getTns_id());
				System.out.println(welRecordVO1.getMem_id());

				int src = welRecordVO1.getTns_src();
				System.out.println(checkTnsSrc(src));

				System.out.println(welRecordVO1.getOrder_id());
				System.out.println(welRecordVO1.getTns_amount());
				System.out.println(welRecordVO1.getTns_time());
				System.out.println("---------------------");
			}

	}

	// 可考慮用map來寫
	public static String checkTnsSrc(int src)
	{
		String str = "";
		switch (src)
		{
		case 10:
			str = "會員儲值";
			break;
		case 20:
			str = "會員提款";
			break;
		case 30:
			str = "平台撥款-一般購買分潤";
			break;
		case 31:
			str = "平台撥款-預購分潤";
			break;
		case 32:
			str = "平台撥款-競標分潤";
			break;
		case 33:
			str = "平台撥款-見面會分潤";
			break;
		case 34:
			str = "平台撥款-預購折扣金";
			break;
		case 40:
			str = "平台扣款-一般購買訂單";
			break;
		case 41:
			str = "平台扣款-預購訂單";
			break;
		case 42:
			str = "平台扣款-競標訂單";
			break;
		case 43:
			str = "平台扣款-見面會";
			break;
		}
		return str;
	}

}
