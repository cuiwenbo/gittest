<#escape x as (x)!> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>店铺认证</title>
		<link href="css/login.css" type="text/css" rel="stylesheet" />
		<script src="js/jquery-1.8.2.js" type="text/javascript" ></script>
		<script type="text/javascript" src="js/add_pic.js"></script>
		
		  
		<script language="javascript">

		 function addPict(picid) {
				if(picid==1){
					$('#op').val('addpict1');
					
				}
				if(picid==2){
					$('#op').val('addpict2');
					
				}
				if(picid==3){
					$('#op').val('addpict3');
					
				}
				if(picid==4){
					$('#op').val('addpict4');
					
				}
				var message= $("#uploadDiv").html();		
				message = message.replace(/__PRE__/g, '');
				
				var title = "上传相关信息的图片";
				add_pic({title:title, text:message, height:125, width:430});
						
		    }
		
		function AfterOperate(param) {	
			if(param.op == "addpict1") return setPict(param,1);
			if(param.op == "addpict2") return setPict(param,2);
			if(param.op == "addpict3") return setPict(param,3);
			if(param.op == "addpict4") return setPict(param,4);
			if(param.op == "addshop") return afterSave(param);
		}
			
		var picts = new Array();
    	var numb2,numb3,numb4;
    	
    	function setPict(p,num) {
    	if(p.result=='1000'){
    		alert(p.message);
  			return ;
    	}
        	var s = p.data.split(",");
    		var idx = picts.length;
    		picts[idx] = s[1] + s[2];
    		var pict = s[3] + s[1] + s[2];
    		$('#pic'+num).hide();
    		if($('#picture'+num).height()){
    			$('#picture'+num).attr('src',pict)
    		}else{
    			$('<img src="'+pict+'" id="picture'+num+'" width="84" height="84" style="vertical-align:bottom;border:none"> <a href="javascript:;" onclick="addPict('+num+')">修改</a>').insertAfter('#pic'+num)
    		}
    		if(num==1){
    			$("#pict").val(s[1] + s[2]);
    		}else if(num==2){
    			numb2=s[1] + s[2];
    			$("#pict1").val(numb2+","+numb3+","+numb4);
    		}else if(num==3){
    			numb3=s[1] + s[2];
    			$("#pict1").val(numb2+","+numb3+","+numb4);
    		}else if(num==4){
    			numb4=s[1] + s[2];
    			$("#pict1").val(numb2+","+numb3+","+numb4);
    		}
    		closeMessage();    
		
    	}
    	function afterSave(param) {
    		if(param.result == '0000') {
    			alert("添加成功");
    			window.location.reload();
    			return;
    		} else {
    			alert("失败:" + param.message);
    			return ;
    		}
    	}
	function shopaudit() {
			if(person_card()&&person_code()&&person_name()){
				$.ajax({
					type: "POST",
					data: {sid:${shop.id},picts:$("#pict").val(),idcn:$("#idcn").val(),type:$("#type").val(),username:$("#uname").val()},
					url:"${rc.contextPath}/shop/shopAudit",
					dataType: "json",
					success: function(ret){
						if('0000' == ret.result) {
							alert(ret.message);
							window.location = "${rc.contextPath}/shop/toSuccPage?sid=${shop.id}";
						} else {
							alert(ret.message);
						}
					}
				});
			}
	}
	function shopaudit1() {
			if(company_card()&&company_card1()&&company_card2()){
				$.ajax({
					type: "POST",
					data: {sid:${shop.id},picts:$("#pict1").val(),type:$("#type1").val()},
					url:"${rc.contextPath}/shop/shopAudit",
					dataType: "json",
					success: function(ret){
						if('0000' == ret.result) {
							alert(ret.message);
							window.location = "${rc.contextPath}/shop/toSuccPage?sid=${shop.id}";
						} else {
							alert(ret.message);
						}
					}
				});
			}
	}
	function showError(obj,msg){
		$(obj).nextAll('.error').html(msg);
		obj.css({'border-color':'#e94946','color':'#e94946'})
	}
	
	function showSuccess(obj){
		$(obj).nextAll('.error').html('');
		obj.css({'border-color':'#cccccc','color':'#333'})
	}
	//判断个人身份证图是否上传
	function person_card(){
		if($('#pic1').css('display')=="none"){
			showSuccess($('#pic1'))
			return true;
		}else{
			showError($('#pic1'),'个人身份证图片未上传')
			return false;
		}	
	}
	
	//判断身份证号码
	function person_code(){
		
		if((/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/).test($.trim($('#idcn').val()))){
			showSuccess($('#idcn'))
			return true;
		}
		else{
			showError($('#idcn'),'身份证号码格式不对');
			return false;
		}
	}
	//判断姓名
	function person_name(){
		if($.trim($('#uname').val())!=""){
			showSuccess($('#uname'))
			return true;
		}
		else{
			showError($('#uname'),'姓名不能为空');
			return false;
		}
	}
	//判断企业营业执照是否上传
	function company_card(){
		if($('#pic2').css('display')=="none"){
			showSuccess($('#pic2'))
			return true;
		}else{
			showError($('#pic2'),'营业执照图片未上传')
			return false;
		}	
	}
	//判断法人身份证正面图是否上传
	function company_card1(){
		if($('#pic3').css('display')=="none"){
			showSuccess($('#pic3'))
			return true;
		}else{
			showError($('#pic3'),'法人身份证正面图片未上传')
			return false;
		}	
	}
	//判断发人身份证反图是否上传
	function company_card2(){
		if($('#pic4').css('display')=="none"){
			showSuccess($('#pic4'))
			return true;
		}else{
			showError($('#pic4'),'法人身份证反面图片未上传')
			return false;
		}	
	}
		
		$(function(){
			$('#person').click(function(){
				$('#company_manner').hide();
				$('#company').css({
					'background':'#fff',
					'border':'1px solid #e5e5e5'
				});
				$('#person_manner').show();
				$('#person').css({
					'background':'#e3fccd',
					'border':'2px solid #8fbc67'
				});
				$('#up_3').css('left','135px');
			})
			$('#company').click(function(){
				$('#person_manner').hide();
				$('#person').css({
					'background':'#fff',
					'border':'1px solid #e5e5e5'
				});
				$('#company_manner').show();
				$('#company').css({
					'background':'#e3fccd',
					'border':'2px solid #8fbc67'
				});
				$('#up_3').css('left','470px');
			})
		})
		</script>
	</head>		
<body style="background:#fff">
	<div class="header">
	<div class="top"> 
    	微袋<span>丨 店铺认证</span>
    </div>
</div>
<div class="main" style="border:none;background:#fafafa">
	<div class="schedule"><img src="images/schedule.png" /></div>
    <div class="main_identstore">
    	<div class="manner_pro">
    		<h1>微袋网提供<span>个人认证</span>、<span>企业认证</span>，请根据实际情况选择认证方式</h1>
    		<p>不同认证方式不影响店铺的任何功能</p>
    		<p>企业认证店铺，店铺收入提现时，审核周期为1个工作日；个人认证店铺，审核周期为3个工作日</p>
    	</div>
    	<div class="manner">
    		<div class="person" id="person">
    			<h1>个人认证</h1>
    			<p>适合个人经营者，材料简单</p>
    		</div>
    		<div class="company" id="company">
    			<h1>企业认证</h1>
    			<p>适合企业、公司进行认证</p>
    		</div>
    	</div>
    	<div class="manner_content">
    		<span class="up_3" id="up_3"></span>
    		<div class="company_manner" id="person_manner">
    			<div class="license_ident">
				 	<div class="ident_title">认证身份证要求</div>
					<div class="request"></div>
				</div>
    			<form  name="updateFrm" action="" method="post" target="hidefrm">
						<input type="hidden" name="picts" value="" id="pict">
				 		<input type="hidden" name="type" value="0" id="type">
				 		<input type="hidden" name="sid" value="${shop.id}" id="s1">
				 <div class="license_ident">
				 	<div class="ident_title">上传个人身份证图</div>
					<div class="ident_pic" style="width:800px"><em>本地图片：</em>
						    <input type="button" class="addpic" id="pic1" onclick="addPict(1)"/>
						    <br/>
						    <span class="tishi">最大上传小于10MB的图片（jpg/png/gif/jpeg/bmp），必须清晰</span><span class="error"></span>
							<div style="margin-top:25px;"><label>身份证号码：</label><input type="text" class="txt" name="idcn" id="idcn"  placeholder="与上传图片中的一致"/><span class="error"></span></div>
				  			<div style="margin-top:25px;margin-bottom:25px;"><label>姓名：</label><input maxlength="40" type="text" class="txt"  id="uname" name="uname" placeholder="身份证上的姓名"/><span class="error"></span></div>
					</div>
					
				</div>
				<input type="button" class="btn" style="margin-left:125px;" name="btn1" value="提交认证"  onclick="shopaudit();"/>
    			</form>
    		</div>
    		
    		<div class="company_manner" id="company_manner" style="display:none;">
			    <form name="updateFrm" action="" method="post" target="hidefrm">
						<input type="hidden" name="picts" value="" id="pict1">
				 		<input type="hidden" name="type" value="1" id="type1">
				 		<input type="hidden" name="sid" value="${shop.id}" id="s2">
				 <div class="license_ident">
				 	<div class="ident_title">企业营业执照认证</div>
					<div class="ident_pic"><em>本地图片：</em>
						    <input type="button" class="addpic" id="pic2" onclick="addPict(2)"/>
						    <br/>
						    <span class="tishi">最大上传小于10MB的图片（jpg/png/gif/jpeg/bmp），必须清晰</span><span class="error"></span>
					</div>
				  <div class="ident_shili">
				  	<span>示例</span>
				  	<div class="shili"></div>
				  </div>
				</div>
				<div class="license_ident" style="width:800px;height:520px;">
				 	<div class="ident_title">法人身份证</div>
				 	<div style="color:red;font-size:13px;margin:10px 0 0 25px;">
				 		身份证上姓名必须与营业执照上‘法定代表人’姓名一致
				 	</div>
					<div class="ident_pic">
							<span style="color:blue;font-size:16px;display:block;margin:0 0 20px 25px">法人身份证正面</span>
							<em>本地图片：</em>
						    <input type="button" class="addpic" id="pic3" onclick="addPict(3)"/>
						    <br/>
						    <span class="tishi">最大上传小于10MB的图片（jpg/png/gif/jpeg/bmp），必须清晰</span><span class="error"></span>
					</div>
				 	<div class="ident_pic">
							<span style="color:blue;font-size:16px;display:block;margin:0 0 20px 25px">法人身份证反面</span>
							<em>本地图片：</em>
						    <input type="button" class="addpic" id="pic4" onclick="addPict(4)"/>
						    <br/>
						    <span class="tishi">最大上传小于10MB的图片（jpg/png/gif/jpeg/bmp），必须清晰</span><span class="error"></span>
					</div>
				</div>  
					<input type="button" class="btn" style="margin-left:100px;" name="btn1" value="提交认证"  onclick="shopaudit1();"/>
				</form>
			</div>
		</div>
	
  </div>
 	<div style="display:none">
		<iframe name="hidefrm"></iframe>
	</div>
</div>
 <div class="footer">
  Copyright © hilinli.com All Rights Reserved.  
  </div>
	







<a href="shop!get.jhtml?sid=${shop.id}&op=tocauth" style="display:none">企业认证（-适合企业、公司进行认证）</a>
<img width="300px" height="260px" src="${imghost}${u.pict}" id="detail_pic" style="display:none">	

	<div style=" display:none;">
	  	<div id="uploadDiv">
			<div class="pop_sub" style="margin:10px;">
	    		<form name="mainfrm" action="upload.jhtml" method="post" enctype="multipart/form-data" target="hidefrm" onsubmit="return checkPic()">
					<input type="hidden" id="op" name="op" value="addpict"/>
					<input type="file" name="upload" id="upload"><br>
					<br>
					<center>
					<input type="submit" value=" 上传   " id="__PRE__Upload">
					</center>
				</form>
			</div>
     	</div>    
	</div>	

  <iframe name="hidefrm" style="display:none"></iframe>
  
</body>
</html>
</#escape> 
