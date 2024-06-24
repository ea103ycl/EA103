package com.bidding.model;

import java.util.List;

public interface BiddingDAO_interface {
	public String insert(BiddingVO bVO);
	public void update(BiddingVO bVO);
	public BiddingVO getOne(String bd_no);
	public List<BiddingVO> getAll();
}