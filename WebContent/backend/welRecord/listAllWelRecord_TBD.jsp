<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wel_record.model.*"%>   
    
<!DOCTYPE html>
<html lang="en">


<%
    WelRecordService welRecordSvc = new WelRecordService();
    List<WelRecordVO> list = welRecordSvc.getAll();
    pageContext.setAttribute("list",list);
%>



<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>EA103WELRECORD</title>
    <!-- Custom styles for this template -->
    <link href="<%=request.getContextPath()%>/backend/template/css/sb-admin-2.min.css" rel="stylesheet">
    <!-- Custom styles for this page -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.22/css/jquery.dataTables.min.css" />
</head>

<body id="page-top">
    <!-- Page Wrapper -->
    <div id="wrapper">
        <!-- Begin Page Content -->
        <div class="container-fluid">
            <!-- Page Heading -->
            <h1 class="h3 mb-2 text-gray-800" style="font-family:Microsoft JhengHei;font-weight:bold;">
                                    錢包交易紀錄</h1>
            <p class="mb-4">在這邊您可以看到全部會員的交易紀錄</p>
            <!-- DataTales Example -->
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">交易總覽</h6>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table id="example" class="hover" style="width:100%">
                            <thead>
                                <tr>
                                    <th>交易流水號</th>
                                    <th>會員編號</th>
                                    <th>交易來源</th>
                                    <th>來源訂單編號</th>
                                    <th>交易時間</th>
                                    <th>交易金額</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <th>交易流水號</th>
                                    <th>會員編號</th>
                                    <th>交易來源</th>
                                    <th>來源訂單編號</th>
                                    <th>交易時間</th>
                                    <th>交易金額</th>
                                </tr>
                            </tfoot>
                            <tbody>
     <c:forEach var="welRecordVO" items="${list}">
		
                                <tr>
            <td>${welRecordVO.tns_id}</td>
			<td>${welRecordVO.mem_id}</td>
			<td>${welRecordVO.tns_src}</td>
			<td>${welRecordVO.order_id}</td>
			<td>${welRecordVO.tns_time}</td>
			<td>${welRecordVO.tns_amount}</td> 
		
			</tr>
			</c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
    </div>
    <!-- End of Main Content -->
    <!-- Footer -->
    <footer class="sticky-footer bg-white">
        <div class="container my-auto">
            <div class="copyright text-center my-auto">
                <span>Copyright &copy; CarolineKaku 2020</span>
            </div>
        </div>
    </footer>
    <!-- End of Footer -->
    </div>
    <!-- End of Content Wrapper -->
    </div>
    <!-- End of Page Wrapper -->
    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>
    <!-- Page level plugins -->
    <!--  <script src="vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="vendor/datatables/dataTables.bootstrap4.min.js"></script> -->
    <!-- Page level custom scripts -->
    <!--     <script src="js/demo/datatables-demo.js"></script> -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <!--引用dataTables.js-->
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#example').DataTable(
            		searching : false);
        });
    </script>
</body>

</html>