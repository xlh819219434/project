
function initUserRemote(id, value) {
	initUser(id, value, 'remote');
}
function initUser(id, value, mode) {
	//由于前端性能问题，用户下拉列表统一使用remote模式，remote端返回最多200个记录
	id = id.replace(/\./g, "\\.");
	var opts = {
		valueField : 'userid',
		textField : 'name',
		extendMatchFields : 'loginid', // combobox默认匹配valueField和textField，可以通过指定extendMatchFields匹配其它属性。
		// extendMatchFields : ['loginid'], // 可以用数组指定多个字段名，eg: ['field1', 'field12']，返回的数组中需要有相应的属性才能进行匹配。
		multiple : false,
		required : false,
		editable : true,
		delay : 500,
//		mode : mode === 'remote' ? 'remote' : 'local', // 数据只加载一次
		mode : 'remote',								//每次都从后台加载
		url : '/rq/app/auth/login/combo',
		method : 'post',
		loadFilter : function(data) {
			return data.list;
		},
		onBeforeLoad : function(param) {
			var q = $.trim($(this).combobox('getText'));
			param.code = q;
			param.name = q;
			var opts = $(this).combobox('options');
			var nowValue = $(this).combobox('getValue');
			if(opts.multiple != true && nowValue == value){
				param.code = value;
				param.name = value;
			}
		}
	};
	if (value != null) {
		opts = $.extend(opts, {
			"value" : value
		});
	}
	$("#" + id).combobox(opts);
}

function initAnalystRemote(id, value) {
	initAnalyst(id, value, 'remote');
}
function initAnalyst(id, value, mode) {
	//由于前端性能问题，用户下拉列表统一使用remote模式，remote端返回最多200个记录
	id = id.replace(/\./g, "\\.");
	var opts = {
		valueField : 'name',
		textField : 'name',
		//extendMatchFields : 'loginid', // combobox默认匹配valueField和textField，可以通过指定extendMatchFields匹配其它属性。
		// extendMatchFields : ['loginid'], // 可以用数组指定多个字段名，eg: ['field1', 'field12']，返回的数组中需要有相应的属性才能进行匹配。
		multiple : false,
		required : false,
		editable : true,
		delay : 500,
//		mode : mode === 'remote' ? 'remote' : 'local', // 数据只加载一次
		mode : 'remote',								//每次都从后台加载
		url : '/rq/app/auth/login/nameList',
		method : 'post',
		loadFilter : function(data) {
			return data.list;
		},
		onBeforeLoad : function(param) {
			var q = $.trim($(this).combobox('getText'));
			param.code = q;
			param.name = q;
			var opts = $(this).combobox('options');
			var nowValue = $(this).combobox('getValue');
			if(opts.multiple != true && nowValue == value){
				param.code = value;
				param.name = value;
			}
		}
	};
	if (value != null) {
		opts = $.extend(opts, {
			"value" : value
		});
	}
	$("#" + id).combobox(opts);
}

function initProposerRemote(id, value) {
	initProposer(id, value, 'remote');
}
function initProposer(id, value, mode) {
	//由于前端性能问题，用户下拉列表统一使用remote模式，remote端返回最多200个记录
	id = id.replace(/\./g, "\\.");
	var opts = {
		valueField : 'demandperson',
		textField : 'demandperson',
		//extendMatchFields : 'loginid', // combobox默认匹配valueField和textField，可以通过指定extendMatchFields匹配其它属性。
		// extendMatchFields : ['loginid'], // 可以用数组指定多个字段名，eg: ['field1', 'field12']，返回的数组中需要有相应的属性才能进行匹配。
		multiple : false,
		required : false,
		editable : true,
		delay : 500,
//		mode : mode === 'remote' ? 'remote' : 'local', // 数据只加载一次
		mode : 'remote',								//每次都从后台加载
		url : '/rq/analyze/requirement/combo',
		method : 'post',
		loadFilter : function(data) {
			return data.list;
		},
		onBeforeLoad : function(param) {
			var q = $.trim($(this).combobox('getText'));
			param.code = q;
			param.name = q;
			var opts = $(this).combobox('options');
			var nowValue = $(this).combobox('getValue');
			if(opts.multiple != true && nowValue == value){
				param.code = value;
				param.name = value;
			}
		}
	};
	if (value != null) {
		opts = $.extend(opts, {
			"value" : value
		});
	}
	$("#" + id).combobox(opts);
}

/**
 * 初始化模块选择树。
 * 使用注意：需要先引入JS文件“/r/jquery-easyui/tree-filter.js”
 * @param id
 * @param appid
 * @param editable 为false时不可编辑
 */
function initModuleCombotree(id, appid, editable) {
	id = id.replace(/\./g, "\\.");
	var $target = $("#" + id);
	$target.combotree({
		url : '/rq/analyze/module/getEasyMenu?appid=' + $.trim(appid),
		editable : editable === false ? false : true,
		onLoadSuccess : function(node, data) {
			var $tree = $target.combotree('tree');
			$target.combotree("textbox").bind("input propertychange", function() {
				var inputText = $.trim($(this).val());
				selectMatchNode($tree, data, inputText);
				function selectMatchNode(tree, nodeList, inputText) {
					var isSelect = false;
					if (nodeList && nodeList.length > 0) {
						var node = null;
						for (var i = 0; i < nodeList.length; i++) {
							node = nodeList[i];
							if (node.children) {
								isSelect = selectMatchNode(tree, node.children, inputText);
							}
							if (!isSelect && $.trim(inputText) == $.trim(node.text)) {
								$target.combotree('setValue', node.id);
								isSelect = true;
								break;
							}
						}
					}
					return isSelect;
				}
				var n = $tree.tree('getSelected');
				if (n == null || $.trim(n.text) != inputText) {
					$target.combotree('setValue', inputText);
				}
				$tree.tree("search", inputText);
			});
		}
	});
}

function initApp(id, value) {
	id = id.replace(/\./g, "\\.");
	var $target = $("#" + id);
	$target.combobox({
		value: value,
		valueField: 'appid',    
	    textField: 'appname',
		multiple : false,
		required : false,
		editable : true,
		delay : 500,
		mode : 'local', // 数据只加载一次
		url: '/rq/app/sys/appDef/findAll',
		method: 'post',
		loadFilter : function(data) {
			return data.list;
		},	
		onBeforeLoad : function(param) {
			var q = $.trim(param.q);
			param.code = q;
			param.name = q;
		}
	});
}