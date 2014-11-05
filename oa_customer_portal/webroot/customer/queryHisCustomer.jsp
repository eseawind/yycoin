<%@page contentType="text/html; charset=UTF-8" errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="历史客户" link="true" guid="true" cal="false"/>
<script src="../js/common.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
	 guidMap = {
		 title: '客户修改历史',
		 url: '../customer/customer.do?method=queryHisCustomer&id=${param.id}',
		 colModel : [
		     {display: '客户', name : 'name', width : '20%', sortable : false, align: 'left'},
		     {display: '编码', name : 'code', width : '10%', sortable : false, align: 'left'},
		     {display: '类型', name : 'selltype', width : '10%', sortable : false, align: 'left', cc: 101},
		     {display: '类型1', name : 'selltype1', width : '10%', sortable : false, align: 'left', cc: 124},
		     {display: '级别', name : 'qqtype', width : '10%', sortable : false, align: 'left', cc: 104},
		     {display: '分类', name : 'rtype', width : '10%', sortable : false, align: 'left', cc: 105},
		      {display: '修改人', name : 'updaterName', width : '10%', sortable : false, align: 'left', cc: 106},
		     {display: '时间', name : 'loginTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../customer/customer.do?method=findHisCustomer&id={id} title=查看明细>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'back', caption: '返回上一页', bclass: 'back', onpress : gback}
		     ],
		 <p:conf callBack="loadForm" queryMode="1"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }
 
function gback()
{
    window.history.go(-1);
}
</script>
</head>
<body class="body_class" onload="load()">
<form>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>