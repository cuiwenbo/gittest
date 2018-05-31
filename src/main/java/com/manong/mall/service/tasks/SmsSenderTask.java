package com.manong.mall.service.tasks;

import com.alibaba.fastjson.JSONObject;
import com.manong.mall.bean.OperationResponse;
import com.manong.mall.bean.SmsBean;
import com.manong.mall.service.dao.SmsDao;
import com.manong.mall.utils.HttpResult;
import com.manong.mall.utils.HttpUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qjy on 2017/8/7.
 */
@Component("smsSender")
public class SmsSenderTask {
    public static Logger logger = Logger.getLogger("SmsLog");
    @Autowired
    SmsDao smsDao;

    private static String URI_SEND_SMS = "http://sms.haotingyun.com/v2/sms/single_send.json";
    private static String apikey = "6b05b99371ef41f947bd3976ee72d45c";
    public static boolean running = false;

    @PostConstruct
    public void init() {
        logger.debug("start sms sender");
    }
    @PreDestroy
    public void destroy() {
        logger.debug("will stop...");
        running = false;
    }
    public void execute() {
        logger.debug("执行短信发送.");
        if(running) {
            logger.debug("in running, over");
            return;
        }
        running = true;
        try {
            logger.debug("开始循环处理.....");
            while (true) {
                //取出未发送短信（List）

                List<SmsBean> list = smsDao.listUnsend(100, "'REG'");
                //logger.debug("COUNT LEFT:" + list == null?0:list.size());
                if (list==null||list.size()==0){
                    try {
                        Thread.sleep(3000);
                        continue;
                    }
                    catch (Exception e)
                    {}
                }

                int count = 0;
                for(SmsBean s:list) {
                    count++;
                    Long id = s.getId();
                    String phone = s.getPhone();
                    String errmess = null;

                    if (phone == null || phone.length() != 11) {
                        errmess = "无效的手机号码";
                        logger.debug(id + "," + phone + "," + errmess);
                        s.setSendstate(2);
                        s.setSendresult(errmess);
                        smsDao.updateSms(s);
                        continue;
                    }
                    OperationResponse send_ret = doSend(s);
                    if(send_ret.isSuccess())
                        errmess = null;
                    else
                        errmess = send_ret.getMessage();

                    logger.debug(id + "," + phone + ","  + errmess);
                    if (errmess == null) {
                        s.setSendstate(1);
                    } else {
                        s.setSendstate(2);
                    }
                    s.setSendresult(errmess);
                    smsDao.updateSms(s);
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                long sleeptime = 3000;
                if (count == 100) {
                    sleeptime = 200;
                }
                try {
                    Thread.sleep(sleeptime);
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            running = false;
        }
    }

    public OperationResponse doSend(SmsBean sms) {
        String sendContent = "";
        switch (sms.getType()) {
            case "REG":
                sendContent = String.format("您的注册验证码为%s，请在10分钟内完成注册", sms.getCode());
                Map<String, String> params = new HashMap<String, String>();
                params.put("apikey", apikey);
                params.put("text", sendContent);
                params.put("mobile", sms.getPhone());
                HttpResult result = HttpUtils.post(URI_SEND_SMS, params, null);
                JSONObject ret = null;
                try {
                    ret = JSONObject.parseObject(result.getData());
                    int code = ret.getInteger("code");
                    if (code != 0) {
                        return new OperationResponse(false, code, ret.getString("msg"));
                    } else {
                        return new OperationResponse(true, 0, "success");
                    }
                } catch (Exception e) {
                }
            default:
                break;
        }
        return new OperationResponse(false, 3001, "bad req");
    }

}
