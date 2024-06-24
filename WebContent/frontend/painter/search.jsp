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

<%@include file="/frontend/bar/testLogin.jsp"%>

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
	            <div class="col-xs-12 col-sm-6">
	            
				<div class="ui-widget">
				  <label for="tags">Tags: </label>
				  <input id="tags">
				</div>
	            
	            
	            	<label>搜尋其他會員：</label>
	            	<input id="memSearch" name="memSearch" placeholder="請輸入會員帳號">
	            	<div id="memmemSearchResult" style="display: none;">
	            


	            </div>
			</div>
	
	</div> <!-- end container -->

	<!-- footer -->
	<%@include file="/frontend/bar/frontBarFooter.jsp"%>
	  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<script>
	
	  //設定查詢結果的位置
	  function ChangeCoords() {
	   var left = $("#memSearch").position().left; //獲得最左邊的距離
	   var top = $("#memSearch").position().top + 20; ; //獲得頂端的距離(20是輸入框的高度)
	   $("#memmemSearchResult").css("left", left + "px"); //依照輸入框調整搜尋結果的位置
	   $("#memmemSearchResult").css("top", top + "px"); //依照輸入框調整搜尋結果的位置
	  }
	
	  $(function () {
		  
		   $("#memSearch").keyup(function (evt) {
			
			let keyword = $("#memSearch").text;
			console.log('搜尋會員關鍵字:', keyword, ",", $("#memSearch").val());
			
			//設定查詢結果要顯示的頁面位置
		    ChangeCoords();
			
			//解析按下的按鈕
		    var k = window.event ? evt.keyCode : evt.which;
			
		    //不為空、往上、back時
		    if ($("#memSearch").val() != "" && k != 38 && k != 40 && k != 13) {
		     $.ajax({
		      type: 'Post',
		      dataType: "json",
		      url: "<%=request.getContextPath()%>/painter/painter.do?action=searchMem&keyword=e", //要call的方法
// 		      data: "{'keyword'" + $("#memSearch").val() + "'}",    //參數
		      contentType: "application/json; charset=utf-8",
		      error: function (msg) {
		       alert("資料載入失敗");
		      },
		      success: function (data) {
		       let result = JSON.parse(data);
		       console.log(result);
		       if (result.length > 0) {
		        var layer = "";
		        layer = "<table id='aa'>";
		        $.each(objData, function (idx, item) {
		         layer += "<tr class='line'><td class='std'>" + item.m_accno + "</td></tr>";
		        });
		        layer += "</table>";
		        
		        //将結果加入div中
		        $("#memSearchResult").empty();
		        $("#memSearchResult").append(layer);
		        $(".line:first").addClass("hover");
		        $("#memSearchResult").css("display", "");
		        
		        //滑鼠移動事件
		        $(".line").hover(function () {
		         $(".line").removeClass("hover");
		         $(this).addClass("hover");
		        }, function () {
		         $(this).removeClass("hover");
		        });
		        
		        //滑鼠點擊時代入內容
		        $(".line").click(function () {
		         $("#memSearch").val($(this).text());
		         $("#memSearchResult").css("display", "none");
		        });
		       } else {
		        $("#memSearchResult").empty();
		        $("#memSearchResult").css("display", "none");
		       }
		      }
		     });
		    }
		    else if (k == 38) {//向上的箭頭
		     $('#aa tr.hover').prev().addClass("hover");
		     $('#aa tr.hover').next().removeClass("hover");
		     $('#memSearch').val($('#aa tr.hover').text());
		    } else if (k == 40) {//向下的箭頭
		     $('#aa tr.hover').next().addClass("hover");
		     $('#aa tr.hover').prev().removeClass("hover");
		     $('#memSearch').val($('#aa tr.hover').text());
		    }
		    else if (k == 13) { //back箭頭
		     $('#memSearch').val($('#aa tr.hover').text());
		     $("#memSearchResult").empty();
		     $("#memSearchResult").css("display", "none");
		    }
		    else {
		     $("#memSearchResult").empty();
		     $("#memSearchResult").css("display", "none");
		    }
		   });
		   $("#memSearchResult").bind("mouseleave", function () {
		    $("#memSearchResult").empty();
		    $("#memSearchResult").css("display", "none");
		   });
		  });
	
	  
	  
	  $( function() {
		  
		  $.ajax({
		      type: 'Post',
		      dataType: "json",
		      url: "<%=request.getContextPath()%>/painter/painter.do?action=searchMem&keyword=e", //要call的方法
		      contentType: "application/json; charset=utf-8",
		      error: function (msg) {
		       alert("資料載入失敗");
		      },
		      success: function (data) {
		       let result = JSON.parse(data);
		  	   var availableTags = data;
		      }
		      $("#tags").autocomplete({
		        source: availableTags
		      });
		  } );
		  </script>

	
</body>
</html>