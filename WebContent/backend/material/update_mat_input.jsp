<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.material.model.*"%>


<%
Material_Data_VO material_Data_VO = (Material_Data_VO) request.getAttribute("material_Data_VO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
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
						<h1 class="h3 mb-2 text-gray-800">素材資料修改</h1>
						
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
	
<div class="container">
<div class="row align-items-center">

<jsp:useBean id="matypeSvc" scope="page" class="com.material.model.Material_Type_Service" />


<div class="col col-xs-12 col-sm-12 col-md-5">
<div class="row" id="preview"></div>
<img src="<%=request.getContextPath()%>/backend/material/mat.pic?ma_no=<%=material_Data_VO.getMaNo()%>" width="330" height="330" id="image">
		
<div class="input-group mb-3 mt-5" >
  <div class="input-group-prepend">
<!--     <span class="input-group-text" id="inputGroupFileAddon01" >Upload</span> -->
  </div>
  <div class="custom-file" style="max-width:300px">
    <input type="file" class="custom-file-input" id="myFile" name="ma_photo" aria-describedby="inputGroupFileAddon01" >
    <input type=hidden name="filename" id="filename">
    <label class="custom-file-label" style="width:330px" for="inputGroupFile01">Choose Photo</label>  
  </div>
</div>

</div>




<div class="col col-xs-12 col-sm-12 col-md-6">

<div class="input-group mb-3" style="max-width:1200px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">素材種類:
  </span>
</div>

<div>
   <select class="form-control " id="exampleFormControlSelect1"  style="width:337px" name="ma_ty_no">
     <c:forEach var="matypeVO" items="${matypeSvc.all}">
      <option value="${matypeVO.maTyNo}" ${(material_Data_VO.maTyNo==matypeVO.maTyNo)?'selected':'' } >${matypeVO.maTyNam}
	 </c:forEach>
   </select> 
</div>       
</div>

<div class="input-group mb-3" style="max-width:1200px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">素材編號:<font color=red><b>*</b></font><%=material_Data_VO.getMaNo()%>
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    
    </span>
  </div>
</div>

<div class="input-group mb-3" style="max-width:430px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">素材名稱:</span>
  </div>
  <input type="text" class="form-control"  name="ma_name" value="<%=material_Data_VO.getMaName()%>"  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
</div>		
		
<div class="input-group mb-3" style="max-width:430px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">素材金額:</span>
  </div>
  <input type="text" class="form-control"  name="ma_price" value="<%=material_Data_VO.getMaPrice()%>"  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
</div>	

<div>
<div class="input-group mb-3" style="max-width:1200px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">狀態:
  </span>
  </div>


<div>
  <select class="form-control " id="exampleFormControlSelect1"  style = "width:370px" name="ma_status">
    <option value="<%=material_Data_VO.getMaStatus()%>"> <%=material_Data_VO.getMaStatus() == 1? "上架":"下架"%></option>
    <option value="<%=material_Data_VO.getMaStatus() == 1? "0":"1"%>"> <%=material_Data_VO.getMaStatus() == 1? "下架":"上架"%></option>
  </select>
</div>
</div>	
	
<!-- 	 <div class="container"> -->
<!-- 	  <div class="row"> -->
<!-- 	    <div class="col text-center"> -->
<div style = "margin-left:176px">
<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> <!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
<input type="hidden" name="whichPage"  value="<%=request.getParameter("whichPage")%>">  <!--只用於:istAllEmp.jsp-->
<input type="hidden" name="action" value="update">
<input type="hidden" name="ma_no" value="<%=material_Data_VO.getMaNo()%>">
<button class="btn btn-primary" type="submit">送出修改</button>
</div>
</div>
<!-- </div> -->
<!-- </div> -->
<!-- </div> -->
</div>

</div>
</div>

</FORM>
	
						<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					
				</div> <!--END OF content-->
				
				<%@include file="/backend/bar/footer.jsp"%>
				
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
	
    <script type="text/javascript">
    
      function loadImageFile(event){ 
      var image = document.getElementById('image'); 
      image.src = URL.createObjectURL(event.target.files[0]); };
      
  </script> 
						
	</body>
	
</html>