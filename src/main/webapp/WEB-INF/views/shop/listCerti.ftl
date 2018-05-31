<#escape x as (x)!> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>查看认证资料</title>
		<script src="${rc.contextPath}/js/jquery-1.8.2.js" type="text/javascript" ></script>
		<script type="text/javascript" src="${rc.contextPath}/js/add_pic.js"></script>
		
		
		<script language="javascript">

		</script>
	</head>		
<body>	
	<div  style="color:red;">-店铺<#if audit.type==0>个人<#else>企业</#if>认证资料</div>
	
	<div>
	 	<input type="hidden" name="picts" value="" id="pict">
	 	<#if audit.type==0>
	 		<div class="license_ident">
			 	<div class="ident_title">个人身份证图:</div>
			 	<div class="ident_pic" style="width:800px"><img src="${imghost}${audit.pict}"  style="width:300px;height:200px;" name="img1"/></div>
				<div style="margin-top:25px;"><label>身份证号码：</label>${audit.idcn}<span class="error"></span></div>
	  			<div style="margin-top:25px;margin-bottom:25px;"><label>姓名：</label>${audit.username}<span class="error"></span></div>
			</div>
	 	</#if>
	 	
	 	<#if audit.type==1>
	 		<script>
				var picts = new Array(); 
		    	picts = "${audit.pict}".split(","); 
		    	var pict = '${imghost}'+picts[0];
		    	var pict1 = '${imghost}'+picts[1];
		    	var pict2 = '${imghost}'+picts[2];
		    	if("${audit.pict}".length==0){
		    		pict='${rc.contextPath}/images/no-pic.png'
		    	}   		
	    		var m = "营业执照:<a href='"+pict+"' target='_blank'><img style='width:300px;height:200px;' src='"+pict+"' /></a></br>";
	    		m+="法人身份证:正面<a href='"+pict+"' target='_blank'><img style='width:300px;height:200px;' src='"+pict1+"' /></a></br>"
	    		m+="反面<a href='"+pict+"' target='_blank'><img style='width:300px;height:200px;' src='"+pict2+"' /></a></br>"
				document.write(m)
			</script>
	 	</#if>
	
	</div>	
</body>
</html>
</#escape> 
