// 扩展核验规则
$.extend($.fn.validatebox.defaults.rules, getCustomizedRules());

// 自定义核验规则
function getCustomizedRules() {
	return {
		value : {
			validator : function(value, param) {
				value = $.trim(value);
				return value >= param[0] && value <= param[1];
			},
			message : '数值必须介于{0}和{1}之间'
		},
		length : {
			validator : function(value, param) {
				value = $.trim(value);
				return value.length == param[0];
			},
			message : '长度只能为{0}'
		},
		minLength : {
			validator : function(value, param) {
				value = $.trim(value);
				return value.length >= param[0];
			},
			message : '最小长度为{0}'
		},
		maxLength : {
			validator : function(value, param) {
				value = $.trim(value);
				return value.length <= param[0];
			},
			message : '最大长度为{0}'
		},
		maxValue : {
			validator : function(value, param) {
				value = $.trim(value);
				return value <= param[0];
			},
			message : '最大值为{0}'
		},
		comboRequired : {
			validator : function(value, param) {
				if (value == "") {
					return false;
				} else {
					return true;
				}
			},
			message : '该下拉列表不得为空'
		},
		equals : {
			validator : function(value, param) {
				value = $.trim(value);
				return value == $.trim($(param[0]).val());
			},
			message : '重新的内容不匹配'
		},
		deptdepends : {
			validator : function(value, param) {
				if (value && $(param[0]).combobox('getValue') == "") {
					return false;
				} else {
					return true;
				}
			},
			message : '此时{1}不能为空'
		},
		as : {
			validator : function(value, param) {
				if (value == "" && $(param[0]).combobox('getValue') != "") {
					return false;
				} else {
					return true;
				}
			},
			message : '请选择一个有效选项'
		},
		loginid : {
			validator : function(value, param) {
				value = $.trim(value);
				return /^[A-Za-z]{1}[0-9A-Za-z_]{3,19}$/.test(value);
			},
			message : '由字母、数字、下划线组成<br>以字母开头，4-20个字符'
		},
		strict : {
			validator : function(value, param) {
				var $target = $(param[0]);
				if ($target.length < 1) {
					return true;
				}
				var opts = $target.combobox("options");
				var datalist = $target.combobox("getData");
				var textField = opts.textField;
				if (datalist == null || datalist.length == 0) {
					return true;
				}
				for (var i = 0; i < datalist.length; i++) {
					if ($.trim(datalist[i][textField]) == $.trim(value))
						return true;
				}
				return false;
			},
			message : '输入的选择项非系统定义，请重新选择'
		},
		combosize : {
			validator : function(value, param) {
				var ret = value.split(",");
				var max = 1;
				var min = 0;
				if (param && param.length) {
					if (param.length == 1) {
						max = param[0];
					}
					if (param.length == 2) {
						min = param[0];
						max = param[1];
					}
				}
				if (ret && ret.length) {
					if (ret.length < min || ret.length > max) {
						return false;
					}
				}
				return true;
			},
			message : '该下拉列表必须选择{0}至{1}个'
		},
		chs : {
			validator : function(value, param) {
				return /^[\u0391-\uFFE5]+$/.test(value);
			},
			message : '请输入汉字'
		},
		english : {// 验证英语
			validator : function(value) {
				return /^[A-Za-z]+$/i.test(value);
			},
			message : '请输入英文'
		},
		number : {// 验证数字
			validator : function(value) {
				return /^[0-9]{1}[0-9]{5,20}$/.test(value);
			},
			message : '卡号格式不正确'
		},
		zip : {
			validator : function(value, param) {
				return /^[1-9]\d{5}$/.test(value);
			},
			message : '邮政编码不存在'
		},
		qq : {
			validator : function(value, param) {
				return /^[1-9]\d{4,10}$/.test(value);
			},
			message : 'QQ号码不正确'
		},
		age : {// 验证年龄
			validator : function(value) {
				return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
			},
			message : '年龄必须是0到120之间的整数'
		},
		phone : {// 验证电话号码
			validator : function(value) {
				return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i
						.test(value);
			},
			message : '格式不正确,请使用下面格式:020-88888888'
		},
		intOrFloat : {// 验证整数或小数
			validator : function(value) {
				return /^\d+(\.\d+)?$/i.test(value);
			},
			message : '请输入数字，并确保格式正确'
		},
		int : {//验证正整数
			validator : function(value) {
				return /^[0-9]*[1-9][0-9]*$/i.test(value);
			},
			message : '请输入正整数，并确保格式正确'
		},
		mobile : {// 验证手机号码
			validator : function(value) {
				return /^1\d{10}$/i.test(value);
			},
			message : '手机号码格式不正确'
		},
		phoneOrmobile : {
			validator : function(value) {
				return /^((0\d{2,3}-\d{7,8})|(1\d{10}))$/i.test(value);
			},
			message : '固话或者手机格式不正确,固话格式:020-88888888'
		},
		loginName : {
			validator : function(value, param) {
				return /^[\u0391-\uFFE5\w]+$/.test(value);
			},
			message : '登录名称只允许汉字、英文字母、数字及下划线。'
		},
		username : {// 验证用户名
			validator : function(value) {
				return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
			},
			message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
		},
		safepass : {
			validator : function(value, param) {
				return safePassword(value);
			},
			message : '密码由字母和数字组成，至少6位'
		},
		password : {
			validator : function(value) {
				return /^([A-Za-z0-9_\~,=;:!@#\$%\^&\*\-\|\.\(\)\[\]\{\}<>\?\\\/\'\+\"]{6,20})$/.test(value);
			},
			message : '密码由6~20位字母、数字或者特殊字符组成'
		},
		equalTo : {
			validator : function(value, param) {
				return value == $(param[0]).val();
			},
			message : '两次输入的字符不一至'
		},
		idcard : {
			validator : function(value, param) {
				return idCard(value);
			},
			message : '请输入正确的身份证号码'
		},
		name : {// 验证姓名，可以是中文或英文
			validator : function(value) {
				return /^[\Α-\￥]+$/i.test(value)
						| /^\w+[\w\s]+\w+$/i.test(value);
			},
			message : '请输入姓名'
		},
		unnormal : {// 验证是否包含空格和非法字符
			validator : function(value) {
				return /^[a-zA-Z0-9\u4E00-\u9FA5]+$/.test(value);
			},
			message : '输入值不能为空和包含其他非法字符'
		},
		verifyDateFormat : {
			validator : function(value, param) {
				param = param || [];
				var obj = {};
				if (isDate($.trim(value), obj)) {
					return true;
				} else {
					param[10] = obj.errMsg;
					return false;
				}
			},
			message : '{10}'
		},
		verifyDateTimeFormat : {
			validator : function(value, param) {
				param = param || [];
				var obj = {};
				if (isDateTime($.trim(value), obj)) {
					return true;
				} else {
					param[10] = obj.errMsg;
					return false;
				}
			},
			message : '{10}'
		},
		compareDate : {
			validator : function(value, param) {
				return _compareDateValidator(value, param);
			},
			message : '{10}'
		},
		checkCardIdAndBirthday :{
			validator : function(value, param) {
				return _checkIdAndBirthday(value, param);
			},
			message : '身份证与生日日期不相符'
		}		
	};
}

/**
 * @param value
 *            要验证的日期或日期时间字符串，格式形如："yyyy-MM-dd" 或 "yyyy-MM-dd hh:mm:ss"
 * @param param
 *            与value进行比较的相关参数对象，<br>
 *            参数对象形如：{eq:[{id:'databox1',msg:'应等于databox1'}],lt:[{id:'databox2'},{id:'databox3',msg:'应小于databox3'}]}<br>
 *            上述参数对象表示：
 *            value等于databox1，否则提示'应等于databox1'；并且value小于databox2和databox3，否则提示'不满足【value
 *            小于 databox2的value】（没有msg则使用此默认提示）'或'应小于databox3'<br>
 *            Comparison Operators: 'eq', 'ne', 'gt', 'ge', 'lt', 'le'.
 *            (e:equal, g:greater, l:less, n:not, t:than)
 * @returns {Boolean}
 */
function _compareDateValidator(value, param) {
	param = param || [];
	var opMap = {
		'eq' : '等于',
		'ne' : '不等于',
		'gt' : '大于',
		'ge' : '大于等于',
		'lt' : '小于',
		'le' : '小于等于'
	};
	var ok = true;
	var expressions = param[0];
	if (!(expressions instanceof Object)) {
		param[10] = '输入的比较参数格式不对';
		return false;
	}
	for ( var op in expressions) {
		var targets = expressions[op];
		if (!(targets instanceof Array)) {
			param[10] = '输入的比较参数格式不对';
			return false;
		}
		for (var i = 0; i < targets.length; i++) {
			var target = targets[i];
			var rValue = $('#' + target.id).datebox('getValue');
			var comp = compareDateString(value, rValue);
			if (comp === undefined) { // 不进行比较
				continue;
			}
			if ('eq' == op) {
				ok = (comp == 0);
			} else if ('ne' == op) {
				ok = (comp != 0);
			} else if ('gt' == op) {
				ok = (comp == 1);
			} else if ('ge' == op) {
				ok = (comp == 1 || comp == 0);
			} else if ('lt' == op) {
				ok = (comp == -1);
			} else if ('le' == op) {
				ok = (comp == -1 || comp == 0);
			} else {
				target.msg = '未知比较';
				ok = false;
			}
			if (!ok) {
				param[10] = target.msg || '不满足【"' + value + '" ' + opMap[op]
						+ ' "' + rValue + '"】';
				return false;
			}
		}
	}
	return ok;
}

/* 密码由字母和数字组成，至少6位 */
function safePassword(value) {
	return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/
			.test(value));
};

function idCard(value) {
	if (value.length == 18 && 18 != value.length)
		return false;
	var number = value.toLowerCase();
	var d, sum = 0, v = '10x98765432', w = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7,
			9, 10, 5, 8, 4, 2 ], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
	var re = number
			.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
	if (re == null || a.indexOf(re[1]) < 0)
		return false;
	if (re[2].length == 9) {
		number = number.substr(0, 6) + '19' + number.substr(6);
		d = [ '19' + re[4], re[5], re[6] ].join('-');
	} else
		d = [ re[9], re[10], re[11] ].join('-');
	if (!isDate(d))
		return false;
	for (var i = 0; i < 17; i++)
		sum += number.charAt(i) * w[i];
	return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));

}

/**
 * 检查str（忽略前后空白字符）是否为 "yyyy-MM-dd" 的日期格式
 * 
 * @param str
 *            日期时间字符串
 * @param outObj
 *            对象参数，部分属性会被设置为相应的值，以供外部使用<br>
 *            outObj.date: 如果日期合法将被设为相应的日期对象<br>
 *            outObj.errMsg: 日期非法信息
 * @returns {Boolean}
 */
function isDate(str, outObj) {
	str = $.trim(str);
	outObj = outObj || {};
	var reg = /^(\d{4})(-)(\d{1,2})\2(\d{1,2})$/;
	var result = str.match(reg);
	if (result == null) {
		outObj.errMsg = '日期格式形如 "yyyy-MM-dd"';
		return false;
	}
	var d = new Date(result[1], result[3] - 1, result[4]);
	if (d.getFullYear() == result[1] && d.getMonth() + 1 == result[3]
			&& d.getDate() == result[4]) {
		outObj.date = d;
		return true;
	} else {
		outObj.errMsg = '日期非法';
		return false;
	}
}

/**
 * 判断str（忽略前后空白字符）是否是有效的日期时间格式 - "yyyy-MM-dd hh:mm:ss"
 * 
 * @param str
 *            日期时间字符串
 * @param outObj
 *            对象参数，部分属性会被设置为相应的值，以供外部使用<br>
 *            outObj.date: 如果日期合法将被设为相应的日期对象<br>
 *            outObj.errMsg: 日期非法信息
 * @returns {Boolean}
 */
function isDateTime(str, outObj) {
	str = $.trim(str);
	outObj = outObj || {};
	var reg = /^(\d{4})(-)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	var result = str.match(reg);
	if (result == null) {
		outObj.errMsg = '日期时间格式形如 "yyyy-MM-dd hh:mm:ss"';
		return false;
	}
	var d = new Date(result[1], result[3] - 1, result[4], result[5], result[6],
			result[7]);
	if (d.getFullYear() == result[1] && (d.getMonth() + 1) == result[3]
			&& d.getDate() == result[4] && d.getHours() == result[5]
			&& d.getMinutes() == result[6] && d.getSeconds() == result[7]) {
		outObj.date = d;
		return true;
	} else {
		outObj.errMsg = '日期非法';
		return false;
	}
}

/**
 * 比较日期或日期时间字符串表示的日期大小，格式形如："yyyy-MM-dd" 或 "yyyy-MM-dd hh:mm:ss"
 * 
 * @param dateStr
 *            日期或日期时间字符串
 * @param dateStr2
 *            日期或日期时间字符串
 * @returns if 日期字符串不合法 返回{undefined} elseif dateStr>dateStr2 返回{1} elseif
 *          dateStr<dateStr2 返回{-1} elseif dateStr=dateStr2 返回{0}
 */
function compareDateString(dateStr, dateStr2) {
	var tempDate = {};
	var lTime, rTime;
	if (!isDate(dateStr, tempDate) && !isDateTime(dateStr, tempDate)) {
		return undefined;
	}
	lTime = tempDate.date.getTime();
	if (!isDate(dateStr2, tempDate) && !isDateTime(dateStr2, tempDate)) {
		return undefined;
	}
	rTime = tempDate.date.getTime();
	var code;
	if (lTime > rTime) {
		code = 1;
	} else if (lTime < rTime) {
		code = -1;
	} else {
		code = 0;
	}
	return code;
}

/**
 * 
 * @param value   
 * @param param  日期的id
 * @returns {Boolean}
 */
function _checkIdAndBirthday(value, param){

	if(param[0].type == 'cc'){
		//身份证的值
		var idcard = $('#' + param[0].id).textbox('getValue');
		if (checkIdAndBirthday(idcard, value)){
			return true;
		}else{
			return false;
		}		
	}	
	if(param[0].type == 'cb'){
		//查找生日日期的值
		var birthday = $('#' + param[0].id).datebox('getValue');
		if (checkIdAndBirthday(value, birthday)){
			return true;
		}else{
			return false;
		}		
	}	
}


/**
 * 比较身份证上的出生日期是否与所填写的生日一致
 * @param strId
 * @param strBrithday
 * @returns {Boolean}
 */
function checkIdAndBirthday(strId,strBrithday){ 
	
	if(strBrithday != ""){ 
		var arr_date = strBrithday.split("-"); 

		//15位身份证 
		if(strId.length == 15){ 
			//从ID NO 中截取生日6位数字，前面加上19 
			var idBirthday = "19"+strId.substr(6,6); 
			//日期字符串中的8位生日数字 
			var textBirthday = arr_date[0]+arr_date[1]+arr_date[2]; 
			if(idBirthday == textBirthday){ 
				return true; 
			}else{ 
				return false; 
			}               
		} 
		
		//18位身份证 
		if(strId.length == 18){ 
			//从ID NO 中截取生日8位数字 
			var idBirthday = strId.substr(6,8); 
			//日期字符串中的8位生日数字 
			var textBirthday = arr_date[0]+arr_date[1]+arr_date[2]; 
			if(idBirthday == textBirthday){ 
				return true; 
			}else{ 
				return false; 
			} 
		} 
    } 
	return true;
}



