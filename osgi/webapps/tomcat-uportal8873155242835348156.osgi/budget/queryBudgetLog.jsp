<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="预算日志申请" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

function load()
{
     preload();
     
	 guidMap = {
		 title: '预算日志列表',
		 url: '../budget/budget.do?method=queryBudgetLog',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, sortable : false, align: 'center'},
		     {display: '使用人/部门', name : 'stafferName', content: '{stafferName}/{departmentName}', width : '12%', sortable : false, align: 'left'},
		     {display: '预算/预算项', name : 'budgetName', content: '{budgetName}/{feeItemName}', width : '15%', sortable : false, align: 'left'},
		     {display: '使用来源', name : 'fromType', cc: 'budgetLogFromType', width : '5%'},
		     {display: '单据', name : 'refId', width : '15%'},
		     {display: '类型', name : 'userType', cc: 'budgetLogUserType', width : '5%'},
		     {display: '形态', name : 'status', cc: 'budgetLogStatus', width : '5%'},
		     {display: '金额', name : 'smonery', width : '5%'},
		     {display: '时间', name : 'logTime', width : '15%', sortable : true},
		     {display: '日志', name : 'log', width : 'auto'}
		     ],
		 extAtt: {
		     stafferName : {begin : '<a href=../budget/budget.do?method=findBudgetLog&id={id}>', end : '</a>'},
		     budgetName : {begin : '<a href=../budget/budget.do?method=findBudget&update=1&id={budgetId}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		<p:conf/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['使用'], 'blue');
    highlights($("#mainTable").get(0), ['临时'], 'red');
}


function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryBudgetLog');
}


</script>
</head>
<body onload="load()" class="body_class">
<p:cache />

<p:message />
<table id="mainTable" style="display: none"></table>
<p:query height="300px" width="600px"/>
</body>