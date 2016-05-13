<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Slider Rule - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="/springmvc/plugin/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/springmvc/plugin/jquery-easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="/springmvc/plugin/jquery-easyui/demo/demo.css">
	<script type="text/javascript" src="/springmvc/plugin/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="/springmvc/plugin/jquery-easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>Slider Rule</h2>
	<p>This sample shows how to define slider rule.</p>
	<div style="margin:20px 0 50px 0;"></div>
	<input class="easyui-slider" style="width:300px" data-options="
				showTip:true,
				rule: [0,'|',25,'|',50,'|',75,'|',100]
			" value="10">
</body>
</html>