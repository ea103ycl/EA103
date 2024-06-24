<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.preorder.model.*"%>
<%@ page import="com.preorderdetail.model.*"%>
<%
String po_no = (String) request.getAttribute("po_no");

PreOrderDetailService preorderdetailSvc = new PreOrderDetailService();
List<PreOrderDetailVO> list1 = preorderdetailSvc.getAllByPo_no(po_no);
pageContext.setAttribute("list1",list1);
%>



<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>



</body>
</html>