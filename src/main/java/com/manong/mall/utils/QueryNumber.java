package com.manong.mall.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by huangtz on 2017/2/22.
 */
public class QueryNumber {

    public static JSONObject queryNumber(String phone) {
        JSONObject ret = new JSONObject();
        HttpResult result = HttpUtils.get("http://sj.apidata.cn/?mobile=" + phone);
        if (result.getCode() != 200) {
            ret.put("code", 3001);
            ret.put("message", "网络请求失败");
            return ret;
        }
        String data = result.getData();
        return JSONObject.parseObject(data);
    }

    public static String getAreaIsp(String phone) {
        JSONObject json = QueryNumber.queryNumber(phone);
        JSONObject data = json.getJSONObject("data");

        String ispName = (String) data.get("isp");
        if ("联通".equals(ispName)) {
            return "cucc";
        }

        String province = (String) data.get("province");
        String provinceCode = PinyinUtils.getProvince(province);
        String ispCode = "移动".equals(ispName) ? "Cmcc" : "Ctcc";
        return provinceCode + ispCode;
    }

    public static void main(String[] args) {
        QueryNumber q = new QueryNumber();
        JSONObject json = q.queryNumber("18270822146");
        JSONObject data = json;
        int state = data.getInteger("status");

        System.out.println(state);
        JSONObject jsons = data.getJSONObject("data");
        System.out.println(jsons.get("province"));
        System.out.println(json);
    }
}
