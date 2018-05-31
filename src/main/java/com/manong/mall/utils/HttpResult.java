package com.manong.mall.utils;

import lombok.Data;

/**
   * @author  作者:qujy
   * @date 创建时间：2016年3月4日 下午2:27:22 
   * @version 1.0
*/
@Data
public class HttpResult {
	int result = 0;
	String message = "";
	String type = "html";
	String data = "";	
	String cookies = null;
	byte[] file = null;
	int code;
}
