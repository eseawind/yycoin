<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="分类账" cal="true" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
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
    if ($$('beginDate') < '2012-04-01')
    {
        alert('开始日期必须大于等于:2012-04-01');
        return false;
    }
    
	//if (compareDays($$('beginDate'), $$('endDate')) > 180)
	//{
		//alert('跨度不能大于180天');
		//return false;
	//}
	
	//if ($$('beginDate').substring(0, 4) != $$('endDate').substring(0, 4))
	//{
	    //alert('查询条件不能跨年');
        //return false;
	//}
	
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


function selectProduct()
{
    window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1&abstractType=0&status=0');
}

function selectUnit()
{
    window.common.modal('../finance/finance.do?method=rptQueryUnit&load=1');
}

function getUnit(oo)
{
    $O('unitId').value = oo.value;
    $O('unitName').value = oo.pname;
}

function getStaffers(oos)
{
    var obj = oos[0];
    
    $("input[name='stafferId']").val(obj.value);
    $("input[name='stafferName']").val(obj.pname);
}

function getProduct(oos)
{
    var obj = oos[0];
    
    $("input[name='productId']").val(obj.value);
    $("input[name='productName']").val(obj.pname);
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
    $("input[name='productId']").val('');
    $("input[name='unitId']").val('');
    
    $("input[name='taxName']").val('');
    $("input[name='stafferName']").val('');
    $("input[name='departmentName']").val('');
    $("input[name='productName']").val('');
    $("input[name='unitName']").val('');
    
    setSelectIndex($O('depotId'), 0);
}

function clearStaffer()
{
    $("input[name='stafferId']").val('');
    $("input[name='stafferName']").val('');
}

function clearProduct()
{
    $("input[name='productId']").val('');
    $("input[name='productName']").val('');
}

function clearUnit()
{
    $("input[name='unitId']").val('');
    $("input[name='unitName']").val('');
}

function clearDepartment()
{
    $("input[name='departmentId']").val('');
    $("input[name='departmentName']").val('');
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/finance.do">
<input type="hidden" name="method" value="queryTaxFinance1"> 
<input type="hidden" value="1" name="firstLoad">
<input type="hidden" name="taxId" value="${taxId}"> 
<input type="hidden" name="stafferId" value="${stafferId}"> 
<input type="hidden" name="departmentId" value="${departmentId}"> 
<input type="hidden" name="productId" value="${productId}"> 
<input type="hidden" name="unitId" value="${unitId}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation">分类账</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr class="content1">
				<td width="15%" align="center">开始日期</td>
				<td align="left">
				<p:plugin name="beginDate" size="20" value="${beginDate}"  oncheck="notNone"/>
				<font color="#FF0000">*</font>
				</td>
				<td width="15%" align="center">结束日期</td>
				<td align="left">
				<p:plugin name="endDate" size="20" value="${endDate}"  oncheck="notNone"/>
				<font color="#FF0000">*</font>
				</td>
			</tr>

			<tr class="content2">
			    <td width="15%" align="center">类型:</td>
                <td align="left" colspan="1"><select name="queryType"
                    class="select_class" values="${queryType}" style="width: 80%">
                    <option value="0">明细帐</option>
                    <option value="1">总帐</option>
                </select></td>
                
                <td width="15%" align="center">科目</td>
                <td align="left" colspan="1"><input type="text" name="taxName" style="width: 80%" value="${taxName}" oncheck="notNone;" readonly="readonly">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectTax()">
                <font color="#FF0000">*</font>
                </td>
            </tr>
            
            <tr class="content1">
			    <td width="15%" align="center">所属纳税:</td>
                <td align="left" colspan="1"><select name="dutyId"
                    class="select_class" values="${dutyId}" style="width: 80%">
                   <p:option type="$dutyList" empty="true"/>
                </select></td>
                
                <td width="15%" align="center"></td>
                <td align="left" colspan="1">
                </td>
            </tr>
            
            <tr class="content1">
                <td width="15%" align="center">职员</td>
                <td align="left" colspan="1"><input type="text" name="stafferName" style="width: 70%" value="${stafferName}" readonly="readonly">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectStaffer()">&nbsp;
                <input type="button" value="&nbsp;C&nbsp;" name="qout_c" id="qout_c"
                    class="button_class" onclick="clearStaffer()">
                </td>
                
                <td width="15%" align="center">部门(5)</td>
                <td align="left" colspan="1"><input type="text" name="departmentName" style="width: 70%" value="${departmentName}" readonly="readonly">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectDepartment()">&nbsp;
                <input type="button" value="&nbsp;C&nbsp;" name="qout_c1" id="qout_c1"
                    class="button_class" onclick="clearDepartment()">
                </td>
            </tr>
            
             <tr class="content2">
                <td width="15%" align="center">产品</td>
                <td align="left" colspan="1"><input type="text" name="productName" style="width: 70%" value="${productName}" readonly="readonly">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectProduct()">&nbsp;
                <input type="button" value="&nbsp;C&nbsp;" name="qout_p" id="qout_p"
                    class="button_class" onclick="clearProduct()">
                </td>
                
                <td width="15%" align="center">仓库</td>
                <td align="left" colspan="1">
	                <select name="depotId" class="select_class" style="width: 240px" values="${depotId}">
	                <option value="">--</option>
	                    <p:option type="g_tax_depotList"></p:option>
	                </select>
                </td>
            </tr>
            
             <tr class="content2">
                <td width="15%" align="center">单位</td>
                <td align="left" colspan="1"><input type="text" name="unitName" style="width: 70%" value="${unitName}" readonly="readonly">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectUnit()">&nbsp;
                <input type="button" value="&nbsp;C&nbsp;" name="qout_u" id="qout_u"
                    class="button_class" onclick="clearUnit()">
                </td>
                
                <td width="15%" align="center"></td>
                <td align="left" colspan="1">
                </td>
            </tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="button" onclick="query()"
					class="button_class" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;<input
					type="button" class="button_class" onclick="resetAll()"
					value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
			</tr>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>分类账：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="senfe">
			<tr align=center class="content0">
				<td align="center" width="8%" class="td_class" onclick="tableSort(this)"><strong>日期</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>凭证</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>科目</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>摘要</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>借方金额</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>贷方金额</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>借/贷</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>余额</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>辅助</strong></td>
			</tr>

			<c:forEach items="${resultList}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="left" width="8%"  onclick="hrefAndSelect(this)">${item.financeDate}</td>
					<td align="left" onclick="hrefAndSelect(this)">
					<a href="../finance/finance.do?method=findFinance&id=${item.pid}">${item.pid}</a>
					</td>
					<td align="left" onclick="hrefAndSelect(this)">${item.taxId} ${item.taxName}</td>
					<td align="left" onclick="hrefAndSelect(this)">${item.description}</td>
					<td align="left" width="8%" onclick="hrefAndSelect(this)" title="${item.showChineseInmoney}">${item.showInmoney}</td>
					<td align="left" width="8%" onclick="hrefAndSelect(this)" title="${item.showChineseOutmoney}">${item.showOutmoney}</td>
					<td align="left" onclick="hrefAndSelect(this)">${item.forwardName}</td>
					<td align="left" width="8%" onclick="hrefAndSelect(this)" title="${item.showChineseLastmoney}">${item.showLastmoney}</td>
					<td align="left" onclick="hrefAndSelect(this)">${item.departmentName}/${item.stafferName}/${item.unitName}/${item.productName}/${item.depotName}/${item.duty2Name}</td>
				</tr>
			</c:forEach>
		</table>
		
		<p:formTurning form="formEntry" method="queryTaxFinance1"></p:formTurning>

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

