<#escape x as (x)!> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>添加店铺</title>
		<link href="css/login.css" type="text/css" rel="stylesheet" />
		<script src="js/jquery-1.8.2.js" type="text/javascript" ></script>
		<script src="js/catalogC.js" type="text/javascript" ></script>
		<script type="text/javascript" src="js/cityData.js"></script>
		<script type="text/javascript" src="js/city.js"></script>
		
		<script language="javascript">
			function showError(obj,msg){
				$(obj).nextAll('.error').html(msg);
				obj.css({'border-color':'#e94946','color':'#e94946'})
			}
			
			function showSuccess(obj){
				$(obj).nextAll('.error').html('');
				obj.css({'border-color':'#cccccc','color':'#333'})
			}
			function AfterOperate(param) {	
				if(param.op == "addshop") return afterSave(param);
			}
			
    	function afterSave(param) {
    		if(param.result == '0000') {
    			window.location="shop!toCerPage.jhtml";
    			return;
    		} else {
    			alert("失败:" + param.message);
    			return ;
    		}
    	}
	function sendPhoneCode() {
		var phone = $.trim($("#phone").val());
		if(!(/^1[3|4|5|8][0-9]\d{8}$/.test(phone))) {
			showError($("#phone"),"请输入有效的手机号");
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
						$("#phonecode").val("");
					} else {
						showError($("#phonecode"),ret.message);
					}
				}
			});
}

	function getBox(){
		$(".href_msg").show();
	}


	$(function(){
		$('.href_a').click(function(e){
			e.stopPropagation();
		
		})
		$(document).not('a.href_a').click(function(){
	      	for(var i=0;i<$('.href_msg').length;i++){
				$('.href_msg').eq(i).hide()
			}
	    });
	   
	})
	function getValue(id){
		var se=document.getElementById("sel"+id);
		if(se.checked==true){
			//alert("被选中");
			//alert($("input[name='catalog']:checked").next("label").text());
			$("#select_radio").html($("input[name='catalog']:checked").parent("label").text());
			$("#type_href").hide();
			$(".href_a").children('span').eq(0).html("修改");
		}
		else{
			alert("未选中");
		}
		
	}
	//60秒重发验证码
	function resetCode(){
		var time=60;
		var timer=null;
		clearInterval(timer)
			$('#getcode').attr('disabled','disabled');
			$('#getcode').css({'background':'#fff','border-color':'#ddd','color':'#ccc'})
			$('#getcode').val(time+'秒后可以重发');	
		timer=setInterval(function(){
			
			time-=1;
			$('#getcode').attr('disabled','disabled');
			$('#getcode').css({'background':'#fff','border-color':'#ddd','color':'#ccc'})
			$('#getcode').val(time+'秒后可以重发');	
			if(time==0){
				clearInterval(timer);
				$('#getcode').removeAttr('disabled');
				$('#getcode').css({'background':'#f0f0f0','border-color':'#ccc','color':'#666'})
				$('#getcode').mouseover(function(){$(this).css({'background':'#ddd'})});
				$('#getcode').mouseout(function(){$(this).css({'background':'#f0f0f0'})});
				$('#getcode').val('获取短信验证码');	
			}
			
			
		},1000)
	}
	
</script>
</head>		
<body style="background:#fff">
<div class="header">
	<div class="top"> 
    	微袋<span>丨 创建店铺</span>
    </div>
</div>
<div class="main" style="border:none">
	<div class="schedule"><img src="images/schedule1.png" /></div>
    <div class="main_addstore">
    <form name="mainfrm1" action="shop!addShop.jhtml" method="post" target="hidefrm" onsubmit="return checkInput()">

			<input type="hidden" name="province" id="v_province" />
			<input type="hidden" name="city" id="v_city" />
			<input type="hidden" name="district" id="v_district" />
	<table style="margin-left:200px" border="0" cellspacing="0">
	  <tr>
	    <td><label><em>*</em>店铺名称：</label><input maxlength="100" placeholder="认证后不可修改" class="tet2"  type="text" name="name" id="name"  autocomplete="off" onblur="checkShopName()"/><span class="error"></span></td>
	  </tr>
	  <tr>
	     <td>
	     	<label><em>*</em>联系地址：</label>
	     	<select id="province" onchange="prov_change('province','city')"  class="add_select" onfocus="checkShopName()" >
	     		<option value="0">选择省份</option>
	     	</select>
	     	<select id="city" onchange="city_change('city','district')" class="add_select">
	     		<option value="0">选择城市</option>
	     	</select>
	     	<select id="district" class="add_select">
	     		<option value="0">选择地区</option>
	     	</select>
	     	<span class="error"></span>
	     </td>
	  </tr>
	  <tr>
	     <td><label></label><input class="tet2"  maxlength="100" placeholder="请填写详细地址" type="text" name="address" id="address" autocomplete="off" onfocus="checkCitys()" onblur="checkAddress()"/><span class="error"></span></td>
	  </tr>
	  
	  
	  <tr>


	    <td>
	    	<label><em>*</em>主营类目：</label>
	    	<span id="select_radio" style="line-height:35px;"></span>
			<a href="javascript:;" style="position:relative" id="aa" class="href_a" >
				<span style="line-height:35px;" onclick="getBox();" >选择经营的类目</span>
				<div id="type_href" class="href_msg" style="display:none;">
							<span class="right_3"></span>
							<span class="href_radio">
								<div name="catalog" id="catalog" class="redio_s"></div>
							</span>
				</div>
			</a>
			<span class="error"></span></td>

	  </tr>
	  <tr>
	     <td><label><em>*</em>联系人姓名：</label><input maxlength="40"  class="tet2" placeholder="请填写真实姓名"  type="text" name="uname" id="uname" value="${u.name}" autocomplete="off" onfocus="checkCatalog()" onblur="checkUsername()"/><span class="error"></span></td>
	  </tr>
	  <tr>
	    <td><label><em>*</em>联系人手机号：</label><input class="tet2"  placeholder="请填写真实手机号,方便联系"  type="text" name="phone" id="phone" value="${u.phone}" autocomplete="off" onfocus="checkUsername()" onblur="checkPhone()"/><span class="error"></span></td>
	  </tr>
	  <tr>
	     <td><label><em>*</em>短信校验码：</label><input class="code" maxlength="6"  type="text" name="phonecode" id="phonecode"  autocomplete="off" onfocus="checkPhone()" onblur="checkPhonecode()"/><input class="get_code" id="getcode" type="button" value="获取短信验证码" onclick="sendPhoneCode()"><span class="error"></span></td>
	  </tr>
	  <tr>
	     <td><label><em>*</em>常用QQ：</label><input  maxlength="20"  class="tet2" placeholder="请填写您常用的QQ号码"  type="text" name="qq" id="qq"  autocomplete="off" onfocus="checkPhonecode()" onblur="checkQQ()"/><span class="error"></span></td>
	  </tr>
	  <tr>

		<td><label>店铺介绍：</label><textarea maxlength="1000" name="intro" id="intro" style="width:280px;height:70px;padding:10px;border:1px solid #ccc;color:#333"></textarea><span class="error"></span></td>
	  </tr>
	  <tr>
	   <td><label></label><input type="checkbox"  id="check" checked=""/> 我已阅读并同意<a href="javascript:showPrivacy()" onclick="" style="color:#07d">微袋服务协议</a><span class="error"></span></td>
	  </tr>
	  <tr>
	    <td style="padding-left:170px"><input class="btn"  type="submit" name="btn1" id="btn1" value="创建店铺" /></td>
	  </tr>
	  
	</table>
	</form>
	<div style="display:none">
	<iframe name="hidefrm"></iframe>
	</div>
  </div>
 
</div>
 <div class="footer">
  Copyright © hilinli.com All Rights Reserved.  
  </div>

<div id="privacy" style="display:none;">
				
			<div style="top:-20px;left:0;opacity:0.15;background:#000;position:fixed;width:100%;height:100%;min-height:726px;min-width:1366px;z-index:100001"></div>
			
			
			<div id="privacy_one" style="font-size:12px;width:740px;height:516px;border:5px solid rgba(0,0,0,0.1);border-radius: 5px;z-index:100002;position:fixed">
			<div style="border-bottom:#ccc 1px solid;background:rgb(243,243,243);height:35px;line-height:35px;font-size:14px;font-weight:bold;text-align:center">微袋服务协议<span style="display:block;float:right;margin-right:10px;line-height:35px;font-weight:normal;cursor:pointer" onclick="closePrivacy()">关闭[×]</span></div>	
			<div style="text-align:left;padding:20px;background:#fff;overflow:auto;width:700px;height:360px;border:none">
				<pre style="line-height:20px">
服务使用方（甲方）：微袋商户

服务提供方（乙方）：杭州邻方科技有限公司

“微袋代理销售和结算服务”（以下简称“本服务”）是由杭州邻方科技有限公司（以下简称“乙方”）向微袋商户（以下简称“甲
方”）提供的“微袋”软件系统（以下简称“本系统”）及（或）附随的商品代理销售和货款结算服务，方便微袋商户的买家通过微
袋软件系统，并提供通过本系统集成的第三方支付网关完成付款。本协议由甲方和乙方签订。


声明与承诺

一、甲方确认，在甲方申请开通微袋店铺的代理销售和结算服务之前，甲方已充分阅读、理解并接受本协议的全部内容，一旦甲方
使用本服务，即表示甲方同意遵循本协议的所有约定。

二、甲方同意，乙方有权随时对本协议内容进行单方面的变更，并以在乙方网站公告的方式予以公布，无需另行单独通知甲方；若
甲方在本协议内容公告变更后继续使用本服务的，表示甲方已充分阅读、理解并接受修改后的协议内容，也将遵循修改后的协议内
容使用本服务；若甲方不同意修改后的协议内容，甲方应停止使用本服务。

三、甲方声明，在甲方同意接受本协议并注册开通微袋店铺时，甲方是具有法律规定的完全民事权利能力和民事行为能力，能够独
立承担民事责任的自然人、法人或其他组织；本协议内容不受甲方所属国家或地区的排斥。不具备前述条件的，甲方应立即终止注
册或停止使用本服务。


微袋代理销售和结算服务概要

一、微袋店铺账户：指在甲方使用本服务时，乙方向甲方提供的店铺唯一编号。甲方可自行设置密码，提交商品信息，并用以查询
或计算甲方的货款。

二、微袋代理销售结算服务：即乙方向甲方提供的商品代理销售和货款结算的中介服务，其中包含

1、 代理销售：如果甲方未在店铺中填写微信支付信息，乙方代为收取交易对方通过第三方支付网关向甲方支付的货款。

2、 货款支付：甲方可以要求乙方向甲方支付甲方的货款。当甲方向乙方做出结算指示时，必须提供一个与甲方或甲方的公司名称
相符的有效的中华人民共和国境内（不含港澳台）银行账户，乙方将于收到指示后的一至五个工作日内，将相应的款项汇入甲方提
供的有效银行账户（根据甲方提供的银行不同，会产生汇入时间上的差异）。除本条约定外，乙方不提供其他受领方式。

3、 系统查询：乙方将对甲方在本系统中的所有操作进行记录，不论该操作之目的最终是否实现。甲方可以在本系统中实时查询甲
方的微袋店铺账户名下的交易记录，甲方认为记录有误的，乙方将向甲方提供乙方已按照甲方的指示收付款的记录。甲方理解并同
意甲方最终收到款项的服务是由甲方提供的银行账户对应的银行提供的，甲方需向该银行请求查证。

4、 款项专属：对甲方通过微袋店铺账户收到的货款，乙方将予以妥善保管，除本协议另行规定外，不作任何其他非甲方指示的用
途。乙方通过甲方的用户名和密码识别甲方的指示，请甲方妥善保管甲方的用户名和密码，对于因密码泄露所致的损失，由甲方自
行承担。 本服务所涉及到的任何款项只以人民币计结，不提供任何形式的外币兑换业务。

5、 异常交易处理：甲方使用本服务时，可能由于银行本身系统问题、银行相关作业网络连线问题或其他不可抗拒因素，造成本服
务无法提供。甲方确保甲方所输入的甲方的资料无误，如果因资料错误造成乙方于上述异常状况发生时，无法及时通知甲方相关交
易后续处理方式的，乙方不承担任何损害赔偿责任。

6、 甲方同意，基于运行和交易安全的需要，乙方可以暂时停止提供或者限制本服务部分功能，或提供新的功能，在任何功能减少
、增加或者变化时，只要甲方仍然使用本服务，表示甲方仍然同意本协议或者变更后的协议。



微袋店铺账户

一、注册相关

在使用本服务前，甲方必须先行注册，取得乙方提供给甲方的“微袋店铺账户”（以下简称该账户），甲方同意：

1、 依本服务注册表之提示准确提供并在取得该账户后及时更新甲方的正确、最新及完整的资料。若有合理理由怀疑甲方提供的资
料错误、不实、过时或不完整的，乙方有权暂停或终止向甲方提供部分或全部“微袋服务”。乙方对此不承担任何责任，甲方将承担
因此产生的任何直接或间接支出。

2、 因甲方未及时更新资料，导致本服务无法提供或提供时发生任何错误，甲方不得将此作为取消交易、拒绝付款的理由，甲方将
承担因此产生的一切后果，乙方不承担任何责任。

3、 甲方应对甲方的微袋店铺账户负责，只有甲方本人可以使用甲方的微袋店铺账号。在甲方决定不再使用该账户时，甲方应将该
账户下所对应的可用款项全部结算，并向乙方申请注销该账户。甲方同意，若甲方丧失全部或部分民事权利能力或民事行为能力，
乙方有权根据有效法律文书（包括但不限于生效的法院判决、生效的遗嘱等）处置与甲方的微袋店铺账户相关的款项。

二、账户安全

甲方将对使用该账户及密码进行的一切操作及言论负完全的责任，甲方同意：

1、 不向其他任何人泄露该账户及密码，亦不使用其他任何人的“微袋店铺账户”及密码。

2、 你应该及时更新“微袋店铺账户”的管理员权限，店铺下其他管理员的操作，视为甲方授权其进行管理。

3、 如甲方发现有他人冒用或盗用甲方的账户及密码或任何其他未经合法授权之情形时，应立即以有效方式通知乙方，要求乙方暂
停相关服务。同时，甲方理解乙方对甲方的请求采取行动需要合理期限，在此之前，乙方对已执行的指令及(或)所导致的甲方的损
失不承担任何责任。


微袋代理销售和结算使用规则

为有效保障甲方使用本服务的合法权益，甲方理解并同意接受以下规则：

一、一旦甲方使用本服务，甲方即授权乙方代理甲方及（或）甲方的公司在甲方及（或）甲方指定人符合指定条件或状态时，结算
款项给甲方指定人。

二、乙方通过以下两种方式中接受来自甲方的指令：其一，甲方在微袋网站依照本服务预设流程申请结算；其二甲方通过甲方注册
时作为该账户名称或者与该账户绑定的手机或其他专属于甲方的通讯工具（以下合称该手机）号码向本系统发送的信息（短信或电
话等）回复。无论甲方通过以上两种方式中的任一种向乙方发出指令，都不可撤回或撤销，且成为乙方代理甲方结算款项的唯一指
令。在甲方与第三方发生交易纠纷时，甲方授权乙方自行判断并决定将争议货款的全部或部分结算给交易一方或双方。

三、 甲方在使用本服务过程中，本协议内容、网页上出现的关于结算操作的提示或乙方发送到该手机的信息（短信或电话等）内容
是甲方使用本服务的相关规则，甲方使用本服务即表示甲方同意接受本服务的相关规则。甲方了解并同意乙方单方修改服务的相关
规则，而无须征得甲方的同意，服务规则应以甲方使用服务时的页面提示（或发送到该手机的短信或电话等）为准，甲方同意并遵
照服务规则是甲方使用本服务的前提。

四、乙方会以电子邮件（或发送到该手机的短信或电话等）方式通知甲方交易进展情况以及提示甲方进行下一步的操作，但乙方不
保证甲方能够收到或者及时收到该邮件（或发送到该手机的短信或电话等），且不对此承担任何后果，因此，在交易过程中甲方应
当及时登录到乙方网站查看和进行交易操作。因甲方没有及时查看和对交易状态进行修改或确认或未能提交相关申请而导致的任何
纠纷或损失，乙方不负任何责任。

五、甲方如果需要向交易对方交付货物，应根据交易状态页面（该手机接收到的信息）显示的买方地址，委托有合法经营资格的承
运人将货物直接运送至对方或其指定收货人，并要求对方或其委托的第三方（该第三方应当提供对方的授权文件并出示对方及第三
方的身份证原件）在收货凭证上签字确认，因货物延迟送达或在送达过程中的丢失、损坏，乙方不承担任何责任，应由甲方与交易
对方自行处理。

六、 乙方对甲方所交易的标的物不提供任何形式的鉴定、证明的服务。如果甲方与交易对方发生交易纠纷，甲方授权由乙方根据本
协议及微袋网站上载明的各项规则进行处理。甲方为解决纠纷而支出的通讯费、文件复印费、鉴定费等均由甲方自行承担。因市场
因素致使商品涨价跌价而使任何一方得益或者受到损失而产生的纠纷，乙方不予处理。

七、若甲方未完成注册过程成为微袋店铺，他人无通过本服务购买甲方的商品，直到甲方完成该账户的注册和店铺认证。

八、乙方会将与甲方微袋店铺账户相关的资金，独立于乙方营运资金之外，且不会将该资金用于非甲方指示的用途，但本条第（十
三）项约定的除外。

九、乙方并非银行或其它金融机构，本服务也非金融业务，本协议项下的资金移转均通过银行来实现，你理解并同意甲方的资金于
流转途中的合理时间。

十、甲方完全承担甲方使用本服务期间由乙方保管或代理销售或结算的款项的货币贬值风险及可能的孳息损失。

十一、在甲方注册成为微袋店铺账户时，甲方授权乙方审核甲方的身份和资格.

十二、甲方不得将本服务用于非乙方许可的其他用途。

十三、交易风险

1、当甲方通过本服务进行各项交易或接受交易款项时，若甲方或对方未遵从本服务条款或网站说明、交易页面中之操作步骤，则
乙方有权拒绝为甲方与交易对方提供相关服务，且乙方不承担损害赔偿责任。若发生上述状况，而款项已先行划付至甲方的微袋商
户账户名下，甲方同意乙方有权直接自相关账户余额中扣回款项及禁止甲方要求支付此笔款项之权利。此款项若已汇入甲方的银行
账户，甲方同意乙方有向甲方事后索回之权利，因甲方的原因导致乙方事后追索的，甲方应当承担乙方合理的追索费用。

2、因甲方的过错导致的任何损失由甲方自行承担，该过错包括但不限于：不按照交易提示操作，未及时进行交易操作，遗忘或泄
漏密码，密码被他人破解，甲方使用的计算机被他人侵入。

十四、服务费用

1、在甲方使用本服务时，微袋暂不收取任何费用。但交易对方使用第三方支付网关支付货款时，以及甲方提出结算时，乙方有权
按照第三方支付网关或银行转账规定，扣除相关手续费用。乙方拥有制订及调整手续费之权利，具体手续费用以甲方使用本服务时
微袋网站上所列之收费方式公告（在没有公告之前本服务免费）或甲方与乙方达成的其他书面协议为准。

2、除非另有说明或约定，甲方同意乙方有权自甲方委托乙方代理销售、结算的款项中直接扣除上述手续费用。

十五、品牌规范

1、乙方有权依据（包括但不限于）品牌需求、营业执照、法人身份证、甲方经营状况及服务水平等因素退回甲方的入驻申请；同
时乙方有权要求甲方在申请入驻及后续经营阶段提供其他必要的资质证明文件；

2、甲方需确保在申请入驻及后续经营阶段提供的所有资质证明文件真实有效（若甲方提供的相关资质文件为其他第三方所有，如
商标所有权证书、授权书等，甲方有义务先行核实文件真实性和有效性）。若乙方发现甲方提供的资质文件虚假，甲方相关信息将
被列入乙方的黑名单，乙方将不再与甲方进行任何形式的合作，由此产生的任何损失（包括但不限于消费者资损、乙方资损）均由
甲方承担；

3、乙方不接受拟销售商品为违禁品（包括但不限于烟草、医疗器械、管制刀具等）的店铺的入驻申请；

4、乙方不接受以甲方个人认证方式注册企业资质店铺；

5、乙方保留对甲方店铺进行强制更名或技术锁定的权利。

微袋代理销售和结算服务使用限制

一、甲方在使用本服务时应遵守中华人民共和国相关法律法规、甲方所在国家或地区之法令及相关国际惯例，不将本服务用于任何
非法目的（包括用于禁止或限制交易物品的交易），也不以任何非法方式使用本服务。

二、甲方不得利用本服务从事侵害他人合法权益之行为，否则应承担所有相关法律责任，因此导致乙方或乙方雇员受损的，甲方应
承担赔偿责任。上述行为包括但不限于：

1、侵害他人名誉权、隐私权、商业秘密、商标权、著作权、专利权等合法权益。

2、违反依法定或约定之保密义务。

3、冒用他人名义使用本服务。

4、从事不法交易行为，如洗钱、贩卖枪支、毒品、禁药、盗版软件、黄色淫秽物品、其他乙方认为不得使用本服务进行交易的物
品等。

5、提供赌博资讯或以任何方式引诱他人参与赌博。

6、非法使用他人银行账户（包括信用卡账户）或无效银行账号（包括信用卡账户）交易。

7、违反《银行卡业务管理办法》使用银行卡，或利用信用卡套取现金（以下简称套现）。

8、进行与甲方或交易对方宣称的交易内容不符的交易，或不真实的交易。

9、从事任何可能含有电脑病毒或是可能侵害本服务系统、资料之行为。

10、其他乙方有正当理由认为不适当之行为。

三、甲方理解并同意，乙方不对因下述任一情况导致的任何损害赔偿承担责任，包括但不限于利润、商誉、使用、数据等方面的损
失或其他无形损失的损害赔偿

1、 乙方有权基于单方判断，包含但不限于乙方认为甲方已经违反本协议的明文规定及精神，暂停、中断或终止向甲方提供本服务
或其任何部分，并移除甲方的资料。

2、 乙方在发现异常交易或有疑义或有违反法律规定或本协议约定之虞时，有权不经通知先行暂停或终止该账户的使用（包括但不
限于对该账户名下的款项和在途交易采取取消交易、调账等限制措施），并拒绝甲方使用本服务之部分或全部功能。

3、 在必要时，乙方无需事先通知即可终止提供本服务，并暂停、关闭或删除该账户及甲方账号中所有相关资料及档案，并将甲方
滞留在该账户的全部合法资金退回到甲方的银行账户。

四、如甲方需要注销甲方的微袋账户，应先经乙方审核同意。乙方注销该账户，即表明乙方与甲方之间的协议已解除，但甲方仍应
对甲方使用本服务期间的行为承担可能的违约或损害赔偿责任。


系统中断或故障

系统因下列状况无法正常运作，使甲方无法使用各项服务时，乙方不承担损害赔偿责任，该状况包括但不限于：

一、乙方在微袋网站公告之系统停机维护期间。

二、 电信设备出现故障不能进行数据传输的。

三、因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力之因素，造成乙方系统障碍不能执行业务的。

四、由于黑客攻击、电信部门技术调整或故障、网站升级、银行方面的问题等原因而造成的服务中断或者延迟。


法律适用管辖

本协议之效力、解释、变更、执行与争议解决均适用中华人民共和国法律，没有相关法律规定的，参照通用国际商业惯例和（或）
行业惯例。

你对本协议理解和认同，甲方即对本协议所有组成部分的内容理解并认同，一旦甲方使用本服务，你和乙方即受本协议所有组成部
分的约束。

本协议部分内容被有管辖权的法院认定为违法的，不因此影响其他内容的效力。

因本协议产生之争议，均应依照中华人民共和国法律予以处理，并以浙江省杭州市西湖区人民法院为第一审管辖法院。
				</pre>
			</div>
			<div style="background:#fff;width:740px;height:80px;text-align:center;"> <input type="button" value="阅读并同意协议" style="cursor:pointer;border:none;margin:20px 0;background: none repeat scroll 0 0 #e4393c;border-radius:3px; color: #fff;font-size: 16px;font-weight: 800;height: 34px;width: 250px;text-align:center" onclick="closePrivacy()"></div>
				</div>
			</div>
</body>
</html>
<script>

catalogs.show("catalog", "")
city_init('province', 'city', 'district');
//判断店铺名
var oResult=false;
function checkShopName(){
	var name = $.trim($("#name").val());
	if(name.length == 0) {
		showError($("#name"),"店铺名称不能为空");
		return false;
	}
	var url = "shop!checkShop.jhtml";
	var par = "name=" + name;
		$.ajax({
			type: "POST",data: par,url: url,dataType: "json",
			success: function(ret){
				if('0000' == ret.result) {
					showSuccess($("#name"));
					oResult=true;
				} else {
					showError($("#name"),ret.message);
					oResult=false;
				}
			}
		});
	return oResult
}
//判断省市区
function checkCitys(){
	var citys = city_getSelectValue("province", "city", "district");
	if(citys.district == "") {
		showError($("#province"),"请选择店铺所在的省市区");
		showError($("#city"),"请选择店铺所在的省市区");
		showError($("#district"),"请选择店铺所在的省市区");
		return false;
	}else{
		showSuccess($("#province"));
		showSuccess($("#city"));
		showSuccess($("#district"));
		
	}
	$("#v_province").val(citys.province);
	$("#v_city").val(citys.city);
	$("#v_district").val(citys.district);
	return true;
}

//判断详细地址

function checkAddress(){
	var address = $.trim($("#address").val());
	if(address.length == 0){
		showError($("#address"),"详细地址不能为空");
		return false;
	}else{
		showSuccess($("#address"));
		return true;
	}
}
//判断类目选择
function checkCatalog(){
	if($.trim($('#select_radio').html()) == ""){
		showError($('#select_radio'),'请选择类目')
		return false;
	}else{
		showSuccess($('#select_radio'))
		return true;
	}
} 

//判断联系人姓名
function checkUsername(){
	var uname = $.trim($("#uname").val());
	if(uname.length == 0){
		showError($("#uname"),"联系人姓名不能为空");
		return false;;
	}else{
		showSuccess($("#uname"));
		return true;
	}
}
//判断手机号
function checkPhone(){
	var phone = $.trim($("#phone").val());
	if(phone.length == 0){
		showError($("#phone"),"联系人手机号不能为空");
		return false;;
	}else if(!(/^1[3|4|5|8][0-9]\d{8}$/.test(phone))) {
		showError($("#phone"),"请输入有效的手机号");
		return false;
	}else{
		showSuccess($("#phone"));
		return true;
	}
}
//判断手机验证码格式

function checkPhonecode(){
	var phonecode = $.trim($("#phonecode").val());
	if(phonecode.length!=6||isNaN(phonecode)){
		showError($("#phonecode"),"短信验证码为6位数字");
		return false;;
	}else{
		showSuccess($("#phonecode"));
		return true;
	}
}
//判断QQ号格式
function checkQQ(){
	var qq = $.trim($("#qq").val());
	if(isNaN(qq)||qq.length<5){
		showError($("#qq"),"请输入正确的QQ号码");
		return false;
	}else{
		showSuccess($("#qq"));
		return true;
	}
}
function checkInput() {
	if(checkShopName()&&checkCitys()&&checkAddress()&&checkCatalog()&&checkUsername()&&checkPhone()&&checkPhonecode()&&checkQQ()){
		return true;
	}else{
		return false;
	}
}
//弹出
function showPrivacy(){
	var top = ($(window).outerHeight()-526)/2;
	var left = ($(window).outerWidth()-750)/2;
	document.getElementById("privacy").style.display = "block";
	document.getElementById("privacy_one").style.top = top+"px";
	document.getElementById("privacy_one").style.left = left+"px";
	
	window.onresize = function(){
		var top = ($(window).outerHeight()-526)/2;
		var left = ($(window).outerWidth()-750)/2;
		document.getElementById("privacy_one").style.top = top+"px";
		document.getElementById("privacy_one").style.left = left+"px";	
		}
}


//关闭
function closePrivacy(){
	
	document.getElementById("privacy").style.display = "none";
	
	
	
}
</script>
</#escape> 
