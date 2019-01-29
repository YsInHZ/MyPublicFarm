package com.ys.administrator.mydemo.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
//    /**利用MD5进行加密
//     * @param str  待加密的字符串
//     * @return  加密后的字符串
//     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
//     * @throws UnsupportedEncodingException
//     */
//    public String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        //确定计算方法
//        MessageDigest md5=MessageDigest.getInstance("MD5");
//        BASE64Encoder base64en = new BASE64Encoder();
//        //加密后的字符串
//        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
//        return newstr;
//    }

    public static String encodeMd5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
