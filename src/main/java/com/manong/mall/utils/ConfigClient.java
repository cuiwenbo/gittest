package com.manong.mall.utils;

import com.manong.mall.api.ConfigService;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qjy on 2017/7/19.
 * 配置客户端
 *
 */
public class ConfigClient {

    public static Map<String,Map<String,String>> data = null;
    public static long lastCheckTime = 0L;
    public static String configVersion = "C000";
    public static String serverVersion = "S000";

    public long updateSecond = 10000;

    ConfigService configService;
    CacheTools jedisTools;

    public ConfigClient(ConfigService configService, CacheTools jedisObjectTools, long updateSecond) {
        this.configService = configService;
        this.jedisTools = jedisObjectTools;
        this.updateSecond = updateSecond;
    }

    public boolean check() {
        long now = System.currentTimeMillis() - updateSecond;
        if(lastCheckTime == 0 || lastCheckTime < now) {
            String key = "ZHIBO:CONFIG:V2:VERSION";
            serverVersion = jedisTools.getString(key);
            if(serverVersion == null) serverVersion = "S000";
            if(configVersion == null) configVersion = "C000";
            if(configVersion.equals(serverVersion)) return true;
            return false;
        }
        return true;
    }

    public void readConfigFromService() {
        byte[] configData = configService.getData();
        data = (Map) SerializationUtil.deserialize(configData);
        lastCheckTime = System.currentTimeMillis();
        configVersion = serverVersion;
        if(serverVersion == null) serverVersion = "S000";
        if(configVersion == null) configVersion = "C000";
    }

    public Map<String,String> gets(String app, String key) {
        return gets(app, 0, key);
    }
    public Map<String,String> gets(String app, int version, String key) {
        Map<String,String> ret = new HashMap();
        if(key == null || key.trim().length() == 0) return ret;
        if(!check()) {
            synchronized(this) {
                readConfigFromService();
            }
        }

        if(app == null) return ret;
        key = key.toLowerCase();
        Map<String,String> appmap = data.get(app + ":" + version);
        if(appmap == null) appmap = new HashMap<String,String>();
        Map<String,String> t1 = data.get(app + ":0");
        if(t1 != null) appmap.putAll(t1);
        appmap.putAll(data.get("PUB"));

        for (String item : appmap.keySet()) {
            if(item.indexOf(key) == 0) {
                ret.put(item, appmap.get(item));
            }
        }
        return ret;
    }



    public String get(String app,int version, String key) {
        if(app == null) app = "PUB";

        if(!check()) {
            synchronized(this) {
                readConfigFromService();
            }
        }
        if(key == null) return null;
        key = key.toLowerCase();
        Map<String,String> appmap = null;
        String ret = null;
        if(!"PUB".equals(app)) {
            appmap = data.get(app + version);
            if (appmap != null) {
                ret = appmap.get(key);
                if (ret != null) return ret;
            }

            appmap = data.get(app + "0");
            if (appmap != null) {
                ret = appmap.get(key);
                if (ret != null) return ret;
            }
        }
        appmap = data.get("PUB");
        if(appmap != null) {
            ret = appmap.get(key);
            if(ret != null) return ret;
        }
        return null;
    }
    public  String get(String key) {
        return get("PUB", 0, key);
    }
    public  String get(String app, String key) {
       return get(app, 0, key);
    }

    public String getString(String key, String d) {
        String value = get(key);
        if (StringUtils.isBlank(value)) {
            value = d;
        }
        return value;
    }

    public  int getInt( String key, int d){
        return getInt("PUB", 0, key, d);
    }
    public  int getInt( String app, String key, int d){
        return getInt(app, 0, key, d);
    }

    public  long getLong(String key, long d) {
        return getLong("PUB", 0, key, d);
    }
    public  long getLong(String app, String key, long d) {return getLong(app, 0, key,d);}

    public  double getDouble(String key, double d) {
        return getDouble("PUB", 0, key, d);
    }
    public double getDouble(String app, String key, double d) {return getDouble(app, 0, key, d);};


    public  int getInt(String app, int version, String key, int d) {
        String v = get(app, version, key);
        if(v == null) return d;
        try {
            return Integer.parseInt(v);
        } catch (Exception e) {
            return d;
        }
    }

    public  long getLong(String app, int version, String key, long d) {
        String v = get(app, version, key);
        if(v == null) return d;
        try {
            return Long.parseLong(v);
        } catch (Exception e) {
            return d;
        }
    }

    public  double getDouble(String app, int version, String key, double d) {
        String v = get(app, key);
        if(v == null) return d;
        try {
            return Double.parseDouble(v);
        } catch (Exception e) {
            return d;
        }
    }
}
