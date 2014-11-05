<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="查询入库单" />
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script language="javascript">
function detail()
{
	document.location.href = '../sail/out.do?method=findOut&outId=' + getRadioValue("fullId");
}

function pagePrint()
{
	window.open('../sail/out.do?method=findOut&fow=4&outId=' + getRadioValue("fullId"));
}

function selectCustomer()
{
    window.common.modal("../provider/provider.do?method=rptQueryProvider&load=1");
}

function exports()
{
	if (window.confirm("确定导出当前的全部查询的库单?"))
	document.location.href = '../sail/out.do?method=export&flags=1';
}

function mark()
{
	if (window.confirm("确定标记当前选择的库单?"))
	{
		adminForm.action = '../sail/out.do';
		adminForm.method.value = 'mark';
		adminForm.outId.value = getRadioValue("fullId");

		adminForm.submit();
	}
}

function comp()
{
	var now = '${now}';

	var str1 = $O('outTime').value;

	var str2 = $O('outTime1').value;

	//必须要有开始和结束时间一个
	if (str1 == '' && str2 == '')
	{
		alert('必须要有开始和结束时间一个');
		return false;
	}

	if (str1 != '' && str2 == '')
	{
		if (!coo(str1, now))
		{
			alert('查询日期跨度不能大于3个月(90天)!');
			return false;
		}

		$O('outTime1').value = now;
	}

	if (str1 == '' && str2 != '')
	{
		if (!coo(now, str2))
		{
			alert('查询日期跨度不能大于3个月(90天)!');
			return false;
		}

		$O('outTime').value = now;
	}

	if (str1 != '' && str2 != '')
	{
		if (!coo(str1, str2))
		{
			alert('查询日期跨度不能大于3个月(90天)!');
			return false;
		}
	}

	return true;
}

function coo(str1, str2)
{
	var s1 = str1.split('-');
	var s2 = str2.split('-');

	var year1 = parseInt(s1[0], 10);

	var year2 = parseInt(s2[0], 10);

	var month1 = parseInt(s1[1], 10);

	var month2 = parseInt(s2[1], 10);

	var day1 = parseInt(s1[2], 10);

	var day2 = parseInt(s2[2], 10);

	return Math.abs((year2 - year1) * 365 + (month2 - month1) * 30 + (day2 - day1)) <= 90;
}

function modfiy()
{
	if ((getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2') && getRadio('fullId').outtype != 4 && getRadio('fullId').outtype != 5)
	{
		document.location.href = '../sail/out.do?method=findOut&outId=' + getRadioValue("fullId") + "&fow=1";
	}
	else
	{
		alert('只有保存态和驳回态的库单可以修改!');
	}
}

function del()
{
	if ((getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2' || getRadio('fullId').temptype == '1') && getRadio('fullId').outtype != 4
	       && getRadio('fullId').outtype != 5)
	{
		 if (window.confirm("确定删除入库单?"))
		document.location.href = '../sail/out.do?method=delOut&outId=' + getRadioValue("fullId");
	}
	else
	{
		alert('只有保存态和驳回态的库单可以删除!');
	}
}

function sub()
{
    //个人领样的退货不能再次提交
	if ((getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2') && getRadio('fullId').outtype != '4' && getRadio('fullId').outtype != 5)
	{
		 if (getRadioValue('fullId').indexOf('TM') != -1) 
		 {
			 alert('TM型单据需拆分,不能直接提交!点击修改');
			 return ;
		 }
		
		 if (window.confirm("确定提交入库单?"))
		 {
		 	$O('method').value = 'modifyOutStatus';
		 	$O('oldStatus').value = getRadio('fullId').statuss;
		 	$O('statuss').value = '1';
		 	$O('outId').value = getRadioValue("fullId");
		 	
		 	disableAllButton();
		 	adminForm.submit();
		 }
	}
	else
	{
		alert('此状态不能提交!');
	}
}

function query()
{
	if (comp())
	{
		adminForm.submit();
	}
}

function getProvider(value, name)
{
	$O("customerName").value = name;
	
	$O("customerId").value = value;
}

function res()
{
	$O('customerName').value = '';
	$O("customerId").value = '';
	$O("id").value = '';
	setSelectIndex($O('outType'), 0);
	setSelectIndex($O('inway'), 0);
	setSelectIndex($O('location'), 0);
}

var jmap = new Object();
<c:forEach items="${listOut1}" var="item">
	jmap['${item.fullId}'] = "${divMap[item.fullId]}";
</c:forEach>

function showDiv(id)
{
	tooltip.showTable(jmap[id]);
}

function load()
{
	loadForm();
	tooltip.init();
	
	bingTable("mainTable");
	
	highlights($("#mainTable").get(0), ['驳回', '在途中'], 'red');
}

</script>

</head>
<body class="body_class" onkeypress="tooltip.bingEsc(event)" onload="load()">
<form action="../sail/out.do" name="adminForm"><input type="hidden"
	value="querySelfBuy" name="method"> <input type="hidden" value="1"
	name="firstLoad">
	<input type="hidden" value="${customerId}"
	name="customerId">
	<input type="hidden" value="${queryType}"
	name="queryType">
	<input type="hidden" value=""
	name="outId">
	<input type="hidden" value=""
    name="oldStatus">
	<input type="hidden" value=""
	name="statuss">
<c:set var="fg" value='入库'/>

<p:navigation
    height="22">
    <td width="550" class="navigation">库单管理 &gt;&gt; 我的入库单</td>
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

					<tr class="content2">
						<td width="15%" align="center">入库单状态</td>
						<td align="center">
						<c:if test="${!ff}">
						<select name="status" class="select_class" values="${status}">
							<option value="">--</option>
							<p:option type="buyStatus"/>
							<option value="99">发货态</option>
						</select>
						</c:if>

						</td>
						<td width="15%" align="center">供应商：</td>
                        <td align="center"><input type="text" name="customerName" maxlength="14" value="${customerName}"></td>
					</tr>

					<tr class="content1">
						<td width="15%" align="center">入库类型</td>
						<td align="center">
						<select name="outType"
							class="select_class" values=${outType}>
							<option value="">--</option>
							<p:option type="outType_in"/>
						</select>

						</td>
						<td width="15%" align="center">入库单号</td>
						<td align="center"><input type="text" name="id" value="${id}"></td>
					</tr>

					<tr class="content2">
						<td width="15%" align="center">在途方式</td>
                        <td colspan="1" align="center">
                        <select name="inway" values="${inway}"
                            class="select_class">
                            <option value="">--</option>
                            <p:option type="inway"></p:option>
                            </select>
                        </td>
						
						<td width="15%" align="center">仓库</td>
                        <td align="center">
                        <select name="location"
                            class="select_class" values=${location}>
                            <option value="">--</option>
                            <c:forEach items="${depotList}" var="item">
                             <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        </td>
					
					</tr>

					<tr class="content1">

						<td colspan="4" align="right"><input type="button" id="query_b"
							onclick="query()" class="button_class"
							value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;
							<input type="button" onclick="res()" class="button_class" value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
					</tr>
				</table>
				</td>
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
				<div align="left">
				<table width="90%" border="0" cellspacing="2">
					<tr>
						<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="10">
							<tr>
								<td width="35">&nbsp;</td>
								<td width="6"><img src="../images/dot_r.gif" width="6"
									height="6"></td>
								<td class="caption"><strong>浏览${fg}单:</strong><font color=blue>[当前查询数量:${my:length(listOut1)}]</font></td>
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
						<td align="center" width="5%" align="center">选择</td>
						<td align="center" onclick="tableSort(this)" class="td_class">单据编号</td>
						<td align="center" onclick="tableSort(this)" class="td_class">供应商</td>
						<td align="center" onclick="tableSort(this)" class="td_class">状态</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}类型</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}时间</td>
						<td align="center" onclick="tableSort(this)" class="td_class">金额</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}人</td>
						<td align="center" onclick="tableSort(this)" class="td_class">在途</td>
						<td align="center" onclick="tableSort(this)" class="td_class">仓库</td>
					</tr>

					<c:forEach items="${listOut1}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'
						>
							<td align="center"><input type="radio" name="fullId" 
							    temptype="${item.tempType}"
							    outtype="${item.outType}"
								statuss='${item.status}' 
								value="${item.fullId}" ${vs.index== 0 ? "checked" : ""}/></td>
							<td align="center"
							onMouseOver="showDiv('${item.fullId}')" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"><a onclick="hrefAndSelect(this)" href="../sail/out.do?method=findOut&fow=99&outId=${item.fullId}">
							${item.fullId}</a></td>
							<td align="center">${item.customerName}</td>
							<td align="center">${my:get('buyStatus', item.status)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('outType_in', item.outType)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('inway', item.inway)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.depotName}</td>
						</tr>
					</c:forEach>
				</table>

				<p:formTurning form="adminForm" method="querySelfBuy"></p:formTurning>
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

	<c:if test="${my:length(listOut1) > 0}">
	<tr>
		<td width="100%">
		<div align="right">
		<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;修 改&nbsp;&nbsp;" onclick="modfiy()" />&nbsp;&nbsp;<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="sub()" />&nbsp;&nbsp; 
			<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;删 除&nbsp;&nbsp;" onclick="del()" />&nbsp;&nbsp;
	    <c:if test="${my:auth(user, '1506')}">
		<input
			type="button" class="button_class"
			value="&nbsp;导出查询结果&nbsp;" onclick="exports()" />
		</c:if>
			</div>
		</td>
		<td width="0%"></td>
	</tr>
	
	</c:if>

	<p:message2/>
</table>

</form>
</body>
</html>
