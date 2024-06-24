package com.painter_report.model;

import java.sql.Timestamp;

public class PainterReportVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer ptr_no; // 被檢舉的投稿作品流水號
	private String mem_id; // 檢舉者會員編號
	private String reason; // 檢舉原因
	private Timestamp rpt_dt; // 檢舉時間
	private Integer rpt_stat; // 檢舉狀態

	public Integer getPtr_no() {
		return ptr_no;
	}

	public void setPtr_no(Integer ptr_no) {
		this.ptr_no = ptr_no;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Timestamp getRpt_dt() {
		return rpt_dt;
	}

	public void setRpt_dt(Timestamp rpt_dt) {
		this.rpt_dt = rpt_dt;
	}

	public Integer getRpt_stat() {
		return rpt_stat;
	}

	public void setRpt_stat(Integer rpt_stat) {
		this.rpt_stat = rpt_stat;
	}

}
