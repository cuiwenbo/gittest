package com.manong.mall.action;

import com.manong.mall.api.FileService;
import com.manong.mall.utils.CacheTools;
import com.manong.mall.utils.ConfigClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class FileUploadAction extends BaseAction{
	public static Logger logger = Logger.getLogger("BizLog");

	@Resource
	ConfigClient configClient;

	@Autowired
	CacheTools jedisTools;

	@Resource
	FileService fileService;

	@RequestMapping("/**/upload")
	public String upload(@RequestParam(value = "upload", required = true) MultipartFile file, HttpServletRequest req,
					   HttpServletResponse resp) {
		logger.debug("\n\n\n=======upload============\n");
		String op = getString(req, "op", "upload");
		req.setAttribute("op", op);
		if(file == null || file.getSize() == 0) {
			req.setAttribute("result", "1000");
			req.setAttribute("message",  "无效文件");
			return "/afterop";
		}

		String baseWebPath = configClient.get("comm.upload.basepath");
		String fileName = file.getOriginalFilename();
		String ext = "data";
		int idx = fileName.lastIndexOf(".");
		if (idx > -1)
			ext = fileName.substring(idx + 1);
		try {
			byte[] buff = file.getBytes();
			String r = fileService.save(2, buff, ext);
			String dest = baseWebPath + r;
			String message = fileName + "," + baseWebPath + "," + r + ",";
			req.setAttribute("result", "0000");
			req.setAttribute("data", message);
			return "/afterop";
		} catch (Exception e) {
			req.setAttribute("result", "1000");
			req.setAttribute("message",   "系统故障,上传文件失败");
			return "/afterop";
		}
	}
}
