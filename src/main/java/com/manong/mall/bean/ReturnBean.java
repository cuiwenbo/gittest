package com.manong.mall.bean;


import lombok.Data;

import java.util.Date;

@Data
public class ReturnBean extends BaseBean {
	long id;//` bigint(20) NOT NULL,
	  long sid;//` bigint(20) DEFAULT '0' COMMENT '店铺id',
	  long orderid;//` bigint(20) DEFAULT '0' COMMENT '原始订单id',
	  double omoney;//` double(10,2) DEFAULT '0.00' COMMENT '原始金额',
	  double rmoney;//` double(10,2) DEFAULT '0.00' COMMENT '退款金额',
	  String reason;//` varchar(100) DEFAULT NULL COMMENT '退货原因',
	  int state;//` int(1) DEFAULT '0' COMMENT '状态，0：创建 1：店家已同意 2：货物发回中 3：确认收货 4：退货完成 9：不同意退货',
	  String delivery;//` varchar(40) DEFAULT NULL COMMENT '物流公司',
	  String deliveryid;//` varchar(40) DEFAULT NULL COMMENT '物流id',
	  Date createtime;//` datetime DEFAULT NULL COMMENT '创建时间',
	  String userremarks;//` varchar(200) DEFAULT NULL COMMENT '用户备注',
	  String shopremarks;//` varchar(200) DEFAULT NULL COMMENT '店家说明',
	  Date lastoptime;//` datetime DEFAULT NULL COMMENT '店家操作时间',
	  
	  String name;//` varchar(100) DEFAULT NULL COMMENT '商品名',
	  String param;//` varchar(400) DEFAULT NULL COMMENT '商品参数',
	  double price;//` double(9,2) DEFAULT NULL COMMENT '价格',
	  double amount;//` double(12,2) DEFAULT NULL COMMENT '数量',
	  String unit;//` varchar(10) DEFAULT NULL COMMENT '计量单位',
	  
	  
	 String statename;
	  public String getStatename() {
		  switch(state) {
		  case 1:return "店家已同意";
		  case 2:return "货物发回中";
		  case 3:return "确认收货";
		  case 4:return "退货完成";
		  case 9:return "不同意退货";
		  }
		  return "";
	  }
	}
