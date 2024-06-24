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
<%@page import="com.mem.model.*" %>
<%
	HttpSession sess=request.getSession();	
	String event_no=null;
	boolean isEventNoRefresh=false;
	if(request.getAttribute("event_no")!=null){
		event_no=(String)request.getAttribute("event_no");	
		sess.setAttribute("event_no", event_no);
		isEventNoRefresh=true;
	}else{
		event_no=(String)sess.getAttribute("event_no");
		
	}//把活動編號鎖定在session
	
	EventService eventSvc=new EventService();
	EventVO eventVO=eventSvc.findByPrimaryKey(event_no);
	pageContext.setAttribute("eventVO",eventVO);
	
	//當活動結束會依照投票高到低排序顯示
	Event_PService pSvc=new Event_PService();
	Event_P_RepService repSvc=new Event_P_RepService();
	List<Event_PVO> event_pVOs=null;
	if(isEventNoRefresh||sess.getAttribute("event_pVOs")==null){
		event_pVOs=pSvc.findAllNoReport(event_no);//從A主題跳到B主題的作品刷新或檢舉數目更動
	}else if(sess.getAttribute("event_pVOs")!=null){
		event_pVOs=(List<Event_PVO>)sess.getAttribute("event_pVOs");//
	}
	if(eventVO.getEvent_stat()==3){
		event_pVOs=pSvc.findAllByEventNoRankDescWithoutReport(event_no);
		sess.setAttribute("event_pVOs", event_pVOs);
		getServletContext().setAttribute("event_no", eventSvc.findLastEndEvent());
		
	}
	pageContext.setAttribute("event_pVOs",event_pVOs);
	
	

	Event_PService eventPSVC=new Event_PService();
	boolean checkMemPic=eventPSVC.checkUploadByMemid(((MemVO)sess.getAttribute("memVO")).getMem_id(),eventVO.getEvent_no());
	pageContext.setAttribute("checkMemPic", checkMemPic);//確認會員是否投過搞
	
	//判斷是否檢舉過
	
// 	Event_P_RepVO event_p_repVO=repSvc.findByMemId((String)ses.getAttribute("mem_id"));
	Event_P_RepVO event_p_repVO=repSvc.findReportByMemAndEventNo(((MemVO)sess.getAttribute("memVO")).getMem_id(), event_no);//取得會員這次活動是否有檢舉過	

	Timestamp[] event_statTime={eventVO.getEvent_start(),eventVO.getEvent_ul_end(),eventVO.getEvent_vote_end(),eventVO.getEvent_end()};//活動尚未開始，開始徵稿，投票，活動結束
	pageContext.setAttribute("event_statTime",event_statTime);//use in countDownTimer
	
	String[] event_statStr={"活動尚未開始","徵稿","投票","活動結束"};
	pageContext.setAttribute("event_statStr",event_statStr);
	
	//取得會員已投過的作品
	Vote_DService voteSvc=new Vote_DService();
	List<Vote_DVO> voteVOs=voteSvc.findAllByMem(((MemVO)sess.getAttribute("memVO")).getMem_id());
	List<Integer> event_p_nos=new ArrayList<Integer>();
	Iterator iter=voteVOs.iterator();
	while(iter.hasNext()){
		Vote_DVO voteVO= (Vote_DVO)iter.next();
		event_p_nos.add(voteVO.getEvent_p_no());
	}
	pageContext.setAttribute("event_p_nos", event_p_nos);
	
	
	
%>

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

 .queryResult { 
 	margin-left: 1.5%; 
 	
 } 
 #showSearchNum{
 	color:#c39d6d;
 }
 #error{ 
 	color:red;
 	margin-top:5px; 
 }
#neo-vote-button,#neo-delvote-button{
	padding-left:0px;
}
#header-banner{
	background-image: url("<%=request.getContextPath()%>/frontend/template/neoTools/img/DART-Art-web-2020.jpg");/*art-competition.jpg*/
	background-repeat: no-repeat;
	background-position:center;
	background-size: cover;
}

</style>
<%--     <link href="<%=request.getContextPath() %>/frontend/temple/css/album.css" rel="stylesheet"> --%>
</head>
<body>
<div id="preloader">
    <div class="spinner spinner-round"></div>
</div>
<%@include  file="/frontend/bar/frontBarTop.jsp"%>
<form>
	<input type="hidden" id="mem_id" value="<%=((MemVO)sess.getAttribute("memVO")).getMem_id()%>">
</form>



<!-- 		<section class="jumbotron text-center"> -->
    <!-- header-banner -->
    <div id="header-banner">
    
        <div class="banner-content single-page text-center">
            
            <div class="banner-border">
            <img src="<%=request.getContextPath()%>/frontend/template/neoTools/img/art-competition.jpg">    
                <div class="banner-info">
           			<h1>
						本次活動編號:<%=sess.getAttribute("event_no") %>
					</h1>
					<h2>目前活動狀態:
						${event_statStr[eventVO.event_stat]}
<%-- 						${eventVO.event_stat } --%>
					</h2>
                    <h3>${eventVO.event_name}</h3>
                </div>
                <!-- / banner-info -->
            </div>
            <!-- / banner-border -->
        </div>
        <!-- / banner-content -->
    </div>
    <!-- / header-banner -->
			
			<div class="container text-center">
				<div class="row">
								<p>
<!-- 					Button trigger modal -->
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#exampleModalCenter">我的投稿作品</button>

<!-- 					Modal -->
				<div class="modal fade" id="exampleModalCenter" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalCenterTitle"
					aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered modal-lg"
						role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLongTitle">我的投稿作品</h5>
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
					      		尚未上傳過作品。
					      	</c:otherwise>
								</c:choose>
							</div>
							<div class="modal-footer"></div>
						</div>
					</div>
				</div>
<!-- 				Modal end -->
				</div>
				

			<div class="row text-center">
				<input type="button" class="btn btn-sm btn-outline-secondary"
					value="上傳作品"
					<%=eventVO.getEvent_stat()!=1||checkMemPic?"disabled":"" %>
					onclick="location.href='<%=request.getContextPath() %>/frontend/event_p/TestInsert.jsp;'">
				</p>
			</div>
			</div>
		</section>
		


<section id="blog-masonry">
    <div class="container">
    
    <div class="row queryRow">
	<div class=" col-sm-6 " class="queryResult" id="showSearchNum">
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
				</optgroup>
			</select> <input type="hidden" name="action" value="selectOrderBy">
			<input type="submit" value="搜尋">
		</form>
	</span>
</div>
        <div id="grid" class="row">

<c:forEach var="event_pVO" items="${event_pVOs}" begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1 %>">
            <!-- post-block -->
            <div class="col-sm-4">
                <div class="post-block">
<!--                     <a href="single-post.html"> -->
                    	<img src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${event_pVO.event_p_no}" width="300px" height="300px" fill="#55595c">
<!--                     </a> -->
                    <div class="small-post-text">
         				<div class="row">
<!-- 							<div class="col-lg-6 "id="report-button"> -->
								<form action="Event_P_RepServlet" method="post">
									<input type="hidden" name="event_no"
										value="<%=(String)sess.getAttribute("event_no")%>">
									<input type="hidden" name="mem_id"value="${sessionScope.memVO.getMem_id()}">
									<input type="hidden" name="event_p_no" value="${event_pVO.event_p_no}"> 
									<input type="hidden" name="action" value="report"> 
										<input	type="submit" class="btn btn-default"  value="檢舉"	<%=eventVO.getEvent_stat()!=2||event_p_repVO!=null?"disabled":""%>> <!--  -->
								</form>
<!-- 							</div> -->
						</div>
						<div class="row">
							
							<ul class="list-group">
								<lem class="list-group-item" <%=eventVO.getEvent_stat()!=3?"style=\""+"display:none;"+"\"":""%>>排名:${event_pVO.vote_rank }</li>
								<lem class="list-group-item" id="showVoteNum">票數:${event_pVO.event_vote_num}</li>
							</ul>
						</div>
										<!-- div class="btn-group" change to p tag -->
						
						<div class="row " >
							<div class="col-md-2" id="neo-vote-button" >
								<form action="Event_pServlet" method="post">
									<input type="hidden" name="event_no"
										value="${eventVO.event_no}"> 
									<input type="hidden"
										name="mem_id" value="${sessionScope.memVO.getMem_id() }">
									<%-- event_no 和mem_id 用於確認投了幾次  --%>
									<input type="hidden" name="event_p_no"
										value="${event_pVO.event_p_no}" id="event_p_no"> 
									<input
										type="hidden" name="action" value="vote">
									<!-- 把錯誤訊息用alert方式顯示 -->
									<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
									<input type="hidden" name="whichPage" value="<%=whichPage%>">
									
								</form>
								<button class="vote btn btn-primary btn-pill" <%=eventVO.getEvent_stat()!=2?"style=\""+"display:none;"+"\"":""%>>投票</button>
							</div>
						<small class="text-muted col-md-5 col-md-offset-1" id="error">
							<c:if test="${not empty errMsgs&&event_p_no==event_pVO.event_p_no}">
							 
<!-- 									<font style="color: red">請修正以下錯誤:</font> -->
										<tr>
										<c:forEach var="message" items="${errMsgs}">
											<td style="color:red;">${message}</td><br>
										</c:forEach>
										<tr>
							
							</c:if>
						</small>
							<div class="col-md-4 " id="neo-delvote-button"><!-- col-lg-offset-2 -->
								<form action="Event_pServlet" method="post">
									<input type="hidden" name="event_no" value="${eventVO.event_no}">
									<input type="hidden" name="mem_id"
										value="${sessionScope.memVO.getMem_id() }"> 
										<input type="hidden" name="event_p_no" value="${event_pVO.event_p_no}"> 
										<input	type="hidden" name="action" value="deleteVote">
<%-- 										<input	type="submit" class="btn btn-default " value="取消投票" <%=eventVO.getEvent_stat()!=2?"disabled":""%>> --%>
									<!--btn btn-sm btn-outline-secondary change to  -->
										<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
										<input type="hidden" name="whichPage" value="<%=whichPage%>">
								</form>
								<button class="deleteVoteBtn btn btn-default btn-pill" <%=eventVO.getEvent_stat()!=2?"style=\""+"display:none;"+"\"":""%>>取消投票</button>
								
							</div>

						</div>

                        <h5><a href="single-post.html">${event_pVO.event_p_name}</a></h5>
                        <p class="small-post-meta">
                        <fmt:parseDate value="${event_pVO.event_p_date}" var="dateObject" pattern="yyyy-MM-dd"/>
						<fmt:formatDate value="${dateObject}"/>                        
                        </p>
                        <p class="small-post-footer">
                        </p>
                    </div><!-- / small-post-text -->
                </div><!-- / post-block -->
            </div><!-- / col-md-4 -->
            <!-- / post-block -->
</c:forEach>

        </div><!-- / row -->
	<div class="queryResult">
	<%@include file="/frontend/event_p/page2.file"%>
    </div>
    </div><!-- / container -->
</section>

<a href="<%=request.getContextPath() %>/frontend/event_p/event_homePage.jsp">回到瀏覽主題競賽</a>
<%@include file="/frontend/bar/frontBarFooter.jsp" %>
</body>

<%-- <script src="<%=request.getContextPath()%>/frontend/template/jquery/jquery-3.5.1.js"></script> --%>
<%-- <script src="<%=request.getContextPath()%>/frontend/template/js/bootstrap.min.js"></script> --%>
    <script src="<%=request.getContextPath() %>/frontend/template/js/jquery.min.js" ></script>

<!-- mark by YCL 重複引入導致modal跑不出來     -->
<%-- 	<script src="<%=request.getContextPath() %>/frontend/template/js/bootstrap.min.js" ></script> --%>
	<script src="<%=request.getContextPath()%>/frontend/template/neoTools/sweetAlert/sweetalert.min.js"></script>
<script>
	// 		var checkreport=document.getElementsByClassName("checkreport");
	// 		for(let i=0;i<checkreport.length;i++){
	// 			if(checkreport[i].value==2){
	// 				checkreport[i].style.visibility="hidden";
	// 			}
	// 		}
	var countDownTimer = document.getElementById("countDownTimer");
// 	console.log(countDownTimer.textContent);
	//extract  string tomatch js date format input
// 	var timer = countDownTimer.textContent.split("-");
// 	var y = timer[0];
// 	var m = timer[1];
// 	var d = timer[2];
// 	console.log(d);
// 	var timer = setInterval(countDown, 1000);
// 	function countDown() {
// 		var countDownTime = new Date(y, m - 1, d).getTime();//js會月份+1
// 		var now = new Date().getTime();
// 		var remainTime = countDownTime - now;//get long remain time

// 		//start convert long time to day,hours,min,sec,
// 		var day = Math.floor(remainTime / (1000 * 60 * 60 * 24));
// 		var hours = Math.floor((remainTime % (1000 * 60 * 60 * 24))
// 				/ (1000 * 60 * 60));
// 		var min = Math.floor((remainTime % (1000 * 60 * 60)) / (1000 * 60));
// 		var sec = Math.floor((remainTime % (1000 * 60)) / (1000));
// 		console.log(day);
// 		//create one div under the countDownTimer div
// 		var display = document.getElementById("displayReamainTime");
// 		display.innerHTML = "剩下" + day + "天" + hours + "小時" + min + "分" + sec
// 				+ "秒";
// 		if (remainTime < 0) {
// 			display.innerHTML = "已結束";
// 		}
// 	}
	$(document).ready(function(){
		$(".vote").click(function(){
			console.log($(this).prev().find("input"));
			var arr=$(this).prev().find("input");
			console.log($(this).parent().next());
			//第一個this是投票按鈕的this，第二個this是input的this
			$(this).prev().find("input").each(function(){
				console.log($(this).val());//印出所有的val
			});
			//用作品編號設定id:this代表 按下的vote button prev是上一個兄弟元素，find指找呼叫find的子元素
			var event_p_no=$(this).prev().find("input[name='event_p_no']").val();//取得當前元素的作品編號
			$(this).parent().next().append("<td id=\""+event_p_no+"\"></td>");//創建id為作品編號的元素，作為success要插入的值
			var elementShowVoteNum=$(this).parent().parent().prev().children().children("#showVoteNum");//將元素變數李
			$.ajax({
				url:"<%=request.getContextPath()%>/frontend/event_p/Event_pServlet",
				type:"post",
				data:{
					action:"vote_from_ajax",
					mem_id:"${sessionScope.memVO.getMem_id()}",
					event_no:"${eventVO.event_no}",
					event_p_no:event_p_no
				},
				success: function(e){
					
					var element="#"+event_p_no;
					//e:1->達到投票數上限(最多投3次) ;2->已投過此作品
					var infoArr=e.split(",");
					var infoStr=["voteSuccess","達到投票數上限(最多投3次)","已投過此作品"];
					for(let i=0;i<infoArr.length;i++){
						if(infoArr[i]==="0"){
							swal("投票成功","", "success");
							var voteNum=elementShowVoteNum.text().split(":")[1];//get id=showVoteNum voteNum
							$(element).text("");
							elementShowVoteNum.text("票數:"+(parseInt(voteNum)+1));
						}else{
							$(element).text(infoStr[infoArr[i]]);//錯誤訊息	
						}
					}
				}
			});
		});
		$(".deleteVoteBtn").click(function(){
			
			var event_p_no=$(this).prev().find("input[name='event_p_no']").val();//找對應event_p_no
			$(this).parent().prev().append("<td id=\""+event_p_no+"\"></td>");//放錯誤訊息
			var voteNumElement=$(this).parent().parent().prev().children().find("li[id='showVoteNum']");//取得目前投票數
			$.ajax({
				
				url:"<%=request.getContextPath()%>/frontend/event_p/Event_pServlet",
				type:"post",
				data:{
					action:"delete_vote_ByAjax",
					mem_id:"${sessionScope.memVO.getMem_id()}",
					event_p_no:event_p_no					
				},
				success:function(e){
					if(e==="1"){
						console.log(e);
						$("#"+event_p_no).text("尚未投過此作品");
					}else{
						var voteNum=voteNumElement.text().split(":")[1];
						voteNumElement.text("票數:"+(parseInt(voteNum)-1));
						$("#"+event_p_no).text("");
						swal("取消投票成功","", "success");
					}
				}
				
			});
		});
	});

</script>
</html>