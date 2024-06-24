package com.material.model;

import java.util.List;
import java.util.Map;

public interface Material_Data_DAO_interface {
	
	public void insert(Material_Data_VO material_Data_VO);
	public void update(Material_Data_VO material_Data_VO);
	public void delete(String ma_no);
	public void deletetype(String ma_ty_no);
    public Material_Data_VO findByPrimaryKey(String ma_no);
    public List<Material_Data_VO> findByMaterialType(String ma_ty_no);
    public List<Material_Data_VO> getAll();
    public List<Material_Data_VO> getAll(Map<String, String[]> map);
}
