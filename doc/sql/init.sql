CREATE TABLE `car` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
`name` varchar(20) DEFAULT NULL COMMENT '品牌',
`color` varchar(10) DEFAULT NULL COMMENT '颜色',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='汽车表';

CREATE TABLE `user` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`username` varchar(50) NOT NULL COMMENT '用户名',
`phone` varchar(20) DEFAULT NULL COMMENT '注册手机号',
`age` int(11) DEFAULT NULL COMMENT '年龄',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

INSERT INTO `user`(`id`, `username`, `phone`, `age`) VALUES (1, '我来自主数据库zjq666', '15656455662', 16);
INSERT INTO `user`(`id`, `username`, `phone`, `age`) VALUES (2, '我来自主数据库zjq', '15656455662', 18);

CREATE TABLE `sys_operation_log` (
 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
 `type` tinyint(1) DEFAULT '1' COMMENT '日志类型 1-正常日志 9-异常日志',
 `title` varchar(255) DEFAULT '' COMMENT '日志标题',
 `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
 `ip` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
 `ip_location` varchar(255) DEFAULT NULL COMMENT 'IP地理位置',
 `user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
 `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
 `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
 `params` text COMMENT '操作提交的数据',
 `time` mediumtext COMMENT '执行时间',
 `exception` text COMMENT '异常信息',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='系统操作日志表';
