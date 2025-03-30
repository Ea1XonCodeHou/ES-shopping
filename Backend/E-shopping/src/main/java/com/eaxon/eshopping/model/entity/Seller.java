package com.eaxon.eshopping.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Seller implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String username;
    
    @JsonIgnore // 防止密码在JSON序列化时泄露
    private String password;
    
    private String name;
    private String phone;
    private String email;
    private String avatar;
    private String shopName;
    private String shopLogo;
    private String shopDesc;
    private String identityCard;
    private String address;
    private Integer level;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;
}
