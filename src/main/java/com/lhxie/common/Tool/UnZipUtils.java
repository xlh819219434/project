package com.lhxie.common.Tool;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
public class UnZipUtils {
	/**使用GBK编码可以避免压缩中文文件名乱码*/
    private static final String CHINESE_CHARSET = "GBK";
    /**文件读取缓冲区大小*/
    private static final int CACHE_SIZE = 1024;
	
	/**
     * 解压压缩包
     * @param zipFilePath 压缩文件路径
     * @param destDir 解压目录
     */
    public static void unZipCH(String zipFilePath, String destDir) {
        ZipFile zipFile = null;
        try {
            BufferedInputStream bis = null;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            zipFile = new ZipFile(zipFilePath, CHINESE_CHARSET);
            @SuppressWarnings("unchecked")
			Enumeration<ZipEntry> zipEntries = zipFile.getEntries();
            File file, parentFile;
            ZipEntry entry;
            byte[] cache = new byte[CACHE_SIZE];
            while (zipEntries.hasMoreElements()) {
                entry = (ZipEntry) zipEntries.nextElement();
                if (entry.isDirectory()) {
                	//System.out.println("1====="+(destDir + "\\" + entry.getName())+"   "+entry.getName());
                    new File(destDir + "\\" + entry.getName()).mkdirs();
                    continue;
                }
                bis = new BufferedInputStream(zipFile.getInputStream(entry));
                //System.out.println("2====="+(destDir + "\\" + entry.getName())+"   "+entry.getName());
                file = new File(destDir + "\\" + entry.getName());
                parentFile = file.getParentFile();
                //System.out.println("3====="+parentFile);
                if (parentFile != null && (!parentFile.exists())) {
                    parentFile.mkdirs();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, CACHE_SIZE);
                int readIndex = 0;
                while ((readIndex = bis.read(cache, 0, CACHE_SIZE)) != -1) {
                    fos.write(cache, 0, readIndex);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                zipFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	public static void main(String[] args) {
		try {
			UnZipUtils.unZipCH("d:\\workphoto.zip", "d:\\workphoto");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
