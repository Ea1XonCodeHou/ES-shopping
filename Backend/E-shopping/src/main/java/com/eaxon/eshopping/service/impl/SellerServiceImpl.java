package com.eaxon.eshopping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eaxon.eshopping.common.result.Result;
import com.eaxon.eshopping.common.util.JwtUtil;
import com.eaxon.eshopping.common.util.PasswordUtil;
import com.eaxon.eshopping.mapper.SellerMapper;
import com.eaxon.eshopping.model.dto.SellerLoginDTO;
import com.eaxon.eshopping.model.dto.SellerRegisterDTO;
import com.eaxon.eshopping.model.entity.Seller;
import com.eaxon.eshopping.model.vo.SellerLoginVO;
import com.eaxon.eshopping.model.vo.SellerRegisterVO;
import com.eaxon.eshopping.service.SellerService;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;
    
    @Autowired
    private PasswordUtil passwordUtil;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Result<SellerLoginVO> login(SellerLoginDTO loginDTO) {
        // 根据用户名查找用户
        Seller seller = sellerMapper.findByUsername(loginDTO.getUsername());
        
        // 用户不存在
        if (seller == null) {
            return Result.error("用户名或密码错误");
        }
        
        // 账号被禁用
        if (seller.getStatus() == 0) {
            return Result.error("账号已被禁用，请联系管理员");
        }
        
        // 校验密码
        if (!passwordUtil.matches(loginDTO.getPassword(), seller.getPassword())) {
            return Result.error("用户名或密码错误");
        }
        
        // 生成JWT令牌
        String token = jwtUtil.generateToken(seller.getUsername(), seller.getId());
        
        // 构建返回结果
        SellerLoginVO loginVO = new SellerLoginVO(
            seller.getId(), 
            seller.getUsername(), 
            seller.getName(), 
            token
        );
        
        return Result.success(loginVO);
    }

    @Override
    @Transactional
    public Result<SellerRegisterVO> register(SellerRegisterDTO registerDTO) {
        // 校验用户名是否已存在
        if (sellerMapper.findByUsername(registerDTO.getUsername()) != null) {
            return Result.error("用户名已存在，请更换");
        }
        
        // 校验手机号是否已存在
        if (sellerMapper.findByPhone(registerDTO.getPhone()) != null) {
            return Result.error("手机号已注册，请直接登录");
        }
        
        // 校验邮箱是否已存在
        if (sellerMapper.findByEmail(registerDTO.getEmail()) != null) {
            return Result.error("邮箱已注册，请直接登录");
        }
        
        // 创建新卖家对象
        Seller seller = new Seller();
        seller.setUsername(registerDTO.getUsername());
        // 加密密码
        seller.setPassword(passwordUtil.encode(registerDTO.getPassword()));
        seller.setName("用户" + registerDTO.getUsername()); // 设置默认的名称
        seller.setPhone(registerDTO.getPhone());
        seller.setEmail(registerDTO.getEmail());
        seller.setShopName("店铺" + registerDTO.getUsername()); // 设置默认的店铺名称
        seller.setStatus(1); // 默认启用
        seller.setLevel(1);  // 默认普通卖家级别
        
        // 保存到数据库
        sellerMapper.insert(seller);
        
        // 构建返回结果
        SellerRegisterVO registerVO = new SellerRegisterVO(
            seller.getId(),
            seller.getUsername(),
            seller.getName()
        );
        
        return Result.success(registerVO);
    }
}
