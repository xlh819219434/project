package com.lhxie.common.Tool;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类（包含一些常用的方法）
 */
public class Tool {

	private Tool() {

	}
	
	/**
	 * 判断字符串是否为空
	 * @param s
	 * @return
	 */
	public static boolean isNull(String str) {
		return (str == null || "".equals(str) || Tool.parseString(str).length() == 0) ? true : false;

	}
	
	/**
	 * 转换字符串为非空值
	 * @param str - 要处理的字符串
	 * @return 若str为空(null)则返回"",否则原值
	 */
	public static String notNull(String str) {
		return str == null  || Tool.parseString(str).length() == 0 ? "" : str;
	}

	/**
	 * 处理字符串
	 * @param str - 要处理的字符串
	 * @return 若str为空(null)则返回"",否则返回arg0除去前后空格之后的值
	 */
	public static String parseString(String str) {
		return str == null ? "" : str.trim();
	}

	/**
	 * 处理字符串
	 * @param obj - 要处理的对象
	 * @return 若obj为空(null)则返回"",否则返回obj转换成字符串且除去该字符前后空格之后的值
	 */
	public static String parseString(Object obj) {
		return obj == null ? "" : obj.toString().trim();
	}

	/**
	 * 生成随机主键（48位）
	 * @return
	 */
	public static long createId() {
		UUID uuid = UUID.randomUUID();
		return (uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits()) << 16 >>> 16;
	}
	
	/**
	 * 匹配字符串是否为数字
	 * @param args -要处理的对象
	 * @return 若只有数字返回true,否则false.
	 * */
	public static boolean matchDigital(String args){
		if (args == null || "".equals(args)) {
			return false;
		}
		Pattern p = Pattern.compile("\\d*");
		Matcher matcher = p.matcher(args);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
        
	}
	
	//检查是不是图片
	public static boolean checkIsImage(String imgStr) {
		boolean flag = false;
		if (imgStr != null) {
			if (imgStr.equalsIgnoreCase(".gif")
					|| imgStr.equalsIgnoreCase(".jpg")
					|| imgStr.equalsIgnoreCase(".jpeg")
					|| imgStr.equalsIgnoreCase(".png")) {
				flag = true;
			}
		}
		return flag;
	}


}