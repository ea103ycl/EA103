<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<!DOCTYPE html>
<%
	MemVO memVO = (MemVO) request.getAttribute("memVO");
%>


<html>

<head>
<title>LoginPage</title>

<style type="text/css" media="screen">
*, *:before, *:after {
	box-sizing: border-box;
}

body {
	min-height: 100vh;
	font-family: 'Raleway', sans-serif;
}

.container {
	position: absolute;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.container:hover .top:before, .container:hover .top:after, .container:hover .bottom:before,
	.container:hover .bottom:after, .container:active .top:before,
	.container:active .top:after, .container:active .bottom:before,
	.container:active .bottom:after {
	margin-left: 200px;
	transform-origin: -200px 50%;
	transition-delay: 0s;
}

.container:hover .center, .container:active .center {
	opacity: 1;
	transition-delay: 0.2s;
}

.top:before, .top:after, .bottom:before, .bottom:after {
	content: '';
	display: block;
	position: absolute;
	width: 200vmax;
	height: 200vmax;
	top: 50%;
	left: 50%;
	margin-top: -100vmax;
	transform-origin: 0 50%;
	transition: all 0.5s cubic-bezier(0.445, 0.05, 0, 1);
	z-index: 10;
	opacity: 0.65;
	transition-delay: 0.2s;
}

.top:before {
	transform: rotate(45deg);
	background: #A78383;
}

.top:after {
	transform: rotate(135deg);
	background: #D2BC9D;
}

.bottom:before {
	transform: rotate(-45deg);
	background: #B0BFBC;
}

.bottom:after {
	transform: rotate(-135deg);
	background: #ccc;
}

.center {
	position: absolute;
	width: 400px;
	height: 400px;
	top: 50%;
	left: 50%;
	margin-left: -200px;
	margin-top: -200px;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	padding: 30px;
	opacity: 0;
	transition: all 0.5s cubic-bezier(0.445, 0.05, 0, 1);
	transition-delay: 0s;
	color: #333;
}

.center input {
	width: 100%;
	padding: 15px;
	margin: 5px;
	border-radius: 1px;
	border: 1px solid #ccc;
	font-family: inherit;
}

button {
	margin-top: 6%;
	width: 20%;
	height: 7%;
}

button #submitbtn {
	width: 100%;
	text-align: center;
	font-size: 10px;
}

a:link {
	color: grey;
}

a:visited {
	color: grey;
}

a:hover {
	color: black;
}

a:active {
	color: black;
}

table {
	text-align: right;
	
}

table #registerLink, #forgetPwdLink {
	text-decoration: none;
	font-size: 13px;
}

table #forgetPwdLink {
	margin-right: 5%;
}


img {
	width: 180px;
}
</style>
</head>

<body>
<!-- gittest -->
	<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/frontend/members/memLoginHandler.do">
		<div class="container">
			<div class="top"></div>
			<div class="bottom"></div>
			<div class="center"  style="">
			
				<a href="<%=request.getContextPath()%>/frontend/front_index.jsp" title=""><img
					src="<%=request.getContextPath()%>/frontend/members/images/logo.PNG" alt=""></a>
				<div style="text-align: left; color: red; font-size: 9px;width:100%; height:8%">
						<c:if test="${not empty errorMsgs}">
								
									<c:forEach var="message" items="${errorMsgs}"><em class="zmdi zmdi-alert-circle-o"></em>${message}<br>
									</c:forEach>
								
							</c:if>
                  </div>
				<input
					style="" type="text" placeholder="userID" id="accno"
					name="usrid" value="<%=(memVO == null) ? "" : memVO.getM_accno()%>" /> 
					<input type="password" placeholder="password" name="password" id="psw" value="<%=(memVO == null) ? "" : memVO.getM_psw()%>"/>
				<table style="width: 100%;">
					<tr>
						<td>
						
						 <a id="registerLink" href="memRegister.jsp">註冊會員</a> 
							<a id="forgetPwdLink" href="memforgotPsw.jsp">　忘記密碼</a></td>

					</tr>
				</table>
				 <button id="smartbtn1" type="button" style="margin:0 ;outline: 0;width:100%;height:100%; color: transparent; background-color: transparent; border-color: transparent; cursor: default;"></button>
				<button id="submitbtn" type="submit" style="margin:0 ">登入</button>
               <button id="smartbtn2" type="button" style="margin:0 ;outline: 0;width:100%;height:100%; color: transparent; background-color: transparent; border-color: transparent; cursor: default;"></button>
			</div>
		</div>
		
		<input type="hidden" name="action" value="login">
	</FORM>
</body>
<script src="<%=request.getContextPath()%>/frontend/members/jqueryJs/jquery.min.js"></script>
<script>

$('#smartbtn1').click(function(){
	$('#accno').val('peter123');
	$('#psw').val('000000');});
	
$('#smartbtn2').click(function(){
	$('#accno').val('peter520');
	$('#psw').val('000000');
});



</script>


</html>