<script src="${base}/js/jquery-1.8.2.js"></script>
<script src="${base}/js/header.js"></script>
<div style="width:100%;background-color:#aaa1aa;border:1px solid red;height:50px">
<#if LoginUser??>
${LoginUser.nick}
<#else>
未登录
</#if>
<br>
<a href="${base}/home.jhtml">首页</a>|
<a href="${base}/tuan/order.jhtml">我的拼单</a>|
<a href="${base}/tuan/order!pre.jhtml">发起拼单</a>|
<a href="${base}/tuan/order!his.jhtml">历史拼单</a>|
<a href="${base}/mer/goods.jhtml">优选商品</a>

</div>