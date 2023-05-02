package com.liuscraft.tradingplatform.utils;

import java.util.Random;

/**
 * @author LiusCraft
 * @DateTime 2023/4/30 21:43
 */
public class StrUtils {
    public static String random(int len) {
        // 可选字符集合
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(len);
        Random rnd = new Random();
        for (int i = 0; i < len; i++) {
            // 随机生成字符或数字的下标
            int index = rnd.nextInt(chars.length());
            // 根据下标取出字符或数字
            char c = chars.charAt(index);
            // 拼接生成的字符或数字
            sb.append(c);
        }
        return sb.toString();
    }
}
