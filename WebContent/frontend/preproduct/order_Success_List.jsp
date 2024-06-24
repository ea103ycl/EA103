<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>

<%@ page import="com.wel_record.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.preorder.model.*"%>
<%@ page import="com.preorderdetail.model.*"%>
<%@ page import="com.event_p.model.*"%>
<%@ page import="com.event.model.*"%>
<%/*----------顯示錢包----------*/
  WelRecordVO welRecordVO = (WelRecordVO) request.getAttribute("welRecordVO");
  MemVO memVO = (MemVO) session.getAttribute("memVO");
  PreOrderVO preorderVO = (PreOrderVO) request.getAttribute("preorderVO");
  PreOrderDetailVO preorderdetailVO = (PreOrderDetailVO) request.getAttribute("PreOrderDetailVO");
%>
<%/*----------顯示訂單LIST----------*/
    PreOrderService preorderSvc = new PreOrderService();
    List<PreOrderVO> list = preorderSvc.getAllByMemid(memVO.getMem_id());
    pageContext.setAttribute("list",list);

    /*----------顯示錢包內容----------*/
    WelRecordService welRecordSvc = new WelRecordService();
    List<WelRecordVO> set = welRecordSvc.getWelRecordByMemID(memVO.getMem_id());
    pageContext.setAttribute("set",set);

    /*----------顯示第一名作品----------*/

    EventService eventSvc=new EventService();
	Event_PService eventpSvc = new Event_PService();
    List<Event_PVO> list01 = eventpSvc.findAllByEventNoRankDescWithoutReport(eventSvc.findLastEndEvent());
    pageContext.setAttribute("list01",list01);
    
%>
<% /*----------阻擋重複提交(F5)----------*/
	Random ran = new Random();
	String formhash = String.valueOf(ran.nextInt());
	Set<String> formhashSession = (Set<String>) session.getAttribute("formhashSession");
	if(formhashSession == null){
		formhashSession = new HashSet<String>();
	}
	while(formhashSession.contains(formhash)){
		formhash = String.valueOf(ran.nextInt());
	}
	formhashSession.add(formhash);
	session.setAttribute("formhashSession",formhashSession);
%>
<%@ page import="com.preproduct.model.*"%>
<%
    PreProductService preproductSvc = new PreProductService();
    List<PreProductVO> list_for_countdown = preproductSvc.getAll();
    pageContext.setAttribute("list_for_countdown",list_for_countdown);
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <%@include file="/frontend/bar/frontBarTop.jsp" %>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
  <META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
  <title>ArtsBlock - Order</title>
  <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
  <script type="text/javascript" src="<%=request.getContextPath()%>/frontend/preproduct/css/index_parts/jquery-3.5.1.js"></script>
  <style type="text/css" media="screen">
  
  @media screen and (max-width:1200px){
	.btn-lg {
	    width: 110px !important;
	    font-size: 18px !important;
		}
	}
    #disappear,
    #disappear01,
    #disappear02,
    #addcartbtn{
      display: none;
    }
    .img01{
      width:250px; height:95%;
      margin-right: 30px;
            margin-left: 10px;
      float: left;
    }
    .count_conutdown h3{
      margin: 10px 20px;
      color:#c39d6d;
    }
    .count_contentBx .count_conutdown{

      display: flex;
      line-height: 30px !important;
    }
    .count_contentBx .count_conutdown .count_time{
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      background: #fff;
      margin-right: 20px;
      font-size: 48px;
      min-width: 110px;
      font-weight: 700;
      padding: 20px 5px 10px;
      border-radius: 4px;
      color: gray; 
    }
    header #header-banner{
    /*  background-image: url(css/img/index_css_imgs/banner_bg.jpg) !important; */
      background-image: url("https://mdbootstrap.com/img/Photos/Horizontal/Nature/full page/img(12).jpg");
      background-repeat: no-repeat;
      background-position:center;
      background-size: cover;
    }
    .banner-info h1{
      font-size: 48px;
      font-weight: 600;
      margin-bottom:20px !important;
    }
    .banner-border {
      width: 80% !important;
      height:200px !important;
    }
    .banner-info{
      width: 100% !important;
      background: rgba(255, 255, 255, 0.9)!important;
      padding: 20px 0 !important;
    }
    .count_conutdown{
      justify-content: center;
      align-items: center;
    }
    .btn-lg{
      width: 100% !important;
      font-size: 28px;
      margin:8px;
      padding: 12px 0px;
      font-weight: 600;
      justify-content: center;
      align-items: center;
      box-sizing: border-box;
    }
    .divcss5{
     border:1px groove #000; width:300px; height:300px; 
     } 
    .divcss5 img{width:auto; height:100%;} 
  </style>
</head>
<body>
	<!-- preloader -->
	<div id="preloader">
	    <div class="spinner spinner-round"></div>
	</div>
	<!-- / preloader -->
	<div id="top"></div>
<header>
    <!-- header-banner 本期預購活動倒數-->
    <div id="header-banner">
        <div class="banner-content single-page text-center">
            <div class="banner-border">
            <img class="img01" src="<%=request.getContextPath()%>/backend/preproduct/tools/eventP_show.jsp?EVENT_P_NO=${list01[0].event_p_no}">
                <div class="banner-info">
	                <div class="count_contentBx">
		                <div class="count_conutdown">
		                    <h3>預購倒數</h3>
		                    <button type="button" class="btn btn-lg btn-primary" role="button"><div id="count_day">0</div><span>Days</span></button>
		                    <button type="button" class="btn btn-lg btn-primary" role="button"><div id="count_hour">0</div><span>Hours</span></button>
		                    <button type="button" class="btn btn-lg btn-primary" role="button"><div id="count_minute">0</div><span>Mins</span></button>
		                    <button type="button" class="btn btn-lg btn-primary" role="button"><div id="count_second">0</div><span>Secs</span></button>
		                    <div id="disappear">${list_for_countdown[0].getPo_end()}</div>
		                </div>
	                </div>
                </div><!-- / banner-info -->
            </div><!-- / banner-border -->
        </div><!-- / banner-content -->
    </div><!-- / header-banner -->
</header>

<!--=====自定義內容start ================================================== -->
  
<%@ include file="/backend/preproduct/pages/page1.file" %>
<jsp:useBean id="detailSvc" scope="page" class="com.preorderdetail.model.PreOrderDetailService"/> 
	<div class="container">
		<div id="shipping-info" class="account-info-content">
			<div>
				<p>會員資訊</p>
				<span><strong>
					<c:if test="${not empty sessionScope.memVO.m_accno}">
						錢包餘額:${sessionScope.memVO.balance}
					</c:if>
				</strong></span>
				<c:if test="${not empty errorMsgs}">
					<script type="text/javascript">
						alert("錯誤: ${errorMsgs}");
					</script>
				</c:if>
				<hr>
			</div>
			<h4>我的預購訂單 <span class="pull-right"><a href="<%=request.getContextPath()%>/frontend/preproduct/frontindex.jsp" class="btn btn-sm btn-primary btn-rounded no-margin"><em class="lnr lnr-pencil"></em><span>繼續購物</span></a></span></h4>
			<c:forEach var="preorderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				<div class="row justify-content-around">
					<div class="col-md-4">
						<p class="space-bottom"><span><strong>訂單編號：${preorderVO.po_no}</strong></span></p>
						<p>會員編號: <span>${preorderVO.mem_id}</span></p>
						<p>訂單成立: <span><fmt:formatDate value="${preorderVO.po_time}" pattern="yyyy/MM/dd"/></span></p>
						<p>收件人: <span>${preorderVO.po_name}</span></p>
						<p>聯絡電話: <span>${preorderVO.po_phone}</span></p>
						<p>收件地址: <span>${preorderVO.po_addr}</span></p>
						<p>訂單狀態:
						<span>
							<c:if test="${preorderVO.po_status == 1}">處理中</c:if>
							<c:if test="${preorderVO.po_status == 2}">出貨中</c:if>
							<c:if test="${preorderVO.po_status == 3}">已到貨</c:if>
							<c:if test="${preorderVO.po_status == 4}">已結案</c:if>
						</span>
						</p>
					</div>
					<div class="col-md-8">
						<p class="space-bottom"><span><strong>訂單明細</strong></span></p>
						<c:forEach var="detailVO" items="${detailSvc.getAll()}">
							<c:if test="${preorderVO.po_no==detailVO.po_no}">
								<p>商品編號: <span>${detailVO.po_prod_no}</span></p>
								<p><span>數量: ${detailVO.po_qty}</span></p>
								<br>
							</c:if>
						</c:forEach>
					</div>
				</div>
				<br>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/preproduct/preorder.do" style="margin-bottom: 0px;">
					<input type="submit" class="btn btn-sm btn-primary btn-rounded no-margin" value="取消訂單">
					<input type="hidden" name="po_no"  value="${preorderVO.po_no}">
					<input type="hidden" name="po_total"  value="${preorderVO.po_total}">
					<input id="addcartbtn" type="text" name="formhash"  value="<%=formhash %>">
					<input type="hidden" name="action" value="cancel_order">
				</FORM>
				<div class="account-info-footer"></div>
			</c:forEach>
		</div>
	</div>
<!-- / shipping-info -->

<!--===== 自定義內容end ================================================== -->
<%@include file="/frontend/bar/frontBarFooter.jsp" %>
<script src="https://unpkg.com/ionicons@5.2.3/dist/ionicons.js"></script>  
<script src="<%=request.getContextPath()%>/frontend/preproduct/css/index_parts/jquery-3.5.1.js"></script>
<script type="text/javascript">
	$(function () {
		$("#mdb-lightbox-ui").load("mdb-addons/mdb-lightbox-ui.html");
	});
</script>
<script type="text/javascript">//倒數計時器

	var countDate = $("#disappear").text();
	var someday = new Date(countDate);

	function newYear(){
		var now = new Date();
		console.log(typeof now);

		gap = someday - now;
		
		var second = 1000;
		var minute = second * 60;
		var hour = minute * 60;
		var day = hour * 24;
		
		var d = Math.floor(gap/(day));
		var h = Math.floor((gap%(day)) / (hour));
		var m = Math.floor((gap%(hour)) / (minute));
		var s = Math.floor((gap%(minute)) / second);
		
		document.getElementById('count_day').innerText = d;
		document.getElementById('count_hour').innerText = h;
		document.getElementById('count_minute').innerText = m;
		document.getElementById('count_second').innerText = s;
	}

	setInterval(function(){
        newYear();
	},1000)
</script>
</body>
</html>


