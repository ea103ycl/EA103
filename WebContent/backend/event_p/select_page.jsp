<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.event_p.model.*"%>
<%@ page import="com.event.model.*"%>
<%@ page import="java.util.*"%>
<%
	Event_PService svc = new Event_PService();
	List<Event_PVO> event_pVOs = svc.finAllPic();
	request.setAttribute("event_pVOs", event_pVOs);

	//event
	EventService eventSvc = new EventService();
	List<EventVO> eventVOs = eventSvc.findAllEvent();
	pageContext.setAttribute("eventVOs", eventVOs);
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
					<h1 class="h3 mb-2 text-gray-800">參賽作品管理</h1>

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

					<h4>投稿作品查詢:</h4>
					<ul>
						<li><a href="listAllPic.jsp">找全部投稿作品</a></li>
						<li>

							<form action="Event_pServlet" method="post">
								選擇要搜尋的競賽名稱: <select name="event_no">
									<c:forEach var="eventVO" items="${eventVOs}">
										<option value="${eventVO.event_no}">${eventVO.event_name}
									</c:forEach>
								</select> <input type="hidden" name="action" value="findAllByEventNo">
								<input type="submit" value="送出">
							</form>

						</li>

					</ul>
					<h4>作品管理:</h4>
					<ul>
						<li><a
							href="<%=request.getContextPath()%>/backend/event_p_rep/listAllRep.jsp">檢舉列表</a></li>
						<!-- 		<li><a href="TestInsert.jsp">新增(之後刪除)</li> -->
					</ul>
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