<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.material.model.*"%>


<%
Material_Type_VO material_Type_VO = (Material_Type_VO) request.getAttribute("material_Type_VO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>

<!DOCTYPE html>
<html>
	
	<head>
	
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
	
		<title>素材修改</title>
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
						<h1 class="h3 mb-2 text-gray-800">素材類別修改</h1>
						
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/material/mat.do" name="form1" enctype="multipart/form-data" onchange="loadImageFile(event)">
	
<jsp:useBean id="matypeSvc" scope="page" class="com.material.model.Material_Type_Service" />




<div class="input-group mb-3" style="max-width:1200px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">素材類別編號:<font color=red><b>*</b></font><%=material_Type_VO.getMaTyNo()%>
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    &nbsp&nbsp&nbsp&nbsp</span>
    <input type="hidden" class="form-control"  name="ma_ty_no" value="<%=material_Type_VO.getMaTyNo()%>"  >
  </div>
</div>


<div class="input-group mb-3" style="max-width:1200px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">素材類別名稱:<font color=red><b>*</b></font><%=material_Type_VO.getMaTyNam()%>
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    </span>
    <input type="hidden" class="form-control"  name="ma_ty_no" value="<%=material_Type_VO.getMaTyNam()%>"  >
  </div>
</div>

<!-- <div class="input-group mb-3" style="max-width:300px"> -->
<!--   <div class="input-group-prepend"> -->
<!--     <span class="input-group-text" id="inputGroup-sizing-default">素材類別編號:</span> -->
<!--   </div> -->
<%--   <input type="text" class="form-control"  name="ma_ty_no" value="<%=material_Type_VO.getMaTyNo()%>"  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default"> --%>
<!-- </div> -->

<!-- <div class="input-group mb-3" style="max-width:300px"> -->
<!--   <div class="input-group-prepend"> -->
<!--     <span class="input-group-text" id="inputGroup-sizing-default">素材類別名稱:</span> -->
<!--   </div> -->
<%--   <input type="text" class="form-control"  name="ma_ty_nam" value="<%=material_Type_VO.getMaTyNam()%>"  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default"> --%>
<!-- </div> -->

<div class="input-group mb-3" style="max-width:300px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">更新類別名稱:</span>
  </div>
  <input type="text" class="form-control"  name="ma_ty_nam_edit" value=""  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
</div>		
			
<label>&nbsp&nbsp</label><br>
<input type="hidden" name="action" value="updateType">
<input type="hidden" name="ma_ty_no" value="<%=material_Type_VO.getMaTyNo()%>">
<button class="btn btn-primary" type="submit">送出修改</button></FORM>
	
						<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					
				</div> <!--END OF content-->
				
				<%@include file="/backend/bar/footer.jsp"%>
				
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
	

						
	</body>
	
</html>