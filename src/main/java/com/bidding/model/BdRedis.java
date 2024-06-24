package com.bidding.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.biddingPage.controller.BdTimer;
import com.event.model.EventService;
import com.event_p.model.Event_PService;
import com.event_p.model.Event_PVO;

import redis.clients.jedis.Jedis;

public class BdRedis {

	public BdRedis() {
		super();
	}

	public BiddingVO getRunningBd() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

		Timestamp bdDateStr = new Timestamp(Long.valueOf(jedis.get("startTime")));
		Timestamp bdDateEnd = new Timestamp(Long.valueOf(jedis.get("endTime")));

		BiddingVO bVO = new BiddingVO();

		bVO.setBdNo(jedis.get("bdNo"));
		bVO.setBdDateStr(bdDateStr);
		bVO.setBdDateEnd(bdDateEnd);

		jedis.close();
		return bVO;
	}

	public void registerBdNo(String eventNo) {
		if (isRegistered()) {
			return;
		}
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

		if (jedis.exists("result:"+eventNo)) {
			jedis.close();
			return;
		}

		Event_PService epSvc= new Event_PService();
		Event_PVO epVO=epSvc.findTopByEventNoWithoutReport(eventNo);
		
		Integer eventpno=epVO.getEvent_p_no();
		
		if(eventpno==null) {
			eventpno=3001;
		}
		
		
		jedis.set("bdNo", eventNo);
//		jedis.expire("bdNo", (60 * 60 * 24 * 30));// sec

		Long startTime = System.currentTimeMillis();
		jedis.set("startTime", startTime.toString());

//		Long endTime = startTime + (60 * 60 * 24 * 30 * 1000L);// millis
		Long endTime = startTime + (60 * 10 * 1000L);// millis
		jedis.set("endTime", endTime.toString());

		BiddingService bdSvc = new BiddingService();
		Timestamp startT = new Timestamp(Long.valueOf(startTime));
		Timestamp endT = new Timestamp(Long.valueOf(endTime));
		String sqlBdNo = bdSvc.insert("",eventpno, 0, startT, endT, 0, 0, 0, 0, "", "", "");
		// 3001 bdProdNo, 1 競標作品編號

		// ======save another info to record========
		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", startTime.toString());
		map.put("endTime", endTime.toString());
		map.put("sqlBdNo", sqlBdNo);
		jedis.hmset("result:" + eventNo, map);

		// =======start Timer=============
		BdTimer timer = new BdTimer();
		timer.startTimer();
		// =======start Timer=============

		jedis.close();
	}

	public boolean isRegistered() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		if(jedis.exists("bdNo")) {
			jedis.close();
			return true;
		}
		jedis.close();
		return false;
	}

	public void newBid(String bdNo, String memId, Integer price) {
		if (isRegistered()) {
			Jedis jedis = new Jedis("localhost", 6379);
			jedis.auth("123456");

			jedis.zadd(bdNo, price, memId);

			System.out.println("(BdRedis)new bid!");

			jedis.close();
		} else {
			System.out.println("(BdRedis) bdNo is not Registered");
			return;
		}
	}

	public String getHighestBidder(String bdNo) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

		Set<String> set = jedis.zrevrange(bdNo, 0, 0);

		if (set.isEmpty()) {
			jedis.close();
			return "noWinner";
		}
		Iterator<String> i = set.iterator();

		String highestBidder = i.next();

		jedis.close();
		return highestBidder;
	}

	public Integer getTopBid(String bdNo) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

		Set<String> set =  jedis.zrevrange(bdNo, 0, 0);

		if (set.isEmpty()) {
			jedis.close();
			return 0;
		}
		Iterator<String> i = set.iterator();

		String highestBidder = i.next();
		Integer highestBid = jedis.zscore(bdNo, highestBidder).intValue();

		jedis.close();
		return highestBid;
	}

	public Integer getBid(String bdNo, String memId) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

		Integer bid = jedis.zscore(bdNo, memId).intValue();

		jedis.close();
		return bid;
	}

	public String[] getTopBidders(String bdNo) {

		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

		Set<String> set = jedis.zrevrange(bdNo, 0, 4);
		Iterator<String> i = set.iterator();
		String[] topBidders = new String[set.size()];

		int n = 0;
		while (i.hasNext()) {
			topBidders[n] = i.next();
			n++;
		}

		jedis.close();
		return topBidders;
	}

	public Integer getBidsCount(String bdNo) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

		Integer count = (jedis.zcard(bdNo)).intValue();

		jedis.close();
		return count;
	}

	public Timestamp getBdEndTime() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

		Timestamp bdDateEnd = new Timestamp(Long.valueOf(jedis.get("endTime")));

		jedis.close();
		return bdDateEnd;
	}

	public Timestamp getBdStartTime() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

		Timestamp bdStartTime = new Timestamp(Long.valueOf(jedis.get("startTime")));

		jedis.close();
		return bdStartTime;
	}

	public String getSqlBdNo(String bdNo) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		String sqlBdNo = jedis.hget("result:" + bdNo, "sqlBdNo");

		jedis.close();
		return sqlBdNo;
	}

	public String clearRunningBdNo() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		String bdNo = jedis.get("bdNo");
		jedis.expire("bdNo", 0);
		jedis.close();
		return bdNo;
	}


	public static void main(String[] args) {
		BdRedis bdr = new BdRedis();
//		bdr.newBid("B00001", "M0001", 150);
//		bdr.newBid("B00001", "M0002", 200);
//		bdr.getHighestBid("B00001");
//		System.out.println("(BdRedis) getTopBidders:"+bdr.getTopBidders("B00001")[0]);
//		System.out.println("(BdRedis)"+bdr.getBidsCount("B00001"));	
//		bdr.registerBdNo("B0001");
//		bdr.storeDB("B00003");
//		bdr.paid("B0011");
//		bdr.getRunningBd();
//		bdr.getLatestBdNo(0);
	}

}
