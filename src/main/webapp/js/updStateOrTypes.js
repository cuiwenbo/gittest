//审核、推荐等状态修改通用js
function retIdsData(id){
	var ids="";
	var cbox = document.getElementsByName("cbox_m");
	if(id&&id!=""){
		ids=id;
	}else{
		var cbox = document.getElementsByName("cbox_m");
		if (cbox) {
			for (var i=0; i<cbox.length; i++) {
				if (cbox[i].checked) {
					ids = ids + cbox[i].value + ",";
				} 
			}
		}
		ids = ids.substring(0, ids.length-1);
	}
	return ids;
}

function updState(tag,url,id){
	var ids=retIdsData(id);
	if(tag==2){
		if(confirm("您确定删除数据！")){
			if(ids.length<=0){
				alert("请选择要操作的数据！");
				return false;
			}
			var param="tag="+tag+"&ids="+ids;
			var myAjax=new Ajax.Request(url,{method:'post',parameters:param,onComplete:retState,onError:error});
		}
	}else{
		if(ids.length<=0){
			alert("请选择要操作的数据！");
			return false;
		}
		var param="tag="+tag+"&ids="+ids;
		var myAjax=new Ajax.Request(url,{method:'post',parameters:param,onComplete:retState,onError:error});
	}
}

function retState(response){
	var ret = eval('(' + response.responseText + ')');
	var res=ret.res;
	if(res==-1){
		alert("你还没有登录或登录超时，请重新登录！");
		return false;
	}else if(res==0){
		alert("操作数据有误，请重新选择！");
		window.location.reload();
		return false;
	}else{
		window.location.reload();
	}
}