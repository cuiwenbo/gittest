<#escape x as (x)!> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

<link href="css/bootstrap.css" rel="stylesheet" >
<link href="css/style.css" rel="stylesheet" >
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/scroll.js"></script>
<style>
.row {margin:0;padding:0}
.container{padding:0}
.mod_01{float:left;}
.dotModule_new{padding:0 5px;
				height:11px;
				line-height:6px;
				-webkit-border-radius:11px;
				background:rgba(45,45,45,0.5);
				position:absolute;
				bottom:5px;
				right:10px;
				z-index:11;}
#slide_01_dot{text-align:center;
				margin:3px 0 0 0;}
#slide_01_dot span{display:inline-block;
					margin:0 3px;
					width:5px;
					height:5px;
					vertical-align:middle;
					background:#f7f7f7;
					-webkit-border-radius:5px;}
#slide_01_dot .selected{background:#66ff33;}
.nav-zv{z-index:300}
a:hover{text-decoration:none}
a:focus{text-decoration:none}
a:visited{text-decoration:none}
a:active{text-decoration:none}
</style>

<title>微袋</title>

<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->


<script>

			
	window.onload =  function() {
					picts = "${goods.picts}".split(",");    		
		    		var path  = "${imghost}";
		    		
		    		if("${goods.picts}"==""){
		    			$("#pic_ppt").hide()
		    		}
		    		if(picts.length == 1){
		    			$("#pic_down").hide()
		    		}
		    		for(var i = 0; i < picts.length; i++) {
		    			if(picts[i].length == 0) continue;
		    			var pict = path + picts[i];
		    			var m = "<div class='mod_01'><img src='"+pict+"' width='"+ScreenWidth+"' onload='pic_height()'/></div>";
									
					$("#slide_01").append(m);
		    		} 
		    		if(document.getElementById("slide_01")){
						var slide_01 = new ScrollPic();
						slide_01.scrollContId   = "slide_01"; //内容容器ID
						slide_01.dotListId      = "slide_01_dot";//点列表ID
						slide_01.dotOnClassName = "selected";
						slide_01.arrLeftId      = "sl_left"; //左箭头ID
						slide_01.arrRightId     = "sl_right";//右箭头ID
						slide_01.frameWidth     = ScreenWidth;
						slide_01.pageWidth      = ScreenWidth;
						slide_01.upright        = false;
						slide_01.speed          = 30;
						slide_01.space          = 30; 
						slide_01.initialize(); //初始化
					} 
					$('#shop_content').find('img').css({width:ScreenWidth}) ;
			         $('#shop_content').find('img').css('max-width','');
			         $('#pic_ppt').css({width:ScreenWidth,height:ScreenWidth*3/4})
			        
					
					
				}
	function isWeiXin(){ 
		var ua = window.navigator.userAgent.toLowerCase(); 
		if(ua.match(/MicroMessenger/i) == 'micromessenger'){ 
			return true; 
		}else{ 
			return false; 
		} 
	}
	$(function(){
		if(!isWeiXin()){
			$('#share').hide()
		
		}
	}) 
	function pic_height(){
		var pic=$('#slide_01').find('img')
		for(var i=0;i<pic.length;i++){
			$(pic[i]).css('position','relative')
			if($(pic[i]).height()<ScreenWidth*3/4) continue;
			$(pic[i]).css('top',function(){
					return -(($(this).height()-ScreenWidth*3/4)/2)
			})
		}
	
	}
</script>
</head>

<body style="margin-bottom:50px;background:#f9f9f9">
<div class="container" id="container">
	<div class="row ">
    	 <div class=" col-xs-12 nlist-top">
    	 	<a href="shop.jhtml?id=${shop.id}" class="inde-gua" style="color:#999;float:left">
    	 		<img src="${imghost}${shop.pict}" style="width:20px;height:20px;border-radius:10px"/>
    	 		 ${shop.name}
    	 	</a>
           <a href="javascript:;" id="share" class="inde-guc" onclick="fx_dialog()">分享</a>      
         </div>
    	<div class="col-xs-12 det-img" style="padding:0">
         	<div id="myCarousel" class="carousel slide">
  				<div style="position:relative;overflow:hidden" id="pic_ppt">
					<div class="slide_01" id="slide_01">
					</div>
					<div class="dotModule_new" id="pic_down">
						<div id="slide_01_dot"></div>
					</div>
				</div>
			</div> 
        </div>
	</div>
    <div class="row ">
    	<div class="col-xs-12 det-iuc" style="border-top:1px solid #e5e5e5;border-bottom:0;background:#fff">
        	<div class="det-ifa">${goods.name}</div>
        	<div class="det-ifc">
        	<div class="det-idu"><span>售价:</span>￥${goods.price?string('0.00')} <del style="color:#ccc;font-size:12px;font-weight:normal">￥${goods.oprice?string('0.00')}</del><em style="float:right;padding-right:20px;color:#999;font-style:normal;font-weight:normal">已售${goods.orderamount}</em></div>
        	</div>
        </div>
	</div>
	
     <div class="row ">
    	<div class="with-par1" style="background:#fff;border-color:#e5e5e5;padding:5px 5px 5px 10px;font-size:13px;color:#666">
			<div>剩余：${goods.amount?string('0')}</div>
			<div>运费：<#if goods.deliveryfee==0>免运费<#else>${goods.deliveryfee?string('0.00')}</#if></div>
         </div>
     </div> 
         <div class="row">
         	
         		<div class="with-par1" style="overflow:hidden;background:#fff;margin:10px 0 10px 0;height:40px;line-height:40px;padding:0 10px;border-color:#e5e5e5;font-size:14px">
         			<a href="shop.jhtml?id=${goods.sid}" style="color:#333;text-decoration:none"><span style="float:left"><img src="img/shop-pic.png" width="30" style="vertical-align:middle"/>${shop.name}</span></a>
         			<span style="float:right;color:#999;font-size:12px">${shop.phone}</span>
         		</div>
      			
         </div>
         <div class="row">
 	<div class="col-xs-12 blaspace" id="shop_content" style="overflow:hidden;height:auto;padding-left:5px;font-size:14px">
   	 	${goods.content}
    </div>
 </div>
          <div class="container">
     	<div class="row">
     		<div class="col-xs-12 footer">
            	<span class="col-xs-12"><a href="${rc.contextPath}/m/shop/start?id=${shop.id}">店铺首页</a>丨 <a href="javascript:;" onclick="gz_dialog()">关注我们</a> </span>
                <span class="col-xs-12"><a href="http://www.hilinli.com">由 <h1  class="label label-primary">邻方科技</h1> 免费技术支持</a></span>
            </div>
        </div>
     </div> 
        <div class="container">
 <div class="row" >
 	<div class="col-xs-12 nav-zv" style="position:fixed">

	  <input type="button" value="立刻购买" style="margin-top:6px" class="bgc1" onclick="window.location='goods.jhtml?op=p1&id=${goods.id}'">
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
 <div id="fenx_dialog" onclick="close_fxdialog()" style="background:#000;position:fixed;z-index:2000;top:0;opacity:0;display:none">
   <div id="fx_dialog" style="position:relative;top:-150px">
   	<div style="color:#fff;text-align:right;font-size:20px;display:block;padding-right:20px">
   		X
   	</div>
   
      <div class="modal-content">
         <div class="modal-header" style="font-size:18px">
         
              <span class="modal-title" id="myModalLabel">
               1.点击右上角的 <img src="img/gongneng.png" style="width:40px;height:20px;border-radius:2px">
               
            </span>
            <br><br>
            <span class="modal-title" id="myModalLabel">
               2.在弹出的菜单中找到 <img src="img/set.png" style="width:40px;height:40px;border-radius:2px"/> 或者 <img src="img/share.png" style="width:40px;height:40px;border-radius:2px"/> 并点击,然后发送给好友或朋友圈
            </span>
             
         </div>
     </div>
   </div>
 </div>
 
      </body>
      <script>
      	var ScreenWidth=$('#container').outerWidth();
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
      	function fx_dialog(){
      		$('#fenx_dialog').show()
      		$('#fenx_dialog').animate({opacity:0.9},600)
      		$('#fx_dialog').animate({top:0},600)	
      	}
      	function close_fxdialog(){
      		$('#fx_dialog').animate({top:-150},600,function(){
      			$('#fenx_dialog').hide()
      		})
      		$('#fenx_dialog').animate({opacity:0},600)
      			
      	}
      	$(function(){
			$('#guanzhu_dialog').width(ScreenWidth);
			$('#guanzhu_dialog').height(ScreenHeight);
			$('#fenx_dialog').width(ScreenWidth);
			$('#fenx_dialog').height(ScreenHeight);
		})
      </script>
</html>
</#escape> 