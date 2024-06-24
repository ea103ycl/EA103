<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.bidding.model.*"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>SB Admin 2 - Tables</title>
<!-- Custom fonts for this template -->
<link
	href="<%=request.getContextPath()%>/backend/bidding/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">
<!-- Custom styles for this template -->
<link
	href="<%=request.getContextPath()%>/backend/bidding/css/sb-admin-2.min.css"
	rel="stylesheet">
<!-- Custom styles for this page -->
<link
	href="<%=request.getContextPath()%>/backend/bidding/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">
</head>

<body id="page-top">
	<!-- Page Wrapper -->
	<div id="wrapper">
		<!-- Sidebar -->
	<%@include file="/backend/bar/backBarSide.jsp" %>
		<!-- Divider -->
		<hr class="sidebar-divider d-none d-md-block">
		<!-- Sidebar Toggler (Sidebar) -->
		<!-- End of Sidebar -->
		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">
			<!-- Main Content -->
			<div id="content">
				<!-- Topbar -->
				 <%@include file="/backend/bar/backBarTop.jsp" %>
				<!-- End of Topbar -->
				<!-- ============================================================ -->


				<!-- Begin Page Content -->

				<div class="container-fluid">
					<!-- Page Heading -->
					<h1 class="h3 mb-2 text-gray-800">限時競標管理</h1>
					<p class="mb-4">競標管理訂單明細</p>
					<a
						href="<%=request.getContextPath()%>/backend/bidding//select_page.jsp">回首頁</a>
					<!-- DataTales Example -->
					<div class="card shadow mb-4">
						<div class="card-header py-3">
							<h6 class="m-0 font-weight-bold text-primary">資料查詢:</h6>
						</div>
						<div class="card-header py-3">
							<c:if test="${not empty errorMsgs}">
								<font style="color: red">請修正以下錯誤:</font>
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color: red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">
									<thead>
										<tr>
											<th>競標活動流水號</th>
											<th>會員編號</th>
											<th>競標作品編號</th>
											<th>得標金額</th>
											<th>競標開始時間</th>
											<th>競標結束時間</th>
											<th>競標狀態</th>
											<th>訂單狀態</th>
											<th>付款狀態</th>
											<th>郵遞區號</th>
											<th>收件人姓名</th>
											<th>收件人電話</th>
											<th>收件地址</th>
											<th>資料修改</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>競標活動流水號</th>
											<th>會員編號</th>
											<th>競標作品編號</th>
											<th>得標金額</th>
											<th>競標開始時間</th>
											<th>競標結束時間</th>
											<th>競標狀態</th>
											<th>訂單狀態</th>
											<th>付款狀態</th>
											<th>郵遞區號</th>
											<th>收件人姓名</th>
											<th>收件人電話</th>
											<th>收件地址</th>
											<th>資料修改</th>
										</tr>
									</tfoot>
									<tbody>
										<tr>
											<c:choose>
												<c:when test="${bVO.bdNo!=null}">
													<td>${bVO.bdNo}</td>
													<td>${bVO.memId}</td>
													<td>${bVO.bdProdNo}</td>
													<td>${bVO.bdPrice}</td>
													<td>${bVO.bdDateStr}</td>
													<td>${bVO.bdDateEnd}</td>
													<td>${bVO.bdStatus}</td>
													<td>${bVO.bdOrdStatus}</td>
													<td>${bVO.pmtStatus}</td>
													<td>${bVO.bdZip}</td>
													<td>${bVO.bdName}</td>
													<td>${bVO.bdPhone}</td>
													<td>${bVO.bdAddr}</td>
													<td>
														<FORM METHOD="post"
															ACTION="<%=request.getContextPath()%>/bidding/BiddingServlet"
															style="margin-bottom: 0px;">
															<input class=".btn-icon-split" type="submit" value="修改">
															<input type="hidden" name="bdNo" value="${bVO.bdNo}">
															<input type="hidden" name="action"
																value="getOneForUpdate">
														</FORM>
													</td>
												</c:when>
												<c:otherwise>
													<td colspan="13" style="text-align: center;"><h2>查無此筆資料</h2>
													<td>
												</c:otherwise>
											</c:choose>
										</tr>
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
		      <%@include file="/backend/bar/footer.jsp" %>
			<!-- End of Footer -->
		</div>
		<!-- End of Content Wrapper -->
	</div>
	<!-- End of Page Wrapper -->
	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></em>
	</a>
	<!-- Logout Modal-->
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">Select "Logout" below if you are ready
					to end your current session.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="login.html">Logout</a>
				</div>
			</div>
		</div>
	</div>
	<!-- Bootstrap core JavaScript-->
	<script
		src="<%=request.getContextPath()%>/backend/bidding/vendor/jquery/jquery.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/backend/bidding/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- Core plugin JavaScript-->
	<script
		src="<%=request.getContextPath()%>/backend/bidding/vendor/jquery-easing/jquery.easing.min.js"></script>
	<!-- Custom scripts for all pages-->
	<script
		src="<%=request.getContextPath()%>/backend/bidding/js/sb-admin-2.min.js"></script>
	<!-- Page level plugins -->
	<script
		src="<%=request.getContextPath()%>/backend/bidding/vendor/datatables/jquery.dataTables.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/backend/bidding/vendor/datatables/dataTables.bootstrap4.min.js"></script>
	<!-- Page level custom scripts -->
	<script
		src="<%=request.getContextPath()%>/backend/bidding/js/demo/datatables-demo.js"></script>

</body>

</html>