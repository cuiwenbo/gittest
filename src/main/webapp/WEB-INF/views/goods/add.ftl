<#escape x as (x)!> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${rc.contextPath}/css/goodslist.css" />
<title>商品添加</title>
<style>   
	.att_pic_div {float:left;text-align:center;height:120px;width:120px;margin-top:10px;position:relative;}
	.delete_pic{position:absolute;background:url(${rc.contextPath}/images/xx.png);width:20px;height:20px;top:-9px;left:100px;border:none;line-height:18px;color:white;font-size:13px;opacity:0.8;filter:alpha(opacity=80);}
	.delete_pic:hover{background:url(${rc.contextPath}/images/xx_hover.png)}
	.set_mainpic{position:absolute;width:100px;height:25px;bottom:18px;left:10px;background:#c6c6c6;opacity:0.8;color:white;font-size: 14px;line-height: 25px;filter:alpha(opacity=80)}
	.set_mainpic:hover{background:#4c4c4c}
	.main_goods_pict {border: 2px solid red;}
</style>

<script src="${rc.contextPath}/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/add_pic.js"></script>
<script charset="utf-8" src="${rc.contextPath}/kindeditor/kindeditor.js"></script>
<script>

	 function addPict() {
				var message= $("#uploadDiv").html();		
				message = message.replace(/__PRE__/g, '');
					
				var title = "上传商品标志图片";
				add_pic({title:title, text:message, height:125, width:430});
						
		    }
//头部设置    	
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
	$('#contain').height($('#main_right').outerHeight())
})	
    	    		
	function AfterOperate(param) {	
		if(param.op == "addpict") return endMainPict(param);
		if(param.op =="addgoods")  return afterSave(param);
	}

    	
    	function afterSave(param) {
    		if(param.result = '0000') {
    			alert("添加商品信息成功");
    			window.location.href="${rc.contextPath}/goods/goodsList?sid=${sid}";
    		} else {
    			alert("添加商品信息失败:" + param.message);
    		}
    	}
 

    	var picts = new Array(); 
    	var mainPictIdx = -1;
	function delPict(idx) {
    		picts[idx] = '';
    		$("#PICT_DIV" + idx).remove();
    		if(idx == mainPictIdx) {
    			for(var i = 0; i < picts.length; i ++) {
    				if(picts[i] != "") { mainPictIdx = i; break;}
    			}
    			if(mainPictIdx != -1) {
    				$("#PIC_IMG_" + mainPictIdx).addClass("main_goods_pict");
    			}
    		}    	
    	}
	function setMainPict(idx) {
    		if(mainPictIdx == idx) return;
    		$("#PIC_IMG_" + mainPictIdx).removeClass("main_goods_pict");	
    		mainPictIdx = idx;	
    		$("#PIC_IMG_" + mainPictIdx).addClass("main_goods_pict");
	}
    function endMainPict(p) {
    	if(p.result=='1000'){
    		alert(p.message);
  			return ;
    	}
        	var s = p.data.split(",");
    		var idx = picts.length;
    		picts[idx] = s[1] +s[2];
    		var pict = s[3]+s[1] +s[2];
    		var m = "<div class='att_pic_div' id='PICT_DIV" + idx + "'><a href='"+pict+"' target='_blank'><img id='PIC_IMG_"+idx+"' style='height:100px;width:100px' src='"+pict+"' /></a><br><a href='javascript:setMainPict("+idx+")' class='set_mainpic'>设为主图</a><a href='javascript:delPict("+idx+")' class='delete_pic'>x</a></div>";    	
		$("#PICT_DIV").append(m);
		closeMessage();    
		if(mainPictIdx == -1) {
			mainPictIdx = idx;		
			$("#PIC_IMG_" + mainPictIdx).addClass("main_goods_pict");		
		}
    }
	function checkData() {
		var p = '';
		var c = 0;
		for(var i = 0; i < picts.length; i ++) {
			if(picts[i] == '') continue;
			if(i == mainPictIdx) {
				if(c == 0) {
					p = picts[i];
				}  else {
					p = picts[i] + ',' + p;
				}
			}
			else {
				if(c == 0) {
					p = picts[i];
				} else {
					p = p + ',' + picts[i]
				}
			}
			c ++;
		}
		$("#pict").val(p);
		return true;
	}
    	
    KE.show({
				id : 'content',
				
				uploadJson: ' ../../jsp/upload_json.jsp',
				fileManagerJson : '../../jsp/file_manager_json.jsp',
				allowFileManager : true,
				 allowImageUpload : true, 
				
				cssPath : '${rc.contextPath}/kindeditor/examples/index.css',
				items : [
				'fullscreen', 'undo', 'redo', 'cut', 'copy', 'paste',
				'|', 'justifyleft', 'justifycenter', 'justifyright',
				'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
				'superscript', '|', 'selectall', '-',
				'title', 'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold',
				'italic', 'underline', 'strikethrough', 'removeformat', '|',
				 'image','hr', 'emoticons',  '|','page'],
			afterCreate : function(id) {
					KE.event.ctrl(document, 13, function() {
						KE.util.setData(id);
						document.forms['mainfrm1'].submit();
					});
					KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
						KE.util.setData(id);
						document.forms['mainfrm1'].submit();
					});
				}
			});
	
	function showError(obj,msg){
		var oSpan=$(obj).nextAll('span');
		oSpan.html(msg);
		obj.css({'border-color':'#e94946','color':'#e94946'})
	}
	function showSuccess(obj){
		var oSpan=$(obj).nextAll('span');
		oSpan.html('');
		obj.css({'border-color':'#cccccc','color':'#333'})
	}
	
	function checkTwo(){
		if($.trim($('#name').val())==""){
			return false;
		}
		if($.trim($('#price').val())==""){
			return false;
		}
		if($.trim($('#amount').val())==""){
			return false;
		}
		if($.trim($('#deliveryfee').val())==""){
			return false;
		}
		
		return true;
	}
	function checkOne(){
		if($.trim($('#name').val())==""){
			showError($('#name'),'请填写商品名称');
			$(window).scrollTop($('#name').offset().top)
			return false;
		}else{
			showSuccess($('#name'));
		}
		if($.trim($('#price').val())==""){
			showError($('#price'),'请填写商品价格');
			$(window).scrollTop($('#price').offset().top)
			return false;
		}else{
			showSuccess($('#price'));
		}
		if($.trim($('#amount').val())==""){
			showError($('#amount'),'请填写商品总数目');
			$(window).scrollTop($('#amount').offset().top)
			return false;
		}else{
			showSuccess($('#amount'));	
		}
		var oprice = $('#oprice').val();
		var price = $('#price').val();
		if(oprice==""){
			$('#oprice').val(price)
		}
		
		return true;
	}
	//更换步骤			
    function oneToTwo(){
    	if(checkOne()){
    		$('#add_first').hide();
    		$('#add_second').show();
	    	$('#contain').height($('#main_right').outerHeight())
    	}
    }
    function twoToOne(){
    	$('#add_second').hide();
    	$('#add_first').show();
		$('#contain').height($('#main_right').outerHeight())
    }
    	
    	
</script>

</head>
 
<body style="background:#e8e8e8">
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
					
						<img src="${imghost}${shop.pict}" width="50" height="50"/>
					
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
			<a href="${rc.contextPath}/goods/goodsList?sid=${sid}&op=insale" ><p class="left_link">出售中的商品</p></a>
			<a href="${rc.contextPath}/goods/goodsList?sid=${sid}&op=soldout" ><p class="left_link">已售罄的商品</p></a>
			<a href="${rc.contextPath}/goods/goodsList?sid=${sid}&op=warehouse"><p class="left_link" >仓库中的商品</p></a>
		</div>
		<div class="main_right"  id="main_right" style="background:#fbfbfb">
			<div class="add_title">
				发布商品
			</div>
			<form action="${rc.contextPath}/goods/addGoods" method="post" target="hidefrm" name="myForm" onsubmit="return checkData()">
			 <input type="hidden" name="picts" value="" id="pict">
			
			<div id="add_first" >
					<div class="step">
						<a href="javascript:;" style="border-bottom:1px solid #fff;margin-left:0px;">1.编辑基本信息</a>
						<a href="javascript:;" onclick="oneToTwo()">2.编辑商品详情</a>
					</div>
					<div class="step_one">
						<div class="basic_info" style="height:240px">
							<div class="shop_title" style="height:240px">
								<h1>基本信息</h1>
							</div>
							<div class="shop_main" style="height:240px">
								<p style="margin-top:15px;">
									<label><em>*</em>商品名称：</label>
									<input class="txt" type="text" autocomplete="off" id="name" name="name">
									<span class="error"></span>
								</p>
								<p>
									<label><em>*</em>商品价格：</label>
									<input class="txt" type="text" autocomplete="off" id="price" name="price">
									<span class="error"></span>
								</p>
								<p>
									<label>商品原价：</label>
									<input class="txt" type="text" autocomplete="off" id="oprice" name="oprice">
									<span>（不写则表明原价与现在价格相等）</span>
								</p>
								<p>
									<label><em>*</em>商品库存：</label>
									<input class="txt" type="text" autocomplete="off" id="amount" name="amount">
									<span class="error"></span>
								</p>
								<p>
									<label>限购数量：</label>
									<input class="txt" type="text" autocomplete="off" id="userlimit" name="userlimit" value="0">
									<span>（0 代表不限购）</span>
								</p>
							</div>
							
						</div>
						<div class="basic_info">
							<div class="shop_title">
								<h1>商品规格</h1>
							</div>
							<div class="shop_main">
								<p style="margin-top:15px;">
									<label>商品单位：</label>
									<input class="txt"  type="text" autocomplete="off" id="unit" name="unit"><span class="error"></span>
								</p>
								<p>
									<label>商品规格：</label>
									<input type="hidden" name="param" id="param"/>
									名称: <input class="standard" type="text" autocomplete="off" name="pn1" >
									参数值: <input class="standard"  type="text" autocomplete="off" name="pv1"> <span>(参数值之间用,分割)</span>
								<p>
									<label></label>
									名称: <input class="standard"  type="text" autocomplete="off" name="pn2">
									参数值: <input class="standard"  type="text" autocomplete="off" name="pv2"> <span>(例如：名称：尺寸 )</span>
								</p>
								<p>
									<label></label>
									名称: <input class="standard" type="text" autocomplete="off" name="pn3">
									参数值: <input class="standard" type="text" autocomplete="off" name="pv3"> <span>(例如：参数值：S，M，L，XL，XXL )</span></p>
									<input type="hidden" name="recommend" id="recommend" value="0"/>
								</p>
							</div>
						</div>
						<div class="basic_info" style="height:230px">
							<div class="shop_title" style="height:230px">
								<h1>商品图片</h1>
							</div>
							<div class="shop_main" style="height:230px">
								<p>
									<label>商品图片：</label>
									<input class="pic_btn" type="button" value="添加图片" onclick="addPict()">
								</p>
								<div class="addglo" style="margin:5px 0 0 130px;padding-right:3px;white-space:nowrap;height:135px;width:525px;overflow-x:scroll;overflow-y:hidden;border:1px solid #e0e0e0;background-color:white">
										<div style="width:10000px;margin-right:3px;"  id="PICT_DIV">	
										</div>
								</div>
							</div>
						</div>
						<div class="basic_info">
							<div class="shop_title">
								<h1>支付/物流</h1>
							</div>
							<div class="shop_main">
								<p>
									<label>支付方式：</label>
									<!--<input type="radio" name="paytype" id="weixin" value="微信支付">  微信支付-->
									<input type="radio" name="paytype" id="xianxia" checked  value="线下支付"> 线下支付
									<span> (使用线下支付，则可自行选择支付方式，如支付宝转账，面对面交易等)</span>
								</p>
								<p>
									<label>送货方式：</label>
									<input type="radio" name="deliverymethod" id="express" checked value="0">  快递
									<input type="radio" name="deliverymethod" id="byself" value="1"> 上门提货
									
								</p>
								<p>
									<label>送货金额：</label>
									<input class="txt" type="text" autocomplete="off" id="deliveryfee" name="deliveryfee" value="0"/> 元 <span class="error"></span>
									<input  type="hidden" name="state" id="state" value="0"/>
								</p>
								
								
							</div>
						</div>
						<input type="button" class="next_step" value="下一步" onclick="oneToTwo()"/>
					</div>
			</div>
			<div id="add_second" style="display:none">
				<div class="step">
						<a href="javascript:;" style="margin-left:0px;" onclick="twoToOne()">1.编辑基本信息</a>
						<a href="javascript:;" onclick="oneToTwo()" style="border-bottom:1px solid #fff;">2.编辑商品详情</a>
				</div>
				<div class="step_two">
					<div class="shili_pic"></div>
					<div class="goodscontent">
						<span class="left_3"></span>
						<textarea id="content" name="content"  style="width:550px;border:1px solid #ccc;height:450px;visibility:hidden;resize:none;max-width:545px"></textarea>
					</div>
					<input type="submit" class="next_step" value="保存商品"/>
				</div>	
			</div>
			</form>
		</div>
	</div>
<div class="footer" id="footer">
  Copyright © hilinli.com All Rights Reserved.  
</div>
<div style=" display:none;">
	  	<div id="uploadDiv">
			<div class="pop_sub" style="margin:10px;">
	    		<form name="mainfrm" action="upload" method="post" enctype="multipart/form-data" target="hidefrm"  onsubmit="return checkPic()">
					<input type="hidden" name="op" value="addpict"/>
					<input type="file" name="upload" value="选择文件"><br>
					<br>
					<center>
					<input type="submit" value=" 上传 ..." id="__PRE__Upload">
					</center>
				</form>
			</div>
     	</div>    
</div>	
<iframe name="hidefrm" style="display:none"></iframe>
</body>
</html>
</#escape>