<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.preorder.model.*"%>
<%@ page import="com.preorderdetail.model.*"%>
<%
	PreOrderService preorderSvc = new PreOrderService();
    List<PreOrderVO> list = preorderSvc.getAll();
    pageContext.setAttribute("list",list);
    
    PreOrderDetailService detailSvc = new PreOrderDetailService();
    List<PreOrderDetailVO> list01 = detailSvc.getAll();
    pageContext.setAttribute("list01",list01);
%>
<%
	PreOrderVO preorderVO = (PreOrderVO) request.getAttribute("preorderVO"); 
%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
	
		<title>ArtsBlock預購商品首頁</title>
		
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
				  	<!-- Page Heading -->
			          <h1 class="h3 mb-2 text-gray-800">預購商品訂單總表</h1>
					  <div>
				          <c:if test="${not empty errorMsgs}">
							<script type="text/javascript">
								alert("請修正以下錯誤:${errorMsgs}");
							</script>
						  </c:if>
			          </div>
			          <!-- DataTales Example -->
			          <div class="card shadow mb-4">
			            <div class="card-header py-3">
			              <h6 class="m-0 font-weight-bold text-primary">主訂單列表</h6>
			            </div>
			            <div class="card-body">
			              <div class="table-responsive">
			                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
			                  <thead>
			                    <tr>
			                      <th>訂單編號</th>
			                      <th>會員編號</th>
			                      <th>訂單成立</th>
			                      <th>收件人</th>
			                      <th>狀態</th>
			                      <th>訂單總額</th>
			                      <th>修改訂單</th>
			                    </tr>
			                  </thead>
			                  <tfoot>
			                    <tr>
			                      <th>訂單編號</th>
			                      <th>會員編號</th>
			                      <th>訂單成立</th>
			                      <th>收件人</th>
			                      <th>狀態</th>
			                      <th>訂單總額</th>
			                      <th>修改訂單</th>
			                    </tr>
			                  </tfoot>
			                  <%@ include file="pages/page4_forOrder.file" %>
			                  <tbody>
				                  <c:forEach var="preorderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				                    <tr>
					                    <td>
											<a>${preorderVO.po_no}</a>
					                    	<!-- Button trigger modal -->
											<button type="button" id="${preorderVO.po_no}" class="btn btn-primary float-right ${preorderVO.po_no}" data-toggle="modal" data-target="#exampleModalCenter${preorderVO.po_no}">
											 查看
											</button>
											<!-- Modal -->
											<div class="modal fade" id="exampleModalCenter${preorderVO.po_no}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
											  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
											    <div class="modal-content">
											      <div class="modal-header">
											        <h5 class="modal-title" id="exampleModalLongTitle">訂單明細</h5>
											        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
											          <span aria-hidden="true">&times;</span>
											        </button>
											      </div>
											      <div class="modal-body">
											      
											      <div class="card shadow mb-4">
									                <div class="card-header py-3">
									                  <h6 class="m-0 font-weight-bold text-primary">訂單編號 : ${preorderVO.po_no}</h6>
									              		</div>
									              		<div class="card-body">
									                		會員編號：${preorderVO.mem_id}
									                		<br>
									                		訂單成立時間：<fmt:formatDate value="${preorderVO.po_time}" pattern="yyyy/MM/dd"/>
									                		<hr>
									                		寄件資訊
									                		<br>
									                		收件人:${preorderVO.po_name}
									                		<br>
									                		郵遞區號：${preorderVO.po_zip}
									                		<br>
									                		連絡電話：${preorderVO.po_phone}
									                		<br>
									                		寄送地址：${preorderVO.po_addr}
									                		<br>
									                		訂單狀態: 
									                		<c:if test="${preorderVO.po_status == 1}">處理中</c:if>
									                		<c:if test="${preorderVO.po_status == 2}">出貨中</c:if>
									                		<c:if test="${preorderVO.po_status == 3}">已到貨</c:if>
									                		<c:if test="${preorderVO.po_status == 4}">已結案</c:if>
									                		<c:if test="${preorderVO.po_status == 5}">結案(已折讓)</c:if>
									                		<hr>
									                		訂單總金額：${preorderVO.po_total} 元
									                		<hr>
									              		</div>
									              <div class="card shadow mb-4">
									                <div class="card-header py-3">
									                  <h6 class="m-0 font-weight-bold text-primary">選購明細</h6>
									                </div>
									                <div class="card-body">
															<c:forEach var="preorderdetailVO" items="${list01}">
																<c:if test="${preorderVO.po_no == preorderdetailVO.po_no}">
																	商品編號: ${preorderdetailVO.po_prod_no}<br>
																	購買數量: ${preorderdetailVO.po_qty}件<br>
																	商品單價: $${preorderdetailVO.po_price}<br>
																	商品小計: $${preorderdetailVO.po_price*preorderdetailVO.po_qty}
																	<hr>
																</c:if>
															</c:forEach>
									                </div>
									              </div>
											      </div>
											      <div class="modal-footer">
											      </div>
											    </div>
											  </div>
											  </div>
											</div><!-- Modal end-->
					                    </td>
					                    <td>${preorderVO.mem_id}</td>
					                    <td><fmt:formatDate value="${preorderVO.po_time}" pattern="yyyy/MM/dd"/></td>
					                    <td>${preorderVO.po_name}</td>
					                    <td>
					                    	<c:if test="${preorderVO.po_status == 1}">處理中</c:if>
									        <c:if test="${preorderVO.po_status == 2}">出貨中</c:if>
					                		<c:if test="${preorderVO.po_status == 3}">已到貨</c:if>
					                		<c:if test="${preorderVO.po_status == 4}">已結案</c:if>
					                		<c:if test="${preorderVO.po_status == 5}">結案(已折讓)</c:if>
					                    </td>
					                    <td>${preorderVO.po_total}</td>
					                    <td>
						                    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/preproduct/preorder.do" style="margin-bottom: 0px;"> 
												<label>選擇狀態</label>
												<select name="po_status" onchange="submit();" >
													<option>
														<c:if test="${preorderVO.po_status == 1}">處理中</c:if>
												        <c:if test="${preorderVO.po_status == 2}">出貨中</c:if>
								                		<c:if test="${preorderVO.po_status == 3}">已到貨</c:if>
								                		<c:if test="${preorderVO.po_status == 4}">已結案</c:if>
								                		<c:if test="${preorderVO.po_status == 5}">結案(已折讓)</c:if>
													</option>
													<option value="1">處理中</option>
													<option value="2">出貨中</option>
													<option value="3">已到貨</option>
													<option value="4">已結案</option>
													<option value="5">結案(已折讓)</option>
												</select>
												<input type="hidden" name="po_no"  value="${preorderVO.po_no}">
												<input type="hidden" name="action" value="update_status">
											</FORM>
					                    </td>
				                    </tr>
				                  </c:forEach>
			                  </tbody>
			                </table>
			                
			              </div>
			            </div>
			          </div>

					<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					<%@include file="/backend/bar/footer.jsp"%>
				</div> <!--END OF content-->
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
		<script>

		</script>				
	</body>
	
	
</html>