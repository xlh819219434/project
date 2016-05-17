package com.lhxie.common.Tool;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class HttpUtil {

	public final static String CHARSET = "UTF-8";
	private final static String TWO_HYPENS = "--";
    private final static String BOUNDARY = "------^ha8wi`ynzg`";
    private final static String CRLF = "\r\n";
    
    private static Logger loggerError = Logger.getLogger(HttpUtil.class);
    
    public static String upload(String url, Map<String, String> postParams, Map<String, String> headers0,
    		String filePath, String contentType) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		InputStream fileStream = null;
		try {
			fileStream = new FileInputStream(file);
			return upload(url, postParams, headers0, file.getName(), contentType, file.length(), fileStream);
		} catch (FileNotFoundException e) {
			loggerError.error(e.getMessage(), e);
		} finally {
			if (fileStream != null) {
				try {
					fileStream.close();
				} catch (IOException e) {
					loggerError.error(e.getMessage(), e);
				}
			}
		}
		return null;
    }

	public static String upload(String url, Map<String, String> postParams, Map<String, String> headers0,
			String fileName, String contentType, long fileLength, InputStream fileStream) throws IOException {
		List<String> fileNames = new ArrayList<String>(1);
		fileNames.add(fileName);
		List<String> contentTypes = new ArrayList<String>(1);
		contentTypes.add(contentType);
		List<Long> fileLengths = new ArrayList<Long>(1);
		fileLengths.add(fileLength);
		List<InputStream> fileStreams = new ArrayList<InputStream>(1);
		fileStreams.add(fileStream);
		return upload(url, postParams, headers0, fileNames, contentTypes, fileLengths, fileStreams);
	}

	public static String upload(String url, Map<String, String> postParams, Map<String, String> headers0,
			List<String> fileNames, List<String> contentTypes, List<Long> fileLengths,
			List<InputStream> fileStreams) throws IOException {
		long totalLen = 0;
		// 参数
		List<byte[]> paramList = null;
		if (postParams != null && postParams.size() > 0) {
			paramList = new ArrayList<byte[]>(postParams.size());
			for (Map.Entry<String, String> tmp : postParams.entrySet()) {
				try {
					StringBuilder sb = new StringBuilder();
					sb.append(TWO_HYPENS + BOUNDARY + CRLF);
					sb.append("Content-Disposition: form-data; name=\"").append(tmp.getKey())
							.append("\"" + CRLF);
					sb.append(CRLF);
					sb.append(tmp.getValue()).append(CRLF);
					byte[] param = sb.toString().getBytes(CHARSET);
					paramList.add(param);
					totalLen += param.length;
				} catch (java.io.UnsupportedEncodingException neverHappen) {
				}
			}
		}

		// 文件
		int index = 0;
		List<byte[]> fileParamList = new ArrayList<byte[]>(fileNames.size());
		for (String fileName : fileNames) {
			StringBuilder sb = new StringBuilder();
			sb.append(TWO_HYPENS + BOUNDARY + CRLF);
			sb.append("Content-Disposition: form-data; name=\"file").append(index == 0? "" : index).append("\";filename=\"")
					.append(fileName).append("\"").append(CRLF);
			sb.append("Content-Type:").append(contentTypes.get(index)).append(CRLF);
			sb.append(CRLF);
			byte[] fileParam = null;
			try {
				fileParam = sb.toString().getBytes(CHARSET);
			} catch (UnsupportedEncodingException neverHappen) {
			}
			fileParamList.add(fileParam);
			totalLen += fileParam.length + fileLengths.get(index) + 2;
			index++;
		}

		byte[] end = null;
		try {
			end = (TWO_HYPENS + BOUNDARY + TWO_HYPENS + CRLF).getBytes(CHARSET);
			totalLen += end.length;
		} catch (UnsupportedEncodingException neverHappen) {
		}

		Map<String, String> headers = new LinkedHashMap<String, String>();
		headers.putAll(headers0);
		headers.put("Connection", "close");
		headers.put("Cache-Control", "cache");
		headers.put("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
		headers.put("Content-Length", Long.toString(totalLen));

		HttpURLConnection con = null;
		OutputStream osw = null;
		InputStream ins = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod("POST");
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> tmp : headers.entrySet()) {
					con.setRequestProperty(tmp.getKey(), tmp.getValue());
				}
			}

			con.setDoOutput(true);
			osw = con.getOutputStream();
			// 参数
			if (null != paramList) {
				for (byte[] param : paramList) {
					osw.write(param);
				}
			}
			// 文件
			index = 0;
			byte[] readBuffer = new byte[4096];
			for (byte[] fileParam : fileParamList) {
				osw.write(fileParam);

				InputStream inStream = fileStreams.get(index);
				int len;
				while ((len = inStream.read(readBuffer)) > 0) {
					osw.write(readBuffer, 0, len);
				}
				inStream.close();
				osw.write(CRLF.getBytes(CHARSET));
			}
			osw.write(end);
			osw.flush();

			int resCode = con.getResponseCode();
			if (resCode < 400) {
				ins = con.getInputStream();
			} else {
				ins = con.getErrorStream();
				if (headers != null) {
					headers.put("__ERROR","__ERROR");
				}
			}
			return readContent(ins);

		} finally {

			try {
				if (osw != null) {
					osw.close();
				}
			} catch (IOException e) {
				loggerError.error(e.getMessage(), e);
			}
			try {
				if (ins != null) {
					ins.close();
				}
			} catch (IOException e) {
				loggerError.error(e.getMessage(), e);
			}
		}
	}
	
	public static String post(String url, Map<String, String> postParams) throws IOException {
		return post(url, postParams, null);
	}
	
	public static String post(String url, Map<String, String> postParams, Map<String, String> headers) throws IOException {
		HttpURLConnection con = null;
		OutputStream osw = null;
		InputStream ins = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setDoInput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> tmp : headers.entrySet()) {
					con.setRequestProperty(tmp.getKey(), tmp.getValue());
				}
			}
			if (null != postParams) {
				con.setDoOutput(true);
				String postParam = encodeParameters(postParams);
				byte[] bytes = postParam.getBytes(CHARSET);

				con.setRequestProperty("Content-Length", Integer.toString(bytes.length));

				osw = con.getOutputStream();
				osw.write(bytes);
				osw.flush();
			}

			int resCode = con.getResponseCode();
			if (resCode < 400) {
				ins = con.getInputStream();
			} else {
				ins = con.getErrorStream();
				if (headers != null) {
					headers.put("__ERROR","__ERROR");
				}
			}
			return readContent(ins);
		
		} finally {
			closeStream(osw);
			closeStream(ins);
		}
	}
	
	//关闭流
	private static void closeStream(Closeable stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException e) {
			loggerError.error(e.getMessage(), e);
		}
	}
	
	public static String put(String url, Map<String, String> postParams) throws IOException {
		return put(url, postParams, null);
	}
	
	public static String put(String url, Map<String, String> postParams, Map<String, String> headers) throws IOException {
		HttpURLConnection con = null;
		OutputStream osw = null;
		InputStream ins = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setDoInput(true);
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> tmp : headers.entrySet()) {
					con.setRequestProperty(tmp.getKey(), tmp.getValue());
				}
			}
			if (null != postParams) {
				con.setDoOutput(true);
				String postParam = encodeParameters(postParams);
				byte[] bytes = postParam.getBytes(CHARSET);
				
				con.setRequestProperty("Content-Length", Integer.toString(bytes.length));
				
				osw = con.getOutputStream();
				osw.write(bytes);
				osw.flush();
			}
			
			int resCode = con.getResponseCode();
			if (resCode < 400) {
				ins = con.getInputStream();
			} else {
				ins = con.getErrorStream();
				if (headers != null) {
					headers.put("__ERROR","__ERROR");
				}
			}
			return readContent(ins);
		
		} finally {
			closeStream(osw);
			closeStream(ins);
		}
	}

	public static String get(String url) throws IOException {
		return get(url, null);
	}
	
	public static String get(String url, Map<String, String> headers) throws IOException {
		//对url进行编码，替换特殊字符，比如空格
		url = url.replaceAll(" ", "%20");

		HttpURLConnection con = null;
		InputStream ins = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod("GET");
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> tmp : headers.entrySet()) {
					con.setRequestProperty(tmp.getKey(), tmp.getValue());
				}
			}
			int resCode = con.getResponseCode();
			if (resCode < 400) {
				ins = con.getInputStream();
			} else {
				ins = con.getErrorStream();
				if (headers != null) {
					headers.put("__ERROR","__ERROR");
				}
			}
			return readContent(ins);
		} finally {
			closeStream(ins);
		}
	}
	
	public static byte[] getBytes(String url, Map<String, String> headers) throws IOException {
		HttpURLConnection con = null;
		InputStream ins = null;
		
		//对url进行编码，替换特殊字符，比如空格
		url = url.replaceAll(" ", "%20");
		
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod("GET");
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> tmp : headers.entrySet()) {
					con.setRequestProperty(tmp.getKey(), tmp.getValue());
				}
			}
			int resCode = con.getResponseCode();
			if (resCode < 400) {
				ins = con.getInputStream();
				return readBytes(ins);
			} else {
				ins = con.getErrorStream();
				if (headers != null) {
					headers.put("__ERROR", readContent(ins));
				}
				return null;
			}
		} finally {
			closeStream(ins);
		}
	}
	
	public static String delete(String url) throws IOException {
		return delete(url, null);
	}
	
	public static String delete(String url, Map<String, String> headers) throws IOException {
		HttpURLConnection con = null;
		OutputStream osw = null;
		InputStream ins = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod("DELETE");
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> tmp : headers.entrySet()) {
					con.setRequestProperty(tmp.getKey(), tmp.getValue());
				}
			}
			int resCode = con.getResponseCode();
			if (resCode < 400) {
				ins = con.getInputStream();
			} else {
				ins = con.getErrorStream();
				if (headers != null) {
					headers.put("__ERROR","__ERROR");
				}
			}
			return readContent(ins);

		} finally {
			closeStream(osw);
			closeStream(ins);
		}
	}
	
	private static byte[] readBytes(InputStream is) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		IOTool.copy(is, os);
		byte[] bytes = os.toByteArray();
		closeStream(os);
		return bytes;
	}

	private static String readContent(InputStream ins) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStreamReader isr = new InputStreamReader(ins, CHARSET);
		BufferedReader br = new BufferedReader(isr);
		if (ins != null) {
			String line;
			Pattern p = Pattern.compile("(?i)\\\\u([\\da-f]{4})");
			while ((line = br.readLine()) != null) {
				StringBuffer sbLine = new StringBuffer();
				Matcher m = p.matcher(line);
				while(m.find()) {
				    m.appendReplacement(sbLine, Character.toString((char)Integer.parseInt(m.group(1), 16)));
				}
				m.appendTail(sbLine);
				line = sbLine.toString();
				sb.append(line);
			}
		}
		closeStream(br);
		closeStream(isr);
		return sb.toString();
	}

	public static String encodeParameters(Map<String, String> postParams) {
		StringBuilder buf = new StringBuilder();
		if (postParams != null && postParams.size() > 0) {

			for (Map.Entry<String, String> tmp : postParams.entrySet()) {
				try {
					buf.append(URLEncoder.encode(tmp.getKey(), CHARSET)).append("=")
							.append(URLEncoder.encode(tmp.getValue(), CHARSET)).append("&");
				} catch (java.io.UnsupportedEncodingException neverHappen) {
				}
			}

			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}
	
	/**
	 * 判断是否IE浏览器
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isMSIE(HttpServletRequest request) {
		// 判断是否IE
		String ua = request.getHeader("User-Agent");
		return ua != null && (ua.indexOf("MSIE") != -1 || ua.indexOf("rv:11") != -1 || ua.indexOf("Edge") != -1);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String url = "5625a695b23469250c92150f_续订劳动合同模板 (7).xls";
		System.out.println(URLEncoder.encode(url, CHARSET).replaceAll("\\+", "%20"));
	}
}
