<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.material.model.*"%>

<%
  Material_Data_VO material_Data_VO = (Material_Data_VO) request.getAttribute("material_Data_VO");
  Material_Type_VO material_Type_VO = (Material_Type_VO) request.getAttribute("material_Type_VO");
%>


<%
    Material_Data_Service matSvc = new Material_Data_Service();
    List<Material_Data_VO> list = matSvc.getAll();
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
	
		<title>素材查詢</title>
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
						<h1 class="h3 mb-2 text-gray-800">素材查詢</h1>
						
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
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/material/mat.do" name="form1" enctype="multipart/form-data">


<jsp:useBean id="matypeSvc" scope="page" class="com.material.model.Material_Type_Service" />

<br>
<div class="input-group mb-3" style="max-width:1200px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">素材種類:
  </span>
  </div>
<div>
   <select class="form-control " id="exampleFormControlSelect1"  style="width:208px" name="ma_ty_no" id="ma_ty_no">
      <option value="">
     <c:forEach var="matypeVO" items="${matypeSvc.all}">
      <option value="${matypeVO.maTyNo}" >${matypeVO.maTyNam}
	 </c:forEach>  
   </select>
</div>
</div>

<div class="input-group mb-3" style="max-width:300px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">素材編號:</span>
  </div>
  <input type="text" class="form-control"  name="ma_no" id="ma_no" value=""  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
</div>

<div class="input-group mb-3" style="max-width:300px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">素材名稱:</span>
  </div>
  <input type="text" class="form-control"  name="ma_name" id="ma_name" value=""  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
</div>

<div class="input-group mb-3" style="max-width:300px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">金額上限:</span>
  </div>
  <input type="text" class="form-control"  name="ma_price_up" id="ma_price_up" value=""  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
</div>

<div class="input-group mb-3" style="max-width:300px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">金額下限:</span>
  </div>
  <input type="text" class="form-control"  name="ma_price_down" id="ma_price_down" value=""  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
</div>

<!-- <div style = "margin-left:70px"> -->
<!-- <button class="btn btn-primary" type="button" id="click2">Clean</button>  -->
<!-- &nbsp -->
<!-- <button class="btn btn-primary" type="button" id="click1">Click Me</button>  -->
<!-- </div> -->


<div class="mt-2" style = "margin-left:40px" >
<button  class="btn btn-primary"  type="submit" name="action" value="listAllMatype">                                
<span class="text">素材類別列表</span>                             
</button>
&nbsp  
<button class="btn btn-primary"  type="submit" name="action" value="listMat_ByCompositeQuery" >  
<span class="text">送出查詢</span>                             
</button>  
</div>


</FORM>
	
  <a class="scroll-to-top rounded" href="#page-top">
    <em class="fas fa-angle-up"></em>
  </a>

	
						<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					
				</div> <!--END OF content-->
				
				<%@include file="/backend/bar/footer.jsp"%>
				
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->

	
     <script type="text/javascript">
     
     $("#click1").click(function(){
     	  $("#ma_no").val("");
     	  $("#ma_name").val("");
     	  $("#ma_price_up").val("");
     	  $("#ma_price_down").val("");     	  
       });
     
     $("#click2").click(function(){
    	  $("#ma_no").val("");
    	  $("#ma_name").val("");
    	  $("#ma_price_up").val("");
    	  $("#ma_price_down").val("");     	  
      });
     
    </script>   
				
	</body>
	
</html>