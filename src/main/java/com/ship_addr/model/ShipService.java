package com.ship_addr.model;

import java.util.List;

public class ShipService
{

	private ShipDAO_interface dao;

	public ShipService()
	{
		dao = new ShipJDBCDAO();
	}

	public ShipVO addShip(String mem_id, Integer sh_zip, String sh_name, String sh_phone, String sh_addr)
	{

		ShipVO shipVO = new ShipVO();

		shipVO.setMem_id(mem_id);
		shipVO.setSh_zip(sh_zip);
		shipVO.setSh_name(sh_name);
		shipVO.setSh_phone(sh_phone);
		shipVO.setSh_addr(sh_addr);
		dao.insert(shipVO);

		return shipVO;

	}

	public ShipVO UpdateShip(Integer sh_zip, String sh_name, String sh_phone, String sh_addr, Integer ship_id)
	{

		ShipVO shipVO = new ShipVO();

		shipVO.setSh_zip(sh_zip);
		shipVO.setSh_name(sh_name);
		shipVO.setSh_phone(sh_phone);
		shipVO.setSh_addr(sh_addr);
		shipVO.setShip_id(ship_id);
		dao.update(shipVO);

		return shipVO;
	}

	public void deleteOneShip(Integer ship_id)
	{

		dao.deleteOne(ship_id);
	}

	public void deleteAllShip(String mem_id)
	{

		dao.deleteAll(mem_id);
	}

	public ShipVO getOneShip(Integer ship_id)
	{

		return dao.findByPrimaryKey(ship_id);
	}

	public List<ShipVO> getAll()
	{

		return dao.getAll();
	}

}
