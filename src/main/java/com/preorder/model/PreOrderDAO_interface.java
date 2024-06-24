package com.preorder.model;

import java.util.List;
import java.util.Set;

import com.preorder.model.*;
import com.preorderdetail.model.PreOrderDetailVO;

	public interface PreOrderDAO_interface {
        public void insert(PreOrderVO preorderVO);
        public void update(PreOrderVO preorderVO);
        public void updateStatus(PreOrderVO preorderVO);
        public void delete(String po_no);
        public PreOrderVO findByPrimaryKey(String po_no);
        public List<PreOrderVO> getAllByMemid(String mem_id);
        public List<PreOrderVO> getAll();
        public Set<PreOrderDetailVO> getDetailByOrderno(String po_no);
        public void inserWithDetail(PreOrderVO preorderVO , List<PreOrderDetailVO> list);
        
        public List<PreOrderVO> look_discount_pono(Integer reach_number,String po_prod_no);
}
