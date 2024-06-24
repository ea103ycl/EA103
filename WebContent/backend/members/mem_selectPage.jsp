<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>



<!DOCTYPE html>
<html>


<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/material-design-iconic-font/2.2.0/css/material-design-iconic-font.min.css">
<link
	href="<%=request.getContextPath()%>/backend/template/css/sb-admin-2.min.css"
	rel="stylesheet">

<title>我是標題</title>


<style>
#result {
	margin-top: 20px;
}

tr, td, th {
	text-align: center;
}
</style>

</head>



<body id="page-top">

	<div id="wrapper">

		<%@include file="/backend/bar/backBarSide.jsp"%>

		<div id="content-wrapper" class="d-flex flex-column">

			<div id="content">

				<!-- Topbar -->
				<%@include file="/backend/bar/backBarTop.jsp"%>

				<div class="container-fluid">
					<!--=====自定義內容start ================================================== -->

					<%-- 頁面標題 --%>
					<h1 class="h3 mb-2 text-gray-800">會員查詢及管理</h1>

					<%-- 錯誤表列 --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>


					<%-- 資料內容 --%>
					<div class="row" style="margin-bottom: 20px">
					<c:if test="${not empty applicationScope.onlineCount}">
						<div class="col-md-6">
							<div class="card border-left-primary shadow h-100 py-2">
								<div class="card-body">
									<div class="row no-gutters align-items-center">
										<div class="col mr-2">
											<div
												class="text-xs font-weight-bold text-primary text-uppercase mb-1">在線人數
											</div>
											<div class="h5 mb-0 font-weight-bold text-gray-800"
												id="onlineAmount">${applicationScope.onlineCount}</div>
										</div>
										<div class="col-auto">
											<em class="fas fa-user-friends fa-2x text-gray-300"></em>
										</div>
									</div>
								</div>
							</div>
						</div>
						</c:if>
						<c:if test="${not empty applicationScope.onLineList}">
						<div class="col-md-6">
							<div class="card border-left-primary shadow h-100 py-2">
								<div class="card-body">
									<div class="row no-gutters align-items-center">
										<div class="col mr-2">
											<div
												class="text-xs font-weight-bold text-primary text-uppercase mb-1">在線使用者名稱
											</div>

											
												<div>
													<c:forEach var="username" items="${applicationScope.onLineList}">
                                                     ${username}<br>
													</c:forEach>
												</div>
										
										</div>
										<div class="col-auto">
											<em class="fas fa-user-friends fa-2x text-gray-300"></em>
										</div>
									</div>
								</div>
							</div>
						</div>
						</c:if>
					</div>
						
				
<div class="accordion" id="accordionExample">
		<div class="card">
						<div class="card-header" id="headingOne">
							<h2 class="mb-0">
								<button class="btn btn-link" type="button"
									data-toggle="collapse" data-target="#collapseOne1"
									aria-expanded="true" aria-controls="collapseOne"
									id="simpleSearch">
									<em class="zmdi zmdi-search"></em>&ensp;簡易查詢(單一會員帳號、名稱或編號)
								</button>
							</h2>
						</div>

						<div id="collapseOne1" class="collapse"
							aria-labelledby="headingOne" data-parent="#accordionExample">
							<div class="card-body">
								<!-- -------------------------簡易查詢卡片內容開始------------------------ -->

								<form>
									<div class="row">
										<div class="col col-md-8 offset-md-2 ">
											<input type="text" class="form-control" id="accNameOrId"
												placeholder="請輸入會員帳號、會員名稱或會員編號">

										</div>
										<div class="col col-md-2">
											<button class="btn btn-primary" type="button"
												id="getOneById_Name_Accout">搜尋</button>
										</div>
									</div>

									<div class="row"></div>
									<input type="hidden" name="action" value="getOneByIdNameAccout">
								</form>

								<!-- -------------------------簡易查詢卡片內容結束------------------------ -->
							</div>
						</div>
					</div>
				
					<div class="card">
						<div class="card-header" id="headingThree">
							<h2 class="mb-0">
								<button class="btn btn-link collapsed" type="button"
									data-toggle="collapse" data-target="#collapseThree"
									aria-expanded="false" aria-controls="collapseThree"
									id="getAllMembers">
									<em class="zmdi zmdi-search"></em>&ensp;全部會員查詢
								</button>
							</h2>
						</div>
						<div id="collapseThree" class="collapse"
							aria-labelledby="headingThree" data-parent="#accordionExample">
						</div>
					</div>
			
<!-- 					<div class="card"> -->
<!-- 						<div class="card-header" id="headingTwo"> -->
<!-- 							<h2 class="mb-0"> -->
<!-- 								<button class="btn btn-link collapsed" type="button" -->
<!-- 									data-toggle="collapse" data-target="#collapseTwo2" -->
<!-- 									aria-expanded="false" aria-controls="collapseTwo" -->
<!-- 									id="muti-query"> -->
<!-- 									<em class="zmdi zmdi-search"></em>&ensp;進階查詢(居住地、性別、加入時間等) -->
<!-- 								</button> -->
<!-- 							</h2> -->
<!-- 						</div> -->
<!-- 						<div id="collapseTwo2" class="collapse" -->
<!-- 							aria-labelledby="headingTwo" data-parent="#accordionExample"> -->
<!-- 							<div class="card-body"> -->
<!-- 								-------------------------複合查詢卡片內容開始------------------------ -->
<!-- 								<form> -->
<!-- 									<div class="row"> -->

<!-- 										<p class="text-left" -->
<!-- 											style="margin-left: 20px; font-size: 18px; color: #5B5B5B">居住地</p> -->

<!-- 									</div> -->

<!-- 									<div class="row"> -->
<!-- 										<div class="col col-md-2"> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck1"> <label class="form-check-label" -->
<!-- 													for="defaultCheck1"> 台北市 </label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck2"> <label class="form-check-label" -->
<!-- 													for="defaultCheck2"> 基隆市 </label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck3"> <label class="form-check-label" -->
<!-- 													for="defaultCheck3"> 新北市 </label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck4"> <label class="form-check-label" -->
<!-- 													for="defaultCheck4"> 連江縣 </label> -->

<!-- 											</div> -->

<!-- 										</div> -->
<!-- 										------------------------縣市第二區------------------------ -->
<!-- 										<div class="col col-md-2"> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck5"> <label class="form-check-label" -->
<!-- 													for="defaultCheck5"> 宜蘭縣 </label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck6"> <label class="form-check-label" -->
<!-- 													for="defaultCheck6"> 新竹市 </label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck7"> <label class="form-check-label" -->
<!-- 													for="defaultCheck7"> 新竹縣 </label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck8"> <label class="form-check-label" -->
<!-- 													for="defaultCheck8"> 桃園市 </label> -->

<!-- 											</div> -->
<!-- 										</div> -->
<!-- 										------------------------縣市第三區------------------------ -->
<!-- 										<div class="col col-md-2"> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck9"> <label class="form-check-label" -->
<!-- 													for="defaultCheck9"> 苗栗縣 </label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck10"> <label -->
<!-- 													class="form-check-label" for="defaultCheck10"> 台中市 -->
<!-- 												</label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck11"> <label -->
<!-- 													class="form-check-label" for="defaultCheck11"> 彰化縣 -->
<!-- 												</label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck12"> <label -->
<!-- 													class="form-check-label" for="defaultCheck12"> 南投縣 -->
<!-- 												</label> -->

<!-- 											</div> -->
<!-- 										</div> -->
<!-- 										------------------------縣市第四區------------------------ -->
<!-- 										<div class="col col-md-2"> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck13"> <label -->
<!-- 													class="form-check-label" for="defaultCheck13"> 嘉義市 -->
<!-- 												</label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck14"> <label -->
<!-- 													class="form-check-label" for="defaultCheck14"> 嘉義縣 -->
<!-- 												</label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck22"> <label -->
<!-- 													class="form-check-label" for="defaultCheck22"> 雲林縣 -->
<!-- 												</label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck15"> <label -->
<!-- 													class="form-check-label" for="defaultCheck15"> 台南市 -->
<!-- 												</label> -->

<!-- 											</div> -->
<!-- 										</div> -->
<!-- 										------------------------縣市第五區------------------------ -->
<!-- 										<div class="col col-md-2"> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck16"> <label -->
<!-- 													class="form-check-label" for="defaultCheck16"> 高雄市 -->
<!-- 												</label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck17"> <label -->
<!-- 													class="form-check-label" for="defaultCheck17"> 澎湖縣 -->
<!-- 												</label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck18"> <label -->
<!-- 													class="form-check-label" for="defaultCheck18"> 金門縣 -->
<!-- 												</label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck19"> <label -->
<!-- 													class="form-check-label" for="defaultCheck19"> 屏東縣 -->
<!-- 												</label> -->

<!-- 											</div> -->
<!-- 										</div> -->
<!-- 										------------------------縣市第6區------------------------ -->
<!-- 										<div class="col col-md-2"> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck20"> <label -->
<!-- 													class="form-check-label" for="defaultCheck20"> 台東縣 -->
<!-- 												</label> -->

<!-- 											</div> -->
<!-- 											<div class="form-check"> -->
<!-- 												<input class="form-check-input" type="checkbox" value="" -->
<!-- 													id="defaultCheck21"> <label -->
<!-- 													class="form-check-label" for="defaultCheck21"> 花蓮縣 -->
<!-- 												</label> -->

<!-- 											</div> -->

<!-- 										</div> -->

<!-- 									</div> -->

<!-- 									<div class="row"> -->
<!-- 										<br> -->
<!-- 										<p class="text-left" -->
<!-- 											style="margin-left: 20px; margin-top: 20px; font-size: 18px; color: #5B5B5B">性別</p> -->

<!-- 									</div> -->
<!-- 									<div class="row" style="margin-left: 20px;"> -->

<!-- 										<div class="form-check"> -->
<!-- 											<input class="form-check-input" type="checkbox" value="" -->
<!-- 												id="male"><label class="form-check-label" for="male"> -->
<!-- 												男性 </label> -->

<!-- 										</div> -->
<!-- 										<div class="form-check"> -->
<!-- 											&nbsp; <input class="form-check-input" type="checkbox" -->
<!-- 												value="" id="female"><label class="form-check-label" -->
<!-- 												for="female">女性</label> -->

<!-- 										</div> -->

<!-- 									</div> -->

<!-- 									<div class="row"> -->
<!-- 										<br> -->
<!-- 										<p class="text-left" -->
<!-- 											style="margin-left: 20px; margin-top: 20px; font-size: 18px; color: #5B5B5B">加入時間</p> -->

<!-- 									</div> -->
<!-- 									<div class="row"> -->

<!-- 										<div class="col col-md-6">從</div> -->
<!-- 										<div class="col col-md-6">到</div> -->
<!-- 									</div> -->




<!-- 									<div class="row"> -->
<!-- 										<br> -->
<!-- 										<p class="text-left" -->
<!-- 											style="margin-left: 20px; margin-top: 20px; font-size: 18px; color: #5B5B5B">總儲值金額</p> -->

<!-- 									</div> -->
<!-- 									<div class="row"> -->
<!-- 										<div class="col col-md-1" style="text-align: right">從</div> -->
<!-- 										<div class="col col-md-4"> -->
<!-- 											<div class="form-group"> -->
<!-- 												<input type="number" class="form-control" id="money" -->
<!-- 													placeholder="0"> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 										<div class="col col-md-1" style="text-align: right">到</div> -->
<!-- 										<div class="col col-md-4"> -->
<!-- 											<div class="form-group"> -->
<!-- 												<input type="number" class="form-control" id="money" -->
<!-- 													placeholder="2000"> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</div> -->
						
<!-- 							<div class="row" style="margin-top: 20px;"> -->
<!-- 								<div class="col col-md-12" style="text-align: center"> -->
<!-- 									<button class="btn btn-primary" type="submit">搜尋</button> -->
<!-- 									<br> <br> -->
<!-- 								</div> -->
							
<!-- 							</div> -->
<!-- 						</form> -->
							</div>
						
						
							<!-- -----------------------複合查詢卡片內容結束-------------------------- -->
						
							<div class="container-fluid" id=result>
						</div>
<!-- 					</div> -->
				</div>
		
			<!-- -----------------------查詢結果開始-------------------------- -->

		

		
			
			</div>
				<!-- -----------------------查詢結果結束-------------------------- -->
			<%@include file="/backend/bar/footer.jsp"%>
		</div>

	</div>

	<!--JavaScript內容-->

	<!--引用dataTables.js-->
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.5.1.js"></script>
	<!--引用dataTables.js-->
	<script type="text/javascript"
		src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script>

	<!--     </script> -->

	<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script>
	
	
	function getAll() {
		
		 $("#result").empty();
	    	$.ajax({
	    		url: "<%=request.getContextPath()%>/frontend/members/mem.do",
					type : "POST",
					data : {
						action : "getAllMembers",
					},
					success : function(data) {

						//#("queryResult").html(data);

						var memVO = JSON.parse(data);
						
						var str = getStr(memVO);
						
						console.log(data);
						
			            $("#result").html(str);
						 
					}
				});
		
		
	}
	
	$(document).ready(function(){	
		
		 getAll();
	
	});
	
	
	
	$("#getAllMembers").click(function(){
		
		 getAll(); 
	});
	
$("#getOneById_Name_Accout").click(function(){
	
	
	 
    	$.ajax({
    		url: "<%=request.getContextPath()%>/frontend/members/mem.do",
				type : "POST",
				data : {
					action : "getOneByIdNameAccout",
					input : $("#accNameOrId").val()
				},

				success : function(data) {

					//#("queryResult").html(data);

					var memVO = JSON.parse(data);

					console.log(data);

					var str = getStr(memVO);

					$("#result").html(str);
					
					

				}

			});
		});

		function getStr(memVO) {

			var str = '<h3 class="h3 mb-2 text-gray-800">查詢結果</h3>'
					+ '<div class=\"card shadow mb-4\">'
					+ '<div class=\"card-header py-3\">'
					+ '<h6 class=\"m-0 font-weight-bold text-primary\">會員資料總覽</h6></div>'
					+ '<div class=\"card-body\"><div class=\"table-responsive\">'
					+ '<table id=\"example\" class=\"hover\" style=\"width: 100%\">'
					+ '<thead><tr><th>會員編號</th><th>會員帳號</th><th>會員姓名</th><th>性別'
					+ '</th><th>生日</th><th>居住縣市</th><th>加入時間</th>'
					+ '<th>帳號狀態</th><th>公開狀態</th><th>違規次數</th></tr></thead><tbody>';

			for (i = 0; i < memVO.length; i++) {

				str += '<tr><td>' + memVO[i].mem_id + '</td>';
				str += '<td>' + memVO[i].m_accno + '</td>';
				str += '<td>' + memVO[i].m_name + '</td>';
				str += '<td>' + memVO[i].m_gender + '</td>';
				str += '<td>' + nullToStr(memVO[i].m_bday) + '</td>';
				str += '<td>' + memVO[i].m_city + '</td>';
				str += '<td>' + memVO[i].m_joindate + '</td>';
				str += '<td>' + accStatus(memVO[i].m_active) + '</td>';
				str += '<td>' + publicStatus(memVO[i].m_public) + '</td>';
				str += '<td>' + memVO[i].m_bancount + '</td></tr>';
			}

			str += '</tbody></table></div></div></div></div>';

			return str;

		}

		$("#simpleSearch").click(function() {
			$("#result").empty();

		});

		$("#muti-query").click(function() {
			$("#result").empty();

		});

		function nullToStr(input) {

			return (typeof (input) === 'undefined') ? 'N/A' : input;

		}

		function accStatus(input) {
			switch (input) {
			case 1:
				return '<em class="zmdi zmdi-alert-triangle"></em>&ensp;未驗證';
			case 2:
				return '<em class="zmdi zmdi-check"></em>&ensp;使用中';
			case 3:
				return '<em class="zmdi zmdi-block"></em>&ensp;已停用';

			}

		}

		function publicStatus(input) {
			switch (input) {
			case 1:
				return '<em class="zmdi zmdi-eye"></em>&ensp;公開';
			case 2:
				return '<em class="zmdi zmdi-eye-off"></em>&ensp;隱藏';

			}

		}
	</script>





</body>

</html>