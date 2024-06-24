<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>

<%
  Emp_Account_VO emp_Account_VO = (Emp_Account_VO) request.getAttribute("emp_Account_VO");
%>


<%
    Emp_Account_Service empSvc = new Emp_Account_Service();
    List<Emp_Account_VO> list = empSvc.getAll();
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
	
		<title>員工新增</title>
		<style>
			#preview img {
				width: 300px;
				height: 300px;
			}
		</style>
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
						
						<%-- 頁面標題 --%>
						<h1 class="h3 mb-2 text-gray-800">員工新增</h1>
						
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
	

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/emp/emp.do" name="form1" enctype="multipart/form-data">

<div class="container">
<div class="row align-items-center">



		<div class="col col-xs-12 col-sm-12 col-md-5">
			<div class="row" id="preview">
				<img src="<%=request.getContextPath()%>/backend/emp/images/avatar-icon-images-4.jpg" width="300" height="300" id="image">

			</div>


			<div class="input-group mb-3 mt-5" >
			  <div class="input-group-prepend">
<!-- 			    <span class="input-group-text" id="inputGroupFileAddon01" >Upload</span> -->
			  </div>
			  <div class="custom-file" style="max-width:300px">
			    <input type="file" class="custom-file-input" id="myFile" name="emp_photo" aria-describedby="inputGroupFileAddon01" >
			    <input type=hidden name="filename" id="filename">
			    <label class="custom-file-label" style = "width:280px" for="inputGroupFile01">Choose Photo</label>  
			  </div>
			</div>
		</div>


<div class="col col-xs-12 col-sm-12 col-md-6">

	<div class="input-group mb-3" style="max-width:430px"> 
	  <div class="input-group-prepend">
	    <span class="input-group-text" id="inputGroup-sizing-default">姓名:</span>
	  </div>
	  <input type="text" class="form-control"  name="emp_name" id="emp_name" value="<%= (emp_Account_VO==null)? "" : emp_Account_VO.getEmpName()%>"  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
	 </div>

	<div class="input-group mb-3" style="max-width:430px">
	  <div class="input-group-prepend">
	    <span class="input-group-text" id="inputGroup-sizing-default">職位:</span>
	  </div>
	  <input type="text" class="form-control"  name="emp_pos" id="emp_pos" value="<%= (emp_Account_VO==null)? "" : emp_Account_VO.getEmpPos()%>"  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
	</div>

	<div class="input-group mb-3" style="max-width:430px">
	  <div class="input-group-prepend">
	    <span class="input-group-text" id="inputGroup-sizing-default">信箱:</span>
	  </div>
	  <input type="text" class="form-control"  name="emp_mail" id="emp_mail" value="<%= (emp_Account_VO==null)? "" : emp_Account_VO.getEmpMail()%>"  aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
	</div>
	
	<div>
		<input type="hidden" name="emp_status"  value="1">
	</div>	

	<jsp:useBean id="funcidSvc" scope="page" class="com.emp.model.Func_No_List_Service"/>
	<table class="table table-borderless">
  		<tbody>
			<tr>
		 		<c:forEach var="funcidVO" items="${funcidSvc.all}" varStatus="vs">
	      		<td><input type="checkbox" id="${funcidVO.funcId }" name="func_id" value="${funcidVO.funcId }" >${funcidVO.funcName}</td>
		  		<c:if test="${vs.count % 3 == 0 }">
		  			<tr></tr>
		  		</c:if>	
	  			</c:forEach>
    		</tr>
  		</tbody>
	</table>
<div style = "margin-left:70px">
<!-- 	 <div class="container"> -->
<!-- 	  <div class="row"> -->
<!-- 	    <div class="col text-center"> -->
	      <button class="btn btn-primary" type="button" onclick="selAll();">全選</button>  
	      <button class="btn btn-primary" type="button" onclick="unselAll();">全取消</button>  
		  <button class="btn btn-secondary" type="button" id="click">Click Me</button>        
	      <button class="btn btn-primary" type="submit" name="action" value="insert">送出新增</button> 

<!-- 	    </div> -->
<!-- 	  </div> -->
<!-- 	</div> -->
</div>


</div>



</div>
</div>


	  

</FORM>

	
	

	
  <a class="scroll-to-top rounded" href="#page-top">
    <em class="fas fa-angle-up"></em>
  </a>
	
						<%-- 資料內容 --%>
	
	
						<!--===== 自定義內容end ================================================== -->
					</div> <!--END OF container-fluid-->
					
				</div> <!--END OF content-->
				
				<%@include file="/backend/bar/footer.jsp"%>
				
			</div><!--END OF content-wrapper -->
		</div><!--END OF wrapper -->
	
  
      <script type="text/javascript">
        // 題目： 請製作可以同時上傳多張圖片到前端預覽的功能
        // 學習重點：
        // 1. File API – Read as Data URL

        function init() {

            // 1. 抓取DOM元素
            var myFile = document.getElementById("myFile");
            var filename = document.getElementById("filename");
            var preview = document.getElementById('preview');

            // 2. 對myFile物件註冊change事件 - 改變選擇的檔案時觸發
            myFile.addEventListener('change', function() {
                // 取得檔案物件的兩種方式
                // 1. 直接從myFile物件上取得檔案物件 (因為非同步，一樣，多個classname註冊時會有問題)
                var files = myFile.files;
                preview.innerHTML='';
                // 2. 從event物件中取得他的soure target，也就是myFile物件，再取得檔案物件 
                // 檔案的基本資訊，包括：檔案的名稱、大小與文件型態

                // 判斷files物件是否存在
                if (files !== null) {
                    for (var i = 0; i < files.length; i++) {
                        var file = files[i];
                        console.log(file);
                        // 判斷file.type的型別是否包含'image'
                        if (file.type.indexOf('image') > -1) {
                            // 填入檔名
                            filename.value = file.name;
                            // new a FileReader
                            var reader = new FileReader();
                            // 在FileReader物件上註冊load事件 - 載入檔案的意思
                            reader.addEventListener('load', function(e) {
                                // 取得結果 提示：從e.target.result取得讀取到結果
                                // console.log(result) 確認讀取到結果
                                // 新增img元素
                                var img = document.createElement('img');
                                // 賦予src屬性
                                img.setAttribute('src', e.target.result);
                                // 放到div裡面
                                preview.append(img);
                            });
                            // 使用FileReader物件上的 readAsDataURL(file) 的方法，傳入要讀取的檔案，並開始進行讀取
                            reader.readAsDataURL(file); // ***** trigger
                        } else {
                            // 彈出警告視窗 alert('請上傳圖片！');
                            alert('請上傳圖片！');
                        }
                    }
                }
            });

        }

        window.onload = init;

        $("#click").click(function(){
      	  $("#emp_name").val("THANOS");
      	  $("#emp_pos").val("倉儲人員");
      	  $("#emp_mail").val("farmer7382@gmail.com");
        });
             
        function selAll(){
            var checkItem = document.getElementsByName("func_id");
            for(var i=0;i<checkItem.length;i++){
                checkItem[i].checked=true; 
            }
        }
        
        function unselAll(){
            var checkItem = document.getElementsByName("func_id");
            for(var i=0;i<checkItem.length;i++){
                checkItem[i].checked=false;
            }
        }
                    
    </script>   
						
	</body>
	
</html>