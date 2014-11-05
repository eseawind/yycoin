<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="日志客户查询" link="true" guid="true" cal="false"/>
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
	 guidMap = {
		 title: '客户拜访记录',
		 url: '../customer/customer.do?method=queryWorkCustomer&targerId=${param.targerId}',
		 colModel : [
		     {display: '类型', name : 'workTypeName', width : '10%', sortable : false, align: 'left'},
		     {display: '客户', name : 'targerName', width : '15%', sortable : false, align: 'left'},
		     {display: '结果', name : 'result', width : '10%', sortable : false, align: 'left', cc: '111'},
		     {display: '职员', name : 'stafferName', width : '10%'},
		     {display: '描述', name : 'description', width : '20%'},
		     {display: '下一步', name : 'nextWork', width : '10%'},
		     {display: '时间', name : 'logTime', width : 'auto', sortable : true}
		     ],
		 extAtt: {
		     description : {begin : '<p title={description}>', end : '</p>'},
		     nextWork : {begin : '<p title={nextWork}>', end : '</p>'}
		 },
		 buttons : [
		     {id: 'back', bclass: 'back', caption:'返回上一页', onpress : doBack}
		     ],
		 <p:conf/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function $callBack()
{
    loadForm();
    
    $.highlight($("#mainTable").get(0), '优质', 'blue');
    
    $.highlight($("#mainTable").get(0), '放弃', 'red');
}
 
function doBack()
{
    window.history.go(-1);
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../examine/city.do" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>