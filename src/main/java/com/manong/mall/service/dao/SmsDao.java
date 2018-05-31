package com.manong.mall.service.dao;


import com.manong.mall.bean.SmsBean;
import com.manong.mall.service.BaseDaoImpl;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("smsDao")
public class SmsDao extends BaseDaoImpl {
    public boolean addSms(SmsBean sms) {
        try {
            add("sms.add", sms);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean usePhoneCode(String app, String phone, String code, String type) {
        Map param = new HashMap();
        param.put("app", app);
        param.put("phone", phone);
        param.put("code", code);
        param.put("type", type);
        param.put("now", new Date());
        if (update("sms.using", param) < 1) {
            return false;
        }
        return true;
    }

    public boolean checkPhoneCode(String app, String phone, String code, String type) {
        Map param = new HashMap();
        param.put("app", app);
        param.put("phone", phone);
        param.put("code", code);
        param.put("type", type);
        param.put("now", new Date());
        int count = (Integer) get("sms.check", param);
        if (count > 0) {
            if (update("sms.addCheckTimes", param) < 1) return false;
            return true;
        }
        return false;
    }

    public List<SmsBean> listUnsend(int limitcount, String types) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("limit", limitcount);
        param.put("types", types);
        param.put("now", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return list("sms.listUnsendSms", param);
    }

    public void updateSms(SmsBean sms) {
        update("sms.updateSms",sms);
    }

}
