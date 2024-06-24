<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	MemService memSvc = new MemService();
	MemVO memVO = memSvc.findByPrimaryKey("M000002");
	session.setAttribute("memVO", memVO);
%>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<input type="submit" value="確認" id="confirm"/>
</body>

</html>