/**
 * 自定义初始化控件，对一些自定义控件和公共校验等进行统一解析或初始化。<br>
 * 当使用artTemplate之类的模板后，如果生成的html中有相应自定义初始化控件，<br>
 * 并想要相应控件有默认的能力，需要主动调用相应的初始化方法或统一调用此方法<br>
 * （注：目前此方法已设置为在/s/jquery-easyui/jquery.easyui.min.js中的$.parser.parse()完成之后调用）。
 */
function customizationParse(context) {
	// console.info('do customizationParse');
	try {
		initMultipleSelect(null, context);
	} catch (e) {
	}
	try {
		initShowOrHide(null, context);
	} catch (e) {
	}
	try {
		initEnterClick(null, context);
	} catch (e) {
	}
	try {
		initDatebox(null, context);
	} catch (e) {
	}
	try {
		initDateTimeBox(null, context);
	} catch (e) {
	}
}

/**
 * 初始化日期控件
 * 
 * @param selector
 *            jquery选择器，默认'.easyui-datebox'
 * @param context
 *            jquery选择器相对的上下文（相对选择器），默认当前文档
 * @param options
 */
function initDatebox(selector, context, options) {
	selector = selector == null ? '.easyui-datebox' : selector;
	options = options || {};
	var opts = {};
	for ( var property in options) { // 防止自定义的扩展方法被修改。
		if ('onChange' == property) {
			opts.fnOnChange = options[property];
		} else {
			opts[property] = options[property];
		}
	}
	options = null;
	$(selector, context).each(function() {
		var target = $(this);
		var targetOpts = target.datebox('options');
		var oldValidType = targetOpts.validType;

		// 扩展validType,默认添加格式校验
		var validType = [ 'verifyDateFormat[]' ];
		if (typeof oldValidType == 'string') {
			validType.push(oldValidType);
		} else if (oldValidType instanceof Array) {
			validType.concat(oldValidType);
		} else if (oldValidType instanceof Object) {
			validType = $.extend({
				'verifyDateFormat' : []
			}, oldValidType);
		}
		opts.validType = validType;

		// defaultOptions只在第一次调用当前方法初始化控件时使用
		if ($.trim(target.attr('_isUsingDefaultOptions')) != 'true') {
			opts = $.extend(getDefaultOptions(), opts);
			target.attr('_isUsingDefaultOptions', 'true');
		}
		target.datebox(opts);
	});
	function getDefaultOptions() {
		return {
			onChange : function(newValue, oldValue) {
				var target = $(this);
				var opts = target.datebox('options');
				$('.easyui-datebox').datebox('validate');
				$('.easyui-datetimebox').datebox('validate');
				if (typeof opts.fnOnChange == "function") {
					opts.fnOnChange.call(this, newValue, oldValue);
				}
			}
		};
	}
}

/**
 * 初始化日期时间控件
 * 
 * @param selector
 *            jquery选择器，默认'.easyui-datetimebox'
 * @param context
 *            jquery选择器相对的上下文（相对选择器），默认当前文档
 * @param options
 */
function initDateTimeBox(selector, context, options) {
	selector = selector == null ? '.easyui-datetimebox' : selector;
	options = options || {};
	var opts = {};
	for ( var property in options) { // 防止自定义的扩展方法被修改。
		if ('onChange' == property) {
			opts.fnOnChange = options[property];
		} else {
			opts[property] = options[property];
		}
	}
	options = null;
	$(selector, context).each(function() {
		var target = $(this);
		var targetOpts = target.datetimebox('options');
		var oldValidType = targetOpts.validType;

		// 扩展validType,默认添加格式校验
		var validType = [ 'verifyDateTimeFormat[]' ];
		if (typeof oldValidType == 'string') {
			validType.push(oldValidType);
		} else if (oldValidType instanceof Array) {
			validType.concat(oldValidType);
		} else if (oldValidType instanceof Object) {
			validType = $.extend({
				'verifyDateTimeFormat' : []
			}, oldValidType);
		}
		opts.validType = validType;

		// defaultOptions只在第一次调用当前方法初始化控件时使用
		if ($.trim(target.attr('_isUsingDefaultOptions')) != 'true') {
			opts = $.extend(getDefaultOptions(), opts);
			target.attr('_isUsingDefaultOptions', 'true');
		}
		target.datetimebox(opts);
	});
	function getDefaultOptions() {
		return {
			onChange : function(newValue, oldValue) {
				var target = $(this);
				var opts = target.datetimebox('options');
				$('.easyui-datebox').datebox('validate');
				$('.easyui-datetimebox').datebox('validate');
				if (typeof opts.fnOnChange == "function") {
					opts.fnOnChange.call(this, newValue, oldValue);
				}
			}
		};
	}
}

/**
 * 初始化下拉多选控件：元素的class中有multiple-select类名，则自动初始化为下拉多选控件。
 * 重写了combobox的以下方法或事件：loadFilter, onShowPanel, onSelect, onUnselect和formatter。
 * 使用时不应再重写以上的方法或事件，对于要使用loadFilter, onShowPanel, onSelect,
 * onUnselecton和formatter的功能， 请分别重写fnLoadFilter, fnOnShowPanel, fnOnSelect,
 * fnOnUnselect和fnFormatter。
 * 
 * @param selector
 *            jquery选择器，默认'.multiple-select'
 * @param context
 *            jquery选择器相对的上下文（相对选择器），默认当前文档
 * @param options
 */
function initMultipleSelect(selector, context, options) {
	selector = selector == null ? '.multiple-select' : selector;
	options = options || {};
	var opts = {};
	for ( var property in options) { // 防止自定义的扩展方法被修改，同时保留combobox的options初始化方式。
		if ('loadFilter' == property) {
			opts.fnLoadFilter = options[property];
		} else if ('formatter' == property) {
			opts.fnFormatter = options[property];
		} else if ('onSelect' == property) {
			opts.fnOnSelect = options[property];
		} else if ('onUnselecton' == property) {
			opts.fnOnUnselect = options[property];
		} else if ('onShowPanel' == property) {
			opts.fnOnShowPanel = options[property];
		} else {
			opts[property] = options[property];
		}
	}
	options = null;
	opts = $.extend({}, getComboboxMultipleOptions(), opts);
	$(selector, context).each(function() {
		if (!$(this).hasClass('multiple-select')) {
			$(this).addClass('multiple-select');
		}
		$(this).combobox(opts);
	});
	function getComboboxMultipleOptions() {
		return {
			multiple : true,
			loadFilter : function(data) {
				var target = $(this);
				var opts = target.combobox('options');
				if (target.attr('__checkboxName') == null) {
					target.attr('__checkboxName', '_' + target.attr('id') + '_'
							+ Math.ceil(Math.random() * 100000000000) + '_');
				}
				// console.debug('opts.fnLoadFilter: ' + opts.fnLoadFilter)
				if (typeof opts.fnLoadFilter == "function") {
					data = opts.fnLoadFilter.call(this, data);
				}
				if (data instanceof Array && data.length > 0) {
					opts.data = data;
					var checkAllItem = new Object();
					checkAllItem[opts.valueField] = '__checkAll';
					checkAllItem[opts.textField] = '全选';
					data = [ checkAllItem ].concat(data);
				} else {
					data = [];
				}
				// alert('data: ' + JSON.stringify(data) + '\n__checkboxName: '
				// +
				// target.attr('__checkboxName'))
				return data;
			},
			formatter : function(row) {
				var target = $(this);
				var checkboxName = target.attr('__checkboxName');
				var opts = target.combobox('options');
				var v = row[opts.valueField];
				var t = row[opts.textField];
				if (opts.fnFormatter) {
					t = opts.fnFormatter.call(this, row);
				}
				return '<input type="checkbox" name="' + checkboxName
						+ '" id="' + checkboxName + v + '"/>&nbsp;' + t;
			},
			onSelect : function(record) {
				var target = $(this);
				var opts = target.combobox('options');
				var checkboxName = target.attr('__checkboxName');
				var v = record[opts.valueField];
				// alert('onSelect: ' + v + ', data: ' +
				// JSON.stringify(opts.data))
				if (v == '__checkAll') {
					var flag = target.attr('__isAllChecked');
					if ('y' == flag) {
						target.combobox('clear');
						$.each($('input[name=' + checkboxName + ']'),
								function() {
									this.checked = false;
								});
						target.attr('__isAllChecked', 'n');
					} else {
						var values = new Array();
						for (var i = 0; i < opts.data.length; i++) {
							values.push(opts.data[i][opts.valueField]);
						}
						target.combobox('setValues', values);
						$.each($('input[name=' + checkboxName + ']'),
								function() {
									this.checked = true;
								});
						target.attr('__isAllChecked', 'y');
					}
				} else {
					$('#' + checkboxName + v)[0].checked = true;
					var len = target.combobox('getValues').length;
					if (len == opts.data.length) {
						$('input[name=' + checkboxName + ']:first')[0].checked = true;
						target.attr('__isAllChecked', 'y');
					}
				}
				if (typeof opts.fnOnSelect == "function") {
					opts.fnOnSelect.call(this, record);
				}
			},
			onUnselect : function(record) {
				var target = $(this);
				var checkboxName = target.attr('__checkboxName');
				var opts = target.combobox('options');
				var v = record[opts.valueField];
				// alert('onUnselect: ' + v)
				$('#' + checkboxName + v)[0].checked = false;
				var len = target.combobox('getValues').length;
				if (len != opts.data.length) {
					$('input[name=' + checkboxName + ']:first')[0].checked = false;
					target.attr('__isAllChecked', 'n');
				}
				if (typeof opts.fnOnUnselect == "function") {
					opts.fnOnUnselect.call(this, record);
				}
			},
			onShowPanel : function() {
				var target = $(this);
				var checkboxName = target.attr('__checkboxName');
				$.each($('input[name=' + checkboxName + ']'), function() {
					this.checked = false;
				});
				var opts = target.combobox('options');
				var values = target.combobox('getValues');
				var data = opts.data;
				if (values instanceof Array && data instanceof Array) {
					for (var i = 0; i < values.length; i++) {
						for (var j = 0, record = null; j < data.length; j++) {
							record = data[j];
							if (values[i] == record[opts.valueField]) {
								opts.onSelect.call(this, record);
							} else {
								// 去除没有对应项的值
							}
						}
					}
				}
				if (typeof opts.fnOnShowPanel == "function") {
					opts.fnOnShowPanel.call(this);
				}
			}
		};
	}
}

/**
 * 初始化页面中的切换或更多按钮，即有more类的元素。 通过使用元素的data-options属性传入初始化参数，共有两个参数，
 * targetId：需要控制显示或隐藏的目标元素ID； show：true显示目标元素（默认） or false隐藏目标元素。
 * 
 * @param selector
 *            jquery选择器，默认'.more'
 * @param context
 *            jquery选择器相对的上下文（相对选择器），默认当前文档
 */
function initShowOrHide(selector, context) {
	selector = selector == null ? '.more' : selector;
	$(selector, context).each(function() {
		this.data = {};
		var opts = $.trim($(this).attr('data-options'));
		if (opts) {
			if (opts.substring(0, 1) != '{') {
				opts = '{' + opts + '}';
			}
			this.data = (new Function('return ' + opts))();
		}
		this.data.show = this.data.show === false ? false : true;
		this.data.state = this.data.show ? 'hide' : 'show';
		if (this.data.targetId) {
			$(this).click(function() { // 点击时切换
				if (this.data.state == 'show') {
					this.data.state = 'hide';
					$('#' + this.data.targetId).hide();
					$(this).linkbutton({
						text : "高级查询",
						iconCls : "i-2down"
					});
				} else {
					this.data.state = 'show';
					$('#' + this.data.targetId).show();
					$(this).linkbutton({
						text : "简易查询",
						iconCls : "i-2up"
					});
				}
			});
			$(this).click();
		}
	});
}

/**
 * 当焦点为某个<input>元素时，<br>
 * 按下Enter（如果是textarea，需要同时按下Ctrl和Enter）后，将触发selector对应元素的click事件。
 * 
 * @param selector
 *            jquery选择器，默认'.enter-click'
 * @param context
 *            jquery选择器相对的上下文（相对选择器），默认当前文档
 */
function initEnterClick(selector, context) {
	selector = selector == null ? '.enter-click' : selector;
	var $target = $(selector, context);
	if ($target.length < 1) {
		return;
	}
	var $input = $(':input[_isListeningEnterClick!="true"]');
	var timer = 1000;
	if ($input.length > 0) {
		$input.each(function() {
			$(this).attr('_isListeningEnterClick', 'true'); // 避免重复注册事件
			$(this).keydown(enterClick);
		});
	} else {
		timer = 2000; // 降低频率
	}

	function enterClick(event) {
		var tagName = event.target.tagName.toLocaleLowerCase();
		// console.debug('Press Enter in [' + tagName + '], type [' +
		// event.target.type + ']')
		if (tagName == 'textarea' && !event.ctrlKey) {
			return;
		}
		if (event.which == 13) {
			$target.each(function() {
				$(this).click();
			});
		}
	}

	setTimeout(function() {
		initEnterClick(selector, context);
	}, timer);
}

/**
 * @param id
 *            datagrid控件的id
 * @param options
 *            参数集，主要用于列定义，可以添加额外的属性 options.columns = [[{field: 'XX', title:
 *            'XX',.. },{field: 'XX', title: 'XX',.. }]]
 * @param url
 *            请求数据的URL
 * @param fnParamWarpper
 *            可选 设置URL请求时的额外参数对象，比如查询条件等
 * @param fnResultWrapper
 *            可选 对返回数据进行包装，传入URL正确返回的数据，返回形如{rows:[{...},{...}], total:2}的对象
 */
function initTable(id, options, url, fnParamWarpper, fnResultWrapper) {
	id = id.replace(/\./g, "\\.");
	options = options || {};
	var opts = {};
	for ( var property in options) { // 防止自定义的扩展方法被修改，同时保留combobox的options初始化方式。
		if ('loadFilter' == property) {
			opts.fnLoadFilter = options[property];
		} else if ('onBeforeLoad' == property) { // If return false the load
			// action will be canceled.
			opts.fnOnBeforeLoad = options[property];
		} else {
			opts[property] = options[property];
		}
	}
	options = null;

	if ($.trim(url) == '') { // 使用本地数据
		url = undefined;
	} else {
		url += (/\?/.test(url) ? '&' : '?') + '_t=' + (+new Date());
	}
	opts.url = url; // 后台服务的URL

	opts.fnParamWarpper = fnParamWarpper; // 处理参数
	opts.fnResultWrapper = fnResultWrapper; // 处理结果
	// defaultOptions只在第一次调用当前方法初始化控件时使用
	if ($.trim($('#' + id).attr('_isUsingDefaultOptions')) != 'true') {
		opts = $.extend(getDefaultOptions(), opts || {});
		$('#' + id).attr('_isUsingDefaultOptions', 'true');
	}
	$('#' + id).datagrid(opts);
	function getDefaultOptions() {
		return {
			title : null,
			fit : true,
			collapsible : true,
			closable : false,
			nowrap : false,
			animate : true,
			striped : true, // 隔行背景不同
			rownumbers : true, // 行号
			singleSelect : true, // 单选
			pagination : true, // 分页控件
			pageSize : 50, // 每页显示的记录条数，默认为50
			pageList : [ 50, 100, 500, 1000 ], // 可以设置每页记录条数的列表
			remoteSort : true, // 默认进行服务端排序
			onBeforeLoad : function(p) {
				var target = $(this);
				var opts = target.datagrid('options');
				var params = {};
				if (typeof opts.fnParamWarpper == "function") {
					params = opts.fnParamWarpper.call(this) || {};
				}
				// 根据后台组装分页参数
				params.pageNumber = p.page;
				params.pageSize = p.rows;

				if (p.sort) {
					// 根据需要组装排序参数
					if (!$(this).datagrid('options').multiSort) { // 只处理单列排序的情况
						var sortName = p.sort
								.substring(p.sort.lastIndexOf(".") + 1);
						params.orderby = sortName + " " + p.order;
					}
				}
				// p发往后台
				$.extend(p, {
					"___p" : JSON.stringify(params)
				});
				if (typeof opts.fnOnBeforeLoad == "function") {
					if (false === opts.fnOnBeforeLoad.call(this, p)) {
						return false;
					}
				}
			},
			loadFilter : function(data) {
				var target = $(this);
				var opts = target.datagrid('options');
				if (typeof opts.fnLoadFilter == "function") {
					data = opts.fnLoadFilter.call(this, data);
				}
				var d = {
					rows : [],
					total : 0
				};
				if (data.retMsg && data.retMsg.code == 1) {
					if (typeof opts.fnResultWrapper == "function") {
						d = opts.fnResultWrapper.call(this, data) || {
							rows : [],
							total : 0
						};
					} else if (data.list instanceof Array) {
						d.rows = data.list;
						d.total = data.total;
					}
				} else {
					var msg = "出现未知异常，请联系管理员";
					if (data.retMsg && data.retMsg.msg)
						msg = data.retMsg.msg;
					alert(msg);
				}
				return d;
			}
		};
	}
}
