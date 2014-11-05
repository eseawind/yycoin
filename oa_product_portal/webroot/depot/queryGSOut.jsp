<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="查询金银料出入库" />
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


function query()
{
	//if (comp())
	//{
	    getObj('method').value = 'queryGSOut';
	    
		adminForm.submit();
	//}
}


function res()
{
	$O("gsOutId").value = '';
	
	setSelectIndex($O('status'), 0);
	
	setSelectIndex($O('type'), 0);
}

function load()
{
	loadForm();
}

function modfiy()
{
	if (getRadio('fullId').ptype == 0 && (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2'))
	{
		document.location.href = '../depot/storage.do?method=findGSOut&id=' + getRadioValue("fullId") + "&flow=1";
	}
	else
	{
		alert('只有保存态和驳回态的库单可以修改!');
	}
}

function reject()
{
    if (getRadio('fullId').statuss != 0 && getRadio('fullId').statuss != 2 && getRadio('fullId').statuss != 4)
    {
        $.messager.prompt('驳回', '请输入驳回原因', '', function(r){
                if (r)
                {
                    $Dbuttons(true);
                    getObj('method').value = 'modifyStatus';
                    getObj('id').value = getRadioValue("fullId");

                    getObj('statuss').value = '2';
                    getObj('oldStatus').value = getRadio('fullId').statuss;
        
                    var sss = r;
        
                    getObj('reason').value = r;
        
                    if (!(sss == null || sss == ''))
                    {
                        adminForm.submit();
                    }
                    else
                    {
                        $Dbuttons(false);
                    }
                }
               
            });
    }
    else
    {
        alert('不可以操作!');
    }
}

function pass()
{
    if (getRadio('fullId').statuss == 1 && window.confirm("确定通过?"))
    {
        getObj('method').value = 'modifyStatus';
        getObj('id').value = getRadioValue("fullId");

        getObj('statuss').value = '7';
        getObj('oldStatus').value = getRadio('fullId').statuss;
        
        adminForm.submit();
    }
    else
    {
        alert('不能操作');
    }
}

function passToDepot()
{
    if (getRadio('fullId').statuss == 7 && window.confirm("确定通过, 库存发生变化?"))
    {
        getObj('method').value = 'modifyStatus';
        getObj('id').value = getRadioValue("fullId");

        getObj('statuss').value = '4';
        getObj('oldStatus').value = getRadio('fullId').statuss;
        
        adminForm.submit();
    }
    else
    {
        alert('不能操作');
    }
}

function del()
{
    if (getRadio('fullId').statuss == 0 || getRadio('fullId').statuss == 2)
    {
        if (window.confirm("确定删除?"))
        {
        	getObj('method').value = 'delGSOut';
            
            getObj('id').value = getRadioValue("fullId");
            
            adminForm.submit();
        }
    }
    else
    {
        alert('不能操作');
    }
}

function trans()
{
	if (getRadio('fullId').statuss == 4 && window.confirm("确定出库转为入库?"))
    {
		document.location.href = '../depot/storage.do?method=findGSOut&id=' + getRadioValue("fullId") + "&flow=2";
    }
    else
    {
        alert('不能操作');
    }
}

function exports()
{
	if (window.confirm("确定导出当前的全部查询的库单?"))
	document.location.href = '../depot/storage.do?method=exportGS';
}

</script>

</head>
<body class="body_class" onload="load()">
<form action="../depot/storage.do" name="adminForm">
<input type="hidden"
	value="queryGSOut" name="method"> <input type="hidden" value="1"
	name="firstLoad">
	<input type="hidden" value="${queryType}" name="queryType">
	<input type="hidden" value="" name="id">
<input type="hidden" value="" name="oldStatus">
<input type="hidden" value="" name="statuss">
<input type="hidden" value="" name="reason" >

<p:navigation
    height="22">
    <td width="550" class="navigation">库存管理 &gt;&gt; 金银料出入库</td>
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
						<td width="15%" align="center">时间从</td>
						<td align="center" width="35%"><p:plugin name="outTime" size="20" value="${outTime}"/></td>
						<td width="15%" align="center">到</td>
						<td align="center"><p:plugin name="outTime1" size="20" value="${outTime1}"/>
						</td>
					</tr>
					
					<tr class="content1">
						<td width="15%" align="center">状态</td>
						<td align="center">
						<select name="status" class="select_class" values="${status}">
							<option value="">--</option>
							<p:option type="gsOutStatus"/>
						</select>
						</td>
						
						<td width="15%" align="center">单号</td>
                        <td align="center"><input type="text" name="gsOutId" value="${id}"></td>
					</tr>
					
					<tr class="content2">
                        <td width="15%" align="center">类型</td>
                        <td align="center">
                        <select name="type" class="select_class" values="${type}">
                            <option value="">--</option>
                            <option value="0">出库</option>
                            <option value="1">入库</option>
                        </select>
                        </td>
                        
                        <td width="15%" align="center">职员</td>
						<td align="center"><input type="text" name="stafferName" value="${stafferName}"></td>
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
	
	<p:title>
        <td class="caption"><strong>清单</strong></td>
    </p:title>

    <p:line flag="0" />

	<tr>
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="mainTable">
					<tr align="center" class="content0">
						<td align="center" width="5%" align="center">选择</td>
						<td align="center" onclick="tableSort(this)" class="td_class">单号</td>
						<td align="center" onclick="tableSort(this)" class="td_class">状态</td>
						<td align="center" onclick="tableSort(this)" class="td_class">类型</td>
						<td align="center" onclick="tableSort(this)" class="td_class">金料总克数</td>
						<td align="center" onclick="tableSort(this)" class="td_class">银料总克数</td>
						<td align="center" onclick="tableSort(this)" class="td_class">提交人</td>
						<td align="center" onclick="tableSort(this)" class="td_class">关联单号</td>
						<td align="center" onclick="tableSort(this)" class="td_class">时间</td>
					</tr>

					<c:forEach items="${resultList}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'
						>
							<td align="center"><input type="radio" name="fullId" 
								statuss='${item.status}' 
								ptype='${item.type}' 
								value="${item.id}" ${vs.index== 0 ? "checked" : ""}/></td>
							<td align="center" onclick="hrefAndSelect(this)">
                            <a href="../depot/storage.do?method=findGSOut&flow=99&id=${item.id}">
                            	${item.id}
                            </a>
                            </td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('gsOutStatus', item.status)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('storageGSType', item.type)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.gtotal}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.stotal}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.refId}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
						</tr>
					</c:forEach>
				</table>

				<p:formTurning form="adminForm" method="queryGSOut"></p:formTurning>
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

	<c:if test="${my:length(resultList) > 0}">
	<tr>
		<td width="100%">
		<div align="right">
		
		<c:if test="${queryType == '1'}">
			<input
				type="button" class="button_class"
				value="&nbsp;&nbsp;修 改&nbsp;&nbsp;" onclick="modfiy()" />&nbsp;&nbsp;
			<input type="button" class="button_class"
				value="&nbsp;&nbsp;删 除&nbsp;&nbsp;" onClick="del()">&nbsp;&nbsp;
				
			<input type="button" class="button_class"
				value="&nbsp;&nbsp;出库转入库&nbsp;&nbsp;" onClick="trans()">&nbsp;&nbsp;				
		</c:if>
		
		<c:if test="${queryType == '2'}">
		<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;通 过&nbsp;&nbsp;" onclick="pass()" />&nbsp;&nbsp; 
			<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;驳 回&nbsp;&nbsp;" onclick="reject()" />&nbsp;&nbsp;
		</c:if>
		
		<c:if test="${queryType == '3'}">
        <input
            type="button" class="button_class"
            value="&nbsp;&nbsp;通过入库&nbsp;&nbsp;" onclick="passToDepot()" />&nbsp;&nbsp; 
            <input
            type="button" class="button_class"
            value="&nbsp;&nbsp;驳 回&nbsp;&nbsp;" onclick="reject()" />&nbsp;&nbsp;
        </c:if>
        
        <c:if test="${queryType == '0'}">
        <input
                type="button" class="button_class"
                value="&nbsp;导出查询结果&nbsp;" onclick="exports()" />&nbsp;&nbsp;
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
