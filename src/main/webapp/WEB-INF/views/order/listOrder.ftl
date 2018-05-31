<#escape x as (x)!> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${rc.contextPath}/css/order.css" />
<link type="text/css" rel="stylesheet" href="${rc.contextPath}/css/pager.css" />
<title>微袋订单查询管理</title>
<script type="text/javascript" src="${rc.contextPath}/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/pager.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/add_pic.js"></script>
<#--<script type="text/javascript" src="${rc.contextPath}/js/My97DatePicker/WdatePicker.js"></script>-->
    <script type="text/javascript" src="${rc.contextPath}/js/laydate.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/go_top.js"></script>
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


//全选		
		function selectAll(){
			var checkList=document.getElementsByName("sel_one");
			if(document.getElementById("all_select").checked){
				for(var i=0;i<checkList.length;i++){
					checkList[i].checked=true;
				}
			}
			else{
				for(var j=0;j<checkList.length;j++){
					checkList[j].checked=false;
				}
			}
			
		}
	
		function selected(){
			$("#state").val(${s1}).selected;//订单状态
			$("#deliverymethod").val(${deliverymethod}).selected;//运送方式
			$("#pay").val("${pay}").selected;//订单状态
		}
		//最近七天
		function seven_days(){
			var oDate = new Date();
			var	oYear = oDate.getFullYear();
			var	oMonth = oDate.getMonth()+1;
			var	oDay = oDate.getDate();
			if(oMonth<10){
				oMonth=0+''+oMonth
			}
			if(oDay<10){
				oDay=0+''+oDay
			}
			$('#etime').val(oYear+'-'+oMonth+'-'+oDay)
			oDate.setDate(oDate.getDate()-6);
			var	oYears = oDate.getFullYear();
			var	oMonths = oDate.getMonth()+1;
			var	oDays = oDate.getDate();
			if(oMonths<10){
				oMonths=0+''+oMonths
			}
			if(oDays<10){
				oDays=0+''+oDays
			}
			$('#stime').val(oYear+'-'+oMonth+'-'+oDays)
			
			
		}
		//最近三十天
		function thirty_days(){
			var oDate = new Date();
			var	oYear = oDate.getFullYear();
			var	oMonth = oDate.getMonth()+1;
			var	oDay = oDate.getDate();
			if(oMonth<10){
				oMonth=0+''+oMonth
			}
			if(oDay<10){
				oDay=0+''+oDay
			}
			$('#etime').val(oYear+'-'+oMonth+'-'+oDay)
			oDate.setDate(oDate.getDate()-29);
			var	oYears = oDate.getFullYear();
			var	oMonths = oDate.getMonth()+1;
			var	oDays = oDate.getDate();
			if(oMonths<10){
				oMonths=0+''+oMonths
			}
			if(oDays<10){
				oDays=0+''+oDays
			}
			$('#stime').val(oYears+'-'+oMonths+'-'+oDays)
			
			
		}
		//发货相关  	
    	function setinfo(id){
    		for(var i=0;i<$('.tosend_box').length;i++){
					$('.tosend_box').eq(i).hide();
			}
    		$("#"+id).show();
    		
    	}
    	$(function(){
			$('.order_tosend').click(function(e){
				e.stopPropagation();
				
			})
			
			$(document).not('div.order_tosend').click(function(){
		      	for(var i=0;i<$('.tosend_box').length;i++){
					$('.tosend_box').eq(i).hide();
				}
		    });
		  
		})
    	function toSend(id1,sid1){
    		var deliverymd = document.getElementsByName('deliverymethod_'+id1);
		    var md;
		    for(var i = 0 ; i < deliverymd.length ; i++){
		        if(deliverymd[i].checked){
		        	md=deliverymd[i].value
		        }
		   }
		  	$.ajax({
				type: "POST",
				data:{
					id:id1,
					sid:sid1,
					deliverymethod:md,
					delivery:$("#delivery_"+id1).val(),
					deliveryid:$("#deliveryid_"+id1).val()
				},
				url:"${rc.contextPath}/order/sendOut",
				dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						alert(ret.message);
						window.location.reload();
					} else {
						alert(ret.message);
					}
			 	}
			});	
	}
	function check_radio(obj,orderid){
		var parents=$("#info_"+orderid)
		if($(obj).val()==0){
			parents.css({
				height:'210',
				top:'-85px'
			})
			parents.find('.right_3').css('top','90px')
			$('#wuliu_'+orderid).show()
		}else{
			parents.css({
				height:'110',
				top:'-40px'
			})
			parents.find('.right_3').css('top','45px')
			$('#wuliu_'+orderid).hide()
		}
	}	
		//线下支付
		function topay(id,sid){
		//alert("id="+id+"sid="+sid);
		if(!confirm("确定支付？？")) return;
				$.ajax({
				type: "POST",
				data:{
					id:id,
					sid:sid
				},
				url:"${rc.contextPath}/order/offline",
				dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						alert(ret.message);
						window.location.reload();
					} else {
						alert(ret.message);
					}
			 	}
			});	
		}
	//退货处理
	function returnM(id,sid){
		if(!confirm("确定退货？？")) return;
				$.ajax({
				type: "POST",
				data:{
					orderid:id,
					sid:sid
				},
				url:"${rc.contextPath}/order/refund",
				dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						alert(ret.message);
						window.location.reload();
					} else {
						alert(ret.message);
					}
			 	}
			});	
	}
</script>
</head>
 
<body style="background:#e8e8e8" onload="selected();">
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
			<a href="${rc.contextPath}/order/getFinance?sid=${shop.id}&op=lastSale"><p class="left_link">收入/提现</p></a>
		</div>
		<div class="contain_right" id="contain_right">
			<div class="right_title">订单信息</div>
			<form action="${rc.contextPath}/order/start?sid=${sid}&op=search" name="form1" method="post" >
				<div class="right_search">
					<table border="0" cellspacing="0 " cellpadding="0" class="search_table">
		   				<tr>
		   					<td width="100" align="right">
		   						订单类型：
		   					</td>
		   					<td width="180">
		   						<select name="orderType" class="td_select">
	   								<option value="all">全部</option>
	   							</select>
		   					</td>
						    <td width="150">
						   		下单时间：
						    </td>
						    <td  width="330">
						    	<input type="text" name="stime" id="stime" class="td_date" readonly onClick="laydate({istime: true, format: 'YYYY-MM-DD'})" value="${stime}"/>
						    	到
						    	<input type="text" name="etime" id="etime" class="td_date" readonly onClick="laydate({istime: true, format: 'YYYY-MM-DD'})" value="${etime}"/>
							</td>
							<td class="td_Day" style="text-align:left;">					 
						 		<a href="javascript:;" onclick="seven_days()">最近7天</a>
								<a href="javascript:;" onclick="thirty_days()">最近30天</a>   
						    </td>
						</tr>
						<tr>
							<td>
						    	订单状态：
						    </td>
						    <td>
						    	<select name="state" id="state" class="td_select">
									<option value="" select="selected">全部</option>
									<option value="0">待付款</option>	
	   								<option value="1">待发货</option>	
	   								<option value="3">已发货</option>
	   								<option value="4">已收货</option>	
	   								<option value="55">关闭订单</option>
	   							</select>
						    </td>
						    <td>
						    	订单编号：
						    </td>
						    <td>
					    		<input autocomplete="off" type="text" name="orderid" id="orderid" class="td_txt" value="${oid}"/>
					    	</td>
							<td>
							</td>
						</tr>
					  	<tr>
	   						<td>
	   							物流方式：
	   						</td>
	   						<td>
	   							<select name="deliverymethod" id="deliverymethod" class="td_select">
	   								<option value="" select="selected">全部</option>
	   								<option value="0">快递发货</option>	
	   								<option value="1">上门提货</option>	
	   								<option value="2">其他</option>	
	   							</select>
	   						</td>
					    	<td>
					    		收货人姓名：
					    	</td>
					    	<td>
					    		<input type="text"autocomplete="off" name="username" id="username" class="td_txt" value="${username}"/>
					    	</td>
					    	<td>
							</td>
					  	</tr>
					  	<tr>
	   						<td>
	   							支付方式：
	   						</td>
	   						<td>
	   							<select name="paytype" id="pay" class="td_select">
									<option value="">全部</option>
									<option value="微信支付">微信支付</option>
									<option value="银行卡支付">银行卡支付</option>
									<option value="支付宝支付">支付宝支付</option>
									<option value="货到付款">货到付款</option>
	   							</select>
							</td>
					    	<td>
					    		收货人手机号：
					    	</td>
					    	<td>
					    		<input type="text" autocomplete="off" name="phone" id="phone" class="td_txt" value="${phone}"/>
					    	</td>
					    	<td>
							</td>
					  	</tr>
		   			</table>
				<input type="submit" class="search_btn" name="btn1" value="搜  索"/>
			</div>
		</form>
		
		
		
		<form name="searchfrm" action="${rc.contextPath}/order/strat?sid=${shop.id}" method="get" >
			<input type="hidden" name="pageIndex" value="${page.pageIndex!'1'}">
     			<input type="hidden" name="pageSize" value="${page.pageSize!'20'}">
     			
		</form>
			<div class="order_state_list">
				<div class="state">
					<a href="${rc.contextPath}/order/start?sid=${sid}" <#if op==''|| op=="search">class="selectUrl"<#else>class="link"</#if>>所有订单</a>
					<a href="${rc.contextPath}/order/start?sid=${sid}&op=topay" <#if op=='topay'>class="selectUrl"<#else>class="link"</#if>>待付款</a>
					<a href="${rc.contextPath}/order/start?sid=${sid}&op=tosend" <#if op=='tosend'>class="selectUrl"<#else>class="link"</#if>>待发货</a>
					<a href="${rc.contextPath}/order/start?sid=${sid}&op=send" <#if op=='send'>class="selectUrl"<#else>class="link"</#if>>已发货</a>
					<a href="${rc.contextPath}/order/start?sid=${sid}&op=sign" <#if op=='sign'>class="selectUrl"<#else>class="link"</#if>>标记签收</a>
					<!--<a href="order.jhtml?sid=${sid}&op=toRefund" <#if op=='toRefund'>class="selectUrl"<#else>class="link"</#if>>待退货</a>-->
					<a href="${rc.contextPath}/order/start?sid=${sid}&op=refunded" <#if op=='refunded'>class="selectUrl"<#else>class="link"</#if>>已退货</a>
					<a href="${rc.contextPath}/order/start?sid=${sid}&op=closed&state=55" style="border-right:none" <#if op=='closed'>class="selectUrl"<#else>class="link"</#if>>已关闭</a>

				</div>
				<div class="list_title">
					<div class="list_th" style="width:300px">商品</div>
					<div class="list_th" style="width:100px;">单价（元）</div>
					<div class="list_th" style="width:80px">数量</div>
					<div class="list_th" style="width:150px">买家</div>
					<div class="list_th" style="width:150px">交易状态</div>
					<#if op=='refunded'>
						<div class="list_th" style="width:100px">退款金额（元）</div>
					<#else>
						<div class="list_th" style="width:100px">实付金额（元）</div>
					</#if>
					
					<#if op=='topay'><div class="list_th" style="width:80px">支付方式</div></#if>
				</div>
			</div>
			<#if op=''>
			<div class="allCheck">
				<a href="${rc.contextPath}/order/start?sid=${sid}&state=66">不显示已关闭订单 </a>
			</div>
			</#if>
			<#if page?? && page.elements??>
   			<#list page.elements as order>
			<div class="order_list">
				<div class="order_title">
					<span>
						<#if op=='tosend'><input type="checkbox" name="sel_one" id="sel_one" style="vertical-align: middle;"/></#if> 
						 订单号：${order.orderid} 
					</span>
					<span>
						<#if order.state==0>下单时间：${order.createtime?string('yyyy-MM-dd HH:mm')}</#if>
						<#if order.state==1||order.state==3>成交时间：${order.paytime?string('yyyy-MM-dd HH:mm')}</#if>
					</span>
				</div>
				<div class="order_content">
					<script>
						var picts = new Array(); 
				    	picts = "${order.picts}".split(","); 
				    	var pict = '${imghost}'+picts[0];
				    	if("${order.picts}".length==0){
				    		pict='images/no-pic.png'
				    	}   		
			    		var m = "<a href='"+pict+"' target='_blank'><img class='order_img' src='"+pict+"' /></a>";
						document.write(m)
					</script>
				
					<div class="goodsName">
						${order.name}
					</div>
					<div class="order_price">
						${order.price?string('0.00')}
					</div>
					<div class="order_amount">
						${order.amount}<#if order.unit?exists&&order.unit?length gt 0> (${order.unit})</#if>
					</div>
					<div class="order_buyer">
						${order.username}
				    	<div class="contact"><#if order.phone?exists&&order.phone?length gt 0>手机：${order.phone}</#if></div>
				    	<div class="contact"><#if order.weixin?exists&&order.weixin?length gt 0>微信：</#if></div>
					</div>
					<div class="order_state">
						${order.statename}
				    	<div><a href="${rc.contextPath}/order/getOrder?id=${order.id}">详情</a></div>
				    	
					</div>
					<div class="order_money">
						${order.totalmoney?string('0.00')}
					    <#if order.deliverymoney!=0><div>(含运费  ${order.deliverymoney?string('0.00')})</div></#if>
					   <!-- <div><a href="javascript:;">查看物流</a></div>-->
					</div>
					<#if order.state==0>
						<div class="order_buyer" style="width:80px;">
							<span <#if order.paytype!='线下支付'>style="line-height:60px"</#if>>${order.paytype}</span>
							<#if order.paytype=='线下支付'>
								<input type="button" name="pay_btn" class="pay_btn" value="付款" onclick="topay('${order.id}','${order.sid}');"/>
					    	</#if>
						</div>
					</#if>
					<#if order.state==1||order.state==3||order.state==4>
					<div class="order_tosend" <#if order.state==1>style="margin:5px 0;"</#if>>
						 <#if order.state==1>
						 	<input type="button" name="btn2" value="发 货" class="tosend" onclick="setinfo('info_${order.id}');"/>
					 		<div id="info_${order.id}" class="tosend_box" style="display:none;">
					 			<span class="right_3"></span>
					 			<p>
									<label>发货方式:</label>
									<span>
										<input type="radio" name="deliverymethod_${order.id}"  value="0" checked="checked" onclick="check_radio(this,'${order.id}')"/>
										需要物流
									</span> 
									<span>
										<input type="radio" name="deliverymethod_${order.id}"  value="1" onclick="check_radio(this,'${order.id}')"/>
										无需物流
									</span>	
								</p>  
								<div id="wuliu_${order.id}">
									<p>
										<label>物流公司:</label>
										<select name="delivery_${order.id}" id="delivery_${order.id}">
											<option value="">请选择一个物流公司</option>
											<option value="邮政平邮">邮政平邮</option>
											<option value="EMS">EMS</option>
											<option value="百世汇通">百世汇通</option>
											<option value="能达快递">能达快递</option>
											<option value="顺丰速运">顺丰速运</option>
											<option value="龙邦速递">龙邦速递</option>
											<option value="邮政国内小包">邮政国内小包</option>
											<option value="中通速递">中通速递</option>
											<option value="天天快递">天天快递</option>
											<option value="圆通速递">圆通速递</option>
											<option value="申通E物流">申通E物流</option>
											<option value="德邦物流">德邦物流</option>
											<option value="快捷速递">快捷速递</option>
											<option value="全峰快递">全峰快递</option>
											<option value="韵达快递">韵达快递</option>
											<option value="国通快递">国通快递</option>
											<option value="其它快递">其它快递</option>
										</select>
									</p>
									<p>
										<label>快递单号:</label>
										<input type="text" name="deliveryid_${order.id}" id="deliveryid_${order.id}"/>
									</p>
								</div>
								<input type="button" name="sub1" value="确定" class="tosend_btn" onclick="toSend('${order.id}','${order.sid}');"/>
							</div>
						</#if>
						
							<input type="button" name="return_btn" class="pay_btn" value="退货" onclick="returnM('${order.id}','${order.sid}')"/>
						
					</div>
					</#if>	
				</div>
			</div>
			</#list>
			</#if>
			<div class="cutpage" id="page">
       	     	<script language="javascript">
					document.write(pageStr);
		       	</script>
       	 	</div> 
		</div>		
	</div>

 				
           	   		
<div id="go_top" onclick="go_top()"><a class="go_top" href="javascript:;"><img src="${rc.contextPath}/images/arrows.png" style="margin-bottom:5px">返回顶部</a></div>
		  
 <div class="footer" id="footer">
  Copyright © hilinli.com All Rights Reserved.  
</div>
<iframe name="hidefrm" style="display:none"></iframe>
</body>
</html>
</#escape>