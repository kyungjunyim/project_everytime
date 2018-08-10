<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="member.do" method="post">
	<input type="hidden" name="job" value="realUpdate">
	<div><h3>회원 정보 변경</h3></div>
	
	<div>
		<input type="text" name="id" value="${member.memberId }" readonly>
	</div>

	<div>
		<input type="text" name="name" value="${member.name }">
	</div>
	
	<div>
		<input type="text" name="nickName" value="${member.nickName }">
	</div>
	
	<div>
		<input type="text" name="mail" value="${member.mail }">
	</div>
	
	<div>
		<input type="password" name="pwd" placeholder="현재 비밀번호">
	</div>
	
	<div>
		<input type="password" name="newPwd" placeholder="바꿀 비밀번호">
	</div>
	
	<div>
		<input type="password" name="newPwd2" placeholder="비밀번호 확인">
	</div>
	
	<div>
		<button type="submit" class="btn-update">변경</button>
		<button type="button" class="btn-cancel">취소</button>
	</div>
	
</form>
</body>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="js/registCheck.js"></script>
</html>