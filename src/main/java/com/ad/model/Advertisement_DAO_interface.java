package com.ad.model;

import java.util.List;

public interface Advertisement_DAO_interface {
	
	public void insert(Advertisement_VO advertisement_VO);
	public void update(Advertisement_VO advertisement_VO);
	public void delete(String ad_no);
    public Advertisement_VO findByPrimaryKey(String ad_no);
    public List<Advertisement_VO> getAll();

}
