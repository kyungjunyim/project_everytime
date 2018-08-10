$('#register').click(function(e) {
		$('#textId').text('');
		$('#textPwd').text('');
		$('#textPwd2').text('');
		$('#textMail').text('');
		$('#textName').text('');
		$('#textNickName').text('');

		var obj = this;

		if ($('#id').checkNull() == false) {
			$('#id').focus();
			$('#textId').text('아이디 입력은 필수입니다.');
			return;
		}

		else if ($('#id').idCheck() == false) {
			$('#id').focus();
			$('#textId').text('아이디는 2자이상입니다.');
			return;
		}

		else if ($('#pwd').checkNull() == false) {
			$('#pwd').focus();
			$('#textPwd').text('비밀번호는 필수입니다.');
			return;
		}

		else if ($('#pwd').pwdCheck() == false) {
			$('#pwd').focus();
			$('#textPwd').text('비밀번호는 4-20자 사이 입니다.');
			return;
		}

		else if ($('#pwd2').checkNull() == false) {
			$('#pwd2').focus();
			$('#textPwd2').text('비밀번호 재확인은 필수입니다.');

			return;
		}

		else if ($('#pwd2').pwdCheck() == false) {
			$('#pwd2').focus();
			$('#textPwd2').text('비밀번호는 4-20자 사이입니다.');
			return;
		}

		else if ($('#pwd2').pwdCheck2() == false) {
			$('#pwd2').focus();
			$('#textPwd2').text('비밀번호가 일치하지 않습니다.');
			return;
		}

		else if ($('#mail').checkNull() == false) {
			$('#mail').focus();
			$('#textMail').text('메일은 필수입니다.');

			return;
		}

		else if ($('#mail').checkEmail() == false) {
			$('#mail').focus();
			$('#textMail').text('메일형식에 맞지 않습니다.');

			return;
		}

		else if ($('#name').checkNull() == false) {
			$('#name').focus();
			$('#textName').text('이름은 필수입니다.');

			return;
		}

		else if ($('#name').nameLengthCheck() == false) {
			$('#name').focus();
			$('#textName').text('이름은 2글자 이상입니다.');

			return;
		}

		else if ($('#name').checkName() == false) {
			$('#name').focus();
			$('#textName').text('이름은 한글과 영문만 사용 가능합니다.');
			return;
		}

		else if ($('#nickName').checkNull() == false) {
			$('#nickName').focus();
			$('#textNickName').text('닉네임은 필수입니다.');

			return;
		}

		else if ($('#nickName').nickNameLengthCheck() == false) {
			$('#nickName').focus();
			$('#textNickName').text('닉네임은 2-10자 사이입니다.');

			return;
		}

		$('.formField').submit();

	});

	$.fn.checkName = function(e) {
		var obj = this;
		var reg = /^[가-힣a-zA-Z]+$/;
		if (this.val().match(reg)) {
			return true;
		} else {
			return false;
		}

	}

	$.fn.checkEmail = function(e) {
		var regExp = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i

		if ($('#mail').val().length == 0) {
			return false;
		}
		if (!$('#mail').val().match(regExp)) {
			return false;
		}
		return true;
	}

	$.fn.checkNull = function(e) {
		var obj = this;
		if (obj.val() == null || obj.val().trim() == '') {
			obj.focus();
			return false;
		} else {
			return true;
		}
	};

	$.fn.idCheck = function(e) {
		var obj = this;
		if (obj.val().length < 2) {
			return false;
		} else {
			return true;
		}

	}

	$.fn.pwdCheck = function(e) {
		var obj = this;
		if (obj.val().length < 4 || obj.val().length > 20) {
			return false;
		} else {
			return true;
		}
	}

	$.fn.nameLengthCheck = function(e) {
		var obj = this;
		if (obj.val().length < 2) {
			return false;
		} else {
			return true;
		}
	}

	$.fn.nickNameLengthCheck = function(e) {
		var obj = this;
		if (obj.val().length < 2 || obj.val().length > 10) {
			return false;
		} else {
			return true;
		}
	}

	$.fn.pwdCheck2 = function(e) {
		var obj = this;
		if (obj.val() != $('#pwd').val()) {
			return false;
		} else {
			return true;
		}
	}

	$('#id').blur(function(e) {
		if (('#id').checkNull() == false) {
			$('#textId').text('아이디 입력은 필수입니다.');
		}
	});

	$('#pwd').blur(function(e) {
		if (('#pwd').checkNull() == false) {
			$('#textPwd').text('비밀번호 입력은 필수입니다.');
		}

	});

	$('#pwd2').blur(function(e) {
		if (('#pwd2').checkNull() == false) {
			$('#textPwd2').text('비밀번호 재입력은 필수입니다.');
		}
	});

	$('#mail').blur(function(e) {
		if (('#mail').checkNull() == false) {
			$('#textMail').text('메일 입력은 필수입니다.');
		}
	});

	$('#name').blur(function(e) {
		if (('#name').checkNull() == false) {
			$('#textName').text('이름 입력은 필수입니다.');
		}
	});

	$('#nickName').blur(function(e) {
		if (('#nickName').checkNull() == false) {
			$('#textNickName').text('닉네임 입력은 필수입니다.');
		}
	});