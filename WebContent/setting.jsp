<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<div>
			<h3>내정보</h3>
		</div>
		<div>
			<label>이름 </label> <span>${member.name }</span>
		</div>
		<div>
			<label>아이디 </label> <span>${member.memberId }</span>
		</div>
		<div>
			<label>닉네임 </label> <span>${member.nickName }</span>
		</div>
		<div>
			<label>이메일 </label> <span>${member.mail }</span>
		</div>
		<div>
			<form action="member.do" method="post">
				<input type="hidden" name="job" value="update">
				<button type="submit" class="btn-update">정보 변경</button>
			</form>
			<form class="delete" action="member.do" method="post">
				<input type="hidden" name="job" value="deleteMember">
				<button type="button" class="btn-drop">회원 탈퇴</button>
			</form>
			<form action="boardList.do" method="post">
				<input type="hidden" name="job" value="postList">
				<input type="hidden" name="pageNum" value="1">
				<button type="submit" class="home">메인화면으로</button>
			</form>
			
		</div>
</body>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	$(".btn-drop").click(function(e) {
		if(confirm("정말 탈퇴하시겠습니까??")) {
			$(".delete").submit();
		}
		else {
			return;
		}
	})
</script>
</html>