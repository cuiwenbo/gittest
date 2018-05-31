
function nextPage(requesturl,pageIndex,pageNumber){
	
	if(pageIndex > 0 && pageIndex <= pageNumber){
	 pageIndex = pageIndex + 1;
	 gotoPager(requesturl,pageIndex,pageNumber);
		
	}
}

function proPage(requesturl,pageIndex,pageNumber){
	if(pageIndex > 0 && pageIndex <= pageNumber){
		pageIndex = pageIndex - 1;
		this.gotoPager(requesturl,pageIndex,pageNumber);
	}
	
}

function gotoPager(requesturl,pageIndex,pageNumber){

	if(pageIndex > 0 && pageIndex <= pageNumber){
		var url = requesturl+"&pageIndex="+pageIndex;
		window.location.href = url;
	}
}

function goPager(requesturl,pageNumber){
	
	var currentPageIndex = document.getElementById("currentPageIndex").value;
	gotoPager(requesturl,currentPageIndex,pageNumber);
	
}
function createPageBar2(pageNumber,pageSize,pageIndex,total,callback,viewedNum,param){
	var sb = [];
	sb.push('<ul class="pagebar">共'+total+'条记录,每页'+pageSize+'条,当前第'+pageIndex+'页');
	if(pageNumber<=1){
		sb.push('</ul>');
		return sb.join('');
	}	
	if (param==null)
		param="";
	if(viewedNum==null)
		viewedNum = 5;
	var firstNumber = pageIndex-Math.floor(viewedNum/2);
	
	if(firstNumber<1)
		firstNumber = 1;
	var lastNumber = firstNumber+viewedNum-1;
	if(lastNumber>pageNumber)
		lastNumber = pageNumber;
	while(firstNumber>1 && (lastNumber-firstNumber+1)<viewedNum){
		firstNumber--;
	}
	var curl = '';
	if(pageIndex>1){
	    curl = callback + '(' + (1) + ',' + pageSize+')';
	    sb.push('<li><a href="'+curl+'">首页</a></li>');
		curl = callback + '(' + (pageIndex-1) + ',' + pageSize+')';
		sb.push('<li><a href="'+curl+'">上一页</a></li>');
	}
	for(var i=firstNumber;i<=lastNumber;i++){
		curl = callback + '(' + i + ',' + pageSize+')';
		if(i==pageIndex){
			sb.push('<li class="ch"> <a href="'+curl+'">'+i+'</a></li>');
		}else{
			sb.push('<li> <a href="'+curl+'">'+i+'</a></li>');
		}
	}
	if(pageIndex<pageNumber){
		curl = callback + '(' + (pageIndex+1) + ',' + pageSize+')';
		sb.push('<li><a href="'+curl+'">下一页</a></li>');
		curl = callback + '(' + (pageNumber) + ',' + pageSize+')';
		sb.push('<li><a href="'+curl+'">末页</a></li>');
	}
    
	sb.push('<li>共' + pageNumber + '页，跳转至</li>'); 
	sb.push('<li><select id="pid"  onchange="location.href=this.value" >');  		
	for(var i=1;i<=pageNumber;i++){	
	if(i==pageIndex){
		curl = callback + '(' + i + ',' + pageSize+')';
		sb.push('<li> <option value="'+curl+'"  selected="selected">'+i+'</option></li>');
	}else{
		curl = callback + '(' + i + ',' + pageSize+')';
		sb.push('<li> <option value="'+curl+'">'+i+'</option></li>');
	}
	}
	sb.push('</select></li>');  

	sb.push('</ul>');
	return sb.join('');
}


function createPageBar3(pageNumber,pageSize,pageIndex,total,callback,viewedNum,param){
	var sb = [];
	sb.push('<ul class="pagebar3">');
	if(pageNumber<=1){
		sb.push('</ul>');
		return sb.join('');
	}	
	if (param==null)
		param="";
	if(viewedNum==null)
		viewedNum = 5;
	var firstNumber = pageIndex-Math.floor(viewedNum/2);
	
	if(firstNumber<1)
		firstNumber = 1;
	var lastNumber = firstNumber+viewedNum-1;
	if(lastNumber>pageNumber)
		lastNumber = pageNumber;
	while(firstNumber>1 && (lastNumber-firstNumber+1)<viewedNum){
		firstNumber--;
	}
	var curl = '';
	if(pageIndex>1){
	    curl = callback + '(' + (1) + ',' + pageSize+')';
	    sb.push('<li><a href="'+curl+'">首页</a></li>');
		curl = callback + '(' + (pageIndex-1) + ',' + pageSize+')';
		sb.push('<li><a href="'+curl+'">上一页</a></li>');
	} else {
	    sb.push('<li>首页</li>');
	    sb.push('<li>上一页</li>');		
	}
	
	if(pageIndex<pageNumber){
		curl = callback + '(' + (pageIndex+1) + ',' + pageSize+')';
		sb.push('<li><a href="'+curl+'">下一页</a></li>');
		curl = callback + '(' + (pageNumber) + ',' + pageSize+')';
		sb.push('<li><a href="'+curl+'">末页</a></li>');
	} else {
		
		sb.push('<li>下一页</li>');		
		sb.push('<li>末页</li>');
		
	} 
    
	 
	
	sb.push('</ul>');
	return sb.join('');
}