<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<title>登陆</title>
<link rel="stylesheet" type="text/css" href="/r/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/r/jquery-easyui/themes/icon.css">
	
<script type="text/javascript">
	//返回IE版本（5，6，7，8，9，10）；不是IE或版本大于10返回0。
	function getIEVersion() {
		var ua = navigator.userAgent;
		var ie = ua.indexOf("MSIE ");
		return ((ie > 0) ? parseInt(ua.substring(ie + 5, ua.indexOf(".", ie)))
				: 0);
	}

	var __ieV = getIEVersion();
	var __ieD = document.documentMode; // The documentMode is an IE only property, supported in IE8+.
	// console.log('__ieV: ' + __ieV + '\n__ieD: ' + __ieD)
	if (__ieD != undefined && __ieD < 8 || __ieV != 0 && __ieV < 8) {
		alert('当前IE浏览器版本或使用的文档模式低，请使用ie8或者以上的版本，或者关闭兼容模式继续使用！');
	}
</script>

</head>
<body style="margin:0px;font-size:12px;background: url('${ctx}/pages/r/img/back.png');">
	<form id="form1" method="post" action="${ctx}/login/checkLogin.html">
		<table width="400" border="0" align="center">
			<tr>
				<td>
					<div style="margin-left: auto; margin-right: auto;">
						<div style="margin: 100px 0;"></div>
						<div class="easyui-panel" title="用户登录"
							style="width: 400px; padding: 30px 70px 20px 70px">
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
								<a href="javascript:void(0)" onclick="alert('请联系系统管理员')">忘记密码？</a>
							</div>
							<div style="margin-bottom: 20px">
								<a href="javascript:void(0)" onclick="submitForm()"
									class="easyui-linkbutton enter-click" data-options=""
									style="padding: 5px 0px; width: 100%;"> <span
									style="font-size: 14px;">登&nbsp;&nbsp;录</span>
								</a>
								<a href="javascript:void(0)" onclick="register()"
									class="easyui-linkbutton enter-click" data-options=""
									style="padding: 5px 0px; width: 100%;"> <span
									style="font-size: 14px;">注&nbsp;&nbsp;册</span>
								</a>
							</div>
							<div id="msg"
								style="color: red; font-style: italic; display: none;"></div>
						</div>
					</div>
				</td>
			</tr>
		</table>
		
		<%-- <div style="text-align: center;">
			<img src="${ctx}/pages/r/img/line.png">
		</div>

		<div
			style="text-align: center; padding-top: 30px; border-top: 1px solid #ffffff;">ICP备案号：粤ICP备xxx号</div> --%>
	</form>
</body>
<script type="text/javascript" src="/r/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/r/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/r/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/pages/r/js/validate.js"></script>
<script type="text/javascript" src="${ctx}/pages/r/js/common.js"></script>
<script type="text/javascript" src="${ctx}/pages/r/js/plugins.js"></script>
<script type="text/javascript" src="${ctx}/pages/user/js/login.js?version=20160516"></script>
<script>

	/*	
	$(document).ready(
			function() {
				jQuery.ajax({
					type : 'GET',
					contentType : 'application/json',
					url : 'user/list',
					dataType : 'json',
					success : function(data) {
						if (data && data.success == "true") {
							$('#info').html("共" + data.total + "条数据。<br/>");
							$.each(data.data, function(i, item) {
								$('#info').append(
										"编号：" + item.id + "，姓名："
												+ item.username + "，年龄："
												+ item.age);
							});
						}
					},
					error : function() {
						alert("error")
					}
				});
				$("#submit").click(function() {
					var jsonuserinfo = $.toJSON($('#form').serializeObject());
					alert(jsonuserinfo);
					jQuery.ajax({
						type : 'POST',
						contentType : 'application/json',
						url : 'user/add',
						data : jsonuserinfo,
						dataType : 'json',
						success : function(data) {
							alert("新增成功！");
						},
						error : function(data) {
							alert("error")
						}
					});
				});
			});
	
			$(function() {
		$('#form1').form({
			success : function(data) {
				if (data) {
					alert(data)
					data = JSON.parse(data);
					if (data.rtnMsg == 'success') {
						location.href = "${ctx}/pages/index.jsp";
					} else {
						$("#msg").html(data.rtnMsg);
						$("#msg").css("display", "");
					}
				}

			}
		});
	})
	*/
	/*
	function submitForm() {
				alert('a')
		var formData = $("#form1").serializeObject();
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : '${ctx}/login/checkLogin.do',
			data : JSON.stringify(formData),
			dataType : 'json',
			success : function(data) {
				if (data) {
					alert(data)
					data = JSON.parse(data);
					if (data.rtnMsg == 'success') {
						location.href = "${ctx}/pages/index.jsp";
					} else {
						$("#msg").html(data.rtnMsg);
						$("#msg").css("display", "");
					}
				}
			},
			error : function(data) {
				alert("error")
			}
		});
		
		$.ajax({
			url : '${ctx}/login/checkLogin.do',
			data : {
				"___p" : JSON.stringify(formData)
			},
			type : "post",
			async : true,
			dataType : "json",
			beforeSend : function(jqXHR, settings) {
				try {
					loading(); // 加载蒙版
				} catch (e) {
				}
				return true;
			},
			success : function(data, textStatus, jqXHR) {
				if (!dealCommonResponse(data) && typeof successCallback == 'function') {
					try {
						successCallback(data, data ? data.retMsg || {} : {});
					} catch (e) {
						console.log('http do successCallback exception: ' + e.message);
					}
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				// textStatus: null, "timeout", "error", "abort", "parsererror"
				// errorThrown: "Not Found", "Internal Server Error."
				if (typeof errorCallback == 'function') {
					try {
						errorCallback(jqXHR, textStatus, errorThrown);
					} catch (e) {
						console.log('http do errorCallback exception: ' + e.message);
					}
				} else {
					var msg = '出现异常，请联系管理员。异常信息：' + textStatus + ", " + errorThrown;
					top.$.messager.alert('错误信息', msg, 'error');
				}
			},
			complete : function(jqXHR, textStatus) {
				try {
					unloading(); // 加载蒙版
				} catch (e) {
				}
			}
		});		
				
	}*/
	function submitForm() {
		$('#form1').form('submit');
		//location.href = "${ctx}/jsp/index.jsp";
	}
	
	$(function() {
		$('#form1').form({
			success : function(data) {
				if (data) {
					data = JSON.parse(data);
					if (data.rtnMsg == 'success') {
						location.href = "${ctx}/pages/index.jsp";
					} else {
						$("#msg").html(data.rtnMsg);
						$("#msg").css("display", "");
					}
				}

			}
		});
	});
	$(document).ready(function() {
		$('#userid').textbox('textbox').focus();
	});
</script>
</html>
