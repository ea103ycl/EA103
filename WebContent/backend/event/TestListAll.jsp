<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.event.model.*"%>
<!-- 取得list -->
<%
	HttpSession sess=request.getSession();
	EventService eventSvc = new EventService();
	List<EventVO> eventVOs = eventSvc.findAllEvent();
	pageContext.setAttribute("eventVOs", eventVOs);
	EventVO eventVO = null;
	pageContext.setAttribute("eventVO", eventVO);

	List<String> statList = new ArrayList<String>();
	statList.add("尚未開始");
	statList.add("開始徵稿");
	statList.add("開始投票");
	statList.add("活動結束");
	pageContext.setAttribute("statList", statList);
%>
<% 
	String lastEndEvent=eventSvc.findLastEndEvent();
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
<title>list all event</title>
<style type="text/css">
#showData{
	text-align: center;
}

table.table {
	margin-top: 25px;
}
#searchDiv{

	margin-left:5%;
}
</style>
</head>
<body id="page-top">

	<div id="wrapper">

		<%@include file="/backend/bar/backBarSide.jsp"%>

		<div id="content-wrapper" class="d-flex flex-column">

			<div id="content">

				<!-- Topbar -->
				<%@include file="/backend/bar/backBarTop.jsp"%>

				<div class="container-fluid" >
					<!--=====自定義內容start ================================================== -->
					<%-- 頁面標題 --%>
					<h1 class="h3 mb-2 text-gray-800">創意主題管理</h1>
					<!-- 做出新增、修改、刪除、查詢連接 -->
					<div id="searchDiv">
					<h4>資料查詢:</h4>
					<ul>
						<li><a href="TestListAll.jsp">List All</a></li>
						<li><%=lastEndEvent%> </li>
						<% 
							application.setAttribute("lastEndEvent", lastEndEvent);
						%>
						
						<li>

							<form action="<%=request.getContextPath() %>/backend/event/EventServlet" method="post">
								選擇要搜尋的競賽名稱: <select name="event_no">
									<c:forEach var="eventVO" items="${eventVOs}">
										<option value="${eventVO.event_no}">${eventVO.event_name}
									</c:forEach>
								</select> <input type="hidden" name="action" value="findByPrimaryKey">
								<input type="submit" value="送出">
							</form>
						</li>
						<li>
							<form
								action="<%=request.getContextPath()%>/backend/event/eventServlet"
								method="post">
								選擇要搜尋的編號: <select name="event_no">
									<c:forEach var="eventVO" items="${eventVOs}">
										<option value="${eventVO.event_no}">${eventVO.event_no}
									</c:forEach>
								</select> <input type="hidden" name="action" value="findByPrimaryKey">
								<input type="submit" value="送出">
							</form>

						</li>
					</ul>
					<h4>活動管理:</h4>
					<ul>
						<li><a href="TestInsert.jsp">新增主題</a></li>

					</ul>
					</div>

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
					<%
						int num = 1;
					%>
<!-- 					<a href="event_select_page.jsp">回首頁</a><br> -->
					<table class="table container table-bordered table-hover " id="showData">
						<thead class="thead-light">
							<tr>
								<th scope="col">#</th>
								<th scope="col">活動編號</th>
								<th scope="col">活動名稱</th>
								<th scope="col">活動開始時間</th>
								<th scope="col">活動結束時間</th>
								<th scope="col">投稿作品開始時間</th>
								<th scope="col">投稿作品結束時間</th>
								<th scope="col">投票開始時間</th>
								<th scope="col">投票結束時間</th>
								<th scope="col">活動狀態</th>
								<th scope="col">修改</th>
							</tr>
						</thead>
						<div class="container"><%@ include file="page1.file"%></div>

						<c:forEach var="eventVO" items="${eventVOs}"
							begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1 %>">

							<tr ${param.event_no==eventVO.event_no?"bgcolor=#CCCCFF":""}>
								<td scope="row"><%=num++%></td>
								<td>${eventVO.event_no}</td>
								<td>${eventVO.event_name}</td>

								<td><fmt:parseDate value="${eventVO.event_start}"
										var="dateObj" pattern="yyyy-MM-dd" /> <fmt:formatDate
										value="${dateObj }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:parseDate value="${eventVO.event_end}"
										var="dateObj" pattern="yyyy-MM-dd" /> <fmt:formatDate
										value="${dateObj }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:parseDate value="${eventVO.event_ul_start}"
										var="dateObj" pattern="yyyy-MM-dd" /> <fmt:formatDate
										value="${dateObj }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:parseDate value="${eventVO.event_ul_end}"
										var="dateObj" pattern="yyyy-MM-dd" /> <fmt:formatDate
										value="${dateObj }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:parseDate value="${eventVO.event_vote_start}"
										var="dateObj" pattern="yyyy-MM-dd" /> <fmt:formatDate
										value="${dateObj }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:parseDate value="${eventVO.event_vote_end}"
										var="dateObj" pattern="yyyy-MM-dd" /> <fmt:formatDate
										value="${dateObj }" pattern="yyyy-MM-dd" /></td>
								<td><c:set var="stat" value="${eventVO.event_stat}"></c:set>
									<c:forEach var="statStr" items="${statList}" varStatus="status">
										<c:if test="${status.index== stat}">
										${statStr}
										</c:if>
									</c:forEach>
						</td>

								<td>
									<form action="eventServlet" method="post">
										<input type="hidden" name="event_no" value="${eventVO.event_no}"> 
										<input type="hidden" name="action" value="updateFromListAll"> 
										<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
										<input type="hidden" name="whichPage" value="<%=whichPage%>">
										<input type="submit" value="修改">
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

	<script>
	<!--JavaScript內容-->
		
	</script>

</body>
</html>