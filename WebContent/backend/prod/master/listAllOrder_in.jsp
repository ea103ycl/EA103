<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="com.order.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<%
	OrderService orderSvc = new OrderService();
	List<OrderVO> list = orderSvc.getAll();
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
<form  action="<%=request.getContextPath()%>/frontend/shop/order" method="POST" enctype="multipart/form-data">
   <select  name="or_status" style="width:200px;font-size:15px; margin:0 0 10px 0;"onchange="submit();" >
   
   <option value="">選擇訂單狀態</option>
   <option value="100">查看全部</option> 
   <option value="1">尚未出貨</option> 
   <option value="2">已出貨</option> 
   <option value="3">買家已收到貨</option>
   <option value="4">訂單完成</option>  
   </select>
   <input type="hidden" name="action" value="Get_Order_By_Status"> 
</form>   
    <div class="card shadow mb-4">
       
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
            <th>訂單編號</th>
            <th>會員編號</th>
			<th>收件人姓名</th>
			<th>收件人電話</th>
			<th>郵遞區號</th>
			<th>收件地址</th>
			<th>訂單總金額</th>
			<th>購買備註</th>
			<th>訂單成立時間</th>
			<th>訂單狀態</th>	
                        </tr>
                    </thead>
                    <tfoot>                   
                    </tfoot>
                    <tbody>
            <div style="margin:5px 0 0 0;"><%@ include file="page1.file" %></div>
                        <c:forEach var="orderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				<tr>
				    <td>${orderVO.or_no}</td>
					<td>${orderVO.mem_id}</td>
					<td>${orderVO.or_name}</td>
					<td>${orderVO.or_phone}</td>
					<td>${orderVO.or_zip}</td>
					<td>${orderVO.or_addr}</td>
					<td>${orderVO.or_total}</td>
					<td>${orderVO.or_note}</td>
					<td><fmt:formatDate value="${orderVO.or_time}" pattern="yyyy-MM-dd HH:mm" /></td>
					
					<td> 
					<c:if test="${orderVO.or_status==1}"><font style="color:red;">待出貨</font></c:if> 
					<c:if test="${orderVO.or_status==2}">運送中</c:if> 
					<c:if test="${orderVO.or_status==3}">已到貨</c:if> 
					<c:if test="${orderVO.or_status==4}">訂單完成</c:if>
					</td>
					
					<td>
										
											<input type="hidden" name="or_no" value="${orderVO.or_no}">
											<button type="button" class="btn btn-primary btn-sm" onclick='openWindows("${orderVO.or_no}")' >查看訂單詳情</button>
			<script>											
			function openWindows(or_no){
				window.open("<%=request.getContextPath()%>/frontend/shop/order/DetailSearch.jsp?or_no=" + or_no ,"Sample","fullscreen=no,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no, copyhistory=no,width=1000,height=430,left=750,top=150");
			}
	     </script>
										
					</td>
					
					<td>
					<c:if test="${orderVO.or_status==1}">
				<FORM METHOD="post"  action="<%=request.getContextPath()%>/frontend/shop/order"	enctype="multipart/form-data" style="margin-bottom: 0px;"  onclick="javascript:alert('出貨囉!!')">
							<input  type="submit"  value="出貨" class="btn btn-primary">
							<input type="hidden" name="or_status" value="${orderVO.or_status}">
							<input type="hidden" name="or_no" value="${orderVO.or_no}">
							<input type="hidden" name="action" value="Change_Order_Status">
				</FORM>
				 </c:if>
					</td>
					
					
				</tr>
				
			</c:forEach>
			<%@ include file="page2.file" %>
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