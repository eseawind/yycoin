<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />  
</head>  

<style>
*{margin:0;padding:0;font-size:12px}
ul,ol{list-style-type:none}
#show{height:160px;width:100%;position:relative;overflow:hidden;text-align:center}
#show h2{position:absolute;height:45px;padding-top:15px;text-align:center;line-height:5px;width:100%;opacity:0.5;background:black;color:white;left:0;bottom:0}
</style>
<script>
function go(){
	var t,tt;
	var _div=document.getElementById("show");
	var obj=_div.getElementsByTagName('h2')[0];
	obj.style.bottom="0px";
	var change=function(){
		var obj_h=parseInt(obj.style.bottom);
		if(obj_h<0){obj.style.bottom=(obj_h+Math.floor((0-obj_h)*0.1))+"px"}//if
		else{clearInterval(t)}
	} 
	var back=function(){
		var obj_hh=parseInt(obj.style.bottom);
		if(obj_hh>-50){obj.style.bottom=(obj_hh+Math.floor((-50-obj_hh)*0.1))+"px"}
		else{clearInterval(tt)}
	}
 _div.onmouseover=function(){clearInterval(tt);t=setInterval(change,10);}
 _div.onmouseout=function(){clearInterval(t);tt=setInterval(back,10)}
}
window.onload=function(){
	go();
}
</script>


<body>
<table border="1" cellpadding="0" width="100%" id="tables" cellspacing="0"
            class="border">
<tr class="content1">
<td width="5%">合同1</td>
<td width="65%" height="100">
<div id="show">
<h2>北国南疆，风光无限</h2>
<img src="../project/project.do?method=drawGant"  style="{background:white;height:125px}" width="100%"  alt="任务一完成时间：2013.3.31,任务一完成时间：2013.4.30" />
</div>
</td>
</tr>
<tr class="content1">
<td width="5%">合同2</td>
<td width="65%" height="120"><img src="../project/project.do?method=drawGant"  style="{background:white;}" width="100%" height="120" alt="任务一完成时间：2013.3.31,任务一完成时间：2013.4.30" /></td>
</tr>
</table>
<div></div>
</body>  
</html>  
