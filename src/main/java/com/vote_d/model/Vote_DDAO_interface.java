package com.vote_d.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Vote_DDAO_interface {
	public void insert(Vote_DVO vote_d,Connection con) ;
	public Integer voteNumByMemInEventno(String event_no,String mem_id);
	public void delete(Integer event_p_no,String mem_id);
	public List<Vote_DVO> findAllByMem(String mem_id);
	public List<Vote_DVO> findAllVote_D();
}
