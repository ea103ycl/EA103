<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="java.util.* , com.order.model.OrderVO ,com.prod.model.ProdVO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import=" java.text.*,  java.util.*"  %>
<%@page import="com.mem.model.*" %>
<%@page import="com.deal.model.*" %>


<% 
OrderVO orderVO = (OrderVO)request.getAttribute("orderVO"); 
%>
<%
SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
 Date current = new Date();
%>



<% 
DealService dealSvc = new DealService();
Integer after_balance = dealSvc.getOneDeal(orderVO.getMem_id()).getBalance();//從DB取餘額出來check

MemVO memVO = new MemVO();
memVO = (MemVO)request.getSession().getAttribute("memVO");
memVO.setBalance(after_balance);//set到session，組員要求
%>

<%
  Integer or_total =0;
  Integer count=0;
  Vector<ProdVO> buylist = (Vector<ProdVO>) session.getAttribute("shoppingcart");
                if (buylist != null && (buylist.size() > 0)) {
				for (int index = 0; index < buylist.size(); index++) {
				count = (index+1);
				                     }
	                                 }
                
                if (buylist != null && (buylist.size() > 0)) {
                	for (int index = 0; index < buylist.size(); index++) {
                		ProdVO order = buylist.get(index);
                	    int prod_qty = order.getProd_qty();
                		int prod_price = order.getProd_price();
                		int prod_no = order.getProd_no();
                		 or_total += prod_qty * prod_price;
                }
                }
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
                    <h1>Checkout</h1>
                </div><!-- / banner-info -->
            </div><!-- / banner-border -->
        </div><!-- / banner-content -->
    </div>
    <!-- / header-banner -->
</header>
<!-- / header -->

<!-- content -->

<!-- shopping-cart -->





  <!------------------------------------ / checkout-form ----------------------------------------->
<div id="checkout">
    <div class="container">
        <div class="row checkout-screen">
            <div class="col-sm-8 checkout-form">
                <h4 class="space-left" style="color:#c39d6d;">訂單成立!!</h4>
                <h4 class="space-left" style="color:#c39d6d;">你的訂單編號: ${orderVO.or_no}</h4>
                
 
         

                
                <div class="row">
                    <div class="col-sm-6">
                   
                   <font style="color:#c39d6d; font-size:20px;">訂單成立時間:</font><div type="text" class="form-control" name="or_time"><%=Format.format(current) %></div>
                   <font style="color:#c39d6d; font-size:20px;">收件人姓名:<div type="text" class="form-control" name="or_name">${orderVO.or_name}</div>
                   <font style="color:#c39d6d; font-size:20px;">郵遞區號:<div type="text" class="form-control" name="or_zip"  >${orderVO.or_zip}</div>
                        
                    </div>
                    <div class="col-sm-6">
                        <font style="color:#c39d6d; font-size:20px;">會員編號:<div  type="text" class="form-control" name="or_mem_id">${orderVO.mem_id}</div>
                        <font style="color:#c39d6d; font-size:20px;">收件人電話:<div  type="text" class="form-control" name="or_phone">${orderVO.or_phone}</div>
                        <font style="color:#c39d6d; font-size:20px;">收件地址:<div type="text" class="form-control" name="or_addr"  >${orderVO.or_addr}</div>
                    </div>
                </div><!-- / row -->

                  <div class="checkout-form-footer space-left space-right">
                   <font style="color:#c39d6d; font-size:20px;">備註:<div class="form-control" name="or_note" >${orderVO.or_note}</div>
                    
        
         
         
                   

                    
                    
                 </div><!-- / checkout-form-footer -->




<!--                 <div class="row"> -->
<!--                     <div class="col-sm-6"> -->
<!--                         <select class="form-control" name="country"> -->
<!--                             <optgroup label="Country:"> -->
<!--                                 <option value="usa">USA</option> -->
<!--                                 <option value="australia">Australia</option> -->
<!--                                 <option value="g-britain">Great Britain</option> -->
<!--                                 <option value="sp">Spain</option> -->
<!--                             </optgroup> -->
<!--                         </select> -->
<!--                         <select class="form-control" name="state"> -->
<!--                             <optgroup label="State:"> -->
<!--                                 <option value="s1">Florida</option> -->
<!--                                 <option value="s2">VIC</option> -->
<!--                                 <option value="s3">United Kingdom</option> -->
<!--                                 <option value="s4">Catalonia</option> -->
<!--                             </optgroup> -->
<!--                         </select> -->
<!--                     </div> -->
<!--                     <div class="col-sm-6"> -->
<!--                         <select class="form-control" name="city"> -->
<!--                             <optgroup label="State:"> -->
<!--                                 <option value="s1">Miami</option> -->
<!--                                 <option value="s2">Melbourne</option> -->
<!--                                 <option value="s3">London</option> -->
<!--                                 <option value="s4">Barcelona</option> -->
<!--                             </optgroup> -->
<!--                         </select> -->
<!--                         <input type="text" class="form-control" name="zip" placeholder="ZIP CODE" required> -->
<!--                     </div> -->
<!--                 </div>/ row -->
            </div>
    
      
  
    
            

            <div class="col-sm-4 checkout-total">
                <h4 >感謝你的消費: <h3 style="color:deeppink;">NT$ ${orderVO.or_total}</h3></h4> 
                <h4 >你的錢包剩餘: <h3 style="color:purple;">NT$ <%=after_balance %></h3></h4>   
                <div class="cart-total-footer">
                    <a href="<%=request.getContextPath()%>/frontend/shop/order/OrderSearch.jsp" class="btn btn-default-filled btn-rounded"><em class="lnr lnr-arrow-left"></em><span>查看訂單</span></a>
                    <a href="<%=request.getContextPath()%>/frontend/shop/EShop.jsp" class="btn btn-primary-filled btn-rounded"><em class="lnr lnr-store"></em><span>再逛逛</span></a>
                </div><!-- / cart-total-footer -->
            </div><!-- / checkout-total -->


<!------------------------------------ / checkout-form ----------------------------------------->


        </div><!-- / row -->
    </div><!-- / container -->
</div>
<!-- / shopping-cart -->

<!-- / content -->

<!-- scroll to top -->
<a href="#top" class="scroll-to-top page-scroll is-hidden" data-nav-status="toggle"><em class="fa fa-angle-up"></em></a>
<!-- / scroll to top -->

<!--------------------------------------- footer --------------------------------------->
<%@include file="/frontend/bar/frontBarFooter.jsp"%>
<!--------------------------------------- /footer --------------------------------------->

<!-- javascript -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<!-- sticky nav -->
<script src="js/jquery.easing.min.js"></script>
<script src="js/scrolling-nav-sticky.js"></script>
<!-- / sticky nav -->

<!-- hide nav -->
<script src="js/hide-nav.js"></script>
<!-- / hide nav -->

<!-- preloader -->
<script src="js/preloader.js"></script>
<!-- / preloader -->

<!-- / javascript -->
</body>

</html>