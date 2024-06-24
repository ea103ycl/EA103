<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Minimal Shop Theme">
<meta name="keywords"
	content="responsive, retina ready, html5, css3, shop, market, onli store, bootstrap theme" />
<meta name="author" content="KingStudio.ro">

<!-- favicon -->
<link rel="icon" href="images/favicon.png">
<!-- page title -->
<title>MS - Minimal Shop Theme</title>
<!-- bootstrap css -->
<link
	href="<%=request.getContextPath()%>/frontend/template/css/bootstrap.min.css"
	rel="stylesheet">
<!-- css -->
<link
	href="<%=request.getContextPath()%>/frontend/template/css/style.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/frontend/template/css/animate.css"
	rel="stylesheet">
<!-- fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Rubik:400,500,700,900"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Lily+Script+One"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/frontend/template/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link
	href='<%=request.getContextPath()%>/frontend/template/fonts/FontAwesome.otf'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/frontend/template/css/linear-icons.css">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

</head>

<body>

	<jsp:useBean id="bdr" scope="application"
		class="com.bidding.model.BdRedis" />
	<jsp:useBean id="bdSvc" scope="page"
		class="com.bidding.model.BiddingService" />
	<jsp:useBean id="epSvc" scope="page"
		class="com.event_p.model.Event_PService" />

	<c:if test="${empty event_no}">
		<c:set var="event_no" value="E000001" scope="application" />
	</c:if>
	
	<c:set var="bdNo" value="${event_no}" scope="application" />
	<c:set var="epVO" value="${epSvc.findTopByEventNoWithoutReport(bdNo)}" />
	<c:set var="sqlBdNo" value="${bdr.getSqlBdNo(bdNo)}" />${sqlBdNo}
	<c:set var="bVO" value="${bdSvc.getOne(sqlBdNo)}" />${bVO.bdNo}
	<c:set var="topBidder" value="${bdr.getHighestBidder(bdNo)}" />
	<input type="hidden" value="<%=request.getContextPath()%>" name="contextPath" id="contextPath">
	<form id="enterBidPage" style="display: hidden"
		action="<%=request.getContextPath()%>/biddingPage/BdPageServlet">
		<input type="text" id="bdNo" name="bdNo" value="${bdNo}" required><br> <input
			type="hidden" name="action" value="setBidInfo">
	</form>

	<!-- preloader -->
	<div id="preloader">
		<div class="spinner spinner-round"></div>
	</div>
	<!-- / preloader -->

	<%@include file="/frontend/bar/frontBarTop.jsp"%>

	<div id="top"></div>

	<!-- header -->
	<header>
		<div class="space-50">&nbsp;</div>
		<div class="space-50">&nbsp;</div>



		<!-- content -->

		<div id="page-content" class="container">
			<div class="section-w-image space-top-2x">
				<div class="row biddingPage"
					style="background-color: #f8f8f8; cursor: pointer">
					<h1 style="display: none;">${event_no}</h1>
					<span style="display: none;">${event_no}</span>
					<div class="col-sm-7 ">
						<img
							onerror="this.onerror=null; this.src='<%=request.getContextPath()%>/frontend/front_index/img/img (1).jpg'"
							src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${epVO.event_p_no}">
					</div>
					<div class="col-sm-5 text-center space-top-2x" style="">
						<span><h2>Up-running Event!</h2>
							<p>Come Join Quick!</p>
							<p>Start Date:</p>${bdr.bdStartTime}</span>
					</div>
				</div>
				<!-- / row -->
			</div>
			<!-- / section-w-image -->

			<div class="space-50">&nbsp;</div>
			<section id="team">
				<div class="container">
					<h2 class="text-center space-top-2x" style="margin-bottom: 40px;">Latest
						Event</h2>
					<div class="row">
						<!-- team-block -->
						<div class="col-sm-4 biddingPage" id="latestBd1">
							<span style="display: none;">${latestBd1}</span>
							<c:set var="latestEpVO1"
								value="${epSvc.findTopByEventNoWithoutReport(latestBd1)}" />
							<div class="team block text-center" style="cursor: pointer;">
								<img
									onerror="this.onerror=null; this.src='<%=request.getContextPath()%>/frontend/front_index/img/img (2).jpg'"
									src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${latestEpVO1.event_p_no}">
								<div class="team-info-box">
									<h6>${memSvc.findByPrimaryKey(latestEpVO1.mem_id).m_name}</h6>
									<p class="team-role">${latestEpVO1.event_p_name}</p>
									<p class="social text-center">
										<a href="#"><em class="fa fa-facebook"></em></a> <a href="#"><i
											class="fa fa-twitter"></em></a> <a href="#"><i
											class="fa fa-google-plus"></em></a>
									</p>
								</div>
								<!-- / team-info-box -->
							</div>
							<!-- / team-block -->
						</div>
						<!-- / col-sm-4 -->
						<!-- / team-block -->

						<!-- team-block -->
						<div class="col-sm-4 biddingPage" id="latestBd2">
							<span style="display: none;">${latestBd2}</span>
							<c:set var="latestEpVO2"
								value="${epSvc.findTopByEventNoWithoutReport(latestBd2)}" />
							<div class="team block text-center" style="cursor: pointer;">
								<img
									onerror="this.onerror=null; this.src='<%=request.getContextPath()%>/frontend/front_index/img/img (3).jpg'"
									src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${latestEpVO2.event_p_no}">
								<div class="team-info-box">
									<h6>${memSvc.findByPrimaryKey(latestEpVO2.mem_id).m_name}</h6>
									<p class="team-role">${latestEpVO2.event_p_name}</p>
									<p class="social text-center">
										<a href="#"><em class="fa fa-facebook"></em></a> <a href="#"><i
											class="fa fa-twitter"></em></a> <a href="#"><i
											class="fa fa-google-plus"></em></a>
									</p>
								</div>
								<!-- / team-info-box -->
							</div>
							<!-- / team-block -->
						</div>
						<!-- / col-sm-4 -->
						<!-- / team-block -->

						<!-- team-block -->
						<div class="col-sm-4 biddingPage" id="latestBd3">
							<span style="display: none;">${latestBd3}</span>
							<c:set var="latestEpVO3"
								value="${epSvc.findTopByEventNoWithoutReport(latestBd3)}" />
							<div class="team block text-center" style="cursor: pointer;">
								<img
									onerror="this.onerror=null; this.src='<%=request.getContextPath()%>/frontend/front_index/img/img (4).jpg'"
									src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${latestEpVO3.event_p_no}">
								<div class="team-info-box">
									<h6>${memSvc.findByPrimaryKey(latestEpVO3.mem_id).m_name}</h6>
									<p class="team-role">${latestEpVO3.event_p_name}</p>
									<p class="social text-center">
										<a href="#"><em class="fa fa-facebook"></em></a> <a href="#"><i
											class="fa fa-twitter"></em></a> <a href="#"><i
											class="fa fa-google-plus"></em></a>
									</p>
								</div>
								<!-- / team-info-box -->
							</div>
							<!-- / team-block -->
						</div>
						<!-- / col-sm-4 -->
						<!-- / team-block -->

					</div>
					<!-- / row -->
				</div>
				<!-- / container -->
			</section>
		</div>
		<!-- / container -->

		<!-- / content -->

		<!-- scroll to top -->
		<a href="#top" class="scroll-to-top page-scroll is-hidden"
			data-nav-status="toggle"><em class="fa fa-angle-up"></em></a>
		<!-- / scroll to top -->

		<!-- footer -->
		<footer class="light-footer">
			<div class="widget-area">
				<div class="container">
					<div class="row">

						<div class="col-md-4 widget">
							<div class="about-widget">
								<div class="widget-title-image">
									<img
										src="<%=request.getContextPath()%>/frontend/template/images/logo2.png"
										alt="">
								</div>
								<p>Vivamus consequat lacus quam, nec egestas quam egestas
									sit amet. Suspendisse et risus gravida tellus aliquam
									ullamcorper. Pellentesque elit dolor, ornare ut lorem nec,
									convallis nibh accumsan lacus morbi leo lipsum.</p>
							</div>
							<!-- / about-widget -->
						</div>
						<!-- / widget -->
						<!-- / first widget -->

						<div class="col-md-2 widget">
							<div class="widget-title">
								<h4>BRANDS</h4>
							</div>
							<div class="link-widget">
								<div class="info">
									<a href="#x">Brand 1</a>
								</div>
								<div class="info">
									<a href="#x">Brand 2</a>
								</div>
								<div class="info">
									<a href="#x">Brand 3</a>
								</div>
								<div class="info">
									<a href="#x">Brand 4</a>
								</div>
							</div>
						</div>
						<!-- / widget -->
						<!-- / second widget -->

						<div class="col-md-2 widget">
							<div class="widget-title">
								<h4>SUPPORT</h4>
							</div>
							<div class="link-widget">
								<div class="info">
									<a href="#x">Terms & Conditions</a>
								</div>
								<div class="info">
									<a href="#x">Shipping & Return</a>
								</div>
								<div class="info">
									<a href="faq.html">F.A.Q</a>
								</div>
								<div class="info">
									<a href="contact.html">Contact</a>
								</div>
							</div>
						</div>
						<!-- / widget -->
						<!-- / third widget -->

						<div class="col-md-4 widget">
							<div class="widget-title">
								<h4>CONTACT</h4>
							</div>
							<div class="contact-widget">
								<div class="info">
									<p>
										<em class="lnr lnr-map-marker"></em><span>Miami, S Miami
											Ave, SW 20th, Store No.1</span>
									</p>
								</div>
								<div class="info">
									<a href="tel:+0123456789"><em class="lnr lnr-phone-handset"></em><span>+0123
											456 789</span></a>
								</div>
								<div class="info">
									<a href="mailto:hello@yoursite.com"><i
										class="lnr lnr-envelope"></em><span>office@yoursite.com</span></a>
								</div>
								<div class="info">
									<em class="lnr lnr-thumbs-up"></em> <span
										class="social text-left"> <a class="no-margin" href="#"><i
											class="fa fa-facebook"></em></a> <a href="#"><i
											class="fa fa-twitter"></em></a> <a href="#"><i
											class="fa fa-google-plus"></em></a> <a href="#"><i
											class="fa fa-linkedin"></em></a> <a href="#"><i
											class="fa fa-pinterest"></em></a>
									</span>
								</div>
							</div>
							<!-- / contact-widget -->
						</div>
						<!-- / widget -->
						<!-- / fourth widget -->

					</div>
					<!-- / row -->
				</div>
				<!-- / container -->
			</div>
			<!-- / widget-area -->
			<div class="footer-info">
				<div class="container">
					<div class="pull-left copyright">
						<p>
							<strong>Â© MS - MINIMAL SHOP THEME</strong>
						</p>
					</div>
					<span class="pull-right"> <img
						src="<%=request.getContextPath()%>/frontend/template/images/visa.png"
						alt=""> <img
						src="<%=request.getContextPath()%>/frontend/template/images/mastercard.png"
						alt=""> <img
						src="<%=request.getContextPath()%>/frontend/template/images/discover.png"
						alt=""> <img
						src="<%=request.getContextPath()%>/frontend/template/images/paypal.png"
						alt="">
					</span>
				</div>
				<!-- / container -->
			</div>
			<!-- / footer-info -->
		</footer>
		<!-- / footer -->
		<%@include file="/frontend/bar/frontBarFooter.jsp"%>
		<!-- javascript -->
		<script
			src="<%=request.getContextPath()%>/frontend/template/js/jquery.min.js"></script>
		<script
			src="<%=request.getContextPath()%>/frontend/template/js/bootstrap.min.js"></script>

		<!-- sticky nav -->
		<script
			src="<%=request.getContextPath()%>/frontend/template/js/jquery.easing.min.js"></script>
		<script
			src="<%=request.getContextPath()%>/frontend/template/js/scrolling-nav-sticky.js"></script>
		<!-- / sticky nav -->

		<!-- hide nav -->
		<script
			src="<%=request.getContextPath()%>/frontend/template/js/hide-nav.js"></script>
		<!-- / hide nav -->

		<!-- preloader -->
		<script
			src="<%=request.getContextPath()%>/frontend/template/js/preloader.js"></script>
		<!-- / preloader -->

		<!-- / javascript -->
</body>

<script>
	$('.biddingPage').on("click", function(e) {
		var bdNo = $(this).children('span').html();
		if (bdNo.length === 0) {
			return;
		}
		$('#enterBidPage').children('#bdNo').val(bdNo);
		$('#enterBidPage').submit();

	})
</script>
</html>