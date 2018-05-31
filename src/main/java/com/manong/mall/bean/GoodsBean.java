package com.manong.mall.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class GoodsBean extends BaseBean {
	long id;//` bigint(20) NOT NULL DEFAULT '0',
	long sid;//` bigint(20) DEFAULT '0' COMMENT '商户id',
	String name;//` varchar(100) DEFAULT NULL COMMENT '商品名称',
	 String picts;//` varchar(2000) DEFAULT NULL COMMENT '附加图片',
	 String content;//` varchar(2000) DEFAULT NULL COMMENT '内容描述',
	 double price;//` double(9,2) DEFAULT '0.00' COMMENT '价格',
	 String unit;//` varchar(10) DEFAULT NULL COMMENT '计量单位',
	 String param;//` varchar(2000) DEFAULT NULL COMMENT '参数',
	 double amount;// double(12,2) DEFAULT '0.00' COMMENT '总数量',
	 double orderamount;//` double(12,2) DEFAULT '0.00' COMMENT '订单数量',
	  double userlimit;//` double(12,2) DEFAULT '0.00' COMMENT '限购数量',
	  Date optime;//` datetime DEFAULT NULL COMMENT '更新时间',
	  String deliverymethod;//` varchar(20) DEFAULT NULL COMMENT '送货方式：0，快递 1：自提',
	  double deliveryfee;//` double(9,2) DEFAULT NULL COMMENT '送货金额',
	  int state;//` int(1) DEFAULT '0' COMMENT '0:正常 9:下架',
	  int  recommend;//` int(1) DEFAULT '0' COMMENT '1.推荐 0.',
	  long evalcount;//` bigint(20) DEFAULT '0' COMMENT '评价次数',
	 // char remarks;//` char(10) DEFAULT NULL COMMENT '备注信息',
	 long  pid;//` bigint(20) DEFAULT '0' COMMENT '原始商品id',
	  int isvalid;//` int(1) DEFAULT '0' COMMENT '0：有效，1:已被修改失效',
	  Date unvalidtime;//` datetime DEFAULT NULL COMMENT '失效时间',
	  long showtimes;
	  double oprice;//` double(9,2) DEFAULT '0.00' COMMENT '商品原价',
	  String paytype;//` varchar(40) DEFAULT '线下支付' COMMENT '支付方式',
	  JSONArray jparam;
	  List paramList;
	  public List getParamList(){		
			List<String[]> ret = new ArrayList();
			try {
				if(param == null || param.length() < 4) return null;
				String param1= param.substring(1, param.length() - 1);
				
				String[] t1 = param1.split(",");
				
				for(String t:t1) {
					String t2 = t.substring(1, t.length() - 1);
					String[] t3 = t2.split(":");
					//String t4=t3.
					if(t3 != null && t3.length == 2) 
						{
						t3[0]=t3[0].substring(1, t3[0].length()-1);
						t3[1]=t3[1].substring(1, t3[1].length()-1);
						ret.add(t3);}			
				}			
	    		} catch (Exception e){};
	    		return ret;
		}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSid() {
		return sid;
	}
	public void setSid(long sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicts() {
		return picts;
	}
	public String[] getPicta() {
		if(picts == null) return null;
		return picts.split(",");
	}
	public void setPicts(String picts) {
		this.picts = picts;
	}
	public String getMainPict() {
		String de = "";
		if(picts == null || picts.length() == 0) return de;
		String[] ps = picts.split(",");
		if(ps == null || ps.length == 0) return de;
		return ps[0];
	}
	public void setParam(String param) {
		this.param = param;
		try {
    			jparam = JSONArray.parseArray(param);
    		} catch (Exception e){};      
	}
	public String getPn(int idx){
		if(jparam != null && jparam.size() > idx) {
			try {
				return jparam.getJSONObject(idx).getString("pname");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return "";		
	}
	public String[] getPva(int idx) {
		String r = getPv(idx);
		if(r == null) return null;
		return r.split(",");
	}
	public String getPv(int idx){
		String ret = "";
		if(jparam != null && jparam.size() > idx) {
			try {
				JSONArray r1  = jparam.getJSONObject(idx).getJSONArray("pvalues");
				for(int i = 0; i < r1.size(); i ++){
					if(i > 0) ret += ",";
					ret += r1.getString(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return ret;			
	}
}
