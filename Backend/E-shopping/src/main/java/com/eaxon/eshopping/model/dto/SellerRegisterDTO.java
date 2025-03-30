package com.eaxon.eshopping.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SellerRegisterDTO implements Serializable {
    private String username;
    private String password;
    private String phone;
    private String email;
}
