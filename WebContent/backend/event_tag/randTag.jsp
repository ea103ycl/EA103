<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.event_tag.model.*"%>
<%@ page import="java.util.*"%>
<%
	Event_TagService tagSvc = new Event_TagService();
	List<Event_TagVO> event_tagVOs = tagSvc.randTag();
	pageContext.setAttribute("event_tagVOs", event_tagVOs);
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
<title>Rand Tag</title>
</head>
<body>
	<form action="Event_TagServlet" method="post">

		<div class="form-group row">
			<label for="inputEmail3" class="col-sm-2 col-form-label">Hashtag名稱</label>
			<c:forEach var="event_tagVO" items="${event_tagVOs}">
				<div class="col-sm-3">
					<input type="text" class="form-control " name="event_tag_name"	value="${event_tagVO.event_tag_name}">
				</div>
			</c:forEach>
		</div>


		<input type="hidden" name="action" value="randTag"> <input
			type="submit" value="重新抽選">
	</form>
</body>
</html>