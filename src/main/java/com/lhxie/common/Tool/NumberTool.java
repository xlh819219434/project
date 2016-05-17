package com.lhxie.common.Tool;

import java.text.DecimalFormat;

public class NumberTool {

	private NumberTool() {
		
	}
	
	/**
	 * 数字格式化，以逗号分隔
	 * @param number - 数字，整数或小数
	 * @param scale - 小数位数，如果截短，则四舍五入。
	 * @param scaleFixed - 小数位数是否固定，如果固定，则当位数不够时在右边以0补足，否则只保留必要的位数。
	 * @return 返回百分比化之后的字符串
	 */
	public static String format(Number number, int scale, boolean scaleFixed){
		if (number == null) {
			return "";
		}
		StringBuilder sbFmt = new StringBuilder("###,###,###,###,###.");
		for (int i = 0; i < scale; i++) {
			if (scaleFixed) {
				sbFmt.append("0");
			} else {
				sbFmt.append("#");
			}
		}
		return new DecimalFormat(sbFmt.toString()).format(number);
	}
	
	/**
	 * 数字转换为金额形式（保留两位小数）
	 * @param number
	 * @return
	 */
	public static String toMoney(Number number) {
		return format(number, 2, true);
	}

    /**
	 * 数字转换为百分比
	 * @param number - 数字，整数或小数
	 * @param scale - 小数位数，如果截短，则四舍五入。
	 * @param scaleFixed - 小数位数是否固定，如果固定，则当位数不够时在右边以0补足，否则只保留必要的位数。
	 * @return 返回百分比化之后的字符串
	 */
	public static String toPercent(Number number, int scale, boolean scaleFixed){
		if (number == null) {
			return "";
		}
		StringBuilder sbFmt = new StringBuilder("###,###,###,###,###.");
		for (int i = 0; i < scale; i++) {
			if (scaleFixed) {
				sbFmt.append("0");
			} else {
				sbFmt.append("#");
			}
		}
		return new DecimalFormat(sbFmt.append("%").toString()).format(number);
	}
	
	public static void main(String[] args) {
//		System.out.println(percent(new BigDecimal("13134.2011")));
//		System.out.println(format(new BigDecimal("13134.20151"), 3, true));
	}

}
