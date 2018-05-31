<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.json.*" %>
<%@ page import="java.awt.*" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGCodec" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGImageEncoder" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%

/**
 * KindEditor JSP
 * 
 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
 *      webpath="http://news.zghdjks.com:8091/" 
 *    diskpath="/deploy/newhdjk/"
 */

//文件保存目录路径
String savePath = pageContext.getServletContext().getRealPath("/") + "attached/";
//文件保存目录URL
String saveUrl  =  request.getContextPath() + "/attached/";
//水印图片路径
//String waterImage=Constants.getWaterImgPath(request);
//判断文件夹是否存在，不存在则创建
if(!new File(savePath).isDirectory()){
	new File(savePath).mkdir();
}
if(!new File(saveUrl).isDirectory()){
	new File(saveUrl).mkdir();
}
//定义允许上传的文件扩展名
String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
//最大文件大小
long maxSize = 1000000;

response.setContentType("text/html; charset=UTF-8");

if(!ServletFileUpload.isMultipartContent(request)){
	out.println(getError("请选择文件。"));
	return;
}
//检查目录
File uploadDir = new File(savePath);
if(!uploadDir.isDirectory()){
	out.println(getError("上传目录不存在。"));
	return;
}
//检查目录写权限
if(!uploadDir.canWrite()){
	out.println(getError("上传目录没有写权限。"));
	return;
}

FileItemFactory factory = new DiskFileItemFactory();
ServletFileUpload upload = new ServletFileUpload(factory);
upload.setHeaderEncoding("UTF-8");
java.util.List items = upload.parseRequest(request);
Iterator itr = items.iterator();
while (itr.hasNext()) {
	FileItem item = (FileItem) itr.next();
	String fileName = item.getName();
	long fileSize = item.getSize();
	if (!item.isFormField()) {
		//检查文件大小
		if(item.getSize() > maxSize){
			out.println(getError("上传文件大小超过限制。"));
			return;
		}
		//检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(fileTypes).contains(fileExt)){
			out.println(getError("上传文件扩展名是不允许的扩展名。"));
			return;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
		String newPath=savePath+newFileName;
		try{
			File uploadedFile = new File(newPath);
			item.write(uploadedFile);
			Image img = ImageIO.read(uploadedFile);
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(img, 0, 0, width, height, null);
			//File _filebiao = new File(waterImage);
            //Image src_biao = ImageIO.read(_filebiao);
            //int wideth_biao = src_biao.getWidth(null);
            //int height_biao = src_biao.getHeight(null);
			//g.drawImage(src_biao, width - wideth_biao - 30, height - height_biao -30, wideth_biao,height_biao, null);
			g.dispose();
			FileOutputStream os = new FileOutputStream(newPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			encoder.encode(image);
			os.close();
		}catch(Exception e){
			e.printStackTrace();
			out.println(getError("上传文件失败。"));
			return;
		}

		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", saveUrl + newFileName);
		out.println(obj.toString());
	}
}
%>
<%!
private String getError(String message) {
    try{
	  JSONObject obj = new JSONObject();
	  obj.put("error", 1);
	  obj.put("message", message);
	  return obj.toString();
	}catch(Exception e){
	  e.printStackTrace();
	}
	return null;
}
%>