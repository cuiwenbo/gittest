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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderAction extends BaseAction {
    @Autowired
    ShopService shopService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    UserService userService;

    @Autowired
    IDService idService;

    @RequestMapping("/start")
    public String execute(HttpServletRequest req) {
        UserBean user = getLoginUser(req);
        if (user == null) return "redirect:/login.html";
        Map p = new HashMap();
        long sid = getLong(req, "sid", 0);
        req.setAttribute("sid", sid);
        ShopBean shop = shopService.getShop(sid);
        if (shop == null || user.getId() != shop.getUid()) {
            req.setAttribute("message", "该店铺不存在");
            return "/error";
        }
        req.setAttribute("shop", shop);
        p.put("sid", sid);

        int state = getInt(req, "state", 0);//默认未支付状态
        String op = getString(req, "op", "").trim();
        if (op.equals("topay")) {
            p.put("state1", 0);//未支付(待付款)
        }
        if (op.equals("tosend")) {
            p.put("state1", 1);//已支付(待发货)
        }
        if (op.equals("send")) {
            p.put("state1", 3);//已发货
        }
        if (op.equals("sign")) {
            p.put("state1", 4);//标记签收（已收货）
        }
        if (op.equals("fail")) {
            p.put("state1", 8);//已失效（已关闭）
        }
        if (op.equals("toRefund")) {//待退货
            p.put("state1", 5);
        }
        if (op.equals("refunded")) {//已退货
            p.put("state1", 6);
        }
        if (op.equals("closed") && state == 55) {//已关闭订单
            p.put("state", 55);

        }
        if (state == 66) {
            p.put("state", 66);//不显示已关闭订单
        }

        if (op.equals("cancel")) {
            p.put("state", 9);//已取消
        }
        req.setAttribute("state", state);
        req.setAttribute("op", op);

        String deliverymethod = getString(req, "deliverymethod", "");//运送方式
        //按条件查询
        if (op.equals("search")) {
            if (state > 0) {
                if (state == 55) {
                    p.put("state", state);
                } else {
                    p.put("state1", state);
                }
                req.setAttribute("s1", state);
            }

            String orderid = getString(req, "orderid", "").trim();//订单号
            if (!orderid.equals("")) {
                p.put("orderid", orderid);
                req.setAttribute("oid", orderid);
            }
            String name = getString(req, "name", "").trim();//商品名称
            if (!name.equals("")) {
                p.put("name", name);
                req.setAttribute("gname", name);
            }
            if (!deliverymethod.equals("")) {
                p.put("deliverymethod", deliverymethod);
                req.setAttribute("deliverymethod", deliverymethod);
            }

            String username = getString(req, "username", "");//收货人姓名
            if (!username.equals("")) {
                p.put("username", username);
                req.setAttribute("username", username);
            }
            String phone = getString(req, "phone", "");//收货人手机号
            if (!phone.equals("")) {
                p.put("phone", phone);
                req.setAttribute("phone", phone);
            }
            String paytype = getString(req, "paytype", "");
            if (!paytype.equals("")) {
                p.put("paytype", paytype);
                req.setAttribute("pay", paytype);
            }

            Date sdate = getDate(req, "stime", "yyyy-MM-dd");
            if (sdate != null) {
                String stime = getString(req, "stime", "");
                p.put("stime", stime);
                req.setAttribute("stime", stime);
            }
            Date edate = getDate(req, "etime", "yyyy-MM-dd");
            if (edate != null) {
                String etime = getString(req, "etime", "");
                p.put("etime", etime + " 23:59:59");
                req.setAttribute("etime", etime);
            }

        }
        Page page = new Page();
        getPageInfo(req, page);
        int ret = orderService.listObj(p, page, "order.getOrderCount", "order.ListOrder");
        if (ret != 0) {
            req.setAttribute("message", "系统异常");
            return "/error";
        }
        req.setAttribute("page", page);
        return "/order/listOrder";
    }

    /**
     * 订单详情
     *
     * @return
     */
    @RequestMapping("/getOrder")
    public String getOrder(HttpServletRequest req) {
        UserBean user = getLoginUser(req);
        if (user == null) return "redirect:/login.html";
        long id = getLong(req, "id", 0);//订单id
        OrderBean order = orderService.get(id);


        ShopBean shop = shopService.getShop(order.getSid());
        if (shop == null || shop.getUid() != user.getId()) {
            req.setAttribute("message", "系统异常,无权访问");
            return "/error";
        }
        req.setAttribute("shop", shop);
        req.setAttribute("op", "tosend");
        req.setAttribute("order", order);
        req.setAttribute("picts", goodsService.getGoodsInfoByid(order.getGoods()).getPicts());

        double price = order.getPrice();
        double count = order.getAmount();
        double discount = order.getDiscount();
        if (discount != 0) {
            double totalmoney1 = price * count * discount;
            req.setAttribute("totalA", totalmoney1);
        }
        return "/order/detail";
    }

    /**
     * 发货
     */
    @RequestMapping("/sendOut")
    public void sentOut(HttpServletRequest req, HttpServletResponse resp) {
        UserBean user = getLoginUser(req);
        if (user == null) {
            writeJson(resp, "2001", "请先登录", null);
            return;
        }
        //return "login";
        long id = getLong(req, "id", 0);
        long sid = getLong(req, "sid", 0);
        OrderBean order = orderService.get(id);
        if (order == null || order.getSid() != sid) {
            /*req.setAttribute("message", "订单信息异常，无权访问");
			return "error";*/
            writeJson(resp, "2002", "订单信息异常，无权访问", null);
            return;
        }
        order.setDeliverymethod(getString(req, "deliverymethod", ""));//物流方式（需或不需物流）
        order.setDelivery(getString(req, "delivery", ""));//物流公司
        order.setDeliveryid(getString(req, "deliveryid", ""));//运送单号
        order.setState(3);//已发货状态
        int ret = orderService.getSend(order);
        if (ret == FAILED) {
			/*req.setAttribute("message", "系统异常");
			return "error";*/
            writeJson(resp, "2003", "系统异常", null);
            return;
        }
		/*req.setAttribute("result", "0000");
		req.setAttribute("message","发货成功");
		return "afterop";*/
        writeJson(resp, "0000", "发货成功", null);
    }

    /**
     * 管理财务信息
     *
     * @return
     */
    @RequestMapping("/getFinance")
    public String getFinance(HttpServletRequest req) {
        UserBean user = getLoginUser(req);
        if (user == null) return "redirect:/login.html";

        long sid = getLong(req, "sid", 0);
        ShopBean shop = shopService.getShop(sid);
        if (shop == null || shop.getUid() != user.getId()) {
            req.setAttribute("message", "店铺信息异常");
            return "/error";
        }
        req.setAttribute("shop", shop);

        //最近7天收入
        Map i = new HashMap();
        i.put("sid", sid);
        i.put("type", 0);//收入
        List<AccountBean> account = shopService.list("shop.income", i);
        double total = 0;
        for (int j = 0; j < account.size(); j++) {
            total += account.get(j).getMoney();
        }
        req.setAttribute("total", total);

        ShopExtBean shopExt = shopService.getShopExt(sid);
        String accountno = shopExt.getAccountno();
        if (accountno != null) {
            int elength = accountno.length();
            if (elength > 0) {
                String lastFourNum = accountno.substring(elength - 4, elength);
                req.setAttribute("four", lastFourNum);
            }
        }
        req.setAttribute("ext", shopExt);
        Map p = new HashMap();
        p.put("sid", sid);

        String op = getString(req, "op", "");
        int ret = 0;
        if (op.equals("lastSale")) {//最近交易
            p.put("state1", 4);//已确认收货的订单
            Page page = new Page();
            getPageInfo(req, page);
            ret = orderService.listObj(p, page, "order.getOrderCount", "order.ListOrder");
            if (ret != SUCCESS) {
                req.setAttribute("message", "系统异常,無任何交易信息");
                return "error";
            }
            req.setAttribute("page", page);
        }
        if (op.equals("refundRecord")) {//退货记录
            Page page = new Page();
            getPageInfo(req, page);
            ret = orderService.listObj(p, page, "order.return_getCount", "order.return_getlist");
            if (ret != SUCCESS) {
                req.setAttribute("message", "系统异常,無任何退货信息");
                return "error";
            }
            req.setAttribute("page", page);
        }
        req.setAttribute("op", op);
        return "/order/getfinance";
    }

    //提现记录-收支记录
    @RequestMapping("/cashList")
    public String getCashList(HttpServletRequest req) {
        UserBean user = getLoginUser(req);
        if (user == null) return "redirect:/login.html";
        long sid = getLong(req, "sid", 0);
        ShopBean shop = shopService.getShop(sid);
        if (shop == null || shop.getUid() != user.getId()) {
            req.setAttribute("message", "店铺信息异常");
            return "/error";
        }
        req.setAttribute("shop", shop);
        Map p = new HashMap();
        p.put("sid", sid);

        String op = getString(req, "op", "");
        int ret = 0;

        if (op.equals("paymentDe")) {//收支记录
            Page page = new Page();
            getPageInfo(req, page);
            Date sdate = getDate(req, "stime", "yyyy-MM-dd");
            if (sdate != null) {
                String stime = getString(req, "stime", "");
                p.put("stime", stime);
                req.setAttribute("stime", stime);
            }
            Date edate = getDate(req, "etime", "yyyy-MM-dd");
            if (edate != null) {
                String etime = getString(req, "etime", "");
                p.put("etime", etime + " 23:59:59");
                req.setAttribute("etime", etime);
            }
            ret = orderService.listObj(p, page, "order.refund_selCount", "order.refund_selList");
            if (ret != SUCCESS) {
                req.setAttribute("message", "系统异常，无收支记录");
                return "/error";
            }
            req.setAttribute("page", page);
        }
        if (op.equals("cashRecord")) {//提現記錄
            Page page = new Page();
            getPageInfo(req, page);
            Date sdate = getDate(req, "stime", "yyyy-MM-dd");
            if (sdate != null) {
                String stime = getString(req, "stime", "");
                p.put("stime", stime);
                req.setAttribute("stime", stime);
            }
            Date edate = getDate(req, "etime", "yyyy-MM-dd");
            if (edate != null) {
                String etime = getString(req, "etime", "");
                p.put("etime", etime + " 23:59:59");
                req.setAttribute("etime", etime);
            }
            ret = orderService.listObj(p, page, "order.draw_getCount1", "order.draw_getList1");
            if (ret != SUCCESS) {
                req.setAttribute("message", "系统异常，无提现记录");
                return "/error";
            }
            req.setAttribute("page", page);
        }
        req.setAttribute("op", op);
        return "order/cashList";
    }

    /**
     * 卖家-退货
     */
    @RequestMapping("/refund")
    public void refund(HttpServletRequest req, HttpServletResponse resp) {
        UserBean user = getLoginUser(req);
        if (user == null) {
            writeJson(resp, "2001", "请先登录", null);
            return;
        }
        long sid = getLong(req, "sid", 0);
        ShopBean shop = shopService.getShop(sid);
        if (shop == null || shop.getUid() != user.getId()) {
            writeJson(resp, "2002", "出错啦，无权操作请检查", null);
            return;
        }
        long oid = getLong(req, "orderid", 0);//订单id
        OrderBean order = orderService.get(oid);
        int ostate = order.getState();
        if (order == null || order.getSid() != shop.getId()) {
            writeJson(resp, "2002", "出错啦，没有该订单信息", null);
            return;
        }
        if (order.getSettleid() != 0) {
            writeJson(resp, "1002", "该订单暂时不能退货，请联系卖家", null);
            return;
        }
        if (ostate == 0 || ostate == 6 || ostate == 8 || ostate == 9) {//未支付，已退货，已取消，已失效
            writeJson(resp, "2002", "出错啦，订单未支付或已无效", null);
            return;
        }

        GoodsBean goods = goodsService.getinfoById(order.getGoods());
        if (goods == null || goods.getSid() != shop.getId()) {
            writeJson(resp, "2002", "出错啦，店铺没有该商品信息", null);
            return;
        }
        if (goods.getState() != 0 || goods.getIsvalid() != 0) {
            writeJson(resp, "2002", "出错啦，该商品已下架或已失效 ", null);
            return;
        }

        ReturnBean re = new ReturnBean();

        long rid = idService.getId("tb_order_return");
        re.setId(rid);
        re.setSid(sid);
        re.setOrderid(oid);
        re.setState(4);//退货完成
        re.setOmoney(order.getTotalmoney());//原始金额
        re.setRmoney(order.getTotalmoney());//退货金额
        re.setShopremarks("同意退货");
		
		/*long id=getLong("id", 0);//退货id
		int s=getInt("state", 0);
		ReturnBean re=os.getById(id);
		if(re==null){
			writeJson("2002", "出错啦，没有该退货信息", null);
			return ;
		}
		re.setState(s);//是否允许退货（1-yes 9-no）
		re.setShopremarks("允许退货");*/
        int ret = orderService.refund(re, order);
        if (ret == FAILED) {
            writeJson(resp, "2003", "系统异常", null);
            return;
        }
        writeJson(resp, "0000", "ok", null);
    }

    /**
     * 提现
     */
    @RequestMapping("/getDraw")
    public void getDraw(HttpServletRequest req, HttpServletResponse resp) {
        UserBean user = getLoginUser(req);
        if (user == null) {
            writeJson(resp, "2001", "请先登录", null);
            return;
        }
        long sid = getLong(req, "sid", 0);
        ShopExtBean ext = shopService.getShopExt(sid);
        double drawMoney = getDouble(req, "money", 0);//提款
        if (drawMoney < 0.01 || drawMoney > ext.getFree()) {
            writeJson(resp, "2002", "无效提款", null);
            return;
        }
        double free = ext.getFree() - drawMoney;
        ext.setFree(free);
        ext.setFroze(ext.getFroze() + drawMoney);

        DrawBean draw = new DrawBean();
        draw.setSid(sid);
        draw.setBank(getString(req, "bank", ""));
        draw.setAccountno(ext.getAccountno());
        draw.setBankdeposit(getString(req, "bank", ""));
        draw.setUsername(user.getName());
        draw.setMoney(drawMoney);
        draw.setRemarks(getString(req, "remarks", "提现"));

        AccountBean account = new AccountBean();
        account.setSid(sid);
        account.setType(1);//提款
        account.setMoney(drawMoney);//发生额
        account.setRestmoney(free);//剩余可用余额
        account.setRemarks("用户提款");


        int ret = orderService.drawMoney(ext, draw, account);
        if (ret == FAILED) {
            writeJson(resp, "2003", "系统异常，提款失败", null);
            return;
        }
        writeJson(resp, "0000", "成功", null);
        return;
    }

    /**
     * 线下支付
     */
    @RequestMapping("/offline")
    public void Offline(HttpServletRequest req, HttpServletResponse resp) {
        UserBean user = getLoginUser(req);
        if (user == null) {
            writeJson(resp, "2001", "请先登录", null);
            return;
        }
        long sid = getLong(req, "sid", 0);
        ShopBean shop = shopService.getShop(sid);
        if (shop == null) {
            writeJson(resp, "2002", "系统异常", null);
            return;
        }
        long id = getLong(req, "id", 0);
        OrderBean order = orderService.get(id);
        if (order == null || order.getState() != 0) {
            writeJson(resp, "2002", "系统异常,暂时没有该订单信息", null);
            return;
        }
        order.setState(1);//转为已支付
        int ret = orderService.setState(order);
        if (ret == FAILED) {
            writeJson(resp, "2002", "系统异常", null);
            return;
        }
        writeJson(resp, "0000", "付款成功", null);
    }
}
