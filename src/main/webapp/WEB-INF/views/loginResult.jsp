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
 <c:when test="${loginTrueFalse == 0 }">
 
 <script>
 alert("로그인에 성공했습니다.")
 location.href = "/";
 </script>
 
 </c:when>
 
 
 <c:when test="${loginTrueFalse == 1 }">
 
 <script>
 alert("비밀번호가 틀립니다.")
 location.href = "/";
 </script>
 
 </c:when>
 
 
<c:otherwise>

<script>
//http://localhost/joinForm
alert("아이디가 존재하지 않습니다.")
 location.href = "/";
</script>

</c:otherwise>



</c:choose>


</body>
</html>