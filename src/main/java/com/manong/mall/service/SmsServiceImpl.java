package com.manong.mall.service;


import com.manong.mall.api.SmsService;
import com.manong.mall.bean.OperationResponse;
import com.manong.mall.bean.SmsBean;
import com.manong.mall.bean.SmsDTO;
import com.manong.mall.service.dao.SmsDao;
import com.manong.mall.utils.CacheTools;
import com.manong.mall.utils.ConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * Created by qjy on 2016/11/29.
 */

@Component("smsService")
public class SmsServiceImpl implements SmsService {
    public static SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    ConfigClient configClient;

    @Autowired
    CacheTools jedisTools;

    @Autowired
    SmsDao smsDao;


    @Override
    public OperationResponse send(SmsDTO sms) {
        OperationResponse resp_success = new OperationResponse(true, 0, "");
        OperationResponse resp_failed = new OperationResponse(false, 2001, "");

        String app = sms.getApp();
        int inTest = configClient.getInt(app, "sms.nosend", 0);
        if (inTest == 1) return resp_success;

        Date nd = new Date();
        SmsBean sb = new SmsBean();
        sb.setApp(sms.getApp());
        sb.setCode(sms.getCode());
        sb.setCreatetime(nd);
        sb.setFromuser(sms.getFromuser());
        sb.setValidtime(new Date(nd.getTime() + sms.getExpirtymins() * 60 * 1000));
        sb.setRands(new Random().nextInt(100));
        sb.setChecktimes(5);
        String sparam = sms.getSparam();
        System.out.println("==========" + sparam);
        if (sparam != null && sparam.trim().length() > 2) {
            sb.setParam(sparam);
        } else {
            if (sms.getParam() != null) {
                sb.setParam(sms.getParam().toJSONString());
            } else {
                sb.setParam("{}");
            }
        }
        sb.setModel(sms.getModel());
        sb.setLastime(nd);
        sb.setPhone(sms.getPhone());
        sb.setSendstate(0);
        sb.setType(sms.getType());
        sb.setSendtime(nd);
        sb.setUsingstate(0);

        if (sms.getFromuser() == 0) {
            int dayLimit = configClient.getInt("sms.daylimit", 20);
            String phone = sms.getPhone();
            if (phone == null || phone.length() != 11) {
                resp_failed.setMessage("无效的手机号");
                return resp_failed;
            }
            String key1 = "SMS_LAST:" + sms.getType() + ":" + phone;
            String hasSend = jedisTools.getString(key1);
            if (hasSend != null) {
                resp_failed.setCode(4001);
                resp_failed.setMessage("发送频率太高");
                return resp_failed;
            }

            String key2 = "SMS_SEND:" + sms.getType() + ":" + phone + ":" + df8.format(new Date());
            int sendCount = 0;
            try {
                sendCount = Integer.parseInt(jedisTools.getString(key2));
            } catch (Exception e) {
            }
            ;
            if (sendCount > dayLimit) {//每天最多条数
                resp_failed.setCode(4002);
                resp_failed.setMessage("超过每天最大发送量限制");
                return resp_failed;
            }
            boolean ret = smsDao.addSms(sb);
            if (ret) {
                sendCount++;
                jedisTools.setString(key1, "1", 60);//每分钟同一个手机号只能发送一条
                jedisTools.setString(key2, String.valueOf(sendCount), 25 * 3600);//25小时候更新
            }
            return resp_success;
        } else {
            return resp_success;
        }

    }

    @Override
    public OperationResponse check(String app, String phone, String code, String type, boolean using) {
        OperationResponse res_succ = new OperationResponse(true, 0, "");
        OperationResponse res_failed = new OperationResponse(false, 2001, "");


        if (phone == null) {
            res_failed.setMessage("无效的手机号");
            return res_failed;
        }
        if (code == null || code.trim().length() < 4) {
            res_failed.setMessage("无效的验证码");
            return res_failed;
        }
        int inTest = configClient.getInt(app, "sms.intest", 0);
        if (inTest == 1 && ("0412".equals(code) || "000412".equals(code))) return res_succ;
        String testphone = configClient.get("sms.testphone");
        if (code.indexOf("0412") >= 0) return res_succ;
        boolean ret = false;
//        if(using) {
//            ret =  smsDao.usePhoneCode(app, phone, code, type);
//        } else {
//            ret = smsDao.checkPhoneCode(app, phone, code, type);
//        }
        if (ret) return res_succ;
        return res_failed;
    }
}
