package com.manong.mall.utils;

import com.manong.mall.bean.SmsBean;
import com.manong.mall.service.BaseDaoImpl;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by boleli on 16/7/26.
 */
public class KeyGenerator {
    /**
     * 生成缓存key
     * @param business 所属业务，不能为空
     * @param type 数据类型，list,map等
     * @param code 唯一标识，如uid等
     * @return
     */
    public static String getKey(String business, String type, Object code) {
        return getKey(business, type, code, false);
    }

    /**
     * 生成缓存key
     * @param business 所属业务，不能为空
     * @param type 数据类型，list,map等
     * @param code 唯一标识，如uid等
     * @param needRandom 是否需要附加一个随机数
     * @return
     */
    public static String getKey(String business, String type, Object code, boolean needRandom) {
        String key = business + "_" + (StringUtils.isBlank(type) ? "" : type + "_") + code;
        if (needRandom) {
            key += "_";
            key += RandomUtils.nextInt(0, 10000);
        }
        return key;
    }

}
