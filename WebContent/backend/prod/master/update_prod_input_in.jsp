<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.prod.model.*"%>

<%
  ProdVO prodVO = (ProdVO) request.getAttribute("prodVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
 %>

<html lang="en">
  
    <style type="text/css" media="screen">
        html, body {
            padding: 0px;
            margin: 0px;
            font-family: microsoft jhengHei;
            /*background-color: #F0F0F0;*/
        }

        #wrapper {
            width: 100%;
           text-align: center;
        }
        #wrapper header {
        	width: 100%;
            text-align: right;
            background-color: #C0C0C0;
        }
        #wrapper header h1 {
            float: left;
            color: darkcyan;
            margin-left: 2%;
            font-size: 40px;
            margin-top: 10px;
        }
        #wrapper header a {
            font-weight: bold;
            line-height: 80px;
            text-decoration: none;
            color: black;
            margin: 0px 10px;
            width: 150px;
            display: inline-block;
            text-align: center;
            font-size: 20px;
        }
        #wrapper header a span {
            display: none;
        }
        #wrapper header a i {
            font-style: normal;
        }
        #wrapper header a:hover span {
            display: inline-block;
            color: black;
        }
        #wrapper header a:hover i {
            display: none;
        }
        #personalpic {
            text-align: center;
            margin-top: 20px;
        }

        form {
            max-width: 800px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 150px;
            width: 800px;
            /*text-align: center;*/
            background-color: white;
            font-family: Microsoft JhengHei;
        }
        legend {
            background-color: white;
            font-size: 25px;
            background-color: #C0C0C0;
            border: solid 1px grey;
            border-radius: 5px;
            font-family: "Courgette", 微軟正黑體;
            font-weight: bold;
        }
       
        .show_pic {
            width: 20px;
            height: 20px;
        }
        
        }
        .AlignRight {
            text-align: right;
        }
        .Data-Items {
            float: left;
            width: 450px;
            height: 660px;
            margin-top:15px;
        }
        .Data-Title {
            float: left;
            width: 25%;
            margin-right: 20px;
            margin-top:16px;
        }
        .Data-Title label{
        	margin-bottom: 0;
        }
        .Data-Content {
            width: 100%;
            line-height: 60px;
            font-family: Microsoft JhengHei;
        }
        .input-div{
			height:60px;  
        }
        input {
            border: 0.5px solid #9D9D9D;
            padding: 6px;
            font-size: 15px;
            font-family: 微軟正黑體;
            font-weight: 400;
            width: 450px;
            height:30px;
            outline-color: #003D79;
        }
        input : focus {
            background-color: #66B3FF;
        }

        #file {
            border: 0;
            outline: none;
        }
        #file : focus {
            outline: none;
        }
		
        #btn-submit {
			   margin-left: -80px;
			   margin-top: 20px;
			   width: 130px;
			   height: 40px;
			   text-align: center;
			   background-color: #c0c0c0;
			   color: #fffafa;
			   border: 1px solid grey;
			   font-size: 20px;
			   /* margin: 10px auto; */
			   border-radius: 8px;
			   box-shadow: 3px 3px 9px black;
			   font-weight: bold;
                     }
                     
        #btn-submit:hover {
            border-top-color: #28597a;
            background: grey;
            color: #ccc;
        }
        #btn-submit:hover {
            border-top-color: #1f89cf;
            background: grey;
        }
        .Data-Items p {
            color: red;
            font-size: 15px;
            margin-top: 15px;
            margin-right: 15px;
        }
        .input-div p{
        	color: red;
            font-size: 3px;
            margin-top: -15px;
            margin-right: 15px;
        }
        .myFooter footer {
            clear: both;
            width: 100%;
            background-color: #333;
            margin-bottom: 0px;
        }
        .myFooter footer p {
            text-align: center;
            line-height: 100px;
            color: #fff;
            letter-spacing: 1px;
        }
        #whitespace {
            clear: both;
            height: 150px;
            width: 100%;
        }
    </style>
    
    
    
                  

    
    
    
    
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>修改商品</title>      
</head>


<body>
    <div id="wrapper">        
</div> 
    
        
    




 

<form method="post" action="prod.do" enctype="multipart/form-data">
<legend>修改商品</legend>
<img src="<%=request.getContextPath()%>/backend/prod/prod.pic?action=getpic&prod_no=<%=prodVO.getProd_no()%>" width="250" height="250">
	
	               <div class="Data-Content">
                    <div class="Data-Title">
                        <div class="AlignRight">
                        	
                        	<div><label><span>*</span>商品名稱</label></div>
                        	<div><label><span>*</span>商品價格</label></div>
                        	<div><label><span>*</span>商品描述</label></div>
                        	<div><label><span>*</span>選擇作品</label></div>
                        	<div><label><span>*</span>選擇素材</label></div>
                        	<div><label><span>*</span>商品狀態</label></div>
                        	<div><label><span>*</span>上傳商品照片</label></div>                                                                                                                                                                                                                                                                                    
                        </div>
                    </div>
	  
                       <div class="Data-Items"> 
                       
                        <div class="input-div">
                        	<input type="text" id="email" placeholder="商品名稱" name="prod_name" 
                        	value="<%=prodVO.getProd_name()%>">
                        	
                        </div>
                        
                        <div class="input-div">
                        	<input type="text" id="email" placeholder="商品價格" name="prod_price" 
                        	value="<%=prodVO.getProd_price()%>">                       	
                       </div>
                        
                        <div class="input-div">
                        	<input type="text" id="nickname" placeholder="商品描述" name="prod_detail" 
                        	value="<%=prodVO.getProd_detail()%>">
                        	
                        </div>
                 
                 
		

                         
<jsp:useBean id="prodSvc" scope="page" class="com.prod.model.ProdService" />

<div class="input-div">
  <select size="1" name="ptr_no" style=" width:450px; height:30px; font-size: 15px;"  >
   <c:forEach var="prodVO2" items="${prodSvc.allptr}">
     <option value="${prodVO2.ptr_no}" ${(prodVO2.ptr_no==prodVO.ptr_no)?'selected':'' }> ${prodVO2.ptr_no} ${prodVO2.ptr_nm}</option>
   </c:forEach>
  </select>
</div>
 
 
 
    <div class="input-div">
  <select size="1" name="ma_no" style=" width:450px; height:30px; font-size: 15px;  ">
   <c:forEach var="prodVO2" items="${prodSvc.allma}">
    <option value="${prodVO2.ma_no}" ${(prodVO2.ma_no==prodVO.ma_no)?'selected':'' }> ${prodVO2.ma_no} ${prodVO2.ma_name}</option>
   </c:forEach>
  </select>
</div>
                         
                                                          
                         
                          <div class="input-div">
	                        <select  name="prod_status" id="sexlist"  style="width: 450px;height: 30px;">
	                   <option value="0" ${(prodVO.prod_status==0)?'selected':'' }>下架</option>
	                   <option value="1" ${(prodVO.prod_status==1)?'selected':'' }>上架</option>
	                        </select>
                        </div>
                        
				
         <div value="<%=prodVO.getProd_pic()%>"  class="input-div"><input type="file" name="prod_pic" id="file" onchange="loadImageFile(event)"></div>
           <img id="image"  src=""  width="200" height="200" hidden="true"> 
           <input type="hidden" value="上傳">
         <script>
            function loadImageFile(event){ 
            var image = document.getElementById('image'); 
            image.src = URL.createObjectURL(event.target.files[0]);
            image.hidden = false;
            };
            
            </script> 
            
        




                                                                                               
						
   
						
						<div>
						<p style="font-color:red;"> * 為必填欄位，請填妥欄位資訊。</p>
						
							<input type="hidden" name="action" value="update">
                            <input type="hidden" name="prod_no" value="<%=prodVO.getProd_no()%>">
                        	<button type="submit" class="btn" id="btn-submit">修改商品</button>
						</div> 
						
						
						                       
                    </div>
                </div>
            </form>
          
        
    
    <div id="whitespace"></div>
    <div class="myFooter">
    
    
    </div>
</body>

</html>
	
	
	
	
	



