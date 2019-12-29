package com.cys.system.common.util;

import java.io.*;

public class FileUtil {

    public static byte[] getBytesByFile(File file) throws IOException {
        return getBytesByFile(new FileInputStream(file));
    }

    //将文件转换成Byte数组
    public static byte[] getBytesByFile(InputStream fis) throws IOException {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[fis.available()];
            fis.read(b);
            bos.write(b);
            byte[] data = bos.toByteArray();
            return data;
        } finally {
            fis.close();
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    throw new IOException(e);
                }
            }
        }
    }

    //将Byte数组转换成文件
    public static void getFileByBytes(byte[] bytes, String filePath, String fileName) throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            bos.flush();
            bos.toString();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    throw new IOException(e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new IOException(e);
                }
            }
        }
    }
}

