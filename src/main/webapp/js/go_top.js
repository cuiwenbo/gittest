$(function(){
	var sTop=$(document).scrollTop();
	if(sTop>500){
			$('#go_top').show()
	}
	var wWidth=$(window).width()
	$('#go_top').css('left',1200+(wWidth-1200)/2);
	
})
		
$(window).scroll(function(){
	var sTop=$(document).scrollTop();	
	if(sTop>500){
		$('#go_top').show()
	}else{
		$('#go_top').hide()
	}
})
$(window).resize(function(){
	var wWidth=$(window).width()
	$('#go_top').css('left',1200+(wWidth-1200)/2);
})
function go_top(){
	$(document).scrollTop(0)
}