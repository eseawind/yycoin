<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="发货单列表" />
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
	if (getRadioValue('consigns') == '')
	{
		alert('请选择发货单');
		return;
	}
	
	$l('../sail/transport.do?method=findConsign&fullId=' + getRadioValue('consigns') + '&gid=' + getRadio('consigns').pgid);
}

function load()
{
	loadForm();

	$('#dlg').dialog({
        modal:true,
        closed:true,
        buttons:{
            '确 定':function(){
                centerSubmit();
            },
            '取 消':function(){
                $('#dlg').dialog({closed:true});
            }
        }
	});

	$ESC('dlg');
}

function pagePrint()
{
	if (getRadioValue('consigns') == '')
	{
		alert('请选择发货单');
		return;
	}

	var industryId = getRadio('consigns').industryId;

	// 银行事业部
	if (industryId == '5350926')
	{
		$('#dlg').dialog({closed:false});
	}
	else
	{
		window.open('../sail/transport.do?method=findConsign&forward=1&fullId=' + getRadioValue("consigns") + '&distId=' + getRadio('consigns').distId + '&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
	}
}

function centerSubmit()
{
	var printType = $$('printType');

    $('#dlg').dialog({closed:true});

    if (printType == 0)
    {
    	window.open('../sail/transport.do?method=findConsign&forward=1&fullId=' + getRadioValue("consigns") + '&distId=' + getRadio('consigns').distId + '&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
    }else
    {
    	window.open('../sail/transport.do?method=findConsign&forward=4&fullId=' + getRadioValue("consigns") + '&distId=' + getRadio('consigns').distId + '&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());        
    }
}

function rchange()
{
    $('#printType').val($$('ptRadio'));
}

function dialog_open()
{
    $v('dia_inner', true);
}

function exports()
{
    if (window.confirm("确定导出当前的全部查询的发货单?"))
    document.location.href = '../sail/transport.do?method=exportConsign';
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/transport.do">
<input type="hidden" name="method" value="queryConsign"> 
<input type="hidden" value="1" name="firstLoad">
	
	<p:navigation
	height="22">
	<td width="550" class="navigation">发货单管理 &gt;&gt; 发货单列表</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr class="content1">
				<td width="15%" align="center">发货日期从</td>
				<td align="left">
				<p:plugin name="beginDate" size="20" value="${g_queryConsign_pmap.beginDate}"/>
				</td>
				<td width="15%" align="center">到</td>
				<td align="left">
				<p:plugin name="endDate" size="20" value="${g_queryConsign_pmap.endDate}"/>
				</td>
			</tr>
			
			<tr class="content2">
				<td width="15%" align="center">(到货)开始时间</td>
				<td align="left">
				<p:plugin name="abeginDate" size="20" value="${g_queryConsign_pmap.abeginDate}"/>
				</td>
				<td width="15%" align="center">(到货)结束时间</td>
				<td align="left">
				<p:plugin name="aendDate" size="20" value="${g_queryConsign_pmap.aendDate}"/>
				</td>
			</tr>

			<tr class="content1">
				<td width="15%" align="center">货单状态:</td>
				<td align="left"><select name="currentStatus"
					class="select_class" values="${g_queryConsign_pmap.currentStatus}">
					<option value="">--</option>
					<option value="1">初始</option>
					<option value="2">通过</option>
				</select></td>
				<td width="15%" align="center">单号：</td>
				<td align="left">
				<input name="fullId" size="20" value="${g_queryConsign_pmap.fullId}"  />
				</td>
			</tr>
			
			<tr class="content1">
				<td colspan="4" align="right"><input type="submit"
					class="button_class" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;<input
					type="reset" class="button_class"
					value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
			</tr>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>发货单列表</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class">选择</td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>配送单</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>单据时间</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>单据</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>货单状态</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>到货时间</strong></td>

			</tr>
			
			<c:forEach items="${consignList}" var="item"
                varStatus="vs">
                <tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
                    <td align="center"><input type="radio" name="consigns"
                        statuss="${item.currentStatus}" 
                        value="${item.fullId}"
                        distId="${item.distId}"
                        industryId="${item.industryId}"
                        ${vs.index== 0 ? "checked" : ""}/></td>
                    <td align="center" onclick="hrefAndSelect(this)">
                    <a
                        href="../sail/transport.do?method=findConsign&fullId=${item.fullId}&distId=${item.distId}"
                        >
                    ${item.distId}</a></td>
                    <td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>
                    <td align="center" onclick="hrefAndSelect(this)"><a href="../sail/out.do?method=findOut&radioIndex=0&fow=99&outId=${item.fullId}">${item.fullId}</a></td>
                    <td align="center" onclick="hrefAndSelect(this)">${my:get('consignStatus', item.currentStatus)}</td>
                    
                    <td align="center" onclick="hrefAndSelect(this)">
                    	<c:if test="${item.arriveDate == 'null'}">
                    	--
                    	</c:if>
                    	<c:if test="${item.arriveDate != 'null'}">
                    		${item.arriveDate}
                    	</c:if>
                    </td>
                </tr>
            </c:forEach>
            
            <p:formTurning form="formEntry" method="queryConsign"></p:formTurning>
		</table>

	</p:subBody>

	<p:line flag="1"/>
	
	<br>

	<p:button>
		<div align="right">
			    <input
	            type="button" class="button_class"
	            value="&nbsp;导出查询结果&nbsp;" onclick="exports()" />&nbsp;&nbsp;
				<!--<input type="button" 
				class="button_class" onclick="pagePrint()"
				value="&nbsp;&nbsp;定制打印&nbsp;&nbsp;">&nbsp;&nbsp;
				--><input type="button" class="button_class"
				value="&nbsp;&nbsp;处理发货单&nbsp;&nbsp;" onclick="process()">&nbsp;&nbsp;
			</div>	
	</p:button>

	<p:message2 />

</p:body></form>
<div id="dlg" title="选择打印类型" style="width:320px;height:300px;">
    <div style="padding:20px;height:200px;display:none" id="dia_inner" title="" >
    <input type="hidden" name="printType" id="printType" value=""><br>
    <input type="radio" name="ptRadio" value="0" onclick="rchange()">按单打印<br>
    <input type="radio" name="ptRadio" value="1" onclick="rchange()">按客户打印<br>
   </div>
</div>

</body>
</html>

