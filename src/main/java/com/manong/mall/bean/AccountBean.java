package com.manong.mall.bean;

import lombok.Data;

import java.util.Date;

@Data
public class AccountBean extends BaseBean {
	 long id;//` bigint(20) NOT NULL AUTO_INCREMENT,
	  long sid;//` bigint(20) DEFAULT '0' COMMENT '店铺id',
	  int type;//` int(2) DEFAULT '0' COMMENT '类别：0：收入 1：提款',
	  String content;//` varchar(100) DEFAULT NULL COMMENT '内容',
	  double money;//` double(10,2) DEFAULT '0.00' COMMENT '发生额',
	  double restmoney;//` double(10,2) DEFAULT '0.00' COMMENT '发生余额',
	  Date optime;//` datetime DEFAULT NULL COMMENT '发送时间',
	  String remarks;//` varchar(100) DEFAULT NULL COMMENT '备注',
	}
