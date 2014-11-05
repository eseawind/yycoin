<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="选择客户类型" link="true" guid="true" cal="true" dialog="true" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit(null, null, null);
}

function radio_click(obj)
{
	if (obj.value == '1')
	{
		$O('stype').value = obj.value;
	}

	if (obj.value == '2')
	{
		$O('stype').value = obj.value;
	}

	if (obj.value == '3')
	{
		$O('stype').value = obj.value;
	}
}

function load()
{
	var radioElements = document.getElementsByName('mode');
	
	for (var i=0; i< radioElements.length; i++)
	{
		if (radioElements[i].value == "1")
		{
			radioElements[i].checked = "checked";

			radio_click(radioElements[i]);
		}
	}
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry"
	action="../client/client.do?method=preForAddClient" method="post">
	<input type="hidden" name="stype" value="">
	
	<p:navigation
	height="22">
	<td width="550" class="navigation"><span>客户类型</span></td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>选择类型：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="50%">

		<p:table cells="1">

		    <p:cell title="客户类型">
               <input type="radio" name="mode" value="1" onClick="radio_click(this)"/>个人客户&nbsp;&nbsp;
               <Br>
               <Br>
			   <input type="radio" name="mode" value="2" onClick="radio_click(this)"/>部门客户&nbsp;&nbsp;
			   <Br>
               <Br>
			   <input type="radio" name="mode" value="3" onClick="radio_click(this)"/>组织客户&nbsp;&nbsp;
            </p:cell>
            
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="center">
			<input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()">
		</div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

