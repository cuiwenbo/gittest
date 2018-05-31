package com.manong.mall.bean;

import lombok.Data;

import java.util.Date;

@Data
public class AuditBean extends BaseBean {
	 long id;//` bigint(20) NOT NULL DEFAULT '0',
	  long uid;//` bigint(20) DEFAULT '0' COMMENT '用户id',
	  long sid;//` bigint(20) DEFAULT '0' COMMENT '店铺id',
	  int type;//` int(1) DEFAULT '0' COMMENT '类别：0，个人 1：企业',
	  String name;//` varchar(40) DEFAULT NULL COMMENT '店铺名称',
	  String idcn;//` varchar(40) DEFAULT NULL COMMENT '证号',
	  String pict;//` varchar(600) DEFAULT NULL COMMENT '图片',
	  Date createtime;//` datetime DEFAULT NULL COMMENT '申请时间',
	  Date optime;//` datetime DEFAULT NULL COMMENT '处理时间',
	  int audit;//` int(1) DEFAULT '0' COMMENT '审核结果:0,待审核1：通过,2：不通过,9，取消',
	  String opertor;//` varchar(40) DEFAULT NULL COMMENT '审核人',
	  String auditresult;//` varchar(100) DEFAULT NULL COMMENT '审核意见',
	  String remarks;//` varchar(100) DEFAULT NULL COMMENT '备注',
	 String username;//` varchar(40) DEFAULT NULL COMMENT '用户姓名',

}
