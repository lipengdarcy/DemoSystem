$(function() {

	// 眼睛
	$(".eye2 a").on("click", function() {
		$(this).parents(".eye").siblings("input").attr("type", "text");
		$(this).parent(".eye2").siblings(".eye1").removeClass("dn");
		$(this).parent(".eye2").addClass("dn");
	});
	$(".eye1 a").on("click", function() {
		$(this).parents(".eye").siblings("input").attr("type", "password");
		$(this).parent(".eye1").siblings(".eye2").removeClass("dn");
		$(this).parent(".eye1").addClass("dn");
	});

	$("#signupForm").validate({
		rules : {
			userName : {
				required : true
			},
			passWord : {
				required : true,
			// minlength: 5
			}
		},
		messages : {
			userName : {
				required : "请输入您的账号！",
			},
			passWord : {
				required : "请输入您的密码！",
			}
		},
		submitHandler : function(form) {
			login();
			return false;
		}

	});

	// 回车事件，等同点击按钮
	$('#signupForm input').keypress(function(e) {
		if (e.which == 13) {
			if ($('#signupForm').validate().form()) {
				$('#signupForm').submit();
			}
			return false;
		}
	});

});

// 登录
function login() {
	$('#password').val($.md5($("#password").val()));
	$.ajax({
		url : '/security/login',
		data : $('#signupForm').serialize(),
		type : 'post',
		dataType : 'json',
		success : function(res) {
			if (res.code == 0) {
				window.location.href = res.data.home;
			} else {
				$('#password').val('');
				alertDiag(res.message);
			}
		}
	});
}

// 找回密码弹框页面
function resetPage() {
	var myDialog = dialog({
		title : '找回密码',
		width : 390,
		content : $('#passmin'),
		lock : true,
	});
	myDialog.showModal();
}

//下载APP弹框页面
function loup() {
	var myDialog = dialog({
		title : '扫描二维码下载pad端APP',
		width : 390,
		content : $('#loup'),
		lock : true,
	});
	myDialog.showModal();
}

// 找回密码表单校验
function resetValid() {
	return $("#resetPasswordForm").validate({
		rules : {
			tel : {
				required : true
			},
			vcode : {
				required : true,
				equalTo : "#sentVcode"
			},
			passWord : {
				required : true,
			},
			passWord_repeat : {
				required : true,
				equalTo : "#passWord1"
			}
		},
		messages : {
			userName : {
				required : "请输入您的账号！",
			},
			passWord : {
				required : "请输入您的密码！",
			}
		},

	}).form();
}
// 找回密码
function resetPassword() {
	if (!resetValid()) {
		return;
	}
	$('#passWord1').val($.md5($("#passWord1").val()));
	$.ajax({
		url : '/user/changePassword',
		data : $('#resetPasswordForm').serialize(),
		type : 'post',
		dataType : 'json',
		success : function(data) {
			alert(data.message);
			if (data.code == 0) {
				window.location.reload();
			}
		}
	});
}

/**
 * 判断合法的手机号码 1--以1为开头； 2--第二位可为3,4,5,7,8,中的任意一位； 3--最后以0-9的9个整数结尾。
 */
function isPhoneNum(str) {
	var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
	if (!myreg.test(str)) {
		return false;
	} else {
		return true;
	}
}

// 验证码获取时间
function countDown(obj, count) {
	if (count == 0) {
		obj.removeAttribute("disabled");
		obj.value = "重新获取";
		count = 60;
		return;
	} else {
		obj.setAttribute("disabled", true);
		obj.value = "重新获取(" + count + ")";
		count--;
	}
	setTimeout(function() {
		countDown(obj, count)
	}, 1000)
}

// 发送短信验证码
function sendCode(obj) {
	var countdown = 60;
	// var tel = '13777898622';
	var tel = $('#Mobile').val();
	if (tel == '' || !isPhoneNum(tel)) {
		alert('请输入正确的手机号码后再尝试');
	} else {
		$.ajax({
			url : '/sendCode',
			data : {
				tel : tel
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				$('#sentVcode').val(data.data);
			}
		});
		countDown(obj, countdown);
	}
}

$.fn.extend({
	'active' : function() { // 登陆输入
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
		$(this).siblings().removeClass("active");
	}
});

// 浏览器页面
function look() {
	var myDialog = dialog({
		title : '浏览器',
		content : $('#look'),
		width : 520,
		height : 'auto',
	});
	myDialog.showModal();
}

// 判断高度
$(window).load(function() {
	setSizeOnResize();
});

function setSizeOnResize() {
	var height = $(window).height();
	/*
	 * var height1=height-40+"px"; var loginheight=height-380+"px";
	 */
	var loginheight2 = height - 300 + "px";
	$(".login-box").css({
		"height" : height
	});
}
$(window).resize(function() { // 判断窗体调整是否显示滚动条
	setSizeOnResize();
});
