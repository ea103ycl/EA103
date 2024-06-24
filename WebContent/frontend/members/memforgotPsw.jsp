<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<!DOCTYPE html>
<html lang="en">

<head>

		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		

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
    

      <%@include file="/frontend/bar/frontBarTop.jsp"%>
    </header>
    <!-- / header -->
    <!-- content -->
    <!-- header-banner -->
    <div id="header-banner">
        <div class="banner-content single-page text-center" style="padding-bottom:20px">
            <div class="banner-border">
                <div class="banner-info" style="padding: 0px">
                    <div id="login-form">
                        <h3 class="log-title">忘記密碼</h3>
                        <div style="margin-bottom:40px">
<%--                             <!--          <img src="<%=request.getContextPath()%>+/frontend/members/images/forgot.png "> --> --%>
                            <img src="images/forgot.png" style="width: 100px">
                        </div>
                        
                        
                        <div class="form-group">
              
                           <p style="margin-bottom:15px">請輸入使用者名稱或註冊之電子信箱<br>將會發送電子郵件給您重設密碼</p>
                            <input type="text" class="form-control" id="input" placeholder="使用者名稱或註冊之電子信箱" required>
                            <div class="help-block with-errors"></div>
                              <button id="submitBtn" class="btn btn-md btn-primary-filled btn-log btn-rounded"
									type="button" style="width:100%">
								提交
								</button>
                      
                        </div>
    
                   
                    </div>
                                 
     
                </div><!-- / banner-info -->
                 
            </div><!-- / banner-border -->      
             </div><!-- / banner-content -->
               <div class="alert alert-success alert-dismissible" role="alert" id="alertSuccess" style="display: none;">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>寄送成功！</strong> 請到您的電子信箱查看收取信件或<a href="memLogin.jsp" style="color:white; text-decoration:underline;">重新登入</a>
                </div>
                  <div class="alert alert-warning alert-dismissible" role="alert" id="alertWarn" style="display: none;">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>注意!</strong> 查無此使用者名稱或電子信箱
                </div>
  
    </div>
    
   
    
    
    
    
    
    
    
    <!-- / content -->
  
    <!-- footer -->
    <footer class="light-footer" >
     	<%@include file="/frontend/bar/frontBarFooter.jsp"%>
        <div class="footer-info">
            <div class="container" style="text-align:center">
                <div class="copyright" >
                    <p><strong>© ArtsBlock 2020</strong></p>
                </div>
             
            </div><!-- / container -->
        </div><!-- / footer-info -->
    </footer>
    <!-- / footer -->


<script>

$("#submitBtn").click(function(){

	$("#submitBtn").text("寄送中...");
	$("#submitBtn").prop("disabled",true);
	
	$.ajax({
		url: "<%=request.getContextPath()%>/frontend/members/memLoginHandler.do",
								type : "POST",
								data : {
									action : "forgetPsw",
									input : $("#input").val(),
									
			
									
								},
								
								success : function(data) {
									
									console.log(data);

									if (data === 'true'){
										$("#submitBtn").text("提交");
										$("#submitBtn").prop("disabled",false);
										$('#alertWarn').css("display", "none");
									$('#alertSuccess').css("display", "block");
								
									
									}
									
									else{
										$("#submitBtn").text("提交");
										$("#submitBtn").prop("disabled",false);
										$('#alertSuccess').css("display", "none");
										$('#alertWarn').css("display", "block");
									}
										
									}
								
							});
				});


</script>

</body>

</html>