package com.manong.mall.bean;


import lombok.Data;

import java.util.Date;

@Data
public class ShopExtBean extends BaseBean {
	   long id                  ;// bigint(20) not null default 0 comment '商户id',
	   double total           ;//     double(13,2) default 0 comment '总金额',
	   double draw          ;//       double(13,2) default 0 comment '已提款金额',
	   double froze          ;//      double(11,2) default 0 comment '冻结金额-已申请提款,但尚未完成',
	   double free            ;//     double(11,2) default 0 comment '可用金额',
	   String bank              ;//   varchar(100) comment '银行',
	   String bankdeposit    ;//      varchar(100) comment '开户行',
	   String username        ;//     varchar(100) comment '账户名称',
	   String accountno       ;//     varchar(100) comment '帐号',
	   Date createtime         ;//  datetime comment '创建时间',
	   Date visittime            ;//datetime comment '最后访问时间',
}
