<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wel_record.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.preproduct.model.*"%>

<%
	WelRecordVO welRecordVO = (WelRecordVO) request.getAttribute("welRecordVO");
	MemVO memVO = (MemVO) session.getAttribute("memVO");

    WelRecordService welRecordSvc = new WelRecordService();
    List<WelRecordVO> set = welRecordSvc.getWelRecordByMemID(memVO.getMem_id());
    pageContext.setAttribute("set",set);

	PreProductVO preproductVO = (PreProductVO) request.getAttribute("preproductVO");
%>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" integrity="sha512-PgQMlq+nqFLV4ylk1gwUOgm6CtIIXkKwaIHp/PAIWHzig/lKZSEGKEysh0TCVbHJXCLN7WetD8TFecIky75ZfQ==" crossorigin="anonymous" />
    <title>cart</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/frontend/preproduct/css/shoppingcart/Shopping_Cart.css">
    <style type="text/css">
    	.hid01{
    		display: none;
    	}
    	.hid02{
    		display: none;
    	}
    </style>
</head>
<body class="bg-light">
<%
	@SuppressWarnings("unchecked")
	List<PreProductVO> buylist = (Vector<PreProductVO>) session.getAttribute("preshoppingcart");
%>
<%
	if (buylist != null && (buylist.size() > 0)) {
%>
                            
    <div class="container-fluid">
    
        <div class="row">
            <div class="col-md-10 col-11 mx-auto">
            <span><strong><br>
            <c:if
				test="${not empty sessionScope.memVO.m_accno}">
				會員帳號:${sessionScope.memVO.m_accno}
			</c:if></strong></span>
                <div class="row mt-5 gx-3">
                    <div class="col-md-12 col-lg-8 col-11 mx-auto main_cart mb-lg-0 mb-5">
                    <!-- 左側邊的 div -->

                        <%
							for (int index = 0; index < buylist.size(); index++) {
								 PreProductVO order = buylist.get(index);
						%>
						
						<div class="card p-4">
						<h2 class="py-4 font-weight-bold">您選購的商品</h2>
                            <div class="row">
                                <!-- 購物商品圖的 div -->
                                <div class="col-md-5 col-11 mx-auto bg-light d-flex justify-content-center align-items-center shadow product_img">
                                    <img class="img01"; src="<%=request.getContextPath()%>/backend/preproduct/tools/materialP_show.jsp?MA_NO=<%=order.getMa_no()%>">
                                    <img class="img01"; src="<%=request.getContextPath()%>/backend/preproduct/tools/eventP_show.jsp?EVENT_P_NO=<%=order.getEvent_p_no()%>">
                                </div>
                                <!-- cart product details -->
                                <div class="col-md-7 col-11 mx-auto px-4 mt-2">
                                    <div class="row">
                                        <!-- 商品名稱 -->
                                        <div class="col-6 card-title">
                                            <h3 class="mb-4 product_name"><%=order.getPo_prod_no()%> </h3>
                                            <p class="mb-2"><%= index %> </p>
                                            <p class="mb-2"><%="p"+ index %></p>
                                            <p class="mb-3"><%=buylist.size()%></p>
                                        </div>
                                        <!-- 數量 增加/減少 -->
                                        <div class="col-6">
                                            <ul class="pagination justify-content-end set_quantity">
                                                <lem class="page-item">
                                                    <button class="page-link" onclick="decreaseNumber('<%="p"+ index %>','<%="a"+ index %>')"><em class="fas fa-minus"></em></button>
                                                </li>
                                                <lem class="page-item"><input type="text" name="buynumber" oninput="Calculate('<%="p"+ index %>','<%="a"+ index %>','<%= index %>')" class="page-link" value="0" id="<%="p"+ index %>" onkeyup="document.getElementById('<%="q"+ index %>').value=this.value"></li>
                                                <lem class="page-item">
                                                    <button class="page-link" onclick="increaseNumber('<%="p"+ index %>','<%="a"+ index %>')"><em class="fas fa-plus"></em></button>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <!-- remove move and price -->
                                    <div class="row">
                                        <div class="col-8 d-flex justify-content-between remove_wish">
                                        <form name="deleteForm" action="Shopping.do" method="POST">
                                        	<input type="hidden" name="action"  value="DELETE">
                                        	<input type="hidden" name="del" value="<%= index %>">
                                            <p><em class="fas fa-trash-alt"></em><input type="submit" value="移除商品"></p>
                                        </form>
                                            <p><em class="fas fa-heart"></em><a href="frontindex.jsp">繼續選購</a></p>
                                        </div>
                                        <div class="col-4 justify-content-end price_money">
                                        	<span class="hid02" id="<%= index %>" >0</span>
                                        	<input type="text" class="hid01" oninput="Calculate('<%="p"+ index %>','<%="a"+ index %>','<%= index %>')" class="page-link" value="1" id="<%="p"+ index %>">
                                            <h3>$ <span id="<%="a"+ index %>"><%=order.getPo_price()%></span></h3>
                                        </div>
                                   </div>
                               </div>
                          </div>
                      </div>
                  <%}%>
                      
                  </div>
                        <hr>
                        <!-- 第二件商品在此 -->
                        
                    <!-- 右側邊的 div -->
                    
                    <div class="col-md-12 col-lg-4 col-11 mx-auto mt-lg-0 mt-md-5">
                    <form name="checkoutForm" action="<%=request.getContextPath()%>/frontend/preproduct/Shopping.do" method="POST">
                    <%
							for (int index = 0; index < buylist.size(); index++) {
								 PreProductVO order = buylist.get(index);
					%>
                    	<input type="hidden" name="po_prod_no"  id="" value="<%=order.getPo_prod_no()%>">
                    	<input type="hidden" name="po_price"  id="" value="<%=order.getPo_price()%>">
                    	<input type="hidden" name="buynumber" oninput="Calculate('<%="p"+ index %>','<%="a"+ index %>','<%= index %>')" class="page-link" value="0" id="<%="p"+ index %>">
                    	
                    	
                    	
                    <%}%>
                        <div class="right_side p-3 shadow bg-white">
                            <h2 class="product_name mb-5">總計金額</h2>
                            <div class="price_indiv d-flex justify-content-between">
                                <p>商品小計</p>
                                <p>$<span id="product_total_amt">0</span></p>
                            </div>
                            <div class="price_indiv d-flex justify-content-between">
                                <p>運費</p>
                                <p><span id="shopping_charge">50</span></p>
                            </div>
                            <hr>
                            <div class="total-amt d-flex justify-content-between font-weight-bold">
                                <p>總金額(含運費)</p>
                                <p>$<span id="total_cart_amt">0</span></p>
                                
                                
                            </div>
                            <p class="space-bottom">
							<span><strong>您目前的錢包餘額：${sessionScope.memVO.balance}</strong></span>
						</p>
                            
                            <c:if test="${not empty sessionScope.memVO.m_accno}">
								<input type="hidden" name="mem_id"  class="form-control font-weight-bold" value="${sessionScope.memVO.mem_id}">	
                            	
                            </c:if>
                            	<input type="hidden" name="total_cart_amt"  id="total_cart_amt1" value="">
                            	
                                
                            
                        </div>
                        <!-- 買方備註範圍 -->
                        <div class="discount_code mt-3 shadow">
                            <div class="card">
                                <div class="card-body">
                                    <a class="d-flex justify-content-between" data-toggle="collapse" href="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                                      	  我要下訂單
                                        <span><em class="fas fa-chevron-down pt-1"></em></span>
                                    </a>
                                    <div class="collapse" id="collapseExample">
                                        <div class="mt-3">
                                        
	                                        <input type="text" name="po_name"  class="form-control font-weight-bold" placeholder="收件名字" value="${memVO.m_name}">
	                                        <input type="text" name="po_phone"  class="form-control font-weight-bold" placeholder="連絡電話" value="${memVO.m_phone}">
	                                        <input type="text" name="po_zip"  class="form-control font-weight-bold" placeholder="郵遞區號" value="${memVO.m_zip}">
	                                        <input type="text" name="po_addr"  class="form-control font-weight-bold" placeholder="寄送地址" value="${memVO.m_city}${memVO.m_addr}">
                                            <input type="text" name="note"  class="form-control font-weight-bold" placeholder="請填寫備註" value="">
                                            <br>
                                            <input type="hidden" class="btn btn-primary" value="確認付款" class="button">
                                            <input type="hidden" name="action" value="CHECKOUT">
                                			<input type="submit" class="btn btn-primary" value="確認付款" class="button">
                                        
                                            
                                        </div>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 備註結束 -->
                        <div class="mt-3 shadow p-3 bg-white">
                            <div class="pt-4">
                                <h5 class="mb-4"></h5>
                                <%
									for (int index = 0; index < buylist.size(); index++) {
								%>
									<%-- 引入各個購物籃商品取數量 --%>
			                    	<input type="hidden" name="po_qty"  id="<%="q"+ index %>" value="" onkeyup="document.getElementById('<%="p"+ index %>').value=this.value">
			                    <%}%>
                                
                                
                                <c:if test="${not empty errorMsgs}">
				              <font style="color:red">請修正以下錯誤:</font>
				              <ul>
				                <c:forEach var="message" items="${errorMsgs}">
				                  <li style="color:red">${message}</li>
				                </c:forEach>
				              </ul>
				            </c:if>
                            </div>
                        </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script type="text/javascript">
    var product_total_amt = document.getElementById('product_total_amt');
    var shoppingcart = document.getElementById('shoppingcart');
    var total_cart_amt = document.getElementById('total_cart_amt');
    var total_cart_amt1 = document.getElementById('total_cart_amt1');
    
        // 增加數量/減少數量 及 同步增加金額與減少金額
        const decreaseNumber = (incdec, itemprice,tmp) => {
            var itemval = document.getElementById(incdec);
            var itemprice = document.getElementById(itemprice);
            // console.log(itemval.value);
            
                itemval.value = parseInt(itemval.value) - 1;
  
        }
        const increaseNumber = (incdec, itemprice,tmp) => {
            var itemval = document.getElementById(incdec);
            var itemprice = document.getElementById(itemprice);
            // console.log(itemval.value);
                itemval.value = parseInt(itemval.value) + 1;

            }

        const Calculate = (incdec, itemprice,tmp) => {
        
         	var itemval = document.getElementById(incdec);
            var itemprice = document.getElementById(itemprice);
            var tmp = document.getElementById(tmp);
            
            if (itemval.value > 20) {
                itemval.value = 2;
                alert('單筆最多20件，不服來戰');
                itemval.style.background = 'red';
                itemval.style.color = '#fff';
            } else if(itemval.value < 0) {
                itemval.value = 0;
                alert('輸入負值，你當我傻嗎?');
            } else {
            	tmp.innerHTML = parseInt(itemval.value)*parseInt(itemprice.innerHTML);
            	var sum =0;
            	var summ = 0;
     			for(i=1;i<=<%=buylist.size()%>;i++){
     				console.log($("#"+(i-1)).html());
     				sum += parseInt($("#"+(i-1)).html());
     			} 
     			
     			console.log(sum);
     			product_total_amt.innerHTML = sum;
     			summ = sum + parseInt(shopping_charge.innerHTML);
            	total_cart_amt.innerHTML = sum + parseInt(shopping_charge.innerHTML);
            	var sum1=String(summ);
            	total_cart_amt1.setAttribute('value',sum1);
            }
        }
//         <div>
//         	<input>
//         </div>
//         document.createElement("div");
//         innerHTML="<input>";
        //
		
        
        
        
        
    </script>
    <%}else{%>
    
    <div class="container-fluid">
    
        <div class="row">
            <div class="col-md-10 col-11 mx-auto">
                <div class="row mt-5 gx-3">
                    <div class="col-md-12 col-lg-8 col-11 mx-auto main_cart mb-lg-0 mb-5">
                    <!-- 左側邊的 div -->
						<div class="card p-4">
						<h2 class="py-4 font-weight-bold">您尚未選購商品</h2>
                            <div class="row" >
                            <div style=>
								<p><em class="fas fa-heart"></em><a href="frontindex.jsp">繼續選購</a></p>
							</div>	
                          </div>
                      </div>
                  </div>
                        <hr>
                    <!-- 右側邊的 div -->
                    
                    <div class="col-md-12 col-lg-4 col-11 mx-auto mt-lg-0 mt-md-5">
                    <form name="checkoutForm" action="#" method="POST">
                   
                        <div class="right_side p-3 shadow bg-white">
                            <h2 class="product_name mb-5">總計金額</h2>
                            <div class="price_indiv d-flex justify-content-between">
                                <p>商品小計</p>
                               
                            </div>
                            <div class="price_indiv d-flex justify-content-between">
                                <p>運費</p>
                                
                            </div>
                            <hr>
                            <div class="total-amt d-flex justify-content-between font-weight-bold">
                                <p>總金額(含運費)</p>
                                
                                
                                
                            </div>
                            <p class="space-bottom">
							<span><strong>您目前的錢包餘額：${sessionScope.memVO.balance}</strong></span>
						</p>
                            
                            <c:if test="${not empty sessionScope.memVO.m_accno}">
								<input type="hidden" name="mem_id"  class="form-control font-weight-bold" value="${sessionScope.memVO.mem_id}">	
                            	
                            </c:if>
                            	<input type="hidden" name="total_cart_amt"  id="total_cart_amt1" value="">
                            	
                                
                            
                        </div>
                        <!-- 買方備註範圍 -->
                        <div class="discount_code mt-3 shadow">
                            <div class="card">
                                <div class="card-body">
                                    <a class="d-flex justify-content-between" data-toggle="collapse" href="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                                      	  我要下訂單
                                        <span><em class="fas fa-chevron-down pt-1"></em></span>
                                    </a>
                                    <div class="collapse" id="collapseExample">
                                        <div class="mt-3">
                                        
	                                        <input disabled="disabled" type="text" name="po_name"  class="form-control font-weight-bold" placeholder="收件名字" value="">
	                                        <input disabled="disabled" type="text" name="po_phone"  class="form-control font-weight-bold" placeholder="連絡電話" value="">
	                                        <input disabled="disabled" type="text" name="po_zip"  class="form-control font-weight-bold" placeholder="郵遞區號" value="">
	                                        <input disabled="disabled" type="text" name="po_addr"  class="form-control font-weight-bold" placeholder="寄送地址" value="">
                                            <input disabled="disabled" type="text" name="note"  class="form-control font-weight-bold" placeholder="請填寫備註" value="">
                                            <br>
                                            
                                			<input type="submit" class="btn btn-primary" value="確認付款" class="button">
                                        
                                            
                                        </div>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
    
    <%}%>
    
</body>

</html>