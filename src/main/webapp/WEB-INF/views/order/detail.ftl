<#escape x as (x)!> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${rc.contextPath}/css/order.css" />
<title>订单详细信息</title>
<script src="${rc.contextPath}/js/jquery-1.8.2.js"></script>
<script>
	function toSend(){
			var deliverymd = document.getElementsByName('deliverymethod');
		    var md;
		    for(var i = 0 ; i < deliverymd.length ; i++){
		        if(deliverymd[i].checked){
		        	md=deliverymd[i].value
		        }
		   }
			$.ajax({
				type: "POST",
				data:{
					id:${order.id},
					sid:${order.sid},
					deliverymethod:md,
					delivery:$("#delivery").val(),
					deliveryid:$("#deliveryid").val()
				},
				url:"${rc.contextPath}/order/sendOut",
				dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						alert(ret.message);
						window.location="order/start?sid=${order.sid}&op=send";
					} else {
						alert(ret.message);
					}
			 	}
			});	
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
    	
    	function setinfo(){
    		$("#info").show();
    		
    	}

</script>
</head>
 
<body style="background:#e8e8e8">
	<div class="header">
		<div class="header_top">
			<div class="logo">微 袋</div>
			<div class="header_nav">
				<a href="${rc.contextPath}/shop/getShop?sid=${sid}"><div class="nav_shop">店铺</div></a>
				<a href="${rc.contextPath}/goods/goodsList?sid=${shop.id}"><div class="nav_shop">商品</div></a>
				<div class="nav_visited">订单</div>
			</div>
			<div class="shop_info">
				<div class="shop_pic">
					<#if shop.pict?exists&&shop.pict?length gt 0>
						<img src="${imghost}${shop.pict}" width="50" height="50"/>
					<#else>
						<img src="${rc.contextPath}/images/shop-pic0.png" width="50" height="50"/>
					</#if>
				</div>
				<div class="shop_name" style="position:relative">
					<p>${shop.name}</p>
					<p>${shop.phone}<a id="set_info" href="javascript:;">设置 <img src="${rc.contextPath}/images/jiantou.png"/></a>
						<ul class="set_info">
							<li><a href="${rc.contextPath}/home">切换店铺</a></li>
							<li><a href="${rc.contextPath}/user/getUserInfo">个人设置</a></li>
							<li><a href="${rc.contextPath}/shop/getShop?sid=${sid}">店铺设置</a></li>
							<li><a href="${rc.contextPath}/user/logout">退出</a></li>
						</ul>
					</p>
				</div>
			</div>
		</div>
	</div>
	<div class="contain" id="contain">
		
		<div class="contain_left">
			<h1 class="left_title">我的订单/财务</h1>
			<p class="left_visited">所有订单</p>
			<a href="${rc.contextPath}/order/getFinance?sid=${sid}&op=lastSale"><p class="left_link">收入/提现</p></a>
		</div>
		<div class="contain_right" id="contain_right">
			<div class="order_title_state">
				当前订单状态：<b>${order.statename}</b>
			</div> 
			<div class="order_info">
				<div class="order_info_title">
					订单详情<a href="javascript:window.history.go(-1)" style="font-weight:normal;float:right;font-size:14px;color:#07d">返回</a>
				</div>
				<div class="order_info_address">
					<p><label>收货地址：</label>${order.username} , ${order.phone} , ${order.address} </p>
					<p><label>买家留言：</label><span class="order_remarks">${order.remarks}</span></p>
				</div>
				<div class="order_userinfo">
					<h1>买家信息</h1> 
					<p><span>微信号：${order.username}</span><span>支付方式：${order.paytype}</span></p>
				</div>
				<div class="order_detail">
					<h1>订单信息</h1>
					<ul>
						<li>订单编号：${order.orderid}</li>
						<li>下单时间：${order.createtime?string('yyyy-MM-dd HH:mm')}</li>
						<li>付款时间：${order.paytime?string('yyyy-MM-dd HH:mm')}</li>
						<li>发货时间：${order.deliverytime?string('yyyy-MM-dd HH:mm')}</li>
						<li>收货时间：${order.recvtime?string('yyyy-MM-dd HH:mm')}</li>
						<li>物流方式：${order.delivery}</li>
					</ul>
				</div>
				<div class="order_goods">
					<div class="order_goodstitle">
						<div class="goodstitle" style="width:310px">宝贝</div>
						<div class="goodstitle" style="width:130px">宝贝属性</div>
						<div class="goodstitle" style="width:120px">订单状态</div>
						<div class="goodstitle" style="width:110px">单价(元)</div>
						<div class="goodstitle" style="width:100px">数量</div>
						<div class="goodstitle" style="width:130px">商品总价(元)</div>
					</div>
					<div class="order_goodscontain">
						<div class="goodscontain" style="width:309px">
							<p class="pic_info">
								<script>
									var picts = new Array(); 
							    	picts = "${picts}".split(","); 
							    	var pict = '${imghost}'+picts[0];
							    	if("${picts}".length==0){
							    		pict='${rc.contextPath}/images/no-pic.png'
							    	}   		
						    		var m = "<img src='"+pict+"'  width='60' height='60'/>";
									document.write(m)
								</script>
								<span>${order.name}</span>
							</p>
						</div>
						<div class="goodscontain" style="width:129px;text-align:center">
							<span class="cont">
								<script>
									var params='${order.param}'.split(',');
									var p='';
									for(var i=0;i<params.length;i++){
										p+=params[i]+'<br/>'
									}
									document.write(p)
								</script>
								
							</span>
						</div>
						<div class="goodscontain" style="width:119px;text-align:center">
							<span class="cont1">
								${order.statename}
								<br>
								<#if order.state==1>
								<input type="button" class="tosend" name="b1" id="b1" value="发 货" onclick="setinfo();">
								<div id="info" class="send_box" style="display:none;">
									<span class="right_3"></span>
									<p>
										<label>发货方式:</label>
										<span><input type="radio" onclick="check_radio(this)" name="deliverymethod" class="dm" value="0" checked="checked"/> 需要物流  </span>
										<span><input type="radio" onclick="check_radio(this)" name="deliverymethod" class="dm" value="1"/> 无需物流  </span>
									</p>
									<div id="wuliu">
									<p>
										<label>物流公司:</label>
										<select name="delivery" id="delivery">
											<option value="">请选择一个物流公司</option>
											<option value="申通">申通</option>
											<option value="顺丰">顺丰</option>
									  	</select>
									</p>		  
									<p>
									<label>快递单号:</label><input type="text" name="deliveryid" id="deliveryid"/>
									</p>
									</div>
									<input type="button" name="sub1" value="确定" onclick="toSend();" class="tosend_btn" />
								</div>
								</#if>
							</span>
						</div>
						<div class="goodscontain" style="width:109px;text-align:center">
							<span class="cont">${order.price?string('0.00')}<br/>(商品优惠 : <#if order.discount==0>无<#else>${order.discount}</#if>)</span>
						</div>
						<div class="goodscontain" style="width:99px;text-align:center">
							<span class="cont">${order.amount}</span>
						</div>
						<div class="goodscontain" style="width:130px;border:none;text-align:center">
							<span class="cont">${order.totalmoney?string('0.00')}<br>(含运费${order.deliverymoney?string('0.00')})</span>
						</div>
					</div>
				</div>
			</div>
		</div>		
	</div>

	<div class="footer" id="footer">
		 Copyright © hilinli.com All Rights Reserved.  
	</div>
	<script>
		function check_radio(obj){
			var parents=$("#info")
			if($(obj).val()==0){
				parents.css({
					height:'210',
					top:'-35px'
				})
				parents.find('.right_3').css('top','90px')
				$('#wuliu').show()
			}else{
				parents.css({
					height:'110',
					top:'5px'
				})
				parents.find('.right_3').css('top','45px')
				$('#wuliu').hide()
				
			}
		}
		$(function(){
			$('.cont1').click(function(e){
				e.stopPropagation();
				
			})
			
			$(document).not('span.cont1').click(function(){
		      	for(var i=0;i<$('.send_box').length;i++){
					$('.send_box').eq(i).hide();
				}
		    });
		  
		})
	
	</script>
 <iframe name="hidefrm" style="display:none"></iframe>
</body>
</html>
</#escape>