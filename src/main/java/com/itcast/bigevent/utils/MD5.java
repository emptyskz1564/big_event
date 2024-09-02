package com.itcast.bigevent.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    /**
     * MD5加密 通过将盐值和密码进行加密，进行混淆
     * 登录的时候就通过密码和数据库中的盐值进行拼接 进行MD5加密后的结果是否相同
     * @param context
     */
    public static String encrypByMd5(String context) {
        try {
            // 获取一个MD5消息摘要实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 更新消息摘要，将输入的文本内容转换为字节数组并进行处理
            md.update(context.getBytes());

            // 计算消息摘要，得到MD5散列值
            byte[] encryContext = md.digest();

            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < encryContext.length; offset++) {
                // 将字节值转换为无符号整数
                i = encryContext[offset];
                if (i < 0) {
                    i += 256;  // 处理负值
                }
                if (i < 16) {
                    buf.append("0");  // 补充前导0，以保证每个字节都被表示为两位十六进制数
                }
                buf.append(Integer.toHexString(i));  // 将字节值转换为十六进制字符串并追加到结果字符串
            }

            // 返回MD5散列值的十六进制表示
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            // 处理NoSuchAlgorithmException异常，通常是因为指定的MD5算法不可用
            e.printStackTrace();
            return null;
        }
    }
}
