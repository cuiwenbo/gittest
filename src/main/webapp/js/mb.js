/**
 弹出一个对话框
 param.title 标题
 param.text 正文
 param.height 窗口高度
 param.width 窗口宽度
*/
function showMessage(param) {

	var msgbox = document.getElementById("MessageBoxDiv");
	if(msgbox == null) {
		var box = "";		
		box += "<div style='0px solid #ccc; overflow:hidden;'>";//外边框
		box += "	<div style='height:27px; width:100%;padding-top:7px; color:#red; border-bottom:1px #d2d2d2 solid;'>";//TOP
		box += "		<div id='MessageBoxTitle' style='float:left;margin-left:10px;line-height:22px;text-shadow:1px 1px 0px #fff; font-weight:bold;'></div>"; //top-content
		box += "		<div style='float:right;margin-right:10px;height:24px;line-height:22px;'><a style='color:#333; font-size:12px; text-shadow:1px 1px 0px #fff;' href='javascript:closeMessage();'>关闭[×]</a></div>";	//x	
		box += "	</div>";
		box += "	<div id='MessageBoxContent' style='background-color:#fff;margin:0px;padding-top:0px;'>";//content
		box += "	</div>";
		box += "</div>";
		
		msgbox = document.createElement("div");
		msgbox.id = "MessageBoxDiv";		
		msgbox.style.display = "none";
		msgbox.style.position = "absolute";
		msgbox.style.border = "3px solid #90b0e3";
		msgbox.style.background="#f6f6f6";
		msgbox.innerHTML = box;
		document.body.appendChild(msgbox);
	} 
	
	
	if(msgbox == null) {
		alert("脚本异常，创建对话框失败");
		return;
	}
	msgbox.style.height = param.height + "px";
	msgbox.style.width = param.width + "px";
	msgbox.style.top = "10px";
	if(param.top != null) {
		msgbox.style.top = param.top + "px";
	}
	msgbox.style.left = "100px";
	if(param.left != null) {
		msgbox.style.left = param.left + "px";
	}
	
	var title = document.getElementById("MessageBoxTitle");
	title.innerHTML = param.title;
	var content = document.getElementById("MessageBoxContent");
	content.style.height = (param.height-34) + "px";
	content.innerHTML = param.text;	
	msgbox.style.display = "";
}
//关闭
function closeMessage() {
	var msgbox = document.getElementById("MessageBoxDiv");
	if(msgbox == null) return;
	msgbox.style.display = "none";
}