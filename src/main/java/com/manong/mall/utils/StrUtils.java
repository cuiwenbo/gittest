package com.manong.mall.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangdk
 * @version 创建时间：2016年5月27日 下午4:50:39 类说明:
 */
public class StrUtils {

	public static String formatStringNull(Object resource) {
		if (resource == null) {
			return "";
		}
		if (resource instanceof String) {
			return (String) resource;
		}
		return null;
	}

	public static int getRandom(int min, int max) {
		if (min > max)
			return 0;
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	/**
	 * 验证手机号码 @param mobiles @return [0-9]{5,9}
	 */

	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[6-8])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// 取出每一个字符
			char c = string.charAt(i);

			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}

	public static String getCallTimes(long seconds) {
		long hour = seconds / 3600;
		long hourLeft = seconds % 3600;
		long minute = hourLeft / 60;
		long second = hourLeft % 60;

		String format = null;
		Object[] args = null;
		if (hour > 0) {
			format = "%d小时%02d分钟%02d秒";
			args = new Object[] { hour, minute, second };
		} else if (minute > 0) {
			format = "%d分钟%02d秒";
			args = new Object[] { minute, second };
		} else {
			format = "%d秒";
			args = new Object[] { second };
		}
		return String.format(format, args);
	}

	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}

					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();

	}

	/**
	 * 检查名字是否一致
	 * 
	 * @param name-原始数据
	 * @param name2
	 * @return
	 */
	public static boolean nameMatch(String name, String name2) {
		if (name == null || name2 == null || name.trim().length() < 2 || name2.trim().length() == 0)
			return false;

		name = name.trim();
		name2 = name2.trim();

		int l = name2.length();
		for (int i = 0; i < l; i++) {
			char c = name2.charAt(i);
			if (c < 19968 || c > 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
				continue;
			}
			if (name.indexOf(c) >= 0) {
				return true;
			}
		}
		return false;

		/*
		 * 
		 * name2 = name2.replaceAll("＊", "*"); if (name2.indexOf('*') > -1) {
		 * int l = name.length(); for (int i = 0; i < l; i++) { char c =
		 * name.charAt(i); if (c < 19968 || c > 171941) {// 汉字范围 \u4e00-\u9fa5
		 * (中文) continue; } if (name.indexOf(c) == -1) { return false; } }
		 * return true; } else { if (!name.equals(name2)) { return false; } else
		 * { return true; } }
		 */
	}

	/**
	 * 检查身份证是否匹配
	 * 
	 * @param id
	 * @param id2
	 * @return
	 */
	public static boolean idnoMatch(String id, String id2) {
		if (id == null || id2 == null || id.trim().length() == 0 || id2.trim().length() == 0)
			return false;
		id = id.trim().toUpperCase();
		id2 = id2.trim().toUpperCase();
		id2 = id2.replaceAll("＊", "*");
		int idx1 = id2.indexOf("*");
		int idx2 = id2.lastIndexOf("*");
		String id3 = null;
		String id4 = null;
		if (idx1 == -1) {
			id3 = id;
			id4 = id2;
		} else if (idx1 == 0) {
			id4 = id2.substring(idx2 + 1);
			id3 = id.substring(id.length() - id4.length());
		} else {
			if (idx2 == (id2.length() - 1)) {
				id4 = id2.substring(0, idx1);
				id3 = id.substring(0, idx1);
			} else {
				id4 = id2.substring(0, idx1);
				id3 = id.substring(0, idx1);

				String tid4 = id2.substring(idx2 + 1);
				String tid3 = id.substring(id.length() - tid4.length());
				id4 += "-" + tid4;
				id3 += "-" + tid3;
			}
		}
		System.out.println("CHECK IDNO:" + id3 + "," + id4);
		if (id3 == null || id4 == null) {
			return false;
		} else {
			if (id3.equals(id4)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static String getHttp(String url) throws Exception {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		int code = client.executeMethod(method);
		if (code != 200)
			throw new Exception(code + "");
		String result = new String(method.getResponseBody(), "UTF-8");
		return result;

	}

	public static JSONObject getCity(String ip) {
		String ret = "";
		String uri = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip;
		try {
			String data = getHttp(uri);
			return JSONObject.parseObject(data);
		} catch (Exception e) {
			return null;
		}
	}
	public static String getCityByIp(String ip) {
		String ret = "";
		String uri = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip;
		// String uri =
		// "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="
		// + ip;
		try {
			String data = getHttp(uri);
			if(StringUtils.isEmpty(data) || !data.trim().startsWith("{"))
				return null;
			JSONObject o = JSONObject.parseObject(data);
			System.out.println(o.toJSONString());
			ret = o.getString("city");
			if (ret != null && ret.trim().length() > 0)
				return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getCallTime(long callTime) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = callTime / dd;
		long hour = (callTime - day * dd) / hh;
		long minute = (callTime - day * dd - hour * hh) / mi;
		long second = (callTime - day * dd - hour * hh - minute * mi) / ss;

		String strHour = hour < 10 ? "0" + hour : "" + hour;// 小时
		String strMinute = minute < 10 ? "0" + minute : "" + minute;// 分钟
		String strSecond = second < 10 ? "0" + second : "" + second;// 秒
		return strHour + "小时" + strMinute + "分钟" + strSecond + "秒";
	}

	public static String getDataTraffic(List datas) {
		if (datas == null || datas.isEmpty()) {
			return "0KB";
		}
		Pattern p = Pattern.compile("((\\d{1,})(?:GB))*((\\d{1,})(?:MB))*((\\d{1,})(?:KB))*");
		Pattern p_trim = Pattern.compile("\\s*|\t|\r|\n");
		long total = 0;
		Matcher m = null;
		for (Object data : datas) {
			String pre_data = (String) data;
			if (!StringUtils.isBlank(pre_data)) {
				m = p_trim.matcher(pre_data);
				pre_data = m.replaceAll("");
			}
			m = p.matcher(pre_data.toUpperCase());
			if (m.find()) {
				String lg = m.group(2);
				String lm = m.group(4);
				String lk = m.group(6);
				if (!StringUtils.isBlank(lg)) {
					total += Long.parseLong(lg) * 1024 * 1024;
				}
				if (!StringUtils.isBlank(lm)) {
					total += Long.parseLong(lm) * 1024;
				}
				if (!StringUtils.isBlank(lk)) {
					total += Long.parseLong(lk);
				}
			}
		}
		long gb = total / 1024 / 1024;
		long gbLeft = total % (1024 * 1024);
		long mb = gbLeft / 1024;
		long kb = gbLeft % 1024;

		String format = null;
		Object[] args = null;
		if (gb > 0) {
			format = "%dGB %dMB %dKB";
			args = new Object[] { gb, mb, kb };
		} else if (mb > 0) {
			format = "%dMB %dKB";
			args = new Object[] { mb, kb };
		} else {
			format = "%dKB";
			args = new Object[] { kb };
		}

		return String.format(format, args);
	}

	public static String getDoubleTraffic(List datas) {
		if (datas == null || datas.isEmpty()) {
			return "0KB";
		}
		Pattern p = Pattern.compile("((.*?)(?:GB))*((.*?)(?:MB))*((.*?)(?:KB))*");
		Pattern p_trim = Pattern.compile("\\s*|\t|\r|\n");
		double total = 0.0;
		Matcher m = null;
		for (Object data : datas) {
			String pre_data = (String) data;
			if (!StringUtils.isBlank(pre_data)) {
				m = p_trim.matcher(pre_data);
				pre_data = m.replaceAll("");
			}
			m = p.matcher(pre_data.toUpperCase());
			if (m.find()) {
				String lg = m.group(2);
				String lm = m.group(4);
				String lk = m.group(6);
				if (!StringUtils.isBlank(lg)) {
					total += Double.parseDouble(lg) * 1024 * 1024;
				}
				if (!StringUtils.isBlank(lm)) {
					total += Double.parseDouble(lm) * 1024;
				}
				if (!StringUtils.isBlank(lk)) {
					total += Double.parseDouble(lk);
				}
			}
		}
		long tkb = (int) total;
		String temp = String.valueOf(total);
		double decimalPart = Double.parseDouble(temp.substring(temp.indexOf("."), temp.length()));
		DecimalFormat df = new DecimalFormat("0.00");
		decimalPart = new Double(df.format(decimalPart));
		long gb = tkb / 1024 / 1024;
		long gbLeft = tkb % (1024 * 1024);
		long mb = gbLeft / 1024;
		double kb = gbLeft % 1024 + decimalPart;
		String format = null;
		Object[] args = null;
		if (gb > 0) {
			format = "%sGB %sMB %sKB";
			args = new Object[] { gb, mb, kb };
		} else if (mb > 0) {
			format = "%sMB %sKB";
			args = new Object[] { mb, kb };
		} else {
			format = "%sKB";
			args = new Object[] { kb };
		}
		return String.format(format, args);
	}

	public static String getDataTraffic(String datas) {
		if (datas == null || datas.isEmpty()) {
			return "0KB";
		}
		Pattern p = Pattern.compile("((\\d{1,})(?:GB))*((\\d{1,})(?:MB))*((\\d{1,})(?:KB))*");
		Pattern p_trim = Pattern.compile("\\s*|\t|\r|\n");
		long total = 0;
		Matcher m = null;
		if (!StringUtils.isBlank(datas)) {
			m = p_trim.matcher(datas);
			datas = m.replaceAll("");
		}
		m = p.matcher(datas.toUpperCase());
		if (m.find()) {
			String lg = m.group(2);
			String lm = m.group(4);
			String lk = m.group(6);
			if (!StringUtils.isBlank(lg)) {
				total += Long.parseLong(lg) * 1024 * 1024;
			}
			if (!StringUtils.isBlank(lm)) {
				total += Long.parseLong(lm) * 1024;
			}
			if (!StringUtils.isBlank(lk)) {
				total += Long.parseLong(lk);
			}
		}
		long gb = total / 1024 / 1024;
		long gbLeft = total % (1024 * 1024);
		long mb = gbLeft / 1024;
		long kb = gbLeft % 1024;

		String format = null;
		Object[] args = null;
		if (gb > 0) {
			format = "%dGB %dMB %dKB";
			args = new Object[] { gb, mb, kb };
		} else if (mb > 0) {
			format = "%dMB %dKB";
			args = new Object[] { mb, kb };
		} else {
			format = "%dKB";
			args = new Object[] { kb };
		}

		return String.format(format, args);
	}

	public static String getDoubleTraffic(String datas) {
		if (datas == null || datas.isEmpty()) {
			return "0KB";
		}
		Pattern p = Pattern.compile("((.*?)(?:GB))*((.*?)(?:MB))*((.*?)(?:KB))*");
		Pattern p_trim = Pattern.compile("\\s*|\t|\r|\n");
		double total = 0.0;
		Matcher m = null;
		if (!StringUtils.isBlank(datas)) {
			m = p_trim.matcher(datas);
			datas = m.replaceAll("");
		}
		m = p.matcher(datas.toUpperCase());
		if (m.find()) {
			String lg = m.group(2);
			String lm = m.group(4);
			String lk = m.group(6);
			if (!StringUtils.isBlank(lg)) {
				total += Double.parseDouble(lg) * 1024 * 1024;
			}
			if (!StringUtils.isBlank(lm)) {
				total += Double.parseDouble(lm) * 1024;
			}
			if (!StringUtils.isBlank(lk)) {
				total += Double.parseDouble(lk);
			}
		}
		long tkb = (int) total;
		String temp = String.valueOf(total);
		double decimalPart = Double.parseDouble(temp.substring(temp.indexOf("."), temp.length()));
		DecimalFormat df = new DecimalFormat("0.00");
		decimalPart = new Double(df.format(decimalPart));
		long gb = tkb / 1024 / 1024;
		long gbLeft = tkb % (1024 * 1024);
		long mb = gbLeft / 1024;
		double kb = gbLeft % 1024 + decimalPart;
		String format = null;
		Object[] args = null;
		if (gb > 0) {
			format = "%sGB %sMB %sKB";
			args = new Object[] { gb, mb, kb };
		} else if (mb > 0) {
			format = "%sMB %sKB";
			args = new Object[] { mb, kb };
		} else {
			format = "%sKB";
			args = new Object[] { kb };
		}
		return String.format(format, args);
	}

	public static int getAge(String idno) {
		String birth = "";		
		if(idno.length() == 18) {
			birth = idno.substring(6, 14);
		} else if(idno.length() == 15) {
			birth = "19" + idno.substring(6, 12); 					
		}		
		String today = new java.text.SimpleDateFormat("yyyyMMdd").format(new Date());
		int age = 0;
		try {
			age = Integer.parseInt(today.substring(0, 4)) - Integer.parseInt(birth.substring(0, 4));
			if(Integer.parseInt(birth.substring(4)) > Integer.parseInt(today.substring(4))) age --;
		} catch (Exception e) {
			age = 0;
		}
		return age;
	}
	public static JSONObject getMobileInfo(String phone) {
		String data = "";
		try {
			data = getHttp("http://sj.apidata.cn/?mobile=" + phone);
		} catch (Exception e) {
			return null;
		}
		JSONObject item = JSONObject.parseObject(data);
		JSONObject jp = new JSONObject();
		String province = "";
		String city = "";
		String isp = "";
		String types = "";
		String mobile = "";
		String status = "";
		if (item.getString("status").equals("1")) // 成功 1
		{
			jp.put("province", item.getJSONObject("data").getString("province"));
			jp.put("city", item.getJSONObject("data").getString("city"));
			jp.put("isp", item.getJSONObject("data").getString("isp"));
			jp.put("types", item.getJSONObject("data").getString("types"));
			jp.put("mobile", item.getJSONObject("data").getString("mobile"));
			jp.put("status", 1);
		} else { // 请求返回错误信息 2
			jp.put("status", 2);
		}
		return jp;
	}
	public static  String leftSub(String str, int len) {
		if(str == null) return "";
		return str.substring(0, str.length() < len?str.length():len);
	}

	public static void main(String[] args) {
		//System.out.println(getCity("121.199.48.88"));
		//if(true) return;
//		System.out.println(getMobileInfo("15988199065"));
//		if(true) return;
//		System.out.println(getAge("332621961012607"));
//		if(true) return;
		
		//System.out.println(nameMatch("叶蓝康", "叶蓝康"));
		System.out.println(idnoMatch("330726199310101712","3****************2"));
//		List datas = new ArrayList<>();
//		datas.add("479.99MB 653.2KB");
//		datas.add("598.11MB 648.1KB");
//		datas.add("176MB 172.0KB");
//		datas.add("2048MB");
//		datas.add("598MB 648KB");
//		System.out.println(getDoubleTraffic(datas));
		// System.out.println(StringEscapeUtils.unescapeHtml(
		// "<tr><td>&#26519;&#x63D0;&#x793A;&#x4FE1;&#x606F;&#xFF1A;&#x8BBF;&#x95EE;&#x5931;&#x8D25;</td></tr>"));
	}

	public static String between(String str, String start, String end) {
		int startInd = str.indexOf(start);

		if (startInd > -1) {
			int endInd = str.indexOf(end, startInd + start.length());
			if (endInd > -1) {
				return str.substring(startInd + start.length(), endInd);
			}
		}

		return "";
	}
}
