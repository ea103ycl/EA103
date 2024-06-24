<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.event_p.model.*"%>
<%@ page import="java.util.*"%>
<%
	Event_PService svc = new Event_PService();
	List<Event_PVO> event_pVOs = svc.finAllPic();
	pageContext.setAttribute("event_pVOs",event_pVOs);
	
	List<Event_PVO> event_p_tops=new ArrayList<Event_PVO>();
	List<String> event_nos=svc.findAllEventNo();
	
	Iterator iter=event_nos.iterator();
	
	while(iter.hasNext()){
		String  event_no=(String)iter.next();
		Event_PVO event_pVO=null;

		event_pVO=svc.findTopByEventNoWithoutReport(event_no);
		
		event_p_tops.add(event_pVO);

	}
	pageContext.setAttribute("event_p_tops",event_p_tops);
	pageContext.setAttribute("event_nos",event_nos);
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
<!-- Bootstrap CSS -->
<!-- <link rel="stylesheet" type="text/css" -->
<%-- 	href="<%=request.getContextPath()%>/frontend/template/css/bootstrap.min.css"> --%>
<!-- <link -->
<%-- 	href="<%=request.getContextPath()%>/frontend/template/css/carousel.css" --%>
<!-- 	rel="stylesheet"> -->
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
<body>
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
	     				 <img src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${event_pVO.event_p_no}" class="d-block " alt="..." id="carouselCard" height="500">
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

         <!-- controls -->
         <a class="left carousel-control" href="#blog-carousel" role="button" data-slide="prev">
             <span class="lnr lnr-chevron-left" aria-hidden="true"></span>
         </a>
         <a class="right carousel-control" href="#blog-carousel" role="button" data-slide="next">
             <span class="lnr lnr-chevron-right" aria-hidden="true"></span>
         </a>
         <!-- / controls -->
	</div>
	</div>
	</div>
</div>
<div class="container">

<!-- 			<button type="button" class="btn btn-primary btn-rounded">Rounded</button> -->

              <div class="container text-center ">
					
				

                <p>
                <!-- Button trigger modal -->
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"  >
					 我有點子
					</button>
					
					
					<!-- Modal -->
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
					</div><!-- Modal end-->
							
                </p>
            </div>

            
            <c:forEach var="event_p_top" items="${event_p_tops}" varStatus="status">
<!--             <hr class="featurette-divider"> -->
             <div class="media">
                <div class="media-left">
                		<a href="<%=request.getContextPath() %>/frontend/event_p/Event_pServlet?event_no=${event_nos[status.index]}&action=to_event_sepc"> 
                			<img alt="" src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${event_p_top.event_p_no}" width="300" height="300">
                        </a>
                </div>
                <!-- media-alignment -->
                <div class="media-body">
                    <h4 class="media-heading">得票最高的作品編號:${event_p_top.event_p_no}</h4>
                    <p>Etiam pharetra pellentesque odio, id porttitor augue varius vel. Phasellus ut dui sit amet justo ullamcorper volutpat non at diam. Nunc a sapien et dui convallis pulvinar. Aenean nec molestie tortor, ut porttitor arcu. Cras eu lectus ultrices, tristique nisi ac, viverra diam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In sit amet dictum nibh. Duis sem purus, mollis nec dictum eget, pellentesque at quam. Donec metus mi, ornare eu consequat sit amet, hendrerit tincidunt lacus. Donec dignissim dolor lorem, a gravida enim convallis vitae. Donec orci metus, sodales a diam ut, maximus posuere odio.</p>
                </div>
                <!-- / media-body -->
            </div>
            <!-- / media -->
            
<!--             <div class="row featurette"> -->
                
<!--                 <div class="col-md-7 col-md-push-5"> -->
<!--                     <h2 class="featurette-heading">Oh yeah, it’s that good. <span class="text-muted">See for yourself.</span></h2> -->
<!--                    <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p> -->
<!--                 </div> -->
<!--                 <div class="col-md-5 col-md-pull-7"> -->
<!--                 		每次event的第一名 -->
                		
<!-- 						<div> -->
<%-- 							${event_p_top.event_p_no} --%>
<%-- 							${event_nos[status.index]}//for display all event_no --%>
<!-- 						</div> -->

<%--                 		<a href="<%=request.getContextPath() %>/frontend/event_p/Event_pServlet?event_no=${event_nos[status.index]}&action=to_event_sepc">  --%>
<%--                 			<img alt="" src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${event_p_top.event_p_no}" width="500" height="500"> --%>
<!--                         </a> -->
<!--                         <title>Placeholder</title> -->
<!--                         <rect width="100%" height="100%" fill="#eee" /><text x="50%" y="50%" fill="#aaa" dy=".3em">500x500</text> -->
<!--                 </div> -->
<!--             </div> -->
                   
            </c:forEach>


</div>
	
<!-- scroll to top -->
<a href="#top" class="scroll-to-top page-scroll is-hidden" data-nav-status="toggle"><em class="fa fa-angle-up"></em></a>
<!-- / scroll to top -->
<%@include file="/frontend/bar/frontBarFooter.jsp" %>

    <script src="<%=request.getContextPath() %>/frontend/template/jquery/jquery.min.js" ></script>
	<script src="<%=request.getContextPath() %>/frontend/template/js/bootstrap.min.js" ></script>
	<script src="<%=request.getContextPath()%>/frontend/template/js/jquery.easing.min.js"></script><!-- return to top id -->
	
</body>
</html>