/**
 弹出一个对话框
 param.title 标题
 param.text 正文
 param.height 窗口高度
 param.width 窗口宽度
*/
function add_pic(param) {

	var msgbox = document.getElementById("MessageBoxDiv");
	var clientWidth = document.documentElement.clientWidth||document.body.clientWidth;
	var clientHeight = document.documentElement.clientHeight||document.body.clientHeight;
	var DocHeight = document.documentElement.scrollHeight||document.body.scrollHeight;
	var DocWidth = document.documentElement.scrollWidth||document.body.scrollWidth;
	
	if(msgbox == null) {
		var box = "";		
		box += "<div id='MessageMask' style='z-index:1000;opacity:0.1;filter:alpha(opacity=10);background:#000;position:absolute;top:0;left:0'></div>"
		box += "<div id='MessageB' style='background:#fff;position:fixed;z-index:2001;border:0px solid #ccc; overflow:hidden;-webkit-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);-moz-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);'>";//外边框
		box += "	<div style='background:#fff;height:35px; width:100%;line-height:35px; color:#333; border-bottom:1px #ddd solid;'>";//TOP
		box += "		<div id='MessageBoxTitle' style='float:left;margin-left:10px;line-height:35px;font-size:14px; font-weight:bold;'></div>"; //top-content
		box += "		<div style='float:right;margin-right:10px;height:35px;line-height:35px;'><a style=' text-shadow:1px 1px 0px #fff;font-size: 20px;text-decoration:none;font-weight: bold;line-height: 35px;color: #000000;opacity: 0.2;fliter:alpha(opacity=20);' href='javascript:closeMessage();'>×</a></div>";	//x	
		box += "	</div>";
		box += "	<div id='MessageBoxContent' style='background-color:#fff;margin:0px;padding-top:0px;'>";//content
		box += "	</div>";
		box += "</div>";
		
		msgbox = document.createElement("div");
		msgbox.id = "MessageBoxDiv";		
		msgbox.style.display = "none";
		msgbox.style.position = "absolute";
		msgbox.style.left=0;
		msgbox.style.top=0;
		msgbox.innerHTML = box;
		$(msgbox).appendTo('body');
	} 
	
	
	if(msgbox == null) {
		alert("脚本异常，创建对话框失败");
		return;
	}
	var messageB = document.getElementById("MessageB");
	messageB.style.height = param.height + "px";
	messageB.style.width = param.width + "px";
	messageB.style.left=(clientWidth-param.width)/2+"px";
	messageB.style.top=(clientHeight-param.height)/2+"px";
	var Mask = document.getElementById("MessageMask");
	if(DocHeight<clientHeight){
		Mask.style.height=clientHeight+"px";
	}else{
		Mask.style.height=DocHeight+"px";
	}
	if(DocWidth<clientWidth){
		Mask.style.width=clientWidth+"px";
	}else{
		Mask.style.width=DocWidth+"px";
	}
	var title = document.getElementById("MessageBoxTitle");
	title.innerHTML = param.title;
	var content = document.getElementById("MessageBoxContent");
	content.style.height = (param.height-34) + "px";
	content.innerHTML = param.text;	
	msgbox.style.display = "";
	msgbox.style.zIndex = '3000';
}
//改变浏览器大小时触发
window.onresize=function(){
	if(document.getElementById("MessageB")){
	var messageB = document.getElementById("MessageB");
	var Mask = document.getElementById("MessageMask");
	var clientWidth = document.documentElement.clientWidth||document.body.clientWidth;
	var clientHeight = document.documentElement.clientHeight||document.body.clientHeight;
	var DocHeight = document.documentElement.scrollHeight||document.body.scrollHeight;
	var DocWidth = document.documentElement.scrollWidth||document.body.scrollWidth;
	var MB_height=messageB.offsetHeight;
	var MB_width=messageB.offsetWidth;
	
	messageB.style.left=(clientWidth-MB_width)/2+"px";
	messageB.style.top=(clientHeight-MB_height)/2+"px";
	if(DocHeight<clientHeight){
		Mask.style.height=clientHeight+"px";
	}else{
		Mask.style.height=DocHeight+"px";
	}
	if(DocWidth<clientWidth){
		Mask.style.width=clientWidth+"px";
	}else{
		Mask.style.width=DocWidth+"px";
	}
	}
}
//关闭
function closeMessage() {
	var msgbox = document.getElementById("MessageBoxDiv");
	if(msgbox == null) return;
	msgbox.style.display = "none";
}
//判断上传图片格式大小
function checkPic(){
	var Pic_url = document.getElementsByName('upload')[1].value.split('.');
	if(Pic_url==""){
		alert('请上传文件')
		return false;
	}else{
		pic_style =	Pic_url[Pic_url.length-1].toLowerCase();	
		if(pic_style!='jpg' && pic_style!='jpeg' && pic_style!='png' && pic_style!='gif' && pic_style!='bmp'){
			alert('图片格式不对！');
			return false;
		}
		if(navigator.userAgent.indexOf("MSIE")<0){
			if(document.getElementsByName('upload')[1].files[0].size>10485760){
				alert('图片大小必须小于10M')
				return false;
			}
		}
	}
	$('#Upload').attr('disabled','disabled')
	return true;	
}

















