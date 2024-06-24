package com.wda.model;

import java.util.List;

public interface WdaDAO_interface {

	public void insert(WdaVO wdaVO);

	public void update(WdaVO wdaVO);

	public void delete(Integer wda_id);

	public WdaVO findByPrimaryKey(Integer wda_id);

	public List<WdaVO> getAll();

}
