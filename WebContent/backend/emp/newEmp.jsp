<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>

<!DOCTYPE html>
<html>
	
	<head>
	
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
	
		<title>新增員工</title>
		<style>
			#preview img {
				width: 300px;
				height: 300px;
			}
		</style>
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
						<h1 class="h3 mb-2 text-gray-800">重設密碼</h1>
						
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
	

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/emp/emp.do" name="form1" enctype="multipart/form-data">


<div> &nbsp</div>

<div class="input-group mb-3" style="max-width:300px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">帳號:</span>
  </div>
  <input type="text" class="form-control"  name="emp_no" value="<%=request.getParameter("emp_no1")%>">
</div>

<div class="input-group mb-3" style="max-width:300px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">密碼:</span>
  </div>
  <input type="text" class="form-control"  name="old_pwd" value="<%=request.getParameter("emp_pwd1")%>">
</div>

<div class="input-group mb-3" style="max-width:300px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">請輸入新密碼:</span>
  </div>
  <input type="password" class="form-control"  name="emp_pwd" value="">
</div>

<div class="input-group mb-3" style="max-width:300px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">確認新密碼:</span>
  </div>
  <input type="password" class="form-control"  name="emp_pwdc" value="">
</div>

<div style = "margin-left:108px">
<input type="hidden" name="action" value="newEmp">
<button class="btn btn-primary" type="submit">送出修改</button>
</div>
</FORM>
	
  <a class="scroll-to-top rounded" href="#page-top">
    <em class="fas fa-angle-up"></em>
  </a>
					<%-- 資料內容 --%>
	
	
						<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					
				</div> <!--END OF content-->
				
				<%@include file="/backend/bar/footer.jsp"%>
				
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
	
  

						
	</body>
	
</html>