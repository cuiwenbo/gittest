package com.manong.mall.service;

import com.manong.mall.bean.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("shopService")
public class ShopService extends BaseServiceImpl{
	//添加店铺
		public int addShop(ShopBean shopBean, UserBean user){
			try {
				add("shop.addshop", shopBean);
				if(user.getSeller()==0){
					update("user.setSeller", user);
				}
			} catch (Exception e){
				return FAILED;
			}
			return SUCCESS;
			//return add(MyConstants.DB_MAIN, "user.addshop", shopBean);
		}
		public ShopBean getShop(long sid) {
			return (ShopBean)get("shop.get", sid);
		}
		/**
		 * 添加店铺认证信息
		 * @param shop
		 * @param auditBean
		 * @return
		 */
		public int shopAddAuth(ShopBean shop, AuditBean auditBean){
			try {
				update("shop.updateAuth", shop);
				add("shop.Certification", auditBean);
			} catch (Exception e){
				return FAILED;
			}

			return SUCCESS;
		}
	//用户所有的店铺
		public List<ShopBean> getAllByUser(long uid){
			return list("shop.getAllByUser" ,uid);
		}
		public AuditBean getResult(long sid){
			return (AuditBean)get( "audit.getResult", sid);
		}
		/**
		 * 修改店铺信息
		 */
		public int editShop(ShopBean shop){
			return update( "shop.editinfo", shop);
		}
		
		
		/**
		 * 读取店铺扩展信息表
		 * @param id
		 * @return
		 */
		public ShopExtBean getShopExt(long id) {
			ShopExtBean ext = (ShopExtBean)get( "shop.getExt", id);
			if(ext == null) {
				ext = new ShopExtBean();
				ext.setId(id);				
				addShopExt(ext);
			}
			return ext;			
		}
		/**
		 * 添加店铺扩展表
		 * @param ext
		 */
		public void addShopExt(ShopExtBean ext) {
			add( "shop.addExt", ext);
		}
		
		
		/**
		 * 更新店铺提款帐号信息
		 * @param ext
		 */
		public  void updateDrawBank(ShopExtBean ext) {
			if( ext == null) return;
			add( "shop.updateExtBank", ext);
		}
		
		
		/**
		 * 店铺登录
		 * @param shopid
		 */
		public void shopLogin(long shopid,long uid) {
			try {
				Map p=new HashMap();
				p.put("sid", shopid);
				p.put("uid", uid);
				add("shop.addLoginLog", p);
				update("shop.updateExtLogin", shopid);
			} catch (Exception e) {
			
			}
		}
		/**
		 * 删除店铺
		 * @param id
		 * @return
		 */
		public int delShop(long id){
			try {delete("shop.del", id);
				delete("shop.delExt", id);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return FAILED;
			}
			return SUCCESS;
			
		}
		 public ShopBean getLast(long uid){
			 return (ShopBean)get("shop.topage",uid);
		 }
		 
		 public ShopBean checkshopName(String name){
			 return (ShopBean)get("shop.check", name);
		 }
		 public AuditBean getAudit(long sid){
			 return (AuditBean)get( "shop.selAudit", sid);
		 }
		/**
		 * 取消认证
		 * @param shop
		 * @param audit
		 * @return
		 */
		 public int cancelCer(ShopBean shop,AuditBean audit){

			 try {

				shop.setAuthed(0);//未认证
				update("shop.updateAuth", shop);
				audit.setAudit(9);//已取消状态
				audit.setRemarks("已取消认证");
				update("shop.cancelAudit", audit);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return FAILED;
			}
			return SUCCESS;
			 
		 }
		 
		 /**
		  * 更新店铺的最后访问时间和访问次数
		  * @param sb
		  * @return
		  */
		 public int updateShopShow(ShopBean sb) {
			 return update("shop.updateShow", sb);
		 }
		 /**
		  * 修改收款账号
		  * @param ext
		  * @return
		  */
		 public int updateBankNo(ShopExtBean ext){
			 return update( "shop.editBankNo", ext);
		 }
		 /**
		  * 关注
		  */
		 public ShopFocusAndVisitBean getFocusinfo(long uid, long sid){
			 Map p=new HashMap();
			 p.put("uid", uid);
			 p.put("sid", sid);
			 return (ShopFocusAndVisitBean)get("shop.focus_getinfo", p);
		 }
		 public int addFocus(ShopFocusAndVisitBean svBean){
			return add("shop.focus_insertInfo", svBean);
		 }
		 public int deleteFocus(ShopFocusAndVisitBean svBean){
			 return delete( "shop.focus_cancel",svBean);
		 }
		 /**
		  * 修改手机号
		  */
		 public int setPhone(ShopBean shop){
			 return update("shop.setPhone", shop);
		 }
}
