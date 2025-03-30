package com.eaxon.eshopping.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    /**
     * 使用MD5加密密码
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public String encode(String rawPassword) {
        try {
            // 创建MessageDigest实例用于MD5加密
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 加密处理
            byte[] digest = md.digest(rawPassword.getBytes());
            // 转换为Base64编码并返回
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("加密密码时发生错误", e);
        }
    }

    /**
     * 验证密码
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        // 对输入的密码进行加密，然后与已加密的密码比较
        return encode(rawPassword).equals(encodedPassword);
    }
} 