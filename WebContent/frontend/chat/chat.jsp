<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>聊天室</title>

 <!-- top bar -->
<%@include file="/frontend/bar/frontBarTop.jsp"%>
<%@include file="/frontend/template/YCL/YCL.css"%>

<link rel="stylesheet" type="text/css" href="chatStyle.css">

</head>

<body onload="connect();" onunload="disconnect();">





<div id="page-content" class="container ycl-mt">
    <section id="chat">
        <div class="row">
        	
        	<!-- 左側選單 -->
        	<div class="col-sm-2 col-md-2">
        		ssssss
        	</div>
        	
        	
        	<!-- 右側主要聊天室 -->
        	<div class="col-sm-10 col-md-10">
        	
        		<div class="chat_window">
        			<div class="top_menu">
        				<div class="title">Chat</div>
        			</div>
        			
        			<ul class="messages"></ul>
        			
        			<div class="bottom_wrapper clearfix">
        				<div class="message_input_wrapper">
        					<input class="message_input" placeholder="message"/></div>
        				<div class="send_message">
        					<div class="icon"></div>
        					<div class="text">送出</div>
        				</div>
        			</div>
        		</div>
        		
				<!--訊息模板 -->
        		<div class="message_template">
        			<lem class="message">
<!--         				<div class="avatar"></div> -->
        				<div class="text_wrapper"><div class="text"></div></div>
        			</li>
        		</div>
        	
        	</div>
        
        
        
        </div>
    </section>
</div>


<!-- footer -->
<%@include file="/frontend/bar/frontBarFooter.jsp"%>


<script>
var MyPoint = "/FriendWS/${sessionScope.memVO.mem_id}";
var host = window.location.host;
var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));
var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

var statusOutput = document.getElementById("statusOutput");
var messagesArea = document.getElementById("messagesArea");
var self = '${userName}';
var webSocket;














(function () {
	
    var Message;
    Message = function (arg) {
        this.text = arg.text, this.message_side = arg.message_side;
        this.draw = function (_this) {
            return function () {
                var $message;
                $message = $($('.message_template').clone().html());
                $message.addClass(_this.message_side).find('.text').html(_this.text);
                $('.messages').append($message);
                return setTimeout(function () {
                    return $message.addClass('appeared');
                }, 0);
            };
        }(this);
        return this;
    };
    
    $(function () {
        var getMessageText, message_side, sendMessage;
        message_side = 'right';
        getMessageText = function () {
            var $message_input;
            $message_input = $('.message_input');
            return $message_input.val();
        };
        sendMessage = function (text) {
            var $messages, message;
            if (text.trim() === '') {
                return;
            }
            $('.message_input').val('');
            $messages = $('.messages');
            message_side = message_side === 'left' ? 'right' : 'left';
            message = new Message({
                text: text,
                message_side: message_side
            });
            message.draw();
            return $messages.animate({ scrollTop: $messages.prop('scrollHeight') }, 300);
        };
        $('.send_message').click(function (e) {
            return sendMessage(getMessageText());
        });
        $('.message_input').keyup(function (e) {
            if (e.which === 13) {
                return sendMessage(getMessageText());
            }
        });
//         sendMessage('Hello Philip! :)');
//         setTimeout(function () {
//             return sendMessage('Hi Sandy! How are you?');
//         }, 1000);
//         return setTimeout(function () {
//             return sendMessage('I\'m fine, thank you!');
//         }, 2000);
    });
}.call(this));

</script>


</body>


</html>