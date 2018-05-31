<#escape x as (x)!> 
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link href="${rc.contextPath}/m/css/bootstrap.css" rel="stylesheet" >
<link href="${rc.contextPath}/m/css/nstyle.css" rel="stylesheet" >
<link href="${rc.contextPath}/m/css/pager.css" rel="stylesheet" >

<script type="text/javascript" src="${rc.contextPath}/js/pager.js"></script>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<style>
	a:hover{text-decoration:none}
a:focus{text-decoration:none}
a:visited{text-decoration:none}
a:active{text-decoration:none}
.col-xs-6{margin-top:5px}
.inde-sa-indec{
	height:40px;
	overflow:hidden;
	display:block
}
.inde-sa-img img{width:100%}
</style>
<script>
	var pageStr = createPageBar3(${page.pageNumber},${page.pageSize},${page.pageIndex},${page.total},"javascript:dosearch",5);
	function dosearch(p1, p2) {
		var frm = document.searchfrm;
		frm.pageIndex.value = p1;
		frm.pageSize.value = p2;
		frm.submit();
	}
	$(function(){
		var shoplist = $('#shop_list').find('.col-xs-6')
		for(var i=0;i<shoplist.length;i++){
			if(i%2==0){
				$(shoplist[i]).addClass('inde-ipc');
			}else{
				$(shoplist[i]).addClass('nlist-ipp');
			}
		}
		var shop_pic = $('#shop_list').find('img');
		for(var i=0;i<shop_pic.length;i++){
			$(shop_pic[i]).height(function(){
				return $(this).width();
			})
		}
	})
</script>
<title>微袋</title>

<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>

<body style="background:#f7f7f7;">
<form name="searchfrm" action="${rc.contextPath}/m/goods/visitList" method="get" >
	<input type="hidden" name="pageIndex" value="${page.pageIndex!'1'}">
	<input type="hidden" name="pageSize" value="${page.pageSize!'20'}">
</form>
<div class="container">
 <div class="row">	
      	 <div class=" col-xs-12 nlist-top">
			 <a href="javascript:;" class="inde-gua" onclick="window.history.go(-1)">ㄑ返回</a>
			 <div style="line-height:40px;text-align:center;padding-right:68px">浏览记录</div>
         </div>
      </div>   
</div>

<div class="container">
        <div id="myTabContent" class="tab-content">
           <div class="tab-pane active">
             	<div class="container">
                	<div class="row nlist-body">
                    	<div class="col-xs-12" id="shop_list">
                        	 <!--商品-->  
 							<#if page?? && page.elements??>
   							<#list page.elements as v>
                        	 <a href="${rc.contextPath}/m/goods/start?id=${v.goodsid}&op=show">
                        	 <div class="col-xs-6">
		                        <div class=" col inde-sa-img">
		                            <img src="${imghost}${v.mainPict}">
		                        </div>
		                        <div class="inde-indc">
		                        	<span class=" inde-sa-indec">
		                          		${v.gname}	
		                            </span>
		                            <span class=" col-xs-12 inde-sa-pric">
		                          		￥${v.price?string('0.00')}
		                            </span>
		                        </div>
                   			 </div>
                   			 </a> 
                   			 </#list>
     						 </#if>  	
                        </div>
                    </div>
                </div>
             
           </div>
         
           
         
     </div> 
  </div>           
</div>
<div class="row">
<div class="cutpage" id="page">
   	     	<script language="javascript">
				document.write(pageStr);
	       	</script>
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
     
  <div id="guanzhu_dialog" onclick="close_dialog()" style="background:#000;position:fixed;z-index:2000;top:0;opacity:0;display:none">
   <div id="gz_dialog" style="position:relative;top:-150px">
   	<div style="color:#fff;text-align:right;font-size:20px;display:block;padding-right:20px">
   		X
   	</div>
   <img src="${rc.contextPath}/m/img/tisi.png"/>
   </div>
 </div>   
     
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
</body>
</html>

</#escape> 