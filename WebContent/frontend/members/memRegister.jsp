<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<!DOCTYPE html>
<%
	MemVO memVO = (MemVO) request.getAttribute("memVO");
%>
<html>

<head>
<meta charset="utf-8">
<title>RegistrationForm</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- MATERIAL DESIGN ICONIC FONT -->
<link href="<%=request.getContextPath()%>/frontend/template/Caroline/material-design-iconic-font/css/material-design-iconic-font.min.css" rel="stylesheet">

<!-- STYLE CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/frontend/members/registerCss/styleRegister.css">

</head>

<body>
	<div class="wrapper" style="background-color: #ccc">

		<div class="inner" style="border-radius: 7px;">
			<div id ="logoImageDiv" class="image-holder" style="display: block; margin:50px 30px 30px 50px ">

				<a href="<%=request.getContextPath()%>/frontend/front_index.jsp" title=""><img id ="logoImage" src="<%=request.getContextPath()%>/frontend/members/images/logoFinal1.png" ></a>
			
				<%-- 錯誤表列 --%>
				<div id="usridCorrect" style="color: green; text-align: center"></div>
				<div id="usridWrong" style="color: red; text-align: center"></div>
				<c:if test="${not empty errorMsgs}">
					<div
						style="text-align: left; color: red; width: 80%; margin: 0px auto;">
						<h4>
							<em class="zmdi zmdi-alert-circle-o zmdi-hc-2x"
								style="color: red; display: inline-block; margin: auto 0px;"></em>
							請修正以下錯誤:<br><br>
						</h4>
					<table>
					<tr><td width="50%">
						<c:forEach var="message" items="${errorMsgs}" begin="0" end="4">
                            ${message}<br>
						</c:forEach>
						</td>
						<td width="50%">
						<c:forEach var="message" items="${errorMsgs}" begin="5" end="10">
                            ${message}<br>
						</c:forEach>
							</td>
						</tr>
					</table>
					</div>
				</c:if>
			</div>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/frontend/members/mem.do">
				<h3 id="smartbtn">Registration Form</h3>
				<div class="form-group">
					<input style="width: 180%" type="text" placeholder="姓名"
						class="form-control" name="name" 
						id="name" value="<%=(memVO == null) ? "" : memVO.getM_name()%>"> 
				    <input type="radio" name="gender" value="M" id="male"
						<c:if test="${memVO.m_gender=='M'}">checked</c:if>>男
						 <input type="radio" name="gender" value="F"  id="female"
						<c:if test="${memVO.m_gender=='F'}">checked</c:if>>女
					<%--   <input  type="radio" name="gender" value="F" <%=(memVO.getM_gender()=="F") ? "selected":"" %>>女 --%>
				</div>
				<div class="form-wrapper">
					<input type="text" placeholder="手機號碼" class="form-control"
						name="mobile" id="mobile"
						value="<%=(memVO == null) ? "" : memVO.getM_mobile()%>" /> <i
						class="zmdi zmdi-phone"></em>
				</div>
				<div class="form-wrapper">
					<input type="text" placeholder="電子信箱" class="form-control"
						name="email" id="email"
						value="<%=(memVO == null) ? "" : memVO.getM_email()%>"> <i
						class="zmdi zmdi-email"></em>
				</div>
				<div class="form-wrapper">
					<select name="zip" id="zip" class="form-control">
						<option value="" disabled selected>郵遞區號</option>
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
<option value="300 新竹市 ">300 新竹市 </option>
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
					</select> <em class="zmdi zmdi-caret-down" style="font-size: 17px"></em>
				</div>
				<div class="form-wrapper">
					<input type="text" placeholder="地址" class="form-control"
						name="address" id="address"
						value="<%=(memVO == null) ? "" : memVO.getM_addr()%>"> <i
						class="zmdi zmdi-home"></em>
				</div>
				<div class="form-wrapper">
					<input id="account" style="width: 70%; display: inline-block;"
						type="text" placeholder="帳號(6-20個英文數字)" class="form-control"
						name="usrid" 
						value="<%=(memVO == null) ? "" : memVO.getM_accno()%>">

					<button type="button" id="checkAccount"
						style="height: 20%; width: 20%; font-size: 12px; display: inline-block; margin: auto 0px; background-color: grey; color: white">檢查帳號</button>

				</div>
				<div class="form-wrapper">
					<input type="password" placeholder="密碼(6-20個英文數字)" id="password1"
						class="form-control" name="psw"> <em class="zmdi zmdi-lock"></em>
				</div>
				<div class="form-wrapper">
					<input type="password" placeholder="再次確認密碼" class="form-control"  id="password2"
						name="confirmpsw"> <em class="zmdi zmdi-lock"></em>
				</div>
				<button id="submitbtn" type="submit">
					<div id="submitdiv">
						Submit<em class="zmdi zmdi-arrow-right"></em>
					</div>
				</button>


				<input type="hidden" name="action" value="InsertMem">
			</form>
		</div>
	</div>
	<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script>
		$("#checkAccount").click(function(){
			
			var str = $("#account").val();
			var rules = /^[(a-zA-Z0-9)]{6,10}$/;
			console.log(rules.test(str));
		
			if (str == null|| str =="" || (!rules.test(str)))
			{
				$("#usridCorrect").html("");
				$("#usridWrong").html("<em class=\"zmdi zmdi-close zmdi-hc-2x\"></em>使用者名稱空白或格式不符");
				return;
			}
			
        	$.ajax({
        		url: "<%=request.getContextPath()%>/frontend/members/mem.do",
										type : "POST",
										data : {
											action : "checkAccount",
											usrid : $("#account").val()
										},
										success : function(data) {

											checkAccount(data);
										}
									});
						});

		function checkAccount(data) {
			if (data === "true") {
				$("#usridCorrect").html("");
				$("#usridWrong")
						.html(
								"<em class=\"zmdi zmdi-close zmdi-hc-2x\"></em>此使用者名稱已經有人使用");
			} else {
				$("#usridWrong").html("");
				$("#usridCorrect")
						.html(
								"<em class= \"zmdi zmdi-check zmdi-hc-2x\" ></em>此使用者名稱可以使用");
			}

		}
		
	
// 	神奇小按鈕
		$('#smartbtn').click(function(){
			$('#name').val('吳美美');
			$('#female').attr('checked',true);
			$('#mobile').val('0952123123');
			$('#email').val('caroline20126862@gmail.com');
			$('#zip option[value=\'320 桃園市 中壢區\']').attr('selected', true);
			$('#address').val('中央路300號');
			$('#account').val('peter520');
			$('#password1').val('000000');
			$('#password2').val('000000');
			
			
		});
		
      $('#submitbtn').click(function(){
	        $('#submitdiv').text('sending...');
			$('#submitbtn').prop('disable',true);
		});
	</script>
</body>

</html>