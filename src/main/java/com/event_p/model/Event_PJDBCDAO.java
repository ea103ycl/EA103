package com.event_p.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Event_PJDBCDAO implements Event_PDAO_interface {
	private static final String DRIVER="oracle.jdbc.driver.OracleDriver";
	private static final String url="jdbc:oracle:thin:@localhost:1521:XE";
	private static final String INSERT=
			"INSERT INTO EVENT_P(event_p_no,mem_id,event_no,event_p_name,event_p_date,event_vote_num,vote_rank,event_p_stat,event_p_img)"
			+"VALUES(EVENT_P_SEQ.nextval,?,?,?,?,?,?,?,?)";
	private static final String FIND_ALL_EVENTPIC="SELECT*FROM EVENT_P";
	private static final String FIND_ONE="SELECT* FROM EVENT_P WHERE EVENT_P_NO=?";
	private static final String UPDATE="UPDATE EVENT_P SET MEM_ID=?,EVENT_NO=?,EVENT_P_NAME=?,EVENT_P_DATE=?,EVENT_VOTE_NUM=?,VOTE_RANK=?,EVENT_P_STAT=?,EVENT_p_IMG=? WHERE EVENT_P_NO=?";
	private static final String FINDALLMEM="SELECT DISTINCT MEM_ID FROM EVENT_P";
	private static final String DELETEVOTE="UPDATE EVENT_P SET EVENT_VOTE_NUM=EVENT_VOTE_NUM-1 WHERE EVENT_P_NO=?";
	private static final String BIGRANKSORT="select  event_p_no,rank() over(order by event_vote_num desc) as rank "
			+ "from event_p   where event_no=? and ROWNUM<=?";
	private static final String BIGRANKUPDATE="UPDATE EVENT_P SET VOTE_RANK=? WHERE EVENT_P_NO=?";
	private static final String FINDALLEVENTNO = "SELECT DISTINCT EVENT_NO FROM EVENT_P ORDER BY EVENT_NO DESC";

	//by 瑞龍
	private static final String GET_ALL_STMT = "SELECT * FROM EVENT_P WHERE EVENT_NO = (SELECT LAST_VALUE (EVENT_NO) OVER (ORDER BY EVENT_NO)  as LastValue FROM EVENT WHERE EVENT_STAT = 3 and rownum<2) ";

	
	
	public void insert(Event_PVO pVO) {
		try {
			Class.forName(DRIVER);
			Connection con=DriverManager.getConnection(url,"G1","123456");
			PreparedStatement pstmt=con.prepareStatement(INSERT);
			
			
			pstmt.setString(1,pVO.getMem_id());//會員ID取得
			pstmt.setString(2,pVO.getEvent_no() );//活動流水號
			pstmt.setString(3,pVO.getEvent_p_name());//作品名稱>使用者輸入(從網頁抓)
			

			pstmt.setTimestamp(4,pVO.getEvent_p_date());
			pstmt.setInt(5,pVO.getEvent_vote_num());//取得投票數
			pstmt.setInt(6,pVO.getVote_rank());//由投票數取得排名
			pstmt.setInt(7,pVO.getEvent_p_stat());//可投票
			pstmt.setBytes(8, pVO.getEvent_p_img());
			
			pstmt.execute();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
	@Override
	public List<Event_PVO> findAllPic() {
		List<Event_PVO> event_pVOs=null;
		try {
			Class.forName(DRIVER);
			Connection con=DriverManager.getConnection(url,"G1","123456");
			PreparedStatement pstmt=con.prepareStatement(FIND_ALL_EVENTPIC);
			ResultSet rs=pstmt.executeQuery();
			event_pVOs=new ArrayList<Event_PVO>();
			while(rs.next()) {
				Event_PVO event_pVO=new Event_PVO();
				event_pVO.setEvent_p_no(rs.getInt(1));
				event_pVO.setMem_id(rs.getString(2));
				event_pVO.setEvent_no(rs.getString(3));
				event_pVO.setEvent_p_name(rs.getString(4));
				event_pVO.setEvent_p_date(rs.getTimestamp(5));
				event_pVO.setEvent_vote_num(rs.getInt(6));
				event_pVO.setVote_rank(rs.getInt(7));
				event_pVO.setEvent_p_stat(rs.getInt(8));
				event_pVO.setEvent_p_img(rs.getBytes(9));
				event_pVOs.add(event_pVO);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return event_pVOs;
	}
	
	@Override
	public Event_PVO findByPrimaryKey(Integer event_p_no) {
		// TODO Auto-generated method stub
		Event_PVO event_pVO=null;
		try {
			Class.forName(DRIVER);
			Connection con=DriverManager.getConnection(url,"G1","123456");
			PreparedStatement pstmt=con.prepareStatement(FIND_ONE);
			pstmt.setInt(1, event_p_no);
			ResultSet rs=pstmt.executeQuery();
			event_pVO=new Event_PVO();
			while(rs.next()) {
				event_pVO.setEvent_p_no(rs.getInt(1));
				event_pVO.setMem_id(rs.getString(2));
				event_pVO.setEvent_no(rs.getString(3));
				event_pVO.setEvent_p_name(rs.getString(4));
				event_pVO.setEvent_p_date(rs.getTimestamp(5));
				event_pVO.setEvent_vote_num(rs.getInt(6));
				event_pVO.setVote_rank(rs.getInt(7));
				event_pVO.setEvent_p_stat(rs.getInt(8));
				event_pVO.setEvent_p_img(rs.getBytes(9));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return event_pVO;
	}
	
	public void update(Event_PVO pVO) {
		Connection con=null;
		
		try {
			Class.forName(DRIVER);
			con=DriverManager.getConnection(url,"G1","123456");
			
			PreparedStatement pstmt=con.prepareStatement(UPDATE);
			
			pstmt.setString(1,pVO.getMem_id());
			pstmt.setString(2, pVO.getEvent_no());
			pstmt.setString(3, pVO.getEvent_p_name());
			pstmt.setTimestamp(4, pVO.getEvent_p_date());
			pstmt.setInt(5, pVO.getEvent_vote_num());
			pstmt.setInt(6, pVO.getVote_rank());
			pstmt.setInt(7, pVO.getEvent_p_stat());
			pstmt.setBytes(8, pVO.getEvent_p_img());
			pstmt.setInt(9, pVO.getEvent_p_no());
			pstmt.execute();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public List<String> findAllMem() {
		// TODO Auto-generated method stub
		Connection con=null;
		List<String> memIds=new ArrayList<String>();
		try {
			Class.forName(DRIVER);
			con=DriverManager.getConnection(url,"G1","123456");
			PreparedStatement pstmt=con.prepareStatement(FINDALLMEM);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				memIds.add(rs.getString(1));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memIds;
	}
	
	public static void main(String[] args) {
//		//新增
//		try {
//		Event_PJDBCDAO dao=new Event_PJDBCDAO();
//		Event_PVO pVO=new Event_PVO();		
//		pVO.setMem_id("M000001");
//		pVO.setEvent_no("E001001");
//		pVO.setEvent_p_name("我要進來了");
//		
//		java.util.Date nowDate=new java.util.Date();
//		Timestamp now=new Timestamp(nowDate.getTime());
//		pVO.setEvent_p_date(now);
//		pVO.setEvent_vote_num(5000);
//		pVO.setVote_rank(1);
//		pVO.setEvent_p_stat(1);
//		
//		byte[] img=null;		
//			img = getPictureByteArray("items/FC_Bayern.png");
//			pVO.setEvent_p_img(img);
//			dao.insert(pVO);
//			System.out.println("done");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//Test查全部
//		Event_PJDBCDAO dao=new Event_PJDBCDAO();
//		List<Event_PVO> event_pVOs=dao.findAllPic();
//		Iterator iter=event_pVOs.iterator();
//		while(iter.hasNext()) {
//			Event_PVO event_pVO=(Event_PVO)iter.next();
//			System.out.println(event_pVO.getEvent_p_name()+",");
//			System.out.println(event_pVO.getVote_rank());
//		}
		
		//查一個
//		Event_PJDBCDAO dao=new Event_PJDBCDAO();
//		Event_PVO event_pVO= dao.findByPrimaryKey(3004);
//		System.out.println(event_pVO.getEvent_p_name());
		
		//更新
//		Event_PJDBCDAO dao=new Event_PJDBCDAO();
//		Event_PVO pVO=new Event_PVO();
//		pVO.setEvent_p_no(3001);
//		pVO.setMem_id("M000001");
//		pVO.setEvent_no("E001002");
//		pVO.setEvent_p_name("PH9.0");
//		pVO.setEvent_p_date(Timestamp.valueOf("2020-09-10 00:00:00") );
//		pVO.setEvent_vote_num(60);
//		pVO.setVote_rank(5);
//		pVO.setEvent_p_stat(1);
//		File f =new File("WebContent/testIMG/test01.jpg");
//		FileInputStream is=null;
//		BufferedInputStream bis=null;
//		ByteArrayOutputStream baos=null;
//		try {
//			is = new FileInputStream(f);
//			bis=new BufferedInputStream(is);
//			baos=new ByteArrayOutputStream();
//			
//			byte[] buf=new byte[8192];
//			int i=0;
//			while((i=bis.read(buf))!=-1) {
//				baos.write(buf);
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		byte[] event_p_img=baos.toByteArray();
//		pVO.setEvent_p_img(event_p_img);
//		
//		dao.update(pVO);
		
		//查會員
//		Event_PJDBCDAO dao=new Event_PJDBCDAO();
//		List<String> memIds=dao.findAllMem();
//		Iterator iter=memIds.iterator();
//		while(iter.hasNext()) {
//			String mem_id=(String) iter.next();
//			System.out.println(mem_id);
//		}
		
		//取消投票
//		Event_PJDBCDAO dao=new Event_PJDBCDAO();
//		dao.deleteVote(3003);
		Event_PJDBCDAO dao=new Event_PJDBCDAO();		
		List<String> event_nos=dao.findAllEventNo();
		
		Iterator<String> iterEventNO=event_nos.iterator();
		while(iterEventNO.hasNext()) {
			String event_no=iterEventNO.next();
			List<Event_PVO> event_pVOs= dao.bigRankSort(event_no, 20);
			Iterator<Event_PVO> iter=event_pVOs.iterator();
			while(iter.hasNext()) {
				Event_PVO event_pVO= iter.next();
				dao.bigRankUpdate(event_pVO.getVote_rank(),event_pVO.getEvent_p_no());
			}
		}

		
	}
	@Override
	public List<String> findAllEventNo() {
		// TODO Auto-generated method stub
		List<String> eventNos = new ArrayList<String>();
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER);
			Connection con=DriverManager.getConnection(url,"G1","123456");
			pstmt = con.prepareStatement(FINDALLEVENTNO);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				eventNos.add(rs.getString(1));
			}			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return eventNos;
	}
	@Override
	public List<Event_PVO> bigRankSort(String event_no, Integer rownum) {
		// TODO Auto-generated method stub
		List<Event_PVO>event_pVOs=new ArrayList<Event_PVO>();
		
		try {
			Class.forName(DRIVER);
			Connection con=DriverManager.getConnection(url,"G1","123456");
			PreparedStatement pstmt=con.prepareStatement(BIGRANKSORT);
			pstmt.setString(1,event_no);
			pstmt.setInt(2, rownum);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				Event_PVO event_pVO=new Event_PVO();
				event_pVO.setEvent_p_no(rs.getInt(1));
				event_pVO.setVote_rank(rs.getInt(2));
				event_pVOs.add(event_pVO);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return event_pVOs;
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);//建立來源目的
		FileInputStream fis = new FileInputStream(file);//建立讀入基本管
		ByteArrayOutputStream baos = new ByteArrayOutputStream();//建立寫出基本管
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();//ByteArrayOutputStream將資料存在byte陣列中，用toByteArray取出資料
	}









	@Override
	public List<Event_PVO> findAllByEventNo(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public void delete(Integer event_p_no) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void votePic(Integer event_p_no, String mem_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteVote(Integer event_p_no) {
		// TODO Auto-generated method stub
		try {
			Class.forName(DRIVER);
			Connection con=DriverManager.getConnection(url,"G1","123456");
			PreparedStatement pstmt=con.prepareStatement(DELETEVOTE);
			pstmt.setInt(1, event_p_no);
			pstmt.execute();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void bigRankUpdate(Integer vote_rank, Integer event_p_no) {
		// TODO Auto-generated method stub
		try {
			Class.forName(DRIVER);
			Connection con=DriverManager.getConnection(url,"G1","123456");
			PreparedStatement pstmt=con.prepareStatement(BIGRANKUPDATE);
			pstmt.setInt(1, vote_rank);
			pstmt.setInt(2, event_p_no);
			pstmt.execute();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public boolean checkUploadByMemid(String mem_id, String event_no) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Event_PVO> findAllNoReport(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event_PVO> findAllOrderBySelect(String event_no, String colName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event_PVO findUploadByMemid(String mem_id, String event_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event_PVO findTopEventPNoByEventNoWithoutReport(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event_PVO> findAllByEventNoRankDescWithoutReport(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event_PVO> find5PicByEventNo(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}

	//==瑞龍=========================================================
	@Override
	public List<Event_PVO> getAll(String event_no) {
		// TODO Auto-generated method stub
		return null;
	}




}
