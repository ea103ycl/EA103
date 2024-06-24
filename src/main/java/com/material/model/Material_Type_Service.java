package com.material.model;

import java.util.List;

public class Material_Type_Service {

	private Material_Type_DAO_interface dao;

	public Material_Type_Service() {
		dao = new Material_Type_DAO();
	}

	public Material_Type_VO addMaterialType(String ma_ty_nam) {

		Material_Type_VO material_Type_VO = new Material_Type_VO();

		material_Type_VO.setMaTyNam(ma_ty_nam);
		dao.insert(material_Type_VO);

		return material_Type_VO;
	}

	public Material_Type_VO updateMaterialType(String ma_ty_no, String ma_ty_nam) {

		Material_Type_VO material_Type_VO = new Material_Type_VO();

		material_Type_VO.setMaTyNo(ma_ty_no);
		material_Type_VO.setMaTyNam(ma_ty_nam);
		dao.update(material_Type_VO);

		return material_Type_VO;
	}

	public void deleteMaterialType(String ma_ty_no) {
		dao.delete(ma_ty_no);
	}

	public Material_Type_VO getOneMaterialType(String ma_ty_no) {
		return dao.findByPrimaryKey(ma_ty_no);
	}

	public List<Material_Type_VO> getAll() {
		return dao.getAll();
	}

}
