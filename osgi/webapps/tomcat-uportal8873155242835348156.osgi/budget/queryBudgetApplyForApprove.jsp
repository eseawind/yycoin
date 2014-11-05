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
<script src="../budget_js/queryBudgetApply.js"></script>
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
         url: '../budget/budget.do?method=queryBudgetApplyForApprove&fowardType=${param.fowardType}',
             colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lname={budgetName} status1={status}>', width : 40, sortable : false, align: 'center'},
             {display: '预算', name : 'budgetName', width : '25%', sortable : false, align: 'left'},
             {display: '提交人', name : 'stafferName', width : '5%', sortable : false, align: 'left'},
             {display: '预算类型', name : 'budgetType', width : '8%', sortable : true, align: 'left', cc: 'budgetType'},
             {display: '申请类型', name : 'type', width : '8%', sortable : true, align: 'left', cc: 'budgetApplyType'},
             {display: '状态', name : 'status', width : '15%', sortable : false, align: 'left',cc: 'budgetApplyStatus'},
             {display: '时间', name : 'logTime', width : 'auto', sortable : true}
             ],
         extAtt: {
             budgetName : {begin : '<a href=../budget/budget.do?method=findBudgetApply&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'pass', caption: '通过预算变更', bclass: 'pass', onpress : doPassApply},
             {id: 'reject', caption: '驳回预算变更', bclass: 'reject', onpress : doRejectApply}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}

function $callBack()
{
    loadForm();
    
    $.highlight($("#mainTable").get(0), '结束', 'red');
    
    $.highlight($("#mainTable").get(0), '通过', 'blue');
    
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
