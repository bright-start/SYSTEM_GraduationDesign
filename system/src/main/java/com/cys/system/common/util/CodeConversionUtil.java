package com.cys.system.common.util;

public class CodeConversionUtil {
    public static String byteToHex(byte[] bytes){
        StringBuilder desc = new StringBuilder();
        String tmp = null;
        for (int i = 0; i < bytes.length; i++) {
            tmp = (Integer.toHexString(bytes[i] & 0xFF));
            if(tmp.length() == 1){
                tmp += "0";
            }
            desc.append(tmp);
        }
        return desc.toString();
    }
}
