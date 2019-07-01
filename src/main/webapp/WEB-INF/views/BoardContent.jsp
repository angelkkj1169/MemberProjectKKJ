<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>
<body>
여기는 클릭한 글의 제목과 내용이 나와야 합니다.<br><br>

<div>글 제목 : ${TextInformation.title} </div>
<div>글 내용 : ${TextInformation.content} </div>

<c:choose>
<c:when test="${loginid == TextInformation.id}">

<input id="modify" type="button" value="글수정">
<input id="delete" type="button" value="글삭제">

<script>
$("#modify").on("click",function(){
location.href = "modifymove?BoardNumber=${TextInformation.seq}";})

$("#delete").on("click",function(){
	
var tf = confirm("정말로 이 글을 삭제하시겠습니까?");		
if(tf==true){location.href= "BoardDeleteProc?BoardNumber=${TextInformation.seq}";}})
</script>

</c:when>
</c:choose>



<input id="back" type="button" value="목록으로 돌아가기">
<script>
$("#back").on("click",function(){	
location.href= "boardmove";})
</script>


</body>
</html>