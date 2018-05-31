package com.manong.mall.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by boleli on 2016/10/22.
 */
public class ProcessTimmer {
    private static ProcessTimmer instance = new ProcessTimmer();

    private Map<String, JSONObject> timmers = new ConcurrentHashMap<>();

    public static ProcessTimmer getInstance() {
        return instance;
    }

    public void start(String key) {
        JSONObject timmer = new JSONObject();
        long current = System.currentTimeMillis();
        timmer.put("start", current);
        timmers.put(key, timmer);
    }

    public void stop(String key) {
        JSONObject timmer = timmers.get(key);
        Optional.ofNullable(timmer)
                .ifPresent(p -> p.put("stop", System.currentTimeMillis()));
    }

    public long getDuringSecond(String key) {
        JSONObject timmer = timmers.get(key);
        return Optional.ofNullable(timmer)
                .map(t -> t.getLong("stop") - t.getLong("start"))
                .map(t -> t / 1000)
                .orElse(0L);
    }
}
