package com.manong.mall.action;

import com.manong.mall.bean.*;
import com.manong.mall.service.GoodsService;
import com.manong.mall.service.ShopService;
import com.manong.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/m/goods")
public class MGoodsAction extends BaseAction{
	@Autowired
	UserService userService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	ShopService shopService;

	@RequestMapping("/start")
	public String execute(HttpServletRequest req) {
		UserBean user = getLoginUser(req);
		String op=getString(req,"op", "show");
		if(!op.equals("show")){
			UserBean u = getLoginUser(req);
			if(u == null) 
			{
				setLastUrl(req);
				return "redirect:/m/login.html";
			}
			UserExtBean ue=userService.getExt(u.getId());
			if(ue!=null){
				req.setAttribute("uu", ue);
			}
		}
		
		long id = getLong(req, "id", 0);
		GoodsBean g  = goodsService.getGoodsInfoByid(id);
		if(g == null || g.getIsvalid() == 1 || g.getState() != 0) {
			return "redirect:/m/home";
		}
		ShopBean sb = shopService.getShop(g.getSid());
		if(sb == null ) {
			return "redirect:/m/home";
		}
		if(sb.getState() != 1){
			req.setAttribute("message", "该店铺尚未通过认证");
			return "/failed";
		}
		
		if(user!=null){//浏览记录
			ShopFocusAndVisitBean v=goodsService.getVisit(user.getId(),sb.getId() , g.getId());
			int ret=0;
			if(v==null){
				ShopFocusAndVisitBean vBean=new ShopFocusAndVisitBean();
				vBean.setUid(user.getId());
				vBean.setSid(sb.getId());
				vBean.setGid(g.getId());
				ret=goodsService.addVisit(vBean);
			}
			else{
				ret=goodsService.editVisit(v.getId());
			}
			if(ret==FAILED){
				req.setAttribute("message", "系统异常，请稍候再试");
				return "/failed";
			}
		}
		goodsService.editSTime(g.getId());
		req.setAttribute("shop", sb);
		req.setAttribute("goods", g);
		req.setAttribute("count", (g.getAmount()-g.getOrderamount()));
		return "/m/goods";
	}
	/**
	 * 浏览记录
	 */
	@RequestMapping("/visitList")
	public String visitList(HttpServletRequest req){
		//long sid=(Long)session.getAttribute("ShopId");
		long sid=getLong(req, "sid", 0);
		req.setAttribute("sid", sid);
		UserBean u=getLoginUser(req);
		if(u==null) 
		{
			setLastUrl(req);
			return "redirect:/m/login.html";
		}
		Page page=new Page();
		getPageInfo(req, page);
		Map h=new HashMap();
		h.put("uid", u.getId());
		int ret=goodsService.listObj( h, page, "goods.getCount","goods.listAll");
		if(ret==FAILED){
			req.setAttribute("message", "系统异常，请稍候再试");
			return "/failed";
		}
		req.setAttribute("page",page);
		return "/m/visitLogs";
	}
}
