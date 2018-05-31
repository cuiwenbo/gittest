//获取ID
function $id(id){
return document.getElementById(id);
}
function ShowMsg(){
document.writeln("<div id=\"msgdiv\" style=\"position:absolute;display:none;border:1px solid #AFCEF9;\"><\/div>");
document.writeln("<div id=\"overdiv\" style=\"position:absolute;display:none;\">");
document.writeln("<\/div>");
//回调函数
this.ok_callback=function(){};
this.cancel_callback=function(){};
this.msgobjname=""
this.show=function(msgtitle,msgcontent,widthx,heightx){
  if(widthx == null) widthx = 400;
  if(heightx == null) heightx = 230;
  var tempobj1=$id("msgdiv");
  var tempobj2=$id("overdiv");
  var msgobj=this.msgobjname;
    tempobj2.style.filter="alpha(opacity=75)";
    tempobj2.style.MozOpacity = 75/100;
    tempobj2.style.backgroundColor = "#FFFFFF";
    tempobj2.style.display = '';
    tempobj2.style.zIndex= 1000;
    ht1=document.body.scrollHeight;
    ht2=window.screen.height;
   
    if(ht1 < ht2) {
    	tempobj2.style.height = ht2 + "px";
    } else {
    	tempobj2.style.height = ht1 + "px";
    }
   
    
  tempobj2.style.width= document.body.clientWidth+"px";
  tempobj2.style.left=0;
  tempobj2.style.top=0;
  tempobj1.style.display="none";
  
  var de = document.documentElement;
  
     var w = window.innerWidth || self.innerWidth || (de&&de.clientWidth) || document.body.clientWidth;
  
      tempobj1.style.left=(w-widthx)/2+"px";
    //tempobj1.style.left= (document.documentElement.clientWidth)/3+"px";
    tempobj1.style.top = (document.body.scrollTop + 100) + "px";
    tempobj1.style.display= '';
    tempobj1.style.width=widthx+"px";
    tempobj1.style.height=heightx+"px";
    tempobj1.style.zIndex= 2000;
    tempobj1.style.backgroundColor = "#ffffff";
    var OutStr;
  
  	OutStr="<div id=\"TB_title\" canmove='true' forid=\"msgdiv\"><div id=\"TB_ajaxWindowTitle\">"+msgtitle+"</div><div id=\"TB_closeAjaxWindow\"><a href=\"javascript:"+msgobj+".cancel()\" style='text-decoration:none;color:#333333'>关闭</a></div></div>"  
    OutStr=OutStr+"<div  id=\"title_2\">"+msgcontent+"</div>"
    
    tempobj1.innerHTML=OutStr;
    var md=false,mobj,ox,oy
    document.onmousedown=function(ev)
    {
  	var ev=ev||window.event;
	var evt=ev.srcElement||ev.target;
          if(typeof(evt.getAttribute("canmove"))=="undefined")
          {
              return;
          }
          if(evt.getAttribute("canmove"))
          {
              md = true;
              mobj = document.getElementById(evt.getAttribute("forid"));
              ox = mobj.offsetLeft - ev.clientX;
              oy = mobj.offsetTop - ev.clientY;
          }

      }
      document.onmouseup= function(){md=false;}
      document.onmousemove= function(ev)
      {
  var ev=ev||window.event;
          if(md)
          {
              mobj.style.left= (ev.clientX + ox)+"px";
              mobj.style.top= (ev.clientY + oy)+"px";
          }
      }
  }
  this.ok = function()
  {
  $id('msgdiv').style.display='none';
  $id('overdiv').style.display='none';
  this.ok_callback();
  }
  this.cancel=function(){
  $id('msgdiv').style.display='none';
  $id('overdiv').style.display='none';
  this.cancel_callback();
  }
}
var ShowMsgo=new ShowMsg();
//设置对象名,供内部引用
ShowMsgo.msgobjname="ShowMsgo";
//定义回调函数
ShowMsgo.ok_callback=function(){
//alert("ok");
}
ShowMsgo.cancel_callback=function(){
//alert("cancel")
}
