package com.manong.mall.bean;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Date;

@Data
public class SmsBean extends BaseBean {
	   long id;//                   bigint(20) not null,
	   String type;//                varchar(10) comment '类别',
	   String content;//              varchar(100) comment '发送内容',
	   String phone;//                varchar(20) comment '手机号',
	   String code;//                varchar(10) comment '手机验证码',
	   Date createtime;//          datetime comment '创建时间',
	   Date create_date;//          varchar(10) comment '创建日期',
	   int sendstate       ;//     int(1) default 0 comment '发送状态(0:未发送  1:已发送 2:发送失败)',
	   Date sendtime         ;//    datetime comment '发送时间',
	   String sendresult         ;//  varchar(100) comment '发送结果',
	   int usingstate    ;//       int(1) default 0 comment '使用状态(0:未使用 1:已使用)',
	   Date usingtime   ;//         datetime comment '使用时间',
	   Date validtime    ;//        datetime comment '有效时间',
	   int valid;//分钟
	   int checktimes ;//          int(2) default 5 comment '最大尝试次数',
	   Date lastime    ;//         datetime comment '上次尝试时间',

	String app;
	int fromuser; //0-来自用户请求 1-来自服务器(不保存到数据库)
	int rands;
	int model;//发送方式：0-短信 1-语音
	String param;
}
