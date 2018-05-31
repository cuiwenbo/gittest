package com.manong.mall.service;


import com.manong.mall.bean.UserBean;
import com.manong.mall.bean.UserExtBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("userService")
public class UserService extends BaseServiceImpl{
	public UserBean getUserByPhone(String phone) {
		UserBean u = (UserBean)get("user.getByPhone", phone);
		return u;
	}
	//注册
	public int regUser(UserBean user,Map o){

		try {

			add("user.register",user);
			o.put("uid", user.getId());
			add("user.addExt", o);
		} catch (Exception e){
			return FAILED;
		}

		return SUCCESS;
		//return add(MyConstants.DB_MAIN, "user.register",user);
	}
	//登录
	public UserBean loginUser(String phone){
		//Map map=new HashMap();
		//map.put("phone", phone);
		//map.put("passwd", passwd);
		return (UserBean)get("user.login", phone);
	}
	//修改密码
	/*public int EditPwd(String passwd,long id){
		Map map=new HashMap();
		map.put("id", id);
		map.put("passwd", passwd);
		return update(MyConstants.DB_MAIN, "user.updatePwd", map);
	}*/
	public boolean editPasswd(UserBean user){
		return update( "user.updatePwd", user) > 0;
	}
	//修改用户基本信息
	public int editUserinfo(UserBean user){
		return update("user.editInfo", user);
	}
	public void addUserAction(Map p) {
		add( "user.action", p);
	}
	public void addUserLoginLog(long uid, String ip) {
		Map p = new HashMap();
		p.put("uid", uid);
		p.put("loginip", ip);
		add( "user.loginlog", p);
	}
	public UserExtBean getExt(long id){
		return (UserExtBean)get("user.getExt", id);
	}
	
	public int setLoginPhone(UserBean user){
		return update("user.setPhone",user);
	}
}
