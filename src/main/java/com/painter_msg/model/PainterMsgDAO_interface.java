package com.painter_msg.model;

import java.util.List;

public interface PainterMsgDAO_interface {
	
	public void insert(PainterMsgVO painterMsgVO);
	public void	delete(Integer msg_no, Integer msg_stat);
	public List<PainterMsgVO> getAll(Integer ptr_no);
	public Integer getPainterMsgCnt(Integer ptr_no);
}
