<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.event_p.model.*" %>
<%@ page import="com.event.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Base64.*" %>
<%@ page import="com.mem.model.*" %>

<%
	HttpSession ses_displayMem=request.getSession();	
// 	if(ses_displayMem.getAttribute("mem_id")==null){
// 		ses_displayMem.setAttribute("mem_id","M000003");
// 	}	

	String mem_id=((MemVO)ses_displayMem.getAttribute("memVO")).getMem_id();
	Event_PService eventPSvc=new Event_PService();
	Event_PVO event_pVO=eventPSvc.findUploadByMemid(mem_id,(String)ses_displayMem.getAttribute("event_no"));
	pageContext.setAttribute("event_pVO",event_pVO);
	
	EventService eventService=new EventService();
	EventVO eventMemVO=eventService.findByPrimaryKey(event_pVO.getEvent_no());
%>


<c:if test="<%=event_pVO!=null %>">
	<%
	  byte[] imgBytes=event_pVO.getEvent_p_img();
	  final Base64.Encoder encoder = Base64.getEncoder();
	  String data=encoder.encodeToString(imgBytes);
	  request.setAttribute("data", data);
	%>	
</c:if>
  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
<%-- 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css"> --%>
<!-- 			<link rel="stylesheet" type="text/css" -->
<%-- 	href="<%=request.getContextPath()%>/frontend/template/css/bootstrap.min.css"> --%>
<title>投稿作品頁面</title>

</head>
<body>

<c:if test="${not empty errMsgs }">
	<c:forEach var="err" items="${errMsgs}">
		<ul>
			<li>${err}</li>
		</ul>		
	</c:forEach>
</c:if>
<!-- <a href="event_spec.jsp">回到主題競賽</a> -->


	<div class="container " id="displayMemUploadMain">
	
	<form action="<%=request.getContextPath()%>/frontend/event_p/Event_pServlet" method="post" enctype="multipart/form-data">
		
	<div class="row ">
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label col-sm-offset-1">會員編號</label>
                <div class="col-sm-3">
                	<input type="text" class="form-control" name="mem_id" value="<%=((MemVO)ses_displayMem.getAttribute("memVO")).getMem_id()%>" readonly="readonly">
                </div>
            </div>

			<div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label col-sm-offset-1">活動編號</label>
                <div class="col-sm-3">
                	<input type="text" class="form-control" name="event_no" value="<%=ses_displayMem.getAttribute("event_no") %>" readonly="readonly">
                </div>
            </div>
             <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label col-sm-offset-1">投稿作品名稱</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control " name="event_p_name" value="${event_pVO.event_p_name }"readonly="readonly" >
                </div>
            </div>
            
             <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label col-sm-offset-1">投稿作品投票數</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="event_vote_num" value="${event_pVO.event_vote_num}" readonly >
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label col-sm-offset-1">投稿作品排行</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control " name="vote_rank" value="${event_pVO.vote_rank}" readonly >
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label col-sm-offset-1">投稿作品狀態</label>
                <div class="col-sm-3">
                		<%
                			String[] pic_statStr={"可投票","已被檢舉"};
                			pageContext.setAttribute("pic_statStr",pic_statStr);
                		%>
                		<input type="text" class="form-control " name="event_p_stat" value="${pic_statStr[event_pVO.event_p_stat-1]}" readonly >
<!--                 	<select class="form-control" name="event_p_stat"> -->
<%--                 		<option value="1" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==1?"selected":"" %>>待審核</option> --%>
<%--                 		<option value="2" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==2?"selected":"" %>>通過</option> --%>
<%--                 		<option value="3" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==3?"selected":"" %> >不通過</option> --%>
<!--                 	</select> -->
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label col-sm-offset-1">上傳圖片(待修)</label>
                <div class="col-sm-3">
                			<img alt="" src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=<%=event_pVO.getEvent_p_no().toString()%>">
<!-- 						<input type="file" name="pic01" id="myFile" onchange="upload()"> -->
<%-- 						<img id="demo" <%=event_pVO==null?"":"src=data:image/jepg;base64,"+request.getAttribute("data") %>> --%>
						
                </div>
            </div>   
            <input type="hidden" name="event_p_no" value="${event_pVO.event_p_no}">
			<input type="hidden" name="imgForm" value="">
			<input type="hidden" name="action" value="change_to_update"><!-- value="change_to_update" -->
			<div class="col-sm-2 col-sm-offset-3"  style="padding-right: 0px; padding-left: 60px;">
			<input type="submit" value="修改"  ${eventVO.event_stat!=1?"disabled":""} style="align:center;">
			<!-- 2:開始投票 -->
			</div>
			</div>
	</form>
	
	</div>
	<hr class="featurette-divider">
	<div class="row">
		<div class="">
		</div>
		<form action="<%=request.getContextPath()%>/Event_pServlet" method="post" id="deletePicForm">
					<input type="hidden" name="action" value="delete">
					<input type="hidden" name="event_no" value="${event_pVO.event_no }">
					<input type="hidden" name="event_p_no" value="${event_pVO.event_p_no }" id="event_pVOEvent_p_no">
					<input type="submit" value="刪除" ${eventVO.event_stat!=1?"disabled":""}>
		</form>
	</div>
	<img id="demo2">
	</div>
</body>




<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.datetimepicker.full.js"></script>
<script type="text/javascript">
// 	function init(){
// 	}
	var img2=document.getElementById("demo2");
	var myFile;
// 	var url="";
	function upload(){		
		myFile=document.getElementById("myFile");
		var img=document.getElementById("demo");
		var files =myFile.files;//找出上傳檔案
		var file=files[0];//取第一個來源
		var reader =new FileReader;
		reader.addEventListener('load',function(){			
			img.setAttribute('src',reader.result);			
		})

		reader.readAsDataURL(file);
		
		
	}


		

// 	window.onload=init;
</script>
    <script src="<%=request.getContextPath() %>/frontend/template/jquery/jquery-3.1.0.min.js" ></script>
	 <script>
    window.jQuery || document.write('<script src="<%=request.getContextPath()%>/frontend/template/jquery/jquery.min.js"><\/script>')

  </script>
	<script src="<%=request.getContextPath() %>/frontend/template/js/bootstrap.min.js" ></script>
</html>