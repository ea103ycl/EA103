<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.event_tag.model.*" %>
<%@ page import="java.util.*" %>
<% 
	Event_TagService svc=new Event_TagService();
	List<Event_TagVO>event_tagVOs=svc.findAllTag();
	request.setAttribute("event_tagVOs", event_tagVOs);
%>

<c:if test="${not empty errMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css">
<title>event_tag_select_page</title>
</head>
<body>
	<ul>
		<li><a href="listAllTag.jsp">找全部</a></li>
		<li>
			<form action="Event_TagServlet" method="post">
				<select name="event_tag_no">
					<c:forEach var="event_tagVO" items="${event_tagVOs}">
						<option value="${event_tagVO.event_tag_no }">${event_tagVO.event_tag_no }</option>
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="findByPrimaryKey">
				<input type="submit" value="搜尋">
			</form>
		</li>
		<li><a href="TestInsert.jsp">新增</li>
	</ul>
	
</body>
</html>