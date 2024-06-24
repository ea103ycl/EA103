package com.bidding.model;

import java.sql.Timestamp;
import java.util.List;

public class BiddingService {
	
	private BiddingDAO_interface dao;
	
	public BiddingService(){
		dao= new BiddingDAO();
	}
	
	
	public String insert(String memId,Integer bdProdNo,Integer bdPrice,Timestamp bdDateStr,Timestamp bdDateEnd,Integer bdStatus,Integer bdOrdStatus,Integer pmtStatus,Integer bdZip,String bdName,String bdPhone,String bdAddr) {
		BiddingVO bVO=new BiddingVO();

		bVO.setMemId(memId);
		bVO.setBdProdNo(bdProdNo);
		bVO.setBdPrice(bdPrice);
		bVO.setBdDateStr(bdDateStr);
		bVO.setBdDateEnd(bdDateEnd);
		bVO.setBdStatus(bdStatus);
		bVO.setBdOrdStatus(bdOrdStatus);
		bVO.setPmtStatus(pmtStatus);
		bVO.setBdZip(bdZip);
		bVO.setBdName(bdName);
		bVO.setBdPhone(bdPhone);
		bVO.setBdAddr(bdAddr);
		
		String bdNo= dao.insert(bVO);
		return bdNo;
	}

	
	public BiddingVO update(String bdNo,String memId,Integer bdProdNo,Integer bdPrice,Timestamp bdDateStr,Timestamp bdDateEnd,Integer bdStatus,Integer bdOrdStatus,Integer pmtStatus,Integer bdZip,String bdName,String bdPhone,String bdAddr){
		BiddingVO bVO=new BiddingVO();
		bVO.setBdNo(bdNo);
		bVO.setMemId(memId);
		bVO.setBdProdNo(bdProdNo);
		bVO.setBdPrice(bdPrice);
		bVO.setBdDateStr(bdDateStr);
		bVO.setBdDateEnd(bdDateEnd);
		bVO.setBdStatus(bdStatus);
		bVO.setBdOrdStatus(bdOrdStatus);
		bVO.setPmtStatus(pmtStatus);
		bVO.setBdZip(bdZip);
		bVO.setBdName(bdName);
		bVO.setBdPhone(bdPhone);
		bVO.setBdAddr(bdAddr);
		
		dao.update(bVO);
		return bVO;
	}
	
	public BiddingVO getOne(String bdNo) {
		return dao.getOne(bdNo);
	}
	
	public List<BiddingVO> getAll(){
		return dao.getAll();
	}
}
