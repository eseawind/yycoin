<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="模板管理" link="true" guid="true" cal="true" dialog="true" />
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
		 title: '流程模板列表',
		 url: '../flow/template.do?method=queryTemplateFile',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
		     {display: '名称', name : 'name', width : '20%'},
		     {display: '附件名', name : 'fileName', width : '40%'},
		     {display: '时间', name : 'logTime', width : 'auto'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../flow/template.do?method=findTemplateFile&id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean, auth: '0701'},
		     {id: 'update', bclass: 'update',  onpress : updateBean, auth: '0701'},
		     {id: 'del', bclass: 'del',  onpress : delBean, auth: '0701'},
		     {id: 'search', bclass: 'search',  onpress : doSearch}
		     ],
		 <p:conf callBack="loadForm"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function addBean(opr, grid)
{
    $l('../flow/addTemplateFile.jsp');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && window.confirm('确定删除模板?'))
    $ajax('../flow/template.do?method=deleteTemplateFile&id=' + getRadioValue('checkb'), callBackFun);
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../flow/template.do?method=findTemplateFile&update=1&id=' + getRadioValue('checkb'));
    }
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryTemplateFile');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../flow/template.do" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>