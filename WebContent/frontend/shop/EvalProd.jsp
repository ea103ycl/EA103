<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="com.detail.model.*"%>

<%
  List<DetailVO> list = (ArrayList<DetailVO>) request.getAttribute("detail_list");
  pageContext.setAttribute("list", list);

%>



<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Minimal Shop Theme">
<meta name="keywords" content="responsive, retina ready, html5, css3, shop, market, onli store, bootstrap theme" />
<meta name="author" content="KingStudio.ro">

<!-- favicon -->
<link rel="icon" href="images/favicon.png">
<!-- page title -->
<title>MS - Minimal Shop Theme</title>
<!-- bootstrap css -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- css -->
<link href="css/style.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<!-- fonts -->
<link href="https://fonts.googleapis.com/css?family=Rubik:400,500,700,900" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Lily+Script+One" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href='fonts/FontAwesome.otf' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/linear-icons.css">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

</head>

<body>

<!-- preloader -->
<div id="preloader">
    <div class="spinner spinner-round"></div>
</div>
<!-- / preloader -->

<div id="top"></div>

<!-- header -->
<header>

<!-------------------------------------- nav ------------------------------------------>
        <%@include file="/frontend/bar/frontBarTop.jsp"%>
<!-------------------------------------- /nav ------------------------------------------>   
        


</header>

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



<!-- / header -->

<!-- content -->

<!-- shop single-product -->
<section id="shop">
    <div class="container space-top-30">
        <div class="row">

            <!-- product content area -->
            <div class="col-sm-6 col-md-7 product-content-area">
                <div class="product-content-area">
                    <div id="product-slider" class="carousel slide" data-ride="carousel">
                        <!-- wrapper for slides -->
                        

                        <div class="carousel-inner" role="listbox"  >
                        
                        
                        
 <FORM METHOD="post"  action="<%=request.getContextPath()%>/frontend/shop/order"	enctype="multipart/form-data" >
 <jsp:useBean id="orderSvc" scope="page" class="com.order.model.OrderService" />
 <jsp:useBean id="prodSvc" scope="page" class="com.prod.model.ProdService" />
 
 <h4 style="margin: 100px 0 100px 0"></h4>
 <%Integer x =0; %>  
                        <c:forEach var="detailVO" items="${list}"> 
  <%x++;%>                      
          
                        <h2 style="margin: 0 0 0 250px "><font color="deeppink">${pageScope.prodSvc.getOneProd(detailVO.prod_no).prod_name}</font></h2>
                            <div>
                                <img src="<%=request.getContextPath()%>/frontend/shop/prod.pic?action=getpic&prod_no=${detailVO.prod_no}" width="100" height="300"  >
                            </div>
                           
                            
                                    
                                    <div class="row">
                                        
 
<div class="star"  style="margin:  20px 0 20px 200px ">
    <input type="radio" name="eval_rang<%=x%>" value="" checked="checked" required/><label>請打分</label>
    <input type="radio" name="eval_rang<%=x%>" value="1"/><label>太低了吧<</label>
    <input type="radio" name="eval_rang<%=x%>" value="2"/><label>再高一點</label>
    <input type="radio" name="eval_rang<%=x%>" value="3"/><label>還可以</label>
    <input type="radio" name="eval_rang<%=x%>" value="4"/><label>謝謝你</label>
    <input type="radio" name="eval_rang<%=x%>" value="5"/><label>超級感謝你</label>
</div>
                            <div class="col-sm-12 review-form" style="margin: 20px 0 0 0 ;">
                                <textarea name="eval_review<%=x%>" rows="7" class="form-control" placeholder="*留下你的評論" required   style="font-size:15px;color:blue;"></textarea> 
                            </div>
                             </div>
                        
                            
                            <input type="hidden" name="prod_no<%=x%>" value="${detailVO.prod_no}">
							<input type="hidden" name="or_no" value="${detailVO.or_no}">
							<input type="hidden" name="mem_id" value="${pageScope.orderSvc.getOneOrder(detailVO.or_no).mem_id}">
							
							 
                             </c:forEach> 
                            <input type="hidden" name="data_count" value="<%=x%>" >
							<input type="hidden" name="action" value="Eval_Prod_ok">
							<input type="submit" class="btn btn-submit btn-primary-filled btn-rounded" value="送出評價" style="margin: 10px 0 0 200px">
</FORM>
				         
 <c:if test="${not empty errorMsgs}">
		<ul style="margin: 35px 0 0 140px">
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:deeppink; font-size:20px;  ">${message}</li>
			</c:forEach>
		</ul>
	</c:if>       
				         
                        
                            
          
 </div>
				
                        </div>
                        


                        
                        <!-- / wrapper for slides -->

                        <!-- controls -->
                        <a class="left carousel-control" href="#product-slider" role="button" data-slide="prev">
                            
                        </a>
                        <a class="right carousel-control" href="#product-slider" role="button" data-slide="next">
                           
                        </a>
                        <!-- / controls -->
                    </div><!-- / product-slider -->

                    
                    <!-- / nav-tabs -->
                    <div class="tab-content">
                        
                        <!-- / description-tab -->

                        
                       
                      <!---------------------------  reviews-tab -------------------------------------------->
                       
                        <!--------------------------- / reviews-tab -------------------------------------------->
                    </div>
                    <!-- / tab-content -->
                </div><!-- / product-content-area -->

            </div>
            <!-------------------------------------- / product-content-area --------------------------------->


           

<!-------------------------------------- / 下拉 --------------------------------->
                    


    
<!-------------------------------------- / 加入購物車 --------------------------------->
                </div><!-- product-details -->

            </div><!-- / col-sm-4 col-md-3 -->
            <!-- / product sidebar area -->

        </div><!-- / row -->
<!-------------------------------------- / 相關作品????? --------------------------------->
       
                
<!----------------------------------------------- Related-product ------------------------------------------>

 <!---------------------------------------------- /Related-product ----------------------------------->

                

            </ul><!-- / row -->
        </div><!-- / related-products -->

    </div><!-- / container -->
</section>
<!-- / shop single-product -->

<!-- / content -->

<!-- scroll to top -->
<a href="#top" class="scroll-to-top page-scroll is-hidden" data-nav-status="toggle"><em class="fa fa-angle-up"></em></a>
<!-- / scroll to top -->

<!--------------------------------------- footer --------------------------------------->
<%@include file="/frontend/bar/frontBarFooter.jsp"%>
<!--------------------------------------- /footer --------------------------------------->

<!-- javascript -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<!-- sticky nav -->
<script src="js/jquery.easing.min.js"></script>
<script src="js/scrolling-nav-sticky.js"></script>
<!-- / sticky nav -->

<!-- hide nav -->
<script src="js/hide-nav.js"></script>
<!-- / hide nav -->

<!-- preloader -->
<script src="js/preloader.js"></script>
<!-- / preloader -->

<!-- / javascript -->
</body>

</html>