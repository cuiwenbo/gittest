<#escape x as (x)!> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>商品管理</title>
<link type="text/css" rel="stylesheet" href="${rc.contextPath}/css/goodslist.css" />
<link type="text/css" rel="stylesheet" href="${rc.contextPath}/css/pager.css" />
<script type="text/javascript" src="${rc.contextPath}/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/pager.js"></script>
<#--<script type="text/javascript" src="${rc.contextPath}/js/ZeroClipboard.js"></script>-->
<script>
		var pageStr = createPageBar2(${page.pageNumber},${page.pageSize},${page.pageIndex},${page.total},"javascript:dosearch",5);
		function dosearch(p1, p2){
			var frm = document.searchfrm;
			frm.pageIndex.value = p1;
			frm.pageSize.value = p2;
			frm.submit();
		}
		function rec(){//页面加载事件--选中所有推荐商品
			var c=document.getElementsByName("box");
			for(var i=0;i<c.length;i++){
				//alert(c[i].value);
				if(c[i].value==1){
					//alert("ok");
					c[i].checked=true;
				}
			}
		}

		function show(id,recommend,c){//进行推荐商品
				//alert("推荐状态"+recommend);
				$.ajax({
				type: "POST",
				data:{
					id:id,
					recommend:recommend
				},
				url:"${rc.contextPath}/goods/rcmGoods",
				dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						//alert('删除成功');
						//window.location.reload();
					} else {
							alert(ret.message);
					}
			 	}
			});	
		}
		
		//下架--恢复正常商品相关
		function offorUp(id,state){
					if(confirm("确定该操作？")){
						$.ajax({
							type: "POST",
							data:{
								id:id,state:state,sid:${sid}
							},
							url:"${rc.contextPath}/goods/offGoods",
							dataType: "json",
							success: function(ret){
								if('0000' == ret.result) {
									alert('操作成功');
									window.location.reload();
								} else {
										alert(ret.message);
								}
						 	}
						});
					}
					
					
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
})
//删除商品
function del(id){
	
	if(confirm('确定删除该商品？')){
				$.ajax({
				type: "POST",
				data:{
					id:id
				},
				url:"goods/delgoods",
				dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						alert('删除成功');
						window.location.reload();
					} else {
							alert(ret.message);
					}
			 	}
			});	
	}
}
//二维码弹出框
function get_qrcode(gid){
	for(var i=0;i<$('.href_code').length;i++){
	
		$('.href_code').eq(i).hide()
		$('.href_msg').eq(i).hide()
	}
	
	$('#'+gid+'_code').show()
}

//生成二维码
function getCode(id){
//alert(id);
	$.ajax({
		type: "POST",
		data:{
			id:id
		},
		url:"${rc.contextPath}/goods/getQRCode",
		dataType: "json",
		success: function(ret){
			//alert(ret.data);
			$('#'+id+'_qrcode').attr("src",ret.data);
			get_qrcode(id);
	 	}
	});
}

//链接弹出框
//复制按钮
//var clip = new ZeroClipboard.Client();
//	clip.setHandCursor( true );
//	clip.addEventListener( "complete", function(){
//		 alert("复制成功！");
//	});
function get_href(gid){
	for(var i=0;i<$('.href_msg').length;i++){
		$('.href_msg').eq(i).hide();
		$('.href_code').eq(i).hide()
	}
	
	$('#'+gid+'_href').show()
//	clip.setText($('#'+gid+'_copytxt').val());
//	if(!$("#ZeroClipboardMovie_1").size()>0){
//		clip.glue(gid+"_copybtn");
//  	}else{
//  		$('#ZeroClipboardMovie_1').parent().css({
//			left:$('#'+gid+'_copybtn').offset().left,
//			top:$('#'+gid+'_copybtn').offset().top
//		})
//
//  	}
  }

  function copyUrl2(a) {
	  var Url2 = document.getElementById(a + "_copytxt");
	  Url2.select(); // 选择对象
	  document.execCommand("Copy"); // 执行浏览器复制命令
//      alert("复制成功！");
  }

//点击其他地方，框消失
$(function(){
	$('.href_a').click(function(e){
		e.stopPropagation();
		
	})
	$('.href_qrcode').click(function(e){
		e.stopPropagation();
		
	})
	$(document).not('a.href_qrcode').click(function(){
      	for(var i=0;i<$('.href_code').length;i++){
			$('.href_code').eq(i).hide();
		}
    });
	$(document).not('a.href_a').click(function(){
      	for(var i=0;i<$('.href_msg').length;i++){
			$('.href_msg').eq(i).hide();
			$('#ZeroClipboardMovie_1').parent().css({
				top:-9999
			})
		}
    });
   $('#contain').height($('#main_right').outerHeight())
   $('#main_left').height($('#main_right').outerHeight())
})

</script>
</head>
 
<body onload="rec();" style="background:#e8e8e8">

	<div class="header">
		<div class="header_top">
			<div class="logo">微 袋</div>
			<div class="header_nav">
				<a href="${rc.contextPath}/shop/getShop?sid=${sid}"><div class="nav_shop">店铺</div></a>
				<div class="nav_visited">商品</div>
				<a href="${rc.contextPath}/order/start?sid=${shop.id}"><div class="nav_shop">订单</div></a>
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
		<div class="main_left" id="main_left">
			<input type="button" value="发布商品" name="add" onclick="window.location='${rc.contextPath}/goods/toAddPage?sid=${shop.id}'">
			<a href="${rc.contextPath}/goods/goodsList?sid=${sid}"><h1>商品管理</h1></a>
			<a href="${rc.contextPath}/goods/goodsList?sid=${sid}&op=insale" ><p <#if op=='insale'> class="left_visited"<#else>class="left_link" </#if>>出售中的商品</p></a>
			<a href="${rc.contextPath}/goods/goodsList?sid=${sid}&op=soldout" ><p <#if op=='soldout'>  class="left_visited"<#else>class="left_link" </#if>>已售罄的商品</p></a>
			<a href="${rc.contextPath}/goods/goodsList?sid=${sid}&op=warehouse"><p  <#if op=='warehouse'>  class="left_visited"<#else>class="left_link" </#if>>仓库中的商品</p></a>
		</div>
		<div class="main_right"  id="main_right">
			<div class="main_right_title">
				<#if op=='insale'>出售中的商品</#if>
				<#if op=='soldout'>已售罄的商品</#if>
				<#if op=='warehouse'>仓库中的商品</#if>
				<#if op==''>所有商品</#if>
			</div>
			<div class="main_right_goodslist">
				<!--<div class="btns">
					<input type="button" value="下架" /><input type="button" value="删除" />
				</div>-->
				<div class="goods">
					<form name="searchfrm" action="${rc.contextPath}/goods/goodsList" method="get">
						<input type="hidden" name="pageIndex" value="${page.pageIndex!'1'}">
			     		<input type="hidden" name="pageSize" value="${page.pageSize!'20'}">
			     		<input type="hidden" name="shopid" value="${shop.id}">
					</form>
					<table class="table_shop"  cellspacing="0 " cellpadding="0">
						<tr class="table_th">
							<!--<td width="85px"><input type="checkbox" name="selAll" ></td>-->
							<td width="50px">推荐</td>
							<td width="250px">商品名称/价格</td>
							<td width="160px">创建时间</td>
							<td width="130px">库存</td>
							<td width="120px">序号</td>
							<td width="200px">操作</td>
						</tr>
						<#if page?? && page.elements??>
		   				<#list page.elements as goods>
						<tr>
							<td class="table_list">
								<input type="checkbox" name="box" id="${goods.id}" onclick="show(${goods.id},${goods.recommend},this);" value="${goods.recommend}"/>
							</td>
							<td class="table_list" id="haha">
							<a href="${rc.contextPath}/goods/getGoodsInfo?id=${goods.id}" style="cursor:pointer;height:70px;outline:none;background:#fff;width:260px;display:block">
							<script>
								var picts = new Array(); 
						    	picts = "${goods.picts}".split(","); 
						    	var pict = '${imghost}'+picts[0];
						    	if("${goods.picts}".length==0){
						    		pict='${rc.contextPath}/images/no-pic.png'
						    	}   		
					    		var m = "<img style='float:left;margin-top:5px;border-radius:2px;width:60px;height:60px;' src='"+pict+"' />";
								document.write(m)
							</script>
								<div class="goods_info">
									<h1>${goods.name}</h1>
									<h2>￥${goods.price?string('0.00')} <del style="color:#ccc">￥${goods.oprice?string('0.00')}</del></h2>
								</div>
							</a>
							</td>
							
							<td class="table_list">${goods.optime?string('yyyy-MM-dd HH:mm:ss')}</td>
							<td class="table_list">${goods.amount}</td>
							<td class="table_list">${goods_index + 1}</td>
							<td class="table_list">
								<input type="hidden" name="orderamount" id="orderamount" value="${goods.orderamount}"/>
								<a href="${rc.contextPath}/goods/getGoodsInfo?id=${goods.id}">编辑</a> |
								<a href="javascript:;" onclick="del(${goods.id});">删除</a>
								<#if goods.state==0>| <a href="#" onclick="offorUp(${goods.id},${goods.state});">下架</a></#if>
								<#if goods.state==9>| <a href="#" onclick="offorUp(${goods.id},${goods.state});">上架</a></#if>
								<br/>
								<a href="javascript:;" class="href_a" style="position:relative" onclick="get_href('${goods.id}')">
									链接
									<div id="${goods.id}_href" class="href_msg" style="display:none;">
										<span class="right_3"></span>
										<h4>商品详情链接</h4><br/>
										<span class="href_input">
											<input type="text" value="${pathLink}?op=show&id=${goods.id}" id="${goods.id}_copytxt" readonly>
											<input type="button" class="copy_botton" onclick="copyUrl2(${goods.id})" value="复制">
										</span>
									</div>
								</a> | 
								<a href="javascript:;" class="href_qrcode" style="position:relative" onclick="getCode('${goods.id}');">
									二维码
									<div id="${goods.id}_code" class="href_code" style="display:none">
										<span class="right_3"></span>
										<h4>商品二维码</h4><br/><br/>
										<img src="" name="qr_code" id="${goods.id}_qrcode" width=150px height=150px/>
									</div>
								</a>
								
							</td>
						</tr>
						</#list>
						</#if>
					</table>
		          	<!--分页符-->		
				    <div class="cutpage" id="page">
		       	      	<script language="javascript">
						document.write(pageStr);
						</script>
		        	</div>
				</div>
			</div>
		</div>
	</div>
<div class="footer" id="footer">
  Copyright © hilinli.com All Rights Reserved.  
</div>
<iframe name="hidefrm" style="display:none"></iframe>

</body>
</html>
</#escape>