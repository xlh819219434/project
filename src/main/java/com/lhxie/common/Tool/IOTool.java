package com.lhxie.common.Tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class IOTool {

	/** 
     * 从对象获取一个字节数组  
     */   
    public static byte[] getBytesFromObject(Serializable obj) {
		if (obj == null) {
			return null;
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(os);
			out.writeObject(obj);
			byte[] bytes = os.toByteArray();
			os.reset();
			out.reset();
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
    
    /**  
     * 从字节数组获取对象  
     */   
    public static Object getObjectFromBytes(byte[] objBytes) {   
        if (objBytes == null || objBytes.length == 0) {   
            return null;   
        }   
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);   
        ObjectInputStream oi;
        Object object = null;
		try {
			oi = new ObjectInputStream(bi);
			object = oi.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		bi.reset();
        return object;   
    }   

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    private static long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        return copyLarge(input, output, new byte[4096]);//大小为4096
    }

    private static long copyLarge(InputStream input, OutputStream output, byte[] buffer)
            throws IOException {
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    
}
