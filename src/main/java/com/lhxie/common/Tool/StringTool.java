package com.lhxie.common.Tool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

public class StringTool {

	private StringTool() {

	}

	/**
	 * 判断字符串是否为空白
	 * 
	 * @param str
	 * @return
	 */
	@Deprecated
	public static boolean isEmpty(String str) {
		return isBlank(str);
	}

	/**
	 * 判断字符串是否为空白
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return (str == null || "".equals(str) || parseString(str).length() == 0);
	}

	/**
	 * 处理字符串
	 */
	public static String parseString(String str) {
		return str == null ? "" : str.trim();
	}

	/**
	 * @param obj
	 * @return if obj或obj的toString()为null 返回空字符串；<br>
	 *         else 返回obj的toString()去除前后空格后的字符串
	 */
	public static String trim(Object obj) {
		return obj == null || obj.toString() == null ? "" : obj.toString().trim();
	}

	/**
	 * @param html
	 * @return 去除所有html标签的字符串，if html==null return null.
	 */
	public static String removeHtmlTags(String html) {
		return null == html ? null : html.replaceAll("<([^>]*)>", "");
	}

	/**
	 * 支持中文的字符串比较
	 * 
	 * @param s1
	 * @param s2
	 * @return 正数：s1>s2；0：s1=s2；负数：s1<s2。具体数值为开始不相等时的位置（从1开始）。
	 * @author cfhuang
	 */
	public static int compareCN(String s1, String s2) {
		if (s1 == null && s2 == null)
			return 0;
		if (s2 == null)
			return 1;
		if (s1 == null)
			return -1;
		try {
			byte[] a1 = s1.getBytes("GB18030");
			byte[] a2 = s2.getBytes("GB18030");
			int rc = 0;
			for (int i = 0; i < a1.length; i++) {
				if (a2.length < i + 1) {
					rc = i + 1;
					break;
				}
				int n1 = a1[i] > 0 ? a1[i] : 256 + a1[i];
				int n2 = a2[i] > 0 ? a2[i] : 256 + a2[i];
				if (n1 > n2) {
					rc = i + 1;
					break;
				}
				if (n1 < n2) {
					rc = -i - 1;
					break;
				}
			}
			return rc;
		} catch (UnsupportedEncodingException e) {
			return 0;
		}
	}

	/**
	 * 将一个数组用分隔符连接起来。
	 * 
	 * @param items
	 *            - 数组
	 * @param split
	 *            - 分隔符，如果是null或""，则无分隔符。
	 * @return 连接后的字符串，如果数组是null，则返回""。
	 */
	public static String joinArray(String[] items, String split) {
		StringBuffer sb = new StringBuffer();
		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				if (i != 0 && split != null) {
					sb.append(split);
				}
				sb.append(items[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 将一个字符集合用分隔符连接起来。
	 * 
	 * @param items
	 *            - 数组
	 * @param split
	 *            - 分隔符，如果是null或""，则无分隔符。
	 * @return 连接后的字符串，如果数组是null，则返回""。
	 */
	public static String joinCollection(Collection<?> items, String split) {
		StringBuffer sb = new StringBuffer();
		if (items != null) {
			Iterator<?> iterator = items.iterator();
			for (int i = 0; i < items.size(); i++) {
				if (i != 0 && split != null) {
					sb.append(split);
				}
				sb.append(iterator.next());
			}
		}
		return sb.toString();
	}

	/**
	 * 把映射转换为列表。 列表元素为Map，键值分别为key和value。
	 * 
	 * @param map
	 * @return List
	 */
	public static List<Map<String, String>> map2List(Map<String, String> map) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(map.size());
		for (Map.Entry<String, String> entry : map.entrySet()) {
			Map<String, String> item = new HashMap<String, String>();
			item.put("key", entry.getKey());
			item.put("value", entry.getValue());
			list.add(item);
		}
		return list;
	}

	/**
	 * 判断字符串是不是全数字，用于整数
	 * 
	 * @param arg0
	 *            - 要判断的字符串
	 * @return 判断的结果
	 */
	public static boolean isNumeric(String str) {
		try {
			str = parseString(str);
			String regex = "^[0123456789]+$";
			if (str.matches(regex)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否为合法数字，可以是小数
	 * 
	 * @param arg0
	 *            - 要判断的字符串
	 * @return 判断的结果
	 */
	public static boolean isNumeric2(String str) {
		try {
			new Double(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 右补位，左对齐
	 * 
	 * @param oriStr
	 *            原字符串
	 * @param len
	 *            目标字符串长度
	 * @param alexin
	 *            补位字符
	 * @return 目标字符串
	 */
	public static String padRight(String oriStr, int len, char alexin) {
		String str = oriStr;
		int strlen = oriStr.length();
		if (strlen < len) {
			for (int i = 0; i < len - strlen; i++) {
				str += alexin;
			}
		}
		return str;
	}

	/**
	 * 左补位，右对齐
	 * 
	 * @param oriStr
	 *            原字符串
	 * @param len
	 *            目标字符串长度
	 * @param alexin
	 *            补位字符
	 * @return 目标字符串
	 */
	public static String padLeft(String oriStr, int len, char alexin) {
		String str = oriStr;
		int strlen = oriStr.length();
		if (strlen < len) {
			for (int i = 0; i < len - strlen; i++) {
				str = alexin + str;
			}
		}
		return str;
	}

	/**
	 * 判断字符创是否为日期 格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

	private static final char[] HEX_DIGITAL = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * MD5加密字符串
	 * 
	 * @param encodingString
	 *            要加密的字符串
	 * @return 加密过后的16进制小写字符串，出现异常返回null。
	 */
	public static final String toMD5HexString(String encodingString) {
		return encodeToHexString(encodingString, "MD5");
	}

	/**
	 * SHA1加密字符串
	 * 
	 * @param encodingString
	 *            要加密的字符串
	 * @return 加密过后的16进制小写字符串，加密过程中出现异常返回null。
	 */
	public static final String toSHA1HexString(String encodingString) {
		return encodeToHexString(encodingString, "SHA1");
	}

	/**
	 * 把字节数组bytes转换成十六进制表示的小写字符串
	 * 
	 * @param bytes
	 * @return String
	 */
	public static String toHexString(byte[] bytes) {
		if (bytes == null || bytes.length < 1) {
			return null;
		}
		int len = bytes.length;
		StringBuilder sb = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			sb.append(HEX_DIGITAL[(bytes[j] >> 4) & 0x0f]);
			sb.append(HEX_DIGITAL[bytes[j] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * 使用method方法加密字符串encodingString
	 * 
	 * @param encodingString
	 *            要加密的字符串
	 * @param method
	 *            使用的加密方法，如：MD5、SHA1
	 * @return null or 加密后的16进制小写字符串
	 */
	private static String encodeToHexString(String encodingString, String method) {
		String result = null;
		try {
			if (encodingString == null || method == null) {
				throw new Exception("要加密的字符串或加密方法为空");
			}
			MessageDigest messageDigest = MessageDigest.getInstance(method);
			messageDigest.update(encodingString.getBytes());
			byte[] bytes = messageDigest.digest();
			result = toHexString(bytes);
		} catch (Exception e) {
			result = null;
			throw new RuntimeException("[" + method + "]加密[" + encodingString + "]时异常: " + e.getMessage());
		}
		return result;
	}

	/**
	 * 转义str中的特殊字符，以适合传入SQL语句中。对like子句中用到的‘%’和‘_’不做处理。<br>
	 * eg: statement.executeQuery("SELECT * FROM MOVIES WHERE TITLE='" +
	 * StringEscapeUtils.escapeSql("McHale's Navy") + "'");<br>
	 * 此例中使单引号变为两个单引号("McHale's Navy" => "McHale''s Navy")，以免SQL报错。
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeSql(String str) {
		return StringEscapeUtils.escapeSql(str);
	}

	/**
	 * 格式化文件大小, 输出成带单位的字符串
	 * 
	 * @param size
	 *            文件大小
	 * @param 精确到的小数点数
	 *            ，默认2位。
	 * @example formatSize( 100 ); // => 100B<br>
	 *          formatSize( 1024 ); // => 1.00K<br>
	 *          formatSize( 1024, 0 ); // => 1K<br>
	 *          formatSize( 1024 * 1024 ); // => 1.00M<br>
	 *          formatSize( 1024 * 1024 * 1024 ); // => 1.00G
	 * @return
	 */
	public static String formatSize(double size, int pointLength) {
		String[] units = { "B", "K", "M", "G", "TB" };
		int unit = 0;
		double sizeInput = size;
		while (size > 1024) {
			size = size / 1024;
			unit++;
		}
		if (unit > 4) {
			unit = 0;
			size = sizeInput;
		}
		String template = "%." + (pointLength < 0 ? 0 : pointLength) + "f%s";
		return String.format(template, size, units[unit]);
	}

	public static String formatSize(double size) {
		return formatSize(size, 2);
	}

	public static void main(String[] args) {
		System.out.println(StringTool.encodeToHexString("123456", "MD5"));
		if(StringTool.encodeToHexString("abc", "MD5").equals("900150983cd24fb0d6963f7d28e17f72")) {
			System.out.println("a");
		}
	}
}
