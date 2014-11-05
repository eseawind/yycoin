<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="预算管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../budget_js/queryBudget.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var allDef = getAllDef();
var guidMap;
var thisObj;

function load()
{
     preload();
     
	 guidMap = {
		 title: '预算列表',
		 url: '../budget/budget.do?method=querySelfBudget',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lname={name} status1={status} etype={type} llevel={level}>', width : 40, sortable : false, align: 'center'},
		     {display: '预算', name : 'name', width : '12%', sortable : false, align: 'left'},
		     {display: '权签人', name : 'signerName', width : '5%', sortable : false, align: 'left'},
		     {display: '级别', name : 'type', width : '8%', sortable : true, align: 'left', cc: 'budgetType'},
		     {display: '类型', name : 'level', width : '8%', sortable : true, align: 'left', cc: 'budgetLevel'},
		     {display: '状态', name : 'status', width : '5%', sortable : false, align: 'left',cc: 'budgetStatus'},
		     {display: '执行状态', name : 'carryStatus', width : '5%', sortable : false, align: 'left',cc: 'budgetCarry'},
		     {display: '预算/使用', name : 'stotal', width : '12%', content : '{stotal}/{srealMonery}', sortable : true, cname: 'total'},
		     {display: '预算时间', name : 'beginDate', content : '{beginDate}至{endDate}', width : '10%', sortable : true, align: 'left', cname: 'beginDate'},
		     {display: '父预算', name : 'parentName', width : '10%', sortable : true, align: 'left'},
		     {display: '预算部门', name : 'budgetFullDepartmentName', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../budget/budget.do?method=findBudget&update=1&id={id}>', end : '</a>'},
		     parentName : {begin : '<a href=../budget/budget.do?method=findBudget&update=1&id={parentId}>', end : '</a>'}
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
    
    $.highlight($("#mainTable").get(0), '结束', 'red');
    
    highlights($("#mainTable").get(0), ['通过', '执行中'], 'blue');
    
}

</script>
</head>
<body onload="load()" class="body_class">
<p:cache />
<p:pop title="请输入驳回原因" id="rejectReson"></p:pop>
<div id="logDiv" style="display:none">
<p align='left'><label><font color=""><b>审批日志:</b></font></label></p>
<p><label>&nbsp;</label></p>
<div id="logD" align='left'>
</div>
<p><label>&nbsp;</label></p>
<p>
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;关 闭&nbsp;&nbsp;' class='button_class' onclick='$cancle()'/>
</p>
<p><label>&nbsp;</label></p>
</div>

<p:message />
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>