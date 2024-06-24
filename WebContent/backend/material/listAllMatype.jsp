<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.material.model.*"%>

<!DOCTYPE html>
<html>
	
	<head>
	
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
	
		<title>素材查詢</title>
		
 <style>
 .displayEmp li{
 	list-style:none;
 	display:inline-block;
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
						<h1 class="h3 mb-2 text-gray-800">&nbsp</h1>
						
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
	
  


<!--         <div class="container-fluid"> -->

<!--           Page Heading -->
<!--           <h3 class="h3 mb-2 text-gray-800">&nbsp</h3> -->
	       <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</h6>
            </div>



<jsp:useBean id="matypeSvc" scope="page" class="com.material.model.Material_Type_Service" />
<jsp:useBean id="matSvc" scope="page" class="com.material.model.Material_Data_Service" />


             <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>類別編號</th>
                      <th>類別名稱</th>
                      <th>類別素材清單</th>
                      <th>修改</th>
<!--                       <th>刪除</th> -->
                    </tr>            
                  </thead>
                  <tfoot>
                    <tr>
                      <th>MaterialTypeNo</th>
                      <th>Name</th>
                      <th>MaterialList</th>
                      <th>Edit</th>
<!--                       <th>Delete</th> -->
                    </tr>
                  </tfoot>
                  <tbody>




                  <c:forEach var="matypeVO" items="${matypeSvc.all}" > 
                    <tr>
                      <td>${matypeVO.maTyNo}</td>
                      <td>${matypeVO.maTyNam}</td>
                      <td>   
                 
                 	<c:forEach var="matVO" items="${matSvc.getOneMaterialType(matypeVO.maTyNo)}" > 
					<value="${matVO.maName}">＊${matVO.maName}<br>
					</c:forEach>     
                      
                     <td>
			  			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/material/mat.do" style="margin-bottom: 0px;">
   					    <input type="hidden" name="ma_ty_no" value="${matypeVO.maTyNo}">
                        <input type="hidden" name="action"	value="getOne_For_UpdateType">
                        <button class="btn btn-primary" type="submit">修改</button></FORM>
					 </td>				 
<!-- 					  <td> -->
<%-- 			  		   <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/material/mat.do" style="margin-bottom: 0px;"> --%>
<%-- 			    	   <input type="hidden" name="ma_ty_no"  value="${matypeVO.maTyNo}"> --%>
<!-- 			     	   <input type="hidden" name="action" value="deleteType"> -->
<!--                        <button class="btn btn-primary" type="submit">刪除</button></FORM> -->
<!-- 				    </td> -->
                    </tr>
                   </c:forEach> 
                  </tbody>
                </table>
              </div>
            </div>
          </div>

        </div>
        <!-- /.container-fluid -->					
				</div> <!--END OF content-->
				
				<%@include file="/backend/bar/footer.jsp"%>
				
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
	
<!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <em class="fas fa-angle-up"></em>
  </a>	
	
		<script>
		<!--JavaScript內容-->
		</script>
		

		
  <script src="<%=request.getContextPath()%>/backend/template/vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="<%=request.getContextPath()%>/backend/template/vendor/datatables/dataTables.bootstrap4.min.js"></script>

  <!-- Page level custom scripts -->
<%--   <script src="<%=request.getContextPath()%>/backend/template/js/demo/datatables-demo.js"></script> --%>
						
	</body>
	
</html>