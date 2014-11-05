<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="回访对账任务管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var gurl = '../customerService/feedback.do?method=';
var addUrl = '../feedback/addBlack.jsp';
var ukey = 'FeedBackVisit';

var mode = '<p:value key="mode"/>';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '任务列表',
         url: gurl + 'query' + ukey + '&mode=' + mode,
         colModel : [
		   {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} ltype={type} lstatus={status} ltaskId={taskId}>', width : 40, align: 'center'},
		   {display: '任务', name : 'taskId', width : '12%'},
		   {display: '类型', name : 'taskType',  width : '5%', cc: 'feedbackType'},
		   {display: '状态', name : 'status',  width : '4%', cc: 'feedbackPstatus'},
		   {display: '回访者', name : 'caller', width : '8%'},
		   {display: '回访时间', name : 'callTime', width : '8%'},
		   {display: '客户', name : 'customerName', width : '20%'},
		   {display: '业务员', name : 'stafferName', width : '4%'},
           {display: '是否联系成功', name : 'ifHasContact', width : '8%', cc:'hasConnect'},
           {display: '联系人', name : 'contact', width : '8%'},
           {display: '联系电话', name : 'contactPhone',  width : '8%'},
           {display: '计划回复时间', name : 'planReplyDate',  width : '5%'},
           {display: '异常状态', name : 'exceptionStatus',  width : '6%', cc:'exceptionStatus'},
           {display: '异常处理人', name : 'exceptionProcesserName',  width : 'auto'}
             ],
         extAtt: {
    	 	taskId : {begin : '<a href=../customerService/feedback.do?method=findFeedBackVisit&id={id}&update=0&taskId={taskId}>', end : '</a>'}
         },
         buttons : [
			 <c:if test="${mode == 1}">       
			 {id: 'add', caption: '异常处理', bclass: 'add', onpress : addBean},
			 </c:if>
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);

}
 
function $callBack()
{
    loadForm();

    //highlights($("#mainTable").get(0), ['废弃'], 'red');
}

function addBean(opr, grid)
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb'));
	}
	else
	$error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=query' + ukey);
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message/>
<table id="mainTable" style="display: none"></table>

<p:query/>

</body>