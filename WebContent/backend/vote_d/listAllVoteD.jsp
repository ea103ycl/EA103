<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.vote_d.model.*" %>
<%@ page import="java.util.*" %>
<% 
	Vote_DService vote_dSvc=new Vote_DService();
	List<Vote_DVO> vote_dVOs=vote_dSvc.findAllVote_d();
	pageContext.setAttribute("vote_dVOs", vote_dVOs);
%>
<%=vote_dVOs.size() %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css">
<title>投票明細</title>
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
					<th scope="col">會員編號</th>
					<th scope="col">投稿作品編號</th>

				</tr>
		</thead>
		<div class="container"><%@ include file="page1.file" %></div>
		<c:forEach var="vote_dVO" items="${vote_dVOs}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1 %>">
			<tr>
				<td>${vote_dVO.mem_id}</td>
				<td>${vote_dVO.event_p_no}</td>
				

			</tr>
		</c:forEach>	
	</table>
	<div class="container"><%@ include file="page2.file" %></div>
</body>
</html>