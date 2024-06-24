<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.event_p_rep.model.*"%>
<%@ page import="com.event.model.*"%>
<%@ page import="java.util.*"%>
<%
	Event_P_RepService repSvc = new Event_P_RepService();
	HttpSession sess = request.getSession();
	List<Event_P_RepVO> event_p_repVOs = null;
	if (sess.getAttribute("event_p_repVOs") != null) {
		event_p_repVOs = (List<Event_P_RepVO>) sess.getAttribute("event_p_repVOs");
	} else {
		event_p_repVOs = repSvc.findAllRep();
	}
	String event_no = "";
	if (request.getAttribute("event_no") != null) {
		event_no = (String) request.getAttribute("event_no");//紀錄上次選擇

	}
	pageContext.setAttribute("event_p_repVOs", event_p_repVOs);
	pageContext.setAttribute("event_no", event_no);
	//event use in select tag
	EventService eventSvc = new EventService();
	List<EventVO> eventVOs = eventSvc.findAllEvent();
	pageContext.setAttribute("eventVOs", eventVOs);
	//rep_stat selected tag
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css">
<title>list All Rep</title>
<style type="text/css">
* {
	text-align: center;
}

table.table {
	margin-top: 30px;
}

.queryRow {
	margin-bottom: 1%;
}

#queryResult {
	margin-left: -10%;
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
					<h1 class="h3 mb-2 text-gray-800">檢舉作品頁面</h1>

					<%-- 錯誤表列 --%>
					<c:if test="${not empty errMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>

					<%-- 資料內容 --%>

					<h4>
						<a
							href="<%=request.getContextPath()%>/backend/event_p/listAllPic.jsp">回到投稿作品管理主頁</a>
					</h4>
					<form action="Event_P_RepServlet" method="post">
<!-- 						選擇要搜尋的競賽名稱:  -->
						<select name="rep_stat">
							<option value="1" ${currentChoice=="1"?"selected":"" }>待審核</option><!-- repVO.getRep_stat==1?"selected":"" -->
							<option value="2" ${currentChoice=="2"?"selected":"" }>檢舉通過</option>
							<option value="3" ${currentChoice=="3"?"selected":"" }>檢舉不通過</option>
							<option value="4" ${currentChoice=="4"?"selected":"" }>查全部 </option>
						</select>
						<input type="hidden" name="action" value="compositeQuery">
						<input type="submit" value="送出">
					</form>
<!-- 					已被檢舉的狀態分類搜尋 -->

					<table class="table container tavle-bordered table-hover">
						<thead class="thead-light">
							<tr>
								<th scope="col">投稿作品編號</th>
								<th scope="col">會員編號</th>
								<th scope="col">檢舉作品時間</th>
								<th scope="col">檢舉作品原因</th>
								<th scope="col">檢舉作品圖片</th>
								<th scope="col">檢舉作品狀態</th>
								<th scope="col">檢舉作品審核結果</th>

							</tr>
						</thead>

						<div class="container"><%@ include file="page1.file"%></div>
						<c:forEach var="event_p_repVO" items="${event_p_repVOs}"
							begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1 %>">
							<tr ${param.event_p_no==event_p_repVO.event_p_no?"bgcolor=#CCCCFF":""}>
								<td>${event_p_repVO.event_p_no}</td>
								<td>${event_p_repVO.mem_id}</td>
								<td><fmt:parseDate value="${event_p_repVO.rep_time}"
										var="dateObject" pattern="yyyy-MM-dd" /> <fmt:formatDate
										value="${dateObject }" pattern="yyyy-MM-dd" /></td>
								<td>${event_p_repVO.rep_reason}</td>
								<td>檢舉作品圖片 <img
									src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${event_p_repVO.event_p_no}"
									width="300px" height="300px">
								</td>
								<td>
									${event_p_repVO.rep_stat }
									<%
										String[] repStat = {"待審核", "通過", "不通過"};
											pageContext.setAttribute("repStat", repStat);
									%> ${repStat[event_p_repVO.rep_stat-1]}
								</td>

								<td>
									<form action="Event_P_RepServlet" method="post">
										<select name="rep_stat" id="statPick">
											<option value="1">待審</option>
											<option value="2">通過</option>
											<option value="3">不通過</option>
										</select> 
										<input type="hidden" name="event_p_no" value="${event_p_repVO.event_p_no}"> 
										<input	type="hidden" name="action" value="updateFromlistAllRep">
										<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
										<input type="hidden" name="whichPage" value="<%=whichPage%>">
										<input type="submit" value="送出">
									</form>
								</td>
							</tr>
						</c:forEach>
					</table>
					<div class="container"><%@ include file="page2.file"%></div>
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
<script
	src="<%=request.getContextPath()%>/backend/template/jquery/jquery-3.5.1.js"></script>
<%-- <script src="<%=request.getContextPath()%>/backend/template/js/bootstrap.min.js"></script> --%>
<script
	src="<%=request.getContextPath()%>/backend/template/js/bootstrap.bundle.min.js"></script>
<script>
	
</script>
</html>