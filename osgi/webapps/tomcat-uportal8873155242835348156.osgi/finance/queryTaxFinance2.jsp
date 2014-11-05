<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="科目余额" cal="true" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="javascript">
function exportTaxQuery()
{
	document.location.href = '../finance/finance.do?method=exportTaxQuery';
}

function exportTaxQuery2()
{
    $O('method').value = 'queryExportTaxFinance2';
    
    submit('导出查询需要等待一段时间,请没有看到下载提示前等待服务器处理,确定导出?', null, cc1);
}

function load()
{
	loadForm();
	
	if ($O('senfe') != null)
	bingTable("senfe");
}

function cc1()
{
    if ($$('beginDate') < '2012-04-01')
    {
        alert('开始日期必须大于等于:2012-04-01');
        return false;
    }
    
    if (compareDays($$('beginDate'), $$('endDate')) > 180)
    {
        alert('跨度不能大于180天');
        return false;
    }
    
    if ($$('queryType') == 0)
    {
       if ($$('stafferId') != '' && $$('unitId') != '')
       {
           alert('科目余额查询不能同时有职员和单位');
           return false;
       }
    }
    
    return true;
}

function cc()
{
    if ($$('beginDate') < '2012-04-01')
    {
        alert('开始日期必须大于等于:2012-04-01');
        return false;
    }
    
	if (compareDays($$('beginDate'), $$('endDate')) > 180)
	{
		alert('跨度不能大于180天');
		return false;
	}
	
	//科目查询
	if ($$('queryType') == 0)
	{
	   if ($$('stafferId') != '' && $$('unitId') != '')
	   {
	       alert('科目余额查询不能同时有职员和单位');
           return false;
	   }
	}
	
	if ($$('queryType') == 1)
    {
       if ($$('taxId') == '')
       {
           alert('缺少科目');
           return false;
       }
    }
    
    if ($$('queryType') == 2)
    {
       if ($$('taxId') == '')
       {
           alert('缺少科目');
           return false;
       }
    }

    if ($$('queryType') == 3)
    {
       if ($$('taxId') == '')
       {
           alert('缺少科目');
           return false;
       }

       if ($$('taxId') != '1132' && $$('taxId') != '2131')
       {
           alert('只限应收账款科目与预收账款科目');
           return false;
       }  
    }
    
	return true;
}

function asynQuery(){
	$O('method').value = 'asynQueryTaxFinance2';

	submit('后台查询需要花费一段时间，同时只能一个后台任务，是否确定操作?', null, cc2);
}

function cc2()
{
    if ($$('beginDate') < '2012-04-01')
    {
        alert('开始日期必须大于等于:2012-04-01');
        return false;
    }
    
    return true;
}

function query()
{
    $O('method').value = 'queryTaxFinance2';
    
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

function selectUnit(obj)
{
    window.common.modal('../finance/finance.do?method=rptQueryUnit&load=1');
}

function getUnit(obj)
{
    $("input[name='unitId']").val(obj.value);
    $("input[name='unitName']").val(obj.pname);
}

function resetAll()
{
    $("input[name='taxId']").val('');
    $("input[name='stafferId']").val('');
    $("input[name='unitId']").val('');
    $("input[name='taxName']").val('');
    $("input[name='stafferName']").val('');
    $("input[name='unitName']").val('');
}

function clearStaffer()
{
    $("input[name='stafferId']").val('');
    $("input[name='stafferName']").val('');
}

function clearUnit()
{
    $("input[name='unitId']").val('');
    $("input[name='unitName']").val('');
}

function clearTax()
{
    $("input[name='taxId']").val('');
    $("input[name='taxName']").val('');
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/finance.do">
<input type="hidden" name="method" value="queryTaxFinance2"> 
<input type="hidden" value="1" name="firstLoad">
<input type="hidden" name="taxId" value="${taxId}"> 
<input type="hidden" name="stafferId" value="${stafferId}"> 
<input type="hidden" name="unitId" value="${unitId}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation">科目余额</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr class="content1">
				<td width="20%" align="center">开始日期</td>
				<td align="left">
				<p:plugin name="beginDate" size="20" value="${beginDate}"  oncheck="notNone"/>
				<font color="#FF0000">*</font>
				</td>
				<td width="20%" align="center">结束日期</td>
				<td align="left">
				<p:plugin name="endDate" size="20" value="${endDate}"  oncheck="notNone"/>
				<font color="#FF0000">*</font>
				</td>
			</tr>
			
			<tr class="content1">
			    <td width="20%" align="center">所属纳税</td>
                <td align="left" colspan="1"><select name="dutyId"
                    class="select_class" values="${dutyId}" style="width: 80%">
                   <p:option type="$dutyList" empty="true"/>
                </select></td>
                
                <td width="20%" align="center">科目级别</td>
                <td align="left" colspan="1">
                <select name="taxLevel"
                    class="select_class" values="${taxLevel}" style="width: 80%">
                   <option value="">--</option>
                   <option value="0">一级</option>
                   <option value="1">二级</option>
                   <option value="2">三级</option>
                   <option value="3">四级</option>
                   <option value="4">五级</option>
                   <option value="5">六级</option>
                </select>
                </td>
            </tr>
			
			<tr class="content1">
                <td width="20%" align="center">职员</td>
                <td align="left" colspan="1"><input type="text" name="stafferName" style="width: 70%" value="${stafferName}" readonly="readonly">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectStaffer()">&nbsp;
                <input type="button" value="&nbsp;C&nbsp;" name="qout_c" id="qout_c"
                    class="button_class" onclick="clearStaffer()">
                </td>
                
                <td width="20%" align="center">单位</td>
                <td align="left" colspan="1"><input type="text" name="unitName" style="width: 70%" value="${unitName}" readonly="readonly">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectUnit()">&nbsp;
                <input type="button" value="&nbsp;C&nbsp;" name="qout_c1" id="qout_c1"
                    class="button_class" onclick="clearUnit()">
                </td>
            </tr>

			<tr class="content2">
                <td width="20%" align="center">科目</td>
                <td align="left" colspan="1"><input type="text" name="taxName" style="width: 70%" value="${taxName}" readonly="readonly">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectTax()">&nbsp;
                <input type="button" value="&nbsp;C&nbsp;" name="qout_c2" id="qout_c2"
                    class="button_class" onclick="clearTax()">
                </td>
                
                <td width="20%" align="center">类型</td>
                <td align="left" colspan="1">
                <select name="queryType"
                    class="select_class" values="${queryType}" style="width: 80%">
                    <option value="0">科目查询</option>
                    <option value="1">职员查询</option>
                    <option value="2">单位查询</option>
                    <option value="3">职员单位查询</option> 
                </select>
                </td>
            </tr>

			<tr class="content1">
				<td width="20%" align="center">模式</td>
                <td align="left" colspan="1">
                <select name="queryMode"
                    class="select_class" values="${queryMode}" style="width: 80%">
                    <option value="0">同步</option>
                    <option value="1">异步</option>
                </select>
                </td>
				<td colspan="2" align="right">
				<input type="button" onclick="asynQuery()"
                    class="button_class" value="&nbsp;&nbsp;后台查询&nbsp;&nbsp;">&nbsp;&nbsp;
				<input type="button" onclick="exportTaxQuery2()"
                    class="button_class" value="&nbsp;&nbsp;查询导出&nbsp;&nbsp;">&nbsp;&nbsp;
				<input type="button" onclick="query()"
					class="button_class" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;<input
					type="button" class="button_class" onclick="resetAll()"
					value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
			</tr>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>科目余额：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	    <c:if test="${queryType == '0'}">
			<table width="100%" align="center" cellspacing='1' class="table0" id="senfe">
				<tr align=center class="content0">
					<td align="center" width="20%" class="td_class" onclick="tableSort(this)"><strong>科目</strong></td>
					<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>期初余额</strong></td>
					<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>本期借方</strong></td>
					<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>本期贷方</strong></td>
					<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>借方累计</strong></td>
					<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>贷方累计</strong></td>
					<td align="center" class="td_class" onclick="tableSort(this)"><strong>方向</strong></td>
					<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>期末余额</strong></td>
				</tr>
	
				<c:forEach items="${resultList_2}" var="item"
					varStatus="vs">
					<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
						<td align="left" width="20%" onclick="hrefAndSelect(this)">${item.taxId} ${item.taxName}</td>
						<td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showBeginAllmoney}</td>
						<td align="left" width="8%" onclick="hrefAndSelect(this)" >${item.showCurrInmoney}</td>
						<td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showCurrOutmoney}</td>
						<td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showAllInmoney}</td>
						<td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showAllOutmoney}</td>
						<td align="left" width="4%" onclick="hrefAndSelect(this)">${item.forwardName}</td>
						<td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showLastmoney}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		
		<c:if test="${queryType == '1'}">
            <table width="100%" align="center" cellspacing='1' class="table0" id="senfe">
                <tr align=center class="content0">
                    <td align="center" width="20%" class="td_class" onclick="tableSort(this)"><strong>科目</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>职员</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>期初余额</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>本期借方</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>本期贷方</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>借/贷累计</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>方向</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>期末余额</strong></td>
                </tr>
    
                <c:forEach items="${resultList_2}" var="item"
                    varStatus="vs">
                    <tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
                        <td align="left" width="20%"  onclick="hrefAndSelect(this)">${item.taxId} ${item.taxName}</td>
                        <td align="left" width="15%" onclick="hrefAndSelect(this)">${item.stafferName}</td>
                        <td align="left" width="8% onclick="hrefAndSelect(this)">${item.showBeginAllmoney}</td>
                        <td align="left" width="8%" onclick="hrefAndSelect(this)" >${item.showCurrInmoney}</td>
                        <td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showCurrOutmoney}</td>
                        <td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showAllInmoney}/${item.showAllOutmoney}</td>
                        <td align="left" width="4%" onclick="hrefAndSelect(this)">${item.forwardName}</td>
                        <td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showLastmoney}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
		
		
		<c:if test="${queryType == '2'}">
            <table width="100%" align="center" cellspacing='1' class="table0" id="senfe">
                <tr align=center class="content0">
                    <td align="center" width="20%" class="td_class" onclick="tableSort(this)"><strong>科目</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>单位名称</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>期初余额</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>本期借方</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>本期贷方</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>借/贷累计</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>方向</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>期末余额</strong></td>
                </tr>
    
                <c:forEach items="${resultList_2}" var="item"
                    varStatus="vs">
                    <tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
                        <td align="left" width="20%"  onclick="hrefAndSelect(this)">${item.taxId} ${item.taxName}</td>
                        <td align="left" width="15%" onclick="hrefAndSelect(this)">${item.unitName}</td>
                        <td align="left" width="8%   onclick="hrefAndSelect(this)">${item.showBeginAllmoney}</td>
                        <td align="left" width="8%" onclick="hrefAndSelect(this)" >${item.showCurrInmoney}</td>
                        <td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showCurrOutmoney}</td>
                        <td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showAllInmoney}/${item.showAllOutmoney}</td>
                        <td align="left" width="4%" onclick="hrefAndSelect(this)">${item.forwardName}</td>
                        <td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showLastmoney}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
		
		<c:if test="${queryType == '3'}">
            <table width="100%" align="center" cellspacing='1' class="table0" id="senfe">
                <tr align=center class="content0">
                    <td align="center" width="20%" class="td_class" onclick="tableSort(this)"><strong>科目</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>职员</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>单位名称</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>期初余额</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>本期借方</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>本期贷方</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>借/贷累计</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>方向</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this, true)"><strong>期末余额</strong></td>
                </tr>
    
                <c:forEach items="${resultList_2}" var="item"
                    varStatus="vs">
                    <tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
                        <td align="left" width="20%"  onclick="hrefAndSelect(this)">${item.taxId} ${item.taxName}</td>
                        <td align="left" width="15%" onclick="hrefAndSelect(this)">${item.stafferName}</td>
                        <td align="left" width="15%" onclick="hrefAndSelect(this)">${item.unitName}</td>
                        <td align="left" width="8%   onclick="hrefAndSelect(this)">${item.showBeginAllmoney}</td>
                        <td align="left" width="8%" onclick="hrefAndSelect(this)" >${item.showCurrInmoney}</td>
                        <td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showCurrOutmoney}</td>
                        <td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showAllInmoney}/${item.showAllOutmoney}</td>
                        <td align="left" width="4%" onclick="hrefAndSelect(this)">${item.forwardName}</td>
                        <td align="left" width="8%" onclick="hrefAndSelect(this)">${item.showLastmoney}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="98%" rightWidth="2%">
        <div align="right"><input type="button" class="button_class"
            value="&nbsp;&nbsp;导出明细&nbsp;&nbsp;" onclick="exportTaxQuery()">&nbsp;&nbsp;
        </div>
    </p:button>

	<p:message2 />
	
</p:body>
</form>
</body>
</html>

