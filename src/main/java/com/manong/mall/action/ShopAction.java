package com.manong.mall.action;

import com.alibaba.fastjson.JSONObject;
import com.manong.mall.api.SmsService;
import com.manong.mall.bean.*;
import com.manong.mall.service.IDService;
import com.manong.mall.service.ShopService;
import com.manong.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/shop")
public class ShopAction extends BaseAction{
	@Autowired
	SmsService smsService;

	@Autowired
	ShopService shopService;

	@Autowired
	UserService userService;

	@Autowired
	IDService idService;

	/**
	 * 添加店铺
	 * 
	 */
	@RequestMapping("/start")
	public String shopPage(HttpServletRequest req){
		UserBean user=getLoginUser(req);
		req.setAttribute("op", "shopinfo");
		if(user==null){
			req.setAttribute("result", "2002");
			req.setAttribute("message","请先登录");
			return "/afterop";
		}
		req.setAttribute("u",user);
		return "/shop/addStore";
	}
	@RequestMapping("/add")
	public String addShop(HttpServletRequest req){
		UserBean user=getLoginUser(req);
		req.setAttribute("op", "addshop");
		if(user==null){
			req.setAttribute("result", "2002");
			req.setAttribute("message","请先登录");
			return "/afterop";
		}
		String phone=getString(req, "phone", "").trim();//联系人电话
		String code = getString(req, "phonecode","");

		OperationResponse ret = smsService.check("", phone, code, "REG", true);
		if(!ret.isSuccess()) {
			req.setAttribute("result", "2002");
			req.setAttribute("message", "无效验证码");
			return "/afterop";
		}


		long id=idService.getId("tb_shop");
		if(id<0){
			req.setAttribute("result", "2002");
			req.setAttribute("message","id生成有误");
			return "/afterop";
		}
		String name=getString(req,"name", "").trim();//店铺名称
		String catalog=getString(req,"catalog", "");//类目
		String uname=getString(req,"uname", "").trim();//联系人姓名
		
		String qq=getString(req, "qq", "").trim();//qq
		ShopBean shop=new ShopBean();
		shop.setId(id);
		shop.setUid(user.getId());     
		shop.setCatalog(catalog);
		shop.setName(name);
		shop.setUname(uname);
		shop.setQq(qq);
		shop.setPhone(getString(req, "phone",user.getPhone()));
		shop.setAddress(getString(req, "address", ""));
		shop.setProvince(getString(req, "province", ""));
		shop.setCity(getString(req, "city",""));
		shop.setDistrict(getString(req, "district",""));
		shop.setIntro(getString(req, "intro",""));
		
		ShopExtBean extBean=new ShopExtBean();//店铺拓展信息
		extBean.setId(id);//店铺id
		extBean.setUsername(uname);//开户人姓名
		shopService.addShopExt(extBean);//添加店铺拓展信息
		
		int ret1=shopService.addShop(shop,user);
		if(ret1!=0){
			req.setAttribute("result", "2003");
			req.setAttribute("message", "系统错误，添加失败");
			return "/afterop";
		}
		req.setAttribute("shop", shop);
		req.setAttribute("result", "0000");
		return "/afterop";
	}
	/**
	 * 检查店铺名称是否被使用
	 */
	@RequestMapping("/check")
	public void checkShop(HttpServletRequest req, HttpServletResponse resp){
		String name=getString(req, "name", "").trim();//店铺名称
		
		ShopBean shop=shopService.checkshopName(name);
		if(shop!=null){
			writeJson(resp, "2001","该店铺名称已被使用",null);
			return ;
		}
		writeJson(resp, "0000", "", null);
	}
	/**
	 * 详细店铺信息
	 */
	@RequestMapping("/getShop")
	public String getShopByid(HttpServletRequest req, HttpServletResponse resp){

		UserBean u = getLoginUser(req);
		if(u == null) {
			return "redirect:/login.html";
		}
		long sid=getLong(req, "sid",0 );
		ShopBean shop=shopService.getShop(sid);
		if(shop==null || shop.getUid() != u.getId()){
			req.setAttribute("message", "系统异常，没有相关信息");
			return "/error";
		}
		req.setAttribute("shop", shop);
		return "/shop/editShopinfo";
	}
	
	/**
	 * 修改店铺信息
	 * @return
	 */
	@RequestMapping("/editInfo")
	public String editInfo(HttpServletRequest req, HttpServletResponse resp) {
		UserBean u = getLoginUser(req);
		if(u == null) {
			return "redirect:/login.html";
		}
		long id=getLong(req, "id", 0);
		ShopBean shop = shopService.getShop(id);
		if(shop.getUid() != u.getId()) {
			req.setAttribute("message", "无权修改");
			return "/error";
		}
		String name=getString(req, "name", "").trim();//店铺名称
		int type=getInt(req, "type",0);//认证
		String phone=getString(req, "phone", "").trim();
		String qq=getString(req, "qq", "").trim();
		shop.setName(name);
		shop.setType(type);
		shop.setPhone(phone);
		shop.setQq(qq);
		shop.setPict(getString(req, "picts", ""));
		shop.setBpict(getString(req, "bpict", ""));//设置大图
		shop.setCatalog(getString(req, "catalog", ""));
		shop.setAddress(getString(req, "address", ""));
		shop.setUname(getString(req, "uname", ""));
		shop.setProvince(getString(req, "province", ""));
		shop.setCity(getString(req, "city",""));
		shop.setDistrict(getString(req, "district",""));
		shop.setIntro(getString(req, "intro",""));
		
		String weixin=getString(req, "weixin", "");
		if(weixin.equals("0")){
			shop.setPartner("");
			shop.setAppid("");
			shop.setAppsecret("");
		}
		else{
			shop.setPartner(getString(req, "partner", ""));
			shop.setAppid(getString(req, "appid", ""));
			shop.setAppsecret(getString(req, "appsecret", ""));
		}
		req.setAttribute("weixin", weixin);
		int ret=shopService.editShop(shop);
		req.setAttribute("op", "editshop");
		if(ret == -1){
			req.setAttribute("result", "2000");
			req.setAttribute("message", "系统异常，修改失败");
			return "/afterop";
		}
		req.setAttribute("result", "0000");
		req.setAttribute("message", "保存成功");
		return "/afterop";
	}
	@RequestMapping("/get")
	public String get(HttpServletRequest req) {
		UserBean user=getLoginUser(req);
		if(user == null) return "redirect:/login.html";
		
		long sid = getLong(req, "sid", 0);
		ShopBean shop=shopService.getShop(sid);
		if(shop == null || shop.getUid() != user.getId() || shop.getState() != 0) {
			req.setAttribute("message", "店铺不存在");
				return "/error";
		}
		req.setAttribute("shop", shop);
		String op = getString(req, "op", "show");
		return "/shop/" + op;
	}
	/**
	 * 跳转页面到认证页面
	 */
	@RequestMapping("/toCerPage")
	public String toCerPage(HttpServletRequest req) {
		UserBean user=getLoginUser(req);
		if(user == null) return "redirect:/login.html";
		ShopBean shop=shopService.getLast(user.getId());
		if(shop==null){
			req.setAttribute("message", "系统异常");
			return "/error";
		}
		
		req.setAttribute("shop", shop);
		return "/shop/shopCertification";
	}
	/**
	 * 店铺认证
	 */
	@RequestMapping("/shopAudit")
	public void shopAudit(HttpServletRequest req, HttpServletResponse resp) {
		UserBean user=getLoginUser(req);
		if(user==null){
			writeJson(resp, "2001", "请先登录", null);
			return;
		}
		long sid = getLong(req, "sid", 0);
		ShopBean shop=shopService.getShop(sid);
		if(shop==null || shop.getUid() != user.getId() || shop.getState() != 0){
			writeJson(resp, "2001", "请先创建店铺", null);
			return ;
		}
		if(shop.getAuthed() == 1){
			writeJson(resp, "2001", "店铺已经通过认证,无需提交", null);
			return;
		}
		if(shop.getAuthed() == 2){
			writeJson(resp, "2001", "店铺已经提交认证,请等待审核结果", null);
			return;
		}
		long id=idService.getId("tb_audit");
		AuditBean audit=new AuditBean();
		audit.setId(id);
		audit.setUid(user.getId());
		audit.setSid(shop.getId());
		audit.setType(getInt(req, "type", 0));//认证类型
		audit.setName(shop.getName());
		audit.setIdcn(getString(req,"idcn", "").trim());
		audit.setPict(getString(req, "picts", ""));
		audit.setUsername(getString(req, "username", ""));//姓名
		audit.setAudit(0);
		shop.setAuthed(2);
		shop.setType(getInt(req,"type", 0));
		
		int ret =shopService.shopAddAuth(shop, audit);
		if(ret!=0){
			writeJson(resp, "2002", "系统故障，认证失败", null);
			return;
		}
		writeJson(resp, "0000","认证申请已经提交,请等待审核", null);
	}
	@RequestMapping("/toSuccPage")
	public String toSuccPage(HttpServletRequest req, HttpServletResponse resp){
		long sid=getLong(req, "sid", 0);
		ShopBean shop=shopService.getShop(sid);
		if(shop==null){
			req.setAttribute("message", "");
			return "/error";
		}
		req.setAttribute("shopid", sid);
		return "/shop/finishAdd";
	}
	/**
	 * 取消认证
	 */
	@RequestMapping("/cancel")
	public void cancel(HttpServletRequest req, HttpServletResponse resp){
		UserBean user=getLoginUser(req);
		if(user==null){
			writeJson(resp, "2001", "请先登录", null);
			return ;
		}
		long sid=getLong(req, "sid",0);
		ShopBean shop=shopService.getShop(sid);
		if(shop==null||shop.getUid()!=user.getId()){
			writeJson(resp, "2003","无权操作", null);
			return ;
		}
		AuditBean audit=shopService.getAudit(sid);
		if(audit==null){
			writeJson(resp, "2003", "请先提交认证资料", null);
			return ;
		}
		if(audit.getAudit()!=0){
			writeJson(resp, "2003", "认证状态已发生变化，请检查", null);
			return ;
		}
		int ret=shopService.cancelCer(shop, audit);
		if(ret!=SUCCESS){
			writeJson(resp, "2003", "系统异常,取消失败", null);
			return ;
		}
		writeJson(resp, "0000", "操作成功", null);
	}
	/**
	 * 查看认证审核资料
	 */
	@RequestMapping("/getCerinfo")
	public String getCerinfo(HttpServletRequest req){
		UserBean user=getLoginUser(req);
		if(user==null) return "redirect:/login.html";
		long sid=getLong(req, "sid", 0);
		AuditBean audit=shopService.getAudit(sid);
		if(audit==null){
			req.setAttribute("message", "系统异常，没有该认证资料信息，请检查");
			return "/error";
		}
		req.setAttribute("audit", audit);
		return "/store/listCerti";
	}
	/**
	 * 删除未认证的店铺
	 */
	@RequestMapping("/deleteShop")
	public void deleteShop(HttpServletRequest req, HttpServletResponse resp){
		UserBean user=getLoginUser(req);
		if(user==null){
			writeJson(resp, "2001", "请先登录", null);
			return;
		}
		long id=getLong(req,"id", 0);
		ShopBean shopBean=shopService.getShop(id);
		if(shopBean==null||shopBean.getUid()!=user.getId()||shopBean.getState()!=0||shopBean.getState()==3){
			writeJson(resp, "2002", "系统异常，删除失败", null);
			return;
		}
		int ret=shopService.delShop(id);
		if(ret!=0){
			writeJson(resp, "2002", "系统异常，删除失败", null);
			return;
		}
		writeJson(resp, "0000", "ok", null);
	}
	/**
	 * 修改收款账号
	 */
	@RequestMapping("/editBankNo")
	public void editBankNo(HttpServletRequest req, HttpServletResponse resp){
		UserBean user=getLoginUser(req);
		if(user==null){
			writeJson(resp,"2001", "请先登录", null);
			return;
		}
		long sid=getLong(req, "sid", 0);
		ShopExtBean ext= shopService.getShopExt(sid);
		if(ext==null){
			writeJson(resp, "2002", "无相应信息", null);
			return ;
		}
		String phone=getString(req,"phone", "");
		String phonecode = getString(req, "phonecode","");
		OperationResponse ret = smsService.check("", phone, phonecode, "REG", true);
		if(!ret.isSuccess()) {
			writeJson(resp, "2002", "无效验证码", null);
			return;
		}	
		ext.setBank(getString(req, "bank", ""));
		ext.setBankdeposit(getString(req, "bankdeposit", ""));
		ext.setAccountno(getString(req, "accountno", ""));
		ext.setUsername(getString(req, "username", ""));
		int ret1=shopService.updateBankNo(ext);
		if(ret1==FAILED){
			writeJson(resp, "2003", "系统异常,修改失败", null);
			return ;
		}
		writeJson(resp, "0000", "修改成功", null);
	}
	//审核原因
	@RequestMapping("/auditResult")
	public void auditResult(HttpServletRequest req, HttpServletResponse resp){

		long sid=getLong(req, "sid", 0);
		AuditBean audit=shopService.getResult(sid);
		if(audit==null){
			writeJson(resp, "2001", "系统异常", null);
			return;
		}
		req.setAttribute("audit", audit);
		writeJson(resp,"0000", "", JSONObject.toJSON(audit));
	}
	/**
	 * 修改手机号码
	 */
	@RequestMapping("/setPhone")
	public void setPhone(HttpServletRequest req, HttpServletResponse resp){
		UserBean user=getLoginUser(req);
		if(user==null){
			writeJson(resp,"2001", "请先登录", null);
			return;
		}
		long id=getLong(req, "id", 0);
		ShopBean shop = shopService.getShop(id);
		if(shop==null||shop.getUid() != user.getId()) {
			writeJson(resp, "2001", "无权修改", null);
			return ;
		}
		String phone=getString(req, "phone", "");
		String phonecode=getString(req, "phonecode", "");
		OperationResponse checkret = smsService.check("",phone,phonecode,"REG", true);
		if(!checkret.isSuccess()) {
			writeJson(resp, "2002", "无效验证码", null);
			return;
		}	
		shop.setPhone(phone);
		if(shopService.setPhone(shop)==FAILED){
			writeJson(resp, "2003", "系统异常",null);
			return ;
		}
		writeJson(resp, "0000", "修改成功", null);
	}
}
