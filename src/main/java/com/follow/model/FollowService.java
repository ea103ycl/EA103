package com.follow.model;

import java.util.List;

public class FollowService {

	private FollowDAO_interface dao;

	public FollowService() {
		dao = new FollowDAO();
	}

	public void update(FollowVO followVO) {
		dao.update(followVO);
	}

	public Integer getCnt(String mem_id, String f_mem_id) {
		return dao.getCnt(mem_id, f_mem_id);
	}

	public FollowVO getOne(String mem_id, String f_mem_id) {
		return dao.getOne(mem_id, f_mem_id);
	}

	public List<FollowVO> getFollowList(String mem_id) {
		return dao.getFollowList(mem_id);
	}

	public List<FollowVO> getFansList(String mem_id) {
		return dao.getFansList(mem_id);
	}

	public Integer getFollowCnt(String mem_id) {
		return dao.getFollowCnt(mem_id);
	}

	public Integer getFansCnt(String mem_id) {
		return dao.getFansCnt(mem_id);
	}

}
