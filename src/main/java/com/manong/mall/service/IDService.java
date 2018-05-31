package com.manong.mall.service;

import com.manong.mall.utils.CacheTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("idService")
public class IDService extends BaseServiceImpl{
	long tid = 0;

	@Autowired
	CacheTools cacheTools;


	public long getId(String table) {		
		String key = "WB_DB_ID:" + table;		
		long ret = cacheTools.incr(key, 1);
		if(ret < 10001) {
			Long tid = (Long) get("getId_" + table.toLowerCase(), null);
			if (tid == null) ret = 0;
			else ret = tid;
		}
		if(ret < 10001) ret = 10001;
		cacheTools.delString(key);
		ret = cacheTools.incr(key, ret);
		return ret;
	}	
}
