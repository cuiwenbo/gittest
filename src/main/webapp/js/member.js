function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
//ajax查询 会员名称 是否存在
function existsName(name){
	if (name==null || name=="" || name.length > 20) {
		var userSpan = document.getElementById("userNameSpan");
		userSpan.innerHTML = "<font color='red'>用户名为空或长度超过20个字符，请重新输入。</font>";
		return false;
	}
    var para = "userName="+name;
	var myAjax = new Ajax.Request("member!checkName.action",{method:'post',parameters:para,onComplete:retName,onError:error});
	return true;
}
function retName(response){
  	var userSpan = document.getElementById("userNameSpan");
  	var ret = eval('(' + response.responseText + ')');
  	if (ret.message == 'yes') {
     	userSpan.innerHTML = "<font color='red'>该用户名已被注册，请重新输入。</font>";
  	} else if(ret.message == 'no'){
    	userSpan.innerHTML = "请输入用户名（不能超过20个字符）";
  	} else {
  		//会员名称为空
  		userSpan.innerHTML = "<font color='red'>" + ret.message + "</font>";
  	}
}

//ajax查询 email 是否存在
function existsEmail(email){
	var id = document.getElementById("id").value;
    var para = "email="+email;
    if(id){
    	para = para + "&id=" + id;
    }
	var myAjax = new Ajax.Request("member!checkEmail.action",{method:'post',parameters:para,onComplete:retEmail,onError:error});
}
function retEmail(response){
  	var emailSpan =document.getElementById("emailSpan");
  	var ret = eval('(' + response.responseText + ')');
  	if (ret.message == 'yes') {
     	emailSpan.innerHTML = "<font color='red'>该电子邮件地址已被注册，请重新输入</font>";
  	} else if (ret.message == 'no') {
    	emailSpan.innerHTML = "请填写有效的Email地址";
  	} else {
  		//email为空
  		emailSpan.innerHTML = "<font color='red'>" + ret.message + "</font>";
  	}
}

//检查email格式有效性
function checkEmail(obj){
	var value = obj.value;
	var emailSpan =document.getElementById("emailSpan");
	if(value==""){
		emailSpan.innerHTML = "<font color='red'>电子邮件地址不能为空</font>";
		return false;
	}else if(!(/^([a-zA-Z0-9]+[_|\_|\-\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(value)==true)){
		emailSpan.innerHTML = "<font color='red'>电子邮件地址格式不正确</font>";
		return false;
	}else{
	   	existsEmail(value);
	}
	return true;
}

//ajax查询 推荐人 是否存在
function existsPUser(name){
    var para = "pName="+name;
	var myAjax = new Ajax.Request("member!checkPUser.action",{method:'post',parameters:para,onComplete:retPUser,onError:error});
}
function retPUser(response){
  	var pNameSpan = document.getElementById("pNameSpan");
  	var ret = eval('(' + response.responseText + ')');
  	if (ret.message == 'yes') {
     	pNameSpan.innerHTML = "";
  	} else if (ret.message == 'no'){
    	pNameSpan.innerHTML = "<font color='red'>系统不存在此推荐人，请核对后重新输入。</font>";
  	} else {
  		//推荐人为空
  		pNameSpan.innerHTML = "";
  	}
}

function checkAnswer(answer) {
	var answerSpan = document.getElementById("answerSpan");
	if( document.getElementById("quest").value != -1 && (answer == null || answer=="" || answer.length < 6 || answer.length > 20) ){
		answerSpan.innerHTML = "为了您个人资料的安全，请使用6~20个字符";
	}
	answerSpan.innerHTML = "";
}

function checkPwd(word) {
	var passwordSpan = document.getElementById("passwordSpan");
	if (word == null || word=="" || word.length < 6 || word.length > 20) {
		passwordSpan.innerHTML = "<font color='red'>密码长度少于6位或超过20位，请重新输入。</font>";
		return false;
	}
	passwordSpan.innerHTML = "(为了您个人资料的安全，建议使用字母加数字的复杂密码。为6~20个字符)";
	return true;
}

function checkProvince(province) {
	if(province == null || -1 == province.value ){
	 	addressSpan.innerHTML = "<font color='red'>所在地区不能为空。</font>";
	 	return false;
	}	
	addressSpan.innerHTML = "";
	return true;
}

function checkArea(area) {
	if(area == null || -1 == area.value ){
	 	addressSpan.innerHTML = "<font color='red'>所在地区不能为空。</font>";
	 	return false;
	}	
	addressSpan.innerHTML = "";
	return true;
}


function checkRePwd(word) {
	var repwdSpan = document.getElementById("repwdSpan");
	if (document.getElementById("password").value == word) {
		repwdSpan.innerHTML = "(必须和上面输入的密码一致)";
		return true;
	} else {
		repwdSpan.innerHTML = "<font color='red'>两次输入密码不一致，请重新输入。</font>";
		return false;
	}
}

function checkQqmsn(qqmsn) {
	var qqmsnSpan = document.getElementById("qqmsnSpan");
	if (qqmsn == null || qqmsn =="" || qqmsn.length < 5 || qqmsn.length > 20){
		qqmsnSpan.innerHTML = "<font color='red'>qqmsn长度少于5位或超过20位，请重新输入。</font>";
		return false;
	}else {
		qqmsnSpan.innerHTML = "";
		return true;
	}
}

function checkExt(obj){
		var AllImgExt=".jpg|.jpeg|.gif|.bmp|.png|";  //全部图片格式类型
		var FileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
		if(AllImgExt.indexOf(FileExt+"|")!=-1){    //如果图片文件，则进行图片信息处理
 		   	document.getElementById("photoShow").src=obj.value;
 		   	return false;
		}else{
    		alert("请选择gif或jpg,png的图象文件");
		}
	}
	
		//ajax 返回方法(所有都一样，公用一个)
function retObjs(response) {
	var ret = eval('(' + response.responseText + ')');
	if(ret.message=='yes'){
	    alert("操作成功");
  		window.location.reload();
  	} else {
  		alert(ret.message);
  	}
}
	
function excuteAll() {
	
		var ids = "";
		var cbox = document.getElementsByName("cbox_m");
		if (cbox) {
			for (var i=0; i<cbox.length; i++) {
				if (cbox[i].checked) {
					ids = ids + cbox[i].value + ",";
				} 
			}
		}
		
		ids = ids.substring(0, ids.length-1);
		var radios = document.getElementsByName("caozuo");
		var temp = "";
		if (radios) {
			for (var i=0; i<radios.length; i++) {
				if (radios[i].checked) {
					temp = radios[i].value;
					break;
				}
			}
		}else {
			alert("请选择操作类别");
		}
		var type = document.getElementById("type").value;
		var para = "ids=" + ids;
		var url = "member!updateState.action";
		if (temp == 1) {
			//	删除
				url = "member!delMember.action";
			} else if (temp == 2) {
			//	锁定
				para = para +"&state=" + 2;
			} else if (temp == 3) {
			//	解锁
				para = para +"&state=" + 1;
			} else if (temp == 5) {
			//	移动到
			 	url = "member!updateType.action";
				para = para +"&type=" + type;
			}
			
			var myAjax=new Ajax.Request(url, {method:'post',parameters:para,onComplete:retObjs,onError:error});
		}
	
function updateState(state, url, id) {
	
			if(state == 0) {
		//删除操作提示一下
		if(!confirm("是否要删除？")){
			return false;
		}
	}
	var ids = "";
	if (id && id != "") {
		ids = id;
	} else {
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
	var para = "ids=" + ids + "&state=" + state;
	var myAjax=new Ajax.Request(url, {method:'post',parameters:para,onComplete:retObjs,onError:error});
}



function addMember(flag) {
	var canSubmit = true;
	var userName = document.getElementById("userName").value;
	canSubmit = existsName(userName) && canSubmit;   //检查用户名是否填写 1-20位
	var pwd = document.getElementById("password").value;
	canSubmit = checkPwd(pwd) && canSubmit;         //检查密码是否填写 6-20位
	var repwd = document.getElementById("repassword").value;
	canSubmit = checkRePwd(repwd) && canSubmit;     //检查2次密码是否一致
	var email = document.getElementById("email");
	canSubmit = checkEmail(email) && canSubmit;     //检查email格式合法性
	var province = document.getElementById("province");
	canSubmit = checkProvince(province) && canSubmit;
	var area = document.getElementById("area");
	canSubmit = checkArea(area) && canSubmit;
	
	var province = document.getElementById("province");  //省
   	if( province.value != -1){
   		var proIndex = province.selectedIndex;   
   		var proText = province.options[proIndex].text; 
   		document.getElementById("provstr").value = proText;
   	}
   
   	var area = document.getElementById("area");          //市
   	if(area.value != -1){
   		var areaIndex = area.selectedIndex;   
   		var areaText = area.options[areaIndex].text; 
   		document.getElementById("areastr").value = areaText;
   	}
	
	if(flag==1){
		document.getElementById("flag").value = 1;
	}
	if (canSubmit) {
		document.getElementById("addForm").submit();
	}
}

function updateMember() {
	var canSubmit1 = true;
	var pwd = document.getElementById("password").value;
	var repwd = document.getElementById("repassword").value;
	if((pwd != null && pwd != "") || (repwd != null && repwd != "") ){
		canSubmit1 = checkPwd(pwd) && canSubmit1;         //检查密码是否填写 6-20位
		canSubmit1 = checkRePwd(repwd) && canSubmit1;     //检查2次密码是否一致
	}
	var email = document.getElementById("email");
	canSubmit1 = checkEmail(email) && canSubmit1;     //检查email格式合法性
	var province = document.getElementById("province");
	canSubmit1 = checkProvince(province) && canSubmit1;
	var area = document.getElementById("area");
	canSubmit1 = checkArea(area) && canSubmit1;
	var file = document.getElementById("photo");
	if(file != null){
		canSubmit1 = !checkExt(file) && canSubmit1;
	}
	
	var province = document.getElementById("province");  //省
   	if( province.value != -1){
   		var proIndex = province.selectedIndex;   
   		var proText = province.options[proIndex].text; 
   		document.getElementById("provstr").value = proText;
   	}
   
   	var area = document.getElementById("area");          //市
   	if(area.value != -1){
   		var areaIndex = area.selectedIndex;   
   		var areaText = area.options[areaIndex].text; 
   		document.getElementById("areastr").value = areaText;
   	}
	
	if (canSubmit1) {
		document.getElementById("updateForm").submit();
	}
}

var cache = new Object();
var areas = [];
function getChildArea(id){
   	if(cache[id] != null){
		return cache[id];
	}
	var para = "parentId="+id;
	var myAjax=new Ajax.Request("member!findArea.action",{method:'post',parameters:para,onComplete:retArea,onError:error});
}

function retArea(response){
  	var ret = eval('(' + response.responseText + ')');
  	if (ret.message != null) {
    	alert(ret.message);
  	} else {
    	areas = ret.list;
	    if(ret.id == 0){
	      	fillSelect("province",areas);
	    }else{
	      	fillSelect("area",areas);
	    }
  	}
}	

function clearSelect(id){
	var dom = document.getElementById(id);
	for(var i=dom.options.length-1;i>0;i--){
		var option = dom.options[i];
		dom.removeChild(option);
	}
}
	
function fillSelect(id,areas){
	var dom = document.getElementById(id);
	var sb = [];
	for(var i=0;i<areas.length;i++){
		var area = areas[i];
		var option=new Option();
		option.value = areas[i].c_id;
		option.text = areas[i].c_name;
		dom.options[dom.options.length] = option; 
	}
}
	
function fillChild(select){
	var index = select.selectedIndex;
	var option = select.options[index];
	var id = option.value;
	var domId = select.id;
	var nextDomId = null;
	if(domId=="province"){
	  	nextDomId = "area";
	}
	if(nextDomId==null){
		return;	
	}
	var n = document.getElementById(nextDomId);
	if(id=="-1"){
		clearSelect(nextDomId);
		fillChild(n);
	}else{
		clearSelect(nextDomId);
		getChildArea(id);
		if(n.value=="-1"){
			n.selectedIndex = 1;
		}
	}
}





