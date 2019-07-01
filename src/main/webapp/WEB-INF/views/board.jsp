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

<!--이 div는 데이터베이스에 올라온 글 내용에 따라서  갯수가 변할 수 있음(jstl을 이용)
페이지 알고리즘이 가장 어려우니 일단 그건 제쳐두고 우선 기본적인 것부터 생각해보자

기본적인 게시판에는 글쓰기는 필수이고, 게시판의 어떤 글을 클릭하고 그 링크로 들어갔을 때
만일 그 글이 자기 자신이 쓴 글일 경우 글 수정하기와 삭제하기가 보여야함 / 그게 아닐경우 버튼이 비활성화되어야 함

private int seq;
	private String id;
	private String content;
	private int boardview;
	private Timestamp boardwritetime;
-->

<table border="1px">
<tr>
<td>글번호</td>
<td>아이디</td>
<td>제목</td>
<td>조회수</td>
<td>작성일</td>
</tr>


<c:forEach var="i" items="${boardlist}">
<tr>
<td>${i.seq}</td>
<td>${i.id} </td>

<td><a href="boardcontentProc?BoardNumber=${i.seq}">${i.title}</a></td>

<td>${i.boardview}</td>
<td>${i.boardwritetime}</td>
</tr>
</c:forEach>


<c:forEach var="i" items="${Selectboardlist}">
<tr>
<td>${i.seq}</td>
<td>${i.id} </td>

<td><a href="boardcontentProc?BoardNumber=${i.seq}">${i.title}</a></td>

<td>${i.boardview}</td>
<td>${i.boardwritetime}</td>
</tr>
</c:forEach>


</table>

<c:choose>
<c:when test="${loginid != null}">

<input id="write" type="button" value="글쓰기">
<script>
$("#write").on("click", function(){
	
	location.href = "BoardWriteMove";})	
</script>

</c:when>
</c:choose>

<!-- 
if (page < totalPage) {
    
    System.out.println("<a href=\"?page=" + (page + 1)  + "\">다음</a>");

}

if (endPage < totalPage) {

    System.out.print("<a href=\"?page=" + totalPage + "\">끝</a>");

} -->


<c:forEach var="i" items="${pageList}">
<c:choose>

<c:when test="${i =='<이전'}">
<a href="pageController?page=${page-1}">${i}</a>
</c:when>

<c:when test="${i =='다음>'}">
<a href="pageController?page=${page+1}">${i}</a>
</c:when>

<c:otherwise>
<a href="pageController?page=${i}">${i}</a>
</c:otherwise>

</c:choose>
</c:forEach>



<c:forEach var="i" items="${SelectpageList}">
<c:choose>

<c:when test="${i =='<이전'}">
<a href="PageKeywordSelect?page=${page-1}&keyword=${keyword}">${i}</a>
</c:when>

<c:when test="${i =='다음>'}">
<a href="PageKeywordSelect?page=${page+1}&keyword=${keyword}">${i}</a>
</c:when>

<c:otherwise>
<a href="PageKeywordSelect?page=${i}&keyword=${keyword}">${i}</a>
</c:otherwise>

</c:choose>
</c:forEach>




<br>


<!--페이지 1을 기준으로 생각하면 쉬워짐 -->
<form action="PageKeywordSelect" method="post">
<input type="hidden" name="page" value="1">
<input id="KeyWord" name="keyword" type="text" value="${keyword}">
<input id="IdBoardSelect" type="submit" value="id로 검색하기">
</form>


<input id="returnHome" type="button" value="Home으로 돌아가기">

<script>

$("#returnHome").on("click",function(){
location.href = "/";})

</script>
</body>
</html>