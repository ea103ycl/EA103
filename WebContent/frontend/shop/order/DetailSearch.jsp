<!-------------------------------------- nav ------------------------------------------>
        <%@include file="/frontend/bar/frontBarTop.jsp"%>
<!-------------------------------------- /nav ------------------------------------------>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="com.detail.model.*"%>
<%@ page import="com.eval.model.*"%>
<!DOCTYPE html>




<% 
String or_no = request.getParameter("or_no").trim();
DetailService detailSvc = new DetailService();
List<DetailVO> detail_list = new ArrayList<DetailVO>();
detail_list = detailSvc.detailSearch(or_no);
pageContext.setAttribute("detail_list", detail_list);
%>



<html lang="en">


<head>
    <meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>SB Admin 2 - Tables</title>
<!-- Custom fonts for this template -->
<!--     <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css"> -->
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
<!--     Custom styles for this template -->
<!-- <link href="css/sb-admin-2.min.css" rel="stylesheet"> -->
<!--     Custom styles for this page -->
<link href="vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">
<body>


<style type="text/css">

    .star {position:relative;display:inline-block;line-height:1em;}
    .star input {display:block;float:left;margin:0;padding:0;width:1em;height:1em;font:inherit;background:center 0/cover no-repeat;outline:0 none transparent;
        -webkit-appearance:none;
        -moz-appearance:none;
        appearance:none;
    }
    .star label {display:none;float:right;margin-left:0.5em;}
    .star input:first-child {
        display:none;
        /* -webkit-appearance:radio;-moz-appearance:radio;appearance:radio; */
    }

    .star input:checked ~ input {background-position:center -1em;}
    .star:hover input:checked ~ input {background-position:center 0;}
    .star:hover input:hover ~ input {background-position:center -1em;}

    .star input:checked + label {display:block;}
    .star:hover input:checked + label {display:none;}
    .star:hover input:hover + label {display:block;}

    .star-once input:first-child:not(:checked) ~ input {background-position:center 0;pointer-events:none;}
    .star-once input:not(:first-child):checked ~ input {background-position:center -1em;}

    .star-once input:first-child:not(:checked) ~ label {display:none;}
    .star-once input:not(:first-child):checked + label {display:block;}

    .star-lock {pointer-events:none;}

    /*
     以上的 css 可寫入統一樣式表
     以下的樣式 可在用的地方分別處理，自定義 大小，星星圖片
     */
    .star {font-size:30px;}
    .star input {background-image:url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAA8CAYAAABxVAqfAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6RjJFRDE5Qjg0NTYwMTFFOTk5QzM5RjY0NTY0MDc0MEUiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6RjJFRDE5Qjk0NTYwMTFFOTk5QzM5RjY0NTY0MDc0MEUiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDpGMkVEMTlCNjQ1NjAxMUU5OTlDMzlGNjQ1NjQwNzQwRSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDpGMkVEMTlCNzQ1NjAxMUU5OTlDMzlGNjQ1NjQwNzQwRSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PtLQhvIAAAOnSURBVHja7FdbSBRRGHbKSizKzIzNagnSpBvkbmEPXSiK6Lb0EEJEkkUURNFD0IOvQdAVFgoRErqgQQ/RDYSEjKCLuz5EJElEkZRb2YpGaBe371/+ibPTnHXO3CRw4OPMnDlzvvnPOf/l01KpVM5/dYVisYPAIrvfazZJS9B0AZ+AQDwcHlKdY4zNHz7HbTGw0ReLYW0AzQehK8FWp7y2+IzheQaw3lOLYS2RdJu8+giUqFitavEpST8t/1pPLIa10/kUy673QNCq1SoWnxzm/WxgtS2LYRU95wNkXRBYACwD1gBzLcxH/hwHHgHtwCteiSRWYjCDGGTL0e7jvy3zMNj9Ap4BreQZRHwHN5t9jrgR2uPtwBMfSWux7Lc03ttcNPdVDofN6xhIT6f3WE+L4Xh8LJp7wAaPSA/HQqHo38Ml5mOQ09LfBLa6THoApHUZp9pYCDB5E7DDJdIakDb848dmFQjIae8vA7scku7EnjaacZgSa5pGB24cbn84IG0FKQWeHDOObCFzkkNrl9qN1XMcEk/Gqo23Q7zEhYNVZId4lQvE8+wQuxG/K5SIOYQGskxYD0zjtBnPMk5aleRK+gsl/TeAQ3CTBD9/JbfHj4bRXjNJq+ukhYAkgFSieSx0tQB7QfguS2mkcZK5SoWf8CoPkWvQqsVBbtuAahB2DLeZXGs9wA9QCbSFI18BuRXw2arFeWhmYrI3DrQVZbty4CUsTlkiHlWLo2pxpNXiJSb1XS1OBVZwgeibWoyCpBdts37A/FKLhSBOcj9Fqdt+qMULOilftNRf/FCLRSDuMXwX4XrcM7VYjwn2m2wDrVwPJwVHarGXB5CYew68Ztfpk+0hFw8FnBLLuMpcCVSaZMGIxvXzQx5A12JM/sLluH4EzXlBLZ4wU4vfgFK87HaJVN//TLVoyJ+6Whxg8i6HpFWsw9JqEfNFZadaVIskX+Zj8FubpHs4rKbVIuapyxpAmFxXi3QSF+KjTtVcTf6uq0V832ApcrE/62pxiH03aZF0E5q7olq0HLnYZWqEMb8VDP4pqMVGOyFTVIv9CsSdbqnFNsUkn3BLLTZL9nIWkG+yTQPsjo7V4lNjegSaOPj34/6oiWUtbqjFDiacAlzknFwlfH+Wf6Cag5BIXKFUCHAI1U9nObtVrUEtHud66wp9wv19wG5gAnCdCgQs/TYV4mLhkGRTi/p4mVr8jrETVYhdVYvGImBk1aLk791Si6VU/pjFgT8CDAD9y6CbITrTjwAAAABJRU5ErkJggg==');}
</style>

<!-- Begin Page Content -->

<div class="container-fluid">
  
    <div class="card shadow mb-4">
       
        <div class="card-body">
        
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0" style="margin: 160px 0 160px 0;">
                
                    <thead>
                        <tr>         
            <th>商品照片</th>
			<th>商品名稱</th>
			<th>商品單價</th>
			<th>購買數量</th>
			<th>評價</th>
			<th>回饋</th>
					
                        </tr>
                    </thead>
                    <tfoot>                      
                    </tfoot>
                    <tbody>
                    
                        <jsp:useBean id="prodSvc" scope="page" class="com.prod.model.ProdService" />
          
                        <c:forEach var="detailVO" items="${detail_list}">
                            <tr>
   <td style="text-align:center; vertical-align:middel;">
                                
   <img style=" height: 150px; width: auto;" src="<%=request.getContextPath()%>/frontend/shop/prod.pic?action=getpic&prod_no=${detailVO.prod_no}"   >
<%--   <form  action="<%=request.getContextPath()%>/frontend/shop/shopping" method="POST" enctype="multipart/form-data"> --%>
<%--   <input type="hidden" name="prod_no" value="${detailVO.prod_no}"> --%>
<!--   <input type="hidden" name="action" value="getOne_For_Detail">		 -->
<!--   <input type="submit" name="Submit" value="查看商品"   style=" height: 20px; width:100px; color:deeppink; font-size:10px;"> -->
    
<!-- </form>    -->
                                
   </td>                                  
                                <td>
                                 <a  href="<%=request.getContextPath()%>/frontend/shop/shopping?action=getOne_For_Detail&prod_no=${detailVO.prod_no}" ><u style="font-size:20px;">${pageScope.prodSvc.getOneProd(detailVO.prod_no).prod_name}</u></a>
                                </td>
                               <td><font size="5px" color="">$ ${detailVO.or_p_price}</font></td>
                                <td><font size="5px" color="">${detailVO.or_qty}</font></td>
          
           <jsp:useBean id="evalSvc" scope="page" class="com.eval.model.EvalService" />
           <td>
             <c:if test="${pageScope.evalSvc.getOneByOr_no_and_Prod_no(detailVO.or_no,detailVO.prod_no).eval_rang==1}">
     <div class="star star-lock">
    <input type="radio" name="rdStarLock"/><label>請打分</label>
    <input type="radio" name="rdStarLock" value="1"checked="checked"/><label></label>
</div>
          </c:if>
          
         <c:if test="${pageScope.evalSvc.getOneByOr_no_and_Prod_no(detailVO.or_no,detailVO.prod_no).eval_rang==2}">
     <div class="star star-lock">
    <input type="radio" name="rdStarLock"/><label>請打分</label>
    <input type="radio" name="rdStarLock" value="1"/><label></label>
    <input type="radio" name="rdStarLock" value="2"checked="checked"/><label></label>
   
</div>
          </c:if>
          
          <c:if test="${pageScope.evalSvc.getOneByOr_no_and_Prod_no(detailVO.or_no,detailVO.prod_no).eval_rang==3}">
     <div class="star star-lock">
    <input type="radio" name="rdStarLock"/><label>請打分</label>
    <input type="radio" name="rdStarLock" value="1"/><label></label>
    <input type="radio" name="rdStarLock" value="2"/><label></label>
    <input type="radio" name="rdStarLock" value="3"checked="checked" /><label></label>
</div>
          </c:if>
          
         <c:if test="${pageScope.evalSvc.getOneByOr_no_and_Prod_no(detailVO.or_no,detailVO.prod_no).eval_rang==4}">
     <div class="star star-lock">
    <input type="radio" name="rdStarLock"/><label>請打分</label>
    <input type="radio" name="rdStarLock" value="1"/><label></label>
    <input type="radio" name="rdStarLock" value="2"/><label></label>
    <input type="radio" name="rdStarLock" value="3"/><label></label>
    <input type="radio" name="rdStarLock" value="4"checked="checked"/><label></label>
</div>
          </c:if>
          
         <c:if test="${pageScope.evalSvc.getOneByOr_no_and_Prod_no(detailVO.or_no,detailVO.prod_no).eval_rang==5}">
 <div class="star star-lock">
    <input type="radio" name="rdStarLock"/><label>請打分</label>
    <input type="radio" name="rdStarLock" value="1"/><label></label>
    <input type="radio" name="rdStarLock" value="2"/><label></label>
    <input type="radio" name="rdStarLock" value="3" /><label></label>
    <input type="radio" name="rdStarLock" value="4"/><label></label>
    <input type="radio" name="rdStarLock" value="5"checked="checked"/><label></label>
</div>
          </c:if>
           </td>
           
           <td><font size="3px" color="">${pageScope.evalSvc.getOneByOr_no_and_Prod_no(detailVO.or_no,detailVO.prod_no).eval_review}</font></td>
                            </tr>
                        </c:forEach>
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
<!--------------------------------------- footer --------------------------------------->
<%@include file="/frontend/bar/frontBarFooter.jsp"%>
<!--------------------------------------- /footer --------------------------------------->
</body>

</html>