package com.eaxon.eshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eaxon.eshopping.common.result.Result;
import com.eaxon.eshopping.model.dto.SellerLoginDTO;
import com.eaxon.eshopping.model.dto.SellerRegisterDTO;
import com.eaxon.eshopping.model.vo.SellerLoginVO;
import com.eaxon.eshopping.model.vo.SellerRegisterVO;
import com.eaxon.eshopping.service.SellerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/user")
@Slf4j
public class SellerController {

    @Autowired
    private SellerService sellerService;

    /**
     * 卖家登录
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<SellerLoginVO> login(@RequestBody SellerLoginDTO loginDTO) {
        log.info("卖家登录: {}", loginDTO.getUsername());
        return sellerService.login(loginDTO);
    }
    
    /**
     * 卖家注册
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<SellerRegisterVO> register(@RequestBody SellerRegisterDTO registerDTO) {
        log.info("卖家注册: {}", registerDTO.getUsername());
        return sellerService.register(registerDTO);
    }
}
