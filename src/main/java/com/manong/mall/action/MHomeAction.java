package com.manong.mall.action;

import com.manong.mall.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MHomeAction extends BaseAction{
	@Autowired
	ShopService shopService;

	@RequestMapping("m/home")
	public String execute(HttpServletRequest req) {
		Page page=new Page();
		getPageInfo(req,page);
		page.setPageSize(20);
		Map p = new HashMap();
		shopService.listObj( p, page, "shop.getHomeCount", "shop.listHome");
		req.setAttribute("page", page);
		return "/m/home";
	}

}
