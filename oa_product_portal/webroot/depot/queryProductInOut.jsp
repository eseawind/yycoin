<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="产品异动列表" cal="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="javascript">
function exports()
{
    if (window.confirm("确定导出当前的异动结果?"))
    document.location.href = '../depot/storage.do?method=exportProductInOut';
}

function querys()
{
    formEntry.submit();
}

function goBack()
{
    $l('../depot/queryDepotStorageRelation.jsp?depotId=${depotId}');
}

function press()
{
    window.common.enter(querys);
} 

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../depot/storage.do" method="post"> 
<input type="hidden" value="queryProductInOut" name="method">
<input type="hidden" value="${depotId}" name="depotId">
<p:navigation height="22">
	<td width="550" class="navigation"><span>库存列表</span> &gt;&gt; 产品异动列表</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

    <p:subBody width="98%">
        <table width="100%" align="center" cellspacing='1' class="table0"
            id="result">
            <tr class="content1">
                <td width="15%" align="center">日期</td>
                <td align="center" colspan="1"><p:plugin name="now" size="20" value="${queryProductInOut_now}"/></td>
                <td width="15%" align="center"></td>
                <td align="center"></td>
            </tr>
            
            <tr class="content2">
                <td width="15%" align="center">产品名称</td>
                <td align="center"><input type="text" name="name" onkeypress="press()"
                    value="${name}"></td>
                <td width="15%" align="center">产品编码</td>
                <td align="center"><input type="text" name="code" onkeypress="press()"
                    value="${code}"></td>
            </tr>

            <tr class="content1">
                <td colspan="4" align="right"><input type="button"
                    onclick="querys()" class="button_class"
                    value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"></td>
        </table>

    </p:subBody>
    

	<p:title>
		<td class="caption"><strong>${queryProductInOut_depot.name}下产品异动列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>时间</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>产品</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>入库数量</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>出库数量</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>异动数量</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>当前库存</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>良品库存</strong></td>
			</tr>

			<c:forEach items="${showList}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)">${queryProductInOut_now}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}(${item.productCode})</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.preAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.afterAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.changeAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.afterAmount1}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.afterAmount2}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="97%" rightWidth="3%">
		<div align="right">
		 <input
                type="button" class="button_class"
                value="&nbsp;导出异动结果&nbsp;" onclick="exports()" />&nbsp;&nbsp;
		<input type="button" class="button_class"
			onclick="goBack()"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;">
		</div>
	</p:button>

	<p:message2 />

</p:body></form>
</body>
</html>

