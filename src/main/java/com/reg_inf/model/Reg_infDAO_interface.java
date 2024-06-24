package com.reg_inf.model;

import java.util.*;

public interface Reg_infDAO_interface {
          public String insert(Reg_infVO reg_infVO);
          public void update(Reg_infVO reg_infVO);
          public void delete(String ri_id);
          public Reg_infVO findByPrimaryKey(String ri_id);
          public List<Reg_infVO> getAll();
          public List<Reg_infVO> getMem_Reg_inf(String mem_id);
          public void cancel(Reg_infVO reg_infVO);
          public List<Reg_infVO> getReg_inf_mt_no(String mt_no);
          public void check_mt_num();


}
