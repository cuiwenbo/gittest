package com.manong.mall.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.manong.mall.bean.GoodsBean;
import com.manong.mall.bean.ShopBean;
import com.manong.mall.bean.UserBean;
import com.manong.mall.service.GoodsService;
import com.manong.mall.service.IDService;
import com.manong.mall.service.ShopService;
import com.manong.mall.utils.ConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Controller
@RequestMapping("/goods")
public class GoodsAction extends BaseAction{
	@Autowired
	GoodsService goodsService;

	@Autowired
	ShopService shopService;

	@Autowired
	IDService idService;

	@Autowired
	ConfigClient configClient;

	/**
	 * 商品列表
	 */
	@RequestMapping("/goodsList")
	public String goodsList(HttpServletRequest req){
		UserBean user=getLoginUser(req);
		if(user==null) return "redirect:/login.html";
		long sid=getLong(req, "sid", 0);
		if(sid<=0){
			req.setAttribute("message","系统异常");
			return "/error";
		}
		req.setAttribute("pathLink", MyConstants.PATH_LINK);
		//req.setAttribute("sid", sid);
		req.getSession().setAttribute("sid", sid);

		ShopBean shop=shopService.getShop(sid);
		if(shop==null){
			req.setAttribute("message","系统异常,得不到该店铺信息");
			return "/error";
		}
		shopService.shopLogin(sid,user.getId());
		req.setAttribute("shop", shop);

		Page page=new Page();
		getPageInfo(req, page);
		Map p=new HashMap();
		p.put("sid", sid);
		
		int state=getInt(req,"state", 0);//默认所有商品正常
		
		String op=getString(req, "op","");
		if(op.trim().equals("insale")){//出售中
			p.put("state", state);
			//p.put("orderamount", 0);
			p.put("amount1", getDouble(req, "amount", 0));
		}
		if(op.trim().equals("soldout")){//已售罄
			//p.put("state", state);
			p.put("amount", getDouble(req, "amount", 0));
		}
		if(op.trim().equals("warehouse")){//仓库中
			p.put("amount1", getDouble(req, "amount", 0));
			p.put("state",9);
		}
		req.setAttribute("op", op);
		int ret=goodsService.listObj(p , page, "goods.getCount", "goods.listAll");
		if(ret!=0){
			req.setAttribute("message","系统异常,显示失败");
			return "/error";
		}
		req.setAttribute("page", page);
		return "/goods/list";
	}

	@RequestMapping("/toAddPage")
	public String toAddPage(HttpServletRequest req){
		UserBean user=getLoginUser(req);
		if(user==null) return "redirect:/login.html";
		long sid=getLong(req, "sid", 0);

		ShopBean shop=shopService.getShop(sid);
		if(shop==null||shop.getUid()!=user.getId()){
			req.setAttribute("message","系统异常");
			return "/error";
		}
		req.setAttribute("shop", shop);
		return "/goods/add";
	}
	/**
	 * 添加商品
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping("/addGoods")
	public String addGoods(HttpServletRequest req) throws JSONException {

		UserBean user=getLoginUser(req);
		req.setAttribute("op", "addgoods");
		if(user==null){
			req.setAttribute("result", "2002");
			req.setAttribute("message","请先登录");
			return "/afterop";
		}
		long id=idService.getId("tb_goods");
		GoodsBean g=new GoodsBean();
		g.setId(id);
		long sid=(Long) req.getSession().getAttribute("sid");
		g.setSid(sid);
		g.setName(getString(req, "name", "").trim());
		g.setPicts(getString(req, "picts", ""));
		g.setContent(getString(req, "content", ""));
		g.setPrice(getDouble(req,"price", 0));
		g.setAmount(getDouble(req,"amount", 0));//总数目
		g.setOrderamount(getDouble(req, "orderamount", 0));
		g.setUnit(getString(req,"unit", ""));
		g.setUserlimit(getDouble(req,"userlimit", 0));
		String deliverymethod=getString(req,"deliverymethod", "");
		g.setDeliverymethod(deliverymethod);
		g.setOprice(getDouble(req,"oprice", 0));//商品原价
		g.setPaytype(getString(req,"paytype", "线下支付"));//支付方式
		if(deliverymethod.equals("1")){
			g.setDeliveryfee(0);
		}
		else{
			g.setDeliveryfee(getDouble(req,"deliveryfee", 0));
		}
		String param="";
		JSONArray p = new JSONArray();
		for(int i = 1; i <= 3; i ++) {
			String pname = getString(req, "pn" + i, "");
			if(pname.length() ==0) continue;
			String pval = getString(req, "pv" + i, "");
			if(pval.length() == 0) continue;
			String[] pvs = null;
			try {pvs = pval.replaceAll("，",",").split(",");} catch (Exception e){};
			if(pvs == null || pvs.length ==0) continue;
			JSONObject p1 = new JSONObject();
			p1.put("pname", pname);
			//p1.put("pvalues", JSONArray.parseArray(pvs));//todo

			p.add(p1);
		}
		param = p.toString();
		g.setParam(param);
		
		g.setEvalcount(getLong(req, "evalcount", 0));
		//g.setRemarks(req.getParameter("remarks").toCharArray());
		int ret=goodsService.addGoods(g);
		if(ret!=0){
			req.setAttribute("result", "2003");
			req.setAttribute("message", "系统错误，添加失败");
			return "/afterop";
		}
		
		req.setAttribute("result", "0000");
		req.setAttribute("message", "添加成功");
		return "/afterop";
	}
	/**
	 * 推荐商品
	 */
	@RequestMapping("/rcmGoods")
	public void  rcmGoods(HttpServletRequest req, HttpServletResponse resp){
		UserBean ub=getLoginUser(req);
		GoodsBean gb=goodsService.getGoodsInfoByid(getLong(req, "id", 0));
		if(gb==null){
			writeJson(resp, "2001", "为无效商品",null);
			return;
		}
		if(gb.getRecommend()==0){goodsService.updateRcm(getLong(req, "id",0), 1);
		}
		else{
			goodsService.updateRcm(getLong(req, "id",0), 0);
		}
		writeJson(resp, "0000", "成功", null);
	}
	/**
	 * 下架/恢复商品
	 */
	@RequestMapping("/offGoods")
	public void offGoods(HttpServletRequest req, HttpServletResponse resp){
		UserBean ub=getLoginUser(req);
		GoodsBean gb=goodsService.getGoodsInfoByid(getLong(req, "id", 0));
		long sid=getLong(req, "sid", 0);
		if(gb==null||gb.getSid()!=sid){
			writeJson(resp, "2001", "为无效商品",null);
			return;
		}
		if(gb.getState()==0){
			goodsService.goodsOff(9, getLong(req, "id",0));//下架
			//gs.update(MyConstants.DB_MAIN, "goods.cancelOrder", getLong("id", 0));
		}
		if(gb.getState()==9){
			goodsService.goodsOff(0,getLong(req, "id",0));//正常
		}
		writeJson(resp, "0000", "成功", null);
	}
	/**
	 * 删除商品
	 */
	@RequestMapping("/delgoods")
	public void delgoods(HttpServletRequest req, HttpServletResponse resp){
		long id=getLong(req, "id", 0);
		
		int ret=goodsService.delGoods(id);
		if(ret!=0){
			writeJson(resp, "1002","系统异常，删除失败" , null);
			return ;
		}
		writeJson(resp, "0000", "ok", null);
		
	}
	/**
	 * 商品详细
	 */
	@RequestMapping("/getGoodsInfo")
	public String getGoodsInfo(HttpServletRequest req, HttpServletResponse resp){
		UserBean user=getLoginUser(req);
		if(user==null) return "redirect:/login.html";
		GoodsBean goods=goodsService.getGoodsInfoByid(getLong(req, "id", 0));
		if(goods==null){
			req.setAttribute("message","系统异常，没有该商品信息");
			return "/error";
		}
		req.setAttribute("g", goods);
		//long sid =getLong("sid", 0);
		ShopBean shop=shopService.getShop(goods.getSid());
		if(shop==null||shop.getUid()!=user.getId()){
			req.setAttribute("message","系统异常");
			return "/error";
		}
		req.setAttribute("shop", shop);
		return "/goods/goodsinfo";
	}
	/**
	 * 编辑商品
	 */
	@RequestMapping("/editgoods")
	public String editgoods(HttpServletRequest req, HttpServletResponse resp) throws JSONException{
		UserBean user=getLoginUser(req);
		req.setAttribute("op", "edit");
		if(user==null){
			req.setAttribute("result", "2002");
			req.setAttribute("message","请先登录");
			return "/afterop";
		}
		long sid=(Long)getSession(req, "sid");
		long id=getLong(req, "id",0);
		GoodsBean g=goodsService.getinfoById(id);
		if(g==null||g.getSid()!=sid){
			req.setAttribute("message", "没有权限对该商品进行操作");
			return "/error";
		}
		g.setId(getLong(req, "id", 0));
		g.setName(getString(req, "name", "").trim());
		g.setPicts(getString(req, "picts", ""));
		g.setContent(getString(req,"content", ""));
		g.setPrice(getDouble(req, "price", 0));
		g.setAmount(getDouble(req, "amount", 0));//总数目
		g.setOrderamount(getDouble(req,"orderamount", 0));
		g.setUnit(getString(req, "unit", ""));
		g.setUserlimit(getDouble(req,"userlimit", 0));
		
		double oprice=getDouble(req,"oprice", 0);
		if(oprice==0){
			g.setOprice(getDouble(req, "price", 0));
		}else{
			g.setOprice(oprice);//原价
		}
		String deliverymethod=getString(req,"deliverymethod", "");
		g.setDeliverymethod(deliverymethod);
		if(deliverymethod.equals("1")){
			g.setDeliveryfee(0);
		}else{
			g.setDeliveryfee(getDouble(req, "deliveryfee", 0));
		}
		String param="";
		JSONArray p = new JSONArray();
		for(int i = 1; i <= 3; i ++) {
			String pname = getString(req, "pn" + i, "");
			if(pname.length() ==0) continue;
			String pval = getString(req, "pv" + i, "");
			if(pval.length() == 0) continue;
			String[] pvs = null;
			try {pvs = pval.replaceAll("，",",").split(",");} catch (Exception e){};
			if(pvs == null || pvs.length ==0) continue;
			JSONObject p1 = new JSONObject();
			p1.put("pname", pname);
			//p1.put("pvalues", new JSONArray(pvs)); //TODO
			p.add(p1);
		}
		param = p.toString();
		g.setParam(param);
		g.setEvalcount(getLong(req, "evalcount", 0));
		g.setPaytype(getString(req, "paytype", "线下支付"));

		if(goodsService.editGoods(g)!=SUCCESS){
			req.setAttribute("result", "2003");
			req.setAttribute("message", "系统错误，编辑失败");
			return "/afterop";
		}
		req.setAttribute("result", "0000");
		req.setAttribute("message", "保存成功");
		return "/afterop";
	}
	/**
	 * 生成二维码
	 * @throws WriterException
	  */
	//TODO


	@RequestMapping("/getQRCode")
	public void getQRCode(HttpServletRequest req, HttpServletResponse resp) throws WriterException{
		long id=getLong(req, "id", 0);
		String text = configClient.get("PATH_LINK")+"?op=show&id="+id;
        int width = 300;
        int height = 300;
        //二维码的图片格式
        String format = "gif";
        Hashtable hints = new Hashtable();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
                BarcodeFormat.QR_CODE, width, height, hints);
        //生成二维码

        DateFormat pathdf = new java.text.SimpleDateFormat("/yyyy/MM/dd/HH/");
		String mp = pathdf.format(new Date());

		new File(MyConstants.PATH_DESK + mp).mkdirs();
		String destFile = mp + id + ".gif";

        File outputFile = new File(MyConstants.PATH_DESK+destFile);
        try {
			MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       writeJson(resp,"0000","", MyConstants.IMG_HOST+MyConstants.WEB_PATH + "qrcode" +destFile);
	}
}
