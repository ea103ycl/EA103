<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>

<%
  Emp_Account_VO emp_Account_VO = (Emp_Account_VO) request.getAttribute("emp_Account_VO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<jsp:useBean id="funcSvc" scope="page" class="com.emp.model.Available_Func_Service"/>
<jsp:useBean id="funcidSvc" scope="page" class="com.emp.model.Func_No_List_Service"/>

<!DOCTYPE html>
<html>
	
	<head>
	
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
	
		<title>員工查詢</title>
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
						<h1 class="h3 mb-2 text-gray-800">Summary</h1>
						
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
                      <th>員工編號</th>
                      <th>員工姓名</th>
                      <th>職位</th>
                      <th>信箱</th>
                      <th>照片</th>
                      <th>狀態</th>   
                      <th>權限</th>     
                    </tr>            
                  </thead>
                  <tfoot>
                    <tr>
                      <th>EmpNo</th>
                      <th>EmpName</th>
                      <th>Position</th>
                      <th>Email</th>
                      <th>Photo</th>
                      <th>Status</th>
                      <th>Authority</th>
                    </tr>
                  </tfoot>
                  <tbody>
              
	<tr>
		<td><%=emp_Account_VO.getEmpNo()%></td>
		<td><%=emp_Account_VO.getEmpName()%></td>
		<td><%=emp_Account_VO.getEmpPos()%></td>
		<td><%=emp_Account_VO.getEmpMail()%></td>
		<td><img src="<%=request.getContextPath()%>/backend/emp/emp.pic?emp_no=<%=emp_Account_VO.getEmpNo()%>" width="120" height="120"></td>
		<td><%=emp_Account_VO.getEmpStatus() == 1? "在職中":"已離職"%></td>
		<td>
		<c:forEach var="funcVO" items="${funcSvc.getOneEmpFunc(emp_Account_VO.empNo)}" varStatus="vs"> 
		<value="${funcVO.funcId}">${funcidSvc.getOneFunc(funcVO.funcId).funcName}<br>
		<c:if test="${vs.count % 1 == 0 }">
		</c:if>	
		</c:forEach>
		</td>
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