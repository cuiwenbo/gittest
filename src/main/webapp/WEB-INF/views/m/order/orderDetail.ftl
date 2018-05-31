<#escape x as (x)!> 
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link href="${rc.contextPath}/m/css/bootstrap.css" rel="stylesheet" >
<link href="${rc.contextPath}/m/css/style.css" rel="stylesheet" >
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="${rc.contextPath}/m/js/bootstrap.js"></script>
<style>
.row {margin:0;padding:0}
.container{ padding:0}
.owith-addr{padding:5px 10px}
.olist-box{padding:5px 10px}
.owith-sta{padding:10px}
</style>
<title>微袋</title>

<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<script>
function getRecept(id){
if(confirm("确认收货？？")){
	$.ajax({
		type: "POST",
		data:{
			id:id
		},
		url:"${rc.contextPath}/m/order/receipt",
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
}

</script>
</head>

<body style=" background:#f6f6f6;margin-bottom:40px">
	<div class="container"> 
      <div class="row">	
    	<div class="car2-name">收货地址</div>
    	<div class=" col-xs-12 owith-addr">
       		<span  class=" col-xs-12">${order.address}</span>
			<span  class=" col-xs-12">${order.username} ${order.phone}</span>
        </div> 
 		<div class="col-xs-12 owith-sta">交易状态：<span>${order.statename}</span></div>
          
           	  <div class="col-xs-12 olist-box">
                <div class="olist-sname">商品信息</div> 
                <div class="col-xs-3 olist-img"><img src="${imghost}${goods.mainPict}"></div>
                <div class="col-xs-9 car-ind">
                    <div class="car-dic1">${order.name}</div>
                    <div class="car-dic2">价格：￥${order.price?string('0.00')}（元）</div>
                    <div class="car-dic2">数量：${order.amount}${order.unit}</div>
                    <#if order.paramList??>
	          		   <#list order.paramList as param>
	                		<div class="car-dic2">${param[0]}:${param[1]}</div>
						</#list>
					<#else>
					
			     </#if>
       			</div>
   			 </div>
          <div class="col-xs-12 owith-cont">
          		联系店家:<span class="badge1">电话：  ${shop.phone}</span>

          </div>
         
          <div class="col-xs-12 owith-info">
          	<div>订单信息</div>
            <div class="col-xs-12 owith-sic ">
            	<span class="col-xs-12">订单编号:${order.orderid}</span>
                <span class="col-xs-12">创建时间:${order.createtime?string('yyyy-MM-dd HH:mm:ss')}</span>
                <span class="col-xs-12">成交时间:${order.paytime?string('yyyy-MM-dd HH:mm:ss')}</span>
                <span class="col-xs-12">发货时间:${order.deliverytime?string('yyyy-MM-dd HH:mm:ss')}</span>
                <span class="col-xs-12">确认时间:${order.recvtime?string('yyyy-MM-dd HH:mm:ss')}</span>
            	<#if order.state==3||order.state==4>
            	<span class="col-xs-12">物流公司:<#if order.delivery=="">无需物流<#else>${order.delivery}</#if></span>
            	<span class="col-xs-12">快递单号:${order.deliveryid}</span>
            	</#if>
            </div>
          </div>
           
		   <div class="container">
     	<div class="row">
     		<div class="col-xs-12 footer">
            	<span class="col-xs-12"><a href="${rc.contextPath}/m/shop/start?id=${sid}">店铺首页</a>丨 <a href="javascript:;" onclick="gz_dialog()">关注我们</a> </span>
                <span class="col-xs-12"><a href="http://www.hilinli.com">由 <h1  class="label label-primary">邻方科技</h1> 免费技术支持</a></span>
            </div>
        </div>
</div> 
		         
      	 <div class="container">
      	 
      	 
 <div class="row" >
 	<div class="col-xs-12 nav-car">
     
   		<div class="col-xs-3" style="width:68%;float:left;text-align:left;padding-left:10px">共计金额:<span style="color:red">￥${order.totalmoney?string('0.00')}</span>元</div>
    	<#if order.state==0>
    	<#if goods.paytype!='线下支付'>
    	<a  href="javascript:;" onclick="">
      	<div class="col-xs-6 nvt" data-target="#myModal"  data-toggle="modal" style="width:32%;padding:0">
      		立即付款
      	</div>
      	</a>
      	<#else>
      		线下支付
      	</#if>
      	</#if>
    	<#if order.state==3>
    	<a  href="javascript:;" onclick="getRecept('${order.id}');">
      	<div class="col-xs-6 nvt" data-target="#myModal"  data-toggle="modal" style="width:32%;padding:0">
      		确认收货
      	</div>
      	</a>
      	</#if>  
    </div>
   
                
        </div>
                
    </div>
 </div> 
<div id="guanzhu_dialog" onclick="close_dialog()" style="background:#000;position:fixed;z-index:2000;top:0;opacity:0;display:none">
   <div id="gz_dialog" style="position:relative;top:-150px">
   	<div style="color:#fff;text-align:right;font-size:20px;display:block;padding-right:20px">
   		X
   	</div>
   
      <img src="${rc.contextPath}/m/img/tisi.png"/>
   </div>
 </div>
</body>
</html>
<script>
      	var ScreenWidth=$(window).outerWidth();
      	var ScreenHeight=$(window).outerHeight();
      	function gz_dialog(){
      		$('#guanzhu_dialog').show()
      		$('#guanzhu_dialog').animate({opacity:0.9},600)
      		$('#gz_dialog').animate({top:0},600)	
      	}
      	function close_dialog(){
      		$('#gz_dialog').animate({top:-150},600,function(){
      			$('#guanzhu_dialog').hide()
      		})
      		$('#guanzhu_dialog').animate({opacity:0},600)
      			
      	}
      	$(function(){
			$('#guanzhu_dialog').width(ScreenWidth);
			$('#guanzhu_dialog').height(ScreenHeight);
		})
      </script>
</#escape> 