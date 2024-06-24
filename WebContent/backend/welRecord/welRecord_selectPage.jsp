<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wel_record.model.*"%>



<!DOCTYPE html>
<html>


<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/backend/material-design-iconic-font/css/material-design-iconic-font.min.css">
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




					<h1 class="h3 mb-2 text-gray-800">錢包查詢及管理</h1>



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
											<input type="text" class="form-control" id="accMemIdOrWelId"
												placeholder="請輸入會員編號或交易流水號">

										</div>
										<div class="col col-md-2">
											<button class="btn btn-primary" type="button"
												id="getOneById_Acc">搜尋</button>
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
										id="getAllRecords">
										<em class="zmdi zmdi-search"></em>&ensp;全部錢包紀錄查詢
									</button>
								</h2>
							</div>
							<div id="collapseThree" class="collapse"
								aria-labelledby="headingThree" data-parent="#accordionExample">
							</div>
						</div>
					</div>

					<!-- -----------------------查詢結果開始-------------------------- -->
					<div class="chart-container" id="chart" style="display: none;">

						<!-- Collapsable Card Example -->
						<div class="card shadow mb-4" style="margin-top: 10px">
							<!-- Card Header - Accordion -->
							<a href="#collapseCardExample" class="d-block card-header py-3"
								data-toggle="collapse" role="button" aria-expanded="true"
								aria-controls="collapseCardExample">
								<h6 class="font-weight-bold text-primary">平台金流分析</h6>
							</a>
							<!-- Card Content - Collapse -->
							<div class="collapse show" id="collapseCardExample">
								<div class="card-body">

									<div class="row" style="margin-bottom: 20px">
										<div class="col-md-3">
											<div class="card border-left-danger shadow h-100 py-2">
												<div class="card-body">
													<div class="row no-gutters align-items-center">
														<div class="col mr-2">
															<div
																class="text-xs font-weight-bold text-primary text-uppercase mb-1">會員儲值
																(本月)</div>
															<div class="h5 mb-0 font-weight-bold text-gray-800" id="depositMonthly"></div>
														</div>
														<div class="col-auto">
															<em class="fas fa-calendar fa-2x text-gray-300"></em>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-3">
											<div class="card border-left-danger shadow h-100 py-2">
												<div class="card-body">
													<div class="row no-gutters align-items-center">
														<div class="col mr-2">
															<div
																class="text-xs font-weight-bold text-primary text-uppercase mb-1">會員儲值
																(本季)</div>
															<div class="h5 mb-0 font-weight-bold text-gray-800" id="depositSeasonal"></div>
														</div>
														<div class="col-auto">
															<em class="fas fa-dollar-sign fa-2x text-gray-300"></em>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-3">

											<div class="card border-left-success shadow h-100 py-2">
												<div class="card-body">
													<div class="row no-gutters align-items-center">
														<div class="col mr-2">
															<div
																class="text-xs font-weight-bold text-primary text-uppercase mb-1">會員提領
																(本月)</div>
															<div class="h5 mb-0 font-weight-bold text-gray-800" id="withdrawMonthly"></div>
														</div>
														<div class="col-auto">
															<em class="fas fa-calendar fa-2x text-gray-300"></em>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-3">

											<div class="card border-left-success shadow h-100 py-2">
												<div class="card-body">
													<div class="row no-gutters align-items-center">
														<div class="col mr-2">
															<div
																class="text-xs font-weight-bold text-primary text-uppercase mb-1">會員提領
																(本季)</div>
															<div class="h5 mb-0 font-weight-bold text-gray-800" id="withdrawSeasonal"></div>
														</div>
														<div class="col-auto">
															<em class="fas fa-dollar-sign fa-2x text-gray-300"></em>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="row" style="margin-bottom: 20px">
										<div class="col-md-6" style="height:320px">
											<canvas id="myChart1"></canvas>
										</div>
										<div class="col-md-6" style="height:320px">
											<canvas id="myChart2"
												></canvas>
										</div>
									</div>


								</div>
							</div>
						</div>


					</div>

					<div class="row" id="resultHeader"
						style="visibility: hidden; margin-top: 20px">
						<div class="col-md-4" id="queryResult">
							<h3 class="h3 mb-2 text-gray-800" style="margin-left: 20px">查詢結果</h3>
						</div>
						<div class="col-md-6" style="text-align: right;">
							<p style="margin-top: 15px;">篩選:</p>
						</div>
						<div class="col-md-2">
							<select id="tnsSrcSelect"
								style="margin-top: 15px; margin-right: 20px">
								<option value="" disabled selected>交易來源</option>
								<option value="10">會員儲值</option>
								<option value="20">會員提款</option>
								<option value="77">平台扣款-訂單</option>
								<option value="88">平台撥款-分潤</option>
								<option value="99">平台退款-訂單</option>
							</select>

						</div>
					</div>

					<div class="container-fluid" id=result>

						<!-- -----------------------查詢結果結束-------------------------- -->

					</div>
					<!--END OF container-fluid-->

				</div>
				<!--END OF content-->

			

			</div>
			<!--END OF content-wrapper -->
				<%@include file="/backend/bar/footer.jsp"%>
		</div>
		<!--END OF wrapper -->
	</div>

	<!--JavaScript內容-->
	<!--chart cdn-->
	<script src="<%=request.getContextPath()%>/backend/template/vendor/chart.js/Chart.js"></script>
	<!--引用dataTables.js-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/backend/template/vendor/jquery/jquery.js"></script>
	<!--引用dataTables.js-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/backend/template/vendor/datatables/jquery.dataTables.min.js"></script>


	<!--     </script> -->

	<script src="<%=request.getContextPath()%>/backend/template/vendor/jquery/jquery.min.js"></script>
	<script>
	
	function sumTool (recordVO)
	{
		
		var amount10 = 0;//會員儲值(正值)
		var amount20 = 0; //會員提款(負值)
		var amount77 = 0;//平台扣款-訂單(負值)
		var amount88 = 0;//平台撥款-分潤(正值)
		var amount99 = 0;//平台退款-訂單(正值)

		for(i=0;i<recordVO.length;i++){
			
		if (recordVO[i].tns_src == 10){  
			
			amount10 += recordVO[i].tns_amount;
			
		}else if(recordVO[i].tns_src == 20)
		{
			amount20 += recordVO[i].tns_amount;
			
		}else if(recordVO[i].tns_src >=40 && recordVO[i].tns_src <=43)
		{
			amount77 += recordVO[i].tns_amount;
			
		}else if(recordVO[i].tns_src >=30 && recordVO[i].tns_src <=34)
		{
			amount88 += recordVO[i].tns_amount;
		}else{
			
			amount99 += recordVO[i].tns_amount;
		}
		
		}
		//負值轉正
		var array = [ amount10 , -amount20 , -amount77 , amount88 , amount99 ]
		
		return array;
		
	}
	

	  function getSeason(month){ 
	  
	        if(month<3){ 
	            return 'spring'; 
	        } 
	         
	        if(month<6){ 
	            return 'summer'; 
	        } 
	         
	        if(month<9){ 
	            return 'fall'; 
	        } 
	         
	        return 'winter'; 

	    }
	
	  
    
	function sumToolForCard (recordVO)
	{
		
		var amount10Monthly = 0;//會員儲值(正值)
		var amount20Monthly = 0; //會員提款(負值)
		var amount10Season = 0;//會員儲值(正值)
		var amount20Season = 0; //會員提款(負值)
		
		for(i=0;i<recordVO.length;i++){

		    var now = new Date(); //當前時間
			
			var strDate = recordVO[i].tns_time; 
			
			var newDate = new Date(Date.parse(strDate.replace(/-/g,'/')));
			
			console.log(newDate);
			
			if(newDate.getFullYear() === now.getFullYear() &&  newDate.getMonth() === now.getMonth()){ //是否為今年度本月
				
				console.log('newDate.getMonth()'+now.getMonth());
				console.log('now.getMonth()'+ now.getMonth());
				console.log('recordVO[i].tns_src'+ recordVO[i].tns_src);
				
			if (recordVO[i].tns_src === 10) {  //儲值
				
				amount10Monthly += recordVO[i].tns_amount;
				
			}else if(recordVO[i].tns_src === 20)  //提領
			{
				amount20Monthly += recordVO[i].tns_amount;
				
			}
			
		}
			console.log('getSeason(newDate.getMonth())'+getSeason(newDate.getMonth()));
			console.log('getSeason(now.getMonth())'+ getSeason(now.getMonth()));
			
			if(newDate.getFullYear() === now.getFullYear() &&  getSeason(newDate.getMonth()) === getSeason(now.getMonth())) //是否為今年度本季
			{
				if (recordVO[i].tns_src === 10) {  //儲值
					
					amount10Season += recordVO[i].tns_amount;
					
				}else if(recordVO[i].tns_src === 20)  //提領
				{
					amount20Season += recordVO[i].tns_amount;
				}
			}
		}
		
		var arrayCard = [amount10Monthly, amount10Season, -amount20Monthly, -amount20Season];//提領轉正值
		return arrayCard;	
	}
	
	
	$("#simpleSearch").click(function(){
		 $("#resultHeader").css("visibility","hidden");
		 $("#result").empty();
		 $("#chart").css("display","none");
		 $("#queryResult").css("visibility","hidden"); 
		
	});

	//全部交易紀錄查詢
	
	function getAll()
	{
		 $("#result").empty();
		 $("#resultHeader").css("visibility","visible");
		 $("#queryResult").css("visibility","visible"); 
		 $("#chart").css("display","inline");
		 $("#myChart1").css("display","inline-block");
		 $("#myChart2").css("display","inline-block");
		 
		 var arrayChart = new Array(5);
		 
    	$.ajax({
    		url: "<%=request.getContextPath()%>/welRecord/welRecord.do",
				type : "POST",
				data : {
					
					action : "getAllRecords",
				
				},
				success : function(data) {
					
					var recordVO = JSON.parse(data);
					
					var str = getStr(recordVO);
					
		            $("#result").html(str);
		            		            
		            arrayChart = sumTool(recordVO);
		            
		            arrayCard = sumToolForCard(recordVO);
					
		            $("#depositMonthly").text("$"+arrayCard[0]);
		            $("#depositSeasonal").text("$"+arrayCard[1]);
		            $("#withdrawMonthly").text("$"+arrayCard[2]);
		            $("#withdrawSeasonal").text("$"+arrayCard[3]);
		            console.log(arrayCard);	
				
						//圓餅圖
				    	var ctx1 = document.getElementById('myChart1').getContext('2d');
				    	var myDoughnutChart = new Chart(ctx1, {
				    	    type: 'doughnut',
				    	    data: 

				    	    {			    	    	
				    		    datasets: [{
				    		        data: arrayChart ,
			    		    backgroundColor: ['#6666E2', '#00A600', '#D174A0', '#B2385D', '#F29C1B']
				    	
				    		    }],			    		  
				    		    labels: [
				    		        '會員儲值',
				    		        '會員提領',
				    		        '平台扣款-訂單',
				    		        '平台撥款-分潤/折扣金',
				    		        '平台退款-訂單'
				    		    ]

				    		}
				    	    	
				    	});
				    	
				    	//長條圖
				    	var ctx2 = document.getElementById('myChart2').getContext('2d');
				    	var myBarChart = new Chart(ctx2, {
				    	    type: "bar",
				    	    data: 	    {			    	    	
				    	    	
				    		    datasets: [{
				    		    	  label: "平台金流總覽",
				    		        data: arrayChart ,
			    		    backgroundColor: ['#6666E2', '#364A19', '#D174A0', '#B2385D', '#F29C1B']
				    	
				    		    }],
	    		   
				    		    labels: [
				    		        '會員儲值',
				    		        '會員提領',
				    		        '平台扣款-訂單',
				    		        '平台撥款-分潤/折扣金',
				    		        '平台退款-訂單',    		     
				    		    ]
				    		}    	
				    	});
				}
			});
		
	}
	
	$(document).ready(function(){
		getAll();
	});
	
	
	$("#getAllRecords").click(function(){
	

		getAll();

	
	});
	
	
	
	
	
	
	//篩選交易來源
	$("#tnsSrcSelect").change(function(){

		$.ajax({
    		url: "<%=request.getContextPath()%>/welRecord/welRecord.do",
				type : "POST",
				data : {
					action : "filterRecords",
					filter :  $("#tnsSrcSelect").val()
					},
					
				success : function(data) {

					console.log(data);

					var recordVO = JSON.parse(data);
					
					console.log(recordVO[0]);
					
					var str = getStr(recordVO);
					
		            $("#result").html(str);
 
				}
			});
	
	});

	
//單一會員/交易紀錄查詢
	
$("#getOneById_Acc").click(function(){
	
    	$.ajax({
    		url: "<%=request.getContextPath()%>/welRecord/welRecord.do",
				type : "POST",
				data : {
					action : "getOneById_Accno",
					input : $("#accMemIdOrWelId").val()
				},

				success : function(data) {

					var welRecordVO = JSON.parse(data);

					if (welRecordVO.length > 1) {

						var str = getStr(welRecordVO);
					} else {

						var str = getVOStr(welRecordVO);
					}

					$("#result").html(str);
					$("#queryResult").css("visibility", "visible");

				}

			});
		});

		//單一會員或全部交易紀錄(輸入參數為Array)
		function getStr(recordVO) {

			var str = '<div class=\"card shadow mb-4\">'
					+ '<div class=\"card-header py-3\">'
					+ '<h6 class=\"m-0 font-weight-bold text-primary\">錢包紀錄總覽</h6></div>'
					+ '<div class=\"card-body\"><div class=\"table-responsive\">'
					+ '<table id=\"example\" class=\"hover\" style=\"width: 100%\">'
					+ '<thead><tr><th>交易流水號</th><th>會員編號</th><th>交易來源</th><th>來源訂單編號'
					+ '</th><th>交易金額</th><th>交易時間</th></tr></thead><tbody>';

			for (i = 0; i < recordVO.length; i++) {

				str += '<tr><td>' + recordVO[i].tns_id + '</td>';
				str += '<td>' + recordVO[i].mem_id + '</td>';
				str += '<td>' + srcToStr(recordVO[i].tns_src) + '</td>';
				str += '<td>' + nullToStr(recordVO[i].order_id) + '</td>';
				str += '<td>' + recordVO[i].tns_amount + '</td>';
				str += '<td>' + recordVO[i].tns_time + '</td></tr>';
			}

			str += '</tbody></table></div></div></div></div>';

			return str;

		}

		//單一交易紀錄(輸入參數為Object)
		function getVOStr(recordVO) {

			var str = '<div class=\"card shadow mb-4\">'
					+ '<div class=\"card-header py-3\">'
					+ '<h6 class=\"m-0 font-weight-bold text-primary\">錢包紀錄總覽</h6></div>'
					+ '<div class=\"card-body\"><div class=\"table-responsive\">'
					+ '<table id=\"example\" class=\"hover\" style=\"width: 100%\">'
					+ '<thead><tr><th>交易流水號</th><th>會員編號</th><th>交易來源</th><th>來源訂單編號'
					+ '</th><th>交易金額</th><th>交易時間</th></tr></thead><tbody>';

			str += '<tr><td>' + recordVO.tns_id + '</td>';
			str += '<td>' + recordVO.mem_id + '</td>';
			str += '<td>' + srcToStr(recordVO.tns_src) + '</td>';
			str += '<td>' + nullToStr(recordVO.order_id) + '</td>';
			str += '<td>' + recordVO.tns_amount + '</td>';
			str += '<td>' + recordVO.tns_time + '</td></tr>';

			str += '</tbody></table></div></div></div></div>';

			return str;

		}

		function nullToStr(input) {

			return (typeof (input) === 'undefined') ? 'N/A' : input;

		}

		//交易來源碼轉字串
		function srcToStr(input) {
			switch (input) {
			case 10:
				return '會員儲值';
			case 20:
				return '會員提款';
			case 30:
				return '平台撥款-一般購買分潤';
			case 31:
				return '平台撥款-預購分潤';
			case 32:
				return '平台撥款-競標分潤';
			case 33:
				return '平台撥款-一般購買分潤';
			case 34:
				return '平台撥款-見面會分潤';
			case 35:
				return '平台退款-一般購買退款';
			case 36:
				return '平台退款-預購';
			case 37:
				return '平台退款-競標';
			case 38:
				return '平台退款-見面會';
			case 40:
				return '平台扣款-一般購買訂單';
			case 41:
				return '平台扣款-預購訂單';
			case 42:
				return '平台扣款-競標訂單';
			case 43:
				return '平台扣款-見面會';

			}
		}
		
	</script>

</body>

</html>