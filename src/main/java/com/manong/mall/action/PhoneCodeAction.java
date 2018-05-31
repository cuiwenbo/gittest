package com.manong.mall.action;

import com.manong.mall.api.SmsService;
import com.manong.mall.bean.OperationResponse;
import com.manong.mall.bean.SmsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@Controller
@RequestMapping("/mobilecode")
public class PhoneCodeAction extends BaseAction{
	@Autowired
	SmsService smsService;

	@RequestMapping("/send")
	public void send(HttpServletRequest req, HttpServletResponse resp) {
		SmsDTO sms = new SmsDTO();
		String phone = getString(req, "phone", "");
		if (!isMobileNo(phone)) {
			writeJson(resp, "4000", "无效的手机号", null);
			return;
		}
		String authcode = new java.text.DecimalFormat("0000").format(new Random().nextDouble() * 10000);
		String type = getString(req, "type", "REG");
		sms.setCode(authcode);
		sms.setPhone(phone);
		sms.setFromuser(0);
		sms.setType(type);
		sms.setModel(0);
		sms.setExpirtymins(20);
		OperationResponse op = smsService.send(sms);
		if (op.isSuccess()) {
			writeJson(resp, "0000", "SUCCESS", null);
		}
	}

	@RequestMapping("/check")
	public void checkPhone(HttpServletRequest req, HttpServletResponse resp) {
		String phone = getString(req, "phone", "");
		if (!isMobileNo(phone)) {
			writeJson(resp, "4000", "无效的手机号", null);
			return;
		}
		String type = getString(req, "type", "");
		String code = getString(req, "code", "");
		OperationResponse ret = smsService.check("",phone, code, type, true);
		if(ret.isSuccess()) {
			writeJson(resp, "0000","手机验证码已发送，请收到短信后输入，10分钟有效", null);
		} else {
			writeJson(resp, "3001", ret.getMessage(), null);
		}
	}
}
