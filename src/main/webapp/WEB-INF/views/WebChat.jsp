<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<style>
  div{
  border:1px solid black;
  box-sizing: border-box;}

.container{
width:400px;
height: 500px;
margin:0 auto;}

.contents{
width:100%;
height:85%;
overflow:auto;}

.control{
width:100%;
height:15%;}

#input{
width:80%;
height:100%;
box-sizing: border-box;
float: left;
}

#sand{
width:20%;
height:100%;
box-sizing: border-box;
}

</style>
</head>
<body>
<div class="container">
<div class="contents"></div>

<div class="control">
<input type="text" id="input">
<input type="button" id="sand" value="보내기">
</div>

</div>
<script>

//지금부터 우리가 통신을 할려는 것은 http통신이 아니다!
//(localhost에는 사용자 서버컴퓨터의 ip를 넣어줘야함)
var socket = new WebSocket("ws://localhost/webchat");

//onmessage: 메시지가 도착하는 순간
socket.onmessage = function(evt){
	
	//서버로부터 받아온 데이터(채팅 내용)를 채팅창 상자에 담는다.
	$(".contents").append(evt.data + "<br>");}
	
   //서버로 유저가 적은 채팅내용을 적는다.
	$("#sand").on("click", function() {

		var msg = $("#input").val();
		$("#input").val("");
		socket.send(msg);
	})
	
</script>
</body>
</html>