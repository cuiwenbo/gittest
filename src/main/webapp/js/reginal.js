var _reginaldata = [{c:'01',n:'浙江',s:[{c:'0101',n:'杭州',s:[{c:'010101',n:'杭州市区'},{c:'010102',n:'富阳'}]},{c:'0102',n:'宁波'}]},{c:'02',n:'上海'},{c:'03',n:'北京'}];

var reginal = {};
reginal.data = _reginaldata;

reginal.top = function() {
	var r = new Array();
	for(var i = 0; i < _reginaldata.length; i ++) {
		var t = {};
		t.code = _reginaldata[i].c;
		t.name = _reginaldata[i].n;
		r[r.length] =t;
	}
	return r;
}
 
function _getSub(p, c) {
	var t = null;
	for(var i = 0; i < p.length; i ++) {
		var k = p[i];
		if(k.c == c){
			return k.s; 				
		}
	}	
	return [];
	
}
reginal.sub = function(key) {
	var c1='',c2='';
	if(key.length == 2) c1 = key;
	else if(key.length == 4) {
		c1 = key.substring(0, 2);
		c2 = key.substring(2, 4);		
	} else {
		return [];
	}

	var s =  _getSub(_reginaldata,c1);
	if(c2 == '') return s;
	if(s.length == 0) return s;
	
	var s2 = _getSub(s, c1+c2);
	return s2;	
}

reginal.get = function(key) {
	var c1='',c2='',c3='';
	if(key.length == 2) c1 = key;
	else if(key.length == 4) {
		c1 = key.substring(0, 2);
		c2 = key;		
	} else if(key.length == 6) {
		c1 = key.substring(0, 2);
		c2 = key.substring(0, 4);	
		c3 = key;
	} else {
		return '';
	}
	var ret = "[" + key + "]"
	var sub = [];
	var sub2 = [];
	for(var i = 0; i < _reginaldata.length; i ++) {
		if(c1 == _reginaldata[i].c) {
			sub = _reginaldata[i].s;
			ret += _reginaldata[i].n;		
			break;
		}
	}
	
	if(c2 != '') {
		for(var i = 0; i < sub.length; i ++) {
			if(c2 == sub[i].c) {
				sub2 = sub[i].s;
				ret += " " + sub[i].n;
				break;
			}
		}
	}
	
	if(c3 != '') {
		for(var i = 0; i < sub2.length; i ++) {
			if(c3 == sub2[i].c) {
				ret += " " + sub2[i].n;
				break;
			}
		}
	}
	return ret;
} 

reginal.settop = function(sid1) {
	var o = $("#" + sid1); 
	o.html('');	
	for(var i = 0; i < _reginaldata.length; i++) {
		var option = "<option value='" +  _reginaldata[i].c + "'>" + _reginaldata[i].n + "</option>";
		o.append(option);
	}
	if(_reginaldata.length > 0) {
		o.val(_reginaldata[0].c);
		o.trigger("onchange");
	}
}

reginal.select_1 = function(sid1,sid2) {		
		var s1 = $("#"+sid1);
		var s2 = $("#"+sid2);	
		s2.html('');		
		var d1 = s1.val();		
		var subdata = reginal.sub(d1);
		if(subdata == undefined) {
			s2.trigger("onchange");
			return;
		}
		for(var i = 0; i < subdata.length; i++) {
			var option = "<option value='" +  subdata[i].c + "'>" + subdata[i].n + "</option>";
			s2.append(option);
		}
		if(subdata.length > 0) {
			s2.val(subdata[0].c);					
		}
		s2.trigger("onchange");
}
reginal.select_2 = function(sid2, sid3) {
		var s2 = $("#"+sid2);
		var s3 = $("#"+sid3);
		s3.html('');
		var d2 = s2.val();

		var subdata = reginal.sub(d2);
		for(var i = 0; i < subdata.length; i++) {
			var option = "<option value='" +  subdata[i].c + "'>" + subdata[i].n + "</option>";
			s3.append(option);
		}
}

reginal.init = function(sid1, sid2,sid3,v) {
	reginal.settop(sid1);
	$("#" + sid1).val(v.substring(0, 2));
	$("#" + sid1).trigger("onchange");
	$("#" + sid2).val(v.substring(0, 4));
	$("#" + sid2).trigger("onchange");
	$("#" + sid3).val(v);
}
