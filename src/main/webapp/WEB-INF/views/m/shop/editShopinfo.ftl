<#escape x as (x)!> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>店铺信息</title>
		<link type="text/css" rel="stylesheet" href="css/order.css" />
		<script src="js/jquery-1.8.2.js" type="text/javascript" ></script>
		<script type="text/javascript" src="js/add_pic.js"></script>
		
		<script src="js/catalog.js" type="text/javascript" ></script>
		<script type="text/javascript" src="js/cityData.js"></script>
		<script type="text/javascript" src="js/city.js"></script>
		
		<script language="javascript">

			function AfterOperate(param) {	
				if(param.op == "addpict1") return setPict(param,1);
				if(param.op == "addpict2") return setPict(param,2);
				if(param.op == "editshop") return afterSave(param);
			}
			
   function addPict(num) {
		var message= $("#uploadDiv").html();		
		message = message.replace(/__PRE__/g, '');
		var title = "上传相关信息的图片";
		add_pic({title:title, text:message, height:125, width:430});
		$('#op').val('addpict'+num)				
    }
    function changePhone() {
		var message= $("#changePhone").html();		
		message = message.replace(/__PRE__/g, '');
		var title = "更换手机号";
		add_pic({title:title, text:message, height:200, width:300});
						
    }
    
    	function setPict(p,NUM) {
	    	if(p.result=='1000'){
	    		alert(p.message);
	  		return ;
	    	}
	        var s = p.data.split(",");		
    		var pict = s[3] + s[1] + s[2];
    		
    		if(NUM==1){
    			$('#detail_pic').attr('src',pict);
    			$("#pict").val(s[1] + s[2]);
    		}  	
			if(NUM==2){
				$('#shopBack_pic').attr('src',pict);
				$("#bpict").val(s[1] + s[2]);
			}
			closeMessage();    
		
    	}
    	function afterSave(param) {
    		if(param.result == '0000') {
    			alert(param.message);
    			window.location.reload();
    			return;
    		} else {
    			alert("失败:" + param.message);
    			return ;
    		}
    	}
    	//发送验证码
    function sendPhoneCode() {
		var phone = $.trim($("#newPhone").val());
		if(!(/^1[3|4|5|8][0-9]\d{8}$/.test(phone))) {
			alert("请输入有效的手机号");
			return;
		}
		var url = "mobilecode!reg.jhtml";
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
	
//提交解绑信息
function setChange(){
		var phone = $("#newPhone").val()
		var code = $('#shop_code').val()
		var url = "shop!setPhone.jhtml";
			$.ajax({
				type: "POST",data: {phone:phone,phonecode:code,id:${shop.id}},url: url,dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						alert(ret.message);
						closeMessage();
						window.location.reload()
					} else {
						alert(ret.message);
					}
				}
			});
}	
 //判断手机号是否变化
 function checkchange(){
 	var phone = $.trim($("#newPhone").val());
	if(phone!='${shop.phone}'&&(/^1[3|4|5|8][0-9]\d{8}$/.test(phone))){
 				$('#getcode').removeAttr('disabled');
				$('#getcode').css({'background':'#f0f0f0','border-color':'#ccc','color':'#666'})
				$('#getcode').mouseover(function(){$(this).css({'background':'#ddd'})});
				$('#getcode').mouseout(function(){$(this).css({'background':'#f0f0f0'})});
				
 	}else{
 			$('#getcode').attr('disabled','disabled');
			$('#getcode').css({'background':'#fff','border-color':'#ddd','color':'#ccc'})
		
 	}
 
 }
    	
	window.onload =  function() {
		$("#type option[value='${shop.type}']").attr("selected", "selected");//店铺认证
		
		
	}
	

$(function(){
	$('#set_info').mouseover(function(){
		$('.set_info').css('display','block')
	}).mouseout(function(){
		$('.set_info').css('display','none')
	})
	$('.set_info').mouseover(function(){
		$('.set_info').css('display','block')
	}).mouseout(function(){
		$('.set_info').css('display','none')
	})
	$('#contain').height($('#contain_right').outerHeight())
})

</script>
	</head>		
<body style="background:#e8e8e8">
	<div class="header">
		<div class="header_top">
			<div class="logo">微 袋</div>
			<div class="header_nav">
				<div class="nav_visited">店铺</div>
				<a href="goods!goodsList.jhtml?sid=${shop.id}"><div class="nav_shop">商品</div></a>
				<a href="order.jhtml?sid=${shop.id}"><div class="nav_shop">订单</div></a>
			</div>
			<div class="shop_info">
				<div class="shop_pic">
					<#if shop.pict?exists&&shop.pict?length gt 0>
						<img src="${imghost}${shop.pict}" width="50" height="50"/>
					<#else>
						<img src="images/shop-pic0.png" width="50" height="50"/>
					</#if>
				</div>
				<div class="shop_name" style="position:relative">
					<p>${shop.name}</p>
					<p>${shop.phone}<a id="set_info" href="javascript:;">设置 <img src="images/jiantou.png"/></a>
						<ul class="set_info">
							<li><a href="home.jhtml">切换店铺</a></li>
							<li><a href="user!getuserinfo.jhtml">个人设置</a></li>
							<li><a href="shop!getShopByid.jhtml?sid=${sid}">店铺设置</a></li>
							<li><a href="user!logOut.jhtml">退出</a></li>
						</ul>
					</p>
				</div>
			</div>
		</div>
	</div>
	<div class="contain" id="contain">
		<div class="contain_left">
			<h1 class="left_title">我的微店</h1>
			<p class="left_visited">店铺信息</p>
			<!--<p class="left_link">店铺首页</p>-->
			<!--<a href="javascript:;"><p class="left_link">支付方式管理</p></a>-->
			<!--<p class="left_link">公共帐号管理</p>-->
		</div>
		<div class="contain_right" id="contain_right">
			<div class="shop_title">店铺信息</div>
			<div class="shopinfo">
				<div class="shopinfo_title">
					<div class="shop-pic"></div>
					<div style="float:left">
						<h1>${shop.name}</h1>
						<p>
							<#if shop.authed!=1>
								<div class="norenz_pic">未认证店铺</div>
							<#else>
								<div class="renz_pic"></div>
							</#if>
							<!--<input type="button" class="go_shop" value="访问店铺"/>-->
						</p>
					</div>
				</div>
				<div class="shopinfo_con">
					<form name="mainfrm1" action="shop!editInfo.jhtml" method="post" onsubmit="return checkInput()" target="hidefrm">
						<input type="hidden" name="province" id="v_province" />
						<input type="hidden" name="city" id="v_city" />
						<input type="hidden" name="district" id="v_district" />
						<input type="hidden" name="picts" value="${shop.pict}" id="pict">
						<input type="hidden" name="bpict" value="${shop.bpict}" id="bpict">
						<input type="hidden" name="id" value="${shop.id}">
					<div>		  
						<div> 
							<table class="tab_edit"  border="0" cellpadding="0" cellspacing="0">
								<tr>
							    	<td class="tabss">店铺名称:</td>
							        <td>
							        	<input class="txt"  maxlength="100"  type="text" name="name" id="name"  <#if (shop.authed==1 || shop.authed==2)>readonly</#if> autocomplete="off" value="${shop.name}" onkeyup="checkName()"/>
							        	<span id="error" style=" font-size:12px; color:#fe2f24;margin:4px 0 0 5px;"></span>
							        </td>
								</tr>	
								<tr>
							    	<td class="tabss">认证类型:</td>
							        <td>
							        	<#if shop.authed==0||shop.authed==3>
							        		<select id="type" class="add_select"><option value="0">个人</option><option value="1">企业</option></select>
							        	<#else>
							        		${shop.typename}认证
							        	</#if>
							        </td>
								</tr>	
								<tr>
									<td class="tabss">主营类目:</td>
									<td>
										<select name="catalog" id="catalog" class="add_select" ></select>
									</td>
								</tr>
								<tr>
									<td class="tabss">联系人姓名:</td>
									<td>
										<input class="txt" maxlength="40" type="text" id="uname" autocomplete="off" name="uname" value="${shop.uname}"/>
									</td>
								</tr>
								<tr>
									<td class="tabss">联系人手机号:</td>
									<td>
										<input class="txt" style="cursor:no-drop;width:200px" maxlength="13" readonly type="text" id="phone" name="phone" autocomplete="off" value="${shop.phone}" onkeyup="checkchange()"/>
										<a href="javascript:;"  class="edit_pic" onclick="changePhone()">更改手机</a>
									</td>
								</tr>
								<tr>
									<td class="tabss">所属省区:</td>
									<td>
										<select id="province"  class="add_select" onchange="prov_change('province','city')">
											<option value="0">选择省份</option>
										</select>
										<select  class="add_select" id="city" onchange="city_change('city','district')">
											<option value="0">选择城市</option>
										</select>
										<select  class="add_select" id="district">
											<option value="0">选择地区</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="tabss">详细地址:</td>
									<td><input class="txt"  type="text" maxlength="100" name="address" value="${shop.address}" id="address" autocomplete="off"/></td>
								</tr>
							  	<tr>
									<td class="tabss">店铺Logo:</td>
									<td>
										<#if shop.pict?exists&&shop.pict?length gt 0>
											<img src="${imghost}${shop.pict}" width="50" height="50" id="detail_pic" style="vertical-align: middle;"/>
										<#else>
											<img src="images/shop-pic0.png" width="50" height="50" id="detail_pic"  style="vertical-align: middle;"/>
										</#if>
										<a class="edit_pic" type="button" onclick="addPict(1)" href="javascript:;">修改</a>
										<span class="tixing">(最佳图片长宽比例1：1)</span>
									</td>
								</tr>
								<tr>
									<td class="tabss">店铺首页背景:</td>
									<td>
										<#if shop.bpict?exists&&shop.bpict?length gt 0>
											<img src="${imghost}${shop.bpict}" width="90" height="40" id="shopBack_pic" style="vertical-align: middle;"/>
										<#else>
											<img src="images/no_pic.png" width="90" height="40" id="shopBack_pic"  style="vertical-align: middle;"/>
										</#if>
										<a class="edit_pic" type="button" onclick="addPict(2)" href="javascript:;">修改</a>
										<span class="tixing">(最佳图片长宽比例9：4)</span>
									</td>
								</tr>
								<tr>
									<td class="tabss">联系人QQ:</td>
									<td>
										<input class="txt"  maxlength="20"  type="text" id="qq" name="qq" autocomplete="off" value="${shop.qq}"/>
									</td>
								</tr>
								<!--<tr>
									<td class="tabss">拥有微信公众号:</td>
									<td style="font-size:13px;">
									  <#if shop.partner?exists&&shop.partner?length gt 0>
										<input type="radio" id="yes_weixin" name="weixin" value="1" onclick="checkWeixin(this.value)" checked/>是
										<input type="radio" id="no_weixin" name="weixin" value="0" onclick="checkWeixin(this.value)"/>否
										<span class="tixing">(选择‘是’说明拥有微信公众号并已开通微信支付，则填写支付信息，信息填写不正确会导致买家支付失败)</span>
								<tbody id="weixin_info">
								<tr>
									<td class="tabss">微信商户号:</td>
									<td>
										<input class="txt"  type="text" id="partner" name="partner" autocomplete="off" value="${shop.partner}"/>
									</td>
								</tr>
								<tr>
									<td class="tabss">应用id:</td>
									<td>
										<input class="txt"  type="text" id="appid" name="appid" autocomplete="off" value="${shop.appid}"/>
									</td>
								</tr>
								<tr>
									<td class="tabss">应用密钥:</td>
									<td>
										<input class="txt"  type="text" id="appsecret" name="appsecret" autocomplete="off" value="${shop.appsecret}"/>
									</td>
								</tr>
								</tbody>
									<#else>
										<input type="radio" id="yes_weixin" name="weixin" value="1" onclick="checkWeixin(this.value)"/>是
										<input type="radio" id="no_weixin" name="weixin" value="0" onclick="checkWeixin(this.value)" checked/>否
										<span class="tixing">(选择‘是’说明拥有微信公众号并已开通微信支付，则填写支付信息，信息填写不正确会导致买家支付失败)</span>
									</#if>
										
										
									</td>
								</tr>
								<tbody id="weixin_info" style="display:none">
								<tr>
									<td class="tabss">微信商户号:</td>
									<td>
										<input class="txt"  type="text" id="partner" name="partner" autocomplete="off" value="${shop.partner}"/>
									</td>
								</tr>
								<tr>
									<td class="tabss">应用id:</td>
									<td>
										<input class="txt"  type="text" id="appid" name="appid" autocomplete="off" value="${shop.appid}"/>
									</td>
								</tr>
								<tr>
									<td class="tabss">应用密钥:</td>
									<td>
										<input class="txt"  type="text" id="appsecret" name="appsecret" autocomplete="off" value="${shop.appsecret}"/>
									</td>
								</tr>
								</tbody>
								-->
								<tr>
									<td class="tabss">店铺介绍:</td>
									<td><textarea name="intro" maxlength="1000"  id="intro" class="area_txt" style="width:420px;height:90px;padding:10px;border:1px solid #ccc;color:#333">${shop.intro}</textarea></td>
								</tr>
							</table>
						  	<div>
							  	<input type="submit" class="btn" name="sub1" value="保 存" /> 
						  	</div>
						</div>	  	
					</div>		
					</form>	
				</div>
			</div>
		</div>		
	</div>
	<div class="footer" id="footer">
  		Copyright © hilinli.com All Rights Reserved.  
	</div>
	<div style=" display:none;">
	  	<!-- 上传图片  -->
	  	<div id="uploadDiv">
			<div class="pop_sub" style="margin:10px;">
	    		<form name="mainfrm" action="upload.jhtml" method="post" enctype="multipart/form-data" target="hidefrm" onsubmit="return checkPic()">
					<input type="hidden" name="op" id="__PRE__op" value="addpict"/>
					<input type="file" name="upload" value="选择文件"><br>
					<br>
					<center>
					<input type="submit" value=" 上传 ..." id="__PRE__Upload">
					</center>
				</form>
			</div>
     	</div>
     	<!-- 解绑手机  -->
     	<div id="changePhone">
     		<div class="changePhone">
     			<p><label>原手机号：</label><span id="__PRE__shopPhone">${shop.phone}</span></p>
     			<p style="height: 40px;"><label>新手机号：</label><input onkeyup="checkchange()" maxlength="11" class="txt" style="width:185px;height:30px;line-height:30px" id="__PRE__newPhone"  autocomplete="off" ></p>
	     		<p><label>验证码：</label><input type="text" class="code" id="__PRE__shop_code" maxlength="6" ><input style="background:#fff;border-color:#ddd;color:#ccc" disabled class="get_code" id="__PRE__getcode" type="button" value="获取验证码" onclick="sendPhoneCode()"></p>
	     		<p><input type="button" value="提交" class="tijiao" onclick="setChange()"></p>
     		</div>
     	</div>
	</div>
  <iframe name="hidefrm" style="display:none"></iframe>
  
</body>
</html>
<script>
catalogs.show("catalog", "${shop.catalog}")
city_init('province', 'city', 'district',"${shop.province}","${shop.city}","${shop.district}");
var oResult=false;
function checkName(){
	var name = $.trim($("#name").val());
	if(name!='${shop.name}'){
		var url = "shop!checkShop.jhtml";
		var par = "name=" + name;
			$.ajax({
				type: "POST",data: par,url: url,dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						oResult=true;
					} else {
						oResult=false;
					}
				}
			});
	}
}
function checkInput() {
	var name = $.trim($("#name").val());
	if(name.length == 0) {
		alert("店铺名称不能为空");
		return false;
	}
	if(name!='${shop.name}'){
		var url = "shop!checkShop.jhtml";
		var par = "name=" + name;
			$.ajax({
				type: "POST",data: par,url: url,dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						oResult=true;
					} else {
						alert(ret.message);
						oResult=false;
					}
				}
			});
	}else{
		oResult=true;
	}
	var uname = $.trim($("#uname").val());
	if(uname.length == 0){
		alert("联系人姓名不能为空");
		return false;
	}
	var phone = $.trim($("#phone").val());
	
	if(phone.length == 0){
		alert("联系人手机号不能为空");
		return false;
	}else if(!(/^1[3|4|5|8][0-9]\d{8}$/.test(phone))) {
		alert("请输入有效的手机号");
		return false;
	}

	var citys = city_getSelectValue("province", "city", "district");
	if(citys.district == "") {
		alert("请选择店铺所在的省市区");
		return false;
	}
	$("#v_province").val(citys.province);
	$("#v_city").val(citys.city);
	$("#v_district").val(citys.district);
	
	var address = $.trim($("#address").val());
	if(address.length == 0){
		alert("详细地址不能为空");
		return false;
	}
	var qq = $.trim($("#qq").val());
	if(isNaN(qq)||qq.length<5){
		alert("请输入正确的QQ号码");
		return false;
	}
	
	return oResult;
}

function checkWeixin(val){
	if(val==0){
		$('#weixin_info').hide();
		$('#contain').height($('#contain_right').outerHeight())
	}else{
		$('#weixin_info').show();
		$('#contain').height($('#contain_right').outerHeight())
	}
}
</script>
</#escape> 
