<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="信用杠杆" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定变化信用杠杆?');
}

function selectCus()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
	var obj = oos[0];
    $O('cid').value = obj.value;
    $O('cname').value = obj.pname;
    $O('oldcval').value = obj.plever;
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../client/client.do"><input
	type="hidden" name="method" value="interposeLever">
<input type="hidden" name="cid" value=""> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">信用杠杆管理</span> &gt;&gt; 信用杠杆维护</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>信用杠杆调整：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">

			<p:cell title="选择职员">
			    <input type="text" class="input_class" name="cname" id="cname" style="width: 240px">
			    <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectCus()">&nbsp;&nbsp; 
			</p:cell>
			
			<p:cell title="职员原始信用杠杆">
                <input type="text" class="input_class" name="oldcval" id="oldcval" readonly="readonly">
            </p:cell>
            
            <p:cell title="更新后职员信用杠杆">
                <input type="text" class="input_class" name="newcval" id="newcval" oncheck="notNone;isInt;range(1)"> <font color="red">*</font>
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

