package com.manong.mall.action;

import com.manong.mall.api.FileService;
import com.manong.mall.api.SmsService;
import com.manong.mall.bean.OperationResponse;
import com.manong.mall.bean.UserBean;
import com.manong.mall.service.IDService;
import com.manong.mall.service.UserService;
import com.manong.mall.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction{

	@Autowired
	UserService userService;

	@Autowired
	IDService idService;

	@Autowired
	FileService fileService;

	@Autowired
	SmsService smsService;

	/**
	 * 检查手机号是否已经注册
	 */
	@RequestMapping("/checkPhoneUsed")
	public void checkphone(HttpServletRequest req, HttpServletResponse resp) {
		String phone = getString(req, "phone", "");
		if(!isMobileNo(phone)) {
			writeJson(resp, "2001","无效的手机号",null);
			return;
		}
		UserBean u = userService.getUserByPhone(phone);
		if(u != null) {
			writeJson(resp, "2002","该手机号已经注册", null);
		} else {
			writeJson(resp, "0000", "", null);
		}		
	}
	@RequestMapping("/reg")
	public String reg(HttpServletRequest req) {

		req.setAttribute("op", "addUser");
		
		//检验验证码
		String phone=getString(req, "phone", "");
		if(userService.getUserByPhone(phone)!=null){
			req.setAttribute("result", "2002");
			req.setAttribute("message", "该用户已存在");
			return "/afterop";
		}
		String code = getString(req, "phonecode","");
		OperationResponse checkret = smsService.check("",phone, code, "REG", true);
		if(!checkret.isSuccess()) {
			req.setAttribute("result", "2002");
			req.setAttribute("message", "无效验证码");
			return "/afterop";
		}	
		//

		UserBean user=new UserBean();
		String passwd= MD5.md5(getString(req, "passwd", ""));

		long id=idService.getId("tb_user");
		if(id<0){
			req.setAttribute("result", "2002");
			req.setAttribute("message", "系统异常");
			return "/afterop";
		}
		user.setId(id);
		
		user.setPhone(phone);
		user.setPasswd(passwd);
		user.setNick(getString(req, "nick", null));
		Map map=new HashMap();
		map.put("regip", getip(req));
		
		
		int ret=userService.regUser(user,map);
		if(ret!=SUCCESS){
			req.setAttribute("result", "2002");
			req.setAttribute("message","系统错误,注册失败");
			return "/afterop";
		}
		setLoginUser(req, user);
		req.setAttribute("result", "0000");
		req.setAttribute("message","注册成功");
		req.setAttribute("data", getLastUrl(req));
		
		return "/afterop";
	}
	/**
	 * 登录
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest req){
		String phone=getString(req, "phone", "").trim();
		String passwd=getString(req, "passwd", "").trim();
		
		req.setAttribute("op", "loginUser");
		UserBean user=userService.loginUser(phone);
		String md5_passwd=MD5.md5(passwd);
		if(user==null){
			req.setAttribute("result", "1002");
			req.setAttribute("message","该用户尚未注册");
			return "/afterop";
		}
		else if(!md5_passwd.equals(user.getPasswd())){
			req.setAttribute("result", "1003");
			req.setAttribute("message","密码输入错误");
			return "/afterop";
		}
		else if(user.getState()!=0){
			req.setAttribute("result", "1002");
			req.setAttribute("message","该用户已停用");
			return "/afterop";
		}
		else{
			setLoginUser(req, user);
			userService.addUserLoginLog(user.getId(), getip(req));
			req.setAttribute("result", "0000");
			req.setAttribute("message","登录成功");
			req.setAttribute("data", getLastUrl(req));
			return "/afterop";
		}
		
	//	return "afterop";
	}
	/**
	 * 退出登录
	 */
	@RequestMapping("/logout")
	public String logOut(HttpServletRequest req){
		clearLoginUser(req);
		return "redirect:/login.html";
	}
	/**
	 * 修改用户密码
	 */
	@RequestMapping("/editPwd")
	public void EditPwd(HttpServletRequest req, HttpServletResponse resp){
		UserBean user=getLoginUser(req);
		if(user==null){
			writeJson(resp, "2001", "请先登录",null);
			return ;
		}
		String pwd=MD5.md5(getString(req, "passwd", "").trim());
		if(!pwd.equals(user.getPasswd())){
			writeJson(resp, "2002", "原密码输入错误", null);
			return ;
		}
		String passwdNew=getString(req, "passwdNew", "").trim();
		String passwd=MD5.md5(passwdNew); 
		
		//int ret=service.EditPwd(passwd, user.getId());
		user.setPasswd(passwd);
		//user.setId(user.getId());
		if(!userService.editPasswd(user)){
			writeJson(resp, "2003", "系统错误，修改失败", null);
			return ;
		}else{
			setLoginUser(req, user);
			writeJson(resp, "0000", "修改成功，请重新进行登录", null);
			return ;
		}
	}
	@RequestMapping("/setLoginPhone")
	public void setLoginPhone(HttpServletRequest req, HttpServletResponse resp){
		UserBean user=getLoginUser(req);
		if(user==null){
			writeJson(resp, "2001", "请先登录",null);
			return ;
		}
		String phone=getString(req, "phone", "");
		String phonecode=getString(req, "phonecode", "");
		OperationResponse respret = smsService.check("", phone, phonecode, "REG", true);
		if(!respret.isSuccess()) {
			writeJson(resp, "2002", "无效验证码", null);
			return;
		}	
		user.setPhone(phone);
		if(userService.setLoginPhone(user) == -1){
			writeJson(resp, "2003", "系统错误，修改失败", null);
			return ;
		}
		setLoginUser(req, user);
		writeJson(resp, "0000", "修改成功，请重新登录", null);
	}
	/**
	 * 得到user相关
	 */
	@RequestMapping("/getUserInfo")
	public String getuserinfo(HttpServletRequest req){
		UserBean user=getLoginUser(req);
		req.setAttribute("op", "editinfo");
		if(user==null){
			req.setAttribute("result", "2002");
			req.setAttribute("message","请先登录");
			return "/afterop";
		}
		req.setAttribute("u", user);
		return "/user/editUserinfo";
	}
	/**
	 *修改用户基本信息 
	 */
	@RequestMapping("/editUserInfo")
	public String  editUserInfo(HttpServletRequest req){

		UserBean user=getLoginUser(req);
		req.setAttribute("op", "editinfo");
		if(user==null){
			req.setAttribute("result", "2002");
			req.setAttribute("message","请先登录");
			return "/afterop";
		}
		req.setAttribute("user", user);
		String nick=getString(req, "nick", "").trim();
		String sign=getString(req,"sign", "").trim();
		String pict=getString(req, "picts", "");
		user.setNick(nick);
		user.setSign(sign);
		user.setPict(pict);
		user.setId(getLong(req, "id", 0));
		int ret=userService.editUserinfo(user);
		if(ret==-1){
			req.setAttribute("result", "2002");
			req.setAttribute("message","系统错误");
			return "/afterop";
		}
		setLoginUser(req, user);
		req.setAttribute("result", "0000");
		req.setAttribute("message","修改成功");
		return "/afterop";
	}
	@RequestMapping("/getforgetPwd")
	public String getforgetPwd(HttpServletRequest req){
		String phone=getString(req, "phone", "");
		String code=getString(req, "phonecode", "");
		String newPwd=getString(req, "passwd", "");
		String passwd=MD5.md5(newPwd);
		
		String pwd2=getString(req, "passwd2", "");
		//得到验证码
		UserBean user=userService.loginUser(phone);
		req.setAttribute("op", "editPwd");
		if(user==null){
			req.setAttribute("result", "2001");
			req.setAttribute("message", "该号码尚未注册，请检查");
			return "/afterop";
		}
		OperationResponse checkret = smsService.check("",phone, code, "REG", true);
		if(!checkret.isSuccess()) {
			req.setAttribute("result", "2002");
			req.setAttribute("message", "无效验证码");
			return "/afterop";
		}	
		user.setPasswd(passwd);

		if(!userService.editPasswd(user)){
			req.setAttribute("result", "2003");
			req.setAttribute("message", "系统异常，找回失败");
			return "/afterop";
		}
		req.setAttribute("result", "0000");
		req.setAttribute("message", "ok");
		return "/afterop";
	}
}
