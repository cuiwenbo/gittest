<#escape x as (x)!> 

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${rc.contextPath}/m/css/nstyle.css" rel="stylesheet" >
<link href="${rc.contextPath}/m/css/bootstrap.css" rel="stylesheet" >
   <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
   <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>

<title>微袋首页</title>
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body style="background:#f5f5f5;">
	<div class="container">
    	<div class="row">
             <div class="col-xs-12 str-tit">
            	最新入驻店铺
             </div>
             <div class="col-xs-12 ">
            	<div class="col-xs-1">
                </div>
                <div class="col-xs-10 str-bor">
                </div>
                <div class="col-xs-1">
                </div>
        </div>
    </div>
 </div>   
    <div class="container">
    	<div class="row">
        	<div class="col-xs-12">
        		<div class="col-xs-1">
                </div>
                <div class="col-xs-10" style="text-align:center;">
                	<div class="col-xs-12">
                	   <#if page?? && page.elements??>
        <#list page.elements as shop>
        
        <div class="col-xs-4" >
        <a href="${rc.contextPath}/m/shop/start?id=${shop.id}">
                          <div class="col-xs-11 str-box">
                        	<div>
                              <img src="${imghost}${shop.pict}">
                            </div>
                            <div class="str-nam">
                            ${shop.name}
                            </div>
                          </div>  
	</a>                          
        </div>
        </#list>
        </#if>
        
        
                    </div>
                </div>
                <div class="col-xs-1">
                </div>
            </div>
        </div>
    </div>
    <div class="container">
    	<div class="row">
        	<div class="col-xs-12 str-block">
            </div>
        </div>
    </div>

</body>

</html>


</#escape> 