package com.reg_inf.model;

import java.util.List;

public class Reg_infService {

	private Reg_infDAO_interface dao;

	public Reg_infService() {
		dao = new Reg_infDAO();
	}

	public Reg_infVO addReg_inf(String mt_no,String mem_id, 
			Integer ri_qty,Integer ri_status,String ri_note) {

		Reg_infVO reg_infVO = new Reg_infVO();

		reg_infVO.setMt_no(mt_no);
		reg_infVO.setMem_id(mem_id);
		reg_infVO.setRi_qty(ri_qty);
		reg_infVO.setRi_status(ri_status);
		reg_infVO.setRi_note(ri_note);
		
		reg_infVO.setRi_id(dao.insert(reg_infVO));//回傳字串帶有"next_ri_id"的值

		return reg_infVO;
	}
	
	public Reg_infVO updateReg_inf(String mt_no,String mem_id, 
			Integer ri_qty,String ri_note, String ri_id) {

		Reg_infVO reg_infVO = new Reg_infVO();


		reg_infVO.setMt_no(mt_no);
		reg_infVO.setMem_id(mem_id);
		reg_infVO.setRi_qty(ri_qty);

		reg_infVO.setRi_note(ri_note);
		reg_infVO.setRi_id(ri_id);

		System.out.println(ri_note);
		
		dao.update(reg_infVO);

		return reg_infVO;
	}

	public Reg_infVO cancelReg_inf(Integer ri_status,Integer ri_qty,String ri_id) {

		Reg_infVO reg_infVO = new Reg_infVO();

		reg_infVO.setRi_status(ri_status);
		reg_infVO.setRi_qty(ri_qty);

		reg_infVO.setRi_id(ri_id);

		
		dao.cancel(reg_infVO);

		return reg_infVO;
	}
	

	public void deleteReg_inf(String ri_id) {
		dao.delete(ri_id);
	}

	public Reg_infVO getOneReg_inf(String ri_id) {
		return dao.findByPrimaryKey(ri_id);
	}

	public List<Reg_infVO> getAll() {
		return dao.getAll();
	}
	public List<Reg_infVO> getMem_Reg_inf(String mem_id) {
		System.out.println("getMem_Reg_inf" + mem_id);
		
		return dao.getMem_Reg_inf(mem_id);
		
	}
	public List<Reg_infVO> getReg_inf_mt_no(String mt_no) {

		return dao.getReg_inf_mt_no(mt_no);

	}
	
	public void check_mt_numReg_inf() {
		dao.check_mt_num();
	}
	
	
}
