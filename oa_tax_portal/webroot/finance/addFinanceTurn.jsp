<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="月结" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/tableSort.js"></script>
<script language="javascript">

function addBean()
{
    if (formCheck(formEntry) && window.confirm('月结需要一段时间,需要耐心等待.确定结转[${nextKey}]?'))
    {
        $.blockUI({ message: $('#loadingDiv'),css:{width: '40%'}}, true);
        
        submitC(formEntry);
    }
}

function load()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['贷方'], 'red');
    highlights($("#mainTable").get(0), ['借方'], 'blue');
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/finance.do" method="post">
<input type="hidden" name="method" value="addFinanceTurn"> 
<input type="hidden" name="monthKey" value="${nextKey}">

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">月结列表</span> &gt;&gt; 增加月结</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>[${nextKey}]科目月结列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="mainTable">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>月份</strong></td>
				<td align="center" width="30%" class="td_class" onclick="tableSort(this)"><strong>科目</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>借贷</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>借总额</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>贷总额</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>月度余额</strong></td>
			</tr>

			<c:forEach items="${monthList}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)">${nextKey}</td>
					<td align="center" width="30%" onclick="hrefAndSelect(this)">${item.taxName}(${item.taxId})</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.forwardName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.inmoneyTotal / 10000.0)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.outmoneyTotal / 10000.0)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.lastTotal / 10000.0)}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="97%" rightWidth="3%">
		<div align="right">
		<input type="button" class="button_class" id="sub_b"
            value="&nbsp;&nbsp;确定月结&nbsp;&nbsp;" onclick="addBean()">&nbsp;&nbsp;
		<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;">
		</div>
	</p:button>

	<p:message />

</p:body></form>

<div id="loadingDiv" style="display:none">
<p>&nbsp;</p>
<p align='center'>月结进行中......</p>
<p><img src="../images/oa/process.gif" /></p>
<p>&nbsp;</p>
</div>
</body>
</html>

