<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.event_p.model.*"%>
<%@ page import="com.event.model.*" %>
<%@ page import="java.util.*"%>
<%
	EventService eventSvc=new EventService();//用於顯示主題名稱
	Event_PService pSvc = new Event_PService();
	List<Event_PVO> event_pVOs = pSvc.finAllPic();
	pageContext.setAttribute("event_pVOs",event_pVOs);
	HttpSession sessHomePage=request.getSession();
	sessHomePage.setAttribute("event_pVOs",null);//當從event_spec跳回主頁時，將設定成null再走回spec可以進入判斷
	List<Event_PVO> event_p_tops=new ArrayList<Event_PVO>();
	List<String> event_nos=pSvc.findAllEventNo();
	
	Iterator iter=event_nos.iterator();
	//取得每次比賽的Top
	while(iter.hasNext()){
		String  event_no=(String)iter.next();
		Event_PVO event_pVO=null;

		event_pVO=pSvc.findTopByEventNoWithoutReport(event_no);
		
		event_p_tops.add(event_pVO);

	}
	//取得每次比賽的名子
	List<String> event_names=new ArrayList<String>();
	List<EventVO> eventVOs=new ArrayList<EventVO>();
	Iterator<String> event_nosIter=event_nos.iterator();
	while(event_nosIter.hasNext()){
// 		  String event_name=eventSvc.findByPrimaryKey(event_nosIter.next()).getEvent_name() ;
		  EventVO eventVO=eventSvc.findByPrimaryKey(event_nosIter.next());
// 		  event_names.add(event_name);
		  eventVOs.add(eventVO);
	}
	pageContext.setAttribute("event_p_tops",event_p_tops);
	pageContext.setAttribute("event_nos",event_nos);
// 	pageContext.setAttribute("event_names",event_names);
	pageContext.setAttribute("eventVOs",eventVOs);
	
	//每個活動五張縮圖
	
%>

<%-- <c:if test="${not empty errMsgs}"> --%>
<!-- 	<font style="color: red">請修正以下錯誤:</font> -->
<!-- 	<ul> -->
<%-- 		<c:forEach var="message" items="${errMsgs}"> --%>
<%-- 			<li style="color: red">${message}</li> --%>
<%-- 		</c:forEach> --%>
<!-- 	</ul> -->
<%-- </c:if> --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link href="<%=request.getContextPath()%>/frontend/template/neoTools/sweetAlert/sweetalert.css">
<title>event Home Page</title>
<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
.item>#carouselCard{
	min-width:512px;
	margin-right:-100%;
	margin-left: 30% ;
	max-width:512px;
}
#carouselspace{
	margin-top:8%;
}
.carousel .item {
background-color:#fff;
}
.media>.media-left>a>img{
	width:300px;
}
/* #blog-carousel>.carousel-inner>.item{ */
/* 	width: 500px; */
/* 	height:500px; */
/* } */


</style>


</head>
<body >
<div id="top"></div>
<%@include  file="/frontend/bar/frontBarTop.jsp"%>
	


<div class="container " id="carouselspace">
	<div class="row">
	<div class="col-12">
	<div id="blog-carousel" class="carousel slide " data-ride="carousel">
	  <div class="carousel-inner" role="listbox">
	  	<c:forEach var="event_pVO" items="${event_pVOs}" varStatus="status">
	  		<c:choose>
	  			<c:when test="${status.index==0}">
	  				 <div class="item active">
	     				 <img  onerror="this.src='<%=request.getContextPath()%>/frontend/template/images/logo2.png'" src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${event_pVO.event_p_no}" class="d-block " alt="..." id="carouselCard" height="500">
	   				 </div>
	  			</c:when>
	  			<c:otherwise>
	  				<div class="item">
	     				 <img src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${event_pVO.event_p_no}" class="d-block " alt="..." id="carouselCard" height="500">
	    			</div>
	  			</c:otherwise>
	  		</c:choose>
	  	</c:forEach>
	  </div>
         
         <a class="left carousel-control" href="#blog-carousel" role="button" data-slide="prev">
             <span class="lnr lnr-chevron-left" aria-hidden="true"></span>
         </a>
         <a class="right carousel-control" href="#blog-carousel" role="button" data-slide="next">
             <span class="lnr lnr-chevron-right" aria-hidden="true"></span>
         </a>
        
	</div>
	</div>
	</div>
</div>
<div class="container" >
<!--               <div class="container text-center "> -->
<!--             </div> -->
			 <div class="media">
                <div class="media-left">
                		<a href="<%=request.getContextPath() %>/frontend/event_p/Event_pServlet?event_no=${event_nos[status.index]}&action=to_event_sepc"> 
 							<img src="<%=request.getContextPath()%>/frontend/template/images/logo.png" width="150" height="150	">
                        </a>
                </div>
                <!-- media-alignment -->
                            <div class="media-body">
                                <h4 class="media-heading">我有點子</h4>
                                <small>如果你有點子可以在這裡投稿，就有機會抽選為下次的競賽主題</small>
                                <p>
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"  >
					 我有點子
					</button>
					<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
					  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					          <span aria-hidden="true">&times;</span>
					        </button>
					      </div>
					      <div class="modal-body">
										<%@include file="TestTagInsert.jsp" %>
					      </div>
					      <div class="modal-footer">
					      </div>
					    </div>
					  </div>
					</div>
                                </p>
                            </div><!-- / media-body -->
                <!-- / media-body -->
            </div>
            <!-- / media -->
            <% int i=0; %><!-- 紀錄是第幾個index -->
<            <c:forEach var="event_p_top" items="${event_p_tops}" varStatus="status">
            <hr class="featurette-divider">
             <div class="media">
                <div class="media-left">
                		<a href="<%=request.getContextPath() %>/frontend/event_p/Event_pServlet?event_no=${event_nos[status.index]}&action=to_event_sepc"> 
                			<c:choose>
	                			<c:when test="${not empty  event_p_top.event_p_no}">
	                				<img alt="" src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${event_p_top.event_p_no}" width="300" height="300">
	                			</c:when>
	                			<c:otherwise>
	                				<img alt="" src="<%=request.getContextPath()%>/frontend/template/images/logo2.png" width="300" height="300">
	                			</c:otherwise>
							</c:choose>
                        </a>
                </div>
                <!-- media-alignment -->
                <% 
                	String[] event_statStr={"活動尚未開始","徵稿中","投票中","活動結束"};
                	pageContext.setAttribute("event_statStr", event_statStr);
                %>
                <div class="media-body">
                	<h3>活動名稱:${eventVOs[status.index].event_name} (${event_statStr[eventVOs[status.index].event_stat]})</h3>
                	
                	<c:if test="${eventVOs[status.index].event_stat==3}">
                		<h6 class="media-heading" >得票最高的作品編號:${event_p_top.event_p_no}</h6>
                	</c:if>
                    <p>
<!--                     	本次活動五張圖event_pVOs(五張圖的event_pVO5s) -->
<%--                     	<%=i %> --%>
<%--                     	<%=event_nos.get(i)%> --%>
                    	<% 
                    		List<Event_PVO> event_pVO5s=pSvc.find5PicByEventNo(event_nos.get(i));
                    		pageContext.setAttribute("event_pVO5s", event_pVO5s);
                    	%>
                    	<div class="row">
	                    		<c:forEach var="event_pVO5" items="${event_pVO5s}">
	                    			<div class="col-sm-2">
	                    				<img onerror="this.src='<%=request.getContextPath()%>/frontend/template/images/logo2.png'" src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${event_pVO5.event_p_no}" style="width:80px;height:80px">
	                    			</div>	
	                    		</c:forEach>
                    	</div>
                    	<c:if test="${eventVOs[status.index].event_stat==1}"><!-- 狀態為投稿藍色 -->
							<form action="<%=request.getContextPath()%>/frontend/event_p/Event_pServlet" method="post">
	                    		<input type="submit" class="btn btn-info" value="去投稿" style="margin-left:30px;"><!-- 投稿藍色，投票土黃色 -->
	                    		<input type="hidden" name="event_no" value="${event_nos[status.index]}" >
	                    		<input type="hidden" name="action" value="to_event_sepc">
                    		</form>
                    	</c:if>
                    	<c:if test="${eventVOs[status.index].event_stat==2}"><!-- 狀態為投票土黃色-->
                    		<form action="<%=request.getContextPath()%>/frontend/event_p/Event_pServlet" method="post">
	                    		<input type="submit" class="btn btn-warning" value="去投票" style="margin-left:30px;"><!-- 投票土黃色 -->
	                    		<input type="hidden" name="event_no" value="${event_nos[status.index]}" >
	                    		<input type="hidden" name="action" value="to_event_sepc">
                    		</form>

                    	</c:if>
                    </p>
                </div>
                <!-- / media-body -->
            </div>
            <!-- / media -->
            <%i++;%>
			</c:forEach>

</div>
	
<!-- scroll to top -->
<a href="#top" class="scroll-to-top page-scroll is-hidden" data-nav-status="toggle"><em class="fa fa-angle-up"></em></a>
<!-- / scroll to top -->
<%@include file="/frontend/bar/frontBarFooter.jsp" %>

    <script src="<%=request.getContextPath() %>/frontend/template/js/jquery.min.js" ></script>
    
<!-- mark by YCL 重複引入導致modal跑不出來     -->
<%-- 	<script src="<%=request.getContextPath() %>/frontend/template/js/bootstrap.min.js" ></script> --%>
	<script src="<%=request.getContextPath()%>/frontend/template/js/jquery.easing.min.js"></script><!-- return to top id -->
	
<%-- 	<script src="<%=request.getContextPath()%>/frontend/neoTools/sweetAlert/polyfills.js"></script> --%>
	<script src="<%=request.getContextPath()%>/frontend/template/neoTools/sweetAlert/sweetalert.min.js"></script>
	<script>
	$("#eventHashTagSubmit").click(function(){
		console.log($("#tagName").val()==="");
		if($("#tagName").val().trim()===""){
			swal("請輸入文字");
		}else{
			$.ajax({
				url:"<%=request.getContextPath()%>/Event_TagServlet",
				type:"post",
				data:{
					action:"insert",
					event_tag_name:$("#tagName").val()
					},
				success:function(data){
					console.log("OK");
					$("#tagName").val("") //clear text
					swal("Nice!", "點子送出完成", "success");
				}
			});
		}

	})
	
	</script>
</body>
</html>