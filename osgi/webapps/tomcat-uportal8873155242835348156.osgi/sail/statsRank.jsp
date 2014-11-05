<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="统计发货排名" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src='../js/adapter.js'></script>
<script language="javascript">

function query()
{
	adminForm.submit();
}

function getCustomer(oos)
{
    var obj = oos;
    
	$O("customerName").value = obj.pname;
	
	$O("customerId").value = obj.value;
}

function selectCustomer()
{
    window.common.modal('../client/client.do?method=rptQueryAllClient&type=1&first=1&load=1');
}

function res()
{
	$O('customerName').value = '';
	$O("customerId").value = '';
}


function load()
{
	loadForm();
}

function checkChange(obj, type)
{
	var tr1 = getTrObject(obj);

	var idObj = getInputInTr(tr1, "id");

	var id = idObj.value;

	//alert("id:" + id);
	
	if (type == 0)
	{
		var checkObj1 = getInputInTr(tr1, "humanSort");
		
		if (checkObj1.checked)
		{
			$ajax('../sail/out.do?method=updateStatsRank&id=' + id + '&type=humanSort&value=1', callBackFun);
		}else{
			$ajax('../sail/out.do?method=updateStatsRank&id=' + id + '&type=humanSort&value=0', callBackFun);
		}
	}

	if (type == 1)
	{
		var checkObj2 = getInputInTr(tr1, "baseOutTime");
		
		if (checkObj2.checked)
		{
			$ajax('../sail/out.do?method=updateStatsRank&id=' + id + '&type=baseOutTime&value=1', callBackFun);
		}else{
			$ajax('../sail/out.do?method=updateStatsRank&id=' + id + '&type=baseOutTime&value=0', callBackFun);
		}
	}
}

function callBackFun()
{
	//
}
</script>

</head>
<body class="body_class" onload="load()">
<form action="../sail/out.do" name="adminForm">
<input type="hidden"
	value="statsRank" name="method"> 
	<input type="hidden" value="1"
	name="firstLoad">
	<input type="hidden" value="${customerId}"
	name="customerId">
	<input type="hidden" value="${batchId}"
	name="batchId">

<p:navigation
    height="22">
    <td width="550" class="navigation">库单管理 &gt;&gt; 销售单</td>
                <td width="85"></td>
</p:navigation> <br>

<table width="98%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content1">
						<td width="15%" align="center">开始时间</td>
						<td align="center" width="35%"><p:plugin name="outTime" size="20" value="${outTime}"/></td>
						<td width="15%" align="center">结束时间</td>
						<td align="center"><p:plugin name="outTime1" size="20" value="${outTime1}"/>
						</td>
					</tr>

					<tr class="content1">
						<td width="15%" align="center">客户：</td>
						<td align="center"><input type="text" name="customerName" maxlength="14" value="${customerName}"
							onclick="selectCustomer()" style="cursor: pointer;"
							readonly="readonly"></td>
						<td width="15%" align="center"></td>
						<td align="center">
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr class="content2">

				<td colspan="4" align="right"><input type="button" id="query_b"
					onclick="query()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;
					<input type="button" onclick="res()" class="button_class" value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td valign="top" colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--DWLayoutTable-->
			<tr>
				<td width="784" height="6"></td>
			</tr>
			<tr>
				<td align="center" valign="top">
				<table width="90%" border="0" cellspacing="2">
					<tr>
						<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="10">
							<tr>
								<td width="35">&nbsp;</td>
								<td width="6"><img src="../images/dot_r.gif" width="6"
									height="6"></td>
								<td class="caption"><strong>销售单:</strong></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="mainTable">
					<tr align="center" class="content0">
						<td align="center" width="8%" align="center">强制优先</td>
						<td align="center" width="12%" align="center">按订单日期配送</td>
						<td align="center" onclick="tableSort(this)" class="td_class">客户</td>
						<td align="center" onclick="tableSort(this)" class="td_class">订单日期</td>
						<td align="center" onclick="tableSort(this)" class="td_class">商品</td>
						<td align="center" onclick="tableSort(this)" class="td_class">数量</td>
					</tr>

					<c:forEach items="${listOut}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
						
							<td align="center"><input type="checkbox" name="humanSort" 
												${item.humanSort == 1 ? 'checked' : ''} value="${item.humanSort}" onclick="checkChange(this,0)"/>
											   <input type="hidden" name="id" value="${item.id}">
							</td>
							<td align="center"><input type="checkbox" name="baseOutTime" 
												${item.baseOutTime == 1 ? 'checked' : ''} value="${item.baseOutTime}" onclick="checkChange(this,1)"/>
							</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.customerName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.productName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.amount}</td>
							
						</tr>
					</c:forEach>
				</table>

				<p:formTurning form="adminForm" method="queryStatsRankPage"></p:formTurning>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<c:if test="${my:length(listOut) > 0}">
	<tr>
		<td width="100%">
		<div align="right">
		<input type="button" class="button_class" align="right"
			value="&nbsp;&nbsp;排  序&nbsp;&nbsp;" onClick="sort()">&nbsp;&nbsp;
		</div>
		</td>
	</tr>
	
	</c:if>

	<tr height="10">
		<td height="10" colspan='2'></td>
	</tr>

	<p:message2/>
</table>

</form>

</body>
</html>
