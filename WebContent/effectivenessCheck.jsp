<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script src="js/jquery-3.3.1.min.js"></script>
<!-- <script>





$.fn.cehckNull = function(){
	 var obj = this;
	 if(obj.val() == null || obj.val().trim()==''){
		 obj.focus();
		 return false;
	 }
};


$('#register').click(function(e){
	if($('#id').checkNull() == false){
		$('#id').focus();
		$('#textId').text('아이디 입력은 필수입니다.')
		return ;
	}
	else if($('#pwd').checkNull()==false){
		$('#pwd').focus();
		$('#textPwd').text('비밀번호는 필수입니다.');
		return ;
	}
	else if($('#pwd2').checkNull()==false){
		$('#pwd2').focus();
		$('#textPwd2').text('비밀번호 재확인은 필수입니다.');
		return ;
	}
	else if($('#mail').checkNull() == false){
		$('#mail').focus();
		$('#textMail').text('메일은 필수입니다.');
		return ;
	}
	else if($('#name').checkNull()==false){
		$('#name').focus();
		$('#textName').text('이름은 필수입니다.');
		return ;
	}
	else if($('#nickName').checkNull()==false){
		$('#nickName').focus();
		$('#textNickName').text('닉네임은 필수입니다.');
		return ;
	}
	$('#formField').attr('action','sign.Action');
	$('#formField').attr('method','POST');
	$('#register').attr('type','submit');
	
});



$('#id').blur(function(e){
	if(('#id').checkNull() == false){
		$('#textId').text('아이디 입력은 필수입니다.');
	}
});

$('#pwd').blur(function(e){
	if(('#pwd').checkNull() == false){
		$('#textPwd').text('비밀번호 입력은 필수입니다.');
	}
		
});



$('#pwd2').blur(function(e){
	if(('#pwd2').checkNull() == false){
		$('#textPwd2').text('비밀번호 재입력은 필수입니다.');
	}
});



$('#mail').blur(function(e){
	if(('#mail').checkNull() == false){
		$('#textMail').text('메일 입력은 필수입니다.');
	}
});


$('#name').blur(function(e){
	if(('#name').checkNull() == false){
		$('#textName').text('이름 입력은 필수입니다.');
	}
});


$('#nickName').blur(function(e){
	if(('#nickName').checkNull() == false){
		$('#textNickName').text('닉네임 입력은 필수입니다.');
	}
});



$.fn.idCheck = function(e){
	
	if(this.val())
	
	
	
	
}

$.fn.pwdCheck = function(e){
	
	
	
}



</script> -->


</head>
<body>

<form class="formField" id="formField">
	<div>
		<div class="formTitle">회원 정보 입력</div>
		<div><input type ="text" class="id" name="id" id="id" placeholder="아이디입력"><div id="textId">2자이상 입력하세요</div></div>
		<div><input type="password" class="pwd" name="pwd" id="pwd"><div id="textPwd">4-20글자로 입력하세요</div></div>
		<div><input type="password" class="pwd2" name="pwd2" id="pwd2"><div id="textPwd2">비밀번호가 일치하지 않습니다</div></div>
		<div><input type="text" class="mail" name="mail" id="mail" placeholder="이메일입력"><div id="textMail">이메일 형식이 일치하지 않습니다</div></div>
		<div><input type="text" class="name" name="name" id="name" placeholder="이름입력"><div id="textName">2자이상 입력하세요</div></div>
		<div><input type="text" class="nickName" name="nickName" id="nickName" placeholder="닉네임입력"><div id="textNickName">2-10글자로 입력하세요</div></div>
	</div>
	<div>
	<button type="submit" class="register" name="register" id="register">등록하기</button>
	<button type="button" class="cancel" name="cancel" id="cancel">취소하기</button>
	</div>

</form>




</body>
</html>