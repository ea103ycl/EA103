<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.eval.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.mem.model.*" %>

<%
ProdVO prodVO = (ProdVO) request.getAttribute("prodVO"); 
%>

<%	
EvalService evalSvc = new EvalService();
List<EvalVO> list_eval = evalSvc.getAllByProd_no(prodVO.getProd_no());
pageContext.setAttribute("list_eval", list_eval);
%>

<%	
ProdService prodSvc3 = new ProdService();
List<ProdVO> list3 = prodSvc3.getRelatedProd(prodVO.getPtr_no());
pageContext.setAttribute("list3", list3);
%>


<%//Session
MemVO memVO = new MemVO();
memVO = (MemVO)request.getSession().getAttribute("memVO");
%>


<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Minimal Shop Theme">
<meta name="keywords" content="responsive, retina ready, html5, css3, shop, market, onli store, bootstrap theme" />
<meta name="author" content="KingStudio.ro">

<!-- favicon -->
<link rel="icon" href="images/favicon.png">
<!-- page title -->
<title>MS - Minimal Shop Theme</title>
<!-- bootstrap css -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- css -->
<link href="css/style.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<!-- fonts -->
<link href="https://fonts.googleapis.com/css?family=Rubik:400,500,700,900" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Lily+Script+One" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href='fonts/FontAwesome.otf' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/linear-icons.css">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

</head>


<style>



.value-button {
  display: inline-block;
  border: 1px solid #ddd;
  margin: 0px;
  width: 40px;
  height: 40px;
  text-align: center;
  vertical-align: middle;
  padding: 11px 0;
  background: #c39d6d;
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

.value-button:hover {
  cursor: pointer;
}

#decrease {
  margin-right: -4px;
  border-radius: 8px 0 0 8px;
}

#increase {
  margin-left: -4px;
  border-radius: 0 8px 8px 0;
}

form #input-wrap {
  margin: 0px;
  padding: 0px;
}

input#prod_qty {
  text-align: center;
  border: none;
  border-top: 1px solid #ddd;
  border-bottom: 1px solid #ddd;
  margin: 0px;
  width: 40px;
  height: 40px;
}

input[type=number]::-webkit-inner-spin-button,
input[type=number]::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

</style>



<body>

<!-- preloader -->
<div id="preloader">
    <div class="spinner spinner-round"></div>
</div>
<!-- / preloader -->

<div id="top"></div>

<!-- header -->
<header>

<!-------------------------------------- nav ------------------------------------------>
        <%@include file="/frontend/bar/frontBarTop.jsp"%>
<!-------------------------------------- /nav ------------------------------------------>   
        

    <!-- header-banner -->
    <div id="header-banner">
        <div class="banner-content single-page text-center">
            <div class="banner-border">
                <div class="banner-info">
                    <h1>ArtsBlock Shop</h1>
                        <p>Welcome to you</p>
                </div><!-- / banner-info -->
            </div><!-- / banner-border -->
        </div><!-- / banner-content -->
    </div>
    <!-- / header-banner -->
</header>
<!-- / header -->

<!-- content -->

<!-- shop single-product -->
<section id="shop">
    <div class="container space-top-30">
        <div class="row">

            <!-- product content area -->
            <div class="col-sm-6 col-md-7 product-content-area">
                <div class="product-content-area">
                    <div id="product-slider" class="carousel slide" data-ride="carousel">
                        <!-- wrapper for slides -->
                        <div class="carousel-inner" role="listbox">
                            <div class="item active">
                                <img src="<%=request.getContextPath()%>/frontend/shop/prod.pic?action=getpic&prod_no=${prodVO.prod_no}" alt="">
                            </div>
                            <div class="item">
                                 <img src="<%=request.getContextPath()%>/frontend/shop/prod.pic?action=getpic&prod_no=${prodVO.prod_no}" alt="">
                            </div>
                            <div class="item">
                                 <img src="<%=request.getContextPath()%>/frontend/shop/prod.pic?action=getpic&prod_no=${prodVO.prod_no}" alt="">
                            </div>
                        </div>
                        <!-- / wrapper for slides -->

                        <!-- controls -->
                        <a class="left carousel-control" href="#product-slider" role="button" data-slide="prev">
                            <span class="lnr lnr-chevron-left" aria-hidden="true"></span>
                        </a>
                        <a class="right carousel-control" href="#product-slider" role="button" data-slide="next">
                            <span class="lnr lnr-chevron-right" aria-hidden="true"></span>
                        </a>
                        <!-- / controls -->
                    </div><!-- / product-slider -->

                    <ul class="nav nav-tabs" role="tablist">
                        <lem class="active"><a href="#description" role="tab" data-toggle="tab" aria-expanded="true">商品描述</a></li>
                        
                        <lem class=""><a href="#reviews" role="tab" data-toggle="tab" aria-expanded="false">商品評價</a></li>
                    </ul>
                    <!-- / nav-tabs -->
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane animated fadeIn active" id="description">
                            <p></p>
                            <p>${prodVO.prod_detail}</p>
                        </div>
                        <!-- / description-tab -->

                        
                       
                      <!---------------------------  reviews-tab ----------換---------------------------------->
                        <div role="tabpanel" class="tab-pane animated fadeIn" id="reviews">
                            <div class="reviews">
                            
                            
<!--                             / description-tab -->
                        
                   
                    <c:if test="${list_eval.size()==0}">
                                        <font size="5px" color="deeppink">還沒有人評價此商品唷^^"</font>
                      </c:if>
   <c:forEach var="evalVO" items="${list_eval}" >
                       
                      

                            
                                <div class="review-author pull-left">
<jsp:useBean id="evalSvc2" scope="page" class="com.eval.model.EvalService" />
<c:if test="${pageScope.evalSvc2.getMem_pic_ByMem_id(evalVO.mem_id).getM_photo()==null}">
  <img src="<%=request.getContextPath()%>/frontend/shop/order/images/noPic.gif">
</c:if>
<c:if test="${pageScope.evalSvc2.getMem_pic_ByMem_id(evalVO.mem_id).getM_photo()!=null}">
  <img src="<%=request.getContextPath()%>/frontend/shop/eval.pic?action=getpic&mem_id=${evalVO.mem_id}" alt="">
</c:if>
                                 
                                </div>
                         <!---------------------------------------------------------------------  review-content --------------------------------------------------------->
                                <div class="review-content" style="margin: 0 0 20px 0;">
                                    
                                    <div class="review-stars">
                                    
                                        <span class="product-rating">
                                        <c:if test="${evalVO.eval_rang==1}">
                                         <em class="fa fa-star"></em>
                                         </c:if>
                                        <c:if test="${evalVO.eval_rang==2}">
                                        <em class="fa fa-star"></em><em class="fa fa-star"></em>
                                         </c:if>
                                          <c:if test="${evalVO.eval_rang==3}">       
                                        <em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star"></em>
                                        </c:if>
                                        <c:if test="${evalVO.eval_rang==4}"> 
                                        <em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star"></em>
                                        </c:if>
                                        <c:if test="${evalVO.eval_rang==5}"> 
                                            <em class="fa fa-star"></em> <em class="fa fa-star"></em> <em class="fa fa-star"></em> <em class="fa fa-star"></em> <em class="fa fa-star"></em>
                                       </c:if>
                                        </span>
                                    </div>
                                    <h6 class="review-title no-margin">${evalVO.eval_review}</h6>
                                    <cite> ${evalVO.mem_id} 評價於:</cite>
                                    <cite><fmt:formatDate value="${evalVO.eval_time}" pattern="yyyy-MM-dd HH:mm:ss"/></cite>
                                </div>
                          
                                
 </c:forEach>
                          <!-------------------------------------------------------- / review-content -------------------------------------------------------------->
                               
                                
                          <!----------------------------------  review-content -------------------------------------------->
                                
                             <!----------------------------- / review-content ---------------------------------------------->

                              <!----------------------------------- add review --------------------------------------------->
                                
                             <!-------------------------------------- / add review ----------------------------------------->
                            </div><!-- / reviews -->
                        </div>
                        <!--------------------------- / reviews-tab -------------------------------------------->
                    </div>
                    <!-- / tab-content -->
                </div><!-- / product-content-area -->

            </div>
            <!-------------------------------------- / product-content-area --------------------------------->


            <!-- product sidebar area -->
            <div class="col-sm-6 col-md-5 product-sidebar">
                <div class="product-sidebar-details">
                    <h3>${prodVO.prod_name}</h3>
                    
                    <div class="product-info">
                        <div class="info">
                            <p><em class="lnr lnr-tag"></em><span style="font-size:20px;">$ ${prodVO.prod_price}</span></p>
                        </div>
                        
                        
                        <div class="info">
                            <p><em class="lnr lnr-star"></em>
                           
                            
                            <%  if (list_eval.size()==0){ %>
                            <font size="3px" color="deeppink">還沒有人評價過此商品</font>
                             <% } %>
                             
                             
                             <%if (list_eval.size()!=0){
                            	 
                            	 Integer star_total = 0; 
                                 for(EvalVO evalVO2 : list_eval) {
                                 star_total +=  evalVO2.getEval_rang(); 
                                }
                                Integer star_value = star_total / list_eval.size(); 
                            	%> 
                      
                             
                            <%  if ( star_value >0 && star_value <=0.5){ %> 
                            <em class="fa fa-star-half-o"></em>
                            <% } %>
                            <%  if (star_value>0.5 && star_value<=1){ %>
                             <em class="fa fa-star"></em>
                            <% } %>
                            <%  if (star_value >1 && star_value <=1.5){ %>
                             <em class="fa fa-star"></em><em class="fa fa-star-half-o"></em>
                            <% } %>
                            <%  if (star_value >1.5 && star_value<=2){ %>
                            <em class="fa fa-star"></em><em class="fa fa-star"></em>
                            <% } %>
                            <%  if (star_value >2 && star_value <=2.5){ %>
                             <em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star-half-o"></em>
                            <% } %>
                            <%  if (star_value >2.5 && star_value<=3){ %>
                            <em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star"></em>
                            <% } %>
                            <%  if (star_value >3 && star_value <=3.5){ %>
                             <em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star-half-o"></em>
                            <% } %>
                            <%  if (star_value >3.5 && star_value<=4){ %>
                             <em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star"></em>
                            <% } %>
                            <%  if (star_value >4 && star_value <=4.5){ %>
                              <em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star-half-o"></em>
                            <% } %>
                             <%  if (star_value >4.5 && star_value <=5){ %>
                              <em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star"></em><em class="fa fa-star"></em>
                            <% } %>
                            <% } %>
                            </span>
                            
                            </p>
                        </div>
                    </div><!-- / product-info -->

<!-------------------------------------- / 下拉 --------------------------------->
                    
 <div>                  
<form  action="<%=request.getContextPath()%>/frontend/shop/shopping" method="POST" enctype="multipart/form-data"> 
 <jsp:useBean id="prodSvc2" scope="page" class="com.prod.model.ProdService"  />

<div>
<b style="display: inline-block; font-size:15px;">看看其他素材:</b>

<select  name="ma_no" style="width:100px;font-size:15px; "onchange="submit();"  style="display: inline-block;">
<option value="">選擇素材</option>  
   <c:forEach var="prodVO2" items="${prodSvc2.allma}"> 
   <option value="${prodVO2.ma_no}"> ${prodVO2.ma_name}</option>
   </c:forEach>
   </select>
  	
   <c:if test="${not empty errorMsgs}">
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: deeppink">${message}</li>
			</c:forEach>
		</ul>
	</c:if>   
 </div>
        <input type="hidden" name="prod_no" value="${prodVO.prod_no}">
        <input type="hidden" name="ptr_no" value="${prodVO.ptr_no}">
        <input type="hidden" name="action" value="Other_Ma_Search">
</form>	
</div>                  
<!-- -------------------------------------------------------商品詳情------------------------------------------------                     -->
<%-- <form name="shoppingForm" action="<%=request.getContextPath()%>/frontend/shop/cart" method="POST" enctype="multipart/form-data">                   --%>
        
        <div class="buy-product">
<!--         <div class="options"> -->
        
<!--      ---------------------------購買數量----------------------------  -->
  <div class="value-button" id="decrease" onclick="decreaseValue()" value="Decrease Value" >-</div>
 
        <input name="prod_qty" type="number"   step="1" min="1"  value="1" title="Qty" id="prod_qty"  size="1" >  
  <div class="value-button" id="increase" onclick="increaseValue()" value="Increase Value">+</div>
<!--      ---------------------------購買數量----------------------------  -->
        
          
<!--          </div> -->
		 <!-- / options -->
		
		   <div class="space-25">&nbsp;</div>
		        
                        
     <input type="hidden" name="prod_no" value="${prodVO.prod_no}"       id="prod_no">
      <input type="hidden" name="prod_name" value="${prodVO.prod_name}"   id="prod_name">
      <input type="hidden" name="prod_price" value="${prodVO.prod_price}" id="prod_price">
      
       <button type="button" name="Submit" value="放入購物車"   id="addcart" class="btn btn-primary-filled btn-rounded">
       <em class="lnr lnr-cart"></em><span>加入購物車</span></button>
        </div>
<!--        </form> -->
       
<!-- -------------------------------------------------------商品詳情------------------------------------------------                     -->

              
 </div>
<!-------------------------------------- / 加入購物車 --------------------------------->
                </div><!-- product-details -->

            </div><!-- / col-sm-4 col-md-3 -->
            <!-- / product sidebar area -->

        </div><!-- / row -->
<!-------------------------------------- / 相關商品--------------------------------->
        <div id="related-products">
            <h4 class="space-top-30 space-bottom-30 space-left">你可能也感興趣的商品</h4>
            <ul class="row shop list-unstyled" id="grid">
                
<!----------------------------------------------- Related-product --------------------------------------------------->

                <c:forEach var="prodVO3" items="${list3}" varStatus="counter" >
                <lem class="col-xs-6 col-md-4 product m-product" data-groups='["mens"]'>
                    <div class="img-bg-color primary">
                        <h5 class="product-price">${prodVO3.prod_price}</h5>
                        <a href="single-product.html" class="product-link"></a>
                        <!-- / product-link -->
                        <img src="<%=request.getContextPath()%>/frontend/shop/prod.pic?action=getpic&prod_no=${prodVO3.prod_no}" width="200" height="300"  name="prod_pic">
                        <!-- / product-image -->
                        <!-- product-hover-tools -->
<div class="product-hover-tools">
                        
                        

 <div class="product-hover-tools">                     
<form  action="<%=request.getContextPath()%>/frontend/shop/shopping" method="POST" enctype="multipart/form-data">
  <input type="hidden" name="prod_no" value="${prodVO3.prod_no}">
   <input type="hidden" name="action" value="getOne_For_Detail">		
    <button type="submit" name="Submit" value="商品詳情"   class="view-btn" data-toggle="tooltip" title="View Product">
    <em class="lnr lnr-eye"></em>
    </button>   
</form>



<%-- <form name="shoppingForm" action="<%=request.getContextPath()%>/frontend/shop/cart" method="POST" enctype="multipart/form-data"> --%>
      <input type="hidden" name="prod_no" value="${prodVO3.prod_no}"       id="prod_no${counter.count}">
      <input type="hidden" name="prod_name" value="${prodVO3.prod_name}"   id="prod_name${counter.count}">
      <input type="hidden" name="prod_price" value="${prodVO3.prod_price}" id="prod_price${counter.count}">
      <input type="hidden" name="prod_qty" value= 1                       id="prod_qty${counter.count}" >
<!--  <input type="hidden" name="action" value="ADD">	 -->
      <button type="button" name="Submit" value="放入購物車"  id="addcart_related${counter.count}" class="cart-btn" data-toggle="tooltip" title="Add to Cart">
      <em class="lnr lnr-cart"></em>
       </button>   
<!-- </form> -->
 </div>
                                                                   
                            
</div>
<!------------------------------------------- / product-hover-tools ----------------------------------------->
                        <!-- product-details -->
                        <div class="product-details">
                            <h5 class="product-title">${prodVO3.prod_name}</h5>
                            <p class="product-category"></p>
                        </div><!-- / product-details -->
                    </div><!-- / img-bg-color -->
                </li>
</c:forEach>
                <!---------------------------------------------- /Related-product ----------------------------------->

                

            </ul><!-- / row -->
        </div><!-- / related-products -->

    </div><!-- / container -->
</section>
<!-- / shop single-product -->

<!-- / content -->

<!-- scroll to top -->
<a href="#top" class="scroll-to-top page-scroll is-hidden" data-nav-status="toggle"><em class="fa fa-angle-up"></em></a>
<!-- / scroll to top -->

<!--------------------------------------- footer --------------------------------------->
<%@include file="/frontend/bar/frontBarFooter.jsp"%>
<!--------------------------------------- /footer --------------------------------------->

  
    <script src="<%=request.getContextPath() %>/frontend/template/js/jquery/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/frontend/template/js/jquery.easing.min.js"></script><!-- return to top id -->
	<script src="<%=request.getContextPath()%>/frontend/template/tonyTools/sweetAlert/sweetalert.min.js"></script>

<script>
function increaseValue() {
  var value = parseInt(document.getElementById('prod_qty').value, 10);
  value = isNaN(value) ? 0 : value;
  value++;
  document.getElementById('prod_qty').value = value;
}

function decreaseValue() {
  var value = parseInt(document.getElementById('prod_qty').value, 10);
  value = isNaN(value) ? 0 : value;
  value < 1 ? value = 1 : '';
  value--;
  document.getElementById('prod_qty').value = value;
}
</script>



 <script>
$(document).ready(function(){
	 console.log("OK1");
 
  $("#addcart").click(function(){
    let prod_no = $("#prod_no").val();
    let prod_name = $("#prod_name").val();
    let prod_price = $("#prod_price").val();
    let prod_qty = $("#prod_qty").val();
    console.log(prod_no);
    console.log(prod_name);
    console.log(prod_price);
    console.log(prod_qty);
   $.ajax({
    type:"POST",
    url:"<%=request.getContextPath()%>/frontend/shop/cart",
    data:{
    	prod_no : prod_no,
    	prod_name : prod_name,
    	prod_price : prod_price,
    	prod_qty : prod_qty,
        action:"ADD",
    }, 
    success:function(data){
        $('#cartCnt1').text(data);	
         $('#cart-badge').text(data);
     swal(prod_name + "     購買數量:"+ prod_qty,"加入購物車", "success");
    }
   })
  })
});
 
</script>


<script>
$(document).ready(function(){
	
 for(let i = 1 ; i <= <%=list3.size()%> ; i++){
  $("#addcart_related" + i).click(function(){
	  console.log("OK2");
	  
	  let prod_no = $("#prod_no" + i ).val();
	  let prod_name = $("#prod_name" + i ).val();
	  let prod_price = $("#prod_price" + i ).val();
	  let prod_qty = $("#prod_qty" + i ).val();
    console.log(prod_no);
    console.log(prod_name);
    console.log(prod_price);
    console.log(prod_qty);
   $.ajax({
    type:"POST",
    url:"<%=request.getContextPath()%>/frontend/shop/cart",
    data:{
    	prod_no : prod_no,
    	prod_name : prod_name,
    	prod_price : prod_price,
    	prod_qty : prod_qty,
        action:"ADD",
    }, 
    success:function(data){
        $('#cartCnt1').text(data);	
         $('#cart-badge').text(data);
         swal(prod_name, "加入購物車", "success");
    }
   })
  })
 }
});
 
</script>
