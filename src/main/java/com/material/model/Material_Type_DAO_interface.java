package com.material.model;

import java.util.List;

public interface Material_Type_DAO_interface {
	
	public Material_Type_VO insert(Material_Type_VO material_Type_VO);
	public void update(Material_Type_VO material_Type_VO);
	public void delete(String ma_ty_no);
    public Material_Type_VO findByPrimaryKey(String ma_ty_no);
    public List<Material_Type_VO> getAll();
	
}
