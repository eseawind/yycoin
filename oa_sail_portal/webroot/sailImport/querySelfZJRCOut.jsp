<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="查询销售单" link="true" guid="true" cal="true" dialog="true" />
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

function exports()
{
	if (window.confirm("确定导出当前的全部查询的库单?"))
	document.location.href = '../sail/out.do?method=export&flags=1';
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
	if (getRadio('fullId').statuss == '0')
	{
		document.location.href = '../sail/extout.do?method=findZJRCOut&outId=' + getRadioValue("fullId") + "&fow=1";
	}
	else
	{
		alert('只有保存态和驳回态的库单可以修改!');
	}
}

function ysPrint()
{
    document.location.href = '../sail/extout.do?method=ysqrdPrint&outId=' + getRadioValue("fullId") + "&fow=1";
}

function cjPrint()
{
    document.location.href = '../sail/extout.do?method=cjqrdPrint&outId=' + getRadioValue("fullId") + "&fow=1";
}

function del()
{
	//if (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '1')
	//{
	//	 if (window.confirm("确定删除销售单?"))
	//	document.location.href = '../sail/extout.do?method=deleteZJRCOut&outId=' + getRadioValue("fullId");
	//}
	//else
	//{
	//	alert('只有未生成OA订单的库单可以删除!');
	//} 
	
	if (window.confirm("确定删除销售单?"))
		document.location.href = '../sail/extout.do?method=deleteZJRCOut&outId=' + getRadioValue("fullId");
}

function query()
{
	if (comp())
	{
		adminForm.submit();
	}
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
}

</script>

</head>
<body class="body_class" onkeypress="tooltip.bingEsc(event)" onload="load()">
<form action="../sail/extout.do" name="adminForm"><input type="hidden"
	value="querySelfZJRCOut" name="method"> <input type="hidden" value="1"
	name="firstLoad">
	<input type="hidden" value="${customerId}"
	name="customerId">
	<input type="hidden" value="${queryType}"
	name="queryType">
	<input type="hidden" value="${GFlag}"
	name="flagg">
	<input type="hidden" value=""
	name="outId">
	<input type="hidden" value=""
    name="oldStatus">
	<input type="hidden" value=""
	name="statuss">
	<input type="hidden" value="1"
    name="selfQuery">
<c:set var="fg" value='销售'/>

<p:navigation
    height="22">
    <td width="550" class="navigation">库单管理 &gt;&gt; 我的销售单${vtype}</td>
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
						<td width="15%" align="center">销售单号</td>
						<td align="center"><input type="text" name="id" value="${id}"></td>
						<td width="15%" align="center">状态</td>
						<td align="center">
						<select name="status" class="select_class" values="${status}">
							<option value="">--</option>
							<p:option type="zjrcOutStatus"/>
						</select>
						</td>
					</tr>
					
					<tr class="content1">
						<td width="15%" align="center">收货人</td>
						<td align="center"><input type="text" name="receiver" value="${receiver}"></td>
						<td width="15%" align="center">收货人电话</td>
						<td align="center"><input type="text" name="handphone" value="${handphone}"></td>
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
						<td align="center" width="5%" align="center">选择</td>
						<td align="center" onclick="tableSort(this)" class="td_class">单据编号</td>
						<td align="center" onclick="tableSort(this)" class="td_class">客户</td>
						<td align="center" onclick="tableSort(this)" class="td_class">状态</td>
						<td align="center" onclick="tableSort(this)" class="td_class">时间</td>
						<td align="center" onclick="tableSort(this)" class="td_class">金额</td>
						<td align="center" onclick="tableSort(this)" class="td_class">业务员</td>
						<td align="center" onclick="tableSort(this)" class="td_class">收货人</td>
						<td align="center" onclick="tableSort(this)" class="td_class">收货人电话</td>
					</tr>

					<c:forEach items="${listOut1}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'
						>
							<td align="center"><input type="radio" name="fullId" 
							    outtype="${item.outType}"
								pcustomerid='${item.customerId}' 
								statuss='${item.status}' 
								value="${item.fullId}" ${vs.index== 0 ? "checked" : ""}/></td>
							<td align="center"
							onMouseOver="showDiv('${item.fullId}')" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"><a onclick="hrefAndSelect(this)" href="../sail/extout.do?method=findZJRCOut&fow=99&outId=${item.fullId}">
							${item.fullId}</a></td>
							<td align="center" onclick="hrefAndSelect(this)">${item.customerName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('zjrcOutStatus', item.status)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>
							
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.receiver}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.handPhone}</td>
						</tr>
					</c:forEach>
				</table>

				<p:formTurning form="adminForm" method="querySelfZJRCOut"></p:formTurning>
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
			<input type="button" class="button_class"
				value="&nbsp;&nbsp;修 改&nbsp;&nbsp;" onclick="modfiy()" />&nbsp;&nbsp;
			<input type="button" class="button_class"
				value="&nbsp;&nbsp;删 除&nbsp;&nbsp;" onclick="del()" />&nbsp;&nbsp;
            <input type="button" class="button_class"
                   value="&nbsp;&nbsp;预售确认单打印&nbsp;&nbsp;" onclick="ysPrint()" />&nbsp;&nbsp;
            <input type="button" class="button_class"
                   value="&nbsp;&nbsp;成交确认单打印&nbsp;&nbsp;" onclick="cjPrint()" />&nbsp;&nbsp;
			
		</div>
		</td>
		
		<td width="0%"></td>
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
