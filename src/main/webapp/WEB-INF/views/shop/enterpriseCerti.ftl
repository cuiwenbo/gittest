<#escape x as (x)!> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>店铺认证</title>
		<script src="${rc.contextPath}/js/jquery-1.8.2.js" type="text/javascript" ></script>
		<script type="text/javascript" src="${rc.contextPath}/js/add_pic.js"></script>
		
		
		<script language="javascript">

		 function addPict() {
				var message= $("#uploadDiv").html();		
			     message = message.replace(/__PRE__/g, '');
					
				var title = "上传相关信息的图片";
				add_pic({title:title, text:message, height:125, width:430});
						
		    }
		    
		    function addPict1() {
				var message= $("#uploadDiv1").html();		
				message = message.replace(/__PRE__/g, '');
					
				var title = "上传相关信息的图片";
				add_pic({title:title, text:message, height:125, width:430});
						
		    }
		    
		     function addPict2() {
				var message= $("#uploadDiv2").html();		
				message = message.replace(/__PRE__/g, '');
					
				var title = "上传相关信息的图片";
				add_pic({title:title, text:message, height:125, width:430});
						
		    }
		function AfterOperate(param) {	
			if(param.op == "addpict") return setPict(param);
			if(param.op == "addpict1") return setPict1(param);
			if(param.op == "addpict2") return setPict2(param);
			
		}
			
			var picts = new Array();
   
    	function setPict(p) {
        	var s = p.data.split(",");
    		var idx = picts.length;
    		picts[idx] = s[1] + s[2];
    		var pict = s[3] + s[1] + s[2]; 
    		
    		$('#detail_pic').attr('src',pict);
    		$('#a1').attr('href',pict);
			$("#pict").val(s[1] + s[2]);
			closeMessage();    
		
    	}
    	function setPict1(p) {
        	var s = p.data.split(",");
    		var idx = picts.length;
    		picts[idx] = s[2];
    		var pict = s[1] + s[2];
    		$('#detail_pic1').attr('src',pict);  
    		$('#a2').attr('href',pict); 	
			$("#pict").val($("#pict").val()+","+s[2]);
			closeMessage();    
		
    	}
    	function setPict2(p) {
        	var s = p.data.split(",");
    		var idx = picts.length;
    		picts[idx] = s[2];
    		var pict = s[1] + s[2];
    		$('#detail_pic2').attr('src',pict);
    		$('#a3').attr('href',pict);    	
			$("#pict").val($("#pict").val()+","+s[2]);
			closeMessage();    
		
    	}
    
	function shopaudit() {
			$.ajax({
				type: "POST",
				data: {sid:${shop.id},picts:$("#pict").val(),type:$("#type1").val()},
				url:"shop/shopAudit",
				dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						alert(ret.message);
						window.location = "home";
						return ;
					} else {
						alert(ret.message);
					}
				}
			});
}

		</script>
	</head>		
<body>	
	<div  style="color:red;">${shop.name}-提交店铺企业认证认证资料</div>
	<div>		  
		<a href="shop/get?sid=${shop.id}&op=toauth">个人认证（-适合个人认证者，材料简单）</a> | <a href="#">企业认证（-适合企业、公司进行认证）</a>
	</div>
	<div>
	<form name="updateFrm" action="" method="post" target="hidefrm">
	 	<input type="hidden" name="picts" value="" id="pict">
	 	<input type="hidden" name="type" value="1" id="type1">
		<table border="0" cellpadding="0" cellspacing="0" border="1" >
					
					<tr>
				    	<td>营业执照:</td>
				        <td>
				        	<input class="btn" type="button" value="上传营业执照（副本）图片" onclick="addPict()"><br/>
				        	<a href="" target='_blank' id="a1">  
				        	<img width="300px" height="260px" src="${imghost}${u.pict}" id="detail_pic">
							</a>
				        </td>
					</tr>	
					<tr>
						<td>法人身份证:</td>
						<td>
							<input class="btn" type="button" value="上传法人身份证图（正面）" onclick="addPict1()"><br/>
							<a href="" target='_blank' id="a2">  
				        	 <img width="300px" height="260px" src="${imghost}${u.pict}" id="detail_pic1">
							</a>
						</td>
					</tr>
					
					<tr>
						<td></td>
						<td>
							<input class="btn" type="button" value="上传法人身份证图（反面）" onclick="addPict2()"><br/>
							<a href="" target='_blank' id="a3"> 
				        	 <img width="300px" height="260px" src="${imghost}${u.pict}" id="detail_pic2">
				        	</a>
						</td>
					</tr>
		</table>
			<input type="button" name="btn1" value="提交认证"  onclick="shopaudit();"/>
		</form>
	</div>	
		
	<div style=" display:none;">
	  	<div id="uploadDiv">
			<div class="pop_sub" style="margin:20px;">
	    		<form name="mainfrm" action="upload" method="post" enctype="multipart/form-data" target="hidefrm">
					<input type="hidden" name="op" value="addpict"/>
					<input type="file" name="upload" value="选择文件"><br>
					<br>
					<center>
					<input type="submit" value=" 上传 ..."  id="__PRE__Upload">
					</center>
				</form>
			</div>
     	</div>    
	</div>	
	
	
	<div style=" display:none;">
	  	<div id="uploadDiv1">
			<div class="pop_sub" style="margin:20px;">
	    		<form name="mainfrm" action="upload" method="post" enctype="multipart/form-data" target="hidefrm">
					<input type="hidden" name="op" value="addpict1"/>
					<input type="file" name="upload" value="选择文件"><br>
					<br>
					<center>
					<input type="submit" value=" 上传 ..."  id="__PRE__Upload">
					</center>
				</form>
			</div>
     	</div>    
	</div>	
	
	<div style=" display:none;">
	  	<div id="uploadDiv2">
			<div class="pop_sub" style="margin:20px;">
	    		<form name="mainfrm" action="upload" method="post" enctype="multipart/form-data" target="hidefrm">
					<input type="hidden" name="op" value="addpict2"/>
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
