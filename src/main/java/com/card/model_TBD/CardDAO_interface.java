package com.card.model_TBD;

import java.util.List;

public interface CardDAO_interface {

	public void insert(CardVO cardVO);

	public void update(CardVO cardVO);

	public void deleteOne(Integer card_id);

	public void deleteAll(String mem_id);

	public CardVO findByPrimaryKey(Integer card_id);

	public List<CardVO> getAll();

}
