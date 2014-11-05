<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@ page import="java.util.Date;" %>	
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="盘点列表" link="true" guid="true" cal="true" dialog="true" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script type="text/javascript">

function loadDepotpartAndStorage()
{	
	var selDepotVal = $("#sel_depot").val();
	var selDepotpartVal = $("#txt_depotpart").val();
	var selStorageVal = $("#txt_storage").val();
	
	loadDepotpart(selDepotVal);
	setTimeout(function() {
		$("#sel_depotpart option[value='"+selDepotpartVal+"']").attr("selected","selected");
	}, 100);

	setTimeout(function() {
		loadStorage(selDepotpartVal);
	}, 100);
	
	setTimeout(function() {
		$("#sel_storage option[value='"+selStorageVal+"']").attr("selected","selected");
	}, 200);
}
/**
 * 三级联动根据仓库查询仓区列表
 * 
 * @author WUL
 * @version 2012-5-10
 */
function loadDepotpart(depotId) {
	//判断是否选择了仓库
	if(""!=depotId && depotId.length>0)
	{
		$ajax('../product/reports.do?method=queryDepotpartByDepotId&depotId='
			+ depotId, callBackDepotpart);
	}else{
		$("#sel_depotpart option[name=createoptionajax]").remove();
		$("#sel_storage option[name=createoptionajax]").remove();
	}
}

/*
 * aJax查询回调函数
 * 将查询的仓区列表塞入到select框中
 */
function callBackDepotpart(data) {
	var depotpartLst = data.msg;

	var $sel_depotpart = $("#sel_depotpart");
	
	//清空select框
	$("#sel_depotpart option[name=createoptionajax]").remove();
	$("#sel_storage option[name=createoptionajax]").remove();
	
	//便利塞值
	for ( var i = 0; i < depotpartLst.length; i++) {
		var item = depotpartLst[i];

		var $optiontemp = $("<option></option>");

		$optiontemp.val(item.id);
		$optiontemp.text(item.name);
		$optiontemp.attr({
			"name" : "createoptionajax"
		});
		
		$optiontemp.appendTo($sel_depotpart);
	}

	
}

/**
 * 三级联动根据仓区查询储柜列表
 * 
 * @author WUL
 * @version 2012-5-10
 */
function loadStorage(depotpartId) {
	if(""!=depotpartId && depotpartId.length>0)
	{
		$ajax('../product/reports.do?method=queryStorageByDepotpartId&depotpartId='
			+ depotpartId, callBackStorage);
	}else{
		$("#sel_storage option[name=createoptionajax]").remove();
	}
}

/*
 * aJax查询回调函数
 */
function callBackStorage(data) {
	var storageLst = data.msg;

	var $sel_storage = $("#sel_storage");
	
	$("#sel_storage option[name=createoptionajax]").remove();
	for ( var i = 0; i < storageLst.length; i++) {
		var item = storageLst[i];

		var $optiontemp = $("<option></option>");

		$optiontemp.val(item.id);
		$optiontemp.text(item.name);
		$optiontemp.attr({
			"name" : "createoptionajax"
		});
		
		$optiontemp.appendTo($sel_storage);
	}
}



function process2()
{
	if (getRadioValue('consigns') == '')
	{
		alert('请选择');
		return;
	}
	
	document.location.href = '../depot/storage.do?method=queryStorageLog&queryType=2&productId='
	    + getRadio('consigns').pid + '&locationId=' + getRadio('consigns').loca;
}

function process()
{
    if (getRadioValue('consigns') == '')
    {
        alert('请选择');
        return;
    }
    
    document.location.href = '../product/reports.do?method=listStorageLog&productId=' + getRadioValue('consigns');
}

function exports()
{
	document.location.href = '../product/reports.do?method=export';
}

function exportsAll()
{
	//document.location.href = '../product/reports.do?method=exportAll';
	$("input[name=flag]").val("exportAll");
	submit(null,null,null);
}


function load()
{
	controlProcess();
	loadForm();
	preload();
	loadDepotpartAndStorage();

}

function cc()
{
	var beginDate = $$('beginDate');
	var endDate = $$('endDate'); //getObj('endDate').value;(同样适用)

	if (beginDate == '' || beginDate == null)
	{
		alert('开始时间必填！');
		return false;
	}
		
	if (endDate == '' || endDate == null)
	{
		alert('结束时间必填！');
		return false;
	}		
	
	if (compareDays($$('beginDate'), $$('endDate')) > 90)
	{
		alert('跨度不能大于90天');
		return false;
	}
	
	return true;
}
function stat()
{
	$("input[name=flag]").val("stat");
	submit(null, null, cc);
}

function controlProcess()
{
	<% java.util.Date date = new java.util.Date();
		int hour = date.getHours();
		int min = date.getMinutes();
		String timeStr = " "+(hour<10?("0"+hour):hour)+(min<10?("0"+min):min); 
	%>

	var serverTime = parseInt('<%=timeStr %>', 10);

	if ((serverTime>1000 && serverTime < 1200 ) || (serverTime> 1330 && serverTime < 1700))
	{
		alert('由于该功能消耗服务器资源较大，在10:00~12:00和13:30~17:00期间停止使用！');
		document.location.href = '../admin/welcome.jsp';
		return false;
	}
	else
		return true;
		
}

</script>

</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../product/reports.do">
<input type="hidden" name="method" value="statReports"> 
<input type="hidden" name="flag" value="0"/>
<p:navigation
	height="22">
	<td width="550" class="navigation">盘点管理 &gt;&gt; 盘点列表</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr class="content1">
				<td width="15%" align="center">开始时间</td>
				<td align="left"><p:plugin name="beginDate" size="20"
					value="${beginDate}" /><!-- oncheck="notNone" --></td>
				<td width="15%" align="center">结束时间</td>
				<td align="left"><p:plugin name="endDate" size="20"
					value="${endDate}" /><!-- oncheck="notNone" --></td>
			</tr>

			<tr class="content2">
				<td width="15%" align="center">仓库</td>
				<td align="left"><select id="sel_depot" name="depotId" onchange="loadDepotpart(this.value)" class="select_class"
					values="${depotId}" style="width: 100%">
					<option value="">--</option>
					<c:forEach items="${depotList}" var="item">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>
				</select></td>
				<td width="15%" align="center">仓区</td>
				
				<td align="left"><select id="sel_depotpart" name="depotpartId" onchange="loadStorage(this.value)" 
					class="select_class" values="${depotpartId}" style="width: 100%">
					<option value="">--</option>
					<!--<c:forEach items="${depotpartList}" var="item">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>-->
				</select><input id="txt_depotpart" value="${depotpartId}" type="hidden"/></td>
			</tr>
			<tr class="content1">
				<td width="15%" align="center">储位</td>
				
				<td align="left" colspan="3"><select id="sel_storage" name="storageId"
					class="select_class" values="${storageId}" style="width: 395px">
					<option value="">--</option>
					<!--<c:forEach items="${storageList}" var="item">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>-->
				</select>
				<input id="txt_storage" value="${storageId}" type="hidden"/></td>
			</tr>


			<tr class="content2">
				<td width="15%" align="center">管理类型</td>
				<td align="left" colspan="1"><select name="managerType"
					class="select_class" values="${managerType}" style="width: 100%">
					<p:option type="pubManagerType" empty="true" />
				</select></td>
				<td width="15%" align="center">产品类型</td>
				<td align="left" colspan="1"><select name="productType"
					class="select_class" values="${productType}" style="width: 100%">
					<p:option type="productType" empty="true" />
				</select></td>
			</tr>

			<tr class="content1">
				<td width="15%" align="center">产品名称</td>
				<td align="left" colspan="1"><input type="text"
					name="productName" style="width: 100%" value="${productName}"></td>
				<td width="15%" align="center">产品编码</td>
				<td align="left" colspan="1"><input type="text"
					name="productCode" style="width: 100%" value="${productCode}"></td>
			</tr>

			<tr class="content2">
				<td width="15%" align="center">地点</td>
				<td align="left" colspan="1"><select name="industryId2"
					class="select_class" values="${industryId2}" style="width: 100%">
					<p:option type="$adminindustryList" empty="true" />
				</select></td>
				<td width="15%" align="center">事业部</td>
				<td align="left" colspan="1"><select name="industryId"
					class="select_class" values="${industryId}" style="width: 100%">
					<p:option type="$industryList" empty="true" />
				</select></td>
			</tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="button"
					onclick="stat()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;<input
					type="reset" class="button_class"
					value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
			</tr>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>盘点列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class">选择</td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>地点</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>事业部</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>仓库</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>仓区</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>产品</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>原始数量</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>异动数量</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>当前数量</strong></td>
			</tr>

			<c:forEach items="${statList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type="radio" name="consigns"
						value="${item.productId};${item.depotpartId}"
						pid="${item.productId}" loca="${item.locationId}" ${vs.index== 0 ? "checked" : ""}/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.industryName2}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.industryName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.locationName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.depotpartName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}(${item.productCode})</td>

					<td align="center" onclick="hrefAndSelect(this)">${item.preAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.changeAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.currentAmount}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="98%" rightWidth="2%">
		<div align="right"><input type="button" class="button_class"
			value="&nbsp;&nbsp;导出异动&nbsp;&nbsp;" onclick="exports()">&nbsp;&nbsp;
		<input type="button" class="button_class"
			value="&nbsp;&nbsp;导出盘点&nbsp;&nbsp;" onclick="exportsAll()">&nbsp;&nbsp;
		<input type="button" class="button_class"
			value="&nbsp;&nbsp;查看明细&nbsp;&nbsp;" onclick="process()">&nbsp;&nbsp;
		<input type="button" class="button_class"
			value="&nbsp;&nbsp;仓库下异动明细&nbsp;&nbsp;" onclick="process2()">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

