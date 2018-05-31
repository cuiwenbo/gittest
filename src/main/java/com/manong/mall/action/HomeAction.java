package com.manong.mall.action;

import com.manong.mall.bean.ShopBean;
import com.manong.mall.bean.UserBean;
import com.manong.mall.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeAction extends BaseAction{
	@Autowired
	ShopService shopService;
	@RequestMapping("/home")
	public String execute(HttpServletRequest req) {
		UserBean u = getLoginUser(req);
		if(u == null) return "redirect:/login.html";
		req.setAttribute("user", u);
		List<ShopBean> sList=shopService.getAllByUser(u.getId());
		req.setAttribute("slist", sList);
		if(sList != null) req.setAttribute("slistsize",sList.size());
		return "/home";
	}

}
