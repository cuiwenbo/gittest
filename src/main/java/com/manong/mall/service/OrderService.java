package com.manong.mall.service;


import com.alibaba.fastjson.JSONObject;
import com.manong.mall.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("orderService")
public class OrderService extends BaseServiceImpl{
	@Autowired
	IDService idService;

	/**
	 * 订单详情信息
	 * @param id
	 * @return orderBean
	 */
	public OrderBean get(long id){
		return (OrderBean)get( "order.getDetail", id);
	}
	public int getSend(OrderBean order){
		return update("order.sendout", order);
	}
	/**
	 * 提款
	 * @param ext
	 * @param draw
	 * @return
	 */
	public int drawMoney(ShopExtBean ext, DrawBean draw, AccountBean account){
		try {
			draw.setId(idService.getId("tb_shop_draw"));
			add("draw.addinfo", draw);
			update("draw.updateExt", ext);
			add("account.add",account);
		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		}

		return SUCCESS;
	}
	public int setState(OrderBean order){
		return update( "order.setState", order);
	}
	
	public ReturnBean getById(long id) {
		return (ReturnBean)get( "return.get", id);
	}
	public int refund(ReturnBean re,OrderBean order){

		try {

			add("order.return", re);
			order.setState(6);//订单状态改为已退货
			update("order.changeS", order);
				} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		}

		return SUCCESS;
	}
	public int createOrder(OrderBean ob, GoodsBean gb) {
		try {
			if(update("order.pre", ob) != 1) return FAILED;
			if(update("order.updateGoodsAmount", ob) != 1) return FAILED;

		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		}
		return SUCCESS;
	}
	public int toGet(OrderBean ob, AccountBean account) {
		try {
			update("order.recept", ob);
			add("order.addaccount", account);

		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		}
		return SUCCESS;
	}
	/**
	 * 订单状态发生变化，给卖家发送通知消息
	 * @param ownerid
	 * @param orderid
	 * @param newstate
	 */
	public void sendOrderMessage(long ownerid, long shopid, long orderid, int newstate) {
		MessageBean mb = new MessageBean();
		String mid =  "ORDER-" + df8.format(new Date()) + "-" +orderid + "-" + newstate;
		String uid = "webag-" + ownerid;
		mb.setFromuser(0);
		mb.setOfflinemsg("");
		mb.setOverdelay("5 DAY");
		mb.setSenddelay("0 SECOND");
		mb.setMid(mid);
		mb.setTouser(uid);
		try {
			
			
			JSONObject message = new JSONObject();
			message.put("mid", mid);
			message.put("op", "message");
			message.put("op2", "order");
			message.put("touser", uid);
			message.put("sender", "0");			
			message.put("time", df.format(new Date()));
						
			JSONObject content = new JSONObject();
			content.put("shopid", shopid);
			content.put("orderid", orderid);
			content.put("state", newstate);
			message.put("content", content);
			mb.setContent(message.toString());
			add("mc.addMessage", mb);
		} catch (Exception e){}

	}
}
