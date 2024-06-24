<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<title>front_index</title>

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">
<!-- Vendor CSS Files -->
<link
	href="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/venobox/venobox.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/owl.carousel/assets/owl.carousel.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/aos/aos.css"
	rel="stylesheet">
<!-- Template Main CSS File -->
<link
	href="<%=request.getContextPath()%>/frontend/front_index/assets/css/style.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/frontend/front_index/css/masonry.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/frontend/front_index/css/index_front_css.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/frontend/template/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<!-- =======================================================
  * Template Name: iPortfolio - v1.4.0
  * Template URL: https://bootstrapmade.com/iportfolio-bootstrap-portfolio-websites-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
</head>

<body>

	<jsp:useBean id="pSvc" scope="page"
		class="com.painter.model.PainterService" />
	<jsp:useBean id="ptSvc" scope="page"
		class="com.painter_tag.model.PainterTagService" />
	<jsp:useBean id="ptmSvc" scope="page"
		class="com.painter_tag_map.model.PainterTagMapService" />
	<jsp:useBean id="ptmsgSvc" scope="page"
		class="com.painter_msg.model.PainterMsgService" />

	<input type="hidden" value="<%=request.getContextPath()%>"
		id="contextPath">
	<c:if test="${not empty ptrnoList}">
		<input type="hidden" id="ptrnoList" value="hasPtrnoList">
	</c:if>
		

	<!-- ======= Mobile nav toggle button ======= -->
	<button type="button" class="mobile-nav-toggle d-xl-none">
		<em class="icofont-navigation-menu"></em>
	</button>
	<!-- ======= Header ======= -->
	<header id="header"> </header>
	<!-- End Header -->
	<main id="main">
		<!-- ======= Breadcrumbs ======= -->
		<section class="breadcrumbs">
			<div class="container">
				<div class="d-flex justify-content-between align-items-center">
					<h2 style="color: #ad8b60; margin: 0 0 0 -3px;">ArtsBlock</h2>

					<ol>
						<c:choose>
							<c:when test="${empty memVO}">
								<li><a class="mya"
									href="<%=request.getContextPath()%>/frontend/members/memLogin.jsp">Login
										&nbsp</li>/
								<li><a class="mya"
									href="<%=request.getContextPath()%>/frontend/members/memRegister.jsp">&nbsp
										Register</a></li>
							</c:when>
							<c:otherwise>
								<li></li>
								<p>
								<li><a class="mya"
									href="<%=request.getContextPath()%>/frontend/members/memLoginHandler.do?action=logout">Logout
										&nbsp</li>/ &nbsp
								<li><a class="mya"
									href="<%=request.getContextPath()%>/frontend/members/memArea.jsp"
									hover=""><em class="fa fa-user" aria-hidden="true"></em></a></li>
								</p>
							</c:otherwise>
						</c:choose>
					</ol>
				</div>
			</div>
		</section>
		<!-- End Breadcrumbs -->
		<section class="inner-page innerpage" style="padding-top: 30px;">
			<div id=topWrapper>
				<div id=fixedTop>
					<div id="logoBlock">
						<div id="leftHeader">
							<div class="LH-inner">
								<a
									href="<%=request.getContextPath()%>/frontend/painter/listAllPainterFollow.jsp"><span>社群</span></a>
							</div>
							<div class="LH-inner">
								<a href="<%=request.getContextPath()%>/frontend/shop/EShop.jsp"><span>商城</span></a>
							</div>
							<div class="LH-inner">
								<a
									href="<%=request.getContextPath()%>/frontend/event_p/event_homePage.jsp"><span>主題競賽</span></a>
							</div>
						</div>
						<div id="logo">
							<div id="title1">ARTS</div>
							<div id="title2">BLOCK</div>
						</div>
						<div id="rightHeader">
							<div class="RH-inner">
								<a
									href="<%=request.getContextPath()%>/frontend/preproduct/frontindex.jsp"><span>預購</span></a>
							</div>
							<div class="RH-inner">
								<a
									href="<%=request.getContextPath()%>/frontend/biddingFront/biddingIndex.jsp"><span>競標</span></a>
							</div>
							<div class="RH-inner">
								<a href="<%=request.getContextPath()%>/frontend/meeting/listAllMeeting_front.jsp"><span>見面會</span></a>
							</div>
						</div>
					</div>
					<!-- ====searchBlcok================== -->
					<div id="searchBlock" ondrop="drop(event)"
						ondragover="allowDrop(event)">
						<h2 id="searchBlock-icon"
							style="display: none; color: #ad8b60; margin: 0 0 0 -3px;">ArtsBlock</h2>
						<div id="searchBlockMid">
							<form id="searchForm1" method="post"
								action="<%=request.getContextPath()%>/painter/TagGetPic"
								name="searchForm">
								<div>
									<input type="hidden" name="action" value="tagGetPic"> <input
										id="searchBar1" type="text" name="searchBar1"
										placeholder="Search">
								</div>
							</form>
						</div>
					</div>
					<!-- ====searchBlcok================== -->
				</div>
			</div>
			<!-- FixedTopEnd -->
			<div class=" container my-container">
				<!-- ================================= -->
				<!-- FixedTop -->
				<!-- ================================================================================================================ -->
				<div id="bubbleWrapper">
					<div class="bubble" id="bubble1">
						<span></span>
					</div>
					<div class="bubble" id="bubble2">
						<span></span>
					</div>
					<div class="bubble" id="bubble3">
						<span></span>
					</div>
					<div class="bubble" id="bubble4">
						<span></span>
					</div>
					<div class="bubble" id="bubble5">
						<span></span>
					</div>
					<div class="bubble" id="bubble6">
						<span></span>
					</div>
					<div class="bubble" id="bubble7">
						<span></span>
					</div>
					<div class="bubble" id="bubble8">
						<span></span>
					</div>
					<div class="bubble" id="bubble9">
						<span></span>
					</div>
					<div class="bubble" id="bubble10">
						<span></span>
					</div>
					<div class="bubble" id="bubble11">
						<span></span>
					</div>
					<div class="bubble" id="bubble12">
						<span></span>
					</div>
					<div id="BigWhiteDot"></div>
				</div>

				<!-- The Modal -->

				<div id="myModal" class="modal">
					<span class="close">&times;</span>
					<div class="myContainer container">
						<div class="row" id="modal-content">
							<div class="modal-left col-md-8" style="padding: 0">
								<a id="img01-a" href=""> <img class="modal-content" id="img01"
									style="border: 0; border-radius: 25px 0 0 25px">
								</a>
							</div>

							<div class=" modal-right col-md-4">

								<c:choose>
									<c:when test="${not empty memVO}">
										<div id="msg-content"></div>
										<div class="comment-container">
											<div class="comment-body">
												<hr style="margin-top: 0";>
												<h5 class="comment-title">Add a new comment</h5>
												<div style="margin-top: 5%; text-align: center;">
													<form id="writeComment"
														action="<%=request.getContextPath()%>/painter/TagGetPic">
														<input name="action" value="writeComment" type="hidden">
														<img
															src="<%=request.getContextPath()%>/ReadMemPic?action=getPic&memId=${memVO.mem_id}"
															class="comment-avatar" alt="avatar"> <input
															id="commentInput" name="comment" class="comment-textarea"
															placeholder="Type your message ..." rows="8">
														<div class="comment-post">
															<div style="text-align: right;">
																<button class="comment-send">Post Comment</button>
															</div>
														</div>
													</form>
												</div>
									</c:when>
									<c:otherwise>
										<div id="msg-content" style="height: calc(100% - 64px)"></div>
										<div class="comment-container">
											<div class="comment-body">
												<hr style="margin-top: 0";>
												<a
													href="<%=request.getContextPath()%>/frontend/members/memLogin.jsp">
													<h6 class="loginToComment">Login to comment</h6>
												</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</div>
			</div>
			<!-- The Modal -->


			<%-- ============================================= --%>
			<c:if test="${not empty errorMsgs}">
				<br>
				<div class="errorMsgs" id="errorMsgs">
					<h2>${errorMsgs}</h2>
				</div>
				<hr>
			</c:if>
			<c:choose>
				<c:when test="${not empty ptrnoList}">
					<div style="display: hidden" id="getPicResult"></div>
					<div class="gridWrapper">
						<div class="grid" id="grid">
							<c:forEach var="ptrno" items="${ptrnoList}">
								<div class="grid-item draggableImg">
									<img draggable='true' ondragstart="drag(event);" id='${ptrno}'
										src="<%=request.getContextPath()%>/painter/getOrigPtr.do?ptr_no=${ptrno}">
								</div>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<%-- ==============Top100Liked===================== --%>
				<c:otherwise>
					<div class="gridWrapper">
						<div class="grid" id="grid">
							<c:forEach var="pVO" items="${pSvc.getMostLiked(1,15)}">
								<div class="grid-item">
									<img draggable='true' ondragstart="drag(event);"
										id='${pVO.ptr_no}'
										src="<%=request.getContextPath()%>/painter/getOrigPtr.do?ptr_no=${pVO.ptr_no}">
								</div>
							</c:forEach>
						</div>
					</div>
					
				</c:otherwise>
			</c:choose>




			<!-- ================================= -->
			</div>
		</section>
		<div  style="height:200px;"></div>
		<div id="lastDiv"></div>
	</main>
	<!-- End #main -->
	<a href="#" class="back-to-top"><img
		src="<%=request.getContextPath()%>/frontend/front_index/assets/img/up-chevron-white.svg"></a>
	<!-- Vendor JS Files -->
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/jquery/jquery.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/jquery.easing/jquery.easing.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/php-email-form/validate.js"></script>
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/waypoints/jquery.waypoints.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/counterup/counterup.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/isotope-layout/isotope.pkgd.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/venobox/venobox.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/owl.carousel/owl.carousel.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/typed.js/typed.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/assets/vendor/aos/aos.js"></script>
	<!-- 	<script -->
	<%-- 		src="<%=request.getContextPath()%>/frontend/front_index/js/masonry.js"></script> --%>

	<!-- Template Main JS File -->
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/assets/js/main.js"></script>
	<script
		src="<%=request.getContextPath()%>/frontend/front_index/js/index_front_js.js"></script>


</body>

</html>