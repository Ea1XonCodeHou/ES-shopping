package com.eaxon.eshopping.model.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SellerLoginVO implements Serializable {
    
    private Long id;
    private String username;
    private String name;
    private String token;
    private Integer level;
    
    // 简化构造函数
    public SellerLoginVO(Long id, String username, String name, String token) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.token = token;
    }
}
