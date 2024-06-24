<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.preproduct.model.*"%>
<%
    PreProductService preproductSvc = new PreProductService();
    List<PreProductVO> list = preproductSvc.getALLPreproductInTime();
    pageContext.setAttribute("list",list);
%>
<%
	PreProductVO preproductVO = (PreProductVO) request.getAttribute("preproductVO");
%>
<%/*----------顯示作品排名3D排列----------*/%>
<%@ page import="com.event_p.model.*"%>
<%@ page import="com.event.model.*"%>
<%
	EventService eventSvc=new EventService();
	Event_PService eventpSvc = new Event_PService();
    List<Event_PVO> list01 = eventpSvc.findAllByEventNoRankDescWithoutReport(eventSvc.findLastEndEvent());
    pageContext.setAttribute("list01",list01);
%>
<%
	Event_PVO eventpVO = (Event_PVO) request.getAttribute("eventpVO");
%>
<%/*----------素材購買排列----------*/%>
<%@ page import="com.material.model.*"%>
<%
	Material_Data_Service materialdataSvc = new Material_Data_Service();
    List<Material_Data_VO> list_ma = materialdataSvc.getAll();
    pageContext.setAttribute("list_ma",list_ma);
%>
<%
	Material_Data_VO materialdataVO = (Material_Data_VO) request.getAttribute("materialdataVO");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/frontend/bar/frontBarTop.jsp" %>

  <title>SB Admin 2 - Blank</title>
<!--   <link rel="stylesheet" type="text/css" href="css/index.css">跑版的罪魁禍首 -->
<!--   <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css"> -->
  <script type="text/javascript" src="<%=request.getContextPath()%>/frontend/preproduct/css/index_parts/jquery-3.5.1.js"></script>
  
  <style type="text/css" media="screen">
  
@media screen and (max-width:1200px){
	.btn-lg {
	    width: 110px !important;
	    font-size: 18px !important;
	}
}
@media screen and (max-width:1000px){
	.btn-lg {
	    width: 85px !important;
	    font-size: 12px !important;
	}
}
  
#disappear,
#disappear01,
#disappear02,
#addcartbtn{
	display: none;
	}
	.img01{
	width:100%; height:auto;
	}
	.count_contentBx .count_conutdown{
	display: flex;
	line-height: 30px !important;
	}
.count_contentBx .count_conutdown .count_time{
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
	background: #fff;
	margin-right: 20px;
	font-size: 48px;
	min-width: 110px;
	font-weight: 700;
	padding: 20px 5px 10px;
	border-radius: 4px;
	color: gray; 
}
header #header-banner{
/* 	background-image: url(css/img/index_css_imgs/banner_bg.jpg) !important; */
	background-image: url("https://mdbootstrap.com/img/Photos/Horizontal/Nature/full page/img(2).jpg");
	background-repeat: no-repeat;
	background-position:center;
	background-size: cover;
}
.banner-info h1{
	font-size: 48px;
	font-weight: 600;
	margin-bottom:20px !important;
}
.banner-border {
	width: 40% !important;
}
.banner-info{
	width: 100% !important;
	background: rgba(255, 255, 255, 0.9)!important;
}
.count_conutdown{
	justify-content: center;
	align-items: center;
	
}
.btn-lg{
	width: 150px !important;
    font-size: 25px;
	margin:2px;
	padding: 12px 0px;
	font-weight: 600;
	justify-content: center;
	align-items: center;
	box-sizing: border-box;
}
.divcss5{
 border:1px groove #000; width:300px; height:300px; 
 margin-bottom:5px;
 } 
.divcss5 img{width:100%px; height:auto;} 

.productshow {
		position:absolute;
		opacity: 1;
		z-index: -100;
		top: -150%;
		left: -95%;
		width: 270px;
		max-width: 270px;
		height: 200px;
}
.product:hover img {
    opacity: 0.9;
}
#no1{
height:400px;
}

	</style>
</head>

<body>

<!-- preloader -->
<div id="preloader">
    <div class="spinner spinner-round"></div>
</div>
<!-- / preloader -->

<div id="top"></div>


<header>
    
    <!-- header-banner 本期預購活動倒數-->
    <div id="header-banner">
        <div class="banner-content single-page text-center">
            <div class="banner-border">
            <a href="<%=request.getContextPath()%>/backend/preproduct/tools/eventP_show.jsp?EVENT_P_NO=${list01[0].event_p_no}" data-size="1600x1067">
            	<img id="no1" src="<%=request.getContextPath()%>/backend/preproduct/tools/eventP_show.jsp?EVENT_P_NO=${list01[0].event_p_no}"></a>
                <div class="banner-info">
                    <h1>本期預購活動倒數</h1>
                    <div class="count_contentBx">
			            <div class="count_conutdown">
				            <button type="button" class="btn btn-lg btn-primary" role="button"><div id="count_day">0</div><span>Days</span></button>
				            <button type="button" class="btn btn-lg btn-primary" role="button"><div id="count_hour">0</div><span>Hours</span></button>
				            <button type="button" class="btn btn-lg btn-primary" role="button"><div id="count_minute">0</div><span>Mins</span></button>
				            <button type="button" class="btn btn-lg btn-primary" role="button"><div id="count_second">0</div><span>Secs</span></button>
				            <div id="disappear">${list[0].getPo_end()}</div>
			            </div>
          			</div>
                </div><!-- / banner-info -->
            </div><!-- / banner-border -->
        </div><!-- / banner-content -->
    </div><!-- / header-banner -->
</header>
<!-- / header 本期預購活動倒數-->
<!-- 進入container-->
<div class="container">
  <div id="disappear02">
	<% @SuppressWarnings("unchecked")
		List<PreProductVO> buylist = (Vector<PreProductVO>) session.getAttribute("preshoppingcart"); %>
	<%if (buylist != null && (buylist.size() > 0)) {%>
	<p>這裡!!</p>
		<span id="cartNumbers" name="123" value="<%=buylist.size()%>"><%=buylist.size()%></span>
		<input type="text" id="cartNumbers1" name="123" value="<%=buylist.size()%>">
	<%}%>
  </div>
  
<hr>
<h1>本期得獎作品</h1><%=eventSvc.findLastEndEvent() %>

	<div class="row">
	    <div class="col-12">
			<%@include file="/backend/preproduct/pages/page3.file" %>
	    <div class="mdb-lightbox no-margin">
			<c:forEach var="eventpVO" items="${list01}" begin="<%=pageIndexx+1%>" end="<%=pageIndexx+rowsPerPagee-1%>">
		      <figure class="col-md-4">
		      <p>編號${eventpVO.event_p_no}  第${eventpVO.vote_rank}名</p>
		        <a href="<%=request.getContextPath()%>/backend/preproduct/tools/eventP_show.jsp?EVENT_P_NO=${eventpVO.event_p_no}" data-size="1600x1067"><div class="divcss5 "> 
		          <img alt="picture" src="<%=request.getContextPath()%>/backend/preproduct/tools/eventP_show.jsp?EVENT_P_NO=${eventpVO.event_p_no}"
		            class="img-fluid">
		        </div></a>
		      </figure>
			</c:forEach>
	    </div>
		</div>
	</div>
	<hr>
	<div class="row">
	    <div class="col-12">
			<h1>預購商品列表</h1>
				<!-- shop section -->
			<section id="shop" class="space-top-30">
		    <div class="container">
		        <p class="shop-results space-left">Showing <strong>${list.size()}</strong> items.</p>
		        <ul class="row shop list-unstyled" id="grid">
		        <c:forEach var="preproductVO" items="${list}">
		            <!-- product -->
		            
		            <lem class="col-xs-6 col-md-4 product m-product" data-groups='["mens"]'>
		            <form id="shoppingForm${preproductVO.event_p_no}" name="shoppingForm" action="Shopping.do" method="POST">
		                <div class="img-bg-color primary">
		                    <h5 class="product-price"><input type="hidden" name="po_price" value="${preproductVO.po_price}">$ ${preproductVO.po_price}</h5>
		                    <a href="#" class="product-link"></a>
		                    <!-- / product-link -->
		                    <img class="img01"; src="<%=request.getContextPath()%>/backend/preproduct/tools/eventP_show.jsp?EVENT_P_NO=${preproductVO.event_p_no}">
		                    <!-- / product-image -->
		
		                    <!-- product-hover-tools -->
		                    <div class="product-hover-tools">
			                    <div class="boxxx">
			                    	<img class="productshow " alt="picture" src="<%=request.getContextPath()%>/backend/preproduct/tools/materialP_show.jsp?MA_NO=${preproductVO.ma_no}">
		                        </div>
		                        <a  href="<%=request.getContextPath()%>/backend/preproduct/tools/eventP_show.jsp?EVENT_P_NO=${preproductVO.event_p_no}" data-size="1600x1067" class="view-btn" data-toggle="tooltip" title="View Product">
		                            <em class="lnr lnr-eye"></em>
		                        </a>
		                        <a href="javascript:" class="view-btn" onclick="document.getElementById('shoppingForm${preproductVO.event_p_no}').submit();">
		                            <em class="lnr lnr-cart"><input type="hidden" class="add-cart cart1" value=""></em>
		                        </a>
		                        
		                    </div><!-- / product-hover-tools -->
		
		                    <!-- product-details -->
		                    <div class="product-details">
		                        <h5 class="product-title">商品編號:${preproductVO.po_prod_no}</h5>
		                        <p class="product-category">商品描述:${preproductVO.po_detail}</p>
		                    </div><!-- / product-details -->
		                </div><!-- / img-bg-color -->
		            <input type="hidden" name="po_prod_no" value="${preproductVO.po_prod_no}">
			        <input type="hidden" name="event_p_no" value="${preproductVO.event_p_no}">
			        <input type="hidden" name="ma_no" value="${preproductVO.ma_no}">
			        <input type="hidden" name="po_price" value="${preproductVO.po_price}">
		            <input type="hidden" name="action" value="ADD">
		                </form>
		            </li>

		            </c:forEach>
		            <!-- / product -->
		
		            <!-- sizer -->
		            <lem class="col-xs-6 col-md-4 shuffle_sizer"></li>
		            <!-- / sizer -->
		
		        </ul> <!-- / products -->
		
		        
		
		    </div><!-- / container -->
		
		</section>
		<!-- / shop section -->
	
		</div>
	</div>

</div>
<script type="text/javascript">
// MDB Lightbox Init
$(function () {
$("#mdb-lightbox-ui").load("mdb-addons/mdb-lightbox-ui.html");
});
</script>
 <script type="text/javascript">//倒數計時器

   var countDate = $("#disappear").text();/*這邊放list[0] po_end*/
   var someday = new Date(countDate);

  function newYear(){
   var now = new Date();
   console.log(typeof now);

       gap = someday - now;

       var second = 1000;
       var minute = second * 60;
       var hour = minute * 60;
       var day = hour * 24;

       var d = Math.floor(gap/(day));
       var h = Math.floor((gap%(day)) / (hour));
       var m = Math.floor((gap%(hour)) / (minute));
       var s = Math.floor((gap%(minute)) / second);

       document.getElementById('count_day').innerText = d;
       document.getElementById('count_hour').innerText = h;
       document.getElementById('count_minute').innerText = m;
       document.getElementById('count_second').innerText = s;
       }

       setInterval(function(){
        newYear();
       },1000)
 </script>
 
<script type="text/javascript">


function onLoadCartNumbers() {
       //就算重新整理，cart的數字不會變。
       var  productNumbers = document.getElementById('cartNumbers');
       console.log(productNumbers);
       var  productNumbers2 = document.getElementById('cartNumbers1').value;
       console.log(productNumbers2);
       var cart = document.getElementsByClassName('cart');
       console.log(cart);
       var countcart = document.getElementById('countcart');
       console.log(countcart);

       if (productNumbers) {
           cart.innerHTML = parseInt(productNumbers);
           countcart.innerHTML = productNumbers2;
         	}
     	}
	onLoadCartNumbers();

  </script>
    


<!-- <script src="https://unpkg.com/ionicons@5.2.3/dist/ionicons.js"></script>   -->
<%@include file="/frontend/bar/frontBarFooter.jsp" %>
<%-- <script src="<%=request.getContextPath()%>/frontend/preproduct/css/index_parts/jquery-3.5.1.js"></script> --%>
</body>

</html>

