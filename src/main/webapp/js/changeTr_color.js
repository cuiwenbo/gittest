//鼠标悬停表格行变化背景颜色
		$(document).ready(function(){
			$("tr").mouseover(function(){
					$(this).css("background-color","#FEF3D1");
				}).mouseout(function(){
					$(this).css("background-color","white");
				})
		})