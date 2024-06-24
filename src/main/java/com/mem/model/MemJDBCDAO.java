package com.mem.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import tools.DateTool;

public class MemJDBCDAO implements MemDAO_interface
{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@192.168.99.107:1521/ORCLCDB.localdomain";
	String userid = "C##Caroline";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO MEMBERS VALUES(('M'||LPAD(mem_seq.nextval, 6, '0')), ? , ?, ? , ?, ? , ?, ? , ? , ? , ?, ? , ? ,? ,? , ? ,CURRENT_TIMESTAMP, ? , ? , ? , ?)";

	private static final String UPDATE_PHOTO = "UPDATE MEMBERS SET m_photo =? WHERE mem_id = ?";

	private static final String UPDATE_BALANCE_BY_ID = "UPDATE MEMBERS SET balance = ? WHERE MEM_ID = ?";

	private static final String UPDATE_PSW_BY_ID = "UPDATE MEMBERS SET m_psw = ? WHERE MEM_ID = ?";

	private static final String UPDATE_BY_USER = "UPDATE MEMBERS SET m_name=?, m_gender=?,m_bday=?,m_phone=?,m_mobile=?,m_zip=?,m_city=?,m_addr=?,m_email=? WHERE MEM_ID = ?";

	private static final String UPDATE_BY_STAFF = "UPDATE MEMBERS SET m_active=?, m_public=? ,m_bancount= ? WHERE MEM_ID = ?";

	private static final String GET_ONE_STMT = "SELECT * FROM MEMBERS WHERE mem_id = ?";

	private static final String GET_ONE_BY_ACCNO = "SELECT * FROM MEMBERS WHERE m_accno = ? OR m_email=?";

	private static final String GET_USERID = "SELECT m_accno FROM MEMBERS";

	private static final String GETPASSWORD_BYACCNO = "SELECT m_accno,m_psw FROM MEMBERS";

	private static final String GET_ALL_BY_ID_NAME_ACC = "SELECT * FROM MEMBERS WHERE mem_id LIKE ? OR m_name LIKE ? OR m_accno LIKE ?";

	private static final String GET_ALL_STMT = "SELECT mem_id,lv,m_accno,m_psw,m_name,m_gender,m_bday,m_phone,m_mobile,m_zip,m_city,m_addr,m_email,m_word,"
			+ "m_photo,m_source,m_joindate,m_active,m_public,m_bancount,balance FROM MEMBERS ORDER BY MEM_ID";

	private static final String GET_ALL_DESC = "SELECT mem_id,lv,m_accno,m_psw,m_name,m_gender,m_bday,m_phone,m_mobile,m_zip,m_city,m_addr,m_email,m_word,"
			+ "m_photo,m_source,m_joindate,m_active,m_public,m_bancount,balance FROM MEMBERS ORDER BY MEM_ID DESC";

	private static final String GET_PHOTO = "SELECT m_photo FROM MEMBERS WHERE mem_id = ?";

	// �s�W�|��

	@Override
	public void insert(MemVO memVO)
	{
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setInt(1, memVO.getLv());
				pstmt.setString(2, memVO.getM_accno());
				pstmt.setString(3, memVO.getM_psw());
				pstmt.setString(4, memVO.getM_name());
				pstmt.setString(5, memVO.getM_gender());

				pstmt.setDate(6, memVO.getM_bday());
				pstmt.setString(7, memVO.getM_phone());
				pstmt.setString(8, memVO.getM_mobile());
				pstmt.setInt(9, memVO.getM_zip());
				pstmt.setString(10, memVO.getM_city());

				pstmt.setString(11, memVO.getM_addr());
				pstmt.setString(12, memVO.getM_email());
				pstmt.setString(13, memVO.getM_word());
				pstmt.setBytes(14, memVO.getM_photo());
				pstmt.setInt(15, memVO.getM_source());

				pstmt.setInt(16, memVO.getM_active());
				pstmt.setInt(17, memVO.getM_public());
				pstmt.setInt(18, memVO.getM_bancount());
				pstmt.setInt(19, memVO.getBalance());

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

	}

	// �s�W�j�Y��
	@Override
	public void insertPhoto(String mem_id, byte[] photo)
	{

		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE_PHOTO);

				pstmt.setBytes(1, photo);
				pstmt.setString(2, mem_id);

				pstmt.executeUpdate();

				// �M�Ÿ̭��ѼơA���ШϥΤw���o��PreparedStatement����
				pstmt.clearParameters();

				System.out.println("新增成功");

			} catch (ClassNotFoundException e)
			{
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally
			{
				// �̫إ߶��������귽 (�V�߫إ߶V������)
				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException se)
							{
								System.out.println(se);
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException se)
							{
								System.out.println(se);
							}
					}
			}

	}

	// ��s�l�B
	@Override
	public MemVO updateBalance(int amount, MemVO memVO, Connection con)
	{
		// TODO Auto-generated method stub

		PreparedStatement pstmt = null;

		int oldBalance = memVO.getBalance();

		int newBalance = oldBalance + amount;

		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);

				pstmt = con.prepareStatement(UPDATE_BALANCE_BY_ID);

				pstmt.setInt(1, newBalance);
				pstmt.setString(2, memVO.getMem_id());

				pstmt.executeUpdate();

				memVO.setBalance(newBalance);

				System.out.println("�|��id:" + memVO.getMem_id());
				System.out.println("���[�Ȫ��B:" + amount);
				System.out.println("�[�ȫ�l�B:" + newBalance);

			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e)
			{
				if (con != null)
					{
						try
							{
								// �]�w���exception�o�ͮɤ�catch�϶���
								con.rollback();
								memVO.setBalance(oldBalance);
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
		return memVO;

	}

	// ��s�K�X
	@Override
	public void updatePsw(String mem_id, String m_psw)
	{
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);

				pstmt = con.prepareStatement(UPDATE_PSW_BY_ID);

				pstmt.setString(1, m_psw);
				pstmt.setString(2, mem_id);

				pstmt.executeUpdate();

			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
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

	// �e�x�ק�

	@Override
	public void updateByUser(MemVO memVO)
	{
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE_BY_USER);

				pstmt.setString(1, memVO.getM_name());
				pstmt.setString(2, memVO.getM_gender());
				pstmt.setDate(3, memVO.getM_bday());
				pstmt.setString(4, memVO.getM_phone());
				pstmt.setString(5, memVO.getM_mobile());
				pstmt.setInt(6, memVO.getM_zip());
				pstmt.setString(7, memVO.getM_city());

				pstmt.setString(8, memVO.getM_addr());
				pstmt.setString(9, memVO.getM_email());

				pstmt.setString(10, memVO.getMem_id());

				pstmt.executeUpdate();

			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e)
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

	}

	// ��x�ק�

	@Override
	public void updateByStaff(MemVO memVO)
	{
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE_BY_STAFF);
				// UPDATE MEMBERS SET m_active=?, m_public=? ,m_bancount= ?

				pstmt.setInt(1, memVO.getM_active());
				pstmt.setInt(2, memVO.getM_public());
				pstmt.setInt(3, memVO.getM_bancount());
				pstmt.setString(4, memVO.getMem_id());

				pstmt.executeUpdate();

			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e)
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
	}

	// �d��BY �|���b�� �� �|���H�c
	@Override
	public MemVO findByAccnoEmail(String input)
	{
		// TODO Auto-generated method stub
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_BY_ACCNO);

				pstmt.setString(1, input);
				pstmt.setString(2, input);

				rs = pstmt.executeQuery();

				while (rs.next())
					{
						memVO = new MemVO();
						memVO.setMem_id(rs.getString("mem_id"));
						memVO.setLv(rs.getInt("lv"));
						memVO.setM_accno(rs.getString("m_accno"));

						memVO.setM_psw(rs.getString("m_psw"));
						memVO.setM_name(rs.getString("m_name"));
						memVO.setM_gender(rs.getString("m_gender"));

						memVO.setM_bday(rs.getDate("m_bday"));
						memVO.setM_phone(rs.getString("m_phone"));
						memVO.setM_mobile(rs.getString("m_mobile"));
						memVO.setM_zip(rs.getInt("m_zip"));
						memVO.setM_city(rs.getString("m_city"));

						memVO.setM_addr(rs.getString("m_addr"));
						memVO.setM_email(rs.getString("m_email"));
						memVO.setM_word(rs.getString("m_word"));
						memVO.setM_photo(rs.getBytes("m_photo"));
						memVO.setM_source(rs.getInt("m_source"));
						memVO.setM_joindate(DateTool.timestamp2StringMin(rs.getTimestamp("m_joindate")));

						memVO.setM_active(rs.getInt("m_active"));
						memVO.setM_public(rs.getInt("m_public"));
						memVO.setM_bancount(rs.getInt("m_bancount"));
						memVO.setBalance(rs.getInt("balance"));

					}
			} catch (SQLException e)
			{
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
		return memVO;

	}

	// �d��byPK
	@Override
	public MemVO findByPrimaryKey(String mem_id)
	{
		// TODO Auto-generated method stub
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1, mem_id);
				rs = pstmt.executeQuery();

				while (rs.next())
					{
						memVO = new MemVO(); // ??????
						memVO.setMem_id(rs.getString("mem_id"));
						memVO.setLv(rs.getInt("lv"));
						memVO.setM_accno(rs.getString("m_accno"));

						memVO.setM_psw(rs.getString("m_psw"));
						memVO.setM_name(rs.getString("m_name"));
						memVO.setM_gender(rs.getString("m_gender"));

						memVO.setM_bday(rs.getDate("m_bday"));
						memVO.setM_phone(rs.getString("m_phone"));
						memVO.setM_mobile(rs.getString("m_mobile"));
						memVO.setM_zip(rs.getInt("m_zip"));
						memVO.setM_city(rs.getString("m_city"));

						memVO.setM_addr(rs.getString("m_addr"));
						memVO.setM_email(rs.getString("m_email"));
						memVO.setM_word(rs.getString("m_word"));
						memVO.setM_photo(rs.getBytes("m_photo"));
						memVO.setM_source(rs.getInt("m_source"));
						memVO.setM_joindate(DateTool.timestamp2StringMin(rs.getTimestamp("m_joindate")));

						memVO.setM_active(rs.getInt("m_active"));
						memVO.setM_public(rs.getInt("m_public"));
						memVO.setM_bancount(rs.getInt("m_bancount"));
						memVO.setBalance(rs.getInt("balance"));

					}
			} catch (SQLException e)
			{
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
		return memVO;

	}

	// �d��byPK�η|���W�٩η|���b��
	@Override
	public List<MemVO> findByPKNameAcc(String str)
	{
		// TODO Auto-generated method stub
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_BY_ID_NAME_ACC);
				pstmt.setString(1, "%" + str + "%");
				pstmt.setString(2, "%" + str + "%");
				pstmt.setString(3, "%" + str + "%");

				System.out.println(GET_ALL_BY_ID_NAME_ACC);

				rs = pstmt.executeQuery();

				while (rs.next()) // rs���U���@��, �Y���ȫh�^��ture
					{
						memVO = new MemVO();
						memVO.setMem_id(rs.getString("mem_id"));
						memVO.setLv(rs.getInt("lv"));
						memVO.setM_accno(rs.getString("m_accno"));

						memVO.setM_psw(rs.getString("m_psw"));
						memVO.setM_name(rs.getString("m_name"));
						memVO.setM_gender(rs.getString("m_gender"));

						memVO.setM_bday(rs.getDate("m_bday"));
						memVO.setM_phone(rs.getString("m_phone"));
						memVO.setM_mobile(rs.getString("m_mobile"));
						memVO.setM_zip(rs.getInt("m_zip"));
						memVO.setM_city(rs.getString("m_city"));

						memVO.setM_addr(rs.getString("m_addr"));
						memVO.setM_email(rs.getString("m_email"));
						memVO.setM_word(rs.getString("m_word"));
						memVO.setM_photo(rs.getBytes("m_photo"));
						memVO.setM_source(rs.getInt("m_source"));
						memVO.setM_joindate(DateTool.timestamp2StringMin(rs.getTimestamp("m_joindate")));

						memVO.setM_active(rs.getInt("m_active"));
						memVO.setM_public(rs.getInt("m_public"));
						memVO.setM_bancount(rs.getInt("m_bancount"));
						memVO.setBalance(rs.getInt("balance"));

						list.add(memVO);

					}

			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
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

	// �d�ߥ���
	@Override
	public List<MemVO> getAll()
	{

		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) // rs���U���@��, �Y���ȫh�^��ture
					{
						memVO = new MemVO();
						memVO.setMem_id(rs.getString("mem_id"));
						memVO.setLv(rs.getInt("lv"));
						memVO.setM_accno(rs.getString("m_accno"));

						memVO.setM_psw(rs.getString("m_psw"));
						memVO.setM_name(rs.getString("m_name"));
						memVO.setM_gender(rs.getString("m_gender"));

						memVO.setM_bday(rs.getDate("m_bday"));
						memVO.setM_phone(rs.getString("m_phone"));
						memVO.setM_mobile(rs.getString("m_mobile"));
						memVO.setM_zip(rs.getInt("m_zip"));
						memVO.setM_city(rs.getString("m_city"));

						memVO.setM_addr(rs.getString("m_addr"));
						memVO.setM_email(rs.getString("m_email"));
						memVO.setM_word(rs.getString("m_word"));
						memVO.setM_photo(rs.getBytes("m_photo"));
						memVO.setM_source(rs.getInt("m_source"));
						memVO.setM_joindate(DateTool.timestamp2StringMin(rs.getTimestamp("m_joindate")));

						memVO.setM_active(rs.getInt("m_active"));
						memVO.setM_public(rs.getInt("m_public"));
						memVO.setM_bancount(rs.getInt("m_bancount"));
						memVO.setBalance(rs.getInt("balance"));

						list.add(memVO);

					}

			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
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

	// �d�ߥ���(�s����)
	@Override
	public List<MemVO> getAllDESC()
	{

		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_DESC);
				rs = pstmt.executeQuery();

				while (rs.next()) // rs���U���@��, �Y���ȫh�^��ture
					{
						memVO = new MemVO();
						memVO.setMem_id(rs.getString("mem_id"));
						memVO.setLv(rs.getInt("lv"));
						memVO.setM_accno(rs.getString("m_accno"));

						memVO.setM_psw(rs.getString("m_psw"));
						memVO.setM_name(rs.getString("m_name"));
						memVO.setM_gender(rs.getString("m_gender"));

						memVO.setM_bday(rs.getDate("m_bday"));
						memVO.setM_phone(rs.getString("m_phone"));
						memVO.setM_mobile(rs.getString("m_mobile"));
						memVO.setM_zip(rs.getInt("m_zip"));
						memVO.setM_city(rs.getString("m_city"));

						memVO.setM_addr(rs.getString("m_addr"));
						memVO.setM_email(rs.getString("m_email"));
						memVO.setM_word(rs.getString("m_word"));
						memVO.setM_photo(rs.getBytes("m_photo"));
						memVO.setM_source(rs.getInt("m_source"));
						memVO.setM_joindate(DateTool.timestamp2StringMin(rs.getTimestamp("m_joindate")));

						memVO.setM_active(rs.getInt("m_active"));
						memVO.setM_public(rs.getInt("m_public"));
						memVO.setM_bancount(rs.getInt("m_bancount"));
						memVO.setBalance(rs.getInt("balance"));

						list.add(memVO);

					}

			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
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

	@Override
	public Set<String> getAllUsrId()
	{
		Set<String> set = new TreeSet<String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_USERID);
				rs = pstmt.executeQuery();

				while (rs.next()) // rs���U���@��, �Y���ȫh�^��ture
					{
						set.add(rs.getString("m_accno"));
					}

			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
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

		return set;
	}

	@Override
	public Map<String, String> getAllAccounts()
	{
		// TODO Auto-generated method stub

		Map<String, String> map = new TreeMap<String, String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GETPASSWORD_BYACCNO);
				rs = pstmt.executeQuery();

				while (rs.next()) // rs���U���@��, �Y���ȫh�^��ture
					{
						map.put(rs.getString("m_accno"), rs.getString("m_psw"));
					}

			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
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

		return map;

	}

	// ���o�|���Ӥ�
	@Override
	public byte[] getPhoto(String mem_id)
	{
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		byte[] photo = null;

		try
			{

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_PHOTO);

				pstmt.setString(1, mem_id);
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				photo = rs.getBytes(1);

//			pstmt.setInt(1, 3);
//			ResultSet rs3 = pstmt.executeQuery();
//			rs3.next();
//			InputStream is = rs3.getBinaryStream(1);
//			readPicture(is);

				// �M�Ÿ̭��ѼơA���ШϥΤw���o��PreparedStatement����
				pstmt.clearParameters();

			} catch (ClassNotFoundException ce)
			{
				System.out.println(ce);

			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally
			{
				// �̫إ߶��������귽 (�V�߫إ߶V������)
				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException se)
							{
								System.out.println(se);
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException se)
							{
								System.out.println(se);
							}
					}
			}

		return photo;
	}

	public static void main(String[] args)
	{

		MemJDBCDAO memDao = new MemJDBCDAO();
//		�s�W
//		MemVO memVO1 = new MemVO();
//
//		memVO1.setLv(3);
//		memVO1.setM_accno("test");
//		memVO1.setM_psw("111");
//		memVO1.setM_name("���j��");
//		memVO1.setM_gender("M");
//
//		memVO1.setM_bday(java.sql.Date.valueOf("1991-08-26"));
//		memVO1.setM_phone("0211991191");
//		memVO1.setM_mobile("0919005421");
//		memVO1.setM_zip(242);
//		memVO1.setM_city("�s�_���s����");
//
//		memVO1.setM_addr("������653��");
//		memVO1.setM_email("aaaa@yahoo.com");
//		memVO1.setM_word(null);
//		memVO1.setM_photo(null);
//		memVO1.setM_source(2);
//
//		memVO1.setM_active(2);
//		memVO1.setM_public(1);
//		memVO1.setM_bancount(0);
//		memVO1.setBalance(0);
////
//		memDao.insert(memVO1);

//�ק�K�X		
//		memDao.updatePsw("M000002", "0000000");

//�e�x�ק�

//		MemVO memVO2 = new MemVO();
//		memVO2.setMem_id("M000003");
//		memVO2.setM_name("KKKK");
//		memVO2.setM_gender("F");
//
//		memVO2.setM_bday(java.sql.Date.valueOf("1990-08-16"));
//		memVO2.setM_phone("0277777777");
//		memVO2.setM_mobile("0917777777");
//		memVO2.setM_zip(240);
//		memVO2.setM_city("�s�_���H����");
//
//		memVO2.setM_addr("������777��");
//		memVO2.setM_email("77777@gmail.com");
//		memDao.updateByUser(memVO2);

		// ��x�ק�

//		MemVO memVO3 = new MemVO();
//		memVO3.setMem_id("M000005");
//		memVO3.setM_active(1);
//		memVO3.setM_public(2);
//		memVO3.setM_bancount(3);
//
//		memDao.updateByStaff(memVO3);

		// �d��by�|���b��
//		String accno = "caroline80";

//		String email = "peter123@gmail.com";
//		MemVO mem = new MemVO();
//		mem = memDao.findByAccnoEmail(email);
//
//		System.out.println("----------findbyPK-----------");
//		System.out.println("�|���s��:" + mem.getMem_id() + ", LV:" + mem.getLv());
//		System.out.println(mem.getM_accno());
//		System.out.println(mem.getM_psw());
//		System.out.println(mem.getM_name());
//		System.out.println(mem.getM_gender());
//		System.out.println(mem.getM_bday());
//		System.out.println(mem.getM_phone());
//		System.out.println(mem.getM_mobile());
//		System.out.println(mem.getM_zip());
//		System.out.println(mem.getM_city());
//		System.out.println(mem.getM_addr());
//		System.out.println(mem.getM_email());
//		System.out.println(mem.getM_word());
//		System.out.println(mem.getM_photo());
//		System.out.println(mem.getM_source());
//		System.out.println(mem.getM_joindate());
//		System.out.println(mem.getM_active());
//		System.out.println(mem.getM_public());
//		System.out.println(mem.getM_bancount());
//		System.out.println(mem.getBalance());
//		System.out.println("---------------------");

		// �d��byPK
//		String pk = "M000002";
//		MemVO mem = new MemVO();
//		mem = memDao.findByPrimaryKey(pk);
//
//		System.out.println("----------findbyPK-----------");
//		System.out.println("�|���s��:" + pk + ", LV:" + mem.getLv());
//		System.out.println(mem.getM_accno());
//		System.out.println(mem.getM_psw());
//		System.out.println(mem.getM_name());
//		System.out.println(mem.getM_gender());
//		System.out.println(mem.getM_bday());
//		System.out.println(mem.getM_phone());
//		System.out.println(mem.getM_mobile());
//		System.out.println(mem.getM_zip());
//		System.out.println(mem.getM_city());
//		System.out.println(mem.getM_addr());
//		System.out.println(mem.getM_email());
//		System.out.println(mem.getM_word());
//		System.out.println(mem.getM_photo());
//		System.out.println(mem.getM_source());
//		System.out.println(mem.getM_joindate());
//		System.out.println(mem.getM_active());
//		System.out.println(mem.getM_public());
//		System.out.println(mem.getM_bancount());
//		System.out.println(mem.getBalance());
//		System.out.println("---------------------");

		// �d��byPK/�|���b��/�W��
//		String str = "caroline";
//
//		List<MemVO> list = memDao.findByPKNameAcc(str);
//
//		System.out.println("heeeeeeeee");
//
//		for (MemVO amem : list) {
//			System.out.println("�|���s��:" + amem.getMem_id() + ", LV:" + amem.getLv());
//			System.out.println(amem.getM_accno());
//			System.out.println(amem.getM_psw());
//			System.out.println(amem.getM_name());
//			System.out.println(amem.getM_gender());
//			System.out.println(amem.getM_bday());
//			System.out.println(amem.getM_phone());
//			System.out.println(amem.getM_mobile());
//			System.out.println(amem.getM_zip());
//			System.out.println(amem.getM_city());
//			System.out.println(amem.getM_addr());
//			System.out.println(amem.getM_email());
//			System.out.println(amem.getM_word());
//			System.out.println(amem.getM_photo());
//			System.out.println(amem.getM_source());
//			System.out.println(amem.getM_joindate());
//			System.out.println(amem.getM_active());
//			System.out.println(amem.getM_public());
//			System.out.println(amem.getM_bancount());
//			System.out.println(amem.getBalance());
//			System.out.println("---------------------");
//
//		}

// �d�ߥ���
//		List<MemVO> list = memDao.getAll();
//		for (MemVO amem : list) {
//			System.out.println("�|���s��:" + amem.getMem_id() + ", LV:" + amem.getLv());
//			System.out.println(amem.getM_accno());
//			System.out.println(amem.getM_psw());
//			System.out.println(amem.getM_name());
//			System.out.println(amem.getM_gender());
//			System.out.println(amem.getM_bday());
//			System.out.println(amem.getM_phone());
//			System.out.println(amem.getM_mobile());
//			System.out.println(amem.getM_zip());
//			System.out.println(amem.getM_city());
//			System.out.println(amem.getM_addr());
//			System.out.println(amem.getM_email());
//			System.out.println(amem.getM_word());
//			System.out.println(amem.getM_photo());
//			System.out.println(amem.getM_source());
//			System.out.println(amem.getM_joindate());
//			System.out.println(amem.getM_active());
//			System.out.println(amem.getM_public());
//			System.out.println(amem.getM_bancount());
//			System.out.println(amem.getBalance());
//			System.out.println("---------------------");
//
//		}

		// �d�ߥ���(�Ƨ�DESC�s����)
		List<MemVO> list = memDao.getAllDESC();
		for (MemVO amem : list)
			{
				System.out.println("�|���s��:" + amem.getMem_id() + ", LV:" + amem.getLv());
				System.out.println(amem.getM_accno());
				System.out.println(amem.getM_psw());
				System.out.println(amem.getM_name());
				System.out.println(amem.getM_gender());
				System.out.println(amem.getM_bday());
				System.out.println(amem.getM_phone());
				System.out.println(amem.getM_mobile());
				System.out.println(amem.getM_zip());
				System.out.println(amem.getM_city());
				System.out.println(amem.getM_addr());
				System.out.println(amem.getM_email());
				System.out.println(amem.getM_word());
				System.out.println(amem.getM_photo());
				System.out.println(amem.getM_source());
				System.out.println(amem.getM_joindate());
				System.out.println(amem.getM_active());
				System.out.println(amem.getM_public());
				System.out.println(amem.getM_bancount());
				System.out.println(amem.getBalance());
				System.out.println("---------------------");
			}
		//
//				}

		// �d�ߥ����|���b��
//		Set<String> set = memDao.getAllUsrId();
//
//		for (String accno : set) {
//			System.out.println(accno);
//		}
//		System.out.println("---------------------");
//
//		// �d�ߥ����|���b�K
//		Map<String, String> map = new TreeMap<String, String>();
//		map = memDao.getAllAccounts();
//
//		for (String accnos : map.keySet()) {
//
//			System.out.println("usrid:" + accnos);
//			System.out.println("psw:" + map.get(accnos));
//			System.out.println();
//		}

		// ���o�|���Ӥ�
//
//		byte[] photo = memDao.getPhoto("M000001");
//		System.out.println(photo.length);

	}

}
