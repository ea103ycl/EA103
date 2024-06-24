package com.event.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.event.model.EventService;
import com.event.model.EventVO;
import com.event_p.model.Event_PDAO;
import com.event_p.model.Event_PJDBCDAO;
import com.event_p.model.Event_PVO;


@WebListener
public class EventSchedule implements ServletContextListener {
	Timer timer=null;
	EventTimer task=null;
    Date myDate=null;
	public EventSchedule() {
    }


    public void contextDestroyed(ServletContextEvent sce)  { 
    	System.out.println("活動排程器已結束");
    	timer.cancel();
    }


    public void contextInitialized(ServletContextEvent sce)  { 
    	timer=new Timer();
    	task=new EventTimer();
    	myDate=new Date();
    	timer.schedule(task, myDate, 300000);
    }
	
}
class EventTimer extends TimerTask{

	@Override
	public void run() {
//		System.out.println("start run schedule");
		EventService svc = new EventService();
//		List<EventVO> eventVOs = svc.findAllEvent();
		List<EventVO> eventVOs=svc.findWithoutEnd();//已結束不會再判斷
		Iterator iter = eventVOs.iterator();
		// 轉換現在時間成Timestamp
		//test 
		GregorianCalendar gc=new GregorianCalendar(2020,9,23);
		Date gcDate=gc.getTime();
		Timestamp timestamp=new Timestamp(gc.getTimeInMillis());
		
//		Timestamp timestamp = new Timestamp(new Date().getTime());
		while (iter.hasNext()) {
			System.out.println("in run");
			// 開始比對時間。
			EventVO eventVO = (EventVO) iter.next();

			if (timestamp.getTime() >= eventVO.getEvent_end().getTime()) {
				System.out.println(eventVO.getEvent_no()+"改為活動結束");
				// if活動結束-改變活動狀態-結束投票顯示排行。timestamp>=event_end ->3
				//排名刷新--start
//				Event_PJDBCDAO dao=new Event_PJDBCDAO();
				Event_PDAO dao=new Event_PDAO();
				List<String> event_nos=dao.findAllEventNo();
				
				Iterator<String> iterEventNO=event_nos.iterator();
				while(iterEventNO.hasNext()) {
					String event_no=iterEventNO.next();
					List<Event_PVO> event_pVOs= dao.bigRankSort(event_no, 20);
					Iterator<Event_PVO> iterRank=event_pVOs.iterator();
					while(iterRank.hasNext()) {
						Event_PVO event_pVO= iterRank.next();
						dao.bigRankUpdate(event_pVO.getVote_rank(),event_pVO.getEvent_p_no());
					}
				}
				//排名刷新--end
				eventVO.setEvent_stat(3);
				svc.update(eventVO.getEvent_no(), eventVO.getEvent_name(), eventVO.getEvent_start(),
						eventVO.getEvent_end(), eventVO.getEvent_ul_start(), eventVO.getEvent_ul_end(),
						eventVO.getEvent_vote_start(), eventVO.getEvent_vote_end(), eventVO.getEvent_stat());
				svc.findLastEndEvent();
				
			}else if (timestamp.getTime() >= eventVO.getEvent_vote_start().getTime()) {
				System.out.println(eventVO.getEvent_no()+"改為投票中");
				// if投票開始-改變活動狀態-開始投票。timestamp>=event_vote_start ->2
				eventVO.setEvent_stat(2);
				svc.update(eventVO.getEvent_no(), eventVO.getEvent_name(), eventVO.getEvent_start(),
						eventVO.getEvent_end(), eventVO.getEvent_ul_start(), eventVO.getEvent_ul_end(),
						eventVO.getEvent_vote_start(), eventVO.getEvent_vote_end(), eventVO.getEvent_stat());
			}else if (timestamp.getTime() >= eventVO.getEvent_ul_start().getTime()) {
				System.out.println(eventVO.getEvent_no()+"改為徵稿中");
				eventVO.setEvent_stat(1);
				svc.update(eventVO.getEvent_no(), eventVO.getEvent_name(), eventVO.getEvent_start(),
						eventVO.getEvent_end(), eventVO.getEvent_ul_start(), eventVO.getEvent_ul_end(),
						eventVO.getEvent_vote_start(), eventVO.getEvent_vote_end(), eventVO.getEvent_stat());
			} else {
				System.out.println(eventVO.getEvent_no()+"改為活動尚未開始");
				eventVO.setEvent_stat(0);
				svc.update(eventVO.getEvent_no(), eventVO.getEvent_name(), eventVO.getEvent_start(),
						eventVO.getEvent_end(), eventVO.getEvent_ul_start(), eventVO.getEvent_ul_end(),
						eventVO.getEvent_vote_start(), eventVO.getEvent_vote_end(), eventVO.getEvent_stat());
			}
			
		}
	}
	
	
}
