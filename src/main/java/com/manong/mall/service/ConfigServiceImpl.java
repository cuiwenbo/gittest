package com.manong.mall.service;


import com.manong.mall.api.ConfigService;
import com.manong.mall.service.dao.ConfigDao;
import com.manong.mall.utils.CacheTools;
import com.manong.mall.utils.SerializationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by qjy on 2016/11/29.
 *
 */


@Component("configService")
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    CacheTools cacheTools;

    @Autowired
    ConfigDao configDao;

    static byte[] configData;
    static String version = "0";
    static long lastTime = 0;
    @Override
    public byte[] getData() {
        return configData;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public long getLastTime() {
        return lastTime;
    }

    @Override

    public void reload() {
        init();
    }
    @PostConstruct
    public void init() {
        Map<String,Map<String,String>> data = new HashMap();
        Properties properties = new Properties();
        List<Map<String,String>> configsdata = configDao.listConfigs();
        for(Map<String,String> item:configsdata) {
            String app = item.get("app").toUpperCase();
            String version = item.get("appversion");
            String code = item.get("code");
            String value = item.get("value");
            if(app==null || code == null || code.trim().length() == 0) continue;
            code = code.toLowerCase();
            String key = app;
            if(!"PUB".equals(app)) {
                key = app + ":" + version;
            }
            Map t = data.get(key);
            if(t == null) t=new HashMap();
            t.put(code, value);
            data.put(key, t);
        }
        configData = null;
        configData = SerializationUtil.serialize(data);
        lastTime = System.currentTimeMillis();
        version = lastTime + "";
        cacheTools.setString("WEBAG:CONFIG:V2:VERSION", version, 0);
    }
}
