package com.manong.mall.bean;


import lombok.Data;

@Data
public class UserBean extends BaseBean {
	   long id ;//                  bigint(20) not null default 0,
	   String phone;//                varchar(20) comment '手机号',
	   String passwd;//               varchar(40) comment '密码',
	   String name;//                 varchar(40) comment '姓名',
	   String idcn ;//                varchar(40) comment '身份证',
	   String address;//              varchar(100) comment '地址',
	   String pict ;//                varchar(100) comment '头像',
	   String sign;//                 varchar(100) comment '签名',
	   int authed;//               int(1) default 0 comment '认证标志:0,未认证 1：已认证',
	   int state ;//               int(1) default 0 comment '状态：0，正常 9：停用',
	   String nick;
	  int seller;//` int(1) DEFAULT '0' COMMENT '0:未开店 1：已开店',

}
