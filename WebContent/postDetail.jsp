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
			<input type="hidden" name="job" value="deleteMember">
			<button type="submit" name="logoutBtn" id="logoutBtn">로그아웃</button>
		</form>
		</div>
	</div>
	<div class="board">
	<c:if test="${postInfo[6] == 'true' }">
		<div>${postInfo[4]}</div>
	</c:if>
	<c:if test="${postInfo[6] == 'false' }">
		<div>익명</div>
	</c:if>
		<div>${postInfo[2]}</div>
		<input type="hidden" name="postMemberId" id="postMemberId" value="${postInfo[5]}">
		<div>
			<div>${postInfo[1]}</div>
			<div>${postInfo[3]}</div>
			<div>${postInfo[5]}</div>
			<div>
			<c:forEach items="${replies}" var="replie" varStatus="rePlyStatus">
				<div class="board" style="background-color:#cccccc">
					<c:if test="${replie[5] == 'true'}">
					<span>${replie[2]}</span><br>
					</c:if>
					<c:if test="${replie[5] == 'false'}">
					<span>익명</span><br>
					</c:if>
					<span>${replie[3] }</span><br>
					<span>${replie[4]}</span><br>
					<div>댓글 번호:${rePlyStatus.index+1}</div>
				</div>
				
				<c:forEach items="${comments}" var="replyToComment">
					<c:forEach items="${replyToComment}" var="commentInfo" varStatus="commentStatus">
						<c:if test="${replie[1] eq commentInfo[1] }">
							<div class="board" style="background-color:##f2f2f2">
							<c:if test="${commentInfo[6]=='true' }">
								<span>${commentInfo[5]}</span><br>
							</c:if>
							<c:if test="${commentInfo[6]=='false' }">
								<span>익명</span><br>
							</c:if>
								<span>${commentInfo[3]}</span><br>
								<span>${commentInfo[4]}</span><br>
								<div>대댓글 번호${commentStatus.index+1 }</div>
							</div>
						</c:if>
					</c:forEach>
				</c:forEach>
			</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>