package com.meeting.model;

import java.util.*;

import com.reg_inf.model.Reg_infVO;

public interface MeetingDAO_interface {
          public void insert(MeetingVO meetingVO);
          public void update(MeetingVO meetingVO);
          public void delete(String mt_no);
          public MeetingVO findByPrimaryKey(String mt_no);
          public List<MeetingVO> getAll();
		  public void cancel(MeetingVO meetingVO);
          public List<MeetingVO> getMem_Meeting(String mem_id);
          public List<MeetingVO> getMt_Meeting(String mt_no);

          public List<MeetingVO> getCancel_Meeting();
          public List<MeetingVO> FuzzySearch(String mt_id);


}
