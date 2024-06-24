<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.event_p.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Base64.*" %>
<%
  //因後臺不會新增投稿作品，將控制器的insert forward到event_spec
  Event_PVO event_pVO = (Event_PVO) request.getAttribute("event_pVO");
  pageContext.setAttribute("event_pVO", event_pVO);  
	
 	
 
  Event_PService svc=new Event_PService();	
  List<String> memIds= svc.findAllMem();
  pageContext.setAttribute("memIds",memIds);	


  List<String> eventNos= svc.findAllEventNo();
  pageContext.setAttribute("eventNos",eventNos);
  
  HttpSession sess=request.getSession();
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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css">
<title>Test insert</title>
</head>
<body>
${event_pVO==null }
<c:if test="${not empty errMsgs }">
	<c:forEach var="err" items="${errMsgs}">
		<ul>
			<li>${err}</li>
		</ul>		
		
	</c:forEach>
</c:if>



<a href="select_page.jsp">回首頁</a>
	<div class="container">
	<form action="<%=request.getContextPath() %>/backend/event_p/Event_pServlet" method="post" enctype="multipart/form-data">
				
			 <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">會員編號</label>
                <div class="col-sm-3">
                	<select class="form-control" name="mem_id">
                		<c:forEach var="memId" items="${memIds}">
                			<c:choose>
	                			<c:when test="${event_pVO.mem_id==memId}">
	                				<option value="${memId}" selected>${memId}</option>
	                			</c:when>
	                			<c:otherwise>                		
	                				<option value="${memId}" >${memId}</option>
	                			</c:otherwise>
                			</c:choose>
                		</c:forEach>
<!--                 			測試用會員編號:M000003 測試會員更新自己的上傳作品-->
									<option value="M000003">M000003</option>
                	</select>
                			
                </div>
            </div>

            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">活動編號</label>
                <div class="col-sm-3">
                	<select class="form-control" name="event_no">
                		<c:forEach var="event_no" items="${eventNos}">
                			<c:choose>
	                			<c:when test="${event_pVO.event_no==event_no}">
	                				<option value="${event_no}" selected>${event_no}</option>
	                			</c:when>
	                			<c:otherwise>                		
	                				<option value="${event_no}" >${event_no}</option>
	                			</c:otherwise>
                			</c:choose>
                		</c:forEach>
                	</select>
                </div>
            </div>
             <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品名稱</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control " name="event_p_name" value=<%=(event_pVO==null)?"":event_pVO.getEvent_p_name().toString() %> >
                </div>
            </div>
            
             <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品投票數</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="event_vote_num" value=<%=(event_pVO==null)?"":event_pVO.getEvent_vote_num().toString() %> >
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品排行</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control " name="vote_rank" value=<%=(event_pVO==null)?"":event_pVO.getVote_rank().toString() %> >
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品狀態</label>
                <div class="col-sm-3">
                	<select class="form-control" name="event_p_stat">
                		<option value="1" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==1?"selected":"" %>>待審核</option>
                		<option value="2" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==2?"selected":"" %>>通過</option>
                		<option value="3" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==3?"selected":"" %> >不通過</option>
                	</select>
<%--                     <input type="text" class="form-control " name="event_p_stat" value=<%=(event_pVO==null)?"":event_pVO.getEvent_p_stat().toString() %> > --%>
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">上傳圖片(待修)</label>
                <div class="col-sm-3">
<%--                    <input type="text" class="form-control " name="event_vote_end" value=<%=(event_pVO==null)?"":event_pVO.getEvent_vote_end().toString() %> > --%>
						<input type="file" name="pic01" id="myFile" onchange="upload()">
						<img id="demo" <%=event_pVO==null?"":"src=data:image/jepg;base64,"+request.getAttribute("data") %>>
						
                </div>
            </div>
     
            
			<input type="hidden" name="imgForm" value="">
			<input type="hidden" name="action" value="insert">
			<input type="submit" value="送出" >
	</form>
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
// 		url=getObjectURL(file);	
		
		
	}
// 	function getObjectURL(file) {  
// 	     var url = null;  
// 	     if (window.createObjcectURL != undefined) {  
// 	         url = window.createOjcectURL(file);  
// 	     } else if (window.URL != undefined) {  
// 	         url = window.URL.createObjectURL(file);  
// 	     } else if (window.webkitURL != undefined) {  
// 	         url = window.webkitURL.createObjectURL(file);  
// 	     }  
// 	     return url;  
// 	 }

		

// 	window.onload=init;
</script>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
<!--     <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script> -->
<!--     <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script> -->
<%-- 	<script src="<%=request.getContextPath()%>/backend/template/js/bootstrap.min.js"></script> --%>
</html>