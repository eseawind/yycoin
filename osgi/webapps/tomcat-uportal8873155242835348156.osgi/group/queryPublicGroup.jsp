<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="群组管理" link="true" guid="true" cal="false" dialog="true"/>
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
		 title: '公共群组列表',
		 url: '../group/group.do?method=queryPublicGroup',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
		     {display: '名称', name : 'name', width : '15%'},
		     {display: '成员', name : 'stafferNames', width : '70%'},
		     {display: '类型', name : 'type', width : 'auto', cc: 'groupType'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../group/group.do?method=findGroup&id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean, auth: '0602'},
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		 <p:conf callBack="loadForm"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function addBean(opr, grid)
{
    $l('../group/addGroup.jsp?type=1&menu=1');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && window.confirm('确定删除群组?'))
    $ajax('../group/group.do?method=delGroup&id=' + getRadioValue('checkb'), callBackFun);
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../group/group.do?method=findGroup&update=1&id=' + getRadioValue('checkb'));
    }
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryPublicGroup');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../group/group.do" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="300px"/>
</body>