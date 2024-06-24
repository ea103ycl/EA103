package com.material.model;

import java.util.List;
import java.util.Map;

public class Material_Data_Service {
	
	private Material_Data_DAO_interface dao;
	
	public Material_Data_Service() {
		dao = new Material_Data_DAO();
	}
	
	public Material_Data_VO addMaterialData(String ma_ty_no,
			 String ma_name, Integer ma_price, byte[] ma_photo, Integer ma_status) {
		
		Material_Data_VO material_Data_VO = new Material_Data_VO();
		
		material_Data_VO.setMaTyNo(ma_ty_no);
		material_Data_VO.setMaName(ma_name);
		material_Data_VO.setMaPrice(ma_price);
		material_Data_VO.setMaPhoto(ma_photo);
		material_Data_VO.setMaStatus(ma_status);	
		dao.insert(material_Data_VO);
		
		return material_Data_VO;
	}
	
	public Material_Data_VO updateMaterialData(String ma_no, String ma_ty_no,
			 String ma_name, Integer ma_price, byte[] ma_photo, Integer ma_status) {
		
		Material_Data_VO material_Data_VO = new Material_Data_VO();
		
		material_Data_VO.setMaNo(ma_no);
		material_Data_VO.setMaTyNo(ma_ty_no);
		material_Data_VO.setMaName(ma_name);
		material_Data_VO.setMaPrice(ma_price);
		material_Data_VO.setMaPhoto(ma_photo);
		material_Data_VO.setMaStatus(ma_status);	
		dao.update(material_Data_VO);
		
		return material_Data_VO;
	}
	
	public void deleteMaterialData(String ma_no) {
		dao.delete(ma_no);
	}
	
	public void deletetype(String ma_ty_no) {
		dao.deletetype(ma_ty_no);
	}
	
	public List<Material_Data_VO> getOneMaterialType(String ma_ty_no){
		return dao.findByMaterialType(ma_ty_no);
	}
	
	public Material_Data_VO getOneMaterialData(String ma_no) {
		return dao.findByPrimaryKey(ma_no);	
	}
	
	public List<Material_Data_VO> getAll(){
		return dao.getAll();
	}
	
	public List<Material_Data_VO> getAll(Map<String, String[]> map){
		return dao.getAll(map);
	}
	
}

