package com.manong.mall.utils;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;

/**
 * Created by boleli on 16/7/21.
 */
public class JedisObjectTools {
    private JedisCluster jedisCluster;

    public JedisObjectTools(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    public <T> T getObject(String key) {
        byte[] bytes = jedisCluster.get(key.getBytes());
        if (bytes == null) {
            return null;
        }
        return SerializationUtils.deserialize(bytes);
    }

    public boolean setObject(String key, Serializable obj) {
        String rst = jedisCluster.setex(key.getBytes(), 3600, SerializationUtils.serialize(obj));
        return StringUtils.isNotBlank(rst);
    }

    public boolean setObject(String key, Serializable obj, int offsite) {
        String rst = jedisCluster.setex(key.getBytes(), offsite, SerializationUtils.serialize(obj));
        return StringUtils.isNotBlank(rst);
    }

    public JedisCluster getJedis() {
        return jedisCluster;
    }

    public boolean delObject(String key) {
        Long rst = jedisCluster.del(key.getBytes());
        return rst != null && rst > 0;
    }

    public String getString(String key) {
        return jedisCluster.get(key);

    }
    public void setString(String key, String value) {
        jedisCluster.setex(key,3600, value);
    }

    public void setString(String key, String value, int timeout) {
        if(timeout > 0) {
            jedisCluster.setex(key, timeout, value);
        } else {
            jedisCluster.set(key, value);
        }
    }
    public  void delString(String key) {
        jedisCluster.del(key);
    }
}
