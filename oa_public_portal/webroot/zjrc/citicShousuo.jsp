<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/common.jsp"%>
<%@page
	import="java.util.Random"%>
<html>
<head>
<p:link title="显示菜单" cal="false"/>
<link href="../css/shousuo.css" type=text/css rel=stylesheet>
<script type=text/javascript><!--
var LastLeftID = "";
function menuFix() {
var obj = document.getElementById("nav").getElementsByTagName("li");

for (var i=0; i<obj.length; i++) {
   obj[i].onmouseover=function() {
    this.className+=(this.className.length>0? " ": "") + "sfhover";
   }
   obj[i].onMouseDown=function() {
    this.className+=(this.className.length>0? " ": "") + "sfhover";
   }
   obj[i].onMouseUp=function() {
    this.className+=(this.className.length>0? " ": "") + "sfhover";
   }
   obj[i].onmouseout=function() {
    this.className=this.className.replace(new RegExp("( ?|^)sfhover\\b"), "");
   }
}
}
function DoMenu(emid)
{
var obj = document.getElementById(emid);
obj.className = (obj.className.toLowerCase() == "expanded"?"collapsed":"expanded");
if((LastLeftID!="")&&(emid!=LastLeftID)) //关闭上一个Menu
{
   document.getElementById(LastLeftID).className = "collapsed";
}
LastLeftID = emid;
}
function GetMenuID()
{
var MenuID="";
var _paramStr = new String(window.location.href);
var _sharpPos = _paramStr.indexOf("#");

if (_sharpPos >= 0 && _sharpPos < _paramStr.length - 1)
{
   _paramStr = _paramStr.substring(_sharpPos + 1, _paramStr.length);
}
else
{
   _paramStr = "";
}

if (_paramStr.length > 0)
{
   var _paramArr = _paramStr.split("&");
   if (_paramArr.length>0)
   {
    var _paramKeyVal = _paramArr[0].split("=");
    if (_paramKeyVal.length>0)
    {
     MenuID = _paramKeyVal[1];
    }
   }
   /*
   if (_paramArr.length>0)
   {
    var _arr = new Array(_paramArr.length);
   }

   //取所有#后面的，菜单只需用到Menu
   //for (var i = 0; i < _paramArr.length; i++)
   {
    var _paramKeyVal = _paramArr[i].split('=');

    if (_paramKeyVal.length>0)
    {
     _arr[_paramKeyVal[0]] = _paramKeyVal[1];
    }
   }
   */
}

if(MenuID!="")
{
   DoMenu(MenuID)
}
}


function load()
{
	GetMenuID(); //*这两个function的顺序要注意一下，不然在Firefox里GetMenuID()不起效果
	menuFix();
}
--></script>
</head>
<body class="tree_class" onload="load()">
<table>
	<tr height="10">
		<td colspan="2"></td>
	</tr>

	<tr height="10">
		<td colspan="2"></td>
	</tr>
</table>
<table width="100%">
<div id="PARENT">
<ul id="nav">
	<c:forEach var="item" items="${menuRootList}" varStatus="vs">
			<c:forEach var="item1" items="${menuItemMap[item.id]}"
				varStatus="vs1">
				<c:if test="${item1.id >= '9030' && item1.id <= '9033' }">
					<li><a href="${item1.url}" target="main" id="a_${item1.id}" title="${item1.description}">${item1.menuItemName}</a></li>
				</c:if>
			</c:forEach>
	</c:forEach>
	<li><a href="../admin/modifyPassword.jsp" target="main">修改密码</a></li>
	<li><a href="../zjrc/index.jsp" target="_parent">退出</a></li>
</ul>
</div>
</table>
<table>
	<tr height="10">
		<td colspan="2"></td>
	</tr>

	<tr height="10">
		<td width="15"></td>
		<%
		Random random = new Random(System.currentTimeMillis());

		int ran = random.nextInt(1000);

		request.setAttribute("colock", (ran % 4) + 1);
		%>
		<td></td>
	</tr>

	<tr height="10">
		<td colspan="2"></td>
	</tr>
</table>

</body>
</html>
