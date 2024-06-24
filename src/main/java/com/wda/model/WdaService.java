package com.wda.model;

import java.util.List;

public class WdaService {

	private WdaDAO_interface dao;

	public WdaService() {
		dao = new WdaJDBADAO();
	}

	public WdaVO addWda(String mem_id, String bn_code, String bn_accno, String bn_accname, Integer wda_amount,
			Integer wda_status) {
		WdaVO wdaVO = new WdaVO();
		wdaVO.setMem_id(mem_id);
		wdaVO.setBn_code(bn_code);
		wdaVO.setBn_accno(bn_accno);
		wdaVO.setBn_accname(bn_accname);
		wdaVO.setWda_amount(wda_amount);
		dao.insert(wdaVO);
		return wdaVO;
	}

	public WdaVO updateWda(Integer wda_id, String mem_id, String bn_code, String bn_accno, String bn_accname,
			Integer wda_amount, Integer wda_status) {
		WdaVO wdaVO = new WdaVO();

		wdaVO.setWda_id(wda_id);
		wdaVO.setMem_id(mem_id);
		wdaVO.setBn_code(bn_code);
		wdaVO.setBn_accno(bn_accno);
		wdaVO.setBn_accname(bn_accname);
		wdaVO.setWda_amount(wda_amount);

		dao.update(wdaVO);
		return wdaVO;

	}

	public void deleteWda(Integer wda_id) {
		dao.delete(wda_id);
	}

	public WdaVO getOneWda(Integer wda_id) {
		return dao.findByPrimaryKey(wda_id);

	}

	public List<WdaVO> getAll() {

		return dao.getAll();
	}

}
