<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lv.model.*"%>

<%
    LvService lvSvc = new LvService();
    List<LvVO> list = lvSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>
<html>
	
	<head>
	
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
	
		<title>里程碑列表</title>
		
		<style>
 			.img-responsive{max-width: 50px;}
/*  			.img-responsive:hover{transform: scale(8);} /*放大*/ */
		</style>
		
		<!-- favicon -->
		<link rel="icon" href="<%=request.getContextPath()%>/backend/template/img/favicon.png">
	
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
						<h1 class="h3 mb-2 text-gray-800">里程碑等級</h1>
						
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
	
						<%-- 資料內容 --%>
						<div class="card shadow mb-4">
							<!-- table名稱 -->
							<div class="card-header py-3">
								<h6 class="m-0 font-weight-bold text-primary">查詢結果</h6>
							</div>
						
							<div class="card-body">
	
								<button class="btn btn-primary btn-icon-split mb-1 ml-3"
									onclick="location.href='<%=request.getContextPath()%>/backend/lv/addLv.jsp'">
									<span class="text">新增</span>
								</button>
	

								<div class="table table-hover">
	
									<table class="table table-bordered text-center"
										id="dataTableNoSearchChange" width="100%" cellspacing="0">
										<thead>
											<tr class="bg-gray-400">
<!-- 											    <th>里程碑流水編號</th> -->
												<th>里程碑名稱</th>
												<th>里程碑icon</th>
												<th>所需粉絲數</th>
												<th>動作</th>
											</tr>
										</thead>
	
										<tbody>
										
<%-- 										<jsp:useBean id="lvSvc" scope="page" class="com.lv.model.LvService" /> --%>
<%-- 											<c:forEach var="lvVO" items="${lvSvc.all}"> --%>	
											<%@ include file="/backend/pages/page1.file" %>
											<c:forEach var="lvVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
												<tr>
<%-- 												    <td class="align-middle">${lvVO.lv}</td> --%>
													<td class="align-middle">${lvVO.lv_nm}</td>
													<td class="align-middle">
														<img class="img-responsive" src="<%=request.getContextPath()%>/lv/lv.do?action=showPic&lv=${lvVO.lv}"></img>
													</td>
													<td class="align-middle">${lvVO.f_cnt}</td>
													<td class="align-middle">
														<button class="btn btn-primary btn-icon-split mt-1 ml-3 "
														        onclick="location.href='<%=request.getContextPath()%>/lv/lv.do?action=getOneForUpdate&lv=${lvVO.lv}'">
															<span class="text">修改</span>
														</button>
														
														<c:if test="${lvVO.lv!=1}">
															<button class="btn btn-primary btn-icon-split mt-1 ml-3 deleteLvBtn " value="${lvVO.lv}">
																<span class="text">刪除</span>
															</button>
														</c:if>
														
													</td>
												</tr>
											</c:forEach>
											
										</tbody>
									</table>	
									
									<div style="text-align:center;">
									<%@ include file="/backend/pages/page2.file" %>
									</div>
								</div>								
							</div>
						</div>
						<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					
				</div> <!--END OF content-->
				
				<%@include file="/backend/bar/footer.jsp"%>
				
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
	
		<!-- 	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
		<script src="<%=request.getContextPath()%>/backend/template/YCL/sweetalert.min.js"></script>
		
		<script>
			$('.deleteLvBtn').click(function() {
				let lvID = $(this).val();
				swal({
					  title: "刪除里程碑",
					  text: "確定要刪除里程碑嗎？",
					  icon: "warning",
					  buttons: ["取消", "確定"],
					  dangerMode: true,
					})
					.then((willDelete) => {
					  if (willDelete) {
						  location.href='<%=request.getContextPath()%>/lv/lv.do?action=delete&lv=' + lvID;
					  } else {
					   null;
					  }
					});
			});
		</script>
						
	</body>
	
</html>