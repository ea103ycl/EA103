<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="com.prod.model.*"%>

<!DOCTYPE html>




<%
	ProdService prodSvc = new ProdService();
	List<ProdVO> list = prodSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>SB Admin 2 - Tables</title>
    <!-- Custom fonts for this template -->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">
    <!-- Custom styles for this page -->
    <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
</head>
<body>



<!-- Begin Page Content -->
<div class="container-fluid">
 
    <div class="card shadow mb-4">
       
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
            <th>商品照片</th>
            <th>商品編號</th>
			<th>作品</th>
			<th>素材</th>
			<th>商品名稱</th>
			<th>商品價格</th>
			<th>商品描述</th>
			<th>商品狀態</th>
			<th>商品修改</th>
			<th>上/下架</th>	
                        </tr>
                    </thead>
                    <tfoot>                   
                    </tfoot>
                    <tbody>
<div style="margin:5px 0 0 0;"><%@ include file="page1.file" %></div>
<jsp:useBean id="prodSvc2" scope="page" class="com.prod.model.ProdService" />
                        <c:forEach var="prodVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
                        
				<tr>
				<td>
				<img src="<%=request.getContextPath()%>/backend/prod/prod.pic?action=getpic&prod_no=${prodVO.prod_no}" width="100" height="100">
				</td>
				
					<td>${prodVO.prod_no}</td>
					<td>${prodVO.ptr_no}<br></br>${pageScope.prodSvc2.getOneByPtr_no(prodVO.ptr_no).ptr_nm}</td>
					<td>${prodVO.ma_no} <br></br>${pageScope.prodSvc2.getOneByMa_no(prodVO.ma_no).ma_name}</td>
					<td>${prodVO.prod_name}</td>
					<td>${prodVO.prod_price}</td>
					<td>${prodVO.prod_detail}</td>
					 <td>
					<c:if test="${prodVO.prod_status==1}">上架</c:if> 
					<c:if test="${prodVO.prod_status==0}">下架</c:if>
					</td>
					<td>
						<FORM METHOD="post"  action="<%=request.getContextPath()%>/backend/prod/prod.do"
							style="margin-bottom: 0px;">
							<input type="submit" value="修改" class="btn btn-primary"> 
							<input type="hidden" name="prod_no" value="${prodVO.prod_no}"> 
							<input type="hidden" name="action" value="getOne_For_Update">
						</FORM>
					</td>
					
					<td>
					<c:if test="${prodVO.prod_status==0}">
						<FORM METHOD="post"  action="<%=request.getContextPath()%>/backend/prod/prod.do"  onclick="javascript:alert('商品上架!!')"
							style="margin-bottom: 0px;">
							<input type="submit" value="上架/下架" class="btn btn-primary"> 
							<input type="hidden" name="prod_no" value="${prodVO.prod_no}">
							 <input type="hidden" name="prod_status" value="${prodVO.prod_status}">
							<input type="hidden" name="action" value="getOne_For_Status"> 
						</FORM>
						</c:if>
						
						<c:if test="${prodVO.prod_status==1}">
						<FORM METHOD="post"  action="<%=request.getContextPath()%>/backend/prod/prod.do"  onclick="javascript:alert('商品下架!!')"
							style="margin-bottom: 0px;">
							<input type="submit" value="上架/下架" class="btn btn-primary"> 
							<input type="hidden" name="prod_no" value="${prodVO.prod_no}">
							 <input type="hidden" name="prod_status" value="${prodVO.prod_status}">
							<input type="hidden" name="action" value="getOne_For_Status"> 
						</FORM>
						</c:if>
						
						
					</td>
				</tr>
			</c:forEach>
			<div style="margin:20px 0 0 0; font-size:18px;    display:inline-block;" > 
			<%@ include file="page2.file" %>
			<input class="btn btn-primary" style="margin: 0 0 10px 1250px;  display:inline-block; color:yellow;" type="button" value="我要新增商品" onclick="location.href='<%=request.getContextPath()%>/backend/prod/addProd.jsp'">
			</div> 
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- /.container-fluid -->
<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>
<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>
<!-- Page level plugins -->
<script src="vendor/datatables/jquery.dataTables.min.js"></script>
<script src="vendor/datatables/dataTables.bootstrap4.min.js"></script>
<!-- Page level custom scripts -->
<script src="js/demo/datatables-demo.js"></script>

</body>

</html>