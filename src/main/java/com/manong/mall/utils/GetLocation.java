package com.manong.mall.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.net.URL;

/**
 * Created by huangtz on 2017/1/17.
 */
public class GetLocation {
    public static void main(String[] args) {
        // lat 39.97646
        //log 116.3039
        JSONObject add = getCityByLocation("120.3039", "30.17646");
        System.out.println(add.toJSONString());
    }

    /**
     * 根据经纬度获取城市信息
     * @param lnt 经度
     * @param lat 纬度
     * @return
     */
    public static JSONObject getCityByLocation(String lnt, String lat ){
        //lat 小  log  大
        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+lnt+"&type=010";
        String res = "";
        JSONObject ret = new JSONObject();
        ret.put("success", false);
        try {
            URL url = new URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line + "\n";
            }
            in.close();

            JSONObject jsonObject = JSONObject.parseObject(res);
            JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("addrList"));
            JSONObject j_2 = JSONObject.parseObject(jsonArray.get(0).toString());
            String admCode = j_2.getString("admCode");
            String admName = j_2.getString("admName");
            String[] names = admName.split(",");
            ret.put("code", admCode);
            ret.put("province", names[0]);
            ret.put("city", names[1]);
            ret.put("region", names[2]);
            ret.put("success", true);
        } catch (Exception e) {
            ret.put("success", false);
        }
        return ret;
    }

}
