<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>





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


    </style>
</head>

<body id="page-top" style=" background-image: linear-gradient(to bottom, #D0D0D0, #D0D0D0); background-position: top left;  background-size: 100%;  background-repeat: repeat;">

                <nav class="navbar navbar-expand navbar-light bg-white topbar static-top shadow mb-4">
                
                    <!-- Sidebar Toggle (Topbar) -->
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <em class="fa fa-bars"></em>
                    </button>
                    
                    <!-- ======== Topbar Search START ========  -->
<!--                     <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search"> -->
<!--                         <div class="input-group"> -->
<!--                             <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2"> -->
<!--                             <div class="input-group-append"> -->
<!--                                 <button class="btn btn-primary" type="button"> -->
<!--                                     <em class="fas fa-search fa-sm"></em> -->
<!--                                 </button> -->
<!--                             </div> -->
<!--                         </div> -->
<!--                     </form>                                      -->
					<!-- ======== Topbar Search END ========  -->
                                        
                    <!-- ======== Topbar Right Items START ========  -->
                    <ul class="navbar-nav ml-auto">         
                                                     
<!--                         Nav Item - Search Dropdown (Visible Only XS) -->
<!--                         <lem class="nav-item dropdown no-arrow d-sm-none"> -->
<!--                             <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> -->
<!--                                 <em class="fas fa-search fa-fw"></em> -->
<!--                             </a> -->
<!--                             Dropdown - Messages -->
<!--                             <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown"> -->
<!--                                 <form class="form-inline mr-auto w-100 navbar-search"> -->
<!--                                     <div class="input-group"> -->
<!--                                         <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2"> -->
<!--                                         <div class="input-group-append"> -->
<!--                                             <button class="btn btn-primary" type="button"> -->
<!--                                                 <em class="fas fa-search fa-sm"></em> -->
<!--                                             </button> -->
<!--                                         </div> -->
<!--                                     </div> -->
<!--                                 </form> -->
<!--                             </div> -->
<!--                         </li> -->
                        
<!--                         Nav Item - Alerts -->
<!--                         <lem class="nav-item dropdown no-arrow mx-1"> -->
                        
<!--                             <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> -->
<!--                                 <em class="fas fa-bell fa-fw"></em> -->
<!--                                 Counter - Alerts -->
<!--                                 <span class="badge badge-danger badge-counter">99+</span> -->
<!--                             </a> -->
                            
<!--                             瑞龍:Dropdown信息欄 - Alerts下拉出來顯示 -->
<!--                             <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="alertsDropdown"> -->
                                
<!--                                 <h6 class="dropdown-header"> 信息中心 </h6> -->
                                
<!--                                 <a class="dropdown-item d-flex align-items-center" href="#">                                     -->
<!--                                     <div class="mr-3"> -->
<!--                                         <div class="icon-circle bg-primary"> -->
<!--                                             圓形icon版 -->
<!--                                             <em class="fas fa-file-alt text-white"></em> -->
<!--                                         </div> -->
<!--                                     </div> -->
                                    
<!--                                     <div> -->
<!--                                         <div class="small text-gray-500">December 12, 2019</div> -->
<!--                                         <span class="font-weight-bold">新增了一筆商品訂單!</span> -->
<!--                                     </div>                                     -->
<!--                                 </a> -->
                                
<!--                                 <a class="dropdown-item d-flex align-items-center" href="#"> -->
<!--                                     <div class="mr-3"> -->
<!--                                         <div class="icon-circle bg-success"> -->
<!--                                             <em class="fas fa-donate text-white"></em> -->
<!--                                         </div> -->
<!--                                     </div> -->
<!--                                     <div> -->
<!--                                         <div class="small text-gray-500">December 7, 2019</div> 競標結束，最終得標價:$290! -->
<!--                                     </div> -->
<!--                                 </a> -->
                                
<!--                                 <a class="dropdown-item d-flex align-items-center" href="#"> -->
<!--                                     <div class="mr-3"> -->
<!--                                         <div class="icon-circle bg-warning"> -->
<!--                                             <em class="fas fa-exclamation-triangle text-white"></em> -->
<!--                                         </div> -->
<!--                                     </div> -->
<!--                                     <div> -->
<!--                                         <div class="small text-gray-500">December 2, 2019</div> 有會員檢舉一項作品. -->
<!--                                     </div> -->
<!--                                 </a> -->
                                
<!--                                 <a class="dropdown-item text-center small text-gray-500" href="#">顯示全部訊息</a> -->
<!--                             </div> -->
<!--                         </li> -->
                        
                        <!-- === --------------------------------- ====  -->  
                        
                        <!-- Nav Item - Messages -->
                        <lem class="nav-item dropdown no-arrow">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small">員工編號<%=(String)session.getAttribute("emp_no") == null? "XXX": (String)session.getAttribute("emp_no")%></span>
                                <img class="img-profile rounded-circle" src="<%=request.getContextPath()%>/backend/emp/emp.pic?emp_no=<%=(String)session.getAttribute("emp_no")%>">
                            </a>
                           
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                                <a class="dropdown-item" href="#">
                                    <em class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></em> 查看權限 </a>
                                <a class="dropdown-item" href="#">
                                    <em class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></em> 設定 </a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="<%=request.getContextPath()%>/backend/emp/emp.do?action=logout" >
                                    <em class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></em> 登出 </a>
                            </div>
                        </li>
                        
                    </ul>
                	<!-- ======== Topbar Right Items END ========  -->
                </nav>               
	
	
</body>
