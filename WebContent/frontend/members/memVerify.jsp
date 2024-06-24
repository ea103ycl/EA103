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
        <div class="banner-content single-page text-center" style="padding-bottom:30px ;padding-top:50px">
            <div class="banner-border">
                <div class="banner-info" style="padding: 0px">
                    <div id="login-form" style="padding-bottom:0px">
                        <h3 class="log-title">設定密碼</h3>
                        <div style="margin-bottom:40px">

                            <img src="images/login.png" style="width: 100px">
                        </div>
                        
                        
                        <div class="form-group">
              
                           <p id="intro" style="margin-bottom:15px">請輸入您的新密碼</p>
                            <input type="text" class="form-control" id="password1" placeholder="新密碼" required>
                            <div class="help-block with-errors"></div>
                              <input type="text" class="form-control" id="password2" placeholder="再次輸入新密碼" required>
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
                    <strong>密碼重設成功！</strong> 下次登入請使用新密碼或<a href="memLogin.jsp" style="color:white; text-decoration:underline;">重新登入</a>
                </div>
                  <div class="alert alert-warning alert-dismissible" role="alert" id="alertWarn" style="display: none;">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>密碼設定失敗</strong> 請檢查密碼格式是否正確或密碼是否一致
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

	
	$("#submitBtn").text("更改中...");
	$("#submitBtn").prop("disabled",true);
	
	$.ajax({
		url: "<%=request.getContextPath()%>/frontend/members/memLoginHandler.do",
								type : "POST",
								data : {
									action : "changePwdfromEmail",
									newPassword1 : $("#password1").val(),
									newPassword2 : $("#password2").val(),
									
			
									
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