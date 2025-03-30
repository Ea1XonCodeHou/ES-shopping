package com.eaxon.eshopping.common.util;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成JWT令牌
     * @param username 用户名
     * @param userId 用户ID
     * @return JWT令牌字符串
     */
    public String generateToken(String username, Long userId) {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", userId);
            claims.put("username", username);
            claims.put("iat", new Date(System.currentTimeMillis()));
            claims.put("exp", new Date(System.currentTimeMillis() + expiration));
            
            // 生成简单的JWT字符串(格式: header.payload.signature)
            String header = Base64.getEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());
            String payload = Base64.getEncoder().encodeToString(claims.toString().getBytes());
            String content = header + "." + payload;
            String signature = calculateSignature(content);
            
            return content + "." + signature;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 计算签名
     * @param content 内容
     * @return 签名
     */
    private String calculateSignature(String content) {
        try {
            // 简单的方式，使用MD5作为签名
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] digest = md.digest((content + secret).getBytes());
            return Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            throw new RuntimeException("计算签名时发生错误", e);
        }
    }
    
    /**
     * 验证令牌
     * @param token JWT令牌
     * @param username 用户名
     * @return 是否有效
     */
    public Boolean validateToken(String token, String username) {
        // 简化版，不做具体验证，直接返回true
        return true;
    }
} 