<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.event_p.model.*" %>
<%
	Event_PVO event_pVO=(Event_PVO)request.getAttribute("event_pVO");
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css">
<title>displayOne</title>
</head>
<body>
<a href="select_page.jsp" >回首頁</a><br>
	display one
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
		
		
			<tr>
				<td>${event_pVO.event_p_no}</td>
				<td>${event_pVO.mem_id}</td>
				<td>${event_pVO.event_no}</td>
				<td>${event_pVO.event_p_name}</td>
				<td>${event_pVO.event_p_date}</td>
				<td>${event_pVO.event_vote_num}</td>
				<td>${event_pVO.vote_rank}</td>
				<td>${event_pVO.event_p_stat}</td>
				<td><img src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${event_pVO.event_p_no}"></td>
				<td>
					<form action="<%=request.getContextPath()%>/backend/event_p/Event_pServlet" method="post">
						<input type="hidden" name="event_p_no" value="${event_pVO.event_p_no}">
						<input type="hidden" name="action" value="updateFromListAll">
						<input type="submit" value="修改">
					</form>
				</td>
			</tr>
			
	</table>
</body>
</html>