package com.bank.model;

import java.util.List;

public class BankService {

	private BankDAO_interface dao;

	public BankService() {

		dao = new BankJDBCDAO();

	}

	public BankVO addBank(String mem_id, String bn_code, String bn_accno, String bn_accname) {

		BankVO bankVO = new BankVO();

		bankVO.setMem_id(mem_id);
		bankVO.setBn_code(bn_code);
		bankVO.setBn_accno(bn_accno);
		bankVO.setBn_accname(bn_accname);
		dao.insert(bankVO);

		return bankVO;
	}

	public BankVO updateBank(String bn_code, String bn_accno, String bn_accname, Integer bank_id) {

		BankVO bankVO = new BankVO();

		bankVO.setBn_code(bn_code);
		bankVO.setBn_accno(bn_accno);
		bankVO.setBn_accname(bn_accname);
		bankVO.setBank_id(bank_id);
		dao.update(bankVO);

		return bankVO;
	}

	public void deleteOneBank(Integer bank_id) {

		dao.deleteOne(bank_id);
	}

	public void deleteAllBank(String mem_id) {

		dao.deleteAll(mem_id);
	}

	public BankVO getOneBank(Integer bank_id) {

		return dao.findByPrimaryKey(bank_id);
	}

	public List<BankVO> getAll() {

		return dao.getAll();
	}

}
