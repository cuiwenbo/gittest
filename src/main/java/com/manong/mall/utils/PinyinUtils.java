package com.manong.mall.utils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by huangtz on 2017/2/27.
 */
public class PinyinUtils {
    public static String getProvince(String province) {
        Map<String, String> total = new HashMap<>();

        total.put("北京", "beijing");
        total.put("天津", "tianjin");
        total.put("上海", "shanghai");
        total.put("浙江", "zhejiang");
        total.put("安徽", "anhui");
        total.put("福建", "fujian");
        total.put("江西", "jiangxi");
        total.put("湖南", "hunan");
        total.put("山东", "shandong");
        total.put("河南", "henan");
        total.put("内蒙古", "neimenggu");
        total.put("湖北", "hubei");
        total.put("宁夏", "ningxia");
        total.put("新疆", "xinjiang");
        total.put("广东", "guangdong");
        total.put("西藏", "xizang");
        total.put("海南", "hainan");
        total.put("广西", "guangxi");
        total.put("四川", "sichuan");
        total.put("河北", "hebei");
        total.put("贵州", "guizhou");
        total.put("重庆", "chongqing");
        total.put("山西", "shanxi");
        total.put("云南", "yunnan");
        total.put("辽宁", "liaoning");
        total.put("陕西", "shaanxi");
        total.put("吉林", "jilin");
        total.put("甘肃", "gansu");
        total.put("黑龙江", "heilongjiang");
        total.put("青海", "qinghai");
        total.put("江苏", "jiangsu");
        total.put("台湾", "taiwan");
        total.put("香港", "xianggang");
        total.put("澳门", "aomen");

        return total.get(province);
    }
}
