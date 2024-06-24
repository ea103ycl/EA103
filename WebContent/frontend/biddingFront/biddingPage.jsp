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
<link rel="icon"
	href="<%=request.getContextPath()%>/frontend/template/images/favicon.png">
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
<style>

button:disabled {
  cursor: not-allowed;
}

.memAvatar {
	width: 8%;
	display: inline-block;
}

.authAvatar {
	width: 30%;
}

img {
	max-width: 80%;
}

.blog.block img {
	margin: auto;
}

.price {
	margin-top: 3%;
}

.authorBlock {
	text-align: center;
}

.post-category h4 {
	display: inline-block;
	margin: -2% 2% 0 5%;
	color: #ad8b60;
}

#winningForm {
	display: none;
}

.modal-dialog {
	margin: auto;
}

#modalWrapper {
	display: none;
}
.topBidderImg {
	width:10%;
}
</style>
</head>

<body>

	<jsp:useBean id="bdr" scope="application"
		class="com.bidding.model.BdRedis" />
	<jsp:useBean id="bdSvc" scope="page"
		class="com.bidding.model.BiddingService" />
	<jsp:useBean id="epSvc" scope="page"
		class="com.event_p.model.Event_PService" />
	<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />


	<!-- this bdNo is eventNo -->
	<input id="bdNo" type="hidden" value="${bdNo}">
	<input id="memId" type="hidden" value="${memVO.mem_id}">
	<input id="memWallet" type="hidden" value="${memVO.balance}">
	<input id="biddingIndexUrl" type="hidden" value="<%=request.getContextPath()%>/frontend/biddingFront/biddingIndex.jsp">
	<input id="contextPath" type="hidden" value="<%=request.getContextPath()%>">
	<c:set var="epVO" value="${epSvc.findTopByEventNoWithoutReport(bdNo)}" />
	<c:set var="memId" value="${memVO.mem_id}" />
	<c:set var="sqlBdNo" value="${bdr.getSqlBdNo(bdNo)}" />
	<c:set var="bVO" value="${bdSvc.getOne(sqlBdNo)}" />
	<c:set var="topBidder" value="${bdr.getHighestBidder(bdNo)}" />



	<!-- preloader -->
	<div id="preloader">
		<div class="spinner spinner-round"></div>
	</div>
	<!-- / preloader -->
	<div id="top"></div>


	<%@include file="/frontend/bar/frontBarTop.jsp"%>

	<!-- header-banner -->
	<div id="header-banner">
		<div class="banner-content single-page text-center">
			<div class="banner-border">
				<div class="banner-info">
					<h1>Bidding Page</h1>
					<p>Welcome! enjoy the bid!</p>
				</div>
				<!-- 					/ banner-info -->
			</div>
			<!-- 				/ banner-border -->
		</div>
		<!-- / banner-content -->
	</div>
	<!-- / header-banner -->

	<!-- content -->

	<div id="page-content" class="container">
		<!-- blog content + sidebar -->
		<section id="blog">
			<div class="row">
				<!-- blog content area -->
				<div class="col-sm-8 col-md-9">
					<div class="blog block post-content-area">
						<div class="post-info-box">
							<img onerror="this.onerror=null; this.src='<%=request.getContextPath()%>/frontend/front_index/img/coming-soon.jpg'"
								src="<%=request.getContextPath()%>/Event_PViewServlet?event_p_no=${epVO.event_p_no}"
								style="max-height: auto; width: 100%" alt="">
							<div class="space-50">&nbsp;</div>
							<p class="lead clockLead">Time remaining:</p>
							<div id="clock">
								<%@include file="/frontend/biddingFront/clock.jsp"%>
							</div>



							<!-- ModalWrapper -->
							<div style="text-align: center;" id="modalWrapper">

								<c:choose>
									<c:when test="${(bVO.bdStatus==0) and (memId eq topBidder)}">
										<h2 Style="margin-top: 2%">
											<a>Congratulations!</a>
										</h2>
										<!-- Trigger the modal with a button -->
										<button id="modalButton" type="button"
											class="btn btn-info btn-lg" data-toggle="modal"
											data-target="#myModal" onclick="getPrice()">Proceed
											></button>

										<!-- Modal -->
										<div class="modal fade" id="myModal" role="dialog">
											<div class="modal-dialog"
												style="width: auto; height: auto; margin: auto">
												<!-- Modal content-->
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal">&times;</button>
														<h4 class="modal-title">CHECKOUT</h4>
													</div>
													<div class="modal-body">
														<p>Congratulations for winning the bid!</p>
														<div class="container">
															<div class="row checkout-screen" id="checkout-screen">
																<div class="col-sm-8 checkout-form">
																	<form
																		action="<%=request.getContextPath()%>/biddingPage/BdPageServlet"
																		id="checkoutForm">
																		<input type="hidden" name="action" value="checkout">
																		<input type="hidden" name="bdNo" value="${bdNo}">
																		<input type="hidden" name="memId" value="${memId}">
																		<input type="hidden" name="bdProdNo" value="3001">
																		<h4 class="space-left">CHECKOUT</h4>
																		<p class="space-left have-account space-bottom">
																			please fill the form for payment <a
																				href="login-register.html"><span>terms</span></a>
																		</p>
																		<div class="row">
																			<div class="col-sm-6">
																				<input type="text" class="form-control"
																					name="bdName" placeholder="*NAME"
																					value="${memVO.m_name}" required=""> <input
																					type="email" class="form-control" name="bdEmail"
																					placeholder="*EMAIL" value="${memVO.m_email}"
																					required="">

																			</div>
																			<div class="col-sm-6">
																				<input type="tel" class="form-control"
																					name="bdPhone" placeholder="*PHONE"
																					value="${memVO.m_phone}" required=""> <input
																					type="text" class="form-control" name="bdAddr"
																					placeholder="*ADDRESS" value="${memVO.m_addr}"
																					required="">
																			</div>
																		</div>
																		<!-- / row -->

																		<div class="row">

																			<div class="col-sm-6">
																				<select class="form-control" name="city">
																					<optgroup label="city:">
																						<option value="s1">Miami</option>
																						<option value="s2">Melbourne</option>
																						<option value="s3">London</option>
																						<option value="s4">Barcelona</option>
																					</optgroup>
																				</select> <input type="text" class="form-control"
																					name="bdZip" placeholder="ZIP CODE" required="">
																			</div>
																		</div>
																		<!-- / row -->

																		<div
																			class="checkout-form-footer space-left space-right">
																			<textarea class="form-control" name="message"
																				placeholder="MESSAGE"></textarea>
																			<a href=""
																				class="btn btn-primary-filled btn-rounded checkout"><i
																				class="lnr lnr-cart"></em><span>Checkout</span></a>
																			<button type="submit" id="checkout"
																				style="display: none;"></button>
																		</div>
																		<!-- / checkout-form-footer -->
																</div>
																<!-- / checkout-form -->
																</form>
																<div class="space-50">&nbsp;</div>
																<div class="col-sm-4 checkout-total">

																	<h4>
																		CART TOTAL: <span id="checkoutPrice">0</span>
																	</h4>
																	<p>*The price includes shipping and taxes.</p>
																	<div class="space-50">&nbsp;</div>
																	<h5>
																		My Wallet: $<span id="myWallet">${memVO.balance}</span>
																	</h5>
																	<p class="blog-post-footer"></p>
																	<h4>
																		Balance: $<span id="balance">${memVO.balance}</span>
																	</h4>

																	<div class="cart-total-footer" style="margin-top: 10%">
																		<a href="" class="btn btn-default-filled btn-rounded"><i
																			class="lnr lnr-arrow-left"></em><span>Back to
																				Page</span></a> <a href="shop-right.html"
																			class="btn btn-primary-filled btn-rounded"><i
																			class="lnr lnr-store"></em><span>Go to Shop</span></a>
																	</div>
																	<!-- / cart-total-footer -->
																</div>
																<!-- / checkout-total -->
																<div class="space-50">&nbsp;</div>
																<div id="errorMsgs"></div>
															</div>
															<!-- / row -->
														</div>
													</div>
													<!-- modal-body -->
													<div class="modal-footer" style="text-align: right;">
														<button type="button" class="btn btn-default"
															data-dismiss="modal" style="margin-right: 4%;">cancel</button>
													</div>
												</div>
												<!-- Modal content-->
											</div>
										</div>
										<!-- Modal -->

									</c:when>
									<c:when test="${memId eq topBidder}">
										<h2 Style="margin-top: 2%">
											<a>查看我的訂單!</a>
										</h2>
										<!-- Trigger the modal with a button -->
										<button id="modalButton" type="button"
											class="btn btn-info btn-lg" data-toggle="modal"
											data-target="#myModal" onclick="getPrice()">Check
											Order ></button>

										<!-- Modal -->
										<div class="modal fade" id="myModal" role="dialog">
											<div class="modal-dialog"
												style="width: auto; height: auto; margin: auto">
												<!-- Modal content-->
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal">&times;</button>
														<h4 class="modal-title">Bidding Order</h4>
													</div>
													<div class="modal-body">
														<p>您的訂單明細!</p>
														<div class="container">

															<div class="row">

																<div class="col-sm-2 account-sidebar">
																	<img src="images/account-img.jpg" alt="">
																	<p id="pscroll1">
																		<a href="javascript:void(0)" class="page-scroll">PERSONAL
																			INFO</a>
																	</p>
																	<p id="pscroll2">
																		<a href="javascript:void(0)" class="page-scroll">SHIPPING
																			INFO</a>
																	</p>
																	<p id="pscroll3">
																		<a href="javascript:void(0)" class="page-scroll">MY
																			ORDERS</a>
																	</p>

																</div>
																<!-- / account-sidebar -->

																<div class="col-sm-10 account-info">
																	<div id="personal-info" class="account-info-content">
																		<h4>
																			PERSONAL INFO <span class="pull-right">
																		</h4>
																		<div class="row">
																			<div class="col-xs-6 col-sm-4 col-md-2">
																				<img src="/G1/frontend/template/img/ghost.svg"
																					alt="">
																			</div>

																			<div class="col-xs-6 col-sm-8 col-md-10">
																				<p>
																					Full Name: <span>${bVO.bdName}</span>
																				</p>
																				<p>
																					Country: <span>台灣</span>
																				</p>
																				<p>
																					Email: <span>${memVO.m_email}</span>
																				</p>
																				<p>
																					Phone: <span>${bVO.bdPhone}</span>
																				</p>
																				<p>
																					Date of Birth: <span>${memVO.m_bday}</span>
																				</p>
																				<p>
																					MyWallet: $ <span>${memVO.balance}</span>
																				</p>

																			</div>

																		</div>
																		<!-- / row -->
																	</div>
																	<!-- / personal-info -->

																	<div id="shipping-info" class="account-info-content">
																		<h4>SHIPPING INFO</h4>
																		<p class="space-bottom" id="shippingEdit">
																			<span><strong>${memVO.m_email}</strong></span>
																		</p>
																		<p>
																			Country: <span>台灣</span>
																		</p>

																		<p>
																			Address Line: <span>${bVO.bdAddr}</span>
																		</p>
																		<p>
																			ZIP Code: <span>${bVO.bdZip}</span>
																		</p>
																		<div id="checkoutEdit" class="account-info-footer">
																			<a href="javascript:void(0)"
																				class="btn btn-sm btn-primary btn-rounded no-margin"><i
																				class="lnr lnr-pencil"></em><span>Edit</span></a>
																		</div>
																	</div>
																	<!-- / shipping-info -->

																	<div id="my-orders" class="account-info-content">
																		<h4>
																			MY ORDERS <span class="pull-right">
																		</h4>
																		<p>
																			<a href="#x">Order #${bVO.bdNo}</a> - Tracking No: <span>#${bVO.bdNo}</span>
																		</p>
																		<p>
																			<a href="#x">Order Date</a> <span>- Completed
																				on ${bVO.bdDateEnd}</span>
																		</p>
																		<p>
																			<a href="#x">Order Status</a>-
																			<c:choose>
																				<c:when test="${bVO.bdOrdStatus==0}">
																					<span>商品處理中</span>
																				</c:when>
																				<c:when test="${bVO.bdOrdStatus==1}">
																					<span>商品理貨中</span>
																				</c:when>
																				<c:when test="${bVO.bdOrdStatus==2}">
																					<span>商品運送中</span>
																				</c:when>
																				<c:when test="${bVO.bdOrdStatus==3}">
																					<span>商品已到貨</span>
																				</c:when>
																				<c:when test="${bVO.bdOrdStatus==4}">
																					<span>訂單已完成</span>
																				</c:when>
																				<c:otherwise>

																				</c:otherwise>
																			</c:choose>

																		</p>
																		<p>
																			<a href="#x">Price: </a> $ <span>${bVO.bdPrice}</span>
																		</p>

																	</div>
																	<!-- / my-orders -->
																</div>
																<!-- / account-info -->

															</div>
															<!-- / row -->

														</div>
													</div>
													<!-- modal-body -->
													<div class="modal-footer" style="text-align: right;">
														<button type="button" class="btn btn-default"
															data-dismiss="modal" style="margin-right: 4%;">cancel</button>
													</div>
												</div>
												<!-- Modal content-->
											</div>
										</div>
										<!-- Modal -->
									</c:when>
								</c:choose>

							</div>
							<!-- ModalWrapper -->


							<p class="blog-post-footer"></p>
							<!-- / blog-block -->

							<h2 class="my-4">Bidders</h2>
								<div class="post-category" id="top1">
									<a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=" class="top1a"><h4>Top1</h4> 
									<img id="top1pic"
										src="<%=request.getContextPath()%>/frontend/biddingFront/img/trophy.svg"
										alt="Image" class="mr-2 memAvatar" style="margin-left: 0.5%;">
										
<!-- 										dothis later while avaliable -->
<!-- 										<img id="top1a" class="topBidderImg" -->
<%-- 										src="<%=request.getContextPath()%>/ReadMemPic?action=getPic&memId=" /> --%>

										<span></span> <span class="pull-right price" id="price1">$0</span>
									</a>
								</div>
							<!-- / post-category -->
							<div class="post-category" id="top2">
								<a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=" class="top2a"><h4>Top2</h4> <img id="top2pic"
									src="<%=request.getContextPath()%>/frontend/biddingFront/img/second.svg"
									alt="Image" class="mr-2 memAvatar" style="margin-right: 1.5%;">
									<span class="mr-2"></span> <span class="pull-right price"
									id="price2">$0</span> </a>
							</div>
							<!-- / post-category -->
							<div class="post-category" id="top3">
								<a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=" class="top3a">
									<h4>Top3</h4> <img id="top3pic"
									src="<%=request.getContextPath()%>/frontend/biddingFront/img/third.svg"
									alt="Image" class="mr-12 memAvatar" style="margin-right: 1.5%;">
									<span></span> <span class="pull-right price" id="price3">$0</span>
								</a>
							</div>


							<!-- / blog-block -->
							<div class="space-50">&nbsp;</div>
							<h3>Bidding ArtWork</h3>
							<div class="space-50">&nbsp;</div>
							<p class="post-meta">
								Posted by <a href="#">Admin</a> in <a href="#">Blog</a> on <a
									href="#">Nov. 17, 2016</a>
							</p>
							<p>Pellentesque pretium at justo iaculis vehicula. Aenean
								vestibulum purus a nulla sollicitudin molestie. Maecenas
								bibendum erat in erat maximus, vel imperdiet leo mattis. Integer
								vitae pellentesque massa. Fusce ac suscipit neque. Etiam justo
								risus, tristique id feugiat a, venenatis sed justo. Mauris
								aliquam venenatis nulla, et tincidunt enim fermentum ut. Aliquam
								mattis molestie est id condimentum.</p>
							<p>Nam porta lectus nec magna ullamcorper egestas eget at ex.
								In sit amet dolor vitae felis condimentum rutrum eget ut neque.
								Curabitur sagittis quam sodales, consequat urna sit amet, porta
								mi.</p>
							<p class="blog-post-footer">
								<a href="single-post.html"
									class="btn btn-primary-filled btn-rounded"><span>Read
										More</span><em class="fa fa-long-arrow-right"></em></a> <span
									class="post-icons pull-right"> <a href="#x"><i
										class="lnr lnr-thumbs-up"></em></a> <a href="#x"><i
										class="lnr lnr-bubble"></em></a>
								</span>
							</p>
							<!-- / blog-post-footer -->
						</div>
						<!-- / post-info-box -->
					</div>
					<!-- / blog-block -->
					<div class="space-50">&nbsp;</div>
				</div>
				<!-- / col-sm-8 col-md-9 -->
				<!-- / blog content area -->
				<!-- blog sidebar area -->
				<div class="col-sm-4 col-md-3 blog-sidebar">
					<div class="blog block categories-sidebar-widget">
						<div class="side-box mb-4">
							<p>
								Price: $<strong id="currentPrice" class="text-black">0</strong><br>
								Number of Bidders: <strong id="numberOfBids" class="text-black">0</strong>
							</p>
							<form
								action="<%=request.getContextPath()%>/bidding/BiddingServlet">
								<div class="mb-4">
									<div class="pb-3" style="border-bottom: 1px solid #efefef;">
										<input id="submitBidPrice" name="bid" type="text"
											class="form-control mb-2" placeholder="$0.00">
									</div>
									<button type="button" id="submitBid"
										class="btn btn-secondary btn-block mt-4">Submit a Bid</button>
									<div style="text-align: center; margin: 5% 0">
										<span class="d-block text-center my-2">or</span>
									</div>
									<button id="plus100" class="btn btn-primary btn-block">+$100</button>
								</div>
								<p class="mb-0" style="text-align: center; margin-top: 10%;">
									<a
										href="<%=request.getContextPath()%>/frontend/members/memLogin.jsp">Sign
										In</a> / <a
										href="<%=request.getContextPath()%>/frontend/members/memRegister.jsp">Register</a>
								</p>
							</form>
						</div>
					</div>
					<!-- / categories-sidebar-widget -->
					<div class="space-50">&nbsp;</div>
					<div class="space-50">&nbsp;</div>

					<c:set var="author" value="${memSvc.findByPrimaryKey(epVO.mem_id)}" />
					<a
						href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=${author.mem_id}">
						<div class="blog block about-sidebar-widget authorBlock" style="margin-top:2%">
							<p class="blog-post-footer"></p>
							<img style="margin: auto;" onerror="this.onerror=null; this.src='<%=request.getContextPath()%>/frontend/biddingFront/img/user.svg'"
								src="<%=request.getContextPath()%>/ReadMemPic?action=getPic&memId=${author.mem_id}"
								alt="Image"
								class="img-fluid w-50 rounded-circle mb-4 authAvatar">
							<h4>AUTHOR</h4>
							<h3 class="h5 text-black">${author.m_name}</h3>
							<p>Aenean vestibulum purus a nulla sollicitudin molestie.
								Maecenas bibendum erat in erat maximus.</p>
						</div>
					</a>
					<!-- about-sidebar-widget -->
					<div class="space-50">&nbsp;</div>
					<p class="blog-post-footer"></p>
					<div class="blog block tags-sidebar-widget authorBlock">
						<div class="widget-title">
							<h4>TAGS CLOUD</h4>
						</div>
						<p>
							<a href="#" class="btn btn-xs btn-primary-filled">Blog</a> <a
								href="#" class="btn btn-xs btn-primary-filled">Travel</a> <a
								href="#" class="btn btn-xs btn-primary-filled">Design</a> <a
								href="#" class="btn btn-xs btn-primary-filled">Videography</a> <a
								href="#" class="btn btn-xs btn-primary-filled">Tutorials</a>
						</p>
					</div>
					<!-- about-sidebar-widget -->
				</div>
				<!-- / col-sm-4 col-md-3 -->
				<!-- / blog sidebar area -->
			</div>
			<!-- / row -->
		</section>
		<!-- / blog content + sidebar -->
		<!-- pagination -->
		<div class="pagination">
			<a href="#x" class="btn btn-direction btn-default-filled"><i
				class="fa fa-long-arrow-left"></em><span>Previous Page</span></a> <a
				href="#x" class="btn btn-direction btn-default-filled pull-right"><span>Next
					Page</span><em class="fa fa-long-arrow-right"></em></a>
		</div>
		<!-- / pagination -->
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
							<p>Vivamus consequat lacus quam, nec egestas quam egestas sit
								amet. Suspendisse et risus gravida tellus aliquam ullamcorper.
								Pellentesque elit dolor, ornare ut lorem nec, convallis nibh
								accumsan lacus morbi leo lipsum.</p>
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
								<em class="lnr lnr-thumbs-up"></em> <span class="social text-left">
									<a class="no-margin" href="#"><em class="fa fa-facebook"></em></a>
									<a href="#"><em class="fa fa-twitter"></em></a> <a href="#"><i
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
						<strong>© MS - MINIMAL SHOP THEME</strong>
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
	<%@include file="/frontend/bar/frontBarFooter.jsp"%>
	<!-- / footer -->
	<!-- javascript -->
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>

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
	<script
		src="<%=request.getContextPath()%>/frontend/biddingFront/TimJs/biddingPage.js"></script>

</body>


</html>