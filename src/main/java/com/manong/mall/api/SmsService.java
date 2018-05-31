package com.manong.mall.api;


import com.manong.mall.bean.OperationResponse;
import com.manong.mall.bean.SmsDTO;

public interface SmsService {
    /**
     * @param req
     * @return
     */
    OperationResponse send(SmsDTO req);

    /**
     * @param app
     * @param phone
     * @param type
     * @param code
     * @param used  true-直接使用
     * @return
     */
    OperationResponse check(String app, String phone, String code, String type, boolean used);

}
