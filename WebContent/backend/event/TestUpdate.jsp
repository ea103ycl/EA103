<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.event.model.*"%>
<%@ page import="java.util.*"%>
<%@ page buffer="32kb"%>
<%
	EventVO eventVO = (EventVO) request.getAttribute("eventVO");
%>

<%-- <c:if test="${not empty errMsgs}"> --%>
<!-- 	<font style="color: red">請修正以下錯誤:</font> -->
<!-- 	<ul> -->
<%-- 		<c:forEach var="message" items="${errMsgs}"> --%>
<%-- 			<li style="color: red">${message}</li> --%>
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
	href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css">
<title>更新主題競賽</title>
<style>
.xdsoft_datetimepicker {
	margin-left: 20%;
	margin-top: -2%;
}
</style>
</head>
<body>
	<div id="wrapper">

		<%@include file="/backend/bar/backBarSide.jsp"%>

		<div id="content-wrapper" class="d-flex flex-column">

			<div id="content">

				<!-- Topbar -->
				<%@include file="/backend/bar/backBarTop.jsp"%>

				<div class="container-fluid">
					<!--=====自定義內容start ================================================== -->

					<%-- 頁面標題 --%>
					<h1 class="h3 mb-2 text-gray-800">主題活動</h1>

					<%-- 錯誤表列 --%>

					<%-- 資料內容 --%>
					<a href="<%=request.getContextPath()%>/backend/event/TestListAll.jsp">回主頁</a> <br>

					<div class="container">
						<form action="<%=request.getContextPath() %>/backend/event/eventServlet" method="post">
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label">活動編號</label>
								<div class="col-sm-3">

									<input type="text" class="form-control " name="event_no"
										value="<%=eventVO.getEvent_no()%>" readonly="readonly">
								</div>
							</div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label">活動名稱</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" name="event_name"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_name().toString()%> readonly="readonly">
								</div>
							</div>
							<div><hr></div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label">活動開始時間</label>
								<div class="col-sm-3">
									<input type="text" class="form-control f_date1"
										name="event_start"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_start().toString()%>>
								</div>
								
								<c:if test="${not empty errMsgs['event_start']}">
									<div style="color:red;">
									${errMsgs['event_start'] }
									</div>
								</c:if>								
							</div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label">活動結束時間</label>
								<div class="col-sm-3">
									<input type="text" class="form-control f_date2"
										name="event_end"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_end().toString()%>>
								</div>
								<c:if test="${not empty errMsgs['event_end']}">
									<div style="color:red;">
									${errMsgs['event_end'] }
									</div>
								</c:if>								
							</div>
							<div><hr></div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label">徵求作品開始時間</label>
								<div class="col-sm-3">
									<input type="text" class="form-control f_date3"
										name="event_ul_start"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_ul_start().toString()%>>
								</div>
								<c:if test="${not empty errMsgs['event_ul_start']}">
									<div style="color:red;">
									${errMsgs['event_ul_start'] }
									</div>
								</c:if>									
							</div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label">徵求作品結束時間</label>
								<div class="col-sm-3">
									<input type="text" class="form-control f_date4"
										name="event_ul_end"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_ul_end().toString()%>>
								</div>
								<c:if test="${not empty errMsgs['event_ul_end']}">
									<div style="color:red;">
									${errMsgs['event_ul_end'] }
									</div>
								</c:if>									
							</div>
							<div><hr></div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label">投票開始時間</label>
								<div class="col-sm-3">
									<input type="text" class="form-control f_date5"
										name="event_vote_start"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_vote_start().toString()%>>
								</div>
								<c:if test="${not empty errMsgs['event_vote_start']}">
									<div style="color:red;">
									${errMsgs['event_vote_start'] }
									</div>
								</c:if>								
							</div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label">投票結束時間</label>
								<div class="col-sm-3">
									<input type="text" class="form-control f_date6"
										name="event_vote_end"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_vote_end().toString()%>>
								</div>
								<c:if test="${not empty errMsgs['event_vote_end']}">
									<div style="color:red;">
									${errMsgs['event_vote_end'] }
									</div>
								</c:if>									
							</div>
							<div><hr></div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label">活動狀態</label>
								<div class="col-sm-3">
									<select class="form-control" id="exampleFormControlSelect1"
										name="event_stat">
										<option value="0" ${eventVO.event_stat==0?"selected":"" }>活動尚未開始</option>
										<option value="1" ${eventVO.event_stat==1?"selected":"" }>開始徵稿</option>
										<option value="2" ${eventVO.event_stat==2?"selected":"" }>開始投票</option>
										<option value="3" ${eventVO.event_stat==3?"selected":"" }>活動結束</option>
									</select>
									<%--                     <input type="text" class="form-control" name="event_stat" value=<%=(eventVO==null)?"":eventVO.getEvent_stat().toString() %> > --%>
								</div>
							</div>

							<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
							<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">
							<input type="hidden" name="action" value="update"> <input
								type="submit" value="送出">
						</form>
					</div>

					<!--===== 自定義內容end ================================================== -->
				</div>
				<!--END OF container-fluid-->

			</div>
			<!--END OF content-->

			<%@include file="/backend/bar/footer.jsp"%>

		</div>
		<!--END OF content-wrapper -->
	</div>
	<!--END OF wrapper -->

</body>

<%
	java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.datetimepicker.css" />
<script
	src="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.datetimepicker.full.js"></script>

<script>
$.datetimepicker.setLocale('zh');
$('.f_date1').datetimepicker({
   theme: '',              //theme: 'dark',
   timepicker:false,       //timepicker:true,
   step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
   format:'Y-m-d',         //format:'Y-m-d H:i:s',
   value: '<%=(eventVO == null) ? "" : eventVO.getEvent_start().toString()%>',     
   //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
   //startDate:	            '2017/07/10',  // 起始日
   //minDate:			    	// 去除今日(不含)之前
   //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
});
$('.f_date2').datetimepicker({
    theme: '',              //theme: 'dark',
    timepicker:false,       //timepicker:true,
    step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
    format:'Y-m-d',         //format:'Y-m-d H:i:s',
	   value: '<%=(eventVO == null) ? "" : eventVO.getEvent_end().toString()%>',     
   //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
// startDate:	     	//'2017/07/10',  // 起始日開始活動之後
// minDate:			    	// 去除今日(不含)之前
   //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
 });
$('.f_date3').datetimepicker({
    theme: '',              //theme: 'dark',
    timepicker:false,       //timepicker:true,
    step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
    format:'Y-m-d',         //format:'Y-m-d H:i:s',
	   value: '<%=(eventVO == null) ? "" : eventVO.getEvent_ul_start().toString()%>',     
   //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
   //startDate:	            '2017/07/10',  // 起始日
   //minDate:			    	// 去除今日(不含)之前 
    //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
 });
$('.f_date4').datetimepicker({
    theme: '',              //theme: 'dark',
    timepicker:false,       //timepicker:true,
    step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
    format:'Y-m-d',         //format:'Y-m-d H:i:s',
	   value: '<%=(eventVO == null) ? "" : eventVO.getEvent_ul_end().toString()%>',     
    //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
    //startDate:	            '2017/07/10',  // 起始日
    //minDate:			    	// 去除今日(不含)之前
    //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
 });
$('.f_date5').datetimepicker({
    theme: '',              //theme: 'dark',
    timepicker:false,       //timepicker:true,
    step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
    format:'Y-m-d',         //format:'Y-m-d H:i:s',
	   value: '<%=(eventVO == null) ? "" : eventVO.getEvent_vote_start().toString()%>',     
    //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
    //startDate:	            '2017/07/10',  // 起始日
    //minDate:			    	// 去除今日(不含)之前
    //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
 });
$('.f_date6').datetimepicker({
     theme: '',              //theme: 'dark',
     timepicker:false,       //timepicker:true,
     step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
     format:'Y-m-d',         //format:'Y-m-d H:i:s',
	 value: '<%=(eventVO == null) ? "" : eventVO.getEvent_vote_end().toString()%>',

					//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
					//startDate:	            '2017/07/10',  // 起始日
					// minDate:			    	// 去除今日(不含)之前
					//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
					});
</script>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
<!--     <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script> -->
<!--     <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script> -->
</html>