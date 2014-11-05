<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加回款" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定增加回款?');
}

function selectBank()
{
    //单选
    window.common.modal('../finance/bank.do?method=rptQueryBank&load=1');
}

function getBank(obj)
{
    $O('bankName').value = obj.pname;
    
    $O('bankId').value = obj.value;
}

function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
    var oo = oos[0];
    
    $O('owerName').value = oo.pname;
    $O('destStafferId').value = oo.value;
}

function clearValues()
{
    $O('owerName').value = '';
    $O('destStafferId').value = '';
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/bank.do" method="post"><input
	type="hidden" name="method" value="addPayment">
<input type="hidden" name="bankId" value="">
<input type="hidden" name="destStafferId" value="">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">回款管理</span> &gt;&gt; 增加回款</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>回款基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.finance.bean.PaymentBean" />

		<p:table cells="1">
			<p:cell title="选择帐户">
                <input name="bankName" type="text" readonly="readonly" size="60" oncheck="notNone">
                 <font color="red">*</font>
                <input type="button"
                    value="&nbsp;...&nbsp;" name="qout" class="button_class"
                    onclick="selectBank()">
            </p:cell>
			
			<p:pro field="fromer" innerString="size=60"/>
			
			<p:pro field="type">
                <p:option type="paymentType"/>
            </p:pro>
            
            <p:cell title="指定职员">
            <input type="text" readonly="readonly" name="owerName" style="width: 240px"/>
            <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectStaffer()">&nbsp;&nbsp;
            <input type="button" value="&nbsp;清 空&nbsp;" name="qout1" id="qout1"
                    class="button_class" onclick="clearValues()">
            </p:cell>

			<p:pro field="money"/>
			
			<p:pro field="handling"/>
			
			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message2/>
</p:body></form>
</body>
</html>

