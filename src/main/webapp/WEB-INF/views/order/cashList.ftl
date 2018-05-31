<#escape x as (x)!> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${rc.contextPath}/css/order.css" />
<link type="text/css" rel="stylesheet" href="${rc.contextPath}/css/pager.css" />
<title>微袋财务管理</title>
<script type="text/javascript" src="${rc.contextPath}/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/pager.js"></script>
<#--<script type="text/javascript" src="${rc.contextPath}/js/My97DatePicker/WdatePicker.js"></script>-->
<script type="text/javascript" src="${rc.contextPath}/js/go_top.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/js/laydate.js"></script>
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
 	function checkTime(){
 		var s=$("#stime").val();
 		var e=$("#etime").val();
 		var s2=$("#stime2").val();
 		var e2=$("#etime2").val();
 		//alert(s+""+e);
 		if(s>e&&e!=""&&s!=""||s2>e2&&e2!=""&&s2!=""){
 			alert("您选择的起止日期不合法，请检查");
 			return false;
 		}
 		return true;
 	}
	

</script>
</head>
 
<body style="background:#e8e8e8">
	<!--              头部                                                  -->
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
	
	
<!--              内容                                                 -->
	<div class="contain" id="contain">
		<form name="searchfrm" action="${rc.contextPath}/order/getFinance?sid=${shop.id}" method="get" >
			<input type="hidden" name="pageIndex" value="${page.pageIndex!'1'}">
     		<input type="hidden" name="pageSize" value="${page.pageSize!'20'}">
     	</form>
		
		<div class="contain_left">
			<h1 class="left_title">我的订单/财务</h1>
			<a href="${rc.contextPath}/order/start?sid=${sid}"><p class="left_link">所有订单</p></a>
			<p class="left_visited">收入/提现</p>
		</div>
		<div class="contain_right" id="contain_right">
				<div class="right_title">收入/提现</div>
				<div class="payinfo_title">
					<a href="${rc.contextPath}/order/getFinance?sid=${shop.id}&op=lastSale">
						<div class="title_link">我的收入</div>
					</a>
					<a href="${rc.contextPath}/order/cashList?sid=${shop.id}&op=paymentDe">
						<div class="<#if op=='paymentDe'>title_visited<#else>title_link</#if>">收支明细</div>
					</a>	
					<a href="${rc.contextPath}/order/cashList?sid=${shop.id}&op=cashRecord">
						<div class="<#if op=='cashRecord'>title_visited<#else>title_link</#if>">提现记录</div>
					</a>
				</div>
				
				<div class="capital">
				
					<#if op=='cashRecord'>
					<form action="${rc.contextPath}/order/cashList?sid=${shop.id}&op=cashRecord" name="form1" method="post" onsubmit="return checkTime();">
						<div class="capital_search">
							起止日期：<input type="text" class="date" id="stime" name="stime" value="${stime}" readonly onClick="laydate({istime: true, format: 'YYYY-MM-DD'})"> - <input class="date" type="text" id="etime" name="etime" value="${etime}" readonly onClick="laydate({istime: true, format: 'YYYY-MM-DD'})">
							<input class="sbtn" type="submit" value="搜 索"/>
						</div>
							
						<div class="heading">
							<div class="headname" style="width:160px;">流水号</div>
							<div class="headname" style="width:140px;">创建日期</div>
							<div class="headname" style="width:130px">备注|名称</div>
							<div class="headname" style="width:130px">收/支</div>
							<div class="headname" style="width:130px">金额（元）</div>
							<div class="headname" style="width:130px">资金渠道</div>
							<div class="headname" style="width:100px">状态</div>
						</div>
				
				
						<#if page?? && page.elements??>
						<#list page.elements as draw>
						<div class="bill_list">
							<div class="bill_code">
								${draw.id}
							</div>
							<div class="bill_date">
								${draw.createtime?string('yyyy-MM-dd HH:mm:ss')}
							</div>
							<div class="bill_pay">
								${draw.remarks}
							</div>
							<div class="bill_pay">
								支出
							</div>
							<div class="bill_pay" style="color:#f37800;font-weight:bold">
								${draw.money?string('0.00')}
							</div>
							<div class="bill_pay">
								${draw.bank}
							</div>
							<div class="bill_time">
								${draw.statename}
							</div>
						</div>
						</#list>
						</form>
						</#if>
						
					<#else>
					<form action="${rc.contextPath}/order/cashList?sid=${shop.id}&op=paymentDe" name="form2" method="post" onsubmit="return checkTime();">
						<div class="capital_search">
							起止日期：<input type="text" class="date" id="stime2" name="stime" value="${stime}" readonly onClick="laydate({istime: true, format: 'YYYY-MM-DD'})"> - <input class="date" type="text" id="etime2" name="etime" value="${etime}" readonly onClick="laydate({istime: true, format: 'YYYY-MM-DD'})">
							<input class="sbtn" type="submit" value="搜 索"/>
						</div>
							
						<div class="heading">
							<div class="headname" style="width:160px;">流水号</div>
							<div class="headname" style="width:150px;">创建日期</div>
							<div class="headname" style="width:220px">备注|名称</div>
							<div class="headname" style="width:130px">收入（元）</div>
							<div class="headname" style="width:130px">支出（元）</div>
							<div class="headname" style="width:130px">账户余额</div>
						</div>
				
				
						<#if page?? && page.elements??>
						<#list page.elements as pay>
						<div class="bill_list">
							<div class="bill_code">
								${pay.id}
							</div>
							<div class="bill_date" style="width:150px;">
								${pay.optime?string('yyyy-MM-dd HH:mm:ss')}
							</div>
							<div class="bill_pay" style="width:220px">
								${pay.remarks}
							</div>
							<div class="bill_pay" style="color:#1bb974;font-weight:bold"><!--收入-->
								<#if pay.type==0>+${pay.money?string('0.00')}</#if>
							</div>
							<div class="bill_pay" style="color:#f37800;font-weight:bold"><!-- 支出-->
								<#if pay.type==1>-${pay.money?string('0.00')}</#if>
							</div>
							<div class="bill_pay" style="color:#f37800;font-weight:bold"><!-- 账户余额-->
								${pay.restmoney?string('0.00')}
							</div>
						</div>
						</#list>
						</#if>
						</form>
					</#if>	
				</div>
				
					
				<div class="cutpage" id="page">
	       	     	<script language="javascript">
						document.write(pageStr);
			       	</script>
       	 		</div>
       	 		
       	 		
			</div>
		</div>
			
           	   		
<div class="footer" id="footer">
	Copyright © hilinli.com All Rights Reserved.  
</div>
<div id="go_top" onclick="go_top()"><a class="go_top" href="javascript:;"><img src="${rc.contextPath}/images/arrows.png" style="margin-bottom:5px">返回顶部</a></div>
<iframe name="hidefrm" style="display:none"></iframe>
</body>
</html>
</#escape>