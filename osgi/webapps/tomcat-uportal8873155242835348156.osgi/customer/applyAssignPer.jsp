<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="利润分配申请" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定申请利润分配?');
}

function selectCus()
{
    window.common.modal('../client/client.do?method=rptQueryAllClient&load=1&view=1');
}

function getCustomer(obj)
{
    $O('cid').value = obj.value;
    $O('cname').value = obj.pname;
    $O('oassignPer1').value = 0;
    $O('oassignPer2').value = 0;
    $O('oassignPer3').value = 0;
    $O('oassignPer4').value = 0;
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../client/client.do"><input
	type="hidden" name="method" value="addAssignPerApplyCustomer">
<input type="hidden" name="cid" value=""> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">客户管理</span> &gt;&gt; 利润分配申请</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>利润分配申请：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">

			<p:cell title="选择客户">
			    <input type="text" class="input_class" name="cname" id="cname" style="width: 240px">
			    <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectCus()">&nbsp;&nbsp; 
			</p:cell>
			
			<p:cell title="分配比例(事业部)">
                <input type="text" class="input_class" name="oassignPer1"  readonly="readonly" style="width: 60px">%
                <input type="text" class="input_class" name="assignPer1"  oncheck="notNone;isNumber;range(0)" style="width: 60px">% <font color="red">*</font>
            </p:cell>
            
            <p:cell title="分配比例(分公司)">
                <input type="text" class="input_class" name="oassignPer2"  readonly="readonly" style="width: 60px">%
                <input type="text" class="input_class" name="assignPer2"  oncheck="notNone;isNumber;range(0)" style="width: 60px">% <font color="red">*</font>
            </p:cell>
            
            <p:cell title="分配比例(产品部)">
                <input type="text" class="input_class" name="oassignPer3"  readonly="readonly" style="width: 60px">%
                <input type="text" class="input_class" name="assignPer3"  oncheck="notNone;isNumber;range(0)" style="width: 60px">% <font color="red">*</font>
            </p:cell>
            
            <p:cell title="分配比例(其他)">
                <input type="text" class="input_class" name="oassignPer4"  readonly="readonly" style="width: 60px">%
                <input type="text" class="input_class" name="assignPer4"  oncheck="notNone;isNumber;range(0)" style="width: 60px">% <font color="red">*</font>
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

