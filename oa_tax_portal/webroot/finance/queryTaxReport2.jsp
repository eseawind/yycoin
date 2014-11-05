<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="损益表" cal="true" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="javascript">
function exports()
{
	document.location.href = '../finance/finance.do?method=exportTaxReport';
}

function load()
{
	loadForm();
	
	bingTable("dataTable");
}

function resetAll()
{

}

function query()
{
    submit(null, null, null);
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/finance.do">
<input type="hidden" name="method" value="queryTaxReport"> 
<input type="hidden" value="1" name="firstLoad">
<input type="hidden" name="type" value="1"> 
<p:navigation
	height="22">
	<td width="550" class="navigation">损益表</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="98%">
        <table width="100%" align="center" cellspacing='1' class="table0">
            <tr class="content1">
                <td width="15%" align="center">选择年</td>
                <td align="left">
                <select name="year" values="${year}" class="select_class" oncheck="notNone">
                    <p:option type="[2012,2100]"></p:option>
                </select>
                <font color="#FF0000">*</font>
                </td>
                <td width="15%" align="center">选择月</td>
                <td align="left">
                <select name="month" values="${month}" class="select_class" oncheck="notNone">
                    <option value="01">01</option>
                    <option value="02">02</option>
                    <option value="03">03</option>
                    <option value="04">04</option>
                    <option value="05">05</option>
                    <option value="06">06</option>
                    <option value="07">07</option>
                    <option value="08">08</option>
                    <option value="09">09</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                </select>
                <font color="#FF0000">*</font>
                </td>
            </tr>

            <tr class="content2">
                <td colspan="4" align="right"><input type="button" onclick="query()"
                    class="button_class" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;<input
                    type="button" class="button_class" onclick="resetAll()"
                    value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
            </tr>
        </table>

    </p:subBody>

	<p:title>
		<td class="caption"><strong>损益表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="dataTable">
			<tr align=center class="content0">
                <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>行次</strong></td>
                <td align="center" width="15%" class="td_class" onclick="tableSort(this)"><strong>分类</strong></td>
                <td align="center" width="20%" class="td_class" onclick="tableSort(this)"><strong>名称</strong></td>
                <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>本期数</strong></td>
                <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>本年累计数</strong></td>
            </tr>

			<c:forEach items="${g_tax_rep_resultList1}" var="item"
                varStatus="vs">
                <tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
                    <c:if test="${item.rmethod != 99}">
                    <td align="left" onclick="hrefAndSelect(this)">${item.itemIndex}</td>
                    <td align="left" width="15%"  onclick="hrefAndSelect(this)">${item.itemPName}</td>
                    <td align="left" width="20%" onclick="hrefAndSelect(this)" title="${item.expr}">${item.itemName}</td>
                    <td align="left" onclick="hrefAndSelect(this)" >${item.beginMoneyStr}</td>
                    <td align="left" onclick="hrefAndSelect(this)">${item.endMoneyStr}</td>
                    </c:if>
                    
                    <c:if test="${item.rmethod == 99}">
                    <td align="left" onclick="hrefAndSelect(this)"><font color="red"><b>${item.itemIndex}</b></font></td>
                    <td align="left" width="15%"  onclick="hrefAndSelect(this)"><font color="red"><b>${item.itemPName}</b></font></td>
                    <td align="left" width="20%" onclick="hrefAndSelect(this)" title="${item.expr}"><font color="red"><b>${item.itemName}</b></font></td>
                    <td align="left" onclick="hrefAndSelect(this)" ><font color="red"><b>${item.beginMoneyStr}</b></font></td>
                    <td align="left" onclick="hrefAndSelect(this)"><font color="red"><b>${item.endMoneyStr}(${item.endMoneyChineseStr})</b></font></td>
                    </c:if>
                </tr>
            </c:forEach>
		</table>
		
	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="98%" rightWidth="2%">
        <div align="right"><input type="button" class="button_class"
            value="&nbsp;&nbsp;导出明细&nbsp;&nbsp;" onclick="exports()">&nbsp;&nbsp;
        </div>
    </p:button>

	<p:message2 />
	
</p:body>
</form>
</body>
</html>

