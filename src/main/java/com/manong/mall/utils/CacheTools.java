package com.manong.mall.utils;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by qjy on 2017/7/21.
 */
public class CacheTools {
    Map<String, Serializable> map = new ConcurrentHashMap<>();
//    public final static String PREFIX = "WEBAG:";
//    private JedisCluster jedisCluster;
//    private Jedis jedis;
//    private boolean clustered;
//    private String singlehost;
//    private int singleport;


    public CacheTools() {
    }

    public <T> T getObject(String key) {
        return (T) map.get(key);
    }

    public boolean setObject(String key, Serializable obj, int timeout) {
        map.put(key, obj);
        return true;
    }

    public boolean setObject(String key, Serializable obj) {
        map.put(key, obj);
        return true;
    }

    public boolean delObject(String key) {
        Serializable o = map.remove(key);
        return o != null;
    }

    public String getString(String key) {
        return (String) map.get(key);
    }

    public void setString(String key, String value) {
        setString(key, value, 0);
    }

    public void setString(String key, String value, int timeout) {
        setObject(key, value);
    }

    public void delString(String key) {
        delObject(key);
    }

    public long incr(String key) {
        return incr(key, 1);
    }

    public long incr(String key, long amount) {
        Long value = (Long) map.get(key);
        if (value == null) {
            value = 1L;
            setObject(key, value);
        }

        value += amount;
        setObject(key, value);


        return value;
    }

    public void init_single() {
    }
}
