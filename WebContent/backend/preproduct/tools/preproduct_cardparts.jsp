<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.event_p.model.*"%>
<%@ page import="com.event.model.*"%>
<%
	EventService eventSvc=new EventService();
	Event_PService eventpSvc = new Event_PService();
    List<Event_PVO> list01 = eventpSvc.findAllByEventNoRankDescWithoutReport(eventSvc.findLastEndEvent());
    pageContext.setAttribute("list01",list01);
%>
<%
	Event_PVO eventpVO = (Event_PVO) request.getAttribute("eventpVO");
%>



<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <meta charset="utf-8">
    <title>responsive_box</title>
    <style type="text/css">
/*         #rbbody { */
/*             margin: 0; */
/*             padding: 0; */
/*             display: flex; */
/*             justify-content: center; */
/*             align-items: center; */
/*             background: #16384c; */
/*         } */
         .rb_container { 
             position: relative; 
             width: 100%; 
             display: flex; 
             justify-content: center; 
             align-items: center; 
             flex-wrap: wrap; 
             padding: 15px;  
         }
        .rb_container .rb_card {
            position: relative;
            max-width: 400px;
            height: 200px;
/*             background: #fff; */
            margin: 10px;
            margin-top: 40px;
/*             padding: 20px 15px; */
            display: flex;
            flex-direction: column;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.5);
            transition: 0.3s ease-in-out;
        }
        .rb_container .rb_card:hover {
            height: 300px;
        }
        .rb_container .rb_card .rb_imgBx {
            position: relative; 
            width: 260px;
            height: 280px; 
            top: -60px;
/*             left: 20px; */
            z-index: 1;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.2);
        }
        .rb_container .rb_card .rb_imgBx img {
/*         	margin-left: -40px; */
            max-width: 100%;
            height:200px !important;
            border-radius: 6px;
        }
        .rb_container .rb_card .rb_content {
            position: relative;
            margin-top: -140px;
            padding: 10px 15px;
            text-align: center;
            color: #111;
            visibility: hidden;
            opacity: 0;
            transition: 0.3s ease-in-out;
        }
        .rb_container .rb_card:hover .rb_content {
            visibility: visible;
            opacity: 1;
            margin-top: -40px;
            transition-delay: 0.3s;
        }
    </style>
</head>

<body id="rbbody">

<p>活動編號：${list01[0].event_no}</p>

<br>
<div class="row">  
	<%@include file="/backend/preproduct/pages/page3.file" %>
    <c:forEach var="eventpVO" items="${list01}" begin="<%=pageIndexx%>" end="<%=pageIndexx+rowsPerPagee-1%>">
        <div class="rb_container col-3">
            <div class="rb_card">
                <div class="rb_imgBx">
                    <img src="<%=request.getContextPath()%>/backend/preproduct/tools/eventP_show.jsp?EVENT_P_NO=${eventpVO.event_p_no}">
                </div>
                <div class="rb_content">
                    <h3>${eventpVO.event_p_name}</h3>
                    <p>第${eventpVO.vote_rank}名</p>
                    <p>得票數：${eventpVO.event_vote_num}票</p>
                
                </div>
            </div>
        </div>
    
    </c:forEach>
</div>
<script src="<%=request.getContextPath()%>/backend/template/vendor/jquery/jquery.min.js"></script>
</body>

</html>