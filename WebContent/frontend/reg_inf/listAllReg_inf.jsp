0<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import=" java.text.*,  java.util.*"  %>
<%@ page import="com.reg_inf.model.*"%>
<%@ page import="com.meeting.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.wel_record.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
    MemVO memVO = (MemVO) session.getAttribute("memVO");
    
    Reg_infService reg_infSvc = new Reg_infService();
    List<Reg_infVO> list = reg_infSvc.getMem_Reg_inf(memVO.getMem_id());
    pageContext.setAttribute("list",list);
    
    MeetingService meetingSvc = new MeetingService();
    MeetingVO meetingVO = meetingSvc.getOneMeeting("mt_no");
    pageContext.setAttribute("meetingSvc",meetingSvc);
 
%>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 Date current = new Date();
%>


<html>
<head>
<title>「我報名的」見面會-listAllReg_inf.jsp</title>

<style>
  table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
    text-align: center;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
  h4 {
  padding: 5px;
    text-align: center;
  }
  body {
    color: #666666;
    background: #fefefe;
    font-family: "Rubik", sans-serif;
    font-weight: 400;
    font-size: 14px;
    line-height: 20px;
    letter-spacing: 0.05em;
}
table#table-1 {
	background-color: #C4E1E1;
    border: 2px #ECF5FF;
    text-align: center;
    
  }
  table#table-1 h4 {
    color: #613030;
    display: block;
    margin-bottom: 10px;
  }
  
  h4 {
    color: blue;
    display: inline;
  }
  h3 {
  color: blue;
    display: inline;
</style>

</head>
<body bgcolor='white'>
<!-- header -->

	<header>

		<%@include file="/frontend/bar/frontBarTop.jsp"%>

		    <!-- header-banner -->
    <div id="header-banner">
        <div class="banner-content single-page text-center">
            <div class="banner-border">
                <div class="banner-info" id="banner-info">
                    <h1>Meeting</h1>
                </div><!-- / banner-info -->
            </div><!-- / banner-border -->
        </div><!-- / banner-content -->
    </div>
    <!-- / header-banner -->

<table id="table-1">
	<tr><td>
		 <h3>「我報名的」見面會-listAllReg_inf.jsp</h3>
		 <h4><a href="/EA103G1/frontend/meeting/listAllMeeting_front.jsp">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>狀態</th>
		<th>報名表ID</th>
		<th>見面會名稱</th>
		<th>報名期間</th>
		<th>日期</th>
		<th>報名人數</th>
		<th>報名備註</th>
		<th>修改</th>	
		<th>取消</th>	
	</tr>
		
	<%@ include file="page1.file" %> 
	
	<c:forEach var="reg_infVO" items="${list}"   begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

		<tr>
		    <td>	    
<c:set var="ri_status" scope="session" value="${reg_infVO.ri_status}"/>

<c:choose>
    <c:when test="${ri_status == 1}">
            報名成功
    </c:when>
    <c:when test="${ri_status == 2}">
          已退費
    </c:when>
 
    <c:otherwise>
           報名成功
    </c:otherwise>
</c:choose>   
</td>		    
			<td>${reg_infVO.ri_id}</td>
			<td>${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_id}</td>
			<td><fmt:formatDate value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_start_time}" pattern="yyyy-MM-dd" />~<fmt:formatDate value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_end_time}" pattern="yyyy-MM-dd" /></td>
			<td><fmt:formatDate value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_time}" pattern="yyyy-MM-dd" /></td>
			<td>${reg_infVO.ri_qty}</td>
			<td>${reg_infVO.ri_note}</td>
	
	
	<td>	    
<%-- 以下測試時間 --%>
<fmt:formatDate value="<%=current %>" pattern="yyyy-MM-dd" var="now_time" />
<fmt:formatDate value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_start_time}" pattern="yyyy-MM-dd" var="mt_start_time" />
<fmt:formatDate value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_time}" pattern="yyyy-MM-dd" var="mt_time" />
<fmt:formatDate value="${meetingSvc.getOneMeeting(reg_infVO.mt_no).mt_end_time}" pattern="yyyy-MM-dd" var="mt_end_time" />
<c:set var="ri_status" scope="session" value="${reg_infVO.ri_status}"/>


<c:choose> 
  <c:when test="${ri_status == 2}">
              已退款
    </c:when>
    <c:when test="${now_time gt mt_time}">
             活動已結束
    </c:when>
     <c:when test="${now_time gt mt_end_time}">
    <font color=red>報名截止</font>
    </c:when>
    <c:when test="${mt_start_time gt now_time}">
           <font color=blue>   即將開放</font>
    </c:when>
    <c:otherwise>
        <FORM METHOD="get" ACTION="<%=request.getContextPath()%>/frontend/reg_inf/reg_inf.do" style="margin-bottom: 0px;">
	 	 <input type="hidden" name="ri_id"  value="${reg_infVO.ri_id}">
	 <input type="submit" value="修改">
	<input type="hidden" name="action"	value="getOne_For_Update">
	
	</FORM>
    </c:otherwise>
</c:choose>   
</td>	 		      

<td>	    

<c:choose> 
  <c:when test="${ri_status == 2}">
            已退款
    </c:when>
    <c:when test="${now_time gt mt_time}">
             活動已結束
    </c:when>
     <c:when test="${now_time gt mt_end_time}">
    <font color=red>報名截止</font>
    </c:when>
    <c:when test="${mt_start_time gt now_time}">
     <font color=blue>即將開放</font>
    </c:when>
    <c:otherwise>
        <FORM METHOD="get" ACTION="<%=request.getContextPath()%>/frontend/reg_inf/reg_inf.do" style="margin-bottom: 0px;">
	 	 <input type="hidden" name="ri_id"  value="${reg_infVO.ri_id}">
	             <input type="submit" value="取消"> 
	             <input type="hidden" name="action"	value="getOne_For_Display">
	</FORM>
    </c:otherwise>
</c:choose>   
</td>	 		   
		</tr>
	</c:forEach>

</table>
<%@ include file="page2.file" %>
<!-- footer -->
	<%@include file="/frontend/bar/frontBarFooter.jsp"%>
	<!-- / footer -->
	<table id="table-1">
<td></td>
</table>
</body>
</html>