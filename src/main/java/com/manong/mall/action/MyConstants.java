package com.manong.mall.action;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public  class MyConstants {
	
	public  static String DB_MAIN = "main";
	public  static String FILE_PATH="X:/TomcatImageRoot/webag/";
	public  static String WEB_PATH="/img/webag/";
	public  static String IMG_HOST = "http://local.hilinli.com";
	public static String PATH_DESK = "D:\\DEV\\workspace\\wd_web\\webroot\\upload\\QrCode\\";
	public static String PATH_LINK = "http://local.hilinli.com/wd_web/m/goods";
	
	public static Map<String,String> data = new HashMap();

	public static String get(String key) {
		if(key == null) return null;
		return data.get(key);
	}
	public static int getInt(String key, int d) {
		String v = get(key);
		if(v == null) return d;
		try {
			return Integer.parseInt(v);
		} catch (Exception e) {
			return d;
		}
	}
	public static long getLong(String key, long d) {
		String v = get(key);
		if(v == null) return d;
		try {
			return Long.parseLong(v);
		} catch (Exception e) {
			return d;
		}
	}
	public static double getDouble(String key, double d) {
		String v = get(key);
		if(v == null) return d;
		try {
			return Double.parseDouble(v);
		} catch (Exception e) {
			return d;
		}
	}
	
	public void init() {
		System.out.println("===========loading constants.properties===============");
		Properties properties = new Properties();
		try {
//			String path = getClass().getResource("/").getPath();
//			System.out.println(path);
			InputStream inputStream = this.getClass().getResourceAsStream("/constants.properties");
			properties.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		Enumeration keys = properties.keys();
		while(keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			String value = properties.getProperty(key);
			System.out.println(key + ":" + value);
			if(key != null && value != null && value.length() > 0) {
				data.put(key, value);
			}
		}
		String d = get("dbmaster");
		d = get("dbmain");
		if(d != null) {
			DB_MAIN = d;
		}
		d = get("filepath");
		if(d != null) FILE_PATH = d;
		d = get("webpath");
		if(d != null) WEB_PATH = d;
		d = get("imghost");
		if(d != null) IMG_HOST = d;
		d = get("path_desk");
		if(d != null) PATH_DESK = d;
		d = get("path_link");
		if(d != null) PATH_LINK = d;
	}
}
