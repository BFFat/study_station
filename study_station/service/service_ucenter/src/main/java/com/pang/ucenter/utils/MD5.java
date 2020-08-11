package com.pang.ucenter.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.apache.poi.util.HexDump.toHex;

public class MD5 {
    public static String encrypt(String password) {
        String passwordMd5 = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes("utf-8"));
            passwordMd5 = toHex(bytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return passwordMd5;
    }
}
