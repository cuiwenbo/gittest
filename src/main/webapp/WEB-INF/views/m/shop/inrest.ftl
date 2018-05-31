<#escape x as (x)!> 
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/pager.js"></script>
<link href="${rc.contextPath}/m/css/pager.css" rel="stylesheet" >
<link href="${rc.contextPath}/m/css/nstyle.css" rel="stylesheet" >
<link href="${rc.contextPath}/m/css/bootstrap.css" rel="stylesheet" >
<style>
body{font-family:'微软雅黑'}
</style>
<script>
	var pageStr = createPageBar3(${page.pageNumber},${page.pageSize},${page.pageIndex},${page.total},"javascript:dosearch",5);
	function dosearch(p1, p2) {
		var frm = document.searchfrm;
		frm.pageIndex.value = p1;
		frm.pageSize.value = p2;
		frm.submit();
	}
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
					alert('取消成功')
					window.location.reload()
					
				} 
				else if('1001'==ret.result){
					window.location='${rc.contextPath}/m/login.html';
				}
				else {
					alert(ret.message);
				}
			}
		});	
	}
	
	
	
</script>
<title>用户关注列表</title>

<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>

<body>
<form action="${rc.contextPath}/m/shop/intrest" method="get" name="searchfrm">
    	<input type="hidden" name="pageIndex" value="${page.pageIndex!'1'}">
		<input type="hidden" name="pageSize" value="${page.pageSize!'20'}">
</form>
		<div class=" col-xs-12 inde-top">
                <a href="javascript:;" onclick="window.history.go(-1)" style="color:#999;text-decoration:none">ㄑ返回</a>
	                 
                </div>	 
	 
	 <#if page?? && page.elements??>
     <#list page.elements as intre>
	
	<div class="col-xs-12 coll-ihh">
    <a href="${rc.contextPath}/m/shop/goodsList?id=${intre.sid}">               		<div class="col-xs-3 ode-cls">
                        	<img src="${imghost}${intre.pict}" style="width:80px;height:80px">
                        </div>
 					 <div class="col-xs-9 coll-iyu">
                        	<span class="coll-ivv">${intre.name}</span>
							<span class="coll-ivv">掌柜:${intre.uname}</span>
							<span class="coll-ivv">${intre.province}-${intre.city}-${intre.district}</span>
                            
                        </div>
      </a>              <div class="col-xs-9 coll-iyu"><span class="coll-iuc" onclick="setfocus('${intre.sid}','no')">取消关注</span>
	  </div></div>
	  
      </#list>
      </#if>
     

 	<div  style="height:40px;line-height:20px;">		
		<div class="col-xs-12" style="margin-bottom:25px;font-size:12px;">
		<script language="javascript">
			document.write(pageStr);
		</script>
		</div>		
	</div>      

</body>
</html>

</#escape> 