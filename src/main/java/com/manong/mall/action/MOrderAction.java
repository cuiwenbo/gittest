package com.manong.mall.action;

import com.manong.mall.bean.*;
import com.manong.mall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/m/order")
public class MOrderAction extends BaseAction {
	@Autowired
	OrderService orderService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	ShopService shopService;

	@Autowired
	UserService userService;

	@Autowired
	IDService idService;

	@RequestMapping("/start")
	public String execute(HttpServletRequest req) {
		UserBean u = getLoginUser(req);
		if(u == null) 
		{
			setLastUrl(req);
			return "redirect:/m/login.html";
		}
		long sid=(Long)getSession(req,"ShopId");
		req.setAttribute("sid", sid);
		Page page=new Page();
		getPageInfo(req, page);
		Map p = new HashMap();
		int state = getInt(req, "state",-2);//默认待付款
		req.setAttribute("state", state);
		if(state > -2)
		p.put("state", state);

		Date sdate=getDate(req, "stime", "yyyy-MM-dd");
		if(sdate!=null){
			String stime=getString(req, "stime", "");
			p.put("stime", stime);
			req.setAttribute("stime", stime);
		}
		Date edate=getDate(req, "etime", "yyyy-MM-dd");
		if(edate!=null){
			String etime=getString(req, "etime","");
			p.put("etime", etime + " 23:59:59");
			req.setAttribute("etime", etime);
		}
		p.put("uid", u.getId());
		int ret=orderService.listObj( p, page, "order.getUserOrderCount", "order.ListUserOrder");
		req.setAttribute("page", page);
		Map jj=new HashMap();
		jj.put("uid", u.getId());
		int count0=(Integer)orderService.get("order.getCount0", jj);req.setAttribute("count0",count0);
		int count1=(Integer)orderService.get( "order.getCount1", jj);req.setAttribute("count1",count1);
		int count3=(Integer)orderService.get( "order.getCount3", jj);req.setAttribute("count3",count3);
		int count4=(Integer)orderService.get( "order.getCount4", jj);req.setAttribute("count4",count4);
		
		return "/m/order/list";
	}
	@RequestMapping("/getOrder")
	public String getOrder(HttpServletRequest req) {

		long sid=(Long)getSession(req, "ShopId");
		req.setAttribute("sid", sid);
		String op = getString(req, "op", "show");
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
		
		long oid  = getLong(req, "id", 0);
		OrderBean ob = orderService.get(oid);
		if(ob == null || ob.getUserid() != u.getId()) {
			return "redirect:/m/home";
		}
		GoodsBean goods=goodsService.getGoodsInfoByid(ob.getGoods());
		if(goods==null){
			return "redirect:/m/home";
		}

		req.setAttribute("shop", shopService.getShop(goods.getSid()));
		req.setAttribute("goods", goods);
		req.setAttribute("order", ob);
		return "/m/order/orderDetail";
	}

	/**
	 * 立即购买
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/buy")
	public String buy(HttpServletRequest req, HttpServletResponse resp){
		long goodid  = getLong(req, "id", 0);
		String op = getString(req, "op", "p1");
		GoodsBean goods = goodsService.getGoodsInfoByid(goodid);
		req.setAttribute("goods", goods);
		req.setAttribute("shop", shopService.getShop(goods.getSid()));
		return "/m/order_p1";

	}

	/**
	 * 创建订单
	 */
	@RequestMapping("/pre")
	public void pre(HttpServletRequest req, HttpServletResponse resp) {
		UserBean u = getLoginUser(req);
		if(u == null) {
			writeJson(resp, "1000", "您尚未登录，请先登录", null);
			return;
		}
		String  province=getString(req, "province", "");
		String city=getString(req, "city","");
		String district=getString(req, "district", "");
		String address=getString(req,"address", "");
		String phone=getString(req,"phone", "");
		String receiver=getString(req,"name", "");
		UserExtBean uext=userService.getExt(u.getId());
		if(uext==null){
			Map h=new HashMap();
			h.put("uid", u.getId());
			h.put("regip", getip(req));
			h.put("province", province);
			h.put("city", city);
			h.put("district", district);
			h.put("address",address);
			h.put("phone", phone);
			h.put("receiver", receiver);
			int re=userService.add("user.addExt",h);
			if(re==FAILED){
				req.setAttribute("result", "1002");
				req.setAttribute("message", "系统故障，创建订单失败，请稍候再试");
			}
		}else{
			uext.setProvince(province);
			uext.setCity(city);
			uext.setDistrict(district);
			uext.setAddress(address);
			uext.setPhone(phone);
			uext.setReceiver(receiver);
			int ret=userService.update("user.editAddre", uext);
			if(ret==FAILED){
				req.setAttribute("result", "1002");
				req.setAttribute("message", "系统故障，创建订单失败，请稍候再试");
			}
		}

		long id = getLong(req, "id", 0);//商品id
				

		GoodsBean g = goodsService.getGoodsInfoByid(id);
		if(g == null || g.getIsvalid() == 1|| g.getState() != 0 || g.getAmount() < 1) {
			writeJson(resp, "2001", "该商品已经下架，请选择其他商品购买", null);
			return;
		}
		ShopBean sb = shopService.getShop(g.getSid());
		if(sb == null || sb.getAuthed() != 1) {
			writeJson(resp, "2001", "该店铺已经下线", null);
			return;
		}
		double amount  = getDouble(req, "amount", 0);
		double limit=g.getUserlimit();
		if(amount>g.getAmount()){
			writeJson(resp, "2002", "商品存货不足，请修改您的购买数量", null);
			return ;
		}
		if(limit>0&&amount>limit){
			String mm="该商品每人限购"+g.getUserlimit()+g.getUnit();
			if(amount>g.getUserlimit()){
				writeJson(resp, "2002",mm, null);
				return  ;
			}
		}
		
		OrderBean ob = new OrderBean();
		ob.setAmount(amount);
		ob.setGoods(id);
		String param = getString(req, "param", "");
		String[] ps = param.split(",");
		int idx = 0;
		param = "[";
		for(String p:ps) {
			String[] pi = p.split(":");
			if(pi == null || pi.length != 2) continue;
			if(idx > 0) param += ",";
			param += "{\"" + pi[0] + "\":" + "\"" + pi[1] + "\"}";
			idx ++;			
		}
		param += "]";
		
		long oid=idService.getId("tb_order");
		ob.setAddress(province + " " + city + " " + district + " " +  address);
		ob.setCreatetime(new Date());
		ob.setDeliverymethod(g.getDeliverymethod());
		ob.setDeliverymoney(g.getDeliveryfee());
		ob.setName(g.getName());
		ob.setOrderid(df8.format(new Date()) + ndf8.format(oid));
		ob.setPaytype(g.getPaytype());
		ob.setPhone(getString(req, "phone", u.getPhone()));
		ob.setPicts(g.getPicts());
		ob.setRemarks(getString(req, "remarks", ""));
		ob.setState(0);
		ob.setPrice(g.getPrice());
		ob.setUnit(g.getUnit());
		ob.setUsername(getString(req, "name", u.getNick()));
		
		ob.setParam(param);
		ob.setState(0);
		ob.setSid(g.getSid());
		ob.setUserid(u.getId());
		ob.setDeliverymethod(g.getDeliverymethod());
		ob.setDeliverymoney(g.getDeliveryfee());
		ob.setTotalmoney(amount * g.getPrice() + g.getDeliveryfee());
		ob.setId(oid);
		if(orderService.createOrder(ob, g)==SUCCESS) {
			writeJson(resp, "0000", "", ob.getId() + "");
			orderService.sendOrderMessage(sb.getUid(), sb.getId(), ob.getId(), ob.getState());
		} else {
			writeJson(resp, "3001", "系统故障，创建订单失败，请稍候再试", null);
		}
	}

/**
 * 买家收货
 */
	@RequestMapping("/receipt")
	public void receipt(HttpServletRequest req, HttpServletResponse resp){
		long id=getLong(req, "id", 0);
		OrderBean order=orderService.get(id);
		if(order==null||order.getState()!=3){
			writeJson(resp, "2001", "系统故障，请稍候再试", null);
			return ;
		}
		order.setState(4);//确定
		ShopExtBean shopExt=shopService.getShopExt(order.getSid());
		req.setAttribute("ext", shopExt);
		
		AccountBean ac=new AccountBean();//收入记录
		ac.setSid(order.getSid());
		ac.setType(0);//收入
		ac.setRestmoney(shopExt.getFree());
		ac.setContent("已确认收货");
		ac.setMoney(order.getTotalmoney());//发生额
		ac.setRemarks("确认收货，交易成功");
		int ret=orderService.toGet(order, ac);
		if(ret==FAILED){
			writeJson(resp, "2002", "系统故障，请稍候再试", null);
			return ;
		}
		ShopBean sb  = shopService.getShop(order.getSid());
		orderService.sendOrderMessage(sb.getUid(), sb.getId(), order.getId(), order.getState());
		writeJson(resp, "0000", "操作成功", null);
	} 
	/**
	 * 取消订单
	 */
	@RequestMapping("/cancelOrder")
	public void cancelOrder(HttpServletRequest req, HttpServletResponse resp){
		long oid=getLong(req, "id", 0);
		OrderBean order=orderService.get(oid);
		if(order==null||order.getState()!=0){
			writeJson(resp, "2001", "操作失败，请稍候再试", null);
			return ;
		}
		order.setState(9);//取消状态
		int ret=orderService.update("order.changeS",order);
		if(ret==FAILED){
			writeJson(resp, "2002", "系统异常，请稍候再试", null);
			return ;
		}
		writeJson(resp, "0000", "成功取消该订单", null);
	}
}
