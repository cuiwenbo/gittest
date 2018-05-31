package com.manong.mall.bean;


import lombok.Data;

import java.util.Date;

@Data
public class UserExtBean extends BaseBean {
	long id;//` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
	  Date regtime;//` datetime DEFAULT NULL COMMENT '注册时间',
	  Date logintime;//` datetime DEFAULT NULL COMMENT '最后登录时间',
	  String regip;//` varchar(40) DEFAULT NULL COMMENT '注册ip',
	  String loginip;//` varchar(40) DEFAULT NULL COMMENT '最后登录ip',
	  String province;//` varchar(100) DEFAULT NULL COMMENT '收件省',
	  String city;//` varchar(100) DEFAULT NULL COMMENT '市',
	  String district;//` varchar(100) DEFAULT NULL COMMENT '县',
	  String address;//` varchar(100) DEFAULT NULL COMMENT '详细地址',
	  String receiver;//` varchar(100) DEFAULT NULL COMMENT '收件人',
	  String phone;//` varchar(20) DEFAULT NULL COMMENT '联系电话',
}
