<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>


      
     <form name="shoppingForm" action="<%=request.getContextPath()%>/frontend/shop/shopping" method="POST" enctype="multipart/form-data">
     
       <input type="hidden" name="ptr_no" value=55 >
        <input type="hidden" name="action" value="Ptr_Search">
          <input type="submit" value="依照作品編號找尋相關商品">
       </form>


</body>
</html>