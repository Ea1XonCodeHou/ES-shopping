CREATE TABLE `seller` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '卖家ID',
  `username` varchar(50) NOT NULL COMMENT '用户名/登录账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `name` varchar(50) NOT NULL COMMENT '卖家真实姓名',
  `phone` varchar(20) NOT NULL COMMENT '手机号码',
  `email` varchar(100) NOT NULL COMMENT '电子邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `shop_name` varchar(100) DEFAULT NULL COMMENT '店铺名称',
  `shop_logo` varchar(255) DEFAULT NULL COMMENT '店铺LOGO',
  `shop_desc` text COMMENT '店铺描述',
  `identity_card` varchar(30) DEFAULT NULL COMMENT '身份证号',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `level` tinyint DEFAULT '1' COMMENT '卖家等级: 1-普通卖家, 2-高级卖家, 3-VIP卖家',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态: 0-禁用, 1-启用, 2-审核中',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`),
  UNIQUE KEY `idx_phone` (`phone`),
  UNIQUE KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='卖家信息表';

-- 添加初始测试数据
INSERT INTO `seller` (`username`, `password`, `name`, `phone`, `email`, `avatar`, 
  `shop_name`, `level`, `status`, `create_time`)
VALUES 
('admin', '123456', '管理员', '13800138000', 'admin@example.com', NULL, 
  'ES-Shopping官方店', 3, 1, NOW()),
('test', '123456', '测试用户', '13900139000', 'test@example.com', NULL, 
  '测试店铺', 1, 1, NOW());