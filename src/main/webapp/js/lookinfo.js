 var isp = true;
 function setInterval(){
	var para="";
	var myAjax=new Ajax.Request("soft!returnValue.action",{method:'post',parameters:para,onComplete:retlook,onError:error});
 }
 function retlook(response){
   var ret = eval('(' + response.responseText + ')');
   if(ret.message == 1){
         var percent = parseInt(ret.percent);
         var sumSize = ret.sumSize;
         var hasSize = ret.hasSize;
         var uploaded = ret.uploaded;
         if(percent > 0 ){
           document.getElementById("pbar").style.display = "";
           $("#innerDivs").css("width",percent+"%");
           $("#msg").text("大小："+sumSize+",已传："+uploaded+",剩余："+hasSize+"当前上传:"+percent+"%");          
         }
         if(percent == 100){
           isp = false;
           document.getElementById("pbar").style.display = "none";
           document.getElementById("lodingm").style.display = "none";
         }else{
           if(percent == 0 ){
             document.getElementById("lodingm").style.display = "";
           }else{
             document.getElementById("lodingm").style.display = "none";
           }
           ispS();
         }
	  }else{
	    ispS();
	  }
}	
 function ispS(){
       setTimeout("setInterval()",1000);
 }