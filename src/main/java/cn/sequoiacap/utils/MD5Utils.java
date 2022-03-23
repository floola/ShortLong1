package cn.sequoiacap.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.StringUtils;



public class MD5Utils {
    /**
     * 对字符串进行Md5加密
     *
     * @param input 原文
     * @return md5后的密文
     */
    public static String md5(String input) {
        byte[] code = null;
        try {
            code = MessageDigest.getInstance("md5").digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            code = input.getBytes();
        }
        BigInteger bi = new BigInteger(code);
        return bi.abs().toString(32).toUpperCase();
    }

}