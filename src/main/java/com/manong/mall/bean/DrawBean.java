package com.manong.mall.bean;
import lombok.Data;

import java.util.Date;

@Data
public class DrawBean extends BaseBean {
	long id;//` bigint(20) NOT NULL DEFAULT '0',
	  long sid;//` bigint(20) DEFAULT NULL COMMENT '商户id',
	  String bank;//` varchar(100) DEFAULT NULL COMMENT '银行',
	  String bankdeposit;//` varchar(100) DEFAULT NULL COMMENT '开户行',
	  String username;//` varchar(100) DEFAULT NULL COMMENT '开户人',
	  String accountno;//` varchar(100) DEFAULT NULL COMMENT '账户',
	  Date createtime;//` datetime DEFAULT NULL COMMENT '申请时间',
	  int state;//` int(11) DEFAULT '0' COMMENT '状态 0:新建 10:待审 11:审核不通过 20:审核通过待转账 21:拒绝提款请求 30:已完成',
	  double money;//` double(11,2) DEFAULT '0.00' COMMENT '提款金额',
	  String remarks;//` varchar(100) DEFAULT NULL COMMENT '备注',
	  String op1;//` varchar(20) DEFAULT NULL COMMENT '审核人',
	  String op1info;//` varchar(100) DEFAULT NULL COMMENT '审核意见',
	  Date op1time;//` datetime DEFAULT NULL COMMENT '审核时间',
	  String op2;//` varchar(20) DEFAULT NULL COMMENT '审核人(转账)',
	  String op2info;//` varchar(100) DEFAULT NULL COMMENT '审核意见',
	  Date op2time;//` datetime DEFAULT NULL COMMENT '审核时间',
	  String statename;
	  public String getStatename() {
		  switch (state) {
		case 10:return "待审";
		case 11:return "审核不通过";
		case 20:return "审核通过待转账";
		case 21:return "拒绝提款请求";
		case 30:return "成功";	
		}
		return "";
	}
	}
