<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.* , com.order.model.OrderVO ,com.prod.model.ProdVO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.mem.model.*" %>
<%@page import="com.deal.model.*" %>

<%  //假資料
// String mem_id = "M000003"; 
// String m_name = "麥可";
// Integer m_zip = 421;
// String m_city = "台中市豐原區";
// String m_addr = "大馬路123號";
// String m_phone = "0982-766248";

// MemVO memVO = new MemVO();
// memVO.setMem_id(mem_id);
// memVO.setM_name(m_name);
// memVO.setM_zip(m_zip);
// memVO.setM_city(m_city);
// memVO.setM_addr(m_addr);
// memVO.setM_phone(m_phone);
// request.getSession().setAttribute("memVO",memVO);
%> 


<%//Session
MemVO memVO = new MemVO();
memVO = (MemVO)request.getSession().getAttribute("memVO");
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
                <h1 style="color:#c39d6d;" >填寫資料</h1>
                
 
         
<form method="post" action="<%=request.getContextPath()%>/frontend/shop/order"  enctype="multipart/form-data">
                
                <div class="row">
                    <div class="col-sm-6">
                   <font style="color:#c39d6d; font-size:20px;">收件人: </font><input value="${memVO.m_name}"  type="text" class="form-control" name="or_name" placeholder="請輸入收件人姓名" required>
                     <font style="color:#c39d6d; font-size:20px;">郵遞區號: </font><input value="${memVO.m_zip}" type="text" class="form-control" name="or_zip" placeholder="請輸入郵遞區號">
                        
                    </div>
                    <div class="col-sm-6">
                         <font style="color:#c39d6d; font-size:20px;">電話: </font><input value="${memVO.m_phone}" type="text" class="form-control" name="or_phone" placeholder="請輸入收件電話" required>
                         <font style="color:#c39d6d; font-size:20px;">地址: </font><input value="${memVO.m_city}${memVO.m_addr}" type="text" class="form-control" name="or_addr" placeholder="*請輸入收件地址" required>
                    </div>
                </div><!-- / row -->

                  <div class="checkout-form-footer space-left space-right">
                     <font style="color:#c39d6d; font-size:20px;">備註: </font><textarea class="form-control" name="or_note" placeholder="備註的話" ></textarea>
                    
         <%-- 錯誤表列 --%>
        <div  style="text-align:center">
               
        <c:if test="${not empty errorMsgs}">
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
</div>     
                   
<input type="hidden" name="or_total" value="<%=or_total%>">
<input type="hidden" name="action" value="insert">                  
<input name="mem_id" class="input" type="hidden"  value="${memVO.mem_id}">
<button type="submit"  id="btn-submit"    class="btn btn-primary-filled btn-rounded"><em class="lnr lnr-cart"></em><span>確認購買</span></button>
</form> 
                    
                    
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

<%    
DealService dealSvc = new DealService();
Integer mem_balance = dealSvc.getOneDeal(memVO.getMem_id()).getBalance();//從DB取餘額出來check
%>   
    
            

            <div class="col-sm-4 checkout-total">
                <h4 style="color:#c39d6d;">此次消費總金額: <h3 style="color:deeppink;">NT$ <%=or_total%></h3></h4> 
                <h4 style="color:#c39d6d; display:inline-block;  ">你目前錢包金額: <h3 style="color:purple;">NT$ <%=mem_balance%></h3></h4>
                <%if (or_total > mem_balance){%> 
                 <h4 style="color:#c39d6d;  display:inline-block;  ">你的錢不夠唷，你還差: <h3 style="color:red;">NT$ <%=or_total-mem_balance%></h3></h4> 
                	<a href="<%=request.getContextPath()%>/frontend/members/memArea.jsp#accountArea" class="btn btn-primary-filled btn-rounded"><em class="lnr lnr-store"></em><span>儲值去</span></a>
                <%} %>
                
                  
                <div class="cart-total-footer">
                    <a href="Cart.jsp" class="btn btn-default-filled btn-rounded"><em class="lnr lnr-arrow-left"></em><span>返回購物車</span></a>
                    <a href="EShop.jsp" class="btn btn-primary-filled btn-rounded"><em class="lnr lnr-store"></em><span>再逛逛</span></a>
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