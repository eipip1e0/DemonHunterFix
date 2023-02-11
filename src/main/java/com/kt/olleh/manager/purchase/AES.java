package com.kt.olleh.manager.purchase;

import android.util.Log;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private Cipher mCipher;
    private IvParameterSpec mIv;
    private SecretKeySpec mKey;

    public AES(String key, String iv) throws Exception {
        if (key == null || "".equals(key)) {
            throw new NullPointerException("The key cannot be null or an empty string..");
        } else if (iv == null || "".equals(iv)) {
            throw new NullPointerException("The initial vector cannot be null or an empty string.");
        } else {
            this.mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            this.mKey = new SecretKeySpec(md5.digest(key.getBytes("UTF-8")), "AES");
            this.mIv = new IvParameterSpec(md5.digest(iv.getBytes("UTF-8")));
        }
    }

    public String encrypt(String value) throws Exception {
        if (value == null || "".equals(value)) {
            throw new NullPointerException("The cipher string cannot be null or an empty string.");
        }
        if (this.mIv == null || "".equals(this.mIv)) {
            this.mCipher.init(1, this.mKey);
        } else {
            this.mCipher.init(1, this.mKey, this.mIv);
        }
        return new String(encodeBase64(this.mCipher.doFinal(value.getBytes("UTF-8"))));
    }

    public String decrypt(String value) throws Exception {
        if (value == null || "".equals(value)) {
            throw new NullPointerException("The cipher string cannot be null or an empty string.");
        }
        if (this.mIv == null || "".equals(this.mIv)) {
            this.mCipher.init(2, this.mKey);
        } else {
            this.mCipher.init(2, this.mKey, this.mIv);
        }
        return new String(this.mCipher.doFinal(decodeBase64(value.getBytes())), "UTF-8");
    }

    public static byte[] encodeBase64(byte[] binaryData) {
        byte[] buf = null;
        try {
            Class<?> Base64 = Class.forName("org.apache.commons.codec.binary.Base64");
            return (byte[]) Base64.getMethod("encodeBase64", byte[].class).invoke(Base64, binaryData);
        } catch (Exception e) {
            Log.e("AES", e.toString(), e);
            return buf;
        }
    }

    public static byte[] decodeBase64(byte[] base64Data) {
        byte[] buf = null;
        try {
            Class<?> Base64 = Class.forName("org.apache.commons.codec.binary.Base64");
            return (byte[]) Base64.getMethod("decodeBase64", byte[].class).invoke(Base64, base64Data);
        } catch (Exception e) {
            Log.e("AES", e.toString(), e);
            return buf;
        }
    }
}
