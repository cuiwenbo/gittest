package com.manong.mall.bean;


import lombok.Data;

import java.util.Date;

@Data
public class ShopBean extends BaseBean {
	  long id;//` bigint(20) NOT NULL DEFAULT '0' COMMENT '店铺id',
	  long uid;//` bigint(20) DEFAULT '0' COMMENT '用户id',
	  String cname;//` varchar(100) DEFAULT NULL COMMENT '公司名称',
	  String province;//` varchar(40) DEFAULT NULL COMMENT '省',
	  String city;//` varchar(40) DEFAULT NULL COMMENT '市',
	  String district;//` varchar(40) DEFAULT NULL COMMENT '区',
	  String address;//` varchar(100) DEFAULT NULL COMMENT '地址',
	  String name;//` varchar(100) DEFAULT NULL COMMENT '店铺名称',
	  String catalog;//` varchar(20) DEFAULT NULL COMMENT '分类',
	  String uname;//` varchar(40) DEFAULT NULL COMMENT '店主姓名',
	  String intro;//店铺介绍
	  String pict;//` varchar(200) DEFAULT NULL COMMENT '店铺图标',
	  String phone;//` varchar(20) DEFAULT NULL COMMENT '联系电话',
	  String qq;//` varchar(20) DEFAULT NULL COMMENT 'QQ',
	  int type;//` int(1) DEFAULT '0' COMMENT '类别：0个人 1：企业',
	  int authed;//` int(1) DEFAULT '0' COMMENT '认证标志：0，未认证 1：已认证 ,2:待审核 3，审核不通过',
	  int state;//` int(1) DEFAULT '0' COMMENT '状态：0，未开通 1：已开通 9：已关停',
	  
	   long showtimes;//            bigint(10) default 0 comment '被浏览次数',
	   Date lasttime;//             datetime comment '最后浏览时间',
	  String bpict;//` varchar(200) DEFAULT NULL COMMENT '背景大图',
	  
	  String partner;//` varchar(100) DEFAULT NULL COMMENT '微信商户号',
	  String appid;//` varchar(100) DEFAULT NULL COMMENT '应用id',
	  String appsecret;//` varchar(100) DEFAULT NULL COMMENT '应用密钥',
	public String getAuthname() {
		switch(authed) {
		case 0: return "未认证";
		case 1: return "已经认证";
		case 2: return "已提交资料,待审核";
		case 3: return "审核不通过";
		}
		return "";
	}
	public String getTypename() {
		if(type == 0) return "个人";
		return "企业";
	}

}
