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

여기는 글수정이 이루어지는 곳(미리 select한 결과를 출력시켜야겠지?)
글수정란은 로그인한 사람만 접근이 가능하도록 만들어야 하기 때문에 aop를 사용해야 한다.(여기로 이동하는 도중 가로채야함)

<form action="BoardModifyProc" method="post">
<input type="hidden" name="seq" value="${TextInformation.seq}">
<input id="title" type="text" name="title" value="${TextInformation.title}"><br>
<textarea id="content" rows="40" cols="100" name="content">${TextInformation.content}</textarea>
<input id="submit" type="submit" value="글수정완료">
</form>

	<script>
	
		//조건에 맞지 않는 경우 submit를 못하도록 만들어야 함
		//(전송버튼을 클릭시 조건에 맞지 않을 경우 return false를 이용해서 넘어가지 않도록 조정해주면 된다.)

		$("#submit").on("click", function(){

			if ($("#title").val() == "" || $("#content").val() == "") {
				alert("제목 또는 내용을 꼭 입력해 주세요.");

				return false;		
			}
		});
		
	</script>

</body>
</html>