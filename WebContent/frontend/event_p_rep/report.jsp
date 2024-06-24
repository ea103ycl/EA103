<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.event_p.model.*" %>
<%@ page import="com.mem.model.*" %>
<%
	String event_p_no=(String)request.getAttribute("event_p_no");//取得投稿作品資訊
	HttpSession sess=request.getSession();
	String mem_id=((MemVO)sess.getAttribute("memVO")).getMem_id();
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
		<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/frontend/template/css/bootstrap.min.css">
		<link href="<%=request.getContextPath()%>/frontend/template/css/album.css">
<title>report page</title>
</head>
<body>
<%@include  file="/frontend/bar/frontBarTop.jsp"%>
<div style="margin-top:120px;"></div>
<a href="<%=request.getContextPath() %>/frontend/event_p/event_spec.jsp">回到主題競賽</a>


	<div class="container">
<!-- 	<form action="Event_pServlet" method="post" > -->
<!-- 		<input type="submit" value="送出"> -->
<!-- 	</form> -->
	
	<form action="Event_P_RepServlet" method="post" >
		

            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品編號</label>
                <div class="col-sm-3">
                	<input type="text" class="form-control" name="event_p_no" value="<%=event_p_no%>" readonly="readonly">
                </div>
            </div>

			<div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">檢舉人編號</label>
                <div class="col-sm-3">
                	<input type="text" class="form-control" name="mem_id" value="<%=((MemVO)sess.getAttribute("memVO")).getMem_id()%>" readonly="readonly">
                </div>
            </div>
            
             <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">檢舉原因</label>
                <div class="col-sm-3">
                    <textarea  class="form-control" name="rep_reason"  rows="3"  ></textarea>
                </div>
                <c:if test="${not empty errMsgs }">
					<c:forEach var="err" items="${errMsgs}">
						<ul>
							<li style="color:red;">${err}</li>
						</ul>		
						
					</c:forEach>
				</c:if>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">被檢舉的圖片</label>
                <div class="col-sm-3">
                    <img src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=<%=event_p_no%>" width="300px" height="300px">
                </div>
            </div>
    
		
			<input type="hidden" name="action" value="reportpic">
			<input type="submit" value="送出" >
	</form>
	
	</div>
	<%@include file="/frontend/bar/frontBarFooter.jsp" %>
    <script src="<%=request.getContextPath() %>/frontend/template/jquery/jquery-3.1.0.min.js" ></script>
	 <script>
    window.jQuery || document.write('<script src="<%=request.getContextPath()%>/frontend/template/jquery/jquery.min.js"><\/script>')

  </script>
	<script src="<%=request.getContextPath() %>/frontend/template/js/bootstrap.min.js" ></script>
</body>
</html>