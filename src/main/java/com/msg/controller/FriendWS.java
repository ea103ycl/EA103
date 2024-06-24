package com.msg.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import com.msg.model.ChatMessage;
import com.msg.model.State;

//(L)小吳老師影片時間:2020/10/06 16:18
//(L)須加上annotation，webSocket會於觸發時執行該段程式
//(L)可參考 https://javaee.github.io/javaee-spec/javadocs/javax/websocket/package-summary.html
//(L)@OnOpen   :to decorate a Java method that wishes to be called when a new web socket session is open
//(L)@OnMessage: to make a Java method receive incoming web socket messages.
//(L)@OnError  : to decorate a Java method that wishes to be called in order to handle errors.
//(L)@OnClose  :  to decorate a Java method that wishes to be called when a web socket session is closing.
//(L)@ClientEndpoint:to denote that a POJO is a web socket client and can be deployed as such.

//(L)@ServerEndpoint是告訴容器，這個class要被當成一個webSocket的連結端點，而括號內的value就是這個端點的連結path(類似網址的概念)
@ServerEndpoint("/FriendWS/{mem_id}")
public class FriendWS {

	// (L)使用ConcurrentHashMap可以避免hashmap達到飽和resize後產生死結，並同時做到同步鎖定(效能也比使用hashtable或synchronizedMap好)
	// (L)參考 https://zhuanlan.zhihu.com/p/31614308
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	// (L)連線開啟時
	// (L)@PathParam("mem_id")可以取得URI上的mem_id參數內容，參考
	// https://medium.com/1developer/spring-requestparam-vs-queryparam-vs-pathvariable-vs-pathparam-7c5655e541ad
	// (L)URL是路徑，URI是資源實際位置，參考
	// https://codertw.com/%E5%89%8D%E7%AB%AF%E9%96%8B%E7%99%BC/53793/
	@OnOpen
	public void onOpen(@PathParam("mem_id") String mem_id, Session userSession) throws IOException {

		// (L)將【新上線的會員id】加入【上線中的會員清單】內
		sessionsMap.put(mem_id, userSession);

		// (L)將【上線中的會員清單】的key值都取出(即得到目前在線上的會員IDs)
		Set<String> mem_ids = sessionsMap.keySet();

		State stateMessage = new State("open", mem_id, mem_ids);
		String stateMessageJson = gson.toJson(stateMessage);

		// (L)取出【上線中的會員清單】的value(即取得session)
		// (L)如果session還有效，則同步送出訊息文字
		// (L)getAsyncRemote(異步)與getBasicRemote(同步)，參考 https://blog.csdn.net/who_is_xiaoming/article/details/53287691
		// (L)使用getAsyncRemote可避免因為同步而可能出現訊息阻塞、產生IllegalStateException的情況
		Collection<Session> sessions = sessionsMap.values();
		for (Session session : sessions) {
			if (session.isOpen()) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}
		
		String text = String.format("Session ID = %s, connected; mem_id = %s%nusers: %s", userSession.getId(), mem_id,
				mem_ids);
		System.out.println(text);
	}

	// 當收到訊息時
	@OnMessage
	public void onMessage(Session userSession, String message) {
		
		//將Json物件轉為指定的java物件
		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
		
		//發送訊息的會員
		String sender = chatMessage.getSender();
		
		//接收訊息的會員
		String receiver = chatMessage.getReceiver();
		
		//從redis撈出歷史訊息
		if ("history".equals(chatMessage.getType())) {
			List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver);
			String historyMsg = gson.toJson(historyData);
			ChatMessage cmHistory = new ChatMessage("history", sender, receiver, historyMsg);
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
				System.out.println("history = " + gson.toJson(cmHistory));
				return;
			}
		}
		
		Session receiverSession = sessionsMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
			userSession.getAsyncRemote().sendText(message);
			JedisHandleMessage.saveChatMessage(sender, receiver, message);
		}
		System.out.println("Message received: " + message);
	}

	// 有錯誤時
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	// 連線結束時
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String mem_idClose = null;
		Set<String> mem_ids = sessionsMap.keySet();
		for (String mem_id : mem_ids) {
			if (sessionsMap.get(mem_id).equals(userSession)) {
				mem_idClose = mem_id;
				sessionsMap.remove(mem_id); // 從所有當前在線上的會員名單內剔除
				break;
			}
		}

		if (mem_idClose != null) {
			State stateMessage = new State("close", mem_idClose, mem_ids);
			String stateMessageJson = gson.toJson(stateMessage);
			Collection<Session> sessions = sessionsMap.values();
			for (Session session : sessions) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), mem_ids);
		System.out.println(text);
	}
}
