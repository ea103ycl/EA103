package com.meeting.model;

import java.sql.Timestamp;
import java.util.List;

import com.prod.model.ProdVO;
import com.reg_inf.model.Reg_infVO;

public class MeetingService {

	private MeetingDAO_interface dao;

	public MeetingService() {
		dao = new MeetingDAO();
	}

	public MeetingVO addMeeting(String mem_id,Integer mt_status, 
			Integer max_num,Integer min_num,String mt_place,Integer ri_fee,String mt_detail, 
			Timestamp mt_start_time, Timestamp mt_end_time,Timestamp mt_time,String mt_id,Integer mt_num,byte[] mt_pic) {

		MeetingVO meetingVO = new MeetingVO();

		meetingVO.setMem_id(mem_id);
		meetingVO.setMt_status(mt_status);
		meetingVO.setMax_num(max_num);
		meetingVO.setMin_num(min_num);
		meetingVO.setMt_place(mt_place);
		meetingVO.setRi_fee(ri_fee);
		meetingVO.setMt_detail(mt_detail);
		meetingVO.setMt_start_time(mt_start_time);
		meetingVO.setMt_end_time(mt_end_time);
		meetingVO.setMt_time(mt_time);
		meetingVO.setMt_id(mt_id);
		meetingVO.setMt_num(mt_num);
		meetingVO.setMt_pic(mt_pic);

		dao.insert(meetingVO);

		return meetingVO;
	}
	
	public MeetingVO updateMeeting(String mt_no, String mem_id, 
			Integer max_num,Integer min_num,String mt_place,Integer ri_fee,String mt_detail, 
			Timestamp mt_start_time, Timestamp mt_end_time,Timestamp mt_time,String mt_id,byte[] mt_pic) {

		MeetingVO meetingVO = new MeetingVO();

		meetingVO.setMt_no(mt_no);
		meetingVO.setMem_id(mem_id);
		meetingVO.setMax_num(max_num);
		meetingVO.setMin_num(min_num);
		meetingVO.setMt_place(mt_place);
		meetingVO.setRi_fee(ri_fee);
		meetingVO.setMt_detail(mt_detail);
		meetingVO.setMt_start_time(mt_start_time);
		meetingVO.setMt_end_time(mt_end_time);
		meetingVO.setMt_time(mt_time);
		meetingVO.setMt_id(mt_id);
		meetingVO.setMt_pic(mt_pic);

		dao.update(meetingVO);

		return meetingVO;
	}

	

	public void deleteMeeting(String mt_no) {
		dao.delete(mt_no);
	}

	public MeetingVO getOneMeeting(String mt_no) {
		return dao.findByPrimaryKey(mt_no);
	}

	public List<MeetingVO> getAll() {
		return dao.getAll();
	}
	public MeetingVO cancelMeeting(Integer mt_status,String mt_no) {

		MeetingVO meetingVO = new MeetingVO();
		meetingVO.setMt_no(mt_no);
		meetingVO.setMt_status(mt_status);

		dao.cancel(meetingVO);
		return meetingVO;
	}
	public List<MeetingVO> getMem_Meeting(String mem_id) {

		return dao.getMem_Meeting(mem_id);
		
	}
	public List<MeetingVO> getMt_Meeting(String mt_no) {

		return dao.getMt_Meeting(mt_no);
		
	}
	public List<MeetingVO> getCancel_Meeting() {

		return dao.getCancel_Meeting();
		
	}
	public List<MeetingVO> FuzzySearch(String mt_id) {
		return dao.FuzzySearch(mt_id);
	}
}
