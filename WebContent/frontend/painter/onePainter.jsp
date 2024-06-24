<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.painter.model.*"%>
<%@ page import="com.painter_tag_map.model.*"%>
<%@ page import="com.painter_tag.model.*"%>
<%@ page import="com.painter_msg.model.*"%>
<%@ page import="com.follow.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.painter_act.model.*"%>

<%-- <%@include file="/frontend/bar/testLogin.jsp"%> --%>

<%
	MemVO loginMemVO = (MemVO)session.getAttribute("memVO");
	if(loginMemVO == null){
		System.out.println("未登入，重新導向登入頁面");
		response.sendRedirect(request.getContextPath() + "/frontend/members/memLogin.jsp");
		return;
	}

	pageContext.setAttribute("loginMemVO", loginMemVO);
	
	MemService sMembersSvc = new MemService();
	pageContext.setAttribute("sMembersSvc", sMembersSvc);

	PainterService painterSvc = new PainterService();
	Integer ptr_no = Integer.valueOf(request.getParameter("ptr_no"));
	
	PainterVO painterVO = painterSvc.getOnePainter(ptr_no);
	pageContext.setAttribute("painterVO", painterVO);
	
	PainterMsgService painterMsgSvc = new PainterMsgService();
	List<PainterMsgVO> msgList = painterMsgSvc.getAll(ptr_no);
	pageContext.setAttribute("msgList", msgList);
	
	String src = (String)request.getParameter("src");
	pageContext.setAttribute("src", src);
	System.out.println("src=" + src);
	
	String spg = (String)request.getParameter("spg");
	pageContext.setAttribute("spg", spg);
	
	String sid = (String)request.getParameter("sid");
	pageContext.setAttribute("sid", sid);
	System.out.println("sid=" + sid);
	
	PainterTagMapService painterTagMapSvc = new PainterTagMapService();
	List<PainterTagMapVO> tagList = painterTagMapSvc.getAllByPtrNo(ptr_no);
	pageContext.setAttribute("tagList", tagList);
	
	PainterTagService painterTagSvc = new PainterTagService();
	pageContext.setAttribute("painterTagSvc", painterTagSvc);
	
	String tagString = "";
	for( PainterTagMapVO painterTagMapVo : tagList){
		tagString += "#" + painterTagSvc.getOneByTagNo( painterTagMapVo.getTag_no() ).getTag_desc();
	}
	pageContext.setAttribute("tagString", tagString);

	PainterActService painterActSvc = new PainterActService();
	pageContext.setAttribute("painterActSvc", painterActSvc);
%>

<!DOCTYPE html>
<html>
<head>
	
	<title>${painterVO.ptr_nm}</title>
	
	<!-- top bar -->
	<%@include file="/frontend/bar/frontBarTop.jsp"%>
	<%@include file="/frontend/template/YCL/YCL.css"%>
		
</head>
<body>

	<div id="page-content" class="container" >
	
	    <!-- post full-width -->
	    <section id="blog" class="space-top-30">
	        <div class="row">
	            <!-- post content area -->
	            <div class="col-xs-12 col-sm-6">
	            	<!-- 作品圖片 -->
					<img src="<%=request.getContextPath()%>/painter/getOrigPtr.do?ptr_no=${painterVO.ptr_no}">
	            </div>
	            <div class="col-xs-12  col-sm-6">
	                <div class="blog block post-content-area">
	                
                    <c:if test="${loginMemVO.mem_id == painterVO.mem_id}">
						<div class="btn-group ycl-edit-btn pull-right" role="group">
						    <button type="button" class="ycl-transparent-Btn ycl-square-btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						      <em class="fa fa-ellipsis-h"></em>
						    </button>
						    <ul class="dropdown-menu">
							    <li><a><button class="ycl-transparent-Btn mt-100" data-target="#updateModal" onclick="$('#updateModal').modal('show')"><em class="lnr lnr-pencil"></em>&nbsp;修改</button></a></li>
							    <li><a><button class="ycl-transparent-Btn" id="deletePtrBtn"><em class="lnr lnr-trash"></em>&nbsp;刪除</button></a></li>
						    </ul>
						  </div>
					</c:if>
							
	                    <div class="post-info-box">
	                    	
	            			<!-- 作品名稱 -->
	            			<div class="ycl-painter-nm">
	            				<h4>${painterVO.ptr_nm}</h4>
	            			</div>
	            			
	            			<!-- 作品說明區塊 -->           		
	            			<p class="post-meta">
	            			
	            				<!-- 創作者頭像&會員帳號 -->
	            				<a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=${painterVO.mem_id}">
									<%--<img class="ycl-creator-pic" src="<%=request.getContextPath()%>/members/headphotoHandler.do?action=getPic&mem_id=${painterVO.mem_id}"> --%>
	            					<img class="ycl-creator-pic" src="<%=request.getContextPath()%>/painter/painter.do?action=showCreatorPhoto&sid=${painterVO.mem_id}">
	            					${sMembersSvc.findByPrimaryKey(painterVO.mem_id).getM_accno()}
	            				</a>
	            				
	            				<!-- 發布日期 -->
	            				<fmt:formatDate value="${painterVO.create_dt}" pattern="yyyy/MM/dd HH:mm"/>
	            				
	            				<!-- 隱私權圖示 -->
	            				<c:choose>
	            				    <c:when test="${painterVO.priv_stat==1}"><span class="ycl-priv-stat-icon" title="公開">‧ <em class="fa fa-globe"></em></span></c:when>
	            				    <c:when test="${painterVO.priv_stat==2}"><span class="ycl-priv-stat-icon" title="個人">‧ <em class="lnr lnr-lock"></em></c:when>
	            				    <c:when test="${painterVO.priv_stat==3}"><span class="ycl-priv-stat-icon" title="粉絲">‧ <em class="lnr lnr-users"></em></c:when>
	            				</c:choose>
	            				
	            			</p>
						    	
						    	<!-- 作品說明 -->
						    	<div class="ycl-painter-desc">
<%-- 							    	<c:forTokens var="str" items="${painterVO.intro}" delims="&#13;&#10;"> --%>
<%-- 							    		<p>${str}</p> --%>
<%-- 							    	</c:forTokens> --%>
							    	
							    	<pre>${painterVO.intro}</pre>
						    	</div>
						    	
						    	<!-- 作品tag -->
						   		<c:forEach var="painterTagMapVO" items="${tagList}">
						    		<a href="#" class="ycl-hashtag"># ${painterTagSvc.getOneByTagNo(painterTagMapVO.tag_no).tag_desc}&nbsp</a>
						    	</c:forEach>
						    	
						    	<hr>
						    	<!-- 互動行為 -->
						    	<div>
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
									
									<!-- 購物 -->
									<span class="post-icons ycl-post-icons  pull-right">
										<button class="ycl-act-btn-msg">
										 	<a href="<%=request.getContextPath()%>/frontend/shop/shopping?action=Ptr_Search&ptr_no=${painterVO.ptr_no}"><em class="lnr lnr-cart"></em></a>
										</button>											
									</span>
						    	</div>
						    	
	                </div><!-- / blog-block -->
	
	                <div class="comments">
	                    <h4>留言</h4>
	                    <ul class="media-list">
	                    	                    
	                    	<c:if test="${fn:length(msgList) == 0}">
	                    		無任何留言。
	                    	</c:if>
	                    	
	                    	<c:set var="count" value="0" scope="page" />
	                    	<c:forEach var="painterMsgVO" items="${msgList}">
	                    	
	                    		<!-- 每則留言內容 -->                  
		                    	<lem class="media">
		                    		                        
		                        	<!-- 留言者頭像 -->
		                            <div class="media-left">
		                            
		                                <a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=${painterMsgVO.mem_id}">
											<%--<img class="media-object" alt="" src="<%=request.getContextPath()%>/members/headphotoHandler.do?action=getPic&mem_id=${painterMsgVO.mem_id}"> --%>
		                                    <img class="media-object" alt="" src="<%=request.getContextPath()%>/painter/painter.do?action=showCreatorPhoto&sid=${painterMsgVO.mem_id}">
		                                </a>
		                            </div>
		                            
		                            <!-- 留言內容 -->
		                            <div class="media-body">
		                            
		                                <div class="comment-info">		                                    
		                                    <div class="comment-author">
		                                    	<a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=${painterMsgVO.mem_id}"> ${sMembersSvc.findByPrimaryKey(painterMsgVO.mem_id).getM_accno()}</a>
		                                    <span class="comment-date ycl-comment-date pull-right">
		                                    	<c:set var="count" value="${count + 1}" scope="page"/> ${count}樓
		                                    	<fmt:formatDate value="${painterMsgVO.msg_dt}" pattern="yyyy/MM/dd HH:mm"/>		                                    	
		                                    	<c:choose>
		                                    		
		                                    		<c:when test="${loginMemVO.mem_id == painterMsgVO.mem_id}">		                                    		
														<button class="ycl-msg-btn-del deleteByMsgOwner" value="${painterMsgVO.msg_no}"><em class="fa fa-window-close" aria-hidden="true"></em></button>
		                                    		</c:when>
		                                    		
		                                    		<c:when test="${loginMemVO.mem_id == painterVO.mem_id}">
															<button class="ycl-msg-btn-del deleteByPainterOwner" value="${painterMsgVO.msg_no}"><em class="fa fa-window-close" aria-hidden="true"></em></button>
		                                    		</c:when>
		                                    			                                    	
		                                    	</c:choose>
		                                    </span>		                                    
		                                    </div>
		                                    
		                                </div><!-- / comment-info -->
		                                
		                                <div class="comment ycl-msg-comment">
		                                    <p>${painterMsgVO.msg}</p>
		                                </div><!-- / comment -->
		                            </div><!-- / parent media-body -->
		                        </li><!-- / media -->	                    
	                    
	                    	</c:forEach>
	                    </ul><!-- / media-list -->
	                    
	                    
  						<!-- comment form -->
	                    <div id="comment-form">
	                    
		                    <c:if test="${not empty errorMsgs}">
								<font style="color: red">請修正以下錯誤:</font>
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color: red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>
	                    
	                        <form id="commentForm" data-toggle="validator" method="post" action="<%=request.getContextPath()%>/painter/painter_msg.do" enctype="multipart/form-data">
	                            <div class="row">                         
	                                <div class="col-xs-12">
	                                    <div class="form-group">
	                                        <span class="btn btn-xs btn-danger-filled btn-rounded" id="ptrMsgMsg" style="display:none;"><em class="fa fa-times"></em><span>請輸入留言</span></span>
	                                        <textarea id="msg" name="msg" class="form-control" rows="5" placeholder="MESSAGE" required maxlength="333"></textarea>
	                                    	<input style="display:none" name="ptr_no" value="${painterVO.ptr_no}"></input>
	                                    </div>
	                                </div>
	                            </div><!-- / row -->
	                            
	                            <div class="row">
	                            	<c:choose>
	                            		<c:when test="${src==2}"><div class="col-xs-5"><a href="<%=request.getContextPath()%>/frontend/painter/listAllPainterCol.jsp?sid=${sid}&whichPageCol=${spg}" class="btn btn-md btn-primary btn-form-submit"><strong>回收藏列表</strong></a></div></c:when>
	                            		<c:when test="${src==3}"><div class="col-xs-5"><a href="<%=request.getContextPath()%>/frontend/painter/listAllPainterFollow.jsp?sid=${sid}&whichPage=${spg}" class="btn btn-md btn-primary btn-form-submit"><strong>回關注圈</strong></a></div></c:when>
	                            		<c:otherwise><div class="col-xs-5"><a href="<%=request.getContextPath()%>/frontend/painter/listAllPainter.jsp?sid=${painterVO.mem_id}&whichPage=${spg}" class="btn btn-md btn-primary btn-form-submit"><strong>回作品列表</strong></a></div></c:otherwise>
	                            	</c:choose>
	                            	<input style="display:none" name="src" value="${src}">
	                            	<input style="display:none" name="sid" value="${sid}">
	                            	<div class="col-xs-7"><button type="submit" name="action" value="insert" id="form-submit" class="btn btn-md btn-primary-filled btn-form-submit"><strong>送出</strong></button></div>
	                            </div>
	                            
	                        </form>
	                        
	                    </div>
	                    <!-- / comment form -->
	                </div><!-- / comments -->

	                
	            </div><!-- / col-sm-8 col-md-9 -->
	            <!-- / post content area -->
	
	            
	        </div><!-- / row -->
	    </section>
	    <!-- / post full-width -->
	    
	    	<!-- Modal 修改 -->
			<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="updateModalLabel">修改作品</h4>
			      </div>
			      <div class="modal-body">

                    <c:if test="${not empty errorMsgsForUpdate}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgsForUpdate}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
			      
			        <form method="post" action="<%=request.getContextPath()%>/painter/painter.do" enctype="multipart/form-data">

			        	<div class="form-group" style="display:none;">
	                        <label>作品編號</label>
	                        <input type="text" class="form-control" id="ptr_no" name="ptr_no" value="${painterVO.ptr_no}">
	                    </div>

	                    <div class="form-group">
	                        <label>作品能見度</label>
	                        <select class="form-control" id="priv_stat" name="priv_stat">
	                        	<option value="1" <c:if test="${1==painterVO.priv_stat}">selected</c:if>>公開</option>
	                        	<option value="3" <c:if test="${3==painterVO.priv_stat}">selected</c:if>>僅限粉絲</option>
	                        	<option value="2" <c:if test="${2==painterVO.priv_stat}">selected</c:if>>僅限個人</option>
	                        </select>    
	                    </div>
	                    
			        	<div class="form-group">
	                        <label>作品名稱</label><span class="btn btn-xs btn-danger-filled btn-rounded" id="ptrNmMsg" style="display:none;"><em class="fa fa-times"></em><span>請輸入作品名稱</span></span></label>
	                        <input type="text" class="form-control" id="ptr_nm" name="ptr_nm" placeholder="請輸入作品名稱" required="required" data-error="*請輸入作品名稱" 
	                        	   value="${painterVO.ptr_nm}" maxlength="33">
	                    </div>

	                    
	                    <div class="form-group">
	                        <label>作品說明</label>
	                        <textarea class="form-control" id="intro" name="intro" placeholder="請輸入作品說明" rows="5" onkeyup="autogrow(this);" >${painterVO.intro}</textarea>
	                    </div>
						
			        	<div class="form-group">
	                        <label>HashTag</label><span class="btn btn-xs btn-danger-filled btn-rounded errMsg" id="ptrTagDescMsg" style="display:none;"><em class="fa fa-times"></em><span>tag過長，每個tag最多只能100個英文字或33個中文字</span></span>
	                        <input class="form-control" id="tag_desc" name="tag_desc" placeholder="請輸入作品tag(需以#分開)" value="${tagString}">  
	                    </div>
	                    
	                    <div class="form-group">
	                        <label>選擇作品圖片</label>
	                        <input type="file" id="imgPath" name="imgPath" onchange="readURL(this);">
	                        <img class="img-fluid w-25 h-25" id="imgUpload"
	                        	 src="<%=request.getContextPath()%>/painter/getOrigPtr.do?ptr_no=${painterVO.ptr_no}">	
                    	</div>
                    	
                    	<input style="display:none" name="src" value="${src}">
                    	<input style="display:none" name="sid" value="${sid}">
                    	
	                     <div class="modal-footer">
	                        <span class="btn btn-xs btn-danger btn-rounded" id="submitErrMsg" style="display:none;"><em class="fa fa-times"></em><span>請修正錯誤</span></span>
				        	<button type="submit" class="btn btn-primary btn-rounded" data-dismiss="modal">取消</button>
				        	<button type="submit" class="btn btn-primary-filled btn-rounded" id="updatePtrBtn" name="action" value="update">修改</button>
				      	 </div>

			        </form>			      
			      </div>

			    </div>
			  </div>
			</div>
			<!-- Modal 修改 END -->
	    
	
	</div> <!-- end container -->

	<!-- footer -->
	<%@include file="/frontend/bar/frontBarFooter.jsp"%>
	
<!-- 	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
	<script src="<%=request.getContextPath()%>/frontend/template/YCL/sweetalert.min.js"></script>
	
	<script>
	
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					$('#imgUpload').attr('src', e.target.result);
				};					
					reader.readAsDataURL(input.files[0]);
			}
		}
	
	
		$("#deletePtrBtn").click(function() {
			swal({
				  title: "刪除作品",
				  text: "確定要從ArtsBlock永久移除這個作品嗎？",
				  icon: "warning",
				  buttons: ["取消", "確定"],
				  dangerMode: true,
				})
				.then((willDelete) => {
				  if (willDelete) {
					  location.href='<%=request.getContextPath()%>/painter/painter.do?action=delete&ptr_no=${painterVO.ptr_no}';
				  } else {
				   null;
				  }
				});
		});
		
		$('.deleteByPainterOwner').click(function() {
			let id= $(this).val();
			swal({
				  title: "刪除留言",
				  text: "確定要刪除留言嗎？",
				  icon: "warning",
				  buttons: ["取消", "確定"],
				  dangerMode: true,
				})
				.then((willDelete) => {
				  if (willDelete) {
					  location.href='<%=request.getContextPath()%>/painter/painter_msg.do?action=deleteByMsgOwner&msg_no=' + id + '&ptr_no=${painterVO.ptr_no}&src=${src}&sid=${sid}';
				  } else {
				   null;
				  }
				});
		});
		
		$('.deleteByMsgOwner').click(function() {
			let id= $(this).val();
			swal({
				  title: "刪除留言",
				  text: "確定要刪除的留言嗎？",
				  icon: "warning",
		          buttons: ["取消", "確定"],
				  dangerMode: true,
				})
				.then((willDelete) => {
				  if (willDelete) {
					  location.href='<%=request.getContextPath()%>/painter/painter_msg.do?action=deleteByPainterOwner&msg_no=' + id + '&ptr_no=${painterVO.ptr_no}&src=${src}&sid=${sid}';
				  } else {
				   null;
				  }
				});
		});
	
		function autogrow(textarea){
			let adjustedHeight=textarea.clientHeight;
			    adjustedHeight=Math.max(textarea.scrollHeight,adjustedHeight);
			    if (adjustedHeight>textarea.clientHeight){
			        textarea.style.height=adjustedHeight+'px';
			    }
		}

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
			        let PainterBlockID = '#painterBlock' + result[0].ptr_no;
					$(likeIID).toggleClass('lnr lnr-inbox fa fa-inbox ycl-act-active');
					$(likeCntID).toggleClass('ycl-act-active');
			  	    $(likeCntID).text(result[0].newCnt);
			  	    
			  	    if( ${loginMemVO.mem_id == pageScope.sid}){
			  	    	$(PainterBlockID).remove();
// 			  	    	location.reload(true); //重新載入頁面(單作品頁不作此事)
			  	    }
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
		
		$('#form-submit').click(function(){
			let msg = ($("#msg").val()).trim();
			if( msg==null || msg.length==0 ){
				$('#ptrMsgMsg').css('display', '');
				$('#msg').focus();
				event.preventDefault();
			}
		});
		
		window.onload=function (){
			var isNoErr = ${ empty errorMsgsForUpdate };
			console.log('修改作品時是否沒問題?', isNoErr);
			if(!isNoErr){
				$('#updateModal').modal('show');
			}
		}
		
	    $("#updatePtrBtn").click(function(){
	    	let check = true;
	    	let ptr_nm = ($("#ptr_nm").val()).trim();
	    	
	    	//先將已修改的內容去除提示文字
	        if( ptr_nm != null && ptr_nm.length > 1 ){
	        	$('#ptrNmMsg').css('display', 'none');
	        }
	        
	        //再做錯誤處理
	    	if(ptr_nm == null || ptr_nm.length == 0 ){
	    		$('#ptrNmMsg').css('display', 'inline-block');
	    		$('#ptr_nm').focus(); //跳回輸入框內
	        	check = false;
	        }

	        if( !check ){
	        	event.preventDefault();
	        }
	        	
	    })
		
	</script>
	
</body>
</html>