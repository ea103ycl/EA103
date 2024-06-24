package com.card.model_TBD;

import java.sql.Date;

public class CardVO {

	private Integer card_id;
	private String mem_id;
	private String c_no;
	private Date c_vdate;

	public int getCard_id() {
		return card_id;
	}

	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getC_no() {
		return c_no;
	}

	public void setC_no(String c_no) {
		this.c_no = c_no;
	}

	public Date getC_vdate() {
		return c_vdate;
	}

	public void setC_vdate(Date c_vdate) {
		this.c_vdate = c_vdate;
	}

}
