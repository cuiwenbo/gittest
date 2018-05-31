package com.manong.mall.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qjy on 2017/8/24.
 */

public abstract class BaseBean implements Serializable {
    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
    public static DecimalFormat nf = new DecimalFormat("0.00");
    private static final long serialVersionUID = 1L;
    //	public String toJsonString() {
//		JSONObject obj = JSONObject.fromObject(this);
//		String t = obj.toString();
//		t = t.replaceAll("\\\"", "\"");
//		return t;
//	}
//	public String toString() {
//		return toJsonString();
//	}
    public Map toMap() {
        try {
            return convertBean();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public  Map convertBean()    throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = this.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(this, new Object[0]);
                if (result != null) {
                    if(result instanceof Date) {
                        Date tmp_d = (Date)result;
                        returnMap.put(propertyName, df.format(tmp_d));
                    } else {
                        returnMap.put(propertyName, result);
                    }
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
}
