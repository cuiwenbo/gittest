package com.manong.mall.service;

import com.manong.mall.bean.GoodsBean;
import com.manong.mall.bean.ShopFocusAndVisitBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("goodService")
public class GoodsService extends BaseServiceImpl {
	@Autowired
	IDService idService;

	//添加商品
	public int addGoods(GoodsBean goods){
		return add("goods.insert", goods);
	}
	//推荐商品
		public int updateRcm(long id,int recommend){
			Map r=new HashMap();
			r.put("id",id);
			r.put("recommend",recommend);
			return update("goods.recommend" , r);
		}
	//根据id得到该商品具体信息
	public GoodsBean getGoodsInfoByid(long id){
		return (GoodsBean)get( "goods.getGoodsById", id);
	}
	//删除商品
	public int delGoods(long id){
		return delete("goods.del", id);
	}
	
	//商品下架
	public int goodsOff(int state,long id){
	
		Map o=new HashMap();
		o.put("id",id);
		o.put("state",state);
		
		return update("goods.off", o);
	}
	//商品详细信息
	public GoodsBean getinfoById(long id){
		return (GoodsBean)get("goods.getinfoByid", id);
	}
	//编辑商品
	public int editGoods(GoodsBean goods){

		try {

			int ordercount = (Integer)get("goods.getOrderCount", goods.getId());
			if(ordercount == 0) {
				update("goods.edit", goods);
			} else {				
				update("goods.unvalidOrder", goods.getId());
				update("goods.setUnValid", goods.getId());
				goods.setPid(goods.getPid() == 0?goods.getId():goods.getPid());				
				goods.setId(idService.getId("tb_goods"));
				add("goods.insert", goods);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		}
		return SUCCESS;
	}
	/**
	 * 将已有订单的商品改为失效商品
	 * @param id
	 * @return
	 */
	public int motifyV(long id){
		return update("goods.modifyValid", id);
	}
	/**
	 * 浏览记录
	 */
	public ShopFocusAndVisitBean getVisit(long uid, long sid, long gid){
		Map g=new HashMap();
		g.put("uid",uid);
		g.put("sid",sid);
		g.put("gid", gid);
		return (ShopFocusAndVisitBean)get("goods.getinfo",g );
	}
	public int addVisit(ShopFocusAndVisitBean v){
		return add("goods.addVisit", v);
	}
	public int editVisit(long id){
		return update("goods.editTime", id);
	}
	public int editSTime(long id){
		return update("goods.editShowTime", id);
	}
}
