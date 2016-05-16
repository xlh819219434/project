	
// 使不支持console对象的浏览器中使用console的下述方法不出错，避免中断正常的JS执行。
if (typeof console == 'undefined' || console == null) {
	console = {
		log : function(s) {
		},
		debug : function(s) {
		},
		info : function(s) {
		},
		warn : function(s) {
		},
		error : function(s) {
		},
		fatal : function(s) {
		},
		message : function(s) {
		}
	};
}

// 缓存数据对象，使用sessionStorage存储数据，使不同页面之间共享数据
var BufferUtil = (function () {
	if (sessionStorage) {
		return {
			put : function (key, value) {
				sessionStorage.setItem(key, JSON.stringify(value));
			},
			get : function (key) {
				return JSON.parse(sessionStorage.getItem(key));
			},
			remove : function (key) {
				sessionStorage.removeItem(key);
			},
			clear : function () { // 清空
				sessionStorage.clear();
			},
			size : function () { // 返回存储的属性数
				return sessionStorage.length;
			},
			key : function (i) { // 返回属性名
				return sessionStorage.key(i);
			}
		};
	} else {
		alert('请使用支持html5的浏览器');
		return undefined;
	}
})();

// Html编码获取Html转义实体，eg: value='<br/>' => htmlEncode(value)=='&lt;br/&gt;'
function htmlEncode(value) {
	return $('<div></div>').text(value).html();
}

// Html解码获取Html实体，eg: value='&lt;br/&gt;' => htmlDecode(value)=='<br/>'
function htmlDecode(value) {
	return $('<div></div>').html(value).text();
}

// 生成不重复的元素ID
function createID() {
	var id = '';
	while (true) {
		id = 't' + new Date().getTime() + '_r' + parseInt(Math.random() * 1000);
		if ($('#' + id).length < 1) {
			break;
		}
	}
	return id;
}

/**
 * 格式化文件大小, 输出成带单位的字符串
 * @method formatSize
 * @grammar Base.formatSize( size ) => String
 * @grammar Base.formatSize( size, pointLength ) => String
 * @grammar Base.formatSize( size, pointLength, units ) => String
 * @param {Number} size 文件大小
 * @param {Number} [pointLength=2] 精确到的小数点数。
 * @param {Array} [units=[ 'B', 'K', 'M', 'G', 'TB' ]] 单位数组。从字节，到千字节，一直往上指定。如果单位数组里面只指定了到了K(千字节)，同时文件大小大于M, 此方法的输出将还是显示成多少K.
 * @example
 * console.log( Base.formatSize( 100 ) );    // => 100B
 * console.log( Base.formatSize( 1024 ) );    // => 1.00K
 * console.log( Base.formatSize( 1024, 0 ) );    // => 1K
 * console.log( Base.formatSize( 1024 * 1024 ) );    // => 1.00M
 * console.log( Base.formatSize( 1024 * 1024 * 1024 ) );    // => 1.00G
 * console.log( Base.formatSize( 1024 * 1024 * 1024, 0, ['B', 'KB', 'MB'] ) );    // => 1024MB
 */
function formatSize(size, pointLength, units) {
	var unit;
	units = units || [ 'B', 'K', 'M', 'G', 'TB' ];
	while ((unit = units.shift()) && size > 1024) {
		size = size / 1024;
	}
	return (unit === 'B' ? size : size.toFixed(pointLength || 2)) + unit;
}

// remove whitespaces from both sides of a string
if (String.prototype.trim == undefined) {
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/gm,'');
	};
}

(function() {
	var userAgent = window.navigator.userAgent.toLowerCase(), 
	ios = /iphone|ipod|ipad/.test(userAgent);
	if (ios) {
		// 设置body的宽度为窗口的宽度，已限定内容的宽度。因为在ipad中给iframe指定宽度无效，最终宽度由内容的宽度决定。
		if (self.frameElement && self.frameElement.tagName == "IFRAME") {
			var containerWidth = $(self.frameElement).parent().width() - 2;
			$('body').width(containerWidth);
			$(self.frameElement).parent().css({
				overflow: 'auto',
				'-webkit-overflow-scrolling': 'touch'
            });
		}
	}
})();

/* 打开一个标签 */
function openTab(title, url, icon) {
	/**
	 * 如果这个标题的标签存在，则选择该标签 否则添加一个标签到标签组
	 */
	var tabs = top.$("#home-tab");
	if (tabs.tabs('exists', title)) {
		var t = tabs.tabs("getTab", title);
		var _url = $(t.panel('options').content).attr('src');
		if (_url != url) {
			/* 重新设置该标签 */
			tabs.tabs('update', {
				tab : t,
				options : {
					content : createTabContent(url, title)
				}
			});
		}
		tabs.tabs('select', title);
	} else {
		var content = '';
		if (url == null) {
			content = "页面不存在";
		} else {
			content = createTabContent(url, title);
		}
		tabs.tabs('add', {
			title : title,
			content : content,
			closable : true,
			icon : icon
		});
	}
}

/* 生成标签内容 */
function createTabContent(url, title) {
	url = $.trim(url);
	url += (url.indexOf('?') > 0 ? '&' : '?') + '_tabIndex=' + getCurTabIndex();
    return '<iframe name="'+title+'" style="width:100%;height:100%;" class="iframe-fit" scrolling="auto" frameborder="0" src="' + url + '"></iframe>';
}

/* 关闭标签 */
function closeTab(whitch) {
	var tabs = top.$("#home-tab");
	if (tabs.tabs('getTab', whitch) != null)
		if (tabs.tabs('getTab', whitch).panel('options').closable)
			tabs.tabs('close', whitch);
}
function closeCurTab() {
	closeTab(getCurTabIndex());
}

function getCurTabIndex() {
	var tab = top.$("#home-tab").tabs('getSelected');
	var index = top.$("#home-tab").tabs('getTabIndex', tab);
	return parseInt(index);
}

function getTabContentWindow(whitch) {
	var title = null;
	try {
		whitch = parseInt(whitch);
		title = top.$("#home-tab").tabs('getTab', whitch).panel('options').title;
	} catch (e) {
		title = null;
	}
	return title ? top.frames[title] : title;
}

/**
 * 使元素自适应浏览器窗口高度，即设置元素的高度为从当前obj的位置开始到窗口底部的高度。 注：如果元素是隐藏的，则不改变元素的高度。
 * 
 * @param id
 *            DOM元素ID
 * @param adjust
 *            元素底部与窗口底部的偏离像素值， R1: 不是数字时默认设为0表示没有偏离； R2: 负数表示与窗口底部的间隔； R3:
 *            正数表示超过窗口底部的距离。 注：以上规则只有当元素的高度大于minHeight并小于maxHeight时适用。
 * @param minHeight
 *            元素最小高度，不是大于0的数字时默认设为0
 * @param maxHeight
 *            元素最大高度，不是数字时默认设为null表示没有最大高度限制，小于minHeight则设为minHeight
 * @param fn
 *            调整元素高度后的回调函数
 */
function autoFitWindowRestHeight(id, adjust, minHeight, maxHeight, fn) {
	var timer = 200;
	var $e = $("#" + id);
	if ($e.length > 0) {
		if (adjust == null || isNaN(adjust)) {
			adjust = 0;
		}
		if (minHeight == null || isNaN(minHeight) || minHeight < 0) {
			minHeight = 0;
		}
		if (maxHeight == null || isNaN(maxHeight)) {
			maxHeight = null;
		} else if (maxHeight < minHeight) {
			maxHeight = minHeight;
		}
		var scrollTop = window.pageYOffset
				|| (document.documentElement && document.documentElement.scrollTop)
				|| document.body.scrollTop;
		var targetHeight = scrollTop + $(window).height() - $e.offset().top - 5
				+ adjust;
		var lastWidth = $e.attr("__lastWidth") || 0;
		var lastTargetHeight = $e.attr("__lastTargetHeight") || 0;
		if (lastTargetHeight != targetHeight || lastWidth != $e.width()) {
//			alert("\nwindow scrollTop: "
//					 + scrollTop + ", " + $(window).scrollTop)
			// alert("top: " + $('#autoHeight').offset().top + "\nleft: "
			// + $('#autoHeight').offset().left + "\nwindow scrollTop: "
			// + scrollTop + "\nwindow height: " + $(window).height());
			timer = 200;
			var trueHeight = targetHeight < minHeight ? minHeight
					: targetHeight;
			trueHeight = maxHeight != null && trueHeight > maxHeight ? maxHeight : trueHeight;
			$e.height(trueHeight);
			$e.attr("__lastWidth", $e.width());
			$e.attr("__lastTargetHeight", targetHeight);
			$e.attr("__isResizeDone", "y");
//			alert("targetHeight: " + targetHeight + "\nlastTargetHeight: "
//					+ lastTargetHeight + "\nadjust: " + adjust
//					+ "\nminHeight: " + minHeight + "\nmaxHeight: " + maxHeight
//					+ "\ntrueHeight: " + trueHeight);
		} else {
			timer = 500; // 降低频率
			if ($e.attr("__isResizeDone") == "y") {
				try { // 没变化就不调用，减少占用CPU资源。
					// alert('call' + fn);
					fn.call(this);
				} catch (e) {
					// not a function
				}
				$e.attr("__isResizeDone", "n");
			}
		}
	}
	setTimeout(function() {
		autoFitWindowRestHeight(id, adjust, minHeight, maxHeight, fn);
	}, timer);
}

$.extend({
    getUrlVars: function(){
        var vars = [], hash;
	// url中可能出现#
        var hashes = window.location.search.slice(window.location.search.indexOf('?') + 1).split('&');
        for(var i = 0; i < hashes.length; i++)
        {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        return vars;
    },
    getUrlVar: function(name){
        return $.getUrlVars()[name];
    }
});
$.extend($.fn.form.methods, {
    myLoad : function (jq, param) {
        return jq.each(function () {
            load(this, param);
        });

        function load(target, param) {
            if (!$.data(target, "form")) {
                $.data(target, "form", {
                    options : $.extend({}, $.fn.form.defaults)
                });
            }
            var options = $.data(target, "form").options;
            if (typeof param == "string") {
                var params = {};
                if (options.onBeforeLoad.call(target, params) == false) {
                    return;
                }
                $.ajax({
                    url : param,
                    data : params,
                    dataType : "json",
                    success : function (rsp) {
                        loadData(rsp);
                    },
                    error : function () {
                        options.onLoadError.apply(target, arguments);
                    }
                });
            } else {
                loadData(param);
            }
            function loadData(dd) {
                var form = $(target);
                var formFields = form.find("input[name],select[name],textarea[name]");
                formFields.each(function(){
                    var name = this.name;
                    var value = jQuery.proxy(function(){try{return eval('this.'+name);}catch(e){return "";}},dd)();
                    var rr = setNormalVal(name,value);
                    if (!rr.length) {
//                        var f = form.find("input[numberboxName=\"" + name + "\"]");
//                        if (f.length) {
//                            f.numberbox("setValue", value);
//                        }
                        //新版本的easyui已经统一使用textboxname作为统一标志，numberboxName依旧有保留
                        var t = form.find("input[textboxname=\""+name+"\"]");
                        if(t.length) {
                            t.textbox("setValue",value);
                        }
                            $("input[name=\"" + name + "\"]", form).val(value);
                            $("textarea[name=\"" + name + "\"]", form).val(value);
                            $("select[name=\"" + name + "\"]", form).val(value);
                    }
                    setPlugsVal(name,value);
                });
                $('.easyui-textbox').each(function () {
                	var text = $(this).textbox('getText');
                	var value = $(this).textbox('getValue');
                	if (text != value) {
                		$(this).textbox('setText', value);
                	}
                });
                
                options.onLoadSuccess.call(target, dd);
                $(target).form("validate");
            };
            function setNormalVal(key, val) {
                var rr = $(target).find("input[name=\"" + key + "\"][type=radio], input[name=\"" + key + "\"][type=checkbox]");
                rr._propAttr("checked", false);
                rr.each(function () {
                    var f = $(this);
                    if (f.val() == String(val) || $.inArray(f.val(), val) >= 0) {
                        f._propAttr("checked", true);
                    }
                });
                return rr;
            };
            function setPlugsVal(key, val) {
            	try {
                    var form = $(target);
                    var cc = ["combobox", "combotree", "combogrid", "datetimebox", "datebox", "combo"];
                    var c = form.find("[comboName=\"" + key + "\"]");
                    if (c.length) {
                        for (var i = 0; i < cc.length; i++) {
                            var combo = cc[i];
                            if (c.hasClass(combo + "-f")) {
                                if (c[combo]("options").multiple) {
                                    c[combo]("setValues", val);
                                } else {
                                    c[combo]("setValue", val);
                                }
                                return;
                            }
                        }
                    }
            	} catch (e) {
            	}
            };
        };
    }
});

//增加日期格式化函数
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};
/**
 * 增加数据加载的遮罩层
 */
function loading() {
	y = document.body.scrollTop + ($(window).height() - 45) / 2 ;
    $("<div class=\"datagrid-mask\"></div>").css({"z-index":10000, display: "block", width: "100%", height: $(document).height() }).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({"z-index":10000,  height:"40px", display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: y });
}
/**
 * 去除遮罩层
 */
function unloading() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}
/**
 * 在iframe加载完成之后调用，根据iframe的内容在iframe框架中的宽度和高度设置iframe框架的宽度和高度。
 * 执行后iframe框架的宽度为iframe内容的宽度（由iframe的初始宽度和iframe中最宽的不换内容决定），高度为iframe内容的高度（由iframe的宽度决定）。
 * 说明：最初的iframe内容宽度与内容中最宽元素和最初iframe框架的宽度有关。
 * 
 * @param iframeObj  iframe元素
 *            
 */
function embedIframe(iframeObj) {
//	console.debug("i'm here");
	var delay = 0;
	if (navigator.userAgent.search("Chrome") >= 0) {
		delay = 500;
	}
	setTimeout(function() {
		if (iframeObj && iframeObj.tagName
				&& iframeObj.tagName.toLowerCase() == "iframe") {
			iframeObj.height = 1; // 为确保后面得到真正的内容高度，一些浏览器中body.scrollHeight的值不小于窗口高度。
			var doc = (iframeObj.contentWindow || iframeObj.contentDocument);
			if (doc.document) {
				doc = doc.document;
			}
			iframeObj.width = doc.body.scrollWidth;
			if (iframeObj.height != doc.body.scrollHeight) {
				iframeObj.height = doc.body.scrollHeight;
			}
		}
	}, delay);
}

// jquery.datagrid 扩展
(function() {
	$.extend($.fn.datagrid.methods, {
		// 显示遮罩
		loading : function(jq) {
			return jq.each(function() {
				loading();
			});
		},
		// 隐藏遮罩
		loaded : function(jq) {
			return jq.each(function() {
				unloading();
			});
		}
	});
})(jQuery);

$(document).ready(function() {
	rightFilter();
});
/**
 * 按当前用户的权限显示相关操作
 */
function rightFilter() {
	var $a = $('a[right][_isRightChecked!="true"]');
	var timer = 1000;
	if ($a.length > 0) {
		$a.each(function() {
			// right可以是rightid，也可以是righturl
			var rightinfo = $(this).attr("right");
			if (window.top.rightIdMap || window.top.rightURLMap) {
				$(this).attr('_isRightChecked', 'true'); // 避免重复检测
			}
			if ((window.top.rightIdMap && window.top.rightIdMap[rightinfo])
					|| (window.top.rightURLMap && window.top.rightURLMap[rightinfo])) {
				$(this).removeClass("hide");
				$(this).parent('.easyui-panel').removeClass("hide");
				$(this).css("display","inline-block");
			}
		});
	} else {
		timer = 2000; // 降低频率
	}

	setTimeout(function() {
		rightFilter();
	}, timer);
}

/**
 * jQuery serializeObject
 * 
 * @copyright 2014, macek <paulmacek@gmail.com>
 * @link https://github.com/macek/jquery-serialize-object
 * @license BSD
 * @version 2.4.5
 */
(function(root, factory) {

	// AMD
	if (typeof define === "function" && define.amd) {
		define([ "exports", "jquery" ], function(exports, $) {
			return factory(exports, $);
		});
	}

	// CommonJS
	else if (typeof exports !== "undefined") {
		var $ = require("jquery");
		factory(exports, $);
	}

	// Browser
	else {
		factory(root, (root.jQuery || root.Zepto || root.ender || root.$));
	}

}(this, function(exports, $) {
	var patterns = {
		push : /^$/,
		fixed : /^\d+$/,

		// validate:
		// /^[a-z_][a-z0-9_]*(?:\[(?:\d*|[a-z0-9_]+)\])*$/i,
		key : /[a-z0-9_]+|(?=\[\])/gi,
		named : /^[a-z0-9_]+$/i,

		// use . to nest keys
		validate : /^[a-z][a-z0-9_]*(?:\.[a-z0-9_]+)*(?:\[\])?$/i,

	// key: /[a-z0-9_-]+|(?=\[\])/gi,
	// named: /^[a-z0-9_-]+$/i
	};

	function FormSerializer(helper, $form) {

		// private variables
		var data = {}, pushes = {};

		// private API
		function build(base, key, value) {
			base[key] = value;
			return base;
		}

		function makeObject(root, value) {

			var keys = root.match(patterns.key), k;

			// nest, nest, ..., nest
			while ((k = keys.pop()) !== undefined) {
				// foo[]
				if (patterns.push.test(k)) {
					var idx = incrementPush(root.replace(
							/\[\]$/, ''));
					// 不增加下标
					value = build([], idx, value);
					// value = build([], , value);
					// value = build({}, k, value);
				}

				// foo[n]
				else if (patterns.fixed.test(k)) {
					value = build([], k, value);
				}

				// foo; foo[bar]
				else if (patterns.named.test(k)) {
					value = build({}, k, value);
				}
			}

			return value;
		}

		function incrementPush(key) {
			if (pushes[key] === undefined) {
				pushes[key] = 0;
			}
			return pushes[key]++;
		}

		function encode(pair) {
			switch ($('[name="' + pair.name + '"]', $form)
					.attr("type")) {
			case "checkbox":
				return pair.value === "on" ? true : pair.value;
			}
			return $.trim(pair.value);
		}

		function addPair(pair) {
			if (!patterns.validate.test(pair.name))
				return this;
			var obj = makeObject(pair.name, encode(pair));
			data = helper.extend(true, data, obj);
			return this;
		}

		function addPairs(pairs) {
			if (!helper.isArray(pairs)) {
				throw new Error(
						"formSerializer.addPairs expects an Array");
			}
			for (var i = 0, len = pairs.length; i < len; i++) {
				this.addPair(pairs[i]);
			}
			return this;
		}

		function serialize() {
			return data;
		}

		function serializeJSON() {
			return JSON.stringify(serialize());
		}

		// public API
		this.addPair = addPair;
		this.addPairs = addPairs;
		this.serialize = serialize;
		this.serializeJSON = serializeJSON;
	}

	FormSerializer.patterns = patterns;

	FormSerializer.serializeObject = function serializeObject() {
		if (this.length > 1) {
			return new Error(
					"jquery-serialize-object can only serialize one form at a time");
		}
		return new FormSerializer($, this).addPairs(
				this.serializeArray()).serialize();
	};

	FormSerializer.serializeJSON = function serializeJSON() {
		if (this.length > 1) {
			return new Error(
					"jquery-serialize-object can only serialize one form at a time");
		}
		return new FormSerializer($, this).addPairs(
				this.serializeArray()).serializeJSON();
	};

	if (typeof $.fn !== "undefined") {
		$.fn.serializeObject = FormSerializer.serializeObject;
		$.fn.serializeJSON = FormSerializer.serializeJSON;
	}

	exports.FormSerializer = FormSerializer;

	return FormSerializer;
}));

/**
 * 发送ajax异步POST请求调用后台服务，服务需返回JSON字条串。
 * 
 * @param url
 *            请求地址
 * @param data
 *            要发送的JS对象，此JS对象将被序列化为JSON字符串，并作为参数名为‘___p’的值进行发送
 * @param successCallback
 *            成功返回时调用此方法，并传入两个参数：返回JSON字符串对应的JS对象和retMsg对象。<br>
 *            注意：返回码（retMsg.code）小于等于100为内部定义的返回码，内部定义的返回码会被统一处理（部分例外，如：表示成功的1），此时successCallback方法不会被调用。<br>
 *            目前只有当返回码为1或大于100时，successCallback才会被调用。
 * @param errorCallback
 *            请求过程中发生错误时调用此方法，未指定此方法将按默认方式处理
 */
function http(url, data, successCallback, errorCallback) {
	// 增加时间戳参数，避免浏览器缓存，确保每次调用都发用相应请求
	url += (url.indexOf("?") >= 0 ? '&' : '?') + '_t=' + new Date().getTime();
	$.ajax({
		url : url,
		data : {
			"___p" : JSON.stringify(data)
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
	/**
	 * 处理公共的返回信息。返回true：已对请求数据进行了相应处理；返回false：对请求数据可能需要进一步处理。
	 */
	function dealCommonResponse(data) {
		var retMsg = data ? data.retMsg || {} : {};
		if (retMsg.code == undefined) {
			top.$.messager.alert('返回信息', '返回数据异常', 'warning');
			return true;
		} else if (retMsg.code > 100) { // 大于100的为用户自定义code不做处理
			return false;
		} else { // 处理程序内部定义code
			// console.info('提示：前端可能用于业务处理的自定义返回码请使用100以上的数字');
			if (retMsg.code == 1) { // 正常返回不做处理
				return false;
			}
			var msg = '服务处理异常，请联系管理员。异常信息：' + retMsg.code + ', ' + retMsg.msg;
			top.$.messager.alert('返回信息', msg, 'warning');
			return true;
		}
	}
}