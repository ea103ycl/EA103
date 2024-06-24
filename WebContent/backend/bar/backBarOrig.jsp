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

    <!-- Page Wrapper -->
    <div id="wrapper" style=" background-image: linear-gradient(to bottom, #D0D0D0, transparent); background-position: top left;  background-size: 100%;  background-repeat: repeat;">
    
        <!-- =========================================== Sidebar START ===========================================  -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar" style=" background-image: linear-gradient(to bottom, rgba(0,0,0,0.2), #EA7500); background-position: top left;  background-size: 100%;  background-repeat: repeat;">
                
            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
                <div class="sidebar-brand-icon rotate-n-15">
                    <em class="fas fa-palette"></em>
                </div>
                <div class="sidebar-brand-text mx-3">ArtsBlock<sup></sup></div>
            </a>
            
            <!-- Divider -->
            <hr class="sidebar-divider my-0">
            
            <!-- Nav Item - Dashboard -->
            <lem class="nav-item active">
                <a class="nav-link" href="#">
                    <em class="fas fa-fw fa-tachometer-alt"></em>
                    <span>控制台</span></a>
            </li>
            
            <!-- Divider -->
            <hr class="sidebar-divider">
            
            <!-- Heading -->
            <div class="sidebar-heading"> 社群 Community </div>
            
            <!-- Nav Item - Pages Collapse Menu -->
            <!-- 瑞龍:注意collapseOne -->
            <lem class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                    <em class="fas fa-users"></em>
                    <span>會員管理</span>
                </a>
                <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">會員系統:</h6>
                        <a class="collapse-item" href="#">會員權限</a>
                        <a class="collapse-item" href="#">會員查詢</a>
                        <a class="collapse-item" href="#">里程碑</a>
                        <a class="collapse-item" href="#">交易紀錄</a>
                    </div>
                </div>
            </li>
            
            <lem class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                    <em class="fas fa-flag-checkered"></em>
                    <span>創意主題競賽</span>
                </a>
                <div id="collapseOne" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">主題競賽系統:</h6>
                        <a class="collapse-item" href="#">創意主題管理</a>
                        <a class="collapse-item" href="#">參賽作品管理</a>
                    </div>
                </div>
            </li>
            
            <!-- Divider -->
            <hr class="sidebar-divider">
            
            <!-- Heading -->
            <div class="sidebar-heading"> 商城 Commerce </div>
            <lem class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse3" aria-expanded="true" aria-controls="collapse3">
                    <em class="fas fa-gavel"></em>
                    <span>限時競標管理</span>
                </a>
                <div id="collapse3" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">限時競標系統:</h6>
                        <a class="collapse-item" href="#">競標商品管理</a>
                        <a class="collapse-item" href="#">競標案件查詢</a>
                    </div>
                </div>
            </li>
            
            <!-- Nav Item - Tables -->
            <lem class="nav-item">
                <a class="nav-link" href="javascript:window.location.href='preproduct.jsp'"><!-- 瑞龍:我的預購JSP頁面跳轉 -->
                    <em class="fas fa-store"></em>
                    <span>預購商品管理</span></a>
            </li>
            
            <!-- Nav Item - Tables -->
            <lem class="nav-item">
                <a class="nav-link" href="#"> 
                    <em class="fas fa-gifts"></em>
                    <span>商品管理</span></a>
            </li>
            
            <!-- Nav Item - Tables -->
            <lem class="nav-item">
                <a class="nav-link" href="#">
                    <em class="fas fa-tshirt"></em>
                    <span>素材管理</span></a>
            </li>
            
            <!-- Nav Item - Tables -->
            <lem class="nav-item">
                <a class="nav-link" href="#">
                    <em class="far fa-calendar-alt"></em>
                    <span>見面會管理</span></a>
            </li>
            
            <lem class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse4" aria-expanded="true" aria-controls="collapse4">
                    <em class="fas fa-file-invoice-dollar"></em>
                    <span>訂單管理</span>
                </a>
                <div id="collapse4" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">所有訂單系統:</h6>
                        <a class="collapse-item" href="#">競標訂單管理</a>
                        <a class="collapse-item" href="#">預購訂單管理</a>
                        <a class="collapse-item" href="#">一般訂單管理</a>
                        <a class="collapse-item" href="#">見面會訂單管理</a>
                        <a class="collapse-item" href="#">訂單問題回覆</a>
                    </div>
                </div>
            </li>
            
            <!-- Divider -->
            <hr class="sidebar-divider">
            
            <!-- Heading -->
            <div class="sidebar-heading"> 系統 System </div>
            <lem class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse5" aria-expanded="true" aria-controls="collapse5">
                    <em class="fas fa-ad"></em>
                    <span>廣告管理</span>
                </a>
                <div id="collapse5" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">廣告系統:</h6>
                        <a class="collapse-item" href="#">廣告管理</a>
                        <a class="collapse-item" href="#">社群分析</a>
                    </div>
                </div>
            </li>
            
            <!-- Nav Item - Tables -->
            <lem class="nav-item">
                <a class="nav-link" href="#">
                    <em class="fas fa-poo"></em>
                    <span>作品檢舉</span></a>
            </li>
            
            <lem class="nav-item">
                <a class="nav-link" href="#">
                    <em class="fas fa-user-shield"></em>
                    <span>員工管理</span></a>
            </li>
            
            <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">
            
            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>
       
        
         </ul>
        <!-- =========================================== Sidebar END   ===========================================  -->
        
        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">
        
            <!-- Main Content -->
            <div id="content" style=" background-image: linear-gradient(to bottom, #D0D0D0, #D0D0D0); background-position: top left;  background-size: 100%;  background-repeat: repeat;">
            
                <!-- =========================================== Topbar START ===========================================  -->
                <nav class="navbar navbar-expand navbar-light bg-white topbar static-top shadow mb-4">
                
                    <!-- Sidebar Toggle (Topbar) -->
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <em class="fa fa-bars"></em>
                    </button>
                    
                    
                    
                    <!-- ======== Topbar Search START ========  -->
                    <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                        <div class="input-group">
                            <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                            <div class="input-group-append">
                                <button class="btn btn-primary" type="button">
                                    <em class="fas fa-search fa-sm"></em>
                                </button>
                            </div>
                        </div>
                    </form>                                     
					<!-- ======== Topbar Search END ========  -->
                                        
                    <!-- ======== Topbar Right Items START ========  -->
                    <ul class="navbar-nav ml-auto">         
                                                     
                        <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                        <lem class="nav-item dropdown no-arrow d-sm-none">
                            <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <em class="fas fa-search fa-fw"></em>
                            </a>
                            <!-- Dropdown - Messages -->
                            <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown">
                                <form class="form-inline mr-auto w-100 navbar-search">
                                    <div class="input-group">
                                        <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                                        <div class="input-group-append">
                                            <button class="btn btn-primary" type="button">
                                                <em class="fas fa-search fa-sm"></em>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </li>
                        
                        <!-- Nav Item - Alerts -->
                        <lem class="nav-item dropdown no-arrow mx-1">
                        
                            <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <em class="fas fa-bell fa-fw"></em>
                                <!-- Counter - Alerts -->
                                <span class="badge badge-danger badge-counter">99+</span>
                            </a>
                            
                            <!-- 瑞龍:Dropdown信息欄 - Alerts下拉出來顯示 -->
                            <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="alertsDropdown">
                                
                                <h6 class="dropdown-header"> 信息中心 </h6>
                                
                                <a class="dropdown-item d-flex align-items-center" href="#">                                    
                                    <div class="mr-3">
                                        <div class="icon-circle bg-primary">
                                            <!-- 圓形icon版 -->
                                            <em class="fas fa-file-alt text-white"></em>
                                        </div>
                                    </div>
                                    
                                    <div>
                                        <div class="small text-gray-500">December 12, 2019</div>
                                        <span class="font-weight-bold">新增了一筆商品訂單!</span>
                                    </div>                                    
                                </a>
                                
                                <a class="dropdown-item d-flex align-items-center" href="#">
                                    <div class="mr-3">
                                        <div class="icon-circle bg-success">
                                            <em class="fas fa-donate text-white"></em>
                                        </div>
                                    </div>
                                    <div>
                                        <div class="small text-gray-500">December 7, 2019</div> 競標結束，最終得標價:$290!
                                    </div>
                                </a>
                                
                                <a class="dropdown-item d-flex align-items-center" href="#">
                                    <div class="mr-3">
                                        <div class="icon-circle bg-warning">
                                            <em class="fas fa-exclamation-triangle text-white"></em>
                                        </div>
                                    </div>
                                    <div>
                                        <div class="small text-gray-500">December 2, 2019</div> 有會員檢舉一項作品.
                                    </div>
                                </a>
                                
                                <a class="dropdown-item text-center small text-gray-500" href="#">顯示全部訊息</a>
                            </div>
                        </li>
                        
                        <!-- === --------------------------------- ====  -->  
                        
                        <!-- Nav Item - Messages -->
                        <lem class="nav-item dropdown no-arrow">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small">員工編號XXX</span>
                                <img class="img-profile rounded-circle" src="<%=request.getContextPath()%>/backend/bar/worker01.jpg">
                            </a>
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                                <a class="dropdown-item" href="#">
                                    <em class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></em> 查看權限 </a>
                                <a class="dropdown-item" href="#">
                                    <em class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></em> 設定 </a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                    <em class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></em> 登出 </a>
                            </div>
                        </li>
                        
                    </ul>
                	<!-- ======== Topbar Right Items END ========  -->
                </nav>
                <!-- =========================================== Topbar END ===========================================  -->
                
			</div>
		</div>
		
                <script>
			        window.onload = function() {
			            var display_area = document.getElementById("display_area");
			            var btn = document.getElementById("btn");
			            btn.onclick = function() {
			                var content_val = document.getElementById("content").value;
			                display_area.append(content_val);
			            };
			        }
                </script>
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
<!--                 <pingendo onclick="window.open('https://pingendo.com/', '_blank')" style="cursor:pointer;position: fixed;bottom: 20px;right:20px;padding:4px;background-color: #00b0eb;border-radius: 8px; width:220px;display:flex;flex-direction:row;align-items:center;justify-content:center;font-size:14px;color:white">Made with Pingendo Free&nbsp;&nbsp;<img src="https://pingendo.com/site-assets/Pingendo_logo_big.png" class="d-block" alt="Pingendo logo" height="16"></pingendo> -->

</body>

</html>