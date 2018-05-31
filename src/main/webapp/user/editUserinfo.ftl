<#escape x as (x)!> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>用户基本信息</title>
<link type="text/css" href="css/reg.css" rel="stylesheet"/>
<script src="js/jquery-1.8.2.js"></script>
<script src="js/add_pic.js"></script>
<script type="text/javascript">

	//图片弹窗
   function addPict() {
		var message= $("#uploadDiv").html();		
		message = message.replace(/__PRE__/g, '');
		var title = "上传相关信息的图片";
		add_pic({title:title, text:message, height:125, width:430});
	}
	//上传
	function AfterOperate(param) {	
		if(param.op == "addpict") return setPict(param);
		if(param.op =="editinfo")  return afterSave(param);
	}
	//更换图片		
    function setPict(p) {
    	if(p.result=='1000'){
    		alert(p.message);
  			return ;
    	}
    	var s = p.data.split(",");
		var pict = s[3] + s[1] + s[2];
		if('${u.pict}'!=''){
			$('#detail_pic').attr('src',pict)   	
		}else{
			$('#beiyong_pic').attr('src',pict) 
		}
		$("#pict").val(s[1] + s[2]);
		
		closeMessage();    
		
    }
    //保存修改
   function afterSave(param) {
    		if(param.result = '0000') {
    			alert(param.message);
    			window.history.go(-1);
    			if(window.opener){
    				window.opener.location.reload();
    			}
    		} else {
    			alert("失败:" + param.message);
    		}
    	}
	//修改密码
	function editpass(){
		var message= $("#editpwd").html();		
		message = message.replace(/__PRE__/g, '');
		var title = "修改密码";
		add_pic({title:title, text:message, height:225, width:350});
	}
</script>

</head>
 
<body>
<div class="header">
		<div class="top">微袋<span>丨个人信息</span></div>
</div>
<div class="main">
<div class="title">个人信息</div>
<div class="userinfo_contain">
	<form name="updateFrm" action="user!EditUserInfo.jhtml?id=${u.id}" method="post" target="hidefrm">
	 	<input type="hidden" name="picts" value="${u.pict}" id="pict">
		<div class="userinfo_pic">
			<#if u.pict?exists&&u.pict?length gt 0>
				<img src="${imghost}${u.pict}" width="70" height="70"  id="detail_pic"/>
			<#else>
				<img src="images/head-pic.png" width="70" height="70" id="beiyong_pic"/>
			</#if>
			<div>
				<p>微袋帐号：${u.phone}</p>
				<p>
					<a href="javascript:;" onclick="addPict()">更换头像</a>
					<a href="javascript:;" onclick="editpass()">修改密码</a>
				</p>
			</div>
		</div>
		<div class="userinfo_txt">
			<p><label>昵称 ：</label><input autocomplete="off" maxlength="40" type="text" name="nick" id="nick" value="${u.nick}"/></p>
			<p><label>签名 ：</label><textarea type="text" name="sign" maxlength="100"  id="sign" value="${u.sign}">${u.sign}</textarea></p>
			<input type="submit" name="btn1" class="btn" value="确定修改" />
		</div>
		
	</form>
</div>
</div>
<div class="footer">
  Copyright © hilinli.com All Rights Reserved.  
</div>

<div style=" display:none;">
  	<div id="uploadDiv">
		<div class="pop_sub" style="margin:10px;">
    		<form name="mainfrm" action="upload.jhtml" method="post" enctype="multipart/form-data" target="hidefrm" onsubmit="return checkPic()">
				<input type="hidden" name="op" value="addpict"/>
				<input type="file" name="upload" value="选择文件"><br>
				<br>
				<center>
				<input type="submit" value=" 上传 ..." id="__PRE__Upload">
				</center>
			</form>
		</div>
 	</div>    
</div>
<div style="display:none">
	<div id="editpwd">
		<p class="editpwd"><label>原始密码：</label><input class="txt" placeholder="原始密码不能为空" type="password" name="passwd" id="__PRE__p1"/></p>
		<p class="editpwd"><label>新密码：</label><input class="txt" type="password" placeholder="格式为6~20位任意字母和数字" name="passwdNew" id="__PRE__p2" /></p>
		<p class="editpwd"><label>确认密码：</label> <input type="password" class="txt" placeholder="与新密码一致" name="passwdCopy" id="__PRE__p3"/></p>
		<p class="editpwd"><input type="button" name="btn1" value="确定修改" class="btn" onclick="editPwd()"/></p>
	</div>
</div>
<iframe name="hidefrm" style="display:none"></iframe>
</body>
<script>
	function editPwd(){
   		if(checkPwd()){
	   		$.ajax({
	   			type:"post",
	   			data:{passwd:$("#p1").val(),passwdNew:$("#p2").val()},
	   			url:"user!EditPwd.jhtml",
	   			dataType:"json",
	   			success:function(param){
	   				if('0000'==param.result){
	   					window.location="login.html";
	   				}
	   					alert(param.message);
	   			}
	   		})
   		}
   }
   //判断密码

   function checkPwd(){
		var p1=$("#p1").val(); 
		var p2=$("#p2").val();
		var p3=$("#p3").val();
		if(p1.length==0){
			alert('原始密码不能为空');
			return false;
		} 
		if(!(/^[A-Za-z0-9_]\w{5,19}$/.test(p2))){
			alert('新密码格式为6~20位任意字母和数字');
			return false;
		} 
		if(p2!=p3){
			alert('两次密码输入不一致');
			return false;	
		}
		return true;
	} 
</script>
</html>
</#escape>