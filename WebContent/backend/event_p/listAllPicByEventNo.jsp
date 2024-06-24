<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.event_p.model.*"%>
<%@page import="java.util.*"%>
<%
	Event_PDAO dao = new Event_PDAO();
	HttpSession sess = request.getSession();
	List<Event_PVO> event_pVOs = null;
	if (sess.getAttribute("event_pVOs") != null) {
		event_pVOs = (List<Event_PVO>) sess.getAttribute("event_pVOs");//從選擇活動來的
	} else {
		event_pVOs = dao.findAllPic();
	}

	pageContext.setAttribute("event_pVOs", event_pVOs);
%>
<%-- <c:if test="${not empty errMsgs}"> --%>
<!-- 	<font style="color:red">請修正以下錯誤:</font> -->
<!-- 	<ul> -->
<%-- 	    <c:forEach var="message" items="${errMsgs}"> --%>
<%-- 			<li style="color:red">${message}</li> --%>
<%-- 		</c:forEach> --%>
<!-- 	</ul> -->
<%-- </c:if> --%>
<!-- 

可能可以刪了


 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css">
<title>listAllPic By Event_no</title>
<style type="text/css">
* {
	text-align: center;
}

table.table {
	margin-top: 25px;
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
					<h1 class="h3 mb-2 text-gray-800">投稿作品總覽</h1>

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

					<a href="<%=request.getContextPath()%>/backend/event_p/listAllPic.jsp">回首頁</a><br>
					<table class="table container tavle-bordered table-hover">
						<thead class="thead-light">
							<tr>
								<th scope="col">投稿作品編號</th>
								<th scope="col">會員編號</th>
								<th scope="col">活動編號</th>
								<th scope="col">投稿作品名稱</th>
								<th scope="col">投稿作品時間</th>
								<th scope="col">投稿作品投票數</th>
								<th scope="col">投稿作品排名</th>
								<th scope="col">投稿作品狀態</th>
								<th scope="col">圖片</th>
								<th scope="col">修改</th>
							</tr>
						</thead>
						<div class="container"><%@ include file="page1.file"%></div>
						<c:forEach var="event_pVO" items="${event_pVOs}"
							begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1 %>">
							<tr>
								<td>${event_pVO.event_p_no}</td>
								<td>${event_pVO.mem_id}</td>
								<td>${event_pVO.event_no}</td>
								<td>${event_pVO.event_p_name}</td>
								<td><fmt:parseDate value="${event_pVO.event_p_date}"
										var="dateObject" pattern="yyyy-MM-dd" /> <fmt:formatDate
										value="${dateObject}" pattern="yyyy-MM-dd" /> <%-- 				${event_pVO.event_p_date} --%>
								</td>
								<td>${event_pVO.event_vote_num}</td>
								<td>${event_pVO.vote_rank}</td>
								<td>
									<%
										String[] statStr = { "未被檢舉", "待審核", "通過", "不通過" };
											pageContext.setAttribute("statStr", statStr);
									%> ${statStr[event_pVO.event_p_stat-1]} ${event_pVO.event_p_stat}
								</td>
								<td><img
									src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${event_pVO.event_p_no}"
									width="300" height="300"></td>
								<td>
									<form action="<%=request.getContextPath() %>/backend/event_p/Event_pServlet" method="post">
										<input type="hidden" name="event_p_no"
											value="${event_pVO.event_p_no}"> <input type="hidden"
											name="action" value="updateFromListAll"> <input
											type="submit" value="修改">
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

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="<%=request.getContextPath()%>/backend/template/jquery/jquery-3.5.1.js"></script>
<!-- 	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" -->
<!-- 		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" -->
<!-- 		crossorigin="anonymous"></script> -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
		crossorigin="anonymous"></script>

</body>
</html>