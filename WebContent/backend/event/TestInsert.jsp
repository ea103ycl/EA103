<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.event.model.*"%>
<%@ page import="com.event_tag.model.*"%>
<%@ page import="java.util.*"%>
<%
	EventVO eventVO = (EventVO) request.getAttribute("eventVO");
	String[] stat={"活動尚未開始","徵稿中","投票中","活動結束"};

	Event_TagService tagSvc=new Event_TagService();
	List<Event_TagVO> event_tagVOs=tagSvc.findAllTag();
	
	int total=event_tagVOs.size(); //random tag 
	int rand=(int)Math.random()*total;
%>

<%-- <c:if test="${not empty errMsgs }"> --%>
<%-- 	<c:forEach var="err" items="${errMsgs}"> --%>
<!-- 		<ul> -->
<%-- 			<li style="color: red">${err}</li> --%>
<!-- 		</ul> -->

<%-- 	</c:forEach> --%>
<%-- </c:if> --%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/backend/template/css/bootstrap.min.css">
<title>Insert title here</title>
<style>
.xdsoft_datetimepicker {
	margin-left: 20%;
	margin-top: -2%;
}

</style>
</head>
<body>

	<div id="wrapper">

		<%@include file="/backend/bar/backBarSide.jsp"%>

		<div id="content-wrapper" class="d-flex flex-column">

			<div id="content">

				<!-- Topbar -->
				<%@include file="/backend/bar/backBarTop.jsp"%>

				<div class="container-fluid">
					<!--=====自定義內容start ================================================== -->

					<%-- 頁面標題 --%>
					<h1 class="h3 mb-2 text-gray-800">頁面標題</h1>

					<%-- 錯誤表列 --%>

					<%-- 資料內容 --%>
					<a href="<%=request.getContextPath()%>/backend/event/TestListAll.jsp">回主頁</a><br>
					<div class="container">
						<%@ include file="randTag.jsp"%>
					</div>
					<div class="container">
						<form action="<%=request.getContextPath() %>/backend/event/eventServlet" method="post">

							<div class="form-group row">
<!-- 								<label for="inputEmail3" class="col-sm-2 col-form-label">活動名稱</label> -->
								<label for="inputEmail3" class="col-sm-2 col-form-label">活動名稱</label>
								<div class="col-sm-3" id="event_randTag"  >
										<input readonly type="text" class="form-control" name="event_name"value=<%=request.getAttribute("str")==null?"":request.getAttribute("str") %>  >
								</div>
								<c:if test="${not empty errMsgs['event_name']}">
									<div style="color:red;">
									${errMsgs['event_name'] }
									</div>
								</c:if>
							</div>

							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label time">活動開始時間</label>
								<div class="col-sm-3">
									<input type="text" class="form-control event_start"
										name="event_start"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_start().toString()%>>
								</div>
								<c:if test="${not empty errMsgs['event_start']}">
									<div style="color:red;">
									${errMsgs['event_start'] }
									</div>
								</c:if>
							</div>
							
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label time">活動結束時間</label>
								<div class="col-sm-3">
									<input type="text" class="form-control event_end"
										name="event_end"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_end().toString()%>>
								</div>
								<c:if test="${not empty errMsgs['event_end']}">
									<div style="color:red;">
									${errMsgs['event_end'] }
									</div>
								</c:if>
							</div>
							<div><hr></div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label time">徵求作品開始時間</label>
								<div class="col-sm-3">
									<input type="text" class="form-control event_ul_start"
										name="event_ul_start"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_ul_start().toString()%>>
								</div>
								<c:if test="${not empty errMsgs['event_ul_start']}">
									<div style="color:red;">
									${errMsgs['event_ul_start'] }
									</div>
								</c:if>								
							</div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label time">徵求作品結束時間</label>
								<div class="col-sm-3">
									<input type="text" class="form-control event_ul_end"
										name="event_ul_end"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_ul_end().toString()%>>
								</div>
								<c:if test="${not empty errMsgs['event_ul_end']}">
									<div style="color:red;">
									${errMsgs['event_ul_end'] }
									</div>
								</c:if>									
							</div>
							<div><hr></div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label time">投票開始時間</label>
								<div class="col-sm-3">
									<input type="text" class="form-control event_vote_start"
										name="event_vote_start"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_vote_start().toString()%>>
								</div>
								<c:if test="${not empty errMsgs['event_vote_start']}">
									<div style="color:red;">
									${errMsgs['event_vote_start'] }
									</div>
								</c:if>									
							</div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label time">投票結束時間</label>
								<div class="col-sm-3">
									<input type="text" class="form-control event_vote_end"
										name="event_vote_end"
										value=<%=(eventVO == null) ? "" : eventVO.getEvent_vote_end().toString()%>>
								</div>
								<c:if test="${not empty errMsgs['event_vote_end']}">
									<div style="color:red;">
									${errMsgs['event_vote_end'] }
									</div>
								</c:if>									
							</div>
							<div><hr></div>
							<div class="form-group row">
								<label for="inputEmail3" class="col-sm-2 col-form-label">活動狀態</label>
								<div class="col-sm-3">
									<select class="form-control" id="exampleFormControlSelect1"
										name="event_stat">
										<c:if test="<%=eventVO!=null %>">
											<option value="0"
												<%=eventVO.getEvent_stat()==0 ?"selected":""%>>活動尚未開始</option>
											<option value="1"
												<%=eventVO.getEvent_stat()==1 ?"selected":""%>>開始徵稿</option>
											<option value="2"
												<%=eventVO.getEvent_stat()==2 ?"selected":""%>>開始投票</option>
											<option value="3"
												<%=eventVO.getEvent_stat()==3 ?"selected":""%>>活動結束</option>
										</c:if>
										<c:if test="<%=eventVO==null %>">
											<option value="0">活動尚未開始</option>
											<option value="1">開始徵稿</option>
											<option value="2">開始投票</option>
											<option value="3">活動結束</option>
										</c:if>
									</select>
								</div>
							</div>


							<input type="hidden" name="action" value="insert"> 
							<input type="submit" value="送出">
						</form>
					</div>


					<!--===== 自定義內容end ================================================== -->
				</div>
				<!--END OF container-fluid-->

			</div>
			<!--END OF content-->

			<%@include file="/backend/bar/footer.jsp"%>

		</div>
		<!--END OF content-wrapper -->
	</div>
	<!--END OF wrapper -->

</body>

<%
	java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
%>


<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.datetimepicker.css" />
<script
	src="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/backend/event/datetimepicker/jquery.datetimepicker.full.js"></script>

<script>

$.datetimepicker.setLocale('zh');
var dateArrAll=[".event_start",".event_ul_start",".event_ul_end",".event_vote_start",".event_vote_end",".event_end"];
var dateArrMax;
var dateArrMin;
function rangedate(input){
	dateArrMax=[];
	dateArrMin=[];
	for(var i=0;i<dateArrAll.length;i++){
		if(input===dateArrAll[i]){
			for(var j=i+1;j<dateArrAll.length;j++){
				dateArrMax.push(dateArrAll[j]);
			}
			for(var k=0;k<i;k++){
				dateArrMin.push(dateArrAll[k]);				
			}
			console.log("dateArrMin : "+dateArrMin);
			console.log("dateArrMax : "+dateArrMax);
		}
	}	
}

function maxDateF(input,dateArrMax,dateArrMin){	
	for(var i=dateArrMax.length-1;i>=0;i--){
		if($(dateArrMax[i]).val().length!==0){				
	 		input.setOptions({
			maxDate:$(dateArrMax[i]).val()?$(dateArrMax[i]).val():false				
			});
		}
	}
	for(var j=0;j<dateArrMin.length;j++){
		if($(dateArrMin[j]).val().length!==0){
			input.setOptions({
				minDate:$(dateArrMin[j]).val()?$(dateArrMin[j]).val():false
			});
		}
	}

}


var logic=function(){
	rangedate("."+this[0].attributes.class.ownerDocument.activeElement.name);

	maxDateF(this,dateArrMax,dateArrMin);

};
$(function(){
        $(".event_start").datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
// 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=(eventVO == null) ? "" : eventVO.getEvent_start().toString()%>',     
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:			    	// 去除今日(不含)之前
		   onShow:logic
        });
        $(".event_end").datetimepicker({
 	       theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=(eventVO == null) ? "" : eventVO.getEvent_end().toString()%>',     
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
//         startDate:	     	//'2017/07/10',  // 起始日開始活動之後
		   onShow:logic    	
         });
        $(".event_ul_start").datetimepicker({
 	       theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=(eventVO == null) ? "" : eventVO.getEvent_ul_start().toString()%>',     
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
		   onShow:logic
           
         });
        $(".event_ul_end").datetimepicker({
 	       theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=(eventVO == null) ? "" : eventVO.getEvent_ul_end().toString()%>',     
            //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
            //startDate:	            '2017/07/10',  // 起始日
		   onShow:logic
           
         });
        $(".event_vote_start").datetimepicker({
 	       theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=(eventVO == null) ? "" : eventVO.getEvent_vote_start().toString()%>',     
            //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
            //startDate:	            '2017/07/10',  // 起始日
		   onShow:logic
           
         });
        $(".event_vote_end").datetimepicker({
  	       theme: '',              //theme: 'dark',
  	       timepicker:false,       //timepicker:true,
  	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
  	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
//    		   value: 
		   //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
		   //startDate:	            '2017/07/10',  // 起始日
		   onShow:logic
           
		});
});
$("#roll").click(function(){
	$.ajax({
			url:"<%=request.getContextPath()%>/Event_TagServlet",
			type:"post",
			data:{
				action:"roll"
			},
			success:function(e){
				
				var txt="";
				var txtEvent="";
// 					$("#resTxt").text(e);
// 					txt+="<input type=\"text\" class=\"form-control\" name=\"event_tag_name\""+"value="+e+" readonly >"
// 					$("#resTxt").html(txt);
					txtEvent+="<input type=\"text\" class=\"form-control\" name=\"event_name\""+"value="+e+" readonly>"
					$("#event_randTag").html(txtEvent);
			}
		});
	
	});
</script>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
<!--     <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script> -->
<!--     <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script> -->
</html>