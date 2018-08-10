<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	.board{
		border: 1px solid black;
	}
</style>
</head>
<body>

	<div>
		<div>자유게시판</div>
		<div>
		<form action="member.do" method="post">
			<input type="hidden" name="job" value="setting">
			<button type="submit" name="userSetBtn" id="userSetBtn">계정설정</button>
		</form>
		<form action="member.do" method="post">
			<input type="hidden" name="job" value="logout">
			<button type="submit" name="logoutBtn" id="logoutBtn">로그아웃</button>
		</form>
		</div>
	</div>
	<div class="board">
		<form action="boardInsert.do" method="post">
			<input type="hidden" name="job" value="writePostPage">
			<div><button type="submit" name="writeBtn" id="writeBtn">글쓰기</button></div>
		</form>
		
		<c:forEach items="${posts}" var="post" begin="0" end="${postsLength }" varStatus="status">
		<div id="post${status.index}" class="board">
			<form action="boardSearch.do" method="post" id="postForm${status.index }">
			
				<input type="hidden" name="job" value="postDetail">
				<input type="hidden" name="index" value="${status.index }">
				<input type="hidden" name="postId${status.index }" value="${post[0]}">
				<div id="title${status.index}">${post[1] }</div>
				<div>${post[2] }</div>
				<div>
					<span class = "date">${post[3] }</span>
					<c:if test="${post[6] == 'true' }">
						<span class = "nickName">${post[4]}</span>
					</c:if>
					<c:if test="${post[6] == 'false' }">
						<span class= "nickName">익명</span>
					</c:if>
					<span>댓글 개수:${post[5]}</span>
				</div>
				
			</form>
		</div>
		</c:forEach>
		
		<div>
		<form method="post" action="boardSearch.do">
			<input type="hidden" name="job" value="search">
			<input type="hidden" name="pageNum" value="1">
			<select name="searchCategory">
				<option value="total">전체</option>
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="searchPost" id="searchPost">
			<button type="submit">검색</button>
		</form>
			<c:forEach begin="1" end="${totalPage }" varStatus="status">
			${status.index}&nbsp;|&nbsp;
			</c:forEach>
		</div>
	</div>
</body>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="js/mainJs.js"></script>
<script>
$(document).ready(function(e){
	goSubmit();
})
</script>
</html>