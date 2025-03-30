package com.eaxon.eshopping.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.eaxon.eshopping.model.entity.Seller;

@Mapper
public interface SellerMapper {
    
    /**
     * 根据用户名查询卖家信息
     * @param username 用户名
     * @return 卖家信息
     */
    @Select("SELECT * FROM seller WHERE username = #{username} AND is_deleted = 0")
    Seller findByUsername(@Param("username") String username);
    
    /**
     * 根据手机号查询卖家信息
     * @param phone 手机号
     * @return 卖家信息
     */
    @Select("SELECT * FROM seller WHERE phone = #{phone} AND is_deleted = 0")
    Seller findByPhone(@Param("phone") String phone);
    
    /**
     * 根据邮箱查询卖家信息
     * @param email 邮箱
     * @return 卖家信息
     */
    @Select("SELECT * FROM seller WHERE email = #{email} AND is_deleted = 0")
    Seller findByEmail(@Param("email") String email);
    
    /**
     * 新增卖家
     * @param seller 卖家信息
     * @return 影响行数
     */
    @Insert("INSERT INTO seller (username, password, name, phone, email, shop_name, status, level) " +
            "VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{shopName}, 1, 1)")
    int insert(Seller seller);
}
