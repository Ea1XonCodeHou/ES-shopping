package com.eaxon.eshopping.service;

import com.eaxon.eshopping.common.result.Result;
import com.eaxon.eshopping.model.dto.SellerLoginDTO;
import com.eaxon.eshopping.model.dto.SellerRegisterDTO;
import com.eaxon.eshopping.model.vo.SellerLoginVO;
import com.eaxon.eshopping.model.vo.SellerRegisterVO;

public interface SellerService {
    
    /**
     * 卖家登录
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    Result<SellerLoginVO> login(SellerLoginDTO loginDTO);
    
    /**
     * 卖家注册
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    Result<SellerRegisterVO> register(SellerRegisterDTO registerDTO);
}
