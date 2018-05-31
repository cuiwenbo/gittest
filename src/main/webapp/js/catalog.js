var _catalogs = [{id:1,name:'服装/服饰'},{id:2,name:'鞋子/箱包'},{id:3,name:'运动户外'},{id:4,name:'个人护理'},{id:5,name:'手机/数码'},{id:6,name:'母婴/玩具'},{id:7,name:'食品/酒水'},{id:8,name:'家具建材'},{id:9,name:'生活电器'},{id:10,name:'珠宝饰品'},{id:11,name:'汽车配饰'},{id:12,name:'家纺/家饰'},{id:13,name:'医药保健'},{id:14,name:'图书音响'},{id:15,name:'厨房用具'},{id:16,name:'其他'}];
var catalogs = {};
catalogs.data = _catalogs;
catalogs.get = function (key) {
	for(var i = 0; i < _catalogs.length; i ++){
		if(_catalogs[i].id == key) return _catalogs[i].name;
	}
	return '';
};
catalogs.show = function(sid, defaultvalue) {
	var o = $("#" + sid); 
	for(var i = 0; i < _catalogs.length; i ++) {
		var t = _catalogs[i];
		o.append("<option value='" + t.id + "'>" + t.name + "</option");
	}
	o.val(defaultvalue);
}
