//去掉前后的空格
function trim(data) {
	if(data == null) return "";
	return data.replace(/(^\s*)|(\s*$)/g, "");
}
function valiMark(){
   var remarks = document.getElementById("remarks");
   var lab = document.getElementById("lab");
   if(trim(remarks.value)==""){
      lab.style.display = "";
      return;
   }
   document.myform.submit();
}

//添加冻结/警告理由
function addResult(t){
   var areaResult = document.getElementById("areaResult");
   var para ="areaResult="+areaResult.value;
   if('1'==t){
	  var myAjax= new Ajax.Request("freezeResultAction!addFreezeResult.action",{method:'post',parameters:para,onComplete:pareResult,onError:error});
   }else{
      var myAjax= new Ajax.Request("warnResultAction!addwarnResult.action",{method:'post',parameters:para,onComplete:pareWarnResult,onError:error});
   }
   
}
function pareResult(response){
    var ret = eval('(' + response.responseText + ')');
    if(ret.msg == "same" ){
       alert("已经存在相同的理由");
    }else{
       window.location.href="freezeResultAction!index.action";
    }
}
function pareWarnResult(response){
	var ret = eval('(' + response.responseText + ')');
    if(ret.msg == "same" ){
       alert("已经存在相同的理由");
    }else{
       window.location.href="warnResultAction!index.action";
    }
}
//删除 理由, t :1 从freezeResult.ftl, 2从 warnResult.ftl  穿过来
function deleResult(resultId, t){
   if(confirm('是否确定要删除此理由')){
      if("1"== t){
          window.location.href="freezeResultAction!deleteFreezeResult.action?resultId="+resultId;
      }else{
         window.location.href="warnResultAction!deleteWarnResult.action?resultId="+resultId;
      }
      
   }
}
