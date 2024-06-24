<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wel_record.model.*"%>
<!DOCTYPE html>
<html lang="en">

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
        <h2>為您轉至綠界中...</h2>
        
<%
	String form = (String)request.getAttribute("form");
%>
<%=form%>

    </div>
</div><!-- / container -->

<!-- / content -->

<!-- scroll to top -->
<a href="#top" class="scroll-to-top page-scroll is-hidden" data-nav-status="toggle"><em class="fa fa-angle-up"></em></a>
<!-- / scroll to top -->
   
    
    
    
    
    




</body>

</html>