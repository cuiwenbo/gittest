// JavaScript Document
var flag=true;
function switchSysBar1()
{ 

  if (flag)
  {
	flag = false;
	document.getElementById("ileft").style.display="none";
	document.getElementById("switch1").src="images/flag_open.gif";  //¡¥Ω”Õº∆¨ 
	document.getElementById("open").className="right02";
  }
  else
  {
	flag = true;
	document.getElementById("ileft").style.display="";
	document.getElementById("switch1").src="images/flag_close.gif";
	document.getElementById("open").className="right";          
  }
} 
