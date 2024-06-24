<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>

<%
    Emp_Account_Service empSvc = new Emp_Account_Service();
    List<Emp_Account_VO> list = empSvc.getAll();
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
	
		<title>員工查詢</title>
		
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

<ul class="displayEmp">          
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/emp/emp.do" class="form-inline">
        <b>輸入員工編號 (如E0001):&nbsp&nbsp</b>      
        <input class="form-control mr-sm-2" type="text" name="emp_no1" placeholder="Search" aria-label="Search"  style="max-width:100px" >   

       <b>選擇員工編號:&nbsp&nbsp</b>
       <select class="form-control " id="exampleFormControlSelect1"  style="max-width:100px" name="emp_no2">
          <option value="">
         <c:forEach var="empVO" items="${list}" > 
          <option value="${empVO.empNo}">${empVO.empNo}
         </c:forEach>   
       </select>
   
       <b>&nbsp&nbsp選擇員工姓名:&nbsp&nbsp</b>
       <select class="form-control " id="exampleFormControlSelect1"  style="max-width:170px" name="emp_no3">
          <option value="">
         <c:forEach var="empVO" items="${list}" > 
          <option value="${empVO.empNo}">${empVO.empName}
         </c:forEach>   
       </select>
       <b>&nbsp&nbsp</b><button class="btn btn-primary " type="submit" name="action" value="getOne_For_Display"  >送出</button>
     </FORM>
   </li>
  
 <b>&nbsp&nbsp</b>
  
  <li>
<%--      <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/emp/emp.do" > --%>
<!--        <input type="hidden" name="action" value="logout"> -->
<!--        <button class="btn btn-primary" type="submit">登出</button> -->
<!--      </FORM> -->
  </li>
</ul>

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
                      <th>員工編號</th>
                      <th>員工姓名</th>
                      <th>職位</th>
                      <th>信箱</th>
                      <th>照片</th>
                      <th>狀態</th>
                      <th>修改</th>
                      <th>刪除</th>
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
                      <th>Edit</th>
                      <th>Delete</th>
                    </tr>
                  </tfoot>
                  <tbody>
                  <%@ include file="pages/page1.file" %> 
                  <c:forEach var="empVO" items="${list}"  begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">   
                    <tr>
                      <td>${empVO.empNo}</td>
                      <td>${empVO.empName}</td>
                      <td>${empVO.empPos}</td>
                      <td>${empVO.empMail}</td>
                      <td><img src="<%=request.getContextPath()%>/backend/emp/emp.pic?emp_no=${empVO.empNo}" width="120" height="120"></td>                                    
                     <td> <c:choose>
                      <c:when test="${empVO.empStatus == 1}">
     					  在職中
   					 </c:when>
   					 <c:when test="${empVO.empStatus == 0}">
     					  已離職
   					 </c:when>
					 </c:choose></td>
       
                      <td>
			  			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/emp/emp.do" style="margin-bottom: 0px;">
   					    <input type="hidden" name="emp_no" value="${empVO.empNo}">
                        <input type="hidden" name="action"	value="getOne_For_Update">
                        <button class="btn btn-primary" type="submit">修改</button></FORM>
					 </td>				 
					  <td>
			  		   <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/emp/emp.do" style="margin-bottom: 0px;">
			    	   <input type="hidden" name="emp_no"  value="${empVO.empNo}">
			     	   <input type="hidden" name="action" value="delete">
                       <button class="btn btn-primary" type="submit">刪除</button></FORM>
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