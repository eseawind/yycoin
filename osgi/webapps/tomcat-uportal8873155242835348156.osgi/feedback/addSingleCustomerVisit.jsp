<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="单客户回访生成" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定生成客户回访数据?');
}

function selectCus()
{
    window.common.modal('../client/client.do?method=rptQueryAllClient&load=1&first=1');
}

function getCustomer(obj)
{
    $O('cid').value = obj.value;
    $O('cname').value = obj.pname;
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../customerService/feedback.do"><input
	type="hidden" name="method" value="addSingleCustomerVisit">
<input type="hidden" name="cid" value=""> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">回访管理</span> &gt;&gt; 客户回访生成</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>客户回访生成：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">

			<p:cell title="选择客户">
			    <input type="text" class="input_class" name="cname" id="cname" style="width: 240px">
			    <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectCus()">&nbsp;&nbsp; 
			</p:cell>
			
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
		 value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>
	
	<p:message2></p:message2>
</p:body></form>
</body>
</html>

