<#escape x as (x)!> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="css/order.css" />
<link type="text/css" rel="stylesheet" href="css/pager.css" />
<title>微袋财务管理</title>
<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="js/pager.js"></script>
<script type="text/javascript" src="js/go_top.js"></script>
<script type="text/javascript" src="js/add_pic.js"></script>
<script>
		var pageStr = createPageBar2(${page.pageNumber},${page.pageSize},${page.pageIndex},${page.total},"javascript:dosearch",5);
		function dosearch(p1, p2) {
			var frm = document.searchfrm;
			frm.pageIndex.value = p1;
			frm.pageSize.value = p2;
			frm.submit();
		}

//设置

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
//发送验证码
function sendPhoneCode() {
		var phone = ${shop.phone};
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
	function drawPage(){
		if(${ext.free}<0.01){
			alert("您的余额不足，暂时不能提现");
		}else{
			$("#all").css("display","none");
			$("#draw").css("display","block");
		}
	}
		
function update(){
	var sid=${shop.id};
	var bankname=$('#bankName').val();
	var cardcode=$.trim($('#cardCode').val());
	var username=$.trim($('#userName').val());
	var bankdeposit=$.trim($('#bankdeposit').val());
	var phonecode=$.trim($('#phonecode').val());
	var phone=${shop.phone};
	if(bankname==0){
		alert('请选择开户银行')
		return false;
	}
	if(cardcode==""){
		alert('请填写银行卡卡号')
		return false;
	}
	if(username==""){
		alert('请填写开户人姓名')
		return false;
	}
	if(bankdeposit==""){
		alert('请填写开户支行全称')
		return false;
	}
	if(phonecode==""){
		alert('请填写短信验证码，为6位数字')
		return false;
	}
	$.ajax({
			type:"post",
			data:{
				sid:sid,
				bank:bankname,
				accountno:cardcode,
				username:username,
				bankdeposit:bankdeposit,
				phonecode:phonecode,
				phone:phone
			},
			url:"shop!editBankNo.jhtml",
			dataType:"json",
			success:function(ret){
				if(ret.result=='0000'){
				 	window.location.reload();
				}
				else if(ret.result=='2001'){
				 	alert(ret.message);
				}
				alert(ret.message);
			}
		});		
	
}	
	
	
	function draw(){
		$.ajax({
			type:"post",
			data:{
				bank:$("#bank").val(),
				accountno:$("#accountno").val(),
				money:$("#money").val()
			},
			url:"order!getDraw.jhtml?sid=${shop.id}",
			dataType:"json",
			success:function(ret){
				if(ret.result=='0000'){
				 	window.location="order!getCashList.jhtml?sid=${shop.id}&op=cashRecord";
				}
				alert(ret.message);
			}
		});		
	}
	function editPayCode(){
		var message= $("#set_accout").html();		
		message = message.replace(/__PRE__/g, '');
			
		var title = "设置银行卡提现账号";
		add_pic({title:title, text:message, height:420, width:650});
		
		$("#bankName option[value='${ext.bank}']").attr("selected","selected");
	}
	//退货
	function refund(id,sid,orderid,state){
		$.ajax({
			type:"post",
			data:{
				id:id,
				state:state,
				sid:sid,
				orderid:orderid
			},
			url:"order!refund.jhtml",
			dataType:"json",
			success:function(ret){
				if(ret.result=='0000'){
				//	alert(ret.message);
					window.location.reload();
				}
				alert(ret.message);
			}
		});		
	}

</script>
</head>
 
<body style="background:#e8e8e8">
	<!--              头部                                                  -->
	<div class="header">
		<div class="header_top">
			<div class="logo">微 袋</div>
			<div class="header_nav">
				<a href="shop!getShopByid.jhtml?sid=${sid}"><div class="nav_shop">店铺</div></a>
				<a href="goods!goodsList.jhtml?sid=${shop.id}"><div class="nav_shop">商品</div></a>
				<div class="nav_visited">订单</div>
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
	
	
<!--              内容                                                 -->
	<div class="contain" id="contain">
		<form name="searchfrm" action="order!getFinance.jhtml?sid=${shop.id}" method="get" >			
			<input type="hidden" name="pageIndex" value="${page.pageIndex!'1'}">
     		<input type="hidden" name="pageSize" value="${page.pageSize!'20'}">
     	</form>
		
		<div class="contain_left">
			<h1 class="left_title">我的订单/财务</h1>
			<a href="order.jhtml?sid=${sid}"><p class="left_link">所有订单</p></a>
			<p class="left_visited">收入/提现</p>
		</div>
		<div class="contain_right" id="contain_right">
			<div id="all" style="display:block;">
				<div class="right_title">收入/提现</div>
				<div class="payinfo_title">
					<a href="order!getFinance.jhtml?sid=${shop.id}&op=lastSale">
						<div class="title_visited">我的收入</div>
					</a>
					<a href="order!getCashList.jhtml?sid=${shop.id}&op=paymentDe">
						<div class="title_link">收支明细</div>
					</a>	
					<a href="order!getCashList.jhtml?sid=${shop.id}&op=cashRecord">
						<div class="title_link">提现记录</div>
					</a>
				</div>
				<div class="shop_payinfo">
						<#if shop.pict?exists&&shop.pict?length gt 0>
							<img src="${imghost}${shop.pict}"/>
						<#else>
							<img src="images/shop-pic0.png"/>
						</#if>
						<div class="shop_paycode">
							<p>店铺名称 : <span>${shop.name}</span></p>
							<p>收款账号 : <span>${ext.accountno}</span><a href="javascript:;" onclick="editPayCode()">修改收款账号</a></p>
						</div>
				</div>
				<div class="income">
					<div class="seven_in">
						<p>
							最近7天收入            
							<a href="order!getCashList.jhtml?sid=${shop.id}&op=paymentDe">查看收支明细</a>
						</p>
						<p class="in_money">
							<img src="images/money.png" width="28" height="28" style="vertical-align:top;">
							${total?string('0.00')}<span>元</span>
						</p>
					</div>
					<div class="withdraw">
						 可提现金额
						 <p class="in_money">
							<img src="images/money.png" width="28" height="28" style="vertical-align:top;">
							${ext.free?string('0.00')}<span>元</span>
							<input type="button" name="btn1" value="提现" onclick="drawPage();"/>
						</p>
					</div>
				</div>
				
				<div class="capital">
					<div class="capital_title">资金动态</div>
					
					<#if op=='lastSale'>
						<div class="record">
							<span>最近交易记录</span>
							<a href="order!getFinance.jhtml?sid=${shop.id}&op=refundRecord">退款记录</a>
						</div>
						<div id="d1">
							<div class="heading">
								<div class="headname" style="width:100px;">时间</div>
								<div class="headname" style="width:300px;">商品名</div>
								<div class="headname" style="width:150px">所用金额</div>
								<div class="headname" style="width:150px">交易状态</div>
								<div class="headname" style="width:220px">操作</div>
							</div>
							<#if page?? && page.elements??>
							<#list page.elements as order>
								<div class="bill_list">
									<div class="bill_time">
										${order.recvtime?string('yyyy.MM.dd')}
									</div>
									<div class="bill_name">
										${order.name}
									</div>
									<div class="bill_price">
										${order.totalmoney?string('0.00')}
									</div>
									<div class="bill_state">
										<#if order.state==4>交易成功</#if>
									</div>
									<div class="bill_handle">
										<a href="javascript:;">详情</a>
									</div>
								</div>
							</#list>
							</#if>
						</div>
					</#if>
					
					<#if op=='refundRecord'>
						<div class="record">
							<span>退款记录</span>
							<a href="order!getFinance.jhtml?sid=${shop.id}&op=lastSale">最近交易记录</a>
						</div>
						<div id="d2">
							<div class="heading">
								<div class="headname" style="width:100px;">退款编号</div>
								<div class="headname" style="width:200px;">订单编号/商品信息</div>
								<div class="headname" style="width:150px">交易金额(元)</div>
								<div class="headname" style="width:150px">退款金额(元)</div>
								<div class="headname" style="width:170px">申请时间</div>
								<div class="headname" style="width:150px">退款状态</div>
							</div>
							<#if page?? && page.elements??>
							<#list page.elements as re>
								<div class="bill_list">
									<div class="bill_time">
										${re.id}
									</div>
									<div class="bill_name" style="width:200px;height:60px;margin:10px 0 0 0;">
										<p>${re.orderid}</p>
										<p style="width:200px;height:40px;overflow:hidden">${re.name}</p>
									</div>
									<div class="bill_price">
										${re.omoney?string('0.00')}
									</div>
									<div class="bill_price">
										${re.rmoney?string('0.00')}
									</div>
									<div class="bill_time" style="width:170px">
										${re.createtime?string('yyyy-MM-dd HH:mm:ss')}
									</div>
									<div class="bill_state">
										<#if re.state==0>
											<input type="button" name="b1" value="同意退货" class="refund_btn" onclick="refund('${re.id}','${re.sid}','${re.orderid}',1)"/> 
											<input type="button" name="b1" value="不同意退货" class="refund_btn" onclick="refund('${re.id}','${re.orderid}','${re.sid}',9)"/>
										<#else>
											${re.statename}
										</#if>	
									
									
									</div>
								</div>
							</#list>
							</#if>
						</div>
					</#if>
				</div>		
				
				<div class="cutpage" id="page">
	       	     	<script language="javascript">
						document.write(pageStr);
			       	</script>
       	 		</div>
			</div>
		<!-- 提现 -->
			<div id="draw" class="withdraw_out" style="display:none;">
				<div class="draw_title">
					<h1>提取余额到银行卡</h1>
					<h2>可用余额：<i>${ext.free?string('0.00')}</i> 元</h2>
					<span>
						<a href="order!getCashList.jhtml?sid=${shop.id}&op=cashRecord"">提现记录</a>
					</span>
				</div>
				<div class="draw_contain">
					<div class="pay_card">
						<label>选择银行卡:</label>
						<div class="card_code">
							<input type="text" name="bank" id="bank" value="${ext.bank}" readonly/>
							  尾号:<input  readonly type="text" name="accountno" id="accountno" value="${four}"/>
							<a href="javascript:;" onclick="editPayCode()">选择其他银行卡</a>
						</div>
					</div>
					<div class="pay_card">
						<label>提现金额:</label><input type="text" name="money" class="pay_money" id="money"/>元
					</div>
					<div class="pay_card" style="font-size:13px;color:#999;line-height:60px">
						提现人工审核周期：企业认证的店铺为1个工作日；个人/网店认证店铺为3个工作日。
					</div>
					<input type="button" class="pay_btn" name="draw_btn" value="确认提现" onclick="draw();"/>
				</div>
			</div>	
		</div>	
	</div>
	<!--   提现账户设置        -->
 	<div style="display:none">
 		<div id="set_accout">
 			<div class="warning">
 				<p>1. 请仔细填写账户信息，如果由于您填写错误导致资金流失，微袋概不负责</p>
 				<p>2. 只支持提现到借记卡账户，<span>不支持提现到信用卡</span></p>
 			</div>
 			<table class="pay_for"  border="0" cellpadding="0" cellspacing="0">
 				<tr>
 					<td class="td_left">
 						<span>*</span>开户银行：
 					</td>
 					<td class="td_right">
 						<select class="sel" id="__PRE__bankName">
 							<option value="">请选择开户银行</option>
                        	<option value="中国工商银行">中国工商银行</option>
                        	<option value="中国农业银行">中国农业银行</option>
                   			<option value="中国银行">中国银行</option>
                        	<option value="中国建设银行">中国建设银行</option>
                        	<option value="交通银行">交通银行</option>
                        	<option value="中国邮政储蓄银行">中国邮政储蓄银行</option>
                        	<option value="招商银行">招商银行</option>
                        	<option value="中信银行">中信银行</option>
                        	<option value="中国光大银行">中国光大银行</option>
                        	<option value="中国民生银行">中国民生银行</option>
                        	<option value="兴业银行">兴业银行</option>
                        	<option value="浦东发展银行">浦东发展银行</option>
                        	<option value="广发银行">广发银行</option>
                    	</select>
 					</td>
 				</tr>
 				<tr>
 					<td class="td_left">
 						<span>*</span>银行卡卡号：
 					</td>
 					<td class="td_right">
 						<input type="text" autocomplete="off" class="txt" value="${ext.accountno}" id="__PRE__cardCode"><span class="tishi">请仔细对照一遍卡号</span>
 					</td>
 				</tr>
 				<tr>
 					<td class="td_left">
 						<span>*</span>开卡人姓名：
 					</td>
 					<td class="td_right">
 						<input type="text" autocomplete="off" class="txt" value="${ext.username}" id="__PRE__userName"><span class="tishi">必须与银行开户姓名一致</span>
 					</td>
 				</tr>
 				<tr>
 					<td class="td_left">
 						<span>*</span>开户支行完整名称：
 					</td>
 					<td class="td_right">
 						<input type="text" autocomplete="off" class="txt" value="${ext.bankdeposit}" id="__PRE__bankdeposit"><span class="tishi">如：“中国工商银行杭州市武林支行”</span>
 					</td>
 				</tr>
 				<tr>
 					<td class="td_left">
 						<span>*</span>短信验证码：
 					</td>
 					<td class="td_right">
 						<input type="text" autocomplete="off" id="__PRE__phonecode" class="in_code">
 						<input type="button" id="__PRE__getcode" onclick="sendPhoneCode()" class="get_code" value="获取验证码">
 						<span  class="tishi">你绑定的手机号为${shop.phone}</span>
 					</td>
 				</tr>
 				<tr>
 					<td class="td_left">
 					</td>
 					<td class="td_right">
 						<input type="button" class="save_code" value="保存" onclick="update()"/>
 					</td>
 				</tr>
 			</table>
 		</div>
 	</div>			
   <div id="go_top" onclick="go_top()"><a class="go_top" href="javascript:;"><img src="images/arrows.png" style="margin-bottom:5px">返回顶部</a></div>
           	   		
<div class="footer" id="footer">
	Copyright © hilinli.com All Rights Reserved.  
</div>
<iframe name="hidefrm" style="display:none"></iframe>
</body>
</html>
</#escape>