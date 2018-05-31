<#escape x as (x)!> 
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link href="css/bootstrap.css" rel="stylesheet" >
<link href="css/nstyle.css" rel="stylesheet" >
<link href="css/pager.css"rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/pager.js"></script>
<meta name="description" content="Example icons in navigation with Bootstrap version 2.0 from w3cschool.cc">
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>

<style>
a:hover{text-decoration:none}
a:focus{text-decoration:none}
a:visited{text-decoration:none}
a:active{text-decoration:none}
a{color:inherit}
.ode-igg{overflow:hidden;max-height:45px}
.ode-cls img{width:80px;height:80px}
</style>
<script>
	var pageStr = createPageBar3(${page.pageNumber},${page.pageSize},${page.pageIndex},${page.total},"javascript:dosearch",5);
	function dosearch(p1, p2) {
		var frm = document.searchfrm;
		frm.pageIndex.value = p1;
		frm.pageSize.value = p2;
		frm.submit();
	}
	function selected(){
		if(${state}<0){return}
		$("#state").val(${state}).selected;//订单状态
	}
	function getRecept(id){
		if(confirm("确认收货？？")){
			$.ajax({
				type: "POST",
				data:{
					id:id
				},
				url:"order!Receipt.jhtml",
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
	
	//取消订单
	function cancel(id){
		if(confirm("确定取消该订单？")){
		$.ajax({
				type: "POST",
				data: {
					id:id
				},
				url: "order!cancelOrder.jhtml",
				dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						alert(ret.message);
						window.location.reload();
					}else {
						alert(ret.message);
					}
				}
		});
		}
	}
</script>
<title>微袋</title>

<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>

<body style=" background:#f6f6f6;" onload="selected();">
<div class="container">
 	<form name="searchfrm" action="order.jhtml" method="get" >			
		<input type="hidden" name="pageIndex" value="${page.pageIndex!'1'}">
		<input type="hidden" name="pageSize" value="${page.pageSize!'20'}">
	</form>
	 	<div class="row">
            <ul id="myTab" class="nav nav-tabs ode-tab">
               <li <#if state==0>class="active"</#if>>
               	<a href="order.jhtml?state=0" >
               	  <span class="ode-con">${count0}</span>
                  <span class="ode-con">待付款</span>
               	</a></li>
               <li <#if state==1>class="active"</#if>>
               	<a href="order.jhtml?state=1" >
               	   <span class="ode-con">${count1}</span>
                   <span class="ode-con">待发货</span>
                </a>
               </li>
               <li <#if state==3>class="active"</#if>>
               	 <a href="order.jhtml?state=3">
               	    <span class="ode-con">${count3}</span>
                    <span class="ode-con">已发货</span>
                 </a>
               </li>
               <li <#if state==4>class="active"</#if>>
               	 <a href="order.jhtml?state=4">
                     <span class="ode-con">${count4}</span>
                     <span class="ode-con">已签收</span>
                 </a>
               </li>
              
            </ul>
            <ul id="myTab" class="nav nav-tabs ode-tab" style="margin-top:10px;border-top:1px solid #d5d5d5;">
         		  <li <#if state==-2>class="active"</#if> style="width:100%">
         		  	<a href="order.jhtml" style="text-decoration:none;text-align:left;padding-left:5%;padding-top:0;padding-bottom:0;border-bottom:1px solid #d5d5d5;">全部订单</a></li>
            </ul>
            <div id="myTabContent" class="tab-content">
               <div class=" tab-pane fade in active" id="home">
               	  	<#if page?? && page.elements??>
   					<#list page.elements as order>
		             	  <div class="col-xs-12">
		                    <div class="col-xs-8 ode-iss" style="width:82%">
		                   	 		<span style="background:#44cc6a;color:#fff;padding:2px;border-radius:2px">
			                   	 		<#if order.state == 0>待付款</#if>
			                        	<#if order.state == 1>待发货</#if>
			                        	<#if order.state == 3>已发货</#if>
			                        	<#if order.state == 4>已签收</#if>
			                        	<#if order.state == 8>已失效</#if>
			                        	<#if order.state == 9>已取消</#if>
			                		</span>
			                		 订单号:${order.orderid}
		                    </div>
		                   	<div class="col-xs-4 ode-isy" style="width:18%;float:right">
		                   		 &nbsp;
		                   		 <#if order.state == 0>
		                   			<a href="javascript:;" onclick="cancel('${order.id}')">取消</a>
		                   		</#if> 
		                    </div>
		                  </div>    
		                   <a href="order!getOrder.jhtml?id=${order.id}" style="position:relative">
		                  
		               	 <div class="col-xs-12 ode-the">
		                   		<div class="col-xs-3 ode-cls">
		                        	<img src="${imghost}${order.mainPict}">
		                        </div>
		                        <div class="col-xs-9 ode-isa">
		                        		 <span style="position:absolute;top:35px;right:10px;border-radius:10px;padding:0;width:20px;height:20px;display:block;background:#b8b8b8;">
		                        		 	<img style="padding:0 0 4px 3px;" src="img/arrow.png" />
		                        		 </span>
		                        		<span class="ode-igg">${order.name}</span>
										<span class="ode-ifg">
										<span style="float:left;padding-left:0"><i>￥${order.price}</i> X ${order.amount}</span>
										<span style="float:right">总价:<i>￥${order.totalmoney?string('0.00')}</i></span>
										</span>
		                        	
		                        </div>
		                    </div>
		                    </a>
		                    
	                </#list>
  					</#if> 
		       </div>
            </div>		        
    	</div>
    <div class="row" >

<div class="cutpage" id="page">
   	     	<script language="javascript">
				document.write(pageStr);
	       	</script>
   </div>
   </div>
    </div>
      
 
 
    


<div class="container">
     	<div class="row">
     		<div class="col-xs-12 footer">
            	<span class="col-xs-12"><a href="shop.jhtml?id=${sid}">店铺首页</a>丨 <a href="javascript:;" onclick="gz_dialog()">关注我们</a> </span>
                <span class="col-xs-12"><a href="http://www.hilinli.com">由 <h1  class="label label-primary">邻方科技</h1> 免费技术支持</a></span>
            </div>
        </div>
</div>

<div id="guanzhu_dialog" onclick="close_dialog()" style="background:#000;position:fixed;z-index:2000;top:0;opacity:0;display:none">
   <div id="gz_dialog" style="position:relative;top:-150px">
   	<div style="color:#fff;text-align:right;font-size:20px;display:block;padding-right:20px">
   		X
   	</div>
   <img src="img/tisi.png"/>
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
