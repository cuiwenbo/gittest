package com.manong.mall.bean;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by qjy on 2016/11/29.
 */
@Data
public class SmsDTO implements Serializable {
    String app;
    String type;
    String code;
    int model;//发送方式：0-短信 1-语音
    String phone;
    int expirtymins; //有效期分钟计算
    int fromuser; //0-来自用户请求 1-来自服务器(不保存到数据库)
    String sparam;
    JSONObject param;
    public void addParam(String key,String val) {
        if(param == null) param = new JSONObject(true);
        param.put(key, val);
        sparam = param.toJSONString();
    }
}
