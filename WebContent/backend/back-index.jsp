<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*"%>
<%@ page import="com.preproduct.model.*"%>
<%@ page import="com.preorder.model.*"%>
<%@ page import="com.order.model.*"%>
<%@ page import="com.bidding.model.*" %>
<%@ page import="com.discount.model.*" %>
<%@ page import="com.preorderdetail.model.*" %>
<%@ page import="com.painter.model.*" %>
<%@ page import="com.mem.model.*" %>
<%
  //-------------預購商品與商城-------------
    PreProductService preproductSvc = new PreProductService();
    List<PreProductVO> list_prepro = preproductSvc.getAll();
    pageContext.setAttribute("list_prepro",list_prepro);
    
    PreOrderService preorderSvc = new PreOrderService();
    List<PreOrderVO> list_preorder = preorderSvc.getAll();
    pageContext.setAttribute("list_preorder",list_preorder);
    
//     等宏智的OrderDAO -> ds = ("java:comp/env/jdbc/G1") 改為EA103G1 
    OrderService orderSvc = new OrderService();
    List<OrderVO> list_order = orderSvc.getAll();
    pageContext.setAttribute("list_order", list_order);
    
  //-------------競標-------------
    BiddingService biddingSvc = new BiddingService();
    List<BiddingVO> list_bidding = biddingSvc.getAll();
    pageContext.setAttribute("list_bidding",list_bidding);
%>

<%
  //--------引入作品照片做3D輪播-------------
	PainterService painterSvc = new PainterService();
	List<PainterVO> list_ptr = painterSvc.getLatestPics();
	pageContext.setAttribute("list_ptr",list_ptr);
	
  //--------------引入會員------------------
	MemService memSvc = new MemService();
	List<MemVO> list_mem = memSvc.getAllDESC();
	pageContext.setAttribute("list_mem",list_mem);
	
%>
<%
	PreProductVO preproductVO = (PreProductVO) request.getAttribute("preproductVO");
	DiscountSettingVO discountsettingVO = (DiscountSettingVO) request.getAttribute("discountsettingVO");
%>
<jsp:useBean id="disSvc" scope="page" class="com.discount.model.DiscountSettingService" />
<jsp:useBean id="preorderdetailSvc" scope="page" class="com.preorderdetail.model.PreOrderDetailService" />
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>ArtsBlock首頁</title>
		<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
		
		<!-- favicon -->
		<link rel="icon" href="<%=request.getContextPath()%>/backend/template/img/favicon.png">
	
		<style>
			/*------3D圖片輪播CSS-----*/
			div.3D_body {
		      background: #fff;
		      font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
		      font-size: 14px;
		      color: #000;
		      display:flex;
		      justify-content: center;
      		  align-items: center;
		    }
		
		    .swiper-container {
		      width: 100%;
		      padding-top: 50px;
		      padding-bottom: 50px;
		    }
		
		    .swiper-slide {
		      background-position: center;
		      background-size: cover;
		      width: 220px;
		      height: 220px;
			  background: #000;
		    }
		    .swiper-slide img{
		      width: 300px;
		      height: 300px;
		    }
		    /*------會員3D圖片輪播CSS-----*/
		    div.3D_body1 {
		      background: #fff;
		      font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
		      font-size: 14px;
		      color: #000;
		      display:flex;
		      justify-content: center;
      		  align-items: center;
		    }
		    .mem-swiper-container {
		      width: 100%;
		      padding-top: 50px;
		      -webkit-box-reflect:below 1px linear-gradient(transparent 70%,#0009);
		    }
		
		    .mem_slide {
		      background-position: center;
		      background-size: cover;
		      width: 200px;
		      min-height: 230px;
			  background: #fff;
			  margin:0 10px;
		    }
		    .swiper-container-3d .swiper-slide-shadow-left,
		    .swiper-container-3d .swiper-slide-shadow-right{
		      background-image:none;
		    }
		    .mem_card{
		      position:relative;
		      width: 100%;
		      height:100%;
		      margin:0 auto;
		      background: #333;
		      padding: 50px 30px;
		      text-align: center;
		      overflow: hidden;
		    }
		    .mem_card .mem_layer{
		      position: absolute;
		      top: calc(100% - 2px);
		      left: 0;
		      width: 100%;
		      height: 100%;
		      background: linear-gradient(#03a9f4,#FFC78E);
		      z-index:1;
		      transition:0.5s;
		    }
		    .mem_card:hover .mem_layer{
		      top: 0;
		    }
		    .mem_card .mem_content{
			    position:relative;
			    z-index:2;
		    }
		    .mem_card .mem_content p{
				font-size: 18px;
		    	line-height: 24px;
		    	margin-bottom: 20px;
		    	color: #fff;
		    }
		    .mem_card .mem_content .mem_imgBx{
				width: 100px;
				height: 100px;
				margin: 0 auto 10px;
				border-radius: 50%;
				overflow: hidden;
				border: 4px solid #fff;
				box-shadow: 0 10px 20px rgba(0,0,0,0.2);
		    }
		    
		    .mem_card .mem_content h2{
		    font-size:18px;
		    color:#fff;
		    }
		    .mem_card .mem_content h2 a{
		    color:#03a9f4;
		    font-size:14px;
		    transition:0.5s;
		    text-decoration:none;
		    }
		    
		    
		    #mem_img{
		    width:100px;
		    height:100px !important;
		    }
		    
		    
			
			/*-----------------------*/
		</style>
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
					
		<div class="row">
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">平台會員數</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800 align-items-center">${list_mem.size()}人</div>
                    </div>
                    <div class="col-auto">
                      <em class="far fa-address-card fa-2x text-gray-300 "></em>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-success shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-success text-uppercase mb-1">平台作品數</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">${list_ptr.size()}件</div>
                    </div>
                    <div class="col-auto">
                      <em class="fas fa-palette fa-2x text-gray-300"></em>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-info shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-info text-uppercase mb-1">待處理訂單(預購/一般)</div>
                      <div class="row no-gutters align-items-center">
                        <div class="col-auto">
                          <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">
                          <% int count_preorder = 0; %><% int count_order = 0; %>
                          <c:forEach var="preorderVO" items="${list_preorder}">
	                          <c:if test="${preorderVO.po_status == 1}">
	                          	<%count_preorder += 1; %>
								<% pageContext.setAttribute("count_preorder",count_preorder); %>
	                          </c:if>
                          </c:forEach>

                          <c:forEach var="orderVO" items="${list_order}">
	                          <c:if test="${orderVO.or_status == 1}">
	                          	<%count_order += 1; %>
								<% pageContext.setAttribute("count_order",count_order); %>
	                          </c:if>
                          </c:forEach>

                          <%=pageContext.getAttribute("count_preorder")!=null ? pageContext.getAttribute("count_preorder"):0 %>  件 / 
                          <%=pageContext.getAttribute("count_order")!=null ? pageContext.getAttribute("count_order"):0 %>件
                          
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-auto">
                      <em class="fas fa-luggage-cart fa-2x text-gray-300"></em>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-warning shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">當前競標 (最高價)</div>
                      
                      <div class="h5 mb-0 font-weight-bold text-gray-800">
                      <c:choose>
	                      <c:when test="${list_bidding.size() != 0}}">
	                          $ ${list_bidding.get(list_bidding.size()-1).bdPrice}
	                      </c:when>
	                      <c:otherwise>
	                      		尚未競標
	                      </c:otherwise>
                      </c:choose>
                      </div>
                    </div>
                    <div class="col-auto">
                      <em class="fas fa-gavel fa-2x text-gray-300"></em>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
			<hr>
			<span class="d-block p-2 bg-primary text-white">最新作品發表:</span>		
					<%@include file="/backend/tools/page_forBackendindex.file" %>
						<div class="3D_body">
							<div class="swiper-container">
							    <div class="swiper-wrapper">
							    <c:forEach var="painterVO" items="${list_ptr}" begin="<%=pageIndexx%>" end="<%=pageIndexx+rowsPerPagee-1%>">
							      <div class="swiper-slide"><img src="<%=request.getContextPath()%>/backend/tools/painterP_show.jsp?PTR_NO=${painterVO.ptr_no}"></div>
							    </c:forEach>
							    </div>
							    Add Pagination
							    <div class="swiper-pagination"></div>
							  </div>
						</div>
			<hr>
			<span class="d-block p-2 bg-dark text-white">新進會員:</span>
					<%@include file="/backend/tools/page_forBackendindex_mem.file" %>
					
						<div class="3D_body1">
							<div class="mem-swiper-container swiper1">
								<div class="swiper-wrapper">
									<c:forEach var="memVO" items="${list_mem}" begin="<%=pageIndexxx%>" end="<%=pageIndexxx+rowsPerPageee-1%>" >
									
										<div class="swiper-slide mem_slide">
											<div class="mem_card">
												<div class="mem_layer"></div>
												<div class="mem_content">
													<p>${memVO.m_city}<br>${memVO.m_name}</p>
													<div class="mem_imgBx">
														<img id="mem_img" src="<%=request.getContextPath()%>/backend/tools/memP_show.jsp?MEM_ID=${memVO.mem_id}">
													</div>
													<div class="details">
														<br><br>
													</div>
												</div>
											</div>
										</div>
									
									</c:forEach>
									
								</div>
								Add Pagination
								<div class="swiper-pagination swiper-pagination1"></div>
							</div>
						</div>
					
						
						
						
						
						
						
					<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					<%@include file="/backend/bar/footer.jsp"%>
				</div> <!--END OF content-->
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
		<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
		<script>
		//--------作品3D輪播控制器-----------
		    var swiper = new Swiper('.swiper-container', {
		      effect: 'coverflow',
		      grabCursor: true,
		      centeredSlides: true,
		      slidesPerView: 'auto',
		      coverflowEffect: {
		        rotate: 20,
		        stretch: 0,
		        depth: 200,
		        modifier: 1,
		        slideShadows: true,
		      },
		      loop:true,
		      pagination: {
		        el: '.swiper-pagination',
		      },
		    });
		  //--------會員3D輪播控制器-----------
		    var swiper1 = new Swiper('.swiper1', {
		      effect: 'coverflow',
		      grabCursor: true,
		      centeredSlides: true,
		      slidesPerView: 'auto',
		      centeredSlides: true,
		      coverflowEffect: {
		        rotate: 0,
		        stretch: 0,
		        depth: 0,
		        modifier: 1,
		        slideShadows: true,
		      },
		      loop:true,
		    });
		</script>
	</body>
</html>