package com.ship_addr.model;

import java.util.List;

public interface ShipDAO_interface {
	public void insert(ShipVO shipVO);

	public void update(ShipVO shipVO);

	public void deleteOne(Integer ship_id);

	public void deleteAll(String mem_id);

	public ShipVO findByPrimaryKey(Integer ship_id);

	public List<ShipVO> getAll();

}
