package com.manong.mall.bean;

import lombok.Data;

import java.util.Date;

@Data
public class ShopFocusAndVisitBean extends BaseBean {
	long id;//` bigint(20) NOT NULL AUTO_INCREMENT,
	  long uid;//` bigint(20) DEFAULT '0' COMMENT '用户id',
	  long sid;//` bigint(20) DEFAULT '0' COMMENT '店铺id',
	  Date optime;//` datetime DEFAULT NULL COMMENT '关注时间',
	  
	  long gid;//` bigint(20) DEFAULT '0' COMMENT '商品id',
	  Date visittime;//` datetime DEFAULT NULL COMMENT '浏览时间',
	  
	  long shopid;
	  String name;//店铺
	  long goodsid;
	  String gname;//商品
	  double price;
	  String picts;//商品图片
	  
	  String pict;//店铺图片
	  String province;//` varchar(40) DEFAULT NULL COMMENT '省',
	  String city;//` varchar(40) DEFAULT NULL COMMENT '市',
	  String district;//` varchar(40) DEFAULT NULL COMMENT '区',
	  String address;//` varchar(100) DEFAULT NULL COMMENT '地址',
	  String uname;//` varchar(40) DEFAULT NULL COMMENT '店主姓名',
	  String phone;//` varchar(20) DEFAULT NULL COMMENT '联系电话',
	public String getMainPict() {
		String de = "";
		if(picts == null || picts.length() == 0) return de;
		String[] ps = picts.split(",");
		if(ps == null || ps.length == 0) return de;
		return ps[0];
	}
	}
