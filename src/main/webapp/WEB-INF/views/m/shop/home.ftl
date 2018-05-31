<#escape x as (x)!> 
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link href="${rc.contextPath}/m/css/bootstrap.css" rel="stylesheet" >

<link href="${rc.contextPath}/m/css/nstyle.css" rel="stylesheet" >
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="${rc.contextPath}/m/js/bootstrap.js"></script>

<style>
a:hover{text-decoration:none}
a:focus{text-decoration:none}
a:visited{text-decoration:none}
a:active{text-decoration:none}
.col-xs-6{margin-top:5px}
.inde-incbor{padding:0 5px}
.inde-incbor a{color:#666}
.inde-incbor1{padding:0 5px}
.inde-incbor1 a{color:#666}
.inde-logo{z-index:2;}
.inde-inc{z-index:1;}
.inde-sa-indec{
	height:40px;
	overflow:hidden;
	display:block
}
.inde-sa-img img{width:100%}
</style>

<script>
	$(function(){
		
		var shoplist = $('#shop_list').find('.col-xs-6');
		for(var i=0;i<shoplist.length;i++){
			if(i%2==0){
				$(shoplist[i]).addClass('inde-ipc');
			}else{
				$(shoplist[i]).addClass('nlist-ipp');
			}
		}
		
		var shop_pic = $('#shop_list').find('img');
		for(var i=0;i<shop_pic.length;i++)
			$(shop_pic[i]).height(function(){{
				return $(this).width();
			}
		})
		
		$('#shop_bg').width(ScreenWidth)
		$('#shop_bg').height(ScreenWidth*4/9)
	})
	
	//关注
	function setfocus(sid,op){
		//alert(op);
		$.ajax({
			type: "POST",
			data:{
				sid:sid,
				op:op
			},
			url:"${rc.contextPath}/m/shop/focus",
			dataType: "json",
			success: function(ret){
				if('0000' == ret.result) {
					if(op=='yes'){ 
						alert("关注成功");
						$("#intre").html('<a href="javascript:;" class="inde-guc" onclick="setfocus('+"'${shop.id}','no')"+'">取消关注</a> ');	
					}
					if(op=='no') {
						alert("取消成功");
						$("#intre").html('<a href="javascript:;" class="inde-guc" onclick="setfocus('+"'${shop.id}','yes')"+'">关注</a> ');	
					}
					
				} 
				else if('1001'==ret.result){
					window.location="${rc.contextPath}";
				}
				else {
					alert(ret.message);
				}
			}
		});	
	}
</script>
<title>微袋</title>

<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>

<body>
<div class="container" id="container">
      <div class="row">	
                <div class=" col-xs-12 inde-top">
                	 <a href="${rc.contextPath}/m/shop/start?id=${shop.id}" class="inde-gua"><img src="${imghost}${shop.pict}" style="width:20px;height:20px;border-radius:10px"/> ${shop.name}</a>
	                 <div style="text-align: right;">
                    	<a href="${rc.contextPath}/m/shop/intrest" class="inde-guc" >我的关注</a>
                    </div>
	                 <div id="intre" style="text-align: right;">
	                 
	                 <#if sv??>
	                 	<a href="javascript:;" class="inde-guc" onclick="setfocus('${shop.id}','no')">取消关注</a>
	                 <#else>
	                 	<a href="javascript:;" class="inde-guc" onclick="setfocus('${shop.id}','yes')">关注</a>
	                 </#if>
	                 
	                </div>
	                 
                </div>
                <div class="col-xs-12 inde-xinx">
                    <div  class="inde-img">
                    <#if shop.bpict?exists&&shop.bpict?length gt 0>
                    	<img id="shop_bg" src="${imghost}${shop.bpict}">
                    <#else>
                    	<img src="img/top_bg.jpg">
                    </#if>
	                    <div class="inde-nam">
							${shop.name}
	                    </div>
	                    <div class="inde-logo">
							<img src="${imghost}${shop.pict}">
	                    </div>      
                    </div>
                </div>               
                </div>

        <div class="row">	         
                 <div class="col-xs-12 inde-inc">
                   <div class="col-xs-3 inde-incbor">
                    </div>
                    <div class="col-xs-3 inde-incbor">
                    	<a href="${rc.contextPath}/m/shop/goodsList?sid=${shop.id}&op=all">
                            <div class="inde-count">
                                ${totalgoods}
                            </div>
                            <div class="inde-count">
                            全部商品
                            </div>
                        </a> 
                    </div>
                    <div class="col-xs-3 inde-incbor">
                    	<a href="${rc.contextPath}/m/shop/goodsList?sid=${shop.id}&op=new">
                            <div class="inde-count">
                                ${totalnews}
                            </div>
                            <div class="inde-count">
                           上新商品
                            </div>
                        </a>
                    </div>
                    <div class="col-xs-3 inde-incbor1 ">
                    	<a href="${rc.contextPath}/m/order/start?sid=${shop.id}">
                            <div class="inde-count">
                                ${ordercount}
                            </div>
                            <div class="inde-count">
                            我的订单
                            </div>
                        </a>
                    </div>
                   
              </div>
                    
         
        </div>   
        
        
         
         
          <div class="row inde-posc">	         
                 <div class="col-xs-12 " id="shop_list">
                  <#list goods as p>
                   <a href="${rc.contextPath}/m/goods/start?id=${p.id}&op=show">
                   <div class="col-xs-6">
                        <div class=" col inde-sa-img">
                            <img src="${imghost}${p.mainPict}" >
                        </div>
                        <div class=" inde-indc">
                        	<span class=" inde-sa-indec">
                          		 ${p.name}
                            </span>
                            <span class=" col-xs-12 inde-sa-pric">
                          	￥ ${p.price?string('0.00')} <del style="color:#ccc;font-size:12px;font-weight:normal">￥${p.oprice?string('0.00')}</del>
                            </span>
                        </div>
                    </div>
                    </a>
                    </#list>
         	</div>   
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
          
     <div id="guanzhu_dialog" onclick="close_dialog()" style="background:#000;position:fixed;z-index:2000;top:0;opacity:0;display:none">
   <div id="gz_dialog" style="position:relative;top:-150px">
   	<div style="color:#fff;text-align:right;font-size:20px;display:block;padding-right:20px">
   		X
   	</div>
   
      <img src="${rc.contextPath}/m/img/tisi.png"/>
   </div>
 </div>
</body>
<script>
      	var ScreenWidth=$('#container').outerWidth();
	      var sW=$(window).outerWidth();
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
				$('#guanzhu_dialog').width(sW);
				$('#guanzhu_dialog').height(ScreenHeight);
			})     
      </script>
</html>
</#escape> 