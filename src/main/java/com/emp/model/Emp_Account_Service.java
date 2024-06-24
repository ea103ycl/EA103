package com.emp.model;

import java.util.List;

public class Emp_Account_Service {

	private Emp_Account_DAO_interface dao;
	
	public Emp_Account_Service () {
		dao = new Emp_Account_DAO();
	}
	
	public Emp_Account_VO addEmp(String emp_name, String emp_pwd,
			String emp_pos, String emp_mail, byte[] emp_photo, Integer emp_status) {
		
		Emp_Account_VO emp_Account_VO = new Emp_Account_VO();
		
		emp_Account_VO.setEmpName(emp_name);
		emp_Account_VO.setEmpPwd(emp_pwd);
		emp_Account_VO.setEmpPos(emp_pos);
		emp_Account_VO.setEmpMail(emp_mail);
		emp_Account_VO.setEmpPhoto(emp_photo);
		emp_Account_VO.setEmpStatus(emp_status);
		dao.insert(emp_Account_VO);
		
		return emp_Account_VO;
	}
	
	
	public Emp_Account_VO updateEmp(String emp_no, String emp_name, String emp_pwd,
			String emp_pos, String emp_mail, byte[] emp_photo, Integer emp_status) {
		
		Emp_Account_VO emp_Account_VO = new Emp_Account_VO();
		
		emp_Account_VO.setEmpNo(emp_no);
		emp_Account_VO.setEmpName(emp_name);
		emp_Account_VO.setEmpPwd(emp_pwd);
		emp_Account_VO.setEmpPos(emp_pos);
		emp_Account_VO.setEmpMail(emp_mail);
		emp_Account_VO.setEmpPhoto(emp_photo);
		emp_Account_VO.setEmpStatus(emp_status);
		dao.update(emp_Account_VO);
		
		return emp_Account_VO;
	}
	
	public Emp_Account_VO resetPwd(String emp_no,String emp_pwd) {
		
		Emp_Account_VO emp_Account_VO = new Emp_Account_VO();
		
		emp_Account_VO.setEmpNo(emp_no);
		emp_Account_VO.setEmpPwd(emp_pwd);

		dao.reset(emp_Account_VO);
		
		return emp_Account_VO;
	}
	
	public void deleteEmp(String emp_no) {
		dao.delete(emp_no);
	}
	
	public Emp_Account_VO getOneEmp(String emp_no) {
		return dao.findByPrimaryKey(emp_no);
	}
	
	public List<Emp_Account_VO> getAll(){
		return dao.getAll();
	}
}
