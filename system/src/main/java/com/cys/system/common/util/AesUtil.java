package com.cys.system.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

/**
 * aes加密工具
 */
public class AesUtil {

    private static final String ALGORITHM = "AES";

    private static final byte[] key = "cf97e25120dbfc55".getBytes(Charset.forName("GB2312"));

    /**
     * Encrypts the given plain text
     *
     * @param text The plain text to encrypt
     */
    public static String encrypt(String text) throws Exception {
//		byte[] plainText = text.getBytes();
//		SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
//		Cipher cipher = Cipher.getInstance(ALGORITHM);
//		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//
//		return new String(cipher.doFinal(plainText));
        return text;
    }

    /**
     * Decrypts the given byte array
     *
     * @param text The data to decrypt
     */
    public static String decrypt(String text) throws Exception {
//		byte[] cipherText = text.getBytes();
//		SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
//		Cipher cipher = Cipher.getInstance(ALGORITHM);
//		cipher.init(Cipher.DECRYPT_MODE, secretKey);
//
//		return new String(cipher.doFinal(cipherText));
        return text;
    }
}
