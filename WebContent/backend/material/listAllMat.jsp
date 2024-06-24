<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.material.model.*"%>

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
	
<!-- <ul class="displayEmp">           -->

<!-- 	<li> -->
<%--      <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/material/mat.do" class="form-inline"> --%>
<!--        <b>選擇素材名稱:&nbsp</b> -->
<!--        <select class="form-control " id="exampleFormControlSelect1"  style="max-width:200px" name="ma_no"> -->
<!--           <option value=""> -->
<%--          <c:forEach var="matVO" items="${list}" >  --%>
<%--           <option value="${matVO.maNo}">${matVO.maName} --%>
<%--          </c:forEach>    --%>
<!--        </select> -->
<!--        <b>&nbsp</b><button class="btn btn-primary " type="submit" name="action" value="getOne_For_Display"  >送出</button> -->
<!--      </FORM> -->
<!--    </li> -->
  
<!-- </ul> -->

<!--         <div class="container-fluid"> -->

<!--           Page Heading -->
<!--           <h3 class="h3 mb-2 text-gray-800">&nbsp</h3> -->
	       <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</h6>
            </div>




             <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>素材編號</th>
                      <th>素材種類編號</th>
                      <th>名稱</th>
                      <th>價格</th>
                      <th>照片</th>
                      <th>狀態</th>
                      <th>修改</th>
                    </tr>            
                  </thead>
                  <tfoot>
                    <tr>
                      <th>MaterialNo</th>
                      <th>MaterialTypeNo</th>
                      <th>Name</th>
                      <th>Price</th>
                      <th>Photo</th>
                      <th>Status</th>
                      <th>Edit</th>
                    </tr>
                  </tfoot>
                  <tbody>
                  <%@ include file="pages/page1.file" %> 
                  <c:forEach var="matVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>"> 
                    <tr>
                      <td>${matVO.maNo}</td>
                      <td>${matVO.maTyNo}</td>
                      <td>${matVO.maName}</td>
                      <td>${matVO.maPrice}</td>
                      <td><img src="<%=request.getContextPath()%>/backend/material/mat.pic?ma_no=${matVO.maNo}" width="120" height="120"></td>                                    
                     <td> <c:choose>
                      <c:when test="${matVO.maStatus == 1}">
     					  上架
   					 </c:when>
   					 <c:when test="${matVO.maStatus == 0}">
     					  下架
   					 </c:when>
					 </c:choose></td>
                      
                      
                      <td>
			  			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/material/mat.do" style="margin-bottom: 0px;">
   					    <input type="hidden" name="ma_no" value="${matVO.maNo}">
    					 <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			  			 <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
                        <input type="hidden" name="action"	value="getOne_For_Update">
                        <button class="btn btn-primary" type="submit">修改</button></FORM>
					 </td>				 
                    </tr>
                   </c:forEach> 
                  </tbody>
                </table>
                <%@ include file="pages/page2.file" %>  
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