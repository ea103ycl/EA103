<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%@ page import="com.lv.model.*"%>
<%
  LvVO lvVO = (LvVO) request.getAttribute("lvVO");
  String isUploadFile = (String)request.getAttribute("isUploadFile");
%>


<!DOCTYPE html>
<html>
	
	<head>
	
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">

		<!-- favicon -->
		<link rel="icon" href="<%=request.getContextPath()%>/backend/template/img/favicon.png">
			
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
						<!--=====自定義內容start ================================================== -->
						
						<%-- 頁面標題 --%>
						<h1 class="h3 mb-2 text-gray-800">新增里程碑</h1>
						
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
						<form method="post" class="form-group"
							action="<%=request.getContextPath()%>/lv/lv.do"
							enctype="multipart/form-data">
	
							<div class="form-group">
								<label for="lv_nm">里程碑等級名稱</label>
								<input type="text"
									class="form-control" id="lv_nm" name="lv_nm" placeholder="請輸入"
									value="<%= (lvVO==null) ? "" : lvVO.getLv_nm()%>">
	
							</div>
	
							<div class="form-group">
								<label for="f_cnt">所需粉絲數</label> 
								<input type="number"
									class="form-control" id="f_cnt" name="f_cnt" placeholder="請輸入"
									value="<%= (lvVO==null) ? "" : lvVO.getF_cnt()%>">
							</div>
	
							<div class="form-group">
								<label>里程碑圖示</label>
								<div class="input-group">
									<input type="file" id="imgPath" name="imgPath"
										onchange="readURL(this);">
								</div>
								<img class="img-fluid w-25 h-25" id="imgUpload">							
							</div>
	
							<button
								class="btn btn-primary btn-icon-split mt-1 ml-3 float-right"
								type="submit" name="action" value="insert">
								<span class="text">送出</span>
							</button>
							<button
								class="btn btn-secondary btn-icon-split mt-1 ml-3 float-right"
								type="submit" name="action" value="cancel">
								<span class="text">取消</span>
							</button>
	
						</form>
	
						<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					
				</div> <!--END OF content-->
				
				<%@include file="/backend/bar/footer.jsp"%>
				
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
	
		<script>
			<!--JavaScript內容-->
			function readURL(input) {
				if (input.files && input.files[0]) {
					var reader = new FileReader();
					reader.onload = function(e) {
						$('#imgUpload').attr('src', e.target.result);
						//將圖片存到cache，以便新增出錯時再讀出
						localStorage.setItem('insertIcon', JSON.stringify(e.target.result)); // setXXX
					};					
						reader.readAsDataURL(input.files[0]);
				}
			}
		</script>
						
	</body>
	
</html>