<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.preproduct.model.*"%>
<%@ page import="com.event.model.*"%>
<%@ page import="com.event_p.model.*"%>
<%@ page import="com.discount.model.*"%>

<%
DiscountSettingVO discountsettingVO = (DiscountSettingVO) request.getAttribute("preproductVO");
%>
<%
// 	EventService eventSvc=new EventService();
// 	List<EventVO> list_event = eventSvc.findAllEvent();
// 	pageContext.setAttribute("list_event",list_event);

// 	Event_PService eventpSvc = new Event_PService();
//     List<Event_PVO> list01 = eventpSvc.findAllByEventNoRankDescWithoutReport(eventSvc.findLastEndEvent());
//     pageContext.setAttribute("list01",list01);
%>
<%
// Event_PVO eventpVO = (Event_PVO) request.getAttribute("eventpVO");
EventVO eventVO = (EventVO) request.getAttribute("eventVO");
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
            <h2>新增折扣</h2>
        </div>
	
        <div class="uf-photo-area">
           
           
        </div>
        <%-- 錯誤表列 --%>
        <c:if test="${not empty errorMsgs}">
            <script type="text/javascript">
		        alert("請修正以下錯誤:${errorMsgs}");
		    </script>
        </c:if>
      
        
        <form class="uf-form-area" method="post" action="discount.do" name="form1">
            <table>     
                <label>當前活動為：</label>
                <jsp:useBean id="eventSvc" scope="page" class="com.event.model.EventService" />

	                        <h4><%=eventSvc.findByPrimaryKey(eventSvc.findLastEndEvent()).getEvent_name()%></h4>
							<input type="hidden" name="event_no" value="<%=eventSvc.findByPrimaryKey(eventSvc.findLastEndEvent()).getEvent_no()%>">
                
 
                <br>
                <em class="fa fa-money"></em>
                <input type="text" placeholder="請輸入達標數量" name="reach_number" value="<%= (discountsettingVO==null) ? "":discountsettingVO.getReach_number()%>">
                <em class="fa fa-pencil-square"></em>
                <input type="text" placeholder="請輸入折扣百分數" name="reach_discount" value="<%= (discountsettingVO==null) ? "":discountsettingVO.getReach_discount()%>">
                <input type="hidden" name="action" value="insertdiscount">
                <input class="submit1" type="submit" value="確認新增">
            </table>
        </form>
    </div>
</body>



<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>



</html>