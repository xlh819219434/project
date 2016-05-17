package com.lhxie.common.Tool;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
public class ZipUtils {
	/**
	 * 压缩包解压
	 * 
	 * @param pathName
	 *            压缩包路径
	 * @param dirPath
	 *            要解压到的路径
	 * @throws Exception
	 *             异常
	 */
	public static void unZip(String pathName, String dirPath) throws Exception {
		// 创建压缩文件
		@SuppressWarnings("resource")
		ZipFile zf = new ZipFile(pathName);
		Enumeration<?> enu = zf.entries();
		while (enu.hasMoreElements()) {
			ZipEntry ze = (ZipEntry) enu.nextElement();
			String name = ze.getName();
			String fullname = dirPath + "/" + name;
			String path = fullname.substring(0, fullname.lastIndexOf('/') + 1);

			File file = new File(path);
			if (!file.exists()){
				file.mkdirs();
			}
			file = new File(fullname);
			if (!name.endsWith("/")) {
				InputStream is = zf.getInputStream(ze);
				OutputStream os = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					os.write(bytes, 0, len);
				}
				os.close();
				is.close();
			}
		}
	}

	public static void main(String[] args) {
		try {
			ZipUtils.unZip("d:\\workphoto.zip", "d:\\workphoto");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
