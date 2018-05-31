package com.manong.mall.bean;


import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderBean extends BaseBean {
	long id;//` bigint(20) NOT NULL,
	String orderid;//` varchar(32) DEFAULT NULL COMMENT '订单ID',
	 long userid;//` bigint(20) DEFAULT NULL COMMENT '用户id',
	  long sid;//` bigint(20) DEFAULT NULL COMMENT '店铺id',
	  long goods;//` bigint(20) DEFAULT NULL COMMENT '商品id',
	  String name;//` varchar(100) DEFAULT NULL COMMENT '商品名',
	  String param;//` varchar(400) DEFAULT NULL COMMENT '商品参数',
	  double price;//` double(9,2) DEFAULT NULL COMMENT '价格',
	  double amount;//` double(12,2) DEFAULT NULL COMMENT '数量',
	  String unit;//` varchar(10) DEFAULT NULL COMMENT '计量单位',
	  double deliverymoney;//` double(9,2) DEFAULT NULL COMMENT '运费',
	  double discount;//` double(9,2) DEFAULT NULL COMMENT '折扣',
	  double totalmoney;//` double(9,2) DEFAULT NULL COMMENT '总金额',
	  Date createtime;//` datetime DEFAULT NULL COMMENT '创建订单时间',
	  Date paytime;//` datetime DEFAULT NULL COMMENT '支付时间',
	  String remarks;//` varchar(200) DEFAULT NULL COMMENT '用户备注',
	  String deliverymethod;//` varchar(20) DEFAULT NULL COMMENT '运送方式',
	  String address;//` varchar(100) DEFAULT NULL COMMENT '收件地址',
	  String phone;//` varchar(20) DEFAULT NULL COMMENT '联系电话',
	  String username;//` varchar(20) DEFAULT NULL COMMENT '收件人',
	  String delivery;//` varchar(40) DEFAULT NULL COMMENT '运送单位',
	  String deliveryid;//` varchar(40) DEFAULT NULL COMMENT '运送单号',
	  int state;//` int(2) DEFAULT NULL COMMENT '状态0,未支付 1:已支付 3:已发货4:已收货 8:已失效, 9:已取消',
	  long settleid;//` bigint(20) DEFAULT '0' COMMENT '结算id',
	  double servicefee;//` double(9,2) DEFAULT '0.00' COMMENT '商户服务费',
	  String paytype;//` varchar(20) DEFAULT NULL COMMENT '支付方式',
	  int evalstate;//` int(11) DEFAULT '0' COMMENT '是否已评价0:未评价 1:已评价',
	 Date deliverytime;//` datetime DEFAULT NULL COMMENT '发货时间',
	 Date recvtime;//` datetime DEFAULT NULL COMMENT '收货时间',
	  String picts;
	  String statename;
	  List paramList;
	  String sname;//店铺名称
	  

	public String getStatename() {
		  switch(state) {
		  case 0:return "等待付款";
		  case 1:return "买家已付款";
		  case 2:return "已确认";
		  case 3:return "已发货";
		  case 4:return "已收货";
		  case 5:return "待退货";
		  case 6:return "已退货";
		  case 8:return "订单失效";
		  case 9:return "已取消";
		  }
		  return "";
	  }
	public List getParamList(){
		List<String[]> ret = new ArrayList();
		try {
			if(param == null || param.length() < 4) return null;
			String param1= param.substring(1, param.length() - 1);
			
			String[] t1 = param1.split(",");
			
			for(String t:t1) {
				String t2 = t.substring(1, t.length() - 1);
				String[] t3 = t2.split(":");
				//String t4=t3.
				if(t3 != null && t3.length == 2) 
					{
					t3[0]=t3[0].substring(1, t3[0].length()-1);
					t3[1]=t3[1].substring(1, t3[1].length()-1);
					ret.add(t3);}			
			}			
    		} catch (Exception e){};
    		return ret;
	}
	public String getMainPict() {
		String de = "";
		if(picts == null || picts.length() == 0) return de;
		String[] ps = picts.split(",");
		if(ps == null || ps.length == 0) return de;
		return ps[0];
	}
}
