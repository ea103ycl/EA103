<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>

<!DOCTYPE html>
<html lang="en">


<%
	MemService memSvc = new MemService();
	List<MemVO> list = memSvc.getAll();
	pageContext.setAttribute("list", list);
%>



<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>listAllMembers</title>
<!-- Custom styles for this template -->
<link
	href="<%=request.getContextPath()%>/backend/template/css/sb-admin-2.min.css"
	rel="stylesheet">
<!-- Custom styles for this page -->
<!-- <link rel="stylesheet" -->
<!-- 	href="https://cdn.datatables.net/1.10.22/css/jquery.dataTables.min.css" /> -->
</head>

<body id="page-top">
	<!-- Page Wrapper -->
	<div id="wrapper">
		<!-- Begin Page Content -->
		<div class="container-fluid">

			<!-- DataTales Example -->
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-primary">會員總覽</h6>
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table id="example" class="hover" style="width: 100%">
							<thead>
								<tr>
									<th>會員編號</th>
									<th>會員帳號</th>
									<th>會員姓名</th>
									<th>性別</th>
									<th>生日</th>
									<th>居住縣市</th>
									<th>加入時間</th>
									<th>帳號狀態</th>
									<th>公開帳號狀態</th>
									<th>違規次數</th>

								</tr>
							</thead>

							<tbody>
								<c:forEach var="MemVO" items="${list}">

									<tr>
									<td>${MemVO.mem_id}</td>
										<td>${MemVO.m_accno}</td>
										<td>${MemVO.m_name}</td>
										<td>${MemVO.m_gender}</td>
										<td>${MemVO.m_bday}</td>
										<td>${MemVO.m_city}</td>
										<td>${MemVO.m_joindate}</td>
										<td>${MemVO.m_active}</td>
										<td>${MemVO.m_public}</td>
										<td>${MemVO.m_bancount}</td>
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
<!-- 	<script src="vendor/jquery/jquery.min.js"></script> -->
<!-- 	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script> -->
	<!-- Core plugin JavaScript-->
<!-- 	<script src="vendor/jquery-easing/jquery.easing.min.js"></script> -->
	<!-- Custom scripts for all pages-->
<!-- 	<script src="js/sb-admin-2.min.js"></script> -->
	<!-- Page level plugins -->
	<!--  <script src="vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="vendor/datatables/dataTables.bootstrap4.min.js"></script> -->
	<!-- Page level custom scripts -->
	<!--     <script src="js/demo/datatables-demo.js"></script> -->
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.5.1.js"></script>
	<!--引用dataTables.js-->
	<script type="text/javascript"
		src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript">
        $(document).ready(function() {
            $('#example').DataTable(
            		  "searching": false));
            
       
        };
        
      
    </script>
</body>

</html>