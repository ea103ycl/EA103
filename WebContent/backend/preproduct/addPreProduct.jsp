<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.preproduct.model.*"%>
<%@ page import="com.event_p.model.*"%>
<%@ page import="com.material.model.*"%>

<%
PreProductVO preproductVO = (PreProductVO) request.getAttribute("preproductVO");
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>add</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        body.uf-body{
	margin: 0;
	padding: 0;
	font-family: poppins;
	background: gray;
	background-image: url(<%=request.getContextPath()%>/backend/preproduct/imgs/getOne_For_Update_bg.jpg);
	-webkit-background-size:cover;/*龍:背景圖完全縮放到全版面*/
	background-size: cover;
	background-repeat: no-repeat;

}
.uf-wrapper{
	background-color: rgba(255,255,255,0.5);
	width: 800px;
	height: 500px;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%,-50%);
}
.uf-wrapper:before{
	content: '';
	width: 350px;
	height: 500px;
	background-color: rgba(0,0,0,0.5);
	position: absolute;
}
.uf-header-area h2{
	position: absolute;
	top: 20%;
	left: 20%;
	transform: translate(-50%,-50%);
	width: 25%;
	border: 12px inset #fff;
	text-align: center;
	color: #fff;
	font-size: 30px;
}
.uf-photo-area
{
	position: absolute;
	top: 63%;
	left: 21%;
	transform: translate(-50%,-50%);
}
.uf-photo-area img{
	width: 300px;
}
.uf-form-area{
	position: absolute;
	top: 50%;
	left: 70%;
	transform: translate(-50%,-50%);
	width: 300px;
	/*overflow: hidden;*/
}
label{
	font-size: 14px;

}
.uf-form-area select{
	width: 100%;

	margin-bottom: 10px;
}

.uf-form-area input{
	outline: none;
	padding: 0 0 0 30px;
	border: 1px solid rgba(0,0,0,0);
	border-bottom-color: #262626;
	background: transparent;
	width: 100%;
	height: 50px;
	margin-bottom: 10px;
}
.uf-form-area i{
	width: 20px;
	position: absolute;
	margin-top: 15px;
}
.uf-form-area input[type="submit" i]{
	width: 300px;
	border: none;
	background-color: #262626;
	color: #fff;
	cursor: pointer;
	padding: 0;
}
	
	</style>
</head>

<body class="uf-body">
    <div class="uf-wrapper" name="form2">
        <div class="uf-header-area">
            <h2>新增預購商品</h2>
        </div>
	
        <div class="uf-photo-area">
           <img id="pic1" >
           
        </div>
        <%-- 錯誤表列 --%>
        <c:if test="${not empty errorMsgs}">
           
            <script type="text/javascript">
		        alert("請修正以下錯誤:${errorMsgs}");
		    </script>
            
        </c:if>
      
        
        <form class="uf-form-area" method="post" action="preproduct.do" name="form1">
            <table>     
                <label>選擇作品編號</label>
                <jsp:useBean id="eventSvc" scope="page" class="com.event.model.EventService" />
                <jsp:useBean id="eventPSvc" scope="page" class="com.event_p.model.Event_PService" />
                <select name="event_p_no" onchange="setPic()">
                	<option selected="selected" disabled="disabled"  style='display: none' >--請選擇作品--</option>
                    <c:forEach var="eventpVO" items="${eventPSvc.getAll(eventSvc.findLastEndEvent())}">
                        <option value="${eventpVO.event_p_no}">${eventpVO.event_p_name} --- 第${eventpVO.vote_rank}名作品 ---
                    </c:forEach>
                </select>
                
                <jsp:useBean id="materialDataSvc" scope="page" class="com.material.model.Material_Data_Service" />
                <label>選擇素材編號</label>
                <select name="ma_no">
                	<option selected="selected" disabled="disabled"  style='display: none' value='null'></option>
                    <c:forEach var="materialdataVO" items="${materialDataSvc.all}">
                        <option value="${materialdataVO.maNo}">${materialdataVO.maName}
                    </c:forEach>
                </select>
                <br>
                <em class="fa fa-calendar-plus-o"></em>
                <input type="text" placeholder="預購開始時間" name="po_start" id="po_dateS">
                <em class="fa fa-calendar-times-o"></em>
                <input type="text" placeholder="預購結束時間" name="po_end" id="po_dateE">
                <em class="fa fa-money"></em>
                <input type="text" placeholder="請輸入原始價格" name="po_price" value="<%= (preproductVO==null) ? "":preproductVO.getPo_price()%>">
                <em class="fa fa-pencil-square"></em>
                <input type="text" placeholder="請輸入商品描述" name="po_detail" value="<%= (preproductVO==null) ? "":preproductVO.getPo_detail()%>">
                <input type="hidden" name="action" value="insert">
                <input class="submit1" type="submit" value="確認新增">
            </table>
        </form>
    </div>
</body>

<% 
  java.sql.Timestamp po_start = null;
  try {
	 	 po_start = preproductVO.getPo_start();
   } catch (Exception e) {
	   po_start = new java.sql.Timestamp(System.currentTimeMillis());
   }
%>
<% 
  java.sql.Timestamp po_end = null;
  try {
	  po_end = preproductVO.getPo_end();
   } catch (Exception e) {
	   po_end = new java.sql.Timestamp(System.currentTimeMillis());
   }
%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>


<script>
        $.datetimepicker.setLocale('zh');
        $('#po_dateS').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=po_start%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        $.datetimepicker.setLocale('zh');
        $('#po_dateE').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=po_end%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
</script>
<script>
var searchPic;
 function setPic()
   {
     // thisObj=document.form1.adr
     thisObj=document.form1.event_p_no
	var i =thisObj[thisObj.selectedIndex].value
	console.log(i);//測試用

     searchPic = new Image;
     searchPic.src="tools/eventP_show.jsp?EVENT_P_NO="+i;
     $("#pic1").attr("src", searchPic.src);
   }
</script>

</html>