package com.eaxon.eshopping.model.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerRegisterVO implements Serializable {

    private Long id;
    private String username;
    private String name;

}
