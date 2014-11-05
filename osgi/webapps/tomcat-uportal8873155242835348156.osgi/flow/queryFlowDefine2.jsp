<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="流程定义" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
	 guidMap = {
		 title: '流程定义列表',
		 url: '../flow/flow.do?method=queryFlowDefine2',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} sstatus={status}>', width : 40, align: 'center'},
		     {display: '名称', name : 'name', width : '30%'},
		     {display: '模式', name : 'mode', width : '70', cc : 'flowMode'},
		     {display: '分类', name : 'type', width : '40', cc : 112},
		     {display: '状态', name : 'status', width : '40', cc : 'flowStatus'},
		     {display: '创建人', name : 'stafferName', width : '15%'},
		     {display: '时间', name : 'logTime', width : 'auto'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../flow/flow.do?method=findFlowDefine&id={id} title=查看流程定义>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', caption: '创建流程实例',  onpress : addBean},
		     {id: 'search', bclass: 'search',  onpress : doSearch}
		     ],
		 <p:conf />
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function $callBack()
{
    loadForm();
}

function addBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    $l('../flow/instance.do?method=preForAddFlowInstance&id=' + getRadioValue('checkb'));
}


function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryFlowDefine2');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../flow/flow.do" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>