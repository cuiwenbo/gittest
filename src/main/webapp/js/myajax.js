 ////////////////Ajax start//////////////
	var xmlHttpRequest=false;
	//����XMLHttpRequest���󷽷�
	function createXMLHttpRequest(){
		xmlHttpRequest=false;
		//��IE����:���
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
		
		if(!xmlHttpRequest){//��� request �Ƿ���ȻΪ false�����һ��˳���Ͳ����� false)
			window.alert("Err Create XMLHttpRequest!");
		}
		 
	}

	//��������ĺ���
  	function sendRequest(url) {
		createXMLHttpRequest();
		//ָ��������
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
		//IE������(ע�����open()������)
		xmlHttpRequest.setRequestHeader("If-Modified-Since","0");
		// ����ͷ��Ϣ���������ݱ�����Ϊ����/ֵ�ԡ����Ǳ�׼�ı����ʽ�� 
		xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		//��������
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