<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.event_tag.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Base64.*" %>
<%
  Event_TagVO event_tagVO = (Event_TagVO) request.getAttribute("event_tagVO");
  List<String> errMsgs=(List<String>)request.getAttribute("errMsgs");	
 	
 
  Event_TagService svc=new Event_TagService();	
  List<String> memIds= svc.findAllMem();
  pageContext.setAttribute("memIds",memIds);	


  List<String> eventNos= svc.findAllEventNo();
  pageContext.setAttribute("eventNos",eventNos);
  
  //會員假設E000003
  HttpSession sess=request.getSession();
  String mem_id="";
  if(sess.getAttribute("mem_id")==null){
	  mem_id="M000003";
	  sess.setAttribute("mem_id", mem_id);
  }
%>

  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css">
<title>Insert Event Tag</title>
</head>
<body>
<c:if test="${not empty errMsgs }">
	<c:forEach var="err" items="${errMsgs}">
		<ul>
			<li>${err}</li>
		</ul>		
		
	</c:forEach>
</c:if>
<a href="event_tag_select_page.jsp">回首頁</a><br>



	<div class="container">
	<form action="Event_TagServlet" method="post" enctype="application/x-www-form-urlencoded">
		
			 <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">會員編號</label>
                <div class="col-sm-3">
					<input type="text" class="form-control " name="mem_id" value="${mem_id}" readonly="readonly">                			
                </div>
            </div>

             <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">Hashtag 名稱</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control " name="event_tag_name" value=<%=(event_tagVO==null)?"":event_tagVO.getEvent_tag_name().toString() %> >
                </div>
            </div>
			<input type="hidden" name="action" value="insert">
			<input type="submit" value="送出" >
	</form>
	
	</div>
</body>




<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.datetimepicker.full.js"></script>
<script type="text/javascript">

</script>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
<!--     <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script> -->
<!--     <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script> -->
<%-- 	<script src="<%=request.getContextPath()%>/backend/template/js/bootstrap.min.js"></script> --%>
</html>