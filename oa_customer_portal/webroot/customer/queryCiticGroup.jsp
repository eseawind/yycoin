<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="中信客户分组管理" link="true" guid="true" cal="false" dialog="true"/>
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
		 title: '中信客户分组列表',
		 url: '../customer/citicGroup.do?method=queryCiticGroup',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
		     {display: '名称', name : 'stafferName', width : '15%'},
		     {display: '客户', name : 'customerNames', width : 'auto'}
		     ],
		 extAtt: {
			 stafferName : {begin : '<a href=../customer/citicGroup.do?method=findCiticGroup&id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean, auth: 'true'},
		     {id: 'update', bclass: 'update',  onpress : updateBean, auth: 'true'},
		     {id: 'del', bclass: 'del',  onpress : delBean, auth: 'true'},
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		 <p:conf callBack="loadForm"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function addBean(opr, grid)
{
    $l('../customer/addCiticGroup.jsp?menu=1');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && window.confirm('确定删除群组?'))
    $ajax('../customer/citicGroup.do?method=delCiticGroup&id=' + getRadioValue('checkb'), callBackFun);
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../customer/citicGroup.do?method=findCiticGroup&update=1&id=' + getRadioValue('checkb'));
    }
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCiticGroup');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../customer/citicGroup.do" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="300px"/>
</body>