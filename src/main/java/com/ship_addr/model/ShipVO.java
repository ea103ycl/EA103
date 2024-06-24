package com.ship_addr.model;

public class ShipVO implements java.io.Serializable {
	private Integer ship_id;
	private String mem_id;
	private Integer sh_zip;
	private String sh_name;
	private String sh_phone;
	private String sh_addr;

	public Integer getShip_id() {
		return ship_id;
	}

	public void setShip_id(Integer ship_id) {
		this.ship_id = ship_id;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public Integer getSh_zip() {
		return sh_zip;
	}

	public void setSh_zip(Integer sh_zip) {
		this.sh_zip = sh_zip;
	}

	public String getSh_name() {
		return sh_name;
	}

	public void setSh_name(String sh_name) {
		this.sh_name = sh_name;
	}

	public String getSh_phone() {
		return sh_phone;
	}

	public void setSh_phone(String sh_phone) {
		this.sh_phone = sh_phone;
	}

	public String getSh_addr() {
		return sh_addr;
	}

	public void setSh_addr(String sh_addr) {
		this.sh_addr = sh_addr;
	}

}
