//弹出蒙板层显示弹框
var mask = {
        title:null,
        show:function(id){
			$j("#mask").remove();
			$j("body").append("<div id='mask'></div>");
			resizeMask();
			var iframeDiv = "<iframe id=\"alertIframe\" style=\"position:absolute; z-dex:20;width:100%;height:100%;_filter:alpha(opacity=0);opacity=0;border-style:none;\"></iframe>";
			setTimeout(function(){
				$j("#mask").show();
				$j(iframeDiv).appendTo("#mask");
				$j("#"+id).show();
				moveit(id);
			},0);
			
        },
        hide:function(id){
			$j("#mask").hide().remove();
			$j("#alertIframe").remove();
			$j("#"+id).hide();
        }
}
//调整mask遮罩的高度
function resizeMask(){
	var maskHeight=$j("body").height();
	if (maskHeight<document.documentElement.clientHeight)
	{
		maskHeight = document.documentElement.clientHeight;
	}
	$j("#mask").height(maskHeight);
}

//调整弹框屏幕居中位置
function moveit(id)
{
	var marginleft = -($j("#"+id).width()/2);
	var marginheight = -($j("#"+id).height()/2);
	var top = ($j(window).height())/2 + $j(window).scrollTop();
	var left = ($j(window).width())/2 + $j(window).scrollLeft();
	$j("#"+id).css({
		'margin-left': marginleft,
		'margin-top': marginheight,
		'top': top,
		'left': left
	});
}