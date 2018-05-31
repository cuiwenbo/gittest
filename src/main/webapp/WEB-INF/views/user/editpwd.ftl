

<script src="${rc.contextPath}/js/jquery-1.8.2.js"></script>
<script type="text/javascript">

	function checkPwd(){
		var p1=$("#p1").val(); 
		var p2=$("#p2").val();
		var p3=$("#p3").val();
		if(p1.length==0){
			alert('原始密码不能为空');
			return false;
		} 
		//showError(p1,'');
		if(p2.length==0){
			alert('新密码不能为空');
			return false;
		} 
		//showError(p2,'');
		if(p2!=p3){
			alert('两次密码输入不一致');
			return false;	
		}
		//showError(p3,'');
		return true;
	} 
	
   function editPwd(){
   		$.ajax({
   			type:"post",
   			data:{passwd:$("#p1").val(),passwdNew:$("#p2").val()},
   			url:"${rc.contextPath}/user/editPwd",
   			dataType:"json",
   			success:function(param){
   				if('0000'==param.result){
   					window.location="${rc.contextPath}/login.html";
   				}
   					alert(param.message);
   			}
   		})
   }

	function AfterOperate(param) {	
		if(param.op =="editinfo")  return afterSave(param);
	}
			
  
   function afterSave(param) {
    		if(param.result = '0000') {
    			alert(param.message);
    			window.location.reload();
    		} else {
    			alert("失败:" + param.message);
    		}
    	}

</script>


<div>
<div style="color:green;">修改用户密码</div><br/><br/>
原始密码 <input type="text" name="passwd" id="p1"/><span id="error" style=" font-size:12px; color:#fe2f24;margin:4px 0 0 5px;"></span><br/>
新密码  <input type="text" name="passwdNew" id="p2" /><span id="error" style=" font-size:12px; color:#fe2f24;margin:4px 0 0 5px;"></span><br/>
确认密码 <input type="text" name="passwdCopy" id="p3"/><span id="error" style=" font-size:12px; color:#fe2f24;margin:4px 0 0 5px;"></span><br/>
<br/>
<input type="button" name="btn1" value="确定修改" onclick="editPwd();checkPwd();"/>
</div>


