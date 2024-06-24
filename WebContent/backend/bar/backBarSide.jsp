<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.*"%>

<%
  String funcList = (String)session.getAttribute("funcList");
%>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <title>ArtsBlock 後台管理系統</title>
    
    <!-- Custom fonts for this template -->
    <link href="<%=request.getContextPath()%>/backend/template/css/sb-admin-2.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
    <!-- Custom styles for this page -->
    <link href="<%=request.getContextPath()%>/backend/template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/backend/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    
    <!-- favicon -->
	<link rel="icon" href="<%=request.getContextPath()%>/backend/template/img/favicon.png">
	
    <style type="text/css">/*瑞龍:此處為手動改CSS的地方*/
        .dataTables_wrapper label {
            
        }
        .img01{
        height: 75px;
        }
        .pagination
        {
        	display: none;
        }
        div.dataTables_wrapper div.dataTables_length label
        {
        	display: none;
        }
        
        
      .jeff-non-active a,
	  .jeff-non-active a:hover,
	  .jeff-non-active i{
	  	color:#858796 !important;
	  	cursor:not-allowed;
	  }


    </style>
</head>

<body id="page-top" style=" background-image: linear-gradient(to bottom, #D0D0D0, #D0D0D0); background-position: top left;  background-size: 100%;  background-repeat: repeat;">
   
        <!-- =========================================== Sidebar START ===========================================  -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
                
            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="<%=request.getContextPath()%>/backend/back-index.jsp">
                <div class="sidebar-brand-icon rotate-n-15">
                    <em class="fas fa-palette"></em>
                </div>
                <div class="sidebar-brand-text mx-3">ArtsBlock<sup></sup></div>
            </a>
            
            <!-- Divider -->
            <hr class="sidebar-divider my-0">
            
            <!-- Nav Item - Dashboard -->
            <lem class="nav-item active">
                <a class="nav-link" href="<%=request.getContextPath()%>/backend/back-index.jsp">
                    <em class="fas fa-fw fa-tachometer-alt"></em>
                    <span>控制台</span></a>
            </li>
            
            <!-- Divider -->
            <hr class="sidebar-divider">
            
            <!-- Heading -->
            <div class="sidebar-heading"> 社群 Community </div>
            
            <!-- Nav Item - Pages Collapse Menu -->
            <!-- 瑞龍:注意collapseOne -->
            
            
        <c:choose>
     	 <c:when test="${fn:contains(funcList, 'A')}"> 
     	 <lem class="nav-item">
     	 <a class="nav-link" href="<%=request.getContextPath()%>/backend/members/mem_selectPage.jsp">
     	 </c:when>
     	 <c:otherwise>
     	 <lem class="nav-item jeff-non-active">
     	 <a class="nav-link" href="#">
     	 </c:otherwise>
    	</c:choose>
          <em class="fas fa-users"></em>
          <span>會員查詢</span></a>
            </li>                   
            
        <c:choose>
     	 <c:when test="${fn:contains(funcList, 'B')}"> 
     	 <lem class="nav-item">
     	 <a class="nav-link" href="<%=request.getContextPath()%>/backend/welRecord/welRecord_selectPage.jsp">
     	 </c:when>
     	 <c:otherwise>
     	 <lem class="nav-item jeff-non-active">
     	 <a class="nav-link" href="#">
     	 </c:otherwise>
    	</c:choose>
          <em class="fas fa-store"></em>
          <span>錢包查詢</span></a>
            </li>           
                     
        <c:choose>
     	 <c:when test="${fn:contains(funcList, 'C')}"> 
     	 <lem class="nav-item">
     	 <a class="nav-link" href="<%=request.getContextPath()%>/backend/lv/listAllLv.jsp">
     	 </c:when>
     	 <c:otherwise>
     	 <lem class="nav-item jeff-non-active">
     	 <a class="nav-link" href="#">
     	 </c:otherwise>
    	</c:choose>
          <em class="fas fa-chess-queen"></em>
          <span>里程碑管理</span></a>
            </li>                
                                      
     	<c:choose>
     	 <c:when test="${fn:contains(funcList, 'D')}">
     	 <lem class="nav-item">
     	 <a class="nav-link collapsed" href="" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
     	 </c:when>
     	 <c:otherwise>
     	 <lem class="nav-item jeff-non-active">
     	 <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
     	 </c:otherwise>
     	</c:choose>    
          <em class="fas fa-flag-checkered"></em>
          <span>創意主題競賽</span>
                </a>
                <div id="collapseOne" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">主題競賽系統:</h6>
                        <a class="collapse-item" href="<%=request.getContextPath()%>/backend/event/TestListAll.jsp">創意主題管理</a>
                        <a class="collapse-item" href="<%=request.getContextPath()%>/backend/event_p/listAllPic.jsp">參賽作品管理</a>
                    </div>
                </div>
            </li>
            
            <!-- Divider -->
            <hr class="sidebar-divider">
            
            <!-- Heading -->
          
            <div class="sidebar-heading"> 商城 Commerce </div>
            
    	 <c:choose>
    	  <c:when test="${fn:contains(funcList, 'E')}">
    	  <lem class="nav-item">
    	  <a class="nav-link collapsed" href="" data-toggle="collapse" data-target="#collapse3" aria-expanded="true" aria-controls="collapse3">
    	  </c:when>
    	  <c:otherwise>
    	  <lem class="nav-item jeff-non-active">    	  
    	  <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse3" aria-expanded="true" aria-controls="collapse3">
    	  </c:otherwise>
   	    </c:choose>  
          <em class="fas fa-gavel"></em>
          <span>限時競標管理</span>
                </a>
                <div id="collapse3" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">限時競標系統:</h6>
                        <a class="collapse-item" href="<%=request.getContextPath()%>/backend/bidding/select_page.jsp">競標案件查詢</a>
                        <a class="collapse-item" href="<%=request.getContextPath()%>/backend/bidding/listAll.jsp">競標商品管理</a>
                    </div>
                </div>
            </li>
            
            <!-- Nav Item - Tables -->
        <c:choose>
     	 <c:when test="${fn:contains(funcList, 'F')}"> 
     	 <lem class="nav-item">
     	 <a class="nav-link collapsed" href="" data-toggle="collapse" data-target="#collapseI" aria-expanded="true" aria-controls="collapseI">
     	 </c:when>
     	 <c:otherwise>
     	 <lem class="nav-item jeff-non-active">
     	  <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseI" aria-expanded="true" aria-controls="collapseI">
     	 </c:otherwise>
    	</c:choose>
          <em class="fas fa-store"></em>
          <span>預購商品管理</span>
                </a>
                <div id="collapseI" class="collapse" aria-labelledby="headingI" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">預購系統:</h6>
                        <a class="collapse-item" href="<%=request.getContextPath()%>/backend/preproduct/backendindex.jsp">預購商品</a>
                        <a class="collapse-item" href="<%=request.getContextPath()%>/backend/preproduct/quantityOfSale.jsp">預購銷售折讓管理</a>
                    </div>
                </div>                
            </li>
            
            
            <!-- Nav Item - Tables -->
     	<c:choose>
      	 <c:when test="${fn:contains(funcList, 'G')}">
      	 <lem class="nav-item">
      	 <a class="nav-link" href="<%=request.getContextPath()%>/backend/prod/listAllProd.jsp">
      	 </c:when>
      	 <c:otherwise>
      	 <lem class="nav-item jeff-non-active">
      	 <a class="nav-link" href="#">
      	 </c:otherwise>
     	</c:choose>
          <em class="fas fa-gifts"></em>
          <span>商品管理</span></a>
            </li>
            
            <!-- Nav Item - Tables -->
<%--         <c:choose> --%>
<%--       	 <c:when test="${fn:contains(funcList, 'D')}"><lem class="nav-item"></c:when> --%>
<%--      	 <c:otherwise><lem class="nav-item jeff-non-active"></c:otherwise> --%>
<%--         </c:choose> --%>
<%--         <a class="nav-link" href="<%=request.getContextPath()%>/backend/material/listAllMat.jsp"> --%>
<!--           <em class="fas fa-tshirt"></em> -->
<!--           <span>素材管理</span></a> -->
<!--             </li> -->
          
        <c:choose>
      	 <c:when test="${fn:contains(funcList, 'H')}">
      	 <lem class="nav-item">
      	 <a class="nav-link collapsed" href="" data-toggle="collapse" data-target="#collapseXI" aria-expanded="true" aria-controls="collapseXI">
      	 </c:when>
     	 <c:otherwise>
     	 <lem class="nav-item jeff-non-active">
     	 <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseXI" aria-expanded="true" aria-controls="collapseXI">
     	 </c:otherwise>
        </c:choose>
          <em class="fas fa-tshirt"></em>
          <span>素材管理</span></a>     
                <div id="collapseXI" class="collapse" aria-labelledby="headingThree" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">素材系統:</h6>
                        <a class="collapse-item" href="<%=request.getContextPath()%>/backend/material/selectMat.jsp">素材查詢</a>
                        <a class="collapse-item" href="<%=request.getContextPath()%>/backend/material/addMat.jsp">素材新增</a>
                    </div>
                </div>
            </li>            
                            
            <!-- Nav Item - Tables -->
        <c:choose>
         <c:when test="${fn:contains(funcList, 'I')}">
         <lem class="nav-item">
         <a class="nav-link" href="<%=request.getContextPath()%>/backend/meeting/listAllMeeting_back.jsp">
         </c:when>
         <c:otherwise>
         <lem class="nav-item jeff-non-active">
         <a class="nav-link" href="#">
         </c:otherwise>
        </c:choose>
          <em class="far fa-calendar-alt"></em>
          <span>見面會管理</span></a>
            </li>
            
      	<c:choose>
     	 <c:when test="${fn:contains(funcList, 'J')}">
     	 <lem class="nav-item">
     	 <a class="nav-link collapsed" href="" data-toggle="collapse" data-target="#collapse4" aria-expanded="true" aria-controls="collapse4">
     	 </c:when>
         <c:otherwise>
         <lem class="nav-item jeff-non-active">
         <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse4" aria-expanded="true" aria-controls="collapse4">
         </c:otherwise>
     	</c:choose>
          <em class="fas fa-file-invoice-dollar"></em>
          <span>訂單管理</span>
                </a>
                <div id="collapse4" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">所有訂單系統:</h6>
                        <a class="collapse-item" href="<%=request.getContextPath()%>/backend/preproduct/preOrder.jsp">預購訂單管理</a>
                        <a class="collapse-item" href="<%=request.getContextPath()%>/backend/prod/listAllOrder.jsp">一般訂單管理</a>
                    </div>
                </div>
            </li>
            
            <!-- Divider -->
            <hr class="sidebar-divider">
            
            <!-- Heading -->
            <div class="sidebar-heading"> 系統 System </div>
<%--       	<c:choose> --%>
<%--       	 <c:when test="${fn:contains(funcList, 'K')}"> --%>
<!--       	 <lem class="nav-item"> -->
<%--       	 <a class="nav-link collapsed" href="<%=request.getContextPath()%>/backend/emp/test_power.jsp" data-toggle="collapse" data-target="#collapse5" aria-expanded="true" aria-controls="collapse5"> --%>
<%--       	 </c:when> --%>
<%--       	 <c:otherwise> --%>
<!--       	 <lem class="nav-item jeff-non-active"> -->
<!--       	 <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse5" aria-expanded="true" aria-controls="collapse5"> -->
<%--       	 </c:otherwise> --%>
<%--      	</c:choose> --%>
<!--           <em class="fas fa-ad"></em> -->
<!--           <span>廣告管理</span> -->
<!--                 </a> -->
<!--                 <div id="collapse5" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar"> -->
<!--                     <div class="bg-white py-2 collapse-inner rounded"> -->
<!--                         <h6 class="collapse-header">廣告系統:</h6> -->
<!--                         <a class="collapse-item" href="#">廣告管理</a> -->
<!--                         <a class="collapse-item" href="#">社群分析</a> -->
<!--                     </div> -->
<!--                 </div> -->
<!--             </li> -->
            
            <!-- Nav Item - Tables -->
<%--       	<c:choose> --%>
<%--       	 <c:when test="${fn:contains(funcList, 'K')}"> --%>
<!--       	 <lem class="nav-item"> -->
<%--       	 <a class="nav-link" href="<%=request.getContextPath()%>/backend/emp/test_power.jsp"> --%>
<%--       	 </c:when> --%>
<%--       	 <c:otherwise> --%>
<!--       	 <lem class="nav-item jeff-non-active"> -->
<!--       	 <a class="nav-link" href="#"> -->
<%--       	 </c:otherwise> --%>
<%--      	</c:choose> --%>
<!--           <em class="fas fa-poo"></em> -->
<!--           <span>作品檢舉</span></a> -->
<!--             </li> -->
            
      	<c:choose>
      	 <c:when test="${fn:contains(funcList, 'K')}">
      	 <lem class="nav-item">
      	  <a class="nav-link collapsed" href="" data-toggle="collapse" data-target="#collapseX" aria-expanded="true" aria-controls="collapseX">
      	 </c:when>
      	 <c:otherwise>
      	 <lem class="nav-item jeff-non-active">
      	  <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseX" aria-expanded="true" aria-controls="collapseX">
      	 </c:otherwise>
     	</c:choose>
          <em class="fas fa-user-shield"></em>
          <span>員工管理</span></a>     
                <div id="collapseX" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">員工系統:</h6>
                        <a class="collapse-item" href="<%=request.getContextPath()%>/backend/emp/listAllEmp.jsp">員工查詢</a>
                        <a class="collapse-item" href="<%=request.getContextPath()%>/backend/emp/addEmp.jsp">員工新增</a>
                    </div>
                </div>
            </li>
                      
           <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">
            
            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>
         </ul>
         
         
           <!-- Scroll to Top Button-->

        <!-- =========================================== Sidebar END   ===========================================  -->
        
        <!-- Bootstrap core JavaScript-->
        <script src="<%=request.getContextPath()%>/backend/template/vendor/jquery/jquery.min.js"></script>
        <script src="<%=request.getContextPath()%>/backend/template/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- Core plugin JavaScript-->
        <script src="<%=request.getContextPath()%>/backend/template/vendor/jquery-easing/jquery.easing.min.js"></script>
        <!-- Custom scripts for all pages-->
        <script src="<%=request.getContextPath()%>/backend/template/js/sb-admin-2.min.js"></script>
        <!-- Page level plugins -->
        <script src="<%=request.getContextPath()%>/backend/template/vendor/chart.js/Chart.min.js"></script>
        <!-- Page level custom scripts -->
        <script src="<%=request.getContextPath()%>/backend/template/js/demo/chart-area-demo.js"></script>
        <script src="<%=request.getContextPath()%>/backend/template/js/demo/chart-pie-demo.js"></script>

</body>

</html>