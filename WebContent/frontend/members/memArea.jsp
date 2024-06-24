<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.wel_record.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="java.util.*"%>

<%
	MemVO memVO = (MemVO) session.getAttribute("memVO");

	WelRecordService welRecordSvc = new WelRecordService();
	List<WelRecordVO> list = welRecordSvc.getWelRecordByMemID(memVO.getMem_id());
	pageContext.setAttribute("list", list);
	
%>

<!DOCTYPE html>
<html lang="en">

<head>
<!-- icon -->

<link href="<%=request.getContextPath()%>/frontend/template/Caroline/material-design-iconic-font/css/material-design-iconic-font.min.css" rel="stylesheet">

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- page title -->
<title>ArtsBlock</title>
<style>



#content {
  float: right;
  width: calc(100% - 215px);

}

#sidebar {
  float: left;
  top:100;


}

.clearfix:after {
  display: block;
  content: "";
  clear: both;
}



</style>

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

		    <!-- header-banner -->
    <div id="header-banner">
        <div class="banner-content single-page text-center">
            <div class="banner-border">
                <div class="banner-info" id="banner-info">
                    <h1>My Account</h1>
                </div><!-- / banner-info -->
            </div><!-- / banner-border -->
        </div><!-- / banner-content -->
    </div>
    <!-- / header-banner -->
	</header>
	<!-- / header -->
	<!-- content -->
	<!-- my-account -->
	<section id="my-account">
		<div class="container">
		
		
		<div id="main-content" class="main">
  
		
			<div class="row">
			  <div id="sidebar" class="sidebar">
        <div class="sidebar__inner"  >
    
				<div class="account-sidebar">
				 <div class="sticky-top"  >
				 <div>
					<img
						src="<%=request.getContextPath()%>/members/headphotoHandler.do?action=getPic&mem_id=<%=memVO.getMem_id()%>" alt="" style="width:150px;margin:auto; margin-bottom:15px">
					<p>
						<a href="#banner-info" class="page-scroll"><strong>編輯個人資料</b></a>
					</p>
<!-- 					<p> -->
<!-- 						<a href="#memInfoBtn" class="page-scroll">編輯收件資料</a> -->
<!-- 					</p> -->

					<p>
						<a href="#memInfoBtn" class="page-scroll"><strong>帳號管理</b></a>
					</p>
					<p>
						<a href="#accountArea" class="page-scroll"><strong>電子錢包管理</b></a>
					</p>
</div>
				</div>
				</div>
				
				
        </div>
    </div>
 
				
				
				<!-- / account-sidebar -->
				
				   <div id="content" class="content">

					<div id="personal-info" class="account-info-content">
						<h4>編輯個人資料&emsp;&emsp; ${sessionScope.memVO.m_accno}</h4>

						<div class="row">
							<div class="col-md-2" style="padding-right: 0px;">

								<div></div>


								<img id="headphoto"
									src="<%=request.getContextPath()%>/members/headphotoHandler.do?action=getPic&mem_id=<%=memVO.getMem_id()%>"
									alt="" style="width: 500px;">

								<button id="headphotoBtn"
									class="btn btn-primary btn-xs btn-rounded no-margin"
									type="button" style="font-weight: bold;" data-toggle="modal"
									data-target="#photoModal">
									<em class="fas fa-camera"></em>更換頭像
								</button>


							</div>
							<div class="col-md-10">
								<!-- 							第一行姓名開始 -->

								<div class="row">
									<div class="col-md-2"
										style="text-align: right; margin-top: 13px;">姓名:</div>
									<div class="col-md-9">
										<input type="text" class="form-control rounded" id="name"
											value="<%=memVO.getM_name()%>"
											style="margin-bottom: 0px; background-color: white"
											name="name">
									</div>



								</div>
								<!-- 							第一行姓名結束-->
								<div class="row">
									<div class="col-md-2"
										style="text-align: right; margin-top: 13px;">性別:</div>
									<div class="col-md-9">
										<select name="gender" id="gender" class="form-control"
											style="margin-top: 10px;margin-bottom: 0px; background-color: white">
											<option value="" disabled>性別</option>
											<option value="M"
												<c:if test="${memVO.m_gender=='M'}">selected</c:if>>男性</option>
											<option value="F"
												<c:if test="${memVO.m_gender=='F'}">selected</c:if>>女性</option>
										</select>
									</div>



								</div>
								<!-- 							第二行性別結束-->
								<div class="row">
									<div class="col-md-2"
										style="text-align: right; margin-top: 13px;">生日:</div>
									<div class="col-md-9">
										<input type="text" class="form-control rounded" id="birthday"
											value="<%=memVO.getM_bday()%>" name="birthday"
											style="margin-top: 10px;margin-bottom: 0px; background-color: white">
									</div>



								</div>
								<!-- 							第三行生日結束-->
								<div class="row">
									<div class="col-md-2"
										style="text-align: right; margin-top: 13px;">電話:</div>
									<div class="col-md-9">
										<input type="text" class="form-control rounded" id="phone"
											name="phone"
											value="<%=(memVO.getM_phone() == null) ? "" : memVO.getM_phone()%>"
											style="margin-top: 10px; margin-bottom: 0px;background-color: white">
									</div>
								</div>


								<!-- 							第四行電話結束-->
								<div class="row">
									<div class="col-md-2"
										style="text-align: right; margin-top: 13px;">手機:</div>
									<div class="col-md-9">
										<input type="text" class="form-control rounded" id="mobile"
											value="<%=memVO.getM_mobile()%>" name="mobile"
											style="margin-top: 10px; margin-bottom: 0px;background-color: white">
									</div>
								</div>
								<!-- 							第五行手機結束-->
								<div class="row">
									<div class="col-md-2"
										style="text-align: right; margin-top: 13px;">電子信箱:</div>
									<div class="col-md-9">
										<input type="email" class="form-control rounded" id="email"
											value="<%=memVO.getM_email()%>" name="email"
											style="margin-top: 10px;margin-bottom: 0px; background-color: white">
									</div>
								</div>
								<!-- 							第六行電子郵箱結束-->
								<div class="row">
									<div class="col-md-2"
										style="text-align: right; margin-top: 13px;">聯絡地址:</div>
									<div class="col-md-4">


										<select name="contactZip" id="contactZip" class="form-control"
											style="background-color: white; margin-top: 10px;margin-bottom: 0px">
											<option value="" disabled selected>郵遞區號</option>
											<option value="100 台北市 中正區">100 台北市 中正區</option>
											<option value="103 台北市 大同區">103 台北市 大同區</option>
											<option value="104 台北市 中山區">104 台北市 中山區</option>
											<option value="105 台北市 松山區">105 台北市 松山區</option>
											<option value="106 台北市 大安區">106 台北市 大安區</option>
											<option value="234 新北市 永和區">234 新北市 永和區</option>
											<option value="235 新北市 中和區">235 新北市 中和區</option>
											<option value="236 新北市 土城區">236 新北市 土城區</option>
											<option value="237 新北市 三峽區">237 新北市 三峽區</option>
											<option value="238 新北市 樹林區">238 新北市 樹林區</option>
											<option value="239 新北市 鶯歌區">239 新北市 鶯歌區</option>
											<option value="241 新北市 三重區">241 新北市 三重區</option>
											<option value="242 新北市 新莊區">242 新北市 新莊區</option>
											<option value="243 新北市 泰山區">243 新北市 泰山區</option>
											<option value="244 新北市 林口區">244 新北市 林口區</option>
											<option value="247 新北市 蘆洲區">247 新北市 蘆洲區</option>
											<option value="248 新北市 五股區">248 新北市 五股區</option>
											<option value="249 新北市 八里區">249 新北市 八里區</option>
											<option value="251 新北市 淡水區">251 新北市 淡水區</option>
											<option value="252 新北市 三芝區">252 新北市 三芝區</option>
											<option value="253 新北市 石門區">253 新北市 石門區</option>
											<option value="260 宜蘭縣 宜蘭市">260 宜蘭縣 宜蘭市</option>
											<option value="261 宜蘭縣 頭城鎮">261 宜蘭縣 頭城鎮</option>
											<option value="262 宜蘭縣 礁溪鄉">262 宜蘭縣 礁溪鄉</option>
											<option value="263 宜蘭縣 壯圍鄉">263 宜蘭縣 壯圍鄉</option>
											<option value="264 宜蘭縣 員山鄉">264 宜蘭縣 員山鄉</option>
											<option value="265 宜蘭縣 羅東鎮">265 宜蘭縣 羅東鎮</option>
											<option value="266 宜蘭縣 三星鄉">266 宜蘭縣 三星鄉</option>
											<option value="267 宜蘭縣 大同鄉">267 宜蘭縣 大同鄉</option>
											<option value="268 宜蘭縣 五結鄉">268 宜蘭縣 五結鄉</option>
											<option value="269 宜蘭縣 冬山鄉">269 宜蘭縣 冬山鄉</option>
											<option value="270 宜蘭縣 蘇澳鎮">270 宜蘭縣 蘇澳鎮</option>
											<option value="272 宜蘭縣 南澳鄉">272 宜蘭縣 南澳鄉</option>
											<option value="300 新竹市 北區">300 新竹市 北區</option>
											<option value="300 新竹市 東區">300 新竹市 東區</option>
											<option value="300 新竹市 香山區">300 新竹市 香山區</option>
											<option value="300 新竹市 ">300 新竹市</option>
											<option value="302 新竹縣 竹北市">302 新竹縣 竹北市</option>
											<option value="303 新竹縣 湖口鄉">303 新竹縣 湖口鄉</option>
											<option value="304 新竹縣 新豐鄉">304 新竹縣 新豐鄉</option>
											<option value="305 新竹縣 新埔鎮">305 新竹縣 新埔鎮</option>
											<option value="306 新竹縣 關西鎮">306 新竹縣 關西鎮</option>
											<option value="307 新竹縣 芎林鄉">307 新竹縣 芎林鄉</option>
											<option value="308 新竹縣 寶山鄉">308 新竹縣 寶山鄉</option>
											<option value="310 新竹縣 竹東鎮">310 新竹縣 竹東鎮</option>
											<option value="311 新竹縣 五峰鄉">311 新竹縣 五峰鄉</option>
											<option value="312 新竹縣 橫山鄉">312 新竹縣 橫山鄉</option>
											<option value="313 新竹縣 尖石鄉">313 新竹縣 尖石鄉</option>
											<option value="314 新竹縣 北埔鄉">314 新竹縣 北埔鄉</option>
											<option value="315 新竹縣 峨眉鄉">315 新竹縣 峨眉鄉</option>
											<option value="320 桃園市 中壢區">320 桃園市 中壢區</option>
											<option value="324 桃園市 平鎮區">324 桃園市 平鎮區</option>
											<option value="325 桃園市 龍潭區">325 桃園市 龍潭區</option>
											<option value="326 桃園市 楊梅區">326 桃園市 楊梅區</option>
											<option value="327 桃園市 新屋區">327 桃園市 新屋區</option>
											<option value="328 桃園市 觀音區">328 桃園市 觀音區</option>
											<option value="330 桃園市 桃園區">330 桃園市 桃園區</option>
											<option value="333 桃園市 龜山區">333 桃園市 龜山區</option>
											<option value="334 桃園市 八德區">334 桃園市 八德區</option>
											<option value="335 桃園市 大溪區">335 桃園市 大溪區</option>
											<option value="336 桃園市 復興區">336 桃園市 復興區</option>
											<option value="337 桃園市 大園區">337 桃園市 大園區</option>
											<option value="338 桃園市 蘆竹區">338 桃園市 蘆竹區</option>
										</select>
									</div>
									<div class="col-md-5">

										<input type="text" class="form-control rounded"
											id="contactAddr" value="<%=memVO.getM_addr()%>"
											name="contactAddr"
											style="margin-top: 10px;margin-bottom: 0px; background-color: white;">
									</div>
								</div>

								<!-- 							第七行居住地址結束-->


								<div style="text-align: center; margin-top: 25px;">
									<button type="button" id="memInfoBtn"
										class="btn btn-primary-filled btn-pill">提交</button>
								</div>

							</div>
						</div>
					</div>
<!-- 					                      收件人資料區塊開始                          -->
<!-- 					<div id="shippingArea" class="account-info-content"> -->
<!-- 						<h4> -->
<!-- 							編輯收件資料<span class="pull-right"> -->
<!-- 								<button class="btn btn-sm btn-primary btn-rounded no-margin" -->
<!-- 									type="button" data-toggle="collapse" -->
<!-- 									data-target="#collapseExample2" aria-expanded="false" -->
<!-- 									aria-controls="collapseExample2" style="font-weight: bold"> -->
<!-- 									<em class="lnr lnr-pencil"></em>修改收件人資料 -->
<!-- 								</button> -->
<!-- 							</span> -->


<!-- 						</h4> -->
<!-- 						<p class="space-bottom" id="shipingArea"> -->

<!-- 							收件人姓名:<br> -->
<!-- 							<br> 收件人電話:<br> -->
<!-- 							<br> 收件人地址: -->
<!-- 						</p> -->


<!-- 						<div class="collapse" id="collapseExample2" -->
<!-- 							style="margin: auto 50px;"> -->
<!-- 							<div class="well"> -->
<!-- 								<FORM> -->

<!-- 									<div class="row"> -->
<!-- 										<div class="col-md-2" -->
<!-- 											style="text-align: right; margin-top: 13px;">姓名:</div> -->
<!-- 										<div class="col-md-9"> -->
<!-- 											<input type="text" class="form-control rounded" id="shipName" -->
<!-- 												value="" -->
<!-- 												style="margin-bottom: 10px; background-color: white"> -->
<!-- 										</div> -->
<!-- 									</div> -->

<!-- 									<div class="row"> -->
<!-- 										<div class="col-md-2" -->
<!-- 											style="text-align: right; margin-top: 13px;">手機:</div> -->
<!-- 										<div class="col-md-9"> -->
<!-- 											<input type="text" class="form-control rounded" -->
<!-- 												id="shipMobile" value="" -->
<!-- 												style="margin-bottom: 10px; background-color: white"> -->
<!-- 										</div> -->
<!-- 									</div> -->

<!-- 									<div class="row"> -->
<!-- 										<div class="col-md-2" -->
<!-- 											style="text-align: right; margin-top: 13px;">收件地址:</div> -->
<!-- 										<div class="col-md-4"> -->

<!-- 											<select name="shipZip" id="shipZip" class="form-control" -->
<!-- 												style="background-color: white; margin-bottom: 10px"> -->
<!-- 												<option value="" disabled selected>郵遞區號</option> -->
<!-- 												<option value="100 台北市 中正區">100 台北市 中正區</option> -->
<!-- 												<option value="103 台北市 大同區">103 台北市 大同區</option> -->
<!-- 												<option value="104 台北市 中山區">104 台北市 中山區</option> -->
<!-- 												<option value="105 台北市 松山區">105 台北市 松山區</option> -->
<!-- 												<option value="106 台北市 大安區">106 台北市 大安區</option> -->
<!-- 												<option value="234 新北市 永和區">234 新北市 永和區</option> -->
<!-- 												<option value="235 新北市 中和區">235 新北市 中和區</option> -->
<!-- 												<option value="236 新北市 土城區">236 新北市 土城區</option> -->
<!-- 												<option value="237 新北市 三峽區">237 新北市 三峽區</option> -->
<!-- 												<option value="238 新北市 樹林區">238 新北市 樹林區</option> -->
<!-- 												<option value="239 新北市 鶯歌區">239 新北市 鶯歌區</option> -->
<!-- 												<option value="241 新北市 三重區">241 新北市 三重區</option> -->
<!-- 												<option value="242 新北市 新莊區">242 新北市 新莊區</option> -->
<!-- 												<option value="243 新北市 泰山區">243 新北市 泰山區</option> -->
<!-- 												<option value="244 新北市 林口區">244 新北市 林口區</option> -->
<!-- 												<option value="247 新北市 蘆洲區">247 新北市 蘆洲區</option> -->
<!-- 												<option value="248 新北市 五股區">248 新北市 五股區</option> -->
<!-- 												<option value="249 新北市 八里區">249 新北市 八里區</option> -->
<!-- 												<option value="251 新北市 淡水區">251 新北市 淡水區</option> -->
<!-- 												<option value="252 新北市 三芝區">252 新北市 三芝區</option> -->
<!-- 												<option value="253 新北市 石門區">253 新北市 石門區</option> -->
<!-- 												<option value="260 宜蘭縣 宜蘭市">260 宜蘭縣 宜蘭市</option> -->
<!-- 												<option value="261 宜蘭縣 頭城鎮">261 宜蘭縣 頭城鎮</option> -->
<!-- 												<option value="262 宜蘭縣 礁溪鄉">262 宜蘭縣 礁溪鄉</option> -->
<!-- 												<option value="263 宜蘭縣 壯圍鄉">263 宜蘭縣 壯圍鄉</option> -->
<!-- 												<option value="264 宜蘭縣 員山鄉">264 宜蘭縣 員山鄉</option> -->
<!-- 												<option value="265 宜蘭縣 羅東鎮">265 宜蘭縣 羅東鎮</option> -->
<!-- 												<option value="266 宜蘭縣 三星鄉">266 宜蘭縣 三星鄉</option> -->
<!-- 												<option value="267 宜蘭縣 大同鄉">267 宜蘭縣 大同鄉</option> -->
<!-- 												<option value="268 宜蘭縣 五結鄉">268 宜蘭縣 五結鄉</option> -->
<!-- 												<option value="269 宜蘭縣 冬山鄉">269 宜蘭縣 冬山鄉</option> -->
<!-- 												<option value="270 宜蘭縣 蘇澳鎮">270 宜蘭縣 蘇澳鎮</option> -->
<!-- 												<option value="272 宜蘭縣 南澳鄉">272 宜蘭縣 南澳鄉</option> -->
<!-- 												<option value="300 新竹市 北區">300 新竹市 北區</option> -->
<!-- 												<option value="300 新竹市 東區">300 新竹市 東區</option> -->
<!-- 												<option value="300 新竹市 香山區">300 新竹市 香山區</option> -->
<!-- 												<option value="300 新竹市 ">300 新竹市</option> -->
<!-- 												<option value="302 新竹縣 竹北市">302 新竹縣 竹北市</option> -->
<!-- 												<option value="303 新竹縣 湖口鄉">303 新竹縣 湖口鄉</option> -->
<!-- 												<option value="304 新竹縣 新豐鄉">304 新竹縣 新豐鄉</option> -->
<!-- 												<option value="305 新竹縣 新埔鎮">305 新竹縣 新埔鎮</option> -->
<!-- 												<option value="306 新竹縣 關西鎮">306 新竹縣 關西鎮</option> -->
<!-- 												<option value="307 新竹縣 芎林鄉">307 新竹縣 芎林鄉</option> -->
<!-- 												<option value="308 新竹縣 寶山鄉">308 新竹縣 寶山鄉</option> -->
<!-- 												<option value="310 新竹縣 竹東鎮">310 新竹縣 竹東鎮</option> -->
<!-- 												<option value="311 新竹縣 五峰鄉">311 新竹縣 五峰鄉</option> -->
<!-- 												<option value="312 新竹縣 橫山鄉">312 新竹縣 橫山鄉</option> -->
<!-- 												<option value="313 新竹縣 尖石鄉">313 新竹縣 尖石鄉</option> -->
<!-- 												<option value="314 新竹縣 北埔鄉">314 新竹縣 北埔鄉</option> -->
<!-- 												<option value="315 新竹縣 峨眉鄉">315 新竹縣 峨眉鄉</option> -->
<!-- 												<option value="320 桃園市 中壢區">320 桃園市 中壢區</option> -->
<!-- 												<option value="324 桃園市 平鎮區">324 桃園市 平鎮區</option> -->
<!-- 												<option value="325 桃園市 龍潭區">325 桃園市 龍潭區</option> -->
<!-- 												<option value="326 桃園市 楊梅區">326 桃園市 楊梅區</option> -->
<!-- 												<option value="327 桃園市 新屋區">327 桃園市 新屋區</option> -->
<!-- 												<option value="328 桃園市 觀音區">328 桃園市 觀音區</option> -->
<!-- 												<option value="330 桃園市 桃園區">330 桃園市 桃園區</option> -->
<!-- 												<option value="333 桃園市 龜山區">333 桃園市 龜山區</option> -->
<!-- 												<option value="334 桃園市 八德區">334 桃園市 八德區</option> -->
<!-- 												<option value="335 桃園市 大溪區">335 桃園市 大溪區</option> -->
<!-- 												<option value="336 桃園市 復興區">336 桃園市 復興區</option> -->
<!-- 												<option value="337 桃園市 大園區">337 桃園市 大園區</option> -->
<!-- 												<option value="338 桃園市 蘆竹區">338 桃園市 蘆竹區</option> -->

<!-- 											</select> -->
<!-- 										</div> -->
<!-- 										<div class="col-md-5"> -->
<!-- 											<input type="text" class="form-control rounded" id="shipAddr" -->
<!-- 												value="" -->
<!-- 												style="margin-bottom: 10px; background-color: white"> -->
<!-- 										</div> -->
<!-- 									</div> -->

<!-- 									<div class="row" style="text-align: center"> -->
<!-- 										<div class="col col-md-4"> -->
<!-- 											<div class="checkbox checkbox-primary space-bottom"> -->
<!-- 												<label class="hide"><input type="checkbox"></label> -->
<!-- 												<input id="sameAsContactBtn" type="checkbox"> <label -->
<!-- 													for="sameAsContactBtn" style="color: grey"><span><strong>同聯絡地址</strong></span></label> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 										<div class="col col-md-5"> -->
<!-- 											<button type="submit" class="btn btn-primary-filled btn-pill" >提交</button> -->
<!-- 										</div> -->

<!-- 									</div> -->


<!-- 								</FORM> -->

<!-- 							</div> -->
<!-- 						</div> -->

<!-- 					</div> -->




					<!-- / accountArea -->
					<div id="accountArea" class="account-info-content">
						<h4>
							帳號管理<span class="pull-right">
								<button id="changePwdBtn"
									class="btn btn-sm btn-primary btn-rounded no-margin"
									type="button" data-toggle="collapse"
									data-target="#collapseExample" aria-expanded="false"
									aria-controls="collapseExample" style="font-weight: bold">
									<em class="lnr lnr-pencil"></em>修改密碼
								</button>
							</span>


						</h4>
						<p class="space-bottom" id="accountArea">
							<span><strong> <c:if
										test="${not empty sessionScope.memVO.m_accno}">
							加入時間:&emsp;${sessionScope.memVO.m_joindate}<br>
										<br>
							會員帳號:&emsp;${sessionScope.memVO.m_accno}
							
									</c:if></strong></span>
						</p>


						<div class="collapse" id="collapseExample"
							style="margin: auto 50px;">
							<div class="well">
								<FORM METHOD="POST"
									ACTION="<%=request.getContextPath()%>/welRecord/welRecord.do">
									<div id="pwdGroup">
										<div class="form-group">
											<div class="row" style="text-align: right;">
												<div class="col col-md-3" style="margin-top: 15px;">
													<h6 id="smartbtnArea">
														<label for="InputPassword1"
															style="color: #666666; line-height: 20px">目前的密碼</label>

													</h6>
												</div>
												<div class="col col-md-7">
													<input type="password" class="form-control rounded"
														id="InputPassword1" placeholder="Password"
														name="oldPassword" style="margin-bottom: 10px">
												</div>
												<div class="col col-md-2"
													style="margin-top: 5px; text-align: center">
													<button type="button" id="eye"
														class="btn btn-xs btn-default-filled btn-pill">
														<em class="zmdi zmdi-eye-off"></em>顯示密碼
													</button>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="row" style="text-align: right;">
												<div class="col col-md-3" style="margin-top: 15px;">
													<h6>
														<label for="InputPassword2"
															style="color: #666666; line-height: 20px">新密碼</label>
													</h6>
												</div>
												<div class="col col-md-7">
													<input type="password" class="form-control rounded"
														id="InputPassword2" placeholder="Password"
														name="newPassword1" style="margin-bottom: 10px">
												</div>
												<div id="eye2" class="col col-md-2"
													style="margin-top: 18px; text-align: left"></div>
											</div>
										</div>
										<div class="form-group">
											<div class="row" style="text-align: right;">
												<div class="col col-md-3" style="margin-top: 15px;">
													<h6>
														<label for="InputPassword3"
															style="color: #666666; line-height: 20px">再次輸入新密碼</label>
													</h6>
												</div>
												<div class="col col-md-7">
													<input type="password" class="form-control rounded"
														id="InputPassword3" placeholder="Password"
														name="newPassword2" style="margin-bottom: 10px">
												</div>
												<div id="eye3" class="col col-md-2"
													style="margin-top: 18px; text-align: left"></div>
											</div>
										</div>
										<div style="text-align: center">
											<button id="submitPsw" type="button"
												class="btn btn-primary-filled btn-pill">提交</button>
										</div>
									</div>


								</FORM>
							</div>
						</div>

					</div>
					<!-- / welRecordArea -->

					<div id="welRecordArea" class="account-info-content">
						<h4>
							錢包管理 &emsp;<strong>目前餘額：NT$${sessionScope.memVO.balance}</strong>
							<span class="pull-right">

								<button type="button" class="btn btn-primary-filled btn-rounded"
									data-toggle="modal" data-target="#myModal1">
									<em class="fa fa-plus"></em>儲值
								</button> &emsp;
								<button type="button"
									class="btn btn-primary-filled btn-rounded no-margin"
									data-toggle="modal" data-target="#myModal2">
									<em class="fa fa-minus"></em>提領
								</button>

							</span>
						</h4>

						<br> <span style="font-size: 20px; font-weight: bold;">交易紀錄</span>

						<p>
							<c:if test="${not empty errorMsgsForMoney}">
								<div
									style="text-align: left; color: red; width: 50%; margin: 0px auto;">
									<c:forEach var="message" items="${errorMsgsForMoney}">
                            ${message}<br>
									</c:forEach>
								</div>
							</c:if>
						</p>
						<div class="table-responsive">
							<table id="example" class="hover" style="width: 100%">
								<thead>
									<tr>
										<th>編號</th>
										<th>交易種類</th>
										<th>來源訂單編號</th>
										<th>交易時間</th>
										<th>交易金額</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="welRecordVO" items="${list}" varStatus="number">

										<tr>
											<td>${number.count}</td>
											<td><c:if test="${welRecordVO.tns_src==10}">儲值</c:if> <c:if
													test="${welRecordVO.tns_src==20}">提領</c:if> <c:if
													test="${welRecordVO.tns_src==30}">平台撥款-一般購買分潤</c:if> <c:if
													test="${welRecordVO.tns_src==31}">平台撥款-預購分潤</c:if> <c:if
													test="${welRecordVO.tns_src==32}">平台撥款-競標分潤</c:if> <c:if
													test="${welRecordVO.tns_src==33}">平台撥款-見面會分潤</c:if> <c:if
													test="${welRecordVO.tns_src==34}">平台撥款-預購折扣金</c:if> <c:if
													test="${welRecordVO.tns_src==35}">平台退款-一般購買</c:if> <c:if
													test="${welRecordVO.tns_src==36}">平台退款-預購</c:if> <c:if
													test="${welRecordVO.tns_src==37}">平台退款-競標</c:if> <c:if
													test="${welRecordVO.tns_src==38}">平台退款-見面會</c:if> <c:if
													test="${welRecordVO.tns_src==40}">平台扣款-一般購買訂單</c:if> <c:if
													test="${welRecordVO.tns_src==41}">平台扣款-預購訂單</c:if> <c:if
													test="${welRecordVO.tns_src==42}">平台扣款-競標訂單</c:if> <c:if
													test="${welRecordVO.tns_src==43}">平台扣款-見面會</c:if></td>
											<td><c:if test="${empty welRecordVO.order_id}">N/A</c:if>${welRecordVO.order_id}</td>
											<td>${welRecordVO.tns_time}</td>
											<td>${welRecordVO.tns_amount}</td>

										</tr>
									</c:forEach>
								</tbody>
							</table>


							<!-- Button trigger modal -->



							<div class="panel-group tabbed" style="margin-top: 20px">

								<div class="panel">

									<div class="panel-heading">
										<a class="panel-title collapsed" data-toggle="collapse"
											href="#panel4" style="font-weight: bold">進階訂單查詢</a>
									</div>
									<div id="panel4" class="panel-collapse collapse">
										<div class="panel-body text-gray">
											<a href="<%=request.getContextPath()%>/frontend/shop/order/OrderSearch.jsp"><button class="btn btn-sm btn-warning-filled btn-rounded">一般訂單查詢</button></a>
											<a href="<%=request.getContextPath()%>/frontend/preproduct\order_Success_List.jsp"><button class="btn btn-sm btn-warning-filled btn-rounded">預購訂單查詢</button></a>
											<button class="btn btn-sm btn-warning-filled btn-rounded">競標訂單查詢</button>
											<button class="btn btn-sm btn-warning-filled btn-rounded">見面會查詢</button>
										</div>
									</div>
								</div>
								<!-- / panel -->
							</div>



						</div>



						<!-- 儲值視窗 -->
						<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">


									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h5 style="text-align: center">儲值視窗</h5>
									</div>

									<FORM METHOD="POST"
										ACTION="<%=request.getContextPath()%>/welRecord/welRecord.do">
										<div class="modal-body">

											<p>請輸入儲值金額</p>
											<div class="form-group">
												<input type="text" class="form-control" id="depositAmount"
													placeholder="1000" name="depositAmount" required
													data-error="*Please fill out this field">

												<div class="help-block with-errors"></div>



											</div>


										</div>
										<div class="modal-footer" style="">
											<p class="space-button">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">返回</button>
												<button type="submit" class="btn btn-primary-filled" style="margin:5px">送出</button>
												<input type="hidden" name="action" value="deposit">
										</div>
									</FORM>
								</div>
							</div>
						</div>

						<!-- 提領視窗 -->
						<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h5 style="text-align: center">提領視窗</h5>
									</div>
									<FORM METHOD="POST"
										ACTION="<%=request.getContextPath()%>/welRecord/welRecord.do">
										<div class="modal-body">

											<p>請輸入提領金額</p>
											<div class="form-group">
												<input type="text" class="form-control" id="withdrawAmount"
													placeholder="1000" name="withdrawAmount" required
													data-error="*Please fill out this field">

												<div class="help-block with-errors"></div>



											</div>


										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">取消</button>
											<button type="submit" class="btn btn-primary-filled" style="margin:5px">送出</button>
										</div>
										<input type="hidden" name="action" value="withdraw">
									</FORM>
								</div>
							</div>
						</div>


					</div>


					<!-- / wishlist -->
				
				
				    </div>
</div>
				<!-- / account-info -->
			</div>
			<!-- / row -->
		</div>
		<!-- / container -->
<!-- alert通知 -->
		<div class="alert alert-warning alert-dismissible" role="alert"
			id="alertWarn"
			style="display: none; position: fixed; bottom: 0; width: 100%">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>密碼設定失敗</strong> 請檢查密碼格式是否正確或密碼是否一致
		</div>
		
			<div class="alert alert-success alert-dismissible" role="alert"
			id="alertUpdateSuccess"
			style="display: none; position: fixed; bottom: 0; width: 100%">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>個人資料已更新成功！</strong> 
		</div>

	</section>
	<!-- / my-account -->
	<!-- / content -->
	<!-- scroll to top -->
	<a href="#top" class="scroll-to-top page-scroll is-hidden"
		data-nav-status="toggle"><em class="fa fa-angle-up"></em></a>
	<!-- / scroll to top -->
	
	
	
		<!-- Modal -->
		
		
		<button type="button" id="myTest" class="btn btn-primary btn-lg"
			data-toggle="modal" data-target="#myModal3" style="display: none;">
			</button>
		<div class="modal fade" id="myModal3" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">密碼修改成功通知</h4>
					</div>
					<div class="modal-body" style="text-align: center">
						密碼修改成功，下次登入請使用新密碼</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
					</div>
				</div>
			</div>
		</div>
	
		<button type="button" id="depositSuccessBtn" class="btn btn-primary btn-lg"
			data-toggle="modal" data-target="#myModal4" style="display: none;">
			</button>
		<div class="modal fade" id="myModal4" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">儲值結果通知</h4>
					</div>
					<div class="modal-body" style="text-align: center;">
						<h6>儲值成功！<br><br>您的錢包餘額：NT$${sessionScope.memVO.balance} </h6></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
					</div>
				</div>
			</div>
		</div>
	
	
	
		<button type="button" id="withdrawSuccessBtn" class="btn btn-primary btn-lg"
			data-toggle="modal" data-target="#myModal5" style="display: none;">
			</button>
		<div class="modal fade" id="myModal5" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">提領成功通知</h4>
					</div>
					<div class="modal-body" style="text-align: center">
					<h6>提領成功！<br><br>您的錢包餘額：NT$${sessionScope.memVO.balance} </h6></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
					</div>
				</div>
			</div>
		</div>
	
	
			<!-- photoModal -->
		<div class="modal fade" id="photoModal" tabindex="-1" role="dialog"
			aria-labelledby="photoModal">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">編輯個人頭像</h4>
					</div>
					<FORM
						action="<%=request.getContextPath()%>/members/headphotoHandler.do"
						method=post enctype="multipart/form-data">
						<div class="modal-body" style="text-align: center">

							<img id="preview"
								src="<%=request.getContextPath()%>/members/headphotoHandler.do?action=getPic&mem_id=<%=memVO.getMem_id()%>"
								style="width: 400px;">


							<div style="margin-left: 35%">
								<input type="file" id="myFile" name="headPhoto">
							</div>
							<input type="hidden" name="action" value="uploadPhoto">

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">取消</button>

							<button type="submit" class="btn btn-primary-filled">確認更換</button>

						</div>
					</FORM>
				</div>
			</div>
		</div>
	
	
	<!-- footer -->

	<%@include file="/frontend/bar/frontBarFooter.jsp"%>

	<!-- / footer -->



</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<%
	java.sql.Date m_bday = null;
	try {
		m_bday = memVO.getM_bday();
	} catch (Exception e) {
		m_bday = new java.sql.Date(System.currentTimeMillis());
	}
%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/frontend/members/datetimepicker/jquery.datetimepicker.css" />
<script
	src="<%=request.getContextPath()%>/frontend/members/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/frontend/members/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#birthday').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=m_bday%>',   // value:   new Date(),

        });
        
       
        
</script>

<script src="<%=request.getContextPath()%>/frontend/members/jqueryJs/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		//取得聯絡地址及郵遞區號
        $.ajax({
        	url: "<%=request.getContextPath()%>/frontend/members/mem.do",
			type: "POST",
			data: {
				action: "getContactZip"
			},
			success: function(data) {	
				var memVO = JSON.parse(data);
				var zip = memVO.m_zip;
				var city = memVO.m_city;
				$('#contactZip option[value=\''+zip+' '+city+'\']').attr('selected', true);	
			}
		});

// 		檢驗是否為綠界刷卡付款後轉回此頁面
        <c:if test="${not empty EcpaySuccess}">
         var Ecpaystatus = ${EcpaySuccess};
		
    	if (Ecpaystatus === true){
    		$('#depositSuccessBtn').trigger('click');
    	}
        </c:if>
        
// 		檢驗是否剛提領成功		
        <c:if test="${not empty withdrawSuccess}">
            
        var withdrawStatus = ${withdrawSuccess};
		
    	if (withdrawStatus === true){
    		$('#withdrawSuccessBtn').trigger('click');
    	}
       </c:if>
    	
  
	});		

	$("#sameAsContactBtn").click(function() {
		$("#shipName").val($("#name").val());	
		$("#shipMobile").val($("#mobile").val());	
		$('#shipZip option[value=\''+$("#contactZip").val()+'\']').attr('selected', true);
		$("#shipAddr").val($("#contactAddr").val());
	});
	
	$("#memInfoBtn").click(function() {	
		 $('#alertUpdateSuccess').removeClass('show');
		 
		 var errormsg;
		 
		$.ajax({
    		url: "<%=request.getContextPath()%>/frontend/members/mem.do",
			type: "POST",
			data: {
				action: "UpdateMem",
				name: $("#name").val(),
				gender: $("#gender").val(),
				birthday: $("#birthday").val(),
				mobile: $("#mobile").val(),
				phone: $("#phone").val(),
				email: $("#email").val(),
				contactZip: $("#contactZip").val(),
				contactAddr: $("#contactAddr").val()
			},
			success: function(data) {
				if (data === "true") {
					$('#alertUpdateSuccess').addClass('show');
					console.log('succsess');
					
					if (errormsg !== null){
					 	   $.each(errormsg, function(key, val) {
						  	  	$("#" + key).next("div").remove();
						     });
					}	
				} else {
			
											  
					var msg = JSON.parse(data);
					errormsg = msg;
					$.each(msg, function(key, val) {
						$("#" + key).next("div").remove();
						
					    $("#" + key).after($("<div>").text(val).css("color","red"));
					    
					});
				}		
			}
		});
	});
	
	
	$("#submitPsw").click(function() {
		$.ajax({
    		url: "<%=request.getContextPath()%>/frontend/members/memLoginHandler.do",
			type: "POST",
			data: {
				action: "changePwd",
				oldPassword: $("#InputPassword1").val(),
				newPassword1: $("#InputPassword2").val(),
				newPassword2: $("#InputPassword3").val()
			},
			success: function(data) {
				$("#InputPassword1").val("");
				$("#InputPassword2").val("");
				$("#InputPassword3").val("");
									
				if (data === 'true') {
					$('#myTest').trigger('click');
					$('#alertWarn').css("display", "none");
					$('#changePwdBtn').trigger('click');
				} else {
					$('#alertWarn').css("display", "block");
				}
			}
		});
	});
</script>
<script>
	$("#headphoto").click(function() {

		$('#headphotoBtn').trigger('click');

	});

	$("#myFile").change(function() {

		var inputfile = $('#myFile')[0].files[0];

		if (inputfile.type.indexOf('image') != -1) {

			var reader = new FileReader();

			reader.addEventListener('load', function(e) {

				$("#preview").attr('src', e.target.result);
				//<img src="讀取到的結果e.target.result" >

			});

			reader.readAsDataURL(inputfile);
			//使用FileReader的readAsDataURL方法將取得的檔案傳入到預覽區塊

		} else {
			alert('請選擇圖片檔案');

		}

	});

	$("#eye").click(function() {

		if ($("#pwdGroup input").attr("type") === "password")

		{
			$("#pwdGroup input").attr("type", "text");
			$("#eye").html("<em class=\"zmdi zmdi-eye-off\"></em>隱藏密碼");

		} else {
			$("#pwdGroup input").attr("type", "password");
			$("#eye").html('<em class=\"zmdi zmdi-eye\"></em>顯示密碼');
		}
	});
	
	
	$('#smartbtnArea').click(function(){
		
		$('#InputPassword1').val('${sessionScope.memVO.m_psw}');
		$('#InputPassword2').val('888888');
		$('#InputPassword3').val('888888');
	});
</script>


<!-- sticky-sidebar -->
<script src="<%=request.getContextPath()%>/frontend/members/stickysidebarJs/sticky-sidebar.min.js"></script>
<script type="text/javascript">
    var sidebar = new StickySidebar('#sidebar', { 
        containerSelector: '#main-content', // 側邊欄外面的區塊
        innerWrapperSelector: '.sidebar__inner',
        topSpacing: 120, // 距離頂部 120px
   
        
    });
</script>


</html>