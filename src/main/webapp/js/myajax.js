 ////////////////Ajax start//////////////
	var xmlHttpRequest=false;
	//创建XMLHttpRequest对象方法
	function createXMLHttpRequest(){
		xmlHttpRequest=false;
		//非IE；如:火狐
		if(window.XMLHttpRequest){
			xmlHttpRequest=new XMLHttpRequest();
			if(xmlHttpRequest.overrideMimeType){
				xmlHttpRequest.overrideMimeType('text/xml');
			}
		}else if(window.ActiveXObject){//IE
			try{
				xmlHttpRequest=new ActiveXObject("Msxml2.XMLHTTP");
			}catch(e){
				try{
					xmlHttpRequest=new ActiveXObject("Microsoft.XMLHTTP");
				}catch(e2){}
			}
		}
		
		if(!xmlHttpRequest){//检查 request 是否仍然为 false（如果一切顺利就不会是 false)
			window.alert("Err Create XMLHttpRequest!");
		}
		 
	}

	//发送请求的函数
  	function sendRequest(url) {
		createXMLHttpRequest();
		//指定处理函数
		xmlHttpRequest.onreadystatechange = function(){
			if(xmlHttpRequest.readyState == 4) {
				if(xmlHttpRequest.status == 200) {
					var result = xmlHttpRequest.responseText;
					if(callback != null)
						callback(result);
				}
			}	
		};
		//
		xmlHttpRequest.open("post",url,true);
		//IE缓存解决(注意加在open()方法后)
		xmlHttpRequest.setRequestHeader("If-Modified-Since","0");
		// 设置头信息，窗体数据被编码为名称/值对。这是标准的编码格式。 
		xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		//发送请求
		xmlHttpRequest.send(null);
	}
 ///////////////Ajax   end//////////////////


















/*
var Ajax={
	xmlhttp:function (){
		try{
			return new ActiveXObject('Msxml2.XMLHTTP');
		}catch(e){
			try{
				return new ActiveXObject('Microsoft.XMLHTTP');
			}catch(e){
				return new XMLHttpRequest();
			}
		}
	}
};
Ajax.Request=function (){
	if(arguments.length<2)return ;
	var para = {asynchronous:true,method:"GET",parameters:""};
	for (var key in arguments[1]){
		para[key] = arguments[1][key];
	}
	var _x=Ajax.xmlhttp();
	var _url=arguments[0];
	if(para["parameters"].length>0) para["parameters"]+='&_=';
	if(para["method"].toUpperCase()=="GET") _url+=(_url.match(/\?/)?'&':'?')+para["parameters"];
	_x.open(para["method"].toUpperCase(),_url,para["asynchronous"]);
	_x.onreadystatechange=function (){
		if(_x.readyState==4){
			if(_x.status==200)
				para["onComplete"]?para["onComplete"](_x):"";
			else{
				para["onError"]?para["onError"](_x):"";
			}
		}
	};
	if(para["method"].toUpperCase()=="POST")_x.setRequestHeader("Content-Type","application/x-www-form-urlencoded");

	for (var ReqHeader in para["setRequestHeader"]){
		_x.setRequestHeader(ReqHeader,para["setRequestHeader"][ReqHeader]);
	}
	_x.send(para["method"].toUpperCase()=="POST"?(para["postBody"]?para["postBody"]:para["parameters"]):null);
	return _x;
};
function error(response) {
    	//alert("run ajax failed!");
};
*/