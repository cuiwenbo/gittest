package com.manong.mall.api;

public interface ConfigService {
    byte[] getData();
    String getVersion();
    long getLastTime();
    void reload();
}
