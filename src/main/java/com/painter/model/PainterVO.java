package com.painter.model;

import java.sql.Timestamp;

public class PainterVO {

	private static final long serialVersionUID = 1L;

	private Integer ptr_no; // 作品流水號
	private String mem_id; // 會員編號
	private String ptr_nm; // 作品名稱
	private String intro; // 作品介紹
	private byte[] pic; // 作品圖片
	private Integer priv_stat; // 隱私權狀態 1:公開 2:僅限個人觀看 3:僅限粉絲觀看
	private Integer ptr_stat; // 作品狀態  1:正常 2:由創作者刪除 3:由管理員刪除
	private Integer like_cnt; // 被喜歡次數
	private Integer col_cnt; // 被收藏次數
	private Timestamp create_dt; // 作品建立時間

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

	public String getPtr_nm() {
		return ptr_nm;
	}

	public void setPtr_nm(String ptr_nm) {
		this.ptr_nm = ptr_nm;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public Integer getPriv_stat() {
		return priv_stat;
	}

	public void setPriv_stat(Integer priv_stat) {
		this.priv_stat = priv_stat;
	}

	public Integer getPtr_stat() {
		return ptr_stat;
	}

	public void setPtr_stat(Integer ptr_stat) {
		this.ptr_stat = ptr_stat;
	}

	public Integer getLike_cnt() {
		return like_cnt;
	}

	public void setLike_cnt(Integer like_cnt) {
		this.like_cnt = like_cnt;
	}

	public Integer getCol_cnt() {
		return col_cnt;
	}

	public void setCol_cnt(Integer col_cnt) {
		this.col_cnt = col_cnt;
	}

	public Timestamp getCreate_dt() {
		return create_dt;
	}

	public void setCreate_dt(Timestamp create_dt) {
		this.create_dt = create_dt;
	}

}
