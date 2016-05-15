<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<title>登陆</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/plugin/jquery-easyui-1.4.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/plugin/jquery-easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/plugin/jquery-easyui-1.4.5/demo/demo.css">
<script type="text/javascript"
	src="${ctx}/plugin/jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/plugin/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>登陆</h2>
	<div style="margin: 20px 0; text-align: center"></div>
	<div class="easyui-panel" title="" style="width: 400px">
		<div style="padding: 10px 60px 20px 60px">
			<form id="form1" method="post" action="${ctx}/login/checkLogin.do">
				<table cellpadding="5">
					<tr>
						<td>用户名:</td>
						<td><input class="easyui-textbox" type="text" name="name"
							data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>密码:</td>
						<td><input class="easyui-textbox" type="password"
							name="password" data-options="required:true"></input></td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="submitForm()">登陆</a> <a href="javascript:void(0)"
					class="easyui-linkbutton" onclick="register()">注册</a>
			</div>
		</div>
	</div>
	<script>
		function submitForm() {
			$('#form1').form('submit');
			//location.href = "${ctx}/jsp/index.jsp";
		}
		$(function() {
			$('#form1').form({
				success : function(data) {
					$.messager.alert('Info', data, 'info');
				}
			});
		})
	</script>
</body>
</html>
