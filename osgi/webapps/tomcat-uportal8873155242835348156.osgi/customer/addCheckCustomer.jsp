<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="客户信息审核申请" cal="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">

function addBean()
{
	submit('确定提交审核申请?');
}


function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
    var oo = oos[0];
    
    $O('checkerName').value = oo.pname;
    $O('checkerId').value = oo.value;
}

</script>

</head>
<body class="body_class">
<form name="addApply" action="../customer/check.do" method="post">
<input type="hidden" name="method" value="addCheck"> 
<input type="hidden" name="checkerId" value="">
	<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">客户管理</span> &gt;&gt; 客户信息审核申请</td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>审核申请基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.customer.bean.CustomerCheckBean" />

		<p:table cells="1">
			<p:cell title="被检职员">
			<input type="text" name="checkerName" readonly="readonly">			
	       	<input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
					class="button_class" onclick="selectStaffer()">&nbsp;&nbsp;
			</p:cell>
			<p:pro field="beginTime"/>
			
			<p:pro field="endTime"/>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

