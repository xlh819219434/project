
function initAppTable() {
	var opts = {
		fit : false,
		pagination : false, // 分页控件
		toolbar : '#appTableToolbar',
		columns: [[
			{field: 'appid', title: '应用系统编号', width: 100, align: 'center' },
			{field: 'appname', title: '应用系统名称', width: 120, align: 'left'},
            {field: 'shortname',title: '应用系统简称',width: 100, align: 'center'},
			{field: '_op', title: '操作', width: 500, align: 'center', formatter:function(value, row, index) {
            	return createOp(row);
            }}
		]]
	};
	initTable("appTable", opts, "/rq/app/sys/appDef/findAll");

	function createOp(row) {
		if (row == null || $.trim(row.appid) == '') {
			return '';
		}
		var appid = $.trim(row.appid);
		var op = '<a href="javascript:void(0)" style="margin-right: 15px;" onclick="openTab(\'客户需求【' + appid + '】\', \'/rq/custreq/list.html?appid=' + appid + '\')">客户需求(<span title="待分析、分析中的客户需求记录数" style="color:red">'+row.unAnalyzeCustomerRequirements+'</span>)</a>'
			+ '<a href="javascript:void(0)" style="margin-right: 15px;" onclick="openTab(\'需求分析【' + appid + '】\', \'/rq/analyze/list.html?appid=' + appid + '\')">需求分析(<span title="未开发、开发中、未归档需求分析记录数" style="color:red">'+row.unDevelopRequirements+'</span>)</a>'
			+ '<a href="javascript:void(0)" style="margin-right: 15px;" onclick="openTab(\'版本需求\', \'/rq/version/list.html?appid=' + appid + '\')">版本需求(<span title="版本未发布记录数" style="color:red">'+row.unReleasVersions+'</span>)</a>'
			+ '<a href="javascript:void(0)" style="margin-right: 15px;" onclick="openTab(\'需求归档【' + appid + '】\', \'/rq/filing/list.html?appid=' + appid + '\')">需求归档(<span title="未归档记录数" style="color:red">'+row.unfileds+'</span>)</a>'
			+ '<a href="javascript:void(0)" style="margin-right: 15px;" onclick="openTab(\'用例库查询【' + appid + '】\', \'/rq/usecase/list.html?appid=' + appid + '\')">用例库查询(<span title="用例记录数" style="color:red">'+row.usecases+'</span>)</a>';
		return op;
	}
}

function query() {
	$("#appTable").datagrid("reload");
}
