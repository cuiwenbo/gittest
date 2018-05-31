package com.manong.mall.action;

import com.alibaba.fastjson.JSONObject;
import com.manong.mall.bean.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseAction {
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
	
	public static DecimalFormat ndf = new DecimalFormat("0.00");
	public static DecimalFormat ndf8 = new DecimalFormat("00000000");
	public static final int SUCCESS = 0;
	public static final int FAILED = -1;

	String LoginSessionKey = "WebBagLoginUser";
	public UserBean getLoginUser(HttpServletRequest req) {
		try {
			return (UserBean) (req.getSession().getAttribute(LoginSessionKey));
		} catch (Exception e) {
			return null;
		}
	}
	public void setLoginUser(HttpServletRequest req, UserBean ub) {
		req.getSession().setAttribute(LoginSessionKey, ub);
	}
	public void clearLoginUser(HttpServletRequest req) {
		req.getSession().removeAttribute(LoginSessionKey);
	}

	public void getPageInfo(HttpServletRequest req, Page page) {
		int pageindex = 0;
		int pagesize = 0;
		try {
			pageindex = getInt(req, "pageIndex", 1);
			pagesize = getInt(req, "pageSize", 20);
		} catch(Exception e){};
		if(pageindex < 1) pageindex = 1;
		if(pagesize < 1) pagesize = 20;
		page.setPageIndex(pageindex);
		page.setPageSize(pagesize);
	}

	public Object getSession(HttpServletRequest req, String key) {
		return req.getSession().getAttribute(key);
	}
	public void setSession(HttpServletRequest req, String key, Object v) {
		req.getSession().setAttribute(key, v);
	}

	public String getParam(HttpServletRequest req, String param) {
		if(param == null) return null;
		if(param.trim().length() == 0) return null;
		String ret = req.getParameter(param.trim());
		if(ret == null) return null;
		ret = ret.trim();
		if(ret.length() == 0) return null;
		return ret;
	}

	public boolean isMobileNo(String mobiles) {
		if (mobiles == null || mobiles.trim().length() != 11)
			return false;
		mobiles = mobiles.trim();
		String rex = "^((1[0-9]))\\d{9}$";//configService.get("phone.rex");
		Pattern p = Pattern.compile(rex);//"^((13[0-9])|(15[0-9])|(177)|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	public double getDouble(HttpServletRequest req, String param, double defaultvalue) {
		double ret = defaultvalue;
		try {
			ret = Double.parseDouble(req.getParameter(param));
		} catch (Exception e) {
			ret = defaultvalue;
		}
		return ret;
	}

	public int getInt(HttpServletRequest req, String param, int defaultvalue) {
		int ret = defaultvalue;
		try {
			ret = Integer.parseInt(req.getParameter(param));
		} catch (Exception e) {
			ret = defaultvalue;
		}
		return ret;
	}

	public long getLong(HttpServletRequest req, String param, long defaultvalue) {
		long ret = defaultvalue;
		try {
			ret = Long.parseLong(req.getParameter(param));
		} catch (Exception e) {
			ret = defaultvalue;
		}
		return ret;
	}

	public Date getDate(HttpServletRequest req, String param, String fmt) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(fmt);
			return df.parse(req.getParameter(param));
		} catch (Exception e) {
			return null;
		}
	}

	public String getString(HttpServletRequest req, String param, String def) {
		String ret = req.getParameter(param);
		if (ret == null || ret.trim().length() == 0)
			return def;
		return ret.trim();
	}

	public Map getParamMap(HttpServletRequest req) {
		// 参数Map
		Map properties = req.getParameterMap();
		// 返回值Map
		Map<String, Object> returnMap = new HashMap<String, Object>();

		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	public void writeJson(HttpServletResponse resp, String code, String msg, Object data) {
		JSONObject json = new JSONObject();
		try {
			json.put("result", code);
			if (msg != null && msg.trim().length() > 0) {
				json.put("message", msg);
			} else {
				json.put("message", "");
			}
			if (data != null) {
				if (data instanceof JSONObject) {
					json.put("data", (JSONObject) data);
				} else if (data instanceof List) {
					json.put("data", (List) data);
				} else if (data instanceof Map) {
					json.put("data", (Map) data);
				} else {
					json.put("data", data.toString());
				}
			}

			resp.setContentType("application/json;charset=UTF-8");
			resp.setCharacterEncoding("utf-8");
			resp.getWriter().write(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeJson(HttpServletResponse resp, JSONObject json){
		resp.setContentType("application/json;charset=UTF-8");
		resp.setCharacterEncoding("utf-8");
		try {
			resp.getWriter().write(json.toJSONString());
			resp.flushBuffer();
		} catch(Exception e) {
			///;
		}
	}

	protected void writeStr(HttpServletResponse resp, String data) {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("utf-8");
		try {
			resp.getWriter().write(data);
			resp.flushBuffer();
		} catch(Exception e) {
			///;
		}
	}

	public String getIp(HttpServletRequest req) {
		String frmip = req.getHeader("X-Real-IP");
		if (frmip == null || frmip.trim().length() == 0)
			frmip = req.getRemoteAddr();
		if(frmip == null || frmip.trim().length() == 0) frmip = "0.0.0.0";
		return frmip;
	}

	public void setLastUrl(HttpServletRequest req) {
		String reqstr = req.getRequestURI() + "?" + req.getQueryString();
		req.getSession().setAttribute("LastUrl", reqstr);
	}
	public String getLastUrl(HttpServletRequest req) {
		try {
			String ret  =  (String) req.getSession().getAttribute("LastUrl");
			clearLastUrl(req);
			if(ret == null) return "";
			return ret;
		} catch (Exception e) {
			return "";
		}
	}
	public void clearLastUrl(HttpServletRequest req) {
		req.getSession().removeAttribute("LastUrl");
	}
	public String getip(HttpServletRequest req){
		String frmip = req.getHeader("x-real-ip");
		if(frmip == null || frmip.trim().length() == 0)
			frmip = req.getRemoteAddr();
		return frmip;
	}
}
