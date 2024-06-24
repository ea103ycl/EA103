package com.follow.model;

import java.util.List;

public interface FollowDAO_interface {
	
	public void update(FollowVO followVO);
	public Integer getCnt(String mem_id, String f_mem_id); //取得追蹤資料(主要判斷是否已追蹤)
	public FollowVO getOne(String mem_id, String f_mem_id); //取得追蹤資料
	public List<FollowVO> getFollowList(String mem_id); //該會員的追蹤對象列表
	public List<FollowVO> getFansList(String mem_id); //追蹤該會員的列表
	public Integer getFollowCnt(String mem_id); //該會員的追蹤對象人數
	public Integer getFansCnt(String mem_id); //該會員的追蹤對象人數
	
}
