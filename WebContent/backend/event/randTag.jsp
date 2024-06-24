<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.event_tag.model.*"%>
<%@ page import="java.util.*"%>
<%
	Event_TagService randTagSvc = new Event_TagService();
	List<Event_TagVO> event_tagRandVOs = randTagSvc.randTag();
	pageContext.setAttribute("event_tagRandVOs", event_tagRandVOs);
	
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css">
<title>Rand Tag</title>
<style>


</style>
</head>
<body>
	<form action="Event_TagServlet" method="post">
		<input type="button" value="重新抽選" id="roll">
		<div class="form-group row">
<!-- 		<label for="inputEmail3" class="col-sm-2 col-form-label">Hashtag名稱</label> -->
		</div>
		
		
		

		<input type="hidden" name="action" value="randTag"> 
		
	</form>
</body>
<%-- <script src="<%=request.getContextPath()%>/backend/template/jquery/jquery-3.5.1.js"></script> --%>
<script src="<%=request.getContextPath()%>/backend/template/vendor/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/backend/template/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript">
	$("#roll").click(function(){
	$.ajax({
			url:"<%=request.getContextPath()%>/Event_TagServlet",
			type:"post",
			data:{
				action:"roll"
			},
			success:function(e){
				
				var txt="";
				
					$("#resTxt").text(e);
					txt+="<input type=\"text\" class=\"form-control\" name=\"event_tag_name\""+"value="+e+" readonly>"
					$("#resTxt").html(txt);
			}
		});
	
	});
</script>
</html>