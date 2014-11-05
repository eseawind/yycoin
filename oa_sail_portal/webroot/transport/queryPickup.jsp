<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="出库单列表" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/adapter.js"></script>
<script language="javascript">
function process()
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}
	
	$l('../sail/ship.do?method=findConsign&fullId=' + getRadioValue('pickupId') + '&gid=' + getRadio('pickupId').pgid);
}

function batchPagePrint()
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}

	$l('../sail/ship.do?method=findPickup&printMode=0&printSmode=0&compose=2&pickupId=' + getRadioValue("pickupId") + '&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
}

function pickupPagePrint()
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}

	$l('../sail/ship.do?method=findPickup&printMode=1&printSmode=1&compose=2&pickupId=' + getRadioValue("pickupId") + '&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
}

function packagePagePrint()
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}

	$l('../sail/ship.do?method=findNextPackage&printMode=0&printSmode=1&compose=2&pickupId=' + getRadioValue("pickupId") + '&index_pos=0&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
}

function receiptPagePrint(flag)
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}

	if (flag == '1')
		$l('../sail/ship.do?method=findOutForReceipt&compose=1&pickupId=' + getRadioValue("pickupId") + '&index_pos=0&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
	else
		$l('../sail/ship.do?method=findOutForReceipt&compose=2&pickupId=' + getRadioValue("pickupId") + '&index_pos=0&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
}

function sub()
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}

	$O('method').value = 'mUpdateStatus';

	$O('pickupId').value = getRadioValue("pickupId");

	formEntry.submit();
}

function query()
{
	var transport1 = $$('transport1');
	var transport2 = $$('transport2');

	if (transport1 != '' || transport2 != '')
	{
		if ($$('shipment') != '')
		{
			if ($$('shipment') == 0 || $$('shipment') == 1)
			{
				alert('选择快递或货运时发货方式不能为自提与公司');

				return false;
			} 
		}
	}

	formEntry.submit();
}

function selectPrincipalship()
{
    window.common.modal('../admin/pop.do?method=rptQueryPrincipalship&load=1&selectMode=1');
}

function getPrincipalship(oos)
{
    var oo = oos[0];

    $O('industryId').value = oo.value;
    $O('industryName').value = oo.pname;   
}

function clears()
{
	$O('industryId').value = '';
	$O('industryName').value = '';
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/ship.do"><input
	type="hidden" name="method" value="queryPickup"> 
	<input type="hidden" value="1" name="firstLoad">
	<input type="hidden" value="" name="pickupId">
	<input type="hidden" value="${ppmap.industryId}"
	name="industryId">
	
	<p:navigation
	height="22">
	<td width="550" class="navigation">出库单管理 &gt;&gt; 出库单列表</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr class="content1">
				<td width="15%" align="center">批次号</td>
				<td align="left">
				<input type="text" name="batchId" maxlength="40" size="30" value="${ppmap.batchId}">
				</td>
				<td width="15%" align="center">发货方式</td>
				<td align="left">
					<select name="shipment"	class="select_class" style="width:50%" values="${ppmap.shipment}">
					<option value="">--</option>
					<option value="0">自提</option>
					<option value="1">公司</option>
					<option value="2">第三方快递</option>
					<option value="3">第三方货运</option>
					<option value="4">第三方快递+货运</option>
				</select>
				</td>
			</tr>
			
			<tr class="content2">
				<td width="15%" align="center">快递</td>
				<td align="left">
				<select name="transport1" quick=true class="select_class" style="width:50%" values="${ppmap.transport1}">
					<option value="">--</option>
					<c:forEach items="${expressList}" var="item">
						<c:if test="${item.type==0 || item.type == 99}">
							<option value="${item.id}">${item.name}</option>
						</c:if>
					</c:forEach>
				</select>
				</td>
				<td width="15%" align="center">货运</td>
				<td align="left">
				<select name="transport2" quick=true class="select_class" style="width:50%" values="${ppmap.transport2}">
					<option value="">--</option>
					<c:forEach items="${expressList}" var="item">
						<c:if test="${item.type==1 || item.type == 99}">
							<option value="${item.id}">${item.name}</option>
						</c:if>
					</c:forEach>
				</select>
				</td>
			</tr>

			<tr class="content1">
				<td width="15%" align="center">收货人</td>
				<td align="left">
				<input name="receiver" size="30"  value="${ppmap.receiver}"/>
				</td>
				<td width="15%" align="center">联系电话</td>
				<td align="left">
				<input name="mobile" size="30"  value="${ppmap.mobile}"/>
				</td>
			</tr>

			<tr class="content2">
				<td width="15%" align="center">出库单号</td>
				<td align="left">
				<input name="packageId" size="30"  value="${ppmap.packageId}"/>
				</td>
				<td width="15%" align="center">状态</td>
				<td align="left">
				<select name="currentStatus" class="select_class" style="width:50%" values="${ppmap.currentStatus}">
					<option value="">--</option>
					<option value="1">已拣配</option>
					<option value="3">已打印</option>
					<option value="2">已发货</option>
					<option value="4">已拣配/打印</option>
				</select>
				</td>
			</tr>
			
			<tr class="content1">
				<td width="15%" align="center">仓库地点</td>
				<td align="left">
				<select name="location" class="select_class" style="width:50%" values="${ppmap.location}">
					<p:option type="$adminindustryList" empty="true"></p:option>
				</select>
				</td>
				
				<td width="15%" align="center">事业部</td>
                <td align="left">
                        <input type="text" name="industryName" value="${ppmap.industryName}" readonly="readonly" onClick="selectPrincipalship()">
                        <input
								type="button" value="清空" name="qout" id="qout"
								class="button_class" onclick="clears()">&nbsp;&nbsp;
                </td>
			</tr>
			
			<tr class="content2">
				<td width="15%" align="center">销售单号</td>
				<td align="left"><input type="text" name="outId" value="${ppmap.outId}" style="width:50%"></td>
				
				<td width="15%" align="center">紧急</td>
                <td align="left">
                <select name="emergency" class="select_class" style="width:50%" values="${ppmap.emergency}">
					<option value="">--</option>
					<option value="0">不紧急</option>
					<option value="1">紧急</option>
				</select>
                </td>
			</tr>
			
			<tr class="content1">
				<td colspan="4" align="right"><input type="button"
					class="button_class" onclick="query()" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;<input
					type="reset" class="button_class"
					value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
			</tr>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>出库单列表</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class">选择</td>
				<td align="center" class="td_class" ><strong>批次号</strong></td>
				<td align="center" class="td_class" ><strong>系列号</strong></td>
				<td align="center" class="td_class" ><strong>出库单</strong></td>
				<td align="center" class="td_class" ><strong>状态</strong></td>
				<td align="center" class="td_class" ><strong>事业部</strong></td>
				<td align="center" class="td_class" ><strong>发货方式</strong></td>
				<td align="center" class="td_class" ><strong>快递公司</strong></td>
				<td align="center" class="td_class" ><strong>货运公司</strong></td>
				<td align="center" class="td_class" ><strong>收货人</strong></td>
				<td align="center" class="td_class" ><strong>收货电话</strong></td>
				<td align="center" class="td_class" ><strong>仓库地点</strong></td>
			</tr>
			
			<c:forEach items="${itemList}" var="item" varStatus="vs">
                <tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
                    <td align="center"><input type="radio" name="pickupId"
                        value="${item.pickupId}"
                        ${vs.index== 0 ? "checked" : ""}/></td>
                    <td align="center" onclick="hrefAndSelect(this)">${item.pickupId}</td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                </tr>
                <c:forEach items="${item.packageList}" var="item2" varStatus="vs2">
                    <tr class="${vs2.index % 2 == 0 ? 'content1' : 'content2'}">
                    <td align="center"></td>
                    <td align="center">--</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item2.index_pos}</td>
                    <td align="center" onclick="hrefAndSelect(this)">
                    <a
                        href="../sail/ship.do?method=findPackage&packageId=${item2.id}"
                        >${item2.id}</a></td>
                    <td align="center" onclick="hrefAndSelect(this)">${my:get('shipStatus', item2.status)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item2.industryName}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${my:get('outShipment',item2.shipping)}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item2.transportName1}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item2.transportName2}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item2.receiver}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item2.mobile}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item2.locationName}</td>
                </tr>
                </c:forEach>
                
            </c:forEach>
            
		</table>

		<p:formTurning form="formEntry" method="queryPickup"></p:formTurning>
		
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="98%" rightWidth="2%">
		
		<div align="right">
			<input type="button" 
			class="button_class" onclick="pickupPagePrint()"
			value="&nbsp;&nbsp;打印批次出库单&nbsp;&nbsp;">&nbsp;&nbsp;
			<input type="button" 
			class="button_class" onclick="packagePagePrint()"
			value="&nbsp;&nbsp;打印客户出库单&nbsp;&nbsp;">&nbsp;&nbsp;
			<input type="button" 
			class="button_class" onclick="receiptPagePrint(2)"
			value="&nbsp;&nbsp;打印回执单&nbsp;&nbsp;">&nbsp;&nbsp;
			<input type="button" 
			class="button_class" onclick="receiptPagePrint(1)"
			value="&nbsp;&nbsp;打印回执单(含配件)&nbsp;&nbsp;">&nbsp;&nbsp;
			<input type="button" 
			class="button_class" onclick="batchPagePrint()"
			value="&nbsp;&nbsp;批量打印&nbsp;&nbsp;">&nbsp;&nbsp;
			<input type="button" 
			class="button_class" onclick="sub()"
			value="&nbsp;&nbsp;确认发货&nbsp;&nbsp;">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message2 />

</p:body></form>

</body>
</html>

