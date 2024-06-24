<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.event.model.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	EventVO eventVO = (EventVO) request.getAttribute("eventVO");
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
<title>Insert title here</title>
</head>
<body id="page-top">

	<div id="wrapper">

		<%@include file="/backend/bar/backBarSide.jsp"%>

		<div id="content-wrapper" class="d-flex flex-column">

			<div id="content">

				<!-- Topbar -->
				<%@include file="/backend/bar/backBarTop.jsp"%>

				<div class="container-fluid">
					<!--=====自定義內容start ================================================== -->

					<%-- 頁面標題 --%>
					<h1 class="h3 mb-2 text-gray-800">搜尋結果</h1>

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
					<a href="<%=request.getContextPath()%>/backend/event/TestListAll.jsp">回主頁</a><br>
					<table class="table container table-bordered table-hover ">
						<thead class="thead-light">
							<tr>
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




						<tr>
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
							<td><c:if test="${eventVO.event_stat==3 }">
						活動結束
					</c:if> <c:if test="${eventVO.event_stat==2 }">
						開始投票
					</c:if> <c:if test="${eventVO.event_stat==1 }">
						開始徵稿
					</c:if> <c:if test="${eventVO.event_stat==0 }">
						尚未開始
					</c:if></td>

							<td>
								<form action="eventServlet" method="post">
									<input type="hidden" name="event_no" value="${eventVO.event_no}"> 
									<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
									
									<input type="hidden"name="action" value="updateFromListAll"> 
									<input type="submit" value="修改">
								</form>
							</td>
						</tr>

					</table>

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