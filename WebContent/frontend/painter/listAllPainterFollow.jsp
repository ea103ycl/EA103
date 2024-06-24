<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.painter.model.*"%>
<%@ page import="com.painter_msg.model.*"%>
<%@ page import="com.follow.model.*"%>
<%@ page import="com.painter_act.model.*"%>
<%@ page import="com.mem.model.*"%>

<%-- <%@include file="/frontend/bar/testLogin.jsp"%> --%>


<%
	MemVO loginMemVO = (MemVO)session.getAttribute("memVO");
	if(loginMemVO == null){
		System.out.println("未登入，重新導向登入頁面");
		response.sendRedirect(request.getContextPath() + "/frontend/members/memLogin.jsp");
		return;
	}
	pageContext.setAttribute("loginMemVO", loginMemVO);

	String sessionMemId = loginMemVO.getMem_id(); //登入者的ID
	String sid = sessionMemId;
	
	pageContext.setAttribute("sid", sid);

	PainterService painterSvc = new PainterService();
	List<PainterVO> list = painterSvc.getSomeoneFollowAll(sessionMemId);
	pageContext.setAttribute("list", list);
	
	MemService sMemSvc = new MemService();
	pageContext.setAttribute("sMemSvc", sMemSvc);
	
	PainterActService painterActSvc = new PainterActService();
	pageContext.setAttribute("painterActSvc", painterActSvc);
	
	FollowService followSvc = new FollowService();
	List<FollowVO> followList = followSvc.getFollowList(sid);
	List<FollowVO> fansList = followSvc.getFansList(sid);
	pageContext.setAttribute("followSvc", followSvc);
	pageContext.setAttribute("followList", followList);
	pageContext.setAttribute("fansList", fansList);
	
%>
<jsp:useBean id="painterMsgSvc" scope="page" class="com.painter_msg.model.PainterMsgService" />
<jsp:useBean id="lvSvc" scope="page" class="com.lv.model.LvService" />


<!DOCTYPE html>
<html>
<head>

	
<title>${sMemSvc.findByPrimaryKey(sid).getM_accno()}的關注圈</title>


<style>
.modal-header {
    padding:9px 15px;
    border-bottom:1px solid #ad8b60;
    background-color: #F6F3EE;
    -webkit-border-top-left-radius: 5px;
    -webkit-border-top-right-radius: 5px;
    -moz-border-radius-topleft: 5px;
    -moz-border-radius-topright: 5px;
     border-top-left-radius: 5px;
     border-top-right-radius: 5px;
 }
</style>

<!-- top bar -->
<%@include file="/frontend/bar/frontBarTop.jsp"%>
<%@include file="/frontend/template/YCL/YCL.css"%>

</head>
<body>


<section id="shop" class="space-top-30 ycl-mt">
    <div class="container">
        <div class="row">
			
			<!-- =================================== -->
			<%--<h4>現在登入的會員是：${loginMemVO.mem_id} - ${loginMemVO.m_accno}、查詢的是 ${pageScope.sid} - ${sMemSvc.findByPrimaryKey(sid).getM_accno()}<br></h4> --%>
            
			<!-- account-sidebar -->
			<div class="col-xs-12 col-sm-12 ycl-account-sidebar">
			
				<div class="col-xs-12 col-sm-12">
						
						<!-- 查詢的會員的照片 -->
						<%--<img class="ycl-sidebar-member-pic pull-left" src="<%=request.getContextPath()%>/members/headphotoHandler.do?action=getPic&mem_id=${pageScope.sid}"> --%>
						<img class="ycl-sidebar-member-pic pull-left" src="<%=request.getContextPath()%>/painter/painter.do?action=showCreatorPhoto&sid=${pageScope.sid}">
						<div>
							<c:set var="sLv" value="${sMemSvc.findByPrimaryKey(sid).lv}"/>
							<c:set var="lvVO" value="${lvSvc.findByPrimaryKey(sLv)}"/>
							<span class="text-primary ycl-acct-text">${sMemSvc.findByPrimaryKey(sid).getM_accno()}</span><img id="lvPic" class="ycl-lv_icon" src="<%=request.getContextPath()%>/lv/lv.do?action=showPic&lv=${sLv}"  data-toggle="tooltip" data-placement="right" title="${lvVO.lv_nm}，需${lvVO.f_cnt}位粉絲">
							<br>
							<button class="ycl-act-btn-follow" data-target="#followModal" id="followCntBtn" onclick="$('#followModal').modal('show')">已關注：<span id="cnt-follow">${followSvc.getFollowCnt(pageScope.sid)}</span>人</button> ／
							<button class="ycl-act-btn-fans" data-target="#fansModal" id="fansCntBtn" onclick="$('#fansModal').modal('show')">粉絲數：<span id="cnt-fans">${followSvc.getFansCnt(pageScope.sid)}</span>人</button>
							
							<c:if test="${loginMemVO.mem_id != pageScope.sid}">
								<c:choose>
									<c:when test="${0 == followSvc.getCnt(loginMemVO.mem_id, pageScope.sid)}">
							    		<button class="btn btn-xs btn-success ycl-main-follow-btn" id="MainFollowBtn">關注</button>
									</c:when>
									<c:otherwise>
										<button class="btn btn-xs btn-success-filled ycl-main-follow-btn" id="MainFollowBtn">關注中</button>
									</c:otherwise>
								</c:choose>
							</c:if>
						</div>						
					</div>				
				</div>

			</div>
			<!-- account-sidebar -->
			

			<div class="col-sm-12">
			
				<!-- 選單列 -->
                <ul class="nav nav-tabs" role="tablist">
                    <c:if test="${loginMemVO.mem_id == pageScope.sid}">
                    	<lem class="active"><a href="<%=request.getContextPath()%>/frontend/painter/listAllPainterFollow.jsp?sid=${pageScope.sid}">關注圈</a></li>
                    </c:if>
                    <lem class=""><a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=${pageScope.sid}">個人作品</a></li>
                    <lem class=""><a href="<%=request.getContextPath()%>/frontend/painter/listAllPainterCol.jsp?sid=${pageScope.sid}">個人收藏</a></li>
                </ul>
                
                <div class="tab-content">
                
                	
                    <!-- tab1 個人作品區塊 -->
                    <div role="tabpanel" class="tab-pane animated fadeIn" id="tab1">
                    </div>
                    
					<!-- End tab2 個人收藏區塊 -->
                	<div role="tabpanel" class="tab-pane animated fadeIn" id="tab2">
                    </div>
                	
                	
                	<!-- tab3個人作品區塊 -->
                    <div role="tabpanel" class="tab-pane animated fadeIn active" id="tab3">                   	
                    	<c:if test="${loginMemVO.mem_id ==sid}">                    	
				            <div class="col-xs-12 col-sm-12">
				            	<!-- 上傳按鈕 -->
								<div class="pull-right">
									<button type="button" class="btn btn-primary-filled btn-rounded"
									data-target="#uploadModal" id="uploadBtn" onclick="$('#uploadModal').modal('show')">上傳作品</button>
								</div>
				            </div>
			            </c:if>
			            
			            <!-- 總頁數 -->
			            <c:if test="${empty list}">關注圈暫無作品。</c:if>
						<div class="col-xs-12 col-sm-12" style="display:none;">
							<%@ include file="/frontend/painter/painterPage1.file" %>
			            </div>
						
						<div id="grid" class="col-xs-12 col-sm-12">	
							
							<c:forEach var="painterVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					
								<!-- post-block 作品區塊邊界-->
								<div class="col-xs-12 col-sm-3">
					
									<!-- 作品內容 -->
									<div class="post-block ycl-post-block">
										<!-- 作品圖片 -->				
										<a href="<%=request.getContextPath()%>/frontend/painter/onePainter.jsp?ptr_no=${painterVO.ptr_no}&sid=${sid}&src=3&spg=<%=whichPage%>">
											<img src="<%=request.getContextPath()%>/painter/getShrinkPtr.do?px=500&ptr_no=${painterVO.ptr_no}">
										</a>
										
										

										<div class="ycl-painter-content">
											
											<!-- 互動行為 -->
											<div class="ycl-small-post-footer">
											
												<!-- 留言 -->
												<span class="post-icons ycl-post-icons">
													<button class="ycl-act-btn-msg" value="${painterVO.ptr_no}" onclick="location.href='<%=request.getContextPath()%>/frontend/painter/onePainter.jsp?ptr_no=${painterVO.ptr_no}&sid=${sid}&src=3&spg=<%=whichPage%>'">
														<em class="lnr lnr-bubble">
															<span class="ycl-act-cnt">${painterMsgSvc.getPainterMsgCnt(painterVO.ptr_no)}</span>
														</em>
													</button>											
												</span>

												<!-- like -->
												<span class="post-icons ycl-post-icons">
												   <button class="ycl-act-btn-like " value="${painterVO.ptr_no}">
													   <c:choose>
													   		<c:when test="${empty painterActSvc.getOneByActType(painterVO.ptr_no, 1, loginMemVO.mem_id)}">
													   			<em class="lnr lnr-heart" id="likeIcon${painterVO.ptr_no}">
													   				<span class="ycl-act-cnt" id="likeCnt${painterVO.ptr_no}">${painterVO.like_cnt}</span>
													   			</em>
													   		</c:when>
													   		
													   		<c:otherwise>
													   			<em class="fa fa-heart ycl-act-active" id="likeIcon${painterVO.ptr_no}">
													   				<span class="ycl-act-cnt ycl-act-active" id="likeCnt${painterVO.ptr_no}">${painterVO.like_cnt}</span>
													   			</em>
													   		</c:otherwise>
													   </c:choose>
												   </button>
												</span>
												
												<!-- 加入收藏 -->
												<span class="post-icons ycl-post-icons">
													<button class="ycl-act-btn-col " value="${painterVO.ptr_no}">												
														<c:choose>
													   		<c:when test="${empty painterActSvc.getOneByActType(painterVO.ptr_no, 2, loginMemVO.mem_id)}">
													   			<em class="lnr lnr-inbox" id="colIcon${painterVO.ptr_no}">
													   				<span class="ycl-act-cnt" id="colCnt${painterVO.ptr_no}">${painterVO.col_cnt}</span>
													   			</em>
													   		</c:when>
													   		
													   		<c:otherwise>
													   			<em class="fa fa-inbox ycl-act-active" id="colIcon${painterVO.ptr_no}">
													   				<span class="ycl-act-cnt ycl-act-active" id="colCnt${painterVO.ptr_no}">${painterVO.col_cnt}</span>
													   			</em>
													   		</c:otherwise>												   	 
													   </c:choose>
												   </button>
												</span>
												
											</div> <!-- ycl-small-post-footer 互動行為-->											
											
										</div> <!-- ycl-painter-content -->
										
									</div> <!-- post-block ycl-post-block 作品內容-->
									
									<!-- 作者資訊 -->
									<a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=${painterVO.mem_id}">
										<%--<img class="ycl-creator-pic" src="<%=request.getContextPath()%>/members/headphotoHandler.do?action=getPic&mem_id=${painterVO.mem_id}"></img> --%>
										<img class="ycl-creator-pic" src="<%=request.getContextPath()%>/painter/painter.do?action=showCreatorPhoto&sid=${painterVO.mem_id}"></img>
										<span class="ycl-creator-acct">${sMemSvc.findByPrimaryKey(painterVO.mem_id).m_accno}</span>
									</a>
									
								</div><!-- post-block 作品區塊邊界-->
							</c:forEach>						
						</div>

						<!-- 換頁選單 -->
						<div class="col-xs-12 col-sm-12 text-center space-top-30">
							<%@ include file="/frontend/painter/painterPage2.file" %>
						</div>
									
                    </div><!-- end tab1 -->
                    
                    <!-- tab2 個人收藏區塊 -->
                    <div role="tabpanel" class="tab-pane animated fadeIn" id="tab2">
                    </div>
					<!-- End tab2 個人收藏區塊 -->
					
                </div>
            </div>


			<!-- Modal 新增 -->
			<%@include file="/frontend/painter/uploadModal.file"%>
			<%@include file="/frontend/painter/followModal.file"%>

            <!-- =================================== -->
            
        </div> <!-- row -->
    </div> <!-- container -->
    
</section> 
        
        
<!-- footer -->
<%@include file="/frontend/bar/frontBarFooter.jsp"%>

<script>

	$(function() {
		var len = 50; // 超過50個字以"...more"取代
		$(".ycl-post_content").each(
				function(i) {
					if ($(this).text().length > len) {
						$(this).attr("title", $(this).text());
						var text = $(this).text().substring(0, len - 1)
								+ "...more";
						$(this).text(text);
					}
				});
	});
	

	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#imgUpload').attr('src', e.target.result);
			};					
				reader.readAsDataURL(input.files[0]);
		}
	}


	$(function () {
	    // when the modal is closed
	    $('.modal').on('hidden.bs.modal', function () {
	         $('#uploadModal .modal-body').find('textarea, input').val('');
	         $('#uploadModal .modal-body').find('img').attr('src', '');
	         $('#errMsgList').css('display', 'none');
	         $('#ptrNmMsg').css('display', 'none');
	         $('#imgPathMsg').css('display', 'none');
	         $('#ptrTagDescMsg').css('display', 'none');
	    });
	});
	
	
	$('.ycl-act-btn-like').click(function(){
		
		let xhr = new XMLHttpRequest();
		let ptr_no = $(this).val();
		
		//先準備call servlet的function
		xhr.onload = function (){
			
		      if(xhr.status == 200){		    	  
		        console.log('======= 1 OK =======' + xhr.status);
		        let result = JSON.parse(xhr.responseText);
		        let likeIID = '#likeIcon' + result[0].ptr_no;
		        let likeCntID = '#likeCnt' + result[0].ptr_no;
				$(likeIID).toggleClass('lnr lnr-heart fa fa-heart ycl-act-active');
				$(likeCntID).toggleClass('ycl-act-active');
		  	    $(likeCntID).text(result[0].newCnt);
		      }else{
		    	console.log('======= 1 ERROR =======' + xhr.status);
		      }
		      
		  }
		
    	//建立好Post連接
		xhr.open("post", "<%=request.getContextPath()%>/painter/painter_act.do", true);
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		
		//設定請求
		let data_info = "action=updatePainterActCnt&ptr_no=" + ptr_no + "&act_type=1";
		
		//送出請求
		xhr.send(data_info);
		
	});

	
	$('.ycl-act-btn-col').click(function(){
		
		let xhr = new XMLHttpRequest();
		let ptr_no = $(this).val();
		
		//先準備call servlet的function
		xhr.onload = function (){
		      if(xhr.status == 200){		    	  
		        console.log('======= 1 OK =======' + xhr.status);
		        let result = JSON.parse(xhr.responseText);
		        let likeIID = '#colIcon' + result[0].ptr_no;
		        let likeCntID = '#colCnt' + result[0].ptr_no;
				$(likeIID).toggleClass('lnr lnr-inbox fa fa-inbox ycl-act-active');
				$(likeCntID).toggleClass('ycl-act-active');
		  	    $(likeCntID).text(result[0].newCnt);
		      }else{
		    	console.log('======= 1 ERROR =======' + xhr.status);
		      }
		  }
		
    	//建立好Post連接
		xhr.open("post", "<%=request.getContextPath()%>/painter/painter_act.do", true);
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		
		//設定請求
		let data_info = "action=updatePainterActCnt&ptr_no=" + ptr_no + "&act_type=2";
		
		//送出請求
		xhr.send(data_info);
		
	});
	
	
	function autogrow(textarea){
		var adjustedHeight=textarea.clientHeight;
		    adjustedHeight=Math.max(textarea.scrollHeight,adjustedHeight);
		    if (adjustedHeight>textarea.clientHeight){
		        textarea.style.height=adjustedHeight+'px';
		    }
		}

		
	window.onload=function (){
		var isNoErr = ${ empty errorMsgs };
		console.log('新增作品時是否沒問題?', isNoErr);
		if(!isNoErr){
			$('#uploadModal').modal('show');
		}
	}
		
    $("#submitBtn").click(function(){
    	let check = true;
    	let ptr_nm = ($("#ptr_nm").val()).trim();
    	let tag_desc = ($("#tag_desc").val()).trim();
    	
    	//先將已修改的內容去除提示文字
        if( ptr_nm != null && ptr_nm.length > 1 ){
        	$('#ptrNmMsg').css('display', 'none');
        }

        if($("#imgPath").val().trim() != ""){
        	$('#imgPathMsg').css('display', 'none');
        }
        
        //再做錯誤處理
    	if(ptr_nm == null || ptr_nm.length == 0 ){
    		$('#ptrNmMsg').css('display', 'inline-block');
    		$('#ptr_nm').focus(); //跳回輸入框內
        	check = false;
        }

        if($("#imgPath").val().trim() == ""){
        	$('#imgPathMsg').css('display', 'inline-block');
        	check = false;
        }
        
        if( !check ){
        	event.preventDefault();
        }
        	
    })
    
	<%@include file="/frontend/painter/followScript.file"%>
	
		
</script>


</body>
</html>