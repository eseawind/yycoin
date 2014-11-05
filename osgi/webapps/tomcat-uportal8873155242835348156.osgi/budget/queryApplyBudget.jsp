<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="预算申请" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../budget_js/queryBudgetApply.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

function load()
{
     preload();
     
	 guidMap = {
		 title: '预算申请列表',
		 url: '../budget/budget.do?method=queryBudgetApply',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lname={budgetName} status1={status}>', width : 40, sortable : false, align: 'center'},
		     {display: '预算', name : 'budgetName', width : '25%', sortable : false, align: 'left'},
		     {display: '提交人', name : 'stafferName', width : '5%', sortable : false, align: 'left'},
		     {display: '申请类型', name : 'type', width : '8%', sortable : true, align: 'left', cc: 'budgetApplyType'},
		     {display: '状态', name : 'status', width : '15%', sortable : false, align: 'left',cc: 'budgetApplyStatus'},
		     {display: '时间', name : 'logTime', width : 'auto', sortable : true}
		     ],
		 extAtt: {
		     budgetName : {begin : '<a href=../budget/budget.do?method=findBudgetApply&id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'update', bclass: 'pass', caption: '财务总监核准', onpress : cfo_pass, auth: '050602'},
		     {id: 'update1', bclass: 'reject', caption: '财务总监驳回', onpress : cfo_reject, auth: '050602'},
		     {id: 'update', bclass: 'pass', caption: '总经理核准', onpress : coo_pass, auth: '050603'},
             {id: 'update1', bclass: 'reject', caption: '总经理驳回', onpress : coo_reject, auth: '050603'},
		     {id: 'update', bclass: 'pass', caption: '董事长核准', onpress : ceo_pass, auth: '050604'},
             {id: 'update1', bclass: 'reject', caption: '董事长驳回', onpress : ceo_reject, auth: '050604'}
		     ],
		 <p:conf/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function $callBack()
{
    loadForm();
}

</script>
</head>
<body onload="load()" class="body_class">
<p:cache />

<p:pop title="请输入驳回原因" id="rejectReson"/>

<p:message />
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>