package com.preproduct.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.event_p.model.Event_PVO;
import com.material.model.Material_Data_VO;//��Jeff�Q��
import com.preproduct.model.PreProductVO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;

public class PreProductDAO implements PreProductDAO_interface{
	private static  DataSource ds=null;
	PreparedStatement pstmt=null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	 
	private static final String INSERT_STMT = "INSERT INTO PRE_PRODUCT (PO_PROD_NO,EVENT_P_NO,MA_NO,PO_START,PO_END,PO_PRICE,PO_DETAIL) "
			+ "VALUES (PRE_PRODUCT_SEQ.NEXTVAL,?,?,?,?,?,?)";
	private static final String INSERT_ByRanking_STMT = "INSERT INTO PRE_PRODUCT ( PO_PROD_NO, EVENT_P_NO, MA_NO, PO_START, PO_END,PO_PRICE, PO_DETAIL) SELECT PRE_PRODUCT_SEQ.NEXTVAL, EVENT_P_NO, ?, ?, ?,?, ? FROM EVENT_P E WHERE E.VOTE_RANK BETWEEN 1 AND 10 AND E.EVENT_NO = (SELECT MAX(T.EVENT_NO) FROM EVENT T WHERE T.EVENT_STAT = 3)";
	private static final String GET_ONE_STMT = "SELECT * FROM PRE_PRODUCT WHERE PO_PROD_NO = ?";
	private static final String UPDATE = "UPDATE PRE_PRODUCT SET event_p_no=?, ma_no=?, po_start=?, po_end=?, po_price=?, po_detail=? WHERE po_prod_no=?";
	private static final String SWITCH = "UPDATE PRE_PRODUCT SET po_start=?, po_end=? WHERE po_prod_no=?";
	private static final String DELETE = "DELETE FROM PRE_PRODUCT WHERE po_prod_no=?";
	private static final String GET_ALLOF_PREPRODUCT_IN_ESHOP = "SELECT * FROM PRE_PRODUCT WHERE SYSDATE BETWEEN PO_START AND PO_END";
	private static final String GET_ALLOF_PREPRODUCT = "SELECT * FROM EVENT_P WHERE VOTE_RANK BETWEEN 1 AND 10 AND EVENT_NO = (SELECT MAX(EVENT_NO) FROM EVENT WHERE EVENT_STAT = 3)";
	//�Ω���o�����Ϥ� By MA_NO
	private static final String GET_ManoIMG_ByMano_STMT = "SELECT MA_PHOTO FROM MATERIAL_DATA WHERE MA_NO = ? ";
	//�Q�ΦW����@�~(�|���ϥ�)
	private static final String GET_Eventno_ByPreproduct_STMT = "SELECT * FROM EVENT_P WHERE VOTE_RANK = ? AND EVENT_NO = (SELECT MAX(EVENT_NO) FROM EVENT WHERE EVENT_STAT = 3)";
	private static final String GET_ALL = "SELECT * FROM PRE_PRODUCT ORDER BY po_end DESC,PO_PROD_NO ASC";
	
	@Override
	public void insert(PreProductVO preproductVO) {
		Connection con=null;
		PreparedStatement pstmt=null;
		System.out.println("********DAO - ����insert********");
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, preproductVO.getEvent_p_no());
			pstmt.setString(2, preproductVO.getMa_no());
			pstmt.setTimestamp(3, preproductVO.getPo_start());
			pstmt.setTimestamp(4, preproductVO.getPo_end());
			pstmt.setInt(5, preproductVO.getPo_price());
			pstmt.setString(6, preproductVO.getPo_detail());
			
			System.out.println("********DAO - ����executeUpdate()********");
			pstmt.executeUpdate();
			System.out.println("********���}DAO********");
			
		}catch (SQLException e) {
			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	public void insertByRanking(PreProductVO preproductVO) {
		Connection con=null;
		PreparedStatement pstmt=null;
		System.out.println("********DAO - ����insertByRanking********");

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_ByRanking_STMT);
			pstmt.setString(1, preproductVO.getMa_no());
			pstmt.setTimestamp(2, preproductVO.getPo_start());
			pstmt.setTimestamp(3, preproductVO.getPo_end());
			pstmt.setInt(4, preproductVO.getPo_price());
			pstmt.setString(5, preproductVO.getMa_no());
			System.out.println("********DAO - ����executeUpdate********");
			pstmt.executeUpdate();
			System.out.println("********���}DAO********");
			
		}catch (SQLException e) {
			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public PreProductVO findByPrimaryKey(String po_prod_no) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		System.out.println("********DAO - ����findByPrimaryKey********");
		PreProductVO preproductVO = null;

		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, po_prod_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				preproductVO = new PreProductVO();
				preproductVO.setPo_prod_no(rs.getString("po_prod_no"));
				preproductVO.setEvent_p_no(rs.getInt("event_p_no"));
				preproductVO.setMa_no(rs.getString("ma_no"));
				preproductVO.setPo_start(rs.getTimestamp("po_start"));
				preproductVO.setPo_end(rs.getTimestamp("po_end"));
				preproductVO.setPo_price(rs.getInt("po_price"));
				preproductVO.setPo_detail(rs.getString("po_detail"));
			}
		}catch (SQLException e) {
			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return preproductVO;
	}
	
	@Override
	public void update(PreProductVO pre_productVO) {
		System.out.println("********DAO - ����update********");
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, pre_productVO.getEvent_p_no());
			pstmt.setString(2, pre_productVO.getMa_no());
			pstmt.setTimestamp(3, pre_productVO.getPo_start());
			pstmt.setTimestamp(4, pre_productVO.getPo_end());
			pstmt.setInt(5, pre_productVO.getPo_price());
			pstmt.setString(6, pre_productVO.getPo_detail());
			pstmt.setString(7, pre_productVO.getPo_prod_no());
			System.out.println("********DAO - ����executeUpdate********");
			pstmt.executeUpdate();
			System.out.println("********���}DAO********");
			
		}catch (SQLException e) {
			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	public void switchPreProduct(PreProductVO pre_productVO) {
		System.out.println("********DAO - ����switchPreProduct********");
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SWITCH);
			
			pstmt.setTimestamp(1, pre_productVO.getPo_start());
			pstmt.setTimestamp(2, pre_productVO.getPo_end());
			pstmt.setString(3, pre_productVO.getPo_prod_no());
			System.out.println("********DAO - ����executeUpdate********");
			pstmt.executeUpdate();
			System.out.println("********���}DAO********");
		}catch (SQLException e) {
			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void delete(String po_prod_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, po_prod_no);
			pstmt.executeUpdate();
		}catch (SQLException e) {
			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
		} finally {
			if(pstmt != null) {
				try {pstmt.close();} 
				catch (SQLException e) {e.printStackTrace(System.err);}
			}
			if(con != null) {
				try {con.close();} 
				catch (SQLException e) {e.printStackTrace(System.err);}
			}
		}
	}
	
	
	public List<PreProductVO> GET_ALLOF_PREPRODUCT() {
	
		List<PreProductVO> list = new ArrayList<PreProductVO>();
		PreProductVO preproductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALLOF_PREPRODUCT);
			rs = pstmt.executeQuery();
	
			
			while(rs.next()) {
				preproductVO = new PreProductVO();
				preproductVO.setPo_prod_no(rs.getString("po_prod_no"));
				preproductVO.setEvent_p_no(rs.getInt("event_p_no"));
				preproductVO.setMa_no(rs.getString("ma_no"));
				preproductVO.setPo_start(rs.getTimestamp("po_start"));
				preproductVO.setPo_end(rs.getTimestamp("po_end"));
				preproductVO.setPo_price(rs.getInt("po_price"));
				preproductVO.setPo_detail(rs.getString("po_detail"));
				list.add(preproductVO);
			}
	

		}catch (SQLException e) {
			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		System.out.println("********���}DAO********");
		return list;
	}
	
	@Override 	//�Ω���o�����Ϥ� By MA_NO
	public Set<Material_Data_VO> getManoByPreproduct(String ma_no) {
		System.out.println("********DAO - ����getManoByPreproduct********");
		Set<Material_Data_VO> set = new LinkedHashSet<Material_Data_VO>();
		Material_Data_VO materialdataVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ManoIMG_ByMano_STMT);
				pstmt.setString(1, ma_no);
				rs = pstmt.executeQuery();
				System.out.println("********DAO - ����executeQuery********");
				
				while(rs.next()) {
					materialdataVO = new Material_Data_VO();
					materialdataVO.setMaNo(rs.getString("ma_no"));
					materialdataVO.setMaTyNo(rs.getString("ma_ty_no"));
					materialdataVO.setMaName(rs.getString("ma_name"));
					materialdataVO.setMaPrice(rs.getInt("ma_price"));
					materialdataVO.setMaStatus(rs.getInt("event_vote_num"));
					set.add(materialdataVO);	
				}
			}catch (SQLException e) {
				throw new RuntimeException("a database error occured.love you!"+e.getMessage());
			} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace(System.err);
					}
				}
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace(System.err);
					}
				}
				if(con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace(System.err);
					}
				}
			}
		System.out.println("********���}DAO********");
		return set;
	}
	
	@Override
	public Set<Event_PVO> getEventpnoByPreproduct(Integer vote_rank) {
		System.out.println("********DAO - ����getEventpnoByPreproduct********");
		Set<Event_PVO> set = new LinkedHashSet<Event_PVO>();
		Event_PVO eventpVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_Eventno_ByPreproduct_STMT);
				pstmt.setInt(1, vote_rank);
				rs = pstmt.executeQuery();
				
				
				while(rs.next()) {
					eventpVO = new Event_PVO();
					eventpVO.setEvent_p_no(rs.getInt("event_p_no"));
					eventpVO.setMem_id(rs.getString("mem_id"));
					eventpVO.setEvent_no(rs.getString("event_no"));
					eventpVO.setEvent_p_name(rs.getString("event_p_name"));
					eventpVO.setEvent_p_date(rs.getTimestamp("event_p_date"));
					eventpVO.setEvent_vote_num(rs.getInt("event_vote_num"));
					eventpVO.setVote_rank(rs.getInt("vote_rank"));
					eventpVO.setEvent_p_stat(rs.getInt("event_p_stat"));
					set.add(eventpVO);	
				}
				

			}catch (SQLException e) {
				throw new RuntimeException("a database error occured.love you!"+e.getMessage());
			} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace(System.err);
					}
				}
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace(System.err);
					}
				}
				if(con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace(System.err);
					}
				}
			}
		return set;
	}

	// Handle with byte array datajl
	public static void readPicture(byte[] bytes) throws IOException {
		System.out.println("********DAO - ����readPicture********");
		
			FileOutputStream fos = new FileOutputStream("Output/No1.png");
			fos.write(bytes);
			fos.flush();
			fos.close();
	}
	
	@Override
	public List<PreProductVO> getALLPreproductInTime() {
		System.out.println("********DAO - ����getALLPreproductInTime********");
		List<PreProductVO> list = new ArrayList<PreProductVO>();
		PreProductVO preproductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALLOF_PREPRODUCT_IN_ESHOP);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				preproductVO = new PreProductVO();
				preproductVO.setPo_prod_no(rs.getString("po_prod_no"));
				preproductVO.setEvent_p_no(rs.getInt("event_p_no"));
				preproductVO.setMa_no(rs.getString("ma_no"));
				preproductVO.setPo_start(rs.getTimestamp("po_start"));
				preproductVO.setPo_end(rs.getTimestamp("po_end"));
				preproductVO.setPo_price(rs.getInt("po_price"));
				preproductVO.setPo_detail(rs.getString("po_detail"));
				list.add(preproductVO);
				
			}
			

		}catch (SQLException e) {
			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		System.out.println("********"+list.size()+"********");
		System.out.println("********���}DAO********");
		return list;
	}

	@Override
	public List<PreProductVO> getALL() {
		
		System.out.println("********DAO - ����getALL********");
		List<PreProductVO> list = new ArrayList<PreProductVO>();
		PreProductVO preproductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			
			rs = pstmt.executeQuery();
			
			
			int cnt = 0;
			
			while(rs.next()) {
				preproductVO = new PreProductVO();
				preproductVO.setPo_prod_no(rs.getString("po_prod_no"));
				preproductVO.setEvent_p_no(rs.getInt("event_p_no"));
				preproductVO.setMa_no(rs.getString("ma_no"));
				preproductVO.setPo_start(rs.getTimestamp("po_start"));
				preproductVO.setPo_end(rs.getTimestamp("po_end"));
				preproductVO.setPo_price(rs.getInt("po_price"));
				preproductVO.setPo_detail(rs.getString("po_detail"));
				
				list.add(preproductVO);
				cnt++;
			}
			
			

		}catch (SQLException e) {
			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		System.out.println("********���}DAO********");
		return list;
	}
	
	
	}
	
	

