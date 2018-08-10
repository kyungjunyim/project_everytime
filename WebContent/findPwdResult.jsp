<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 찾기 화면</title>
</head>
<body>
<h3>비밀번호검색 결과</h3>
비밀번호는 : 
<%
	String pwd = (String)request.getAttribute("memberPwd");
	out.print(pwd+"<p>");
%>

<div><button type="button" onclick="location.href='login.html'">로그인화면</button></div>

</body>
</html>