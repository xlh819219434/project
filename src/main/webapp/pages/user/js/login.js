//登录
function login() {
	$("#msg").css("display", "none");
	$("#msg").html("");
	
	if (!$("#form1").form('validate')) {
		return;
	}

	var formData = $("#form1").serializeObject();
	alert(formData)
	for(var i = 0 ; i < formData.length;i++) {
		alert(form[i].value)
	}
	http("login/checkLogin.do", formData, function(data, retMsg) {
		if (retMsg.code == 1) {
			top.location.href = "/rq";
		} else {
			$("#msg").html(retMsg.msg);
			$("#msg").css("display", "");
		}
	});
}

//注册
function register() {
	
}

function logout() {
	top.$.messager.confirm("确认退出", "您想要退出该系统吗？", function(r) {
		if (r) {
			http("/rq/app/auth/login/logout", {}, function(data) {
				top.location.href = '/rq/login/login.html';
			});
		}
	});
}
