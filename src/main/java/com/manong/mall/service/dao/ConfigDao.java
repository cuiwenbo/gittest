package com.manong.mall.service.dao;

import com.manong.mall.service.BaseDaoImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by qjy on 2016/11/29.
 */

@Component("configDao")
public class ConfigDao extends BaseDaoImpl {

    public List<Map<String, String>> listConfigs() {
        return list("config.listConfigs", null);
    }

}
