<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="预算使用" cal="true" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="javascript">
function exports()
{
	document.location.href = '../finance/finance.do?method=exportFinanceItem';
}

function load()
{
	loadForm();
	
	bingTable("senfe");
}

function cc()
{
    var pp = $$('parentTax');
    
    
    if ($$('taxId') != '')
    {
        if ($$('taxId').indexOf(pp) != 0)
        {
            alert('必须是' + pp + '下的科目');
            
            return false;
        }
    }
    
	return true;
}

function query()
{
	submit(null, null, cc);
}

function selectTax()
{
    window.common.modal('../tax/tax.do?method=rptQueryTax&load=1&selectMode=1');
}

function getTax(oos)
{
    var obj = oos[0];
    
    $("input[name='taxId']").val(obj.value);
    $("input[name='taxName']").val(obj.value + ' ' + obj.pname);
}

function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
    var obj = oos[0];
    
    $("input[name='stafferId']").val(obj.value);
    $("input[name='stafferName']").val(obj.pname);
}

function selectDepartment()
{
    window.common.modal('../admin/org.do?method=popOrg');
}

function setOrgFromPop(id, name, level, pname)
{
    var showName = '';
    
    if (pname)
    showName = pname + '->' + '[' + level + ']' + name;
    else
    showName = '[' + level + ']' + name;
    
    $("input[name='departmentId']").val(id);
    $("input[name='departmentName']").val(showName);
}

function resetAll()
{
    $("input[name='taxId']").val('');
    $("input[name='stafferId']").val('');
    $("input[name='departmentId']").val('');
    $("input[name='taxName']").val('');
    $("input[name='stafferName']").val('');
    $("input[name='departmentName']").val('');
}

function clearStaffer()
{
    $("input[name='stafferId']").val('');
    $("input[name='stafferName']").val('');
}

function clearDepartment()
{
    $("input[name='departmentId']").val('');
    $("input[name='departmentName']").val('');
}

function clearTax()
{
    $("input[name='taxId']").val('');
    $("input[name='taxName']").val('');
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../budget/budget.do" method="post">
<input type="hidden" name="method" value="${cacheQueryKey}"> 
<input type="hidden" value="1" name="firstLoad">
<input type="hidden" name="itemId" value="${itemId}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation">预算使用</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>预算使用明细：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="senfe">
			<tr align=center class="content0">
				<td align="center" width="12%" class="td_class" onclick="tableSort(this)"><strong>日期</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>使用人</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>部门</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>预算/预算项</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>单据</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>类型</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>金额</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>日志</strong></td>
			</tr>

			<c:forEach items="${beanList}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="left" width="8%"  onclick="hrefAndSelect(this)">
					<a href="../budget/budget.do?method=findBudgetLog&id=${item.id}">
					${item.logTime}
					</a>
					</td>
					<td align="left">${item.stafferName}</td>
					<td align="left">${item.departmentName}</td>
					<td align="left">${item.budgetName}/${item.feeItemName}</td>
					<td align="left">${item.refId}</td>
					<td align="left">${my:get('budgetLogUserType', item.userType)}</td>
					<td align="left">${my:formatNum(item.monery / 100.0)}</td>
					<td align="left"><c:out value="${item.log}"/></td>
				</tr>
			</c:forEach>
		</table>
		
		<p:formTurning form="formEntry" method="${cacheQueryKey}"></p:formTurning>

	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
        <div align="right"><input type="button" class="button_class"
            id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javaScript:window.history.go(-1);"></div>
    </p:button>
	
	<p:message2 />
	
</p:body>
</form>
</body>
</html>

