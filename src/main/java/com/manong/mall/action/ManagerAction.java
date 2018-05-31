package com.manong.mall.action;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.manong.mall.api.ConfigService;
import com.manong.mall.utils.ConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hzhb on 2017/12/11.
 */
public class ManagerAction extends BaseAction{

    @Autowired
    public ConfigClient configClient;

    @Autowired
    public ConfigService configService;


//    @RequestMapping("/mc/get")
//    public void getMc(HttpServletRequest req, HttpServletResponse resp) {
//        String key = getString(req, "key", "");
//        if(key == null || key.trim().length() == 0) {
//            writeJson(resp, "2001", "无效的KEY", null);
//            return;
//        }
//        key = key.trim();
//        String val= getMc(key);
//        if(val == null) {
//            writeJson(resp, "2002", "数据为空", null);
//            return;
//        }
//        writeJson(resp, "0000", "SUCCESS", val);
//    }
//
//    @RequestMapping("/mc/set")
//    public void setMc(HttpServletRequest req, HttpServletResponse resp) {
//        String key = getString(req, "key", "");
//        if(key == null || key.trim().length() == 0) {
//            writeJson(resp, "2001", "无效的KEY", null);
//            return;
//        }
//        key = key.trim();
//        String data = getString(req,"val", "");
//        int  timeout = getInt(req, "time", 0);
//        if(timeout < 0) timeout = 0;
//        super.setMc(key, data, timeout);
//        writeJson(resp, "0000", "success", null);
//    }
//
//    @RequestMapping("/mc/rm")
//    public void RemoveMc(HttpServletRequest req, HttpServletResponse resp) {
//        String key = getString(req, "key", "");
//        if(key == null || key.trim().length() == 0) {
//            writeJson(resp, "2001", "无效的KEY", null);
//            return;
//        }
//        super.rmMc(key);
//        writeJson(resp, "0000", "success", null);
//    }
    @RequestMapping("/tools/getconfig")
    public void getConfig(HttpServletRequest req, HttpServletResponse resp) {
        String key = getString(req, "key", "");
        String app = getString(req, "app", "X1");
        int version = getInt(req, "version", 0);
        if(key == null || key.trim().length() == 0) {
            writeJson(resp, "2001", "无效的KEY", null);
            return;
        }
        key = key.trim();
        //	String val = "";
        String val = configClient.get(app, 0, key);
        if(val == null) {
            writeJson(resp, "2002", "数据为空", null);
            return;
        }
        writeJson(resp, "0000", "SUCCESS", val);
    }

    @RequestMapping("/tools/resetconfig")
    public void setConfig(HttpServletRequest req, HttpServletResponse resp) {
        configService.reload();
        writeJson(resp, "0000", "SUCCESS", null);
    }

}
