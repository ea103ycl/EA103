<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.event_p.model.*" %>
<%@ page import="java.util.*" %>
<%
	Event_PVO event_pVO=(Event_PVO)request.getAttribute("event_pVO");
	List<String> errMsgs=(List<String>)request.getAttribute("errMsgs");
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
	
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
	
		<title>我是標題</title>
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
						<c:if test="${not empty errMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
	
						<%-- 資料內容 --%>
						<a href="<%=request.getContextPath() %>/backend/event_p/listAllPic.jsp">回主頁</a><br>
<div class="container">
	<form action="<%=request.getContextPath() %>/backend/event_p/Event_pServlet" method="post" enctype="multipart/form-data">
			<div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品編號</label>
                <div class="col-sm-3">
					<input type="text" class="form-control" name="event_p_no" value="${event_pVO.event_p_no }" readonly="readonly">                			
                </div>
            </div>
			
			 <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">會員編號</label>
                <div class="col-sm-3">
					<input type="text" class="form-control" name="mem_id" value="${event_pVO.mem_id }" readonly="readonly">                			
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">活動編號</label>
                <div class="col-sm-3">
					<input type="text" class="form-control" name="event_no" value="${event_pVO.event_no }" readonly="readonly">
                </div>
            </div>
             <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品名稱</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control " name="event_p_name" value=<%=(event_pVO==null)?"":event_pVO.getEvent_p_name().toString() %> readonly="readonly">
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
                    <input type="text" class="form-control " name="vote_rank" value=<%=(event_pVO==null)?"":event_pVO.getVote_rank().toString() %> readonly="readonly">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">投稿作品狀態</label>
                <div class="col-sm-3">
                	<input type="hidden" class="form-control" name="event_p_stat" value="${event_pVO.event_p_stat}">
                	<c:choose>
                		<c:when test="${event_pVO.event_p_stat==1}">
                			<input type="text" class="form-control" name="event_p_stat" value="可投票" readonly="readonly">
                		</c:when>
                		<c:otherwise>
                			<input type="text" class="form-control" name="event_p_stat" value="已檢舉" readonly="readonly">
                		</c:otherwise>
                	</c:choose>
<!--                 	<select class="form-control" name="event_p_stat"> -->
<%--                 		<option value="0" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==1?"selected":"" %>>可投票</option> --%>
<%--                 		<option value="1" <%=event_pVO==null?"":event_pVO.getEvent_p_stat()==2?"selected":"" %>>已檢舉</option> --%>
<!--                 	</select> -->
<%--                     <input type="text" class="form-control" name="event_p_stat" value=<%=(event_pVO==null)?"":event_pVO.getEvent_p_stat().toString() %> > --%>
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-2 col-form-label">上傳圖片(待修)</label>
                <div class="col-sm-3">
<%--                    <input type="text" class="form-control " name="event_vote_end" value=<%=(event_pVO==null)?"":event_pVO.getEvent_vote_end().toString() %> > --%>
<!-- 						<input type="file" name="pic01" id="myFile" onchange="upload()"> -->
						<img id="demo" <%=event_pVO==null?"":"src=data:image/jepg;base64,"+request.getAttribute("data") %>>
						
                </div>
            </div>
     
            
			<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
			<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">
			<input type="hidden" name="action" value="update">
			<input type="submit" value="送出" >
	</form>
	
	</div>
						
						<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					
				</div> <!--END OF content-->
				
				<%@include file="/backend/bar/footer.jsp"%>
				
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
	
		<script>
		<!--JavaScript內容-->
		var img2=document.getElementById("demo2");
		var myFile;

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
		</script>
						
	</body>
	
</html>