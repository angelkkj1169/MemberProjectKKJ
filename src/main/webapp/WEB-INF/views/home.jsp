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


<c:choose>

<c:when test="${loginid != null }">

${loginid}님 환영합니다.
(회원탈퇴를 할 경우 폴더까지 통째로 삭제되도록 만들면 되지 않나)

어자피 id는 데이터베이스의 기본키이기 때문에 파일이름이 중복될 일도 없겠지
파일이 실제로 저장되는 폴더가 따로 있고 스프링은 그 저장소로부터 경로를 읽는다는 것에 주의하자
(D:\SpringOnly\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\MemberProject\resources)
<!-- <img src="/resources/${loginid}/profile_image.png">-->

<img src="/resources/spring.png">


<!-- 
컨트롤러 어너테이션을 통한 핸들러 맵핑을 할때 사용한다. 
<mvc:resources location="/resources/" mapping="/resources/**"></mvc:resources>
<mvc:annotation-driven></mvc:annotation-driven> -->

<input id="mypage" type="button" value="마이페이지로 이동">
<input id="logout" type="button" value="로그아웃"><br>

<input id="WebChat" type="button" value="채팅하기">

<script>

$("#WebChat").on("click",function(){
	
	location.href ="WebChat";	
})



$("#mypage").on("click",function(){
	
	location.href ="mypage";	
})


$("#logout").on("click",function(){
  location.href ="logoutProc";})

</script>
</c:when>








<c:otherwise>

<form action="LoginCheck" method="post">
아이디 <input type="text" name="id"><br>
비밀번호 <input type="text" name="password"><br>
<input type="submit" value="로그인">
</form>

<input type="button" id="joinbtn" value="회원가입">

<script>
$("#joinbtn").on("click",function(){

	location.href = "joinForm";	
})

</script>
</c:otherwise>
</c:choose>



<input id="board" type="button" value="게시판으로 가기">


<script>
$("#board").on("click", function(){
	
location.href = "boardmove";	
	
})
</script>
</body>
</html>







