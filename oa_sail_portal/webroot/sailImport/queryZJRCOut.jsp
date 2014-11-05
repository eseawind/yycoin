<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="查询销售单"/>
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/json.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/adapter.js"></script>
<script language="javascript">
//var distMap = JSON.parse('${mapdJSON}');

function comp()
{
	var now = '${now}';

	var str1 = $O('outTime').value;

	var str2 = $O('outTime1').value;
	
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

function query()
{
	var str1 = $O('outTime').value;

	var str2 = $O('outTime1').value;

	//必须要有开始和结束时间一个
	if (str1 == '' && str2 == '' )
	{
		alert('必须要选择一种时间');
		return false;
	}
	
    getObj('method').value = 'queryZJRCOut';
    
	adminForm.submit();
}

function res()
{
	$O("id").value = '';
	$O("stafferName").value = '';
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
    var ll = document.getElementsByName('fullId');
    
    for (var i = 0 ; i < ll.length; i++)
    {
        ll[i].index = $$('radioIndex');
    }
    
	loadForm();
	tooltip.init();
	
	bingTable("mainTable");
}

function isEnd()
{
    return getRadio('fullId').statuss == 3 || getRadio('fullId').statuss == 4;
}

//选择自动联想radio
function hrefAndSelect(obj)
{
    //$Set(radio, obj.indexs);
    var tr = getTrObject(obj);

    if (tr != null)
    {
        var rad = tr.getElementsByTagName('input');

        for (i = 0; i < rad.length; i++)
        {
            if (rad[i].type.toLowerCase() == 'checkbox')
            {
                rad[i].checked = !rad[i].checked;
            }
            
            if (rad[i].type.toLowerCase() == 'radio')
            {
                rad[i].checked = true;
            }
        }
    }
    
    getObj('radioIndex').value = $Index('fullId');
}

var showTr = false;

function showQueryConditionTr()
{
    $v('queryCondition', showTr);
    
    showTr = !showTr;

    if (showTr){

        $O("queryConditionText").innerHTML = '显示查询条件';
    }
    else{
    	$O("queryConditionText").innerHTML = '隐藏查询条件';
    }
}

function createOA()
{
	if (getRadio('fullId').statuss == '1')
	{
		document.location.href = '../sail/extout.do?method=createOA&outId=' + getRadioValue("fullId");
	}
	else
	{
		alert('只有提交状态的单子可以手工生成OA单!');
	}
}

function receive(flag)
{
	if (flag == '1')
	{
		if (getRadio('fullId').statuss == '3')
		{
			if (window.confirm("确定该操作?"))
			 {
			 	$O('method').value = 'modifyOutStatus';
			 	$O('oldStatus').value = getRadio('fullId').statuss;
			 	$O('statuss').value = '4';
			 	$O('outId').value = getRadioValue("fullId");
			 	
			 	disableAllButton();
			 	adminForm.submit();
			 }
		}else{
			alert('此状态不能操作!');
		}
	}else {
		if (getRadio('fullId').statuss == '4')
		{
			if (window.confirm("确定该操作?"))
			 {
			 	$O('method').value = 'modifyOutStatus';
			 	$O('oldStatus').value = getRadio('fullId').statuss;
			 	$O('statuss').value = '5';
			 	$O('outId').value = getRadioValue("fullId");
			 	
			 	disableAllButton();
			 	adminForm.submit();
			 }
		}else{
			alert('此状态不能操作!');
		}
	}
}

</script>

</head>
<body class="body_class" onkeypress="tooltip.bingEsc(event)" onload="load()">
<form action="../sail/extout.do" name="adminForm"><input type="hidden"
	value="queryZJRCOut" name="method"> <input type="hidden" value="1"
	name="firstLoad">
	<input type="hidden" value="${ppmap.customerId}"
	name="customerId">
	<input type="hidden" value="${queryType}"
	name="queryType">
	<input type="hidden" value=""
	name="outId">
<input type="hidden" value="" name="oldStatus">
<input type="hidden" value="" name="statuss">
<input type="hidden" value="${ppmap.radioIndex}" name="radioIndex">
<input type="hidden" value="" name="reason">
<input type="hidden" value="" name="baddebts">
<input type="hidden" value="" name="invoiceMoney">
<input type="hidden" value="${ppmap.industryId}"
	name="industryId">

<c:set var="fg" value='销售'/>

<p:navigation
    height="22">
    <td width="550" class="navigation">库单管理 &gt;&gt; 查询销售单</td>
                <td width="85"></td>
</p:navigation> <br>

<table width="98%" border="0" cellpadding="0" cellspacing="0"
	align="center">

	<p:title>
		<td class="caption">	
		<strong><span id="queryConditionText" style="cursor: pointer;" onclick="showQueryConditionTr()">隐藏查询条件</span></strong>
		</td>
	</p:title>

	<tr id="queryCondition">
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
						<td width="15%" align="center">销售人员</td>
						<td align="center"><input type="text" name="stafferName" value="${stafferName}"></td>
						<td width="15%" align="center">销售单号</td>
						<td align="center"><input type="text" name="id" value="${id}"></td>
					</tr>
					
					<tr class="content1">
						<td width="15%" align="center">收货人</td>
						<td align="center"><input type="text" name="receiver" value="${receiver}"></td>
						<td width="15%" align="center">收货人电话</td>
						<td align="center"><input type="text" name="handphone" value="${handphone}"></td>
					</tr>
					
					<tr class="content2">
						<td width="15%" align="center">状态</td>
						<td align="center">
						<select name="status" class="select_class" values="${status}">
							<option value="">--</option>
							<p:option type="zjrcOutStatus"/>
						</select>
						</td>
						<td width="15%" align="center"></td>
						<td></td>
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

				<p:formTurning form="adminForm" method="queryZJRCOut"></p:formTurning>
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
	
	<c:if test="${my:length(listOut1) > 0 && my:auth(user, '9033')}">
	<tr>
		<td width="100%">
		<div align="right">
			<c:if test="${my:auth(user, '9033')}">
			<input type="button" class="button_class"
				value="&nbsp;&nbsp;手工生成OA单&nbsp;&nbsp;" onclick="createOA()" />&nbsp;&nbsp;
			</c:if>
			<c:if test="${my:auth(user, '9034')}">
			<input type="button" class="button_class"
				value="&nbsp;&nbsp;银行签收&nbsp;&nbsp;" onclick="receive(1)" />&nbsp;&nbsp;
			</c:if>
			<c:if test="${my:auth(user, '9035')}">
			<input type="button" class="button_class"
				value="&nbsp;&nbsp;客户签收&nbsp;&nbsp;" onclick="receive(2)" />&nbsp;&nbsp;
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
