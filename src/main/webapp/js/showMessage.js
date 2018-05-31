var isIe=(document.all)?true:false;
//设置select的可见状态
function setSelectState(state)
{
var objl=document.getElementsByTagName('select');
for(var i=0;i<objl.length;i++)
{
objl[i].style.visibility=state;
}
}
function mousePosition(ev)
{
if(ev.pageX || ev.pageY)
{
return {x:ev.pageX, y:ev.pageY};
}
return {
x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,y:ev.clientY + document.body.scrollTop - document.body.clientTop
};
}
//弹出方法
function showMessageBox(wTitle,content,pos,wWidth,wHeight)
{
closeWindow();
var bWidth=parseInt(document.documentElement.scrollWidth);
var bHeight=parseInt(document.documentElement.scrollHeight);
if(isIe){
setSelectState('hidden');}
var back=document.createElement("div");
back.id="back";
var styleStr="top:0px;left:0px;position:absolute;background:#666;width:"+bWidth+"px;height:"+bHeight+"px;";
styleStr+=(isIe)?"filter:alpha(opacity=0);":"opacity:0;";
back.style.cssText=styleStr;
document.body.appendChild(back);
showBackground(back,50);
var mesW=document.createElement("div");
mesW.id="mesWindow";
mesW.className="mesWindow";
mesW.innerHTML="<div class='mesWindowTop'><table width='100%' height='100%'><tr><td>"+wTitle+"</td><td style='width:1px;'><input type='button' onclick='closeWindow();' title='关闭窗口' class='close' value='关闭' /></td></tr></table></div><div class='mesWindowContent' id='mesWindowContent'>"+content+"</div><div class='mesWindowBottom'></div>";
var v_top=(document.body.clientHeight-mesW.clientHeight)/2;
v_top+=document.documentElement.scrollTop;
styleStr="top:"+(v_top-180)+"px;left:"+(document.body.clientWidth/2-mesW.clientWidth/2)+"px;position:absolute;width:"+wWidth+"px;height:"+wHeight+"px;margin-left:-300px;left:50%;z-index:9999;";
mesW.style.cssText=styleStr;
document.body.appendChild(mesW);
}
//让背景渐渐变暗
function showBackground(obj,endInt)
{
if(isIe)
{
obj.filters.alpha.opacity+=5;
if(obj.filters.alpha.opacity<endInt)
{
setTimeout(function(){showBackground(obj,endInt)},5);
}
}else{
var al=parseFloat(obj.style.opacity);al+=0.05;
obj.style.opacity=al;
if(al<(endInt/100))
{setTimeout(function(){showBackground(obj,endInt)},5);}
}
}
//关闭窗口
function closeWindow()
{
if(document.getElementById('back')!=null)
{
document.getElementById('back').parentNode.removeChild(document.getElementById('back'));
}
if(document.getElementById('mesWindow')!=null)
{
document.getElementById('mesWindow').parentNode.removeChild(document.getElementById('mesWindow'));
}
if(isIe){
setSelectState('');}
}


function inMsgDevelop(ev,type,dename,userid,did){
	 var objPos = mousePosition(ev);
	  messContent="<div style='padding:20px 0 20px 0;text-align:center;'><table>";
	 if(type == 0){//拒绝
		 messContent+="<tr><td>开发者：<span style='color:red;'>";
		 messContent+= dename;
		 messContent+="</span></td><td><input type='radio' value='0' checked='checked' name='mrad' />身份不符合</td></tr>";
		 messContent+="<tr><td></td><td><input type='radio' value='1'  name='mrad' />信息不全或者有误</td></tr>";
		 messContent+="<tr><td></td><td><input type='radio' value='2'  name='mrad' />其它&nbsp;&nbsp;<input type='test'  name='txts' id='txts' value='' style='width:250px;border:solid green 1px;'/></td></tr>";
		 messContent+="<tr><td><input type='button' name='btn' value='确 定' style='border:solid green 1px;' onclick='refusedeve(";
		 messContent+=userid+",";
		 messContent+=did+",";
		 messContent+=type;		 
		 messContent+=");'/></td><td><input type='button' name='btn1' value='取消' style='border:solid green 1px;' onclick='closeWindow();'/></td></tr>";
		 messContent+="</table></div>";	 
	 }else{
	     messContent+="<tr><td>开发者：<span style='color:red;'>";
		 messContent+= dename;
	     messContent+="</span></td><td><input type='radio' value='0' checked='checked' name='mrad' />违规</td></tr>";
		 messContent+="<tr><td></td><td><input type='radio' value='1'  name='mrad' />发布不当应用</td></tr>";
		 messContent+="<tr><td></td><td><input type='radio' value='2'  name='mrad' />其它&nbsp;&nbsp;<input type='test'  name='txts' id='txts' value='' style='width:250px;border:solid green 1px;'/></td></tr>";
		 messContent+="<tr><td><input type='button' name='btn' value='确 定' style='border:solid green 1px;' onclick='refusedeve(";
		 messContent+=userid+",";
		 messContent+=did+",";
		 messContent+=type;
		 messContent+=");'/></td><td><input type='button' name='btn1' value='取消' style='border:solid green 1px;' onclick='closeWindow();'/></td></tr>";
		 messContent+="</table></div>";	
	 }
	 showMessageBox('拒绝开发者',messContent,objPos,600,200);
}

  function refusedeve(userid,did,type){
	  var m = document.getElementsByName("mrad");
	  var rad = 0;
	  var msg = "";
	  for(var i=0;i<m.length;i++){
	    if(m[i].checked){
	       rad = m[i].value;
	       break;
	    }
	  }
	  
	  if(rad == 2){
	     var tx = document.getElementById("txts").value;
	     if(tx == '' || tx.length == 0){
	       alert("请输入原因");
	       return;
	     }
	     msg = tx;
	  }
	  var para = "rad="+rad+"&msg="+msg+"&userid="+userid+"&did="+did+"&type="+type;
	  var myAjax=new Ajax.Request("develop!refusedeve.action",{method:'post',parameters:para,onComplete:retFuseve,onError:error});
	}

  function retFuseve(response){
	  	var ret = eval('(' + response.responseText + ')');
	    if(ret.message == 1){
	      window.location.reload();
	      //closeWindow();
	    }else{
	      alert(ret.message);
	    }
	}