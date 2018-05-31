package com.manong.mall.action;

import com.manong.mall.bean.GoodsBean;
import com.manong.mall.bean.ShopBean;
import com.manong.mall.bean.ShopFocusAndVisitBean;
import com.manong.mall.bean.UserBean;
import com.manong.mall.service.GoodsService;
import com.manong.mall.service.OrderService;
import com.manong.mall.service.ShopService;
import com.manong.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/m/shop")
public class MShopAction extends BaseAction{
	@Autowired
	ShopService shopService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;

	@RequestMapping("/start")
	public String execute(HttpServletRequest req) {
		long id = getLong(req, "id", 0);
		ShopBean sb = shopService.getShop(id);
		if(sb == null || sb.getState() != 1) {
			return "redirect:/m/home";
		}
		shopService.updateShopShow(sb);
		setSession(req, "ShopId", id);
		req.setAttribute("shop", sb);

		int totalgoods = (Integer) shopService.get("shop.getTotalGoods", sb.getId());
		Map p = new HashMap();
		p.put("sid", sb.getId());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);		
		p.put("newdate", new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		int totalnews = (Integer)shopService.get("shop.getTotalNews", p);
		List<GoodsBean> goods = shopService.list( "shop.listHomeGoods", sb.getId());
		UserBean u  = getLoginUser(req);
		if(u != null) {
			int orders = (Integer)shopService.get( "order.userordercount", u.getId());
			req.setAttribute("ordercount", orders);
			
			ShopFocusAndVisitBean sv=shopService.getFocusinfo(u.getId(), id);//关注
			req.setAttribute("sv", sv);
		} else {
			req.setAttribute("ordercount", 0);
		}
		req.setAttribute("totalgoods", totalgoods);
		
		req.setAttribute("totalnews", totalnews);
		req.setAttribute("goods", goods);		
		return "/m/shop/home";
	}
	/**
	 * 商品列表
	 */
	@RequestMapping("/goodsList")
	public String goodsList(HttpServletRequest req){

		//user
		//long sid=(Long) getSession("ShopId");
		long sid=getLong(req, "sid", 0);
		ShopBean sb = shopService.getShop(sid);
		if(sb == null || sb.getState() != 1) {
			return "redirect:/m/home";
		}
		req.setAttribute("shop", sb);
		
		String op=getString(req, "op", "").trim();
		List<GoodsBean> goods =null;
		if(op.equals("all")){
			goods=	shopService.list( "shop.listHomeGoods",sid);//全部商品
			if(goods.size()<0){
				req.setAttribute("message", "暂时没有任何商品信息");
				return "/error";
			}
			req.setAttribute("goodsList", goods);
		}
		
		if(op.equals("new")){
			goods=shopService.list("shop.listHomeGoods1", sid);//最新商品
			req.setAttribute("goodsList", goods);
		}
		if(op.equals("hot")){
			goods=shopService.list("shop.listHomeGoods2", sid);//最热商品（销量最好）
			req.setAttribute("goodsList", goods);
		}
		if(op.equals("popular")){
			goods=shopService.list( "shop.listHomeGoods3", sid);//人气商品
			req.setAttribute("goodsList", goods);
		}
		if(op.equals("price")){
			goods=shopService.list( "shop.listHomeGoods4", sid);//人气商品
			req.setAttribute("goodsList", goods);
		}
		req.setAttribute("op", op);
		return "/m/shop/goodslist";
	}
	/**
	 * 用户关注店铺
	 */
	@RequestMapping("/focus")
	public void setFocus(HttpServletRequest req, HttpServletResponse resp){
		UserBean u = getLoginUser(req);
		if(u == null) {
			writeJson(resp, "1001","请先登录" , null);
			return ;
		}
		long sid=getLong(req, "sid", 0);
		if(sid<0){
			writeJson(resp, "1002","请先选择店铺" , null);
			return ;
		}
		String op=getString(req, "op", "");
		int ret=0;
		if(op.equals("yes")){
			ShopFocusAndVisitBean svBean=new ShopFocusAndVisitBean();
			svBean.setUid(u.getId());
			svBean.setSid(sid);
			ret=shopService.addFocus(svBean);
		}
		if(op.equals("no")){
			ShopFocusAndVisitBean sv=shopService.getFocusinfo(u.getId(), sid);
			ret=shopService.deleteFocus(sv);
		}
		if(ret==FAILED){
			writeJson(resp, "2001", "系统异常，请稍候再试",null );
			return ;
		}
		writeJson(resp, "0000", "", null);
	}
	//关注列表
	@RequestMapping("/intrest")
	public String intresList(HttpServletRequest req){
		UserBean u=getLoginUser(req);
		if(u==null) 
		{
			setLastUrl(req);
			return "redirect:/m/login.html";
		}
		Map k=new HashMap();
		k.put("uid", u.getId());
		Page page=new Page();
		getPageInfo(req, page);
		int ret=shopService.listObj(k , page, "shop.focus_getCount", "shop.focus_listAll");
		if(ret==FAILED){
			req.setAttribute("message", "系统异常，请稍候再试");
			return "/failed";
		}
		req.setAttribute("page", page);
		return "/m/shop/inrest";
	}
}
