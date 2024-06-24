package com.preorderdetail.model;

import java.util.List;

import com.preorderdetail.model.PreOrderDetailVO;

public interface PreOrderDetailDAO_interface {
	public void insert(PreOrderDetailVO preorderdetailVO);
    public void update(PreOrderDetailVO preorderdetailVO);
    public void delete(String po_no);
    public void deleteBypo_prod_no(String po_prod_no);
    public List<PreOrderDetailVO> findByPrimaryKey(String po_no);
    public PreOrderDetailVO getAllByPo_no(String po_no);
    public List<PreOrderDetailVO> getAll();
    public void insert2 (PreOrderDetailVO preorderdetailVO , java.sql.Connection con);
	public List<PreOrderDetailVO> getAll_OrderQty();
}
