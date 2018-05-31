

function __getCode(pid, name) {
	var data = cityL1;	
	if(pid.length == 2) data = cityL2;
	if(pid.length == 4) data = cityL3;
	for(var idx = 0; idx < data.length; idx ++) {
		if(data[idx].pid == pid && name == data[idx].name) {
			return data[idx].id;
		}
	}
	return "";	
}

function __getName(code) {
	if(code == "") return "";
	var data = null;
	if(code.length == 2) {
		data = cityL1;
	}
	if(code.length == 4) {
		data = cityL2;
	}
	if(code.length == 6) {
		data = cityL3;
	}
	if(data == null || data.length == 0) return "";
	for(var idx = 0; idx < data.length; idx ++) {
		if(data[idx].id == code) return data[idx].name;
	}
	return "";
}
var city = {};

function city_getSelectValue(id1, id2, id3) {
	var c1 = $('#'+id1).val();
	var c2 = $('#'+id2).val();
	var c3 = $('#'+id3).val();
	var ret = {};
	ret.province = __getName(c1);
	ret.city = __getName(c2);
	ret.district = __getName(c3);
	return ret;
}


 function city_init(id1,id2,id3,v1,v2,v3) {
	var pro = document.getElementById(id1);
	if(pro == null) return;
	pro.options.length = 1;
	for(var i = 0; i < cityL1.length; i ++) {
		pro.options[pro.options.length] = new Option(cityL1[i].name, cityL1[i].id);
	}
	var c1 = __getCode("", v1);
	var c2 = __getCode(c1, v2);
	var c3 = __getCode(c2, v3);
	if(c1 != "") {
		pro.value = c1;
	}
	if(c2 != "") {
		__loadCity(id2, c1);
		$('#'+id2).val(c2);
		if(c3 != "") {
			__loadCity(id3, c2);
			document.getElementById(id3).value=c3;
		}	
	}
}

 function prov_change(id1, id2) {
	var c = document.getElementById(id1);
	var pcode = c.value;
	__loadCity(id2, pcode);
}

function city_change (id2, id3) {
	var c = document.getElementById(id2);
	var pcode = c.value;
	__loadCity(id3, pcode);
	
}
/**加载市一级 */
function __loadCity(cid, pcode) {
	var c = document.getElementById(cid);
	c.options.length = 1;
	var data = cityL2;
	if(pcode.length == 4) {
		data = cityL3;
	}
	for(var i = 0; i < data.length; i ++) {
		if(data[i].pid == pcode) {
			c.options[c.options.length] = new Option(data[i].name, data[i].id);
		}
	}
}
