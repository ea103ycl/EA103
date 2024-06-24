package com.bank.model;

import java.util.List;

public interface BankDAO_interface
{
	public void insert(BankVO bankVO);

	public void update(BankVO bankVO);

	public void deleteOne(Integer bank_id);

	public void deleteAll(String mem_id);

	public BankVO findByPrimaryKey(Integer bank_id);

	public List<BankVO> getAll();
}
