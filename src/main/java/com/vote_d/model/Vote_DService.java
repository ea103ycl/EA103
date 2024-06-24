package com.vote_d.model;

import java.util.*;

public class Vote_DService {
	private Vote_DDAO dao;
	public Vote_DService() {
		this.dao=new Vote_DDAO();
	}
	public void delete(Integer event_p_no, String mem_id) {
		dao.delete(event_p_no, mem_id);
	}
	public List<Vote_DVO> findAllByMem(String mem_id){
		return dao.findAllByMem(mem_id);
	}
	public List<Vote_DVO> findAllVote_d(){
		return dao.findAllVote_D();
	}
}
