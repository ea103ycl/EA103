package com.preproduct.model;
//package com.preproduct.model;
//
//import java.util.*;
//
//import com.event_p.model.Event_PVO;
//import com.material.model.Material_Data_VO;//待Jeff討論
//import com.preproduct.model.PreProductVO;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.sql.*;
//import java.sql.Date;
//
//public class PreProductJDBCDAO implements PreProductDAO_interface{
//
//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	String userid = "G1";
//	String passwd = "123456";
//	 
//	private static final String INSERT_STMT = "INSERT INTO PRE_PRODUCT (PO_PROD_NO,EVENT_P_NO,MA_NO,PO_START,PO_END,PO_PRICE,PO_DETAIL) "
//			+ "VALUES (PRE_PRODUCT_SEQ.NEXTVAL,?,?,?,?,?,?)";
//	private static final String INSERT_ByRanking_STMT = "INSERT INTO PRE_PRODUCT ( PO_PROD_NO, EVENT_P_NO, MA_NO, PO_START, PO_END,PO_PRICE, PO_DETAIL) SELECT PRE_PRODUCT_SEQ.NEXTVAL, EVENT_P_NO, ?, ?, ?,?, ? FROM EVENT_P E WHERE E.VOTE_RANK BETWEEN 1 AND 10 AND E.EVENT_NO = (SELECT MAX(T.EVENT_NO) FROM EVENT T WHERE T.EVENT_STAT = 3)";
//	private static final String GET_ONE_STMT = "SELECT * FROM PRE_PRODUCT WHERE PO_PROD_NO = ?";
//	private static final String UPDATE = "UPDATE PRE_PRODUCT SET event_p_no=?, ma_no=?, po_start=?, po_end=?, po_price=?, po_detail=? WHERE po_prod_no=?";
//	private static final String SWITCH = "UPDATE PRE_PRODUCT SET po_start=?, po_end=? WHERE po_prod_no=?";
//	private static final String DELETE = "DELETE FROM PRE_PRODUCT WHERE po_prod_no=?";
//	private static final String GET_ALL_STMT = "SELECT PO_PROD_NO,EVENT_P_NO,MA_NO,PO_START,PO_END,PO_PRICE,PO_DETAIL FROM PRE_PRODUCT";
//	private static final String GET_ALLOF_PREPRODUCT = "SELECT * FROM EVENT_P WHERE VOTE_RANK BETWEEN 1 AND 10 AND EVENT_NO = (SELECT MAX(EVENT_NO) FROM EVENT WHERE EVENT_STAT = 3)";
//	//用於取得素材圖片 By MA_NO
//	private static final String GET_ManoIMG_ByMano_STMT = "SELECT MA_PHOTO FROM MATERIAL_DATA WHERE MA_NO = ? ";
//	//利用名次找作品(尚未使用)
//	private static final String GET_Eventno_ByPreproduct_STMT = "SELECT * FROM EVENT_P WHERE VOTE_RANK = ? AND EVENT_NO = (SELECT MAX(EVENT_NO) FROM EVENT WHERE EVENT_STAT = 3)";
//	
//	
//	@Override
//	public void insert(PreProductVO preproductVO) {
//		System.out.println("********DAO - 執行insert********");
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url,userid,passwd);
//			pstmt = con.prepareStatement(INSERT_STMT);
//			pstmt.setInt(1, preproductVO.getEvent_p_no());
//			pstmt.setString(2, preproductVO.getMa_no());
//			pstmt.setTimestamp(3, preproductVO.getPo_start());
//			pstmt.setTimestamp(4, preproductVO.getPo_end());
//			pstmt.setInt(5, preproductVO.getPo_price());
//			pstmt.setString(6, preproductVO.getPo_detail());
//			
//			System.out.println("********DAO - 執行executeUpdate()********");
//			pstmt.executeUpdate();
//			System.out.println("********離開DAO********");
//			
//		}catch(ClassNotFoundException e) {
//			throw new RuntimeException("Could't load database driver.love you!"+e.getMessage());
//		} catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//	
//	public void insertByRanking(PreProductVO preproductVO) {
//		System.out.println("********DAO - 執行insertByRanking********");
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url,userid,passwd);
//			pstmt = con.prepareStatement(INSERT_ByRanking_STMT);
//			pstmt.setString(1, preproductVO.getMa_no());
//			pstmt.setTimestamp(2, preproductVO.getPo_start());
//			pstmt.setTimestamp(3, preproductVO.getPo_end());
//			pstmt.setInt(4, preproductVO.getPo_price());
//			pstmt.setString(5, preproductVO.getMa_no());
//			System.out.println("********DAO - 執行executeUpdate********");
//			pstmt.executeUpdate();
//			System.out.println("********離開DAO********");
//			
//		}catch(ClassNotFoundException e) {
//			throw new RuntimeException("Could't load database driver.love you!"+e.getMessage());
//		} catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//	
//	@Override
//	public PreProductVO findByPrimaryKey(String po_prod_no) {
//		System.out.println("********DAO - 執行findByPrimaryKey********");
//		PreProductVO preproductVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url,userid,passwd);
//			pstmt = con.prepareStatement(GET_ONE_STMT);
//			pstmt.setString(1, po_prod_no);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				preproductVO = new PreProductVO();
//				preproductVO.setPo_prod_no(rs.getString("po_prod_no"));
//				preproductVO.setEvent_p_no(rs.getInt("event_p_no"));
//				preproductVO.setMa_no(rs.getString("ma_no"));
//				preproductVO.setPo_start(rs.getTimestamp("po_start"));
//				preproductVO.setPo_end(rs.getTimestamp("po_end"));
//				preproductVO.setPo_price(rs.getInt("po_price"));
//				preproductVO.setPo_detail(rs.getString("po_detail"));
//			}
//		}catch(ClassNotFoundException e) {
//			throw new RuntimeException("Could't load database driver.love you!"+e.getMessage());
//		} catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return preproductVO;
//	}
//	
//	@Override
//	public void update(PreProductVO pre_productVO) {
//		System.out.println("********DAO - 執行update********");
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url,userid,passwd);
//			pstmt = con.prepareStatement(UPDATE);
//			
//			pstmt.setInt(1, pre_productVO.getEvent_p_no());
//			pstmt.setString(2, pre_productVO.getMa_no());
//			pstmt.setTimestamp(3, pre_productVO.getPo_start());
//			pstmt.setTimestamp(4, pre_productVO.getPo_end());
//			pstmt.setInt(5, pre_productVO.getPo_price());
//			pstmt.setString(6, pre_productVO.getPo_detail());
//			pstmt.setString(7, pre_productVO.getPo_prod_no());
//			System.out.println("********DAO - 執行executeUpdate********");
//			pstmt.executeUpdate();
//			System.out.println("********離開DAO********");
//			
//		}catch(ClassNotFoundException e) {
//			throw new RuntimeException("Could't load database driver.love you!"+e.getMessage());
//		} catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//	
//	public void switchPreProduct(PreProductVO pre_productVO) {
//		System.out.println("********DAO - 執行switchPreProduct********");
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url,userid,passwd);
//			pstmt = con.prepareStatement(SWITCH);
//			
//			pstmt.setTimestamp(1, pre_productVO.getPo_start());
//			pstmt.setTimestamp(2, pre_productVO.getPo_end());
//			pstmt.setString(3, pre_productVO.getPo_prod_no());
//			System.out.println("********DAO - 執行executeUpdate********");
//			pstmt.executeUpdate();
//			System.out.println("********離開DAO********");
//		}catch(ClassNotFoundException e) {
//			throw new RuntimeException("Could't load database driver.love you!"+e.getMessage());
//		} catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//	
//	@Override
//	public void delete(String po_prod_no) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url,userid,passwd);
//			pstmt = con.prepareStatement(DELETE);
//			pstmt.setString(1, po_prod_no);
//			pstmt.executeUpdate();
//		}catch(ClassNotFoundException e) {
//			throw new RuntimeException("Could't load database driver.love you!"+e.getMessage());
//		} catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(pstmt != null) {
//				try {pstmt.close();} 
//				catch (SQLException e) {e.printStackTrace(System.err);}
//			}
//			if(con != null) {
//				try {con.close();} 
//				catch (SQLException e) {e.printStackTrace(System.err);}
//			}
//		}
//	}
//	
//	@Override
//	public List<PreProductVO> getALL() {
//		System.out.println("********DAO - 執行getALL********");
//		List<PreProductVO> list = new ArrayList<PreProductVO>();
//		PreProductVO preproductVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url,userid,passwd);
//			pstmt = con.prepareStatement(GET_ALL_STMT);
//			System.out.println("********DAO - 執行executeQuery********");
//			rs = pstmt.executeQuery();
//			System.out.println("********DAO - (成功)executeQuery********");
//			while(rs.next()) {
//				preproductVO = new PreProductVO();
//				preproductVO.setPo_prod_no(rs.getString("po_prod_no"));
//				preproductVO.setEvent_p_no(rs.getInt("event_p_no"));
//				preproductVO.setMa_no(rs.getString("ma_no"));
//				preproductVO.setPo_start(rs.getTimestamp("po_start"));
//				preproductVO.setPo_end(rs.getTimestamp("po_end"));
//				preproductVO.setPo_price(rs.getInt("po_price"));
//				preproductVO.setPo_detail(rs.getString("po_detail"));
//				list.add(preproductVO);
//			}
//			System.out.println("********DAO - (成功)while(rs.next())********");
//
//		}catch(ClassNotFoundException e) {
//			throw new RuntimeException("Could't load database driver.love you!"+e.getMessage());
//		} catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		System.out.println("********離開DAO********");
//		return list;
//	}
//	
//	public List<PreProductVO> GET_ALLOF_PREPRODUCT() {
//		System.out.println("********DAO - 執行GET_ALLOF_PREPRODUCT********");
//		List<PreProductVO> list = new ArrayList<PreProductVO>();
//		PreProductVO preproductVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url,userid,passwd);
//			pstmt = con.prepareStatement(GET_ALLOF_PREPRODUCT);
//			rs = pstmt.executeQuery();
//			System.out.println("********DAO - (成功)executeQuery********");
//			
//			while(rs.next()) {
//				preproductVO = new PreProductVO();
//				preproductVO.setPo_prod_no(rs.getString("po_prod_no"));
//				preproductVO.setEvent_p_no(rs.getInt("event_p_no"));
//				preproductVO.setMa_no(rs.getString("ma_no"));
//				preproductVO.setPo_start(rs.getTimestamp("po_start"));
//				preproductVO.setPo_end(rs.getTimestamp("po_end"));
//				preproductVO.setPo_price(rs.getInt("po_price"));
//				preproductVO.setPo_detail(rs.getString("po_detail"));
//				list.add(preproductVO);
//			}
//			System.out.println("********DAO - (成功)while(rs.next())********");
//
//		}catch(ClassNotFoundException e) {
//			throw new RuntimeException("Could't load database driver.love you!"+e.getMessage());
//		} catch (SQLException e) {
//			throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//		} finally {
//			if(rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		System.out.println("********離開DAO********");
//		return list;
//	}
//	
//	@Override 	//用於取得素材圖片 By MA_NO
//	public Set<Material_Data_VO> getManoByPreproduct(String ma_no) {
//		System.out.println("********DAO - 執行getManoByPreproduct********");
//		Set<Material_Data_VO> set = new LinkedHashSet<Material_Data_VO>();
//		Material_Data_VO materialdataVO = null;
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//				Class.forName(driver);
//				con = DriverManager.getConnection(url,userid,passwd);
//				pstmt = con.prepareStatement(GET_ManoIMG_ByMano_STMT);
//				pstmt.setString(1, ma_no);
//				rs = pstmt.executeQuery();
//				System.out.println("********DAO - 執行executeQuery********");
//				
//				while(rs.next()) {/*待Jeff討論 set問題*/
//					materialdataVO = new Material_Data_VO();
//					materialdataVO.setMaNo(rs.getString("ma_no"));
//					materialdataVO.setMaTyNo(rs.getString("ma_ty_no"));
//					materialdataVO.setMaName(rs.getString("ma_name"));
//					materialdataVO.setMaPrice(rs.getInt("ma_price"));
//					materialdataVO.setMaStatus(rs.getInt("event_vote_num"));
//					set.add(materialdataVO);	
//				}
//			}catch(ClassNotFoundException e) {
//				throw new RuntimeException("Could't load database driver.love you!"+e.getMessage());
//			} catch (SQLException e) {
//				throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//			} finally {
//				if(rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException e) {
//						e.printStackTrace(System.err);
//					}
//				}
//				if(pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException e) {
//						e.printStackTrace(System.err);
//					}
//				}
//				if(con != null) {
//					try {
//						con.close();
//					} catch (SQLException e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//		System.out.println("********離開DAO********");
//		return set;
//	}
//	
//	@Override
//	public Set<Event_PVO> getEventpnoByPreproduct(Integer vote_rank) {
//		System.out.println("********DAO - 執行getEventpnoByPreproduct********");
//		Set<Event_PVO> set = new LinkedHashSet<Event_PVO>();
//		Event_PVO eventpVO = null;
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//				Class.forName(driver);
//				con = DriverManager.getConnection(url,userid,passwd);
//				pstmt = con.prepareStatement(GET_Eventno_ByPreproduct_STMT);
//				pstmt.setInt(1, vote_rank);
//				rs = pstmt.executeQuery();
//				System.out.println("********DAO - (成功)executeQuery********");
//				
//				while(rs.next()) {
//					eventpVO = new Event_PVO();
//					eventpVO.setEvent_p_no(rs.getInt("event_p_no"));
//					eventpVO.setMem_id(rs.getString("mem_id"));
//					eventpVO.setEvent_no(rs.getString("event_no"));
//					eventpVO.setEvent_p_name(rs.getString("event_p_name"));
//					eventpVO.setEvent_p_date(rs.getTimestamp("event_p_date"));
//					eventpVO.setEvent_vote_num(rs.getInt("event_vote_num"));
//					eventpVO.setVote_rank(rs.getInt("vote_rank"));
//					eventpVO.setEvent_p_stat(rs.getInt("event_p_stat"));
//					set.add(eventpVO);	
//				}
//				System.out.println("********DAO - (成功)while(rs.next())********");
//
//			}catch(ClassNotFoundException e) {
//				throw new RuntimeException("Could't load database driver.love you!"+e.getMessage());
//			} catch (SQLException e) {
//				throw new RuntimeException("a database error occured.love you!"+e.getMessage());
//			} finally {
//				if(rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException e) {
//						e.printStackTrace(System.err);
//					}
//				}
//				if(pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException e) {
//						e.printStackTrace(System.err);
//					}
//				}
//				if(con != null) {
//					try {
//						con.close();
//					} catch (SQLException e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//		return set;
//	}
//
//	// Handle with byte array datajl
//	public static void readPicture(byte[] bytes) throws IOException {
//		System.out.println("********DAO - 執行readPicture********");
//		
//			FileOutputStream fos = new FileOutputStream("Output/No1.png");
//			fos.write(bytes);
//			fos.flush();
//			fos.close();
//	}
//	
//}
