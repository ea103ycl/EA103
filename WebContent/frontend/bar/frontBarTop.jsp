<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.preproduct.model.*"%>

<% 
	//一般購物車
	@SuppressWarnings("unchecked")
	Vector<PreProductVO> tBarCart1 = (Vector<PreProductVO>) session.getAttribute("shoppingcart");
	int tBarCart1Cnt = ( (tBarCart1 != null && tBarCart1.size() > 0) ? tBarCart1.size() : 0);
	System.out.print( "一般購物車數量=" + tBarCart1Cnt );

	//預購購物車
	@SuppressWarnings("unchecked")
	Vector<PreProductVO> tBarCart2 = (Vector<PreProductVO>) session.getAttribute("preshoppingcart");
    int tBarCart2Cnt = ( (tBarCart2 != null && tBarCart2.size() > 0) ? tBarCart2.size() : 0);
    System.out.print( "預購購物車數量=" + tBarCart2Cnt );
    
    //總數量
    int tBarCartTotal = tBarCart1Cnt + tBarCart2Cnt;
    pageContext.setAttribute("tBarCart1Cnt", tBarCart1Cnt);
    pageContext.setAttribute("tBarCart2Cnt", tBarCart2Cnt);
    pageContext.setAttribute("tBarCartTotal", tBarCartTotal);
%>

<!DOCTYPE html>

<html>
	<head>

		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<!-- favicon -->
		<link rel="icon" href="<%=request.getContextPath()%>/frontend/template/images/favicon.png">
		
		<!-- bootstrap css -->
		<link href="<%=request.getContextPath()%>/frontend/template/css/bootstrap.min.css" rel="stylesheet">
		
		<!-- css -->
		<link href="<%=request.getContextPath()%>/frontend/template/css/style.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/frontend/template/css/animate.css" rel="stylesheet">
		
		<!-- fonts -->
		<link href="https://fonts.googleapis.com/css?family=Rubik:400,500,700,900" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lily+Script+One" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/frontend/template/css/font-awesome.min.css" rel="stylesheet" type="text/css">
		<link href='<%=request.getContextPath()%>/frontend/template/fonts/FontAwesome.otf' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/template/css/linear-icons.css">
		
		<!-- Google Fonts -->
		<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
		
		<!-- other css -->
		<style>
			.ycl-topBar-second{
				display:inline-block;
			}
			
			.ycl-topBar-cartCntText{
				margin-left:1em !important;
				font-size:10px !important;
			}
			
			.ycl-topbar-logoNm{
				color: #ad8b60;
				font-family: "Raleway", sans-serif !important;
				--font-family-sans-serif: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
   				--font-family-monospace: SFMono-Regular,Menlo,Monaco,Consolas,"Liberation Mono","Courier New",monospace;
				font-size: 26px;
    			font-weight: 300;
    			letter-spacing:0px;
    			margin-left:5px;
			}
			
			.ycl-topbar-icon{
				margin:0 4px;
			}
			
			.ycl-topbar-memPhoto{
				width: 24px;
				height:24px;
				border-radius: 50%;
				display:inline-block;
			}
		</style>
		
				
		<!-- page title -->
</head>
	<body>
	
    <!-- nav -->
    <nav class="navbar navbar-default nav-sec navbar-fixed-top">
        <div class="container">
            
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<%=request.getContextPath()%>/frontend/front_index.jsp"><h2 class="ycl-topbar-logoNm">ArtsBlock</h2></a>
            </div><!-- / navbar-header -->
            	
            <div class="navbar-collapse collapse text-center">
                <ul class="nav navbar-nav">
                	
                    <lem class=""><a href="<%=request.getContextPath()%>/frontend/front_index.jsp"><span>首頁</span></a></li>
                    
                    <!-- 社群 -->
                    <li><a href="<%=request.getContextPath()%>/frontend/painter/listAllPainterFollow.jsp"><span>社群</span></a></li>
                    
                    <!-- 一般購買 -->
                    <li><a href="<%=request.getContextPath()%>/frontend/shop/EShop.jsp"><span>商城</span></a></li>
                    
                    <!-- 創意主題競賽 -->
                    <li><a href="<%=request.getContextPath()%>/frontend/event_p/event_homePage.jsp"><span>主題競賽</span></a></li>

                    <!-- 預購 -->
                    <li><a href="<%=request.getContextPath()%>/frontend/preproduct/frontindex.jsp"><span>預購</span></a></li>

                    <!-- 競標 -->
                    <li><a href="<%=request.getContextPath()%>/frontend/biddingFront/biddingIndex.jsp"><span>競標</span></a></li>

                    <!-- 見面會 -->
                    <li><a href="<%=request.getContextPath()%>/frontend/meeting/listAllMeeting_front.jsp"><span>見面會</span></a></li>
					
					<div class="secondary-nav" style="display:inline-block;">
						<!-- 購物車 -->
		            	<lem class="dropdown ycl-topBar-second">
		                    <a href="#" class="shopping-cart space-right dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><em class="fa fa-shopping-cart"></em><span class="cart-badge" id="cart-badge">${tBarCartTotal}</span></a>
		                    <ul class="dropdown-menu animated zoomIn fast">
		                        <li><a href="<%=request.getContextPath()%>/frontend/shop/Cart.jsp"><span>一般商品</span><span class="text-primary ycl-topBar-cartCntText" id="cartCnt1">${tBarCart1Cnt}</span></a></li>
		                        <li><a href="<%=request.getContextPath()%>/frontend/preproduct/shoppingCart.jsp"><span>預購商品</span><span class="text-primary ycl-topBar-cartCntText" id="cartCnt2">${tBarCart2Cnt}</span></a></li>
		                    </ul>
		                </li>
		
						<!--會員 -->
		            	<lem class="dropdown ycl-topBar-second">
		                    <a href="#" class="my-account space-right dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
								<c:choose>
									<c:when test="${not empty sessionScope.memVO}">
										<img class="ycl-topbar-memPhoto" src="<%=request.getContextPath()%>/members/headphotoHandler.do?action=getPic&mem_id=${sessionScope.memVO.mem_id}">
								    </c:when>      	
			                    	<c:otherwise>
			                    		<em class="fa fa-user"></em>
			                    	</c:otherwise>
		                    	</c:choose>     
		                    </a>
		                    <ul class="dropdown-menu animated zoomIn fast">
		                    	<c:choose>
		                    		<c:when test="${empty sessionScope.memVO}">
		                    			<li><a href="<%=request.getContextPath()%>/frontend/members/memLogin.jsp"><span>登入</span></a></li>
		                    		</c:when>
		                    		<c:otherwise>
		                    			<li><a href="<%=request.getContextPath()%>/frontend/members/memArea.jsp"><span>設定</span></a></li>
		                        		<li><a href="<%=request.getContextPath()%>/frontend/members/memLoginHandler.do?action=logout"><span>登出</span><em class="lnr lnr-exit ycl-topbar-icon"></em></a></li>
		                        	</c:otherwise>
		                    	</c:choose>
		                    </ul>
		                </li>
		            </div>
                </ul>
                
            </div><!--/ nav-collapse -->
        </div><!-- / container -->
    </nav>
    <!-- / nav -->
    
    <!-- header-banner -->
<!--     <div id="header-banner" style="margin-top:60px;"></div> -->
    <!-- / header-banner -->
	
	</body>
</html>