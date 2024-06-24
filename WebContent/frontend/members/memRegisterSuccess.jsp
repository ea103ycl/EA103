<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<!DOCTYPE html>
<html lang="en">
<%
	MemVO memVO = (MemVO) request.getAttribute("memVO");
%>

<head>

		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
	
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
    
    <%@include file="/frontend/bar/frontBarTop.jsp"%>
      
    </header>
    <!-- / header -->




<!-- content -->

<div id="page-content" class="container" style="height:90%">
    <div class="pnf-content text-center" style="margin-top:100px">
        <h2>WAIT...THE LAST STEP</h2>
        <p class="lead"><strong>請至您的電子信箱收取驗證信, 即可開始探索ArtsBlock！</strong></p>
        
       
        <p><a href="<%=request.getContextPath()%>/frontend/members/mem.do?action=sendVerifyAgain" class="btn btn-lg btn-primary-filled btn-rounded"><span>收不到驗證信? 點此重新發送</span></a></p>
        <h6>OR</h6>
        <p><a href="<%=request.getContextPath()%>/frontend/front_index.jsp" class="btn btn-lg btn-default-filled btn-rounded"><em class="fa fa-long-arrow-right"></em><span>先逛逛首頁</span></a></p>
    </div>
</div><!-- / container -->

<!-- / content -->

<!-- scroll to top -->
<a href="#top" class="scroll-to-top page-scroll is-hidden" data-nav-status="toggle"><em class="fa fa-angle-up"></em></a>
<!-- / scroll to top -->
   
    
    
    
    
    
   
  
  <!-- footer -->
    <footer class="light-footer" >
     	<%@include file="/frontend/bar/frontBarFooter.jsp"%>
        <div class="footer-info">
            <div class="container" style="text-align:center">
                <div class="copyright" >
                    <p><strong>© ArtsBlock 2020</strong></p>
                </div>
             
            </div><!-- / container -->
        </div><!-- / footer-info -->
    </footer>
    <!-- / footer -->


</body>

</html>