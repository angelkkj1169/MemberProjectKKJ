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

<h1>회원가입</h1><br>

<form action="joinProc" method="post" enctype="multipart/form-data">

아이디<input type="text" name="id" id="id"><input type="button" value="중복확인" id="idcheck"><span id="resultid"></span><br>
비밀번호 <input type="text" name="password"><br>
이름 <input type="text" name="name"><br>
이미지<input type="file" name="image"><br>

<input type="submit">

</form>

<script>
//data:{person:JSON.stringify({msg:$("#text").val(),src:"ko",tar:"en"})}
$("#idcheck").on("click",function(){
	
	$.ajax({
				
		url:"IdCheck",
		type:"post",
		data:{id:$("#id").val()},
		datatype:"json"
			
	}).done(function(resp){
		
		if(resp == 0){
			
		$("#resultid").html("id가 이미 존재합니다.")	
			
		}else{
					
			$("#resultid").html("id 사용이 가능합니다!")
			
		}				
	});	
})
</script>


</body>
</html>




