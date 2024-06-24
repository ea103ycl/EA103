<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.material.model.*"%>

<%
	Material_Data_VO material_Data_VO = (Material_Data_VO) request.getAttribute("material_Data_VO"); //EmpServlet.java(Concroller), 存入req的empVO物件
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
						<h1 class="h3 mb-2 text-gray-800">頁面標題</h1>
						
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
	
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
                    </tr>
                  </tfoot>
                  <tbody>
              
	<tr>
		<td><%=material_Data_VO.getMaNo()%></td>
		<td><%=material_Data_VO.getMaTyNo()%></td>
		<td><%=material_Data_VO.getMaName()%></td>
		<td><%=material_Data_VO.getMaPrice()%></td>
		<td><img src="<%=request.getContextPath()%>/backend/material/mat.pic?ma_no=<%=material_Data_VO.getMaNo()%>" width="120" height="120"></td>
		<td><%=material_Data_VO.getMaStatus() == 1? "上架":"下架"%></td>
	</tr>

	
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
	
		<script>
		<!--JavaScript內容-->
		</script>
						
	</body>
	
</html>