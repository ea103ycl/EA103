<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>

<%
  Emp_Account_VO emp_Account_VO = (Emp_Account_VO) request.getAttribute("emp_Account_VO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>

<!DOCTYPE html>
<html>
	
	<head>
	
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
	
		<title>員工修改</title>
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
						<h1 class="h3 mb-2 text-gray-800">員工資料修改</h1>
						
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/emp/emp.do" name="form1" enctype="multipart/form-data" onchange="loadImageFile(event)">

<div class="container">
<div class="row align-items-center">

<!-- <div class="col col-xs-12 col-sm-12 col-md-1"> -->
<!-- &nbsp -->
<!-- </div> -->



<div class="col col-xs-12 col-sm-12 col-md-5">
<div class="row" id="preview"></div>
<img src="<%=request.getContextPath()%>/backend/emp/emp.pic?emp_no=<%=emp_Account_VO.getEmpNo()%>" width="300" height="300" id="image">	


			<div class="input-group mb-3 mt-5" >
			  <div class="input-group-prepend">	
<!--     <span class="input-group-text" id="inputGroupFileAddon01" >Upload</span> -->
  </div>
  <div class="custom-file" style="max-width:300px">
    <input type="file" class="custom-file-input" id="myFile" name="emp_photo" aria-describedby="inputGroupFileAddon01" >
    <input type=hidden name="filename" id="filename">
    <label class="custom-file-label" for="inputGroupFile01">Choose Photo</label>  
    </div>
  </div>
</div>




<div class="col col-xs-12 col-sm-12 col-md-6">

<div class="input-group mb-3" style="max-width:1200px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">編號:<font color=red><b>*</b></font><%=emp_Account_VO.getEmpNo()%>
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    &nbsp&nbsp&nbsp
    </span>
  </div>
</div>

<div class="input-group mb-3" style="max-width:430px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">姓名:</span>
  </div>
  <input type="text" class="form-control"  name="emp_name" id="emp_name" value="<%=emp_Account_VO.getEmpName()%>"  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
</div>

<div class="input-group mb-3" style="max-width:430px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">職位:</span>
  </div>
  <input type="text" class="form-control"  name="emp_pos" id="emp_pos" value="<%=emp_Account_VO.getEmpPos()%>"  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
</div>

<div class="input-group mb-3" style="max-width:430px">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">信箱:</span>
  </div>
  <input type="text" class="form-control"  name="emp_mail" id="emp_mail" value="<%=emp_Account_VO.getEmpMail()%>"  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
</div>

	
<!-- 	<tr> -->
<!-- 		<td>狀態:</td> -->
<!-- 		<td><select size="1" name="emp_status"> -->
<%-- 		<option value="<%=emp_Account_VO.getEmpStatus()%>"> <%=emp_Account_VO.getEmpStatus() == 1? "在職中":"已離職"%></option> --%>
<%-- 		<option value="<%=emp_Account_VO.getEmpStatus() == 1? "0":"1"%>"> <%=emp_Account_VO.getEmpStatus() == 1? "已離職":"在職中"%></option> --%>
<!-- 		</select></td>		 -->
<!-- 	</tr>	 -->
	
	
<div>

<div class="input-group mb-3" >
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">狀態:
  </span>
  </div>
  
  
  

<!-- <div>&nbsp</div> -->

<div>
  <select class="form-control " id="exampleFormControlSelect1"  style = "width:370px" name="emp_status">
    <option value="<%=emp_Account_VO.getEmpStatus()%>"> <%=emp_Account_VO.getEmpStatus() == 1? "在職中":"已離職"%></option>
    <option value="<%=emp_Account_VO.getEmpStatus() == 1? "0":"1"%>"> <%=emp_Account_VO.getEmpStatus() == 1? "已離職":"在職中"%></option>
  </select>
</div>
</div>

	
	
	<jsp:useBean id="funcidSvc" scope="page" class="com.emp.model.Func_No_List_Service"/>
	<table class="table table-borderless">
  		<tbody>
			<tr>
		 		<c:forEach var="funcidVO" items="${funcidSvc.all}" varStatus="vs">
	      		<td><input type="checkbox" id="${funcidVO.funcId }" name="func_id" value="${funcidVO.funcId }" >${funcidVO.funcName}</td>
		  		<c:if test="${vs.count % 3 == 0 }">
		  			<tr></tr>
		  		</c:if>	
	  			</c:forEach>
    		</tr>
  		</tbody>
	</table>


<jsp:useBean id="funcSvc" scope="page" class="com.emp.model.Available_Func_Service"/>

<c:forEach var="funcVO" items="${funcSvc.getOneEmpFunc(emp_Account_VO.empNo)}">
<input type="hidden" value="${funcVO.funcId}" class="g">
</c:forEach>

<!-- 	 <div class="container"> -->
<!-- 	  <div class="row"> -->
<!-- 	    <div class="col text-center"> -->

<div style = "margin-left:70px">
<input type="hidden" name="action" value="update">
<input type="hidden" name="emp_no" value="<%=emp_Account_VO.getEmpNo()%>">
<button class="btn btn-primary" type="button" onclick="selAll();">全選</button>  
<button class="btn btn-primary" type="button" onclick="unselAll();">全取消</button>  
<button class="btn btn-secondary" type="button" id="click">Click Me</button>  
<button class="btn btn-primary" type="submit">送出修改</button>

<!-- </div> -->
<!-- 	  </div> -->
<!-- 	</div> -->
</div>
</div>
</div>

<div class="col col-xs-12 col-sm-12 col-md-2">
</div>

</div>
</div>

</FORM>

						<%-- 資料內容 --%>
	
	
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
      
      
      
      function init(){
    	  var g = document.getElementsByClassName("g");
    	  var box = document.getElementsByName("func_id");
    	  for(let i=0; i< box.length; i++){
    		  for(var j=0; j<g.length; j++){
    			  
    			  if(g[j].value === box[i].value){
    				  box[i].checked = true;
    				     				    				  
    			  }    			  
    		  }    		      		  
    	  }    	      	      	      	  
      }
      window.onload = init;
      
      
      
      $("#click").click(function(){
      	  $("#emp_name").val("THANOS");
      	  $("#emp_pos").val("倉儲經理");
      	  $("#emp_mail").val("farmer7382@gmail.com");
        });
      
      
      function selAll(){
          var checkItem = document.getElementsByName("func_id");
          for(var i=0;i<checkItem.length;i++){
              checkItem[i].checked=true; 
          }
      }
      
      function unselAll(){
          //變數checkItem為checkbox的集合
          var checkItem = document.getElementsByName("func_id");
          for(var i=0;i<checkItem.length;i++){
              checkItem[i].checked=false;
          }
      }  
      
      
      
      
  </script> 
						
	</body>
	
</html>