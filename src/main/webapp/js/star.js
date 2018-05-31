function getStarStr(level){
	var str = "初级";
	if (level == 0) {
		str = "初级";
	} else if (level == 1) {
		str = "一段";
	} else if (level == 2) {
		str = "二段";
	} else if (level == 3) {
		str = "三段";
	} else if (level == 4) {
		str = "四段";
	} else if (level == 5) {
		str = "五段";
	} else if (level == 6) {
		str = "六段";
	} else if (level == 7) {
		str = "七段";
	} else if (level == 8) {
		str = "八段";
	} else if (level == 9) {
		str = "九段";
	}
	return str;
}
  
//根据银币取等级
function getStarStr1(money){
	var level = "初级";
	if (money < 99999) {
		level = "初级";
	} else if (money >= 100000 && money < 999999) {
		level = "一段";
	} else if (money >= 1000000 && money < 9999999) {
		level = "二段";
	} else if (money >= 10000000 && money < 99999999) {
		level = "三段";
	} else if (money >= 100000000 && money < 999999999) {
		level = "四段";
	} else if (money >= 1000000000 && money < 9999999999) {
		level = "五段";
	} else if (money >= 10000000000 && money < 99999999999) {
		level = "六段";
	} else if (money >= 100000000000 && money < 999999999999) {
		level = "七段";
	} else if (money >= 1000000000000 && money < 9999999999999) {
		level = "八段";
	} else if (money >= 10000000000000 && money < 99999999999999) {
		level = "九段";
	}
	return level;
}