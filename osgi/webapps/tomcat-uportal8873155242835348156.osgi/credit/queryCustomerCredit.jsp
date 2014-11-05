<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="客户信用明细" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
	 guidMap = {
		 title: '客户信用明细',
		 url: '../credit/customer.do?method=queryCustomerCredit&cid=${param.targerId}',
		 colModel : [
		     {display: '时间', name : 'logTime', width : '12%', sortable : true, align: 'left'},
		     {display: '指标', name : 'pitemName', width : '15%'},
		     {display: '指标项', name : 'itemName', width : '20%'},
		     {display: '子项', name : 'valueName', width : '10%'},
		     {display: '日志', name : 'log', width : 'auto'},
		     {display: '指标类型', name : 'ptype', width : '10%', cc: 'creditType'},
		     {display: '指标值', name : 'val', width : '10%', toFixed: 2, sortable : true}
		     ],
		 extAtt: {
		 },
		 buttons : [
		     {id: 'search', bclass: 'search', onpress : doSearch, auth: 'true'},
		     {id: 'back', bclass: 'back', caption:'返回上一页', onpress : doBack}
		     ],
		 <p:conf/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['动态指标'], 'red');
}
 
function doBack()
{
    window.history.go(-1);
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCustomerCredit');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../examine/city.do" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>