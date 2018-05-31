	function createParamsObject(){
		var obj=new Object();
		obj.array=new Array();
		obj.toJsonString=function(){
			return obj.array.join("&");
		}
		obj.toArrayString=function(){
			return obj.array.join(",");
		}
		obj.putJson=function(keyString,valueString){
			if(valueString.length>0)
				obj.array.push(keyString+"="+valueString);
		}
		obj.putArray=function(valueString){
			if(valueString.length>0)
				obj.array.push(valueString);
		}
		return obj;
	}
	function queryCheckbox(checkboxHead,checkboxSub){
		var value=$j('#'+checkboxHead).attr('checked')?true:false;
		var chs=$j("input[name='"+checkboxSub+"']");
		for(var i=0;i<chs.length;i++){
			chs[i].checked=value;
		}
	}
	function queryCheckIds(checkboxSub){
		var chs=$j("input[name='"+checkboxSub+"']");
		var myData=createParamsObject();
		for(var i=0;i<chs.length;i++){
			if(chs[i].checked==true)
				myData.putArray(chs[i].value);
		}
		return myData.toArrayString();
	}
	function urlRequestHandle(url,callbackUrl){
		$j.ajax({
			type:"POST",
			url:url,
			dataType:"text",
			success:function(data){
				if(data=='success'){
					alert("处理成功");
					window.location.href=callbackUrl;
				}else if(data=='error'){
					alert("操作未成功");
				}else{
					alert(data);
				}
			}
		});
	}
	function dataHandle(handle,checkboxSub,url,callbackUrl){
		var ids=clearParam(queryCheckIds(checkboxSub));
		if(ids.length<=0){
			alert("请先选择数据");
			return;
		}
		if(handle!=0 && handle!=1){
			alert("参数异常");
			return;
		}
		var myData=createParamsObject();
		myData.putJson("ids",ids);
		myData.putJson("state",''+handle);
		$j.ajax({
			type:"POST",
			url:url,
			data:myData.toJsonString(),
			dataType:"text",
			success:function(data){
				if(data=='success'){
					alert("处理成功");
					window.location.href=callbackUrl;
				}else if(data=='error'){
					alert("操作未成功");
				}
			}
		});
	}
	function mulDataHandle(checkboxSub,url,callbackUrl,paramsArray){
		var idsTemp=queryCheckIds(checkboxSub);
		var ids=clearParam(idsTemp);
		if(ids.length<=0){
			alert("请先选择数据");
			return;
		}
		if(typeof(paramsArray)=='undefined'){
			alert("参数异常");
			return;
		}
		var pArray=paramsArray.split(",");
		var myData=createParamsObject();
		myData.putJson("temp0", ids);
		var idsArray=ids.split(",");
		for ( var int = 0; int < pArray.length; int++) {
			var myData2=createParamsObject();
			for ( var int2 = 0; int2 < idsArray.length; int2++) {
				var strs=pArray[int].split("?");
				var tempValue="";
				if(strs.length==2){
					if(strs[1]=='radio'){
						tempValue=$j("input[name='"+strs[0]+''+idsArray[int2]+"']:checked").val();
						if(typeof(tempValue)=='undefined'){
							tempValue="undefined";
						}
					}else if(strs[1]=='checkbox'){
						var chs=$j("input[name='"+strs[0]+''+idsArray[int2]+"']");
						for(var i=0;i<chs.length;i++){
							if(chs[i].checked==true){
								tempValue=tempValue+chs[i].value;
							}else{
								tempValue=tempValue+"8";
							}
						}
					}else{
						tempValue="";
					}
				}else if(strs.length==1){
					tempValue=$j('#'+pArray[int]+''+idsArray[int2]).val();
				}
				myData2.putArray(tempValue);
			}
			myData.putJson("temp"+(int+1),myData2.toArrayString());
		}
		$j.ajax({
			type:"POST",
			url:url,
			data:myData.toJsonString(),
			dataType:"text",
			success:function(data){
				if(data=='success'){
					alert("处理成功");
					window.location.href=callbackUrl;
				}else if(data=='error'){
					alert("操作未成功");
				}else{
					alert(data);
				}
			}
		});
	}
	function clearParam(param){
		var _value=$j.trim(param);
		if(_value && _value.length!=0){
			return _value;
		}
		return "";
	}