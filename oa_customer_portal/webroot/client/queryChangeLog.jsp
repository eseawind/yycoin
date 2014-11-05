<%@page contentType="text/html; charset=UTF-8" errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="客户日志" link="true" guid="true" cal="true" dialog="true"/>
<script src="../js/json.js"></script>
<script src="../js/common.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/public.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
	 preload();
	 
	 guidMap = {
		 title: '客户职员关系变动日志',
		 url: '../client/client.do?method=queryChangeLog',
		 colModel : [
		     {display: '客户', name : 'customerName', width : '20%', align: 'left'},
             {display: '编码', name : 'customerCode', width : '10%', align: 'left'},
             {display: '职员', name : 'stafferName', width : '20%', align: 'left'},
             {display: '类型', name : 'operation', width : '10%', align: 'left', cc: 'changeLogOpr'},
             {display: '时间', name : 'logTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     //name : {begin : '<a href=../customer/provider.do?method=findHisProvider&id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		 <p:conf callBack="loadForm"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryChangeLog');
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);
    
    if (data.ret == 0)
    commonQuery();
}
</script>
</head>
<body class="body_class" onload="load()">
<form>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>