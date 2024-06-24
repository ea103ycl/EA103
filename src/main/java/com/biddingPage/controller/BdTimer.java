package com.biddingPage.controller;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import com.bidding.model.BdRedis;
import com.bidding.model.BiddingVO;

public class BdTimer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Timer timer=null;
	
	@Override
	public void init(ServletConfig config) {

	}
	@Override 
	public void destroy() {
		timer.cancel();
		System.out.println("(BdTimer) timer canceled)");
	}

	public void startTimer() {
		BdRedis bdr =new BdRedis();
		timer =new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {	
				System.out.println("(BdTimer) timer task activated...");
				String bdNo=bdr.clearRunningBdNo();
				System.out.println("(BdTimer) clearing running bdNo: "+ bdNo);
				timer.cancel();
			}
		};
		Date date= new Date(bdr.getBdEndTime().getTime());
		System.out.println(date);
		timer.schedule(task, date);
	}
}
