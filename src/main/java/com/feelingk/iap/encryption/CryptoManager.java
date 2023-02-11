package com.feelingk.iap.encryption;

import com.feelingk.iap.util.Defines;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoManager {
    private static final String HEX = "0123456789ABCDEF";
    private static final String innerKey = "ahqkdlfdhvltm000";

    public static String encrypt(String cleartext) throws Exception {
        return encrypt(innerKey, cleartext);
    }

    public static String decrypt(String encrypted) throws Exception {
        return decrypt(innerKey, encrypted);
    }

    public static String encrypt(String key, String cleartext) throws Exception {
        return toHex(encrypt(key.getBytes(), cleartext.getBytes()));
    }

    public static String decrypt(String key, String encrypted) throws Exception {
        return new String(decrypt(key.getBytes(), toByte(encrypted)));
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, skeySpec);
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, skeySpec);
        return cipher.doFinal(encrypted);
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(i * 2, (i * 2) + 2), 16).byteValue();
        }
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(buf.length * 2);
        for (byte b : buf) {
            appendHex(result, b);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 15)).append(HEX.charAt(b & 15));
    }

    public String md5(String cleartext) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(cleartext.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (byte b : messageDigest) {
                hexString.append(Integer.toHexString(b & Defines.IAP_GATEWAY_RESPONSE.IAP_ERR_ALREADYCONNECTED));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] getInitialVector(byte[] keyBytes) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.reset();
        md5.update(keyBytes);
        return md5.digest();
    }

    public static byte[] getKey(byte[] initialVector) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.reset();
        md5.update(initialVector);
        return md5.digest();
    }

    public static byte[] encript(byte[] value, byte[] key, byte[] initialVetor) {
        byte[] encryptedText = null;
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(1, new SecretKeySpec(key, "AES"), new IvParameterSpec(initialVetor));
            return c.doFinal(value);
        } catch (Exception e) {
            e.printStackTrace();
            return encryptedText;
        }
    }

    public static byte[] decript(byte[] value, byte[] key, byte[] initialVecor) {
        byte[] encryptedText = null;
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(2, new SecretKeySpec(key, "AES"), new IvParameterSpec(initialVecor));
            return c.doFinal(value);
        } catch (Exception e) {
            e.printStackTrace();
            return encryptedText;
        }
    }
}
