<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html>
	
	<head>
	
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
	
		<title>我是標題</title>
	</head>

	<body id="page-top">
	
		<div id="wrapper">
	
			<%@include file="/backend/bar/backBarSide.jsp"%>
	
			<div id="content-wrapper" class="d-flex flex-column">		
	
				<div id="content">
	
					<!-- Topbar -->
					<%@include file="/backend/bar/backBarTop.jsp"%>
	
					<div class="container-fluid">				
						<!--============================自定義內容start ================================================== -->
						
						<%-- 頁面標題 --%>
						<h1 class="h3 mb-2 text-gray-800">商品管理</h1>
						
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
	
						<%-- 資料內容 --%>
	
	

					</div> <!--END OF container-fluid-->
					
					
					
					<%@include file="/backend/prod/master/listOneProd_in.jsp"%>
					
					
				    </div> <!--END OF content-->
				
<!--====================================== 自定義內容end ================================================== -->		
				<%@include file="/backend/bar/footer.jsp"%>
				
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
	
		<script>
		<!--JavaScript內容-->
		</script>
						
	</body>
	
</html>