<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>아이디/비밀번호 찾기 실패화면</title>
</head>
<body>
<h3>아이디/비밀번호 찾기 결과</h3>
검색 결과 입니다.<br/>
<%
	String error = (String)request.getAttribute("error");
	out.print(error+"<p>");
%>


<div><button type="button" onclick="location.href='login.html'">로그인화면</button></div>

</body>
</html>