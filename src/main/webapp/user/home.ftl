<#escape x as (x)!> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>选择店铺</title>

<link type="text/css" href="css/shoplist.css" rel="stylesheet" />
<script src="js/jquery-1.8.2.js"></script>

<script src="js/add_pic.js"></script>
<script type="text/javascript">

	function checkPwd(){
		var p1=$("#p1").val(); 
		var p2=$("#p2").val();
		var p3=$("#p3").val();
		if(p1.length==0){
			alert('原始密码不能为空');
			return false;
		} 
		//showError(p1,'');
		if(p2.length==0){
			alert('新密码不能为空');
			return false;
		} 
		//showError(p2,'');
		if(p2!=p3){
			alert('两次密码输入不一致');
			return false;	
		}
		//showError(p3,'');
		return true;
	} 
	
   function editPwd(){
   		$.ajax({
   			type:"post",
   			data:{passwd:$("#p1").val()},
   			url:"user!EditPwd.jhtml",
   			dataType:"json",
   			success:function(param){
   				if('0000'==param.result){
   					alert(param.message);
   					return ;
   				}
   					alert(param.message);
   			}
   		})
   }
   
    function showCause(str) {
		var message= $('#showCause').html();		
		message = message.replace(/__PRE__/g, '');
		var title = "不通过审核的原因";
		add_pic({title:title, text:message, height:170, width:350});
		$('#cause').html(str)
	}
	function AfterOperate(param) {	
		if(param.op == "addpict") return endMainPict(param);
		if(param.op =="editinfo")  return afterSave(param);
	}
			
  	 var picts = new Array(); 
	var mainPictIdx = -1;
		
    	function endMainPict(p) {
        	var s = p.data.split(",");
    		var idx = picts.length;
    		picts[idx] = s[2];
    		var pict = s[1] + s[2];
    		$('#detail_pic').attr('src',pict)   	
			$("#pict").val(s[2]);
			closeMessage();    
			if(mainPictIdx == -1) {
				mainPictIdx = idx;		
				$("#PIC_IMG_" + mainPictIdx).addClass("main_goods_pict");		
			}
    	}
   function afterSave(param) {
    		if(param.result = '0000') {
    			alert(param.message);
    			window.location.reload();
    		} else {
    			alert("失败:" + param.message);
    		}
    	}
   
   //删除未认证店铺
 	function deleteShop(sid) {
		if(!confirm("确认删除该店铺吗？")) return;
		var url = "shop!deleteShop.jhtml";
		var param = "id=" + sid;
		$.ajax({
		type: "POST",data: param,url: url,dataType: "json",
		success: function(ret){
			if('0000' == ret.result) {
				alert("删除成功");
				window.location.reload();				
			} else {
				alert(ret.message);
			}
		}
		});
			
	}
  //取消认证
  function cancelCer(sid){
  if(!confirm("取消该店铺认证资料？")) return;
  	$.ajax({
		type: "POST",
		data: {sid:sid},
		url: "shop!cancel.jhtml",
		dataType: "json",
		success: function(ret){
			if('0000' == ret.result) {
				alert(ret.message);
				window.location.reload();
				return ;				
			} else if('2001'==ret.result){
				window.location="login.html";
			}else{
				alert(ret.message);
			}
		}
	});
  }
   	$(function(){
   		$('.shop_list').height(function(){
   			var len=$('.mer_shop').size()+1;
   				
   				if(len%3!=0){
   					height=(Math.floor(len/3)+1)*220;
   				}else{
   					height=Math.floor(len/3)*220;
   				}
   				return height;
   		})
   	})
   	//审核原因
   	function getR(sid){
   		$.ajax({
		type: "POST",
		data: {sid:sid},
		url: "shop!auditResult.jhtml",
		dataType: "json",
		success: function(ret){
			if('0000' == ret.result) {
				showCause(ret.data.auditresult);
			}else{
				alert(ret.message);
			}
		}
	});
   }
   
   //更改登录帐号
   function editPhone() {
		var message= $("#editPhone").html();		
		message = message.replace(/__PRE__/g, '');
		var title = "更改登录帐号";
		add_pic({title:title, text:message, height:200, width:300});
						
    }
</script>
</head>
 
<body>
<div class="header">
	<div class="header_top">
		微 袋
	</div>
</div>
<div class="main">
	<div class="main_title">
		<h1>选择公司/店铺</h1><h2>${user.phone} - <a href="user!logOut.jhtml">退出</a></h2>
	</div>
	<div class="main_contain">
		<div class="mer_pic">
			<#if user.pict?exists&&user.pict?length gt 0>
				<img src="${imghost}${user.pict}" width="80" height="80"/>
			<#else>
				<img src="images/head-pic.png" width="80" height="80"/>
			</#if>
		</div>
		
		<div class="mer_info">
			<h1>${user.nick}</h1>
			<h2>帐号:${user.phone} <a style="border:none;background:none;display:inline;float:none;color:#07d" href="javascript:;" onclick="editPhone()">更改帐号</a></h2>
			<a href="user!getuserinfo.jhtml" target="workwindow">设置</a><a href="shop!shopPage.jhtml" target="workwindow">创建新的店铺</a>
		</div>
	</div>
	<div class="shop_list">
			<#list slist as s>
				
					<div class="mer_shop">
						<div class="shop_name">
							<#if (s.authed == 0 || s.authed == 3)>
								<a href="javascript:deleteShop(${s.id})">删除</a>
							</#if>
							<a href="shop!getShopByid.jhtml?sid=${s.id}" target="workwindow">修改</a>
							<h1>${s.name}(${s.typename})</h1>
							
							
						</div>
						<div class="shop_pic">
							<#if s.pict?exists&&s.pict?length gt 0>
								<img src="${imghost}${s.pict}" width="80" height="80"/>
							<#else>
								<img src="images/shop-pic0.png" width="80" height="80"/>
							</#if>
						</div>
						<div class="shop_info">
							<p>
								<#if (s.authed==1)>
									
									<a  target="workwindow" href="goods!goodsList.jhtml?sid=${s.id}">商品管理</a>
								<#else>												
									<#if (s.authed == 0 || s.authed == 3)>
									<a  target="workwindow" href="shop!get.jhtml?op=toauth&sid=${s.id}">提交认证资料</a>
									<#else>
									<a href="javascript:;" onclick="cancelCer('${s.id}')">取消认证资料</a>			 
									</#if>
								</#if>
							</p>
							
							<p>认证状态：${s.authname}<#if s.authname=="审核不通过"><a href="javascript:;" onclick="getR('${s.id}')">原因</a></#if></p>
						</div>
						
					</div>
				
			</#list>
			<a  target="workwindow" href="shop!shopPage.jhtml">
				<div class="add_shop">
					添加店铺
				</div>
			</a>
	</div>
</div>
<div class="footer">
  Copyright © hilinli.com All Rights Reserved.  
  </div>
  
  <div id="showCause" style="display:none">
  	<textarea id="__PRE__cause" readonly style="resize:none;width:300px;height:90px;border:1px solid #bbb;border-radius:2px;padding:5px;margin:10px 20px;">
  	
  	</textarea>
  </div>
  <div style="display:none">
  <div id="editPhone">
     		<div class="changePhone">
     			<p><label>原手机号：</label><span id="__PRE__userPhone">${user.phone}</span></p>
     			<p style="height: 40px;"><label>新手机号：</label><input onkeyup="checkchange()" maxlength="11" class="txt" style="width:185px;height:30px;line-height:30px" id="__PRE__newPhone"  autocomplete="off" ></p>
	     		<p><label>验证码：</label><input type="text" class="code" id="__PRE__phone_code" maxlength="6" ><input style="background:#fff;border-color:#ddd;color:#ccc" disabled class="get_code" id="__PRE__getcode" type="button" value="获取验证码" onclick="sendPhoneCode()"></p>
	     		<p><input type="button" value="提交" class="tijiao" onclick="setChange()"></p>
     		</div>
     	</div>
  </div>
</body>
<script>
 //判断手机号是否变化
function checkchange(){
 	var phone = $.trim($("#newPhone").val());
	if(phone!='${user.phone}'&&(/^1[3|4|5|8][0-9]\d{8}$/.test(phone))){
 				$('#getcode').removeAttr('disabled');
				$('#getcode').css({'background':'#f0f0f0','border-color':'#ccc','color':'#666'})
				$('#getcode').mouseover(function(){$(this).css({'background':'#ddd'})});
				$('#getcode').mouseout(function(){$(this).css({'background':'#f0f0f0'})});
				
 	}else{
 			$('#getcode').attr('disabled','disabled');
			$('#getcode').css({'background':'#fff','border-color':'#ddd','color':'#ccc'})
		
 	}
 
 }
//提交解绑信息
function setChange(){
		var phone = $("#newPhone").val()
		var code = $('#phone_code').val()
		var url = "user!setLoginPhone.jhtml";
			$.ajax({
				type: "POST",data: {phone:phone,phonecode:code},url: url,dataType: "json",
				success: function(ret){
					if('0000'==ret.result){
	   					window.location="login.html";
	   				}else if('2001'==ret.result){
						window.location="login.html";
					}
	   					alert(ret.message);
				}
			});
}
    	//发送验证码
    function sendPhoneCode() {
		var phone = $.trim($("#newPhone").val());
		if(!(/^1[3|4|5|8][0-9]\d{8}$/.test(phone))) {
			alert("请输入有效的手机号");
			return;
		}
		var url = "mobilecode!regCheckPhone.jhtml";
		var par = "phone=" + phone;
			$.ajax({
				type: "POST",data: par,url: url,dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						resetCode();
						alert(ret.message);
					}else{
						alert(ret.message);
					}
				}
			});
	}	
//60秒重发验证码
	function resetCode(){
		var time=60;
		var timer=null;
		clearInterval(timer)
			$('#getcode').attr('disabled','disabled');
			$('#getcode').css({'background':'#fff','border-color':'#ddd','color':'#ccc'})
			$('#getcode').val(time+'秒后重发');	
		timer=setInterval(function(){
			
			time-=1;
			$('#getcode').attr('disabled','disabled');
			$('#getcode').css({'background':'#fff','border-color':'#ddd','color':'#ccc'})
			$('#getcode').val(time+'秒后重发');	
			if(time==0){
				clearInterval(timer);
				$('#getcode').removeAttr('disabled');
				$('#getcode').css({'background':'#f0f0f0','border-color':'#ccc','color':'#666'})
				$('#getcode').mouseover(function(){$(this).css({'background':'#ddd'})});
				$('#getcode').mouseout(function(){$(this).css({'background':'#f0f0f0'})});
				$('#getcode').val('获取验证码');	
			}
			
			
		},1000)
	}
</script>

</html>
</#escape>