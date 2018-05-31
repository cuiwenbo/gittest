package com.manong.mall.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;


@Data
public class OperationResponse implements Serializable{
    private boolean success;
    private int code;
    private String message;
    private Object extensionData;

    public OperationResponse() {
        super();
    }
    public OperationResponse(boolean res, int retcode, String message) {
        this.success = res;
        this.code = retcode;
        this.message = message;
    }

    public OperationResponse(boolean res, int retcode, String message, Object extensonData) {
        this.success = res;
        this.code = retcode;
        this.message = message;
        this.extensionData = extensonData;
    }

    public <T> T getData() {
        return (T) extensionData;
    }

    public String toString() {
        JSONObject j = new JSONObject();
        j.put("success", success);
        j.put("code", code);
        j.put("message", message);
        return j.toJSONString();
    }
}
