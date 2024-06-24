package com.bank.model;

public class BankVO implements java.io.Serializable
{
	private Integer bank_id;
	private String mem_id;
	private String bn_code;
	private String bn_accno;
	private String bn_accname;

	public Integer getBank_id()
	{
		return bank_id;
	}

	public void setBank_id(Integer bank_id)
	{
		this.bank_id = bank_id;
	}

	public String getMem_id()
	{
		return mem_id;
	}

	public void setMem_id(String mem_id)
	{
		this.mem_id = mem_id;
	}

	public String getBn_code()
	{
		return bn_code;
	}

	public void setBn_code(String bn_code)
	{
		this.bn_code = bn_code;
	}

	public String getBn_accno()
	{
		return bn_accno;
	}

	public void setBn_accno(String bn_accno)
	{
		this.bn_accno = bn_accno;
	}

	public String getBn_accname()
	{
		return bn_accname;
	}

	public void setBn_accname(String bn_accname)
	{
		this.bn_accname = bn_accname;
	}

}
