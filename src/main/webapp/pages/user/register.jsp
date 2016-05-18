<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@include file="../common/common.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户注册</title>
<link rel="stylesheet" type="text/css"
	href="/r/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="/r/jquery-easyui/themes/icon.css">
</head>
<body>
	<form id="form1" method="post" action="${ctx}/login/register.html">
		<div style="margin: 20px 0;"></div>
		<div class="easyui-panel" title="用户注册"
			style="width: 400px; padding: 30px 60px">
			<div style="margin-bottom: 10px">
				<input class="easyui-textbox"
					style="width: 100%; height: 40px; padding: 12px"
					required="required" id="userid" name="userid"
					data-options="prompt:'请输入用户名',iconCls:'icon-man',iconWidth:38">
			</div>
			<div style="margin-bottom: 20px">
				<input class="easyui-textbox" type="password"
					style="width: 100%; height: 40px; padding: 12px"
					required="required" id="passwd" name="passwd"
					data-options="prompt:'请输入密码',iconCls:'icon-lock',iconWidth:38">
			</div>
			<div style="margin-bottom: 20px">
				<input class="easyui-textbox" name="email"
					data-options="prompt:'Enter a email address...',validType:'email'"
					style="width: 100%; height: 32px" required="required">
			</div>

			<div>
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
					style="width: 100%; height: 32px" onclick="register()">注册</a>
			</div>
			<div id="msg"
								style="color: red; font-style: italic; display: none;"></div>
		</div>
	</form>
</body>
<script type="text/javascript" src="/r/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/r/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/r/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/pages/r/js/validate.js"></script>
<script type="text/javascript" src="${ctx}/pages/r/js/common.js"></script>
<script type="text/javascript" src="${ctx}/pages/r/js/plugins.js"></script>
<script>
	function register() {
		$("#msg").html("");
		$("#msg").css("display", "none");
		if (!$("#form1").form('validate')) {
			return;
		}
		var formData = $("#form1").serializeObject();
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : '${ctx}/login/register.html',
			data : JSON.stringify(formData),
			dataType : 'json',
			success : function(data) {
				if (data.rtnMsg == 'success') {
					$.messager.alert('消息','注册成功'); 
				} else {
					$("#msg").html(data.rtnMsg);
					$("#msg").css("display", "");
				}  
			},
			error : function(data) {
				alert("error:" + data)
			} 
		});
	}
</script>

</html>
