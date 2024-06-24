<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.event_p.model.*"%>
<%@page import="com.event.model.*"%>
<%@page import="com.event_p_rep.model.*"%>
<%@page import="com.vote_d.model.*" %>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%
	HttpSession sess=request.getSession();	
	String event_no=null;
	if(request.getAttribute("event_no")!=null){
		event_no=(String)request.getAttribute("event_no");	
		sess.setAttribute("event_no", event_no);
	}else{
		event_no=(String)sess.getAttribute("event_no");
	}//把活動編號鎖定在session
	
	EventService eventSvc=new EventService();
	EventVO eventVO=eventSvc.findByPrimaryKey(event_no);
	pageContext.setAttribute("eventVO",eventVO);
	
	//當活動結束會依照投票高到低排序顯示
	Event_PDAO dao=new Event_PDAO();
	List<Event_PVO> event_pVOs=null;
	if(sess.getAttribute("event_pVOs")!=null){
		event_pVOs=(List<Event_PVO>)sess.getAttribute("event_pVOs");
	}else if(eventVO.getEvent_stat()==3){
		event_pVOs=dao.findAllByEventNoRankDescWithoutReport(event_no);
		
	}else{
		event_pVOs=dao.findAllNoReport(event_no);
		
	}
	pageContext.setAttribute("event_pVOs",event_pVOs);
	
	
	HttpSession ses=request.getSession();	
	if(ses.getAttribute("mem_id")==null){
		ses.setAttribute("mem_id","M000003");//可以寫不同編號測試
	}	
	Event_PService eventPSVC=new Event_PService();
	boolean checkMemPic=eventPSVC.checkUploadByMemid((String)ses.getAttribute("mem_id"),eventVO.getEvent_no());
	pageContext.setAttribute("checkMemPic", checkMemPic);//確認會員是否投過搞
	
	//判斷是否檢舉過
	Event_P_RepService repSvc=new Event_P_RepService();
	Event_P_RepVO event_p_repVO=repSvc.findByMemId((String)ses.getAttribute("mem_id"));
	
	Timestamp[] event_statTime={eventVO.getEvent_start(),eventVO.getEvent_ul_end(),eventVO.getEvent_vote_end(),eventVO.getEvent_end()};//活動尚未開始，開始徵稿，投票，活動結束
	pageContext.setAttribute("event_statTime",event_statTime);//use in countDownTimer
	
	String[] event_statStr={"活動尚未開始","徵稿","投票","活動結束"};
	pageContext.setAttribute("event_statStr",event_statStr);
	
	//取得會員已投過的作品
	Vote_DService voteSvc=new Vote_DService();
	List<Vote_DVO> voteVOs=voteSvc.findAllByMem((String)sess.getAttribute("mem_id"));
	List<Integer> event_p_nos=new ArrayList<Integer>();
	Iterator iter=voteVOs.iterator();
	while(iter.hasNext()){
		Vote_DVO voteVO= (Vote_DVO)iter.next();
		event_p_nos.add(voteVO.getEvent_p_no());
	}
	pageContext.setAttribute("event_p_nos", event_p_nos);
	
	
	
%>

<%-- <c:if test="${not empty errMsgs}"> --%>
<!-- 	<font style="color:red">請修正以下錯誤:</font> -->
<!-- 	<ul> -->
<%-- 	    <c:forEach var="message" items="${errMsgs}"> --%>
<%-- 			<li style="color:red">${message}</li> --%>
<%-- 		</c:forEach> --%>
<!-- 	</ul> -->
<%-- </c:if> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/frontend/template/css/bootstrap.min.css">
<link
	href="<%=request.getContextPath()%>/frontend/template/css/album.css">
<title>主題競賽</title>
<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

#selectBar {
	margin-right: -0.5%;
}

.queryRow {
	margin-bottom: 1%;
}

#queryResult {
	margin-left: 1.5%;
}

#error{
	color:red;
}
</style>
<%--     <link href="<%=request.getContextPath() %>/frontend/temple/css/album.css" rel="stylesheet"> --%>
</head>
<body>
<%-- <%@include  file="/frontend/event_p/frontBarTop.jsp"%> --%>
	<header>
		<a href="event_homePage.jsp">回到瀏覽主題競賽</a>
	</header>
	<main role="main">
		<section class="jumbotron text-center">
			<div class="container">
				<h1>
					本次活動編號:<%=sess.getAttribute("event_no") %></h1>
				<h2>目前活動狀態:
				${event_statStr[eventVO.event_stat]}
				${eventVO.event_stat }
				</h2>
				<p class="lead text-muted"></p>
				<!--                 <div class="CountDown">CountDown Timer</div> -->
				<div id="countDownTimer" style="display: none;">
					<!--  style="display: none; 不會佔據空間並隱藏 -->
					<fmt:parseDate var="dateObject"
						value="${event_statTime[eventVO.event_stat]}" pattern="yyyy-MM-dd" />
					<fmt:formatDate value="${dateObject}" pattern="yyyy-MM-dd" />
				</div>
				${event_statTime[eventVO.event_stat]}
				<div id="displayReamainTime"></div>
				
				
				<p>
					<!-- Button trigger modal -->
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#exampleModalCenter">我的投稿作品</button>

					<!-- Modal -->
				<div class="modal fade" id="exampleModalCenter" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalCenterTitle"
					aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered modal-lg"
						role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLongTitle">Modal
									title</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<c:choose>
									<c:when test="${checkMemPic}">
										<%@include file="displayMemUpload.jsp"%>
									</c:when>
									<c:otherwise>
					      		尚未上傳作品。
					      	</c:otherwise>
								</c:choose>
							</div>
							<div class="modal-footer"></div>
						</div>
					</div>
				</div>
				<!-- Modal end-->

				<input type="button" class="btn btn-sm btn-outline-secondary"
					value="上傳作品"
					<%=eventVO.getEvent_stat()!=1||checkMemPic?"disabled":"" %>
					onclick="location.href='TestInsert.jsp;'">
				</p>
			</div>
		</section>

		<div class="album py-5 bg-light">

			<div class="container ">
				<div class="row">
					<div class="row queryRow">
						<div class=" col-sm-6 " id="queryResult">
						<%@ include	file="page1.file"%>
						</div>
						<span class="pull-right space-right col-sm-2" id="selectBar">
							<form action="Event_pServlet" method="post">
								<select class="selectpicker" name="colName">
									<optgroup label="Sort By:">
										<option value="event_p_dateWithdesc">投稿日期新到舊</option>
										<option value="event_p_dateWithasc">投稿日期舊到新</option>
										<option value="event_vote_numWithdesc">投票數高到低</option>
										<option value="event_vote_numWithasc">投票數低到高</option>
										<!-- 		                        <option>Newness</option> -->
										<!-- 		                        <option>Rating</option> -->
										<!-- 		                        <option>Price Low to High</option> -->
										<!-- 		                        <option>Price High to Low</option> -->
									</optgroup>
								</select> <input type="hidden" name="action" value="selectOrderBy">
								<input type="submit" value="搜尋">
							</form>
						</span>
					</div>
					<c:forEach var="event_pVO" items="${event_pVOs}"
						begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1 %>">
						<div class="col-md-4">

							<div class="thumbnail" class="checkreport">
								<!--   card mb-4 shadow-sm change to thumbnail  style="display: none;"-->
								${event_pVO.event_p_name } ${event_pVO.event_p_no }
								${event_pVO.event_p_date } 
								<img src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${event_pVO.event_p_no}" width="300px" height="300px" fill="#55595c">
								<!--投稿作品檢舉狀態為2(檢舉成功)不會顯示  -->
								<title>Placeholder</title>
								</svg>
								<div class="caption">
									<!--   card-body change to caption -->
									<!--                                 This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer. -->
									<p class="card-text">
									<div class="row">
										<div class="col-lg-6 ">
											<form action="Event_P_RepServlet" method="post">
												<input type="hidden" name="event_no"
													value="<%=(String)sess.getAttribute("event_no")%>">
												<input type="hidden" name="mem_id"value="${sessionScope.mem_id }">
												<input type="hidden" name="event_p_no" value="${event_pVO.event_p_no}"> 
												<input type="hidden" name="action" value="report"> 
													<input	type="submit" class="btn btn-default" value="檢舉"	<%=event_p_repVO!=null?"disabled":""%>> <!-- eventVO.getEvent_stat()!=2|| -->
<%-- 													<c:if test="${not empty reportMsgs&&event_p_no==event_pVO.event_p_no}"> --%>
<%-- 														<c:forEach var="message" items="${reportMsgs}"> --%>
<%-- 															<td style="color:red;">${message}</td><br> --%>
<%-- 														</c:forEach> --%>
<!-- 														<tr> -->
<%-- 													</c:if> --%>
												<!--btn btn-sm btn-outline-secondary change to  -->
											</form>
										</div>
									</div>
									<ul class="list-group">
										<lem class="list-group-item">排名:${event_pVO.vote_rank }</li>
										<lem class="list-group-item">票數:${event_pVO.event_vote_num }</li>
									</ul>
									</p>
									<div class="d-flex justify-content-between align-items-center">
										<!-- div class="btn-group" change to p tag -->
										<div class="row">
											<!--                                         <button type="button" class="btn btn-sm btn-outline-secondary">View</button> -->
											<div class="col-lg-2">
												<form action="Event_pServlet" method="post">
													<input type="hidden" name="event_no"
														value="${eventVO.event_no}"> <input type="hidden"
														name="mem_id" value="${sessionScope.mem_id }">
													<%-- event_no 和mem_id 用於確認投了幾次  --%>
													<input type="hidden" name="event_p_no"
														value="${event_pVO.event_p_no}"> <input
														type="hidden" name="action" value="vote">
													<%-- <input type="submit" value="投票" <%=eventVO.getEvent_stat()!=2?"disabled":"" %>> --%>
													<input type="submit" class="btn btn-primary" value="投票"
														${eventVO.getEvent_stat()!=2||event_p_nos.contains(event_pVO.event_p_no)?"disabled":"" }>
													<!-- 把錯誤訊息用alert方式顯示 -->
													<!-- btn btn-sm btn-outline-secondary change to btn btn-primary -->
												</form>
											</div>
										<small class="text-muted col-lg-6" id="error">
											<c:if test="${not empty errMsgs&&event_p_no==event_pVO.event_p_no}">
											 
	<!-- 												<font style="color: red">請修正以下錯誤:</font> -->
														<tr>
														<c:forEach var="message" items="${errMsgs}">
															<td style="color:red;">${message}</td><br>
														</c:forEach>
														<tr>
											
											</c:if>
										</small>
											<div class="col-lg-4 "><!-- col-lg-offset-2 -->
												<form action="Event_pServlet" method="post">
													<input type="hidden" name="mem_id"
														value="${sessionScope.mem_id }"> 
														<input type="hidden" name="event_p_no" value="${event_pVO.event_p_no}"> 
														<input	type="hidden" name="action" value="deleteVote">
														<input	type="submit" class="btn btn-default" value="取消投票"<%=eventVO.getEvent_stat()!=2?"disabled":"" %>>
													<!--btn btn-sm btn-outline-secondary change to  -->
												</form>
											</div>

											<!--                                         <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button> -->
										</div>


									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
	</main>
	<div class="container"><%@ include file="page2.file"%></div>
</body>
<footer class="text-muted">
	<div class="container">
		<p class="float-right">
			<a href="#">Back to top</a>
		</p>
		<p>Album example is &copy; Bootstrap, but please download and
			customize it for yourself!</p>
		<p>
			New to Bootstrap? <a href="https://getbootstrap.com/">Visit the
				homepage</a> or read our <a href="../getting-started/introduction/">getting
				started guide</a>.
		</p>
	</div>
</footer>
<%-- <script src="<%=request.getContextPath()%>/frontend/template/jquery/jquery-3.5.1.js"></script> --%>
<%-- <script src="<%=request.getContextPath()%>/frontend/template/js/bootstrap.min.js"></script> --%>
<script
	src="<%=request.getContextPath() %>/frontend/template/jquery/jquery-3.1.0.min.js"></script>
<script>
    window.jQuery || document.write('<script src="<%=request.getContextPath()%>/frontend/template/jquery/jquery.min.js"><\/script>')
</script>
<script
	src="<%=request.getContextPath()%>/frontend/template/js/bootstrap.min.js"></script>
<script>
	// 		var checkreport=document.getElementsByClassName("checkreport");
	// 		for(let i=0;i<checkreport.length;i++){
	// 			if(checkreport[i].value==2){
	// 				checkreport[i].style.visibility="hidden";
	// 			}
	// 		}
	var countDownTimer = document.getElementById("countDownTimer");
	console.log(countDownTimer.textContent);
	//extract  string tomatch js date format input
	var timer = countDownTimer.textContent.split("-");
	var y = timer[0];
	var m = timer[1];
	var d = timer[2];
	console.log(d);
	var timer = setInterval(countDown, 1000);
	function countDown() {
		var countDownTime = new Date(y, m - 1, d).getTime();//js會月份+1
		var now = new Date().getTime();
		var remainTime = countDownTime - now;//get long remain time

		//start convert long time to day,hours,min,sec,
		var day = Math.floor(remainTime / (1000 * 60 * 60 * 24));
		var hours = Math.floor((remainTime % (1000 * 60 * 60 * 24))
				/ (1000 * 60 * 60));
		var min = Math.floor((remainTime % (1000 * 60 * 60)) / (1000 * 60));
		var sec = Math.floor((remainTime % (1000 * 60)) / (1000));
		console.log(day);
		//create one div under the countDownTimer div
		var display = document.getElementById("displayReamainTime");
		display.innerHTML = "剩下" + day + "天" + hours + "小時" + min + "分" + sec
				+ "秒";
		if (remainTime < 0) {
			display.innerHTML = "已結束";
		}
	}
</script>
</html>