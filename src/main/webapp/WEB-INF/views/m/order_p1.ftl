<#escape x as (x)!> 

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link href="${rc.contextPath}/m/css/style.css" rel="stylesheet" >
<link href="${rc.contextPath}/m/css/bootstrap.css" rel="stylesheet" >
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="${rc.contextPath}/m/js/bootstrap.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/cityData.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/city.js"></script>
<title>微袋</title>
<style>
.row {margin:0;padding:0}
.container{padding:0}
.col-xs-2{float:left;width:25%}
.car2-pic img{min-width:80px;max-width:80px;height:80px}
.car-ind{width:75%;padding-left:40px}
.param_no_select{}
.param_select {color:red;font-weight:bold;}
</style>
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<script>
function AddOrder() {
	
	var city = city_getSelectValue('province', 'city', 'district');
	if(city.province.length == 0) {
		alert("请选择您所在的省份");
		return;
	}	
	if(city.city.length == 0) {
		alert("请选择您所在的县市");
		return;
	}
	if(city.district.length == 0) {
		alert("请选择您所在的地区");
		return;
	}
	$("#_province").val(city.province);
	$("#_city").val(city.city);
	$("#_district").val(city.district);
	var address = $.trim($("#address").val());
	if(address.length == 0) {
		alert("请输入详细地址");
		return;
	}
	$("#_address").val(address);
	var name = $.trim($("#receiver").val());
	if(name.length == 0) {
		alert("请输入收件人姓名");
		return;
	}
	$("#_name").val(name);
	var phone = $.trim($("#phone").val());
	if(phone.length == 0) {
		alert("请输入收件人的联系电话");
		return;
	}
	$("#_phone").val(phone);
	var remarks=$('#remarks').val()
	$("#_remarks").val(remarks);
	document.mainfrm.submit();
}
function AfterOperate(param) {
		if(param.op !="CreateOrder")  return;
		if(param.result=='0000'){
	    		window.location.href="order.jhtml";
	    		return;
	    	}else{
	    		alert(param.message);
	    	}			
}

	var selectparams = [];
	
	<#list 0..2 as p_idx>
	<#if (goods.pn[p_idx]?? && goods.pn[p_idx]?length>0)>
	selectparams[selectparams.length] = {"p1":"${p_idx}", "p2":"", "p3":"${goods.pn[p_idx]}", "p4":""};
	</#if>	
	</#list>
	function selectParam() {		
		var p1 = $(this).attr("p1");
		var p2 = $(this).attr("p2");
		var p3 = $(this).attr("p3");
		var p4 = $(this).attr("p4");		
		var curv = {};
		var cidx = -1;
		for(var idx = 0; idx < selectparams.length; idx ++) {
			var pitem = selectparams[idx];
			if(pitem.p1 == p1) {
				curv = pitem;
				cidx = idx;
				break;
			}
		}
		if(cidx >= 0) {
			if(curv.p2 == p2) return;
		}
		$(this).addClass("param_select");
		$(this).removeClass("param_no_select");
				
		if(cidx >= 0) {
			var c = ".selectparam[p1='" + p1 + "'][p2='" + curv.p2 + "']";			
			var o = $(c);
			o.addClass("param_no_select");
			o.removeClass("param_select");
		}
		if(cidx >= 0) {
			selectparams[cidx] = {"p1":p1, "p2":p2, "p3":p3, "p4":p4};			
		} else {
			selectparams[selectparams.length] = {"p1":p1, "p2":p2, "p3":p3, "p4":p4};
		}
	}
	function DescAmount() {
		var amount = parseInt($("#in_amount").val());
		if(isNaN(amount)) amount = 1;
		amount --;
		if(amount < 1) amount = 1;
		$("#in_amount").val(amount);
	}
	function IncAmount() {
		var amount = parseInt($("#in_amount").val());
		if(isNaN(amount)) amount = 1;
		amount ++;
		if(amount < 1) amount = 1;
		$("#in_amount").val(amount);
	}	

	var parareq= "";
	function CheckData() {
		var city = city_getSelectValue('province', 'city', 'district');
		if(city.province.length == 0) {
			alert("请选择您所在的省份");
			return;
		}	
		if(city.city.length == 0) {
			alert("请选择您所在的县市");
			return;
		}
		if(city.district.length == 0) {
			alert("请选择您所在的地区");
			return;
		}
		$("#_province").val(city.province);
		$("#_city").val(city.city);
		$("#_district").val(city.district);
		var address = $.trim($("#address").val());
		if(address.length == 0) {
			alert("请输入详细地址");
			return;
		}
		$("#_address").val(address);
		var name = $.trim($("#receiver").val());
		if(name.length == 0) {
			alert("请输入收件人姓名");
			return;
		}
		$("#_name").val(name);
		var phone = $.trim($("#phone").val());
		if(phone.length == 0) {
			alert("请输入收件人的联系电话");
			return;
		}
		$("#_phone").val(phone);
		
		parareq = "";
		for(var i = 0; i < selectparams.length; i ++) {
			var item = selectparams[i];
			if(item.p4.length == 0) {
				alert("请选择" + item.p3);
				return false;
			}
			parareq += item.p3 + ":" + item.p4 + ","
		}
		var amount = parseInt($("#in_amount").val());
		if(isNaN(amount)) amount = 0;
		if(amount < 1) {
			alert("数量不能小于1");
			return false;
		}
		
		return true;
	}
	function addOrder() {
		if(!CheckData()) return;
		var param = "id=${goods.id}";
		var amount = parseInt($("#in_amount").val());
		var remarks=$.trim($("#remarks").val());
		var address = $.trim($("#address").val());
		var name = $.trim($("#receiver").val());
		var phone = $.trim($("#phone").val());
		var province=$("#_province").val();
		var city=$("#_city").val();
		var district=$("#_district").val();
		param += "&province="+province;
		param += "&city="+city;
		param += "&district="+district;
		param += "&address=" + address;
		param += "&name=" + name;
		param += "&phone=" + phone;
		param += "&remarks=" + remarks;
		param += "&param=" + parareq;
		param += "&amount=" + amount;
		var url = "${rc.contextPath}/m/order/pre";
		$.ajax({
				type: "POST",data: param,url: url,dataType: "json",
				success: function(ret){
					if('0000' == ret.result) {
						alert("购买成功！");
						var r = "${rc.contextPath}/m/order/start";
						window.location.href=r;
					} else if("1000" == ret.result) {
						alert(ret.message);
						window.location.href="${rc.contextPath}/m/login.html";
					} else {
						alert(ret.message);
					}
				}
				});
					
	}
</script>
</head>

<body style=" background:#f9f9f9;margin-bottom:50px">
<div style="display:none">
<form id="mainfrm" name="mainfrm" target="hidefrm" method="post" action="${rc.contextPath}/m/order/pre?id=${goods.id}">
<input type="hidden" name="id" value="${goods.id}">
<input type="hidden" name="paytype" value="线下支付">
<input type="hidden" name="province" id="_province">
<input type="hidden" name="city" id="_city">
<input type="hidden" name="district" id="_district">
<input type="hidden" name="address" id="_address">
<input type="hidden" name="name" id="_name">
<input type="hidden" name="phone" id="_phone">
<input type="hidden" name="remarks" id="_remarks">
</form>
<iframe name="hidefrm"></iframe>
</div>


	<div class="container"> 
      <div class="col-xs-12 car2-exp" style="padding-bottom:10px;border-color:#e5e5e5">
      	店铺：${shop.name}
      </div>
     <div class="row ">
    	<div class="col-xs-12 car-box1" style="min-height:110px;border-color:#e5e5e5">
        	<div class="col-xs-2">
                <div class="car2-pic" style="margin-left:10px">
                	<img src="${imghost}${goods.mainPict}"/>
                </div>
            </div>
            <div class="col-xs-10 car-ind">
            	<div class="car-dic1">${goods.name}</div>
				<div class="car-dic4"><span>￥${goods.price?string('0.00')}</span></div>
	    	</div>
       	  </div>
    	
    	
	</div>
     <div class="row " style="padding-bottom:20px;border-color:#e5e5e5;background:#fff">
    	<div class="col-xs-12 det-iuc" style="border-bottom: none;">
		    <#list 0..2 as p_idx>
			<#if (goods.pn[p_idx]?? && goods.pn[p_idx]?length>0)>
				  <div class="det-ifa">
				  ${goods.pn[p_idx]}
		         <div class="with-parameter">
		         	<#list goods.pva[p_idx] as pva>
		         		<label class="btn btn-default" style="padding:0">
		         			<div  class="selectparam param_no_select" style="padding:6px 10px" p1="${p_idx}" p2="${pva_index}" p3="${goods.pn[p_idx]}" p4="${pva}" style="max-width: 152px;overflow: hidden;white-space: normal;">
		         				${pva}
		         			</div>
		         		</label>
		         	</#list>
		         	<script>						
						$(".selectparam").click(selectParam);						
					</script>
		         	
		         </div>
		        </div>
			</#if>
			</#list>
	
	
        <div class=" det-ity">
        	数量：<span style="color:#999"><#if goods.userlimit==0>（不限购）<#else>（限购${goods.userlimit}）</#if></span>
         	<div class="input-group det-post">
               <span class="input-group-btn">
                  <button class="btn btn-default" type="button" onclick="DescAmount();changePrice()">
                     -
                  </button>
               </span>
               		<input type="text" class="form-control with-text" id="in_amount" " style="width:125px" placeholder="1" value="1" onkeyup="changePrice()">
              <span class="input-group-btn"> 
                  <button class="btn btn-default" type="button" onclick="IncAmount();changePrice()">
                     +
                  </button>
               </span>
            </div>
            <div id="countError" style="width:200px;color:red;padding-top:10px;padding-left:10px"></div>
            </div> 
         </div> 
        
</div> 
		 
         <div class="with-par1" style="border-color:#e5e5e5">
			<div>剩余：${goods.amount?string('0')}</div>
			<div>运费：${goods.deliveryfee?string('0.00')}</div>
         </div>
         <div class="col-xs-12 car2-exp" style="border-color:#e5e5e5">
          	配送方式 :
          	<span>
          		<#if goods.deliverymethod=='0'>
          			快递
          			<#if goods.deliveryfee!=0>
          				￥${goods.deliveryfee?string("0.00")}
          			<#else>
          				免邮
          			</#if>
          		<#else>
          			上门提货
          		</#if>
          	</span>
          </div>
          <div class="col-xs-12 car2-exp" style="border-color:#e5e5e5">
          	支付方式 : <span>${goods.paytype}</span>
          </div>          
          <div class="col-xs-12 car2-exp" style="border-color:#e5e5e5;padding-bottom:10px">
          	给卖家留言:<span><input type="text" id="remarks" name="remarks"></span>
          </div>
         
         </div> 
         
                
      	 <div class="container">
 <div class="row" >
 

     
 	<div class="col-xs-12 nav-car"style="padding-left: 0;padding-right: 0;position: fixed;">
      <div class="col-xs-6 nav-fic"  style="width:66%;padding-right:0;margin-left:5px">
      	共<span id="allAmount">1</span>件 合计<span id="allPrice">￥${(goods.price+goods.deliveryfee)?string("0.00")}</span>
      </div>
      <a  href="javascript:;" onclick="addOrder()">
      	<div class="col-xs-6 nvt" data-target="#myModal"  data-toggle="modal" style="width:32%;padding:0">
      		提交订单
      	</div>
      </a>
    </div>
                
 </div>  
 <div class="row">	
    	<div class="car2-name">收货地址</div>
    	<div class=" col-xs-12 car2-addr">
    		<span class="col-xs-4" style="padding:5px">
	     	<select id="province" onchange="prov_change('province','city')"  class="form-control" style="padding:6px">
	     		<option value="0">选择省份</option>
	     	</select>
	     	</span>
	     	<span class="col-xs-4" style="padding:5px">
	     	<select id="city" onchange="city_change('city','district')" class="form-control" style="padding:6px">
	     		<option value="0">选择城市</option>
	     	</select>
	     	</span>
	     	<span class="col-xs-4" style="padding:5px">
	     	<select id="district" class="form-control" style="padding:6px">
	     		<option value="0">选择地区</option>
	     	</select>
	     	</span>
	     	</div>
	     	<div class=" col-xs-12">
	     		<div class="input-group car-pos" style="margin-left:5px;margin-right:5px">
	     			<span class="input-group-addon">
	     			详细地址:</span>
	     			<input class="form-control" type="text" id="address" value="${uu.address}">
	     			
	     		</div>
			</div>  	
        	<div class=" col-xs-12">
        		<div class="input-group car-pos" style="margin-left:5px;margin-right:5px">
	     			<span class="input-group-addon">
        				&nbsp;联 系 人:
        			</span>
        			<input class="form-control" type="text" id="receiver" value="${uu.receiver}">
        		</div>
        	</div>
        	<div class=" col-xs-12">
        		<div class="input-group car-pos" style="margin-left:5px;margin-right:5px">
	     			<span class="input-group-addon">
        				联系电话:
        			</span>
        			<input class="form-control" type="text" id="phone" value="${uu.phone}">
        		</div>
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
  	
  	
  	
 <script>
city_init('province', 'city', 'district',"${uu.province}","${uu.city}","${uu.district}");

function changePrice(){
	var count=$('#in_amount').val();
	if(isNaN(count)) count=1.00;$('#in_amount').val(count);
	if(count<1) count=1.00;$('#in_amount').val(count);
	var bigcount;
	if(${goods.userlimit} > 0){
		${goods.userlimit}>${goods.amount}?bigcount=${goods.amount}:bigcount=${goods.userlimit};
	}else{
		bigcount=${goods.amount}
	}
		if(count > bigcount){
			count = bigcount;
			$('#in_amount').val(count);
			$('#countError').html('超过限购数目或剩余数目');
		}else{
			$('#countError').html('');
		}	
	allprice=(${goods.price}*count+${goods.deliveryfee}).toFixed(2);
	$('#allAmount').html(count)
	$('#allPrice').html('￥'+allprice)
}
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