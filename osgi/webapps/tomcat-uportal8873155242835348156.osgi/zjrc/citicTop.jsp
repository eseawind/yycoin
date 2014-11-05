<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="TOP" />
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<STYLE type="text/css">
body
{
    font-size: 12px;
}
</STYLE>
<%
  String sid = session.getId();

  request.setAttribute("sessionId", sid);
%>
<script language="javascript">
var allDef = JSON.parse('<p:def/>');

var cmap = JSON.parse('${jsStrJSON}');

var pList = JSON.parse('${pStrJSON}');

var uAuth = JSON.parse('${authJSON}');

var gAuth = [];

function load()
{
    for(var i = 0; i < uAuth.length; i++)
    {
        gAuth[i] = uAuth[i].authId;
    }
}

</script>

</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" onload="load()"
	marginheight="0">

<table width="100%" border="0" cellspacing="0" cellpadding="0" background="../images/login/vistablue.jpg" style="height: 63px">
  <tr ondblclick="sho()" background="../images/login/vistablue.jpg">
    <td ondblclick="sho()"></td>
    <td ondblclick="sho()"><font color="#FFFFFF" size="2"><b>&nbsp;永银文化-紫金农商销售数据接口平台</b></font></td>
    
  </tr>
</table>
</body>

</html>
