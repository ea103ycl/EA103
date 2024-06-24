<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%@ page import="com.preproduct.model.*"%>
<%
    PreProductService preproductSvc = new PreProductService();
    List<PreProductVO> list = preproductSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>
<html>
	
	<head>
	
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
	
		<title>我是標題</title>
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
						
<%-- 						頁面標題 --%>
<!-- 						<h1 class="h3 mb-2 text-gray-800">頁面標題</h1> -->
						
<%-- 						錯誤表列 --%>
<%-- 						<c:if test="${not empty errorMsgs}"> --%>
<!-- 							<font style="color: red">請修正以下錯誤:</font> -->
<!-- 							<ul> -->
<%-- 								<c:forEach var="message" items="${errorMsgs}"> --%>
<%-- 									<li style="color: red">${message}</li> --%>
<%-- 								</c:forEach> --%>
<!-- 							</ul> -->
<%-- 						</c:if> --%>
	
<%-- 						資料內容 --%>
	

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800" id="text_h3"><br>預購商品</h1>
          
          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3" id="add_bottom_area">
              <h6 class="m-0 font-weight-bold text-primary">預購商品清單</h6>
              <a href="<%=request.getContextPath()%>/backend/preproduct/addPreProduct.jsp" id="add_bottom" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">新增商品</a>
              
              <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/preproduct/preproduct.do" >
		        <b>搜尋預購商品編號 :</b>
		        <input type="text" name="po_prod_no">
		        <input type="hidden" name="action" value="getOne_For_Display">
		        <input type="submit" value="送出">
		   	  </FORM>

            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>預購商品編號</th>
                      <th>投稿作品編號</th>
                      <th>素材編號</th>
                      <th>預購開始時間</th>
                      <th>預購結束時間</th>
                      <th>原價</th>
                      <th>細節</th>
                      <th>修改</th>
                      <th>刪除</th>                      
                    </tr>                    
                  </thead>
                  
                  <tfoot>
                    <tr>
                      <th>預購商品編號</th>
                      <th>投稿作品編號</th>
                      <th>素材編號</th>
                      <th>預購開始時間</th>
                      <th>預購結束時間</th>
                      <th>原價</th>
                      <th>細節</th>
                      <th>修改</th>
                      <th>刪除</th>                      
                    </tr>
                  </tfoot>
                  
                  <%@ include file="pages/page1.file" %>
                  <tbody>

                   

                      <tr>
                       		<td>${preproductVO.po_prod_no}</td>
                            <td><img class="img01"; src="tools/eventP_show.jsp?EVENT_P_NO=${preproductVO.event_p_no}">
                            <td><img class="img01"; src="tools/materialP_show.jsp?MA_NO=${preproductVO.ma_no}"></td>
                            <td>${preproductVO.po_start}</td>
                            <td>${preproductVO.po_end}</td>
                            <td>${preproductVO.po_price}</td>
                            <td>${preproductVO.po_detail}</td>
                            
                        <td>
                          <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/preproduct/preproduct.do" style="margin-bottom: 0px;">
                             <input type="submit" value="修改">
                             <input type="hidden" name="po_prod_no"  value="${preproductVO.po_prod_no}">
                             <input type="hidden" name="action" value="getOne_For_Update"></FORM>
                        </td>
                        
                        <td>
                          <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/preproduct/preproduct.do" style="margin-bottom: 0px;">
                             <input type="submit" value="刪除">
                             <input type="hidden" name="po_prod_no"  value="${preproductVO.po_prod_no}">
                             <input type="hidden" name="action" value="delete"></FORM>
                        </td>
                      </tr>
                   
                    
                   </tbody>
                    
                </table>
                <%@ include file="pages/page2.file" %>
              </div>
            </div>
          </div>
	
	
						<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					
				</div> <!--END OF content-->
				
				<%@include file="/backend/bar/footer.jsp"%>
				
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
	
		<script>
		<!--JavaScript內容-->
		</script>
						
	</body>
	
</html>