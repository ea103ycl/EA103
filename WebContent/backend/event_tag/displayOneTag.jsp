<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ page import="com.event_tag.model.*" %>
<%@ page import="java.util.*" %>
<%
	
	Event_TagVO event_tagVO=(Event_TagVO)request.getAttribute("event_tagVO");
	pageContext.setAttribute("event_tagVO",event_tagVO);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css">
<title>list All Tag</title>
    <style type="text/css">
        *{
        	text-align:center;
        }
        table.table {
            margin-top: 25px;
        }

    </style>
</head>
<body>
	<table class="table container tavle-bordered table-hover">
		<thead class="thead-light">
				<tr>
					<th scope="col">HashTag編號</th>
					<th scope="col">會員編號</th>
					<th scope="col">活動編號</th>
					<th scope="col">投稿HashTag時間</th>
					<th scope="col">投稿HashTag名稱</th>
				</tr>
		</thead>
			<tr>
				<td>${event_tagVO.event_tag_no}</td>
				<td>${event_tagVO.mem_id}</td>
				<td>${event_tagVO.event_no==null?"未被選中":event_tagVO.event_no}</td>
				<td>
					<fmt:parseDate value="${event_tagVO.event_tag_time}" var="dateObj" pattern="yyyy-MM-dd" />
					<fmt:formatDate value="${dateObj }" pattern="yyyy-MM-dd"/>					
				</td>
				
				<td>${event_tagVO.event_tag_name}</td>

			</tr>
	</table>
</body>
</html>