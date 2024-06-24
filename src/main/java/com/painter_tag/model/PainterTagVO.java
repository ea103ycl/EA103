package com.painter_tag.model;

public class PainterTagVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer tag_no; // Hashtag流水號
	private String tag_desc; // hashtag內容

	public Integer getTag_no() {
		return tag_no;
	}

	public void setTag_no(Integer tag_no) {
		this.tag_no = tag_no;
	}

	public String getTag_desc() {
		return tag_desc;
	}

	public void setTag_desc(String tag_desc) {
		this.tag_desc = tag_desc;
	}

}
