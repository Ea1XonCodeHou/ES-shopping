package com.eaxon.eshopping.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SellerLoginDTO implements Serializable { 
    private String username;
    private String password;
}
