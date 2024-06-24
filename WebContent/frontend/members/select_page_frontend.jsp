<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Caroline</title>

<style>

　body{

    background-image: url("images/fontpage.png");

}

  table#table-1 {
	width: 100%;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
    text-align: center;
  }
  table#table-1 h4 {
    color: white;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
  
  
    table#table-2 {
	width: 100%;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 10px;
    text-align: right;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>首頁</h3><h4>這是美美der首頁</h4></td></tr>
</table>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font>請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table id="table-2">
   <tr>
   <td><a href='memRegister.jsp'>我要註冊</a>　<a href='memLogin.jsp'>我要登入</a></td>
   

   </tr>
    	<tr><td><FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/frontend/members/memLoginHandler.do">
   <button type="submit" >我要登出</button>
   <input type="hidden" name="action" value="logout">
   </FORM></td></tr>
 
   
   <tr> 
   <td>
         <c:if test="${not empty sessionScope.memVO.m_accno}">
			 歡迎${sessionScope.memVO.m_accno}登入
		</c:if>
    </td>
    </tr>
    
      
    <tr>
   <td><a href='<%= request.getContextPath()%>/frontend/members/memArea.jsp'>會員專區</a>
   </tr>
</table>

  
    
</body>

</html>