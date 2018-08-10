<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 등록화면</title>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<style>
	.formField{
	display:block;
	border: 2px solid black;
	width:600px;
	height:auto;
	}
	.postTitle{
	display:block;
	height:auto;
	width:350px;
	}
	.postContent{
	display:block;
	height:400px;
	width:350px;
	}

</style>
<script>
$.fn.check = function(e){
		var obj = this;
		if(obj.val().trim() == ''){
			return true;
		}else{
			return false;
		}
}

$('document').ready(function(e){
	
	$('.btnReg').click(function(e){
		if($('.postTitle').check() || $('.postContent').check()){
			alert("제목과 내용은 필수 입력사항입니다.");
			$('.btnReg').unbind('click');
		}else{
			$('#formId').submit();
			$('.btnReg').unbind('click');
		}
		
	});
	
	
});
</script>
</head>
<body>
<div>자유게시판
<button type="button" name="btnLogout" class="btnLogout" id="btnLogout">로그아웃</button>
</div>



<div class="formField" id="formField">
<form id="formId" action="boardInsert.do" method="POST">
	<div>
		<input type="text" name="postTitle" class="postTitle" id ="postTitle" placeholder="글 제목">
		<input type="checkbox" name="isAnonymous" value="1">익명여부
	</div>
	<div>
		<textarea name="postContent" class="postContent" id="postContent" placeholder="여기를 눌러서 글을 작성할 수 있습니다"></textarea>
	</div>
	<div>
		<input type="hidden" name="job" value="writePost">
		<button type="submit" name="btnReg" class="btnReg" id="btnReg">등록</button>
	</div>
</form>
	<div>
		<form action="boardList.do" method="post">
			<input type="hidden" name="job" value="postList">
			<input type="hidden" name="pageNum" value="1">
			<button type="button" name="btnCancel" class="btnCancel" id="btnCancel">취소</button>
		</form>
		
	</div>
	 

</div>
</body>
</html>