package com.manong.mall.bean;


import lombok.Data;

@Data
public class MessageBean {
	String mid;
	String touser;
	long fromuser;
	String content;
	String senddelay;
	String overdelay;
	String offlinemsg;
}
